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

//import io.scif.util.SCIFIOMetadataTools
//import net.imagej.axis.Axes
//import net.imagej.axis.AxisType
//import org.scijava.util.ArrayUtils
import io.scif.util.FormatTools
import io.scif.util.SCIFIOMetadataTools.getPlanarAxes
import net.imagej.axis.Axes
import net.imagej.axis.CalibratedAxis
import net.imglib2.Dimensions
import net.imglib2.Interval
import safeMultiply64
import uns.L

/**
 * Abstract superclass of all [io.scif.ImageMetadata] implementations.
 *
 * @see io.scif.ImageMetadata
 *
 * @see io.scif.DefaultImageMetadata
 *
 * @author Mark Hiner
 */
abstract class AbstractImageMetadata : /*AbstractCalibratedInterval<CalibratedAxis>,*/ ImageMetadata {

    // -- Fields --

    /** Width (in pixels) of thumbnail planes in this image.  */
    @Field("thumbSizeX")
    override var thumbSizeX: Long = 0

    /** Height (in pixels) of thumbnail planes in this image.  */
    @Field("thumbSizeY")
    override var thumbSizeY: Long = 0

    /**
     * Describes the number of bytes per pixel. Must be one of the *static*
     * pixel types (e.g. `INT8`) in [io.scif.util.FormatTools].
     */
    @Field("pixelType")
    override var pixelType: Int = 0

    /** Number of valid bits per pixel.  */
    @Field("bitsPerPixel")
    override var bitsPerPixel: Int = 0
        get() = when {
            field <= 0 -> FormatTools.getBitsPerPixel(pixelType)
            else -> field
        }

    /**
     * Indicates whether or not we are confident that the dimension order is
     * correct.
     */
    @Field("orderCertain")
    override var isOrderCertain: Boolean = false

    /** Indicates whether or not each pixel's bytes are in little endian order.  */
    @Field("littleEndian")
    override var isLittleEndian: Boolean = false

    /** Indicates whether or not the images are stored as indexed color.  */
    @Field("indexed")
    override var isIndexed: Boolean = false

    /** Indicates whether or not we can ignore the color map (if present).  */
    @Field("falseColor")
    override var isFalseColor: Boolean = true

    /**
     * Indicates whether or not we are confident that all of the metadata stored
     * within the file has been parsed.
     */
    @Field("metadataComplete")
    override var isMetadataComplete: Boolean = false

    /**
     * Indicates whether or not this series is a lower-resolution copy of another
     * series.
     */
    @Field("thumbnail")
    override var isThumbnail: Boolean = false

    // TODO: Consider typing rois and tables on more specific data structures.
    /** The ROIs for this image.  */
    @Field("ROIs")
    override var rOIs: Any? = null

    /** The tables for this image.  */
    @Field("tables")
    override var tables: Any? = null

    // -- Named API methods --
    /** The name of the image.  */
    override var name: String? = null

    /** A table of Field key, value pairs  */
    //    private var table: MetaTable? = null

    // -- Constructors --

    // -- Constructors --
    //    constructor(n: Int) : super(n)
    //
    //    constructor(n: Int, vararg axes: CalibratedAxis) : super(n, *axes)
    //
    //    constructor(n: Int, axes: List<CalibratedAxis>) : super(n, axes)
    //
    //    constructor(interval: Interval) : super(interval)
    //
    //    constructor(interval: Interval, vararg axes: CalibratedAxis) : super(interval, *axes)
    //
    //    constructor(interval: Interval, axes: List<CalibratedAxis>) : super(interval, axes)
    //
    //    constructor(dimensions: Dimensions) : super(dimensions)
    //
    //    constructor(dimensions: Dimensions, vararg axes: CalibratedAxis) : super(dimensions, axes)
    //
    //    constructor(dimensions: Dimensions, axes: List<CalibratedAxis>) : super(dimensions, axes)
    //
    //    constructor(dimensions: LongArray) : super(dimensions)
    //
    //    constructor(dimensions: LongArray, vararg axes: CalibratedAxis) : super(dimensions, *axes)
    //
    //    constructor(dimensions: LongArray, axes: List<CalibratedAxis>) : super(dimensions, axes)
    //
    //    constructor(min: LongArray, max: LongArray) : super(min, max)
    //
    //    constructor(min: LongArray, max: LongArray, vararg axes: CalibratedAxis) : super(min, max, *axes)
    //
    //    constructor(min: LongArray, max: LongArray, axes: List<CalibratedAxis>) : super(min, max, axes)
    //
    //    constructor(source: ImageMetadata) : super(source) {
    //        copy(source)
    //    }

