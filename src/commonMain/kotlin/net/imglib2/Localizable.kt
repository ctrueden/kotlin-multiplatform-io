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

import uns.f
import uns.f64
import uns.i

/**
 * The [Localizable] interface can localize itself in an n-dimensional
 * discrete space. Not only [Cursor]s can use this interface, it might be
 * used by much more classes as [RandomAccess]s can take any
 * [Localizable] as input for where they should move to.
 *
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
interface Localizable : RealLocalizable {
    /**
     * Write the current position into the passed array.
     *
     * @param position
     * receives current position, length must be  [.numDimensions]
     */
    fun localize(position: IntArray) {
        for (d in dimensions) position[d] = getIntPosition(d)
    }

    /**
     * Write the current position into the passed array.
     *
     * @param position
     * receives current position, length must be  [.numDimensions]
     */
    fun localize(position: LongArray) {
        for (d in dimensions) position[d] = getLongPosition(d)
    }

    /**
     * Write the current position into the passed [Positionable].
     *
     * Note for developers: This default implementation forwards to
     * [Positionable.setPosition], so don't do the same
     * there.
     *
     * @param position
     * receives current position,
     * [Positionable.numDimensions] must be
     * [.numDimensions]
     */
    fun localize(position: Positionable) {
        if (position.numDimensions == numDimensions) position.setPosition(this)
        else for (d in dimensions) position.setPosition(getLongPosition(d), d)
    }

    /**
     * Return the current position in a given dimension.
     *
     * @param d
     * dimension
     * @return dimension of current position
     */
    fun getIntPosition(d: Int): Int = getLongPosition(d).i

    /**
     * Allocate and return a long array containing the localizable's position.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.localize].
     *
     * @return the position
     */
    fun positionAsLongArray(): LongArray {
        val out = LongArray(numDimensions)
        localize(out)
        return out
    }

    /**
     * Allocate and return a [Point] containing the localizable's
     * position.
     *
     * Please note that his method allocates a new [Point] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [Point] first and reuse it with [.localize].
     *
     * @return the position
     */
    fun positionAsPoint(): Point = Point(this)

    /**
     * Return the current position in a given dimension.
     *
     * @param d
     * dimension
     * @return dimension of current position
     */
    fun getLongPosition(d: Int): Long

    override fun getFloatPosition(d: Int): Float = getLongPosition(d).f

    override fun getDoublePosition(d: Int): Double = getLongPosition(d).f64
}