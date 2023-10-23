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

//import net.imagej.space.AnnotatedSpace
import net.imglib2.EuclideanSpace
import kotlin.jvm.JvmOverloads

//import net.imagej.space.TypedSpace

/**
 * A dimensional axis of an [AnnotatedSpace].
 *
 *
 * This interface does not define any explicit API, but serves as an extension
 * point for annotated [EuclideanSpace]s: subtypes of `Axis` can be
 * defined to include concepts such as axis names, labels and calibrations, and
 * then assigned to the axes of an [AnnotatedSpace].
 *
 *
 * @author Curtis Rueden
 */
interface Axis

/**
 * An axis with an associated [AxisType].
 *
 * @author Curtis Rueden
 * @see TypedSpace
 */
interface TypedAxis : Axis {
    /** The type of the axis.  */
    var type: AxisType
}

/**
 * A dimensional axis type, for describing the dimensions of a
 * [TypedSpace].
 *
 * @author Curtis Rueden
 */
interface AxisType {
    val label: String
    val isXY: Boolean
    val isSpatial: Boolean
}


/**
 * IdentityAxis is a [CalibratedAxis] whose raw and calibrated values are
 * the same.
 *
 * @author Barry DeZonia
 */
class IdentityAxis
/** Constructs an IdentityAxis of the specified axis type and unit.  */
@JvmOverloads
constructor(type: AxisType = Axes.unknown(), unit: String? = null) : VariableAxis(type, unit) {
    // -- constructors --

    // -- CalibratedAxis methods --
    override fun calibratedValue(rawValue: Double): Double = rawValue

    override fun rawValue(calibratedValue: Double): Double = calibratedValue

    override fun generalEquation(): String = "y = x"

    override fun copy(): IdentityAxis = IdentityAxis(type, unit)
}

/**
 * Default implementation of [TypedAxis].
 *
 * @author Curtis Rueden
 */
open class DefaultTypedAxis
@JvmOverloads
constructor(override var type: AxisType = Axes.unknown()) : TypedAxis
