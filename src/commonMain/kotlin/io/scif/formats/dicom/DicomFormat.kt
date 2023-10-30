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
@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalStdlibApi::class)

package io.scif.formats.dicom

import io.scif.*
import io.scif.api.ReadOnlyFileHandle
import io.scif.api.f64
import io.scif.api.i
import io.scif.codec.*
import io.scif.config.SCIFIOConfig
//import io.scif.services.FilePatternService
import io.scif.util.FormatTools
import io.scif.util.FormatTools.getBytesPerPixel
import io.scif.util.FormatTools.pixelTypeFromBytes
import net.imglib2.display.ColorTable
import net.imglib2.display.ColorTable16
import net.imglib2.display.ColorTable8
import okio.IOException
import org.scijava.util.Bytes.toUInt
import org.scijava.util.Bytes.toUShort
import platform.FormatException
import uns.*
import kotlin.jvm.Transient
import kotlin.math.min

/**
 * DICOMReader is the file format reader for DICOM files. Much of this code is
 * adapted from
 * [ImageJ
 * 's DICOM reader](http://imagej.net/developer/source/ij/plugin/DICOM.java.html).
 *
 * @author Mark Hiner
 */
//@org.scijava.plugin.Plugin(type = Format::class, name = "DICOM")
class DicomFormat : AbstractFormat() {
    // -- AbstractFormat Methods --
    override fun makeSuffixArray(): Array<String> = arrayOf("dic", "dcm", "dicom", "jp2", "j2ki", "j2kr", "raw", "ima")

    // -- Nested Classes --
    class Metadata : AbstractMetadata(), HasColorTable {
        // -- Fields --
        @Transient
        var lut: Array<UByteArray>? = null

        @Transient
        var shortLut: Array<UShortArray>? = null
        private var lut8: ColorTable8? = null
        private var lut16: ColorTable16? = null

        // Getters and Setters
        var offsets: LongArray? = null
        var isJP2K: Boolean = false
        var isJPEG: Boolean = false
        var isRLE: Boolean = false
        var isDeflate: Boolean = false
        var isOddLocations: Boolean = false
        var maxPixelValue: Int = 0
        var imagesPerFile: Int = 0
        var rescaleSlope: Double = 1.0
        var rescaleIntercept: Double = 0.0

        //        @Transient
        //        var fileList: Map<Int, MutableList<BrowsableLocation?>?>? = null
        var isInverted: Boolean = false

        var pixelSizeX: String? = null
        var pixelSizeY: String? = null
        var pixelSizeZ: Double? = null

        var date: String? = null
        var time: String? = null
        var imageType: String? = null
        var originalDate: String? = null
        var originalTime: String? = null
        var originalInstance: String? = null
        var originalSeries: Int = 0

        //        @Transient var companionFiles: List<BrowsableLocation> = java.util.ArrayList<BrowsableLocation>()

        // -- ColorTable API Methods --
        override fun getColorTable(imageIndex: Int, planeIndex: Long): ColorTable? {
            val pixelType = get(0).pixelType

            when (pixelType) {
                FormatTools.INT8, FormatTools.UINT8 -> {
                    if (lut8 == null) {
                        // Need to create the lut8
                        if (isInverted) {
                            // If inverted then lut shall be inverted
                            if (lut == null)
                            // If lut does not exists create an inverted one
                                lut = createInvertedLut8()
                            else
                            // If lut does exists inverted it
                                invertLut8(lut!!)
                        }
                        if (lut != null)
                            lut8 = ColorTable8(lut!!)
                    }
                    return lut8
                }
                FormatTools.INT16, FormatTools.UINT16 -> {
                    if (lut16 == null) {
                        // Need to create the lut16
                        if (isInverted) {
                            // If inverted then lut shall be inverted
                            if (shortLut == null)
                            // If lut does not exists create an inverted one
                                shortLut = createInvertedLut16()
                            else
                            // If lut does exists inverted it
                                invertLut16(shortLut!!)
                        }
                        if (shortLut != null)
                            lut16 = ColorTable16(shortLut!!)
                    }
                    return lut16
                }
            }
            return null
        }

        // -- Metadata API Methods --
        fun populateImageMetadata() {
            TODO()
            //            log().info("Populating metadata")
            //
            //            // TODO this isn't going to work because each parsing will
            //            // get the same filelist size and repeat infinitely
            //            val seriesCount = fileList!!.size
            //
            //            val keys = fileList!!.keys.toTypedArray<Int>()
            //            Arrays.sort(keys)
            //
            //            for (i in 0 until seriesCount) {
            //                get(i).setAxisTypes(Axes.X, Axes.Y)
            //                var sizeZ = 0
            //                if (seriesCount == 1) {
            //                    sizeZ = offsets!!.size * fileList!![keys[i]]!!.size
            //
            //                    get(i).isMetadataComplete = true
            //                    get(i).isFalseColor = false
            //                    if (isRLE) {
            //                        get(i).setAxisTypes(Axes.X, Axes.Y, Axes.CHANNEL)
            //                    }
            //                    if (get(i).getAxisLength(Axes.CHANNEL) > 1) {
            //                        get(i).setPlanarAxisCount(3)
            //                    } else {
            //                        get(i).setPlanarAxisCount(2)
            //                    }
            //                } else {
            //                    try {
            //                        val p = format!!.createParser() as Parser
            //                        val m: Metadata = p.parse(fileList!![keys[i]]!![0],
            //                                                  SCIFIOConfig(getContext()).groupableSetGroupFiles(false))
            //                        add(m[0])
            //                        sizeZ *= fileList!![keys[i]]!!.size
            //                    } catch (e: IOException) {
            //                        log().error("Error creating Metadata from DICOM companion files.",
            //                                    e)
            //                    } catch (e: FormatException) {
            //                        log().error("Error creating Metadata from DICOM companion files.",
            //                                    e)
            //                    }
            //                }
            //
            //                get(i).setAxisLength(Axes.Z, sizeZ)
            //            }
        }

        // -- HasSource API Methods --
        @Throws(IOException::class) override fun close(fileOnly: Boolean) {
            super.close(fileOnly)

            if (!fileOnly) {
                isOddLocations = false
                isDeflate = false
                isRLE = isDeflate
                isJP2K = isRLE
                isJPEG = isJP2K
                lut = null
                offsets = null
                shortLut = null
                maxPixelValue = 0
                rescaleSlope = 1.0
                rescaleIntercept = 0.0
                pixelSizeY = null
                pixelSizeX = pixelSizeY
                pixelSizeZ = null
                imagesPerFile = 0
                //                fileList = null
                isInverted = false
                imageType = null
                time = imageType
                date = time
                originalInstance = null
                originalTime = originalInstance
                originalDate = originalTime
                originalSeries = 0
                // TODO the resetting is a bit too aggressive, perhaps it should just
                // clear out fields..
                // companionFiles.clear();
            }
        }

