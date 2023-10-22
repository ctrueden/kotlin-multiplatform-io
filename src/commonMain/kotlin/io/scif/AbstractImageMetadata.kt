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

//import io.scif.util.FormatTools
//import io.scif.util.SCIFIOMetadataTools
//import net.imagej.axis.Axes
//import net.imagej.axis.AxisType
//import net.imagej.axis.CalibratedAxis
//import org.scijava.util.ArrayUtils
import safeMultiply64
import uns.L
import kotlin.jvm.Transient

/**
 * Abstract superclass of all [io.scif.ImageMetadata] implementations.
 *
 * @see io.scif.ImageMetadata
 *
 * @see io.scif.DefaultImageMetadata
 *
 * @author Mark Hiner
 */
abstract class AbstractImageMetadata() : ImageMetadata {
    // -- Fields --
    /** Cached list of planar axes.  */
    //    @Transient
    //    private var planarAxes: MutableList<CalibratedAxis>? = null

    /** Cached list of non-planar axes.  */
    //    @Transient
    //    private var extendedAxes: MutableList<CalibratedAxis>? = null

    /** Cached list of significant (non-trailing length 1) axes.  */
    //    @Transient
    //    private var effectiveAxes: MutableList<CalibratedAxis>? = null

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

    /**
     * The Axes types for this image. Order is implied by ordering within this
     * array
     */
    //    @Field("dimTypes")
    //    @Transient
    //    override var axes: MutableList<CalibratedAxis>?

    /** Lengths of each axis. Order is parallel of dimTypes.  */
    //    @Field(label = "dimLengths")
    //    private val axisLengths: java.util.HashMap<AxisType, Long>?

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

    /**
     * Number of planar axes in this image. These will always be the first axes in
     * a list of planar and non-planar axes.
     */
    @Field("planarAxiscount")
    override var planarAxisCount: Int = -1
        set(value) {
            field = value
//            clearCachedAxes()
        }
        get() {
//            if (field == -1)
//                return SCIFIOMetadataTools.guessPlanarAxisCount(axes)
            return field
        }

    /**
     * Number of interleaved axes in this image. These will be the first planar
     * axes.
     */
    @Field("interleavedAxisCount")
    override var interleavedAxisCount: Int = -1
        get() {
            //            if (field == -1) {
            //                return SCIFIOMetadataTools.guessInterleavedAxisCount(axes)
            //            }
            return field
        }

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
    init {
        //        axes = java.util.ArrayList<CalibratedAxis>()
        //        axisLengths = java.util.HashMap<AxisType, Long>()
    }

    constructor(copy: ImageMetadata) : this() {
        copy(copy)
    }

    //    fun setAxes(axes: Array<CalibratedAxis?>, axisLengths: LongArray?) {
    //        setAxes(*axes)
    //        setAxisLengths(axisLengths)
    //    }
    //
    //    override fun setAxisTypes(vararg axisTypes: AxisType?) {
    //        val axes: Array<CalibratedAxis?> = arrayOfNulls<CalibratedAxis>(axisTypes.size)
    //
    //        for (i in axisTypes.indices) {
    //            val t: AxisType? = axisTypes[i]
    //            var c: CalibratedAxis = getAxis(t)
    //            if (c == null) c = FormatTools.createAxis(t)
    //            axes[i] = c
    //        }
    //        setAxes(*axes)
    //    }
    //
    //    override fun setAxes(vararg axisTypes: CalibratedAxis?) {
    //        this.axes = java.util.ArrayList<E>(java.util.Arrays.asList<Array<CalibratedAxis>>(*axisTypes))
    //        clearCachedAxes()
    //    }
    //
    //    override fun setAxisLengths(axisLengths: LongArray?) {
    //        if (axisLengths!!.size > axes!!.size) throw java.lang.IllegalArgumentException(
    //            "Tried to set " + axisLengths.size + " axis lengths, but " + axes
    //                    .size + " axes present." + " Call setAxisTypes first.")
    //
    //        for (i in axisLengths.indices) {
    //            updateLength(axes!![i].type(), axisLengths[i])
    //        }
    //    }
    //
    //    override fun setAxisLength(axis: CalibratedAxis, length: Long) {
    //        setAxisLength(axis.type(), length)
    //    }
    //
    //    override fun setAxisLength(axisType: AxisType, length: Long) {
    //        if (getAxisIndex(axisType, axes) == -1) {
    //            addAxis(FormatTools.createAxis(axisType), length)
    //        } else {
    //            updateLength(axisType, length)
    //        }
    //    }
    //
    //    override fun setAxis(index: Int, axis: CalibratedAxis) {
    //        val oldIndex: Int = getAxisIndex(axis)
    //
    //        // Replace existing axis
    //        if (oldIndex < 0) {
    //            val length: Long = axisLengths.remove(axes!![index].type())
    //            axes!!.removeAt(index)
    //
    //            if (index == axes!!.size) {
    //                axes!!.add(axis)
    //            } else {
    //                axes!!.add(index, axis)
    //            }
    //            axisLengths.put(axis.type(), length)
    //        } else {
    //            axes!!.remove(axes!![oldIndex])
    //            axes!!.add(index, axis)
    //        }
    //
    //        clearCachedAxes()
    //    }
    //
    //    override fun setAxisType(index: Int, axisType: AxisType?) {
    //        val axis: CalibratedAxis = FormatTools.createAxis(axisType)
    //        setAxis(index, axis)
    //    }

