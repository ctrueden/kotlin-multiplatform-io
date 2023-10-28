package io.scif.formats

import io.scif.`==`
import io.scif.api.hostFileSystem
import io.scif.api.readOnly
import io.scif.formats.avi.AVIFormat
import okio.Path.Companion.toPath
import kotlin.test.Test

class AviTest {

    val String.path
        get() = hostFileSystem.canonicalize("./build/resources/$this.avi".toPath())

    @Test
    fun `t1-rendering`() {
        val meta = AVIFormat.Metadata()
        "t1-rendering"
            .path
            .readOnly { AVIFormat.Parser().typedParse(it, meta) }
        meta.apply {
            mainHeader.apply {
                microSecPerFrame `==` 100_000u
                maxBytesPerSec `==` 0u
                paddingGranularity `==` 0u
                flags `==` 16u
                totalFrames `==` 36u
                initialFrames `==` 0u
                streams `==` 1u
                suggestedBufferSize `==` 0u
                width `==` 206u
                height `==` 218u
                scaleFactor `==` 0u
                frameRate `==` 0u
                startTime `==` 0u
                length `==` 0u
            }
            dataStreams.size `==` 1
            dataStreams[0].apply {
                header.apply {
                    fccType `==` "vids"
                    fccHandler `==` "DIB "
                    flags `==` 0u
                    priority `==` 0u
                    language `==` 0u
                    initialFrames `==` 0u
                    scale `==` 1u
                    rate `==` 10u
                    start `==` 0u
                    length `==` 36u
                    suggestedBufferSize `==` 0u
                    quality `==` 0xFFFFFFFFu
                    sampleSize `==` 0u
                    frame.apply {
                        left `==` 0
                        top `==` 0
                        right `==` 1_718_776_947
                        bottom `==` 1_064
                    }
                }
                format.apply {
                    size `==` 40u
                    width `==` 208
                    height `==` 218
                    planes `==` 1u
                    bitCount `==` 8u
                    compression `==` 0u
                    sizeImage `==` 0u
                    xPelspermeter `==` 0
                    yPelsPerMeter `==` 0
                    clrUsed `==` 256u
                    clrImportant `==` 0u
                    scanLineSize `==` 208
                }
            }
            offsets `==` arrayListOf(4104uL, 49456uL, 94808uL, 140160uL, 185512uL, 230864uL, 276216uL, 321568uL,
                                     366920uL, 412272uL, 457624uL, 502976uL, 548328uL, 593680uL, 639032uL, 684384uL,
                                     729736uL, 775088uL, 820440uL, 865792uL, 911144uL, 956496uL, 1001848uL, 1047200uL,
                                     1092552uL, 1137904uL, 1183256uL, 1228608uL, 1273960uL, 1319312uL, 1364664uL,
                                     1410016uL, 1455368uL, 1500720uL, 1546072uL, 1591424uL)
            lengths `==` arrayListOf(45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u,
                                     45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u,
                                     45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u, 45344u,
                                     45344u, 45344u, 45344u, 45344u, 45344u, 45344u)
        }
    }
}