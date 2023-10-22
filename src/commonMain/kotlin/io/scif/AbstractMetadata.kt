/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2017 SCIFIO developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.scif

import org.scijava.io.handle.DataHandle
import org.scijava.io.location.Location
import kotlin.jvm.JvmOverloads
import kotlin.jvm.Transient

/**
 * Abstract superclass of all SCIFIO [io.scif.Metadata] implementations.
 *
 * @see io.scif.Metadata
 *
 * @see io.scif.Parser
 *
 * @see io.scif.HasFormat
 *
 * @author Mark Hiner
 */
abstract class AbstractMetadata(list: List<ImageMetadata>? = null) : AbstractHasSource(), TypedMetadata {
    // -- Fields --
    /* The image source associated with this Metadata. */
//    @Transient
//    private var source: DataHandle<Location>? = null

    /* The image source location associated with this Metadata. */
//    @Transient var sourceLocation: Location? = null

    /** The location an image with this metadata will be written to.  */
//    @Transient var destinationLocation: Location? = null

    /* Whether the Metadata should be filtered or not. */
    override var isFiltered: Boolean = false

    /* Contains a list of metadata objects for each image in this dataset */
    @Transient
    private var imageMeta: MutableList<ImageMetadata>? = null

    // -- Setters --
    // -- Getters --
    /* A string id for this dataset. */
    override var datasetName: String? = null

    /* A table of Field key, value pairs */
//    private var table: MetaTable?

    constructor(copy: Metadata) : this(copy.all) {
        table = DefaultMetaTable(copy.getTable())
    }

    // -- Constructors --
    init {
        imageMeta = java.util.ArrayList<ImageMetadata>()
        table = DefaultMetaTable()

        if (list != null) {
            for (core in list) {
                imageMeta!!.add(core.copy())
            }
        }
    }

    // -- Metadata API Methods --
    fun setSource(source: DataHandle<Location?>) {
        this.source = source

        if (source != null) datasetName = source.get().getName()
    }

    fun getSource(): DataHandle<Location>? {
        return source
    }

    fun get(imageIndex: Int): ImageMetadata {
        return imageMeta!![imageIndex]
    }

    override val all: List<Any>?
        get() = imageMeta

    override val imageCount: Int
        get() = imageMeta!!.size

    override val datasetSize: Long
        get() {
            var size: Long = 0

            for (i in all!!.indices) size += get(i).getSize()

            return size
        }

    fun add(meta: ImageMetadata) {
        imageMeta!!.add(meta)
    }

    override fun createImageMetadata(imageCount: Int) {
        imageMeta!!.clear()

        for (i in 0 until imageCount) add(DefaultImageMetadata())
    }

    // -- HasMetaTable API Methods --
    fun getTable(): MetaTable? {
        if (table == null) table = DefaultMetaTable(isFiltered)
        return table
    }

    fun setTable(table: MetaTable?) {
        this.table = table
    }

    // -- HasSource API Methods --
    @Throws(java.io.IOException::class) fun close(fileOnly: Boolean) {
        if (source != null) {
            source.close()
        }

        if (!fileOnly) reset(javaClass)
    }

    // -- Helper Methods --
    private fun reset(type: java.lang.Class<*>?) {
        if (type == null || type == AbstractMetadata::class.java) return

        for (f in type.getDeclaredFields()) {
            f.setAccessible(true)

            if (java.lang.reflect.Modifier.isFinal(f.getModifiers())) continue

            // only reset annotated fields
            if (f.getAnnotation(io.scif.Field::class.java) == null) continue

            val fieldType: java.lang.Class<*> = f.getType()

            try {
                if (fieldType == Boolean::class.javaPrimitiveType) f.set(this, false)
                else if (fieldType == Char::class.javaPrimitiveType) f.set(this, 0)
                else if (fieldType == Double::class.javaPrimitiveType) f.set(this, 0.0)
                else if (fieldType == Float::class.javaPrimitiveType) f.set(this, 0f)
                else if (fieldType == Int::class.javaPrimitiveType) f.set(this, 0)
                else if (fieldType == Long::class.javaPrimitiveType) f.set(this, 0L)
                else if (fieldType == Short::class.javaPrimitiveType) f.set(this, 0)
                else f.set(this, null)
            } catch (e: java.lang.IllegalArgumentException) {
                log().debug(e.message)
            } catch (e: java.lang.IllegalAccessException) {
                log().debug(e.message)
            }

            table = DefaultMetaTable()
            imageMeta = java.util.ArrayList<ImageMetadata>()

            // check superclasses and interfaces
            reset(type.getSuperclass())
            for (c in type.getInterfaces()) {
                reset(c)
            }
        }
    }
}