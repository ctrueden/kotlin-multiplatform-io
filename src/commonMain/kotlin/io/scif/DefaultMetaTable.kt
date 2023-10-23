/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2015 Board of Regents of the University of
 * Wisconsin-Madison
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

//import io.scif.common.DataTools
import org.scijava.util.StringUtils.sanitize
import kotlin.jvm.JvmOverloads

/**
 * Default [MetaTable] implementation. Provides a copying constructor and
 * a [.putList] implementation.
 *
 * @see MetaTable
 *
 * @author Mark Hiner
 */
class DefaultMetaTable
/** Basic constructor */
@JvmOverloads
constructor(var filtered: Boolean = false)
// [Kotlin] we cant extend directly `HashMap` because it's a final class
    : MutableMap<String, Any> by HashMap(), MetaTable {

    /**
     * Construct a MetaTable and populate it using an existing map.
     */
    constructor(copy: Map<String, Any>) : this() {
        putAll(copy)
    }

    // -- MetaTable API Methods --
    override fun putList(key: String, value: Any) {
        TODO()
//        val list = get(key) ?: ArrayList<Any>()
//
//        if (list is java.util.Vector) {
//            val valueList: java.util.Vector<Any>? = (list as java.util.Vector<Any>?)
//            valueList.add(value)
//        } else {
//            val v: java.util.Vector<Any> = java.util.Vector<Any>()
//            v.add(list)
//            v.add(value)
//            list = v
//        }
//
//        put(key!!, list!!)
    }

    override fun put(key: String, value: Any): Any? {
        var k = key
        var v = value
//        if (k == null || v == null /* || TODO !isMetadataCollected() */) {
//            return null
//        }

        k = k.trim()

        val string = v is String || v is Char
        val simple = string || v is Number || v is Boolean

        // string value, if passed in value is a string
        var `val` = if (string) v.toString() else null

        if (filtered) {
            // filter out complex data types
            if (!simple) return null

            // verify key & value are reasonable length
            val maxLen = 8192
            if (k.length > maxLen) return null
            if (string && `val`!!.length > maxLen) return null

            // remove all non-printable characters
            k = k.sanitize()
            if (string) `val` = `val`!!.sanitize()

            // verify key contains at least one alphabetic character
            if (!k.matches(Regex(".*[a-zA-Z].*"))) return null

            // remove &lt;, &gt; and &amp; to prevent XML parsing errors
            val invalidSequences = arrayOf("&lt;", "&gt;", "&amp;", "<", ">", "&")
            for (invalidSequence in invalidSequences) {
                val regex = Regex(invalidSequence)
                k = k.replace(regex, "")
                if (string) `val` = `val`!!.replace(regex, "")
            }

            // verify key & value are not empty
            if (k.isEmpty()) return null
            if (string && `val`!!.trim().isEmpty()) return null

            if (string) v = `val`!!
        }

        return (this as MutableMap<String, Any>).put(k, `val` ?: v)
    }
}