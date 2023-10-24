/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2023 SCIFIO developers.
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
package io.scif.util

import io.scif.*
import io.scif.config.SCIFIOConfig
import net.imagej.axis.Axes
import net.imagej.axis.AxisType
import net.imagej.axis.CalibratedAxis
import net.imagej.axis.DefaultLinearAxis
import net.imagej.axis.LinearAxis
import platform.FormatException
//import net.imglib2.FinalInterval
//import net.imglib2.Interval
//import net.imglib2.util.Intervals
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.handle.DataHandle.ByteOrder
//import org.scijava.io.location.Location
import kotlin.jvm.JvmOverloads

/**
 * A collection of constants and utility methods applicable for all cycles of
 * image processing within SCIFIO.
 */
object FormatTools {
    // -- Constants --
    val COMPRESSION_SUFFIXES: Array<String> = arrayOf("bz2", "gz")

    // -- Constants - Thumbnail dimensions --
    /** Default height and width for thumbnails.  */
    const val THUMBNAIL_DIMENSION: Int = 128

    // -- Constants - pixel types --
    /** Identifies the *INT8* data type used to store pixel values.  */
    const val INT8: Int = 0

    /** Identifies the *UINT8* data type used to store pixel values.  */
    const val UINT8: Int = 1

    /** Identifies the *INT16* data type used to store pixel values.  */
    const val INT16: Int = 2

    /** Identifies the *UINT16* data type used to store pixel values.  */
    const val UINT16: Int = 3

    /** Identifies the *INT32* data type used to store pixel values.  */
    const val INT32: Int = 4

    /** Identifies the *UINT32* data type used to store pixel values.  */
    const val UINT32: Int = 5

    /** Identifies the *FLOAT* data type used to store pixel values.  */
    const val FLOAT: Int = 6

    /** Identifies the *DOUBLE* data type used to store pixel values.  */
    const val DOUBLE: Int = 7

    /** Human readable pixel type.  */
    private val pixelTypes = makePixelTypes()

    fun makePixelTypes(): Array<String> = arrayOf("int8", "uint8",
                                                  "int16", "uint16",
                                                  "int32", "uint32",
                                                  "float", "double")

    // -- Constants - miscellaneous --
    /** File grouping options.  */
    const val MUST_GROUP: Int = 0

    const val CAN_GROUP: Int = 1

    const val CANNOT_GROUP: Int = 2

    /** Patterns to be used when constructing a pattern for output filenames.  */
    const val SERIES_NUM: String = "%s"

    const val SERIES_NAME: String = "%n"

    const val CHANNEL_NUM: String = "%c"

    const val CHANNEL_NAME: String = "%w"

    const val Z_NUM: String = "%z"

    const val T_NUM: String = "%t"

    const val TIMESTAMP: String = "%A"

    // -- Constants - domains --
    /** Identifies the high content screening domain.  */
    const val HCS_DOMAIN: String = "High-Content Screening (HCS)"

    /** Identifies the light microscopy domain.  */
    const val LM_DOMAIN: String = "Light Microscopy"

    /** Identifies the electron microscopy domain.  */
    const val EM_DOMAIN: String = "Electron Microscopy (EM)"

    /** Identifies the scanning probe microscopy domain.  */
    const val SPM_DOMAIN: String = "Scanning Probe Microscopy (SPM)"

    /** Identifies the scanning electron microscopy domain.  */
    const val SEM_DOMAIN: String = "Scanning Electron Microscopy (SEM)"

    /** Identifies the fluorescence-lifetime domain.  */
    const val FLIM_DOMAIN: String = "Fluorescence-Lifetime Imaging"

    /** Identifies the medical imaging domain.  */
    const val MEDICAL_DOMAIN: String = "Medical Imaging"

    /** Identifies the histology domain.  */
    const val HISTOLOGY_DOMAIN: String = "Histology"

    /** Identifies the gel and blot imaging domain.  */
    const val GEL_DOMAIN: String = "Gel/Blot Imaging"

    /** Identifies the astronomy domain.  */
    const val ASTRONOMY_DOMAIN: String = "Astronomy"

    /**
     * Identifies the graphics domain. This includes formats used exclusively by
     * analysis software.
     */
    const val GRAPHICS_DOMAIN: String = "Graphics"

    /** Identifies an unknown domain.  */
    const val UNKNOWN_DOMAIN: String = "Unknown"

    /** List of non-graphics domains.  */
    val NON_GRAPHICS_DOMAINS: Array<String> = arrayOf(LM_DOMAIN,
                                                      EM_DOMAIN, SPM_DOMAIN, SEM_DOMAIN, FLIM_DOMAIN, MEDICAL_DOMAIN,
                                                      HISTOLOGY_DOMAIN, GEL_DOMAIN, ASTRONOMY_DOMAIN, HCS_DOMAIN,
                                                      UNKNOWN_DOMAIN)

