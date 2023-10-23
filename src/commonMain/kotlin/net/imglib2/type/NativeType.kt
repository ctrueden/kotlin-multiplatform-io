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
package net.imglib2.type

//import net.imglib2.Cursor
//import net.imglib2.img.NativeImg
//import net.imglib2.img.array.ArrayImg
//import net.imglib2.img.cell.CellCursor
//import net.imglib2.img.cell.CellImg
//import net.imglib2.util.Fraction

/**
 * A [NativeType] is a [Type] that that provides access to data
 * stored in Java primitive arrays. To this end, implementations maintain a
 * reference to the current storage array and the index of an element in that
 * array.
 *
 * The [NativeType] is positioned on the correct storage array and index
 * by accessors ([Cursors][Cursor] and [RandomAccesses][RandomAccess]
 * ).
 *
 *
 *
 * The [NativeType] is the only class that is aware of the actual data
 * type, i.e., which Java primitive type is used to store the data. On the other
 * hand it does not know the storage layout, i.e., how n-dimensional pixel
 * coordinates map to indices in the current array. It also doesn't know whether
 * and how the data is split into multiple chunks. This is determined by the
 * container implementation (e.g., [ArrayImg], [CellImg], ...).
 * Separating the storage layout from access and operations on the [Type]
 * avoids re-implementation for each container type.
 *
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch
 */
interface NativeType<T : NativeType<T>> : Type<T> {
    /**
     * Get the number of entities in the storage array required to store one
     * pixel value. A pixel value may be spread over several or less than one
     * entity. For example, a complex number may require 2 entries of a float[]
     * array to store one pixel. Or a 12-bit type might need 12/64th entries of
     * a long[] array.
     *
     * @return the number of storage type entities required to store one pixel
     * value.
     */
//    val entitiesPerPixel: Fraction?

    /**
     * Creates a new [NativeType] which stores in the same physical array.
     * This is only used internally.
     *
     * @return a new [NativeType] instance working on the same
     * [NativeImg]
     */
    fun duplicateTypeOnSameNativeImg(): T

//    val nativeTypeFactory: NativeTypeFactory<T, *>?

    /**
     * This method is used by an accessor (e.g., a [Cursor]) to request an
     * update of the current data array.
     *
     * As an example consider a [CellCursor] moving on a [CellImg].
     * The cursor maintains a [NativeType] which provides access to the
     * image data. When the cursor moves from one cell to the next, the
     * underlying data array of the [NativeType] must be switched to the
     * data array of the new cell.
     *
     * To achieve this, the [CellCursor] calls `updateContainer()`
     * with itself as the argument. `updateContainer()` in turn will call
     * [NativeImg.update] on it's container, passing along the
     * reference to the cursor. In this example, the container would be a
     * [CellImg]. While the [NativeType] does not know about the
     * type of the cursor, the container does. [CellImg] knows that it is
     * passed a [CellCursor] instance, which can be used to figure out the
     * current cell and the underlying data array, which is then returned to the
     * [NativeType].
     *
     * The idea behind this concept is maybe not obvious. The [NativeType]
     * knows which basic type is used (float, int, byte, ...). However, it does
     * not know how the data is stored ([ArrayImg], [CellImg], ...).
     * This prevents the need for multiple implementations of [NativeType]
     *
     * @param c
     * reference to an accessor which can be passed on to the
     * container (which will know what to do with it).
     */
    fun updateContainer(c: Any)

    /**
     * Get the (modifiable) index into the current data array. The returned
     * instance will always be the same for the same Type.
     */
//    fun index(): Index

    /**
     * Set the index into the current data array.
     */
//    @Deprecated("""Use {@code index().set(int)} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""")
//    fun updateIndex(i: Int) {
//        index().set(i)
//    }
//
//    @get:Deprecated("""Use {@code index().get()} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""")
//    val index: Int
//        /**
//         * Get the current index into the current data array.
//         */
//        get() = index().get()
//
//    /**
//     * Increment the index into the current data array.
//     */
//    @Deprecated("""Use {@code index().inc()} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""") fun incIndex() {
//        index().inc()
//    }
//
//    /**
//     * Increases the index into the current data array by `increment`
//     * steps.
//     */
//    @Deprecated("""Use {@code index().inc(int)} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""") fun incIndex(increment: Int) {
//        index().inc(increment)
//    }
//
//    /**
//     * Decrement the index into the current data array.
//     */
//    @Deprecated("""Use {@code index().dec()} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""") fun decIndex() {
//        index().dec()
//    }
//
//    /**
//     * Decrease the index into the current data array by `decrement`
//     * steps.
//     */
//    @Deprecated("""Use {@code index().dec(int)} instead. If possible, request the
//	  {@code index()} object only once and re-use it.""") fun decIndex(decrement: Int) {
//        index().dec(decrement)
//    }
}