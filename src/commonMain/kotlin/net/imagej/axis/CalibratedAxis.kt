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

/**
 * An axis with an associated [AxisType], unit and calibration.
 *
 * @author Curtis Rueden
 * @author Barry DeZonia
 * @see TypedAxis
 */
interface CalibratedAxis : TypedAxis {
    /** The dimension's unit.  */
    var unit: String?

    /** Returns a calibrated value given a raw position along the axis.  */
    fun calibratedValue(rawValue: Double): Double

    /**
     * Returns a raw value given a calibrated position along the axis. Returns
     * Double.NaN if the calibrated value maps to more than one point along axis.
     */
    fun rawValue(calibratedValue: Double): Double

    /**
     * Gets the general equation representing values along this axis; for
     * instance: `y = m*x + b`.
     */
    fun generalEquation(): String

    /**
     * Gets the particular equation representing values along this axis; for
     * instance: `y = (14)*x + (4)`.
     */
    fun particularEquation(): String

    /**
     * Returns the average scale between two raw value coordinates along an axis.
     *
     *
     * In the limit this is actually the derivative at a point. For linear axes
     * this value never varies, and there is no error. For nonlinear axes this
     * returns the linear scale between the points and thus may be inaccurate.
     * Calls to this method may point out areas of code that should be generalized
     * to work with nonlinear axes.
     *
     */
    fun averageScale(rawValue1: Double, rawValue2: Double): Double

    /** Creates an exact duplicate of this axis.  */
    fun copy(): CalibratedAxis
}

/**
 * A [CalibratedAxis] that scales coordinates along the axis in a linear
 * fashion. Slope and intercept are configurable. Calibrated values calculated
 * from equation `y = a + b *x`.
 *
 * @author Barry DeZonia
 * @author Curtis Rueden
 */
interface LinearAxis : CalibratedAxis {
    var scale: Double?
    var origin: Double?
    override fun copy(): LinearAxis
}
