@Throws(IllegalArgumentException::class)
fun safeMultiply64(vararg sizes: Long): Long {
    if (sizes.isEmpty()) return 0
    var total: Long = 1
    for (size in sizes) {
        if (size < 1)
            throw IllegalArgumentException("Invalid array size: " + sizeAsProduct(*sizes))
        if (total willOverflow size)
            throw IllegalArgumentException("Array size too large: " + sizeAsProduct(*sizes))
        total *= size
    }
    return total
}



// -- Helper methods --
private fun sizeAsProduct(vararg sizes: Long): String = sizes.joinToString(" x ")

private infix fun Long.willOverflow(v: Long): Boolean = Long.MAX_VALUE / this < v