    override val size: Long
        // -- Getters --
        get() {
            var size: Long = 1

            //            for (a in axes) {
            //                size = ArrayUtils.safeMultiply64(size, getAxisLength(a))
            //            }

            val bytesPerPixel = bitsPerPixel / 8

            return safeMultiply64(size, bytesPerPixel.L)
        }

    override val planeSize: Long
        get() = size / planeCount

    //    override fun getThumbSizeX(): Long {
    //        var thumbX = thumbSizeX
    //
    //        // If the X thumbSize isn't explicitly set, scale the actual width using
    //        // the thumbnail dimension constant
    //        if (thumbX == 0L) {
    //            val sx: Long = getAxisLength(Axes.X)
    //            val sy: Long = getAxisLength(Axes.Y)
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
    //            val sx: Long = getAxisLength(Axes.X)
    //            val sy: Long = getAxisLength(Axes.Y)
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
    //    override fun getAxis(axisType: AxisType?): CalibratedAxis? {
    //        for (axis in axes) {
    //            if (axis.type().equals(axisType)) return axis
    //        }
    //        return null
    //    }
    //
    //    override fun getBitsPerPixel(): Int {
    //        if (bitsPerPixel <= 0) return FormatTools.getBitsPerPixel(pixelType)
    //        return bitsPerPixel
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

    override val planeCount: Long
        get() {
            var length: Long = 1

            //            for (t in axesNonPlanar!!)
            //                length *= getAxisLength(t)

            return length
        }

//    override val axesLengths: LongArray
//        get() = getAxesLengths(axes)

    //    override fun getAxesLengths(axes: List<CalibratedAxis>?): LongArray {
    //        val lengths = LongArray(axes!!.size)
    //
    //        for (i in axes.indices) {
    //            lengths[i] = getAxisLength(axes[i])
    //        }
    //
    //        return lengths
    //    }

    override val axesLengthsPlanar: LongArray?
        get() = getAxesLengths(axesPlanar)

    override val axesLengthsNonPlanar: LongArray?
        get() = getAxesLengths(axesNonPlanar)

//    override val isMultichannel: Boolean
//        get() {
//            val cIndex: Int = getAxisIndex(Axes.CHANNEL)
//            return (cIndex < planarAxisCount && cIndex >= 0)
//        }
//
//    override fun getAxis(axisIndex: Int): CalibratedAxis? {
//        return axes!![axisIndex]
//    }
//
//    override fun getAxisLength(axisIndex: Int): Long {
//        if (axisIndex < 0 || axisIndex >= axes!!.size) {
//            return 1
//        }
//
//        return getAxisLength(getAxis(axisIndex))
//    }
//
//    override fun getAxisLength(t: CalibratedAxis?): Long {
//        return if (t == null) 1 else getAxisLength(t.type())
//    }
//
//    override fun getAxisLength(t: AxisType?): Long {
//        if (axisLengths == null || !axisLengths.containsKey(t) ||
//            (effectiveAxes != null && getAxisIndex(t) == -1)) {
//            return 1
//        }
//
//        return axisLengths.get(t)
//    }
//
//    override fun getAxisIndex(axis: CalibratedAxis): Int {
//        return getAxisIndex(axis.type())
//    }
//
//    override fun getAxisIndex(axisType: AxisType): Int {
//        // Use effectiveAxes if possible. If not, default to axes.
//        val knownAxes: List<CalibratedAxis>? = if (effectiveAxes == null) axes
//        else effectiveAxes
//
//        return getAxisIndex(axisType, knownAxes)
//    }
//
//    override fun addAxis(axis: CalibratedAxis?) {
//        addAxis(axis, 1)
//    }
//
//    override fun addAxis(axis: CalibratedAxis, value: Long) {
//        if (axes == null) axes = java.util.ArrayList<CalibratedAxis>()
//
//        // See if the axis already exists
//        if (!axes!!.contains(axis)) {
//            axes!!.add(axis)
//            clearCachedAxes()
//        }
//
//        updateLength(axis.type(), value)
//    }
//
//    override fun addAxis(axisType: AxisType?, value: Long) {
//        addAxis(FormatTools.createAxis(axisType), value)
//    }
//
//    final override fun copy(toCopy: ImageMetadata?) {
//        populate(toCopy!!.name, toCopy.axes, toCopy.axesLengths, toCopy
//                .pixelType, toCopy.isOrderCertain, toCopy.isLittleEndian, toCopy
//                         .isIndexed, toCopy.isFalseColor, toCopy.isMetadataComplete)
//        this.table = DefaultMetaTable(toCopy.getTable())
//        this.isThumbnail = toCopy.isThumbnail
//        this.thumbSizeX = toCopy.thumbSizeX
//        this.thumbSizeY = toCopy.thumbSizeY
//        this.planarAxisCount = toCopy.planarAxisCount
//    }
//
//    fun populate(name: String?, axes: List<CalibratedAxis?>?,
//                 lengths: LongArray, pixelType: Int, orderCertain: Boolean,
//                 littleEndian: Boolean, indexed: Boolean, falseColor: Boolean,
//                 metadataComplete: Boolean) {
//        populate(name, axes, lengths, pixelType, FormatTools.getBitsPerPixel(
//            pixelType), orderCertain, littleEndian, indexed, falseColor,
//                 metadataComplete)
//    }
//
//    fun populate(name: String?, axes: List<CalibratedAxis?>?,
//                 lengths: LongArray, pixelType: Int, bitsPerPixel: Int,
//                 orderCertain: Boolean, littleEndian: Boolean,
//                 indexed: Boolean, falseColor: Boolean,
//                 metadataComplete: Boolean) {
//        this.name = name
//        this.axes = java.util.ArrayList(axes)
//        setAxisLengths(lengths.clone())
//        this.bitsPerPixel = bitsPerPixel
//        this.isFalseColor = falseColor
//        this.isIndexed = indexed
//        this.isLittleEndian = littleEndian
//        this.isOrderCertain = orderCertain
//        this.pixelType = pixelType
//    }
//
//    // -- HasTable API Methods --
//    fun getTable(): MetaTable? {
//        if (table == null) table = DefaultMetaTable()
//        return table
//    }
//
//    fun setTable(table: MetaTable?) {
//        this.table = table
//    }
//
//    // -- Object API --
//    override fun toString(): String {
//        return FieldPrinter(this).toString()
//    }
//
//    // -- Helper methods --
//    /**
//     * Computes and caches the effective (non-trailing-length-1 axes) axis types
//     * for this dataset.
//     */
//    private fun getEffectiveAxes(): List<CalibratedAxis>? {
//        if (effectiveAxes == null && axes != null) {
//            var end = axes!!.size
//
//            while (end > planarAxisCount) {
//                val axis: CalibratedAxis = axes!![end - 1]
//                if (getAxisLength(axis) > 1) {
//                    break
//                }
//                end--
//            }
//
//            effectiveAxes = java.util.ArrayList<CalibratedAxis>()
//            for (i in 0 until end) {
//                effectiveAxes!!.add(axes!![i])
//            }
//        }
//
//        return effectiveAxes
//    }

