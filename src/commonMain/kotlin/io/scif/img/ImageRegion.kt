/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2015 Board of Regents of the University of
 * Wisconsin-Madison
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
package io.scif.img

import net.imagej.axis.Axes
import net.imagej.axis.AxisType

/**
 * A list of dimensional ranges. Each range is associated with a particular
 * [AxisType], and is used to restrict the indices that are read in the
 * corresponding planes.
 *
 *
 * NB: Subregions must be continuous in the [Axes.X] and [Axes.Y]
 * dimensions. Discontinuous tiles require multiple openings.
 *
 *
 * @author Mark Hiner
 */
class ImageRegion {
    // -- Fields --
    private val dimRanges: MutableMap<AxisType, Range>

    // -- Constructors --
    constructor(axes: Array<AxisType>, ranges: Array<String>) {
        dimRanges = HashMap()

        if (axes.size != ranges.size)
            throw IllegalArgumentException("Number of axes: ${axes.size} does not match number of ranges: ${ranges.size}")

        for (i in axes.indices)
            addRange(axes[i], ranges[i])
    }

    constructor(axes: Array<AxisType>, vararg ranges: Range) {
        dimRanges = HashMap()

        if (axes.size != ranges.size)
            throw IllegalArgumentException("Number of axes: ${axes.size} does not match number of ranges: ${ranges.size}")

        for (i in axes.indices)
            dimRanges[axes[i]] = ranges[i]
    }

    constructor(ranges: MutableMap<AxisType, Range>) {
        dimRanges = ranges
    }

    // -- SubRegion methods --
    /**
     * @param range Dimensional range to add to this SubRegion
     */
    fun addRange(axis: AxisType, range: String) {
        dimRanges[axis] = Range(range)
    }

    /**
     * @return A list of indices for the specified dimension
     */
    fun getRange(axisType: AxisType): Range? = dimRanges[axisType]

    /**
     * @return True if this SubRegion contains a range for the specified AxisType
     */
    fun hasRange(axisType: AxisType): Boolean = dimRanges[axisType] != null

    /**
     * @return Number of ranges in this SubRegion
     */
    val size: Int
        get() = dimRanges.size
}