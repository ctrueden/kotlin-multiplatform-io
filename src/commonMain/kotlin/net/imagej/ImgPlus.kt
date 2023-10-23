/*
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2023 ImageJ2 developers.
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
package net.imagej

import net.imagej.axis.*
import net.imagej.axis.Axes.unknown
import net.imagej.interval.AbstractCalibratedRealInterval
import net.imagej.space.CalibratedSpace
import net.imglib2.*
import net.imglib2.display.ColorTable
//import net.imglib2.display.ColorTable
import net.imglib2.img.Img
import net.imglib2.img.ImgFactory
//import net.imglib2.img.ImgView
//import net.imglib2.img.WrappedImg
//import net.imglib2.img.array.ArrayImg
import kotlin.collections.Iterator
import kotlin.jvm.JvmOverloads

/**
 * A simple container for storing an [Img] together with its metadata.
 * Metadata includes name, dimensional axes and calibration information.
 *
 *
 * **NOTE:** This class is slated for redesign soon. Use at your
 * own risk! If you need future stability, use
 * [ImgLib2](http://javadoc.imagej.net/ImgLib2/) and/or
 * [ImageJ 1.x](http://javadoc.imagej.net/ImageJ1/) classes.
 *
 *
 * @author Curtis Rueden
 */
class ImgPlus<T>(
        /**
         * Gets the backing [Img] of this `ImgPlus`.
         *
         *
         * Note that most of the time, you do *not* need to call this method.
         * Rather, you can use the `ImgPlus` directly because it implements all
         * of the same interfaces. However, there are legitimate cases where you may
         * need access to the backing container (e.g., for performance, to cast to the
         * appropriate [Img] implementation such as [ArrayImg]).
         *
         */
        override val img: Img<T>,
        override var name: String? = null, vararg axes: CalibratedAxis) : AbstractCalibratedRealInterval<CalibratedAxis>(img, *axes),
                                                                          Img<T>, WrappedImg<T>, ImgPlusMetadata {

    // -- Sourced methods --
    override var source: String = ""

    // -- ImageMetadata methods --
    override var validBits: Int = 0

    private val channelMin = ArrayList<Double?>()
    private val channelMax = ArrayList<Double?>()

    override var compositeChannelCount: Int = 1
    private val colorTable = ArrayList<ColorTable?>()
    override val properties: Map<String, Any> = HashMap()

    constructor(img: Img<T>, metadata: ImgPlusMetadata) : this(img, metadata, false)

    /**
     * As [.ImgPlus] but with a flag to determine if
     * metadata beyond axis information is copied.
     *
     * @param axesOnly - if true, only axis metadata is copied.
     */
    constructor(img: Img<T>, metadata: ImgPlusMetadata, axesOnly: Boolean) : this(img, metadata.name, *copyAxes(metadata)) {
        if (!axesOnly) {
            validBits = metadata.validBits
            compositeChannelCount = metadata.compositeChannelCount
            val count = metadata.colorTableCount
            for (i in 0..<count)
                colorTable += metadata.getColorTable(i)
        }
    }

    // -- Constructors --
    @JvmOverloads
    constructor(img: Img<T>, name: String? = null, axisTypes: Array<AxisType>? = null, cal: DoubleArray? = null, units: Array<String?>? = null) :
            this(img, name, *createAxes(img, axisTypes, cal, units))

    // -- ImgPlus methods --

    // -- RandomAccessible methods --
    override fun randomAccess(): RandomAccess<T> = img.randomAccess()

    override fun randomAccess(interval: Interval): RandomAccess<T> = img.randomAccess(interval)

    // -- Interval methods --
    override fun min(d: Int): Long = img.min(d)

    override fun min(min: LongArray) = img.min(min)

    override fun min(min: Positionable) = img.min(min)

    override fun max(d: Int): Long = img.max(d)

    override fun max(max: LongArray) = img.max(max)

    override fun max(max: Positionable) = img.max(max)

    // -- Dimensions methods --
    override fun dimensions(dimensions: LongArray) = img.dimensions(dimensions)

    override fun dimension(d: Int): Long = img.dimension(d)

    // -- RealInterval methods --
    override fun realMin(d: Int): Double = img.realMin(d)

    override fun realMin(min: DoubleArray) = img.realMin(min)

    override fun realMin(min: RealPositionable) = img.realMin(min)

    override fun realMax(d: Int): Double = img.realMax(d)

    override fun realMax(max: DoubleArray) = img.realMax(max)

    override fun realMax(max: RealPositionable) = img.realMax(max)

    // -- IterableInterval methods --
    override val cursor: Cursor<T>
        get() = img.cursor

    override val localizingCursor: Cursor<T>
        get() = img.localizingCursor

    // -- IterableRealInterval methods --

    override val size: Long
        get() = img.size

    override val firstElement: T
        get() = img.firstElement

    override val iterationOrder: Any
        get() = img.iterationOrder

    // -- Iterable methods --
    override fun iterator(): Iterator<T> = img.iterator()

    // -- Img methods --
    override fun factory(): ImgFactory<T> = img.factory()

    override fun copy(): ImgPlus<T> = ImgPlus(img.copy(), this)

    override fun getChannelMinimum(c: Int): Double = when {
        c < 0 || c >= channelMin.size -> Double.NaN
        else -> channelMin[c] ?: Double.NaN
    }

    override fun setChannelMinimum(c: Int, min: Double) {
        if (c < 0) throw IllegalArgumentException("Invalid channel: $c")
        if (c >= channelMin.size) {
            channelMin.ensureCapacity(c + 1)
            for (i in channelMin.size..c) channelMin += null
        }
        channelMin[c] = min
    }

    override fun getChannelMaximum(c: Int): Double = when {
        c < 0 || c >= channelMax.size -> Double.NaN
        else -> channelMax[c] ?: Double.NaN
    }

    override fun setChannelMaximum(c: Int, max: Double) {
        if (c < 0) throw IllegalArgumentException("Invalid channel: $c")
        if (c >= channelMax.size) {
            channelMax.ensureCapacity(c + 1)
            for (i in channelMax.size..c) channelMax += null
        }
        channelMax[c] = max
    }

    override fun getColorTable(no: Int): ColorTable? = colorTable.getOrNull(no)

    override fun setColorTable(colorTable: ColorTable, no: Int) {
        this.colorTable[no] = colorTable
    }

    override fun initializeColorTables(count: Int) {
        colorTable.ensureCapacity(count)
        colorTable.clear()
        for (i in 0..<count)
            colorTable += null
    }

    override val colorTableCount: Int
        get() = colorTable.size

    companion object {
        /** Wraps a [RandomAccessibleInterval] into an [ImgPlus].  */
//        fun <T : Type<T>> wrapRAI(rai: RandomAccessibleInterval<T>): ImgPlus<T> = when (rai) {
//            is ImgPlus<*> -> rai as ImgPlus<T>
//            else -> ImgPlus(wrapToImg(rai))
//        }

        /** Wraps a [RandomAccessibleInterval] into an [Img].  */
        //        fun <T : Type<T>> wrapToImg(rai: RandomAccessibleInterval<T>): Img<T> {
        //            return if (rai is Img<*>) rai as Img<T>
        //            else ImgView.wrap(rai, imgFactory(rai))
        //        }

        /**
         * Gets an [ImgFactory] suitable for creating [Img]s of the given
         * [RandomAccessibleInterval]'s container, type, and dimensions.
         */
        //        fun <T> imgFactory(rai: RandomAccessibleInterval<T>): ImgFactory<T> {
        //            val type: T = Util.getTypeFromInterval(rai)
        //            return Util.getSuitableImgFactory(rai, type)
        //        }

        // -- Utility methods --
        /** Ensures the given [Img] is an ImgPlus, wrapping if necessary.  */
        fun <T> wrap(img: Img<T>): ImgPlus<T> = when (img) {
            is ImgPlus<*> -> img as ImgPlus<T>
            else -> ImgPlus(img)
        }

        /** Ensures the given [Img] is an ImgPlus, wrapping if necessary.  */
        fun <T> wrap(img: Img<T>, metadata: ImgPlusMetadata): ImgPlus<T> = when (img) {
            is ImgPlus<*> -> img as ImgPlus<T>
            else -> ImgPlus(img, metadata)
        }

        // -- Helper methods --
        /** Creates [LinearAxis] objects matching the given arguments.  */
        private fun createAxes(img: Img<*>, axisTypes: Array<AxisType>?, cal: DoubleArray?, units: Array<String?>?): Array<CalibratedAxis> {
            // validate arguments
            val numDims = img.numDimensions
            val validTypes = validateAxisTypes(numDims, axisTypes)
            if (numDims != validTypes.size)
                throw IllegalArgumentException("Axis type count does not match dimensionality: ${validTypes.size} != $numDims")
            val validCal = validateCalibration(numDims, cal)
            if (numDims != validCal.size)
                throw IllegalArgumentException("Calibration count does not match dimensionality: ${validCal.size} != $numDims")
            val validUnits = validateUnits(numDims, units)
            if (numDims != validUnits.size)
                throw IllegalArgumentException("Unit count does not match dimensionality: ${validUnits.size} != $numDims")

            // create axes
            return Array(validTypes.size) { DefaultLinearAxis(validTypes[it], validUnits[it], validCal[it]) }
        }

        /** Ensures the given axis types are valid.  */
        private fun validateAxisTypes(numDims: Int, types: Array<AxisType>?): Array<AxisType> = when {
            types != null && numDims == types.size -> types
            else -> Array(numDims) {
                if (types != null && types.size > it) types[it]
                else when (it) {
                    0 -> Axes.X
                    1 -> Axes.Y
                    else -> unknown()
                }
            }
        }

        /** Ensures the given calibration values are valid.  */
        private fun validateCalibration(numDims: Int, cal: DoubleArray?): DoubleArray = when {
            cal != null && numDims == cal.size -> cal
            else -> DoubleArray(numDims) {
                if (cal != null && cal.size > it) cal[it]
                else 1.0
            }
        }

        /** Ensures the given unit values are valid.  */
        private fun validateUnits(numDims: Int, units: Array<String?>?): Array<String?> = when {
            units != null && numDims == units.size -> units
            else -> Array(numDims) {
                if (units != null && units.size > it) units[it]
                else null
            }
        }

        /** Makes a deep copy of the given space's axes.  */
        private fun copyAxes(space: CalibratedSpace<*>): Array<CalibratedAxis> = Array(space.numDimensions) { space.axis(it).copy() }
    }
}

