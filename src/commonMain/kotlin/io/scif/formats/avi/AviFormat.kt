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
package io.scif.formats.avi

import io.scif.*
import io.scif.api.ReadOnlyFileHandle
import io.scif.api.i
import io.scif.api.i8
import io.scif.codec.*
//import io.scif.common.Constants
import io.scif.config.SCIFIOConfig
import io.scif.util.FormatTools
import io.scif.util.FormatTools.getBytesPerPixel
import io.scif.util.FormatTools.getPixelTypeString
import io.scif.util.FormatTools.pixelTypeFromString
//import io.scif.util.ImageTools
import io.scif.util.SCIFIOMetadataTools
import net.imagej.axis.Axes
import net.imglib2.FinalInterval
import net.imglib2.Interval
import net.imglib2.display.ColorTable
import net.imglib2.display.ColorTable8
import okio.*
import platform.FormatException
import uns.*

//import net.imglib2.display.ColorTable8

/**
 * AVIReader is the file format reader for AVI files. Much of this code was
 * adapted from Wayne Rasband's AVI Movie Reader plugin for
 * [ImageJ](http://imagej.net/).
 *
 * @author Mark Hiner
 */
//@org.scijava.plugin.Plugin(type = Format::class, name = "Audio Video Interleave")
class AVIFormat : AbstractFormat() {

    /* Legend:
        - DWORD = 32-bit unsigned integer
        - WORD = 16-bit unsigned integer
        - LONG = 32-bit signed integer
     */


    // -- AbstractFormat Methods --
    override fun makeSuffixArray(): Array<String> = arrayOf("avi")

    // -- Nested Classes --
    class MainHeader(handle: ReadOnlyFileHandle) {
        val microSecPerFrame = handle.ui
        val maxBytesPerSec = handle.ui
        val paddingGranularity = handle.ui
        val flags = handle.ui
        val totalFrames = handle.ui
        val initialFrames = handle.ui
        val streams = handle.ui
        val suggestedBufferSize = handle.ui
        val width = handle.ui

        //                                    m.addAxis(Axes.X, width)
        val height = handle.ui

        // the next should be reserved, but original Scifio is reading them as follow
        //DWORD  dwReserved[4]
        val scaleFactor = handle.ui
        val frameRate = handle.ui
        val startTime = handle.ui
        val length = handle.ui
        //                                    globalTable["Frame width"] = m.getAxisLength(Axes.X)

        val hasIndex: Boolean
            get() = (flags and HASINDEX) != 0u
        val mustUseIndex: Boolean
            get() = (flags and MUSTUSEINDEX) != 0u
        val isInterleaved: Boolean
            get() = (flags and ISINTERLEAVED) != 0u
        val trustCkType: Boolean
            get() = (flags and TRUSTCKTYPE) != 0u
        val wasCaptureFile: Boolean
            get() = (flags and WASCAPTUREFILE) != 0u
        val copyrighted: Boolean
            get() = (flags and COPYRIGHTED) != 0u
        companion object {
            // AVIF from https://ffmpeg.org/doxygen/0.6/avi_8h-source.html
            val HASINDEX = 0x00000010u
            val MUSTUSEINDEX = 0x00000020u
            val ISINTERLEAVED = 0x00000100u
            val TRUSTCKTYPE = 0x00000800u
            val WASCAPTUREFILE = 0x00010000u
            val COPYRIGHTED = 0x00020000u
        }
    }

    class Metadata : AbstractMetadata(), HasColorTable {
        // -- AVI Metadata --
        lateinit var mainHeader: MainHeader

        val dataStreams = ArrayList<DataStream>()

        /* Offset to each plane. */
        var offsets = ArrayList<ULong>()

        /* Number of bytes in each plane. */
        var lengths = ArrayList<UInt>()

        // -- Metadata Accessors --
        @Deprecated("use the bitmap header")
        var bmpBitsPerPixel: Short = 0

        @Deprecated("use the bitmap header")
        var bmpCompression = 0

        @Deprecated("use the bitmap header")
        var bmpScanLineSize = 0

        @Deprecated("use the bitmap header")
        var bmpColorsUsed = 0

        @Deprecated("use the bitmap header")
        var bmpWidth = 0
        var bytesPerPlane = 0
        var lut: ColorTable? = null

        // -- Cached plane --
        //        private var lastPlane: ByteArrayPlane? = null
        //        var lastPlaneIndex: Long = 0
        //        var lastDimensions: IntArray?
        //
        //        fun getLastPlane(): ByteArrayPlane? {
        //            return lastPlane
        //        }
        //
        //        val lastPlaneBytes: ByteArray?
        //            get() = if (lastPlane == null) null else lastPlane.getBytes()
        //
        //        fun setLastPlane(lastPlane: ByteArrayPlane?) {
        //            this.lastPlane = lastPlane
        //        }

        class DataStream(handle: ReadOnlyFileHandle) {
            val header = Header(handle)
            lateinit var format: BitmapInfoHeader

            class Header(handle: ReadOnlyFileHandle) {
                val fccType = handle utf8 4
                val fccHandler = handle utf8 4
                val flags = handle.ui
                val priority = handle.us
                val language = handle.us
                val initialFrames = handle.ui
                val scale = handle.ui

                /* dwRate / dwScale == samples/second */
                val rate = handle.ui
                val start = handle.ui

                /* In units above... */
                val length = handle.ui
                val suggestedBufferSize = handle.ui
                val quality = handle.ui
                val sampleSize = handle.ui
                val frame = Rect(handle)

                //            globalTable["Stream quality"] = buf.i
                //            meta.bytesPerPlane = buf.i
                //            globalTable["Stream sample size"] = meta.bytesPerPlane
            }

            class BitmapInfoHeader(handle: ReadOnlyFileHandle) {
                val size = handle.ui
                val width = handle.i
                val height = handle.i
                val planes = handle.us
                val bitCount = handle.us
                val compression = handle.ui
                val sizeImage = handle.ui
                val xPelspermeter = handle.i
                val yPelsPerMeter = handle.i
                var clrUsed = handle.ui
                val clrImportant = handle.ui

                //                meta.bmpWidth = buf.i
                //                /*m.addAxis(Axes.Y,*/ buf.i/*)*/
                //                buf.skip(2)
                //                meta.bmpBitsPerPixel = buf.i16
                //                meta.bmpCompression = buf.i
                //                buf.skip(4)
                //                globalTable["Horizontal resolution"] = buf.i
                //                globalTable["Vertical resolution"] = buf.i
                //                meta.bmpColorsUsed = buf.i
                //                buf.skip(4)
                //                globalTable["Bitmap compression value"] = meta.bmpCompression
                //                globalTable["Number of colors used"] = meta.bmpColorsUsed
                //                globalTable["Bits per pixel"] = meta.bmpBitsPerPixel
                val scanLineSize = run {
                    // scan line is padded with zeros to be a
                    // multiple of 4 bytes
                    var npad = width % 4
                    if (npad > 0) npad = 4 - npad
                    (width + npad) * (bitCount.i / 8)
                }
            }
        }

        class Rect(handle: ReadOnlyFileHandle) {
            val left = handle.i
            val top = handle.i
            val right = handle.i
            val bottom = handle.i
        }

        // -- HasColorTable API Methods --
        override fun getColorTable(imageIndex: Int, planeIndex: Long): ColorTable? = lut

