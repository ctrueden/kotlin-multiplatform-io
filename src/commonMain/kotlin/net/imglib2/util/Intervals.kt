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
package net.imglib2.util

import net.imglib2.*
//import net.imglib2.transform.integer.Mixed
//import net.imglib2.view.ViewTransforms
import kotlin.math.*

/**
 * Convenience methods for manipulating [Intervals][Interval].
 *
 * @author Tobias Pietzsch
 */
object Intervals {
    //    /**
    //     * Create a [FinalInterval] from a parameter list comprising minimum
    //     * coordinates and size. For example, to create a 2D interval from (10, 10)
    //     * to (20, 40) use createMinSize( 10, 10, 11, 31 ).
    //     *
    //     * @param minsize
    //     * a list of *2*n* parameters to create a *n*
    //     * -dimensional interval. The first *n* parameters specify
    //     * the minimum of the interval, the next *n* parameters
    //     * specify the dimensions of the interval.
    //     * @return interval with the specified boundaries
    //     */
    //    fun createMinSize(vararg minsize: Long): FinalInterval {
    //        return FinalInterval.createMinSize(minsize)
    //    }
    //
    //    /**
    //     * Create a [FinalInterval] from a parameter list comprising minimum
    //     * and maximum coordinates. For example, to create a 2D interval from (10,
    //     * 10) to (20, 40) use createMinMax( 10, 10, 20, 40 ).
    //     *
    //     * @param minmax
    //     * a list of *2*n* parameters to create a *n*
    //     * -dimensional interval. The first *n* parameters specify
    //     * the minimum of the interval, the next *n* parameters
    //     * specify the maximum of the interval.
    //     * @return interval with the specified boundaries
    //     */
    //    fun createMinMax(vararg minmax: Long): FinalInterval {
    //        return FinalInterval.createMinMax(minmax)
    //    }
    //
    //    /**
    //     * THIS METHOD WILL BE REMOVED IN A FUTURE RELEASE. It was mistakenly
    //     * introduced, analogous to [.createMinSize] for integer
    //     * intervals. Dimension is not defined for [RealInterval] and
    //     * computing the *max* as *min + dim - 1* does not make sense.
    //     *
    //     *
    //     *
    //     * Create a [FinalRealInterval] from a parameter list comprising
    //     * minimum coordinates and size. For example, to create a 2D interval from
    //     * (10, 10) to (20, 40) use createMinSize( 10, 10, 11, 31 ).
    //     *
    //     * @param minsize
    //     * a list of *2*n* parameters to create a *n*
    //     * -dimensional interval. The first *n* parameters specify
    //     * the minimum of the interval, the next *n* parameters
    //     * specify the dimensions of the interval.
    //     * @return interval with the specified boundaries
    //     */
    //    @Deprecated("") fun createMinSizeReal(vararg minsize: Double): FinalRealInterval {
    //        return FinalRealInterval.createMinSize(minsize)
    //    }
    //
    //    /**
    //     * Create a [FinalRealInterval] from a parameter list comprising
    //     * minimum and maximum coordinates. For example, to create a 2D interval
    //     * from (10, 10) to (20, 40) use createMinMax( 10, 10, 20, 40 ).
    //     *
    //     * @param minmax
    //     * a list of *2*n* parameters to create a *n*
    //     * -dimensional interval. The first *n* parameters specify
    //     * the minimum of the interval, the next *n* parameters
    //     * specify the maximum of the interval.
    //     * @return interval with the specified boundaries
    //     */
    //    fun createMinMaxReal(vararg minmax: Double): FinalRealInterval {
    //        return FinalRealInterval.createMinMax(minmax)
    //    }
    //
    //    /**
    //     * Grow/shrink an interval in all dimensions.
    //     *
    //     * Create a [FinalInterval], which is the input interval plus border
    //     * pixels on every side, in every dimension.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param border
    //     * how many pixels to add on every side
    //     * @return expanded interval
    //     */
    //    fun expand(interval: Interval, border: Long): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        for (d in 0 until n) {
    //            min[d] -= border
    //            max[d] += border
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Grow/shrink an interval in all dimensions.
    //     *
    //     * Create a [FinalInterval], which is the input interval plus border
    //     * pixels on every side, in every dimension.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param border
    //     * how many pixels to add on every side
    //     * @return expanded interval
    //     */
    //    fun expand(interval: Interval?, vararg border: Long): FinalInterval {
    //        return expand(interval, FinalDimensions(border))
    //    }
    //
    //    /**
    //     * Grow/shrink an interval in all dimensions.
    //     *
    //     * Create a [FinalInterval], which is the input interval plus border
    //     * pixels on every side, in every dimension.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param border
    //     * how many pixels to add on every side
    //     * @return expanded interval
    //     */
    //    fun expand(interval: Interval, border: Dimensions): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        for (d in 0 until n) {
    //            min[d] -= border.dimension(d)
    //            max[d] += border.dimension(d)
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Grow/shrink an interval in one dimensions.
    //     *
    //     * Create a [FinalInterval], which is the input interval plus border
    //     * pixels on every side, in dimension d.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param border
    //     * how many pixels to add on every side
    //     * @param d
    //     * in which dimension
    //     * @return expanded interval
    //     */
    //    fun expand(interval: Interval, border: Long, d: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        min[d] -= border
    //        max[d] += border
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Translate an interval in one dimension.
    //     *
    //     * Create a [FinalInterval], which is the input interval shifted by t
    //     * in dimension d.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param t
    //     * by how many pixels to shift the interval
    //     * @param d
    //     * in which dimension
    //     * @return translated interval
    //     */
    //    fun translate(interval: Interval, t: Long, d: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        min[d] += t
    //        max[d] += t
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Translate an interval.
    //     *
    //     * Create a [FinalInterval], which is the input interval shifted by
    //     * `translation`.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param translation
    //     * by how many pixels to shift the interval
    //     * @return translated interval
    //     */
    //    fun translate(interval: Interval, vararg translation: Long): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        for (d in 0 until n) {
    //            min[d] += translation[d]
    //            max[d] += translation[d]
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Translate an interval by `-translation`.
    //     *
    //     * Create a [FinalInterval], which is the input interval shifted by
    //     * `-translation`.
    //     *
    //     * @param interval
    //     * the input interval
    //     * @param translation
    //     * by how many pixels to inverse-shift the interval
    //     * @return translated interval
    //     */
    //    fun translateInverse(interval: Interval, vararg translation: Long): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        for (d in 0 until n) {
    //            min[d] -= translation[d]
    //            max[d] -= translation[d]
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Create new interval by adding a dimension to the source [Interval].
    //     * The [Interval] boundaries in the additional dimension are set to
    //     * the specified values.
    //     *
    //     * The additional dimension is the last dimension.
    //     *
    //     * @param interval
    //     * the original interval
    //     * @param minOfNewDim
    //     * Interval min in the additional dimension.
    //     * @param maxOfNewDim
    //     * Interval max in the additional dimension.
    //     */
    //    fun addDimension(interval: Interval, minOfNewDim: Long, maxOfNewDim: Long): FinalInterval {
    //        val m: Int = interval.numDimensions()
    //        val min = LongArray(m + 1)
    //        val max = LongArray(m + 1)
    //        for (d in 0 until m) {
    //            min[d] = interval.min(d)
    //            max[d] = interval.max(d)
    //        }
    //        min[m] = minOfNewDim
    //        max[m] = maxOfNewDim
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Invert the bounds on the d-axis of the given interval
    //     *
    //     * @param interval
    //     * the source
    //     * @param d
    //     * the axis to invert
    //     */
    //    fun invertAxis(interval: Interval, d: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        val tmp = min[d]
    //        min[d] = -max[d]
    //        max[d] = -tmp
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Take a (n-1)-dimensional slice of a n-dimensional interval, dropping the
    //     * d axis.
    //     */
    //    fun hyperSlice(interval: Interval, d: Int): FinalInterval {
    //        val m: Int = interval.numDimensions()
    //        val n = m - 1
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (e in 0 until m) {
    //            if (e < d) {
    //                min[e] = interval.min(e)
    //                max[e] = interval.max(e)
    //            } else if (e > d) {
    //                min[e - 1] = interval.min(e)
    //                max[e - 1] = interval.max(e)
    //            }
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Create an interval with permuted axes. The `fromAxis` is moved to
    //     * `toAxis`, while the order of the other axes is preserved.
    //     *
    //     * If fromAxis=2 and toAxis=4, and axis order of `interval` was XYCZT,
    //     * then an interval with axis order XYZTC would be created.
    //     */
    //    fun moveAxis(interval: Interval, fromAxis: Int, toAxis: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val t: Mixed = ViewTransforms.moveAxis(n, fromAxis, toAxis)
    //        val newAxisIndices = IntArray(n)
    //        t.getComponentMapping(newAxisIndices)
    //
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (d in 0 until n) {
    //            min[newAxisIndices[d]] = interval.min(d)
    //            max[newAxisIndices[d]] = interval.max(d)
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Create an interval with permuted axes. fromAxis and toAxis are swapped.
    //     *
    //     * If fromAxis=0 and toAxis=2, this means that the X-axis of the source
    //     * interval is mapped to the Z-Axis of the permuted interval and vice versa.
    //     * For a XYZ source, a ZYX interval would be created.
    //     */
    //    fun permuteAxes(interval: Interval, fromAxis: Int, toAxis: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        val fromMinNew = min[toAxis]
    //        val fromMaxNew = max[toAxis]
    //        min[toAxis] = min[fromAxis]
    //        max[toAxis] = max[fromAxis]
    //        min[fromAxis] = fromMinNew
    //        max[fromAxis] = fromMaxNew
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Create an interval that is rotated by 90 degrees. The rotation is
    //     * specified by the fromAxis and toAxis arguments.
    //     *
    //     * If fromAxis=0 and toAxis=1, this means that the X-axis of the source
    //     * interval is mapped to the Y-Axis of the rotated interval. That is, it
    //     * corresponds to a 90 degree clock-wise rotation of the source interval in
    //     * the XY plane.
    //     *
    //     * fromAxis=1 and toAxis=0 corresponds to a counter-clock-wise rotation in
    //     * the XY plane.
    //     */
    //    fun rotate(interval: Interval, fromAxis: Int, toAxis: Int): FinalInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        interval.min(min)
    //        interval.max(max)
    //        if (fromAxis != toAxis) {
    //            val fromMinNew = -max[toAxis]
    //            val fromMaxNew = -min[toAxis]
    //            min[toAxis] = min[fromAxis]
    //            max[toAxis] = max[fromAxis]
    //            min[fromAxis] = fromMinNew
    //            max[fromAxis] = fromMaxNew
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Returns an [Interval] with the same dimensions as the given
    //     * interval, but min is all zero.
    //     */
    //    fun zeroMin(interval: Interval): FinalInterval {
    //        return FinalInterval(*dimensionsAsLongArray(interval))
    //    }
    //
    //    /**
    //     * Return an [RealInterval] that is scaled by the given factor.
    //     */
    //    fun scale(interval: RealInterval, scale: Double): RealInterval {
    //        val n: Int = interval.numDimensions()
    //        val min = minAsDoubleArray(interval)
    //        val max = maxAsDoubleArray(interval)
    //        for (i in 0 until n) {
    //            min[i] *= scale
    //            max[i] *= scale
    //        }
    //        return FinalRealInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the intersection of two intervals.
    //     *
    //     * Create a [FinalInterval] , which is the intersection of the input
    //     * intervals (i.e., the area contained in both input intervals).
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return intersection of input intervals
    //     */
    //    fun intersect(intervalA: Interval, intervalB: Interval): FinalInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        val n: Int = intervalA.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (d in 0 until n) {
    //            min[d] = max(intervalA.min(d).toDouble(), intervalB.min(d).toDouble()).toLong()
    //            max[d] = min(intervalA.max(d).toDouble(), intervalB.max(d).toDouble()).toLong()
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the intersection of two intervals.
    //     *
    //     * Create a [RealInterval] , which is the intersection of the input
    //     * intervals (i.e., the area contained in both input intervals).
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return intersection of input intervals
    //     */
    //    fun intersect(intervalA: RealInterval, intervalB: RealInterval): FinalRealInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        val n: Int = intervalA.numDimensions()
    //        val min = DoubleArray(n)
    //        val max = DoubleArray(n)
    //        for (d in 0 until n) {
    //            min[d] = max(intervalA.realMin(d), intervalB.realMin(d))
    //            max[d] = min(intervalA.realMax(d), intervalB.realMax(d))
    //        }
    //        return FinalRealInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the smallest interval that contains both input intervals.
    //     *
    //     * Create a [FinalInterval] that represents that interval.
    //     *
    //     * May produce unexpected results for empty [Interval]s.
    //     * Use [.union] if either input interval could be empty.
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return union of input intervals
    //     */
    //    fun unionUnsafe(intervalA: Interval, intervalB: Interval): FinalInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        val n: Int = intervalA.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (d in 0 until n) {
    //            min[d] = min(intervalA.min(d).toDouble(), intervalB.min(d).toDouble()).toLong()
    //            max[d] = max(intervalA.max(d).toDouble(), intervalB.max(d).toDouble()).toLong()
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the smallest interval that contains both input intervals.
    //     *
    //     * Create a [FinalInterval] that represents that interval.
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return union of input intervals
    //     */
    //    fun union(intervalA: Interval, intervalB: Interval): FinalInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        if (isEmpty(intervalA)) return FinalInterval(intervalB)
    //        else if (isEmpty(intervalB)) return FinalInterval(intervalA)
    //
    //        return unionUnsafe(intervalA, intervalB)
    //    }
    //
    //    /**
    //     * Compute the smallest interval that contains both input intervals.
    //     *
    //     * Create a [RealInterval] that represents that interval.
    //     *
    //     * May produce unexpected results for empty [RealInterval]s.
    //     * Use [.union] if either input interval could be empty.
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return union of input intervals
    //     */
    //    fun unionUnsafe(intervalA: RealInterval, intervalB: RealInterval): FinalRealInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        val n: Int = intervalA.numDimensions()
    //        val min = DoubleArray(n)
    //        val max = DoubleArray(n)
    //        for (d in 0 until n) {
    //            min[d] = min(intervalA.realMin(d), intervalB.realMin(d))
    //            max[d] = max(intervalA.realMax(d), intervalB.realMax(d))
    //        }
    //        return FinalRealInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the smallest interval that contains both input intervals.
    //     *
    //     * Create a [RealInterval] that represents that interval.
    //     *
    //     * @param intervalA
    //     * input interval
    //     * @param intervalB
    //     * input interval
    //     * @return union of input intervals
    //     */
    //    fun union(intervalA: RealInterval, intervalB: RealInterval): FinalRealInterval {
    //        assert(intervalA.numDimensions() === intervalB.numDimensions())
    //        if (isEmpty(intervalA)) return FinalRealInterval(intervalB)
    //        else if (isEmpty(intervalB)) return FinalRealInterval(intervalA)
    //
    //        return unionUnsafe(intervalA, intervalB)
    //    }
    //
    //    /**
    //     * Compute the smallest [Interval] containing the specified
    //     * [RealInterval].
    //     *
    //     * @param ri
    //     * input interval.
    //     * @return the smallest integer interval that completely contains the input
    //     * interval.
    //     */
    //    fun smallestContainingInterval(ri: RealInterval): Interval {
    //        val n: Int = ri.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (d in 0 until n) {
    //            min[d] = floor(ri.realMin(d)).toLong()
    //            max[d] = ceil(ri.realMax(d)).toLong()
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Compute the largest [Interval] that is contained in the specified
    //     * [RealInterval].
    //     *
    //     * @param ri
    //     * input interval.
    //     * @return the largest integer interval that is completely contained in the
    //     * input interval.
    //     */
    //    fun largestContainedInterval(ri: RealInterval): Interval {
    //        val n: Int = ri.numDimensions()
    //        val min = LongArray(n)
    //        val max = LongArray(n)
    //        for (d in 0 until n) {
    //            min[d] = ceil(ri.realMin(d)).toLong()
    //            max[d] = floor(ri.realMax(d)).toLong()
    //        }
    //        return FinalInterval(min, max)
    //    }
    //
    //    /**
    //     * Check whether the given interval is empty, that is, the maximum is
    //     * smaller than the minimum in some dimension.
    //     *
    //     * @param interval
    //     * interval to check
    //     * @return true when the interval is empty, that is, the maximum is smaller
    //     * than the minimum in some dimension.
    //     */
    //    fun isEmpty(interval: Interval): Boolean {
    //        val n: Int = interval.numDimensions()
    //        for (d in 0 until n) if (interval.min(d) > interval.max(d)) return true
    //        return false
    //    }
    //
    //    /**
    //     * Check whether the given interval is empty, that is, the maximum is
    //     * smaller than the minimum in some dimension.
    //     *
    //     * @param interval
    //     * interval to check
    //     * @return true when the interval is empty, that is, the maximum is smaller
    //     * than the minimum in some dimension.
    //     */
    //    fun isEmpty(interval: RealInterval): Boolean {
    //        val n: Int = interval.numDimensions()
    //        for (d in 0 until n) if (interval.realMin(d) > interval.realMax(d)) return true
    //        return false
    //    }
    //
    //    /**
    //     * Test whether the `containing` interval contains the
    //     * `contained` point. The interval is closed, that is, boundary points
    //     * are contained.
    //     *
    //     * @return true, iff `contained` is in `containing`.
    //     */
    //    fun contains(containing: Interval, contained: Localizable): Boolean {
    //        assert(containing.numDimensions() === contained.numDimensions())
    //        val n: Int = containing.numDimensions()
    //        for (d in 0 until n) {
    //            val p = contained.getLongPosition(d)
    //            if (p < containing.min(d) || p > containing.max(d)) return false
    //        }
    //        return true
    //    }
    //
    //    /**
    //     * Test whether the `containing` interval contains the
    //     * `contained` point. The interval is closed, that is, boundary points
    //     * are contained.
    //     *
    //     * @return true, iff `contained` is in `containing`.
    //     */
    //    fun contains(containing: RealInterval, contained: RealLocalizable): Boolean {
    //        assert(containing.numDimensions() === contained.numDimensions())
    //        val n: Int = containing.numDimensions()
    //        for (d in 0 until n) {
    //            val p = contained.getDoublePosition(d)
    //            if (p < containing.realMin(d) || p > containing.realMax(d)) return false
    //        }
    //        return true
    //    }
    //
    //    /**
    //     * Test whether the `containing` interval completely contains the
    //     * `contained` interval.
    //     */
    //    fun contains(containing: Interval, contained: Interval): Boolean {
    //        assert(containing.numDimensions() === contained.numDimensions())
    //        val n: Int = containing.numDimensions()
    //        for (d in 0 until n) {
    //            if (containing.min(d) > contained.min(d) || containing.max(d) < contained.max(d)) return false
    //        }
    //        return true
    //    }
    //
    //    /**
    //     * Test whether the `containing` interval completely contains the
    //     * `contained` interval.
    //     */
    //    fun contains(containing: RealInterval, contained: RealInterval): Boolean {
    //        assert(containing.numDimensions() === contained.numDimensions())
    //        val n: Int = containing.numDimensions()
    //        for (d in 0 until n) {
    //            if (containing.realMin(d) > contained.realMin(d) || containing.realMax(d) < contained.realMax(d)) return false
    //        }
    //        return true
    //    }
    //
    //    /**
    //     * Compute the number of elements contained in an (integer) [Interval]
    //     * .
    //     *
    //     * @return number of elements in `interval`.
    //     */
    //    fun numElements(interval: Dimensions): Long {
    //        var numPixels = max(interval.dimension(0).toDouble(), 0.0).toLong()
    //        val n: Int = interval.numDimensions()
    //        for (d in 1 until n) (numPixels *= max(interval.dimension(d).toDouble(), 0.0)).toLong()
    //        return numPixels
    //    }
    //
    //    /**
    //     * Compute the number of elements contained in an (integer) interval.
    //     *
    //     * @param dimensions
    //     * dimensions of the interval.
    //     * @return number of elements in the interval.
    //     */
    //    fun numElements(vararg dimensions: Int): Long {
    //        var numPixels = max(dimensions[0].toDouble(), 0.0).toLong()
    //        for (d in 1 until dimensions.size) (numPixels *= max(dimensions[d].toDouble(), 0.0)).toLong()
    //        return numPixels
    //    }
    //
    //    /**
    //     * Compute the number of elements contained in an (integer) interval.
    //     *
    //     * @param dimensions
    //     * dimensions of the interval.
    //     * @return number of elements in the interval.
    //     */
    //    fun numElements(vararg dimensions: Long): Long {
    //        var numPixels = max(dimensions[0].toDouble(), 0.0).toLong()
    //        for (d in 1 until dimensions.size) (numPixels *= max(dimensions[d].toDouble(), 0.0)).toLong()
    //        return numPixels
    //    }

    /**
     * Tests whether two intervals are equal in their min / max.
     */
    fun equals(a: Interval, b: Interval): Boolean = when {
        a.numDimensions != b.numDimensions -> false
        else -> !a.dimensions.any { a.min(it) != b.min(it) || a.max(it) != b.max(it) }
    }

    /**
     * Tests whether two [RealInterval]s are equal in their min / max.
     */
    fun equals(a: RealInterval, b: RealInterval): Boolean = when {
        a.numDimensions != b.numDimensions -> false
        else -> !a.dimensions.any { a.realMin(it) != b.realMin(it) || a.realMax(it) != b.realMax(it) }
    }

    /**
     * Tests whether two [RealInterval]s are equal in their min / max.
     * With respect to the given tolerance.
     */
    fun equals(a: RealInterval, b: RealInterval, tolerance: Double): Boolean = when {
        a.numDimensions != b.numDimensions -> false
        else -> !a.dimensions.any {
            val differenceMin = abs(a.realMin(it) - b.realMin(it))
            val differenceMax = abs(a.realMax(it) - b.realMax(it))
            differenceMin > tolerance || differenceMax > tolerance
        }
    }

    /**
     * Tests whether two [Dimensions] have the same size.
     */
    fun equalDimensions(a: Dimensions, b: Dimensions): Boolean = when {
        a.numDimensions != b.numDimensions -> false
        else -> !a.dimensions.any { a.dimension(it) != b.dimension(it) }
    }

    //    /**
    //     * Tests whether two intervals have equal dimensions (same size).
    //     */
    //    @Deprecated("") fun equalDimensions(a: Interval, b: Interval): Boolean {
    //        return equalDimensions(a as Dimensions, b as Dimensions)
    //    }
    //
    //    /**
    //     * Create a `long[]` with the dimensions of a [Dimensions].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly. See
    //     * [Dimensions.dimensions].
    //     *
    //     *
    //     *
    //     * Consider using the more convenient [Dimensions.dimensionsAsLongArray].
    //     * This method may be deprecated in a future release.
    //     *
    //     *
    //     * @param dimensions
    //     * something which has dimensions
    //     *
    //     * @return dimensions as a new `long[]`
    //     */
    //    fun dimensionsAsLongArray(dimensions: Dimensions): LongArray {
    //        val dims = LongArray(dimensions.numDimensions())
    //        dimensions.dimensions(dims)
    //        return dims
    //    }
    //
    //    /**
    //     * Create a `int[]` with the dimensions of an [Interval].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly.
    //     *
    //     *
    //     * @param dimensions
    //     * something which has dimensions
    //     *
    //     * @return dimensions as a new `int[]`
    //     */
    //    fun dimensionsAsIntArray(dimensions: Dimensions): IntArray {
    //        val n: Int = dimensions.numDimensions()
    //        val dims = IntArray(n)
    //        for (d in 0 until n) dims[d] = dimensions.dimension(d).toInt()
    //        return dims
    //    }
    //
    //    /**
    //     * Create a `long[]` with the minimum of an [Interval].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly. See [Interval.min].
    //     *
    //     *
    //     *
    //     * Consider using the more convenient [Interval.minAsLongArray].
    //     * This method may be deprecated in a future release.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return minimum as a new `long[]`
    //     */
    //    fun minAsLongArray(interval: Interval): LongArray {
    //        val min = LongArray(interval.numDimensions())
    //        interval.min(min)
    //        return min
    //    }
    //
    //    /**
    //     * Create a `int[]` with the minimum of an [Interval].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return minimum as a new `int[]`
    //     */
    //    fun minAsIntArray(interval: Interval): IntArray {
    //        val n: Int = interval.numDimensions()
    //        val min = IntArray(n)
    //        for (d in 0 until n) min[d] = interval.min(d).toInt()
    //        return min
    //    }
    //
    //    /**
    //     * Create a `long[]` with the maximum of an [Interval].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly. See [Interval.max].
    //     *
    //     *
    //     *
    //     *
    //     * Consider using the more convenient [Interval.maxAsLongArray].
    //     * This method may be deprecated in a future release.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return maximum as a new `long[]`
    //     */
    //    fun maxAsLongArray(interval: Interval): LongArray {
    //        val max = LongArray(interval.numDimensions())
    //        interval.max(max)
    //        return max
    //    }
    //
    //    /**
    //     * Create a `int[]` with the maximum of an [Interval].
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return maximum as a new `int[]`
    //     */
    //    fun maxAsIntArray(interval: Interval): IntArray {
    //        val n: Int = interval.numDimensions()
    //        val max = IntArray(n)
    //        for (d in 0 until n) max[d] = interval.max(d).toInt()
    //        return max
    //    }
    //
    //    /**
    //     * Create a `double[]` with the maximum of a [RealInterval]
    //     * .
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly. See
    //     * [RealInterval.realMax].
    //     *
    //     *
    //     *
    //     * Consider using the more convenient [RealInterval.maxAsDoubleArray].
    //     * This method may be deprecated in a future release.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return maximum as a new double[]
    //     */
    //    fun maxAsDoubleArray(interval: RealInterval): DoubleArray {
    //        val max = DoubleArray(interval.numDimensions())
    //        interval.realMax(max)
    //        return max
    //    }
    //
    //    /**
    //     * Create a `double[]` with the minimum of a [RealInterval]
    //     * .
    //     *
    //     *
    //     *
    //     * Keep in mind that creating arrays wildly is not good practice and
    //     * consider using the interval directly. See
    //     * [RealInterval.realMin].
    //     *
    //     *
    //     *
    //     * Consider using the more convenient [RealInterval.minAsDoubleArray]
    //     * This method may be deprecated in a future release.
    //     *
    //     *
    //     * @param interval
    //     * something with interval boundaries
    //     *
    //     * @return minimum as a new double[]
    //     */
    //    fun minAsDoubleArray(interval: RealInterval): DoubleArray {
    //        val min = DoubleArray(interval.numDimensions())
    //        interval.realMin(min)
    //        return min
    //    }
    //
    //    /**
    //     * Returns an image, where each pixel value is the position of the pixel
    //     * represented as [Localizable].
    //     *
    //     * @param interval
    //     * Interval of the returned image.
    //     */
    //    fun positions(interval: Interval?): RandomAccessibleInterval<Localizable> {
    //        return Localizables.randomAccessibleInterval(interval)
    //    }

    /**
     * Returns a string that contains min, max and the dimensions of the
     * [Interval].
     */
    fun toString(value: Interval): String {
        val sb = StringBuilder()

        sb.append("[(")
        val n = value.numDimensions
        for (d in 0..<n) {
            sb.append(value.min(d))
            if (d < n - 1) sb.append(", ")
        }
        sb.append(") -- (")
        for (d in 0..<n) {
            sb.append(value.max(d))
            if (d < n - 1) sb.append(", ")
        }
        sb.append(") = ")
        for (d in 0..<n) {
            sb.append(value.dimension(d))
            if (d < n - 1) sb.append('x')
        }
        sb.append(']')

        return sb.toString()
    }

    /**
     * Returns a string that contains min and max of the [RealInterval].
     */
    fun toString(value: RealInterval): String {
        val sb = StringBuilder()

        sb.append("[(")
        val n = value.numDimensions
        for (d in 0..<n) {
            sb.append(value.realMin(d))
            if (d < n - 1) sb.append(", ")
        }
        sb.append(") -- (")
        for (d in 0..<n) {
            sb.append(value.realMax(d))
            if (d < n - 1) sb.append(", ")
        }
        sb.append(")]")

        return sb.toString()
    }

    /**
     * Converts the [Dimensions] into a string.
     */
    fun toString(value: Dimensions): String = value.dimensions.joinToString("x")
}