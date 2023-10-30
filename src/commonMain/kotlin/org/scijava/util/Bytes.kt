/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2023 SciJava developers.
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
// This class was derived from the loci.common.DataTools class of the
// Bio-Formats library, licensed according to Simplified BSD, as follows:
//
// Copyright (C) 2005 - 2015 Open Microscopy Environment:
//   - Board of Regents of the University of Wisconsin-Madison
//   - Glencoe Software, Inc.
//   - University of Dundee
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
@file:OptIn(ExperimentalUnsignedTypes::class)

package org.scijava.util

import uns.i
import uns.i16
import uns.ui
import uns.us

/**
 * Useful methods for reading, writing, decoding and converting `byte`s
 * and `byte` arrays.
 *
 * @author Curtis Rueden
 * @author Melissa Linkert
 * @author Chris Allan
 */
object Bytes {
    // -- Word decoding - bytes to primitive types --
    /**
     * Translates up to the first `len` bytes of a `byte` array beyond
     * the given offset to a `short`. If there are fewer than `len`
     * bytes available, the MSBs are all assumed to be zero (regardless of
     * endianness).
     */
    fun ByteArray.toShort(off: Int, len: Int, little: Boolean): Short {
        var len = len
        if (size - off < len) len = size - off
        var total: Short = 0
        var i = 0
        var ndx = off
        while (i < len) {
            val a = if (this[ndx] < 0) 256 + this[ndx] else this[ndx].i
            val b = if (little) i else len - i - 1
            total = (total.i or (a shl (b * 8))).i16
            i++
            ndx++
        }
        return total
    }

    /**
     * Translates up to the first 2 bytes of a `byte` array beyond the given
     * offset to a `short`. If there are fewer than 2 bytes available the
     * MSBs are all assumed to be zero (regardless of endianness).
     */
    fun ByteArray.toShort(off: Int, little: Boolean): Short = toShort(off, 2, little)

    /**
     * Translates up to the first 2 bytes of a `byte` array to a
     * `short`. If there are fewer than 2 bytes available, the MSBs are all
     * assumed to be zero (regardless of endianness).
     */
    fun ByteArray.toShort(little: Boolean): Short = toShort(0, 2, little)

    /**
     * Translates up to the first `len` bytes of a `byte` array beyond
     * the given offset to a `short`. If there are fewer than `len`
     * bytes available, the MSBs are all assumed to be zero (regardless of
     * endianness).
     */
    fun UByteArray.toUShort(off: Int, len: Int, little: Boolean): UShort {
        var len = len
        if (size - off < len) len = size - off
        var total: UShort = 0u
        var i = 0
        var ndx = off
        while (i < len) {
            val a = if (this[ndx] < 0u) 256u + this[ndx] else this[ndx].ui
            val b = if (little) i else len - i - 1
            total = (total.ui or (a shl (b * 8))).us
            i++
            ndx++
        }
        return total
    }

    /**
     * Translates up to the first 2 bytes of a `byte` array beyond the given
     * offset to a `short`. If there are fewer than 2 bytes available the
     * MSBs are all assumed to be zero (regardless of endianness).
     */
    fun UByteArray.toShort(off: Int, little: Boolean): UShort = toUShort(off, 2, little)

    /**
     * Translates up to the first 2 bytes of a `byte` array to a
     * `short`. If there are fewer than 2 bytes available, the MSBs are all
     * assumed to be zero (regardless of endianness).
     */
    fun UByteArray.toShort(little: Boolean): UShort = toUShort(0, 2, little)

    /**
     * Translates up to the first `len` bytes of a `byte` array beyond
     * the given offset to a `short`. If there are fewer than `len`
     * bytes available, the MSBs are all assumed to be zero (regardless of
     * endianness).
     */
//    fun toShort(bytes: ShortArray, off: Int, len: Int, little: Boolean): Short {
//        var len = len
//        if (bytes.size - off < len) len = bytes.size - off
//        var total: Short = 0
//        var i = 0
//        var ndx = off
//        while (i < len) {
//            total = (total.toInt() or (bytes[ndx].toInt() shl ((if (little) i else len - i - 1) * 8))).toShort()
//            i++
//            ndx++
//        }
//        return total
//    }
//
//    /**
//     * Translates up to the first 2 bytes of a `byte` array beyond the given
//     * offset to a `short`. If there are fewer than 2 bytes available, the
//     * MSBs are all assumed to be zero (regardless of endianness).
//     */
//    fun toShort(bytes: ShortArray, off: Int,
//                little: Boolean): Short {
//        return toShort(bytes, off, 2, little)
//    }
//
//    /**
//     * Translates up to the first 2 bytes of a `byte` array to a
//     * `short`. If there are fewer than 2 bytes available, the MSBs are all
//     * assumed to be zero (regardless of endianness).
//     */
//    fun toShort(bytes: ShortArray, little: Boolean): Short {
//        return toShort(bytes, 0, 2, little)
//    }

