/*
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2023 ImageJ2 developers.
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
package net.imagej.axis

/**
 * An [CalibratedAxis] whose calibration is defined by an equation with
 * one or more variables.
 *
 * @author Curtis Rueden
 */
abstract class VariableAxis(type: AxisType, unit: String? = null) : AbstractCalibratedAxis(type, unit) {
    private val vars: HashMap<String, Double> = HashMap()

    // -- VariableAxis methods --
    /** Gets the value of the variable with the given name, or null if none.  */
    fun get(name: String): Double? = vars[name]

    /**
     * Sets the specified value to a variable of the given name.
     *
     * @param name The variable to assign.
     * @param value The value to assign, or null to clear the variable.
     */
    fun set(name: String, value: Double?) {
        if (value == null) vars -= name
        else vars[name] = value
    }

    /** Gets the number of variables with assigned values.  */
    val numVars: Int
        get() = vars.size

    /** Gets the set of variables with assigned values.  */
    val keys: Set<String>
        get() = vars.keys

    // -- CalibratedAxis methods --
    override fun particularEquation(): String {
        val sb = StringBuilder()
        // NB: Split general equation on potential variables, including delimiters.
        // For an explanation, see: http://stackoverflow.com/a/279337
        val tokens = generalEquation().split("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)")
        for (token in tokens) {
            if (token.matches("\\w+".toRegex())) {
                // token might be a variable; check the vars table
                val value = vars[token]
                if (value != null) {
                    // token *is* a variable; substitute the value!
                    sb.append('(')
                    sb.append(value)
                    sb.append(')')
                    continue
                }
            }
            sb.append(token)
        }
        return sb.toString()
    }
}