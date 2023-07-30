package io.scif.api

/**
 * The interface that converts bytes to the Base64 format.
 */
interface Base64Encoder {
	fun encode(src: ByteArray): ByteArray

	fun encodeToString(src: ByteArray): String {
		val encoded = encode(src)
		return buildString(encoded.size) {
			encoded.forEach { append(it.toInt().toChar()) }
		}
	}
}

/**
 * The factory object is marked with the expect keyword in the cross-platform code.
 * For each platform, you should provide an actual implementation of the
 * Base64Factory object with the platform-specific encoder.
 * To learn more, see https://kotlinlang.org/docs/multiplatform-connect-to-apis.html
 */
expect object Base64Factory {
	fun createEncoder(): Base64Encoder
}