        // -- Metadata API Methods --
        fun populateImageMetadata() {
            val iMeta = get(0)
            iMeta.isLittleEndian = true
            iMeta.isFalseColor = false
            iMeta.isMetadataComplete = true

            TODO()
            // All planes are timepoints
            //            val sizeT = offsets!!.size
            //            table!!["Compression"] = AVIUtils.getCodecName(bmpCompression)
            //            iMeta.setPlanarAxisCount(2)
            //            if (bmpCompression == JPEG) {
            //                val fileOff = offsets!![0]
            //                val options = AVIUtils.createCodecOptions(this, 0, 0)
            //                var nBytes = 0
            //                try {
            //                    val x = 0
            //                    val y = 0
            //                    val w = iMeta.getAxisLength(Axes.X) as Int
            //                    val h = iMeta.getAxisLength(Axes.Y) as Int
            //                    nBytes = AVIUtils.extractCompression(this, options, getSource(), null,
            //                                                         0, intArrayOf(x, y, w, h))!!.size / (w * h)
            //                } catch (e: java.io.IOException) {
            //                    log().error("IOException while decompressing", e)
            //                } catch (e: FormatException) {
            //                    log().error("FormatException while decompressing", e)
            //                }
            //                try {
            //                    getSource().seek(fileOff)
            //                } catch (e: java.io.IOException) {
            //                    log().error("Error seeking to position: $fileOff", e)
            //                }
            //                if (bmpCompression == 16) {
            //                    nBytes /= 2
            //                }
            //                if (nBytes > 1) {
            //                    iMeta.addAxis(Axes.CHANNEL, nBytes)
            //                    iMeta.setPlanarAxisCount(3)
            //                }
            //            } else if (bmpBitsPerPixel.toInt() == 32) {
            //                iMeta.addAxis(Axes.CHANNEL, 4)
            //                iMeta.setPlanarAxisCount(3)
            //            } else if (bytesPerPlane == 0 || bmpBitsPerPixel.toInt() == 24) {
            //                if (bmpBitsPerPixel > 8 || bmpCompression != 0 && getColorTable(0, 0) == null) {
            //                    iMeta.addAxis(Axes.CHANNEL, 3)
            //                    iMeta.setPlanarAxisCount(3)
            //                }
            //            } else if (bmpCompression == MS_VIDEO) {
            //                iMeta.addAxis(Axes.CHANNEL, 3)
            //                iMeta.setPlanarAxisCount(3)
            //            } else {
            //                val sizeC: Long = bytesPerPlane / (iMeta.getAxisLength(Axes.X) *
            //                        iMeta.getAxisLength(Axes.Y) * (bmpBitsPerPixel / 8))
            //                if (sizeC > 1) {
            //                    iMeta.addAxis(Axes.CHANNEL, sizeC)
            //                    iMeta.setPlanarAxisCount(3)
            //                }
            //            }
            //            if (getColorTable(0, 0) != null && !iMeta.isMultichannel()) {
            //                iMeta.isIndexed = true
            //                iMeta.addAxis(Axes.CHANNEL, 1)
            //                iMeta.setPlanarAxisCount(3)
            //                iMeta.setAxisType(2, Axes.CHANNEL)
            //            }
            //            if (bmpBitsPerPixel <= 8) {
            //                iMeta.pixelType = FormatTools.UINT8
            //            } else if (bmpBitsPerPixel.toInt() == 16) iMeta.pixelType = FormatTools.UINT16 else if (bmpBitsPerPixel.toInt() == 24 || bmpBitsPerPixel.toInt() == 32) {
            //                iMeta.pixelType = FormatTools.UINT8
            //            } else {
            //                log().error("Unknown matching for pixel bit width of: " +
            //                                    bmpBitsPerPixel)
            //            }
            //            if (bmpCompression != 0) iMeta.pixelType = FormatTools.UINT8
            //            var effectiveWidth = bmpScanLineSize / (bmpBitsPerPixel / 8)
            //            if (effectiveWidth == 0) {
            //                effectiveWidth = iMeta.getAxisLength(Axes.X) as Int
            //            }
            //            if (effectiveWidth < iMeta.getAxisLength(Axes.X)) {
            //                iMeta.setAxisLength(Axes.X, effectiveWidth)
            //            }
            //            if (bmpBitsPerPixel.toInt() != 16 && iMeta.isMultichannel()) {
            //                iMeta.setAxisTypes(Axes.CHANNEL, Axes.X, Axes.Y)
            //            }
            //            iMeta.addAxis(Axes.TIME, sizeT)
        }

        // -- HasSource API Methods --
        @Throws(IOException::class)
        override fun close(fileOnly: Boolean) {
            super.close(fileOnly)
            if (!fileOnly) {
                //                lastPlane = null
                //                lastPlaneIndex = -1
                //                bmpScanLineSize = 0
                //                bmpCompression = bmpScanLineSize
                //                bmpWidth = bmpCompression
                //                bmpColorsUsed = bmpWidth
                //                bmpBitsPerPixel = 0
                bytesPerPlane = 0
                //                offsets = null
                //                lengths = null
                lut = null
            }
        }

        override fun generateImageMetadata(imageIndex: Int): ImageMetadata? {
            TODO("Not yet implemented")
        }

        override val format: Format
            get() = TODO("Not yet implemented")
    }

    class Checker : AbstractChecker() {
        // -- Checker API Methods --
        fun suffixNecessary(): Boolean {
            return false
        }

        @Throws(IOException::class)
        override fun isFormat(handle: /*DataHandle<org.scijava.io.location.Location?>*/FileHandle): Boolean {
            TODO()
            //            val blockLen = 12
            //            if (!FormatTools.validStream(handle, blockLen, false)) return false
            //            val type: String = handle.readString(4)
            //            handle.skipBytes(4)
            //            val format: String = handle.readString(4)
            //            return type == AVI_MAGIC_STRING && format == "AVI "
        }

        override val format: Format
            get() = TODO("Not yet implemented")

        companion object {
            // -- Constants --
            const val AVI_MAGIC_STRING = "RIFF"
        }
    }

    class Parser : AbstractParser<Metadata>() {

        // -- Parser API Methods --
        @Throws(IOException::class, FormatException::class)
                /*protected*/
        fun typedParse(handle: /*DataHandle<org.scijava.io.location.Location?>*/ReadOnlyFileHandle,
                       meta: Metadata, config: SCIFIOConfig = SCIFIOConfig()) {
            handle.bigEndian = false
            //            log().info("Verifying AVI format")
            //            meta.lastPlaneIndex = -1
            //            meta.lengths = java.util.ArrayList<Long>()
            //            meta.offsets = java.util.ArrayList<Long>()
            //            meta.createImageMetadata(1)
            while (handle.rem >= 8u)
            // NB: size x and size y are implicitly set here
                readChunk(meta, handle)
            //            log().info("Populating metadata")
        }

