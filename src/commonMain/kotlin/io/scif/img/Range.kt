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
package io.scif.img

import io.scif.api.L
import platform.KotlinAssignmentOverloadTarget
import uns.L

/**
 * A list of [Long] values with extra convenience functionality. Values
 * can be parsed from a properly formatted string.
 *
 *
 * A [.head] and [.tail] convenience method are provided.
 * Additionally, a hash index is maintained over all values, for faster
 * [.contains] performance.
 *
 *
 *
 * Practically, ranges are ","-separated list of selected values. For each
 * selection specified, a start value is mandatory. An (inclusive) end value can
 * be specified using a "-". If an end is specified, a step can also be provided
 * using a ":". So, `5,6-10:2` would select planes 5, 6, 8 and 10 from the
 * dimension associated with this Range.
 *
 *
 * Ranges must match the pattern: `\d*(-\d*(:\d*)?)?(,\d*(-\d*(:\d)?*)?)*`
 * Valid patterns:
 *
 *  * 5,10,15
 *  * 5-10,7,10-25:3
 *  * 1-100:1,1-50:2,15-20
 *
 *
 *
 *
 *
 * NB: index order is preserved, so if indices are provided out of order, they
 * will remain out of order.
 *
 *
 * @author Mark Hiner
 */
@KotlinAssignmentOverloadTarget
class Range private constructor() {

    // [Kotlin] we cant extend `ArrayList` because it's a final class, so we create an internal field
    val longs = ArrayList<Long>()
    // -- Fields --
    // A set index is maintained to allow fast lookup for contains checks
    private val setIndex: MutableSet<Long> = HashSet()

    constructor(range: String) : this() {
        // Check for invalid patterns
        if (!range.matches(REGION_PATTERN.toRegex()))
            throw IllegalArgumentException("Invalid range pattern. Must match: $REGION_PATTERN")

        val intervals = range.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // Iterate over each axis of the region and extract its constraints
        for (interval in intervals) {
            val rangeTokens = interval.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val start = rangeTokens[0].toLong()
            var end = start
            var step = 1L

            if (rangeTokens.size == 2) {
                val rangeTail = rangeTokens[1].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                end = rangeTail[0].toLong()

                if (rangeTail.size == 2)
                    step = rangeTail[1].L
            }

            var j = start
            while (j <= end) {
                setIndex += j
                longs += j
                j += step
            }
        }
    }

    constructor(index: Int) : this(index.L)

    /**
     * Creates a singleton DimRange.
     *
     * @param index single index for this DimRange.
     */
    constructor(index: Long) : this() {
        setIndex += index
        longs += index
    }

    /**
     * Creates the DimRange: [start, end]
     *
     * @param start inclusive start value
     * @param end inclusive end value
     */
    constructor(start: Long, end: Long) : this() {
        for (l in start..end) {
            setIndex += l
            longs += l
        }
    }

    /**
     * Constructs a DimRange that includes only the values contained in the
     * provided array.
     *
     * @param values explicit list of values in this range
     */
    constructor(values: LongArray) : this() {
        for (l in values) {
            setIndex += l
            longs += l
        }
    }

    // -- DimensionRanges methods --
    operator fun contains(l: Any): Boolean = setIndex.contains(l)

    /**
     * @return The first value in this range.
     */
    fun head(): Long = longs.first()

    /**
     * @return The last value in this range.
     */
    fun tail(): Long = longs.last()


    // TODO assign operator? https://stackoverflow.com/questions/76022932/how-can-i-define-a-custom-assign-operator-overload-in-kotlin
    // member function
    /** Provides overloaded setter for setting the value of [value] using an assignment syntax */
//    fun assign(value: String) {
//        this.value = value
//    }


    // [Kotlin] `HashMap` methods
    fun trimToSize() = longs.trimToSize()
    fun ensureCapacity(minCapacity: Int) = longs.ensureCapacity(minCapacity)

    // From List

    val size: Int
        get() = longs.size
    fun isEmpty(): Boolean = longs.isEmpty()
    operator fun contains(element: Long): Boolean = longs.contains(element)
    fun containsAll(elements: Collection<Long>): Boolean = longs.containsAll(elements)
    operator fun get(index: Int): Long = longs[index]
    fun indexOf(element: Long): Int = longs.indexOf(element)
    fun lastIndexOf(element: Long): Int = longs.lastIndexOf(element)

    // From MutableCollection

    fun iterator(): MutableIterator<Long> = longs.iterator()

    // From MutableList

    fun add(element: Long): Boolean = longs.add(element)
    fun remove(element: Long): Boolean = longs.remove(element)
    fun addAll(elements: Collection<Long>): Boolean = longs.addAll(elements)
    fun addAll(index: Int, elements: Collection<Long>): Boolean = longs.addAll(index, elements)
    fun removeAll(elements: Collection<Long>): Boolean = longs.removeAll(elements)
    fun retainAll(elements: Collection<Long>): Boolean = longs.retainAll(elements)
    fun clear() = longs.clear()
    operator fun set(index: Int, element: Long): Long = longs.set(index, element)
    fun add(index: Int, element: Long) = longs.add(index, element)
    fun removeAt(index: Int): Long = longs.removeAt(index)
    fun listIterator(): MutableListIterator<Long> = longs.listIterator()
    fun listIterator(index: Int): MutableListIterator<Long> = longs.listIterator(index)
    fun subList(fromIndex: Int, toIndex: Int): MutableList<Long> = longs.subList(fromIndex, toIndex)

    companion object {
        // -- Constants --
        private const val REGION_PATTERN = "\\d*(-\\d*(:\\d*)?)?(,\\d*(-\\d*(:\\d)?)?)*"
    }
}