    /**
     * Translates up to the first `len` bytes of a `byte` array beyond
     * the given offset to an `int`. If there are fewer than `len`
     * bytes available, the MSBs are all assumed to be zero (regardless of
     * endianness).
     */
    fun ByteArray.toInt(off: Int, len: Int, little: Boolean): Int {
        var len = len
        if (size - off < len) len = size - off
        var total = 0
        var i = 0
        var ndx = off
        while (i < len) {
            val a = if (this[ndx] < 0) 256 + this[ndx] else this[ndx].i
            val b = if (little) i else len - i - 1
            total = total or (a shl (b * 8))
            i++
            ndx++
        }
        return total
    }

    /**
     * Translates up to the first 4 bytes of a `byte` array beyond the given
     * offset to an `int`. If there are fewer than 4 bytes available, the
     * MSBs are all assumed to be zero (regardless of endianness).
     */
    fun ByteArray.toInt(off: Int, little: Boolean): Int = toInt(off, 4, little)

    /**
     * Translates up to the first 4 bytes of a `byte` array to an
     * `int`. If there are fewer than 4 bytes available, the MSBs are all
     * assumed to be zero (regardless of endianness).
     */
    fun ByteArray.toInt(little: Boolean): Int = toInt(0, 4, little)

    /**
     * Translates up to the first `len` bytes of a `byte` array beyond
     * the given offset to an `int`. If there are fewer than `len`
     * bytes available, the MSBs are all assumed to be zero (regardless of
     * endianness).
     */
    fun UByteArray.toUInt(off: Int, len: Int, little: Boolean): UInt {
        var len = len
        if (size - off < len) len = size - off
        var total = 0u
        var i = 0
        var ndx = off
        while (i < len) {
            val a = if (this[ndx] < 0u) 256u + this[ndx] else this[ndx].ui
            val b = if (little) i else len - i - 1
            total = total or (a shl (b * 8))
            i++
            ndx++
        }
        return total
    }

    /**
     * Translates up to the first 4 bytes of a `byte` array beyond the given
     * offset to an `int`. If there are fewer than 4 bytes available, the
     * MSBs are all assumed to be zero (regardless of endianness).
     */
    fun UByteArray.toUInt(off: Int, little: Boolean): UInt = toUInt(off, 4, little)

