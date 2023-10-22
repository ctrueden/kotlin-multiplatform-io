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
package net.imagej.space

import net.imagej.axis.*
import net.imglib2.EuclideanSpace
import net.imglib2.RealInterval
import java.util.*

/**
 * Abstract base class for [CalibratedSpace].
 *
 * @author Curtis Rueden
 */
abstract class AbstractCalibratedSpace<A : CalibratedAxis> : AbstractTypedSpace<A>, CalibratedSpace<A> {
    constructor(numDims: Int) : super(numDims)

    constructor(vararg axes: A) : super(*axes)

    constructor(axes: List<A>) : super(axes)
}

/**
 * A Euclidean space whose dimensions have units and calibrations.
 *
 * @author Curtis Rueden
 * @see CalibratedAxis
 */
interface CalibratedSpace<A : CalibratedAxis> : TypedSpace<A> {
    /**
     * Returns the average scale along the given axis, for some reasonable
     * interval.
     *
     *
     * The exact interval used is implementation dependent, but reasonable effort
     * will be made to use the largest in-bounds range for the space; e.g., for
     * [RealInterval]s, the range used is [RealInterval.realMin]
     * to [RealInterval.realMax]. For spaces in general, the default
     * range is `[0, 1]`.
     *
     */
    fun averageScale(d: Int): Double = axis(d).averageScale(0.0, 1.0)
}

/**
 * Abstract base class for [TypedSpace].
 *
 * @author Curtis Rueden
 */
abstract class AbstractTypedSpace<A : TypedAxis> : AbstractAnnotatedSpace<A>, TypedSpace<A> {
    constructor(numDims: Int) : super(numDims)

    constructor(vararg axes: A) : super(*axes)

    constructor(axes: List<A>) : super(axes)
}

/**
 * A Euclidean space with typed dimensional axes.
 *
 * @author Curtis Rueden
 * @see TypedAxis
 */
interface TypedSpace<A : TypedAxis> : AnnotatedSpace<A> {
    /**
     * Gets the dimensional index of the axis with the given type.
     *
     *
     * Note that by convention, each [AxisType] may only be used in a single
     * dimension of the space.
     *
     *
     * @return the dimensional index of the specified axis type. Return
     * `-1` if the specified axis type is not found.
     */
    fun dimensionIndex(axisType: AxisType): Int {
        for (d in dimensions)
            if (axis(d).type === axisType)
                return d
        return -1
    }
}


/**
 * Abstract base class for [AnnotatedSpace] implementations.
 *
 * @author Curtis Rueden
 */
abstract class AbstractAnnotatedSpace<A : Axis> : AnnotatedSpace<A> {
    private val axisList: MutableList<A?>

    constructor(numDims: Int) {
        // We have no way of knowing the axes to populate, so we fill with
        // nulls.
        axisList = MutableList(numDims) { null }
    }

    constructor(vararg axes: A) : this(axes.toMutableList())

    constructor(axes: List<A?>) {
        axisList = ArrayList(axes)
    }

    // -- AnnotatedSpace methods --
    override fun axis(d: Int): A = axisList[d]!!

    override fun axes(axes: Array<A>) {
        for (d in axes.indices)
            axes[d] = axis(d)
    }

    override fun setAxis(axis: A, d: Int) {
        // NB - in some cases AnnotatedSpaces have a fixed number of dimensions.
        // But some users (like ImageJ2 overlays) may not know their dimensions
        // until after initial construction. To be safe we need to allow the
        // axisList to grow as needed. BDZ Aug 14 2013
        while (axisList.size <= d)
            axisList += null
        axisList[d] = axis
    }

    // -- EuclideanSpace methods --
    override val numDimensions: Int
        get() = axisList.size
}

/**
 * A Euclidean space with associated metadata about each dimension of the space.
 * The nature of the metadata is left intentionally open-ended; at the topmost
 * level, the [Axis] interface provides no additional information about a
 * dimensional axis, but it can be extended to do so.
 *
 *
 * One potential use of the [Axis] objects is to store calibration and
 * unit information (see the [net.imagej.units] package), but any desired
 * information about the space's dimensions could conceivably be attached.
 *
 *
 * @author Curtis Rueden
 */
interface AnnotatedSpace<A : Axis> : EuclideanSpace {
    /** Gets the axis associated with the given dimension of the space.  */
    fun axis(d: Int): A

    /** Copies the space's axes into the given array.  */
    fun axes(axes: Array<A>)

    /** Sets the dimensional axis associated with the given dimension.  */
    fun setAxis(axis: A, d: Int)
}

/**
 * Default implementation of [CalibratedSpace].
 *
 * @author Curtis Rueden
 */
class DefaultCalibratedSpace : AbstractCalibratedSpace<CalibratedAxis> {
    /**
     * Constructs a new calibrated space of the given dimensionality, with default
     * [IdentityAxis] axes of unknown type ([Axes.unknown]).
     */
    constructor(numDims: Int) : super(numDims) {
        for (d in 0..<numDims)
            setAxis(IdentityAxis(), d)
    }

    /** Constructs a new calibrated space with the given axes.  */
    constructor(vararg axes: CalibratedAxis) : super(*axes)

    /** Constructs a new calibrated space with the given axes.  */
    constructor(axes: List<CalibratedAxis>) : super(axes)
}
