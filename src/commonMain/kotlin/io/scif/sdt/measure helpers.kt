package io.scif.sdt

import io.scif.api.*
import io.scif.sdt.SdtFormat.Companion.FIFO_IMAGE_MODE
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import okio.BufferedSource
import uns.i

class SdtMeasureInfo(buf: BufferedSource, format: SdtFormat) {

    // -- Fields - MeasureInfo --

    /** Time of creation.  */
    val time: LocalTime = buf.readUtf8(9).dropLast(1).toLocalTime()

    /** Date of creation.  */
    val date: LocalDate = buf.readUtf8(11).dropLast(1).toLocalDate()

    /** Serial number of the module.  */
    val modSerNo: String = buf.readUtf8(16).trim { c -> c <= ' ' }

    val measMode: Short = buf.i16
    val cfdLL: Float = buf.f
    val cfdLH: Float = buf.f
    val cfdZC: Float = buf.f
    val cfdHF: Float = buf.f
    val synZC: Float = buf.f
    val synFD: Short = buf.i16
    val synHF: Float = buf.f
    val tacR: Float = buf.f
    val tacG: Short = buf.i16
    val tacOF: Float = buf.f
    val tacLL: Float = buf.f
    val tacLH: Float = buf.f
    val adcRE: Short = buf.i16
    val ealDE: Short = buf.i16
    val ncx: Short = buf.i16
    val ncy: Short = buf.i16
    val page = buf.us
    val colT: Float = buf.f
    val repT: Float = buf.f
    val stopt: Short = buf.i16
    val overfl: UByte = buf.ub
    val useMotor: Short = buf.i16
    val steps: UShort = buf.us
    val offset: Float = buf.f
    val dither: Short = buf.i16
    val incr: Short = buf.i16
    val memBank: Short = buf.i16

    /** Module type.  */
    val modType: String = buf.readUtf8(16).trim { c -> c <= ' ' }

    val synTH: Float = buf.f
    val deadTimeComp: Short = buf.i16

    /** 2 = disabled line markers.  */
    val polarityL: Short = buf.i16

    val polarityF: Short = buf.i16
    val polarityP: Short = buf.i16

    /** Line predivider = 2 ** (linediv).  */
    val linediv: Short = buf.i16

    val accumulate: Short = buf.i16
    val flbckY: Int = buf.i
    val flbckX: Int = buf.i
    val bordU: Int = buf.i
    val bordL: Int = buf.i
    val pixTime: Float = buf.f
    val pixClk: Short = buf.i16
    val trigger: Short = buf.i16
    val scanX: Int = buf.i
    val scanY: Int = buf.i
    val scanRX: Int = buf.i
    val scanRY: Int = buf.i
    val fifoTyp: Short = buf.i16
    val epxDiv: Int = buf.i
    val modTypeCode: UShort = buf.us

    /** New in v.8.4.  */
    val modFpgaVer: UShort = buf.us

    val overflowCorrFactor: Float = buf.f
    val adcZoom: Int = buf.i

    /** Cycles (accumulation cycles in FLOW mode).  */
    val cycles: Int = buf.i

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
class SdtMeasStopInfo(buf: BufferedSource) {

    // -- Fields - MeasStopInfo --

    /** Last SPC_test_state return value (status).  */
    val status: UShort = buf.us

    /** Scan clocks bits 2-0 (frame, line, pixel), rates_read - bit 15.  */
    val flags: UShort = buf.us

    /**
     * Time from start to  - disarm (simple measurement)
     * - or to the end of the cycle (for complex measurement).
     */
    val stopTime: Float = buf.f

    /** Current step (if multi-step measurement).  */
    val curStep: Int = buf.i

    /**
     * Current cycle (accumulation cycle in FLOW mode) -
     * (if multi-cycle measurement).
     */
    val curCycle: Int = buf.i

    /** Current measured page.  */
    val curPage: Int = buf.i

