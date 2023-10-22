package io.scif

import net.imglib2.Dimensions
import net.imglib2.Interval

interface Block {
    val interval: Interval

    /** The offsets of this Plane relative to the origin image */
    val offsets: Dimensions?
}