    /** List of non-HCS domains.  */
    val NON_HCS_DOMAINS: Array<String> = arrayOf(LM_DOMAIN,
                                                 EM_DOMAIN, SPM_DOMAIN, SEM_DOMAIN, FLIM_DOMAIN, MEDICAL_DOMAIN,
                                                 HISTOLOGY_DOMAIN, GEL_DOMAIN, ASTRONOMY_DOMAIN, UNKNOWN_DOMAIN)

    /**
     * List of domains that do not require special handling. Domains that require
     * special handling are [.GRAPHICS_DOMAIN] and [.HCS_DOMAIN].
     */
    val NON_SPECIAL_DOMAINS: Array<String> = arrayOf(LM_DOMAIN,
                                                     EM_DOMAIN, SPM_DOMAIN, SEM_DOMAIN, FLIM_DOMAIN, MEDICAL_DOMAIN,
                                                     HISTOLOGY_DOMAIN, GEL_DOMAIN, ASTRONOMY_DOMAIN, UNKNOWN_DOMAIN)

    /** List of all supported domains.  */
    val ALL_DOMAINS: Array<String> = arrayOf(HCS_DOMAIN,
                                             LM_DOMAIN, EM_DOMAIN, SPM_DOMAIN, SEM_DOMAIN, FLIM_DOMAIN, MEDICAL_DOMAIN,
                                             HISTOLOGY_DOMAIN, GEL_DOMAIN, ASTRONOMY_DOMAIN, GRAPHICS_DOMAIN,
                                             UNKNOWN_DOMAIN)

    // Utility methods -- dimensional positions --
    /**
     * Wraps the provided AxisType in a CalibratedAxis with calibration = 1.0.
     */
    fun createAxis(axisType: AxisType): CalibratedAxis = DefaultLinearAxis(axisType)

    /**
     * Creates an array, wrapping all provided AxisTypes as CalibratedAxis with
     * calibration = 1.0.
     */
    fun createAxes(vararg axisTypes: AxisType): Array<CalibratedAxis> = Array(axisTypes.size) { createAxis(axisTypes[it]) }

    /**
     * Applies the given scale and origin values to each [LinearAxis] in the
     * provided Metadata.
     */
    /**
     * Applies the given scale value, and an origin of 0.0, to each
     * [LinearAxis] in the provided Metadata.
     */
    //    @JvmOverloads
    //    fun calibrate(m: Metadata, imageIndex: Int, scale: DoubleArray, origin: DoubleArray = DoubleArray(scale.size)) {
    //        var i = 0
    //
    //        for (axis in m[imageIndex].getAxes()) {
    //            if (i >= scale.size || i >= origin.size) continue
    //            calibrate(axis, scale[i], origin[i])
    //            i++
    //        }
    //    }

    /**
     * Applies the given scale and origin to the provided [CalibratedAxis] ,
     * if it is a [LinearAxis].
     *
     * @throws IllegalArgumentException if the axis is not a [LinearAxis].
     */
    fun calibrate(axis: CalibratedAxis, scale: Double, origin: Double) {
        if (axis !is LinearAxis)
            throw IllegalArgumentException("Not a linear axis: $axis")
        axis.scale = scale
        axis.origin = origin
    }

    /**
     * As [.calibrate] but also sets the
     * unit of the axis.
     *
     * @see CalibratedAxis.setUnit
     */
    fun calibrate(axis: CalibratedAxis, scale: Double, origin: Double, unit: String?) {
        calibrate(axis, scale, origin)
        axis.unit = unit
    }

    /**
     * Gets the average scale over the specified axis of the given image metadata.
     *
     * @return the average scale over the axis's values, or 1.0 if the desired
     * axis is null.
     */
    //    fun getScale(m: Metadata, imageIndex: Int, axisType: AxisType): Double = getScale(m[imageIndex], axisType)