        override val format: Format
            get() = TODO("Not yet implemented")

        override fun generateImageMetadata(imageIndex: Int): ImageMetadata? {
            TODO("Not yet implemented")
        }

        companion object {
            private fun createInvertedLut8(): Array<UByteArray> = Array(3) { UByteArray(256) { (255 - it).ub } }

            private fun invertLut8(lut: Array<UByteArray>) {
                for (array in lut) array.reverse()
            }

            private fun createInvertedLut16(): Array<UShortArray> = Array(3) { UShortArray(65536) { (65535 - it).us } }

            private fun invertLut16(lut: Array<UShortArray>) {
                for (array in lut) array.reverse()
            }
        }
    }

    //    class Checker : AbstractChecker() {
    //        // -- Checker API Methods --
    //        fun suffixNecessary(): Boolean {
    //            return false
    //        }
    //
    //        fun suffixSufficient(): Boolean {
    //            return false
    //        }
    //
    //        fun isFormat(location: org.scijava.io.location.Location,
    //                     config: SCIFIOConfig?): Boolean {
    //            // NB: we need to be able look at the companion files.
    //            if (location !is BrowsableLocation) return false
    //
    //            // extension is sufficient as long as it is DIC, DCM, DICOM, J2KI, or J2KR
    //            if (FormatTools.checkSuffix(location.getName(), DICOM_SUFFIXES)) return true
    //            return super.isFormat(location, config!!)
    //        }
    //
    //        @Throws(IOException::class) override fun isFormat(handle: DataHandle<org.scijava.io.location.Location>): Boolean {
    //            // NB: we need to be able look at the companion files.
    //            if (handle.get() !is BrowsableLocation) return false
    //
    //            val blockLen = 2048
    //            if (!FormatTools.validStream(handle, blockLen, true)) return false
    //
    //            handle.seek(128)
    //            if (handle.readString(4).equals(DICOM_MAGIC_STRING)) return true
    //            handle.seek(0)
    //
    //            try {
    //                val tag = DICOMUtils.getNextTag(handle).get()
    //                return TYPES.has(tag)
    //            } catch (e: java.lang.NullPointerException) {
    //                return false
    //            } catch (e: FormatException) {
    //                return false
    //            }
    //        }
    //
    //        companion object {
    //            // -- Constants --
    //            private val DICOM_SUFFIXES = arrayOf("dic", "dcm", "dicom",
    //                                                 "j2ki", "j2kr")
    //        }
    //    }

    class Parser : AbstractParser<Metadata>() {
        //        @org.scijava.plugin.Parameter
        //        private val codecService: CodecService? = null
        //
        //        //        @org.scijava.plugin.Parameter
        //        private val dataHandleService: DataHandleService? = null
        //
        //        //        @org.scijava.plugin.Parameter
        //        private val filePatternService: FilePatternService? = null

        // -- Parser API Methods --
        //        @Throws(FormatException::class, IOException::class)
        //        fun fileGroupOption(id: org.scijava.io.location.Location?): Int = FormatTools.CAN_GROUP

