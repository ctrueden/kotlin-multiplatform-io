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
package io.scif.formats.apng

import io.scif.*
//import io.scif.gui.AWTImageTools
//import io.scif.gui.BufferedImageReader
import io.scif.util.FormatTools
import okio.*
import platform.FormatException
import uns.i8
//import net.imglib2.display.ColorTable8
//import org.scijava.Priority
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.handle.DataHandleInputStream
//import org.scijava.io.handle.DataHandleService
//import org.scijava.io.location.BytesLocation
//import org.scijava.io.location.Location
//import org.scijava.plugin.Parameter
//import org.scijava.plugin.Plugin
//import org.scijava.util.Bytes
import kotlin.jvm.Transient

/**
 * SCIFIO Format supporting the
 * [PNG](http://www.libpng.org/pub/png/spec/) and
 * [APNG](https://wiki.mozilla.org/APNG_Specification) image formats.
 *
 * @author Mark Hiner
 */
//@Plugin(type = Format::class, name = "Animated PNG")
class APNGFormat : AbstractFormat() {
    // -- AbstractFormat Methods --
    override fun makeSuffixArray(): Array<String> = arrayOf("png")

    // -- Nested Classes --
    /**
     * File format SCIFIO Metadata for Animated Portable Network Graphics (APNG)
     * images.
     */
    class Metadata : AbstractMetadata() {
        // -- Fields --
        // APNG Chunks
        @Transient
        var idat: MutableList<IDATChunk> = ArrayList()

        @Transient
        var fctl: MutableList<FCTLChunk> = ArrayList()

        var actl: ACTLChunk? = null

        var ihdr: IHDRChunk? = null

        var plte: PLTEChunk? = null

        var iend: IENDChunk? = null

        /**
         * @return true iff the first frame is not part of the animation.
         */
        /**
         * Sets whether or not the first (default) frame is part of the animation or
         * not.
         */
        // true if the default image is not part of the animation
        var isSeparateDefault: Boolean = false

        /**
         * @return true iff this dataset's pixel type is signed
         */
        /**
         * Sets whether this dataset's pixel type is signed or not.
         */
        // true if the pixel bits are signed
        var isSigned: Boolean = false

        /**
         * @return true iff this dataset is littleEndian. APNG is typically big
         * endian by default/by spec.
         */
        /**
         * Sets the littleEndian flag on this metadata. Used to preserve endianness
         * in translation.
         */
        // True if this dataset is little endian
        var isLittleEndian: Boolean = false

        // -- APNGMetadata API Methods --

        // -- Metadata API Methods --
        fun populateImageMetadata() {
            //            createImageMetadata(1)

            val imageMeta = get(0)

            // These fields are fixed
            imageMeta.isOrderCertain = true
            imageMeta.isFalseColor = false
            imageMeta.isThumbnail = false
            imageMeta.isLittleEndian = isLittleEndian

            // Determine color information
            var indexed = false
            var rgb = true
            var sizec = 1

            val bpp = ihdr!!.bitDepth.toInt()

            when (ihdr!!.colourType) {
                0x0.i8 -> rgb = false
                0x2.i8 -> sizec = 3
                0x3.i8 -> {
                    indexed = true
                    sizec = 1
                }
                0x4.i8 -> {
                    rgb = false
                    sizec = 2
                }
                0x6.i8 -> sizec = 4
            }
            /*
             * TODO: destination metadata doesn't care about the LUT if
             * (indexed) { final byte[][] lut = new byte[3][0];
             *
             * lut[0] = source.getPlte().getRed(); lut[1] =
             * source.getPlte().getGreen(); lut[2] = source.getPlte().getBlue();
             *
             * imageMeta.setLut(lut); }
             */

            // The IHDR contains frame height and width
            //            imageMeta.setAxisTypes(Axes.X, Axes.Y)
            //            imageMeta.setAxisLengths(longArrayOf(ihdr!!.width.toLong(), ihdr!!.height.toLong()))
            //            imageMeta.setPlanarAxisCount(2)

            // Set pixel information
            imageMeta.bitsPerPixel = bpp
            try {
                imageMeta.pixelType = FormatTools.pixelTypeFromBytes(bpp / 8, isSigned, false)
            } catch (e: FormatException) {
                //                log().error("Failed to find pixel type from bytes: " + (bpp / 8), e)
            }

            // RGB planes are always stored planar
            if (rgb) {
                //                imageMeta.addAxis(Axes.CHANNEL, sizec)
                //                imageMeta.setPlanarAxisCount(3)
            }

            val actl = actl
            if (actl != null) {
                //                imageMeta.addAxis(Axes.TIME, actl.numFrames)
            }

            imageMeta.isIndexed = indexed

            // Some anciliary chunks may not have been parsed
            imageMeta.isMetadataComplete = false

            get(0).table!![DEFAULT_KEY] = isSeparateDefault

            // TODO
            // coreMeta.setThumbSizeX(source.thumbSizeX);
            // coreMeta.setThumbSizeY(source.thumbSizeY);

            // coreMeta.setcLengths(source.cLengths);
            // coreMeta.setcTypes(source.cTypes);
        }

