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

//import io.scif.filters.MetadataWrapper
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.location.Location
import io.scif.ImageMetadata
import io.scif.Metadata
import net.imagej.axis.Axes
import net.imagej.axis.CalibratedAxis
import net.imagej.space.DefaultCalibratedSpace
import net.imglib2.Interval
import okio.FileHandle
import platform.FormatException
import java.util.*

/**
 * A utility class for working with [io.scif.Metadata] objects.
 *
 * @see io.scif.Metadata
 *
 * @author Mark Hiner
 */
object SCIFIOMetadataTools {

    // -- Utility Methods -- Metadata --

    // -- Utility Methods -- Metadata --
    /**
     * Counts the number of interleaved axes for specified image of the given [Metadata]
     */
    fun countInterleavedAxes(meta: Metadata, imageIndex: Int): Int = countInterleavedAxes(meta[imageIndex])

    /**
     * Counts the number of interleaved axes in a given [ImageMetadata]
     */
    fun countInterleavedAxes(iMeta: ImageMetadata): Int = getInterleavedAxes(iMeta).numDimensions

    /**
     * Returns the interleaved axes for specified image of the given [Metadata]
     */
    fun getInterleavedAxes(metadata: Metadata, imageIndex: Int): DefaultCalibratedSpace =
        getInterleavedAxes(metadata[imageIndex])

    /**
     * Returns the interleaved axes in a given [ImageMetadata]
     */
    fun getInterleavedAxes(iMeta: ImageMetadata): DefaultCalibratedSpace = getPlanarAxes(iMeta, false)

    /**
     * Counts the number of planar axes for specified image of the given [Metadata]
     */
    fun countPlanarAxes(meta: Metadata, imageIndex: Int): Int = countPlanarAxes(meta[imageIndex])

    /**
     * Counts the number of planar axes in a given [ImageMetadata]
     */
    fun countPlanarAxes(iMeta: ImageMetadata): Int = getPlanarAxes(iMeta).numDimensions

    /**
     * Returns the planar axes for specified image of the given [Metadata]
     */
    fun getPlanarAxes(metadata: Metadata, imageIndex: Int): DefaultCalibratedSpace =
        getPlanarAxes(metadata[imageIndex])

    /**
     * Returns the planar axes in a given [ImageMetadata]
     */
    fun getPlanarAxes(iMeta: ImageMetadata): DefaultCalibratedSpace = getPlanarAxes(iMeta, true)

    /**
     * @param metadata
     * @param imageIndex
     * @return True if [Axes.CHANNEL] appears before [Axes.X] or
     * [Axes.Y] in the specified image of the given [Metadata]
     * .
     */
    fun isMultichannel(metadata: Metadata, imageIndex: Int): Boolean = isMultichannel(metadata[imageIndex])

    /**
     * @param iMeta
     * @return True if [Axes.CHANNEL] appears before [Axes.X] or
     * [Axes.Y] in the target [ImageMetadata].
     */
    fun isMultichannel(iMeta: ImageMetadata): Boolean {
        TODO()
//        val cIndex: Int = iMeta.dimensionIndex(Axes.CHANNEL)
//        return cIndex < iMeta.dimensionIndex(Axes.X) || cIndex < iMeta.dimensionIndex(Axes.Y)
    }

    /** @Returns true if the provided axes correspond to a complete image plane */
    fun wholeBlock(imageIndex: Int, meta: Metadata, range: Interval): Boolean {
        val wholePlane = wholeRow(imageIndex, meta, range)
        TODO()
//        val yIndex: Int = meta[imageIndex].dimensionIndex(Axes.Y)
//        return wholePlane && range.min(yIndex) == 0L && range.max(yIndex) == meta[imageIndex].dimension(Axes.Y)
    }

    /** @Returns true if the provided axes correspond to a complete image row */
    fun wholeRow(imageIndex: Int, meta: Metadata, range: Interval): Boolean {
        var wholeRow = true
        TODO()
//        val yIndex: Int = meta[imageIndex].dimensionIndex(Axes.Y)
//
//        for (i in range.dimensions) {
//            if (!wholeRow) break
//            if (i == yIndex) continue
//            if (range.min(i) != 0L || range.dimension(i) != meta[imageIndex].dimension(i)) wholeRow = false
//        }
//
//        return true
    }

    /**
     * Casts the provided Metadata object to the generic type of this method.
     *
     *
     * Usage: To cast a Metadata instance to ConcreteMetadata, use:
     * `SCIFIOMetadataTools.<ConcreteMetadata>castMeta(meta)`.
     *
     */
    fun <M : Metadata?> castMeta(meta: Metadata?): M? {
        // TODO need to check for safe casting here..

        return meta as? M
    }

