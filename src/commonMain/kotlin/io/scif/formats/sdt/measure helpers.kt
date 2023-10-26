package io.scif.formats.sdt

import io.scif.api.*
import io.scif.formats.sdt.SdtFormat.Companion.FIFO_IMAGE_MODE
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import uns.i

class SdtMeasureInfo(handle: ReadOnlyFileHandle, format: SdtFormat) {

    // -- Fields - MeasureInfo --

    /** Time of creation.  */
    val time: LocalTime = handle.utf8(9).dropLast(1).toLocalTime()

    /** Date of creation.  */
    val date: LocalDate = handle.utf8(11).dropLast(1).toLocalDate()

    /** Serial number of the module.  */
    val modSerNo: String = handle.utf8(16).trim { c -> c <= ' ' }

    val measMode: Short = handle.i16
    val cfdLL: Float = handle.f
    val cfdLH: Float = handle.f
    val cfdZC: Float = handle.f
    val cfdHF: Float = handle.f
    val synZC: Float = handle.f
    val synFD: Short = handle.i16
    val synHF: Float = handle.f
    val tacR: Float = handle.f
    val tacG: Short = handle.i16
    val tacOF: Float = handle.f
    val tacLL: Float = handle.f
    val tacLH: Float = handle.f
    val adcRE: Short = handle.i16
    val ealDE: Short = handle.i16
    val ncx: Short = handle.i16
    val ncy: Short = handle.i16
    val page = handle.us
    val colT: Float = handle.f
    val repT: Float = handle.f
    val stopt: Short = handle.i16
    val overfl: UByte = handle.ub
    val useMotor: Short = handle.i16
    val steps: UShort = handle.us
    val offset: Float = handle.f
    val dither: Short = handle.i16
    val incr: Short = handle.i16
    val memBank: Short = handle.i16

    /** Module type.  */
    val modType: String = handle.utf8(16).trim { c -> c <= ' ' }

    val synTH: Float = handle.f
    val deadTimeComp: Short = handle.i16

    /** 2 = disabled line markers.  */
    val polarityL: Short = handle.i16

    val polarityF: Short = handle.i16
    val polarityP: Short = handle.i16

    /** Line predivider = 2 ** (linediv).  */
    val linediv: Short = handle.i16

    val accumulate: Short = handle.i16
    val flbckY: Int = handle.i
    val flbckX: Int = handle.i
    val bordU: Int = handle.i
    val bordL: Int = handle.i
    val pixTime: Float = handle.f
    val pixClk: Short = handle.i16
    val trigger: Short = handle.i16
    val scanX: Int = handle.i
    val scanY: Int = handle.i
    val scanRX: Int = handle.i
    val scanRY: Int = handle.i
    val fifoTyp: Short = handle.i16
    val epxDiv: Int = handle.i
    val modTypeCode: UShort = handle.us

    /** New in v.8.4.  */
    val modFpgaVer: UShort = handle.us

    val overflowCorrFactor: Float = handle.f
    val adcZoom: Int = handle.i

    /** Cycles (accumulation cycles in FLOW mode).  */
    val cycles: Int = handle.i

    init {
        format.timepoints = stopt.i

        // extract dimensional parameters from measure info
        if (scanX > 0) format.width = scanX
        if (scanY > 0) format.height = scanY
        if (adcRE > 0) format.timeBins = adcRE.i
        if (scanRX > 0 || scanRY > 0) format.channels = nonZeroProduct(scanRX, scanRY)

        when (measMode.i) {
            // measurement mode 0 and 1 are both single-point data
            0, 1 -> {
                format.width = 1
                format.height = 1
            }
            // for measurement_mode `FIFO_IMAGE_MODE` one channel is stored in each block
            // & width & height are not in scanX & scanY
            FIFO_IMAGE_MODE -> {
                format.width = format.setup.sp.imgX
                format.height = format.setup.sp.imgY
                format.channels = format.header.noOfMeasDescBlocks.i
            }
        }
    }
}

// MeasStopInfo - information collected when measurement is finished
class SdtMeasStopInfo(handle: ReadOnlyFileHandle) {

    // -- Fields - MeasStopInfo --

    /** Last SPC_test_state return value (status).  */
    val status: UShort = handle.us

    /** Scan clocks bits 2-0 (frame, line, pixel), rates_read - bit 15.  */
    val flags: UShort = handle.us