        // -- HasSource API Methods --
        @Throws(IOException::class)
        override fun close(fileOnly: Boolean) {
            super.close(fileOnly)

            if (!fileOnly) {
                fctl = ArrayList()
                idat = ArrayList()
            }
        }

        override fun close() {
            TODO("Not yet implemented")
        }

        override val format: Format?
            get() = TODO("Not yet implemented")

        override fun generateImageMetadata(imageIndex: Int): ImageMetadata? {
            TODO("Not yet implemented")
        }
        companion object {
            // -- Constants --
            const val DEFAULT_KEY = "separate default"
        }
    }

    /**
     * File format SCIFIO Checker for Animated Portable Network Graphics (APNG)
     * images.
     */
    class Checker : AbstractChecker() {

        override val format: Format
            get() = TODO("Not yet implemented")

        // -- Checker API Methods --
        fun suffixNecessary(): Boolean = false

        //        @Throws(IOException::class)
        //        override fun isFormat(stream: DataHandle<Location?>): Boolean {
        //            val blockLen = 8
        //            if (!FormatTools.validStream(stream, blockLen, false)) return false
        //
        //            val signature = ByteArray(blockLen)
        //            stream.read(signature)
        //
        //            if (signature[0] != 0x89.toByte() || signature[1].toInt() != 0x50 || signature[2].toInt() != 0x4e || signature[3].toInt() != 0x47 || signature[4].toInt() != 0x0d || signature[5].toInt() != 0x0a || signature[6].toInt() != 0x1a || signature[7].toInt() != 0x0a) {
        //                return false
        //            }
        //            return true
        //        }
    }

