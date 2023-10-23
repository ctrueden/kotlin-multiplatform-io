package io.scif

import net.imglib2.Interval
//import org.scijava.convert.ConvertService
//import org.scijava.plugin.Parameter

abstract class AbstractBlock<T> : TypedBlock<T> {
//    @Parameter
//    var convertService: ConvertService? = null

//    override val interval: Interval
//        get() = convertService.convert(get(), Interval::class.java)
}