package io.scif

import io.scif.*
import io.scif.config.SCIFIOConfig
import io.scif.util.FormatTools
import io.scif.util.FormatTools.getBytesPerPixel
import io.scif.util.SCIFIOMetadataTools
import io.scif.util.SCIFIOMetadataTools.castMeta
import net.imglib2.FinalInterval
import net.imglib2.Interval
import net.imglib2.type.NativeType
import okio.FileHandle
import okio.IOException
import platform.FormatException
import uns.i
import kotlin.math.min
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
abstract class AbstractReader<M : TypedMetadata, T : NativeType<T>, B : TypedBlock<T>>
    (override val blockClass: KClass<B>) : AbstractGroupable(), TypedReader<M, T, B> {
    // -- Fields --
    //    @Parameter
    //    private val handles: DataHandleService? = null

    /** Metadata for the current image source.  */
    override var metadata: Metadata? = null
//        @Throws(IOException::class)
        set(value) {
            m = castMeta<M>(value)
        }
    override var m: M?
        get() = metadata as? M
//        @Throws(IOException::class)
        set(value) {
            if (metadata != null && metadata != value)
                close()

            if (metadata == null) metadata = value
        }


    /** Whether or not to normalize float data.  */
    override var isNormalized: Boolean = false

    /** List of domains in which this format is used.  */
    final override val domains: Array<String> by lazy { createDomainArray() }

    // -- Constructors --

    // -- AbstractReader API Methods --
    /**
     * Helper method to lazily create the domain array for this reader instance,
     * to avoid constantly re-creating the array.
     */
    protected abstract fun createDomainArray(): Array<String>

    // -- Reader API Methods --
    // TODO Merge common Reader and Writer API methods
    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long): B =
        openBlock(imageIndex, blockIndex, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block): B =
        openBlock(imageIndex, blockIndex, this.castToTypedBlock<B>(block))

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, config: SCIFIOConfig): B {
        //FIXME how should we get Intervals from the Metadata?
        TODO()
        //        val bounds: Interval = FinalInterval(*metadata!![imageIndex].axesLengthsPlanar!!)
        ////        val blockMax: LongArray = metadata!![imageIndex].getAxesLengthsPlanar()
        ////        val blockMin = LongArray(blockMax.size)
        //        return openBlock(imageIndex, blockIndex, bounds, config)
    }

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, config: SCIFIOConfig): B =
        openBlock(imageIndex, blockIndex, castToTypedBlock<B>(block), config)

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, range: Interval): B = openRegion(imageIndex, range, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, range: Interval, block: Block): B =
        openRegion(imageIndex, range, castToTypedBlock(block))

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, range: Interval, config: SCIFIOConfig): B {
        val block = try {
            createBlock(range)
        } catch (e: IllegalArgumentException) {
            throw FormatException("Image block too large. Only 2GB of data can " +
                                          "be extracted at one time. You can workaround the problem by opening " +
                                          "the block in tiles; for further details, see: " +
                                          "https://github.com/scifio/scifio-tutorials/blob/master/core" +
                                          "/src/main/java/io/scif/tutorials/core/T1cReadingTilesGood.java", e)
        }
        return openRegion(imageIndex, range, block, config)
    }

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, range: Interval, block: Block, config: SCIFIOConfig): B =
        openRegion(imageIndex, range, castToTypedBlock(block), config)

    //    val currentLocation: Location?
    //        get() = metadata?.getSourceLocation()
    //
    //    val handle: DataHandle<Location>?
    //        get() = metadata?.getSource()


    override fun getOptimalBlockSize(imageIndex: Int): Interval {
        TODO()
        // actually this maybe shouldn't return an interval, because it's a block SIZE
        // which doesn't specify minimums...

        // x optimal
        //        return metadata!![imageIndex].getAxisLength(Axes.X)
        //
        //
        //        // y optimal
        //        val bpp = getBytesPerPixel(metadata!![imageIndex].pixelType)
        //
        //        val width: Long = metadata!![imageIndex].getAxisLength(Axes.X)
        //        val rgbcCount: Long = metadata!![imageIndex].getAxisLength(Axes.CHANNEL)
        //
        //        val maxHeight = (1024 * 1024) / (width * rgbcCount * bpp)
        //        return min(maxHeight.toDouble(), metadata!![imageIndex].getAxisLength(Axes.Y).toDouble())
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
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, range: Interval, block: Block): B =
        readBlock(s, imageIndex, range, castToTypedBlock(block))

    @Throws(IOException::class)
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, range: Interval, scanlinePad: Int, block: Block): B =
        readBlock(s, imageIndex, range, scanlinePad, castToTypedBlock(block))

    override val imageCount: Int
        get() = metadata!!.imageCount

    override fun <B : Block> castToTypedBlock(block: Block): B {
        TODO()
        //        if (!blockClass.isAssignableFrom(block.getClass())) {
        //            throw java.lang.IllegalArgumentException(("Incompatible plane types. " +
        //                    "Attempted to cast: " + plane.getClass()).toString() + " to: " + planeClass)
        //        }
        //
        //        return block as B
    }

    // -- TypedReader API --
    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: B): B =
        openBlock(imageIndex, blockIndex, block, SCIFIOConfig())

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, range: Interval, block: B): B =
        openRegion(imageIndex, range, block, SCIFIOConfig())

    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, range: Interval, block: B): B =
        readBlock(s, imageIndex, range, 0, block)

    @Throws(IOException::class)
    fun readBlock(/*s: DataHandle<Location?>*/s: FileHandle, imageIndex: Int, range: Interval, scanlinePad: Int, block: B): B {
        val metadata = metadata!!
        val bpp = FormatTools.getBytesPerPixel(metadata[imageIndex].pixelType)
        TODO()
//        val bytes: ByteArray = block.getBytes()
//        val xIndex: Int = metadata[imageIndex].getAxisIndex(Axes.X)
//        val yIndex: Int = metadata[imageIndex].getAxisIndex(Axes.Y)
//        if (SCIFIOMetadataTools.wholeBlock(imageIndex, metadata, range) && scanlinePad == 0)
//            s.read(bytes)
//        else if (SCIFIOMetadataTools.wholeRow(imageIndex, metadata, range) && scanlinePad == 0) {
//            if (SCIFIOMetadataTools.countInterleavedAxes(metadata, imageIndex) > 0) {
//                var bytesToSkip = bpp
//                bytesToSkip *= range.min(xIndex).i
//                var bytesToRead = bytesToSkip
//                for (i in range.dimensions)
//                    if (i != xIndex) {
//                        bytesToSkip *= when (i) {
//                            yIndex -> range.min(i)
//                            else -> range.max(i)
//                        }.i
//                        bytesToRead *= range.max(i).i
//                    }
//                s.skip(bytesToSkip)
//                s.read(bytes, 0, bytesToRead)
//            } else {
//                val rowLen = (bpp * range.max(xIndex)).i
//                val h = range.max(yIndex).i
//                val y = range.min(yIndex).i
//                var c: Long = metadata[imageIndex].getAxisLength(Axes.CHANNEL)
//                if (c <= 0 || !metadata[imageIndex].isMultichannel()) c = 1
//                for (channel in 0..<c) {
//                    s.skipBytes(y * rowLen)
//                    s.read(bytes, channel * h * rowLen, h * rowLen)
//                    if (channel < c - 1)
//                    // no need to skip bytes after reading final channel
//                        s.skipBytes((metadata[imageIndex].getAxisLength(Axes.Y) - y - h).i * rowLen)
//                }
//            }
//        } else {
//            val scanlineWidth: Int = metadata[imageIndex].getAxisLength(Axes.X).i + scanlinePad
//            if (SCIFIOMetadataTools.countInterleavedAxes(metadata, imageIndex) > 0) {
//                var blockProduct = bpp.toLong()
//                for (i in range.dimensions)
//                    if (i != xIndex && i != yIndex) blockProduct *= metadata[imageIndex].getAxisLength(i)
//                var bytesToSkip = scanlineWidth * blockProduct.i
//                s.skipBytes(range.min(yIndex).i * bytesToSkip)
//
//                bytesToSkip = bpp
//                var bytesToRead = bytesToSkip
//                bytesToRead *= range.max(xIndex).i
//                bytesToRead *= blockProduct.i
//                bytesToSkip *= range.min(xIndex).i
//                bytesToSkip *= blockProduct.i
//
//                for (row in 0..range.max(yIndex)) {
//                    s.skipBytes(bytesToSkip)
//                    s.read(bytes, row * bytesToRead, bytesToRead)
//                    if (row < range.max(yIndex))
//                    // no need to skip bytes after reading final row
//
//                    // no need to skip bytes after reading final row
//                        s.skipBytes((blockProduct * (scanlineWidth - range.max(xIndex) - range.min(xIndex))).i)
//                }
//            } else {
//                val c: Long = metadata[imageIndex].getAxisLength(Axes.CHANNEL)
//
//                val w = range.max(xIndex).i
//                val h = range.max(yIndex).i
//                val x = range.min(xIndex).i
//                val y = range.min(yIndex).i
//                for (channel in 0..<c) {
//                    s.skipBytes(y * scanlineWidth * bpp)
//                    for (row in 0 until h) {
//                        s.skipBytes(x * bpp)
//                        s.read(bytes, channel * w * h * bpp + row * w * bpp, w * bpp)
//                        if (row < h - 1 || channel < c - 1) {
//                            // no need to skip bytes after reading final row of
//                            // final channel
//                            s.skipBytes(bpp * (scanlineWidth - w - x))
//                        }
//                    }
//                    if (channel < c - 1)
//                    // no need to skip bytes after reading final channel
//                        s.skipBytes(scanlineWidth * bpp * (metadata[imageIndex].getAxisLength(Axes.Y) - y - h).i)
//                }
//            }
//        }
//        return block
    }

    // -- HasSource Format API --
    @Throws(IOException::class)
    override fun close(fileOnly: Boolean) {
        metadata?.close(fileOnly)

        if (!fileOnly)
            metadata = null
    }
}