    /**
     * File format SCIFIO Reader for Animated Portable Network Graphics (APNG)
     * images.
     */
//    class Reader : BufferedImageReader<Metadata?>() {
//        @Parameter
//        private val dataHandleService: DataHandleService? = null
//
//        // -- Fields --
//        // Cached copy of the last plane that was returned.
//        private var lastPlane: BufferedImagePlane? = null
//
//        // Plane index of the last plane that was returned.
//        private var lastPlaneIndex: Long = -1
//
//        // -- AbstractReader API Methods --
//        protected fun createDomainArray(): Array<String> {
//            return arrayOf(FormatTools.GRAPHICS_DOMAIN)
//        }
//
//        // -- Reader API Methods --
//        @Throws(java.io.IOException::class) fun setMetadata(meta: Metadata?) {
//            lastPlaneIndex = -1
//            lastPlane = null
//            super.setMetadata(meta)
//        }
//
//        @Throws(FormatException::class, java.io.IOException::class) fun openPlane(imageIndex: Int,
//                                                                                  planeIndex: Long, plane: BufferedImagePlane,
//                                                                                  bounds: Interval?, config: SCIFIOConfig?): BufferedImagePlane {
//            val meta: Metadata = getMetadata()
//            FormatTools.checkPlaneForReading(meta, imageIndex, planeIndex, -1,
//                                             bounds)
//
//            // If the last processed (cached) plane is requested, return the
//            // requested sub-image, but don't update the last plane (in case the
//            // full plane was not requested)
//            if (planeIndex == lastPlaneIndex && lastPlane != null) {
//                val subImage: java.awt.image.BufferedImage = AWTImageTools.getSubimage(lastPlane
//                                                                                               .getData(), meta[imageIndex].isLittleEndian, bounds)
//                plane.setData(subImage)
//                return plane
//            } else if (lastPlane == null) {
//                lastPlane = createPlane(bounds)
//                if (getMetadata().get(imageIndex).isIndexed()) {
//                    val plte = meta.plte
//                    if (plte != null) {
//                        val ct: ColorTable = ColorTable8(plte.red, plte.green, plte.blue)
//                        plane.setColorTable(ct)
//                    }
//                }
//            }
//
//            // The default frame is requested and we can use the standard
//            // Java ImageIO to extract it
//            val handle: DataHandle<Location> = getHandle()
//            if (planeIndex == 0L) {
//                handle.seek(0)
//                val dis: java.io.DataInputStream = java.io.DataInputStream(java.io.BufferedInputStream(
//                    DataHandleInputStream(handle), 4096))
//                var subImg: java.awt.image.BufferedImage = javax.imageio.ImageIO.read(dis)
//                lastPlane.populate(meta[imageIndex], subImg, bounds)
//
//                lastPlaneIndex = 0
//
//                plane.setData(lastPlane.getData())
//
//                if (!SCIFIOMetadataTools.wholePlane(imageIndex, meta, bounds)) {
//                    // updates the data of the plane to a sub-image, by
//                    // reference
//                    subImg = AWTImageTools.getSubimage(lastPlane.getData(), meta[imageIndex].isLittleEndian, bounds)
//                    plane.setData(subImg)
//                }
//
//                return plane
//            }
//
//            // For a non-default frame, the appropriate chunks will be used to
//            // create
//            // a new image,
//            // which will be read with the standard Java ImageIO and pasted onto
//            // frame
//            // 0.
//            val stream: java.io.ByteArrayOutputStream = java.io.ByteArrayOutputStream()
//            stream.write(PNG_SIGNATURE)
//
//            val coords: IntArray = getMetadata().getFctl().get(planeIndex.toInt())
//                    .getFrameCoordinates()
//            // process IHDR chunk
//            val ihdr: IHDRChunk = getMetadata().getIhdr()
//            processChunk(imageIndex, ihdr.getLength(), ihdr.getOffset(), coords,
//                         stream, true)
//
//            // process fcTL and fdAT chunks
//            val fctl: FCTLChunk = getMetadata().getFctl().get((if (getMetadata()
//                        .isSeparateDefault()) planeIndex - 1 else planeIndex).toInt())
//
//            // fdAT chunks are converted to IDAT chunks, as we are essentially
//            // building a standalone single-frame image
//            for (fdat in fctl.getFdatChunks()) {
//                handle.seek(fdat.getOffset() + 4)
//                var b: ByteArray? = ByteArray(fdat.getLength() + 8)
//                Bytes.unpack(fdat.getLength() - 4, b, 0, 4, getMetadata().get(
//                    imageIndex).isLittleEndian())
//                b!![4] = 'I'.code.toByte()
//                b[5] = 'D'.code.toByte()
//                b[6] = 'A'.code.toByte()
//                b[7] = 'T'.code.toByte()
//                handle.read(b, 8, b.size - 12)
//                val crc = computeCRC(b, b.size - 4).toInt()
//                Bytes.unpack(crc, b, b.size - 4, 4, getMetadata().get(imageIndex)
//                        .isLittleEndian())
//                stream.write(b)
//                b = null
//            }
//
//            // process PLTE chunks
//            val plte: PLTEChunk = getMetadata().getPlte()
//            if (plte != null) {
//                processChunk(imageIndex, plte.getLength(), plte.getOffset(), coords,
//                             stream, false)
//            }
//            val loc: Location = BytesLocation(stream.toByteArray())
//            val dis: DataHandleInputStream<Location> = DataHandleInputStream(
//                dataHandleService.create(loc))
//            val bi: java.awt.image.BufferedImage = javax.imageio.ImageIO.read(dis)
//            dis.close()
//
//            // Recover first plane
//            val firstPlaneBounds: Interval =  //
//                FinalInterval(meta[imageIndex].getAxesLengthsPlanar())
//            openPlane(imageIndex, 0, firstPlaneBounds, config)
//
//            // paste current image onto first plane
//            // NB: last plane read was the first plane
//            val firstRaster: java.awt.image.WritableRaster = lastPlane.getData().getRaster()
//            val currentRaster: java.awt.image.WritableRaster = bi.getRaster()
//
//            firstRaster.setDataElements(coords[0], coords[1], currentRaster)
//            val bImg: java.awt.image.BufferedImage = java.awt.image.BufferedImage(lastPlane.getData()
//                                                                                          .getColorModel(), firstRaster, false, null)
//
//            lastPlane.populate(getMetadata().get(imageIndex), bImg, bounds)
//
//            lastPlaneIndex = planeIndex
//            return plane.populate(lastPlane)
//        }
//
//        @Throws(java.io.IOException::class) fun close(fileOnly: Boolean) {
//            super.close(fileOnly)
//
//            if (!fileOnly) {
//                lastPlane = null
//                lastPlaneIndex = -1
//            }
//        }
//
//        // -- Helper methods --
//        private fun computeCRC(buf: ByteArray?, len: Int): Long {
//            val crc: java.util.zip.CRC32 = java.util.zip.CRC32()
//            crc.update(buf, 0, len)
//            return crc.getValue()
//        }
//
//        @Throws(java.io.IOException::class) private fun processChunk(imageIndex: Int, length: Int,
//                                                                     offset: Long, coords: IntArray, stream: java.io.ByteArrayOutputStream,
//                                                                     isIHDR: Boolean) {
//            var b: ByteArray? = ByteArray(length + 12)
//            val littleEndian: Boolean = getMetadata().get(imageIndex)
//                    .isLittleEndian()
//            Bytes.unpack(length, b, 0, 4, littleEndian)
//            val typeBytes: ByteArray = (if (isIHDR) "IHDR".toByteArray() else "PLTE".toByteArray())
//            java.lang.System.arraycopy(typeBytes, 0, b, 4, 4)
//            getHandle().seek(offset)
//            getHandle().read(b, 8, b!!.size - 12)
//            if (isIHDR) {
//                Bytes.unpack(coords[2], b, 8, 4, littleEndian)
//                Bytes.unpack(coords[3], b, 12, 4, littleEndian)
//            }
//            val crc = computeCRC(b, b.size - 4).toInt()
//            Bytes.unpack(crc, b, b.size - 4, 4, littleEndian)
//            stream.write(b)
//            b = null
//        }
//    }

