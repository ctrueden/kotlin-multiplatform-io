package io.scif

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

infix fun <T> T.`==`(other: T) = assertEquals(this, other)
infix fun ArrayList<*>.`==`(other: ArrayList<*>) = assertContentEquals(this, other)
infix fun IntArray.`==`(other: IntArray) = assertContentEquals(this, other)