    /**
     * Translates up to the first 4 bytes of a `byte` array to an
     * `int`. If there are fewer than 4 bytes available, the MSBs are all
     * assumed to be zero (regardless of endianness).
     */
    fun UByteArray.toUInt(little: Boolean): UInt = toUInt(0, 4, little)

    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to an `int`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toInt(bytes: ShortArray, off: Int, len: Int,
    //              little: Boolean): Int {
    //        var len = len
    //        if (bytes.size - off < len) len = bytes.size - off
    //        var total = 0
    //        var i = 0
    //        var ndx = off
    //        while (i < len) {
    //            total = total or (bytes[ndx].toInt() shl ((if (little) i else len - i - 1) * 8))
    //            i++
    //            ndx++
    //        }
    //        return total
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array beyond the given
    //     * offset to an `int`. If there are fewer than 4 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toInt(bytes: ShortArray, off: Int,
    //              little: Boolean): Int {
    //        return toInt(bytes, off, 4, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array to an
    //     * `int`. If there are fewer than 4 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toInt(bytes: ShortArray, little: Boolean): Int {
    //        return toInt(bytes, 0, 4, little)
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to a `float`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toFloat(bytes: ByteArray, off: Int, len: Int,
    //                little: Boolean): Float {
    //        return java.lang.Float.intBitsToFloat(toInt(bytes, off, len, little))
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array beyond a given
    //     * offset to a `float`. If there are fewer than 4 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toFloat(bytes: ByteArray, off: Int,
    //                little: Boolean): Float {
    //        return toFloat(bytes, off, 4, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array to a
    //     * `float`. If there are fewer than 4 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toFloat(bytes: ByteArray, little: Boolean): Float {
    //        return toFloat(bytes, 0, 4, little)
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * a given offset to a `float`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toFloat(bytes: ShortArray, off: Int,
    //                len: Int, little: Boolean): Float {
    //        return java.lang.Float.intBitsToFloat(toInt(bytes, off, len, little))
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array beyond a given
    //     * offset to a `float`. If there are fewer than 4 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toFloat(bytes: ShortArray, off: Int,
    //                little: Boolean): Float {
    //        return toInt(bytes, off, 4, little).toFloat()
    //    }
    //
    //    /**
    //     * Translates up to the first 4 bytes of a `byte` array to a
    //     * `float`. If there are fewer than 4 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toFloat(bytes: ShortArray, little: Boolean): Float {
    //        return toInt(bytes, 0, 4, little).toFloat()
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to a `long`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toLong(bytes: ByteArray, off: Int, len: Int,
    //               little: Boolean): Long {
    //        var len = len
    //        if (bytes.size - off < len) len = bytes.size - off
    //        var total: Long = 0
    //        var i = 0
    //        var ndx = off
    //        while (i < len) {
    //            total = total or (
    //                    (if (bytes[ndx] < 0) 256L + bytes[ndx] else bytes[ndx].toLong()) shl ((if (little
    //                    ) i else len - i - 1) * 8))
    //            i++
    //            ndx++
    //        }
    //        return total
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array beyond the given
    //     * offset to a `long`. If there are fewer than 8 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toLong(bytes: ByteArray, off: Int,
    //               little: Boolean): Long {
    //        return toLong(bytes, off, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array to a
    //     * `long`. If there are fewer than 8 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toLong(bytes: ByteArray, little: Boolean): Long {
    //        return toLong(bytes, 0, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to a `long`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toLong(bytes: ShortArray, off: Int, len: Int,
    //               little: Boolean): Long {
    //        var len = len
    //        if (bytes.size - off < len) len = bytes.size - off
    //        var total: Long = 0
    //        var i = 0
    //        var ndx = off
    //        while (i < len) {
    //            total = total or ((bytes[ndx].toLong()) shl ((if (little) i else len - i - 1) * 8))
    //            i++
    //            ndx++
    //        }
    //        return total
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array beyond the given
    //     * offset to a `long`. If there are fewer than 8 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toLong(bytes: ShortArray, off: Int,
    //               little: Boolean): Long {
    //        return toLong(bytes, off, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array to a
    //     * `long`. If there are fewer than 8 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toLong(bytes: ShortArray, little: Boolean): Long {
    //        return toLong(bytes, 0, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to a `double`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toDouble(bytes: ByteArray, off: Int,
    //                 len: Int, little: Boolean): Double {
    //        return java.lang.Double.longBitsToDouble(toLong(bytes, off, len, little))
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array beyond the given
    //     * offset to a `double`. If there are fewer than 8 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toDouble(bytes: ByteArray, off: Int,
    //                 little: Boolean): Double {
    //        return toDouble(bytes, off, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array to a
    //     * `double`. If there are fewer than 8 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toDouble(bytes: ByteArray, little: Boolean): Double {
    //        return toDouble(bytes, 0, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first `len` bytes of a `byte` array beyond
    //     * the given offset to a `double`. If there are fewer than `len`
    //     * bytes available, the MSBs are all assumed to be zero (regardless of
    //     * endianness).
    //     */
    //    fun toDouble(bytes: ShortArray, off: Int,
    //                 len: Int, little: Boolean): Double {
    //        return java.lang.Double.longBitsToDouble(toLong(bytes, off, len, little))
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array beyond the given
    //     * offset to a `double`. If there are fewer than 8 bytes available, the
    //     * MSBs are all assumed to be zero (regardless of endianness).
    //     */
    //    fun toDouble(bytes: ShortArray, off: Int,
    //                 little: Boolean): Double {
    //        return toDouble(bytes, off, 8, little)
    //    }
    //
    //    /**
    //     * Translates up to the first 8 bytes of a `byte` array to a
    //     * `double`. If there are fewer than 8 bytes available, the MSBs are all
    //     * assumed to be zero (regardless of endianness).
    //     */
    //    fun toDouble(bytes: ShortArray, little: Boolean): Double {
    //        return toDouble(bytes, 0, 8, little)
    //    }
    //
    //    // -- Word decoding - primitive types to bytes --
    //    /** Translates the `short` value into an array of two `byte`s.  */
    //    fun fromShort(value: Short, little: Boolean): ByteArray {
    //        val v = ByteArray(2)
    //        unpack(value.toLong(), v, 0, 2, little)
    //        return v
    //    }
    //
    //    /** Translates the `int` value into an array of four `byte`s.  */
    //    fun fromInt(value: Int, little: Boolean): ByteArray {
    //        val v = ByteArray(4)
    //        unpack(value.toLong(), v, 0, 4, little)
    //        return v
    //    }
    //
    //    /** Translates the `float` value into an array of four `byte`s.  */
    //    fun fromFloat(value: Float, little: Boolean): ByteArray {
    //        val v = ByteArray(4)
    //        unpack(java.lang.Float.floatToIntBits(value).toLong(), v, 0, 4, little)
    //        return v
    //    }
    //
    //    /** Translates the `long` value into an array of eight `byte`s.  */
    //    fun fromLong(value: Long, little: Boolean): ByteArray {
    //        val v = ByteArray(8)
    //        unpack(value, v, 0, 8, little)
    //        return v
    //    }
    //
    //    /** Translates the `double` value into an array of eight `byte`s.  */
    //    fun fromDouble(value: Double, little: Boolean): ByteArray {
    //        val v = ByteArray(8)
    //        unpack(java.lang.Double.doubleToLongBits(value), v, 0, 8, little)
    //        return v
    //    }
    //
    //    /**
    //     * Translates an array of `short` values into an array of `byte`
    //     * values.
    //     */
    //    fun fromShorts(values: ShortArray, little: Boolean): ByteArray {
    //        val v = ByteArray(values.size * 2)
    //        for (i in values.indices) {
    //            unpack(values[i].toLong(), v, i * 2, 2, little)
    //        }
    //        return v
    //    }
    //
    //    /**
    //     * Translates an array of `int` values into an array of `byte`
    //     * values.
    //     */
    //    fun fromInts(values: IntArray, little: Boolean): ByteArray {
    //        val v = ByteArray(values.size * 4)
    //        for (i in values.indices) {
    //            unpack(values[i].toLong(), v, i * 4, 4, little)
    //        }
    //        return v
    //    }
    //
    //    /**
    //     * Translates an array of `float` values into an array of `byte`
    //     * values.
    //     */
    //    fun fromFloats(values: FloatArray, little: Boolean): ByteArray {
    //        val v = ByteArray(values.size * 4)
    //        for (i in values.indices) {
    //            unpack(java.lang.Float.floatToIntBits(values[i]).toLong(), v, i * 4, 4, little)
    //        }
    //        return v
    //    }
    //
    //    /**
    //     * Translates an array of `long` values into an array of `byte`
    //     * values.
    //     */
    //    fun fromLongs(values: LongArray, little: Boolean): ByteArray {
    //        val v = ByteArray(values.size * 8)
    //        for (i in values.indices) {
    //            unpack(values[i], v, i * 8, 8, little)
    //        }
    //        return v
    //    }
    //
    //    /**
    //     * Translates an array of `double` values into an array of `byte`
    //     * values.
    //     */
    //    fun fromDoubles(values: DoubleArray, little: Boolean): ByteArray {
    //        val v = ByteArray(values.size * 8)
    //        for (i in values.indices) {
    //            unpack(java.lang.Double.doubleToLongBits(values[i]), v, i * 8, 8, little)
    //        }
    //        return v
    //    }
    //
    //    /**
    //     * Translates `nBytes` of the given `long` and places the result
    //     * in the given `byte` array.
    //     *
    //     * @throws IllegalArgumentException if the specified indices fall outside the
    //     * buffer
    //     */
    //    fun unpack(value: Long, buf: ByteArray, ndx: Int,
    //               nBytes: Int, little: Boolean) {
    //        if (buf.size < ndx + nBytes) {
    //            throw java.lang.IllegalArgumentException("Invalid indices: buf.length=" +
    //                                                             buf.size + ", ndx=" + ndx + ", nBytes=" + nBytes)
    //        }
    //        if (little) {
    //            for (i in 0 until nBytes) {
    //                buf[ndx + i] = ((value shr (8 * i)) and 0xffL).toByte()
    //            }
    //        } else {
    //            for (i in 0 until nBytes) {
    //                buf[ndx + i] = ((value shr (8 * (nBytes - i - 1))) and 0xffL).toByte()
    //            }
    //        }
    //    }
    //
    //    /**
    //     * Converts a `byte` array to the appropriate 1D primitive type array.
    //     *
    //     * @param b Byte array to convert.
    //     * @param bpp Denotes the number of bytes in the returned primitive type (e.g.
    //     * if bpp == 2, we should return an array of type `short`).
    //     * @param fp If set and bpp == 4 or bpp == 8, then return `float`s or
    //     * `double`s.
    //     * @param little Whether `byte` array is in little-endian order.
    //     */
    //    fun makeArray(b: ByteArray, bpp: Int,
    //                  fp: Boolean, little: Boolean): Any? {
    //        if (bpp == 1) {
    //            return b
    //        } else if (bpp == 2) {
    //            val s = ShortArray(b.size / 2)
    //            for (i in s.indices) {
    //                s[i] = toShort(b, i * 2, 2, little)
    //            }
    //            return s
    //        } else if (bpp == 4 && fp) {
    //            val f = FloatArray(b.size / 4)
    //            for (i in f.indices) {
    //                f[i] = toFloat(b, i * 4, 4, little)
    //            }
    //            return f
    //        } else if (bpp == 4) {
    //            val i = IntArray(b.size / 4)
    //            for (j in i.indices) {
    //                i[j] = toInt(b, j * 4, 4, little)
    //            }
    //            return i
    //        } else if (bpp == 8 && fp) {
    //            val d = DoubleArray(b.size / 8)
    //            for (i in d.indices) {
    //                d[i] = toDouble(b, i * 8, 8, little)
    //            }
    //            return d
    //        } else if (bpp == 8) {
    //            val l = LongArray(b.size / 8)
    //            for (i in l.indices) {
    //                l[i] = toLong(b, i * 8, 8, little)
    //            }
    //            return l
    //        }
    //        return null
    //    }
    //
    //    /**
    //     * Converts a `byte` array to the appropriate 2D primitive type array.
    //     *
    //     * @param b Byte array to convert.
    //     * @param bpp Denotes the number of bytes in the returned primitive type (e.g.
    //     * if bpp == 2, we should return an array of type `short`).
    //     * @param fp If set and bpp == 4 or bpp == 8, then return `float`s or
    //     * `double`s.
    //     * @param little Whether `byte` array is in little-endian order.
    //     * @param height The height of the output primitive array (2nd dim length).
    //     * @return a 2D primitive array of appropriate type, dimensioned
    //     * [height][b.length / (bpp * height)]
    //     * @throws IllegalArgumentException if input `byte` array does not
    //     * divide evenly into height pieces
    //     */
    //    fun makeArray2D(b: ByteArray, bpp: Int,
    //                    fp: Boolean, little: Boolean, height: Int): Any? {
    //        if (b.size % (bpp * height) != 0) {
    //            throw java.lang.IllegalArgumentException("Array length mismatch: " +
    //                                                             "b.length=" + b.size + "; bpp=" + bpp + "; height=" + height)
    //        }
    //        val width = b.size / (bpp * height)
    //        if (bpp == 1) {
    //            val b2 = Array(height) { ByteArray(width) }
    //            for (y in 0 until height) {
    //                val index = width * y
    //                java.lang.System.arraycopy(b, index, b2[y], 0, width)
    //            }
    //            return b2
    //        } else if (bpp == 2) {
    //            val s = Array(height) { ShortArray(width) }
    //            for (y in 0 until height) {
    //                for (x in 0 until width) {
    //                    val index = 2 * (width * y + x)
    //                    s[y][x] = toShort(b, index, 2, little)
    //                }
    //            }
    //            return s
    //        } else if (bpp == 4 && fp) {
    //            val f = Array(height) { FloatArray(width) }
    //            for (y in 0 until height) {
    //                for (x in 0 until width) {
    //                    val index = 4 * (width * y + x)
    //                    f[y][x] = toFloat(b, index, 4, little)
    //                }
    //            }
    //            return f
    //        } else if (bpp == 4) {
    //            val i = Array(height) { IntArray(width) }
    //            for (y in 0 until height) {
    //                for (x in 0 until width) {
    //                    val index = 4 * (width * y + x)
    //                    i[y][x] = toInt(b, index, 4, little)
    //                }
    //            }
    //            return i
    //        } else if (bpp == 8 && fp) {
    //            val d = Array(height) { DoubleArray(width) }
    //            for (y in 0 until height) {
    //                for (x in 0 until width) {
    //                    val index = 8 * (width * y + x)
    //                    d[y][x] = toDouble(b, index, 8, little)
    //                }
    //            }
    //            return d
    //        } else if (bpp == 8) {
    //            val l = Array(height) { LongArray(width) }
    //            for (y in 0 until height) {
    //                for (x in 0 until width) {
    //                    val index = 8 * (width * y + x)
    //                    l[y][x] = toLong(b, index, 8, little)
    //                }
    //            }
    //            return l
    //        }
    //        return null
    //    }
    //
    //    // -- Byte swapping --
    //    fun swap(x: Short): Short {
    //        return ((x.toInt() shl 8) or ((x.toInt() shr 8) and 0xFF)).toShort()
    //    }
    //
    //    fun swap(x: Char): Char {
    //        return ((x.code shl 8) or ((x.code shr 8) and 0xFF)).toChar()
    //    }
    //
    //    fun swap(x: Int): Int {
    //        return (swap(x.toShort()).toInt() shl 16) or (swap((x shr 16).toShort()).toInt() and 0xFFFF)
    //    }
    //
    //    fun swap(x: Long): Long {
    //        return (swap(x.toInt()).toLong() shl 32) or (swap((x shr 32).toInt()).toLong() and 0xFFFFFFFFL)
    //    }
    //
    //    fun swap(x: Float): Float {
    //        return java.lang.Float.intBitsToFloat(swap(java.lang.Float.floatToIntBits(x)))
    //    }
    //
    //    fun swap(x: Double): Double {
    //        return java.lang.Double.longBitsToDouble(swap(java.lang.Double.doubleToLongBits(x)))
    //    }
    //
    //    // -- Normalization --
    //    /**
    //     * Normalize the given `float` array so that the minimum value maps to
    //     * 0.0 and the maximum value maps to 1.0.
    //     */
    //    fun normalize(data: FloatArray): FloatArray {
    //        val rtn = FloatArray(data.size)
    //
    //        // determine the finite min and max values
    //        var min = Float.MAX_VALUE
    //        var max = Float.MIN_VALUE
    //        for (floatValue in data) {
    //            if (floatValue == Float.POSITIVE_INFINITY ||
    //                floatValue == Float.NEGATIVE_INFINITY) {
    //                continue
    //            }
    //            if (floatValue < min) min = floatValue
    //            if (floatValue > max) max = floatValue
    //        }
    //
    //        // normalize infinity values
    //        for (i in data.indices) {
    //            if (data[i] == Float.POSITIVE_INFINITY) data[i] = max
    //            else if (data[i] == Float.NEGATIVE_INFINITY) data[i] = min
    //        }
    //
    //        // now normalize; min => 0.0, max => 1.0
    //        val range = max - min
    //        for (i in rtn.indices) {
    //            rtn[i] = (data[i] - min) / range
    //        }
    //        return rtn
    //    }
    //
    //    /**
    //     * Normalize the given `double` array so that the minimum value maps to
    //     * 0.0 and the maximum value maps to 1.0.
    //     */
    //    fun normalize(data: DoubleArray): DoubleArray {
    //        val rtn = DoubleArray(data.size)
    //
    //        // determine the finite min and max values
    //        var min = Double.MAX_VALUE
    //        var max = Double.MIN_VALUE
    //        for (doubleValue in data) {
    //            if (doubleValue == Double.POSITIVE_INFINITY ||
    //                doubleValue == Double.NEGATIVE_INFINITY) {
    //                continue
    //            }
    //            if (doubleValue < min) min = doubleValue
    //            if (doubleValue > max) max = doubleValue
    //        }
    //
    //        // normalize infinity values
    //        for (i in data.indices) {
    //            if (data[i] == Double.POSITIVE_INFINITY) data[i] = max
    //            else if (data[i] == Double.NEGATIVE_INFINITY) data[i] = min
    //        }
    //
    //        // now normalize; min => 0.0, max => 1.0
    //        val range = max - min
    //        for (i in rtn.indices) {
    //            rtn[i] = (data[i] - min) / range
    //        }
    //        return rtn
    //    }
    //
    //    // -- Signed data conversion --
    //    fun makeSigned(b: ByteArray): ByteArray {
    //        for (i in b.indices) {
    //            b[i] = (b[i] + 128).toByte()
    //        }
    //        return b
    //    }
    //
    //    fun makeSigned(s: ShortArray): ShortArray {
    //        for (i in s.indices) {
    //            s[i] = (s[i] + 32768).toShort()
    //        }
    //        return s
    //    }
    //
    //    fun makeSigned(i: IntArray): IntArray {
    //        for (j in i.indices) {
    //            i[j] = (i[j] + 2147483648L).toInt()
    //        }
    //        return i
    //    }
}