    /**
     * Gets the average scale over the specified axis of the given image metadata.
     *
     * @return the average scale over the axis's values, or 1.0 if the desired
     * axis is null.
     */
    //    fun getScale(imageMeta: ImageMetadata, axisType: AxisType): Double {
    //        val axis: CalibratedAxis = imageMeta.getAxis(axisType) ?: return 1.0
    //        val axisLength: Long = imageMeta.getAxisLength(axis)
    //        return axis.averageScale(0, axisLength)
    //    }
    //
    //    /**
    //     * Returns the position of the specified [AxisType] for the given image
    //     * and plane indices.
    //     *
    //     * @return position of the specified axis type, or 0 if the given axis is
    //     * planar.
    //     */
    //    fun getNonPlanarAxisPosition(m: Metadata, imageIndex: Int, planeIndex: Long, type: AxisType): Long {
    //        val iMeta: ImageMetadata = m.get(imageIndex)
    //        var axisIndex: Int = iMeta.getAxisIndex(type)
    //
    //        // Axis is a planar axis
    //        if (axisIndex < iMeta.planarAxisCount) return 0
    //
    //        // look up position of the given plane
    //        val position = rasterToPosition(iMeta.axesLengthsNonPlanar, planeIndex)
    //
    //        // Compute relative index of the desired axis
    //        axisIndex -= iMeta.planarAxisCount
    //
    //        return position[axisIndex]
    //    }
    //
    //    /**
    //     * Computes a unique N-D position corresponding to the given rasterized index
    //     * value.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param planeIndex rasterized plane index to convert to axis indices
    //     * @param expectedAxis determines the size and order of the positions in the
    //     * returned array.
    //     * @return position along each dimensional axis
    //     */
    //    fun rasterToPosition(imageIndex: Int,
    //                         planeIndex: Long, m: Metadata,
    //                         expectedAxis: List<CalibratedAxis>): LongArray {
    //        val axisLengths: LongArray = m.get(imageIndex).getAxesLengths(expectedAxis)
    //        return rasterToPosition(axisLengths, planeIndex)
    //    }
    //
    //    /**
    //     * Computes a unique N-D position corresponding to the given rasterized index
    //     * value.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param planeIndex rasterized plane index to convert to axis indices
    //     * @param reader reader used to open the dataset
    //     * @return position along each dimensional axis
    //     */
    //    fun rasterToPosition(imageIndex: Int,
    //                         planeIndex: Long, reader: Reader): LongArray {
    //        return rasterToPosition(imageIndex, planeIndex, reader.metadata)
    //    }
    //
    //    /**
    //     * Computes a unique N-D position corresponding to the given rasterized index
    //     * value.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param planeIndex rasterized plane index to convert to axis indices
    //     * @param m metadata describing the dataset
    //     * @return position along each dimensional axis
    //     */
    //    fun rasterToPosition(imageIndex: Int,
    //                         planeIndex: Long, m: Metadata?): LongArray {
    //        val axisLengths: LongArray = m.get(imageIndex).getAxesLengthsNonPlanar()
    //        return rasterToPosition(axisLengths, planeIndex)
    //    }
    //
    //    /**
    //     * Computes a unique N-D position corresponding to the given rasterized index
    //     * value.
    //     *
    //     * @param lengths the maximum value at each positional dimension
    //     * @param raster rasterized index value
    //     * @param pos preallocated position array to populate with the result
    //     * @return position along each dimensional axis
    //     */
    //    /**
    //     * Computes a unique N-D position corresponding to the given rasterized index
    //     * value.
    //     *
    //     * @param lengths the maximum value at each positional dimension
    //     * @param raster rasterized index value
    //     * @return position along each dimensional axis
    //     */
    //    @JvmOverloads
    //    fun rasterToPosition(lengths: LongArray, raster: Long,
    //                         pos: LongArray = LongArray(lengths.size)): LongArray {
    //        var raster = raster
    //        var offset: Long = 1
    //        for (i in pos.indices) {
    //            val offset1 = offset * lengths[i]
    //            val q = if (i < pos.size - 1) raster % offset1 else raster
    //            pos[i] = q / offset
    //            raster -= q
    //            offset = offset1
    //        }
    //        return pos
    //    }
    //
    //    /**
    //     * Computes the next plane index for a given position, using the current min
    //     * and max planar values. Also updates the position array appropriately.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param r reader used to open the dataset
    //     * @param pos current position in each dimension
    //     * @param offsets offsets in each dimension (potentially cropped)
    //     * @param cropLengths effective lengths in each dimension (potentially
    //     * cropped)
    //     * @return the next plane index, or -1 if the position extends beyond the
    //     * given min/max
    //     */
    //    fun nextPlaneIndex(imageIndex: Int, r: Reader,
    //                       pos: LongArray, offsets: LongArray, cropLengths: LongArray): Long =
    //        nextPlaneIndex(imageIndex, r.metadata, pos, offsets, cropLengths)
    //
    //    /**
    //     * Computes the next plane index for a given position, using the current min
    //     * and max planar values. Also updates the position array appropriately.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param m metadata describing the dataset
    //     * @param pos current position in each dimension
    //     * @param offsets offsets in each dimension (potentially cropped)
    //     * @param cropLengths effective lengths in each dimension (potentially
    //     * cropped)
    //     * @return the next plane index, or -1 if the position extends beyond the
    //     * given min/max
    //     */
    //    fun nextPlaneIndex(imageIndex: Int, m: Metadata,
    //                       pos: LongArray, offsets: LongArray, cropLengths: LongArray): Long =
    //        nextPlaneIndex(m[imageIndex].getAxesLengthsNonPlanar(), pos, offsets, cropLengths)
    //
    //    /**
    //     * Computes the next plane index for a given position, using the current min
    //     * and max planar values. Also updates the position array appropriately.
    //     *
    //     * @param lengths actual dimension lengths
    //     * @param pos current position in each dimension
    //     * @param offsets offsets in each dimension (potentially cropped)
    //     * @param cropLengths effective lengths in each dimension (potentially
    //     * cropped)
    //     * @return the next plane index, or -1 if the position extends beyond the
    //     * given min/max
    //     */
    //    fun nextPlaneIndex(lengths: LongArray, pos: LongArray,
    //                       offsets: LongArray, cropLengths: LongArray): Long {
    //        var updated = false
    //
    //        // loop over each index of the position to see if we can update it
    //        var i = 0
    //        while (i < pos.size && !updated) {
    //            if (pos[i] < offsets[i]) break
    //
    //            // Check if the next index is valid for this position
    //            if (pos[i] + 1 < offsets[i] + cropLengths[i]) {
    //                // if so, make the update
    //                pos[i]++
    //                updated = true
    //            } else
    //            // if not, reset this position and try to update the next position
    //                pos[i] = offsets[i]
    //            i++
    //        }
    //        if (updated)
    //        // Next position is valid. Return its raster
    //            return positionToRaster(lengths, pos)
    //        // Next position is not valid
    //        return -1
    //    }
    //
    //    /**
    //     * Computes a unique 1-D index corresponding to the given multidimensional
    //     * position.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param reader reader used to open the dataset
    //     * @param planeIndices position along each dimensional axis
    //     * @return rasterized index value
    //     */
    //    fun positionToRaster(imageIndex: Int, reader: Reader, planeIndices: LongArray): Long =
    //        positionToRaster(imageIndex, reader.metadata, planeIndices)
    //
    //    /**
    //     * Computes a unique 1-D index corresponding to the given multidimensional
    //     * position.
    //     *
    //     * @param imageIndex image index within dataset
    //     * @param m metadata describing the dataset
    //     * @param planeIndices position along each dimensional axis
    //     * @return rasterized index value
    //     */
    //    fun positionToRaster(imageIndex: Int, m: Metadata, planeIndices: LongArray): Long {
    //        val planeSizes: LongArray = m.get(imageIndex).getAxesLengthsNonPlanar()
    //        return positionToRaster(planeSizes, planeIndices)
    //    }
    //
    //    /**
    //     * Computes a unique 1-D index corresponding to the given multidimensional
    //     * position.
    //     *
    //     * @param lengths the maximum value for each positional dimension
    //     * @param pos position along each dimensional axis
    //     * @return rasterized index value
    //     */
    //    fun positionToRaster(lengths: LongArray, pos: LongArray): Long {
    //        var offset: Long = 1
    //        var raster = 0L
    //        for (i in pos.indices) {
    //            raster += offset * pos[i]
    //            offset *= lengths[i]
    //        }
    //        return raster
    //    }
    //
    //    /**
    //     * Computes the number of raster values for a positional array with the given
    //     * lengths.
    //     */
    //    fun getRasterLength(lengths: LongArray): Long {
    //        var length = 1L
    //        for (lengthVal in lengths) length *= lengthVal
    //        return length
    //    }
    //
    //    // -- Utility methods - sanity checking
    //    /**
    //     * Asserts that the current file is either null, or not, according to the
    //     * given flag. If the assertion fails, an IllegalStateException is thrown.
    //     *
    //     * @param id File name to test.
    //     * @param notNull True iff id should be non-null.
    //     * @param depth How far back in the stack the calling method is; this name is
    //     * reported as part of the exception message, if available. Use zero
    //     * to suppress output of the calling method name.
    //     */
    //    fun assertId(id: Any?, notNull: Boolean, depth: Int) {
    //        val msg = when {
    //            id == null && notNull -> "Current Location should not be null; call setId(Location) first"
    //            id != null && !notNull -> "Current file should be null, but is '$id'; call close() first"
    //            else -> return
    //        }
    //
    //        val ste: Array<StackTraceElement> = Exception().getStackTrace()
    //        val header = when {
    //            depth > 0 && ste.size > depth -> {
    //                var c: String = ste[depth].getClassName()
    //                if (c.startsWith("io.scif."))
    //                    c = c.substring(c.lastIndexOf(".") + 1)
    //                c + "." + ste[depth].getMethodName() + ": "
    //            }
    //            else -> ""
    //        }
    //        throw IllegalStateException(header + msg)
    //    }

