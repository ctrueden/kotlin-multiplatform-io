/*
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2023 ImageJ2 developers.
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
package net.imagej.axis

import net.imagej.axis.Axes.unknown
import kotlin.jvm.JvmOverloads


/**
 * Default implementation of [LinearAxis].
 *
 * @author Barry DeZonia
 */
class DefaultLinearAxis : Variable2Axis, LinearAxis {
    /**
     * Construct a LinearAxis of specified scale. Axis type is unknown, unit is
     * null, slope is the specified scale and intercept is 0.0.
     */
    constructor(scale: Double) : this(unknown(), scale)

    /**
     * Construct a LinearAxis of specified scale and origin. Axis type is unknown,
     * unit is null, slope is specified scale and intercept is specified origin.
     */
    constructor(scale: Double, origin: Double) : this(unknown(), scale, origin)

    /**
     * Construct a LinearAxis of specified type, scale, and origin. Axis type is
     * as specified, unit is null, slope is the specified scale and intercept is
     * specified origin.
     */
    /**
     * Construct a LinearAxis of specified type. Axis type is as specified, unit
     * is null, slope is 1.0 and intercept is 0.0.
     */
    // -- constructors --
    /**
     * Construct a default LinearAxis. Axis type is unknown, unit is null, slope
     * is 1.0 and intercept is 0.0.
     */
    /**
     * Construct a LinearAxis of specified type and scale. Axis type is as
     * specified, unit is null, slope is the specified scale and intercept is 0.0.
     */
    @JvmOverloads
    constructor(type: AxisType = unknown(), scale: Double = 1.0, origin: Double = 0.0) : super(type) {
        this.scale = scale
        this.origin = origin
    }

    /**
     * Construct a LinearAxis of specified type, unit, scale, and origin. Axis
     * type is as specified, unit is as specified, slope is the specified scale
     * and intercept is the specified origin.
     */
    /**
     * Construct a LinearAxis of specified type and unit. Axis type is as
     * specified, unit is as specified, slope is 1.0 and intercept is 0.0.
     */
    /**
     * Construct a LinearAxis of specified type, unit, and scale. Axis type is as
     * specified, unit is as specified, slope is the specified scale and intercept
     * is 0.0.
     */
    @JvmOverloads
    constructor(type: AxisType, unit: String?, scale: Double = 1.0, origin: Double = 0.0) : super(type) {
        this.unit = unit
        this.scale = scale
        this.origin = origin
    }

    override var scale: Double?
        get() = b
        // -- setters/getters --
        set(value) {
            b = value
        }

    override var origin: Double?
        get() = a
        set(value) {
            a = value
        }

    // -- CalibratedAxis methods --
    override fun calibratedValue(rawValue: Double): Double = scale!! * rawValue + origin!!

    override fun rawValue(calibratedValue: Double): Double = (calibratedValue - origin!!) / scale!!

    override fun generalEquation(): String = "y = a + b*x"

    override fun copy(): DefaultLinearAxis = DefaultLinearAxis(type, unit, scale!!, origin!!)

    companion object {
        // -- static helpers --
        /**
         * Returns the slope of the line connecting two points.
         */
        fun slope(x1: Double, y1: Double, x2: Double, y2: Double): Double = (y2 - y1) / (x2 - x1)

        /**
         * Returns the y intercept of the line connecting two points.
         */
        fun intercept(x1: Double, y1: Double, x2: Double, y2: Double): Double = (y1 + y2 + (y1 - y2) * (x1 + x2) / (x2 - x1)) / 2
    }
}

/**
 * Abstract superclass for axes with two variables.
 *
 * @author Barry DeZonia
 */
abstract class Variable2Axis : Variable1Axis {
    constructor(type: AxisType) : super(type)

    constructor(type: AxisType, unit: String?, a: Double, b: Double) : super(type, unit, a) {
        this.b = b
    }

    var b: Double?
        // -- getters --
        get() = get("b")
        // -- setters --
        set(value) = set("b", b)
}

/**
 * Abstract superclass for axes with one variable.
 *
 * @author Barry DeZonia
 */
abstract class Variable1Axis : VariableAxis {
    constructor(type: AxisType) : super(type)

    constructor(type: AxisType, unit: String?, a: Double) : super(type, unit) {
        this.a = a
    }

    var a: Double?
        // -- getters --
        get() = get("a")
        // -- setters --
        set(value) = set("a", value)
}
