/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2017 SCIFIO developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.scif

import io.scif.config.SCIFIOConfig
import io.scif.util.FormatTools
import okio.FileHandle
import okio.IOException
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.handle.DataHandleService
//import org.scijava.io.location.BytesLocation
//import org.scijava.io.location.Location
//import org.scijava.plugin.Parameter

/**
 * Abstract superclass of all SCIFIO [io.scif.Checker] implementations.
 *
 * @see io.scif.Checker
 *
 * @see io.scif.HasFormat
 *
 * @author Mark Hiner
 */
abstract class AbstractChecker : AbstractHasFormat(), Checker {
//    @Parameter
//    private val handles: DataHandleService? = null

    // -- Checker API Methods --
    override val suffixNecessary: Boolean
        get() = true

    override val suffixSufficient: Boolean
        get() = true

    fun isFormat(/*loc: Location*/name: String, config: SCIFIOConfig = SCIFIOConfig()): Boolean {

        val open: Boolean = config.checkerIsOpen()

        // if file extension ID is insufficient and we can't open the file, give
        // up
        if (!suffixSufficient && !open) return false

        if (suffixNecessary || suffixSufficient) {
            // it's worth checking the file extension
            val suffixMatch: Boolean = FormatTools.checkSuffix(/*loc.*/name, format!!.suffixes)

            // if suffix match is required but it doesn't match, failure
            if (suffixNecessary && !suffixMatch) return false

            // if suffix matches and that's all we need, green light it
            if (suffixMatch && suffixSufficient) return true
        }

        // suffix matching was inconclusive; we need to analyze the file
        // contents
        if (!open) return false // not allowed to open any files

        TODO("suffix matching was inconclusive; we need to analyze the file contents")
//        try {
//            handles.readBuffer(loc).use { handle ->
//                if (handle == null) return false
//                return isFormat(handle)
//            }
//        } catch (exc: IOException) {
//            log().debug("", exc)
//            return false
//        }
    }

    @Throws(IOException::class)
    open fun isFormat(handle: FileHandle): Boolean = false

    override fun checkHeader(block: ByteArray): Boolean {
//        val loc: BytesLocation = BytesLocation(block)
//        try {
//            handles.create(loc).use { handle ->
//                return isFormat(handle)
//            }
//        } catch (e: java.io.IOException) {
//            log().debug("", e)
//        }
        return false
    }
}