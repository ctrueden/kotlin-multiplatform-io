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
package net.imagej.interval

import net.imagej.axis.CalibratedAxis
import net.imagej.axis.TypedAxis
import net.imagej.space.AbstractCalibratedSpace
import net.imagej.space.CalibratedSpace
import net.imagej.space.TypedSpace
import net.imglib2.RealInterval
import net.imglib2.RealPositionable

/**
 * Abstract base class for [CalibratedRealInterval].
 *
 * @author Barry DeZonia
 */
abstract class AbstractCalibratedRealInterval<A : CalibratedAxis>
    : AbstractCalibratedSpace<A>, CalibratedRealInterval<A> {
    private val min: DoubleArray
    private val max: DoubleArray

    constructor(interval: RealInterval) : super(interval.numDimensions) {
        min = DoubleArray(interval.numDimensions)
        max = min.copyOf()
        interval.realMin(min)
        interval.realMax(max)
    }

    constructor(interval: RealInterval, vararg axes: A) : super(*axes) {
        if (interval.numDimensions != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        min = DoubleArray(interval.numDimensions)
        max = min.copyOf()
        interval.realMin(min)
        interval.realMax(max)
    }

    constructor(interval: RealInterval, axes: List<A>) : super(axes) {
        if (interval.numDimensions != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        min = DoubleArray(interval.numDimensions)
        max = min.copyOf()
        interval.realMin(min)
        interval.realMax(max)
    }

    constructor(extents: DoubleArray) : super(extents.size) {
        min = DoubleArray(extents.size)
        max = extents.copyOf()
    }

    constructor(extents: DoubleArray, vararg axes: A) : super(*axes) {
        if (extents.size != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        min = DoubleArray(extents.size)
        max = extents.copyOf()
    }

    constructor(extents: DoubleArray, axes: List<A>) : super(axes) {
        if (extents.size != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        min = DoubleArray(extents.size)
        max = extents.copyOf()
    }

    constructor(min: DoubleArray, max: DoubleArray) : super(min.size) {
        if (min.size != max.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        this.min = min.copyOf()
        this.max = max.copyOf()
    }

    constructor(min: DoubleArray, max: DoubleArray, vararg axes: A) : super(*axes) {
        if (min.size != max.size || min.size != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        this.min = min.copyOf()
        this.max = max.copyOf()
    }

    constructor(min: DoubleArray, max: DoubleArray, axes: List<A>) : super(axes) {
        if (min.size != max.size || min.size != axes.size)
            throw IllegalArgumentException("number of provided axes != number of dimensions")
        this.min = min.copyOf()
        this.max = max.copyOf()
    }

    // -- CalibratedSpace methods --
    override fun averageScale(d: Int): Double = axis(d).averageScale(realMin(d), realMax(d))

    // -- RealInterval methods --
    override fun realMax(d: Int): Double = max[d]

    override fun realMax(max: DoubleArray) {
        for (i in max.indices)
            max[i] = realMax(i)
    }

    override fun realMax(max: RealPositionable) {
        for (i in max.dimensions)
            max.setPosition(realMax(i), i)
    }

    override fun realMin(d: Int): Double = min[d]

    override fun realMin(min: DoubleArray) {
        for (i in min.indices)
            min[i] = realMin(i)
    }

    override fun realMin(min: RealPositionable) {
        for (i in min.dimensions)
            min.setPosition(realMin(i), i)
    }
}

/**
 * A CalibratedRealInterval marries a [TypedRealInterval] with a
 * [CalibratedSpace].
 *
 * @author Barry DeZonia
 */
interface CalibratedRealInterval<A : CalibratedAxis> : TypedRealInterval<A>, CalibratedSpace<A>

/**
 * A [RealInterval] over a [TypedSpace].
 *
 * @author Curtis Rueden
 */
interface TypedRealInterval<A : TypedAxis> : RealInterval, TypedSpace<A>
