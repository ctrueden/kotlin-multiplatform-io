package io.scif.formats.sdt

import io.scif.api.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import okio.BufferedSource
import okio.FileHandle
import uns.L
import uns.ul

/**
 * Constructs a new SDT header by reading values from the given input source,
 * populating the given metadata table.
 */
class SdtHeader(handle: ReadOnlyFileHandle) {

    /** Software revision number (lower 4 bits >= 10(decimal)). */
    val revision = handle.i16

    /**
     * Offset of the info part which contains general text information (Title,
     * date, time, contents etc.).
     */
    val infoOffs = handle.i

    /** Length of the info part. */
    val infoLength = handle.i16

    /**
     * Offset of the setup text data (system parameters, display parameters, trace
     * parameters etc.).
     */
    val setupOffs = handle.i

    /** Length of the setup data. */
    val setupLength = handle.us

    /** Offset of the first data block. */
    val dataBlockOffs = handle.i

    /**
     * no_of_data_blocks valid only when in 0 .. 0x7ffe range, if equal to 0x7fff
     * the field 'reserved1' contains valid no_of_data_blocks.
     */
    val noOfDataBlocks = handle.i16

    /** length of the longest block in the file */
    val dataBlockLength = handle.i

    /** offset to 1st. measurement description block
     *  (system parameters connected to data blocks) */
    val measDescBlockOffs = handle.i

    /** number of measurement description blocks */
    val noOfMeasDescBlocks = handle.i16

    /** length of the measurement description blocks */
    val measDescBlockLength = handle.i16

    /** valid: 0x5555, not valid: 0x1111 */
    val headerValid = handle.us

    /** reserved1 now contains noOfDataBlocks */
    val reserved1 = handle.ui

    val reserved2 = handle.us

    /** checksum of file header */
    val chksum = handle.us

    // -- Fields - extended binary header --
    var extended: Extended? = null

    class Extended(handle: ReadOnlyFileHandle) {

        // BHBinHdr

        init {
            handle + 4
        }

        val baseOffset = handle.pos
        val softwareRevision = handle.ui
        val paramLength = handle.ui
        val reserved1 = handle.ui
        val reserved2 = handle.us

        // SPCBinHdr

        val fcsOldOffset = handle.ui
        val fcsOldSize = handle.ui
        val gr1Offset = handle.ui
        val gr1Size = handle.ui
        val fcsOffset = handle.ui
        val fcsSize = handle.ui
        val fidaOffset = handle.ui
        val fidaSize = handle.ui
        val fildaOffset = handle.ui
        val fildaSize = handle.ui
        val gr2Offset = handle.ui
        val grNo = handle.us
        val hstNo = handle.us
        val hstOffset = handle.ui
        val gvdOffset = handle.ui
        val gvdSize = handle.us
        val fitOffset = handle.us
        val fitSize = handle.us
        val extdevOffset = handle.us
        val extdevSize = handle.us
        val binhdrextOffset = handle.ui
        val binhdrextSize = handle.us

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
                handle.pos = baseOffset + binhdrextOffset.ul
                mcsImgOffset = handle.ui
                mcsImgSize = handle.ui
                momNo = handle.us
                momSize = handle.us
                momOffset = handle.ui
                sysparExtOffset = handle.ui
                sysparExtSize = handle.ui
                mosaicOffset = handle.ui
                mosaicSize = handle.ui
                // 52 longs reserved

                if (mcsImgOffset != 0u) {
                    handle.pos = baseOffset + mcsImgOffset

                    mcsActive = handle.i
                    handle + 4 // Window
                    mcstaPoints = handle.us
                    mcstaFlags = handle.us
                    mcstaTimePerPoint = handle.us
                    mcsOffset = handle.f
                    mcsTpp = handle.f
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
            .map { it.split(':', limit = 2).map { v -> v.trim { c -> c <= ' ' } }[1] })

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

class SdtBHFileBlockHeader(handle: ReadOnlyFileHandle) {
    // -- Fields - BHFileBlockHeader --

    /**
     * Number of the block in the file.
     * Valid only when in 0..0x7ffe range, otherwise use lblock_no field
     * obsolete now, lblock_no contains full block no information.
     */
    val blockNo: Short = handle.i16

    /** Offset of the data block from the beginning of the file.  */
    val dataOffs: Int = handle.i

    /** Offset to the data block header of the next data block.  */
    val nextBlockOffs: Int = handle.i

    /** See blockType defines below.  */
    val blockType: UShort = handle.us

    /**
     * Number of the measurement description block
     * corresponding to this data block.
     */
    val measDescBlockNo: Short = handle.i16

    /** Long blockNo - see remarks below.  */
    val lblockNo: UInt = handle.ui

    /** reserved2 now contains block (set) length.  */
    val blockLength: UInt = handle.ui

    /** [Scifio] custom */
    val offset: ULong = handle.pos
}