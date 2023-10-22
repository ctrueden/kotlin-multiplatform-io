package io.scif

/**
 * Abstract superclass for all classes that implement [io.scif.HasFormat].
 *
 * @see io.scif.HasFormat
 *
 * @see io.scif.services.FormatService
 *
 * @author Mark Hiner
 */
abstract class AbstractHasFormat : /*AbstractSCIFIOPlugin(),*/ HasFormat {
    //    @Parameter
    //    private val formatService: FormatService? = null

    //    val format: Format?
    //        // -- HasFormat API --
    //        get() = // All Format lookups go through the FormatService
    //            formatService.getFormatFromComponent(javaClass)

    override val formatName: String
        get() = format?.formatName ?: NO_FORMAT

    /**
     * Safely casts a [Location] to a [BrowsableLocation], throwing a
     * [FormatException] if the cast is not possible.
     *
     * @param loc the location to cast to [BrowsableLocation]
     * @throws FormatException
     */
    //    @Throws(FormatException::class) protected fun asBrowsableLocation(loc: Location?): BrowsableLocation? {
    //        if (loc is BrowsableLocation)
    //            return loc as BrowsableLocation?
    //        throw FormatException("The format: '$formatName' requires a browsable Location!")
    //    }

    /**
     * Convenience overload of [.asBrowsableLocation] that
     * operates on a handle.
     *
     * @param handle
     * @throws FormatException
     */
//    @Throws(FormatException::class) protected fun asBrowsableLocation(handle: DataHandle<Location?>): BrowsableLocation =
//        asBrowsableLocation(handle.get())

    companion object {
        private const val NO_FORMAT = "Format for this metadata could not be determined."
    }
}