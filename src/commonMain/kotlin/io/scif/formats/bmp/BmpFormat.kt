/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2023 SCIFIO developers.
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
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.scif.formats.bmp

import io.scif.*
import io.scif.api.ReadOnlyFileHandle
//import io.scif.codec.BitBuffer
import io.scif.config.SCIFIOConfig
import io.scif.util.FormatTools
//import io.scif.util.ImageTools
import net.imagej.axis.Axes
import net.imglib2.Interval
import net.imglib2.display.ColorTable
import net.imglib2.display.ColorTable8
import okio.IOException
import platform.FormatException
import uns.f64
import uns.i
import kotlin.math.abs
import kotlin.math.absoluteValue

/**
 * BMPReader is the file format reader for
 * [Microsoft Bitmap
 * (BMP)](https://en.wikipedia.org/wiki/BMP_file_format) files.
 *
 * @author Mark Hiner
 */
//@org.scijava.plugin.Plugin(type = Format::class, name = "Windows Bitmap")
class BmpFormat : AbstractFormat() {
    // -- AbstractFormat Methods --
    override fun makeSuffixArray(): Array<String> = arrayOf("bmp")

    // -- Nested Classes --
    class Metadata : AbstractMetadata(), HasColorTable {
        // -- Fields --
        lateinit var fileHeader: FileHeader
        class FileHeader(handle: ReadOnlyFileHandle) {
            val identifier = handle utf8 2
            val size = handle.ui
            val reserved0 = handle.us
            val reserved1 = handle.us
            val offset = handle.ui
        }
        lateinit var dibHeader: DibHeader
        class DibHeader(handle: ReadOnlyFileHandle) {
            val size = handle.ui
            var width = handle.i
            var height = handle.i

//            // get the dimensions
//            var sizeX = getSource().readInt();
//            var sizeY = getSource().readInt();
//
//            //            iMeta.addAxis(Axes.X, sizeX)
//            //            iMeta.addAxis(Axes.Y, sizeY)
//
//            if (sizeX < 1) {
//                //                log().trace("Invalid width: $sizeX; using the absolute value")
//                sizeX = abs(sizeX)
//            }
//            if (sizeY < 1) {
//                //                log().trace("Invalid height: $sizeY; using the absolute value")
//                sizeY = abs(sizeY)
//                meta.isInvertY = true
//            }

            var planesNumber = handle.us
            var bitsPerPixel = handle.us
            var compression = handle.ui

            val imageSize = handle.ui
            val pixelSizeX = handle.i
            val pixelSizeY = handle.i
            var nColors = handle.ui
            init {
                if (nColors == 0u && bitsPerPixel.i != 32 && bitsPerPixel.i != 24)
                    nColors = if (bitsPerPixel < 8u) 1u shl bitsPerPixel.i else 256u
            }
            var nImpColors = handle.ui

        }
        /** The palette for indexed color images.  */
        var palette: ColorTable8? = null

        // -- Getters and Setters --
        /** Compression type  */
        var compression: Int = 0

        /** Offset to image data.  */
        @Deprecated("", ReplaceWith("meta.offset"))
        var global: Long = 0

        var isInvertY: Boolean = false

        // -- Metadata API Methods --
//        fun populateImageMetadata() {
//            log().info("Populating metadata")
//
//            var bpp = get(0).bitsPerPixel
//            val iMeta = get(0)
//            iMeta.setAxisTypes(Axes.X, Axes.Y)
//            iMeta.setPlanarAxisCount(2)
//
//            var sizeC = if (bpp != 24) 1 else 3
//
//            if (bpp == 32) sizeC = 4
//            if (bpp > 8) bpp /= sizeC
//
//            iMeta.bitsPerPixel = bpp
//
//            when (bpp) {
//                16 -> iMeta.pixelType = FormatTools.UINT16
//                32 -> iMeta.pixelType = FormatTools.UINT32
//                else -> iMeta.pixelType = FormatTools.UINT8
//            }
//            iMeta.isLittleEndian = true
//
//            iMeta.isMetadataComplete = true
//            iMeta.isIndexed = getColorTable(0, 0) != null
//
//            if (iMeta.isIndexed) {
//                sizeC = 1
//            }
//
//            if (sizeC > 1 || iMeta.isIndexed) {
//                iMeta.addAxis(Axes.CHANNEL, sizeC)
//                if (sizeC > 1) iMeta.setAxisTypes(Axes.CHANNEL, Axes.X, Axes.Y)
//                iMeta.setPlanarAxisCount(3)
//            }
//
//            iMeta.isFalseColor = false
//        }

