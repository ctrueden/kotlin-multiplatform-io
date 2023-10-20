package io.scif.sdt

import io.scif.api.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import okio.BufferedSource
import okio.FileHandle
import uns.ul

/**
 * Constructs a new SDT header by reading values from the given input source,
 * populating the given metadata table.
 */
class SdtHeader(buffer: BufferedSource) {

    /** Software revision number (lower 4 bits >= 10(decimal)). */
    val revision = buffer.i16

    /**
     * Offset of the info part which contains general text information (Title,
     * date, time, contents etc.).
     */
    val infoOffs = buffer.i

    /** Length of the info part. */
    val infoLength = buffer.i16

    /**
     * Offset of the setup text data (system parameters, display parameters, trace
     * parameters etc.).
     */
    val setupOffs = buffer.i

    /** Length of the setup data. */
    val setupLength = buffer.us

    /** Offset of the first data block. */
    val dataBlockOffs = buffer.i

    /**
     * no_of_data_blocks valid only when in 0 .. 0x7ffe range, if equal to 0x7fff
     * the field 'reserved1' contains valid no_of_data_blocks.
     */
    val noOfDataBlocks = buffer.i16

    /** length of the longest block in the file */
    val dataBlockLength = buffer.i

    /** offset to 1st. measurement description block
     *  (system parameters connected to data blocks) */
    val measDescBlockOffs = buffer.i

    /** number of measurement description blocks */
    val noOfMeasDescBlocks = buffer.i16

    /** length of the measurement description blocks */
    val measDescBlockLength = buffer.i16

    /** valid: 0x5555, not valid: 0x1111 */
    val headerValid = buffer.us

    /** reserved1 now contains noOfDataBlocks */
    val reserved1 = buffer.ul

    val reserved2 = buffer.us

    /** checksum of file header */
    val chksum = buffer.us

    // -- Fields - extended binary header --
    var extended: Extended? = null

    class Extended(handle: FileHandle, buffer: BufferedSource) {

        // BHBinHdr

        init {
            buffer.skip(4)
        }

        val baseOffset = handle.position(buffer).ul
        val softwareRevision = buffer.ui
        val paramLength = buffer.ui
        val reserved1 = buffer.ui
        val reserved2 = buffer.us

        // SPCBinHdr

        val fcsOldOffset = buffer.ui
        val fcsOldSize = buffer.ui
        val gr1Offset = buffer.ui
        val gr1Size = buffer.ui
        val fcsOffset = buffer.ui
        val fcsSize = buffer.ui
        val fidaOffset = buffer.ui
        val fidaSize = buffer.ui
        val fildaOffset = buffer.ui
        val fildaSize = buffer.ui
        val gr2Offset = buffer.ui
        val grNo = buffer.us
        val hstNo = buffer.us
        val hstOffset = buffer.ui
        val gvdOffset = buffer.ui
        val gvdSize = buffer.us
        val fitOffset = buffer.us
        val fitSize = buffer.us
        val extdevOffset = buffer.us
        val extdevSize = buffer.us
        val binhdrextOffset = buffer.ui
        val binhdrextSize = buffer.us

        var mcsImgOffset = 0u
        var mcsImgSize = 0u
        var momNo: UShort = 0u
        var momSize: UShort = 0u
        var momOffset = 0u
        var sysparExtOffset = 0u
        var sysparExtSize = 0u
        var mosaicOffset = 0u
        var mosaicSize = 0u

        var mcsActive = 0

        /** Number of MCS_TA points. */
        var mcstaPoints: UShort = 0u

        var mcstaFlags: UShort = 0u
        var mcstaTimePerPoint: UShort = 0u
        var mcsOffset = 0f
        var mcsTpp = 0f

        init {

            if (binhdrextOffset != 0u) {
                handle.reposition(buffer, baseOffset + binhdrextOffset)
                mcsImgOffset = buffer.ui
                mcsImgSize = buffer.ui
                momNo = buffer.us
                momSize = buffer.us
                momOffset = buffer.ui
                sysparExtOffset = buffer.ui
                sysparExtSize = buffer.ui
                mosaicOffset = buffer.ui
                mosaicSize = buffer.ui
                // 52 longs reserved

                if (mcsImgOffset != 0u) {
                    handle.reposition(buffer, baseOffset + mcsImgOffset)

                    mcsActive = buffer.i
                    buffer.skip(4) // Window
                    mcstaPoints = buffer.us
                    mcstaFlags = buffer.us
                    mcstaTimePerPoint = buffer.us
                    mcsOffset = buffer.f
                    mcsTpp = buffer.f
                }
            }
        }
    }
}

// Dummy "constructor"
fun SdtInfo(info: String) = SdtInfo(
    info.lines()
            .filter { it.isNotEmpty() }
            .drop(1).dropLast(1)
            .map { it.split(':', limit = 2).map(String::trim)[1] })

class SdtInfo(info: List<String>) {
    val id = info[0]
    val title = info[1]
    val version = info[2]
    val revision = info[3]
    val date = LocalDate.parse(info[4])
    val time = LocalTime.parse(info[5])
    val author = info[6]
    val company = info[7]
    val contents = info[8]
}

internal fun nonZeroProduct(vararg args: Int): Int {
    var product = 1
    for (arg in args) {
        if (arg > 0) product *= arg
    }
    return product
}

class SdtBHFileBlockHeader(handle: FileHandle, buf: BufferedSource) {
    // -- Fields - BHFileBlockHeader --

    /**
     * Number of the block in the file.
     * Valid only when in 0..0x7ffe range, otherwise use lblock_no field
     * obsolete now, lblock_no contains full block no information.
     */
    val blockNo: Short = buf.i16

    /** Offset of the data block from the beginning of the file.  */
    val dataOffs: Int = buf.i

    /** Offset to the data block header of the next data block.  */
    val nextBlockOffs: Int = buf.i

    /** See blockType defines below.  */
    val blockType: UShort = buf.us

    /**
     * Number of the measurement description block
     * corresponding to this data block.
     */
    val measDescBlockNo: Short = buf.i16

    /** Long blockNo - see remarks below.  */
    val lblockNo: ULong = buf.ul

    /** reserved2 now contains block (set) length.  */
    val blockLength: ULong = buf.ul

    /** [Scifio] custom */
    val offset: ULong = handle.position(buf).ul
}