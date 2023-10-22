package io.scif

import io.scif.*
import io.scif.config.SCIFIOConfig
import io.scif.util.FormatTools
import io.scif.util.SCIFIOMetadataTools
import io.scif.util.SCIFIOMetadataTools.castMeta
import net.imglib2.FinalInterval
import net.imglib2.Interval
import net.imglib2.type.NativeType
import okio.FileHandle
import okio.IOException
import kotlin.reflect.KClass

/**
 * Abstract superclass of all SCIFIO [io.scif.Reader] implementations.
 *
 * @see io.scif.Reader
 * @see io.scif.HasFormat
 * @see io.scif.Metadata
 * @see io.scif.Block
 * @author Mark Hiner
 */
abstract class AbstractReader<M : TypedMetadata, T : NativeType<T>, B : TypedBlock<T, *>>
    (override val blockClass: KClass<B>) : AbstractGroupable(), TypedReader<M, T, B> {
    // -- Fields --
    //    @Parameter
    //    private val handles: DataHandleService? = null

    /** Metadata for the current image source.  */
    override var metadata: Metadata? = null
        @Throws(IOException::class)
        set(value) {
            m = castMeta<M>(value)
        }
    override var m: M?
        get() = metadata as? M
        set(value) {
            if (metadata != null && metadata != value)
                close()

            if (metadata == null) metadata = value
        }


    /** Whether or not to normalize float data.  */
    override var isNormalized: Boolean = false

    /** List of domains in which this format is used.  */
    override var domains: Array<String>?
        get() {
            if (field == null) {
                field = createDomainArray()
            }
            return field
        }
        private set

    // -- Constructors --

    // -- AbstractReader API Methods --
    /**
     * Helper method to lazily create the domain array for this reader instance,
     * to avoid constantly re-creating the array.
     */
    protected abstract fun createDomainArray(): Array<String>

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long): B =
        openBlock(imageIndex, blockIndex, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, bounds: Interval): B =
        openBlock(imageIndex, blockIndex, bounds, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block): B =
        openBlock(imageIndex, blockIndex, this.castToTypedBlock<B>(block))

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, bounds: Interval): B =
        openBlock(imageIndex, blockIndex, this.castToTypedBlock(block), bounds)

    // -- Reader API Methods --
    // TODO Merge common Reader and Writer API methods
    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, config: SCIFIOConfig): B {
        val bounds: Interval = FinalInterval(*metadata!![imageIndex].axesLengthsPlanar!!)
        return openBlock(imageIndex, blockIndex, bounds, config)
    }

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, bounds: Interval, config: SCIFIOConfig): B {
        val block = try {
            createBlock(bounds)
        } catch (e: IllegalArgumentException) {
            throw FormatException("Image block too large. Only 2GB of data can " +
                                          "be extracted at one time. You can workaround the problem by opening " +
                                          "the block in tiles; for further details, see: " +
                                          "https://github.com/scifio/scifio-tutorials/blob/master/core" +
                                          "/src/main/java/io/scif/tutorials/core/T1cReadingTilesGood.java", e)
        }
        return openBlock(imageIndex, blockIndex, block, bounds, config)
    }

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, config: SCIFIOConfig): B =
        openBlock(imageIndex, blockIndex, castToTypedBlock<B>(block), config)

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, bounds: Interval, config: SCIFIOConfig): B =
        openBlock(imageIndex, blockIndex, castToTypedBlock<B>(block), bounds, config)

    val currentLocation: Location?
        get() = metadata?.getSourceLocation()

    val handle: DataHandle<Location>?
        get() = metadata?.getSource()

    override fun getOptimalTileWidth(imageIndex: Int): Long = metadata!![imageIndex].getAxisLength(Axes.X)

    override fun getOptimalTileHeight(imageIndex: Int): Long {
        val bpp: Int = FormatTools.getBytesPerPixel(metadata.get(imageIndex)
                                                            .getPixelType())

        val width: Long = metadata.get(imageIndex).getAxisLength(Axes.X)
        val rgbcCount: Long = metadata.get(imageIndex).getAxisLength(Axes.CHANNEL)

        val maxHeight = (1024 * 1024) / (width * rgbcCount * bpp)
        return java.lang.Math.min(maxHeight, metadata.get(imageIndex).getAxisLength(Axes.Y))
    }

    override val hasCompanionFiles: Boolean
        get() = false

    //    @Throws(java.io.IOException::class) fun setSource(loc: Location?) {
    //        setSource(loc, SCIFIOConfig())
    //    }
    //
    //    @Throws(java.io.IOException::class) fun setSource(stream: DataHandle<Location?>?) {
    //        setSource(stream, SCIFIOConfig())
    //    }
    //
    //    @Throws(java.io.IOException::class) fun setSource(loc: Location?, config: SCIFIOConfig) {
    //        // check if the same location is set again
    //
    //        if (currentLocation != null && currentLocation.equals(loc)) {
    //            if (handle != null) {
    //                // only need to rewind the handle
    //                handle.seek(0)
    //            }
    //            return
    //        }
    //
    //        // new location
    //        close()
    //
    //        var stream: DataHandle<Location?>? = null
    //        // setting a new source
    //        try {
    //            stream = if (config.bufferedReadingEnabled()) handles.readBuffer(loc)
    //            else handles.create(loc)
    //            if (stream == null) {
    //                // loc only
    //                setMetadata(format!!.createParser().parse(loc, config))
    //            } else {
    //                setMetadata(format!!.createParser().parse(stream, config))
    //                setSource(stream)
    //            }
    //        } catch (e: FormatException) {
    //            if (stream != null) {
    //                stream.close()
    //            }
    //            throw java.io.IOException(e)
    //        }
    //    }
    //
    //    @Throws(java.io.IOException::class) fun setSource(handle: DataHandle<Location?>,
    //                                                      config: SCIFIOConfig?) {
    //        val currentSource: Location? = currentLocation
    //        val newSource: Location = handle.get()
    //        if (metadata != null && (currentSource == null || newSource == null ||
    //                    !currentSource.equals(newSource))) close()
    //
    //        if (metadata == null) {
    //            try {
    //                val meta = format!!.createParser().parse(handle, config) as M
    //                setMetadata(meta)
    //            } catch (e: FormatException) {
    //                throw java.io.IOException(e)
    //            }
    //        }
    //    }

    @Throws(IOException::class)
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, bounds: Interval, block: Block): B =
        readBlock(s, imageIndex, bounds, castToTypedBlock<B>(block))

    @Throws(IOException::class)
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, bounds: Interval, scanlinePad: Int, block: Block): B =
        readBlock(s, imageIndex, bounds, scanlinePad, castToTypedBlock<B>(block))

    override fun getBlockCount(imageIndex: Int): Long = metadata!![imageIndex].planeCount

    override val imageCount: Int
        get() = metadata!!.imageCount

    override fun <B : Block> castToTypedBlock(block: Block): B {
        if (!blockClass.isAssignableFrom(block.getClass())) {
            throw java.lang.IllegalArgumentException(("Incompatible plane types. " +
                    "Attempted to cast: " + plane.getClass()).toString() + " to: " + planeClass)
        }

        return block as B
    }

    // -- TypedReader API --
    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: B): B =
        openBlock(imageIndex, blockIndex, block, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: B, config: SCIFIOConfig): B =
        openBlock(imageIndex, blockIndex, block, block.interval, config)

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: B, bounds: Interval): B =
        openBlock(imageIndex, blockIndex, block, block.interval, SCIFIOConfig())

    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, bounds: Interval, block: B): B =
        readBlock(s, imageIndex, bounds, 0, block)

    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>*/s: FileHandle, imageIndex: Int, bounds: Interval, scanlinePad: Int, block: B): B {
        val bpp = FormatTools.getBytesPerPixel(metadata!![imageIndex].pixelType)

        val bytes: ByteArray = block.getBytes()
        val xIndex = metadata!![imageIndex].getAxisIndex(Axes.X)
        val yIndex = metadata!![imageIndex].getAxisIndex(Axes.Y)
        if (SCIFIOMetadataTools.wholeBlock(imageIndex, metadata, bounds) && scanlinePad == 0)
            s.read(bytes)
        else if (SCIFIOMetadataTools.wholeRow(imageIndex, metadata, bounds) && scanlinePad == 0) {
            if (metadata.get(imageIndex).getInterleavedAxisCount() > 0) {
                var bytesToSkip = bpp
                bytesToSkip *= bounds.max(xIndex)
                var bytesToRead = bytesToSkip
                for (i in bounds.dimensions) {
                    if (i != xIndex) {
                        if (i == yIndex) {
                            bytesToSkip *= bounds.min(i)
                        } else {
                            bytesToSkip *= bounds.max(i)
                        }
                        bytesToRead *= bounds.max(i)
                    }
                }
                s.skip(bytesToSkip)
                s.read(bytes, 0, bytesToRead)
            } else {
                val rowLen = (bpp * bounds.max(xIndex)) as Int
                val h = bounds.max(yIndex) as Int
                val y = bounds.min(yIndex) as Int
                var c: Long = metadata.get(imageIndex).getAxisLength(Axes.CHANNEL)
                if (c <= 0 || !metadata.get(imageIndex).isMultichannel()) c = 1
                for (channel in 0 until c) {
                    s.skipBytes(y * rowLen)
                    s.read(bytes, channel * h * rowLen, h * rowLen)
                    if (channel < c - 1) {
                        // no need to skip bytes after reading final channel
                        s.skipBytes((metadata.get(imageIndex).getAxisLength(Axes.Y) -
                                y - h) as Int * rowLen)
                    }
                }
            }
        } else {
            val scanlineWidth = metadata.get(imageIndex).getAxisLength(
                Axes.X) as Int + scanlinePad
            if (metadata.get(imageIndex).getInterleavedAxisCount() > 0) {
                var blockProduct = bpp.toLong()
                for (i in bounds.dimensions) {
                    if (i != xIndex && i != yIndex) blockProduct *= metadata.get(
                        imageIndex).getAxisLength(i)
                }
                var bytesToSkip = scanlineWidth * blockProduct.toInt()
                s.skipBytes(bounds.min(yIndex) as Int * bytesToSkip)

                bytesToSkip = bpp
                var bytesToRead = bytesToSkip
                bytesToRead *= bounds.max(xIndex)
                bytesToRead *= blockProduct.toInt()
                bytesToSkip *= bounds.min(xIndex)
                bytesToSkip *= blockProduct.toInt()

                for (row in 0..bounds.max(yIndex)) {
                    s.skipBytes(bytesToSkip)
                    s.read(bytes, row * bytesToRead, bytesToRead)
                    if (row < bounds.max(yIndex)) {
                        // no need to skip bytes after reading final row
                        s.skipBytes((blockProduct * (scanlineWidth - bounds.dimension(
                            xIndex))) as Int)
                    }
                }
            } else {
                val c: Long = metadata.get(imageIndex).getAxisLength(Axes.CHANNEL)

                val w = bounds.max(xIndex) as Int
                val h = bounds.max(yIndex) as Int
                val x = bounds.min(xIndex) as Int
                val y = bounds.min(yIndex) as Int
                for (channel in 0 until c) {
                    s.skipBytes(y * scanlineWidth * bpp)
                    for (row in 0 until h) {
                        s.skipBytes(x * bpp)
                        s.read(bytes, channel * w * h * bpp + row * w * bpp, w * bpp)
                        if (row < h - 1 || channel < c - 1) {
                            // no need to skip bytes after reading final row of
                            // final channel
                            s.skipBytes(bpp * (scanlineWidth - w - x))
                        }
                    }
                    if (channel < c - 1) {
                        // no need to skip bytes after reading final channel
                        s.skipBytes(scanlineWidth * bpp * (metadata.get(imageIndex)
                                .getAxisLength(Axes.Y) - y - h) as Int)
                    }
                }
            }
        }
        return block
    }

    // -- HasSource Format API --
    @Throws(IOException::class)
    override fun close(fileOnly: Boolean) {
        metadata?.close(fileOnly)

        if (!fileOnly)
            metadata = null
    }
}
