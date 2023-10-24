@file:OptIn(ExperimentalAssociatedObjects::class)

import kotlinx.serialization.Serializable
import uns.us
import kotlin.reflect.AssociatedObjectKey
import kotlin.reflect.ExperimentalAssociatedObjects
import kotlin.reflect.KClass
import kotlin.reflect.findAssociatedObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame


@Serializable
class Prova {
    var i = -1
    var us = 2.us
    val uL = 10uL
}

class associatedObjects {
    @Test
    //@UseExperimental(ExperimentalAssociatedObjects::class)
    fun testBasics1() {
        val a = Prova.serializer()
        assertSame(Bar, Foo::class.findAssociatedObject<Associated1>())
        assertSame(Baz, Foo::class.findAssociatedObject<Associated2>())
        assertSame(null, Foo::class.findAssociatedObject<Associated3>())

        assertSame(null, Bar::class.findAssociatedObject<Associated1>())
    }

    @AssociatedObjectKey
    @Retention(AnnotationRetention.BINARY)
    annotation class Associated2(val kClass: KClass<*>)

    @AssociatedObjectKey
    @Retention(AnnotationRetention.BINARY)
    annotation class Associated1(val kClass: KClass<*>)

    @AssociatedObjectKey
    @Retention(AnnotationRetention.BINARY)
    annotation class Associated3(val kClass: KClass<*>)

    //
    @Associated1(Bar::class)
    @Associated2(Baz::class)
    class Foo

    object Bar
    object Baz

    @Test
    fun testGlobalOptimizations1() {
        val i1 = I1ImplHolder::class.findAssociatedObject<Associated1>()!! as I1
        assertEquals(42, i1.foo())
    }

    private interface I1 {
        fun foo(): Int
    }

    private object I1Impl : I1 {
        override fun foo() = 42
    }

    @Associated1(I1Impl::class)
    private class I1ImplHolder

    @Test
    fun testGlobalOptimizations2() {
        val i2 = I2ImplHolder()::class.findAssociatedObject<Associated1>()!! as I2
        assertEquals(17, i2.foo())
    }

    private interface I2 {
        fun foo(): Int
    }

    private object I2Impl : I2 {
        override fun foo() = 17
    }

    @Associated1(I2Impl::class)
    private class I2ImplHolder
}