        @Throws(IOException::class, FormatException::class)
                /*protected*/
        fun typedParse(handle: /*DataHandle<org.scijava.io.location.Location?>*/ReadOnlyFileHandle,
                       meta: Metadata, config: SCIFIOConfig = SCIFIOConfig()) {
            //            meta.createImageMetadata(1)

            handle.bigEndian = false

            //            val iMeta = meta[0]

            // look for companion files
            //            if (handle.get() !is BrowsableLocation)
            //                throw FormatException("DICOM Format requires a BrowsableLocation")

            //            val companionFiles: MutableList<BrowsableLocation> = java.util.ArrayList<BrowsableLocation>()
            //            attachCompanionFiles(companionFiles)
            //            meta.companionFiles = companionFiles

            var location = 0uL
            var isJP2K = false
            var isJPEG = false
            var isRLE = false
            var isDeflate = false
            var oddLocations = false
            var maxPixelValue = -1
            var imagesPerFile = 0
            var bigEndianTransferSyntax = false

            var sizeX = 0
            var sizeY = 0
            var bitsPerPixel = 0
            var interleaved: Boolean

            // some DICOM files have a 128 byte header followed by a 4 byte
            // identifier
            //            log().info("Verifying DICOM format")
            //            val level: MetadataLevel = config.parserGetLevel()

            handle pos 128
            if ("DICM" == handle utf8 4) {
                //                if (level != MetadataLevel.MINIMUM) {
                //                    // header exists, so we'll read it
                //                    getSource().seek(0)
                //                    meta.table!!["Header information"] = getSource().readString(128)
                //                    getSource().skipBytes(4)
                //                }
                location = 128u
            } else handle pos 0

            //            log().info("Reading tags")

            var baseOffset = 0uL

            var decodingTags = true
            var signed = false

            while (decodingTags) {
                if (handle.rem <= 4u)
                    break
                //                log().debug("Reading tag from " + getSource().offset())
                val tag = DICOMUtils.getNextTag(handle, bigEndianTransferSyntax, oddLocations)
                //                iMeta.isLittleEndian = tag.isLittleEndian

                if (tag.elementLength <= 0)
                    continue

                oddLocations = (location and 1uL) != 0uL

                //                log().debug("  tag=" + tag.get() + " len=" + tag.elementLength + " fp=" + getSource().offset())
                var s: String? = null
                when (tag()) {
                    TRANSFER_SYNTAX_UID -> {
                        // this tag can indicate which compression scheme is used
                        s = handle utf8 tag.elementLength
                        addInfo(handle, meta, tag, s)
                        when {
                            s.startsWith("1.2.840.10008.1.2.4.9") -> isJP2K = true
                            s.startsWith("1.2.840.10008.1.2.4") -> isJPEG = true
                            s.startsWith("1.2.840.10008.1.2.5") -> isRLE = true
                            s == "1.2.8.10008.1.2.1.99" -> isDeflate = true
                            "1.2.4" in s || "1.2.5" in s -> throw UnsupportedCompressionException("Sorry, compression type $s not supported")
                        }
                        if ("1.2.840.10008.1.2.2" in s)
                            bigEndianTransferSyntax = true
                    }
                    NUMBER_OF_FRAMES -> {
                        s = handle utf8 tag.elementLength
                        addInfo(handle, meta, tag, s)
                        val frames = s.f64
                        if (frames > 1.0) imagesPerFile = frames.i
                    }
                    SAMPLES_PER_PIXEL -> addInfo(handle, meta, tag, handle.i16.i)
                    PLANAR_CONFIGURATION -> {
                        val configuration = handle.i16.i
                        interleaved = configuration == 0
                        if (interleaved) {
                            //                            iMeta.setAxisTypes(Axes.CHANNEL, Axes.X, Axes.Y)
                            //                            iMeta.setPlanarAxisCount(3)
                        }
                        addInfo(handle, meta, tag, configuration)
                    }
                    ROWS -> {
                        if (sizeY == 0) {
                            sizeY = handle.i16.i
                            //                            iMeta.addAxis(Axes.Y, sizeY)
                        } else handle + 2
                        addInfo(handle, meta, tag, sizeY)
                    }
                    COLUMNS -> {
                        if (sizeX == 0) {
                            sizeX = handle.i16.i
                            //                            iMeta.addAxis(Axes.X, sizeX)
                        } else handle + 2
                        addInfo(handle, meta, tag, sizeX)
                    }
                    PHOTOMETRIC_INTERPRETATION, PIXEL_SPACING, SLICE_SPACING, RESCALE_INTERCEPT, WINDOW_CENTER, RESCALE_SLOPE ->
                        addInfo(handle, meta, tag, handle utf8 tag.elementLength)
                    BITS_ALLOCATED -> {
                        if (bitsPerPixel == 0) bitsPerPixel = handle.i16.i
                        else handle + 2
                        addInfo(handle, meta, tag, bitsPerPixel)
                    }
                    PIXEL_REPRESENTATION -> {
                        val ss = handle.i16.i
                        signed = ss == 1
                        addInfo(handle, meta, tag, ss)
                    }
                    PIXEL_SIGN -> {
                        val ss = handle.i16.i
                        addInfo(handle, meta, tag, ss)
                    }
                    537262910u, WINDOW_WIDTH -> {
                        val t = handle utf8 tag.elementLength
                        maxPixelValue = when {
                            t.trim { it <= ' ' }.isEmpty() -> -1
                            else -> try {
                                t.trim { it <= ' ' }.i
                            } catch (e: NumberFormatException) {
                                -1
                            }
                        }
                        addInfo(handle, meta, tag, t)
                    }
                    PIXEL_DATA, ITEM, 0xFFEE000u -> when {
                        tag.elementLength != 0 -> {
                            baseOffset = handle.pos
                            addInfo(handle, meta, tag, location.i)
                            decodingTags = false
                        }
                        else -> addInfo(handle, meta, tag)
                    }
                    0x7F880010u ->
                        if (tag.elementLength != 0) {
                            baseOffset = location + 4u
                            decodingTags = false
                        }
                    0x7FE00000u -> handle + tag.elementLength
                    0u -> handle -= 4u
                    else -> {
                        val oldfp = handle.pos
                        addInfo(handle, meta, tag, s)
                        handle pos (oldfp + tag.elementLength.ui)
                    }
                }
                if (4u >= handle.rem)
                    decodingTags = false
            }
            if (imagesPerFile == 0) imagesPerFile = 1

            var bpp = bitsPerPixel

            while (bitsPerPixel % 8 != 0)
                bitsPerPixel++
            if (bitsPerPixel == 24 || bitsPerPixel == 48) {
                bitsPerPixel /= 3
                bpp /= 3
            }

            val pixelType = pixelTypeFromBytes(bitsPerPixel / 8, signed, false)

            //            iMeta.bitsPerPixel = bpp
            //            iMeta.pixelType = pixelType

            val bytesPerPixel = getBytesPerPixel(pixelType)

            //            val planeSize = sizeX * sizeY * (if (meta.getColorTable(0, 0) == null) meta[0].getAxisLength(Axes.CHANNEL) else 1) * bytesPerPixel

            meta.isJP2K = isJP2K
            meta.isJPEG = isJPEG
            meta.imagesPerFile = imagesPerFile
            meta.isRLE = isRLE
            meta.isDeflate = isDeflate
            meta.maxPixelValue = maxPixelValue
            meta.isOddLocations = oddLocations

            //            log().info("Calculating image offsets")

            // calculate the offset to each plane
            handle pos (baseOffset - 12u)
            val len = handle.i.ul
            if (len >= 0u && len < handle.rem) {
                handle + len
                val check = handle.us.ui
                if (check == 0xFFFEu)
                    baseOffset = handle.pos + 2u
            }

            val offsets = ULongArray(imagesPerFile)
            //            meta.offsets = offsets

            for (i in 0 until imagesPerFile)
                when {
                    isRLE -> {
                        if (i == 0) handle pos baseOffset
                        else {
                            handle pos offsets[i - 1]
                            val options = CodecOptions()
                            //                            options.maxBytes = planeSize / bytesPerPixel
                            for (q in 0..<bytesPerPixel) {
                                //                                val codec: PackbitsCodec = codecService.getCodec(PackbitsCodec::class.java)
                                //                                codec.decompress(getSource(), options)
                                while (handle.i8 == 0.i8) { /* Read to non-0 data */
                                }
                                handle -= 1
                            }
                        }
                        handle + if (i == 0) 64 else 53
                        while (handle.i8 == 0.i8) { /* Read to non-0 data */
                        }
                        offsets[i] = handle.pos - 1u
                    }
                    isJPEG || isJP2K -> {
                        // scan for next JPEG magic byte sequence
                        offsets[i] = if (i == 0) baseOffset
                        else offsets[i - 1] + 3u

                        val secondCheck = if (isJPEG) 0xD8.i8 else 0x4F.i8

                        handle pos offsets[i]
                        var n = min(8192, handle.rem.i)
                        val buf = handle bytes 8192
                        var found = false
                        while (!found) {
                            for (q in 0..<n - 2)
                                if (buf[q] == 0xFF.i8 && buf[q + 1] == secondCheck && buf[q + 2] == 0xFF.i8) {
                                    if (isJPEG || (isJP2K && buf[q + 3].i == 0x51)) {
                                        found = true
                                        offsets[i] = handle.pos + q.ul - n.ul
                                        break
                                    }
                                }
                            if (!found) {
                                for (q in 0..3)
                                    buf[q] = buf[buf.size + q - 4]
                                n = min(buf.size - 4, handle.rem.i)
                                val bytes = handle bytes n
                                bytes.copyInto(buf, 4)
                            }
                        }
                    }
                    else -> offsets[i] = baseOffset /*+ planeSize * i*/
                }

//            makeFileList(config)
        }

