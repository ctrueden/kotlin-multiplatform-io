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
 * Interface for all SCIFIO Metadata objects. Based on the format, a Metadata
 * object can be a single N-dimensional collection of bytes (an image) or a list
 * of multiple images.
 *
 *
 * NB: When defining a new Metadata implementation, you will likely want to
 * create a corresponding [io.scif.Format] implementation. Also, at a
 * minimum, a [io.scif.Translator] should be implemented that can convert
 * between [io.scif.Metadata] and the new Metadata.
 *
 *
 * @see io.scif.Translator
 *
 * @see io.scif.AbstractFormat
 */
interface Metadata: /*java.io.Serializable,*/ HasFormat, HasSource {
    // -- Metadata API --

    /**
     * Returns the source used to generate this Metadata object.
     *
     * @return - The associated RandomAccessInputStream
     */
    /**
     * Sets the input source attached to this Metadata object. Note that calling
     * this method does not affect the structure of this Metadata object.
     *
     * @param in - Input source for this Metadata
     */
//    var source: DataHandle<Location?>?

    /**
     * @return the destination location, this is used by formats that do not use a
     * DataHandle.
     */
    /**
     * Sets the output location. This is used by formats that do not use a
     * DataHandle.
     *
     * @param loc the destination location
     */
//    var destinationLocation: Location?

    /**
     * @return the source location, in most cases this is equivalent to calling
     * [.getSource].get()
     */
    /**
     * Sets the input source location. This is an alternative to
     * [.setSource] for Formats that operate on Locations
     * without a corresponding [DataHandle].
     *
     * @param loc the source location
     */
//    var sourceLocation: Location?

    /**
     * Returns whether or not filterMetadata was set when parsing this Metadata
     * object.
     *
     * @return True if Metadata was filtered when parsing
     */
    /** Sets whether this metadata was filtered when parsed.  */
    var isFiltered: Boolean

    /**
     * Clears all format-agnostic metadata, causing it to be re-generated next
     * time it's requested.
     */
    fun clearImageMetadata()

    // -- Format-agnostic Metadata API Methods --
    /** Returns a String representation of this Dataset's name  */
    /**
     * Sets the name for this dataset.
     *
     * @param name - the dataset name
     */
    var datasetName: String

    /** Returns the ImageMetadata at the specified image within this dataset.  */
    operator fun get(imageIndex: Int): ImageMetadata

    /** Returns the list of ImageMetadata associated with this dataset.  */
    val all: List<Any>

    /** Returns the number of images in this dataset.  */
    val imageCount: Int

    /** Returns the size, in bytes, of the current dataset.  */
    val datasetSize: Long

    /**
     * Adds the provided image metadata to this dataset metadata
     */
    fun add(meta: ImageMetadata)
}