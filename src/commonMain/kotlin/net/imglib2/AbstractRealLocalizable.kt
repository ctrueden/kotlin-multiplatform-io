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
 * An abstract class that implements the [RealLocalizable] interface using
 * an array of doubles to maintain position
 *
 * @author Lee Kamentsky
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch
 */
abstract class AbstractRealLocalizable : AbstractEuclideanSpace, RealLocalizable {
    /**
     * The [RealLocalizable] interface is implemented using the position
     * stored here. [RealPositionable] subclasses, such as
     * [RealPoint], modify this array.
     */
    protected val position: DoubleArray

    /**
     * @param n
     * number of dimensions.
     */
    protected constructor(n: Int) : super(n) {
        position = DoubleArray(n)
    }

    /**
     * Protected constructor that re-uses the passed position array. This is
     * intended to allow subclasses to provide a way to wrap a double[] array.
     *
     * @param position
     * position array to use.
     */
    protected constructor(position: DoubleArray) : super(position.size) {
        this.position = position
    }

    override fun localize(position: FloatArray) {
        for (d in dimensions) position[d] = this.position[d].toFloat()
    }

    override fun localize(position: DoubleArray) {
        for (d in dimensions) position[d] = this.position[d]
    }

    override fun getFloatPosition(d: Int): Float = position[d].toFloat()

    override fun getDoublePosition(d: Int): Double = position[d]
}