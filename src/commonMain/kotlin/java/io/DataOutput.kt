//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package java.io

import okio.IOException

interface DataOutput {
    @Throws(IOException::class)
    fun write(b: Int)

    @Throws(IOException::class)
    fun write(b: ByteArray)

    @Throws(IOException::class)
    fun write(b: ByteArray, off: Int, len: Int)

    @Throws(IOException::class)
    fun writeBoolean(v: Boolean)

    @Throws(IOException::class)
    fun writeByte(v: Byte)

    @Throws(IOException::class)
    fun writeUByte(v: UByte)

    @Throws(IOException::class)
    fun writeShort(v: Short)

    @Throws(IOException::class)
    fun writeUShort(v: UShort)

    @Throws(IOException::class)
    fun writeChar(v: Int)

    @Throws(IOException::class)
    fun writeInt(v: Int)

    @Throws(IOException::class)
    fun writeUInt(v: UInt)

    @Throws(IOException::class)
    fun writeLong(v: Long)

    @Throws(IOException::class)
    fun writeULong(v: ULong)

    @Throws(IOException::class)
    fun writeFloat(v: Float)

    @Throws(IOException::class)
    fun writeDouble(v: Double)

    @Throws(IOException::class)
    fun writeBytes(s: String)

    @Throws(IOException::class)
    fun writeChars(s: String)

    @Throws(IOException::class)
    fun writeUTF(s: String)
}