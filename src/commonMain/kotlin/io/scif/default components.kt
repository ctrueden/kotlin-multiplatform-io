package io.scif

import net.imglib2.Dimensions
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
    constructor() : super()

    constructor(copy: ImageMetadata) : super(copy)

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


class DefaultBlock<T : NativeType<T>?, A> : TypedBlock<T, A> {
    override var offsets: Dimensions? = null

    override val interval: ArrayImg<T, A>?
        // TODO Auto-generated method stub
        get() = null
}