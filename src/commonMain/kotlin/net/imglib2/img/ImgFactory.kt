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
package net.imglib2.img

import net.imglib2.Dimensions
import net.imglib2.IncompatibleTypeException
import net.imglib2.IterableInterval
import net.imglib2.RandomAccessibleInterval
import net.imglib2.Util.toLongArray
import net.imglib2.type.NativeType

/**
 * TODO
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
abstract class ImgFactory<T>(var type: T? = null) {

    // [Kotlin] vararg is known to have sub-optimal perfs, consider getting rid
    // of it and/or using the *Array version

    /**
     * Create an `Img<T>` with the specified `dimensions`.
     *
     * @return new image with the specified `dimensions`.
     */
    abstract fun create(vararg dimensions: Long): Img<T>

    /**
     * Create an `Img<T>` with the specified `dimensions`.
     *
     * @return new image with the specified `dimensions`.
     */
    fun create(dimensions: Dimensions): Img<T> {
        val size = LongArray(dimensions.numDimensions)
        dimensions.dimensions(size)

        return create(*size)
    }

    /**
     * Create an `Img<T>` with the specified `dimensions`.
     *
     *
     *
     * Note: This is not a vararg function because the underlying `int[]`
     * based methods already copies the `int[]` dimensions into a
     * disposable `long[]` anyways. This would be an unnecessary copy for
     * `int...` varargs.
     *
     *
     * @return new image with the specified `dimensions`.
     */
    fun create(dimensions: IntArray): Img<T> = create(*dimensions.toLongArray())

    /**
     * Creates the same [ImgFactory] for a different generic parameter if
     * possible.
     *
     * If the type "S" does not suit the needs of the [ImgFactory] (for
     * example implement [NativeType] in all [NativeImgFactory],
     * this method will throw an [IncompatibleTypeException].
     *
     * @param <S>
     * the new type
     * @param type
     * an instance of S
     * @return [ImgFactory] of type S
     * @throws IncompatibleTypeException
     * if type S is not compatible
    </S> */
    @Throws(IncompatibleTypeException::class)
    abstract fun <S> imgFactory(type: S): ImgFactory<S>

    /**
     * Creates the same [ImgFactory] for a different generic parameter if
     * possible.
     *
     * If the supplied type "S" does not suit the needs of the
     * [ImgFactory] (for example implement [NativeType] in all
     * [NativeImgFactory], this method will throw an
     * [IncompatibleTypeException].
     *
     * @param <S>
     * the new type
     * @param typeSupplier
     * a supplier of S
     * @return [ImgFactory] of type S
     * @throws IncompatibleTypeException
     * if type S is not compatible
    </S> */
    @Throws(IncompatibleTypeException::class)
    fun <S> imgFactory(typeSupplier: () -> S): ImgFactory<S> = imgFactory(typeSupplier())


    /*
	 * -----------------------------------------------------------------------
	 *
	 * Deprecated API.
	 *
	 * Supports backwards compatibility with ImgFactories that are constructed
	 * without a type instance or supplier.
	 *
	 * -----------------------------------------------------------------------
	 */
//    @Deprecated("")
//    constructor() {
//        type = null
//    }
//
//    @Deprecated("") abstract fun create(dim: LongArray?, type: T): Img<T>?
//
//    @Deprecated("") fun create(dim: Dimensions, type: T): Img<T> {
//        val size = LongArray(dim.numDimensions())
//        dim.dimensions(size)
//
//        cache(type)
//        return create(size, type)
//    }
//
//    @Deprecated("") fun create(dim: IntArray?, type: T): Img<T> {
//        return create(Util.int2long(dim), type)
//    }
//
//    @Deprecated("") fun create(typeSupplier: java.util.function.Supplier<T>, vararg dim: Long): Img<T> {
//        return create(dim, typeSupplier.get())
//    }
//
//    @Deprecated("") fun create(typeSupplier: java.util.function.Supplier<T>, dim: Dimensions?): Img<T> {
//        return create(dim, typeSupplier.get())
//    }
//
//    @Deprecated("") fun create(typeSupplier: java.util.function.Supplier<T>, dim: IntArray?): Img<T> {
//        return create(dim, typeSupplier.get())
//    }

    /**
     * If the cached type instance was previously null, the given
     * `type` becomes the cached instance. In this way, if the factory was
     * created using one of the deprecated typeless constructor signatures, but
     * then one of the deprecated `create` methods is called (i.e.: a
     * method which provides a type instance as an argument), the provided type
     * becomes the cached type instance so that subsequent invocations of the
     * typeless `create` methods will work as desired.
     *
     * @param type
     * The type to cache if needed.
     */
//    @Deprecated("")
//    protected fun cache(type: T) {
//        if (this.type == null) this.type = type
//    }
}

/**
 * An [Img] is a [RandomAccessibleInterval] that has its min at
 * 0<sup>*n*</sup> and its max positive. [Img]s store pixels and
 * thus are the basis for conventional image processing.
 *
 * @author Stephan Saalfeld
 * @author Stephan Preibisch
 */
interface Img<T> : RandomAccessibleInterval<T>, IterableInterval<T> {
    /**
     * Get a [ImgFactory] that creates [Img]s of the same kind as
     * this one.
     *
     * This is useful to create Imgs for temporary storage in generic methods
     * where the specific Img type is unknown. Note, that the factory can be
     * used even if all references to this Img have been invalidated.
     *
     * @return a factory for Imgs of the same kind as this one.
     */
    fun factory(): ImgFactory<T>

    /**
     * @return - A copy of the current [Img] instance, all pixels are
     * duplicated
     */
    fun copy(): Img<T>
}
