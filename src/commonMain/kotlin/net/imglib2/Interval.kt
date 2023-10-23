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
import kotlin.collections.Iterator

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

/**
 *
 *
 * *f*:{xZ<sup>*n*</sup>|[min,max]T}
 *
 *
 *
 *
 * A function over an n-dimensional integer interval that can create a random
 * access [Sampler].
 *
 *
 *
 * By convention, a RandomAccessibleInterval represents a function that is
 * *defined at all coordinates of the interval*.
 *
 *
 *
 * There is no guarantee regarding whether the function is defined outside the
 * bounds of its interval. If the function is known to be defined for
 * out-of-bounds values in a particular interval, the
 * [.randomAccess] method should be used to access those
 * valueswhereas the [.randomAccess] (no arguments) method *is
 * not intended to access out-of-bounds values*. See
 * [RandomAccessible.randomAccess] for related discussion.
 *
 *
 * @author Stephan Saalfeld
 */
interface RandomAccessibleInterval<T> : RandomAccessible<T>, Interval

/**
 * An [IterableRealInterval] whose elements are located at integer
 * coordinates.
 *
 *
 * An `IterableInterval` is *not* guaranteed to iterate over all
 * coordinates of its containing [Interval]. In the typical case of a
 * hyperrectangular image, it will do so; however, there are some
 * `IterableInterval`s which visit only a subset of the `Interval`
 * coordinates. For example, the `imglib2-roi` library provides means to
 * model regions of interest (ROIs), along with the ability to iterate over
 * coordinates within a particular ROI; see e.g.
 * `net.imglib2.roi.labeling.LabelRegion`.
 *
 *
 * @author Tobias Pietzsch
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
interface IterableInterval<T> : IterableRealInterval<T>, Interval {
    override val cursor: Cursor<T>
    override val localizingCursor: Cursor<T>
}

/**
 *
 *
 * *f*:R<sup>*n*</sup>[0,*s*]T
 *
 *
 *
 *
 * A function over real space and a finite set of elements in the target domain
 * *T*. All target elements *T* can be accessed through Iterators.
 * There is an iterator that tracks its source location at each move and one
 * that calculates it on request only. Depending on the frequency of requesting
 * the source location, either of them is optimal in terms of speed. Iteration
 * order is constant for an individual [IterableRealInterval] but not
 * further specified.
 *
 *
 * @param <T>
 *
 * @author Stephan Saalfeld
</T> */
interface IterableRealInterval<T> : RealInterval, Iterable<T> {
    /**
     * Returns a [RealCursor] that iterates with optimal speed without
     * calculating the location at each iteration step. Localization is
     * performed on demand.
     *
     * Use this where localization is required rarely/ not for each iteration.
     *
     * @return fast iterating iterator
     */
    val cursor: RealCursor<T>

    /**
     * Returns a [RealLocalizable] [Iterator] that calculates its
     * location at each iteration step. That is, localization is performed with
     * optimal speed.
     *
     * Use this where localization is required often/ for each iteration.
     *
     * @return fast localizing iterator
     */
    val localizingCursor: RealCursor<T>

    /**
     * Returns the number of elements in this [ Function][IterableRealInterval].
     *
     * @return number of elements
     */
    val size: Long

    /**
     * Get the first element of this [IterableRealInterval]. This is a
     * shortcut for `cursor().next()`.
     *
     * This can be used to create a new variable of type T using
     * `firstElement().createVariable()`, which is useful in generic
     * methods to store temporary results, e.g., a running sum over pixels in
     * the [IterableRealInterval].
     *
     * @return the first element in iteration order.
     */
    val firstElement: T    // TODO: fix places where it is not necessary to implement this anymore
        get() = iterator().next()

    /**
     * Returns the iteration order of this [IterableRealInterval]. If the
     * returned object equals ([Object.equals]) the iteration
     * order of another [IterableRealInterval] *f* then they can be
     * copied by synchronous iteration. That is, having an [Iterator] on
     * this and another [Iterator] on *f*, moving both in synchrony
     * will point both of them to corresponding locations in their source
     * domain. In other words, this and *f* have the same iteration order
     * and means and the same number of elements.
     *
     * @see FlatIterationOrder
     *
     *
     * @return the iteration order of this [IterableRealInterval].
     */
    val iterationOrder: Any    // TODO: fix places where it is not necessary to implement this anymore

    override fun iterator(): Iterator<T> = cursor
}
