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
 * An element that can be positioned in n-dimensional discrete space.
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch
 */
interface Positionable : EuclideanSpace {
    /**
     * Move by 1 in one dimension.
     *
     * @param d
     * dimension
     */
    fun fwd(d: Int)

    /**
     * Move by -1 in one dimension.
     *
     * @param d
     * dimension
     */
    fun bck(d: Int)

    /**
     * Move the element in one dimension for some distance.
     *
     * @param distance
     * relative offset in dimension d
     * @param d
     * dimension
     */
    fun move(distance: Int, d: Int)

    /**
     * Move the element in one dimension for some distance.
     *
     * @param distance
     * relative offset in dimension d
     * @param d
     * dimension
     */
    fun move(distance: Long, d: Int)

    /**
     * Move the element relative to its current location using an
     * [Localizable] as distance vector.
     *
     * @param distance
     * relative offset, [Localizable.numDimensions] must be
     *  [.numDimensions]
     */
    fun move(distance: Localizable)

    /**
     * Move the element relative to its current location using an int[] as
     * distance vector.
     *
     * @param distance
     * relative offset, length must be  [.numDimensions]
     */
    fun move(distance: IntArray)

    /**
     * Move the element relative to its current location using a long[] as
     * distance vector.
     *
     * @param distance
     * relative offset, length must be  [.numDimensions]
     */
    fun move(distance: LongArray)

    /**
     * Place the element at the same location as a given [Localizable]
     *
     * @param position
     * absolute position, [Localizable.numDimensions] must be
     *  [.numDimensions]
     */
    fun setPosition(position: Localizable)

    /**
     * Set the position of the element.
     *
     * @param position
     * absolute position, length must be
     * [.numDimensions]
     */
    fun setPosition(position: IntArray)

    /**
     * Set the position of the element.
     *
     * @param position
     * absolute position, length must be
     * [.numDimensions]
     */
    fun setPosition(position: LongArray)

    /**
     * Set the position of the element for one dimension.
     *
     * @param position
     * absolute position in dimension d
     * @param d
     * dimension
     */
    fun setPosition(position: Int, d: Int)

    /**
     * Set the position of the element for one dimension.
     *
     * @param position
     * absolute position in dimension d
     * @param d
     * dimension
     */
    fun setPosition(position: Long, d: Int)
}