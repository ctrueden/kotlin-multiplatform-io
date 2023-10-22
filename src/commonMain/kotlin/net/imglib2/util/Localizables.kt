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
//import net.imglib2.view.Views
import kotlin.math.abs

object Localizables {
    //    @Deprecated(" Use {@link Localizable#positionAsLongArray}.") fun asLongArray(localizable: Localizable): LongArray {
    //        val result = LongArray(localizable.numDimensions())
    //        localizable.localize(result)
    //        return result
    //    }
    //
    //    /**
    //     * Create an n-dimensional [RandomAccessible] whose value domain is
    //     * its source domain.
    //     *
    //     * @param n
    //     * @return
    //     */
    //    fun randomAccessible(n: Int): RandomAccessible<Localizable> {
    //        return LocationRandomAccessible(n)
    //    }
    //
    //    fun randomAccessibleInterval(interval: Interval): RandomAccessibleInterval<Localizable> {
    //        return Views.interval(randomAccessible(interval.numDimensions()), interval)
    //    }
    //
    //    /**
    //     * Create an n-dimensional [RealRandomAccessible] whose value domain is
    //     * its source domain.
    //     *
    //     * @param n
    //     * @return
    //     */
    //    fun realRandomAccessible(n: Int): RealRandomAccessible<RealLocalizable> {
    //        return RealLocationRealRandomAccessible(n)
    //    }

    /** Return true if both [Localizable] refer to the same position. */
    fun equals(a: Localizable, b: Localizable): Boolean {
        val n: Int = a.numDimensions
        if (n != b.numDimensions) return false
        for (d in 0..<n) if (a.getLongPosition(d) != b.getLongPosition(d)) return false
        return true
    }

    /**
     * Return true if the two [RealLocalizable]s refer to the same
     * position.
     */
    fun equals(a: RealLocalizable, b: RealLocalizable): Boolean {
        val n: Int = a.numDimensions
        if (n != b.numDimensions) return false
        for (d in 0..<n) if (a.getDoublePosition(d) != b.getDoublePosition(d)) return false
        return true
    }

    /**
     * Return true if the two [RealLocalizable]s refer to the same
     * position up to a given tolerance.
     */
    fun equals(a: RealLocalizable, b: RealLocalizable, tolerance: Double): Boolean {
        val n: Int = a.numDimensions
        if (n != b.numDimensions) return false
        for (d in 0..<n) if (abs(a.getDoublePosition(d) - b.getDoublePosition(d)) > tolerance) return false

        return true
    }

    /** Return the current position as string. */
    fun toString(value: Localizable): String {
        val sb = StringBuilder()
        var c = '('
        for (i in 0..<value.numDimensions) {
            sb.append(c)
            sb.append(value.getLongPosition(i))
            c = ','
        }
        sb.append(')')
        return sb.toString()
    }

    /**
     * Return the current position as string.
     */
    fun toString(value: RealLocalizable): String {
        val sb = StringBuilder()
        var c = '('
        for (i in 0..<value.numDimensions) {
            sb.append(c)
            sb.append(value.getDoublePosition(i))
            c = ','
        }
        sb.append(')')
        return sb.toString()
    }

    //    // -- Helper classes --
    //    private class LocationRandomAccessible(n: Int) : AbstractEuclideanSpace(n), RandomAccessible<Localizable?> {
    //        fun randomAccess(): RandomAccess<Localizable> {
    //            return LocationRandomAccess(n)
    //        }
    //
    //        fun randomAccess(interval: Interval?): RandomAccess<Localizable> {
    //            return randomAccess()
    //        }
    //    }
    //
    //    /**
    //     * A RandomAccess that returns its current position as value.
    //     */
    //    private class LocationRandomAccess : Point, RandomAccess<Localizable?> {
    //        constructor(n: Int) : super(n)
    //
    //        constructor(initialPosition: Localizable?) : super(initialPosition!!)
    //
    //        fun get(): Localizable {
    //            return this
    //        }
    //
    //        fun copy(): RandomAccess<Localizable> {
    //            return LocationRandomAccess(this)
    //        }
    //    }
    //
    //    private class RealLocationRealRandomAccessible(n: Int) : AbstractEuclideanSpace(n), RealRandomAccessible<RealLocalizable?> {
    //        fun realRandomAccess(): RealLocationRealRandomAccess {
    //            return RealLocationRealRandomAccess(n)
    //        }
    //
    //        fun realRandomAccess(interval: RealInterval?): RealLocationRealRandomAccess {
    //            return realRandomAccess()
    //        }
    //    }
    //
    //    /**
    //     * A RandomAccess that returns its current position as value.
    //     */
    //    private class RealLocationRealRandomAccess : RealPoint, RealRandomAccess<RealLocalizable?> {
    //        constructor(n: Int) : super(n)
    //
    //        constructor(initialPosition: RealLocalizable?) : super(initialPosition)
    //
    //        fun get(): RealLocationRealRandomAccess {
    //            return this
    //        }
    //
    //        fun copy(): RealLocationRealRandomAccess {
    //            return RealLocationRealRandomAccess(this)
    //        }
    //    }
}