    /**
     * Time from start to  - disarm (simple measurement)
     * - or to the end of the cycle (for complex measurement).
     */
    val stopTime: Float = handle.f

    /** Current step (if multi-step measurement).  */
    val curStep: Int = handle.i

    /**
     * Current cycle (accumulation cycle in FLOW mode) -
     * (if multi-cycle measurement).
     */
    val curCycle: Int = handle.i

    /** Current measured page.  */
    val curPage: Int = handle.i

    /** Minimum rates during the measurement.  */
    val minSyncRate: Float = handle.f

    /** (-1.0 - not set).  */
    val minCfdRate: Float = handle.f

    val minTacRate: Float = handle.f
    val minAdcRate: Float = handle.f

    /** Maximum rates during the measurement.  */
    val maxSyncRate: Float = handle.f

    /** (-1.0 - not set).  */
    val maxCfdRate: Float = handle.f

    val maxTacRate: Float = handle.f
    val maxAdcRate: Float = handle.f
    val mReserved1: Int = handle.i
    val mReserved2: Float = handle.f
}

class SdtMeasFCSInfo(handle: ReadOnlyFileHandle) {

    // -- Fields - MeasFCSInfo --

    /** Routing channel number.  */
    val chan: UShort = handle.us

    /**
     * Bit 0 = 1 - decay curve calculated.
     * Bit 1 = 1 - fcs   curve calculated.
     * Bit 2 = 1 - FIDA  curve calculated.
     * Bit 3 = 1 - FILDA curve calculated.
     * Bit 4 = 1 - MCS curve calculated.
     * Bit 5 = 1 - 3D Image calculated.
     */
    val fcsDecayCalc: UShort = handle.us

    /** Macro time clock in 0.1 ns units.  */
    val mtResol: UInt = handle.ui

    /** Correlation time [ms].  */
    val cortime: Float = handle.f

    /** No of photons.  */
    val calcPhotons: UInt = handle.ui

    /** No of FCS values.  */
    val fcsPoints: Int = handle.i

    /** Macro time of the last photon.  */
    val endTime: Float = handle.f

    /**
     * No of Fifo overruns
     * when &gt; 0  fcs curve &amp; endTime are not valid.
     */
    val overruns: UShort = handle.us

    /**
     * 0 - linear FCS with log binning (100 bins/log)
     * when bit 15 = 1 (0x8000) - Multi-Tau FCS
     * where bits 14-0 = ktau parameter.
     */
    val fcsType: UShort = handle.us

    /**
     * Cross FCS routing channel number
     * when chan = crossChan and mod == crossMod - Auto FCS
     * otherwise - Cross FCS.
     */
    val crossChan: UShort = handle.us

    /** Module number.  */
    val mod: UShort = handle.us

    /** Cross FCS module number.  */
    val crossMod: UShort = handle.us

    /** Macro time clock of cross FCS module in 0.1 ns units.  */
    val crossMtResol: UInt = handle.ui
}

class SdtExtendedMeasureInfo(handle: ReadOnlyFileHandle) {

    // -- Fields - extended MeasureInfo -

    /**
     * 4 subsequent fields valid only for Camera mode
     * or FIFO_IMAGE mode.
     */
    val imageX: Int = handle.i
    val imageY: Int = handle.i
    val imageRX: Int = handle.i
    val imageRY: Int = handle.i

    /** Gain for XY ADCs (SPC930).  */
    val xyGain: Short = handle.i16

    /** Use or not  Master Clock (SPC140 multi-module).  */
    val masterClock: Short = handle.i16

    /** ADC sample delay (SPC-930).  */
    val adcDE: Short = handle.i16

    /** Detector type (SPC-930 in camera mode).  */
    val detType: Short = handle.i16

    /** X axis representation (SPC-930).  */
    val xAxis: Short = handle.i16
}

class SdtMeasHISTInfo(handle: ReadOnlyFileHandle) {

    // -- Fields - MeasHISTInfo --

    /** Interval time [ms] for FIDA histogram.  */
    val fidaTime: Float = handle.f

    /** Interval time [ms] for FILDA histogram.  */
    val fildaTime: Float = handle.f

    /** No of FIDA values.  */
    val fidaPoints: Int = handle.i

    /** No of FILDA values.  */
    val fildaPoints: Int = handle.i

    /** Interval time [ms] for MCS histogram.  */
    val mcsTime: Float = handle.f

    /** No of MCS values.  */
    val mcsPoints: Int = handle.i
}