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

import net.imglib2.util.Localizables
import net.imglib2.util.Localizables.toString
import uns.f64
import java.util.Arrays.hashCode

/**
 * A point is a location in EuclideanSpace.
 *
 * @author Lee Kamentsky
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch
 */
class RealPoint : AbstractRealLocalizable, RealPositionable {
    /**
     * Protected constructor that can re-use the passed position array.
     *
     * @param position
     * array used to store the position.
     * @param copy
     * flag indicating whether position array should be duplicated.
     */
    protected constructor(position: DoubleArray, copy: Boolean) : super(if (copy) position.copyOf() else position)

    /**
     * Create a point in *nDimensional* space initialized to 0,0,...
     *
     * @param n
     * number of dimensions of the space
     */
    constructor(n: Int) : super(n)

    /**
     * Create a point at a definite location in a space of the dimensionality of
     * the position.
     *
     * @param position
     * the initial position. The length of the array determines the
     * dimensionality of the space.
     */
    constructor(vararg position: Double) : this(position, true)

    /**
     * Create a point at a definite location in a space of the dimensionality of
     * the position.
     *
     * @param position
     * the initial position. The length of the array determines the
     * dimensionality of the space.
     */
    constructor(vararg position: Float) : super(position.size) {
        setPosition(position)
    }

    /**
     * Create a point using the position and dimensionality of a
     * [RealLocalizable]
     *
     * @param localizable
     * the initial position. Its dimensionality determines the
     * dimensionality of the space.
     */
    constructor(localizable: RealLocalizable) : super(localizable.numDimensions) {
        localizable.localize(position)
    }

    override fun fwd(d: Int) {
        ++position[d]
    }

    override fun bck(d: Int) {
        --position[d]
    }

    override fun move(distance: Int, d: Int) {
        position[d] += distance.f64
    }

    override fun move(distance: Long, d: Int) {
        position[d] += distance.f64
    }

    override fun move(distance: Localizable) {
        for (d in dimensions) position[d] += distance.getDoublePosition(d)
    }

    override fun move(distance: IntArray) {
        for (d in dimensions) position[d] += distance[d].f64
    }

    override fun move(distance: LongArray) {
        for (d in dimensions) position[d] += distance[d].f64
    }

    override fun setPosition(position: Localizable) {
        for (d in dimensions) this.position[d] = position.getDoublePosition(d)
    }

    override fun setPosition(position: IntArray) {
        for (d in dimensions) this.position[d] = position[d].f64
    }

    override fun setPosition(position: LongArray) {
        for (d in dimensions) this.position[d] = position[d].f64
    }

    override fun setPosition(position: Int, d: Int) {
        this.position[d] = position.f64
    }

    override fun setPosition(position: Long, d: Int) {
        this.position[d] = position.f64
    }

    override fun move(distance: Float, d: Int) {
        position[d] += distance.toDouble()
    }

    override fun move(distance: Double, d: Int) {
        position[d] += distance
    }

    override fun move(distance: RealLocalizable) {
        for (d in dimensions) position[d] += distance.getDoublePosition(d)
    }

    override fun move(distance: FloatArray) {
        for (d in dimensions) position[d] += distance[d].toDouble()
    }

    override fun move(distance: DoubleArray) {
        for (d in dimensions) position[d] += distance[d]
    }

    override fun setPosition(position: RealLocalizable) {
        for (d in dimensions) this.position[d] = position.getDoublePosition(d)
    }

    override fun setPosition(position: FloatArray) {
        for (d in dimensions) this.position[d] = position[d].toDouble()
    }

    override fun setPosition(position: DoubleArray) {
        for (d in dimensions) this.position[d] = position[d]
    }

    override fun setPosition(position: Float, d: Int) {
        this.position[d] = position.toDouble()
    }

    override fun setPosition(position: Double, d: Int) {
        this.position[d] = position
    }

    override fun toString(): String = toString(this)

    override fun equals(other: Any?): Boolean = other is RealPoint && Localizables.equals(this, other)

    override fun hashCode(): Int = position.hashCode

    companion object {
        /**
         * Create a point that stores its coordinates in the provided position
         * array.
         *
         * @param position
         * array to use for storing the position.
         */
        fun wrap(position: DoubleArray): RealPoint = RealPoint(position, false)
    }
}