/**
 * An object that wraps an [Img] somehow.
 *
 * @author Christian Dietz
 */
interface WrappedImg<T> {
    val img: Img<T>
}

/**
 * An interface which collects all metadata associated with an [ImgPlus].
 *
 *
 * **NOTE:** This class is slated for redesign in spring 2016. Use
 * at your own risk! If you need future stability, use [ImgLib2](http://javadoc.imagej.net/ImgLib2/) and/or [ImageJ 1.x](http://javadoc.imagej.net/ImageJ1/) classes.
 *
 *
 * @author Curtis Rueden
 * @see ImgPlus
 */
interface ImgPlusMetadata : Named, Sourced, CalibratedSpace<CalibratedAxis>, ImageMetadata

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
 * Interface for classes who track their source. Image files have sources (such
 * as "/usr/me/ImageFile.tif"). This information may be of interest to others.
 *
 *
 * **NOTE:** This class is slated for redesign in spring 2016. Use
 * at your own risk! If you need future stability, use [ImgLib2](http://javadoc.imagej.net/ImgLib2/) and/or [ImageJ 1.x](http://javadoc.imagej.net/ImageJ1/) classes.
 *
 *
 * @author Barry DeZonia
 */
interface Sourced {
    /** The source String  */
    var source: String
}

/**
 * Metadata relating to channels and intensity scales.
 *
 *
 * **NOTE:** This class is slated for redesign in spring 2016. Use
 * at your own risk! If you need future stability, use [ImgLib2](http://javadoc.imagej.net/ImgLib2/) and/or [ImageJ 1.x](http://javadoc.imagej.net/ImageJ1/) classes.
 *
 *
 * @author Lee Kamentsky
 */
interface ImageMetadata {
    /** Gets the number of valid bits (if applicable to this [Img]).  */
    /** Sets the number of valid bits.  */
    var validBits: Int

    /** Gets the minimum actual pixel value for the given channel.  */
    fun getChannelMinimum(c: Int): Double

    /** Sets the minimum actual pixel value for the given channel.  */
    fun setChannelMinimum(c: Int, min: Double)

    /** Gets the maximum actual pixel value for the given channel.  */
    fun getChannelMaximum(c: Int): Double

    /** Sets the maximum actual pixel value for the given channel.  */
    fun setChannelMaximum(c: Int, max: Double)

    /** Gets the number of channels intended to be displayed together.  */
    /** Sets the number of channels intended to be displayed together.  */
    var compositeChannelCount: Int

    /** Gets the color table at the given position.  */
    fun getColorTable(no: Int): ColorTable?

    /**
     * Sets the color table at the given position.
     *
     * @param colorTable The color table to store.
     * @param no The position of the color table, typically (but not necessarily)
     * a 1D dimensional planar index rasterized from an N-dimensional
     * planar position array.
     */
    fun setColorTable(colorTable: ColorTable, no: Int)

    /** Sets the number of available color tables to the given value.  */
    fun initializeColorTables(count: Int)

    /**
     * Gets the number of available [ColorTable]s. For [Img]s, this
     * number typically matches the total number of planes.
     */
    val colorTableCount: Int

    /** Gets a table of key/value pairs associated with the image.  */
    val properties: Map<String, Any>
}