        //        fun getImageUsedFiles(imageIndex: Int, noPixels: Boolean): Array<org.scijava.io.location.Location>? {
        //            FormatTools.assertId(getSource(), true, 1)
        //            if (noPixels || metadata!!.getFileList() == null) return null
        //            val keys = metadata!!.getFileList()!!.keys.toTypedArray<Int>()
        //            Arrays.sort(keys)
        //            val files: MutableList<BrowsableLocation?>? = metadata!!.getFileList()!![keys[imageIndex]]
        //            for (f in metadata!!.companionFiles) {
        //                files!!.add(f)
        //            }
        //            return files?.toTypedArray<org.scijava.io.location.Location?>()
        //        }

        // -- Helper methods --
        @Throws(FormatException::class, IOException::class)
        private fun makeFileList(config: SCIFIOConfig) {
            TODO()
            //            log().info("Building file list")
            //
            //            if (metadata!!.getFileList() == null && metadata!!.originalInstance != null && metadata!!.originalDate != null && metadata!!.originalTime != null && config.groupableIsGroupFiles()) {
            //                val fileList: MutableMap<Int, MutableList<BrowsableLocation?>?> = java.util.HashMap<Int, List<BrowsableLocation>>()
            //                val s = metadata!!.originalSeries
            //                fileList[s] = java.util.ArrayList<BrowsableLocation>()
            //
            //                val instanceNumber = metadata!!.originalInstance!!.toInt() - 1
            //                if (instanceNumber == 0) fileList[s]!!.add(asBrowsableLocation(
            //                    getSource()))
            //                else {
            //                    while (instanceNumber > fileList[s]!!.size) {
            //                        fileList[s]!!.add(null)
            //                    }
            //                    fileList[s]!!.add(asBrowsableLocation(getSource()))
            //                }
            //
            //                // look for matching files in the current directory
            //                val currentFile: BrowsableLocation = asBrowsableLocation(getSource())
            //                var directory: BrowsableLocation = currentFile.parent()
            //                scanDirectory(fileList, directory, false)
            //
            //                // move up a directory and look for other directories that
            //                // could contain matching files
            //                directory = directory.parent()
            //                val subdirs: Set<BrowsableLocation> = directory.children()
            //                if (subdirs != null) {
            //                    for (subdir in subdirs) {
            //                        if (!subdir.isDirectory()) continue
            //                        scanDirectory(fileList, subdir, true)
            //                    }
            //                }
            //
            //                val keys = fileList.keys.toTypedArray<Int>()
            //                Arrays.sort(keys)
            //                for (key in keys) {
            //                    var j = 0
            //                    while (j < fileList[key]!!.size) {
            //                        if (fileList[key]!![j] == null) {
            //                            fileList[key]!!.removeAt(j)
            //                            j--
            //                        }
            //                        j++
            //                    }
            //                }
            //
            //                metadata!!.setFileList(fileList)
            //            } else if (metadata!!.getFileList() == null) {
            //                val fileList: MutableMap<Int, MutableList<BrowsableLocation?>?> = java.util.HashMap<Int, List<BrowsableLocation>>()
            //                val locationList: MutableList<BrowsableLocation?> = java.util.ArrayList<BrowsableLocation>()
            //                locationList.add(asBrowsableLocation(getSource()))
            //                fileList[0] = locationList
            //                metadata!!.setFileList(fileList)
            //            }
        }

        /**
         * DICOM datasets produced by:
         * http://www.ct-imaging.de/index.php/en/ct-systeme-e/mikro-ct-e.html
         * contain a bunch of extra metadata and log files. We do not parse these
         * extra files, but do locate and attach them to the DICOM file(s).
         *
         * @throws IOException
         * @throws FormatException
         */
        //        @Throws(IOException::class, FormatException::class)
        //        private fun attachCompanionFiles(companionFiles: MutableList<BrowsableLocation>) {
        //            // TODO check if valid
        //            val parent: BrowsableLocation = asBrowsableLocation(getSource())
        //                .parent()
        //            val grandparent: BrowsableLocation = parent.parent()
        //
        //            val mifSibling: BrowsableLocation = parent.sibling(parent.getName() +
        //                                                                       ".mif")
        //            if (dataHandleService.exists(mifSibling)) {
        //                val list: Set<BrowsableLocation> = grandparent.children()
        //                for (f in list) {
        //                    if (!f.isDirectory()) {
        //                        companionFiles.add(f)
        //                    }
        //                }
        //            }
        //        }

        /**
         * Scan the given directory for files that belong to this dataset.
         */
        //        @Throws(FormatException::class, IOException::class) private fun scanDirectory(
        //            fileList: MutableMap<Int, MutableList<BrowsableLocation?>?>,
        //            dir: BrowsableLocation, checkSeries: Boolean) {
        //            // FIXME improve performance on large directories
        //            val currentFile: BrowsableLocation = asBrowsableLocation(getSource())
        //            val pattern: FilePattern = FilePattern(filePatternService,
        //                                                   currentFile, dir, dataHandleService)
        //            var patternFiles: Array<org.scijava.io.location.Location?>? = pattern.getFiles()
        //            if (patternFiles == null) patternFiles = arrayOfNulls<org.scijava.io.location.Location>(0)
        //            Arrays.sort(patternFiles)
        //            val files: Set<BrowsableLocation> = dir.children() ?: return
        //            for (f in files) {
        //                log().debug("Checking file $f")
        //
        //                if (!f.equals(getSourceLocation()) && format!!.createChecker()
        //                        .isFormat(f) && arrayContains(f, patternFiles)) {
        //                    addFileToList(fileList, f, checkSeries)
        //                }
        //            }
        //        }

        //        private fun arrayContains(f: BrowsableLocation,
        //                                  patternFiles: Array<org.scijava.io.location.Location?>?): Boolean {
        //            for (location in patternFiles) {
        //                if (location.getURI().equals(f.getURI())) {
        //                    return true
        //                }
        //            }
        //            return false
        //        }

