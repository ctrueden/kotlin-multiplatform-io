/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2017 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, Max Planck
 * Institute of Molecular Cell Biology and Genetics, University of
 * Konstanz, and KNIME GmbH.
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
// Portions of this class were derived from the loci.common.DataTools class of
// the Bio-Formats library, licensed according to Simplified BSD, as follows:
//
// Copyright (C) 2005 - 2015 Open Microscopy Environment:
//   - Board of Regents of the University of Wisconsin-Madison
//   - Glencoe Software, Inc.
//   - University of Dundee
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
package org.scijava.util

import kotlin.jvm.JvmOverloads

/**
 * Useful methods for working with [String]s.
 *
 * @author Curtis Rueden
 * @author Chris Allan
 * @author Melissa Linkert
 * @author Richard Domander (Royal Veterinary College, London)
 */
object StringUtils {

    const val DEFAULT_PAD_CHAR: Char = ' '

    /**
     * Splits a string only at separators outside of quotation marks (`"`).
     * Does not handle escaped quotes.
     */
//    fun splitUnquoted(s: String, separator: String?): Array<String> =
//        // See https://stackoverflow.com/a/1757107/1919049
//        s.split((java.util.regex.Pattern.quote(separator) +
//                "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").toRegex()).toTypedArray()

    /** Normalizes the decimal separator for the user's locale.  */
//    fun sanitizeDouble(value: String): String {
//        var value = value
//        value = value.replace("[^0-9,\\.]".toRegex(), "")
//        val separator: Char = java.text.DecimalFormatSymbols().getDecimalSeparator()
//        val usedSeparator = if (separator == '.') ',' else '.'
//        value = value.replace(usedSeparator, separator)
//        try {
//            value.toDouble()
//        } catch (e: java.lang.Exception) {
//            value = value.replace(separator, usedSeparator)
//        }
//        return value
//    }
//
//    /** Removes null bytes from a string.  */
//    fun stripNulls(toStrip: String): String {
//        val s: java.lang.StringBuilder = java.lang.StringBuilder()
//        for (i in 0 until toStrip.length) {
//            if (toStrip[i].code != 0) {
//                s.append(toStrip[i])
//            }
//        }
//        return s.toString().trim { it <= ' ' }
//    }
//
//    /** Checks if two filenames have the same prefix.  */
//    fun samePrefix(s1: String?, s2: String?): Boolean {
//        if (s1 == null || s2 == null) return false
//        val n1 = s1.indexOf(".")
//        val n2 = s2.indexOf(".")
//        if ((n1 == -1) || (n2 == -1)) return false
//
//        val slash1: Int = s1.lastIndexOf(java.io.File.pathSeparator)
//        val slash2: Int = s2.lastIndexOf(java.io.File.pathSeparator)
//
//        val sub1 = s1.substring(slash1 + 1, n1)
//        val sub2 = s2.substring(slash2 + 1, n2)
//        return sub1 == sub2 || sub1.startsWith(sub2) || sub2.startsWith(sub1)
//    }

    /** Removes unprintable characters from the given string.  */
    fun String.sanitize(): String {
//        if (s == null) return null
        val buf = StringBuilder(this)
        var i = 0
        while (i < buf.length) {
            val c = buf[i]
            if (c != '\t' && c != '\n' && (c < ' ' || c > '~'))
                buf.deleteAt(i--)
            i++
        }
        return buf.toString()
    }

//    fun isNullOrEmpty(s: String?): Boolean {
//        return s == null || s.isEmpty()
//    }
//
//    /**
//     * Adds characters to the end of the [String] to make it the given
//     * length
//     *
//     * @param s the original string
//     * @param length the length of the string with padding
//     * @param padChar the character added to the end
//     * @return the end padded [String]. Null if s is null, s if no padding
//     * is not necessary
//     */
//    /**
//     * Calls [.padEnd] with the [.DEFAULT_PAD_CHAR]
//     */
//    @JvmOverloads fun padEnd(s: String?, length: Int,
//                             padChar: Char = DEFAULT_PAD_CHAR): String? {
//        if (s == null) {
//            return null
//        }
//
//        val builder: java.lang.StringBuilder = java.lang.StringBuilder(s)
//        val padding = length - s.length
//        for (i in 0 until padding) {
//            builder.append(padChar)
//        }
//
//        return builder.toString()
//    }
//
//    /**
//     * Adds characters to the start of the [String] to make it the given
//     * length
//     *
//     * @param s the original string
//     * @param length the length of the string with padding
//     * @param padChar the character added to the start
//     * @return the start padded [String]. Null if s is null, s if no padding
//     * is not necessary
//     */
//    /**
//     * Calls [.padStart] with the
//     * [.DEFAULT_PAD_CHAR]
//     */
//    @JvmOverloads fun padStart(s: String?, length: Int,
//                               padChar: Char = DEFAULT_PAD_CHAR): String? {
//        if (s == null) {
//            return null
//        }
//
//        val builder: java.lang.StringBuilder = java.lang.StringBuilder()
//        val padding = length - s.length
//        for (i in 0 until padding) {
//            builder.append(padChar)
//        }
//
//        return builder.append(s).toString()
//    }
}