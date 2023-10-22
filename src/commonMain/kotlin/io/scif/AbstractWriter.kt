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

import FormatException

//import io.scif.codec.CodecOptions
//import io.scif.config.SCIFIOConfig
//import io.scif.util.FormatTools
//import io.scif.util.SCIFIOMetadataTools
//import net.imagej.axis.Axes
//import net.imglib2.FinalInterval
//import net.imglib2.Interval
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.handle.DataHandleService
//import org.scijava.io.location.Location
//import org.scijava.plugin.Parameter
//import org.scijava.util.ArrayUtils

/**
 * Abstract superclass of all SCIFIO [io.scif.Writer] implementations.
 *
 * @see io.scif.Writer
 *
 * @see io.scif.HasFormat
 *
 * @see io.scif.Metadata
 *
 * @author Mark Hiner
 * @param <M> - The Metadata type required by this Writer.
</M> */
abstract class AbstractWriter<M : TypedMetadata> : AbstractHasSource(), TypedWriter<M> {
    // -- Fields --
    /** Metadata, of the output type, describing the input source.  */
    override var metadata: M? = null

    /** Frame rate to use when writing in frames per second, if applicable.  */
    override var framesPerSecond: Int = 0
        private set

    /** Available compression types.  */
    override var compressionTypes: Array<String>?

    /** Current compression type.  */
    override var compression: String? = null
        private set

    /** The options if required.  */
    private var options: CodecOptions? = null

    /**
     * Whether each plane in each image of the current file has been prepped for
     * writing.
     */
    private var initialized: Array<BooleanArray?>?

    /** The number of valid bits per pixel.  */
    override val validBits: Int = 0

    /** Whether or not we are writing planes sequentially.  */
    private var sequential = false

    /** Where the image should be written.  */
    private var out: DataHandle<Location>? = null

    /** ColorModel for this Writer.  */
    private var model: java.awt.image.ColorModel? = null

    @Parameter
    private val handles: DataHandleService? = null

    // -- AbstractWriter API Methods --
    /**
     * Ensure that the arguments that are being passed to saveBytes(...) are
     * valid.
     *
     * @throws FormatException if any of the arguments is invalid.
     */
    @Throws(FormatException::class) protected fun checkParams(imageIndex: Int, planeIndex: Long,
                                                              buf: ByteArray?, bounds: Interval?) {
        SCIFIOMetadataTools.verifyMinimumPopulated(metadata, out, imageIndex)

        if (buf == null) throw FormatException("Buffer cannot be null.")
        var planes: Long = metadata.get(imageIndex).getPlaneCount()

        if (metadata.get(imageIndex).isMultichannel()) planes *= metadata.get(
            imageIndex).getAxisLength(Axes.CHANNEL)

        if (planeIndex < 0) throw FormatException(String.format(
            "Plane index:%d must be >= 0", planeIndex))
        if (planeIndex >= planes) {
            throw FormatException(String.format("Plane index:%d must be < %d",
                                                planeIndex, planes))
        }

        FormatTools.checkPlaneForWriting(metadata, imageIndex, planeIndex,
                                         buf.size, bounds)
        FormatTools.assertId(out, true, 0)
    }

    /**
     * Ensures this writer is prepared to write the given plane of the given
     * image.
     */
    @Throws(FormatException::class, java.io.IOException::class) protected fun initialize(imageIndex: Int, planeIndex: Long,
                                                                                         bounds: Interval?) {
        initialized!![imageIndex]!![planeIndex.toInt()] = true
    }

    /**
     * @return An array of compression types supported by this format. An empty
     * array indicates no compression.
     */
    protected abstract fun makeCompressionTypes(): Array<String>?

    /**
     * Helper method invoked by [.savePlane] to perform actual format-
     * specific output.
     */
    @Throws(FormatException::class, java.io.IOException::class) protected abstract fun writePlane(imageIndex: Int,
                                                                                                  planeIndex: Long, plane: Plane?, bounds: Interval?)

    // -- Writer API Methods --
    @Throws(FormatException::class, java.io.IOException::class) fun savePlane(imageIndex: Int, planeIndex: Long,
                                                                              plane: Plane?) {
        val bounds: Interval =  //
            FinalInterval(metadata.get(imageIndex).getAxesLengthsPlanar())
        savePlane(imageIndex, planeIndex, plane, bounds)
    }

    @Throws(FormatException::class, java.io.IOException::class) fun savePlane(imageIndex: Int, planeIndex: Long,
                                                                              plane: Plane?, bounds: Interval?) {
        initialize(imageIndex, planeIndex, bounds)
        writePlane(imageIndex, planeIndex, plane, bounds)
    }

    override fun canDoStacks(): Boolean {
        return false
    }