    /**
     * The SCIFIO file format writer for PNG and APNG files.
     */
//    class Writer : AbstractWriter<Metadata?>() {
//        // -- Fields --
//        // Number of frames written
//        private var numFrames = 0
//
//        // Pointer to position in acTL chunk to write the number of frames in
//        // this image
//        private var numFramesPointer: Long = 0
//
//        // Current sequence number, shared by fcTL and fdAT frames to indicate
//        // ordering
//        private var nextSequenceNumber = 0
//
//        // -- AbstractWriter Methods --
//        override fun makeCompressionTypes(): Array<String> {
//            return arrayOfNulls(0)
//        }
//
//        @Throws(FormatException::class, java.io.IOException::class) override fun initialize(imageIndex: Int, planeIndex: Long,
//                                                                                            bounds: Interval) {
//            if (!isInitialized(imageIndex, planeIndex)) {
//                if (numFrames == 0) {
//                    if (!metadata.isSeparateDefault()) {
//                        // first frame is default image
//                        writeFCTL(planeIndex)
//                    }
//                    writePLTE()
//                }
//            }
//
//            super.initialize(imageIndex, planeIndex, bounds)
//        }
//
//        // -- Writer API Methods --
//        @Throws(java.io.IOException::class, FormatException::class) fun setDest(out: DataHandle<Location?>, imageIndex: Int,
//                                                                                config: SCIFIOConfig?) {
//            super.setDest(out, imageIndex, config)
//            if (out.length() <= 0) {
//                val imageMetadata = metadata[imageIndex]
//                val width = imageMetadata.getAxisLength(Axes.X) as Int
//                val height = imageMetadata.getAxisLength(Axes.Y) as Int
//                val bytesPerPixel = getBytesPerPixel(imageMetadata
//                                                             .pixelType)
//                val nChannels = imageMetadata.getAxisLength(Axes.CHANNEL) as Int
//                val indexed = colorModel != null &&
//                        (colorModel is java.awt.image.IndexColorModel)
//
//                // write 8-byte PNG signature
//                out.write(PNG_SIGNATURE)
//
//                // write IHDR chunk
//                out.writeInt(13)
//                val b = ByteArray(17)
//                b[0] = 'I'.code.toByte()
//                b[1] = 'H'.code.toByte()
//                b[2] = 'D'.code.toByte()
//                b[3] = 'R'.code.toByte()
//
//                Bytes.unpack(width, b, 4, 4, false)
//                Bytes.unpack(height, b, 8, 4, false)
//
//                b[12] = (bytesPerPixel * 8).toByte()
//                if (indexed) b[13] = 3.toByte()
//                else if (nChannels == 1) b[13] = 0.toByte()
//                else if (nChannels == 2) b[13] = 4.toByte()
//                else if (nChannels == 3) b[13] = 2.toByte()
//                else if (nChannels == 4) b[13] = 6.toByte()
//                b[14] = metadata.getIhdr().getCompressionMethod()
//                b[15] = metadata.getIhdr().getFilterMethod()
//                b[16] = metadata.getIhdr().getInterlaceMethod()
//
//                out.write(b)
//                out.writeInt(crc(b))
//
//                // write acTL chunk
//                val actl: ACTLChunk = metadata.getActl()
//
//                out.writeInt(8)
//                out.writeBytes("acTL")
//                numFramesPointer = out.offset()
//                out.writeInt(actl?.numFrames ?: 0)
//                out.writeInt(actl?.numPlays ?: 0)
//                out.writeInt(0) // save a place for the CRC
//            }
//        }
//
//        @Throws(FormatException::class, java.io.IOException::class) fun writePlane(imageIndex: Int, planeIndex: Long,
//                                                                                   plane: Plane, bounds: Interval) {
//            checkParams(imageIndex, planeIndex, plane.getBytes(), bounds)
//            if (!SCIFIOMetadataTools.wholePlane(imageIndex, metadata, bounds)) {
//                throw FormatException(
//                    "APNGWriter does not yet support saving image tiles.")
//            }
//
//            // write the data for this frame
//            if (numFrames == 0) {
//                // This is the first frame, and also the default image
//                writePixels(imageIndex, "IDAT", plane, bounds)
//            } else {
//                writeFCTL(planeIndex)
//                writePixels(imageIndex, "fdAT", plane, bounds)
//            }
//            numFrames++
//        }
//
//        override fun canDoStacks(): Boolean {
//            return true
//        }
//
//        override fun getPixelTypes(codec: String): IntArray {
//            return intArrayOf(FormatTools.UINT8, FormatTools.UINT16)
//        }
//
//        // -- HasSource API Methods --
//        @Throws(java.io.IOException::class) override fun close(fileOnly: Boolean) {
//            if (getHandle() != null) {
//                writeFooter()
//            }
//            super.close(fileOnly)
//            numFrames = 0
//            numFramesPointer = 0
//            nextSequenceNumber = 0
//        }
//
//        // -- Helper Methods --
//        private fun crc(buf: ByteArray, off: Int = 0, len: Int = buf.length): Int {
//            val crc: java.util.zip.CRC32 = java.util.zip.CRC32()
//            crc.update(buf, off, len)
//            return crc.getValue().toInt()
//        }
//
//        @Throws(java.io.IOException::class) private fun writeFCTL(planeIndex: Long) {
//            getHandle().writeInt(26)
//            val fctl: FCTLChunk = metadata.getFctl().get((if (metadata
//                        .isSeparateDefault()) planeIndex - 1 else planeIndex).toInt())
//            val b = ByteArray(30)
//
//            Bytes.unpack(22, b, 0, 4, false)
//            b[0] = 'f'.code.toByte()
//            b[1] = 'c'.code.toByte()
//            b[2] = 'T'.code.toByte()
//            b[3] = 'L'.code.toByte()
//
//            Bytes.unpack(nextSequenceNumber++, b, 4, 4, false)
//            Bytes.unpack(fctl.width, b, 8, 4, false)
//            Bytes.unpack(fctl.height, b, 12, 4, false)
//            Bytes.unpack(fctl.getxOffset(), b, 16, 4, false)
//            Bytes.unpack(fctl.getyOffset(), b, 20, 4, false)
//            Bytes.unpack(fctl.delayNum, b, 24, 2, false)
//            Bytes.unpack(fctl.delayDen, b, 26, 2, false)
//            b[28] = fctl.disposeOp
//            b[29] = fctl.blendOp
//
//            getHandle().write(b)
//            getHandle().writeInt(crc(b))
//        }
//
//        @Throws(java.io.IOException::class) private fun writePLTE() {
//            if (colorModel !is java.awt.image.IndexColorModel) return
//
//            val model: java.awt.image.IndexColorModel = colorModel as java.awt.image.IndexColorModel
//            val lut = Array(3) { ByteArray(256) }
//            model.getReds(lut[0])
//            model.getGreens(lut[1])
//            model.getBlues(lut[2])
//
//            getHandle().writeInt(768)
//            val b = ByteArray(772)
//            b[0] = 'P'.code.toByte()
//            b[1] = 'L'.code.toByte()
//            b[2] = 'T'.code.toByte()
//            b[3] = 'E'.code.toByte()
//
//            for (i in lut[0].indices) {
//                for (j in lut.indices) {
//                    b[i * lut.size + j + 4] = lut[j][i]
//                }
//            }
//
//            getHandle().write(b)
//            getHandle().writeInt(crc(b))
//        }
//
//        @Throws(FormatException::class, java.io.IOException::class) private fun writePixels(imageIndex: Int, chunk: String,
//                                                                                            plane: Plane, bounds: Interval) {
//            val stream: ByteArray = plane.getBytes()
//
//            val imageMetadata = metadata[imageIndex]
//            val rgbCCount: Long = imageMetadata.getAxisLength(Axes.CHANNEL)
//            val interleaved: Boolean = plane.getImageMetadata()
//                    .getInterleavedAxisCount() > 0
//
//            val pixelType = imageMetadata.pixelType
//            val signed: Boolean = FormatTools.isSigned(pixelType)
//
//            if (!SCIFIOMetadataTools.wholePlane(imageIndex, metadata, bounds)) {
//                throw FormatException("APNGWriter does not support writing tiles.")
//            }
//
//            val width = imageMetadata.getAxisLength(Axes.X) as Int
//            val height = imageMetadata.getAxisLength(Axes.Y) as Int
//
//            val s: java.io.ByteArrayOutputStream = java.io.ByteArrayOutputStream()
//            s.write(chunk.toByteArray())
//            if (chunk == "fdAT") {
//                s.write(Bytes.fromInt(nextSequenceNumber++, false))
//            }
//            val deflater: java.util.zip.DeflaterOutputStream = java.util.zip.DeflaterOutputStream(s)
//            val planeSize = stream.size / rgbCCount
//            val rowLen = stream.size / height
//            val bytesPerPixel = stream.size / (width * height *
//                    rgbCCount).toInt()
//            val littleEndian = metadata[0].isLittleEndian
//            val rowBuf = ByteArray(rowLen)
//            for (i in 0 until height) {
//                deflater.write(0)
//                if (interleaved) {
//                    if (littleEndian) {
//                        for (col in 0 until width * rgbCCount) {
//                            val offset = (i * rgbCCount * width + col).toInt() *
//                                    bytesPerPixel
//                            val pixel: Int = Bytes.toInt(stream, offset, bytesPerPixel,
//                                                         littleEndian)
//                            Bytes.unpack(pixel, rowBuf, col * bytesPerPixel, bytesPerPixel,
//                                         false)
//                        }
//                    } else java.lang.System.arraycopy(stream, i * rowLen, rowBuf, 0, rowLen)
//                } else {
//                    val max = 2.pow((bytesPerPixel * 8 - 1).toDouble()) as Int
//                    for (col in 0 until width) {
//                        for (c in 0 until rgbCCount) {
//                            val offset = (c * planeSize + (i * width + col) *
//                                    bytesPerPixel).toInt()
//                            var pixel: Int =  //
//                                Bytes.toInt(stream, offset, bytesPerPixel, littleEndian)
//                            if (signed) {
//                                if (pixel < max) pixel += max
//                                else pixel -= max
//                            }
//                            val output = (col * rgbCCount + c).toInt() * bytesPerPixel
//
//                            Bytes.unpack(pixel, rowBuf, output, bytesPerPixel, false)
//                        }
//                    }
//                }
//                deflater.write(rowBuf)
//            }
//            deflater.finish()
//            val b: ByteArray = s.toByteArray()
//
//            // write chunk length
//            val handle: DataHandle<Location> = getHandle()
//            handle.writeInt(b.size - 4)
//            handle.write(b)
//
//            // write checksum
//            handle.writeInt(crc(b))
//        }
//
//        @Throws(java.io.IOException::class) private fun writeFooter() {
//            // write IEND chunk
//            val handle: DataHandle<Location> = getHandle()
//            handle.writeInt(0)
//            handle.writeBytes("IEND")
//            handle.writeInt(crc("IEND".toByteArray()))
//
//            // update frame count
//            handle.seek(numFramesPointer)
//            handle.writeInt(numFrames)
//            handle.skipBytes(4)
//            val b = ByteArray(12)
//            b[0] = 'a'.code.toByte()
//            b[1] = 'c'.code.toByte()
//            b[2] = 'T'.code.toByte()
//            b[3] = 'L'.code.toByte()
//            Bytes.unpack(numFrames, b, 4, 4, false)
//            Bytes.unpack(if (metadata.getActl() == null) 0 else metadata.getActl()
//                    .getNumPlays(), b, 8, 4, false)
//            handle.writeInt(crc(b))
//        }
//    }