    //    /**
    //     * Unwraps the provided [Metadata] class if it has been wrapped in
    //     * [MetadataWrapper](s).
    //     *
    //     * @param meta Metadata instance to unwrap
    //     * @return If meta is a MetadataWrapper, the tail wrapped Metadata. Otherwise
    //     * meta is returned directly.
    //     */
    //    fun unwrapMetadata(meta: Metadata): Metadata {
    //        var unwrappedMeta = meta
    //
    //        // Unwrap MetadataWrappers to get to the actual format-specific metadata
    //        while (unwrappedMeta is MetadataWrapper) {
    //            unwrappedMeta = (unwrappedMeta as MetadataWrapper).unwrap()
    //        }
    //
    //        return unwrappedMeta
    //    }

    /**
     * Checks whether the given metadata object has the minimum metadata populated
     * to successfully describe an Image.
     *
     * @throws FormatException if there is a missing metadata field, or the
     * metadata object is uninitialized
     */
    @Throws(FormatException::class)
    fun verifyMinimumPopulated(src: Metadata, out: FileHandle/*DataHandle<Location?>?*/) =
        verifyMinimumPopulated(src, out, 0)

    /**
     * Checks whether the given metadata object has the minimum metadata populated
     * to successfully describe the nth Image.
     *
     * @throws FormatException if there is a missing metadata field, or the
     * metadata object is uninitialized
     */
    @Throws(FormatException::class)
    fun verifyMinimumPopulated(src: Metadata, out: FileHandle/*DataHandle<Location?>?*/, imageIndex: Int) {
        //        if (src == null)
        //            throw FormatException("Metadata object is null; call Writer.setMetadata() first")
        //        if (out == null)
        //            throw FormatException("DataHandle object is null; call Writer.setSource(<Location/DataHandle>) first")
        TODO()
        //        if (src[imageIndex].numDimensions == 0)
        //            throw FormatException("Axiscount #$imageIndex is 0")
    }

    // -- Utility methods -- dimensional axes --

    //    /**
    //     * Guesses at a reasonable default planar axis count for the given list of
    //     * dimensional axes.
    //     *
    //     *
    //     * The heuristic looks for the first index following both [Axes.X] and
    //     * [Axes.Y]. If the list does not contain both `X` and `Y`
    //     * then the guess will equal the total number of axes.
    //     *
    //     */
    //    fun guessPlanarAxisCount(axes: List<CalibratedAxis>): Int {
    //        var xFound = false
    //        var yFound = false
    //        var d = 0
    //        while (d < axes.size) {
    //            if (xFound && yFound) break
    //            val type: AxisType = axes[d].type()
    //            if (type === Axes.X) xFound = true
    //            else if (type === Axes.Y) yFound = true
    //            d++
    //        }
    //        return d
    //    }
    //
    //    /**
    //     * Guesses at a reasonable default interleaved axis count for the given list
    //     * of dimensional axes.
    //     *
    //     *
    //     * The heuristic looks for the last index preceding both [Axes.X] and
    //     * [Axes.Y]. If the list does not contain either `X` or `Y`
    //     * then the guess will equal the total number of axes.
    //     *
    //     */
    //    fun guessInterleavedAxisCount(axes: List<CalibratedAxis>): Int {
    //        for (d in axes.indices) {
    //            val type: AxisType = axes[d].type()
    //            if (type === Axes.X || type === Axes.Y) return d
    //        }
    //        return axes.size
    //    }
    //
    //    // -- Utility methods -- original metadata --
    //    /**
    //     * Merges the given lists of metadata, prepending the specified prefix for the
    //     * destination keys.
    //     */
    //    fun merge(src: Map<String, Any?>,
    //              dest: MutableMap<String?, Any?>, prefix: String) {
    //        for (key in src.keys) {
    //            dest[prefix + key] = src[key]
    //        }
    //    }
    //
    //    /** Gets a sorted list of keys from the given hashtable.  */
    //    fun keys(meta: java.util.Hashtable<String?, Any?>): Array<String?> {
    //        val keys = arrayOfNulls<String>(meta.size())
    //        meta.keys.toArray<String>(keys)
    //        Arrays.sort(keys)
    //        return keys
    //    }

    // -- Utility Methods --

    // -- Utility Methods --
    /**
     * Helper method to get all the planar axes in a given ImageMetadata. Returns
     * the minimal set of leading axes that includes both [Axes.X] and
     * [Axes.Y]. Whether or not X and Y are themselves included in the
     * returned set can be controlled with `includeXY` - if false, the
     * resulting set is the list of interleaved axes.
     */
    private fun getPlanarAxes(iMeta: ImageMetadata,
                              includeXY: Boolean): DefaultCalibratedSpace {
        val axes = ArrayList<CalibratedAxis>()

        var xyCount = 0

        // Iterate over the axes in the target ImageMetadata.
        // Once we have seen both Axes.X and Axes.Y we have identified all planar
        // axes.
        var i = 0
        TODO()
//        while (xyCount < 2 && i < iMeta.numDimensions) {
//            val axis: CalibratedAxis = iMeta.axis(i)
//            if (axis.type == Axes.X || axis.type == Axes.Y) {
//                if (includeXY) axes += axis
//                xyCount++
//            } else axes += axis
//            i++
//        }
//
//        return DefaultCalibratedSpace(axes)
    }
}