    override val size: Long
        // -- Getters --
        get() {
            var size: Long = 1
            TODO()
            //            for (i in 0 until numDimensions) {
            //                size = DataTools.safeMultiply64(size, dimension(i))
            //            }

            val bytesPerPixel = bitsPerPixel / 8

            return safeMultiply64(size, bytesPerPixel.L)
        }

    //    override fun getThumbSizeX(): Long {
    //        var thumbX = thumbSizeX
    //
    //        // If the X thumbSize isn't explicitly set, scale the actual width using
    //        // the thumbnail dimension constant
    //        if (thumbX == 0L) {
    //                  val sx: Long = dimension(Axes.X)
    //                  val sy: Long = dimension(Axes.Y)
    //
    //            if (sx < THUMBNAIL_DIMENSION && sy < THUMBNAIL_DIMENSION) thumbX = sx
    //            else if (sx > sy) thumbX = THUMBNAIL_DIMENSION
    //            else if (sy > 0) thumbX = sx * THUMBNAIL_DIMENSION / sy
    //            if (thumbX == 0L) thumbX = 1
    //        }
    //
    //        return thumbX
    //    }
    //
    //    override fun getThumbSizeY(): Long {
    //        var thumbY = thumbSizeY
    //
    //        // If the Y thumbSize isn't explicitly set, scale the actual width using
    //        // the thumbnail dimension constant
    //        if (thumbY == 0L) {
    //            val sx: Long = dimension(Axes.X)
    //			      val sy: Long = dimension(Axes.Y)
    //            thumbY = 1
    //
    //            if (sx < THUMBNAIL_DIMENSION && sy < THUMBNAIL_DIMENSION) thumbY = sy
    //            else if (sy > sx) thumbY = THUMBNAIL_DIMENSION
    //            else if (sx > 0) thumbY = sy * THUMBNAIL_DIMENSION / sx
    //            if (thumbY == 0L) thumbY = 1
    //        }
    //
    //        return thumbY
    //    }
    //

    //    override fun getAxes(): List<CalibratedAxis>? {
    //        return getEffectiveAxes()
    //    }
    //
    //    override val axesPlanar: List<Any>?
    //        get() = getAxisList(true)
    //
    //    override val axesNonPlanar: List<Any>?
    //        get() = getAxisList(false)

    override val blockCount: Long
        get() {
            var length: Long = 1

            val planarAxes = getPlanarAxes(this)
            TODO()
//            for (i in planarAxes.dimensions)
//                length *= dimension(planarAxes.axis(i).type)

            return length
        }

    final override fun copy(toCopy: ImageMetadata) {
        populate(toCopy.name!!, toCopy.pixelType, toCopy.isOrderCertain, toCopy.isLittleEndian,
                 toCopy.isIndexed, toCopy.isFalseColor, toCopy.isMetadataComplete)

        // TODO Use setters, not direct assignment.
        table = DefaultMetaTable(toCopy.table!!)
        isThumbnail = toCopy.isThumbnail
        thumbSizeX = toCopy.thumbSizeX
        thumbSizeY = toCopy.thumbSizeY
    }

    override fun populate(name: String, pixelType: Int, orderCertain: Boolean,
                          littleEndian: Boolean, indexed: Boolean, falseColor: Boolean,
                          metadataComplete: Boolean) =
        populate(name, pixelType, FormatTools.getBitsPerPixel(pixelType),
                 orderCertain, littleEndian, indexed, falseColor, metadataComplete)

    override fun populate(name: String, pixelType: Int, bitsPerPixel: Int,
                          orderCertain: Boolean, littleEndian: Boolean,
                          indexed: Boolean, falseColor: Boolean,
                          metadataComplete: Boolean) {
        this.name = name
        this.bitsPerPixel = bitsPerPixel
        this.isFalseColor = falseColor
        this.isIndexed = indexed
        this.isLittleEndian = littleEndian
        this.isOrderCertain = orderCertain
        this.pixelType = pixelType
    }

    // -- HasTable API Methods --
    //    fun getTable(): MetaTable? {
    //        if (table == null) table = DefaultMetaTable() // maybe make table non-nullable and default to this?
    //        return table
    //    }
    //
    //    fun setTable(table: MetaTable?) {
    //        this.table = table
    //    }
    override var table: MetaTable? = DefaultMetaTable()

    // -- Object API --
    override fun toString(): String = TODO()// FieldPrinter(this).toString()

    companion object {
        // -- Constants --
        /** Default thumbnail width and height.  */
        const val THUMBNAIL_DIMENSION: Long = 128
    }
}