    /**
     * This class can be used for translating any io.scif.Metadata to Metadata for
     * writing Animated Portable Network Graphics (APNG) files.
     *
     *
     * Note that Metadata translated from Core is only write-safe.
     *
     *
     *
     * If trying to read, there should already exist an originally-parsed APNG
     * Metadata object which can be used.
     *
     *
     *
     * Note also that any APNG image written must be reparsed, as the Metadata
     * used to write it can not be guaranteed valid.
     *
     */
    //    @Plugin(type = Translator::class, priority = Priority.LOW)
//    class APNGTranslator : AbstractTranslator<io.scif.Metadata?, Metadata?>() {
//        // -- Translator API Methods --
//        fun source(): java.lang.Class<out io.scif.Metadata> {
//            return io.scif.Metadata::class.java
//        }
//
//        fun dest(): java.lang.Class<out io.scif.Metadata> {
//            return Metadata::class.java
//        }
//
//        fun translateImageMetadata(source: List<ImageMetadata>,
//                                   dest: Metadata) {
//            val ihdr = if (dest.ihdr == null) IHDRChunk() else dest.ihdr!!
//            val plte = if (dest.plte == null) PLTEChunk() else dest.plte!!
//            val actl = if (dest.actl == null) ACTLChunk() else dest.actl!!
//            val fctl: MutableList<FCTLChunk> = java.util.ArrayList<FCTLChunk>()
//
//            dest.ihdr = ihdr
//            dest.plte = plte
//            dest.actl = actl
//            dest.fctl = fctl
//
//            ihdr.width = source[0].getAxisLength(Axes.X) as Int
//            ihdr.height = source[0].getAxisLength(Axes.Y) as Int
//            ihdr.bitDepth = source[0].bitsPerPixel.toByte()
//            ihdr.filterMethod = 0.toByte()
//            ihdr.compressionMethod = 0.toByte()
//            ihdr.interlaceMethod = 0.toByte()
//
//            val sizec: Long = if (source[0].isMultichannel()) source[0]
//                    .getAxisLength(Axes.CHANNEL) else 1
//            val indexed = source[0].isIndexed
//
//            if (indexed) {
//                ihdr.colourType = 0x3.toByte()
//
//                /*
//				 * NB: not necessary to preserve ColorTable when translating. If
//				 * an image has a color table it will be parsed and included in
//				 * whatever plane is returned by an openPlane call. So it
//				 * doesn't also need to be preserved in the Metadata. byte[][]
//				 * lut = null; try { lut = source.get8BitLookupTable(0);
//				 * plte.setRed(lut[0]); plte.setGreen(lut[1]);
//				 * plte.setBlue(lut[2]); } catch (final FormatException e) {
//				 * log().error("Format error when finding 8bit lookup table",
//				 * e); } catch (final IOException e) {
//				 * log().error("IO error when finding 8bit lookup table", e); }
//				 */
//            } else if (sizec == 2L) {
//                // grayscale with alpha
//                ihdr.colourType = 0x4.toByte()
//                // Each pixel is 2 samples. Bit depth is bits per sample
//                // and not per pixel. Thus we divide by 2.
//                ihdr.bitDepth = (ihdr.bitDepth / 2).toByte()
//            } else if (sizec == 4L) {
//                // each pixel is an rgb triple, plus alpha
//                ihdr.colourType = 0x6.toByte()
//                // Each pixel is 2 samples. Bit depth is bits per sample
//                // and not per pixel. Thus we divide by 2.
//                ihdr.bitDepth = (ihdr.bitDepth / 2).toByte()
//            } else if (sizec != 3L) {
//                // grayscale image
//                ihdr.colourType = 0x0.toByte()
//            } else {
//                // each pixel is an RGB triple
//                ihdr.colourType = 0x2.toByte()
//            }
//
//            actl.numFrames = source[0].getPlaneCount() as Int
//
//            for (i in 0 until actl.numFrames) {
//                val frame = FCTLChunk()
//                frame.height = ihdr.height
//                frame.width = ihdr.width
//                frame.setxOffset(0)
//                frame.setyOffset(0)
//                frame.sequenceNumber = i
//                frame.delayDen = 0.toShort()
//                frame.delayNum = 0.toShort()
//                frame.blendOp = 0.toByte()
//                frame.disposeOp = 0.toByte()
//                fctl.add(frame)
//            }
//
//            // FIXME: all integers in apng should be written big endian per spec
//            // but for bio-formats endianness is supposed to be preserved...
//            // resolve?
//            dest.isLittleEndian = source[0].isLittleEndian
//
//            val signed: Boolean = FormatTools.isSigned(source[0].pixelType)
//            dest.isSigned = signed
//
//            val separateDefault = source[0].table!![Metadata.DEFAULT_KEY]
//            dest.isSeparateDefault = if (separateDefault == null) false
//            else (separateDefault as Boolean?)!!
//        }
//    }

