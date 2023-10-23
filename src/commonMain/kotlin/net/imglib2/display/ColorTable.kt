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
package net.imglib2.display

/**
 *
 * @author Aivar Grislis
 */
interface ColorTable {
    fun lookupARGB(min: Double, max: Double, value: Double): Int

    /**
     * Gets the number of color components in the table (typically 3 for RGB or
     * 4 for RGBA).
     */
    val componentCount: Int

    /**
     * Gets the number of elements for each color component in the table.
     */
    val length: Int

    /**
     * Gets an individual value from the color table.
     *
     * @param comp
     * The color component to query.
     * @param bin
     * The index into the color table.
     * @return The value of the table at the specified position.
     */
    fun get(comp: Int, bin: Int): Int

    /**
     * Gets an individual value from a color table with given number of bins.
     *
     * @param comp
     * The color component to query.
     * @param bins
     * The total number of bins.
     * @param bin
     * The index into the color table.
     * @return The value of the table at the specified position.
     */
    fun getResampled(comp: Int, bins: Int, bin: Int): Int

    companion object {
        // TODO ARG What about C,M,Y,K?
        const val RED: Int = 0

        const val GREEN: Int = 1

        const val BLUE: Int = 2

        const val ALPHA: Int = 3
    }
}