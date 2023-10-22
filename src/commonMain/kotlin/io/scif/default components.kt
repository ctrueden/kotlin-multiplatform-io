package io.scif

import net.imagej.axis.CalibratedAxis
import net.imglib2.Dimensions
import net.imglib2.Interval
import okio.IOException

/**
 * Marker interface for default class implementations.
 *
 * @author Mark Hiner
 */
interface DefaultComponent

/**
 * Default [io.scif.Checker] implementation. Can perform basic extension
 * checking to determine Format compatibility.
 *
 * Equivalent to creating a checker with `suffixSufficient = true` and
 * `suffixNecessary = true`.
 *
 * If a dataset needs to be opened to determine compatibility, a custom Checker
 * must be implemented.
 *
 * @see io.scif.Checker
 * @see io.scif.Format
 * @author Mark Hiner
 */
class DefaultChecker : AbstractChecker(), DefaultComponent {
    // -- HasFormat API methods --
    // -- Fields --
    override val format: Format? = null
}

/**
 * Default implementation of [ImageMetadata]. No added functionality over
 * [io.scif.AbstractImageMetadata].
 *
 * @see io.scif.ImageMetadata
 * @see io.scif.AbstractImageMetadata
 * @author Mark Hiner
 */
class DefaultImageMetadata : AbstractImageMetadata {
    // -- Constructors --
    constructor(n: Int) : super(n)

    constructor(n: Int, vararg axes: CalibratedAxis) : super(n, *axes)

    constructor(n: Int, axes: List<CalibratedAxis>) : super(n, axes)

    constructor(interval: Interval) : super(interval)

    constructor(interval: Interval, vararg axes: CalibratedAxis) : super(interval, *axes)

    constructor(interval: Interval, axes: List<CalibratedAxis>) : super(interval, axes)

    constructor(dimensions: Dimensions) : super(dimensions)

    constructor(dimensions: Dimensions, vararg axes: CalibratedAxis) : super(dimensions, *axes)

    constructor(dimensions: Dimensions, axes: List<CalibratedAxis>) : super(dimensions, axes)

    constructor(dimensions: LongArray) : super(dimensions)

    constructor(dimensions: LongArray, vararg axes: CalibratedAxis) : super(dimensions, *axes)

    constructor(dimensions: LongArray, axes: List<CalibratedAxis>) : super(dimensions, axes)

    constructor(min: LongArray, max: LongArray) : super(min, max)

    constructor(min: LongArray, max: LongArray, vararg axes: CalibratedAxis) : super(min, max, *axes)

    constructor(min: LongArray, max: LongArray, axes: List<CalibratedAxis>) : super(min, max, axes)

    constructor(source: ImageMetadata) : super(source)

    // -- ImageMetadata API Methods --
    override fun copy(): ImageMetadata = DefaultImageMetadata(this)
}

/**
 * Default [io.scif.Parser] implementation.
 *
 *
 * Populates the following fields:
 *
 *
 *  * [io.scif.Metadata.isFiltered]
 *  * [io.scif.Metadata.getSource]
 *  * [io.scif.Metadata.getDatasetName]
 *
 *
 * @see io.scif.Parser
 *
 * @author Mark Hiner
 */
class DefaultParser : AbstractParser<DefaultMetadata>(), DefaultComponent {
    // -- HasFormatAPI Methods --
    // -- Fields --
    override val format: Format? = null

    // -- AbstractParser API Methods --
    /*
	 * Non-functional typedParse implementation.
	 */
//    @Throws(IOException::class, FormatException::class) protected fun typedParse(stream: DataHandle<Location?>?,
//                                                                                 meta: DefaultMetadata?, config: SCIFIOConfig?) {
        // No implementation
//    }
}

/**
 * Non-functional default [io.scif.Reader] implementation. For use in
 * [io.scif.Format]s that do not need a Reader.
 *
 * @see io.scif.Reader
 *
 * @see io.scif.Format
 *
 * @author Mark Hiner
 */
class DefaultReader : ByteArrayReader<DefaultMetadata>(), DefaultComponent {
    // -- HasFormat API Methods --
    // -- Fields --
    val format: Format? = null

    // -- AbstractReader API Methods --
    protected fun createDomainArray(): Array<String?> {
        return arrayOfNulls(0)
    }

    // -- Reader API Methods --
    /**
     * Non-functional openPlane implementation.
     *
     * @throws UnsupportedOperationException
     */
    @Throws(FormatException::class, java.io.IOException::class) fun openPlane(imageIndex: Int, planeIndex: Long,
                                                                              plane: ByteArrayPlane?, bounds: Interval?,
                                                                              config: SCIFIOConfig?): ByteArrayPlane {
        throw java.lang.UnsupportedOperationException(
            "Trying to read using DefaultReader. " +
                    "Must implement a Reader specifically for this Format")
    }
}

/**
 * Non-functional default [io.scif.Writer] implementation. For use in
 * [io.scif.Format]s that do not need a Writer.
 *
 * @see io.scif.Writer
 *
 * @see io.scif.Format
 *
 * @author Mark Hiner
 */
class DefaultWriter : AbstractWriter<DefaultMetadata?>(), DefaultComponent {
    // -- HasFormat API methods --
    // -- Fields --
    val format: Format? = null

    // -- Writer API Methods --
    /**
     * Non-functional savePlane implementation.
     *
     * @throws UnsupportedOperationException
     */
    @Throws(FormatException::class, java.io.IOException::class) protected fun writePlane(imageIndex: Int, planeIndex: Long,
                                                                                         plane: Plane?, bounds: Interval?) {
        throw java.lang.UnsupportedOperationException(
            "Trying to write using DefaultWriter. " +
                    "Must implement a Writer specifically for this Format")
    }

    protected fun makeCompressionTypes(): Array<String?> {
        return arrayOfNulls(0)
    }
}


class DefaultBlock<T> : TypedBlock<T> {
    private var offsets: Dimensions? = null

    override val interval: Interval?
        get() = offsets

    fun setOffsets(offsets: Dimensions?) {
        this.offsets = offsets
    }

    override fun get(): T? {
        // TODO Auto-generated method stub
        return null
    }
}