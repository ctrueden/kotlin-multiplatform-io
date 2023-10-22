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

//import org.scijava.io.handle.DataHandle
//import org.scijava.io.location.Location

/**
 * Interface for all SCIFIO Parsers.
 *
 *
 * `Parsers` are used to create type-specific [io.scif.Metadata]
 * appropriate for their `Format` by reading from an image source.
 *
 *
 * @see io.scif.Format
 *
 * @author Mark Hiner
 */
interface Parser : HasFormat, HasSource, Groupable {
    // -- Parser API methods --
    /**
     * Creates a `Metadata` object using the provided location of an image
     * source.
     *
     * @param location the [Location] of the image source to parse.
     * @return A new `Metadata` object of the appropriate type.
     */
//    @Throws(IOException::class, FormatException::class) fun parse(location: Location?): Metadata?

    /**
     * Creates a `Metadata` object from the provided image source.
     *
     * @param stream a random access handle to the image source to parse.
     * @return A new `Metadata` object of the appropriate type.
     */
//    @Throws(IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?): Metadata?

    /**
     * Parses metadata using the provided name of an image source, and writes to
     * an existing `Metadata` object (overwriting may occur).
     *
     * @param location the [Location] of the image source to parse.
     * @param meta A base `Metadata` to fill.
     * @return The provided `Metadata` after parsing.
     * @throws IllegalArgumentException if meta is not assignable from the
     * `Metadata` associated with this `Parser's Format`
     */
//    @Throws(IOException::class, FormatException::class) fun parse(location: Location?, meta: Metadata?): Metadata?

    /**
     * Parses metadata from the provided image source to an existing
     * `Metadata` object (overwriting may occur).
     *
     * @param stream A [DataHandle] to the image source to parse.
     * @param meta A base `Metadata` to fill.
     * @return The provided `Metadata` after parsing.
     * @throws IllegalArgumentException if meta is not assignable from the
     * `Metadata` associated with this `Parser's Format`
     */
//    @Throws(IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?, meta: Metadata?): Metadata?

    /**
     * As [.parse] with configuration options.
     *
     * @param fileName Name of the image source to parse.
     * @param config Configuration information to use for this parse.
     * @return A new `Metadata` object of the appropriate type.
     */
//    @Throws(IOException::class, FormatException::class) fun parse(fileName: Location?, config: SCIFIOConfig?): Metadata?

    /**
     * As [.parse] with configuration options.
     *
     * @param handle a [DataHandle] to the image source to parse.
     * @param config Configuration information to use for this parse.
     * @return A new `Metadata` object of the appropriate type.
     */
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(handle: DataHandle<Location?>?, config: SCIFIOConfig?): Metadata?

    /**
     * As [.parse] with configuration options.
     *
     * @param fileName the [Location] of the image source to parse.
     * @param meta A base `Metadata` to fill.
     * @param config Configuration information to use for this parse.
     * @return The provided `Metadata` after parsing.
     * @throws IllegalArgumentException if meta is not assignable from the
     * `Metadata` associated with this `Parser's Format`
     */
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(fileName: Location?, meta: Metadata?, config: SCIFIOConfig?): Metadata?

    /**
     * / ** As [.parse] with configuration options.
     *
     * @param stream a random access handle to the image source to parse.
     * @param meta A base `Metadata` to fill.
     * @param config Configuration information to use for this parse.
     * @return The provided `Metadata` after parsing.
     * @throws IllegalArgumentException if meta is not assignable from the
     * `Metadata` associated with this `Parser's Format`
     */
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?, meta: Metadata?,
//                                                                          config: SCIFIOConfig?): Metadata?

    /**
     * @return The last metadata instance parsed by this parser.
     */
    val metadata: Metadata?

    /**
     * @return The last [DataHandle] read by this parser.
     */
//    val source: DataHandle<Location?>?

    /**
     * @return The last Location read from by this parser.
     */
//    val sourceLocation: Location?

    /**
     * Updates the source being operated on by this parser (e.g. in multi-file
     * formats).
     */
//    @Throws(java.io.IOException::class) fun updateSource(source: Location?)

    /** Returns an array of filenames needed to open this dataset.  */
    val usedFiles: Array<Any?>?

    /**
     * Returns an array of filenames needed to open this dataset. If the
     * 'noPixels' flag is set, then only files that do not contain pixel data will
     * be returned.
     */
//    fun getUsedLocations(noPixels: Boolean): Array<Location?>?

    /** Returns an array of filenames needed to open the indicated image index.  */
//    fun getImageUsedFiles(imageIndex: Int): Array<Location?>?

    /**
     * Returns an array of filenames needed to open the indicated image. If the
     * 'noPixels' flag is set, then only files that do not contain pixel data will
     * be returned.
     */
//    fun getImageUsedFiles(imageIndex: Int, noPixels: Boolean): Array<Location?>?

    /**
     * Returns an array of LocationInfo objects representing the Locations needed to open
     * this dataset. If the 'noPixels' flag is set, then only files that do not
     * contain pixel data will be returned.
     */
//    fun getAdvancedUsedLocations(noPixels: Boolean): Array<LocationInfo?>?

    /**
     * Returns an array of LocationInfo objects representing the Locations needed to open
     * the current series. If the 'noPixels' flag is set, then only files that do
     * not contain pixel data will be returned.
     */
//    fun getAdvancedImageUsedLocations(imageIndex: Int, noPixels: Boolean): Array<LocationInfo?>?

    /**
     * Returns a list of MetadataLevel options for determining the granularity of
     * Metadata collection.
     */
    val supportedMetadataLevels: Set<Any?>?
}