    /** Minimum rates during the measurement.  */
    val minSyncRate: Float = buf.f

    /** (-1.0 - not set).  */
    val minCfdRate: Float = buf.f

    val minTacRate: Float = buf.f
    val minAdcRate: Float = buf.f

    /** Maximum rates during the measurement.  */
    val maxSyncRate: Float = buf.f

    /** (-1.0 - not set).  */
    val maxCfdRate: Float = buf.f

    val maxTacRate: Float = buf.f
    val maxAdcRate: Float = buf.f
    val mReserved1: Int = buf.i
    val mReserved2: Float = buf.f
}

class SdtMeasFCSInfo(buf: BufferedSource) {

    // -- Fields - MeasFCSInfo --

    /** Routing channel number.  */
    val chan: UShort = buf.us

    /**
     * Bit 0 = 1 - decay curve calculated.
     * Bit 1 = 1 - fcs   curve calculated.
     * Bit 2 = 1 - FIDA  curve calculated.
     * Bit 3 = 1 - FILDA curve calculated.
     * Bit 4 = 1 - MCS curve calculated.
     * Bit 5 = 1 - 3D Image calculated.
     */
    val fcsDecayCalc: UShort = buf.us

    /** Macro time clock in 0.1 ns units.  */
    val mtResol: UInt = buf.ui

    /** Correlation time [ms].  */
    val cortime: Float = buf.f

    /** No of photons.  */
    val calcPhotons: UInt = buf.ui

    /** No of FCS values.  */
    val fcsPoints: Int = buf.i

    /** Macro time of the last photon.  */
    val endTime: Float = buf.f

    /**
     * No of Fifo overruns
     * when &gt; 0  fcs curve &amp; endTime are not valid.
     */
    val overruns: UShort = buf.us

    /**
     * 0 - linear FCS with log binning (100 bins/log)
     * when bit 15 = 1 (0x8000) - Multi-Tau FCS
     * where bits 14-0 = ktau parameter.
     */
    val fcsType: UShort = buf.us

    /**
     * Cross FCS routing channel number
     * when chan = crossChan and mod == crossMod - Auto FCS
     * otherwise - Cross FCS.
     */
    val crossChan: UShort = buf.us

    /** Module number.  */
    val mod: UShort = buf.us

    /** Cross FCS module number.  */
    val crossMod: UShort = buf.us

    /** Macro time clock of cross FCS module in 0.1 ns units.  */
    val crossMtResol: UInt = buf.ui
}

class SdtExtendedMeasureInfo(buf: BufferedSource) {

    // -- Fields - extended MeasureInfo -

    /**
     * 4 subsequent fields valid only for Camera mode
     * or FIFO_IMAGE mode.
     */
    val imageX: Int = buf.i
    val imageY: Int = buf.i
    val imageRX: Int = buf.i
    val imageRY: Int = buf.i

    /** Gain for XY ADCs (SPC930).  */
    val xyGain: Short = buf.i16

    /** Use or not  Master Clock (SPC140 multi-module).  */
    val masterClock: Short = buf.i16

    /** ADC sample delay (SPC-930).  */
    val adcDE: Short = buf.i16

    /** Detector type (SPC-930 in camera mode).  */
    val detType: Short = buf.i16

    /** X axis representation (SPC-930).  */
    val xAxis: Short = buf.i16
}

class SdtMeasHISTInfo(buf: BufferedSource) {

    // -- Fields - MeasHISTInfo --

    /** Interval time [ms] for FIDA histogram.  */
    val fidaTime: Float = buf.f

    /** Interval time [ms] for FILDA histogram.  */
    val fildaTime: Float = buf.f

    /** No of FIDA values.  */
    val fidaPoints: Int = buf.i

    /** No of FILDA values.  */
    val fildaPoints: Int = buf.i

    /** Interval time [ms] for MCS histogram.  */
    val mcsTime: Float = buf.f

    /** No of MCS values.  */
    val mcsPoints: Int = buf.i
}