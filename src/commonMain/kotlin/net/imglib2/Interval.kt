/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2023 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
 * John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
 * Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
 * Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
 * Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
 * Jean-Yves Tinevez and Michael Zinsmaier.
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
package net.imglib2

import uns.f64

/**
 *
 *
 * {xZ<sup>*n*</sup>|*min<sub>d</sub>*
 * *x<sub>d</sub>**max<sub>d</sub>*;*d*{0
 * *n*-1}}
 *
 *
 *
 *
 * An [Interval] over the discrete source domain. *Note* that this
 * does *not* imply that for *all* coordinates in the
 * [Interval] function values exist or can be generated. It only defines
 * where the minimum and maximum source coordinates are. E.g. an
 * [IterableInterval] has a limited number of values and a source
 * coordinate for each. By that, minimum and maximum are defined but the
 * [Interval] does not define a value for all coordinates in between.
 *
 *
 * @author Stephan Saalfeld
 * @author Stephan Preibisch
 */
interface Interval : RealInterval, Dimensions {
    /**
     * Get the minimum in dimension d.
     *
     * @param d
     * dimension
     * @return minimum in dimension d.
     */
    fun min(d: Int): Long

    /**
     * Write the minimum of each dimension into long[].
     *
     * @param min
     */
    fun min(min: LongArray) {
        for (d in dimensions) min[d] = min(d)
    }

    /**
     * Sets a [Positionable] to the minimum of this [Interval]
     *
     * @param min
     */
    fun min(min: Positionable) {
        for (d in dimensions) min.setPosition(min(d), d)
    }

    /**
     * Get the maximum in dimension d.
     *
     * @param d
     * dimension
     * @return maximum in dimension d.
     */
    fun max(d: Int): Long

    /**
     * Write the maximum of each dimension into long[].
     *
     * @param max
     */
    fun max(max: LongArray) {
        for (d in dimensions) max[d] = max(d)
    }

    /**
     * Sets a [Positionable] to the maximum of this [Interval]
     *
     * @param max
     */
    fun max(max: Positionable) {
        for (d in dimensions) max.setPosition(max(d), d)
    }

    /** Default implementation of [RealInterval.realMin].  */
    override fun realMin(d: Int): Double = min(d).f64

    /** Default implementation of [RealInterval.realMax].  */
    override fun realMax(d: Int): Double = max(d).f64

    /** Default implementation of [Dimensions.dimension].  */
    override fun dimension(d: Int): Long = max(d) - min(d) + 1

    /**
     * Allocates a new long array with the minimum of this Interval.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.min].
     *
     * @return the min
     */
    fun minAsLongArray(): LongArray {
        val min = LongArray(numDimensions)
        min(min)
        return min
    }

    /**
     * Allocates a new [Point] with the maximum of this Interval.
     *
     * Please note that his method allocates a new [Point] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [Point] first and reuse it with [.min].
     *
     * @return the min
     */
    fun minAsPoint(): Point {
        val min = Point(numDimensions)
        min(min)
        return min
    }

    /**
     * Allocates a new long array with the maximum of this Interval.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.max].
     *
     * @return the max
     */
    fun maxAsLongArray(): LongArray {
        val max = LongArray(numDimensions)
        max(max)
        return max
    }

    /**
     * Allocates a new [Point] with the maximum of this Interval.
     *
     * Please note that his method allocates a new [Point] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [Point] first and reuse it with [.max].
     *
     * @return the max
     */
    fun maxAsPoint(): Point {
        val max = Point(numDimensions)
        max(max)
        return max
    }
}