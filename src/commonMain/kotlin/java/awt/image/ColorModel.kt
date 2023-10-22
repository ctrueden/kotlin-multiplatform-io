//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package java.awt.image

import java.awt.Transparency

abstract class ColorModel : Transparency {
//    private val pData: Long = 0
//    var pixelSize: Int = 0
//        protected set
//    var nBits: IntArray?
//    override var transparency: Int = 3
//    var supportsAlpha: Boolean = true
//    var isAlphaPremultiplied: Boolean = false
//    var numComponents: Int = -1
//    var numColorComponents: Int = -1
//    var colorSpace: java.awt.color.ColorSpace? = java.awt.color.ColorSpace.getInstance(1000)
//    var colorSpaceType: Int = 5
//    var maxBits: Int = 0
//    var is_sRGB: Boolean = true
//    var transferType: Int = 0
//        protected set
//
//    constructor(bits: Int) {
//        this.pixelSize = bits
//        if (bits < 1) {
//            throw java.lang.IllegalArgumentException("Number of bits must be > 0")
//        } else {
//            this.numComponents = 4
//            this.numColorComponents = 3
//            this.maxBits = bits
//            this.transferType = getDefaultTransferType(bits)
//        }
//    }
//
//    protected constructor(pixel_bits: Int, bits: IntArray, cspace: java.awt.color.ColorSpace, hasAlpha: Boolean, isAlphaPremultiplied: Boolean, transparency: Int, transferType: Int) {
//        this.colorSpace = cspace
//        this.colorSpaceType = cspace.getType()
//        this.numColorComponents = cspace.getNumComponents()
//        this.numComponents = this.numColorComponents + (if (hasAlpha) 1 else 0)
//        this.supportsAlpha = hasAlpha
//        if (bits.size < this.numComponents) {
//            throw java.lang.IllegalArgumentException("Number of color/alpha components should be " + this.numComponents + " but length of bits array is " + bits.size)
//        } else if (transparency >= 1 && transparency <= 3) {
//            if (!this.supportsAlpha) {
//                this.isAlphaPremultiplied = false
//                this.transparency = 1
//            } else {
//                this.isAlphaPremultiplied = isAlphaPremultiplied
//                this.transparency = transparency
//            }
//
//            this.nBits = bits.copyOf(this.numComponents)
//            this.pixelSize = pixel_bits
//            if (pixel_bits <= 0) {
//                throw java.lang.IllegalArgumentException("Number of pixel bits must be > 0")
//            } else {
//                this.maxBits = 0
//
//                for (i in bits.indices) {
//                    if (bits[i] < 0) {
//                        throw java.lang.IllegalArgumentException("Number of bits must be >= 0")
//                    }
//
//                    if (this.maxBits < bits[i]) {
//                        this.maxBits = bits[i]
//                    }
//                }
//
//                if (this.maxBits == 0) {
//                    throw java.lang.IllegalArgumentException("There must be at least one component with > 0 pixel bits.")
//                } else {
//                    if (cspace !== java.awt.color.ColorSpace.getInstance(1000)) {
//                        this.is_sRGB = false
//                    }
//
//                    this.transferType = transferType
//                }
//            }
//        } else {
//            throw java.lang.IllegalArgumentException("Unknown transparency: $transparency")
//        }
//    }
//
//    fun hasAlpha(): Boolean {
//        return this.supportsAlpha
//    }
//
//    fun getComponentSize(componentIdx: Int): Int {
//        if (this.nBits == null) {
//            throw java.lang.NullPointerException("Number of bits array is null.")
//        } else {
//            return nBits!![componentIdx]
//        }
//    }
//
//    open val componentSize: IntArray?
//        get() = if (this.nBits != null) nBits.clone() as IntArray? else null
//
//    abstract fun getRed(var1: Int): Int
//
//    abstract fun getGreen(var1: Int): Int
//
//    abstract fun getBlue(var1: Int): Int
//
//    abstract fun getAlpha(var1: Int): Int
//
//    open fun getRGB(pixel: Int): Int {
//        return this.getAlpha(pixel) shl 24 or (this.getRed(pixel) shl 16) or (this.getGreen(pixel) shl 8) or (this.getBlue(pixel) shl 0)
//    }
//
//    open fun getRed(inData: Any): Int {
//        val pixel: Int = false.toInt()
//        val length: Int = false.toInt()
//        val pixel: Int
//        val length: Int
//        when (this.transferType) {
//            0 -> {
//                val bdata = inData as ByteArray
//                pixel = bdata[0].toInt() and 255
//                length = bdata.size
//            }
//            1 -> {
//                val sdata = inData as ShortArray
//                pixel = sdata[0].toInt() and '\uffff'.code
//                length = sdata.size
//            }
//            2 -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//            3 -> {
//                val idata = inData as IntArray
//                pixel = idata[0]
//                length = idata.size
//            }
//            else -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//        }
//        if (length == 1) {
//            return this.getRed(pixel)
//        } else {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//        }
//    }
//
//    open fun getGreen(inData: Any): Int {
//        val pixel: Int = false.toInt()
//        val length: Int = false.toInt()
//        val pixel: Int
//        val length: Int
//        when (this.transferType) {
//            0 -> {
//                val bdata = inData as ByteArray
//                pixel = bdata[0].toInt() and 255
//                length = bdata.size
//            }
//            1 -> {
//                val sdata = inData as ShortArray
//                pixel = sdata[0].toInt() and '\uffff'.code
//                length = sdata.size
//            }
//            2 -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//            3 -> {
//                val idata = inData as IntArray
//                pixel = idata[0]
//                length = idata.size
//            }
//            else -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//        }
//        if (length == 1) {
//            return this.getGreen(pixel)
//        } else {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//        }
//    }
//
//    open fun getBlue(inData: Any): Int {
//        val pixel: Int = false.toInt()
//        val length: Int = false.toInt()
//        val pixel: Int
//        val length: Int
//        when (this.transferType) {
//            0 -> {
//                val bdata = inData as ByteArray
//                pixel = bdata[0].toInt() and 255
//                length = bdata.size
//            }
//            1 -> {
//                val sdata = inData as ShortArray
//                pixel = sdata[0].toInt() and '\uffff'.code
//                length = sdata.size
//            }
//            2 -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//            3 -> {
//                val idata = inData as IntArray
//                pixel = idata[0]
//                length = idata.size
//            }
//            else -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//        }
//        if (length == 1) {
//            return this.getBlue(pixel)
//        } else {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//        }
//    }
//
//    open fun getAlpha(inData: Any): Int {
//        val pixel: Int = false.toInt()
//        val length: Int = false.toInt()
//        val pixel: Int
//        val length: Int
//        when (this.transferType) {
//            0 -> {
//                val bdata = inData as ByteArray
//                pixel = bdata[0].toInt() and 255
//                length = bdata.size
//            }
//            1 -> {
//                val sdata = inData as ShortArray
//                pixel = sdata[0].toInt() and '\uffff'.code
//                length = sdata.size
//            }
//            2 -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//            3 -> {
//                val idata = inData as IntArray
//                pixel = idata[0]
//                length = idata.size
//            }
//            else -> throw java.lang.UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType)
//        }
//        if (length == 1) {
//            return this.getAlpha(pixel)
//        } else {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//        }
//    }
//
//    open fun getRGB(inData: Any): Int {
//        return this.getAlpha(inData) shl 24 or (this.getRed(inData) shl 16) or (this.getGreen(inData) shl 8) or (this.getBlue(inData) shl 0)
//    }
//
//    open fun getDataElements(rgb: Int, pixel: Any?): Any? {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//    }
//
//    open fun getComponents(pixel: Int, components: IntArray?, offset: Int): IntArray? {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//    }
//
//    open fun getComponents(pixel: Any?, components: IntArray?, offset: Int): IntArray {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//    }
//
//    open fun getUnnormalizedComponents(normComponents: FloatArray, normOffset: Int, components: IntArray?, offset: Int): IntArray {
//        var components = components
//        if (this.colorSpace == null) {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//        } else if (this.nBits == null) {
//            throw java.lang.UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.")
//        } else if (normComponents.size - normOffset < this.numComponents) {
//            throw java.lang.IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents)
//        } else {
//            if (components == null) {
//                components = IntArray(offset + this.numComponents)
//            }
//
//            if (this.supportsAlpha && this.isAlphaPremultiplied) {
//                val normAlpha = normComponents[normOffset + this.numColorComponents]
//
//                for (i in 0 until this.numColorComponents) {
//                    components[offset + i] = (normComponents[normOffset + i] * ((1 shl nBits!![i]) - 1).toFloat() * normAlpha + 0.5f).toInt()
//                }
//
//                components[offset + this.numColorComponents] = (normAlpha * ((1 shl nBits!![numColorComponents]) - 1).toFloat() + 0.5f).toInt()
//            } else {
//                for (i in 0 until this.numComponents) {
//                    components[offset + i] = (normComponents[normOffset + i] * ((1 shl nBits!![i]) - 1).toFloat() + 0.5f).toInt()
//                }
//            }
//
//            return components
//        }
//    }
//
//    open fun getNormalizedComponents(components: IntArray, offset: Int, normComponents: FloatArray?, normOffset: Int): FloatArray? {
//        var normComponents = normComponents
//        if (this.colorSpace == null) {
//            throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//        } else if (this.nBits == null) {
//            throw java.lang.UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.")
//        } else if (components.size - offset < this.numComponents) {
//            throw java.lang.IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents)
//        } else {
//            if (normComponents == null) {
//                normComponents = FloatArray(this.numComponents + normOffset)
//            }
//
//            if (this.supportsAlpha && this.isAlphaPremultiplied) {
//                var normAlpha = components[offset + this.numColorComponents].toFloat()
//                normAlpha /= ((1 shl nBits!![numColorComponents]) - 1).toFloat()
//                var i: Int
//                if (normAlpha != 0.0f) {
//                    i = 0
//                    while (i < this.numColorComponents) {
//                        normComponents[normOffset + i] = components[offset + i].toFloat() / (normAlpha * ((1 shl nBits!![i]) - 1).toFloat())
//                        ++i
//                    }
//                } else {
//                    i = 0
//                    while (i < this.numColorComponents) {
//                        normComponents[normOffset + i] = 0.0f
//                        ++i
//                    }
//                }
//
//                normComponents[normOffset + this.numColorComponents] = normAlpha
//            } else {
//                for (i in 0 until this.numComponents) {
//                    normComponents[normOffset + i] = components[offset + i].toFloat() / ((1 shl nBits!![i]) - 1).toFloat()
//                }
//            }
//
//            return normComponents
//        }
//    }
//
//    open fun getDataElement(components: IntArray?, offset: Int): Int {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model.")
//    }
//
//    open fun getDataElements(components: IntArray?, offset: Int, obj: Any?): Any? {
//        throw java.lang.UnsupportedOperationException("This method has not been implemented for this color model.")
//    }
//
//    open fun getDataElement(normComponents: FloatArray, normOffset: Int): Int {
//        val components = this.getUnnormalizedComponents(normComponents, normOffset, null as IntArray?, 0)
//        return this.getDataElement(components, 0)
//    }
//
//    open fun getDataElements(normComponents: FloatArray, normOffset: Int, obj: Any?): Any? {
//        val components = this.getUnnormalizedComponents(normComponents, normOffset, null as IntArray?, 0)
//        return this.getDataElements(components, 0, obj)
//    }
//
//    open fun getNormalizedComponents(pixel: Any?, normComponents: FloatArray?, normOffset: Int): FloatArray? {
//        val components = this.getComponents(pixel, null as IntArray?, 0)
//        return this.getNormalizedComponents(components, 0, normComponents, normOffset)
//    }
//
//    override fun equals(obj: Any?): Boolean {
//        return super.equals(obj)
//    }
//
//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
//
//    fun getColorSpace(): java.awt.color.ColorSpace? {
//        return this.colorSpace
//    }
//
//    open fun coerceData(raster: java.awt.image.WritableRaster?, isAlphaPremultiplied: Boolean): ColorModel? {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//    }
//
//    open fun isCompatibleRaster(raster: java.awt.image.Raster?): Boolean {
//        throw java.lang.UnsupportedOperationException("This method has not been implemented for this ColorModel.")
//    }
//
//    open fun createCompatibleWritableRaster(w: Int, h: Int): java.awt.image.WritableRaster? {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//    }
//
//    open fun createCompatibleSampleModel(w: Int, h: Int): java.awt.image.SampleModel? {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//    }
//
//    open fun isCompatibleSampleModel(sm: java.awt.image.SampleModel?): Boolean {
//        throw java.lang.UnsupportedOperationException("This method is not supported by this color model")
//    }
//
//    open fun getAlphaRaster(raster: java.awt.image.WritableRaster?): java.awt.image.WritableRaster? {
//        return null
//    }
//
//    override fun toString(): String {
//        val var10000 = this.pixelSize
//        return "ColorModel: #pixelBits = " + var10000 + " numComponents = " + this.numComponents + " color space = " + colorSpace.toString() + " transparency = " + this.transparency + " has alpha = " + this.supportsAlpha + " isAlphaPre = " + this.isAlphaPremultiplied
//    }
//
//    companion object {
//        private var loaded = false
//        var rGBdefault: ColorModel? = null
//            get() {
//                if (field == null) {
//                    field = java.awt.image.DirectColorModel(32, 16711680, 65280, 255, -16777216)
//                }
//
//                return field
//            }
//            private set
//        var l8Tos8: ByteArray?
//        var s8Tol8: ByteArray?
//        var l16Tos8: ByteArray?
//        var s8Tol16: ShortArray?
//        var g8Tos8Map: MutableMap<java.awt.color.ICC_ColorSpace, ByteArray>?
//        var lg16Toog8Map: MutableMap<java.awt.color.ICC_ColorSpace, ByteArray>?
//        var g16Tos8Map: MutableMap<java.awt.color.ICC_ColorSpace, ByteArray>?
//        var lg16Toog16Map: MutableMap<java.awt.color.ICC_ColorSpace, ShortArray>?
//
//        fun loadLibraries() {
//            if (!loaded) {
//                java.security.AccessController.doPrivileged<java.lang.Void>(object : java.security.PrivilegedAction<java.lang.Void?>() {
//                    override fun run(): java.lang.Void {
//                        java.lang.System.loadLibrary("awt")
//                        return null
//                    }
//                })
//                loaded = true
//            }
//        }
//
//        private external fun initIDs()
//
//        fun getDefaultTransferType(pixel_bits: Int): Int {
//            return if (pixel_bits <= 8) {
//                0
//            } else if (pixel_bits <= 16) {
//                1
//            } else {
//                if (pixel_bits <= 32) 3 else 32
//            }
//        }
//
//        fun isLinearRGBspace(cs: java.awt.color.ColorSpace): Boolean {
//            return cs === java.awt.color.ColorSpace.getInstance(1004)
//        }
//
//        fun isLinearGRAYspace(cs: java.awt.color.ColorSpace): Boolean {
//            return cs === java.awt.color.ColorSpace.getInstance(1003)
//        }
//
//        val linearRGB8TosRGB8LUT: ByteArray?
//            get() {
//                if (l8Tos8 == null) {
//                    l8Tos8 = ByteArray(256)
//
//                    for (i in 0..255) {
//                        val input = i.toFloat() / 255.0f
//                        var output = if (input <= 0.0031308f) {
//                            input * 12.92f
//                        } else {
//                            1.055f * input.toDouble().pow(0.4166666666666667) as Float - 0.055f
//                        }
//
//                        l8Tos8!![i] = java.lang.Math.round(output * 255.0f).toByte()
//                    }
//                }
//
//                return l8Tos8
//            }
//
//        fun getsRGB8ToLinearRGB8LUT(): ByteArray? {
//            if (s8Tol8 == null) {
//                s8Tol8 = ByteArray(256)
//
//                for (i in 0..255) {
//                    val input = i.toFloat() / 255.0f
//                    var output = if (input <= 0.04045f) {
//                        input / 12.92f
//                    } else {
//                        ((input + 0.055f) / 1.055f).toDouble().pow(2.4) as Float
//                    }
//
//                    s8Tol8!![i] = java.lang.Math.round(output * 255.0f).toByte()
//                }
//            }
//
//            return s8Tol8
//        }
//
//        val linearRGB16TosRGB8LUT: ByteArray?
//            get() {
//                if (l16Tos8 == null) {
//                    l16Tos8 = ByteArray(65536)
//
//                    for (i in 0..65535) {
//                        val input = i.toFloat() / 65535.0f
//                        var output = if (input <= 0.0031308f) {
//                            input * 12.92f
//                        } else {
//                            1.055f * input.toDouble().pow(0.4166666666666667) as Float - 0.055f
//                        }
//
//                        l16Tos8!![i] = java.lang.Math.round(output * 255.0f).toByte()
//                    }
//                }
//
//                return l16Tos8
//            }
//
//        fun getsRGB8ToLinearRGB16LUT(): ShortArray? {
//            if (s8Tol16 == null) {
//                s8Tol16 = ShortArray(256)
//
//                for (i in 0..255) {
//                    val input = i.toFloat() / 255.0f
//                    var output = if (input <= 0.04045f) {
//                        input / 12.92f
//                    } else {
//                        ((input + 0.055f) / 1.055f).toDouble().pow(2.4) as Float
//                    }
//
//                    s8Tol16!![i] = java.lang.Math.round(output * 65535.0f).toShort()
//                }
//            }
//
//            return s8Tol16
//        }
//
//        fun getGray8TosRGB8LUT(grayCS: java.awt.color.ICC_ColorSpace): ByteArray? {
//            if (isLinearGRAYspace(grayCS)) {
//                return linearRGB8TosRGB8LUT
//            } else {
//                var g8Tos8LUT: ByteArray?
//                if (g8Tos8Map != null) {
//                    g8Tos8LUT = g8Tos8Map!![grayCS]
//                    if (g8Tos8LUT != null) {
//                        return g8Tos8LUT
//                    }
//                }
//
//                g8Tos8LUT = ByteArray(256)
//
//                for (i in 0..255) {
//                    g8Tos8LUT[i] = i.toByte()
//                }
//
//                val transformList: Array<sun.java2d.cmm.ColorTransform?> = arrayOfNulls<sun.java2d.cmm.ColorTransform>(2)
//                val mdl: sun.java2d.cmm.PCMM = sun.java2d.cmm.CMSManager.getModule()
//                val srgbCS: java.awt.color.ICC_ColorSpace = java.awt.color.ColorSpace.getInstance(1000) as java.awt.color.ICC_ColorSpace
//                transformList[0] = mdl.createTransform(grayCS.getProfile(), -1, 1)
//                transformList[1] = mdl.createTransform(srgbCS.getProfile(), -1, 2)
//                val t: sun.java2d.cmm.ColorTransform = mdl.createTransform(transformList)
//                val tmp: ByteArray = t.colorConvert(g8Tos8LUT, null as ByteArray?)
//                var i = 0
//
//                var j = 2
//                while (i <= 255) {
//                    g8Tos8LUT[i] = tmp[j]
//                    ++i
//                    j += 3
//                }
//
//                if (g8Tos8Map == null) {
//                    g8Tos8Map = java.util.Collections.synchronizedMap<Any, Any>(java.util.WeakHashMap(2))
//                }
//
//                g8Tos8Map!![grayCS] = g8Tos8LUT
//                return g8Tos8LUT
//            }
//        }
//
//        fun getLinearGray16ToOtherGray8LUT(grayCS: java.awt.color.ICC_ColorSpace): ByteArray {
//            if (lg16Toog8Map != null) {
//                val lg16Toog8LUT = lg16Toog8Map!![grayCS]
//                if (lg16Toog8LUT != null) {
//                    return lg16Toog8LUT
//                }
//            }
//
//            var tmp = ShortArray(65536)
//
//            for (i in 0..65535) {
//                tmp[i] = i.toShort()
//            }
//
//            val transformList: Array<sun.java2d.cmm.ColorTransform?> = arrayOfNulls<sun.java2d.cmm.ColorTransform>(2)
//            val mdl: sun.java2d.cmm.PCMM = sun.java2d.cmm.CMSManager.getModule()
//            val lgCS: java.awt.color.ICC_ColorSpace = java.awt.color.ColorSpace.getInstance(1003) as java.awt.color.ICC_ColorSpace
//            transformList[0] = mdl.createTransform(lgCS.getProfile(), -1, 1)
//            transformList[1] = mdl.createTransform(grayCS.getProfile(), -1, 2)
//            val t: sun.java2d.cmm.ColorTransform = mdl.createTransform(transformList)
//            tmp = t.colorConvert(tmp, null as ShortArray?)
//            val lg16Toog8LUT = ByteArray(65536)
//
//            for (i in 0..65535) {
//                lg16Toog8LUT[i] = ((tmp[i].toInt() and '\uffff'.code).toFloat() * 0.0038910506f + 0.5f).toInt().toByte()
//            }
//
//            if (lg16Toog8Map == null) {
//                lg16Toog8Map = java.util.Collections.synchronizedMap<Any, Any>(java.util.WeakHashMap(2))
//            }
//
//            lg16Toog8Map!![grayCS] = lg16Toog8LUT
//            return lg16Toog8LUT
//        }
//
//        fun getGray16TosRGB8LUT(grayCS: java.awt.color.ICC_ColorSpace): ByteArray? {
//            if (isLinearGRAYspace(grayCS)) {
//                return linearRGB16TosRGB8LUT
//            } else {
//                if (g16Tos8Map != null) {
//                    val g16Tos8LUT = g16Tos8Map!![grayCS]
//                    if (g16Tos8LUT != null) {
//                        return g16Tos8LUT
//                    }
//                }
//
//                var tmp = ShortArray(65536)
//
//                for (i in 0..65535) {
//                    tmp[i] = i.toShort()
//                }
//
//                val transformList: Array<sun.java2d.cmm.ColorTransform?> = arrayOfNulls<sun.java2d.cmm.ColorTransform>(2)
//                val mdl: sun.java2d.cmm.PCMM = sun.java2d.cmm.CMSManager.getModule()
//                val srgbCS: java.awt.color.ICC_ColorSpace = java.awt.color.ColorSpace.getInstance(1000) as java.awt.color.ICC_ColorSpace
//                transformList[0] = mdl.createTransform(grayCS.getProfile(), -1, 1)
//                transformList[1] = mdl.createTransform(srgbCS.getProfile(), -1, 2)
//                val t: sun.java2d.cmm.ColorTransform = mdl.createTransform(transformList)
//                tmp = t.colorConvert(tmp, null as ShortArray?)
//                val g16Tos8LUT = ByteArray(65536)
//                var i = 0
//
//                var j = 2
//                while (i <= 65535) {
//                    g16Tos8LUT[i] = ((tmp[j].toInt() and '\uffff'.code).toFloat() * 0.0038910506f + 0.5f).toInt().toByte()
//                    ++i
//                    j += 3
//                }
//
//                if (g16Tos8Map == null) {
//                    g16Tos8Map = java.util.Collections.synchronizedMap<Any, Any>(java.util.WeakHashMap(2))
//                }
//
//                g16Tos8Map!![grayCS] = g16Tos8LUT
//                return g16Tos8LUT
//            }
//        }
//
//        fun getLinearGray16ToOtherGray16LUT(grayCS: java.awt.color.ICC_ColorSpace): ShortArray {
//            var tmp: ShortArray?
//            if (lg16Toog16Map != null) {
//                tmp = lg16Toog16Map!![grayCS]
//                if (tmp != null) {
//                    return tmp
//                }
//            }
//
//            tmp = ShortArray(65536)
//
//            for (i in 0..65535) {
//                tmp[i] = i.toShort()
//            }
//
//            val transformList: Array<sun.java2d.cmm.ColorTransform?> = arrayOfNulls<sun.java2d.cmm.ColorTransform>(2)
//            val mdl: sun.java2d.cmm.PCMM = sun.java2d.cmm.CMSManager.getModule()
//            val lgCS: java.awt.color.ICC_ColorSpace = java.awt.color.ColorSpace.getInstance(1003) as java.awt.color.ICC_ColorSpace
//            transformList[0] = mdl.createTransform(lgCS.getProfile(), -1, 1)
//            transformList[1] = mdl.createTransform(grayCS.getProfile(), -1, 2)
//            val t: sun.java2d.cmm.ColorTransform = mdl.createTransform(transformList)
//            val lg16Toog16LUT: ShortArray = t.colorConvert(tmp, null as ShortArray?)
//            if (lg16Toog16Map == null) {
//                lg16Toog16Map = java.util.Collections.synchronizedMap<Any, Any>(java.util.WeakHashMap(2))
//            }
//
//            lg16Toog16Map!![grayCS] = lg16Toog16LUT
//            return lg16Toog16LUT
//        }
//
//        init {
//            loadLibraries()
//            initIDs()
//            l8Tos8 = null
//            s8Tol8 = null
//            l16Tos8 = null
//            s8Tol16 = null
//            g8Tos8Map = null
//            lg16Toog8Map = null
//            g16Tos8Map = null
//            lg16Toog16Map = null
//        }
//    }
}