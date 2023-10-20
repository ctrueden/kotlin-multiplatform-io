package sdt

import HostFileSystem
import io.scif.sdt.SdtFormat
import okio.Path.Companion.toPath
import okio.use
import kotlin.test.Test

class SdtTest {

    @Test
    fun std() {
        val path = "/home/elect/IdeaProjects/scifio-lifesci/test.sdt".toPath()
        HostFileSystem.openReadOnly(path).use(::SdtFormat)
//        val std = SDTInfo()
    }
}