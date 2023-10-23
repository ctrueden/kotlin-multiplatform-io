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

//import FormatException
import platform.FormatException
import kotlin.reflect.KClass

/**
 * Abstract superclass of all SCIFIO [io.scif.Format] implementations.
 *
 * @see io.scif.Format
 *
 * @see io.scif.Metadata
 *
 * @see io.scif.Parser
 *
 * @see io.scif.Reader
 *
 * @see io.scif.Writer
 *
 * @see io.scif.Checker
 *
 * @see io.scif.services.FormatService
 *
 * @author Mark Hiner
 */
abstract class AbstractFormat : /*AbstractSCIFIOPlugin(),*/ Format {
    // -- Fields --
    /** Valid suffixes for this file format.  */
    override val suffixes: Array<String> by lazy{ makeSuffixArray() }

    override var isEnabled: Boolean = true

    // Class references to the components of this Format
    override var metadataClass: KClass<out Metadata> = DefaultMetadata::class
    override var checkerClass: KClass<out Checker> = DefaultChecker::class
    override var parserClass: KClass<out Parser> = DefaultParser::class
    override var readerClass: KClass<out Reader> = TODO()// DefaultReader::class
    override var writerClass: KClass<out Writer> = DefaultWriter::class

    // -- Constructor --
    init {
        updateCustomClasses()
    }

    // -- AbstractFormat Methods --
    /**
     * Helper method to cache the suffix array for a format. Concrete format
     * classes should implement this method, returning an array of supported
     * suffixes.
     *
     * @return Valid suffixes for this file format.
     */
    protected abstract fun makeSuffixArray(): Array<String>

    override val formatName: String
        get() = TODO("`getInfo` comes from `AbstractRichPlugin`")// getInfo().getName()

    @Throws(FormatException::class)
    override fun createMetadata(): Metadata = createContextualObject(metadataClass)

    @Throws(FormatException::class)
    override fun createChecker(): Checker = createContextualObject(checkerClass)

    @Throws(FormatException::class)
    override fun createParser(): Parser = createContextualObject(parserClass)

    @Throws(FormatException::class)
    override fun createReader(): Reader = createContextualObject(readerClass)

    @Throws(FormatException::class)
    override fun createWriter(): Writer = createContextualObject(writerClass)

    // -- Helper Methods --
    /*
	 * Creates a SCIFIO component from its class. Also sets its context based on
	 * this format's context.
	 */
    @Throws(FormatException::class)
    private fun <T : HasFormat> createContextualObject(c: KClass<T>): T {
        val t: T = createObject(c)
        TODO()
        //        t.setContext(getContext())
        //
        //        // if we are creating a Default component, we need to
        //        // manually set its Format.
        //        if (DefaultComponent::class.java.isAssignableFrom(t.getClass())) {
        //            try {
        //                val fmt: java.lang.reflect.Field = t.getClass().getDeclaredField(
        //                    "format")
        //                fmt.setAccessible(true)
        //                fmt.set(t, this)
        //            } catch (e: java.lang.NoSuchFieldException) {
        //                throw FormatException( //
        //                    "Failed to populate DefaultComponent field", e)
        //            } catch (e: java.lang.SecurityException) {
        //                throw FormatException(
        //                    "Failed to populate DefaultComponent field", e)
        //            } catch (e: java.lang.IllegalArgumentException) {
        //                throw FormatException(
        //                    "Failed to populate DefaultComponent field", e)
        //            } catch (e: java.lang.IllegalAccessException) {
        //                throw FormatException(
        //                    "Failed to populate DefaultComponent field", e)
        //            }
        //        }
        //        return t
    }

    /*
	 * Returns an instance of an object from its Class
	 */
    @Throws(FormatException::class)
    private fun <T : HasFormat> createObject(c: KClass<T>): T {
        TODO()
        //        try {
        //            return c.newInstance()
        //        } catch (e: java.lang.InstantiationException) {
        //            throw FormatException(e)
        //        } catch (e: java.lang.IllegalAccessException) {
        //            throw FormatException(e)
        //        }
    }

    /*
	 * Overrides the default classes with declared custom components.
	 */
    private fun updateCustomClasses() {
        TODO()
        //        for (c in buildClassList()) {
        //            if (Metadata::class.java.isAssignableFrom(c)) metadataClass = c as KClass<out Metadata>
        //            else if (Checker::class.java.isAssignableFrom(c)) checkerClass = c as KClass<out Checker>
        //            else if (Parser::class.java.isAssignableFrom(c)) parserClass = c as KClass<out Parser>
        //            else if (Reader::class.java.isAssignableFrom(c)) readerClass = c as KClass<out Reader>
        //            else if (Writer::class.java.isAssignableFrom(c)) writerClass = c as KClass<out Writer>
        //        }
    }

    /*
	 * Searches for all nested classes within this class and recursively adds
	 * them to a complete class list.
	 */
    private fun buildClassList(): List<KClass<*>> {
        TODO()
        //        val classes: Array<KClass<*>> = this.javaClass.getDeclaredClasses()
        //        val classList = ArrayList<KClass<*>>()
        //
        //        for (c in classes)
        //            check(c, classList)
        //
        //        return classList
    }

    /*
	 * Recursive method to add a class, and all nested classes declared in that
	 * class, to the provided list of classes.
	 */
    private fun check(newClass: KClass<*>, classList: MutableList<KClass<*>>) {
        classList.add(newClass)
        TODO()
        //        for (c in newClass.getDeclaredClasses()) check(c, classList)
    }
}