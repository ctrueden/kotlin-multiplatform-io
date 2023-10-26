package io.scif.formats.apng

import io.scif.AbstractParser
import io.scif.Format
import io.scif.api.i
import io.scif.api.i8
import io.scif.config.SCIFIOConfig
import okio.FileHandle
import okio.IOException
import okio.buffer
import okio.use
import platform.FormatException
import uns.L
import uns.i8

/**
 * File format SCIFIO Parser for Animated Portable Network Graphics (APNG)
 * images.
 */
class ApngParser/* : AbstractParser<APNGFormat.Metadata>()*/ {
    // -- Parser API Methods --
//    @Throws(IOException::class, FormatException::class)
//    /*protected*/ fun typedParse(handle: FileHandle/*DataHandle<Location?>*/,
//                                 meta: APNGFormat.Metadata = APNGFormat.Metadata(),
//                                 config: SCIFIOConfig = SCIFIOConfig()) {
//
//        handle.source().buffer().use { buf ->
//            // check that this is a valid PNG file
//            val signature = buf.readByteArray(8)
//
//            if (!signature.contentEquals(byteArrayOf(0x89.i8, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)))
//                throw FormatException("Invalid PNG signature.")
//
//            // For determining if the first frame is also the default image
//            var sawFctl = false
//
//            // read data chunks - each chunk consists of the following:
//            // 1) 32 bit length
//            // 2) 4 char type
//            // 3) 'length' bytes of data
//            // 4) 32 bit CRC
//            while (!buf.exhausted()) {
//                val length = buf.i
//                val type = buf.readUtf8(4)
//                val offset = handle.position(buf)
//
//                when (type) {
//                    "acTL" -> APNGFormat.ACTLChunk().also {
//                        it.numFrames = buf.i
//                        it.numPlays = buf.i
//                        meta.actl = it
//                    }
//                    "fcTL" -> APNGFormat.FCTLChunk().also {
//                        sawFctl = true
//                        it.sequenceNumber = buf.i
//                        it.width = buf.i
//                        it.height = buf.i
//                        it.xOffset = buf.i
//                        it.yOffset = buf.i
//                        it.delayNum = buf.i16
//                        it.delayDen = buf.i16
//                        it.disposeOp = buf.i8
//                        it.blendOp = buf.i8
//                        meta.fctl += it
//                    }
//                    "IDAT" -> APNGFormat.IDATChunk().also {
//                        meta.isSeparateDefault = !sawFctl
//                        meta.idat += it
//                        buf.skip(length.L)
//                    }
//                    "fdAT" -> APNGFormat.FDATChunk().also {
//                        it.sequenceNumber = buf.i
//                        meta.fctl.last().fdatChunks += it
//                        buf.skip(length.L - 4)
//                    }
//                    "IHDR" -> APNGFormat.IHDRChunk().also {
//                        it.width = buf.i
//                        it.height = buf.i
//                        it.bitDepth = buf.i8
//                        it.colourType = buf.i8
//                        it.compressionMethod = buf.i8
//                        it.filterMethod = buf.i8
//                        it.interlaceMethod = buf.i8
//                        meta.ihdr = it
//                    }
//                    "PLTE" -> APNGFormat.PLTEChunk().also {
//
//                        it.red = ByteArray(length / 3)
//                        it.blue = ByteArray(length / 3)
//                        it.green = ByteArray(length / 3)
//
//                        for (i in 0..<length / 3) {
//                            it.red[i] = buf.i8
//                            it.green[i] = buf.i8
//                            it.blue[i] = buf.i8
//                        }
//
//                        meta.plte = it
//                    }
//                    "IEND" -> APNGFormat.IENDChunk().also {
//                        buf.skip(handle.size() - handle.position(buf))
//                        meta.iend = it
//                    }
//                    else -> null.also { buf.skip(length.L) }
//                }?.also {
//                    it.offset = offset
//                    it.length = length
//                }
//
//                if (handle.position(buf) < handle.size() - 4)
//                    buf.skip(4) // skip the CRC
//            }
//        }
//    }
//
//    override fun close() {
//        TODO("Not yet implemented")
//    }
//
//    override fun close(fileOnly: Boolean) {
//        TODO("Not yet implemented")
//    }
//
//    override val format: Format
//        get() = TODO("Not yet implemented")
//    override val usedFiles: Array<Any?>
//        get() = TODO("Not yet implemented")
//    override val supportedMetadataLevels: Set<Any?>
//        get() = TODO("Not yet implemented")
}