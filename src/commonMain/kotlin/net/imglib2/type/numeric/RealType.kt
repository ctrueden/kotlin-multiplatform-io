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
package net.imglib2.type.numeric

import net.imglib2.type.Type

/**
 * TODO
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
interface RealType<T : RealType<T>> : ComplexType<T>, Comparable<T> {
    fun inc()

    fun dec()

    val maxValue: Double

    val minValue: Double

    val minIncrement: Double

    val bitsPerPixel: Int
}

/**
 * TODO
 *
 */
interface ComplexType<T : ComplexType<T>> : NumericType<T> {
    val realDouble: Double

    val realFloat: Float

    val imaginaryDouble: Double

    val imaginaryFloat: Float

    fun setReal(f: Float)

    fun setReal(f: Double)

    fun setImaginary(f: Float)

    fun setImaginary(f: Double)

    fun setComplexNumber(r: Float, i: Float)

    fun setComplexNumber(r: Double, i: Double)

    val powerFloat: Float

    val powerDouble: Double

    val phaseFloat: Float

    val phaseDouble: Double

    fun complexConjugate()
}


// TODO [Kotlin] operators
/** TODO */
interface NumericType<T : NumericType<T>> : Type<T>, Add<T>, Mul<T>, Sub<T>, Div<T>, Pow<T>, SetOne, SetZero, MulFloatingPoint, PowFloatingPoint

/**
 * @author Stephan Preibisch
 *
 * @param <T>
</T> */
interface Add<T> {
    infix fun add(c: T)
}

/**
 * @author Stephan Preibisch
 *
 * @param <T>
</T> */
interface Mul<T> {
    infix fun mul(c: T)
}

/**
 * @author Stephan Preibisch
 *
 * @param <T>
</T> */
interface Sub<T> {
    infix fun sub(c: T)
}

/**
 * @author Stephan Preibisch
 *
 * @param <T>
</T> */
interface Div<T> {
    infix fun div(c: T)
}

/**
 * @author Albert Cardona
 *
 * @param <T>
</T> */
interface Pow<T> {
    infix fun pow(c: T)
}

/**
 * @author Stephan Preibisch
 */
interface SetOne {
    fun setOne()
}

/**
 * @author Stephan Preibisch
 */
interface SetZero {
    fun setZero()
}

/**
 * @author Stephan Preibisch
 */
interface MulFloatingPoint {
    infix fun mul(c: Float)
    infix fun mul(c: Double)
}

/**
 * @author Albert Cardona
 */
interface PowFloatingPoint {
    infix fun pow(d: Double)
}
