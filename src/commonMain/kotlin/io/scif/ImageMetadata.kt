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

import io.scif.util.FormatTools
import net.imagej.axis.Axes
import net.imagej.axis.AxisType
import net.imagej.axis.CalibratedAxis

/**
 * ImageMetadata stores the metadata for a single image within a dataset. Here,
 * information common to every format (e.g. height, width, index information)
 * can be stored and retrieved in a standard way.
 *
 * @author Mark Hiner
 */
interface ImageMetadata : Named, HasMetaTable {
    /**
     * Convenience method to set both the axis types and lengths for this
     * ImageMetadata.
     */
    fun setAxes(axes: Array<CalibratedAxis?>?, axisLengths: LongArray?)

    /**
     * Sets the Axes types for this image. Order is implied by ordering within
     * this array
     */
    fun setAxisTypes(vararg axisTypes: AxisType?)

    /**
     * Sets the Axes types for this image. Order is implied by ordering within
     * this array
     */
    fun setAxes(vararg axes: CalibratedAxis?)

    /**
     * Sets the lengths of each axis. Order is parallel of `axes`.
     *
     *
     * NB: axes must already exist for this method to be called. Use
     * [.setAxes] or [.setAxes]
     */
    fun setAxisLengths(axisLengths: LongArray?)

    /**
     * Sets the length for the specified axis. Adds the axis if if its type is not
     * already present in the image.
     */
    fun setAxisLength(axis: CalibratedAxis?, length: Long)

    /**
     * As [.setAxisLength] but requires only the
     * AxisType.
     */
    fun setAxisLength(axis: AxisType?, length: Long)

    /**
     * Sets the axis at the specified index, if an axis with a matching type is
     * not already defined. Otherwise the axes are re-ordered, per
     * [java.util.List.add].
     */
    fun setAxis(index: Int, axis: CalibratedAxis?)

    /**
     * As [.setAxis] but using the default calibration
     * values, per [FormatTools.createAxis].
     */
    fun setAxisType(index: Int, axis: AxisType?)

    // TODO: Consider typing rois and tables on more specific data structures.

    /** Returns the size, in bytes, of this image.  */
    val size: Long

    /** Returns the size, in bytes, of one block in this image.  */
    val blockSize: Long

    /** Width (in pixels) of thumbnail blocks in this image.  */
    var thumbSizeX: Long

    /** Height (in pixels) of thumbnail blocks in this image.  */
    var thumbSizeY: Long

    /**
     * Returns the CalibratedAxis associated with the given type. Useful to
     * retrieve calibration information.
     */
    fun getAxis(axisType: AxisType?): CalibratedAxis?

    /**
     * Returns the data type associated with a pixel. Valid pixel type constants
     * (e.g., [FormatTools.INT8]) are enumerated in [FormatTools].
     */
    /**
     * Sets the data type associated with a pixel. Valid pixel type constants
     * (e.g., [FormatTools.INT8]) are enumerated in [FormatTools].
     */
    var pixelType: Int

    /** Returns the number of valid bits per pixel.  */
    /** Sets the number of valid bits per pixel.  */
    var bitsPerPixel: Int

    /**
     * Returns true if we are confident that the dimension order is correct.
     */
    /**
     * Sets whether or not we are confident that the dimension order is correct.
     */
    var isOrderCertain: Boolean

    /** Returns true if each pixel's bytes are in little endian order.  */
    /** Sets whether or not each pixel's bytes are in little endian order.  */
    var isLittleEndian: Boolean

    /**
     * Whether or not the blocks are stored as indexed color. An indexed
     * color image treats each pixel value as an index into a color table
     * containing one or more (typically 3) actual values for the pixel.
     */
    var isIndexed: Boolean

    /** Returns true if we can ignore the color map (if present).  */
    /** Sets whether or not we can ignore the color map (if present).  */
    var isFalseColor: Boolean

    /**
     * Returns true if we are confident that all of the metadata stored within the
     * image has been parsed.
     */
    /**
     * Sets whether or not we are confident that all of the metadata stored within
     * the image has been parsed.
     */
    var isMetadataComplete: Boolean

    /**
     * Determines whether or not this image is a lower-resolution copy of another
     * image.
     *
     * @return true if this image is a thumbnail
     */
    /**
     * Sets whether or not this image is a lower-resolution copy of another image.
     */
    var isThumbnail: Boolean

    /**
     * Gets the axis of the (zero-indexed) specified block.
     *
     * @param axisIndex - index of the desired axis within this image
     * @return Type of the desired block.
     */
    fun getAxis(axisIndex: Int): CalibratedAxis?