    /**
     * Asserts that the current file is either null, or not, according to the
     * given flag. If the assertion fails, an IllegalStateException is thrown.
     *
     * @param stream Source to test.
     * @param notNull True iff id should be non-null.
     * @param depth How far back in the stack the calling method is; this name is
     * reported as part of the exception message, if available. Use zero
     * to suppress output of the calling method name.
     */
    //    fun assertStream(stream: DataHandle<Location?>?,
    //                     notNull: Boolean, depth: Int) {
    //        var msg: String? = null
    //        if (stream == null && notNull) {
    //            msg = "Current file should not be null; call setId(String) first"
    //        } else if (stream != null && !notNull) {
    //            msg = "Current file should be null, but is '$stream'; call close() first"
    //        }
    //        if (msg == null) return
    //
    //        val ste: Array<java.lang.StackTraceElement> = java.lang.Exception().getStackTrace()
    //        val header: String
    //        if (depth > 0 && ste.size > depth) {
    //            var c: String = ste[depth].getClassName()
    //            if (c.startsWith("io.scif.")) {
    //                c = c.substring(c.lastIndexOf(".") + 1)
    //            }
    //            header = c + "." + ste[depth].getMethodName() + ": "
    //        } else header = ""
    //        throw java.lang.IllegalStateException(header + msg)
    //    }

