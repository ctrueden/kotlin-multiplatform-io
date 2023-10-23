/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2015 Board of Regents of the University of
 * Wisconsin-Madison
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
package io.scif.codec

import java.awt.image.ColorModel

/**
 * Options for compressing and decompressing data.
 */
class CodecOptions {
    /** Width, in pixels, of the image. (READ/WRITE)  */
    var width: Int = 0

    /** Height, in pixels, of the image. (READ/WRITE)  */
    var height: Int = 0

    /** Number of channels. (READ/WRITE)  */
    var channels: Int = 0

    /** Number of bits per channel. (READ/WRITE)  */
    var bitsPerSample: Int = 0

    /** Indicates endianness of pixel data. (READ/WRITE)  */
    var littleEndian: Boolean = false

    /** Indicates whether or not channels are interleaved. (READ/WRITE)  */
    var interleaved: Boolean = false

    /** Indicates whether or not the pixel data is signed. (READ/WRITE)  */
    var signed: Boolean = false

    /**
     * Tile width as it would be provided to:
     * [javax.imageio.ImageWriteParam.setTiling]
     * (WRITE).
     */
    var tileWidth: Int = 0

    /**
     * Tile height as it would be provided to:
     * [javax.imageio.ImageWriteParam.setTiling]
     * (WRITE).
     */
    var tileHeight: Int = 0

    /**
     * Horizontal offset of the tile grid as it would be provided to:
     * [javax.imageio.ImageWriteParam.setTiling]
     * (WRITE).
     */
    var tileGridXOffset: Int = 0

    /**
     * Vertical offset of the tile grid as it would be provided to:
     * [javax.imageio.ImageWriteParam.setTiling]
     * (WRITE).
     */
    var tileGridYOffset: Int = 0

    /**
     * If compressing, this is the maximum number of raw bytes to compress. If
     * decompressing, this is the maximum number of raw bytes to return.
     * (READ/WRITE).
     */
    var maxBytes: Int = 0

    /** Pixels for preceding image (READ/WRITE).  */
    lateinit var previousImage: ByteArray

    /**
     * Used with codecs allowing lossy and lossless compression. Default is set to
     * true (WRITE).
     */
    var lossless: Boolean = false

    /** Color model to use when constructing an image (WRITE).  */
    var colorModel: ColorModel? = null

    /**
     * Compression quality level as it would be provided to:
     * `javax.imageio.ImageWriteParam#compressionQuality` (WRITE).
     */
    var quality: Double = 0.0

    /**
     * Whether or not the decompressed data will be stored as YCbCr.
     */
    var ycbcr: Boolean = false

    // -- Constructors --
    /** Construct a new CodecOptions.  */
    constructor()

    /** Construct a new CodecOptions using the given CodecOptions.  */
    constructor(options: CodecOptions) {
        width = options.width
        height = options.height
        channels = options.channels
        bitsPerSample = options.bitsPerSample
        littleEndian = options.littleEndian
        interleaved = options.interleaved
        signed = options.signed
        maxBytes = options.maxBytes
        previousImage = options.previousImage
        lossless = options.lossless
        colorModel = options.colorModel
        quality = options.quality
        tileWidth = options.tileWidth
        tileHeight = options.tileHeight
        tileGridXOffset = options.tileGridXOffset
        tileGridYOffset = options.tileGridYOffset
        ycbcr = options.ycbcr
    }

    companion object {
        // -- Static methods --
        val defaultOptions: CodecOptions
            /** Return CodecOptions with reasonable default values.  */
            get() {
                val options = CodecOptions()
                options.littleEndian = false
                options.interleaved = false
                options.lossless = true
                options.ycbcr = false
                return options
            }
    }
}