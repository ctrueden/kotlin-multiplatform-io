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
import net.imagej.axis.AxisType
import net.imagej.axis.CalibratedAxis

/**
 * ImageMetadata stores the metadata for a single image within a dataset. Here,
 * information common to every format (e.g. height, width, index information)
 * can be stored and retrieved in a standard way.
 *
 * @author Mark Hiner
 */
interface ImageMetadata : CalibratedInterval<CalibratedAxis>, Named, HasMetaTable {

    // getInterval

    // TODO: Consider typing rois and tables on more specific data structures.

    /** Returns the size, in bytes, of this image.  */
    val size: Long

    /** Width (in pixels) of thumbnail blocks in this image.  */
    var thumbSizeX: Long

    /** Height (in pixels) of thumbnail blocks in this image.  */
    var thumbSizeY: Long

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

    /** @return the number of blocks in this image */
    val blockCount: Long

    // TODO: Consider typing rois and tables on more specific data structures.
    /** Retrieves the ROIs associated with this image.  */
    /** Sets the ROIs associated with this image.  */
    var rOIs: Any?

    /** Retrieves the tables associated with this image.  */
    /** Sets the tables associated with this image.  */
    var tables: Any?

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