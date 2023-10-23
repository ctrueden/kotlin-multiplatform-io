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

import platform.FormatException

//import io.scif.codec.CodecOptions
//import io.scif.config.SCIFIOConfig
//import net.imglib2.Interval
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.location.Location

/**
 * Interface for all SCIFIO writers.
 *
 *
 * Writer components are used to save [io.scif.Plane] objects (output from
 * the [Reader.openBlock] methods) to a destination image location, via
 * the [.savePlane] methods.
 *
 *
 * @author Mark Hiner
 * @see io.scif.Plane
 *
 * @see io.scif.Reader.openBlock
 */
interface Writer : HasFormat, HasSource {
    // -- Writer API methods --
    /**
     * Saves the provided plane to the specified image and plane index of this
     * `Writer's` destination image.
     *
     * @param imageIndex the image index within the dataset.
     * @param planeIndex the plane index within the image.
     * @param plane the pixels save
     * @throws FormatException if one of the parameters is invalid.
     * @throws IOException if there was a problem writing to the file.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun savePlane(imageIndex: Int, planeIndex: Long, plane: Plane?)

    /**
     * Saves the specified tile (sub-region) of the provided plane to the
     * specified image and plane index of this `Writer's` destination image.
     *
     * @param imageIndex the image index within the dataset.
     * @param planeIndex the plane index within the image.
     * @param plane the pixels save
     * @param bounds bounds of the planar axes.
     * @throws FormatException if one of the parameters is invalid.
     * @throws IOException if there was a problem writing to the file.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun savePlane(imageIndex: Int, planeIndex: Long, plane: Plane?,
//                                                                              bounds: Interval?)

    /**
     * @return True if this `Writer` can save multiple images to a single
     * file.
     */
    fun canDoStacks(): Boolean

    /**
     * Gets the `Metadata` that will be used when saving planes.
     *
     * @return The `Metadata` associated with this `Writer`.
     */
//    @set:Throws(FormatException::class)
    var metadata: Metadata

    /**
     * Sets the source that will be written to during [.savePlane] calls.
     * NB: resets any configuration on this writer.
     *
     * @param location The [Location] to where the image source will be
     * written.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(location: Location?)

    /**
     * Sets the source that will be written to during [.savePlane] calls.
     * NB: resets any configuration on this writer.
     *
     * @param handle The image source to write to.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(handle: DataHandle<Location?>?)

    /**
     * As [.setDest] with specification for source image index.
     *
     * @param location The [Location] of an image source to be written.
     * @param imageIndex The index within the source that will be written.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(location: Location?, imageIndex: Int)

    /**
     * As [.setDest], with specification for source image index.
     *
     * @param handle The image source to write to.
     * @param imageIndex The index within the source that will be written.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(handle: DataHandle<Location?>?, imageIndex: Int)

    /**
     * As [.setDest] with specification for new configuration
     * options.
     *
     * @param location The [Location] of an image source to be written.
     * @param config Configuration information to use for this write.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(location: Location?, config: SCIFIOConfig?)

    /**
     * As [.setDest], with specification for new configuration
     * options.
     *
     * @param handle The image source to write to.
     * @param config Configuration information to use for this write.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(handle: DataHandle<Location?>?, config: SCIFIOConfig?)

    /**
     * As [.setDest] with specification for new configuration
     * options.
     *
     * @param location The [Location] of an image source to be written.
     * @param imageIndex The index within the source that will be written.
     * @param config Configuration information to use for this write.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(location: Location?, imageIndex: Int, config: SCIFIOConfig?)

    /**
     * As [.setDest], with specification for new
     * configuration options.
     *
     * @param handle The image source to write to.
     * @param imageIndex The index within the source that will be written.
     * @param config Configuration information to use for this write.
     */
//    @Throws(FormatException::class, java.io.IOException::class) fun setDest(handle: DataHandle<Location?>?, imageIndex: Int, config: SCIFIOConfig?)

    /**
     * Retrieves a reference to the output source that will be written to during
     * [.savePlane] calls.
     *
     * @return The [DataHandle]} associated with this `Writer`.
     */
//    val handle: DataHandle<Location?>?

    /** Gets the color model.  */
    /**
     * Sets the color model.
     *
     *
     * NB: the color model should be set before the output destination.
     *
     */
//    var colorModel: java.awt.image.ColorModel?

    /** Gets the available compression types.  */
    val compressionTypes: Array<String>

    /** Gets the supported pixel types for the given codec.  */
    fun getPixelTypes(codec: String): IntArray

    /** Checks if the given pixel type is supported.  */
    fun isSupportedType(type: Int, codec: String): Boolean

    /** Checks if the given compression type is supported.  */
    @Throws(FormatException::class)
    fun isSupportedCompression(compress: String)

    /**
     * @return True iff this writer is prepared to write the given plane of the
     * given image index.
     */
    fun isInitialized(imageIndex: Int, planeIndex: Long): Boolean

    /**
     * @return The current compression being used by the writer, or null if not
     * set.
     */
    val compression: String?

    /**
     * @return The current frames per second being used by the writer.
     */
    val framesPerSecond: Int

    /**
     * @return [CodecOptions] used by this writer, if applicable.
     */
//    val codecOptions: CodecOptions?

    /**
     * @return True if this writer should output planes sequentially.
     */
    val writeSequential: Boolean

    /**
     * @return Number of valid bits per pixel.
     */
    val validBits: Int
}