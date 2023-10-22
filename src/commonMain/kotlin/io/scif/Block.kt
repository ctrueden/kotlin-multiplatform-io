package io.scif

import net.imglib2.Dimensions
import net.imglib2.Interval

interface Block {
    val interval: Interval
}