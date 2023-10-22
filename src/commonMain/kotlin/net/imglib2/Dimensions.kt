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
package net.imglib2

import net.imglib2.exception.InvalidDimensionsException

//import net.imglib2.exception.InvalidDimensionsException

/**
 * Defines an extent in *n*-dimensional discrete space.
 *
 * @author Tobias Pietzsch
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 * @author Philipp Hanslovsky
 */
interface Dimensions : EuclideanSpace {
    /**
     * Write the number of pixels in each dimension into long[].
     *
     * @param dimensions
     */
    fun dimensions(dimensions: LongArray) {
        // TODO this doesn't have sense..
        for (d in this.dimensions) dimensions[d] = dimension(d)
    }

    /**
     * Write the number of pixels in each dimension into [Positionable].
     *
     * @param dimensions
     */
    fun dimensions(dimensions: Positionable) {
        for (d in this.dimensions) dimensions.setPosition(dimension(d), d)
    }

    /**
     * Get the number of pixels in a given dimension *d*.
     *
     * @param d
     */
    fun dimension(d: Int): Long

    /**
     * Allocates a new long array with the dimensions of this object.
     *
     * Please note that his method allocates a new array each time which
     * introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated array
     * first and reuse it with [.dimensions].
     *
     * @return the dimensions
     */
    fun dimensionsAsLongArray(): LongArray {
        val dims = LongArray(numDimensions)
        dimensions(dims)
        return dims
    }

    /**
     * Allocates a new [Point] with the dimensions of this object.
     *
     * Please note that his method allocates a new [Point] each time
     * which introduces notable overhead in both compute and memory.
     * If you query it frequently, you should allocate a dedicated
     * [Point] first and reuse it with [.dimensions].
     *
     * @return the dimensions
     */
    fun dimensionsAsPoint(): Point = Point(*dimensionsAsLongArray())

    /*
     * -----------------------------------------------------------------------
     *
     * Static methods
     *
     * -----------------------------------------------------------------------
     */
    companion object {
        /**
         * Check whether all entries in `dimensions` are positive
         *
         * @param dimensions
         * @return true if all entries in `dimension` are positive, false
         * otherwise
         */
        fun allPositive(vararg dimensions: Long): Boolean {
            for (d in dimensions) if (d < 1) return false
            return true
        }

        /**
         * Check whether all entries in `dimensions` are positive
         *
         * @param dimensions
         * @return true if all entries in `dimension` are positive, false
         * otherwise
         */
        fun allPositive(vararg dimensions: Int): Boolean {
            for (d in dimensions) if (d < 1) return false
            return true
        }

        /**
         * Check that all entries in `dimensions` are positive
         *
         * @param dimensions
         * @return `dimensions`
         * @throws InvalidDimensionsException
         * if any of `dimensions` is not positive (zero or
         * negative).
         */
        @Throws(InvalidDimensionsException::class)
        fun verifyAllPositive(vararg dimensions: Long): LongArray {
            if (!allPositive(*dimensions))
                throw InvalidDimensionsException(dimensions, "Expected only positive dimensions but got: " + dimensions.contentToString())
            return dimensions
        }

        /**
         * Check that all entries in `dimensions` are positive
         *
         * @param dimensions
         * @return `dimensions`
         * @throws InvalidDimensionsException
         * if any of `dimensions` is not positive (zero or
         * negative).
         */
        @Throws(InvalidDimensionsException::class)
        fun verifyAllPositive(vararg dimensions: Int): IntArray {
            if (!allPositive(*dimensions))
                throw InvalidDimensionsException(dimensions, "Expected only positive dimensions but got: " + dimensions.contentToString())
            return dimensions
        }

        /**
         * Verify that `dimensions` is not null or empty, and that all
         * dimensions are positive. Throw [InvalidDimensionsException] otherwise.
         *
         * @param dimensions
         * to be verified.
         * @return `dimensions` if successfully verified.
         * @throws IllegalArgumentException
         * if `dimensions == null` or
         * `dimensions.length == 0` or any dimensions is zero or
         * negative.
         */
        @Throws(InvalidDimensionsException::class)
        fun verify(vararg dimensions: Long): LongArray {
//            if (dimensions == null) throw InvalidDimensionsException(dimensions, "Dimensions are null.")

            if (dimensions.isEmpty()) throw InvalidDimensionsException(dimensions, "Dimensions are zero length.")

            return verifyAllPositive(*dimensions)
        }

        /**
         * Verify that `dimensions` is not null or empty, and that all
         * dimensions are positive. Throw [InvalidDimensionsException] otherwise.
         *
         * @param dimensions
         * to be verified.
         * @return `dimensions` if successfully verified.
         * @throws IllegalArgumentException
         * if `dimensions == null` or
         * `dimensions.length == 0` or any dimensions is zero or
         * negative.
         */
        @Throws(InvalidDimensionsException::class)
        fun verify(vararg dimensions: Int): IntArray {
//            if (dimensions == null) throw InvalidDimensionsException(dimensions, "Dimensions are null.")

            if (dimensions.isEmpty()) throw InvalidDimensionsException(dimensions, "Dimensions are zero length.")

            return verifyAllPositive(*dimensions)
        }
    }
}