    /**
     * As [.checkPlaneForWriting] but also asserts that the Metadata has a
     * non-null source attached. If no exception is throwin, these parameters are
     * suitable for reading.
     */
    //    @Throws(FormatException::class) fun checkPlaneForReading(m: Metadata,
    //                                                             imageIndex: Int, planeIndex: Long, bufLength: Int,
    //                                                             bounds: Interval) {
    //        assertId(m.getSourceLocation(), true, 2)
    //        checkPlaneForWriting(m, imageIndex, planeIndex, bufLength, bounds)
    //    }
    //
    //    /**
    //     * Convenience method for checking that the plane number, tile size and buffer
    //     * sizes are all valid for the given Metadata. If 'bufLength' is less than 0,
    //     * then the buffer length check is not performed. If no exception is thrown,
    //     * these parameters are suitable for writing.
    //     */
    //    @Throws(FormatException::class) fun checkPlaneForWriting(m: Metadata,
    //                                                             imageIndex: Int, planeIndex: Long, bufLength: Int,
    //                                                             bounds: Interval) {
    //        checkPlaneNumber(m, imageIndex, planeIndex)
    //        checkTileSize(m, bounds, imageIndex)
    //        if (bufLength >= 0) checkBufferSize(m, bufLength, bounds, imageIndex)
    //    }
    //
    //    /** Checks that the given plane number is valid for the given reader.  */
    //    @Throws(FormatException::class) fun checkPlaneNumber(m: Metadata, imageIndex: Int,
    //                                                         planeIndex: Long) {
    //        val imageCount: Long = m.get(imageIndex).getPlaneCount()
    //        if (planeIndex < 0 || planeIndex >= imageCount) {
    //            throw FormatException("Invalid plane number: " + planeIndex + " (" +  /*
    //				 * TODO series=" + r.getMetadata().getSeries() + ",
    //				 */
    //                                          "planeCount=" + planeIndex + ")")
    //        }
    //    }
    //
    //    /** Checks that the given tile size is valid for the given reader.  */
    //    @Throws(FormatException::class) fun checkTileSize(m: Metadata, bounds: Interval,
    //                                                      imageIndex: Int) {
    //        val axes: List<CalibratedAxis> = m.get(imageIndex).getAxesPlanar()
    //
    //        for (i in axes.indices) {
    //            val start: Long = bounds.min(i)
    //            val end: Long = bounds.max(i)
    //            val length: Long = m.get(imageIndex).getAxisLength(axes[i])
    //
    //            if (start < 0 || end < 0 || end >= length) {
    //                throw FormatException("Invalid planar size: start=" + start +
    //                                              ", end=" + end + ", length in metadata=" + length)
    //            }
    //        }
    //    }
    //
    //    /**
    //     * Checks that the given buffer length is long enough to hold planes of the
    //     * specified image index, using the provided Reader.
    //     */
    //    @Throws(FormatException::class) fun checkBufferSize(imageIndex: Int, m: Metadata,
    //                                                        len: Int) {
    //        checkBufferSize(m, len,  //
    //                        FinalInterval(m.get(imageIndex).getAxesLengthsPlanar()), imageIndex)
    //    }
    //
    //    /**
    //     * Checks that the given buffer size is large enough to hold an image with the
    //     * given planar lengths.
    //     *
    //     * @throws FormatException if the buffer is too small
    //     */
    //    @Throws(FormatException::class) fun checkBufferSize(m: Metadata, len: Int,
    //                                                        bounds: Interval?, imageIndex: Int) {
    //        val size = getPlaneSize(m, bounds, imageIndex)
    //        if (size > len) {
    //            throw FormatException("Buffer too small (got " + len + ", expected " +
    //                                          size + ").")
    //        }
    //    }
    //
    //    /**
    //     * Returns true if the given DataHandle contains at least 'len' bytes.
    //     */
    //    @Throws(java.io.IOException::class) fun validStream(handle: DataHandle<Location?>,
    //                                                        len: Int, littleEndian: Boolean): Boolean {
    //        handle.seek(0)
    //        handle.setLittleEndian(littleEndian)
    //        return handle.length() >= len
    //    }
    //
    //    /**
    //     * Returns true if the given DataHandle contains at least 'len' bytes.
    //     */
    //    @Throws(java.io.IOException::class) fun validStream(handle: DataHandle<Location?>,
    //                                                        len: Int, order: ByteOrder?): Boolean {
    //        handle.seek(0)
    //        handle.setOrder(order)
    //        return handle.length() >= len
    //    }
    //
    //    /** Returns the size in bytes of a single plane read by the given Reader.  */
    //    fun getPlaneSize(r: Reader, imageIndex: Int): Long {
    //        return getPlaneSize(r.metadata, imageIndex)
    //    }
    //
    //    /** Returns the size in bytes of a tile defined by the given Metadata.  */
    //    fun getPlaneSize(m: Metadata?, imageIndex: Int): Long {
    //        return m.get(imageIndex).getPlaneSize()
    //    }
    //
    //    /** Returns the size in bytes of a w * h tile.  */
    //    fun getPlaneSize(m: Metadata, width: Int,
    //                     height: Int, imageIndex: Int): Long {
    //        val iMeta: ImageMetadata = m.get(imageIndex)
    //        val lengths = LongArray(iMeta.planarAxisCount)
    //        for (i in lengths.indices) {
    //            val type: AxisType = iMeta.getAxis(i).type()
    //            if (type === Axes.X) {
    //                lengths[i] = width.toLong()
    //            } else if (type === Axes.Y) {
    //                lengths[i] = height.toLong()
    //            } else {
    //                lengths[i] = iMeta.getAxisLength(type)
    //            }
    //        }
    //        val bounds: FinalInterval = FinalInterval(lengths)
    //        return getPlaneSize(m, bounds, imageIndex)
    //    }
    //
    //    /** Returns the size in bytes of a plane with the given minima and maxima.  */
    //    fun getPlaneSize(m: Metadata, bounds: Interval?,
    //                     imageIndex: Int): Long {
    //        val bytesPerPixel: Long = m.get(imageIndex).getBitsPerPixel() / 8
    //        return bytesPerPixel * Intervals.numElements(bounds)
    //    }

