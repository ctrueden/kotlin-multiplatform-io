package kotlinxInternal

internal fun String.utf8Size(startIndex: Int = 0, endIndex: Int = length): Long {
	checkBounds(length, startIndex, endIndex)

	var result = 0L
	var i = startIndex
	while (i < endIndex) {
		val c = this[i].code

		if (c < 0x80) {
			// A 7-bit character with 1 byte.
			result++
			i++
		} else if (c < 0x800) {
			// An 11-bit character with 2 bytes.
			result += 2
			i++
		} else if (c < 0xd800 || c > 0xdfff) {
			// A 16-bit character with 3 bytes.
			result += 3
			i++
		} else {
			val low = if (i + 1 < endIndex) this[i + 1].code else 0
			if (c > 0xdbff || low < 0xdc00 || low > 0xdfff) {
				// A malformed surrogate, which yields '?'.
				result++
				i++
			} else {
				// A 21-bit character with 4 bytes.
				result += 4
				i += 2
			}
		}
	}

	return result
}

internal inline fun checkBounds(size: Int, startIndex: Int, endIndex: Int) =
	checkBounds(size.toLong(), startIndex.toLong(), endIndex.toLong())

internal fun checkBounds(size: Long, startIndex: Long, endIndex: Long) {
	if (startIndex < 0 || endIndex > size)
		throw IndexOutOfBoundsException("startIndex ($startIndex) and endIndex ($endIndex) are not within the range [0..size($size))")
	if (startIndex > endIndex)
		throw IllegalArgumentException("startIndex ($startIndex) > endIndex ($endIndex)")
}