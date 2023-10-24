package io.scif.formats.sdt

import HostFileSystem
import io.scif.`==`
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import okio.Path.Companion.toPath
import okio.use
import uns.*
import kotlin.test.Test

class SdtTest {

    @Test
    fun test() {

        val path = "/home/elect/IdeaProjects/scifio-lifesci/test.sdt".toPath()
        val sdt = HostFileSystem.openReadOnly(path).use(::SdtFormat)

        sdt.apply {
            width `==` 256
            height `==` 256
            timeBins `==` 256
            channels `==` 1
            timepoints `==` 1

            header.apply {
                revision `==` 655.i16
                infoOffs `==` 42
                infoLength `==` 267.i16
                setupOffs `==` 309
                setupLength `==` 25094.us
                dataBlockOffs `==` 25915
                noOfDataBlocks `==` 1.i16
                dataBlockLength `==` 33554432
                measDescBlockOffs `==` 25403
                noOfMeasDescBlocks `==` 1.i16
                measDescBlockLength `==` 512.i16
                headerValid `==` 21845.us
                reserved1 `==` 1u
                reserved2 `==` 0.us
                chksum `==` 52445.us
                extended?.apply {
                    baseOffset `==` 5799.ul
                    softwareRevision `==` 976u
                    paramLength `==` 0u
                    reserved1 `==` 0u
                    reserved2 `==` 0.us
                    fcsOldOffset `==` 86u
                    fcsOldSize `==` 128u
                    gr1Offset `==` 214u
                    gr1Size `==` 580u
                    fcsOffset `==` 794u
                    fcsSize `==` 428u
                    fidaOffset `==` 1222u
                    fidaSize `==` 428u
                    fildaOffset `==` 1650u
                    fildaSize `==` 428u
                    gr2Offset `==` 2078u
                    grNo `==` 8.us
                    hstNo `==` 2.us
                    hstOffset `==` 6718u
                    gvdOffset `==` 7574u
                    gvdSize `==` 112.us
                    fitOffset `==` 7686.us
                    fitSize `==` 92.us
                    extdevOffset `==` 7778.us
                    extdevSize `==` 2646.us
                    binhdrextOffset `==` 10424u
                    binhdrextSize `==` 242.us
                    mcsImgOffset `==` 10666u
                    mcsImgSize `==` 100u
                    momNo `==` 3.us
                    momSize `==` 476.us
                    momOffset `==` 10766u
                    sysparExtOffset `==` 12194u
                    sysparExtSize `==` 256u
                    mosaicOffset `==` 12450u
                    mosaicSize `==` 64u
                    mcsActive `==` 0
                    mcstaPoints `==` 256.us
                    mcstaFlags `==` 0.us
                    mcstaTimePerPoint `==` 0.us
                    mcsOffset `==` 0f
                    mcsTpp `==` 0f
                }
            }
            info.apply {
                id `==` "SPC FCS Data File"
                title `==` "m6_mwctrl1_g1_FAD_pc60_20x_z4"
                version `==` "3  976 M"
                revision `==` "8 bits ADC"
                date `==` LocalDate(2019, 3, 5)
                time `==` LocalTime(9, 29, 54)
                author `==` "Unknown"
                company `==` "Unknown"
                contents `==` ""
            }
            setup.apply {
                pr.apply {
                    pdev `==` 2
                    pport `==` 2
                    pwhat `==` 1
                    pf `==` 0.i8
                    pfname `==` "D:\\SPC400\\APPLICAT\\LW_CVI\\IMAGE.PRT"
                    porien `==` 1
                    peject `==` 1.i8
                    pwidth `==` 100f
                    pheigh `==` 100f
                    pfull `==` 1.i8
                    pauto `==` 1.i8
                    stpFn `==` "e:\\bhdata\\STP.CFG"
                    saveT `==` 0
                    sleep `==` 13
                }
                sp.apply {
                    mode `==` 13
                    cfdLL `==` -50.980392f
                    cfdLH `==` 80f
                    cfdZC `==` -5.2913384f
                    cfdHF `==` 5f
                    synZC `==` -5.2913384f
                    synFD `==` 1
                    synFQ `==` -125.4902f
                    synHF `==` 4f
                    tacR `==` 5.0033574e-08f
                    tacG `==` 4
                    tacOF `==` 7.0588236f
                    tacLL `==` 4.3137255f
                    tacLH `==` 95.686272f
                    tacTC `==` 4.8860912e-11f
                    tacTD `==` 1.2508393e-09f
                    adcRE `==` 256
                    ealDE `==` 0
                    ncx `==` 1
                    ncy `==` 1
                    page `==` 1
                    colT `==` 120f
                    repT `==` 360f
                    disT `==` 1f
                    repeat `==` 0.i8
                    stopt `==` 1.i8
                    overfl `==` 'N'
                    wlSta `==` 500f
                    wlSto `==` 551.09998f
                    wlSte `==` 0.1f
                    extst `==` 0.i8
                    steps `==` 1
                    offset `==` 0f
                    ywinN `==` 8
                    xwinN `==` 8
                    twinN `==` 1
                    xEqu `==` 1.i8
                    yEqu `==` 1.i8
                    tEqu `==` 1.i8
                    dith `==` 128
                    enInt `==` 0.i8
                    incr `==` 1
                    daes `==` 0.i8
                    speFN `==` "e:\\bhdata\\TIMETAG-DATA.SPC"
                    cycles `==` 1u
                    daec `==` 0.i8
                    memBank `==` 0
                    dtcomp `==` 0.i8
                    scf `==` 0
                    polL `==` 1
                    polF `==` 1
                    pix `==` 0
                    ldiv `==` 1
                    accum `==` 0.i8
                    rout `==` 0.i8
                    tacEH `==` 10f
                    dlim `==` 102400
                    photF `==` 26214399u
                    enDlim `==` 1.i8
                    phBuf `==` 102400
                    flbY `==` 1
                    flbX `==` 1
                    bordU `==` 0
                    bordL `==` 0
                    polP `==` 1
                    pixT `==` 4.8000002e-06f
                    pixClk `==` 0
                    trigger `==` 0
                    scanX `==` 256
                    scanY `==` 64
                    scanRX `==` 1
                    scanRY `==` 1
                    fifTyp `==` 0
                    epxDiv `==` 4
                    mtclk `==` 0
                    addSel `==` 0
                    sxwinN `==` 8
                    sywinN `==` 8
                    sxEqu `==` 1.i8
                    syEqu `==` 1.i8
                    asave `==` 48
                    adcZoom `==` 0
                    fifFno `==` 1
                    fcycles `==` 1u
                    xyGain `==` 1
                    imgX `==` 256
                    imgY `==` 256
                    imgRX `==` 1
                    imgRY `==` 1
                    mstClk `==` 0
                    adcDE `==` 0
                    detTyp `==` 0
                    xAxis `==` 0
                    chEN `==` 4191231
                    chSL `==` 16777215
                    chF1 `==` 0
                    chF2 `==` 136314880
                }
                di.apply {
                    scale `==` 1
                    maxcnt `==` 65535
                    lbline `==` 1
                    bline `==` 0
//                    grid `==` 6
                    gcolF `==` 14737632
                    gcolB `==` 16777215
                    trace `==` 0
                    bodC `==` 32896
                    _2ddis `==` 1
                    _2dtrno `==` 8
                    _3doffx `==` 3
                    _3doffy `==` 1
                    _3dincx `==` 0
                    _3dcol `==` 0
                    _3dmode `==` 2
                    ywin `==` 1
                    xwin `==` 1
                    twin `==` 1
                    pstyle `==` 1
                    pfreq `==` 1
                    cur `==` 0.i8
                    rate `==` 1.i8
                    _2dc1 `==` 1.i8
                    _2dc2 `==` 1.i8
                    _2dc1c `==` 10
                    _2dc2c `==` 13
                    _2dc1s `==` 0
                    _2dc2s `==` 0
                    _3dc1c `==` 12
                    _3dc2c `==` 14
                    size `==` 0
                    _3dstyl `==` 1
                    intcol `==` 1
                    intpix `==` 1
                    colNo `==` 2
                    colmin `==` 0
                    colmax `==` 16777215
                    col2 `==` 65280
                    col3 `==` 255
                    col4 `==` 13408716
                    col5 `==` 13421823
                    hicol `==` 16711680
                    xrev `==` 0.i8
                    yrev `==` 0.i8
                    detail `==` 1.i8
                    sywin `==` 3
                    sxwin `==` 1
                    ascale `==` 1
                }
                tr.size `==` 16
                tr[0] `==` intArrayOf(1, 255, 1, 1, 4, 1, 1, 1)
                tr[1] `==` intArrayOf(1, 65280, 1, 1, 1, 2, 1, 1)
                tr[2] `==` intArrayOf(1, 65535, 1, 1, 1, 3, 1, 1)
                tr[3] `==` intArrayOf(1, 16711680, 1, 1, 1, 4, 1, 1)
                tr[4] `==` intArrayOf(1, 16711935, 1, 1, 1, 1, 1, 1)
                tr[5] `==` intArrayOf(1, 16776960, 1, 1, 1, 1, 1, 1)
                tr[6] `==` intArrayOf(1, 0, 1, 1, 1, 1, 1, 1)
                tr[7] `==` intArrayOf(1, 8388608, 1, 1, 1, 1, 1, 1)
                tr[8] `==` intArrayOf(0, 10374982, 1, 1, 1, 1, 1, 1)
                tr[9] `==` intArrayOf(0, 65473, 1, 1, 1, 1, 1, 1)
                tr[10] `==` intArrayOf(0, 33941, 1, 1, 1, 1, 1, 1)
                tr[11] `==` intArrayOf(0, 123, 1, 1, 1, 1, 1, 1)
                tr[12] `==` intArrayOf(0, 9818959, 1, 1, 1, 1, 1, 1)
                tr[13] `==` intArrayOf(0, 16162524, 1, 1, 1, 1, 1, 1)
                tr[14] `==` intArrayOf(0, 13832959, 1, 1, 1, 1, 1, 1)
                tr[15] `==` intArrayOf(0, 8067690, 1, 1, 1, 1, 1, 1)
                wi.size `==` 33
                wi[0] `==` SdtSetup.WI(0, false, 0, 0, 0)
                wi[1] `==` SdtSetup.WI(0, false, 1, 0, 0)
                wi[2] `==` SdtSetup.WI(0, false, 2, 0, 0)
                wi[3] `==` SdtSetup.WI(0, false, 3, 0, 0)
                wi[4] `==` SdtSetup.WI(0, false, 4, 0, 0)
                wi[5] `==` SdtSetup.WI(0, false, 5, 0, 0)
                wi[6] `==` SdtSetup.WI(0, false, 6, 0, 0)
                wi[7] `==` SdtSetup.WI(0, false, 7, 0, 0)
                wi[8] `==` SdtSetup.WI(1, false, 0, 0, 0)
                wi[9] `==` SdtSetup.WI(1, false, 1, 0, 0)
                wi[10] `==` SdtSetup.WI(1, false, 2, 0, 0)
                wi[11] `==` SdtSetup.WI(1, false, 3, 0, 0)
                wi[12] `==` SdtSetup.WI(1, false, 4, 0, 0)
                wi[13] `==` SdtSetup.WI(1, false, 5, 0, 0)
                wi[14] `==` SdtSetup.WI(1, false, 6, 0, 0)
                wi[15] `==` SdtSetup.WI(1, false, 7, 0, 0)
                wi[16] `==` SdtSetup.WI(2, false, 0, 0, 255)
                wi[17] `==` SdtSetup.WI(3, false, 0, 0, 31)
                wi[18] `==` SdtSetup.WI(3, false, 1, 32, 63)
                wi[19] `==` SdtSetup.WI(3, false, 2, 64, 95)
                wi[20] `==` SdtSetup.WI(3, false, 3, 96, 127)
                wi[21] `==` SdtSetup.WI(3, false, 4, 128, 159)
                wi[22] `==` SdtSetup.WI(3, false, 5, 160, 191)
                wi[23] `==` SdtSetup.WI(3, false, 6, 192, 223)
                wi[24] `==` SdtSetup.WI(3, false, 7, 224, 255)
                wi[25] `==` SdtSetup.WI(4, false, 0, 0, 31)
                wi[26] `==` SdtSetup.WI(4, false, 1, 32, 63)
                wi[27] `==` SdtSetup.WI(4, false, 2, 64, 95)
                wi[28] `==` SdtSetup.WI(4, false, 3, 96, 127)
                wi[29] `==` SdtSetup.WI(4, false, 4, 128, 159)
                wi[30] `==` SdtSetup.WI(4, false, 5, 160, 191)
                wi[31] `==` SdtSetup.WI(4, false, 6, 192, 223)
                wi[32] `==` SdtSetup.WI(4, false, 7, 224, 255)
            }
            measureInfo!!.apply {
                time `==` LocalTime(9, 27, 29)
                date `==` LocalDate(2019, 3, 5)
                modSerNo `==` "3F0316"
                measMode `==` 13.i16
                cfdLL `==` -50.980392f
                cfdLH `==` 80f
                cfdZC `==` -5.2913384f
                cfdHF `==` 5f
                synZC `==` -5.2913384f
                synFD `==` 1.i16
                synHF `==` 4f
                tacR `==` 5.0033574E-8f
                tacG `==` 4.i16
                tacOF `==` 7.0588236f
                tacLL `==` 4.3137255f
                tacLH `==` 95.68627f
                adcRE `==` 256.i16
                ealDE `==` 0.i16
                ncx `==` 1.i16
                ncy `==` 1.i16
                page `==` 1.us
                colT `==` 120f
                repT `==` 360f
                stopt `==` 1.i16
                overfl `==` 78.ub
                useMotor `==` 0.i16
                steps `==` 1.us
                offset `==` 0f
                dither `==` 128.i16
                incr `==` 1.i16
                memBank `==` 0.i16
                modType `==` "SPC-150"
                synTH `==` -125.4902f
                deadTimeComp `==` 0.i16
                polarityL `==` 1.i16
                polarityF `==` 1.i16
                polarityP `==` 1.i16
                linediv `==` 1.i16
                accumulate `==` 0.i16
                flbckY `==` 1
                flbckX `==` 1
                bordU `==` 0
                bordL `==` 0
                pixTime `==` 4.8E-6f
                pixClk `==` 0.i16
                trigger `==` 0.i16
                scanX `==` 0
                scanY `==` 0
                scanRX `==` 0
                scanRY `==` 0
                fifoTyp `==` 0.i16
                epxDiv `==` 4
                modTypeCode `==` 150.us
                modFpgaVer `==` 514.us
                overflowCorrFactor `==` 0f
                adcZoom `==` 0
                cycles `==` 1
            }
            measStopInfo!!.apply {
                status `==` 16.us
                flags `==` 33190.us
                stopTime `==` 120.65626f
                curStep `==` 1
                curCycle `==` 1
                curPage `==` 1
                minSyncRate `==` 8.0310016E7f
                minCfdRate `==` 57024f
                minTacRate `==` 56528f
                minAdcRate `==` 55648f
                maxSyncRate `==` 8.0310016E7f
                maxCfdRate `==` 128656f
                maxTacRate `==` 126528f
                maxAdcRate `==` 124800f
                mReserved1 `==` 0
                mReserved2 `==` 0f
            }
            measFCSInfo!!.apply {
                chan `==` 0.us
                fcsDecayCalc `==` 32.us
                mtResol `==` 250u
                cortime `==` 1f
                calcPhotons `==` 14209404u
                fcsPoints `==` 0
                endTime `==` 120.63753f
                overruns `==` 0.us
                fcsType `==` 32801.us
                crossChan `==` 0.us
                mod `==` 0.us
                crossMod `==` 0.us
                crossMtResol `==` 250u
            }
            extendedMeasureInfo!!.apply {
                imageX `==` 256
                imageY `==` 256
                imageRX `==` 1
                imageRY `==` 1
                xyGain `==` 0.i16
                masterClock `==` 0.i16
                adcDE `==` 0.i16
                detType `==` 0.i16
                xAxis `==` 0.i16
            }
            measHISTInfo!!.apply {
                fidaTime `==` 0f
                fildaTime `==` 0f
                fidaPoints `==` 244
                fildaPoints `==` 510
                mcsTime `==` 0f
                mcsPoints `==` 256
            }
            bhFileBlockHeader.size `==` 1
            bhFileBlockHeader[0].apply {
                blockNo `==` 0.i16
                dataOffs `==` 25937
                nextBlockOffs `==` 4892652
                blockType `==` 4201.us
                measDescBlockNo `==` 0.i16
                lblockNo `==` 6291457u
                blockLength `==` 33554432u
                offset `==` 25937uL
            }
        }
    }