        // -- Utility methods - pixel types --
        /**
         * Takes a string value and maps it to one of the pixel type enumerations.
         *
         * @param pixelTypeAsString the pixel type as a string.
         * @return type enumeration value for use with class constants.
         */
        fun pixelTypeFromString(pixelTypeAsString: String): Int {
            val lowercaseTypeAsString = pixelTypeAsString.lowercase()
            for (i in pixelTypes.indices)
                if (pixelTypes[i] == lowercaseTypeAsString) return i
            throw IllegalArgumentException("Unknown type: '$pixelTypeAsString'")
        }

        /**
         * Takes a pixel type value and gets a corresponding string representation.
         *
         * @param pixelType the pixel type.
         * @return string value for human-readable output.
         */
        fun getPixelTypeString(pixelType: Int): String {
            if (pixelType < 0 || pixelType >= pixelTypes.size)
                throw IllegalArgumentException("Unknown pixel type: $pixelType")
            return pixelTypes[pixelType]
        }

        /**
         * Retrieves how many bytes per pixel the current plane or section has.
         *
         * @param pixelType the pixel type as retrieved from
         * @return the number of bytes per pixel.
         * @see io.scif.ImageMetadata.getPixelType
         */
        fun getBytesPerPixel(pixelType: Int): Int = when (pixelType) {
            INT8, UINT8 -> 1
            INT16, UINT16 -> 2
            INT32, UINT32, FLOAT -> 4
            DOUBLE -> 8
            else -> throw IllegalArgumentException("Unknown pixel type: $pixelType")
        }

        /**
         * Retrieves how many bytes per pixel the current plane or section has.
         *
         * @param pixelType the pixel type as retrieved from
         * [io.scif.ImageMetadata.getPixelType].
         * @return the number of bytes per pixel.
         * @see io.scif.ImageMetadata.getPixelType
         */
        fun getBitsPerPixel(pixelType: Int): Int = 8 * getBytesPerPixel(pixelType)

        /**
         * Retrieves the number of bytes per pixel in the current plane.
         *
         * @param pixelType the pixel type, as a String.
         * @return the number of bytes per pixel.
         * @see .pixelTypeFromString
         * @see .getBytesPerPixel
         */
        fun getBytesPerPixel(pixelType: String): Int = getBytesPerPixel(pixelTypeFromString(pixelType))

    //    /**
    //     * Determines whether the given pixel type is floating point or integer.
    //     *
    //     * @param pixelType the pixel type as retrieved from
    //     * [io.scif.ImageMetadata.getPixelType].
    //     * @return true if the pixel type is floating point.
    //     * @see io.scif.ImageMetadata.getPixelType
    //     */
    //    fun isFloatingPoint(pixelType: Int): Boolean {
    //        when (pixelType) {
    //            INT8, UINT8, INT16, UINT16, INT32, UINT32 -> return false
    //            FLOAT, DOUBLE -> return true
    //        }
    //        throw java.lang.IllegalArgumentException("Unknown pixel type: $pixelType")
    //    }
    //
    //    /**
    //     * Determines whether the given pixel type is signed or unsigned.
    //     *
    //     * @param pixelType the pixel type as retrieved from
    //     * [io.scif.ImageMetadata.getPixelType].
    //     * @return true if the pixel type is signed.
    //     * @see io.scif.ImageMetadata.getPixelType
    //     */
    //    fun isSigned(pixelType: Int): Boolean {
    //        when (pixelType) {
    //            INT8, INT16, INT32, FLOAT, DOUBLE -> return true
    //            UINT8, UINT16, UINT32 -> return false
    //        }
    //        throw java.lang.IllegalArgumentException("Unknown pixel type: $pixelType")
    //    }

