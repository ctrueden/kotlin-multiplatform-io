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

/**
 *
 *
 * {xR<sup>*n*</sup>|*min<sub>d</sub>*
 * *x<sub>d</sub>**max<sub>d</sub>*;*d*{0
 * *n*-1}}
 *
 *
 * An [RealInterval] over the real source domain. *Note* that this
 * does *not* imply that for *all* coordinates in the
 * [RealInterval] function values exist or can be generated. It only
 * defines where the minimum and maximum source coordinates are. E.g. an
 * [IterableRealInterval] has a limited number of values and a source
 * coordinate for each. By that, minimum and maximum are defined but the
 * [RealInterval] does not define a value for all coordinates in between.
 *
 * @author Stephan Saalfeld
 */
interface RealInterval : EuclideanSpace {
    /**
     * Get the minimum in dimension d.
     *
     * @param d
     * dimension
     * @return minimum in dimension d.
     */
    fun realMin(d: Int): Double

    /**
     * Write the minimum of each dimension into double[].
     *
     * @param min
     */
    fun realMin(min: DoubleArray) {
        for (d in dimensions) min[d] = realMin(d)
    }

    /**
     * Sets a [RealPositionable] to the minimum of this [Interval]
     *
     * @param min
     */
    fun realMin(min: RealPositionable) {
        for (d in dimensions) min.setPosition(realMin(d), d)
    }

    /**
     * Get the maximum in dimension d.
     *
     * @param d
     * dimension
     * @return maximum in dimension d.
     */
    fun realMax(d: Int): Double

    /**
     * Write the maximum of each dimension into double[].
     *
     * @param max
     */
    fun realMax(max: DoubleArray) {
        for (d in dimensions) max[d] = realMax(d)
    }

    /**
     * Sets a [RealPositionable] to the maximum of this [Interval]
     *
     * @param max
     */
    fun realMax(max: RealPositionable) {
        for (d in dimensions) max.setPosition(realMax(d), d)
    }

    /**
     * Allocates a new double array with the minimum of this RealInterval.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.realMin].
     *
     * @return the min
     */
    fun minAsDoubleArray(): DoubleArray {
        val min = DoubleArray(numDimensions)
        realMin(min)
        return min
    }

    /**
     * Allocates a new [RealPoint] with the minimum of this RealInterval.
     *
     * Please note that his method allocates a new [RealPoint] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [RealPoint] first and reuse it with
     * [.realMin].
     *
     * @return the min
     */
    fun minAsRealPoint(): RealPoint {
        val min: RealPoint = RealPoint(numDimensions)
        realMin(min)
        return min
    }

    /**
     * Allocates a new double array with the maximum of this RealInterval.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.realMax].
     *
     * @return the max
     */
    fun maxAsDoubleArray(): DoubleArray {
        val max = DoubleArray(numDimensions)
        realMax(max)
        return max
    }

    /**
     * Allocates a new [RealPoint] with the maximum of this RealInterval.
     *
     * Please note that his method allocates a new [RealPoint] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [RealPoint] first and reuse it with
     * [.realMax].
     *
     * @return the max
     */
    fun maxAsRealPoint(): RealPoint {
        val max = RealPoint(numDimensions)
        realMax(max)
        return max
    }
}