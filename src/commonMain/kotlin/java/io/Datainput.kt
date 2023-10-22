//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package java.io

import okio.IOException

interface DataInput {
    @Throws(IOException::class)
    fun readFully(b: ByteArray)

    @Throws(IOException::class)
    fun readFully(b: ByteArray, off: Int, len: Int)

    @Throws(IOException::class)
    fun skipBytes(n: Int): Int

    @Throws(IOException::class)
    fun readBoolean(): Boolean

    @Throws(IOException::class)
    fun readByte(): Byte

    @Throws(IOException::class)
    fun readUByte(): UByte

    @Throws(IOException::class)
    fun readShort(): Short

    @Throws(IOException::class)
    fun readUShort(): UShort

    @Throws(IOException::class)
    fun readChar(): Char

    @Throws(IOException::class)
    fun readInt(): Int

    @Throws(IOException::class)
    fun readUInt(): UInt

    @Throws(IOException::class)
    fun readLong(): Long

    @Throws(IOException::class)
    fun readULong(): ULong

    @Throws(IOException::class)
    fun readFloat(): Float

    @Throws(IOException::class)
    fun readDouble(): Double

    @Throws(IOException::class)
    fun readLine(): String

    @Throws(IOException::class)
    fun readUTF(): String
}