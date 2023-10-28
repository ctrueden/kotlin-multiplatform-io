package io.scif.formats

import io.scif.`==`
import io.scif.api.hostFileSystem
import io.scif.api.readOnly
import io.scif.formats.bmp.BmpFormat
import okio.Path.Companion.toPath
import uns.us
import kotlin.test.Test

class BmpTest {
    val String.path
        get() = hostFileSystem.canonicalize("./build/resources/$this.bmp".toPath())
    @Test
    fun `kidney_TFl_1`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_1"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 385602u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 355
                height `==` 361
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 385548u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_2`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_2"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 384
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_3`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_3"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 442422u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 383
                height `==` 384
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 442368u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_4`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_4"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 384
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }
    @Test
    fun `kidney_TFl_5`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_5"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 383
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }
    @Test
    fun `kidney_TFl_6`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_6"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 383
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_7`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_7"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 384
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_8`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_8"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 384
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }

    @Test
    fun `kidney_TFl_9`() {
        val meta = BmpFormat.Metadata()
        "kidney_TFl_8"
            .path
            .readOnly { BmpFormat.Parser().typedParse(it, meta) }
        meta.apply {
            fileHeader.apply {
                identifier `==` "BM"
                size `==` 443574u
                reserved0 `==` 0u
                reserved1 `==` 0u
                offset `==` 54u
            }
            dibHeader.apply {
                size `==` 40u
                width `==` 384
                height `==` 385
                planesNumber `==` 1.us
                bitsPerPixel `==` 24.us
                compression `==` 0u
                imageSize `==` 443520u
                pixelSizeX `==` 3780
                pixelSizeY `==` 3780
                nColors `==` 0u
                nImpColors `==` 0u
            }
            palette `==` null
        }
    }
}