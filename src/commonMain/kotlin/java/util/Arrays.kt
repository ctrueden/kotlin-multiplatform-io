//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
@file:OptIn(ExperimentalUnsignedTypes::class)

package java.util

import uns.i
import kotlin.math.min

object Arrays {
    private const val MIN_ARRAY_SORT_GRAN = 8192
    private const val INSERTIONSORT_THRESHOLD = 7

    //    fun sort(a: IntArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, 0, a.size)
    //    }
    //
    //    fun sort(a: IntArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, 0, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: LongArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, 0, a.size)
    //    }
    //
    //    fun sort(a: LongArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, 0, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: ShortArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun sort(a: ShortArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: CharArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun sort(a: CharArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: ByteArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun sort(a: ByteArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: FloatArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, 0, a.size)
    //    }
    //
    //    fun sort(a: FloatArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, 0, fromIndex, toIndex)
    //    }
    //
    //    fun sort(a: DoubleArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, 0, a.size)
    //    }
    //
    //    fun sort(a: DoubleArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, 0, fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: ByteArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun parallelSort(a: ByteArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: CharArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun parallelSort(a: CharArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: ShortArray) {
    //        java.util.DualPivotQuicksort.sort(a, 0, a.size)
    //    }
    //
    //    fun parallelSort(a: ShortArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: IntArray) {
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), 0, a.size)
    //    }
    //
    //    fun parallelSort(a: IntArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: LongArray) {
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), 0, a.size)
    //    }
    //
    //    fun parallelSort(a: LongArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: FloatArray) {
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), 0, a.size)
    //    }
    //
    //    fun parallelSort(a: FloatArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), fromIndex, toIndex)
    //    }
    //
    //    fun parallelSort(a: DoubleArray) {
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), 0, a.size)
    //    }
    //
    //    fun parallelSort(a: DoubleArray, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        java.util.DualPivotQuicksort.sort(a, java.util.concurrent.ForkJoinPool.getCommonPoolParallelism(), fromIndex, toIndex)
    //    }
    //
    //    fun rangeCheck(arrayLength: Int, fromIndex: Int, toIndex: Int) {
    //        if (fromIndex > toIndex) {
    //            throw java.lang.IllegalArgumentException("fromIndex($fromIndex) > toIndex($toIndex)")
    //        } else if (fromIndex < 0) {
    //            throw java.lang.ArrayIndexOutOfBoundsException(fromIndex)
    //        } else if (toIndex > arrayLength) {
    //            throw java.lang.ArrayIndexOutOfBoundsException(toIndex)
    //        }
    //    }
    //
    //    fun <T : Comparable<T>?> parallelSort(a: Array<T>) {
    //        val n = a.size
    //        var p: Int
    //        if (n > 8192 && (java.util.concurrent.ForkJoinPool.getCommonPoolParallelism().also { p = it }) != 1) {
    //            var g: Int
    //            java.util.ArraysParallelSortHelpers.FJObject.Sorter(null as java.util.concurrent.CountedCompleter?, a, java.lang.reflect.Array.newInstance(a.javaClass.getComponentType(), n) as Array<Comparable<*>?>, 0, n, 0, if (((n / (p shl 2)).also { g = it }) <= 8192) 8192 else g, NaturalOrder.INSTANCE).invoke()
    //        } else {
    //            java.util.TimSort.sort<Any>(a, 0, n, NaturalOrder.INSTANCE, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    fun <T : Comparable<T>?> parallelSort(a: Array<T>, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        val n = toIndex - fromIndex
    //        var p: Int
    //        if (n > 8192 && (java.util.concurrent.ForkJoinPool.getCommonPoolParallelism().also { p = it }) != 1) {
    //            var g: Int
    //            java.util.ArraysParallelSortHelpers.FJObject.Sorter(null as java.util.concurrent.CountedCompleter?, a, java.lang.reflect.Array.newInstance(a.javaClass.getComponentType(), n) as Array<Comparable<*>?>, fromIndex, n, 0, if (((n / (p shl 2)).also { g = it }) <= 8192) 8192 else g, NaturalOrder.INSTANCE).invoke()
    //        } else {
    //            java.util.TimSort.sort<Any>(a, fromIndex, toIndex, NaturalOrder.INSTANCE, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    fun <T> parallelSort(a: Array<T>, cmp: java.util.Comparator<in T>?) {
    //        var cmp: java.util.Comparator<in T>? = cmp
    //        if (cmp == null) {
    //            cmp = NaturalOrder.INSTANCE
    //        }
    //
    //        val n = a.size
    //        var p: Int
    //        if (n > 8192 && (java.util.concurrent.ForkJoinPool.getCommonPoolParallelism().also { p = it }) != 1) {
    //            var g: Int
    //            java.util.ArraysParallelSortHelpers.FJObject.Sorter(null as java.util.concurrent.CountedCompleter?, a, java.lang.reflect.Array.newInstance(a.javaClass.getComponentType(), n) as Array<Any?>, 0, n, 0, if (((n / (p shl 2)).also { g = it }) <= 8192) 8192 else g, cmp as java.util.Comparator?).invoke()
    //        } else {
    //            java.util.TimSort.sort<Any>(a, 0, n, cmp as java.util.Comparator?, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    fun <T> parallelSort(a: Array<T>, fromIndex: Int, toIndex: Int, cmp: java.util.Comparator<in T>?) {
    //        var cmp: java.util.Comparator<in T>? = cmp
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        if (cmp == null) {
    //            cmp = NaturalOrder.INSTANCE
    //        }
    //
    //        val n = toIndex - fromIndex
    //        var p: Int
    //        if (n > 8192 && (java.util.concurrent.ForkJoinPool.getCommonPoolParallelism().also { p = it }) != 1) {
    //            var g: Int
    //            java.util.ArraysParallelSortHelpers.FJObject.Sorter(null as java.util.concurrent.CountedCompleter?, a, java.lang.reflect.Array.newInstance(a.javaClass.getComponentType(), n) as Array<Any?>, fromIndex, n, 0, if (((n / (p shl 2)).also { g = it }) <= 8192) 8192 else g, cmp as java.util.Comparator?).invoke()
    //        } else {
    //            java.util.TimSort.sort<Any>(a, fromIndex, toIndex, cmp as java.util.Comparator?, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    fun sort(a: Array<Any>) {
    //        if (LegacyMergeSort.userRequested) {
    //            legacyMergeSort(a)
    //        } else {
    //            java.util.ComparableTimSort.sort(a, 0, a.size, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    private fun legacyMergeSort(a: Array<Any>) {
    //        val aux = a.clone() as Array<Any>
    //        mergeSort(aux, a, 0, a.size, 0)
    //    }
    //
    //    fun sort(a: Array<Any>, fromIndex: Int, toIndex: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        if (LegacyMergeSort.userRequested) {
    //            legacyMergeSort(a, fromIndex, toIndex)
    //        } else {
    //            java.util.ComparableTimSort.sort(a, fromIndex, toIndex, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    private fun legacyMergeSort(a: Array<Any>, fromIndex: Int, toIndex: Int) {
    //        val aux = copyOfRange(a, fromIndex, toIndex)
    //        mergeSort(aux, a, fromIndex, toIndex, -fromIndex)
    //    }
    //
    //    private fun mergeSort(src: Array<Any>, dest: Array<Any>, low: Int, high: Int, off: Int) {
    //        var low = low
    //        var high = high
    //        val length = high - low
    //        var destLow: Int
    //        var destHigh: Int
    //        if (length < 7) {
    //            destLow = low
    //            while (destLow < high) {
    //                destHigh = destLow
    //                while (destHigh > low && (dest[destHigh - 1] as Comparable<*>).compareTo(dest[destHigh]) > 0) {
    //                    swap(dest, destHigh, destHigh - 1)
    //                    --destHigh
    //                }
    //                ++destLow
    //            }
    //        } else {
    //            destLow = low
    //            destHigh = high
    //            low += off
    //            high += off
    //            val mid = low + high ushr 1
    //            mergeSort(dest, src, low, mid, -off)
    //            mergeSort(dest, src, mid, high, -off)
    //            if ((src[mid - 1] as Comparable<*>).compareTo(src[mid]) <= 0) {
    //                java.lang.System.arraycopy(src, low, dest, destLow, length)
    //            } else {
    //                var i = destLow
    //                var p = low
    //
    //                var q = mid
    //                while (i < destHigh) {
    //                    if (q < high && (p >= mid || (src[p] as Comparable<*>).compareTo(src[q]) > 0)) {
    //                        dest[i] = src[q++]
    //                    } else {
    //                        dest[i] = src[p++]
    //                    }
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    private fun swap(x: Array<Any>, a: Int, b: Int) {
    //        val t = x[a]
    //        x[a] = x[b]
    //        x[b] = t
    //    }
    //
    //    fun <T> sort(a: Array<T>, c: java.util.Comparator<in T>?) {
    //        if (c == null) {
    //            sort(a)
    //        } else if (LegacyMergeSort.userRequested) {
    //            legacyMergeSort(a, c)
    //        } else {
    //            java.util.TimSort.sort(a, 0, a.size, c, null as Array<Any?>?, 0, 0)
    //        }
    //    }
    //
    //    private fun <T> legacyMergeSort(a: Array<T>, c: java.util.Comparator<in T>?) {
    //        val aux: Array<T> = a.clone() as Array<Any>
    //        if (c == null) {
    //            mergeSort(aux, a, 0, a.size, 0)
    //        } else {
    //            mergeSort(aux, a, 0, a.size, 0, c)
    //        }
    //    }
    //
    //    fun <T> sort(a: Array<T>, fromIndex: Int, toIndex: Int, c: java.util.Comparator<in T>?) {
    //        if (c == null) {
    //            sort(a, fromIndex, toIndex)
    //        } else {
    //            rangeCheck(a.size, fromIndex, toIndex)
    //            if (LegacyMergeSort.userRequested) {
    //                legacyMergeSort(a, fromIndex, toIndex, c)
    //            } else {
    //                java.util.TimSort.sort(a, fromIndex, toIndex, c, null as Array<Any?>?, 0, 0)
    //            }
    //        }
    //    }
    //
    //    private fun <T> legacyMergeSort(a: Array<T>, fromIndex: Int, toIndex: Int, c: java.util.Comparator<in T>?) {
    //        val aux = copyOfRange(a, fromIndex, toIndex)
    //        if (c == null) {
    //            mergeSort(aux, a, fromIndex, toIndex, -fromIndex)
    //        } else {
    //            mergeSort(aux, a, fromIndex, toIndex, -fromIndex, c)
    //        }
    //    }
    //
    //    private fun mergeSort(src: Array<Any>, dest: Array<Any>, low: Int, high: Int, off: Int, c: java.util.Comparator) {
    //        var low = low
    //        var high = high
    //        val length = high - low
    //        var destLow: Int
    //        var destHigh: Int
    //        if (length < 7) {
    //            destLow = low
    //            while (destLow < high) {
    //                destHigh = destLow
    //                while (destHigh > low && c.compare(dest[destHigh - 1], dest[destHigh]) > 0) {
    //                    swap(dest, destHigh, destHigh - 1)
    //                    --destHigh
    //                }
    //                ++destLow
    //            }
    //        } else {
    //            destLow = low
    //            destHigh = high
    //            low += off
    //            high += off
    //            val mid = low + high ushr 1
    //            mergeSort(dest, src, low, mid, -off, c)
    //            mergeSort(dest, src, mid, high, -off, c)
    //            if (c.compare(src[mid - 1], src[mid]) <= 0) {
    //                java.lang.System.arraycopy(src, low, dest, destLow, length)
    //            } else {
    //                var i = destLow
    //                var p = low
    //
    //                var q = mid
    //                while (i < destHigh) {
    //                    if (q < high && (p >= mid || c.compare(src[p], src[q]) > 0)) {
    //                        dest[i] = src[q++]
    //                    } else {
    //                        dest[i] = src[p++]
    //                    }
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun <T> parallelPrefix(array: Array<T>, op: java.util.function.BinaryOperator<T>?) {
    //        java.util.Objects.requireNonNull<java.util.function.BinaryOperator<T>>(op)
    //        if (array.size > 0) {
    //            java.util.ArrayPrefixHelpers.CumulateTask(null as java.util.ArrayPrefixHelpers.CumulateTask?, op, array, 0, array.size).invoke()
    //        }
    //    }
    //
    //    fun <T> parallelPrefix(array: Array<T>, fromIndex: Int, toIndex: Int, op: java.util.function.BinaryOperator<T>?) {
    //        java.util.Objects.requireNonNull<java.util.function.BinaryOperator<T>>(op)
    //        rangeCheck(array.size, fromIndex, toIndex)
    //        if (fromIndex < toIndex) {
    //            java.util.ArrayPrefixHelpers.CumulateTask(null as java.util.ArrayPrefixHelpers.CumulateTask?, op, array, fromIndex, toIndex).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: LongArray, op: java.util.function.LongBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.LongBinaryOperator>(op)
    //        if (array.size > 0) {
    //            java.util.ArrayPrefixHelpers.LongCumulateTask(null as java.util.ArrayPrefixHelpers.LongCumulateTask?, op, array, 0, array.size).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: LongArray, fromIndex: Int, toIndex: Int, op: java.util.function.LongBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.LongBinaryOperator>(op)
    //        rangeCheck(array.size, fromIndex, toIndex)
    //        if (fromIndex < toIndex) {
    //            java.util.ArrayPrefixHelpers.LongCumulateTask(null as java.util.ArrayPrefixHelpers.LongCumulateTask?, op, array, fromIndex, toIndex).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: DoubleArray, op: java.util.function.DoubleBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.DoubleBinaryOperator>(op)
    //        if (array.size > 0) {
    //            java.util.ArrayPrefixHelpers.DoubleCumulateTask(null as java.util.ArrayPrefixHelpers.DoubleCumulateTask?, op, array, 0, array.size).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: DoubleArray, fromIndex: Int, toIndex: Int, op: java.util.function.DoubleBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.DoubleBinaryOperator>(op)
    //        rangeCheck(array.size, fromIndex, toIndex)
    //        if (fromIndex < toIndex) {
    //            java.util.ArrayPrefixHelpers.DoubleCumulateTask(null as java.util.ArrayPrefixHelpers.DoubleCumulateTask?, op, array, fromIndex, toIndex).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: IntArray, op: java.util.function.IntBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.IntBinaryOperator>(op)
    //        if (array.size > 0) {
    //            java.util.ArrayPrefixHelpers.IntCumulateTask(null as java.util.ArrayPrefixHelpers.IntCumulateTask?, op, array, 0, array.size).invoke()
    //        }
    //    }
    //
    //    fun parallelPrefix(array: IntArray, fromIndex: Int, toIndex: Int, op: java.util.function.IntBinaryOperator?) {
    //        java.util.Objects.requireNonNull<java.util.function.IntBinaryOperator>(op)
    //        rangeCheck(array.size, fromIndex, toIndex)
    //        if (fromIndex < toIndex) {
    //            java.util.ArrayPrefixHelpers.IntCumulateTask(null as java.util.ArrayPrefixHelpers.IntCumulateTask?, op, array, fromIndex, toIndex).invoke()
    //        }
    //    }
    //
    //    fun binarySearch(a: LongArray, key: Long): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: LongArray, fromIndex: Int, toIndex: Int, key: Long): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: LongArray, fromIndex: Int, toIndex: Int, key: Long): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else {
    //                if (midVal <= key) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: IntArray, key: Int): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: IntArray, fromIndex: Int, toIndex: Int, key: Int): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: IntArray, fromIndex: Int, toIndex: Int, key: Int): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else {
    //                if (midVal <= key) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: ShortArray, key: Short): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: ShortArray, fromIndex: Int, toIndex: Int, key: Short): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: ShortArray, fromIndex: Int, toIndex: Int, key: Short): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else {
    //                if (midVal <= key) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: CharArray, key: Char): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: CharArray, fromIndex: Int, toIndex: Int, key: Char): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: CharArray, fromIndex: Int, toIndex: Int, key: Char): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else {
    //                if (midVal <= key) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: ByteArray, key: Byte): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: ByteArray, fromIndex: Int, toIndex: Int, key: Byte): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: ByteArray, fromIndex: Int, toIndex: Int, key: Byte): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else {
    //                if (midVal <= key) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: DoubleArray, key: Double): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: DoubleArray, fromIndex: Int, toIndex: Int, key: Double): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: DoubleArray, fromIndex: Int, toIndex: Int, key: Double): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else if (midVal > key) {
    //                high = mid - 1
    //            } else {
    //                val midBits: Long = java.lang.Double.doubleToLongBits(midVal)
    //                val keyBits: Long = java.lang.Double.doubleToLongBits(key)
    //                if (midBits == keyBits) {
    //                    return mid
    //                }
    //
    //                if (midBits < keyBits) {
    //                    low = mid + 1
    //                } else {
    //                    high = mid - 1
    //                }
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: FloatArray, key: Float): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: FloatArray, fromIndex: Int, toIndex: Int, key: Float): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: FloatArray, fromIndex: Int, toIndex: Int, key: Float): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid]
    //            if (midVal < key) {
    //                low = mid + 1
    //            } else if (midVal > key) {
    //                high = mid - 1
    //            } else {
    //                val midBits: Int = java.lang.Float.floatToIntBits(midVal)
    //                val keyBits: Int = java.lang.Float.floatToIntBits(key)
    //                if (midBits == keyBits) {
    //                    return mid
    //                }
    //
    //                if (midBits < keyBits) {
    //                    low = mid + 1
    //                } else {
    //                    high = mid - 1
    //                }
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun binarySearch(a: Array<Any>, key: Any): Int {
    //        return binarySearch0(a, 0, a.size, key)
    //    }
    //
    //    fun binarySearch(a: Array<Any>, fromIndex: Int, toIndex: Int, key: Any): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key)
    //    }
    //
    //    private fun binarySearch0(a: Array<Any>, fromIndex: Int, toIndex: Int, key: Any): Int {
    //        var low = fromIndex
    //        var high = toIndex - 1
    //
    //        while (low <= high) {
    //            val mid = low + high ushr 1
    //            val midVal = a[mid] as Comparable<*>
    //            val cmp = midVal.compareTo(key)
    //            if (cmp < 0) {
    //                low = mid + 1
    //            } else {
    //                if (cmp <= 0) {
    //                    return mid
    //                }
    //
    //                high = mid - 1
    //            }
    //        }
    //
    //        return -(low + 1)
    //    }
    //
    //    fun <T> binarySearch(a: Array<T>, key: T, c: java.util.Comparator<in T>?): Int {
    //        return binarySearch0(a, 0, a.size, key, c)
    //    }
    //
    //    fun <T> binarySearch(a: Array<T>, fromIndex: Int, toIndex: Int, key: T, c: java.util.Comparator<in T>?): Int {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //        return binarySearch0(a, fromIndex, toIndex, key, c)
    //    }
    //
    //    private fun <T> binarySearch0(a: Array<T>, fromIndex: Int, toIndex: Int, key: T, c: java.util.Comparator<in T>?): Int {
    //        if (c == null) {
    //            return binarySearch0(a, fromIndex, toIndex, key)
    //        } else {
    //            var low = fromIndex
    //            var high = toIndex - 1
    //
    //            while (low <= high) {
    //                val mid = low + high ushr 1
    //                val midVal = a[mid]
    //                val cmp: Int = c.compare(midVal, key)
    //                if (cmp < 0) {
    //                    low = mid + 1
    //                } else {
    //                    if (cmp <= 0) {
    //                        return mid
    //                    }
    //
    //                    high = mid - 1
    //                }
    //            }
    //
    //            return -(low + 1)
    //        }
    //    }
    //
    //    fun equals(a: LongArray?, a2: LongArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: LongArray, aFromIndex: Int, aToIndex: Int, b: LongArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: IntArray?, a2: IntArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: IntArray, aFromIndex: Int, aToIndex: Int, b: IntArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: ShortArray?, a2: ShortArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: ShortArray, aFromIndex: Int, aToIndex: Int, b: ShortArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    @IntrinsicCandidate fun equals(a: CharArray?, a2: CharArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: CharArray, aFromIndex: Int, aToIndex: Int, b: CharArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    @IntrinsicCandidate fun equals(a: ByteArray?, a2: ByteArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: ByteArray, aFromIndex: Int, aToIndex: Int, b: ByteArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: BooleanArray?, a2: BooleanArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: BooleanArray, aFromIndex: Int, aToIndex: Int, b: BooleanArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: DoubleArray?, a2: DoubleArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: DoubleArray, aFromIndex: Int, aToIndex: Int, b: DoubleArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: FloatArray?, a2: FloatArray?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            return if (a2.size != length) {
    //                false
    //            } else {
    //                jdk.internal.util.ArraysSupport.mismatch(a, a2, length) < 0
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: FloatArray, aFromIndex: Int, aToIndex: Int, b: FloatArray, bFromIndex: Int, bToIndex: Int): Boolean {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        return if (aLength != bLength) {
    //            false
    //        } else {
    //            jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, aLength) < 0
    //        }
    //    }
    //
    //    fun equals(a: Array<Any>?, a2: Array<Any>?): Boolean {
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            if (a2.size != length) {
    //                return false
    //            } else {
    //                for (i in 0 until length) {
    //                    if (a[i] != a2[i]) {
    //                        return false
    //                    }
    //                }
    //
    //                return true
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun equals(a: Array<Any>, aFromIndex: Int, aToIndex: Int, b: Array<Any>, bFromIndex: Int, bToIndex: Int): Boolean {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        if (aLength != bLength) {
    //            return false
    //        } else {
    //            for (i in 0 until aLength) {
    //                if (a[aFromIndex++] != b[bFromIndex++]) {
    //                    return false
    //                }
    //            }
    //
    //            return true
    //        }
    //    }
    //
    //    fun <T> equals(a: Array<T>?, a2: Array<T>?, cmp: java.util.Comparator<in T>): Boolean {
    //        java.util.Objects.requireNonNull(cmp)
    //        if (a == a2) {
    //            return true
    //        } else if (a != null && a2 != null) {
    //            val length = a.size
    //            if (a2.size != length) {
    //                return false
    //            } else {
    //                for (i in 0 until length) {
    //                    if (cmp.compare(a[i], a2[i]) != 0) {
    //                        return false
    //                    }
    //                }
    //
    //                return true
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun <T> equals(a: Array<T>, aFromIndex: Int, aToIndex: Int, b: Array<T>, bFromIndex: Int, bToIndex: Int, cmp: java.util.Comparator<in T>): Boolean {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        java.util.Objects.requireNonNull(cmp)
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        if (aLength != bLength) {
    //            return false
    //        } else {
    //            for (i in 0 until aLength) {
    //                if (cmp.compare(a[aFromIndex++], b[bFromIndex++]) != 0) {
    //                    return false
    //                }
    //            }
    //
    //            return true
    //        }
    //    }
    //
    //    fun fill(a: LongArray, `val`: Long) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: LongArray, fromIndex: Int, toIndex: Int, `val`: Long) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: IntArray, `val`: Int) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: IntArray, fromIndex: Int, toIndex: Int, `val`: Int) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: ShortArray, `val`: Short) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: ShortArray, fromIndex: Int, toIndex: Int, `val`: Short) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: CharArray, `val`: Char) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: CharArray, fromIndex: Int, toIndex: Int, `val`: Char) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: ByteArray, `val`: Byte) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: ByteArray, fromIndex: Int, toIndex: Int, `val`: Byte) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: BooleanArray, `val`: Boolean) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: BooleanArray, fromIndex: Int, toIndex: Int, `val`: Boolean) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: DoubleArray, `val`: Double) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: DoubleArray, fromIndex: Int, toIndex: Int, `val`: Double) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: FloatArray, `val`: Float) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: FloatArray, fromIndex: Int, toIndex: Int, `val`: Float) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun fill(a: Array<Any?>, `val`: Any?) {
    //        var i = 0
    //
    //        val len = a.size
    //        while (i < len) {
    //            a[i] = `val`
    //            ++i
    //        }
    //    }
    //
    //    fun fill(a: Array<Any?>, fromIndex: Int, toIndex: Int, `val`: Any?) {
    //        rangeCheck(a.size, fromIndex, toIndex)
    //
    //        for (i in fromIndex until toIndex) {
    //            a[i] = `val`
    //        }
    //    }
    //
    //    fun <T> copyOf(original: Array<T>, newLength: Int): Array<T> {
    //        return copyOf<Any, T>(original, newLength, original.javaClass)
    //    }
    //
    //    @IntrinsicCandidate fun <T, U> copyOf(original: Array<U>, newLength: Int, newType: java.lang.Class<out Array<T>?>): Array<T?> {
    //        val copy: Array<T?> = if (newType == Array<Any>::class.java) arrayOfNulls<Any>(newLength) else java.lang.reflect.Array.newInstance(newType.getComponentType(), newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: ByteArray, newLength: Int): ByteArray {
    //        val copy = ByteArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: ShortArray, newLength: Int): ShortArray {
    //        val copy = ShortArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: IntArray, newLength: Int): IntArray {
    //        val copy = IntArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: LongArray, newLength: Int): LongArray {
    //        val copy = LongArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: CharArray, newLength: Int): CharArray {
    //        val copy = CharArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: FloatArray, newLength: Int): FloatArray {
    //        val copy = FloatArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: DoubleArray, newLength: Int): DoubleArray {
    //        val copy = DoubleArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun copyOf(original: BooleanArray, newLength: Int): BooleanArray {
    //        val copy = BooleanArray(newLength)
    //        java.lang.System.arraycopy(original, 0, copy, 0, min(original.size.toDouble(), newLength.toDouble()))
    //        return copy
    //    }
    //
    //    fun <T> copyOfRange(original: Array<T>, from: Int, to: Int): Array<T> {
    //        return copyOfRange<Any, T>(original, from, to, original.javaClass)
    //    }
    //
    //    @IntrinsicCandidate fun <T, U> copyOfRange(original: Array<U>, from: Int, to: Int, newType: java.lang.Class<out Array<T>?>): Array<T?> {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy: Array<T?> = if (newType == Array<Any>::class.java) arrayOfNulls<Any>(newLength) else java.lang.reflect.Array.newInstance(newType.getComponentType(), newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = ByteArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: ShortArray, from: Int, to: Int): ShortArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = ShortArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: IntArray, from: Int, to: Int): IntArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = IntArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: LongArray, from: Int, to: Int): LongArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = LongArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: CharArray, from: Int, to: Int): CharArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = CharArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: FloatArray, from: Int, to: Int): FloatArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = FloatArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: DoubleArray, from: Int, to: Int): DoubleArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = DoubleArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    fun copyOfRange(original: BooleanArray, from: Int, to: Int): BooleanArray {
    //        val newLength = to - from
    //        if (newLength < 0) {
    //            throw java.lang.IllegalArgumentException("$from > $to")
    //        } else {
    //            val copy = BooleanArray(newLength)
    //            java.lang.System.arraycopy(original, from, copy, 0, min((original.size - from).toDouble(), newLength.toDouble()))
    //            return copy
    //        }
    //    }
    //
    //    @java.lang.SafeVarargs fun <T> asList(vararg a: T?): List<T?> {
    //        return ArrayList<Any?>(a)
    //    }
    val LongArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                val elementHash = (element xor (element ushr 32)).i
                result = 31 * result + elementHash
            }

            return result
        }
    val IntArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0 until var3) {
                val element = var2[var4]
                result = 31 * result + element
            }

            return result
        }

    val ShortArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + element
            }

            return result
        }

    val CharArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + element.code
            }

            return result
        }

    val ByteArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + element
            }

            return result
        }

    val BooleanArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + (if (element) 1231 else 1237)
            }

            return result
        }

    val FloatArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + element.toRawBits()
            }

            return result
        }

    val DoubleArray.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                val bits: Long = element.toRawBits()
                result = 31 * result + (bits xor (bits ushr 32)).toInt()
            }

            return result
        }

    val Array<Any>.hashCode: Int
        get() {
            var result = 1
            val var2 = this
            val var3 = size

            for (var4 in 0..<var3) {
                val element = var2[var4]
                result = 31 * result + element.hashCode()
            }

            return result
        }

    //    fun deepHashCode(a: Array<Any>?): Int {
    //        if (a == null) {
    //            return 0
    //        } else {
    //            var result = 1
    //            val var2: Array<Any> = a
    //            val var3 = a.size
    //
    //            for (var4 in 0 until var3) {
    //                val element = var2[var4]
    //                var elementHash: Int
    //                if (element == null) {
    //                    elementHash = 0
    //                } else {
    //                    var cl: java.lang.Class
    //                    if ((element.javaClass.getComponentType().also { cl = it }) == null) {
    //                        elementHash = element.hashCode()
    //                    } else if (element is Array<Any>) {
    //                        elementHash = (element as Array<Any?>).contentDeepHashCode()
    //                    } else {
    //                        elementHash = primitiveArrayHashCode(element, cl)
    //                    }
    //                }
    //
    //                result = 31 * result + elementHash
    //            }
    //
    //            return result
    //        }
    //    }
    //
    //    private fun primitiveArrayHashCode(a: Any, cl: java.lang.Class<*>): Int {
    //        return if (cl == java.lang.Byte.TYPE) (a as ByteArray).contentHashCode() else (if (cl == java.lang.Integer.TYPE) (a as IntArray).contentHashCode() else (if (cl == java.lang.Long.TYPE) (a as LongArray).contentHashCode() else (if (cl == java.lang.Character.TYPE) (a as CharArray).contentHashCode() else (if (cl == java.lang.Short.TYPE) (a as ShortArray).contentHashCode() else (if (cl == java.lang.Boolean.TYPE) (a as BooleanArray).contentHashCode() else (if (cl == java.lang.Double.TYPE) (a as DoubleArray).contentHashCode() else (a as FloatArray).contentHashCode()))))))
    //    }
    //
    //    fun deepEquals(a1: Array<Any?>?, a2: Array<Any>?): Boolean {
    //        if (a1 == a2) {
    //            return true
    //        } else if (a1 != null && a2 != null) {
    //            val length = a1.size
    //            if (a2.size != length) {
    //                return false
    //            } else {
    //                for (i in 0 until length) {
    //                    val e1 = a1[i]
    //                    val e2 = a2[i]
    //                    if (e1 !== e2) {
    //                        if (e1 == null) {
    //                            return false
    //                        }
    //
    //                        val eq = deepEquals0(e1, e2)
    //                        if (!eq) {
    //                            return false
    //                        }
    //                    }
    //                }
    //
    //                return true
    //            }
    //        } else {
    //            return false
    //        }
    //    }
    //
    //    fun deepEquals0(e1: Any?, e2: Any): Boolean {
    //        assert(e1 != null)
    //        val eq = if (e1 is Array<Any> && e2 is Array<Any>) {
    //            (e1 as Array<Any?>).contentDeepEquals(e2 as Array<Any?>)
    //        } else if (e1 is ByteArray && e2 is ByteArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is ShortArray && e2 is ShortArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is IntArray && e2 is IntArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is LongArray && e2 is LongArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is CharArray && e2 is CharArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is FloatArray && e2 is FloatArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is DoubleArray && e2 is DoubleArray) {
    //            e1.contentEquals(e2)
    //        } else if (e1 is BooleanArray && e2 is BooleanArray) {
    //            e1.contentEquals(e2)
    //        } else {
    //            e1 == e2
    //        }
    //
    //        return eq
    //    }
    //
    //    fun toString(a: LongArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: IntArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: ShortArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i].toInt())
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: CharArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: ByteArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i].toInt())
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: BooleanArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: FloatArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: DoubleArray?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i])
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun toString(a: Array<Any>?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                return "[]"
    //            } else {
    //                val b: java.lang.StringBuilder = java.lang.StringBuilder()
    //                b.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    b.append(a[i].toString())
    //                    if (i == iMax) {
    //                        return b.append(']').toString()
    //                    }
    //
    //                    b.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun deepToString(a: Array<Any>?): String {
    //        if (a == null) {
    //            return "null"
    //        } else {
    //            var bufLen = 20 * a.size
    //            if (a.size != 0 && bufLen <= 0) {
    //                bufLen = Int.MAX_VALUE
    //            }
    //
    //            val buf: java.lang.StringBuilder = java.lang.StringBuilder(bufLen)
    //            deepToString(a, buf, java.util.HashSet())
    //            return buf.toString()
    //        }
    //    }
    //
    //    private fun deepToString(a: Array<Any>?, buf: java.lang.StringBuilder, dejaVu: MutableSet<Array<Any>>) {
    //        if (a == null) {
    //            buf.append("null")
    //        } else {
    //            val iMax = a.size - 1
    //            if (iMax == -1) {
    //                buf.append("[]")
    //            } else {
    //                dejaVu.add(a)
    //                buf.append('[')
    //                var i = 0
    //
    //                while (true) {
    //                    val element = a[i]
    //                    if (element == null) {
    //                        buf.append("null")
    //                    } else {
    //                        val eClass: java.lang.Class<*> = element.javaClass
    //                        if (eClass.isArray()) {
    //                            if (eClass == ByteArray::class.java) {
    //                                buf.append((element as ByteArray).contentToString())
    //                            } else if (eClass == ShortArray::class.java) {
    //                                buf.append((element as ShortArray).contentToString())
    //                            } else if (eClass == IntArray::class.java) {
    //                                buf.append((element as IntArray).contentToString())
    //                            } else if (eClass == LongArray::class.java) {
    //                                buf.append((element as LongArray).contentToString())
    //                            } else if (eClass == CharArray::class.java) {
    //                                buf.append((element as CharArray).contentToString())
    //                            } else if (eClass == FloatArray::class.java) {
    //                                buf.append((element as FloatArray).contentToString())
    //                            } else if (eClass == DoubleArray::class.java) {
    //                                buf.append((element as DoubleArray).contentToString())
    //                            } else if (eClass == BooleanArray::class.java) {
    //                                buf.append((element as BooleanArray).contentToString())
    //                            } else if (dejaVu.contains(element)) {
    //                                buf.append("[...]")
    //                            } else {
    //                                deepToString(element as Array<Any>, buf, dejaVu)
    //                            }
    //                        } else {
    //                            buf.append(element.toString())
    //                        }
    //                    }
    //
    //                    if (i == iMax) {
    //                        buf.append(']')
    //                        dejaVu.remove(a)
    //                        return
    //                    }
    //
    //                    buf.append(", ")
    //                    ++i
    //                }
    //            }
    //        }
    //    }
    //
    //    fun <T> setAll(array: Array<T>, generator: java.util.function.IntFunction<out T>) {
    //        java.util.Objects.requireNonNull(generator)
    //
    //        for (i in array.indices) {
    //            array[i] = generator.apply(i)
    //        }
    //    }
    //
    //    fun <T> parallelSetAll(array: Array<T>, generator: java.util.function.IntFunction<out T>) {
    //        java.util.Objects.requireNonNull(generator)
    //        java.util.stream.IntStream.range(0, array.size).parallel().forEach(java.util.function.IntConsumer { i: Int ->
    //            array[i] = generator.apply(i)
    //        })
    //    }
    //
    //    fun setAll(array: IntArray, generator: java.util.function.IntUnaryOperator) {
    //        java.util.Objects.requireNonNull<java.util.function.IntUnaryOperator>(generator)
    //
    //        for (i in array.indices) {
    //            array[i] = generator.applyAsInt(i)
    //        }
    //    }
    //
    //    fun parallelSetAll(array: IntArray, generator: java.util.function.IntUnaryOperator) {
    //        java.util.Objects.requireNonNull<java.util.function.IntUnaryOperator>(generator)
    //        java.util.stream.IntStream.range(0, array.size).parallel().forEach(java.util.function.IntConsumer { i: Int ->
    //            array[i] = generator.applyAsInt(i)
    //        })
    //    }
    //
    //    fun setAll(array: LongArray, generator: java.util.function.IntToLongFunction) {
    //        java.util.Objects.requireNonNull<java.util.function.IntToLongFunction>(generator)
    //
    //        for (i in array.indices) {
    //            array[i] = generator.applyAsLong(i)
    //        }
    //    }
    //
    //    fun parallelSetAll(array: LongArray, generator: java.util.function.IntToLongFunction) {
    //        java.util.Objects.requireNonNull<java.util.function.IntToLongFunction>(generator)
    //        java.util.stream.IntStream.range(0, array.size).parallel().forEach(java.util.function.IntConsumer { i: Int ->
    //            array[i] = generator.applyAsLong(i)
    //        })
    //    }
    //
    //    fun setAll(array: DoubleArray, generator: java.util.function.IntToDoubleFunction) {
    //        java.util.Objects.requireNonNull<java.util.function.IntToDoubleFunction>(generator)
    //
    //        for (i in array.indices) {
    //            array[i] = generator.applyAsDouble(i)
    //        }
    //    }
    //
    //    fun parallelSetAll(array: DoubleArray, generator: java.util.function.IntToDoubleFunction) {
    //        java.util.Objects.requireNonNull<java.util.function.IntToDoubleFunction>(generator)
    //        java.util.stream.IntStream.range(0, array.size).parallel().forEach(java.util.function.IntConsumer { i: Int ->
    //            array[i] = generator.applyAsDouble(i)
    //        })
    //    }
    //
    //    fun <T> spliterator(array: Array<T>?): java.util.Spliterator<T> {
    //        return java.util.Spliterators.spliterator<T>(array, 1040)
    //    }
    //
    //    fun <T> spliterator(array: Array<T>?, startInclusive: Int, endExclusive: Int): java.util.Spliterator<T> {
    //        return java.util.Spliterators.spliterator<T>(array, startInclusive, endExclusive, 1040)
    //    }
    //
    //    fun spliterator(array: IntArray?): java.util.Spliterator.OfInt {
    //        return java.util.Spliterators.spliterator(array, 1040)
    //    }
    //
    //    fun spliterator(array: IntArray?, startInclusive: Int, endExclusive: Int): java.util.Spliterator.OfInt {
    //        return java.util.Spliterators.spliterator(array, startInclusive, endExclusive, 1040)
    //    }
    //
    //    fun spliterator(array: LongArray?): java.util.Spliterator.OfLong {
    //        return java.util.Spliterators.spliterator(array, 1040)
    //    }
    //
    //    fun spliterator(array: LongArray?, startInclusive: Int, endExclusive: Int): java.util.Spliterator.OfLong {
    //        return java.util.Spliterators.spliterator(array, startInclusive, endExclusive, 1040)
    //    }
    //
    //    fun spliterator(array: DoubleArray?): java.util.Spliterator.OfDouble {
    //        return java.util.Spliterators.spliterator(array, 1040)
    //    }
    //
    //    fun spliterator(array: DoubleArray?, startInclusive: Int, endExclusive: Int): java.util.Spliterator.OfDouble {
    //        return java.util.Spliterators.spliterator(array, startInclusive, endExclusive, 1040)
    //    }
    //
    //    fun <T> stream(array: Array<T?>): java.util.stream.Stream<T> {
    //        return stream(array as Array<Any?>, 0, array.size)
    //    }
    //
    //    fun <T> stream(array: Array<T?>?, startInclusive: Int, endExclusive: Int): java.util.stream.Stream<T> {
    //        return java.util.stream.StreamSupport.stream<T>(spliterator<T?>(array, startInclusive, endExclusive), false)
    //    }
    //
    //    fun stream(array: IntArray): java.util.stream.IntStream {
    //        return stream(array, 0, array.size)
    //    }
    //
    //    fun stream(array: IntArray?, startInclusive: Int, endExclusive: Int): java.util.stream.IntStream {
    //        return java.util.stream.StreamSupport.intStream(spliterator(array, startInclusive, endExclusive), false)
    //    }
    //
    //    fun stream(array: LongArray): java.util.stream.LongStream {
    //        return stream(array, 0, array.size)
    //    }
    //
    //    fun stream(array: LongArray?, startInclusive: Int, endExclusive: Int): java.util.stream.LongStream {
    //        return java.util.stream.StreamSupport.longStream(spliterator(array, startInclusive, endExclusive), false)
    //    }
    //
    //    fun stream(array: DoubleArray): java.util.stream.DoubleStream {
    //        return stream(array, 0, array.size)
    //    }
    //
    //    fun stream(array: DoubleArray?, startInclusive: Int, endExclusive: Int): java.util.stream.DoubleStream {
    //        return java.util.stream.StreamSupport.doubleStream(spliterator(array, startInclusive, endExclusive), false)
    //    }
    //
    //    fun compare(a: BooleanArray?, b: BooleanArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Boolean.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: BooleanArray, aFromIndex: Int, aToIndex: Int, b: BooleanArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Boolean.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: ByteArray?, b: ByteArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Byte.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: ByteArray, aFromIndex: Int, aToIndex: Int, b: ByteArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Byte.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compareUnsigned(a: ByteArray?, b: ByteArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Byte.compareUnsigned(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compareUnsigned(a: ByteArray, aFromIndex: Int, aToIndex: Int, b: ByteArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Byte.compareUnsigned(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: ShortArray?, b: ShortArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Short.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: ShortArray, aFromIndex: Int, aToIndex: Int, b: ShortArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Short.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compareUnsigned(a: ShortArray?, b: ShortArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Short.compareUnsigned(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compareUnsigned(a: ShortArray, aFromIndex: Int, aToIndex: Int, b: ShortArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Short.compareUnsigned(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: CharArray?, b: CharArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Character.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: CharArray, aFromIndex: Int, aToIndex: Int, b: CharArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Character.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: IntArray?, b: IntArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Integer.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: IntArray, aFromIndex: Int, aToIndex: Int, b: IntArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Integer.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compareUnsigned(a: IntArray?, b: IntArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Integer.compareUnsigned(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compareUnsigned(a: IntArray, aFromIndex: Int, aToIndex: Int, b: IntArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Integer.compareUnsigned(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: LongArray?, b: LongArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Long.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: LongArray, aFromIndex: Int, aToIndex: Int, b: LongArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Long.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compareUnsigned(a: LongArray?, b: LongArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Long.compareUnsigned(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compareUnsigned(a: LongArray, aFromIndex: Int, aToIndex: Int, b: LongArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Long.compareUnsigned(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: FloatArray?, b: FloatArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Float.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: FloatArray, aFromIndex: Int, aToIndex: Int, b: FloatArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Float.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun compare(a: DoubleArray?, b: DoubleArray?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, min(a.size.toDouble(), b.size.toDouble()))
    //            return if (i >= 0) java.lang.Double.compare(a[i], b[i]) else a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun compare(a: DoubleArray, aFromIndex: Int, aToIndex: Int, b: DoubleArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, min(aLength.toDouble(), bLength.toDouble()))
    //        return if (i >= 0) java.lang.Double.compare(a[aFromIndex + i], b[bFromIndex + i]) else aLength - bLength
    //    }
    //
    //    fun <T : Comparable<T>?> compare(a: Array<T>?, b: Array<T>?): Int {
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //
    //            for (i in 0 until length) {
    //                val oa = a[i]
    //                val ob: T? = b[i]
    //                if (oa !== ob) {
    //                    if (oa == null || ob == null) {
    //                        return if (oa == null) -1 else 1
    //                    }
    //
    //                    val v = oa.compareTo(ob)
    //                    if (v != 0) {
    //                        return v
    //                    }
    //                }
    //            }
    //
    //            return a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun <T : Comparable<T>?> compare(a: Array<T>, aFromIndex: Int, aToIndex: Int, b: Array<T>, bFromIndex: Int, bToIndex: Int): Int {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //
    //        for (i in 0 until length) {
    //            val oa = a[aFromIndex++]
    //            val ob: T? = b[bFromIndex++]
    //            if (oa !== ob) {
    //                if (oa == null || ob == null) {
    //                    return if (oa == null) -1 else 1
    //                }
    //
    //                val v = oa.compareTo(ob)
    //                if (v != 0) {
    //                    return v
    //                }
    //            }
    //        }
    //
    //        return aLength - bLength
    //    }
    //
    //    fun <T> compare(a: Array<T>?, b: Array<T>?, cmp: java.util.Comparator<in T>): Int {
    //        java.util.Objects.requireNonNull(cmp)
    //        if (a == b) {
    //            return 0
    //        } else if (a != null && b != null) {
    //            val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //
    //            for (i in 0 until length) {
    //                val oa = a[i]
    //                val ob = b[i]
    //                if (oa !== ob) {
    //                    val v: Int = cmp.compare(oa, ob)
    //                    if (v != 0) {
    //                        return v
    //                    }
    //                }
    //            }
    //
    //            return a.size - b.size
    //        } else {
    //            return if (a == null) -1 else 1
    //        }
    //    }
    //
    //    fun <T> compare(a: Array<T>, aFromIndex: Int, aToIndex: Int, b: Array<T>, bFromIndex: Int, bToIndex: Int, cmp: java.util.Comparator<in T>): Int {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        java.util.Objects.requireNonNull(cmp)
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //
    //        for (i in 0 until length) {
    //            val oa = a[aFromIndex++]
    //            val ob = b[bFromIndex++]
    //            if (oa !== ob) {
    //                val v: Int = cmp.compare(oa, ob)
    //                if (v != 0) {
    //                    return v
    //                }
    //            }
    //        }
    //
    //        return aLength - bLength
    //    }
    //
    //    fun mismatch(a: BooleanArray, b: BooleanArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: BooleanArray, aFromIndex: Int, aToIndex: Int, b: BooleanArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: ByteArray, b: ByteArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: ByteArray, aFromIndex: Int, aToIndex: Int, b: ByteArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: CharArray, b: CharArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: CharArray, aFromIndex: Int, aToIndex: Int, b: CharArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: ShortArray, b: ShortArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: ShortArray, aFromIndex: Int, aToIndex: Int, b: ShortArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: IntArray, b: IntArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: IntArray, aFromIndex: Int, aToIndex: Int, b: IntArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: LongArray, b: LongArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: LongArray, aFromIndex: Int, aToIndex: Int, b: LongArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: FloatArray, b: FloatArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: FloatArray, aFromIndex: Int, aToIndex: Int, b: FloatArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: DoubleArray, b: DoubleArray): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, b, length)
    //            return if (i < 0 && a.size != b.size) length else i
    //        }
    //    }
    //
    //    fun mismatch(a: DoubleArray, aFromIndex: Int, aToIndex: Int, b: DoubleArray, bFromIndex: Int, bToIndex: Int): Int {
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //        val i: Int = jdk.internal.util.ArraysSupport.mismatch(a, aFromIndex, b, bFromIndex, length)
    //        return if (i < 0 && aLength != bLength) length else i
    //    }
    //
    //    fun mismatch(a: Array<Any>, b: Array<Any>): Int {
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            for (i in 0 until length) {
    //                if (a[i] != b[i]) {
    //                    return i
    //                }
    //            }
    //
    //            return if (a.size != b.size) length else -1
    //        }
    //    }
    //
    //    fun mismatch(a: Array<Any>, aFromIndex: Int, aToIndex: Int, b: Array<Any>, bFromIndex: Int, bToIndex: Int): Int {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //
    //        for (i in 0 until length) {
    //            if (a[aFromIndex++] != b[bFromIndex++]) {
    //                return i
    //            }
    //        }
    //
    //        return if (aLength != bLength) length else -1
    //    }
    //
    //    fun <T> mismatch(a: Array<T>, b: Array<T>, cmp: java.util.Comparator<in T>): Int {
    //        java.util.Objects.requireNonNull(cmp)
    //        val length = min(a.size.toDouble(), b.size.toDouble()).toInt()
    //        if (a == b) {
    //            return -1
    //        } else {
    //            for (i in 0 until length) {
    //                val oa = a[i]
    //                val ob = b[i]
    //                if (oa !== ob) {
    //                    val v: Int = cmp.compare(oa, ob)
    //                    if (v != 0) {
    //                        return i
    //                    }
    //                }
    //            }
    //
    //            return if (a.size != b.size) length else -1
    //        }
    //    }
    //
    //    fun <T> mismatch(a: Array<T>, aFromIndex: Int, aToIndex: Int, b: Array<T>, bFromIndex: Int, bToIndex: Int, cmp: java.util.Comparator<in T>): Int {
    //        var aFromIndex = aFromIndex
    //        var bFromIndex = bFromIndex
    //        java.util.Objects.requireNonNull(cmp)
    //        rangeCheck(a.size, aFromIndex, aToIndex)
    //        rangeCheck(b.size, bFromIndex, bToIndex)
    //        val aLength = aToIndex - aFromIndex
    //        val bLength = bToIndex - bFromIndex
    //        val length = min(aLength.toDouble(), bLength.toDouble()).toInt()
    //
    //        for (i in 0 until length) {
    //            val oa = a[aFromIndex++]
    //            val ob = b[bFromIndex++]
    //            if (oa !== ob) {
    //                val v: Int = cmp.compare(oa, ob)
    //                if (v != 0) {
    //                    return i
    //                }
    //            }
    //        }
    //
    //        return if (aLength != bLength) length else -1
    //    }
    //
    //    internal class NaturalOrder : java.util.Comparator<Any?> {
    //        override fun compare(first: Any, second: Any): Int {
    //            return (first as Comparable<*>).compareTo(second)
    //        }
    //
    //        companion object {
    //            val INSTANCE: NaturalOrder = NaturalOrder()
    //        }
    //    }
    //
    //    internal object LegacyMergeSort {
    //        val userRequested: Boolean = java.security.AccessController.doPrivileged<Boolean>(sun.security.action.GetBooleanAction("java.util.Arrays.useLegacyMergeSort"))
    //    }
    //
    //    private class ArrayList<E>(array: Array<E>?) : java.util.AbstractList<E>(), java.util.RandomAccess, java.io.Serializable {
    //        private val a: Array<E>
    //
    //        init {
    //            this.a = java.util.Objects.requireNonNull<Array<E>>(array)
    //        }
    //
    //        override fun size(): Int {
    //            return a.size
    //        }
    //
    //        override fun toArray(): Array<Any> {
    //            return copyOf<Any, E>(this.a, a.size, Array<Any>::class.java)
    //        }
    //
    //        override fun <T> toArray(a: Array<T>): Array<T> {
    //            val size = this.size
    //            if (a.size < size) {
    //                return copyOf<Any, E>(this.a, size, a.javaClass)
    //            } else {
    //                java.lang.System.arraycopy(this.a, 0, a, 0, size)
    //                if (a.size > size) {
    //                    a[size] = null
    //                }
    //
    //                return a
    //            }
    //        }
    //
    //        override fun get(index: Int): E {
    //            return a[index]
    //        }
    //
    //        override fun set(index: Int, element: E): E {
    //            val oldValue = a[index]
    //            a[index] = element
    //            return oldValue
    //        }
    //
    //        override fun indexOf(o: Any): Int {
    //            val a: Array<E?> = this.a
    //            var i: Int
    //            if (o == null) {
    //                i = 0
    //                while (i < a.size) {
    //                    if (a[i] == null) {
    //                        return i
    //                    }
    //                    ++i
    //                }
    //            } else {
    //                i = 0
    //                while (i < a.size) {
    //                    if (o == a[i]) {
    //                        return i
    //                    }
    //                    ++i
    //                }
    //            }
    //
    //            return -1
    //        }
    //
    //        override fun contains(o: Any): Boolean {
    //            return this.indexOf(o) >= 0
    //        }
    //
    //        override fun spliterator(): java.util.Spliterator<E> {
    //            return java.util.Spliterators.spliterator<E>(this.a, 16)
    //        }
    //
    //        override fun forEach(action: java.util.function.Consumer<in E>) {
    //            java.util.Objects.requireNonNull(action)
    //            val var2: Array<Any> = this.a
    //            val var3 = var2.size
    //
    //            for (var4 in 0 until var3) {
    //                val e: E = var2[var4]
    //                action.accept(e)
    //            }
    //        }
    //
    //        override fun replaceAll(operator: java.util.function.UnaryOperator<E>) {
    //            java.util.Objects.requireNonNull<java.util.function.UnaryOperator<E>>(operator)
    //            val a = this.a
    //
    //            for (i in a.indices) {
    //                a[i] = operator.apply(a[i])
    //            }
    //        }
    //
    //        override fun sort(c: java.util.Comparator<in E>) {
    //            sort(this.a, c)
    //        }
    //
    //        override fun iterator(): MutableIterator<E> {
    //            return ArrayItr<Any?>(this.a)
    //        }
    //
    //        companion object {
    //            private const val serialVersionUID = -2764017481108945198L
    //        }
    //    }
    //
    //    private class ArrayItr<E>(private val a: Array<E>) : MutableIterator<E> {
    //        private var cursor = 0
    //
    //        override fun hasNext(): Boolean {
    //            return this.cursor < a.size
    //        }
    //
    //        override fun next(): E {
    //            val i = this.cursor
    //            if (i >= a.size) {
    //                throw java.util.NoSuchElementException()
    //            } else {
    //                this.cursor = i + 1
    //                return a[i]
    //            }
    //        }
    //    }
}