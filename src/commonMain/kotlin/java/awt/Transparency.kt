//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package java.awt

interface Transparency {
    val transparency: Int

    companion object {
        const val OPAQUE: Int = 1
        const val BITMASK: Int = 2
        const val TRANSLUCENT: Int = 3
    }
}