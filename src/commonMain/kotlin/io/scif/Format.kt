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
import kotlin.reflect.KClass

//import org.scijava.io.location.Location

/**
 * Interface for all SCIFIO formats.
 *
 *
 * The `Format` is a bag for all other SCIFIO components associated with
 * image IO in a given image format. It acts as a bridge and a black box for
 * creating components, and facilitates component intercommunication.
 *
 *
 *
 * `Formats` are typically accessed through the
 * [io.scif.services.FormatService], which maintains a list of singleton
 * `Formats` and has many convenience methods relating to their use.
 *
 *
 * @author Mark Hiner
 * @see io.scif.SCIFIO
 *
 * @see io.scif.services.FormatService
 */
interface Format {

    // -- Format API methods --

    /**
     * @return True if this Format is active when checking image compatibility.
     */
    /**
     * Toggle whether this Format should be used when checking image
     * compatibility.
     *
     * @param enabled - if true, this Format will be used in image/IO. Default:
     * true
     */
    var isEnabled: Boolean

    /**
     * Gets the name of this file format.
     *
     * @return a String representation of this Format's name
     */
    val formatName: String

    /**
     * Gets the default file suffixes for this file format.
     *
     *
     * Sample valid suffixes:
     *
     *
     *  * png
     *  * avi
     *  * bmp
     *
     *
     * @return an array of extensions associated with this Format
     */
    val suffixes: Array<String>

    /**
     * Create an instance of the Metadata associated with this format.
     *
     * @return A new [io.scif.Metadata] instance compatible with this
     * Format's components
     * @throws FormatException
     */
    @Throws(FormatException::class)
    fun createMetadata(): Metadata

    /**
     * Create an instance of the Checker associated with this format.
     *
     * @return A new [io.scif.Checker] instance which can determine if
     * sources are compatible with this Format.
     * @throws FormatException
     */
    @Throws(FormatException::class)
    fun createChecker(): Checker

    /**
     * Create an instance of the Parser associated with this format.
     *
     * @return A new [io.scif.Parser] instance capable of populating
     * Metadata compatible with this Format.
     * @throws FormatException
     */
    @Throws(FormatException::class)
    fun createParser(): Parser

    /**
     * Creates an instance of the Reader associated with this format.
     *
     * @return A new [io.scif.Reader] instance capable of reading datasets
     * of this Format.
     * @throws FormatException
     */
    @Throws(FormatException::class)
    fun createReader(): Reader

    /**
     * Creates an instance of the Writer associated with this format.
     *
     * @return A new [io.scif.Writer] instance capable of writing datasets
     * of this Format.
     * @throws FormatException
     */
    @Throws(FormatException::class)
    fun createWriter(): Writer

    /**
     * @return The class of the Metadata associated with this format
     */
    val metadataClass: KClass<*>

    /**
     * @return The class of the Checker associated with this format
     */
    val checkerClass: KClass<*>

    /**
     * @return The class of the Parser associated with this format
     */
    val parserClass: KClass<*>

    /**
     * @return The class of the Reader associated with this format
     */
    val readerClass: KClass<*>

    /**
     * @return The class of the Writer associated with this format
     */
    val writerClass: KClass<*>

    /**
     * This is only needed by Format that depend on their own Location types
     * (e.g. OMEROFormat)
     * @param outputLocation
     * @return if this LocationType is owned by this format
     */
//    fun ownsLocationType(outputLocation: Location?): Boolean {
//        return false
//    }
}