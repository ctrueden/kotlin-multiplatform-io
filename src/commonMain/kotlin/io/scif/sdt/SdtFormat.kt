/*
 * #%L
 * SCIFIO Life Sciences Extension
 * %%
 * Copyright (C) 2013 - 2016 Open Microscopy Environment:
 * 	- Board of Regents of the University of Wisconsin-Madison
 * 	- Glencoe Software, Inc.
 * 	- University of Dundee
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@file:OptIn(ExperimentalContracts::class)

package io.scif.sdt

import io.scif.api.buffer
import io.scif.api.readUtf8
import io.scif.api.reposition
import io.scif.api.seek
import okio.FileHandle
import uns.i
import kotlin.contracts.ExperimentalContracts

/**
 * SDTInfo encapsulates the header information for Becker & Hickl SPC-Image
 * SDT files.
 *
 * @author Curtis Rueden
 * @author Mark Hiner
 */
class SdtFormat
/** Constructs a new SDT header by reading values from the given input source */
constructor(handle: FileHandle) {

    // -- Fields --
    var width = 0
    var height = 0
    var timeBins = 0
    var channels = 0
    var timepoints = 0

    // -- Fields - File header --
    // read bhfileHeader
    val header: SdtHeader = handle.buffer(::SdtHeader)

    // -- Fields - File Info --
    val info: SdtInfo = handle.seek(header.infoOffs) {
        SdtInfo(it readUtf8 header.infoLength)
    }

    // -- Fields -- Setup --
    val setup: SdtSetup

    init {
        handle.seek(header.setupOffs) {
            var text = it readUtf8 header.setupLength
            var textEnd = text.indexOf(BINARY_SETUP)
            if (textEnd > 0) {
                text = text.substring(0, textEnd)
                textEnd += BINARY_SETUP.length
                handle.reposition(it, header.setupOffs + textEnd)
            }
            setup = SdtSetup(text)

            if (handle.position(it) < header.setupOffs + header.setupLength.i)
                header.extended = SdtHeader.Extended(handle, it)
        }
    }

    var measureInfo: SdtMeasureInfo? = null
    var measStopInfo: SdtMeasStopInfo? = null
    var measFCSInfo: SdtMeasFCSInfo? = null
    var extendedMeasureInfo: SdtExtendedMeasureInfo? = null
    var measHISTInfo: SdtMeasHISTInfo? = null

    init {
        if (header.noOfMeasDescBlocks > 0)
            handle.seek(header.measDescBlockOffs) {
                if (header.measDescBlockLength >= 211) measureInfo = SdtMeasureInfo(it, this)
                if (header.measDescBlockLength >= 211 + 60) measStopInfo = SdtMeasStopInfo(it)
                if (header.measDescBlockLength >= 211 + 60 + 38) measFCSInfo = SdtMeasFCSInfo(it)
                if (header.measDescBlockLength >= 211 + 60 + 38 + 26) extendedMeasureInfo = SdtExtendedMeasureInfo(it)
                if (header.measDescBlockLength >= 211 + 60 + 38 + 26 + 24) measHISTInfo = SdtMeasHISTInfo(it)
            }
    }

    val bhFileBlockHeader: Array<SdtBHFileBlockHeader> = handle.seek(header.dataBlockOffs) { buf ->
        Array(header.noOfDataBlocks.i) { SdtBHFileBlockHeader(handle, buf) }
    }

    init {
        // similar logic to TRI2, to "account for SPC-152 type images"
        val info = measureInfo
        val extInfo = extendedMeasureInfo
        if (info != null && extInfo != null)
            if (FIFO_IMAGE_MODE == info.measMode.i) {
                if (extInfo.imageX > 0) width = extInfo.imageX
                if (extInfo.imageY > 0) height = extInfo.imageY
                if (extInfo.imageRX > 0 || extInfo.imageRY > 0)
                    channels = nonZeroProduct(info.scanRX, info.scanRY)
                channels *= header.noOfDataBlocks.i
            }
    }

    companion object {
        const val FIFO_IMAGE_MODE: Int = 13
        const val BINARY_SETUP: String = "BIN_PARA_BEGIN:\u0000"
    }
}