        @Throws(FormatException::class, IOException::class)
        private fun readChunk(meta: Metadata, handle: ReadOnlyFileHandle) {

            var type = ""
            var fcc = ""
            var cb = 0u

            fun readTypeAndSize() {
                type = handle utf8 4
                cb = handle.ui
            }

            fun readChunkHeader() {
                readTypeAndSize()
                fcc = handle utf8 4
            }

            readChunkHeader()
            //            val m = meta[0]
            val globalTable = meta.table
            //            val source: DataHandle<org.scijava.io.location.Location> = getSource()
            when {
                type == "RIFF" ->
                    if (!fcc.startsWith("AVI"))
                        throw FormatException("Sorry, AVI RIFF format not found.")
                handle.pos == 12uL -> throw FormatException("Not an AVI file")
                else -> {
                    if (cb - 4u <= handle.rem)
                        handle + (cb - 4u)
                    return
                }
            }
            var pos = handle.pos
            var spos: ULong
            //            log().info("Searching for image data")
            while (handle.rem > 4u) {
                val listString = handle utf8 4
                if (listString == "RIFF") {
                    handle -= 4u
                    return
                }
                handle.pos = pos
                when (listString) {
                    " JUN" -> {
                        handle + 1
                        pos++
                    }
                    "JUNK" -> {
                        readTypeAndSize()
                        if (type == "JUNK")
                            handle + cb
                    }
                    "LIST" -> {
                        spos = handle.pos
                        readChunkHeader()
                        handle.pos = spos
                        when (fcc) {
                            "hdrl" -> {
                                readChunkHeader()
                                if (type != "LIST" || fcc != "hdrl")
                                    continue
                                readTypeAndSize()
                                if (type != "avih")
                                    continue
                                spos = handle.pos
                                meta.mainHeader = MainHeader(handle)
                                if (spos + cb <= handle.size)
                                    handle.pos = spos + cb
                            }
                            "strl" -> {
                                val startPos = handle.pos
                                val streamSize = cb
                                readChunkHeader()
                                if (type == "LIST") {
                                    if (fcc == "strl") {
                                        readTypeAndSize()
                                        if (type == "strh") {
                                            spos = handle.pos
                                            meta.dataStreams += Metadata.DataStream(handle)
                                            if (spos + cb <= handle.size)
                                                handle.pos = spos + cb
                                        }
                                        readTypeAndSize()
                                        if (type == "strf") {
                                            spos = handle.pos
                                            //                                            if (meta[0].getAxisIndex(Axes.Y) !== -1) {
                                            //                                                source.skipBytes(size)
                                            //                                                readTypeAndSize()
                                            //                                                while (type == "indx") {
                                            //                                                    source.skipBytes(size)
                                            //                                                    readTypeAndSize()
                                            //                                                }
                                            //                                                pos = source.offset() - 4
                                            //                                                source.seek(pos - 4)
                                            //                                                continue
                                            //                                            }
                                            val bmpFormat = Metadata.DataStream.BitmapInfoHeader(handle)
                                            meta.dataStreams.last().format = bmpFormat

                                            var bmpActualColorsUsed = 0u
                                            if (bmpFormat.clrUsed != 0u)
                                                bmpActualColorsUsed = bmpFormat.clrUsed
                                            else if (bmpFormat.bitCount < 16u) {
                                                // a value of 0 means we determine this based on the bits per pixel
                                                bmpActualColorsUsed = 1u shl bmpFormat.bitCount.i
                                                bmpFormat.clrUsed = bmpActualColorsUsed
                                            }
                                            val cmpr = bmpFormat.compression
                                            if (cmpr != MSRLE && cmpr != 0u && cmpr != MS_VIDEO && cmpr != JPEG && cmpr != Y8)
                                                throw UnsupportedCompressionException("$cmpr not supported")
                                            val bpp = bmpFormat.bitCount.i
                                            if (bpp != 4 && bpp != 8 && bpp != 24 && bpp != 16 && bpp != 32)
                                                throw FormatException("$bpp bits per pixel not supported")
                                            if (bmpActualColorsUsed != 0u) {
                                                // read the palette
                                                val lut = Array(3) { ByteArray(bmpFormat.clrUsed.i) }
                                                for (i in 0..<bmpFormat.clrUsed.i)
                                                    if (bmpFormat.compression != Y8) {
                                                        lut[2][i] = handle.i8
                                                        lut[1][i] = handle.i8
                                                        lut[0][i] = handle.i8
                                                        handle + 1
                                                    } else {
                                                        lut[0][i] = i.i8
                                                        lut[1][i] = i.i8
                                                        lut[2][i] = i.i8
                                                    }
                                                meta.lut = ColorTable8(lut[0], lut[1], lut[2])
                                            }
                                            handle.pos = spos + cb
                                        }
                                    }
                                    spos = handle.pos
                                    readTypeAndSize()
                                    if (type == "strd")
                                        handle + cb
                                    else
                                        handle.pos = spos
                                    spos = handle.pos
                                    readTypeAndSize()
                                    if (type == "strn" || type == "indx")
                                        handle + cb
                                    else
                                        handle.pos = spos
                                }
                                if (startPos + streamSize + 8u <= handle.size)
                                    handle.pos = startPos + 8u + streamSize
                            }
                            "movi" -> {
                                readChunkHeader()
                                if (type != "LIST" || fcc != "movi")
                                    continue
                                spos = handle.pos
                                if (spos >= handle.size - 12u) break
                                readChunkHeader()
                                if (!(type == "LIST" && (fcc == "rec " || fcc == "movi")))
                                    handle.pos = spos
                                spos = handle.pos
                                var end = false
                                while (!end) {
                                    readTypeAndSize()
                                    val oldType = type
                                    while (type.startsWith("ix") || type.endsWith("tx") || (type == "JUNK")) {
                                        handle + cb
                                        readTypeAndSize()
                                    }
                                    var check = type.substring(2)
                                    var foundPixels = false
                                    while (check == "db" || check == "dc" || (check == "wb")) {
                                        foundPixels = true
                                        if (check.startsWith("d")) {
                                            if (cb > 0u || meta.bmpCompression != 0) {
                                                meta.offsets += handle.pos
                                                meta.lengths += cb
                                                handle + cb
                                            }
                                        }
                                        spos = handle.pos
                                        if (spos + 8u >= handle.size) return
                                        readTypeAndSize()
                                        if (type == "JUNK") {
                                            handle + cb
                                            spos = handle.pos
                                            if (spos + 8u >= handle.size) return
                                            readTypeAndSize()
                                        }
                                        check = type.substring(2)
                                        if (check == "0d") {
                                            handle.pos = spos + 1u
                                            readTypeAndSize()
                                            check = type.substring(2)
                                        }
                                    }
                                    handle.pos = spos
                                    if (!oldType.startsWith("ix") && !foundPixels)
                                        end = true
                                }
                            }
                            else -> {
                                val oldSize = cb
                                cb = handle.ui - 8u
                                if (cb > oldSize) {
                                    cb = oldSize
                                    handle -= 4u
                                }
                                // skipping unknown block
                                if (cb + 8u >= 0u) handle + (8u + cb)
                            }
                        }
                    }
                    else -> {
                        // skipping unknown block
                        readTypeAndSize()
                        if (8u < handle.rem && type != "idx1")
                            readTypeAndSize()
                        else if (type != "idx1") break
                        if (cb + 4u <= handle.rem)
                            handle + cb
                        if (type == "idx1") break
                    }
                }
                pos = handle.pos
            }
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

    class Reader : ByteArrayReader<Metadata>() {
        // -- AbstractReader API Methods --
        protected fun createDomainArray(): Array<String> {
            return arrayOf(FormatTools.GRAPHICS_DOMAIN)
        }

        // -- Reader API Methods --
        //        @Throws(FormatException::class, IOException::class)
        //        fun openPlane(imageIndex: Int, planeIndex: Long,
        //                                                                                  plane: ByteArrayPlane, bounds: Interval,
        //                                                                                  config: SCIFIOConfig?): ByteArrayPlane {
        //            val buf: ByteArray = plane.getBytes()
        //            val meta: Metadata = getMetadata()
        //            FormatTools.checkPlaneForReading(meta, imageIndex, planeIndex, buf.size,
        //                                             bounds)
        //            plane.setColorTable(meta.getColorTable(0, 0))
        //            val bytes = getBytesPerPixel(meta[imageIndex]
        //                                             .pixelType)
        //            val p = meta.bmpScanLineSize.toDouble() / meta.bmpBitsPerPixel
        //            var effectiveWidth = (meta.bmpScanLineSize / p).toInt()
        //            if (effectiveWidth == 0 || effectiveWidth < meta[imageIndex]
        //                    .getAxisLength(Axes.X)) {
        //                effectiveWidth = meta[imageIndex].getAxisLength(Axes.X) as Int
        //            }
        //            val xAxis: Int = meta[imageIndex].getAxisIndex(Axes.X)
        //            val yAxis: Int = meta[imageIndex].getAxisIndex(Axes.Y)
        //            val x = bounds.min(xAxis).toInt()
        //            val y = bounds.min(yAxis).toInt()
        //            //
        //            val w = bounds.dimension(xAxis).toInt()
        //            val h = bounds.dimension(yAxis).toInt()
        //            val fileOff = meta.offsets[planeIndex.toInt()]
        //            val end = if (planeIndex < meta.offsets.size - 1) meta.offsets[planeIndex.toInt() + 1] else getHandle().length()
        //            val maxBytes = end - fileOff
        //            getHandle().seek(fileOff)
        //            if (meta.bmpCompression != 0 && meta.bmpCompression != Y8) {
        //                uncompress(imageIndex, planeIndex, plane, x, y, w, h)
        //                return plane
        //            }
        //            if (meta.bmpBitsPerPixel < 8) {
        //                var rawSize = FormatTools.getPlaneSize(meta, effectiveWidth,
        //                                                       meta[imageIndex].getAxisLength(Axes.Y) as Int, imageIndex) as Int
        //                rawSize /= 8 / meta.bmpBitsPerPixel
        //                val b = ByteArray(rawSize)
        //                val len = rawSize / meta[imageIndex].getAxisLength(
        //                    Axes.Y) as Int
        //                getHandle().read(b)
        //                val bb = BitBuffer(b)
        //                bb.skipBits(meta.bmpBitsPerPixel * len * (meta[imageIndex]
        //                    .getAxisLength(Axes.Y) - h - y))
        //                for (row in h downTo y) {
        //                    bb.skipBits(meta.bmpBitsPerPixel * x)
        //                    for (col in 0 until len) {
        //                        buf[(row - y) * len + col] = bb.getBits(meta.bmpBitsPerPixel) as Byte
        //                    }
        //                    bb.skipBits(meta.bmpBitsPerPixel * meta[imageIndex]
        //                        .getAxisLength(Axes.X) - w - x)
        //                }
        //                return plane
        //            }
        //            val pad = (meta.bmpScanLineSize / meta[imageIndex]
        //                .getAxisLength(Axes.CHANNEL) - meta[imageIndex].getAxisLength(
        //                Axes.X) * bytes) as Int
        //            val scanline = w * bytes * (if (meta[imageIndex]
        //                    .getInterleavedAxisCount() > 0) meta[imageIndex].getAxisLength(
        //                Axes.CHANNEL) else 1)
        //            getHandle().skipBytes(((meta[imageIndex].getAxisLength(Axes.X) +
        //                    pad) * (meta.bmpBitsPerPixel / 8) * (meta[imageIndex]
        //                .getAxisLength(Axes.Y) - h - y)) as Int)
        //            if (meta[imageIndex].getAxisLength(Axes.X) === w && pad == 0) {
        //                for (row in 0 until meta[imageIndex].getAxisLength(
        //                    Axes.Y)) {
        //                    getHandle().read(buf, (if (meta.bmpCompression == Y8) row else meta[imageIndex].getAxisLength(Axes.Y) - row - 1) * scanline, scanline)
        //                }
        //
        //                // swap channels
        //                if (meta.bmpBitsPerPixel.toInt() == 24 || meta.bmpBitsPerPixel.toInt() == 32) {
        //                    for (i in 0 until buf.size / meta[imageIndex].getAxisLength(
        //                        Axes.CHANNEL)) {
        //                        val r = buf[i * meta[imageIndex].getAxisLength(
        //                            Axes.CHANNEL) as Int + 2]
        //                        buf[i * meta[imageIndex].getAxisLength(Axes.CHANNEL) as Int +
        //                                2] = buf[i * meta[imageIndex].getAxisLength(
        //                            Axes.CHANNEL) as Int]
        //                        buf[i * meta[imageIndex].getAxisLength(Axes.CHANNEL) as Int] = r
        //                    }
        //                }
        //            } else {
        //                var skip = FormatTools.getPlaneSize(meta, meta[imageIndex].getAxisLength(Axes.X) as Int - w - x + pad, 1, imageIndex) as Int
        //                if ((meta[imageIndex].getAxisLength(Axes.X) + pad) * meta[imageIndex].getAxisLength(Axes.Y) * meta[imageIndex]
        //                        .getAxisLength(Axes.CHANNEL) > maxBytes) {
        //                    skip /= meta[imageIndex].getAxisLength(Axes.CHANNEL)
        //                }
        //                for (i in h - 1 downTo 0) {
        //                    getHandle().skipBytes(x * (meta.bmpBitsPerPixel / 8))
        //                    getHandle().read(buf, i * scanline, scanline)
        //                    if (meta.bmpBitsPerPixel.toInt() == 24) {
        //                        for (j in 0 until w) {
        //                            val r = buf[i * scanline + j * 3 + 2]
        //                            buf[i * scanline + j * 3 + 2] = buf[i * scanline + j * 3]
        //                            buf[i * scanline + j * 3] = r
        //                        }
        //                    }
        //                    if (i > 0) getHandle().skipBytes(skip)
        //                }
        //            }
        //            if (meta.bmpBitsPerPixel.toInt() == 16 && meta[imageIndex]
        //                    .isMultichannel()) {
        //                // channels are stored as BGR, need to swap them
        //                ImageTools.bgrToRgb(plane.getBytes(), meta[imageIndex]
        //                    .getInterleavedAxisCount() > 0, 2, meta[imageIndex]
        //                                        .getAxisLength(Axes.CHANNEL) as Int)
        //            }
        //            return plane
        //        }

        // -- Helper methods --
        //        @Throws(FormatException::class, java.io.IOException::class) private fun uncompress(imageIndex: Int,
        //                                                                                           planeIndex: Long, plane: ByteArrayPlane, x: Int,
        //                                                                                           y: Int, w: Int, h: Int): ByteArrayPlane {
        //            val meta: Metadata = getMetadata()
        //            var buf: ByteArray? = null
        //            if (haveCached(meta, planeIndex, x, y, w, h)) {
        //                buf = meta.getLastPlane().getBytes()
        //            } else {
        //                val options = AVIUtils.createCodecOptions(meta,
        //                                                          imageIndex, planeIndex)
        //
        //                // if not full plane, open the full plane and then decompress
        //                val bounds: Interval =  //
        //                    FinalInterval(meta[imageIndex].getAxesLengthsPlanar())
        //                val tmpPlane: ByteArrayPlane = createPlane(bounds)
        //
        //                // If our last cached plane was of insufficient size for the
        //                // requested
        //                // region, we need to open it as a full plan.
        //                if (meta.lastDimensions != null && !sufficientRegion(meta, x, y, w,
        //                                                                     h)) {
        //                    val lastPlane = meta.lastPlaneIndex
        //                    // Reset last plane information
        //                    meta.lastDimensions = null
        //                    meta.setLastPlane(null)
        //                    meta.lastPlaneIndex = -1
        //                    // Open the full last plane again
        //                    openPlane(imageIndex, lastPlane, tmpPlane)
        //                    options.previousImage = meta.lastPlaneBytes!!
        //                }
        //                if (options.previousImage == null && meta.bmpCompression != JPEG) {
        //                    while (meta.lastPlaneIndex < planeIndex - 1) {
        //                        openPlane(imageIndex, meta.lastPlaneIndex + 1, tmpPlane)
        //                    }
        //                    options.previousImage = meta.lastPlaneBytes!!
        //                }
        //                buf = AVIUtils.extractCompression(meta, options, getHandle(), tmpPlane,
        //                                                  planeIndex, intArrayOf(x, y, w, h))
        //            }
        //            val rowLen = FormatTools.getPlaneSize(meta, w, 1, imageIndex) as Int
        //            val bytes = getBytesPerPixel(meta[imageIndex]
        //                                             .pixelType)
        //            val inputRowLen = FormatTools.getPlaneSize(meta, meta[imageIndex].getAxisLength(Axes.X) as Int, 1, imageIndex) as Int
        //            for (row in 0 until h) {
        //                java.lang.System.arraycopy(buf, (row + y) * inputRowLen + x * bytes, plane
        //                    .getBytes(), row * rowLen, rowLen)
        //            }
        //            return plane
        //        }

        //        private fun sufficientRegion(meta: Metadata, x: Int,
        //                                     y: Int, w: Int, h: Int): Boolean {
        //            var cached = true
        //            val dims = meta.lastDimensions
        //            if (dims == null) {
        //                cached = false
        //            } else {
        //                cached = cached && dims[0] <= x
        //                cached = cached && dims[1] <= y
        //                cached = cached && dims[2] + dims[0] >= x + w
        //                cached = cached && dims[3] + dims[1] >= y + h
        //            }
        //            return cached
        //        }
        //
        //        private fun haveCached(meta: Metadata, planeIndex: Long,
        //                               x: Int, y: Int, w: Int, h: Int): Boolean {
        //            var cached = true
        //            cached = cached && meta.lastPlaneIndex == planeIndex
        //            cached = cached && sufficientRegion(meta, x, y, w, h)
        //            return cached
        //        }
    }

    class Writer : AbstractWriter<Metadata>() {
        // -- Fields --
        private var planesWritten = 0
        private var bytesPerPixel = 0
        private var xDim = 0
        private var yDim = 0
        private var zDim = 0
        private var tDim = 0
        private var xPad = 0
        private var microSecPerFrame = 0
        private var savedbLength: MutableList<Long>? = null
        private var idx1Pos: Long = 0
        private var endPos: Long = 0
        private var saveidx1Length: Long = 0

        override val format: Format
            get() = TODO("Not yet implemented")

        //        @org.scijava.plugin.Parameter
        //        private val dataHandleService: DataHandleService? = null
        //
        //        // -- AbstractWriter Methods --
        override fun makeCompressionTypes(): Array<String> {
            TODO()
            //            return arrayOfNulls(0)
        }

        // -- Writer API Methods --
        //        @Throws(FormatException::class, java.io.IOException::class) fun writePlane(imageIndex: Int, planeIndex: Long,
        //                                                                                   plane: Plane, bounds: Interval?) {
        //            val meta: Metadata = metadata
        //            val buf: ByteArray = plane.getBytes()
        //            val interleaved: Boolean = plane.getImageMetadata()
        //                .getInterleavedAxisCount() > 0
        //            checkParams(imageIndex, planeIndex, buf, bounds!!)
        //            if (!SCIFIOMetadataTools.wholePlane(imageIndex, meta, bounds)) {
        //                throw FormatException(
        //                    "AVIWriter does not yet support saving image tiles.")
        //            }
        //            val nChannels = plane.getImageMetadata().getAxisLength(
        //                Axes.CHANNEL) as Int
        //
        //            // Write the data. Each 3-byte triplet in the bitmap array represents the
        //            // relative intensities of blue, green, and red, respectively, for a
        //            // pixel.
        //            // The color bytes are in reverse order from the Windows convention.
        //            val width = xDim - xPad
        //            val height = buf.size / (width * bytesPerPixel)
        //            val handle: DataHandle<org.scijava.io.location.Location> = getHandle()
        //            handle.seek(idx1Pos)
        //            handle.writeBytes(DATA_SIGNATURE)
        //            savedbLength!!.add(handle.offset())
        //
        //            // Write the data length
        //            handle.writeInt(bytesPerPixel * xDim * yDim)
        //            val rowPad = xPad * bytesPerPixel
        //            val rowBuffer = ByteArray(width * bytesPerPixel + rowPad)
        //            for (row in height - 1 downTo 0) {
        //                for (col in 0 until width) {
        //                    var offset = row * width + col
        //                    if (interleaved) offset *= nChannels
        //                    val r = buf[offset]
        //                    if (nChannels > 1) {
        //                        val g = buf[offset + (if (interleaved) 1 else width * height)]
        //                        var b: Byte = 0
        //                        if (nChannels > 2) {
        //                            b = buf[offset + (if (interleaved) 2 else 2 * width * height)]
        //                        }
        //                        rowBuffer[col * bytesPerPixel] = b
        //                        rowBuffer[col * bytesPerPixel + 1] = g
        //                    }
        //                    rowBuffer[col * bytesPerPixel + bytesPerPixel - 1] = r
        //                }
        //                handle.write(rowBuffer)
        //            }
        //            planesWritten++
        //
        //            // Write the idx1 CHUNK
        //            // Write the 'idx1' signature
        //            idx1Pos = handle.offset()
        //            handle.seek(SAVE_LIST2_SIZE)
        //            handle.writeInt((idx1Pos - (SAVE_LIST2_SIZE + 4)).toInt())
        //            handle.seek(idx1Pos)
        //            handle.writeBytes("idx1")
        //            saveidx1Length = handle.offset()
        //
        //            // Write the length of the idx1 CHUNK not including the idx1
        //            // signature
        //            handle.writeInt(4 + planesWritten * 16)
        //            for (z in 0 until planesWritten) {
        //                // In the ckid field write the 4 character code to identify the
        //                // chunk
        //                // 00db or 00dc
        //                handle.writeBytes(DATA_SIGNATURE)
        //                // Write the flags - select AVIIF_KEYFRAME
        //                if (z == 0) handle.writeInt(0x10) else handle.writeInt(0x00)
        //
        //                // AVIIF_KEYFRAME 0x00000010L
        //                // The flag indicates key frames in the video sequence.
        //                // Key frames do not need previous video information to be
        //                // decompressed.
        //                // AVIIF_NOTIME 0x00000100L The CHUNK does not influence video
        //                // timing
        //                // (for example a palette change CHUNK).
        //                // AVIIF_LIST 0x00000001L Marks a LIST CHUNK.
        //                // AVIIF_TWOCC 2L
        //                // AVIIF_COMPUSE 0x0FFF0000L These bits are for compressor use.
        //                handle.writeInt((savedbLength!![z] - 4 - SAVE_MOVI).toInt())
        //
        //                // Write the offset (relative to the 'movi' field) to the
        //                // relevant
        //                // CHUNK. Write the length of the relevant CHUNK. Note that this
        //                // length
        //                // is also written at savedbLength
        //                handle.writeInt(bytesPerPixel * xDim * yDim)
        //            }
        //            endPos = handle.offset()
        //            handle.seek(SAVE_FILE_SIZE)
        //            handle.writeInt((endPos - (SAVE_FILE_SIZE + 4)).toInt())
        //            handle.seek(saveidx1Length)
        //            handle.writeInt((endPos - (saveidx1Length + 4)).toInt())
        //
        //            // write the total number of planes
        //            handle.seek(FRAME_OFFSET)
        //            handle.writeInt(planesWritten)
        //            handle.seek(FRAME_OFFSET_2)
        //            handle.writeInt(planesWritten)
        //        }

        override fun canDoStacks(): Boolean = true

        override fun getPixelTypes(codec: String): IntArray = intArrayOf(FormatTools.UINT8)

        @Throws(IOException::class)
        override fun close() {
            super.close()
            planesWritten = 0
            bytesPerPixel = 0
            xPad = 0
            tDim = xPad
            zDim = tDim
            yDim = zDim
            xDim = yDim
            microSecPerFrame = 0
            savedbLength = null
            idx1Pos = 0
            endPos = 0
            saveidx1Length = 0
        }

        //        @Throws(FormatException::class, java.io.IOException::class) fun setDest(out: DataHandle<org.scijava.io.location.Location?>, imageIndex: Int,
        //                                                                                config: SCIFIOConfig?) {
        //            super.setDest(out, imageIndex, config)
        //            savedbLength = java.util.ArrayList<Long>()
        //            val meta: Metadata = metadata
        //
        //            // FIXME is this correct behavior?
        //            if (out.length() > 0) {
        //                val `in`: DataHandle<org.scijava.io.location.Location> = dataHandleService.create<org.scijava.io.location.Location>(out.get())
        //                `in`.setOrder(DataHandle.ByteOrder.LITTLE_ENDIAN)
        //                `in`.seek(FRAME_OFFSET)
        //                planesWritten = `in`.readInt()
        //                `in`.seek(SAVE_FILE_SIZE)
        //                endPos = `in`.readInt() + SAVE_FILE_SIZE + 4
        //                `in`.seek(SAVE_LIST2_SIZE)
        //                idx1Pos = `in`.readInt() + SAVE_LIST2_SIZE + 4
        //                saveidx1Length = idx1Pos + 4
        //                if (planesWritten > 0) `in`.seek(saveidx1Length + 4)
        //                for (z in 0 until planesWritten) {
        //                    `in`.skipBytes(8)
        //                    savedbLength!!.add(`in`.readInt() + 4 + SAVE_MOVI)
        //                    `in`.skipBytes(4)
        //                }
        //                `in`.close()
        //                out.seek(idx1Pos)
        //            }
        //            out.setOrder(DataHandle.ByteOrder.LITTLE_ENDIAN)
        //            val imageMetadata = meta[imageIndex]
        //            tDim = imageMetadata.getAxisLength(Axes.Z) as Int
        //            zDim = imageMetadata.getAxisLength(Axes.TIME) as Int
        //            yDim = imageMetadata.getAxisLength(Axes.Y) as Int
        //            xDim = imageMetadata.getAxisLength(Axes.X) as Int
        //            val type = getPixelTypeString(imageMetadata
        //                                              .pixelType)
        //            val pixelType = pixelTypeFromString(type)
        //            bytesPerPixel = getBytesPerPixel(pixelType)
        //            bytesPerPixel *= imageMetadata.getAxisLength(Axes.CHANNEL)
        //            xPad = 0
        //            val xMod = xDim % 4
        //            if (xMod != 0) {
        //                xPad = 4 - xMod
        //                xDim += xPad
        //            }
        //            var lut: Array<ByteArray>? = null
        //            if (colorModel is java.awt.image.IndexColorModel) {
        //                lut = Array(4) { ByteArray(256) }
        //                val model: java.awt.image.IndexColorModel = colorModel as java.awt.image.IndexColorModel
        //                model.getReds(lut[0])
        //                model.getGreens(lut[1])
        //                model.getBlues(lut[2])
        //                model.getAlphas(lut[3])
        //            }
        //            if (out.length() == 0L) {
        //                out.writeBytes("RIFF") // signature
        //                // Bytes 4 thru 7 contain the length of the file. This length
        //                // does not include bytes 0 thru 7.
        //                out.writeInt(0) // for now write 0 for size
        //                out.writeBytes("AVI ") // RIFF type
        //                // Write the first LIST chunk, which contains
        //                // information on data decoding
        //                out.writeBytes("LIST") // CHUNK signature
        //                // Write the length of the LIST CHUNK not including the first 8
        //                // bytes
        //                // with LIST and size. Note that the end of the LIST CHUNK is
        //                // followed
        //                // by JUNK.
        //                out.writeInt(if (bytesPerPixel == 1) 1240 else 216)
        //                out.writeBytes("hdrl") // CHUNK type
        //                out.writeBytes("avih") // Write the avih sub-CHUNK
        //
        //                // Write the length of the avih sub-CHUNK (38H) not including
        //                // the
        //                // the first 8 bytes for avihSignature and the length
        //                out.writeInt(0x38)
        //
        //                // dwMicroSecPerFrame - Write the microseconds per frame
        //                microSecPerFrame = (1.0 / framesPerSecond * 1.0e6).toInt()
        //                out.writeInt(microSecPerFrame)
        //
        //                // Write the maximum data rate of the file in bytes per second
        //                out.writeInt(0) // dwMaxBytesPerSec
        //                out.writeInt(0) // dwReserved1 - set to 0
        //                // dwFlags - just set the bit for AVIF_HASINDEX
        //                out.writeInt(0x10)
        //
        //                // 10H AVIF_HASINDEX: The AVI file has an idx1 chunk containing
        //                // an index at the end of the file. For good performance, all
        //                // AVI files should contain an index.
        //                //
        //                // 20H AVIF_MUSTUSEINDEX: Index CHUNK, rather than the physical
        //                // ordering of the chunks in the file, must be used to determine
        //                // the order of the frames.
        //                //
        //                // 100H AVIF_ISINTERLEAVED: Indicates that the AVI file is
        //                // interleaved. This is used to read data from a CD-ROM more
        //                // efficiently.
        //                //
        //                // 800H AVIF_TRUSTCKTYPE: USE CKType to find key frames
        //                // 10000H AVIF_WASCAPTUREFILE: The AVI file is used for
        //                // capturing real-time video. Applications should warn the user before
        //                // writing over a file with this flag set because the user
        //                // probably defragmented this file.
        //                //
        //                // 20000H AVIF_COPYRIGHTED: The AVI file contains copyrighted
        //                // data and software. When, this flag is used, software should not
        //                // permit the data to be duplicated.
        //
        //                // dwTotalFrames - total frame number
        //                out.writeInt(0)
        //
        //                // dwInitialFrames -Initial frame for interleaved files.
        //                // Noninterleaved files should specify 0.
        //                out.writeInt(0)
        //
        //                // dwStreams - number of streams in the file - here 1 video and
        //                // zero audio.
        //                out.writeInt(1)
        //
        //                // dwSuggestedBufferSize - Suggested buffer size for reading the
        //                // file.
        //                // Generally, this size should be large enough to contain the
        //                // largest
        //                // chunk in the file.
        //                out.writeInt(0)
        //
        //                // dwWidth - image width in pixels
        //                out.writeInt(xDim - xPad)
        //                out.writeInt(yDim) // dwHeight - height in pixels
        //
        //                // dwReserved[4] - Microsoft says to set the following 4 values
        //                // to 0.
        //                out.writeInt(0)
        //                out.writeInt(0)
        //                out.writeInt(0)
        //                out.writeInt(0)
        //
        //                // Write the Stream line header CHUNK
        //                out.writeBytes("LIST")
        //
        //                // Write the size of the first LIST subCHUNK not including the
        //                // first 8
        //                // bytes with LIST and size. Note that saveLIST1subSize =
        //                // saveLIST1Size
        //                // + 76, and that the length written to saveLIST1subSize is 76
        //                // less than
        //                // the length written to saveLIST1Size. The end of the first
        //                // LIST
        //                // subCHUNK is followed by JUNK.
        //                out.writeInt(if (bytesPerPixel == 1) 1164 else 140)
        //                out.writeBytes("strl") // Write the chunk type
        //                out.writeBytes("strh") // Write the strh sub-CHUNK
        //                out.writeInt(56) // Write length of strh sub-CHUNK
        //
        //                // fccType - Write the type of data stream - here vids for video
        //                // stream
        //                out.writeBytes("vids")
        //
        //                // Write DIB for Microsoft Device Independent Bitmap.
        //                // Note: Unfortunately, at least 3 other four character codes
        //                // are
        //                // sometimes used for uncompressed AVI videos: 'RGB ', 'RAW ',
        //                // 0x00000000
        //                out.writeBytes("DIB ")
        //                out.writeInt(0) // dwFlags
        //
        //                // 0x00000001 AVISF_DISABLED The stram data should be rendered
        //                // only when
        //                // explicitly enabled.
        //                // 0x00010000 AVISF_VIDEO_PALCHANGES Indicates that a palette
        //                // change is
        //                // included in the AVI file. This flag warns the playback
        //                // software that
        //                // it will need to animate the palette.
        //
        //                // dwPriority - priority of a stream type. For example, in a
        //                // file with
        //                // multiple audio streams, the one with the highest priority
        //                // might be
        //                // the default one.
        //                out.writeInt(0)
        //
        //                // dwInitialFrames - Specifies how far audio data is skewed
        //                // ahead of
        //                // video frames in interleaved files. Typically, this is about
        //                // 0.75
        //                // seconds. In interleaved files specify the number of frames in
        //                // the
        //                // file prior to the initial frame of the AVI sequence.
        //                // Noninterleaved files should use zero.
        //                out.writeInt(0)
        //
        //                // rate/scale = samples/second
        //                out.writeInt(1) // dwScale
        //
        //                // dwRate - frame rate for video streams
        //                out.writeInt(framesPerSecond)
        //
        //                // dwStart - this field is usually set to zero
        //                out.writeInt(0)
        //
        //                // dwLength - playing time of AVI file as defined by scale and
        //                // rate
        //                // Set equal to the number of frames
        //                out.writeInt(tDim * zDim)
        //
        //                // dwSuggestedBufferSize - Suggested buffer size for reading the
        //                // stream.
        //                // Typically, this contains a value corresponding to the largest
        //                // chunk
        //                // in a stream.
        //                out.writeInt(0)
        //
        //                // dwQuality - encoding quality given by an integer between 0
        //                // and
        //                // 10,000. If set to -1, drivers use the default quality value.
        //                out.writeInt(-1)
        //
        //                // dwSampleSize #
        //                // 0 if the video frames may or may not vary in size
        //                // If 0, each sample of data(such as a video frame) must be in a
        //                // separate chunk. If nonzero, then multiple samples of data can
        //                // be
        //                // grouped into a single chunk within the file.
        //                out.writeInt(0)
        //
        //                // rcFrame - Specifies the destination rectangle for a text or
        //                // video
        //                // stream within the movie rectangle specified by the dwWidth
        //                // and
        //                // dwHeight members of the AVI main header structure. The
        //                // rcFrame member
        //                // is typically used in support of multiple video streams. Set
        //                // this
        //                // rectangle to the coordinates corresponding to the movie
        //                // rectangle to
        //                // update the whole movie rectangle. Units for this member are
        //                // pixels.
        //                // The upper-left corner of the destination rectangle is
        //                // relative to the
        //                // upper-left corner of the movie rectangle.
        //                out.writeShort(0.toShort().toInt()) // left
        //                out.writeShort(0.toShort().toInt()) // top
        //                out.writeShort(0.toShort().toInt()) // right
        //                out.writeShort(0.toShort().toInt()) // bottom
        //
        //                // Write the size of the stream format CHUNK not including the first 8
        //                // bytes for strf and the size. Note that the end of the stream format
        //                // CHUNK is followed by strn.
        //                out.writeBytes("strf") // Write the stream format chunk
        //
        //                // write the strf CHUNK size
        //                out.writeInt(if (bytesPerPixel == 1) 1068 else 44)
        //
        //                // Applications should use this size to determine which
        //                // BITMAPINFO
        //                // header structure is being used. This size includes this
        //                // biSize field.
        //                // biSize- Write header size of BITMAPINFO header structure
        //                out.writeInt(40)
        //
        //                // biWidth - image width in pixels
        //                out.writeInt(xDim)
        //
        //                // biHeight - image height in pixels. If height is positive, the
        //                // bitmap
        //                // is a bottom up DIB and its origin is in the lower left
        //                // corner. If
        //                // height is negative, the bitmap is a top-down DIB and its
        //                // origin is
        //                // the upper left corner. This negative sign feature is
        //                // supported by the
        //                // Windows Media Player, but it is not supported by PowerPoint.
        //                out.writeInt(yDim)
        //
        //                // biPlanes - number of color planes in which the data is stored
        //                // This must be set to 1.
        //                out.writeShort(1)
        //                val bitsPerPixel = if (bytesPerPixel == 3) 24 else 8
        //
        //                // biBitCount - number of bits per pixel #
        //                // 0L for BI_RGB, uncompressed data as bitmap
        //                out.writeShort(bitsPerPixel.toShort().toInt())
        //                out.writeInt(0) // biSizeImage #
        //                out.writeInt(0) // biCompression - compression type
        //                // biXPelsPerMeter - horizontal resolution in pixels
        //                out.writeInt(0)
        //                // biYPelsPerMeter - vertical resolution in pixels per meter
        //                out.writeInt(0)
        //                val nColors = 256
        //                out.writeInt(nColors)
        //
        //                // biClrImportant - specifies that the first x colors of the
        //                // color table
        //                // are important to the DIB. If the rest of the colors are not
        //                // available, the image still retains its meaning in an
        //                // acceptable
        //                // manner. When this field is set to zero, all the colors are
        //                // important,
        //                // or, rather, their relative importance has not been computed.
        //                out.writeInt(0)
        //
        //                // Write the LUTa.getExtents()[1] color table entries here. They
        //                // are
        //                // written: blue byte, green byte, red byte, 0 byte
        //                if (bytesPerPixel == 1) {
        //                    if (lut != null) {
        //                        for (i in 0..255) {
        //                            out.write(lut[2][i].toInt())
        //                            out.write(lut[1][i].toInt())
        //                            out.write(lut[0][i].toInt())
        //                            out.write(lut[3][i].toInt())
        //                        }
        //                    } else {
        //                        val lutWrite = ByteArray(4 * 256)
        //                        for (i in 0..255) {
        //                            lutWrite[4 * i] = i.toByte() // blue
        //                            lutWrite[4 * i + 1] = i.toByte() // green
        //                            lutWrite[4 * i + 2] = i.toByte() // red
        //                            lutWrite[4 * i + 3] = 0
        //                        }
        //                        out.write(lutWrite)
        //                    }
        //                }
        //                out.seek(SAVE_STRF_SIZE)
        //                out.writeInt((SAVE_STRN_POS - (SAVE_STRF_SIZE + 4)).toInt())
        //                out.seek(SAVE_STRN_POS)
        //
        //                // Use strn to provide zero terminated text string describing
        //                // the stream
        //                out.writeBytes("strn")
        //                out.writeInt(16) // Write length of strn sub-CHUNK
        //                out.writeBytes("FileAVI write  ")
        //                out.seek(SAVE_LIST1_SIZE)
        //                out.writeInt((SAVE_JUNK_SIG - (SAVE_LIST1_SIZE + 4)).toInt())
        //                out.seek(SAVE_LIST1_SUBSIZE)
        //                out.writeInt((SAVE_JUNK_SIG - (SAVE_LIST1_SUBSIZE + 4)).toInt())
        //                out.seek(SAVE_JUNK_SIG)
        //
        //                // write a JUNK CHUNK for padding
        //                out.writeBytes("JUNK")
        //                out.writeInt(PADDING_BYTES.toInt())
        //                for (i in 0 until PADDING_BYTES / 2) {
        //                    out.writeShort(0.toShort().toInt())
        //                }
        //
        //                // Write the second LIST chunk, which contains the actual data
        //                out.writeBytes("LIST")
        //                out.writeInt(4) // For now write 0
        //                out.writeBytes("movi") // Write CHUNK type 'movi'
        //                idx1Pos = out.offset()
        //            }
        //        }

        companion object {
            // -- Constants --
            private const val SAVE_MOVI: Long = 4092
            private const val SAVE_FILE_SIZE: Long = 4

            // location of length of strf CHUNK - not including the first 8 bytes
            // with
            // strf and size. strn follows the end of this CHUNK.
            private const val SAVE_STRF_SIZE: Long = 168
            private const val SAVE_STRN_POS = SAVE_STRF_SIZE + 1068
            private const val SAVE_JUNK_SIG = SAVE_STRN_POS + 24

            // location of length of CHUNK with first LIST - not including first 8
            // bytes with LIST and size. JUNK follows the end of this CHUNK
            private const val SAVE_LIST1_SIZE: Long = 16

            // location of length of CHUNK with second LIST - not including first 8
            // bytes with LIST and size. Note that saveLIST1subSize = saveLIST1Size
            // +
            // 76, and that the length size written to saveLIST2Size is 76 less than
            // that written to saveLIST1Size. JUNK follows the end of this CHUNK.
            private const val SAVE_LIST1_SUBSIZE: Long = 92
            private const val FRAME_OFFSET: Long = 48
            private const val FRAME_OFFSET_2: Long = 140
            private const val PADDING_BYTES = 4076 - SAVE_JUNK_SIG
            private const val SAVE_LIST2_SIZE: Long = 4088
            private const val DATA_SIGNATURE = "00db"
        }
    }

    //    @org.scijava.plugin.Plugin(type = io.scif.Translator::class, priority = org.scijava.Priority.LOW)
    //    class Translator : AbstractTranslator<io.scif.Metadata?, Metadata?>() {
    //        // -- Translator API methods --
    //        fun source(): java.lang.Class<out io.scif.Metadata> {
    //            return io.scif.Metadata::class.java
    //        }
    //
    //        fun dest(): java.lang.Class<out io.scif.Metadata> {
    //            return Metadata::class.java
    //        }
    //
    //        protected fun translateImageMetadata(source: List<ImageMetadata>,
    //                                             dest: Metadata) {
    //            val offsets: MutableList<Long> = java.util.ArrayList<Long>()
    //            val lengths: MutableList<Long> = java.util.ArrayList<Long>()
    //            dest.offsets = offsets
    //            dest.lengths = lengths
    //            dest.add(source[0].copy())
    //            var sizeX = source[0].getAxisLength(Axes.X) as Int
    //            val sizeY = source[0].getAxisLength(Axes.Y) as Int
    //            val bpp = source[0].bitsPerPixel
    //            var length = bpp.toLong()
    //            for (l in source[0].getAxesLengthsPlanar()) {
    //                length *= l
    //            }
    //            var offset: Long = 0
    //            val npad = sizeX % 4
    //            if (npad != 0) sizeX += 4 - npad
    //            val numChannels = source[0].getAxisLength(Axes.CHANNEL) as Int
    //            dest.bmpBitsPerPixel = (bpp * numChannels).toShort()
    //            dest.bmpWidth = sizeX * (bpp / 8)
    //            dest.bmpScanLineSize = dest.bmpWidth * numChannels
    //            try {
    //                offset = if (dest.getSource() == null) 0 else dest.getSource().offset()
    //            } catch (e: java.io.IOException) {
    //                log().error("Error retrieving AVI plane offset", e)
    //            }
    //
    //            // Channels are folded into bmpBitsPerPixel, so they should be
    //            // omitted from the plane count.
    //            val destImageMeta = dest[0]
    //            var nonplanarChannels: Long = 1
    //            if (!destImageMeta.isMultichannel()) {
    //                nonplanarChannels = numChannels.toLong()
    //                length *= numChannels.toLong()
    //            }
    //            for (i in 0 until source[0].getPlaneCount() / nonplanarChannels) {
    //                offsets.add(offset)
    //                lengths.add(length)
    //                offset += length
    //            }
    //            if (numChannels > 1) {
    //                dest.bmpColorsUsed = 2.0.pow(bpp.toDouble()) as Int
    //            }
    //            dest.bmpCompression = 0
    //            if (HasColorTable::class.java.isAssignableFrom(source.javaClass)) {
    //                val ct: ColorTable = (source as HasColorTable).getColorTable(0, 0)
    //                dest.bmpColorsUsed = ct.length
    //            }
    //            dest.bytesPerPlane = length.toInt() / 8
    //        }
    //    }

    /*
	 * Utility Helper class
	 */
    //    private object AVIUtils {
    //        fun createCodecOptions(meta: Metadata,
    //                               imageIndex: Int, planeIndex: Long): CodecOptions {
    //            val options = CodecOptions()
    //            options.width = meta[imageIndex].getAxisLength(Axes.X) as Int
    //            options.height = meta[imageIndex].getAxisLength(Axes.Y) as Int
    //            options.previousImage = if (meta.lastPlaneIndex == planeIndex - 1) meta.lastPlaneBytes!! else null
    //            options.bitsPerSample = meta.bmpBitsPerPixel.toInt()
    //            options.interleaved = meta[imageIndex].getInterleavedAxisCount() > 0
    //            options.littleEndian = meta[imageIndex].isLittleEndian
    //            return options
    //        }
    //
    //        fun getCodecName(bmpCompression: Int): String {
    //            return when (bmpCompression) {
    //                0 -> "Raw (uncompressed)"
    //                MSRLE -> "Microsoft Run-Length Encoding (MSRLE)"
    //                MS_VIDEO -> "Microsoft Video (MSV1)"
    //                JPEG -> "JPEG"
    //                else -> "Unknown"
    //            }
    //        }
    //
    //        @Throws(java.io.IOException::class, FormatException::class) fun extractCompression(meta: Metadata,
    //                                                                                           options: CodecOptions?, stream: DataHandle<org.scijava.io.location.Location?>,
    //                                                                                           plane: ByteArrayPlane?, planeIndex: Long, dims: IntArray): ByteArray {
    //            val bmpCompression = meta.bmpCompression
    //            val fileOff = meta.offsets[planeIndex.toInt()]
    //            val filePointer: Long = stream.offset()
    //            stream.seek(fileOff)
    //            var buf: ByteArray? = null
    //            val codecService: CodecService = meta.context().service(
    //                CodecService::class.java)
    //            if (bmpCompression == MSRLE) {
    //                val b = ByteArray(meta.lengths[planeIndex.toInt()].toInt())
    //                stream.read(b)
    //                val codec: MSRLECodec = codecService.getCodec(MSRLECodec::class.java)
    //                buf = codec.decompress(b, options)
    //                plane.setData(buf)
    //                if (updateLastPlane(meta, planeIndex, dims)) {
    //                    meta.setLastPlane(plane)
    //                    meta.lastPlaneIndex = planeIndex
    //                    meta.lastDimensions = dims
    //                }
    //            } else if (bmpCompression == MS_VIDEO) {
    //                val codec: MSVideoCodec = codecService.getCodec(MSVideoCodec::class.java)
    //                buf = codec.decompress(stream, options)
    //                plane.setData(buf)
    //                if (updateLastPlane(meta, planeIndex, dims)) {
    //                    meta.setLastPlane(plane)
    //                    meta.lastPlaneIndex = planeIndex
    //                    meta.lastDimensions = dims
    //                }
    //            } else if (bmpCompression == JPEG) {
    //                val codec: JPEGCodec = codecService.getCodec(JPEGCodec::class.java)
    //                var tmpPlane = ByteArray(meta.lengths[planeIndex.toInt()].toInt())
    //                stream.read(tmpPlane)
    //                val motionJPEG = String(tmpPlane, 6, 4,
    //                                        Constants.ENCODING) == "AVI1"
    //                if (motionJPEG) {
    //                    // this is Motion JPEG data
    //                    // we must manually insert the Huffman table, as Motion JPEG
    //                    // uses a fixed (but not stored) Huffman table for all
    //                    // planes
    //                    val fixedPlane = ByteArray(tmpPlane.size +
    //                                                       MJPEG_HUFFMAN_TABLE.size)
    //                    java.lang.System.arraycopy(plane.getBytes(), 0, fixedPlane, 0, 20)
    //                    java.lang.System.arraycopy(MJPEG_HUFFMAN_TABLE, 0, fixedPlane, 20,
    //                                               MJPEG_HUFFMAN_TABLE.size)
    //                    java.lang.System.arraycopy(plane.getBytes(), 20, fixedPlane, 20 +
    //                            MJPEG_HUFFMAN_TABLE.size, tmpPlane.size - 20)
    //                    tmpPlane = fixedPlane
    //                }
    //                buf = codec.decompress(tmpPlane, options)
    //                if (motionJPEG) {
    //                    // transform YCbCr data to RGB
    //                    // see http://en.wikipedia.org/wiki/YCbCr#JPEG_conversion
    //                    buf = plane.getBytes()
    //                    var i = 0
    //                    while (i < buf.size) {
    //                        val y = buf[i].toInt() and 0xff
    //                        val cb = (buf[i + 1].toInt() and 0xff) - 128
    //                        val cr = (buf[i + 2].toInt() and 0xff) - 128
    //                        var red = (y + 1.402 * cr).toInt()
    //                        var green = (y - 0.34414 * cb - 0.71414 * cr).toInt()
    //                        var blue = (y + 1.772 * cb).toInt()
    //                        if (red < 0) {
    //                            red = 0
    //                        } else if (red > 255) {
    //                            red = 255
    //                        }
    //                        if (green < 0) {
    //                            green = 0
    //                        } else if (green > 255) {
    //                            green = 255
    //                        }
    //                        if (blue < 0) {
    //                            blue = 0
    //                        } else if (blue > 255) {
    //                            blue = 255
    //                        }
    //                        buf[i] = (red and 0xff).toByte()
    //                        buf[i + 1] = (green and 0xff).toByte()
    //                        buf[i + 2] = (blue and 0xff).toByte()
    //                        i += 3
    //                    }
    //                }
    //            } else {
    //                throw UnsupportedCompressionException(bmpCompression.toString() +
    //                                                              " not supported")
    //            }
    //            stream.seek(filePointer)
    //            return buf
    //        }
    //
    //        private fun updateLastPlane(meta: Metadata,
    //                                    planeIndex: Long, dims: IntArray): Boolean {
    //            // different planes, fine to update
    //            if (meta.lastPlaneIndex != planeIndex) return true
    //
    //            // same plane.. make sure we're not overwriting a larger plane with
    //            // a
    //            // smaller one
    //            val lastDims = meta.lastDimensions
    //            val smaller = //
    //                //
    //                dims[0] > lastDims!![0] && dims[1] > lastDims[1] && dims[2] < lastDims[2] && dims[3] < lastDims[3]
    //            return !smaller
    //        }
    //    }

    companion object {
        // -- Supported compression types --
        private const val MSRLE = 1u
        private const val MS_VIDEO = 1296126531u

        // private static final int CINEPAK = 1684633187;
        private const val JPEG = 1196444237u
        private const val Y8 = 538982489u
        // -- Constants --
        /** Huffman table for MJPEG data.  */
        private val MJPEG_HUFFMAN_TABLE = byteArrayOf(0xff.i8,
                                                      0xc4.i8, 1, 0xa2.i8, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
                                                      0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                                                      0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0x10, 0, 2, 1, 3, 3, 2,
                                                      4, 3, 5, 5, 4, 4, 0, 0, 1, 0x7D, 1, 2, 3, 0, 4, 0x11, 5, 0x12, 0x21, 0x31,
                                                      0x41, 6, 0x13, 0x51, 0x61, 7, 0x22, 0x71, 0x14, 0x32, 0x81.i8,
                                                      0x91.i8, 0xa1.i8, 8, 0x23, 0x42, 0xb1.i8, 0xc1.i8, 0x15,
                                                      0x52, 0xd1.i8, 0xf0.i8, 0x24, 0x33, 0x62, 0x72, 0x82.i8, 9, 10,
                                                      0x16, 0x17, 0x18, 0x19, 0x1a, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x34,
                                                      0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48,
                                                      0x49, 0x4a, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x63, 0x64,
                                                      0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78,
                                                      0x79, 0x7a, 0x83.i8, 0x84.i8, 0x85.i8, 0x86.i8, 0x87.i8,
                                                      0x88.i8, 0x89.i8, 0x8a.i8, 0x92.i8, 0x93.i8,
                                                      0x94.i8, 0x95.i8, 0x96.i8, 0x97.i8, 0x98.i8,
                                                      0x99.i8, 0x9a.i8, 0xa2.i8, 0xa3.i8, 0xa4.i8,
                                                      0xa5.i8, 0xa6.i8, 0xa7.i8, 0xa8.i8, 0xa9.i8,
                                                      0xaa.i8, 0xb2.i8, 0xb3.i8, 0xb4.i8, 0xb5.i8,
                                                      0xb6.i8, 0xb7.i8, 0xb8.i8, 0xb9.i8, 0xba.i8,
                                                      0xc2.i8, 0xc3.i8, 0xc4.i8, 0xc5.i8, 0xc6.i8,
                                                      0xc7.i8, 0xc8.i8, 0xc9.i8, 0xca.i8, 0xd2.i8,
                                                      0xd3.i8, 0xd4.i8, 0xd5.i8, 0xd6.i8, 0xd7.i8,
                                                      0xd8.i8, 0xd9.i8, 0xda.i8, 0xe1.i8, 0xe2.i8,
                                                      0xe3.i8, 0xe4.i8, 0xe5.i8, 0xe6.i8, 0xe7.i8,
                                                      0xe8.i8, 0xe9.i8, 0xea.i8, 0xf1.i8, 0xf2.i8,
                                                      0xf3.i8, 0xf4.i8, 0xf5.i8, 0xf6.i8, 0xf7.i8,
                                                      0xf8.i8, 0xf9.i8, 0xfa.i8, 0x11, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5,
                                                      4, 4, 0, 1, 2, 0x77, 0, 1, 2, 3, 0x11, 4, 5, 0x21, 0x31, 6, 0x12, 0x41,
                                                      0x51, 7, 0x61, 0x71, 0x13, 0x22, 0x32, 0x81.i8, 8, 0x14, 0x42,
                                                      0x91.i8, 0xa1.i8, 0xb1.i8, 0xc1.i8, 9, 0x23, 0x33, 0x52,
                                                      0xf0.i8, 0x15, 0x62, 0x72, 0xd1.i8, 10, 0x16, 0x24, 0x34,
                                                      0xe1.i8, 0x25, 0xf1.i8, 0x17, 0x18, 0x19, 0x1a, 0x26, 0x27, 0x28,
                                                      0x29, 0x2a, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x43, 0x44, 0x45, 0x46,
                                                      0x47, 0x48, 0x49, 0x4a, 0x53, 0x54, 0x55.i8, 0x56.i8, 0x57.i8,
                                                      0x58.i8, 0x59.i8, 0x5a, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69,
                                                      0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x82.i8,
                                                      0x83.i8, 0x84.i8, 0x85.i8, 0x86.i8, 0x87.i8,
                                                      0x88.i8, 0x89.i8, 0x8a.i8, 0x92.i8, 0x93.i8,
                                                      0x94.i8, 0x95.i8, 0x96.i8, 0x97.i8, 0x98.i8,
                                                      0x99.i8, 0x9a.i8, 0xa2.i8, 0xa3.i8, 0xa4.i8,
                                                      0xa5.i8, 0xa6.i8, 0xa7.i8, 0xa8.i8, 0xa9.i8,
                                                      0xaa.i8, 0xb2.i8, 0xb3.i8, 0xb4.i8, 0xb5.i8,
                                                      0xb6.i8, 0xb7.i8, 0xb8.i8, 0xb9.i8, 0xba.i8,
                                                      0xc2.i8, 0xc3.i8, 0xc4.i8, 0xc5.i8, 0xc6.i8,
                                                      0xc7.i8, 0xc8.i8, 0xc9.i8, 0xca.i8, 0xd2.i8,
                                                      0xd3.i8, 0xd4.i8, 0xd5.i8, 0xd6.i8, 0xd7.i8,
                                                      0xd8.i8, 0xd9.i8, 0xda.i8, 0xe2.i8, 0xe3.i8,
                                                      0xe4.i8, 0xe5.i8, 0xe6.i8, 0xe7.i8, 0xe8.i8,
                                                      0xe9.i8, 0xea.i8, 0xf2.i8, 0xf3.i8, 0xf4.i8,
                                                      0xf5.i8, 0xf6.i8, 0xf7.i8, 0xf8.i8, 0xf9.i8,
                                                      0xfa.i8)
    }
}