        /**
         * Determine if the given file belongs in the same dataset as this file.
         */
        //        @Throws(FormatException::class, IOException::class) private fun addFileToList(
        //            fileList: MutableMap<Int, MutableList<BrowsableLocation?>?>,
        //            file: BrowsableLocation, checkSeries: Boolean) {
        //            dataHandleService.create<BrowsableLocation>(file).use { stream ->
        //                if (!format!!.createChecker().isFormat(stream)) {
        //                    return
        //                }
        //                stream.setOrder(DataHandle.ByteOrder.LITTLE_ENDIAN)
        //
        //                stream.seek(128)
        //                if ("DICM" != stream.readString(4)) stream.seek(0)
        //
        //                var fileSeries = -1
        //
        //                var date: String? = null
        //                var time: String? = null
        //                var instance: String? = null
        //                while (date == null || time == null || instance == null ||
        //                    (checkSeries && fileSeries < 0)) {
        //                    val fp: Long = stream.offset()
        //                    if (fp + 4 >= stream.length() || fp < 0) break
        //                    val tag = DICOMUtils.getNextTag(stream)
        //                    val key: String = TYPES.name(tag.get())
        //                    if ("Instance Number" == key) {
        //                        instance = stream.readString(tag.elementLength).trim()
        //                        if (instance.length == 0) instance = null
        //                    } else if ("Acquisition Time" == key) {
        //                        time = stream.readString(tag.elementLength)
        //                    } else if ("Acquisition Date" == key) {
        //                        date = stream.readString(tag.elementLength)
        //                    } else if ("Series Number" == key) {
        //                        fileSeries = stream.readString(tag.elementLength).trim().toInt()
        //                    } else stream.skipBytes(tag.elementLength)
        //                }
        //
        //                if (date == null || time == null || instance == null || (checkSeries &&
        //                            fileSeries == metadata!!.originalSeries)) {
        //                    return
        //                }
        //
        //                var stamp = 0
        //                try {
        //                    stamp = time!!.toInt()
        //                } catch (e: java.lang.NumberFormatException) {
        //                }
        //
        //                var timestamp = 0
        //                try {
        //                    timestamp = metadata!!.originalTime!!.toInt()
        //                } catch (e: java.lang.NumberFormatException) {
        //                }
        //                if (date == metadata!!.originalDate && (abs((stamp -
        //                            timestamp).toDouble()) < 150)) {
        //                    var position = instance!!.toInt() - 1
        //                    if (position < 0) position = 0
        //                    if (fileList[fileSeries] == null) {
        //                        fileList[fileSeries] = java.util.ArrayList<BrowsableLocation>()
        //                    }
        //                    if (position < fileList[fileSeries]!!.size) {
        //                        while (position < fileList[fileSeries]!!.size && fileList[fileSeries]!![position] != null) {
        //                            position++
        //                        }
        //                        if (position < fileList[fileSeries]!!.size) {
        //                            fileList[fileSeries]!![position] = file
        //                        } else fileList[fileSeries]!!.add(file)
        //                    } else {
        //                        while (position > fileList[fileSeries]!!.size) {
        //                            fileList[fileSeries]!!.add(null)
        //                        }
        //                        fileList[fileSeries]!!.add(file)
        //                    }
        //                }
        //            }
        //        }

        @Throws(IOException::class)
        private fun addInfo(handle: ReadOnlyFileHandle, meta: Metadata, tag: DICOMTag, value: String? = null) {
            val oldValue = value
            var info = getHeaderInfo(handle, tag, value)

            if (info != null && tag() != ITEM) {
                info = info.trim { it <= ' ' }
                if (info == "") info = oldValue?.trim { it <= ' ' } ?: ""

                var key = TYPES.name(tag()) ?: formatTag(tag())
                when {
                    key == "Samples per pixel" -> {
                        val sizeC = info.i
                        if (sizeC > 1) {
                            //                        meta[0].setAxisLength(Axes.CHANNEL, sizeC)
                            //                        meta[0].setPlanarAxisCount(2)
                        }
                    }
                    key == "Photometric Interpretation" -> {
                        if (info == "PALETTE COLOR") {
                            //                            meta[0].isIndexed = true
                            //                            meta[0].setAxisLength(Axes.CHANNEL, 1)
                            //                            meta.lut = arrayOfNulls(3)
                            //                            meta.shortLut = arrayOfNulls(3)
                        } else if (info.startsWith("MONOCHROME"))
                            meta.isInverted = info.endsWith("1")
                    }
                    key == "Acquisition Date" -> meta.originalDate = info
                    key == "Acquisition Time" -> meta.originalTime = info
                    key == "Instance Number" ->
                        if (info.trim { it <= ' ' }.isNotEmpty())
                            meta.originalInstance = info
                    key == "Series Number" ->
                        try {
                            meta.originalSeries = info.i
                        } catch (_: NumberFormatException) {
                        }
                    "Palette Color LUT Data" in key -> {
                        val color = key.substring(0, key.indexOf(" ")).trim { it <= ' ' }
                        val ndx = when (color) {
                            "Red" -> 0
                            "Green" -> 1
                            else -> 2
                        }
                        val fp = handle.pos
                        handle + (-tag.elementLength + 1)
                        meta.shortLut!![ndx] = UShortArray(tag.elementLength / 2)
                        meta.lut!![ndx] = UByteArray(tag.elementLength / 2)
                        for (i in meta.lut!![ndx].indices) {
                            meta.shortLut!![ndx][i] = handle.us
                            meta.lut!![ndx][i] = meta.shortLut!![ndx][i].ub
                            // [Kotlin] This casting doesn't make any sense
                            //                            meta.lut[ndx][i] = (byte) (meta.shortLut[ndx][i] & 0xff);
                        }
                        handle pos fp
                    }
                    key == "Content Time" -> meta.time = info
                    key == "Content Date" -> meta.date = info
                    key == "Image Type" -> meta.imageType = info
                    key == "Rescale Intercept" -> meta.rescaleIntercept = info.f64
                    key == "Rescale Slope" -> meta.rescaleSlope = info.f64
                    key == "Pixel Spacing" -> {
                        meta.pixelSizeX = info.substring(0, info.indexOf("\\"))
                        meta.pixelSizeY = info.substring(info.lastIndexOf("\\") + 1)
                    }
                    key == "Spacing Between Slices" -> meta.pixelSizeZ = info.f64
                }

                if (((tag() and 0xFFFF0000u) shr 16) != 0x7FE0u) {
                    key = formatTag(tag()) + " " + key
                    val imageIndex = meta.imageCount - 1

//                    val t = meta[imageIndex].table!!
//                    t[key]?.let { v ->
//                        // make sure that values are not overwritten
//                        t -= key
//                        t.putList(key, v)
//                        t.putList(key, info)
//                    } ?: run { t[key] = info }
                }
            }
        }

