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

import net.imglib2.util.Intervals
import uns.f64
import kotlin.jvm.JvmOverloads

/**
 * Implementation of the [Interval] interface.
 *
 *
 * @author Tobias Pietzsch
 * @author Stephan Preibisch
 */
abstract class AbstractInterval : AbstractEuclideanSpace, Interval {

    protected val min: LongArray
    protected val max: LongArray

    /**
     * Creates an *n*-dimensional [AbstractInterval] with min and
     * max = 0<sup>n</sup>.
     *
     * @param n
     * number of dimensions
     */
    constructor(n: Int) : super(n) {
        min = LongArray(n)
        max = LongArray(n)
    }

    /**
     * Creates a [AbstractInterval] from another [Interval]
     *
     * @param interval
     * another [Interval]
     */
    constructor(interval: Interval) : this(interval.numDimensions) {
        interval.min(min)
        interval.max(max)
    }

    /**
     * Creates an Interval with the boundaries [0, dimensions-1]
     *
     * @param dimensions
     * the size of the interval
     */
    constructor(dimensions: Dimensions) : this(dimensions.numDimensions) {
        for (d in dimensions.dimensions) max[d] = dimensions.dimension(d) - 1
    }

    /**
     * Creates an Interval with the boundaries [min, max] (both including)
     *
     * @param min
     * the position of the first elements in each dimension
     * @param max
     * the position of the last elements in each dimension
     * @param copy
     * flag indicating whether min and max arrays should be duplicated.
     */
    /**
     * Creates an Interval with the boundaries [min, max] (both including)
     *
     * @param min
     * the position of the first elements in each dimension
     * @param max
     * the position of the last elements in each dimension
     */
    @JvmOverloads
    constructor(min: LongArray, max: LongArray, copy: Boolean = true) : super(min.size) {
        //        assert(min.size == max.size)
        this.min = if (copy) min.copyOf() else min
        this.max = if (copy) max.copyOf() else max
    }

    /**
     * Creates an Interval with the boundaries [min, max] (both including)
     *
     * @param min
     * the position of the first elements in each dimension
     * @param max
     * the position of the last elements in each dimension
     */
    constructor(min: Localizable, max: Localizable) : this(min.positionAsLongArray(), max.positionAsLongArray(), false)

    /**
     * Creates an Interval with the boundaries [0, dimensions-1]
     *
     * @param dimensions
     * the size of the interval
     */
    constructor(dimensions: LongArray) : this(dimensions.size) {
        for (d in dimensions.indices) max[d] = dimensions[d] - 1
    }

    override fun realMin(d: Int): Double {
        //        assert(d in 0..<n)
        return min[d].f64
    }

    override fun realMin(min: DoubleArray) {
        //        assert(min.size == n)
        for (d in dimensions) min[d] = this.min[d].f64
    }

    override fun realMin(min: RealPositionable) {
        //        assert(min.numDimensions == numDimensions)
        min.setPosition(this.min)
    }

    override fun realMax(d: Int): Double {
        //        assert(d in 0..<n)
        return max[d].f64
    }

    override fun realMax(max: DoubleArray) {
        //        assert(max.size == n)
        for (d in dimensions) max[d] = this.max[d].toDouble()
    }

    override fun realMax(max: RealPositionable) {
        //        assert(max.numDimensions == numDimensions)
        max.setPosition(this.max)
    }

    override fun min(d: Int): Long {
        //        assert(d in 0..<n)
        return min[d]
    }

    override fun min(min: LongArray) {
        //        assert(min.size == numDimensions)
        for (d in dimensions) min[d] = this.min[d]
    }

    override fun min(min: Positionable) {
        //        assert(m.numDimensions == numDimensions)
        min.setPosition(this.min)
    }

    override fun max(d: Int): Long {
        //        assert(d in 0..< n)
        return max[d]
    }

    override fun max(max: LongArray) {
        //        assert(max.size == numDimensions)
        for (d in dimensions) max[d] = this.max[d]
    }

    override fun max(max: Positionable) {
        //        assert(max.numDimensions == numDimensions)
        max.setPosition(this.max)
    }

    override fun dimensions(dimensions: LongArray) {
        //        assert(dimensions.size == numDimensions)
        for (d in this.dimensions) dimensions[d] = max[d] - min[d] + 1
    }

    override fun dimension(d: Int): Long {
//        assert(d in 0..<n)
        return max[d] - min[d] + 1
    }

    override fun toString(): String = this::class.simpleName!! + " " + Intervals.toString(this)
}