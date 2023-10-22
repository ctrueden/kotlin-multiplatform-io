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

import okio.IOException

/**
 * A dummy [io.scif.Metadata] implementation. Holds no format-specific
 * information, and adds no functionality over [io.scif.AbstractMetadata].
 *
 * @see io.scif.Metadata
 * @see io.scif.AbstractMetadata
 * @author Mark Hiner
 */
class DefaultMetadata : AbstractMetadata, DefaultComponent {
    // -- HasFormat API Methods --
    // -- Fields --
    val format: Format?

    // -- Constructors --
    constructor() : super()

    constructor(copy: Metadata) : super(copy)

    constructor(list: List<ImageMetadata>) : super(list)
}

/**
 * Interface for things that have names.
 *
 * @author Lee Kamentsky
 */
interface Named {
    /** The name of the object.  */
    var name: String?
}

/**
 * Abstract implementation of the [HasSource] interface. Provides a
 * default [.close] implementation that calls `close(false)`.
 *
 * @author Mark Hiner
 */
abstract class AbstractHasSource : AbstractHasFormat(), HasSource

/**
 * Super-interface for SCIFIO components that relate back to a [io.scif.Format].
 *
 * @see io.scif.Format
 * @author Mark Hiner
 */
interface HasFormat /*: SCIFIOPlugin */ {
    /**
     * Provides a link back to the format associated with this component.
     *
     * @return the associated Format
     */
    val format: Format?

    /**
     * Helper method for accessing [Format.formatName]. Some
     * `Formats` may be polymorphic and wish to provide additional name
     * information based on the specific dataset open - this method allows
     * overriding of that behavior.
     *
     * @return the name of the associated Format
     */
    val formatName: String
}

/**
 * Interface for components that can be attached to open image sources that
 * should be closed to avoid resource leaks
 *
 * @author Mark Hiner
 */
interface HasSource {
    /**
     * Closes the image source(s) associated with this component, with a toggle to
     * determine if the component itself is reset.
     *
     * @param fileOnly - If true, only the associated source(s) are closed.
     */
    @Throws(IOException::class)
    fun close(fileOnly: Boolean)

    /**
     * Closes the image source(s) associated with this component and resets the
     * component to its default state.
     *
     * Equivalent to calling `close(false)`.
     */
    @Throws(IOException::class)
    fun close()
}

/**
 * Interface for components that maintain a MetaTable mapping metadata names to
 * values.
 *
 * @see MetaTable
 *
 * @author Mark Hiner
 */
interface HasMetaTable {
    /**
     * @return the MetaTable attached to this component
     */
    /**
     * Sets the table for this component
     *
     * @param table - a MetaTable implementation
     */
    var table: MetaTable?
}

/**
 * Marker interface for a String : Object [Map]. Provides helper methods
 * as needed (e.g. [.putList] for mapping keys with many
 * values).
 *
 * @author Mark Hiner
 */
interface MetaTable : MutableMap<String, Any> {
    /**
     * Special [Map.put] implementation. Treats the provided
     * value as a list item. The previous value(s) is not overwritten, but instead
     * also added to the list.
     *
     * @param key - Key to a list of values
     * @param value - Value to add to the list
     */
    fun putList(key: String?, value: Any?)
}

/**
 * Interface for components that can group multiple files together (e.g. for
 * Parsing metadata or reading pixels)
 *
 * @author Mark Hiner
 */
interface Groupable {
    /**
     * Returns an int indicating that we cannot, must, or might group the files in
     * a given dataset.
     */
    //    @Throws(FormatException::class, IOException::class) fun fileGroupOption(id: Location?): Int

    /** Returns true if this is a single-file format.  */
    //    @Throws(FormatException::class, IOException::class) fun isSingleFile(id: Location?): Boolean

    /** Returns true if this format supports multi-file datasets.  */
    val hasCompanionFiles: Boolean
}

/**
 * Field annotation. Used for flagging SCIFIO component fields and
 * differentiating Metadata source fields from Java fields.
 *
 *
 * The [.label] method allows preservation of the original field name,
 * without being restricted to the Java variable naming conventions.
 *
 *
 * @see io.scif.Metadata
 * @author Mark Hiner
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Field(
        /** Original label of this field  */
        val label: String = "",
        /** Whether or not this field is actually a list of fields  */
        val isList: Boolean = false)

/**
 * Abstract super-class for all [io.scif.Groupable] components.
 *
 * @author Mark Hiner
 * @see io.scif.Groupable
 */
abstract class AbstractGroupable : AbstractHasSource(), Groupable {
    //    @Throws(FormatException::class, IOException::class) fun fileGroupOption(id: Location?): Int = FormatTools.CANNOT_GROUP

    //    @Throws(FormatException::class, IOException::class) fun isSingleFile(id: Location?): Boolean = true

    override val hasCompanionFiles: Boolean = false
}

/**
 * Extension of the base [Plane] interface. Adds the concept of a native
 * underlying data representation.
 *
 * @see io.scif.Plane
 *
 * @author Mark Hiner
 * @param <T> the native data type used to store this plane's pixel information.
</T> */
interface DataPlane<T> /*: Plane*/ {
    /**
     * Gets this plane's type-specific pixel data.
     *
     * @return The native representation for this plane's data.
     */
    /**
     * Sets the native pixel data for this plane. If [.getColorTable]
     * returns null, then this is the pixel data. Otherwise, this data should be
     * used as an index into the associated ColorTable.
     *
     * @param data - an object matching the native data type of this plane.
     */
    var data: T

    /**
     * Populate this plane by copying the provided plane.
     *
     * @param plane the plane to copy
     * @return A reference to this plane
     */
    fun populate(plane: DataPlane<T>?): DataPlane<T>?

    /**
     * Populate this plane using the specified lengths and offsets, with the
     * provided data representation of the pixels in that region.
     *
     * @param data Pixel information for the region of this plane.
     * @param bounds bounds of the planar axes.
     * @return A reference to this plane
     */
    //    fun populate(data: T, bounds: Interval?): DataPlane<T>?

    /**
     * As [.populate], but also sets the ImageMetadata of
     * the source from which this Plane was read.
     *
     * @param meta ImageMetadata of the source associated with this Plane
     * @param data Pixel information for the region of this plane.
     * @param bounds bounds of the planar axes.
     * @return A reference to this plane
     */
    //    fun populate(meta: ImageMetadata?, data: T, bounds: Interval?): DataPlane<T>?
}

/**
 * Abstract superclass for all [io.scif.Reader] implementations that
 * return a [io.scif.ByteArrayPlane] when reading datasets.
 *
 * @see io.scif.Reader
 *
 * @see io.scif.ByteArrayPlane
 *
 * @author Mark Hiner
 * @param <M> - The Metadata type required by this Reader.
</M> */
abstract class ByteArrayReader<M : TypedMetadata> : AbstractReader<M, ByteArrayPlane>(ByteArrayPlane::class) {
    // -- Reader API Methods --
    fun createPlane(bounds: Interval?): ByteArrayPlane {
        return createPlane(getMetadata().get(0), bounds)
    }

    fun createPlane(meta: ImageMetadata?,
                    bounds: Interval?): ByteArrayPlane {
        return ByteArrayPlane(meta, bounds)
    }
}

enum class MetadataLevel { MINIMUM, NO_OVERLAYS, ALL }
