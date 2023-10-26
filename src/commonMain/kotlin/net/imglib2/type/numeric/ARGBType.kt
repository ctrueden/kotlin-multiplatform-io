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

import net.imglib2.img.NativeImg
import net.imglib2.img.basictypeaccess.IntAccess
import net.imglib2.type.AbstractNativeType
import net.imglib2.type.NativeType
//import net.imglib2.type.NativeTypeFactory
//import net.imglib2.util.Fraction
//import net.imglib2.util.Util
import kotlin.jvm.JvmOverloads

/**
 * A [native][NativeType] [NumericType] that encodes four channels at
 * unsigned byte precision into one 32bit signed integer which is the format
 * used in most display oriented image processing libraries such as AWT or
 * ImageJ. [ARGBType] implements [NumericType] as element-wise
 * vector algebra.
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
class ARGBType/* : AbstractNativeType<ARGBType>, NumericType<ARGBType>*/ {
//    protected val img: NativeImg<*, out IntAccess>
//
//    // the DataAccess that holds the information
//    protected var dataAccess: IntAccess? = null
//
//    // this is the constructor if you want it to read from an array
//    constructor(intStorage: NativeImg<*, out IntAccess>) {
//        img = intStorage
//    }
//
//    // this is the constructor if you want it to be a variable
//    // this is the constructor if you want it to be a variable
//    @JvmOverloads
//    constructor(value: Int = 0) {
//        img = null
//        dataAccess = IntArray(1)
//        set(value)
//    }
//
//    // this is the constructor if you want to specify the dataAccess
//    constructor(access: IntAccess?) {
//        img = null
//        dataAccess = access
//    }
//
//    fun updateContainer(c: Any?) {
//        dataAccess = img.update(c)
//    }
//
//    fun duplicateTypeOnSameNativeImg(): ARGBType {
//        return ARGBType(img)
//    }
//
//    val nativeTypeFactory: NativeTypeFactory<ARGBType, IntAccess>
//        get() = typeFactory
//
//    fun get(): Int {
//        return dataAccess.getValue(i.get())
//    }
//
//    fun set(f: Int) {
//        dataAccess.setValue(i.get(), f)
//    }
//
//    override fun mul(c: Float) {
//        val value = get()
//        set(rgba(red(value) * c, green(value) * c, blue(value) * c, alpha(value) * c))
//    }
//
//    override fun mul(c: Double) {
//        val value = get()
//        set(rgba(red(value) * c, green(value) * c, blue(value) * c, alpha(value) * c))
//    }
//
//    override fun add(c: ARGBType) {
//        val value1 = get()
//        val value2 = c.get()
//        set(rgba(red(value1) + red(value2), green(value1) + green(value2), blue(value1) + blue(value2), alpha(value1) + alpha(value2)))
//    }
//
//    override operator fun div(c: ARGBType) {
//        val value1 = get()
//        val value2 = c.get()
//        set(rgba(red(value1) / red(value2), green(value1) / green(value2), blue(value1) / blue(value2), alpha(value1) / alpha(value2)))
//    }
//
//    override fun mul(c: ARGBType) {
//        val value1 = get()
//        val value2 = c.get()
//        set(rgba(red(value1) * red(value2), green(value1) * green(value2), blue(value1) * blue(value2), alpha(value1) * alpha(value2)))
//    }
//
//    override fun sub(c: ARGBType) {
//        val value1 = get()
//        val value2 = c.get()
//        set(rgba(red(value1) - red(value2), green(value1) - green(value2), blue(value1) - blue(value2), alpha(value1) - alpha(value2)))
//    }
//
//    override fun pow(c: ARGBType) {
//        val value1 = get()
//        val value2 = c.get()
//        set(
//            rgba(red(value1).pow(red(value2).toDouble()), green(value1).pow(green(value2).toDouble()), blue(value1).pow(blue(value2).toDouble()), alpha(value1).pow(alpha(value2).toDouble())))
//    }
//
//    override fun pow(power: Double) {
//        val value1 = get()
//        set(
//            rgba(red(value1).pow(power), green(value1).pow(power), blue(value1).pow(power), alpha(value1).pow(power)))
//    }
//
//    override fun set(c: ARGBType) {
//        set(c.get())
//    }
//
//    override fun setOne() {
//        set(rgba(1, 1, 1, 1))
//    }
//
//    override fun setZero() {
//        set(0)
//    }
//
//    override fun createVariable(): ARGBType {
//        return ARGBType(0)
//    }
//
//    override fun copy(): ARGBType {
//        return ARGBType(get())
//    }
//
//    override fun toString(): String {
//        val rgba = get()
//        return "(r=" + red(rgba) + ",g=" + green(rgba) + ",b=" + blue(rgba) + ",a=" + alpha(rgba) + ")"
//    }
//
//    val entitiesPerPixel: Fraction
//        get() = Fraction()
//
//    override fun valueEquals(t: ARGBType): Boolean {
//        return get() == t.get()
//    }
//
//    override fun equals(obj: Any?): Boolean {
//        return Util.valueEqualsObject(this, obj)
//    }
//
//    override fun hashCode(): Int {
//        return java.lang.Integer.hashCode(get())
//    }
//
//    companion object {
//        private val typeFactory: NativeTypeFactory<ARGBType, IntAccess> = NativeTypeFactory.INT { ARGBType() }
//        fun rgba(r: Int, g: Int, b: Int, a: Int): Int {
//            return r and 0xff shl 16 or (g and 0xff shl 8) or (b and 0xff) or (a and 0xff shl 24)
//        }
//
//        fun rgba(r: Float, g: Float, b: Float, a: Float): Int {
//            return rgba(Util.round(r), Util.round(g), Util.round(b), Util.round(a))
//        }
//
//        fun rgba(r: Double, g: Double, b: Double, a: Double): Int {
//            return rgba(Util.round(r) as Int, Util.round(g) as Int, Util.round(b) as Int, Util.round(a) as Int)
//        }
//
//        fun red(value: Int): Int {
//            return value shr 16 and 0xff
//        }
//
//        fun green(value: Int): Int {
//            return value shr 8 and 0xff
//        }
//
//        fun blue(value: Int): Int {
//            return value and 0xff
//        }
//
//        fun alpha(value: Int): Int {
//            return value shr 24 and 0xff
//        }
//    }
}