    @Throws(FormatException::class) override fun setMetadata(meta: Metadata) {
        setMetadata(SCIFIOMetadataTools.< M > castMeta < M ? > meta)
    }

    override fun getMetadata(): M? {
        return metadata
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(fileName: Location?) {
        setDest(fileName, 0)
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(out: DataHandle<Location?>?) {
        setDest(out, 0)
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(loc: Location?, imageIndex: Int) {
        setDest(loc, imageIndex, SCIFIOConfig())
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(out: DataHandle<Location?>?, imageIndex: Int) {
        setDest(out, imageIndex, SCIFIOConfig())
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(loc: Location?, config: SCIFIOConfig?) {
        setDest(loc, 0, config)
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(out: DataHandle<Location?>?, config: SCIFIOConfig?) {
        setDest(out, 0, config)
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(loc: Location?, imageIndex: Int,
                                                                            config: SCIFIOConfig) {
        val handle: DataHandle<Location> = handles.create(loc)

        // set common metadeta
        setDestinationMeta(imageIndex, config)

        if (handle != null) {
            this.out = handle
            setDest(out, imageIndex, config)
        } else { // handling Location only formats
            metadata.setDestinationLocation(loc)
            SCIFIOMetadataTools.verifyMinimumPopulated(metadata, loc)
        }
    }

    @Throws(FormatException::class, java.io.IOException::class) fun setDest(out: DataHandle<Location?>, imageIndex: Int,
                                                                            config: SCIFIOConfig) {
        setDestinationMeta(imageIndex, config)
        metadata!!.datasetName = out.get().getName()
        this.out = out
        SCIFIOMetadataTools.verifyMinimumPopulated(metadata, out)
    }

    @Throws(FormatException::class) private fun setDestinationMeta(imageIndex: Int,
                                                                   config: SCIFIOConfig) {
        if (metadata == null) throw FormatException(
            "Can not set Destination without setting Metadata first.")
        framesPerSecond = config.writerGetFramesPerSecond()
        options = config.writerGetCodecOptions()
        model = config.writerGetColorModel()
        compression = config.writerGetCompression()
        sequential = config.writerIsSequential()
        initialized = arrayOfNulls(metadata!!.imageCount)
        for (i in 0 until metadata!!.imageCount) {
            initialized!![i] = BooleanArray(metadata.get(imageIndex)
                                                    .getPlaneCount() as Int)
        }
    }

    val handle: DataHandle<Location>?
        get() = out

    var colorModel: java.awt.image.ColorModel?
        get() = model
        set(cm) {
            model = cm
        }

    override fun getCompressionTypes(): Array<String>? {
        if (compressionTypes == null) {
            compressionTypes = makeCompressionTypes()
        }
        return compressionTypes
    }

    val codecOptions: CodecOptions?
        get() = options

    override fun writeSequential(): Boolean {
        return sequential
    }

    override fun getPixelTypes(codec: String?): IntArray? {
        return intArrayOf(FormatTools.INT8, FormatTools.UINT8, FormatTools.INT16,
                          FormatTools.UINT16, FormatTools.INT32, FormatTools.UINT32,
                          FormatTools.FLOAT)
    }

    override fun isSupportedType(type: Int, codec: String?): Boolean {
        val types = getPixelTypes(codec)
        for (otherType in types!!) {
            if (type == otherType) return true
        }
        return false
    }

    @Throws(FormatException::class) override fun isSupportedCompression(compression: String?) {
        for (compressionType in compressionTypes!!) {
            if (compressionType == compression) {
                this.compression = compression
                return
            }
        }
        throw FormatException("Invalid compression type: $compression")
    }

    // -- TypedWriter API Methods --
    @Throws(FormatException::class) override fun setMetadata(meta: M) {
        if (metadata != null && metadata !== meta) {
            try {
                metadata.close()
            } catch (e: java.io.IOException) {
                throw FormatException(e)
            }
        }

        if (out != null) {
            try {
                close()
            } catch (e: java.io.IOException) {
                throw FormatException(e)
            }
        }

        metadata = meta

        // Check the first pixel type for compatibility with this writer
        for (i in 0 until metadata!!.imageCount) {
            val pixelType: Int = metadata.get(i).getPixelType()

            if (!ArrayUtils.contains(getPixelTypes(compression), pixelType)) {
                throw FormatException(("Unsupported image type '" + FormatTools
                        .getPixelTypeString(pixelType)).toString() + "'.")
            }
        }
    }

    override fun isInitialized(imageIndex: Int, planeIndex: Long): Boolean {
        return initialized!![imageIndex]!![planeIndex.toInt()]
    }

    // -- HasSource API Methods --
    @Throws(java.io.IOException::class) override fun close(fileOnly: Boolean) {
        if (out != null) out.close()
        if (metadata != null) metadata.close(fileOnly)
        out = null
        initialized = null
    }
}