@file:Suppress("UNUSED", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package io.scif.api

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import okio.*
import uns.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

val bigEndian = false
val BufferedSource.i8: Byte
    get() = readByte()
val BufferedSource.i16: Short
    get() = if (bigEndian) readShort() else readShortLe()
val BufferedSource.i32: Int
    get() = if (bigEndian) readInt() else readIntLe()
val BufferedSource.i: Int
    get() = i32
val BufferedSource.i64: Long
    get() = if (bigEndian) readLong() else readLongLe()
val BufferedSource.L: Long
    get() = i64

//val Source.f32: Float
//	get() = readF

val BufferedSource.ub: UByte
    get() = i8.ub
val BufferedSource.us: UShort
    get() = i16.us
val BufferedSource.ui: UInt
    get() = i.ui
val BufferedSource.ul: ULong
    get() = L.ul

val BufferedSource.u8: U8
    get() = ub.u8
val BufferedSource.u16: U16
    get() = us.u16
val BufferedSource.u32: U32
    get() = ui.u32
val BufferedSource.u64: U64
    get() = ul.u64

val BufferedSource.f32: F32
    get() = Float.fromBits(i)
val BufferedSource.f: F32
    get() = f32
val BufferedSource.f64: F64
    get() = Double.fromBits(L)

fun BufferedSource.localTime(byteCount: Int) = LocalTime.parse(readUtf8(byteCount))
fun BufferedSource.localDate(byteCount: Int) = LocalDate.parse(readUtf8(byteCount))

inline infix fun BufferedSource.readUtf8(byteCount: Short): String = readUtf8(byteCount.L)
inline infix fun BufferedSource.readUtf8(byteCount: UShort): String = readUtf8(byteCount.L)
inline infix fun BufferedSource.readUtf8(byteCount: Int): String = readUtf8(byteCount.L)

@ExperimentalContracts
inline fun <R> FileHandle.buffer(block: (BufferedSource) -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return buffer(0, block)
}

@ExperimentalContracts
inline fun <R> FileHandle.buffer(offset: Int, block: (BufferedSource) -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return source(offset.toLong()).buffer().use(block)
}

@ExperimentalContracts
inline fun <R> FileHandle.seek(offset: Int, block: (BufferedSource) -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return seek(offset.toULong(), block)
}

@ExperimentalContracts
inline fun <R> FileHandle.seek(offset: ULong, block: (BufferedSource) -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return source(offset.L).buffer().use(block)
}

inline fun FileHandle.reposition(bufferedSource: BufferedSource, position: Int) = reposition(bufferedSource, position.L)
inline fun FileHandle.reposition(bufferedSource: BufferedSource, position: ULong) = reposition(bufferedSource, position.L)

//var Buffer.pos: Int
//    set(value) {
//        val foo = this::class
//        //java.getDeclaredField("foo").run {
////            isAccessible = true
////            get(x) as String
////        }.also {
////            println(it)
////        }
//    }

val String.i: Int
    get() = toInt()
val String.L: Long
    get() = toLong()
val String.f: Float
    get() = toFloat()