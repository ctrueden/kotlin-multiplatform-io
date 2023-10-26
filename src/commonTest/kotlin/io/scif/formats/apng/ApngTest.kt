package io.scif.formats.apng

import io.scif.`==`
import io.scif.api.hostFileSystem
import io.scif.api.readOnly
import okio.Path.Companion.toPath
import okio.use
import uns.i8
import kotlin.test.BeforeTest
import kotlin.test.Test

class ApngTest {

    val String.path
        get() = hostFileSystem.canonicalize("./build/resources/apng/$this.png".toPath())

    @Test
    fun `scifio-test`() {
        val meta = APNGFormat.Metadata()
        "scifio-test"
            .path
//            .readOnly { ApngParser().typedParse(it, meta) }
//            .apply {
//            idat.size `==` 9
//            idat[0].apply {
//                offset `==` 136L
//                length `==` 8192
//            }
//            idat[1].apply {
//                offset `==` 8340L
//                length `==` 8192
//            }
//            idat[2].apply {
//                offset `==` 16544L
//                length `==` 8192
//            }
//            idat[3].apply {
//                offset `==` 24748L
//                length `==` 8192
//            }
//            idat[4].apply {
//                offset `==` 32952L
//                length `==` 8192
//            }
//            idat[5].apply {
//                offset `==` 41156L
//                length `==` 8192
//            }
//            idat[6].apply {
//                offset `==` 49360L
//                length `==` 8192
//            }
//            idat[7].apply {
//                offset `==` 57564L
//                length `==` 8192
//            }
//            idat[8].apply {
//                offset `==` 65768L
//                length `==` 6695
//            }
//            fctl.size `==` 0
//            actl `==` null
//            ihdr!!.apply {
//                width `==` 500
//                height `==` 500
//                bitDepth `==` 8
//                colourType `==` 2
//                compressionMethod `==` 0.i8
//                filterMethod `==` 0.i8
//                interlaceMethod `==` 0.i8
//                offset `==` 16L
//                length `==` 13
//            }
//            plte `==` null
//            iend!!.apply {
//                offset `==` 72475L
//                length `==` 0
//            }
//        }
//    }
//    @Test
//    fun `scifio-test-with-alpha`() {
//        val meta = APNGFormat.Metadata()
//        HostFileSystem.openReadOnly("scifio-test-with-alpha".path).use { ApngParser().typedParse(it, meta) }
//        meta.apply {
//            idat.size `==` 13
//            idat[0].apply {
//                offset `==` 136L
//                length `==` 8192
//            }
//            idat[1].apply {
//                offset `==` 8340L
//                length `==` 8192
//            }
//            idat[2].apply {
//                offset `==` 16544L
//                length `==` 8192
//            }
//            idat[3].apply {
//                offset `==` 24748L
//                length `==` 8192
//            }
//            idat[4].apply {
//                offset `==` 32952L
//                length `==` 8192
//            }
//            idat[5].apply {
//                offset `==` 41156L
//                length `==` 8192
//            }
//            idat[6].apply {
//                offset `==` 49360L
//                length `==` 8192
//            }
//            idat[7].apply {
//                offset `==` 57564L
//                length `==` 8192
//            }
//            idat[8].apply {
//                offset `==` 65768L
//                length `==` 8192
//            }
//            idat[9].apply {
//                offset `==` 73972L
//                length `==` 8192
//            }
//            idat[10].apply {
//                offset `==` 82176L
//                length `==` 8192
//            }
//            idat[11].apply {
//                offset `==` 90380L
//                length `==` 8192
//            }
//            idat[12].apply {
//                offset `==` 98584L
//                length `==` 4476
//            }
//            fctl.size `==` 0
//            actl `==` null
//            ihdr!!.apply {
//                width `==` 500
//                height `==` 500
//                bitDepth `==` 8
//                colourType `==` 6
//                compressionMethod `==` 0.i8
//                filterMethod `==` 0.i8
//                interlaceMethod `==` 0.i8
//                offset `==` 16L
//                length `==` 13
//            }
//            plte `==` null
//            iend!!.apply {
//                offset `==` 103072L
//                length `==` 0
//            }
//        }
//    }
//    @Test
//    fun `scifio-test-animated`() {
//        val meta = APNGFormat.Metadata()
//        HostFileSystem.openReadOnly("scifio-test-animated".path).use { ApngParser().typedParse(it, meta) }
//        meta.apply {
//            idat.size `==` 1
//            idat[0].apply {
//                offset `==` 99L
//                length `==` 28426
//            }
//            fctl.size `==` 453
//            fctl[0].apply {
//                sequenceNumber `==` 0
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 0
//                offset `==` 61
//                length `==` 26
//            }
//            fctl[99].apply {
//                sequenceNumber `==` 197
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 1
//                fdatChunks[0].apply {
//                    sequenceNumber `==` 198
//                    offset `==` 251205
//                    length `==` 3365
//                }
//                offset `==` 251167
//                length `==` 26
//            }
//            fctl[199].apply {
//                sequenceNumber `==` 397
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 1
//                fdatChunks[0].apply {
//                    sequenceNumber `==` 398
//                    offset `==` 487945
//                    length `==` 2880
//                }
//                offset `==` 487907
//                length `==` 26
//            }
//            fctl[299].apply {
//                sequenceNumber `==` 597
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 1
//                fdatChunks[0].apply {
//                    sequenceNumber `==` 598
//                    offset `==` 680503
//                    length `==` 1079
//                }
//                offset `==` 680465
//                length `==` 26
//            }
//            fctl[399].apply {
//                sequenceNumber `==` 797
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 1
//                fdatChunks[0].apply {
//                    sequenceNumber `==` 798
//                    offset `==` 898091
//                    length `==` 2017
//                }
//                offset `==` 898053
//                length `==` 26
//            }
//            fctl[452].apply {
//                sequenceNumber `==` 903
//                width `==` 530
//                height `==` 480
//                xOffset `==` 0
//                yOffset `==` 0
//                delayNum `==` 0
//                delayDen `==` 0
//                disposeOp `==` 0
//                blendOp `==` 0
//                fdatChunks.size `==` 1
//                fdatChunks[0].apply {
//                    sequenceNumber `==` 904
//                    offset `==` 975252
//                    length `==` 273
//                }
//                offset `==` 975214
//                length `==` 26
//            }
//            actl!!.apply {
//                sequenceNumber `==` 0
//                numFrames `==` 453
//                numPlays `==` 0
//                offset `==` 41
//                length `==` 8
//            }
//            ihdr!!.apply {
//                width `==` 530
//                height `==` 480
//                bitDepth `==` 8
//                colourType `==` 0
//                compressionMethod `==` 0.i8
//                filterMethod `==` 0.i8
//                interlaceMethod `==` 0.i8
//                offset `==` 16L
//                length `==` 13
//            }
//            plte `==` null
//            iend!!.apply {
//                offset `==` 975537L
//                length `==` 0
//            }
//        }
    }
}