    @Test
    fun test1() {
        val path = "/home/elect/Downloads/test1.sdt".toPath()
        val sdt = HostFileSystem.openReadOnly(path).use(::SdtFormat)

        sdt.apply {
            width `==` 128
            height `==` 128
            timeBins `==` 64
            channels `==` 16
            timepoints `==` 1

            header.apply {
                revision `==` 605.i16
                infoOffs `==` 42
                infoLength `==` 282.i16
                setupOffs `==` 324
                setupLength `==` 7026.us
                dataBlockOffs `==` 7709
                noOfDataBlocks `==` 1.i16
                dataBlockLength `==` 33554432
                measDescBlockOffs `==` 7350
                noOfMeasDescBlocks `==` 1.i16
                measDescBlockLength `==` 359.i16
                headerValid `==` 21845.us
                reserved1 `==` 1u
                reserved2 `==` 0.us
                chksum `==` 41409.us
                extended?.apply {
                    baseOffset `==` 5554.ul
                    softwareRevision `==` 850u
                    paramLength `==` 0u
                    reserved1 `==` 0u
                    reserved2 `==` 0.us
                    fcsOldOffset `==` 86u
                    fcsOldSize `==` 128u
                    gr1Offset `==` 214u
                    gr1Size `==` 484u
                    fcsOffset `==` 698u
                    fcsSize `==` 272u
                    fidaOffset `==` 970u
                    fidaSize `==` 272u
                    fildaOffset `==` 1242u
                    fildaSize `==` 272u
                    gr2Offset `==` 1514u
                    grNo `==` 0.us
                    hstNo `==` 1.us
                    hstOffset `==` 1514u
                    gvdOffset `==` 0u
                    gvdSize `==` 0.us
                    fitOffset `==` 0.us
                    fitSize `==` 0.us
                    extdevOffset `==` 0.us
                    extdevSize `==` 0.us
                    binhdrextOffset `==` 0u
                    binhdrextSize `==` 0.us
                    mcsImgOffset `==` 0u
                    mcsImgSize `==` 0u
                    momNo `==` 0.us
                    momSize `==` 0.us
                    momOffset `==` 0u
                    sysparExtOffset `==` 0u
                    sysparExtSize `==` 0u
                    mosaicOffset `==` 0u
                    mosaicSize `==` 0u
                    mcsActive `==` 0
                    mcstaPoints `==` 0.us
                    mcstaFlags `==` 0.us
                    mcstaTimePerPoint `==` 0.us
                    mcsOffset `==` 0f
                    mcsTpp `==` 0f
                }
            }
            info.apply {
                id `==` "SPC Setup & Data File"
                title `==` "slice1_810nm_40x_z1_pcc100_scanin_20s_01"
                version `==` "1  850 M"
                revision `==` "6 bits ADC"
                date `==` LocalDate(2006, 3, 8)
                time `==` LocalTime(14, 26, 58)
                author `==` "Unknown"
                company `==` "Unknown"
                contents `==` ""
            }
            setup.apply {
                pr.apply {
                    pdev `==` 2
                    pport `==` 2
                    pwhat `==` 1
                    pf `==` 0.i8
                    pfname `==` "D:\\SPC400\\APPLICAT\\LW_CVI\\IMAGE.PRT"
                    porien `==` 1
                    peject `==` 1.i8
                    pwidth `==` 100f
                    pheigh `==` 100f
                    pfull `==` 1.i8
                    pauto `==` 1.i8
                    stpFn `==` "C:\\PROGRAM FILES\\BH\\SPCM\\STP.CFG"
                    saveT `==` 0
                    sleep `==` 15
                }
                sp.apply {
                    mode `==` 9
                    cfdLL `==` -19.607843f
                    cfdLH `==` 80f
                    cfdZC `==` 3.023622f
                    cfdHF `==` 5f
                    synZC `==` -7.5590553f
                    synFD `==` 1
                    synFQ `==` -11.764706f
                    synHF `==` 4f
                    tacR `==` 5e-08f
                    tacG `==` 4
                    tacOF `==` 3.5164835f
                    tacLL `==` 9.0196075f
                    tacLH `==` 92.941177f
                    tacTC `==` 1.953125e-10f
                    tacTD `==` 1.25e-09f
                    adcRE `==` 64
                    ealDE `==` 1
                    ncx `==` 16
                    ncy `==` 1
                    page `==` 1
                    colT `==` 20f
                    repT `==` 60f
                    disT `==` 0.19999999f
                    repeat `==` 0.i8
                    stopt `==` 1.i8
                    overfl `==` 'N'
                    wlSta `==` 500f
                    wlSto `==` 551.09998f
                    wlSte `==` 0.1f
                    extst `==` 0.i8
                    steps `==` 1
                    offset `==` 0f
                    ywinN `==` 8
                    xwinN `==` 8
                    twinN `==` 8
                    xEqu `==` 1.i8
                    yEqu `==` 1.i8
                    tEqu `==` 1.i8
                    dith `==` 128
                    enInt `==` 0.i8
                    incr `==` 4
                    daes `==` 0.i8
                    speFN `==` "c:\\program files\\bh\\spcm\\ttt1.sdt"
                    cycles `==` 1u
                    daec `==` 0.i8
                    memBank `==` 0
                    dtcomp `==` 0.i8
                    scf `==` 0
                    polL `==` 1
                    polF `==` 1
                    pix `==` 0
                    ldiv `==` 2
                    accum `==` 0.i8
                    rout `==` 0.i8
                    tacEH `==` 10f
                    dlim `==` 1024
                    photF `==` 262144u
                    enDlim `==` 1.i8
                    phBuf `==` 3000
                    flbY `==` 0
                    flbX `==` 0
                    bordU `==` 0
                    bordL `==` 0
                    polP `==` 0
                    pixT `==` 9.9999997e-06f
                    pixClk `==` 0
                    trigger `==` 16
                    scanX `==` 128
                    scanY `==` 128
                    scanRX `==` 16
                    scanRY `==` 1
                    fifTyp `==` 0
                    epxDiv `==` 4
                    mtclk `==` 0
                    addSel `==` 0
                    sxwinN `==` 8
                    sywinN `==` 8
                    sxEqu `==` 1.i8
                    syEqu `==` 1.i8
                    asave `==` 0
                    adcZoom `==` 0
                    fifFno `==` 1
                    fcycles `==` 1u
                    xyGain `==` 1
                    imgX `==` 1
                    imgY `==` 1
                    imgRX `==` 1
                    imgRY `==` 1
                    mstClk `==` 0
                    adcDE `==` 0
                    detTyp `==` 1
                    xAxis `==` 0
                    chEN `==` 0
                    chSL `==` 0
                    chF1 `==` 0
                    chF2 `==` 0
                }
                di.apply {
                    scale `==` 1
                    maxcnt `==` 65535
                    lbline `==` 1
                    bline `==` 0
                    grid `==` 6
                    gcolF `==` 65535
                    gcolB `==` 16777215
                    trace `==` 0
                    bodC `==` 32768
                    _2ddis `==` 0
                    _2dtrno `==` 2
                    _3doffx `==` 3
                    _3doffy `==` 1
                    _3dincx `==` -31
                    _3dcol `==` 0
                    _3dmode `==` 2
                    ywin `==` 1
                    xwin `==` 8
                    twin `==` 1
                    pstyle `==` 10
                    pfreq `==` 1
                    cur `==` 0.i8
                    rate `==` 1.i8
                    _2dc1 `==` 1.i8
                    _2dc2 `==` 1.i8
                    _2dc1c `==` 10
                    _2dc2c `==` 13
                    _2dc1s `==` 0
                    _2dc2s `==` 0
                    _3dc1c `==` 12
                    _3dc2c `==` 14
                    size `==` 0
                    _3dstyl `==` 1
                    intcol `==` 1
                    intpix `==` 1
                    colNo `==` 2
                    colmin `==` 0
                    colmax `==` 16777215
                    col2 `==` 3355647
                    col3 `==` 6723942
                    col4 `==` 6736998
                    col5 `==` 13421823
                    hicol `==` 16711680
                    xrev `==` 0.i8
                    yrev `==` 0.i8
                    detail `==` 1.i8
                    sywin `==` 1
                    sxwin `==` 1
                    ascale `==` 0
                }
                tr.size `==` 8
                tr[0] `==` intArrayOf(0, 0, 1, 1, 4, 1, 1, 1)
                tr[1] `==` intArrayOf(0, 65280, 2, 1, 1, 2, 2, 1)
                tr[2] `==` intArrayOf(0, 16776960, 3, 1, 1, 3, 3, 1)
                tr[3] `==` intArrayOf(1, 16711680, 4, 1, 1, 4, 4, 1)
                tr[4] `==` intArrayOf(1, 128, 3, 1, 1, 1, 5, 1)
                tr[5] `==` intArrayOf(0, 32768, 6, 1, 1, 1, 6, 1)
                tr[6] `==` intArrayOf(0, 8421376, 7, 1, 1, 1, 7, 1)
                tr[7] `==` intArrayOf(0, 8388608, 8, 1, 1, 1, 8, 1)
                wi.size `==` 40
                wi[0] `==` SdtSetup.WI(0, false, 0, 0, 1)
                wi[1] `==` SdtSetup.WI(0, false, 1, 2, 3)
                wi[2] `==` SdtSetup.WI(0, false, 2, 4, 5)
                wi[3] `==` SdtSetup.WI(0, false, 3, 6, 7)
                wi[4] `==` SdtSetup.WI(0, false, 4, 8, 9)
                wi[5] `==` SdtSetup.WI(0, false, 5, 10, 11)
                wi[6] `==` SdtSetup.WI(0, false, 6, 12, 13)
                wi[7] `==` SdtSetup.WI(0, false, 7, 14, 15)
                wi[10] `==` SdtSetup.WI(1, false, 2, 0, 0)
                wi[11] `==` SdtSetup.WI(1, false, 3, 0, 0)
                wi[12] `==` SdtSetup.WI(1, false, 4, 0, 0)
                wi[13] `==` SdtSetup.WI(1, false, 5, 0, 0)
                wi[14] `==` SdtSetup.WI(1, false, 6, 0, 0)
                wi[15] `==` SdtSetup.WI(1, false, 7, 0, 0)
                wi[16] `==` SdtSetup.WI(2, false, 0, 0, 7)
                wi[17] `==` SdtSetup.WI(2, false, 1, 0, 7)
                wi[18] `==` SdtSetup.WI(2, false, 2, 0, 7)
                wi[19] `==` SdtSetup.WI(2, false, 3, 0, 7)
                wi[20] `==` SdtSetup.WI(2, false, 4, 0, 7)
                wi[21] `==` SdtSetup.WI(2, false, 5, 0, 7)
                wi[22] `==` SdtSetup.WI(2, false, 6, 0, 7)
                wi[23] `==` SdtSetup.WI(2, false, 7, 0, 7)
                wi[24] `==` SdtSetup.WI(3, false, 0, 0, 15)
                wi[25] `==` SdtSetup.WI(3, false, 1, 16, 31)
                wi[26] `==` SdtSetup.WI(3, false, 2, 32, 47)
                wi[27] `==` SdtSetup.WI(3, false, 3, 48, 63)
                wi[28] `==` SdtSetup.WI(3, false, 4, 64, 79)
                wi[29] `==` SdtSetup.WI(3, false, 5, 80, 95)
                wi[30] `==` SdtSetup.WI(3, false, 6, 96, 111)
                wi[31] `==` SdtSetup.WI(3, false, 7, 112, 127)
                wi[32] `==` SdtSetup.WI(4, false, 0, 0, 15)
                wi[33] `==` SdtSetup.WI(4, false, 1, 16, 31)
                wi[34] `==` SdtSetup.WI(4, false, 2, 32, 47)
                wi[35] `==` SdtSetup.WI(4, false, 3, 48, 63)
                wi[36] `==` SdtSetup.WI(4, false, 4, 64, 79)
                wi[37] `==` SdtSetup.WI(4, false, 5, 80, 95)
                wi[38] `==` SdtSetup.WI(4, false, 6, 96, 111)
                wi[39] `==` SdtSetup.WI(4, false, 7, 112, 127)
            }
            measureInfo!!.apply {
                time `==` LocalTime(14, 26, 4)
                date `==` LocalDate(2006, 3, 8)
                modSerNo `==` "3D0049"
                measMode `==` 9.i16
                cfdLL `==` -19.607843f
                cfdLH `==` 80f
                cfdZC `==` 3.023622f
                cfdHF `==` 5f
                synZC `==` -7.5590553f
                synFD `==` 1.i16
                synHF `==` 4f
                tacR `==` 5E-8f
                tacG `==` 4.i16
                tacOF `==` 3.5164835f
                tacLL `==` 9.019608f
                tacLH `==` 92.94118f
                adcRE `==` 64.i16
                ealDE `==` 1.i16
                ncx `==` 16.i16
                ncy `==` 1.i16
                page `==` 1.us
                colT `==` 20f
                repT `==` 60f
                stopt `==` 1.i16
                overfl `==` 78.ub
                useMotor `==` 0.i16
                steps `==` 1.us
                offset `==` 0f
                dither `==` 128.i16
                incr `==` 4.i16
                memBank `==` 0.i16
                modType `==` "SPC-830"
                synTH `==` -11.764706f
                deadTimeComp `==` 0.i16
                polarityL `==` 1.i16
                polarityF `==` 1.i16
                polarityP `==` 0.i16
                linediv `==` 2.i16
                accumulate `==` 0.i16
                flbckY `==` 0
                flbckX `==` 0
                bordU `==` 0
                bordL `==` 0
                pixTime `==` 1.0E-5f
                pixClk `==` 0.i16
                trigger `==` 16.i16
                scanX `==` 128
                scanY `==` 128
                scanRX `==` 16
                scanRY `==` 1
                fifoTyp `==` 0.i16
                epxDiv `==` 4
                modTypeCode `==` 830.us
                modFpgaVer `==` 260.us
                overflowCorrFactor `==` 0f
                adcZoom `==` 0
                cycles `==` 1
            }
            measStopInfo!!.apply {
                status `==` 3624.us
                flags `==` 32774.us
                stopTime `==` 21.199951f
                curStep `==` 1
                curCycle `==` 1
                curPage `==` 1
                minSyncRate `==` 7.6013568E7f
                minCfdRate `==` 2095264f
                minTacRate `==` 598384f
                minAdcRate `==` 315936f
                maxSyncRate `==` 7.6013568E7f
                maxCfdRate `==` 2176032f
                maxTacRate `==` 614256f
                maxAdcRate `==` 596720f
                mReserved1 `==` 0
                mReserved2 `==` 0f
            }
            measFCSInfo!!.apply {
                chan `==` 0.us
                fcsDecayCalc `==` 0.us
                mtResol `==` 0u
                cortime `==` 0f
                calcPhotons `==` 0u
                fcsPoints `==` 0
                endTime `==` 0f
                overruns `==` 0.us
                fcsType `==` 0.us
                crossChan `==` 0.us
                mod `==` 0.us
                crossMod `==` 0.us
                crossMtResol `==` 0u
            }
            extendedMeasureInfo!!.apply {
                imageX `==` 0
                imageY `==` 0
                imageRX `==` 0
                imageRY `==` 0
                xyGain `==` 0.i16
                masterClock `==` 0.i16
                adcDE `==` 0.i16
                detType `==` 0.i16
                xAxis `==` 0.i16
            }
            measHISTInfo!!.apply {
                fidaTime `==` 0f
                fildaTime `==` 0f
                fidaPoints `==` 0
                fildaPoints `==` 0
                mcsTime `==` 0f
                mcsPoints `==` 0
            }
            bhFileBlockHeader.size `==` 1
            bhFileBlockHeader[0].apply {
                blockNo `==` 1.i16
                dataOffs `==` 7731
                nextBlockOffs `==` 33562163
                blockType `==` 19.us
                measDescBlockNo `==` 0.i16
                lblockNo `==` 1u
                blockLength `==` 33554432u
                offset `==` 7731uL
            }
        }
    }
}