    /**
     * Gets the length of the (zero-indexed) specified block.
     *
     * @param axisIndex - index of the desired axis within this image
     * @return Length of the desired axis, or 1 if the axis is not found.
     */
    fun getAxisLength(axisIndex: Int): Long

    /**
     * A convenience method for looking up the length of an axis based on its
     * type. No knowledge of block ordering is necessary.
     *
     * @param t - CalibratedAxis to look up
     * @return Length of axis t, or 1 if the axis is not found.
     */
    fun getAxisLength(t: CalibratedAxis?): Long

    /**
     * As [.getAxisLength] but only requires the
     * [AxisType] of the desired axis.
     *
     * @param t - CalibratedAxis to look up
     * @return Length of axis t, or 1 if the axis is not found.
     */
    fun getAxisLength(t: AxisType?): Long

    /**
     * Returns the array index for the specified CalibratedAxis. This index can be
     * used in other Axes methods for looking up lengths, etc...
     *
     *
     * This method can also be used as an existence check for the target
     * CalibratedAxis.
     *
     *
     * @param axis - axis to look up
     * @return The index of the desired axis or -1 if not found.
     */
    fun getAxisIndex(axis: CalibratedAxis?): Int

    /**
     * As [.getAxisIndex] but only requires the
     * [AxisType] of the desired axis.
     *
     * @param axisType - axis type to look up
     * @return The index of the desired axis or -1 if not found.
     */
    fun getAxisIndex(axisType: AxisType?): Int

    /**
     * Returns an array of the types for axes associated with the specified image
     * index. Order is consistent with the axis length (int) array returned by
     * [.getAxesLengths].
     *
     *
     * CalibratedAxis order is sorted and represents order within the image.
     *
     *
     * @return List of CalibratedAxes. Ordering in the list indicates the axis
     * order in the image.
     */
    val axes: List<Any?>?

    /** @return the number of blocks in this image */
    val blockCount: Long

    /**
     * Returns an array of the lengths for axes associated with the specified
     * image index.
     *
     *
     * Ordering is consistent with the CalibratedAxis array returned by
     * [.getAxes].
     *
     *
     * @return Sorted axis length array
     */
    val axesLengths: LongArray?

    /**
     * Returns an array of the lengths for axes in the provided CalibratedAxis list.
     *
     *
     * Ordering of the lengths is consistent with the provided ordering.
     *
     *
     * @return Sorted axis length array
     */
    fun getAxesLengths(axes: List<CalibratedAxis?>?): LongArray?

    // TODO: Consider typing rois and tables on more specific data structures.
    /** Retrieves the ROIs associated with this image.  */
    /** Sets the ROIs associated with this image.  */
    var rOIs: Any?

    /** Retrieves the tables associated with this image.  */
    /** Sets the tables associated with this image.  */
    var tables: Any?

    /**
     * Appends the provided [CalibratedAxis] to the metadata's list of axes,
     * with a length of 1.
     *
     * @param axis - The new axis
     */
//    fun addAxis(axis: CalibratedAxis?)

    /**
     * Appends the provided CalibratedAxis to the current CalibratedAxis array and
     * creates a corresponding entry with the specified value in axis lengths.
     *
     * @param axis - The new axis
     * @param value - length of the new axis
     */
//    fun addAxis(axis: CalibratedAxis?, value: Long)

    /**
     * As [.addAxis] using the default calibration
     * value, per [FormatTools.createAxis].
     */
//    fun addAxis(axisType: AxisType?, value: Long)

    /**
     * @return A new copy of this ImageMetadata.
     */
    fun copy(): ImageMetadata

    /**
     * Populates this ImageMetadata using the provided instance.
     *
     * @param toCopy - ImageMetadata to copy
     */
    fun copy(toCopy: ImageMetadata)

    /**
     * As
     * [.populate]
     * but automatically determines bits per pixel.
     */
//    fun populate(name: String?, axes: List<CalibratedAxis?>?, lengths: LongArray?,
//                 pixelType: Int, orderCertain: Boolean, littleEndian: Boolean, indexed: Boolean,
//                 falseColor: Boolean, metadataComplete: Boolean)

    /**
     * Convenience method for manually populating an ImageMetadata.
     */
//    fun populate(name: String?, axes: List<CalibratedAxis?>?, lengths: LongArray?,
//                 pixelType: Int, bitsPerPixel: Int, orderCertain: Boolean, littleEndian: Boolean,
//                 indexed: Boolean, falseColor: Boolean, metadataComplete: Boolean)
}