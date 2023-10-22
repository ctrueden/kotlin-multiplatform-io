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

//import net.imglib2.*
//import net.imglib2.exception.IncompatibleTypeException
//import net.imglib2.img.Img
//import net.imglib2.img.ImgFactory
//import net.imglib2.img.array.ArrayImg
//import net.imglib2.img.array.ArrayImgFactory
//import net.imglib2.img.cell.CellImgFactory
//import net.imglib2.img.list.ListImgFactory
//import net.imglib2.type.NativeType
//import net.imglib2.type.Type
//import net.imglib2.type.numeric.RealType
//import net.imglib2.type.operators.ValueEquals
//import net.imglib2.view.Views
import uns.L
import kotlin.jvm.JvmOverloads
import kotlin.math.*

/**
 * A collection of general-purpose utility methods for working with ImgLib2 data
 * structures.
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 * @author Curtis Rueden
 */
object Util {
//    /**
//     * The possible java array size is JVM dependent an actually
//     * slightly below Integer.MAX_VALUE. This is the same MAX_ARRAY_SIZE
//     * as used for example in ArrayList in OpenJDK8.
//     */
//    private const val MAX_ARRAY_SIZE = Int.MAX_VALUE - 8
//
//    /**
//     * This does only work when T is erased to Object at call site.
//     *
//     *
//     * See https://github.com/imglib/imglib2/issues/253
//     */
//    @Deprecated("") fun <T> genericArray(length: Int): Array<T?> {
//        return arrayOfNulls<Any>(length) as Array<T?>
//    }
//
//    fun log2(value: Double): Double {
//        return ln(value) / ln(2.0)
//    }
//
//    // TODO: move to ArrayUtil?
//    fun getArrayFromValue(value: Double, numDimensions: Int): DoubleArray {
//        val values = DoubleArray(numDimensions)
//
//        for (d in 0 until numDimensions) values[d] = value
//
//        return values
//    }
//
//    // TODO: move to ArrayUtil?
//    fun getArrayFromValue(value: Float, numDimensions: Int): FloatArray {
//        val values = FloatArray(numDimensions)
//
//        for (d in 0 until numDimensions) values[d] = value
//
//        return values
//    }
//
//    // TODO: move to ArrayUtil?
//    fun getArrayFromValue(value: Int, numDimensions: Int): IntArray {
//        val values = IntArray(numDimensions)
//
//        for (d in 0 until numDimensions) values[d] = value
//
//        return values
//    }
//
//    // TODO: move to ArrayUtil?
//    fun getArrayFromValue(value: Long, numDimensions: Int): LongArray {
//        val values = LongArray(numDimensions)
//
//        for (d in 0 until numDimensions) values[d] = value
//
//        return values
//    }
//
//    fun distance(position1: RealLocalizable, position2: RealLocalizable): Double {
//        var dist = 0.0
//
//        val n: Int = position1.numDimensions()
//        for (d in 0 until n) {
//            val pos = position2.getDoublePosition(d) - position1.getDoublePosition(d)
//
//            dist += pos * pos
//        }
//
//        return sqrt(dist)
//    }
//
//    fun distance(position1: LongArray, position2: LongArray): Double {
//        var dist = 0.0
//
//        for (d in position1.indices) {
//            val pos = position2[d] - position1[d]
//
//            dist += (pos * pos).toDouble()
//        }
//
//        return sqrt(dist)
//    }
//
//    /**
//     * Computes the percentile of a collection of doubles (percentile 0.5
//     * roughly corresponds to median)
//     *
//     * @param values
//     * - the values
//     * @param percentile
//     * - the percentile [0...1]
//     * @return the corresponding value
//     */
//    fun percentile(values: DoubleArray, percentile: Double): Double {
//        val temp: DoubleArray = values.clone()
//        val length = temp.size
//        val pos = kotlin.math.min((length - 1).toDouble(), max(0.0, java.lang.Math.round((length - 1) * percentile).toInt().toDouble())).toInt()
//
//        KthElement.kthElement(pos, temp)
//
//        return temp[pos]
//    }
//
//    fun averageDouble(values: List<Double>): Double {
//        val size = values.size.toDouble()
//        var avg = 0.0
//
//        for (v in values) avg += v / size
//
//        return avg
//    }
//
//    fun averageFloat(values: List<Float>): Float {
//        val size = values.size.toDouble()
//        var avg = 0.0
//
//        for (v in values) avg += v / size
//
//        return avg.toFloat()
//    }
//
//    fun min(values: List<Float>): Float {
//        var min = Float.MAX_VALUE
//
//        for (v in values) if (v < min) min = v
//
//        return min
//    }
//
//    fun max(values: List<Float>): Float {
//        var max = -Float.MAX_VALUE
//
//        for (v in values) if (v > max) max = v
//
//        return max
//    }
//
//    fun average(values: FloatArray): Float {
//        val size = values.size.toDouble()
//        var avg = 0.0
//
//        for (v in values) avg += v / size
//
//        return avg.toFloat()
//    }
//
//    fun average(values: DoubleArray): Double {
//        val size = values.size.toDouble()
//        var avg = 0.0
//
//        for (v in values) avg += v / size
//
//        return avg
//    }
//
//    fun min(values: DoubleArray): Double {
//        var min = values[0]
//
//        for (v in values) if (v < min) min = v
//
//        return min
//    }
//
//    fun max(values: DoubleArray): Double {
//        var max = values[0]
//
//        for (v in values) if (v > max) max = v
//
//        return max
//    }
//
//    fun median(values: LongArray): Long {
//        val temp: LongArray = values.clone()
//        val median: Long
//
//        val length = temp.size
//
//        quicksort(temp, 0, length - 1)
//
//        median = if (length % 2 == 1) // odd length
//            temp[length / 2]
//        else  // even length
//            (temp[length / 2] + temp[length / 2 - 1]) / 2
//
//        return median
//    }
//
//    fun median(values: DoubleArray): Double {
//        val temp: DoubleArray = values.clone()
//        val median: Double
//
//        val length = temp.size
//
//        quicksort(temp, 0, length - 1)
//
//        median = if (length % 2 == 1) // odd length
//            temp[length / 2]
//        else  // even length
//            (temp[length / 2] + temp[length / 2 - 1]) / 2
//
//        return median
//    }
//
//    fun median(values: FloatArray): Float {
//        val temp: FloatArray = values.clone()
//        val median: Float
//
//        val length = temp.size
//
//        quicksort(temp, 0, length - 1)
//
//        median = if (length % 2 == 1) // odd length
//            temp[length / 2]
//        else  // even length
//            (temp[length / 2] + temp[length / 2 - 1]) / 2
//
//        return median
//    }
//
//    @JvmOverloads fun quicksort(data: LongArray?, left: Int = 0, right: Int = data.length - 1) {
//        if (data == null || data.size < 2) return
//        var i = left
//        var j = right
//        val x = data[(left + right) / 2]
//        do {
//            while (data[i] < x) i++
//            while (x < data[j]) j--
//            if (i <= j) {
//                val temp = data[i]
//                data[i] = data[j]
//                data[j] = temp
//                i++
//                j--
//            }
//        } while (i <= j)
//        if (left < j) quicksort(data, left, j)
//        if (i < right) quicksort(data, i, right)
//    }
//
//    @JvmOverloads fun quicksort(data: DoubleArray?, left: Int = 0, right: Int = data.length - 1) {
//        if (data == null || data.size < 2) return
//        var i = left
//        var j = right
//        val x = data[(left + right) / 2]
//        do {
//            while (data[i] < x) i++
//            while (x < data[j]) j--
//            if (i <= j) {
//                val temp = data[i]
//                data[i] = data[j]
//                data[j] = temp
//                i++
//                j--
//            }
//        } while (i <= j)
//        if (left < j) quicksort(data, left, j)
//        if (i < right) quicksort(data, i, right)
//    }
//
//    @JvmOverloads fun quicksort(data: FloatArray?, left: Int = 0, right: Int = data.length - 1) {
//        if (data == null || data.size < 2) return
//        var i = left
//        var j = right
//        val x = data[(left + right) / 2]
//        do {
//            while (data[i] < x) i++
//            while (x < data[j]) j--
//            if (i <= j) {
//                val temp = data[i]
//                data[i] = data[j]
//                data[j] = temp
//                i++
//                j--
//            }
//        } while (i <= j)
//        if (left < j) quicksort(data, left, j)
//        if (i < right) quicksort(data, i, right)
//    }
//
//    fun quicksort(data: DoubleArray?, sortAlso: IntArray, left: Int, right: Int) {
//        if (data == null || data.size < 2) return
//        var i = left
//        var j = right
//        val x = data[(left + right) / 2]
//        do {
//            while (data[i] < x) i++
//            while (x < data[j]) j--
//            if (i <= j) {
//                val temp = data[i]
//                data[i] = data[j]
//                data[j] = temp
//
//                val temp2 = sortAlso[i]
//                sortAlso[i] = sortAlso[j]
//                sortAlso[j] = temp2
//
//                i++
//                j--
//            }
//        } while (i <= j)
//        if (left < j) quicksort(data, sortAlso, left, j)
//        if (i < right) quicksort(data, sortAlso, i, right)
//    }
//
//    fun gLog(z: Double, c: Double): Double {
//        if (c == 0.0) return z
//        return log10((z + sqrt(z * z + c * c)) / 2.0)
//    }
//
//    fun gLog(z: Float, c: Float): Float {
//        if (c == 0f) return z
//        return log10((z + sqrt((z * z + c * c).toDouble())) / 2.0).toFloat()
//    }
//
//    fun gLogInv(w: Double, c: Double): Double {
//        if (c == 0.0) return w
//        return 10.pow(w) - (((c * c) * 10.pow(-w)) / 4.0)
//    }
//
//    fun gLogInv(w: Float, c: Float): Double {
//        if (c == 0f) return w.toDouble()
//        return 10.pow(w.toDouble()) - (((c * c) * 10.pow(-w.toDouble())) / 4.0)
//    }
//
//    fun isApproxEqual(a: Float, b: Float, threshold: Float): Boolean {
//        return if (a == b) true
//        else if (a + threshold > b && a - threshold < b) true
//        else false
//    }
//
//    fun isApproxEqual(a: Double, b: Double, threshold: Double): Boolean {
//        return if (a == b) true
//        else if (a + threshold > b && a - threshold < b) true
//        else false
//    }
//
//    fun round(value: Float): Int {
//        return roundToInt(value)
//    }
//
//    fun round(value: Double): Long {
//        return roundToLong(value)
//    }
//
//    fun roundToInt(value: Float): Int {
//        return (value + (0.5f * sign(value.toDouble()))).toInt()
//    }
//
//    fun roundToInt(value: Double): Int {
//        return (value + (0.5 * sign(value))).toInt()
//    }
//
//    fun roundToLong(value: Float): Long {
//        return (value + (0.5f * sign(value.toDouble()))).toLong()
//    }
//
//    fun roundToLong(value: Double): Long {
//        return (value + (0.5 * sign(value))).toLong()
//    }
//
//
//    /**
//     * This method creates a gaussian kernel
//     *
//     * @param sigma
//     * Standard Derivation of the gaussian function
//     * @param normalize
//     * Normalize integral of gaussian function to 1 or not...
//     * @return double[] The gaussian kernel
//     */
//    fun createGaussianKernel1DDouble(sigma: Double, normalize: Boolean): DoubleArray {
//        var size = 3
//        val gaussianKernel: DoubleArray
//
//        if (sigma <= 0) {
//            gaussianKernel = DoubleArray(3)
//            gaussianKernel[1] = 1.0
//        } else {
//            size = kotlin.math.max(3.0, (2 * (3 * sigma + 0.5).toInt() + 1).toDouble()).toInt()
//
//            val two_sq_sigma = 2 * sigma * sigma
//            gaussianKernel = DoubleArray(size)
//
//            for (x in size / 2 downTo 0) {
//                val `val` = exp(-(x * x) / two_sq_sigma)
//
//                gaussianKernel[size / 2 - x] = `val`
//                gaussianKernel[size / 2 + x] = `val`
//            }
//        }
//
//        if (normalize) {
//            var sum = 0.0
//            for (value in gaussianKernel) sum += value
//
//            for (i in gaussianKernel.indices) gaussianKernel[i] /= sum
//        }
//
//        return gaussianKernel
//    }
//
//    fun getSuggestedKernelDiameter(sigma: Double): Int {
//        var size = 3
//
//        if (sigma > 0) size = kotlin.math.max(3.0, (2 * (3 * sigma + 0.5).toInt() + 1).toDouble()).toInt()
//
//        return size
//    }
//
//    fun printCoordinates(value: FloatArray?): String {
//        var out = "(Array empty)"
//
//        if (value == null || value.size == 0) return out
//        out = "(" + value[0]
//
//        for (i in 1 until value.size) out += ", " + value[i]
//
//        out += ")"
//
//        return out
//    }
//
//    fun printCoordinates(value: DoubleArray?): String {
//        var out = "(Array empty)"
//
//        if (value == null || value.size == 0) return out
//        out = "(" + value[0]
//
//        for (i in 1 until value.size) out += ", " + value[i]
//
//        out += ")"
//
//        return out
//    }
//
//    fun printCoordinates(localizable: RealLocalizable?): String {
//        var out = "(RealLocalizable empty)"
//
//        if (localizable == null || localizable.numDimensions() === 0) return out
//        out = "(" + localizable.getFloatPosition(0)
//
//        for (i in 1 until localizable.numDimensions()) out += ", " + localizable.getFloatPosition(i)
//
//        out += ")"
//
//        return out
//    }
//
//    fun printInterval(interval: Interval?): String {
//        var out = "(Interval empty)"
//
//        if (interval == null || interval.numDimensions() === 0) return out
//
//        out = "[" + interval.min(0)
//
//        for (i in 1 until interval.numDimensions()) out += ", " + interval.min(i)
//
//        out += "] -> [" + interval.max(0)
//
//        for (i in 1 until interval.numDimensions()) out += ", " + interval.max(i)
//
//        out += "], dimensions (" + interval.dimension(0)
//
//        for (i in 1 until interval.numDimensions()) out += ", " + interval.dimension(i)
//
//        out += ")"
//
//        return out
//    }
//
//    fun printCoordinates(value: IntArray?): String {
//        var out = "(Array empty)"
//
//        if (value == null || value.size == 0) return out
//        out = "(" + value[0]
//
//        for (i in 1 until value.size) out += ", " + value[i]
//
//        out += ")"
//
//        return out
//    }
//
//    fun printCoordinates(value: LongArray?): String {
//        var out = "(Array empty)"
//
//        if (value == null || value.size == 0) return out
//        out = "(" + value[0]
//
//        for (i in 1 until value.size) out += ", " + value[i]
//
//        out += ")"
//
//        return out
//    }
//
//    fun printCoordinates(value: BooleanArray?): String {
//        var out = "(Array empty)"
//
//        if (value == null || value.size == 0) return out
//        out = "("
//
//        out += if (value[0]) "1"
//        else "0"
//
//        for (i in 1 until value.size) {
//            out += ", "
//            out += if (value[i]) "1"
//            else "0"
//        }
//
//        out += ")"
//
//        return out
//    }
//
//    fun pow(a: Int, b: Int): Int {
//        if (b == 0) return 1
//        else if (b == 1) return a
//        else {
//            var result = a
//
//            for (i in 1 until b) result *= a
//
//            return result
//        }
//    }
//
//    fun <T> max(value1: T, value2: T): T where T : Type<T>?, T : Comparable<T>? {
//        return if (value1!!.compareTo(value2) >= 0) value1
//        else value2
//    }
//
//    fun <T> min(value1: T, value2: T): T where T : Type<T>?, T : Comparable<T>? {
//        return if (value1!!.compareTo(value2) <= 0) value1
//        else value2
//    }
//
//    fun long2int(a: LongArray): IntArray {
//        val i = IntArray(a.size)
//
//        for (d in a.indices) i[d] = a[d].toInt()
//
//        return i
//    }
//
    fun IntArray.toLongArray() = LongArray(size) { this[it].L }

//    /**
//     * Gets an instance of T from the [RandomAccessibleInterval] by
//     * querying the value at the min coordinate
//     *
//     * @param <T>
//     * - the T
//     * @param rai
//     * - the [RandomAccessibleInterval]
//     * @return - an instance of T
//    </T> */
//    fun <T, F> getTypeFromInterval(rai: F): T where F : Interval?, F : RandomAccessible<T>? {
//        // create RandomAccess
//        val randomAccess: RandomAccess<T> = rai.randomAccess()
//
//        // place it at the first pixel
//        rai.min(randomAccess)
//
//        return randomAccess.get()
//    }
//
//    /**
//     * Gets an instance of T from the [RandomAccessibleInterval] by
//     * querying the value at the min coordinate
//     *
//     * @param <T>
//     * - the T
//     * @param rai
//     * - the [RandomAccessibleInterval]
//     * @return - an instance of T
//    </T> */
//    fun <T, F> getTypeFromRealInterval(rai: F): T where F : RealInterval?, F : RealRandomAccessible<T>? {
//        // create RealRandomAccess
//        val realRandomAccess: RealRandomAccess<T> = rai.realRandomAccess()
//
//        // place it at the first pixel
//        rai.realMin(realRandomAccess)
//
//        return realRandomAccess.get()
//    }
//
//    /**
//     * Create an [ArrayImgFactory] if an image of the requested
//     * `targetSize` could be held in an [ArrayImg]. Otherwise
//     * return a [CellImgFactory] with as large as possible cell size.
//     *
//     * @param targetSize
//     * size of image that the factory should be able to create.
//     * @param type
//     * type of the factory.
//     * @return an [ArrayImgFactory] or a [CellImgFactory].
//     */
//    fun <T : NativeType<T>?> getArrayOrCellImgFactory(targetSize: Dimensions, type: T): ImgFactory<T> {
//        return getArrayOrCellImgFactory(targetSize, Int.MAX_VALUE, type)
//    }
//
//    /**
//     * Create an [ArrayImgFactory] if an image of the requested
//     * `targetSize` could be held in an [ArrayImg]. Otherwise
//     * return a [CellImgFactory] with cell size
//     * `targetCellSize` (or as large as possible if
//     * `targetCellSize` is too large).
//     *
//     * @param targetSize
//     * size of image that the factory should be able to create.
//     * @param targetCellSize
//     * if a [CellImgFactory] is created, what should be the
//     * cell size.
//     * @param type
//     * type of the factory.
//     * @return an [ArrayImgFactory] or a [CellImgFactory].
//     */
//    fun <T : NativeType<T>?> getArrayOrCellImgFactory(targetSize: Dimensions, targetCellSize: Int, type: T): ImgFactory<T> {
//        val entitiesPerPixel: Fraction = type.getEntitiesPerPixel()
//        val numElements: Long = Intervals.numElements(targetSize)
//        val numEntities: Long = entitiesPerPixel.mulCeil(numElements)
//        if (numElements <= Int.MAX_VALUE && numEntities <= MAX_ARRAY_SIZE) return ArrayImgFactory(type)
//        val maxCellSize = java.lang.Math.min(MAX_ARRAY_SIZE / entitiesPerPixel.getRatio(), Int.MAX_VALUE).pow(1.0 / targetSize.numDimensions()) as Int
//        val cellSize = kotlin.math.min(targetCellSize.toDouble(), maxCellSize.toDouble()).toInt()
//        return CellImgFactory(type, cellSize)
//    }
//
//    /**
//     * Create an appropriate [ImgFactory] for the requested
//     * `targetSize` and `type`. If the target size is a [Img],
//     * return its [ImgFactory]. If the type is a [NativeType], then
//     * [.getArrayOrCellImgFactory] is used; if
//     * not, a [ListImgFactory] is returned.
//     *
//     * @param targetSize
//     * size of image that the factory should be able to create.
//     * @param type
//     * type of the factory.
//     * @return an [ArrayImgFactory], [CellImgFactory] or
//     * [ListImgFactory] as appropriate.
//     */
//    fun <T> getSuitableImgFactory(targetSize: Dimensions, type: T): ImgFactory<T> {
//        if (targetSize is Img) {
//            val targetImg: Img<*> = targetSize as Img<*>
//            val factory: ImgFactory<*> = targetImg.factory()
//            if (factory != null) {
//                try {
//                    return factory.imgFactory(type)
//                } catch (e: IncompatibleTypeException) {
//                }
//            }
//        }
//        if (type is NativeType) {
//            // NB: Eclipse does not demand the cast to ImgFactory< T >, but javac does.
//            val arrayOrCellImgFactory: ImgFactory<T> = getArrayOrCellImgFactory<NativeType<T>>(targetSize, type as NativeType) as ImgFactory<T>
//            return arrayOrCellImgFactory
//        }
//        return ListImgFactory(type)
//    }
//
//    /**
//     * (Hopefully) fast floor log<sub>2</sub> of an unsigned(!) integer value.
//     *
//     * @param v
//     * unsigned integer
//     * @return floor log<sub>2</sub>
//     */
//    fun ldu(v: Int): Int {
//        var v = v
//        var c = 0
//        do {
//            v = v shr 1
//            ++c
//        } while (v > 1)
//        return c
//    }
//
//    /**
//     * Checks whether n [IterableInterval] have the same iteration order.
//     */
//    fun equalIterationOrder(vararg intervals: IterableInterval<*>): Boolean {
//        val order: Any = intervals[0].iterationOrder()
//        for (i in 1 until intervals.size) {
//            if (order != intervals[i].iterationOrder()) return false
//        }
//
//        return true
//    }
//
//    /**
//     * Determines whether the two [Localizable] objects have the same
//     * position, with `long` precision.
//     *
//     *
//     * At first glance, this method may appear to be unnecessary, since there is
//     * also [.locationsEqual], which is
//     * more general. The difference is that this method compares the positions
//     * using [Localizable.getLongPosition], which has higher
//     * precision in integer space than
//     * [RealLocalizable.getDoublePosition] does, which is what the
//     * [.locationsEqual] method uses.
//     *
//     *
//     * @param l1
//     * The first [Localizable].
//     * @param l2
//     * The second [Localizable].
//     * @return True iff the positions are the same, including dimensionality.
//     * @see Localizable.getLongPosition
//     */
//    fun locationsEqual(l1: Localizable, l2: Localizable): Boolean {
//        val numDims: Int = l1.numDimensions()
//        if (l2.numDimensions() !== numDims) return false
//        for (d in 0 until numDims) {
//            if (l1.getLongPosition(d) != l2.getLongPosition(d)) return false
//        }
//        return true
//    }
//
//    /**
//     * Determines whether the two [RealLocalizable] objects have the same
//     * position, with `double` precision.
//     *
//     * @param l1
//     * The first [RealLocalizable].
//     * @param l2
//     * The second [RealLocalizable].
//     * @return True iff the positions are the same, including dimensionality.
//     * @see RealLocalizable.getDoublePosition
//     */
//    fun locationsEqual(l1: RealLocalizable, l2: RealLocalizable): Boolean {
//        val numDims: Int = l1.numDimensions()
//        if (l2.numDimensions() !== numDims) return false
//        for (d in 0 until numDims) {
//            if (l1.getDoublePosition(d) != l2.getDoublePosition(d)) return false
//        }
//        return true
//    }
//
//    /**
//     * Checks if both images have equal intervals and content.
//     */
//    fun <T : ValueEquals<U>?, U> imagesEqual(a: RandomAccessibleInterval<out T?>?, b: RandomAccessibleInterval<out U>?): Boolean {
//        return imagesEqual<Any?, Any>(a, b, ValueEquals::valueEquals)
//    }
//
//    /**
//     * Checks if both images have equal intervals and content.
//     * A predicate must be given to check if two pixels are equal.
//     */
//    fun <T, U> imagesEqual(a: RandomAccessibleInterval<out T>?, b: RandomAccessibleInterval<out U>?, pixelEquals: java.util.function.BiPredicate<T, U>): Boolean {
//        if (!Intervals.equals(a, b)) return false
//        for (pair in Views.interval(Views.pair(a, b), b)) if (!pixelEquals.test(pair.getA(), pair.getB())) return false
//        return true
//    }
//
//    /**
//     * Writes min(a,b) into a
//     *
//     * @param a
//     * @param b
//     */
//    fun min(a: DoubleArray, b: DoubleArray) {
//        for (i in a.indices) if (b[i] < a[i]) a[i] = b[i]
//    }
//
//    /**
//     * Writes max(a,b) into a
//     *
//     * @param a
//     * @param b
//     */
//    fun max(a: DoubleArray, b: DoubleArray) {
//        for (i in a.indices) if (b[i] > a[i]) a[i] = b[i]
//    }
//
//    /**
//     * Returns the content of `Iterable<RealType>` as array of doubles.
//     */
//    fun asDoubleArray(iterable: Iterable<RealType<*>?>): DoubleArray {
//        return java.util.stream.StreamSupport.stream(iterable.spliterator(), false).mapToDouble(RealType::getRealDouble).toArray()
//    }
//
//    /**
//     * Returns the pixels of an RandomAccessibleInterval of RealType as array of doubles.
//     * The pixels are sorted in flat iteration order.
//     */
//    fun asDoubleArray(rai: RandomAccessibleInterval<out RealType<*>?>?): DoubleArray {
//        return asDoubleArray(Views.flatIterable(rai))
//    }
//
//    /**
//     * Returns the pixels of an image of RealType as array of doubles.
//     * The pixels are sorted in flat iteration order.
//     */
//    fun asDoubleArray(image: Img<out RealType<*>?>?): DoubleArray {
//        return asDoubleArray(image as RandomAccessibleInterval<out RealType<*>?>?)
//    }
//
//    /**
//     * This method should be used in implementations of [ValueEquals], to
//     * override [Object.equals].
//     *
//     * @see net.imglib2.type.AbstractNativeType.equals
//     */
//    fun <T : ValueEquals<T>?> valueEqualsObject(a: T, b: Any): Boolean {
//        if (!a.getClass().isInstance(b)) return false
//        val t = b as T
//        return a.valueEquals(t)
//    }

    infix fun Int.combineHash(hash: Int): Int = 31 * this + hash
}