        private fun formatTag(tag: UInt): String {
            var s = tag.toHexString()
            while (s.length < 8)
                s = "0$s"
            return s.substring(0, 4) + "," + s.substring(4)
        }

        @Throws(IOException::class)
        private fun addInfo(handle: ReadOnlyFileHandle, meta: Metadata, tag: DICOMTag, value: Int) =
            addInfo(handle, meta, tag, value.toString())

        @Throws(IOException::class)
        private fun getHeaderInfo(handle: ReadOnlyFileHandle, tag: DICOMTag, value: String?): String? {
            var value = value
            if (tag() == ITEM_DELIMINATION || tag() == SEQUENCE_DELIMINATION)
                tag.isInSequence = false

            var id = TYPES.name(tag())
            var vr = tag.vR

            if (id != null) {
                if (vr == DICOMUtils.IMPLICIT_VR) {
                    val vrName = TYPES.vr(tag())!!
                    vr = ((vrName[0].code shl 8) + vrName[1].code).ui
                    tag.vR = vr
                }
                if (id.length > 2) id = id.substring(2)
            }

            if (tag() == ITEM) return id
            if (value != null) return value

            var skip = false
            when (vr) {
                DICOMUtils.AE, DICOMUtils.AS, DICOMUtils.AT -> {
                    // Cannot fix element length to 4, because AT value representation
                    // is always 4 bytes long (DICOM specs PS3.5 par.6.2), but value
                    // multiplicity is 1-n
                    // Read from stream
                    val bytes = handle bytes tag.elementLength
                    // If little endian, swap bytes to get a string with a user friendly
                    // representation of tag group and tag element
                    if (tag.isLittleEndian) {
                        var i = 0
                        while (i < bytes.size / 2) {
                            val t = bytes[2 * i]
                            bytes[2 * i] = bytes[2 * i + 1]
                            bytes[2 * i + 1] = t
                            ++i
                        }
                    }
                    // Convert the bytes to a string
                    value = bytes.toHexString()
                }
                DICOMUtils.CS, DICOMUtils.DA, DICOMUtils.DS, DICOMUtils.DT, DICOMUtils.IS, DICOMUtils.LO,
                DICOMUtils.LT, DICOMUtils.PN, DICOMUtils.SH, DICOMUtils.ST, DICOMUtils.TM, DICOMUtils.UI -> value = handle utf8 tag.elementLength
                DICOMUtils.US ->
                    when (tag.elementLength) {
                        2 -> value = handle.i16.toString()
                        else -> {
                            value = ""
                            val n = tag.elementLength / 2
                            var i = 0
                            while (i < n) {
                                value += handle.i16.toString() + " "
                                i++
                            }
                        }
                    }
                DICOMUtils.IMPLICIT_VR -> {
                    value = handle utf8 tag.elementLength
                    if (tag.elementLength <= 4 || tag.elementLength > 44) value = null
                }
                DICOMUtils.SQ -> {
                    value = ""
                    val privateTag = (tag.elementLength shr 16) and 1 != 0
                    if (tag() == ICON_IMAGE_SEQUENCE || privateTag) skip = true
                }
                else -> skip = true
            }
            if (skip) {
                val skipCount = tag.elementLength.ul
                if (skipCount <= handle.rem)
                    handle + skipCount
                tag.location += tag.elementLength
                value = ""
            }

            return when {
                value != null && id == null && value != "" -> value
                id == null -> null
                else -> value
            }
        }

