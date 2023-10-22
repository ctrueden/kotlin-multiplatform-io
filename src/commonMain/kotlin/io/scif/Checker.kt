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

/**
 * Interface for all SCIFIO Checker components.
 *
 *
 * `Checker` components are used to determine if the `Format` they
 * are associated with is compatibile with a given image. This is accomplished
 * via the [.isFormat] methods.
 *
 *
 * @see io.scif.Format
 *
 * @see io.scif.HasFormat
 *
 * @author Mark Hiner
 */
interface Checker/* : HasFormat*/ {
    // -- Checker API methods --
    /**
     * Whether the file extension matching one of the format's suffixes is
     * necessary to identify the file as a source compatible with this format.
     */
    val suffixNecessary: Boolean

    /**
     * Whether the file extension matching one of the format's suffixes is
     * sufficient to identify the file as a source compatible with this format.
     *
     *
     * If false, the source will have to be read to determine compatibility.
     *
     */
    val suffixSufficient: Boolean

    /**
     * Checks if the provided image source is compatible with this `Format`.
     * Will not open the source during this process.
     *
     * @param loc path to the image source to check.
     * @return True if the image source is compatible with this `Format`.
     */
//    fun isFormat(loc: Location?): Boolean

    /**
     * Checks if the provided image source is compatible with this `Format`.
     *
     *
     * If `open` is true and the source name is insufficient to determine
     * the image type, the source may be opened for further analysis, or other
     * relatively expensive file system operations (such as file existence tests
     * and directory listings) may be performed.
     *
     *
     * @param loc path to the image source to check.
     * @param config [SCIFIOConfig] for this isFormat call.
     * @return True if the image source is compatible with this `Format`.
     */
//    fun isFormat(loc: Location?, config: SCIFIOConfig?): Boolean

    /**
     * Checks if the given stream is a valid stream for this `Format`.
     *
     * @param stream the image source to check.
     * @return True if `stream` is compatible with this `Format`.
     * @throws IOException
     */
//    @Throws(java.io.IOException::class) fun isFormat(stream: DataHandle<Location?>?): Boolean

    /**
     * Checks if the given bytes are a valid header for this `Format`.
     *
     * @param block the byte array to check.
     * @return True if `block` is compatible with this `Format`.
     */
    fun checkHeader(block: ByteArray): Boolean
}