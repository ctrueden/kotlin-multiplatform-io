@file:Suppress("UNUSED", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@file:OptIn(ExperimentalStdlibApi::class)

package io.scif.api

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import okio.*
import uns.*


inline fun <R> Path.readOnly(block: (ReadOnlyFileHandle) -> R): R =
    hostFileSystem.openReadOnly(this).use { fileHandle ->
        ReadOnlyFileHandle(fileHandle).use(block)
    }

class ReadOnlyFileHandle(val fileHandle: FileHandle) : AutoCloseable {
    val bufferedSource = fileHandle.source().buffer()

    var pos: ULong
        get() = fileHandle.position(bufferedSource).ul
        set(value) = fileHandle.reposition(bufferedSource, value.L)

    val size: ULong
        get() = fileHandle.size().ul
    val rem: ULong
        get() = size - pos

    infix fun pos(pos: Int) = fileHandle.reposition(bufferedSource, pos.L)
    infix fun pos(pos: Long) = fileHandle.reposition(bufferedSource, pos)


    var bigEndian = false
    val i8: Byte
        get() = bufferedSource.readByte()
    val i16: Short
        get() = if (bigEndian) bufferedSource.readShort() else bufferedSource.readShortLe()
    val i32: Int
        get() = if (bigEndian) bufferedSource.readInt() else bufferedSource.readIntLe()
    val i: Int
        get() = i32
    val i64: Long
        get() = if (bigEndian) bufferedSource.readLong() else bufferedSource.readLongLe()
    val L: Long
        get() = i64

    //val Source.f32: Float
    //	get() = readF

    val ub: UByte
        get() = i8.ub
    val us: UShort
        get() = i16.us
    val ui: UInt
        get() = i.ui
    val ul: ULong
        get() = L.ul

    val u8: U8
        get() = ub.u8
    val u16: U16
        get() = us.u16
    val u32: U32
        get() = ui.u32
    val u64: U64
        get() = ul.u64

    val f32: F32
        get() = Float.fromBits(i)
    val f: F32
        get() = f32
    val f64: F64
        get() = Double.fromBits(L)

    operator fun plus(byteCount: Int) = skip(byteCount)
    operator fun plus(byteCount: UInt) = skip(byteCount.L)
    operator fun plus(byteCount: ULong) = skip(byteCount.L)
    operator fun plus(byteCount: Long) = skip(byteCount)
    operator fun plusAssign(byteCount: Int) = plusAssign(byteCount.ui)
    operator fun plusAssign(byteCount: UInt) {
        pos += byteCount
    }
    operator fun minusAssign(byteCount: Int) =  minusAssign(byteCount.ui)
    operator fun minusAssign(byteCount: UInt) {
        pos -= byteCount
    }

    fun skipTillEnd() = skip(fileHandle.size() - pos.L)
    infix fun skip(byteCount: Int) = skip(byteCount.L)
    infix fun skip(byteCount: Long) = bufferedSource.skip(byteCount)

    fun localTime(byteCount: Int) = LocalTime.parse(utf8(byteCount))
    fun localDate(byteCount: Int) = LocalDate.parse(utf8(byteCount))

    inline infix fun utf8(byteCount: Short): String = utf8(byteCount.L)
    inline infix fun utf8(byteCount: UShort): String = utf8(byteCount.L)
    inline infix fun utf8(byteCount: Int): String = utf8(byteCount.L)
    inline infix fun utf8(byteCount: Long): String = bufferedSource.readUtf8(byteCount)

    fun bytes(byteCount: Int) = bytes(byteCount.L)
    fun bytes(byteCount: Long) = bufferedSource.readByteArray(byteCount)

    val exhausted
        get() = bufferedSource.exhausted()
    val notExhausted
        get() = !exhausted

    override fun close() = bufferedSource.close()
}

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

val String.i8: Byte
    get() = toByte()
val String.i: Int
    get() = toInt()
val String.L: Long
    get() = toLong()
val String.f: Float
    get() = toFloat()