package io.scif

import io.scif.config.SCIFIOConfig
import net.imglib2.Interval
import okio.FileHandle
import okio.IOException
import kotlin.reflect.KClass


/**
 * Sub-interface for [io.scif.Metadata] to facilitate generic
 * parameterization of SCIFIO components.
 *
 *
 * Although `Metadata` itself does not require any generic parameters,
 * this sub-interface allows type narrowing of method parameters that accept
 * `Metadata` objects, e.g. [io.scif.Writer.setMetadata]
 * has a different type erasure than
 * [io.scif.TypedWriter.setMetadata] so the two signatures
 * can co-exist.
 *
 *
 * @author Mark Hiner
 * @see io.scif.Metadata
 */
interface TypedMetadata : Metadata


/**
 * Interface for all [io.scif.Parser] implementations that use generic
 * parameters.
 *
 *
 * Generics allow each concrete `Parser` implementation to type narrow the
 * return the type of `Metadata` from its [.parse] methods, as well
 * as the argument `Metadata` types for the same methods.
 *
 *
 * @author Mark Hiner
 * @param <M> The [io.scif.Metadata] type that will be returned by this
 * `Parser`.
</M> */
interface TypedParser<M : TypedMetadata> : Parser {
    //    @Throws(IOException::class, FormatException::class)
    //    override fun parse(loc: Location?): M

    //    @Throws(java.io.IOException::class, FormatException::class) override fun parse(handle: DataHandle<Location?>?): M

    //    @Throws(java.io.IOException::class, FormatException::class) override fun parse(loc: Location?, config: SCIFIOConfig?): M

    //    @Throws(java.io.IOException::class, FormatException::class) override fun parse(handle: DataHandle<Location?>?, config: SCIFIOConfig?): M

    /**
     * Generic-parameterized `parse` method, using
     * [io.scif.TypedMetadata] to avoid type erasure conflicts with
     * [io.scif.Parser.parse].
     *
     * @see io.scif.Parser.parse
     */
    //    @Throws(java.io.IOException::class, FormatException::class) override fun parse(stream: DataHandle<Location?>?, meta: M): M

    /**
     * Generic-parameterized `parse` method, using
     * [io.scif.TypedMetadata] to avoid type erasure conflicts with
     * [io.scif.Parser.parse].
     *
     * @see io.scif.Parser.parse
     */
    //    @Throws(java.io.IOException::class, FormatException::class) fun parse(fileName: Location?, meta: M, config: SCIFIOConfig?): M

    /**
     * Generic-parameterized `parse` method, using
     * [io.scif.TypedMetadata] to avoid type erasure conflicts with
     * [io.scif.Parser.parse].
     *
     * @see io.scif.Parser.parse
     */
    //    @Throws(java.io.IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?, meta: M, config: SCIFIOConfig?): M
}

/**
 * Interface for all [io.scif.Writer] implementations that use generic
 * parameters.
 *
 *
 * Generics all each concrete `Writer` implementation to guarantee, by
 * type narrowing, any methods that return SCIFIO components. Also, parallel
 * methods that take SCIFIO component arguments are defined to be type narrowed.
 *
 *
 * @author Mark Hiner
 * @param <M> The [io.scif.Metadata] type associated with this Writer.
</M> */
interface TypedWriter<M : TypedMetadata> : Writer {
    @set:Throws(FormatException::class)
    override var metadata: Metadata
}

/**
 * Interface for all [io.scif.Reader] implementations that use generic
 * parameters.
 *
 * Generics are used in `Reader` concrete implementations to type narrow
 * return types, and to provide parallel methods that can type narrow arguments.
 *
 * @author Mark Hiner
 * @param <M> - [io.scif.Metadata] used by this reader for reading images.
 * @param <B> - [io.scif.Block] return and parameter type for this
 * reader's [.openBlock] and [.readBlock] methods.
 * @see .openBlock
 * @see .readBlock
 * @see .setMetadata
 * @see .getMetadata
</P></M> */
interface TypedReader<M : TypedMetadata, T : NativeType<T>, B : TypedBlock<T, *>> : Reader {

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long): B

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block): B

    /**
     * Version of {@link #openBlock(int, long, Block)} with type-narrowed input
     * parameter.
     */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: B): B

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, config: SCIFIOConfig): B

    @Throws(FormatException::class, IOException::class)
    override fun openBlock(imageIndex: Int, blockIndex: Long, block: Block, config: SCIFIOConfig): B

    /** @see io.scif.TypedReader.openBlock */
    @Throws(FormatException::class, IOException::class)
    fun openBlock(imageIndex: Int, blockIndex: Long, block: B, config: SCIFIOConfig): B

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, pos: Interval, range: Interval): B

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: Block): B

    /**
     * Version of [.openRegion] with
     * type-narrowed input parameter.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: B): B

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, pos: Interval, range: Interval, config: SCIFIOConfig): B

    @Throws(FormatException::class, IOException::class)
    override fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: Block, config: SCIFIOConfig): B

    /**
     * Version of
     * [.openRegion] with
     * type-narrowed input parameter.
     */
    @Throws(FormatException::class, IOException::class)
    fun openRegion(imageIndex: Int, pos: Interval, range: Interval, block: B, config: SCIFIOConfig): B

    @set:Throws(IOException::class)
    override var metadata: Metadata?

    @set:Throws(IOException::class)
    var m: M?

    /**
     * Generic-parameterized `readBlock` method, using
     * [io.scif.TypedMetadata] to avoid type erasure conflicts with
     * [io.scif.Reader.readBlock]
     *
     * NB Presumes that the source stream `s` is set to the correct offset,
     * i.e. start of the block
     *
     * @see io.scif.Reader.readBlock
     */
    @Throws(IOException::class)
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, pos: Interval, range: Interval, block: Block): B

    /**
     * Generic-parameterized `readBlock` method, using
     * [io.scif.TypedMetadata] to avoid type erasure conflicts with
     * [io.scif.Reader.readBlock]
     *
     * NB Presumes that the source stream `s` is set to the correct offset,
     * i.e. start of the block
     *
     * @see io.scif.Reader.readBlock
     */
    @Throws(IOException::class)
    override fun readBlock(/*s: DataHandle<Location?>?*/s: FileHandle, imageIndex: Int, pos: Interval, range: Interval, scanlinePad: Int, block: Block): B

    override fun createBlock(extents: Interval): B

    /** Returns the class of `Blocks` associated with this `Reader`. */
    val blockClass: KClass<B>
}

interface TypedBlock<T : NativeType<T>, A> : Block