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
 * An element that can be positioned in n-dimensional real space.
 *
 * @author Tobias Pietzsch
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
interface RealPositionable : Positionable {
    /**
     * Move the element in one dimension for some distance.
     *
     * @param distance
     * @param d
     */
    fun move(distance: Float, d: Int)

    /**
     * Move the element in one dimension for some distance.
     *
     * @param distance
     * @param d
     */
    fun move(distance: Double, d: Int)

    /**
     * Move the element relative to its current location using a
     * [RealLocalizable] as distance vector.
     *
     * @param distance
     * relative offset, [RealLocalizable.numDimensions] must
     * be  [.numDimensions]
     */
    fun move(distance: RealLocalizable)

    /**
     * Move the element relative to its current location using a float[] as
     * distance vector.
     *
     * @param distance,
     * length must be  [.numDimensions]
     */
    fun move(distance: FloatArray)

    /**
     * Move the element relative to its current location using a float[] as
     * distance vector.
     *
     * @param distance,
     * length must be  [.numDimensions]
     */
    fun move(distance: DoubleArray)

    /**
     * Place the element at the same location as a given [RealLocalizable]
     *
     * @param position
     * absolute position, [RealLocalizable.numDimensions]
     * must be  [.numDimensions]
     */
    fun setPosition(position: RealLocalizable)

    /**
     * Set the position of the element.
     *
     * @param position
     * absolute position, length must be
     * [.numDimensions]
     */
    fun setPosition(position: FloatArray)

    /**
     * Set the position of the element.
     *
     * @param position
     * absolute position, length must be
     * [.numDimensions]
     */
    fun setPosition(position: DoubleArray)

    /**
     * Set the position of the element for one dimension.
     *
     * @param position
     * @param d
     */
    fun setPosition(position: Float, d: Int)

    /**
     * Set the position of the element for one dimension.
     *
     * @param position
     * @param d
     */
    fun setPosition(position: Double, d: Int)
}