    /**
     * Searches the given list of axes for an axis of the given type, returning
     * the index of the first match.
     */
    //    private fun getAxisIndex(axisType: AxisType,
    //                             axisList: List<CalibratedAxis>?): Int {
    //        if (axisList == null) return -1
    //        var index = -1
    //        var i = 0
    //        while (index == -1 && i < axisList.size) {
    //            if (axisList[i].type().equals(axisType)) index = i
    //            i++
    //        }
    //
    //        return index
    //    }

    /**
     * Resets the cached planar and non-planar axes. Used after the axes or
     * planarAxisCount are modified.
     */
//    private fun clearCachedAxes() {
//        planarAxes = null
//        extendedAxes = null
//        effectiveAxes = null
//    }

    //    private fun updateLength(axisType: AxisType, value: Long) {
    //        axisLengths.put(axisType, value)
    //        // only effectiveAxes needs to be cleared here, because it's the only
    //        // cached axis that can be affected by axis lengths.
    //        effectiveAxes = null
    //    }

    // If spatial == true, returns every non-CHANNEL axis after both X and Y
    // have been seen. If false, returns every non-CHANNEL axis until both X
    // and Y have been seen.
    //    private fun getAxisList(planar: Boolean): List<CalibratedAxis>? {
    //        var index = -1
    //        var end = -1
    //        var axisList: MutableList<CalibratedAxis>? = null
    //
    //        if (planar) {
    //            if (planarAxes == null) planarAxes = java.util.ArrayList<CalibratedAxis>()
    //            axisList = planarAxes
    //            index = 0
    //            end = planarAxisCount
    //        } else {
    //            if (extendedAxes == null) extendedAxes = java.util.ArrayList<CalibratedAxis>()
    //            axisList = extendedAxes
    //            index = planarAxisCount
    //            end = axes!!.size
    //        }
    //
    //        if (axisList!!.size == 0) {
    //            kotlin.synchronized(axisList) {
    //                if (axisList.size == 0) {
    //                    axisList.clear()
    //
    //                    var position = 0
    //                    while (index < end) {
    //                        if (position <= axisList.size) {
    //                            axisList.add(axes!![index])
    //                            position++
    //                        } else {
    //                            axisList[position++] = axes!![index]
    //                        }
    //                        index++
    //                    }
    //                }
    //            }
    //        }
    //
    //        return axisList
    //    }

    companion object {
        // -- Constants --
        /** Default thumbnail width and height.  */
        const val THUMBNAIL_DIMENSION: Long = 128
    }
}