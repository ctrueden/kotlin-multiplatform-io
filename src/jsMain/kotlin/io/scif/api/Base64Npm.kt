package io.scif.api

@JsModule("base-64")
@JsNonModule
external object Base64 {
	fun encode(s: String): String
}