        // -- HasSource API Methods --
        @Throws(IOException::class) override fun close(fileOnly: Boolean) {
            super.close(fileOnly)

            if (!fileOnly) {
                compression = 0
                global = 0
                palette = null
                isInvertY = false
            }
        }

        override fun generateImageMetadata(imageIndex: Int): ImageMetadata? {
            TODO("Not yet implemented")
        }

        override val format: Format
            get() = TODO("Not yet implemented")

        // -- HasColorTable API Methods --
        override fun getColorTable(imageIndex: Int,
                                   planeIndex: Long): ColorTable? {
            return palette
        }
    }

//    class Checker : AbstractChecker() {
//        @Throws(IOException::class) override fun isFormat(stream: DataHandle<org.scijava.io.location.Location?>): Boolean {
//            val blockLen = 2
//            if (!FormatTools.validStream(stream, blockLen, false)) return false
//            return stream.readString(blockLen).startsWith(BMP_MAGIC_STRING)
//        }
//    }

    class Parser : AbstractParser<Metadata>() {
        @Throws(IOException::class, FormatException::class)
        /*protected*/
        fun typedParse(handle: /*DataHandle<org.scijava.io.location.Location?>*/ReadOnlyFileHandle,
                       meta: Metadata, config: SCIFIOConfig = SCIFIOConfig()) {
//            meta.createImageMetadata(1)

//            val iMeta = meta[0]
//            val globalTable = meta.table

            handle.bigEndian = false

            // read the first header - 14 bytes
            meta.fileHeader = Metadata.FileHeader(handle)

            // read the second header - 40 bytes
            meta.dibHeader = Metadata.DibHeader(handle)

            // read the palette, if it exists
            if (meta.dibHeader.nColors != 0u && meta.dibHeader.bitsPerPixel.i == 8) {
                val palette = Array(3) { UByteArray(256) }

                for (i in 0 until meta.dibHeader.nColors.i) {
                    for (j in palette.indices.reversed())
                        palette[j][i] = handle.ub
                    handle + 1
                }
                meta.palette = ColorTable8(palette)
            } else if (meta.dibHeader.nColors != 0u)
                handle + (meta.dibHeader.nColors * 4u)

//            if (config.parserGetLevel() != MetadataLevel.MINIMUM) {
//                globalTable["Indexed color"] = meta.getColorTable(0, 0) != null
//                globalTable["Image width"] = sizeX
//                globalTable["Image height"] = sizeY
//                globalTable["Bits per pixel"] = bpp
//                var comp = "invalid"
//
//                when (meta.compression) {
//                    RAW -> comp = "None"
//                    RLE_8 -> comp = "8 bit run length encoding"
//                    RLE_4 -> comp = "4 bit run length encoding"
//                    RGB_MASK -> comp = "RGB bitmap with mask"
//                }
//                globalTable["Compression type"] = comp
//                globalTable["X resolution"] = pixelSizeX
//                globalTable["Y resolution"] = pixelSizeY
//            }
        }

        override fun close(fileOnly: Boolean) {
            TODO("Not yet implemented")
        }

