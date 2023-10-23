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

//import net.imglib2.view.Views

/**
 *
 *
 * *f:Z<sup>n</sup>T*
 *
 *
 *
 *
 * A function over integer space that can create a random access [Sampler].
 *
 *
 *
 *
 * If your algorithm takes a RandomAccessible, this usually means that you
 * expect that the domain is infinite. (In contrast to this,
 * [RandomAccessibleInterval]s have a finite domain.)
 *
 *
 *
 * A `RandomAccessible` might be defined only partially. You should be
 * aware of this especially when creating [RandomAccessibleInterval]s.
 * While it is straightforward to turn a `RandomAccessible` into a
 * `RandomAccessibleInterval` by adding interval boundaries via
 * [Views.interval], it is your responsibility to ensure that the
 * `RandomAccessible` is fully defined within these boundaries.
 *
 *
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch
 */
interface RandomAccessible<T> : EuclideanSpace {
    /**
     * Create a random access sampler for integer coordinates.
     *
     *
     *
     * The returned random access covers as much of the domain as possible.
     *
     *
     * *
     * Please note: [RandomAccessibleInterval]s have a finite domain (their [Interval]),
     * so [.randomAccess] is only guaranteed to cover this finite domain.
     * *  This may lead to unexpected results when using [Views]. In
     * the following code
     *
     * <pre>
     * RandomAccessible&lt;T&gt; extended = Views.extendBorder( img )
     * RandomAccessibleInterval&lt;T&gt; cropped = Views.interval( extended, img );
     * RandomAccess&lt;T&gt; a1 = extended.randomAccess();
     * RandomAccess&lt;T&gt; a2 = cropped.randomAccess();
    </pre> *
     *
     * The [access][RandomAccess] `a1` on the extended image is valid
     * everywhere. However, somewhat counter-intuitively, the
     * [access][RandomAccess] `a2` on the extended and cropped image
     * is only valid on the interval `img` to which the extended image was
     * cropped. The access is only required to cover this interval, because it
     * is the domain of the cropped image. [Views] attempts to provide the
     * fastest possible access that meets this requirement, and will therefore
     * strip the extension.
     *
     * To deal with this, if you know that you need to access pixels outside the
     * domain of the [RandomAccessibleInterval], and you know that the
     * [RandomAccessibleInterval] is actually defined beyond its interval
     * boundaries, then use the [.randomAccess] variant and
     * specify which interval you actually want to access. In the above example,
     *
     * <pre>
     * RandomAccess&lt;T&gt; a2 = cropped.randomAccess( Intervals.expand( img, 10 ) );
    </pre> *
     *
     * will provide the extended access as expected.
     *
     * @return random access sampler
     */
    fun randomAccess(): RandomAccess<T>

    /**
     * Create a random access sampler for integer coordinates.
     *
     *
     *
     * The returned random access is intended to be used in the specified
     * interval only. Thus, the RandomAccessible may provide optimized versions.
     * If the interval is completely contained in the domain, the random access
     * is guaranteed to provide the same values as that obtained by
     * [.randomAccess] within the interval.
     *
     *
     * @param interval
     * in which interval you intend to use the random access.
     *
     * @return random access sampler
     */
    fun randomAccess(interval: Interval): RandomAccess<T>

    /**
     * Convenience method to query a [RandomAccessible] for the value at a
     * position.
     *
     *
     * WARNING: This method is VERY SLOW, and memory inefficient when used in tight
     * loops, or called many times!!! Use [.randomAccess] when efficiency
     * is important.
     *
     *
     * This method is a short cut for `randomAccess().setPositionAndGet( position );`
     *
     * @param position, length must be  [.numDimensions]
     * @return value of the the [RandomAccessible] at `position`.
     */
    fun getAt(vararg position: Long): T = randomAccess().setPositionAndGet(*position)

    /**
     * Convenience method to query a [RandomAccessible] for the value at a
     * position.
     *
     *
     * WARNING: This method is VERY SLOW, and memory inefficient when used in tight
     * loops, or called many times!!! Use [.randomAccess] when efficiency
     * is important.
     *
     *
     * This method is a short cut for `randomAccess().setPositionAndGet( position );`
     *
     * @param position, length must be  [.numDimensions]
     * @return value of the the [RandomAccessible] at `position`.
     */
    fun getAt(vararg position: Int): T = randomAccess().setPositionAndGet(*position)

    /**
     * Convenience method to query a [RandomAccessible] for the value at a
     * position.
     *
     *
     * WARNING: This method is VERY SLOW, and memory inefficient when used in tight
     * loops, or called many times!!! Use [.randomAccess] when efficiency
     * is important.
     *
     *
     * This method is a short cut for `randomAccess().setPositionAndGet( position );`
     *
     * @param position, [Localizable.numDimensions] must be  [.numDimensions]
     * @return value of the the [RandomAccessible] at `position`.
     */
    fun getAt(position: Localizable): T = randomAccess().setPositionAndGet(position)
}