        companion object {
            // -- Constants --
            private const val PIXEL_REPRESENTATION = 0x00280103u
            private const val PIXEL_SIGN = 0x00281041u
            private const val TRANSFER_SYNTAX_UID = 0x00020010u
            private const val SLICE_SPACING = 0x00180088u
            private const val SAMPLES_PER_PIXEL = 0x00280002u
            private const val PHOTOMETRIC_INTERPRETATION = 0x00280004u
            private const val PLANAR_CONFIGURATION = 0x00280006u
            private const val NUMBER_OF_FRAMES = 0x00280008u
            private const val ROWS = 0x00280010u
            private const val COLUMNS = 0x00280011u
            private const val PIXEL_SPACING = 0x00280030u
            private const val BITS_ALLOCATED = 0x00280100u
            private const val WINDOW_CENTER = 0x00281050u
            private const val WINDOW_WIDTH = 0x00281051u
            private const val RESCALE_INTERCEPT = 0x00281052u
            private const val RESCALE_SLOPE = 0x00281053u
            private const val ICON_IMAGE_SEQUENCE = 0x00880200u
            private const val ITEM = 0xFFFEE000u
            private const val ITEM_DELIMINATION = 0xFFFEE00Du
            private const val SEQUENCE_DELIMINATION = 0xFFFEE0DDu
            private const val PIXEL_DATA = 0x7FE00010u
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

    //    class Reader : ByteArrayReader<Metadata?>() {
    //        @org.scijava.plugin.Parameter
    //        private val initializeService: InitializeService? = null
    //
    //        @org.scijava.plugin.Parameter
    //        private val codecService: CodecService? = null
    //
    //        // -- AbstractReader API Methods --
    //        protected fun createDomainArray(): Array<String> {
    //            return arrayOf(FormatTools.MEDICAL_DOMAIN)
    //        }
    //
    //        // -- Reader API Methods --
    //        fun hasCompanionFiles(): Boolean {
    //            return true
    //        }
    //
    //        @Throws(FormatException::class, IOException::class) fun openPlane(imageIndex: Int, planeIndex: Long,
    //                                                                          plane: ByteArrayPlane, bounds: Interval,
    //                                                                          config: SCIFIOConfig?): ByteArrayPlane {
    //            var planeIndex = planeIndex
    //            val meta: Metadata = getMetadata()
    //            plane.setColorTable(meta.getColorTable(imageIndex, planeIndex))
    //            FormatTools.checkPlaneForReading(meta, imageIndex, planeIndex, plane
    //                .getData().length, bounds)
    //
    //            val xAxis: Int = meta[imageIndex].getAxisIndex(Axes.X)
    //            val yAxis: Int = meta[imageIndex].getAxisIndex(Axes.Y)
    //            val x = bounds.min(xAxis).toInt()
    //            val y = bounds.min(yAxis).toInt()
    //            val w = bounds.dimension(xAxis).toInt()
    //            val h = bounds.dimension(yAxis).toInt()
    //
    //            val fileList: Map<Int, MutableList<BrowsableLocation?>?>? = meta.getFileList()
    //
    //            val keys = fileList!!.keys.toTypedArray<Int>()
    //            Arrays.sort(keys)
    //            if (fileList[keys[imageIndex]]!!.size > 1) {
    //                val fileNumber = (planeIndex / meta.imagesPerFile).toInt()
    //                planeIndex = planeIndex % meta.imagesPerFile
    //                val file: org.scijava.io.location.Location? = fileList[keys[imageIndex]]!![fileNumber]
    //                val r: io.scif.Reader = initializeService.initializeReader(file,
    //                                                                           SCIFIOConfig(getContext()).checkerSetOpen(true))
    //                return r.openPlane(imageIndex, planeIndex, plane,
    //                                   bounds, config) as ByteArrayPlane
    //            }
    //
    //            val ec = if (meta[0].isIndexed) 1 else meta[imageIndex]
    //                .getAxisLength(Axes.CHANNEL) as Int
    //            val bpp = getBytesPerPixel(meta[imageIndex]
    //                                           .pixelType)
    //            val bytes = (meta[imageIndex].getAxisLength(Axes.X) * meta[imageIndex].getAxisLength(Axes.Y) * bpp * ec) as Int
    //
    //            val handle: DataHandle<org.scijava.io.location.Location> = getHandle()
    //            handle.seek(meta.offsets!![planeIndex.toInt()])
    //
    //            if (meta.isRLE) {
    //                // plane is compressed using run-length encoding
    //                val options = CodecOptions()
    //                options.maxBytes = (meta[imageIndex].getAxisLength(Axes.X) *
    //                        meta[imageIndex].getAxisLength(Axes.Y))
    //                val codec: PackbitsCodec = codecService.getCodec(PackbitsCodec::class.java)
    //                for (c in 0 until ec) {
    //                    var t: ByteArray? = null
    //
    //                    if (bpp > 1) {
    //                        // TODO unused int planeSize = bytes / (bpp * ec);
    //                        val tmp = arrayOfNulls<ByteArray>(bpp)
    //                        for (i in 0 until bpp) {
    //                            tmp[i] = codec.decompress(handle, options)
    //                            if (planeIndex < meta.imagesPerFile - 1 || i < bpp - 1) {
    //                                while (handle.read() == 0) { /* Read to non-0 data */
    //                                }
    //                                handle.seek(handle.offset() - 1)
    //                            }
    //                        }
    //                        t = ByteArray(bytes / ec)
    //                        for (i in 0 until planeIndex) {
    //                            for (j in 0 until bpp) {
    //                                val byteIndex = if (meta[imageIndex].isLittleEndian
    //                                ) bpp - j - 1 else j
    //                                if (i < tmp[byteIndex]!!.size) {
    //                                    t[(i * bpp + j).toInt()] = tmp[byteIndex]!![i.toInt()]
    //                                }
    //                            }
    //                        }
    //                    } else {
    //                        t = codec.decompress(handle, options)
    //                        if (t.size < (bytes / ec)) {
    //                            val tmp = t
    //                            t = ByteArray(bytes / ec)
    //                            java.lang.System.arraycopy(tmp, 0, t, 0, tmp!!.size)
    //                        }
    //                        if (planeIndex < meta.imagesPerFile - 1 || c < ec - 1) {
    //                            while (handle.read() == 0) { /* Read to non-0 data */
    //                            }
    //                            handle.seek(handle.offset() - 1)
    //                        }
    //                    }
    //
    //                    val rowLen = w * bpp
    //                    val srcRowLen = meta[imageIndex].getAxisLength(
    //                        Axes.X) as Int * bpp
    //
    //                    // TODO unused int srcPlane = meta.getAxisLength(imageIndex, Axes.Y)
    //                    // srcRowLen;
    //                    for (row in 0 until h) {
    //                        val src = (row + y) * srcRowLen + x * bpp
    //                        val dest = (h * c + row) * rowLen
    //                        val len = min(rowLen.toDouble(), (t.size - src - 1).toDouble()).toInt()
    //                        if (len < 0) break
    //                        java.lang.System.arraycopy(t, src, plane.getBytes(), dest, len)
    //                    }
    //                }
    //            } else if (meta.isJPEG || meta.isJP2K) {
    //                // plane is compressed using JPEG or JPEG-2000
    //                val end = if (planeIndex < meta.offsets!!.size - 1) meta.offsets!![planeIndex.toInt() + 1] else handle.length()
    //                var b = ByteArray((end - handle.offset()).toInt())
    //                handle.read(b)
    //
    //                if (b[2] != 0xff.toByte()) {
    //                    val tmp = ByteArray(b.size + 1)
    //                    tmp[0] = b[0]
    //                    tmp[1] = b[1]
    //                    tmp[2] = 0xff.toByte()
    //                    java.lang.System.arraycopy(b, 2, tmp, 3, b.size - 2)
    //                    b = tmp
    //                }
    //                if ((b[3].toInt() and 0xff) >= 0xf0) {
    //                    b[3] = (b[3] - 0x30.toByte()).toByte()
    //                }
    //
    //                var pt = b.size - 2
    //                while (pt >= 0 && b[pt] != 0xff.toByte() || b[pt + 1] != 0xd9.toByte()) {
    //                    pt--
    //                }
    //                if (pt < b.size - 2) {
    //                    val tmp = b
    //                    b = ByteArray(pt + 2)
    //                    java.lang.System.arraycopy(tmp, 0, b, 0, b.size)
    //                }
    //
    //                val options = CodecOptions()
    //                options.littleEndian = meta[imageIndex].isLittleEndian
    //                options.interleaved = meta[imageIndex]
    //                    .getInterleavedAxisCount() > 0
    //                val codec: Codec = if (meta.isJPEG) //
    //                    codecService.getCodec(JPEGCodec::class.java) else  //
    //                    codecService.getCodec(JPEG2000Codec::class.java)
    //                b = codec.decompress(b, options)
    //
    //                val rowLen = w * bpp
    //                val srcRowLen = meta[imageIndex].getAxisLength(Axes.X) as Int *
    //                        bpp
    //
    //                val srcPlane = meta[imageIndex].getAxisLength(Axes.Y) as Int *
    //                        srcRowLen
    //
    //                for (c in 0 until ec) {
    //                    for (row in 0 until h) {
    //                        java.lang.System.arraycopy(b, c * srcPlane + (row + y) * srcRowLen + x * bpp,
    //                                                   plane.getBytes(), h * rowLen * c + row * rowLen, rowLen)
    //                    }
    //                }
    //            } else if (meta.isDeflate) {
    //                // TODO
    //                throw UnsupportedCompressionException(
    //                    "Deflate data is not supported.")
    //            } else {
    //                // plane is not compressed
    //                readPlane(getHandle(), imageIndex, bounds, plane)
    //            }
    //
    //            // NB: do *not* apply the rescale function
    //            return plane
    //        }
    //    }

    // -- DICOM Helper Classes --
    private object DICOMUtils {
        const val AE = 0x4145u
        const val AS = 0x4153u
        const val AT = 0x4154u
        const val CS = 0x4353u
        const val DA = 0x4441u
        const val DS = 0x4453u
        const val DT = 0x4454u
        private const val FD = 0x4644u
        private const val FL = 0x464Cu
        const val IS = 0x4953u
        const val LO = 0x4C4Fu
        const val LT = 0x4C54u
        const val PN = 0x504Eu
        const val SH = 0x5348u
        private const val SL = 0x534Cu
        private const val SS = 0x5353u
        const val ST = 0x5354u
        const val TM = 0x544Du
        const val UI = 0x5549u
        private const val UL = 0x554Cu
        const val US = 0x5553u
        private const val UT = 0x5554u
        private const val OB = 0x4F42u
        private const val OW = 0x4F57u
        const val SQ = 0x5351u
        private const val UN = 0x554Eu
        private const val QQ = 0x3F3Fu

        const val IMPLICIT_VR = 0x2d2du

        @Throws(FormatException::class, IOException::class)
        fun getNextTag(handle: /*DataHandle<org.scijava.io.location.Location>*/ReadOnlyFileHandle,
                       bigEndianTransferSyntax: Boolean = false,
                       isOddLocations: Boolean = false): DICOMTag {

            val fp = handle.pos
            var groupWord = handle.us.ui
            val diTag = DICOMTag()
            var littleEndian = true

            if (groupWord == 0x0800u && bigEndianTransferSyntax) {
                littleEndian = false
                groupWord = 0x0008u
                handle.bigEndian = true
            } else if (groupWord == 0xFEFFu || groupWord == 0xFFFEu) {
                handle + 6
                return getNextTag(handle, bigEndianTransferSyntax)
            }

            var elementWord = handle.i16.ui
            var tag = ((groupWord shl 16) and 0xFFFF0000u) or (elementWord and 0xFFFFu)

            diTag.elementLength = getLength(handle, diTag)
            if (diTag.elementLength > handle.size.i) {
                handle pos fp
                littleEndian = !littleEndian
                handle.bigEndian = !littleEndian

                groupWord = handle.us.ui
                elementWord = handle.i16.ui
                tag = ((groupWord shl 16) and 0xFFFF0000u) or (elementWord and 0xFFFFu)
                diTag.elementLength = getLength(handle, diTag)

                if (diTag.elementLength > handle.size.i)
                    throw FormatException("Invalid tag length " + diTag.elementLength)
                diTag(tag)
                return diTag
            }

            if (diTag.elementLength < 0 && groupWord == 0x7FE0u) {
                handle + 12
                diTag.elementLength = handle.i
                if (diTag.elementLength < 0)
                    diTag.elementLength = handle.i
            }

            if (diTag.elementLength == 0 && (groupWord == 0x7FE0u || tag == 0x291014u))
                diTag.elementLength = getLength(handle, diTag)
            else if (diTag.elementLength == 0) {
                handle pos (handle.pos - 4u)
                val v = handle utf8 2
                if (v == "UT") {
                    handle + 2
                    diTag.elementLength = handle.i
                } else handle + 2
            }

            // HACK - needed to read some GE files
            // The element length must be even!
            if (!isOddLocations && diTag.elementLength % 2 == 1)
                diTag.elementLength++

            // "Undefined" element length.
            // This is a sort of bracket that encloses a sequence of elements.
            if (diTag.elementLength == -1) {
                diTag.elementLength = 0
                diTag.isInSequence = true
            }
            diTag(tag)
            diTag.isLittleEndian = littleEndian

            return diTag
        }

        @Throws(IOException::class)
        private fun getLength(handle: /*DataHandle<org.scijava.io.location.Location>*/ReadOnlyFileHandle,
                              tag: DICOMTag): Int {
            val b = handle ubytes 4

            // We cannot know whether the VR is implicit or explicit
            // without the full DICOM Data Dictionary for public and
            // private groups.

            // We will assume the VR is explicit if the two bytes
            // match the known codes. It is possible that these two
            // bytes are part of a 32-bit length for an implicit VR.
            val vr = (b[0].ui shl 8) or b[1].ui
            tag.vR = vr
            when (vr) {
                OB, OW, SQ, UN -> {
                    // Explicit VR with 32-bit length if other two bytes are zero
                    if (b[2].i == 0 || b[3].i == 0)
                        return handle.i
                    tag.vR = IMPLICIT_VR
                    return b.toUInt(handle.isLittleEndian).i
                }
                AE, AS, AT, CS, DA, DS, DT, FD, FL, IS, LO, LT, PN, SH, SL, SS, ST, TM, UI, UL, US, UT, QQ -> {
                    // Explicit VR with 16-bit length
                    if (tag() == 0x00283006u)
                        return b.toUInt(2, 2, handle.isLittleEndian).i
                    val n1 = b.toUShort(2, 2, handle.isLittleEndian).i
                    val n2 = b.toUShort(2, 2, !handle.isLittleEndian).i
                    return when {
                        n1 < 0 || n1 > handle.rem.i -> n2
                        n2 < 0 || n2 > handle.rem.i -> n1
                        else -> n1
                    }
                }
                0xFFFFu -> {
                    tag.vR = IMPLICIT_VR
                    return 8
                }
                else -> {
                    tag.vR = IMPLICIT_VR
                    var len = b.toUInt(handle.isLittleEndian)
                    if (len > handle.rem || len < 0u) {
                        len = b.toUInt(2, 2, handle.isLittleEndian)
                        len = len and 0xFFFFu
                    }
                    return len.i
                }
            }
        }
    }

    class DICOMTag {
        var elementLength = 0
        private var tagValue = 0u
        var vR = 0u
        var isInSequence: Boolean = false
        var location: Int = 0
        var isLittleEndian: Boolean = false

        operator fun invoke(): UInt = tagValue
        operator fun invoke(value: UInt) {
            tagValue = value
        }
    }

    companion object {
        // -- Constants --
        const val DICOM_MAGIC_STRING: String = "DICM"

        private val TYPES = DicomDictionary
    }
}