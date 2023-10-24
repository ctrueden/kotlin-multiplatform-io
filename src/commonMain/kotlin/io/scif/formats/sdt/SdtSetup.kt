package io.scif.formats.sdt

import io.scif.api.L
import io.scif.api.f
import io.scif.api.i
import io.scif.api.i8
import uns.i8
import uns.ui

fun SdtFormat.SdtSetup(setup: String): SdtSetup {
    val (pr, rest) = setup.lines()
            .filter { it.startsWith("  #") }
            .partition { it.startsWith("  #PR") }
    val (sp, rest1) = rest.partition { it.startsWith("  #SP") }
    val (di, rest2) = rest1.partition { it.startsWith("  #DI") }
    val (tr, wi) = rest2.partition { it.startsWith("  #TR") }
    return SdtSetup(pr, sp, di, tr, wi, this)
}

// TODO `#MP`
class SdtSetup(pr: List<String>,
               sp: List<String>,
               di: List<String>,
               tr: List<String>,
               wi: List<String>,
               format: SdtFormat) {

    val pr = PR(pr)
    val sp = SP(sp, format)
    val di = DI(di)
    val tr: List<IntArray> = List(tr.size) { IntArray(8) }
    val wi: List<WI> = List(wi.size) { WI(wi[it]) }

    var channels = 0

    var timepoints: Int = 0

    inner class PR(pr: List<String>) {
        var pdev = 0
        var pport = 0
        var pwhat = 0
        var pf = 0.i8
        var pfname = ""
        var porien = 0
        var peject = 0.i8
        var pwidth = 0f
        var pheigh = 0f
        var pfull = 0.i8
        var pauto = 0.i8
        var stpFn = ""
        var saveT = 0
        var sleep = 0

        init {
            for (token in pr) {
                val prefix = "  #PR [PR_".length
                val key = token.substring(prefix, token.indexOf(',', prefix))
                val v = token.substring(token.lastIndexOf(',') + 1, token.length - 1)
                when (key) {
                    "PDEV" -> pdev = v.i
                    "PPORT" -> pport = v.i
                    "PWHAT" -> pwhat = v.i
                    "PF" -> pf = v.i8
                    "PFNAME" -> pfname = v
                    "PORIEN" -> porien = v.i
                    "PEJECT" -> peject = v.i8
                    "PWIDTH" -> pwidth = v.f
                    "PHEIGH" -> pheigh = v.f
                    "PFULL" -> pfull = v.i8
                    "PAUTO" -> pauto = v.i8
                    "STP_FN" -> stpFn = v
                    "SAVE_T" -> saveT = v.i
                    "SLEEP" -> sleep = v.i
                }
            }
        }
    }

    inner class SP(sp: List<String>, format: SdtFormat) {
        var mode = 0
        var cfdLL = 0f
        var cfdLH = 0f
        var cfdZC = 0f
        var cfdHF = 0f
        var synZC = 0f
        var synFD = 0
        var synFQ = 0f
        var synHF = 0f
        var tacR = 0f
        var tacG = 0
        var tacOF = 0f
        var tacLL = 0f
        var tacLH = 0f
        var tacTC = 0f
        var tacTD = 0f
        var adcRE = 0
        var ealDE = 0
        var ncx = 0
        var ncy = 0
        var page = 0
        var colT = 0f
        var repT = 0f
        var disT = 0f
        var repeat = 0.i8
        var stopt = 0.i8
        var overfl = '\u0000'
        var wlSta = 0f
        var wlSto = 0f
        var wlSte = 0f
        var extst = 0.i8
        var steps = 0
        var offset = 0f
        var ywinN = 0
        var xwinN = 0
        var twinN = 0
        var xEqu = 0.i8
        var yEqu = 0.i8
        var tEqu = 0.i8
        var dith = 0
        var enInt = 0.i8
        var incr = 0
        var daes = 0.i8
        var speFN = ""
        var cycles = 0u
        var daec = 0.i8
        var memBank = 0
        var dtcomp = 0.i8
        var scf = 0
        var polL = 0
        var polF = 0
        var pix = 0
        var ldiv = 0
        var accum = 0.i8
        var rout = 0.i8
        var tacEH = 0f
        var dlim = 0
        var photF = 0u
        var enDlim = 0.i8
        var phBuf = 0
        var flbY = 0
        var flbX = 0
        var bordU = 0
        var bordL = 0
        var polP = 0
        var pixT = 0f
        var pixClk = 0
        var trigger = 0
        var scanX = 0
        var scanY = 0
        var scanRX = 0
        var scanRY = 0
        var fifTyp = 0
        var epxDiv = 0
        var mtclk = 0
        var addSel = 0
        var sxwinN = 0
        var sywinN = 0
        var sxEqu = 0.i8
        var syEqu = 0.i8
        var asave = 0
        var adcZoom = 0
        var fifFno = 0
        var fcycles = 0u
        var xyGain = 0
        var imgX = 0
        var imgY = 0
        var imgRX = 0
        var imgRY = 0
        var mstClk = 0
        var adcDE = 0
        var detTyp = 0
        var xAxis = 0
        var chEN = 0
        var chSL = 0
        var chF1 = 0
        var chF2 = 0

        init {
            for (token in sp) {
                val prefix = "  #SP [SP_".length
                val key = token.substring(prefix, token.indexOf(',', prefix))
                val v = token.substring(token.lastIndexOf(',') + 1, token.length - 1)
                when (key) {
                    "MODE" -> mode = v.i
                    "CFD_LL" -> cfdLL = v.f
                    "CFD_LH" -> cfdLH = v.f
                    "CFD_ZC" -> cfdZC = v.f
                    "CFD_HF" -> cfdHF = v.f
                    "SYN_ZC" -> synZC = v.f
                    "SYN_FD" -> synFD = v.i
                    "SYN_FQ" -> synFQ = v.f
                    "SYN_HF" -> synHF = v.f
                    "TAC_R" -> tacR = v.f
                    "TAC_G" -> tacG = v.i
                    "TAC_OF" -> tacOF = v.f
                    "TAC_LL" -> tacLL = v.f
                    "TAC_LH" -> tacLH = v.f
                    "TAC_TC" -> tacTC = v.f
                    "TAC_TD" -> tacTD = v.f
                    "ADC_RE" -> {
                        adcRE = v.i
                        format.height = adcRE
                    }
                    "EAL_DE" -> ealDE = v.i
                    "NCX" -> ncx = v.i
                    "NCY" -> ncy = v.i
                    "PAGE" -> page = v.i
                    "COL_T" -> colT = v.f
                    "REP_T" -> repT = v.f
                    "DIS_T" -> disT = v.f
                    "REPEAT" -> repeat = v.i8
                    "STOPT" -> stopt = v.i8
                    "OVERFL" -> overfl = v[0]
                    "WL_STA" -> wlSta = v.f
                    "WL_STO" -> wlSto = v.f
                    "WL_STE" -> wlSte = v.f
                    "EXTST" -> extst = v.i8
                    "STEPS" -> steps = v.i
                    "OFFSET" -> offset = v.f
                    "YWIN_N" -> ywinN = v.i
                    "XWIN_N" -> xwinN = v.i
                    "TWIN_N" -> twinN = v.i
                    "X_EQU" -> xEqu = v.i8
                    "Y_EQU" -> yEqu = v.i8
                    "T_EQU" -> tEqu = v.i8
                    "DITH" -> dith = v.i
                    "EN_INT" -> enInt = v.i8
                    "INCR" -> incr = v.i
                    "DAES" -> daes = v.i8
                    "SPE_FN" -> speFN = v
                    "CYCLES" -> cycles = v.i.ui
                    "DAEC" -> daec = v.i8
                    "MEM_BANK" -> memBank = v.i
                    "DTCOMP" -> dtcomp = v.i8
                    "SCF" -> scf = v.i
                    "POL_L" -> polL = v.i
                    "POL_F" -> polF = v.i
                    "PIX" -> pix = v.i
                    "LDIV" -> ldiv = v.i
                    "ACCUM" -> accum = v.i8
                    "ROUT" -> rout = v.i8
                    "TAC_EH" -> tacEH = v.f
                    "DLIM" -> dlim = v.i
                    "PHOT_F" -> photF = v.i.ui
                    "EN_DLIM" -> enDlim = v.i8
                    "PH_BUF" -> phBuf = v.i
                    "FLB_Y" -> flbY = v.i
                    "FLB_X" -> flbX = v.i
                    "BORD_U" -> bordU = v.i
                    "BORD_L" -> bordL = v.i
                    "POL_P" -> polP = v.i
                    "PIX_T" -> pixT = v.f
                    "PIX_CLK" -> pixClk = v.i
                    "TRIGGER" -> trigger = v.i
                    "SCAN_X" -> {
                        scanX = v.i
                        format.width = scanX
                    }
                    "SCAN_Y" -> {
                        scanY = v.i
                        format.height = scanY
                    }
                    "SCAN_RX" -> {
                        scanRX = v.i
                        channels = nonZeroProduct(channels, scanRX)
                    }
                    "SCAN_RY" -> {
                        scanRY = v.i
                        channels = nonZeroProduct(channels, scanRY)
                    }
                    "FIF_TYP" -> fifTyp = v.i
                    "EPX_DIV" -> epxDiv = v.i
                    "MTCLK" -> mtclk = v.i
                    "ADD_SEL" -> addSel = v.i
                    "SXWIN_N" -> sxwinN = v.i
                    "SYWIN_N" -> sywinN = v.i
                    "SX_EQU" -> sxEqu = v.i8
                    "SY_EQU" -> syEqu = v.i8
                    "ASAVE" -> asave = v.i
                    "ADC_ZOOM" -> adcZoom = v.i
                    "FIF_FNO" -> fifFno = v.i
                    "FCYCLES" -> fcycles = v.i.ui
                    "XY_GAIN" -> xyGain = v.i
                    "IMG_X" -> imgX = v.i
                    "IMG_Y" -> imgY = v.i
                    "IMG_RX" -> imgRX = v.i
                    "IMG_RY" -> imgRY = v.i
                    "MST_CLK" -> mstClk = v.i
                    "ADC_DE" -> adcDE = v.i
                    "DET_TYP" -> detTyp = v.i
                    "X_AXIS" -> xAxis = v.i
                    "CH_EN" -> chEN = v.i
                    "CH_SL" -> chSL = v.i
                    "CH_F1" -> chF1 = v.i
                    "CH_F2" -> chF2 = v.i
                }
            }
        }
    }

    inner class DI(di: List<String>) {
        var scale = 0
        var maxcnt = 0L
        var lbline = 0L
        var bline = 0L
        var grid = 0.i8
        var gcolF = 0
        var gcolB = 0
        var trace = 0
        var bodC = 0
        var _2ddis = 0
        var _2dtrno = 0
        var _3doffx = 0
        var _3doffy = 0
        var _3dincx = 0
        var _3dcol = 0
        var _3dmode = 0
        var ywin = 0
        var xwin = 0
        var twin = 0
        var pstyle = 0
        var pfreq = 0
        var cur = 0.i8
        var rate = 0.i8
        var _2dc1 = 0.i8
        var _2dc2 = 0.i8
        var _2dc1c = 0
        var _2dc2c = 0
        var _2dc1s = 0
        var _2dc2s = 0
        var _3dc1c = 0
        var _3dc2c = 0
        var size = 0
        var _3dstyl = 0
        var intcol = 0
        var intpix = 0
        var colNo = 0
        var colmin = 0
        var colmax = 0
        var col2 = 0
        var col3 = 0
        var col4 = 0
        var col5 = 0
        var hicol = 0
        var xrev = 0.i8
        var yrev = 0.i8
        var detail = 0.i8
        var sywin = 0
        var sxwin = 0
        var ascale = 0

        init {
            for (token in di) {
                val prefix = "  #DI [DI_".length
                val key = token.substring(prefix, token.indexOf(',', prefix))
                val v = token.substring(token.lastIndexOf(',') + 1, token.length - 1)
                when (key) {
                    "SCALE" -> scale = v.i
                    "MAXCNT" -> maxcnt = v.L
                    "LBLINE" -> lbline = v.L
                    "BLINE" -> bline = v.L
                    "GRID" -> grid = v.i8
                    "GCOL_F" -> gcolF = v.i
                    "GCOL_B" -> gcolB = v.i
                    "TRACE" -> trace = v.i
                    "BOD_C" -> bodC = v.i
                    "2DDIS" -> _2ddis = v.i
                    "2DTRNO" -> _2dtrno = v.i
                    "3DOFFX" -> _3doffx = v.i
                    "3DOFFY" -> _3doffy = v.i
                    "3DINCX" -> _3dincx = v.i
                    "3DCOL" -> _3dcol = v.i
                    "3DMODE" -> _3dmode = v.i
                    "YWIN" -> ywin = v.i
                    "XWIN" -> xwin = v.i
                    "TWIN" -> twin = v.i
                    "PSTYLE" -> pstyle = v.i
                    "PFREQ" -> pfreq = v.i
                    "CUR" -> cur = v.i8
                    "RATE" -> rate = v.i8
                    "2DC1" -> _2dc1 = v.i8
                    "2DC2" -> _2dc2 = v.i8
                    "2DC1C" -> _2dc1c = v.i
                    "2DC2C" -> _2dc2c = v.i
                    "2DC1S" -> _2dc1s = v.i
                    "2DC2S" -> _2dc2s = v.i
                    "3DC1C" -> _3dc1c = v.i
                    "3DC2C" -> _3dc2c = v.i
                    "SIZE" -> size = v.i
                    "3DSTYL" -> _3dstyl = v.i
                    "INTCOL" -> intcol = v.i
                    "INTPIX" -> intpix = v.i
                    "COL_NO" -> colNo = v.i
                    "COLMIN" -> colmin = v.i
                    "COLMAX" -> colmax = v.i
                    "COL2" -> col2 = v.i
                    "COL3" -> col3 = v.i
                    "COL4" -> col4 = v.i
                    "COL5" -> col5 = v.i
                    "HICOL" -> hicol = v.i
                    "XREV" -> xrev = v.i8
                    "YREV" -> yrev = v.i8
                    "DETAIL" -> detail = v.i8
                    "SYWIN" -> sywin = v.i
                    "SXWIN" -> sxwin = v.i
                    "ASCALE" -> ascale = v.i
                }
            }
        }
    }

    init {
        for (ti in tr.indices) {
            val ints = tr[ti].substringAfter('[').substringBefore(']').split(',')
            for (i in 0..7)
                this.tr[ti][i] = ints[i].i
        }
    }

    fun WI(token: String): WI {
        val split = token.drop("  #WI #".length).split(' ')
        val a = split[0].i
        val b = split[1].drop(1) == "*YES"
        val c = split[2].drop(1).i
        val (x, y) = split[3].drop(1).dropLast(1).split(',')
        val dx = x.i
        val dy = y.i
        return WI(a, b, c, dx, dy)
    }

    data class WI(val a: Int, val b: Boolean, val c: Int, val dx: Int, val dy: Int)
}