    /**
     * A parent class for all APNG Chunk classes.
     *
     *
     * Provides a length and offset (in the overall file stream) field.
     *
     *
     *
     * Each chunk should instantiate and define its own CHUNK_SIGNATURE.
     *
     */
    sealed class APNGChunk(
        // Unique chunk type signature (e.g. "IHDR")
            val chunkSignature: ByteArray) {

        // -- Fields --
        // Offset in the file data stream. Points to the start of the
        // data of the chunk, which comes after an entry for the length
        // and the chunk's signature.
        var offset: Long = 0

        // Length of the chunk
        var length: Int = 0

        open val frameCoordinates: IntArray
            get() = IntArray(0)

        override fun toString(): String {
            TODO()
//            return FieldPrinter(this).toString()
        }
    }

    /**
     * Represents the IHDR chunk of the APNG image format.
     *
     *
     * The IHDR chunk is a critical chunk for all APNG and PNG images. It contains
     * basic information about the image.
     *
     *
     *
     * The IHDR is always the first chunk of a correct PNG or APNG image file.
     *
     */
    class IHDRChunk : APNGChunk(byteArrayOf(0x49.i8, 0x48, 0x44, 0x52)) {
        @Field(label = "Width") var width: Int = 0
        @Field(label = "height") var height: Int = 0
        @Field(label = "Bit depth") var bitDepth: Byte = 0
        @Field(label = "Colour type") var colourType: Byte = 0
        @Field(label = "Compression Method") var compressionMethod: Byte = 0
        @Field(label = "Filter method") var filterMethod: Byte = 0
        @Field(label = "Interlace method") var interlaceMethod: Byte = 0
    }