        /**
         * Returns an appropriate pixel type given the number of bytes per pixel.
         *
         * @param bytes number of bytes per pixel.
         * @param signed whether or not the pixel type should be signed.
         * @param fp whether or not these are floating point pixels.
         */
        @Throws(FormatException::class)
        fun pixelTypeFromBytes(bytes: Int, signed: Boolean, fp: Boolean): Int = when (bytes) {
            1 -> if (signed) INT8 else UINT8
            2 -> if (signed) INT16 else UINT16
            4 -> if (fp) FLOAT else if (signed) INT32 else UINT32
            8 -> DOUBLE
            else -> throw FormatException("Unsupported byte depth: $bytes")
        }

    //    // -- Utility methods -- export
    //    /**
    //     * @throws FormatException Never actually thrown.
    //     * @throws IOException Never actually thrown.
    //     */
    //    @Throws(FormatException::class, java.io.IOException::class) fun getFilename(imageIndex: Int, image: Int,
    //                                                                                r: Reader, pattern: String): String {
    //        var filename = pattern.replace(SERIES_NUM.toRegex(), imageIndex.toString())
    //
    //        var imageName: String = r.getCurrentLocation().getName()
    //        if (imageName == null || "" == imageName) imageName = "Image#" +
    //                imageIndex
    //        imageName = imageName.replace("/".toRegex(), "_")
    //        imageName = imageName.replace("\\\\".toRegex(), "_")
    //
    //        filename = filename.replace(SERIES_NAME.toRegex(), imageName)
    //
    //        val coordinates = rasterToPosition(imageIndex, image.toLong(),
    //                                           r)
    //        filename = filename.replace(Z_NUM.toRegex(), coordinates[0].toString())
    //        filename = filename.replace(T_NUM.toRegex(), coordinates[2].toString())
    //        filename = filename.replace(CHANNEL_NUM.toRegex(), coordinates[1].toString())
    //
    //        var channelName = coordinates[1].toString()
    //        channelName = channelName.replace("/".toRegex(), "_")
    //        channelName = channelName.replace("\\\\".toRegex(), "_")
    //
    //        filename = filename.replace(CHANNEL_NAME.toRegex(), channelName)
    //
    //        /*
    //		 * //TODO check for date String date =
    //		 * retrieve.getImageAcquisitionDate(imageIndex).getValue(); long stamp =
    //		 * 0; if (retrieve.getPlaneCount(imageIndex) > image) { Double deltaT =
    //		 * retrieve.getPlaneDeltaT(imageIndex, image); if (deltaT != null) {
    //		 * stamp = (long) (deltaT * 1000); } } stamp += DateTools.getTime(date,
    //		 * DateTools.ISO8601_FORMAT); date = DateTools.convertDate(stamp, (int)
    //		 * DateTools.UNIX_EPOCH);
    //		 *
    //		 * filename = filename.replaceAll(TIMESTAMP, date);
    //		 */
    //        return filename
    //    }
    //
    //    /**
    //     * @throws FormatException
    //     * @throws IOException
    //     */
    //    @Throws(FormatException::class, java.io.IOException::class) fun getFilenames(pattern: String, r: Reader): Array<String> {
    //        val filenames: java.util.Vector<String> = java.util.Vector<String>()
    //        var filename: String? = null
    //        for (series in 0 until r.imageCount) {
    //            for (image in 0 until r.imageCount) {
    //                filename = getFilename(series, image, r, pattern)
    //                if (!filenames.contains(filename)) filenames.add(filename)
    //            }
    //        }
    //        return filenames.toTypedArray<String>()
    //    }
    //
    //    /**
    //     * @throws FormatException
    //     * @throws IOException
    //     */
    //    @Throws(FormatException::class, java.io.IOException::class) fun getImagesPerFile(pattern: String, r: Reader): Int {
    //        val filenames = getFilenames(pattern, r)
    //        var totalPlanes = 0
    //        for (series in 0 until r.imageCount) {
    //            totalPlanes += r.metadata.get(series).getPlaneCount()
    //        }
    //        return totalPlanes / filenames.size
    //    }
    //
    //    // -- Conversion convenience methods --
    //    /**
    //     * Convenience method for writing all of the images and metadata obtained from
    //     * the specified Reader into the specified Writer.
    //     *
    //     * @param input the pre-initialized Reader used for reading data.
    //     * @param output the uninitialized Writer used for writing data.
    //     * @param outputFile the full path name of the output file to be created.
    //     * @throws FormatException if there is a general problem reading from or
    //     * writing to one of the files.
    //     * @throws IOException if there is an I/O-related error.
    //     */
    //    @Throws(FormatException::class, java.io.IOException::class) fun convert(input: Reader, output: Writer,
    //                                                                            outputFile: String?) {
    //        var p: Plane? = null
    //
    //        for (i in 0 until input.imageCount) {
    //            for (j in 0 until input.getBlockCount(i)) {
    //                p = input.openBlock(i, j)
    //                output.savePlane(i, j, p)
    //            }
    //        }
    //
    //        input.close()
    //        output.close()
    //    }
    //
    //    /**
    //     * As [.convert], with configuration options.
    //     *
    //     * @param input the pre-initialized Reader used for reading data.
    //     * @param output the uninitialized Writer used for writing data.
    //     * @param outputFile the full path name of the output file to be created.
    //     * @param config [SCIFIOConfig] to use for the reading and writing.
    //     * @throws FormatException if there is a general problem reading from or
    //     * writing to one of the files.
    //     * @throws IOException if there is an I/O-related error.
    //     */
    //    @Throws(FormatException::class, java.io.IOException::class) fun convert(input: Reader, output: Writer,
    //                                                                            outputFile: String?, config: SCIFIOConfig?) {
    //        var p: Plane? = null
    //
    //        for (i in 0 until input.imageCount) {
    //            for (j in 0 until input.getBlockCount(i)) {
    //                p = input.openBlock(i, j, config)
    //                output.savePlane(i, j, p)
    //            }
    //        }
    //
    //        input.close()
    //        output.close()
    //    }
    //
    //    /**
    //     * Get the default range for the specified pixel type. Note that this is not
    //     * necessarily the minimum and maximum value which may be stored, but the
    //     * minimum and maximum which should be used for rendering.
    //     *
    //     * @param pixelType the pixel type.
    //     * @return an array containing the min and max as elements 0 and 1,
    //     * respectively.
    //     * @throws IllegalArgumentException if the pixel type is floating point or
    //     * invalid.
    //     */
    //    fun defaultMinMax(pixelType: Int): LongArray {
    //        var min: Long = 0
    //        var max: Long = 0
    //
    //        when (pixelType) {
    //            INT8 -> {
    //                min = Byte.MIN_VALUE.toLong()
    //                max = Byte.MAX_VALUE.toLong()
    //            }
    //            INT16 -> {
    //                min = Short.MIN_VALUE.toLong()
    //                max = Short.MAX_VALUE.toLong()
    //            }
    //            INT32, FLOAT, DOUBLE -> {
    //                min = Int.MIN_VALUE.toLong()
    //                max = Int.MAX_VALUE.toLong()
    //            }
    //            UINT8 -> {
    //                min = 0
    //                max = 2.pow(8.0) as Long - 1
    //            }
    //            UINT16 -> {
    //                min = 0
    //                max = 2.pow(16.0) as Long - 1
    //            }
    //            UINT32 -> {
    //                min = 0
    //                max = 2.pow(32.0) as Long - 1
    //            }
    //            else -> throw java.lang.IllegalArgumentException("Invalid pixel type")
    //        }
    //        return longArrayOf(min, max)
    //    }
    //
    //    /**
    //     * Get the default range for the specified bits per pixel. Note that this is
    //     * not necessarily the minimum and maximum value which may be stored, but the
    //     * minimum and maximum which should be used for rendering.
    //     *
    //     * @return an array containing the min and max as elements 0 and 1,
    //     * respectively.
    //     * @throws IllegalArgumentException if the bits per pixel are non-positive.
    //     */
    //    fun defaultMinMax(bitsPerPixel: Int,
    //                      signed: Boolean): LongArray {
    //        if (bitsPerPixel <= 0) throw java.lang.IllegalArgumentException(
    //            "Bits per pixel must be positive. Value was: $bitsPerPixel")
    //
    //        var min: Long = 0
    //        var max: Long = 0
    //        var bits = bitsPerPixel
    //
    //        if (signed) {
    //            bits--
    //            min = -2.pow(bits.toDouble())
    //        }
    //
    //        max = 2.pow(bits.toDouble()) as Long - 1
    //
    //        return longArrayOf(min, max)
    //    }
    //
    //    /**
    //     * Helper method to delegate to [.defaultMinMax] or
    //     * [.defaultMinMax] based on the given parameters. If a valid
    //     * bitsPerPixel is available, that will be preferred over pixelType.
    //     *
    //     * @return an array containing the min and max as elements 0 and 1,
    //     * respectively.
    //     */
    //    fun defaultMinMax(pixelType: Int,
    //                      bitsPerPixel: Int): LongArray {
    //        if (bitsPerPixel > 0) {
    //            return defaultMinMax(bitsPerPixel, isSigned(pixelType))
    //        }
    //
    //        return defaultMinMax(pixelType)
    //    }
    //
    //    /**
    //     * Helper method to get the default range for the specified
    //     * [ImageMetadata].
    //     *
    //     * @return an array containing the min and max as elements 0 and 1,
    //     * respectively.
    //     */
    //    fun defaultMinMax(iMeta: ImageMetadata): LongArray {
    //        return defaultMinMax(iMeta.pixelType, iMeta.bitsPerPixel)
    //    }
    //
    /** Performs suffix matching for the given filename.  */
    fun checkSuffix(name: String, suffix: String): Boolean = checkSuffix(name, arrayOf(suffix))

    /** Performs suffix matching for the given filename.  */
    fun checkSuffix(name: String, suffixList: Array<String>): Boolean {
        val lname = name.lowercase()
        return suffixList.any { suffix ->
            val s = ".$suffix"
            lname.endsWith(s) || COMPRESSION_SUFFIXES.any { lname.endsWith("$s.$it") }
        }
    }
}