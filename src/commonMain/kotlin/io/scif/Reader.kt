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
import net.imglib2.Interval
import okio.FileHandle
import okio.IOException

/**
 * Interface for all SCIFIO Readers.
 *
 * `Reader` components generate [io.scif.Block] representations of
 * images via the [.openBlock] methods. These blocks can then be used by
 * calling software (e.g. for display) or passed to another method for writing
 * to an output source (e.g. via the [io.scif.Writer.saveBlock] methods).
 *
 * Before a `Reader` can be used, it must be initialized via
 * [.setSource] and [.setMetadata] calls.
 *
 * @see io.scif.Block
 * @see io.scif.Metadata
 * @see io.scif.Writer.saveBlock
 * @author Mark Hiner
 */
interface Reader : HasFormat, HasSource, Groupable {
    // -- Reader API methods --
    /**
     * Creates a [io.scif.Block] representation of the pixels at the
     * specified indices.
     *
     * @param imageIndex the image index within the dataset.
     * @param blockIndex the block index within the image.
     * @return The complete `Block` at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long): Block

    /**
     * Creates a [io.scif.Block] representation of a desired sub-region from
     * the pixels at the specified indices.
     *
     * @param imageIndex the image index within the dataset.
     * @param blockIndex the block index within the image.
     * @param bounds bounds of the planar axes.
     * @return The desired sub-region at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, bounds: Interval): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * complete blocks.
     *
     * @see .openBlock
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: Block): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * sub-regions of blocks.
     *
     * @see .openBlock
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, bounds: Interval): Block

    /**
     * As [.openBlock] with configuration options.
     *
     * @param imageIndex the image index within the dataset.
     * @param blockIndex the block index within the image.
     * @param config Configuration information to use for this read.
     * @return The complete `Block` at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, config: SCIFIOConfig): Block

    /**
     * As [.openBlock] with configuration options.
     *
     * @param imageIndex the image index within the dataset.
     * @param blockIndex the block index within the image.
     * @param bounds bounds of the planar axes.
     * @param config Configuration information to use for this read.
     * @return The desired sub-region at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, bounds: Interval, config: SCIFIOConfig): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * complete blocks.
     *
     * @see .openBlock
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, config: SCIFIOConfig): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * sub-regions of blocks.
     *
     * @see .openBlock
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, bounds: Interval, config: SCIFIOConfig): Block

    /**
     * Creates a [io.scif.Block] representation of a desired sub-region from
     * the pixels at the specified indices.
     *
     * @param imageIndex the image index within the dataset.
     * @param pos starting position (offsets) of the range to open
     * @param range the extents of the range to open
     * @return The desired sub-region at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * sub-regions of blocks.
     *
     * @see .openRegion
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: Block): Block

    /**
     * As [.openRegion] with configuration options.
     *
     * @see .openRegion
     * @return The desired sub-region at the specified indices.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval, config: SCIFIOConfig): Block

    /**
     * Allows a single `Block` object to be reused by reference when opening
     * sub-regions of blocks.
     *
     * @see .openRegion
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: Block, config: SCIFIOConfig): Block


    /** Returns the current file.  */
    //    val currentLocation: Location?

    /** Returns the list of domains represented by the current file.  */
    val domains: Array<String?>?

    /**
     * Retrieves the current input stream for this reader.
     *
     * @return A RandomAccessInputStream
     */
    //    val handle: DataHandle<Location?>?

    /** Returns the optimal sub-image extents for use with [.openBlock].  */
    fun getOptimalBlockSize(imageIndex: Int): Interval

    /** Gets the type-specific Metadata for this Reader  */
    @set:Throws(IOException::class)
    var metadata: Metadata?

    // TODO remove normalization methods

    /** Returns true if we should normalize float data.  */
    /** Specifies whether or not to normalize float data.  */
    var isNormalized: Boolean

    /** Returns true if this format supports multi-file datasets.  */
    override val hasCompanionFiles: Boolean

    /**
     * Sets the source for this reader to read from.
     *
     * @param loc the location
     * @throws IOException
     */
    //    @Throws(java.io.IOException::class) fun setSource(loc: Location?)

    /**
     * Sets the source for this reader to read from.
     *
     * @param stream - The stream to read from
     */
    //    @Throws(java.io.IOException::class) fun setSource(stream: DataHandle<Location?>?)

    /**
     * As [.setSource] with configuration options.
     *
     * @param loc the Location
     * @param config Configuration information to use for this read.
     * @throws IOException
     */
    //    @Throws(java.io.IOException::class) fun setSource(loc: Location?, config: SCIFIOConfig?)

    /**
     * As [.setSource] with configuration options.
     *
     * @param handle - The handle to read from
     * @param config Configuration information to use for this read.
     * @throws IOException
     */
    //    @Throws(java.io.IOException::class) fun setSource(handle: DataHandle<Location?>?, config: SCIFIOConfig?)

    /**
     * Reads a raw block from disk.
     *
     * NB Presumes that the source stream `s` is set to the correct offset,
     * i.e. start of the block
     *
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, pos: Interval, range: Interval, block: Block): Block

    /**
     * Reads a raw block from disk.
     *
     * NB Presumes that the source stream `s` is set to the correct offset,
     * i.e. start of the block     *
     *
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, pos: Interval, range: Interval, scanlinePad: Int, block: Block): Block

    /** Determines the number of blocks in the current file.  */
    fun getBlockCount(imageIndex: Int): Long

    /** Determines the number of images in the current file.  */
    val imageCount: Int

    /**
     * Creates a blank block compatible with this reader.
     *
     * @param extents The extents of the new block
     *
     * @return The created block
     */
    fun createBlock(extents: Interval): Block

    /**
     * Convenience method for casting `Block` implementations to the type
     * associated with this `Reader`.
     *
     * NB: this method will fail if the provided `Block` is not compatible
     * with this `Reader`.
     *
     * @param block - The base [io.scif.Block] to cast.
     * @return The `Block` argument cast to `B`.
     * @throws IllegalArgumentException If the provided `Block` type is not
     * compatible with this `Reader`.
     */
    fun <B : Block> castToTypedBlock(block: Block): B
}