        override val format: Format?
            get() = TODO("Not yet implemented")
        override val usedFiles: Array<Any?>?
            get() = TODO("Not yet implemented")
        override val supportedMetadataLevels: Set<Any?>?
            get() = TODO("Not yet implemented")
    }

//    class Reader : ByteArrayReader<Metadata>() {
//        // -- AbstractReader API Methods --
//        protected fun createDomainArray(): Array<String> {
//            return arrayOf(FormatTools.GRAPHICS_DOMAIN)
//        }
//
//        // -- Reader API Methods --
//        @Throws(FormatException::class, IOException::class) fun openPlane(imageIndex: Int, planeIndex: Long,
//                                                                          plane: ByteArrayPlane, bounds: Interval,
//                                                                          config: SCIFIOConfig?): ByteArrayPlane {
//            val meta: Metadata = getMetadata()
//            val imageMeta = meta[imageIndex]
//            val xIndex: Int = imageMeta.getAxisIndex(Axes.X)
//            val yIndex: Int = imageMeta.getAxisIndex(Axes.Y)
//            val x = bounds.min(xIndex).toInt()
//            val y = bounds.min(yIndex).toInt()
//            val w = bounds.dimension(xIndex).toInt()
//            val h = bounds.dimension(yIndex).toInt()
//
//            val buf: ByteArray = plane.getData()
//            val compression = meta.compression
//            val bpp = imageMeta.bitsPerPixel
//            val sizeX = imageMeta.getAxisLength(Axes.X) as Int
//            val sizeY = imageMeta.getAxisLength(Axes.Y) as Int
//            val sizeC = imageMeta.getAxisLength(Axes.CHANNEL) as Int
//
//            FormatTools.checkPlaneForReading(meta, imageIndex, planeIndex, buf.size,
//                                             bounds)
//
//            if (compression != RAW && getHandle().length() < FormatTools.getPlaneSize(
//                    this, imageIndex)) {
//                throw UnsupportedCompressionException(compression.toString() +
//                                                              " not supported")
//            }
//
//            val rowsToSkip = if (meta.isInvertY) y else sizeY - (h + y)
//            val rowLength = sizeX * (if (imageMeta.isIndexed) 1 else sizeC)
//            getHandle().seek(meta.global + rowsToSkip * rowLength)
//
//            var pad = ((rowLength * bpp) / 8) % 2
//            if (pad == 0) {
//                pad = ((rowLength * bpp) / 8) % 4
//            } else {
//                pad *= sizeC
//            }
//            var planeSize = sizeX * sizeC * h
//            if (bpp >= 8) planeSize *= (bpp / 8)
//            else planeSize /= (8 / bpp)
//            planeSize += pad * h
//            if (planeSize + getHandle().offset() > getHandle().length()) {
//                planeSize -= (pad * h)
//
//                // sometimes we have RGB images with a single padding byte
//                if (planeSize + sizeY + getHandle().offset() <= getHandle().length()) {
//                    pad = 1
//                    planeSize += h
//                } else {
//                    pad = 0
//                }
//            }
//
//            getHandle().skipBytes(rowsToSkip * pad)
//
//            val rawPlane = ByteArray(planeSize)
//            getHandle().read(rawPlane)
//
//            val bb: BitBuffer = BitBuffer(rawPlane)
//
//            val palette = meta.getColorTable(0, 0)
//            plane.setColorTable(palette)
//
//            val effectiveC = if (palette != null && palette.length > 0) 1
//            else sizeC
//            for (row in h - 1 downTo 0) {
//                val rowIndex = if (meta.isInvertY) h - 1 - row else row
//                bb.skipBits(x * bpp * effectiveC)
//                for (i in 0 until w * effectiveC) {
//                    if (bpp <= 8) {
//                        buf[rowIndex * w * effectiveC + i] = (bb.getBits(bpp) and
//                                0xff) as Byte
//                    } else {
//                        for (b in 0 until bpp / 8) {
//                            buf[bpp / 8 * (rowIndex * w * effectiveC + i) + b] = (bb
//                                .getBits(8) and 0xff) as Byte
//                        }
//                    }
//                }
//                if (row > 0) {
//                    bb.skipBits((sizeX - w - x) * bpp * effectiveC + pad * 8)
//                }
//            }
//
//            if (imageMeta.getAxisLength(Axes.CHANNEL) > 1) {
//                ImageTools.bgrToRgb(buf, imageMeta.getInterleavedAxisCount() > 0, 1,
//                                    imageMeta.getAxisLength(Axes.CHANNEL) as Int)
//            }
//            return plane
//        }
//    }

    companion object {
        // -- Constants --
        const val BMP_MAGIC_STRING: String = "BM"

        // -- Compression types --
        private const val RAW = 0

        private const val RLE_8 = 1

        private const val RLE_4 = 2

        private const val RGB_MASK = 3
    }
}