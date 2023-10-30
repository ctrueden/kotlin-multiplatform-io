/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2023 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
 * John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
 * Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
 * Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
 * Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
 * Jean-Yves Tinevez and Michael Zinsmaier.
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
@file:OptIn(ExperimentalUnsignedTypes::class)

package net.imglib2.display

import uns.i
import uns.ui
import uns.us

/**
 * 16-bit color lookup table.
 *
 * @author Stephan Saalfeld
 * @author Curtis Rueden
 */
class ColorTable16 : AbstractArrayColorTable<UShortArray> {
    /**
     * Initializes a 16-bit color table with a linear grayscale ramp.
     */
    constructor() : super(gray())

    /**
     * Initializes a 16-bit color table with the given table values.
     */
    constructor(values: Array<UShortArray>) : super(values)

    override val length: Int
        get() = values[0].size

    override val bits: Int
        get() = 16

    override fun get(comp: Int, bin: Int): Int {
        // convert 0xffff to 0xff
        return (getNative(comp, bin) shr 8).i
    }

    override fun getNative(comp: Int, bin: Int): UInt =
        // returns short value as unsigned, expressed as int
        values[comp][bin].ui

    override fun getResampled(comp: Int, bins: Int, bin: Int): Int {
        val newBin = (length.toLong() * bin / bins).i
        return get(comp, newBin)
    }

    companion object {
        // -- Helper methods --
        /**
         * Creates a linear grayscale ramp with 3 components and 65536 values.
         */
        private fun gray(): Array<UShortArray> = Array(3) { UShortArray(65536) { it.us } }
    }
}