    /**
     * Represents the PLTE chunk of the APNG image format.
     *
     *
     * The PLTE chunk contains color palette data for the current image and is
     * only present in certain ARGB color formats.
     *
     */
    class PLTEChunk  // -- Constructor --
        : APNGChunk(byteArrayOf(0x50.toByte(), 0x4C, 0x54, 0x45)) {
        // -- Methods --
        // -- Fields --
        // Red palette entries
        lateinit var red: ByteArray

        // Green palette entries
        lateinit var green: ByteArray

        // Blue palette entries
        lateinit var blue: ByteArray
    }

    /**
     * Represents the fcTL chunk of the APNG image format.
     *
     *
     * The fcTL chunk contains metadata for a matching fdAT chunk, or IDAT chunk
     * (if the default image is also the first frame of the animation).
     *
     */
    class FCTLChunk : APNGChunk(byteArrayOf(0x66.toByte(), 0x63, 0x54, 0x4C)) {
        // -- Fields --
        /* Sequence number of the animation chunk, starting from 0 */
        @Field(label = "sequence_number") var sequenceNumber: Int = 0

        /* Width of the following frame */
        @Field(label = "width") var width: Int = 0

        /* Height of the following frame */
        @Field(label = "height") var height: Int = 0

        /* X position at which to render the following frame */
        @Field(label = "x_offset")                /*private*/ var xOffset = 0

