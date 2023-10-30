package io.scif.formats

import io.scif.`==`
import io.scif.api.hostFileSystem
import io.scif.api.readOnly
import io.scif.formats.dicom.DicomFormat
import okio.Path.Companion.toPath
import kotlin.test.Test

class DicomTest {

    val String.path
        get() = hostFileSystem.canonicalize("./build/resources/$this.DCM".toPath())

    @Test
    fun `0002`() {
        val meta = DicomFormat.Metadata()
        "0002"
            .path
            .readOnly { DicomFormat.Parser().typedParse(it, meta) }
        meta.apply {
            isJPEG `==` true
            imagesPerFile `==` 96
            imageType `==` "DERIVED\\PRIMARY\\SINGLE PLANE\\SINGLE A"
        }
    }
}