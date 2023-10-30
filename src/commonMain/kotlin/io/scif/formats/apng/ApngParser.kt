package io.scif.formats.apng

import io.scif.AbstractParser
import io.scif.Format
import io.scif.api.ReadOnlyFileHandle
import io.scif.config.SCIFIOConfig
import okio.IOException
import platform.FormatException
import uns.i8

/**
 * File format SCIFIO Parser for Animated Portable Network Graphics (APNG)
 * images.
 */
class ApngParser : AbstractParser<APNGFormat.Metadata>() {
    // -- Parser API Methods --
    @Throws(IOException::class, FormatException::class)
    /*protected*/
    fun typedParse(handle: ReadOnlyFileHandle/*DataHandle<Location?>*/,
                   meta: APNGFormat.Metadata = APNGFormat.Metadata(),
                   config: SCIFIOConfig = SCIFIOConfig()) {

        handle.bigEndian = true

        // check that this is a valid PNG file
        val signature = handle bytes 8

        if (!signature.contentEquals(byteArrayOf(0x89.i8, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)))
            throw FormatException("Invalid PNG signature.")

        // read data chunks - each chunk consists of the following:
        // 1) 32 bit length
        // 2) 4 char type
        // 3) 'length' bytes of data
        // 4) 32 bit CRC
        while (handle.notExhausted) {
            val length = handle.i
            val type = handle.utf8(4)
            val offset = handle.pos

            when (type) {
                "acTL" -> APNGFormat.ACTLChunk(handle).also { meta.actl = it }
                "fcTL" -> APNGFormat.FCTLChunk(handle).also { meta.fctl += it }
                "IDAT" -> APNGFormat.IDATChunk().also {
                    // For determining if the first frame is also the default image
                    meta.isSeparateDefault = meta.fctl.isEmpty()
                    meta.idat += it
                    handle + length
                }
                "fdAT" -> APNGFormat.FDATChunk(handle).also {
                    meta.fctl.last().fdatChunks += it
                    handle + (length - 4)
                }
                "IHDR" -> APNGFormat.IHDRChunk(handle).also { meta.ihdr = it }
                "PLTE" -> APNGFormat.PLTEChunk(handle, length / 3).also { meta.plte = it }
                "IEND" -> APNGFormat.IENDChunk().also {
                    handle.skipTillEnd()
                    meta.iend = it
                }
                else -> null.also {
                    handle + length
                }
            }?.also {
                it.offset = offset
                it.length = length
            }

            if (handle.rem >= 4u)
                handle + 4 // skip the CRC
        }
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun close(fileOnly: Boolean) {
        TODO("Not yet implemented")
    }

    override val format: Format
        get() = TODO("Not yet implemented")
    override val usedFiles: Array<Any?>
        get() = TODO("Not yet implemented")
    override val supportedMetadataLevels: Set<Any?>
        get() = TODO("Not yet implemented")
}