        /* Y position at which to render the following frame */
        @Field(label = "y_offset")                /*private*/ var yOffset = 0

        /* Frame delay fraction numerator */
        @Field(label = "delay_num") var delayNum: Short = 0

        /* Frame delay fraction denominator */
        @Field(label = "delay_den") var delayDen: Short = 0

        /* Type of frame area disposal to be done after rendering this frame */
        @Field(label = "dispose_op") var disposeOp: Byte = 0

        /* Type of frame area rendering for this frame */
        @Field(label = "blend_op") var blendOp: Byte = 0

        /*private */val fdatChunks: ArrayList<FDATChunk> = ArrayList()


        // -- Helper Method --
        override val frameCoordinates: IntArray
            get() = intArrayOf(xOffset, yOffset, width, height)
    }

    /**
     * Represents the IDAT chunk of the APNG image format.
     *
     *
     * The IDAT chunk is simply a dump of compressed image data for a single plane
     * (the default image for the file).
     *
     */
    class IDATChunk : APNGChunk(byteArrayOf(0x49.i8, 0x44, 0x41, 0x54))

    /**
     * Represents the acTL chunk of the APNG image format.
     *
     *
     * There is one acTL chunk per APNG image, and is not present in PNG files.
     *
     *
     *
     * The acTL chunk contains metadata describing the number of frames in the
     * image, and how many times the animation sequence should be played.
     *
     */
    class ACTLChunk : APNGChunk(byteArrayOf(0x61.i8, 0x63, 0x54, 0x4C)) {
        /* Sequence number of the animation chunk, starting from 0 */
        @Field(label = "sequence_number") var sequenceNumber: Int = 0

        /* Number of frames in this APNG file */
        @Field(label = "num_frames") var numFrames = 0

        /* Times to play the animation sequence */
        @Field(label = "num_plays") var numPlays = 0
    }

    /**
     * Represents the fdAT chunk of the APNG image format.
     *
     *
     * The fdAT chunk is identical in concept to the IDAT chunk: a container for
     * compressed image data for a single frame.
     *
     *
     *
     * In the case of fdAT chunks, the image is of a non-default frame.
     *
     *
     *
     * Each fdAT chunk is paired with an fcTL chunk.
     *
     */
    class FDATChunk : APNGChunk(byteArrayOf(0x66.i8, 0x64, 0x41, 0x54)) {
        /** Sequence number of the animation chunk, starting from 0  */
        @Field(label = "sequence_number") var sequenceNumber: Int = 0
    }

    /**
     * This class represents the critical IEND chunk that signifies the end of a
     * PNG stream.
     *
     * @author Mark Hiner
     */
    class IENDChunk  // -- Constructor --
        : APNGChunk(byteArrayOf(0x49.toByte(), 0x45, 0x4E, 0x44))

    companion object {
        // -- Constants --
        val PNG_SIGNATURE: ByteArray = byteArrayOf(0x89.toByte(), 0x50,
                                                   0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)
    }
}