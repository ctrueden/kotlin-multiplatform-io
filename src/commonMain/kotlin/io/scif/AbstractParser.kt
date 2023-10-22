/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2017 SCIFIO developers.
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
package io.scif

//import io.scif.config.SCIFIOConfig
//import io.scif.util.SCIFIOMetadataTools
//import org.scijava.io.handle.DataHandle
//import org.scijava.io.handle.DataHandleService
//import org.scijava.io.location.Location
//import org.scijava.plugin.Parameter

/**
 * Abstract superclass of all SCIFIO [io.scif.Parser] implementations.
 *
 * @see io.scif.Parser
 *
 * @see io.scif.Metadata
 *
 * @see io.scif.HasFormat
 *
 * @author Mark Hiner
 * @param <M> - The Metadata type returned by this Parser.
</M> */
abstract class AbstractParser<M : TypedMetadata> : AbstractGroupable(), TypedParser<M> {
    // -- Fields --
    /** Last Metadata instance parsed by this parser.  */
    final override var metadata: M? = null
        private set

//    @Parameter
//    private val handles: DataHandleService? = null

    // -- Parser API Methods --
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(fileName: Location?): M {
//        return parse(fileName, SCIFIOConfig())
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?): M {
//        return parse(stream, SCIFIOConfig())
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(loc: Location?, meta: Metadata?): M {
//        return parse(loc, meta, SCIFIOConfig())
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(handle: DataHandle<Location?>?, meta: Metadata?): M {
//        return parse(handle, meta, SCIFIOConfig())
//    }
//
//    val source: DataHandle<Location>?
//        get() {
//            val m: M = metadata ?: return null
//            return m.getSource()
//        }
//
//    val sourceLocation: Location?
//        get() = if (metadata == null) null else metadata.getSourceLocation()
//
//    @Throws(java.io.IOException::class) fun updateSource(source: Location?) {
//        metadata.setSourceLocation(source)
//        metadata.setSource(handles.readBuffer(source))
//    }

//    override val usedFiles: Array<Any?>?
//        get() = getUsedLocations(false)
//
//    fun getUsedLocations(noPixels: Boolean): Array<Location> {
//        val files: MutableSet<Location> = java.util.HashSet<Location>()
//        for (i in 0 until metadata!!.imageCount) {
//            val s: Array<Location>? = getImageUsedFiles(i, noPixels)
//            if (s != null) {
//                for (file in s) {
//                    // Set takes care of duplicates
//                    files.add(file)
//                }
//            }
//        }
//        return files.toArray(arrayOfNulls<Location>(files.size))
//    }
//
//    fun getImageUsedFiles(imageIndex: Int): Array<Location>? {
//        return getImageUsedFiles(imageIndex, false)
//    }
//
//    fun getImageUsedFiles(imageIndex: Int,
//                          noPixels: Boolean): Array<Location>? {
//        return if (noPixels) null else arrayOf<Location>(metadata
//                                                                 .getSourceLocation())
//    }
//
//    fun getAdvancedUsedLocations(noPixels: Boolean): Array<LocationInfo?>? {
//        val files: Array<Any> = getUsedLocations(noPixels) ?: return null
//        return getLocationInfo(files)
//    }
//
//    fun getAdvancedImageUsedLocations(imageIndex: Int,
//                                      noPixels: Boolean): Array<LocationInfo?>? {
//        val files: Array<Any> = getImageUsedFiles(imageIndex, noPixels) ?: return null
//        return getLocationInfo(files)
//    }
//
//    val supportedMetadataLevels: Set<Any>
//        get() {
//            val supportedLevels: MutableSet<MetadataLevel> = java.util.HashSet<MetadataLevel>()
//            supportedLevels.add(MetadataLevel.ALL)
//            supportedLevels.add(MetadataLevel.NO_OVERLAYS)
//            supportedLevels.add(MetadataLevel.MINIMUM)
//            return supportedLevels
//        }
//
//    // -- TypedParser API Methods --
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(handle: DataHandle<Location?>?, meta: M): M {
//        return parse(handle, meta, SCIFIOConfig())
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(loc: Location, meta: M, config: SCIFIOConfig): M {
//        var handle: DataHandle<Location?>? = source
//
//        // reset / change the internal handle
//        if (handle != null) {
//            if (handle.get().equals(loc)) {
//                handle.seek(0)
//            } else {
//                close()
//                handle.close()
//                handle = null
//            }
//        }
//
//        // set basic info
//        meta!!.isFiltered = config.parserIsFiltered()
//        if (meta.getContext() == null) meta.setContext(getContext())
//        meta.datasetName = loc.getName()
//        metadata = meta
//        meta.setSourceLocation(loc)
//
//        if (handle == null) { // no source set or source changed
//            handle = handles.readBuffer(loc)
//            if (handle == null) {
//                // no handle found for this location, expected for
//                // "Location-only" formats
//                meta.populateImageMetadata()
//                return meta
//            }
//        }
//        return parse(handle, meta, config)
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(handle: DataHandle<Location>, meta: M,
//                                                                          config: SCIFIOConfig): M {
//        val `in`: DataHandle<Location>? = source
//
//        if (`in` == null || !`in`.get().equals(handle.get())) {
//            init(handle)
//
//            if (config.parserIsSaveOriginalMetadata()) {
//                // TODO store all metadata in OMEXML store..
//                // or equivalent function? as per setId.. or handle via
//                // annotations
//            }
//        }
//        // we need to set this here, because we can not know if parse(Location) was
//        // called before.
//        meta!!.isFiltered = config.parserIsFiltered()
//        if (meta.getContext() == null) meta.setContext(getContext())
//        meta.datasetName = handle.get().getName()
//        meta.setSource(handle)
//        meta.setSourceLocation(handle.get())
//        metadata = meta
//
//        typedParse(handle, meta, config)
//        meta.populateImageMetadata()
//        return meta
//    }

    // -- HasSource API Methods --
//    @Throws(java.io.IOException::class) fun close(fileOnly: Boolean) {
//        if (metadata != null) metadata.close(fileOnly)
//    }

    // -- AbstractParser Methods --
    /**
     * A helper method, called by [.parse].
     * Allows for boilerplate code to come after parsing, specifically calls to
     * [Metadata.populateImageMetadata].
     *
     *
     * This method should be implemented to populate any format-specific Metadata.
     *
     *
     *
     * NB: if a Format requires type-specific parsing to occur before the Abstract
     * layer, Override `#parse(String, TypedMetadata)` instead.
     *
     */
//    @Throws(java.io.IOException::class, FormatException::class) protected abstract fun typedParse(handle: DataHandle<Location>?, meta: M,
//                                                                                                  config: SCIFIOConfig?)

    /* Sets the input stream for this parser if provided a new stream */
//    @Throws(java.io.IOException::class) private fun init(handle: DataHandle<Location>) {
//        // Check to see if the stream is already open
//
//        if (metadata != null) {
//            val usedFiles: Array<Location> = usedFiles
//            for (fileName in usedFiles) {
//                if (handle.get().equals(fileName)) return
//            }
//        }
//
//        close()
//    }

    /* Builds a LocationInfo array around the provided array of locations*/
//    private fun getLocationInfo(locations: Array<Location>): Array<LocationInfo?> {
//        val infos: Array<LocationInfo?> = arrayOfNulls<LocationInfo>(locations.size)
//        for (i in infos.indices) {
//            infos[i] = LocationInfo()
//            infos[i].locationName = locations[i].getName()
//            infos[i].reader = getFormat().getReaderClass()
//            infos[i].usedToInitialize = locations[i].equals(sourceLocation)
//        }
//        return infos
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(loc: Location?, config: SCIFIOConfig?): M {
//        val meta = getFormat().createMetadata() as M
//        return parse(loc, meta, config)
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(stream: DataHandle<Location?>?, config: SCIFIOConfig?): M {
//        val meta = getFormat().createMetadata() as M
//        return parse(stream, meta, config)
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(loc: Location?, meta: Metadata,
//                                                                          config: SCIFIOConfig?): M {
//        return parse(loc, SCIFIOMetadataTools.< M > castMeta < M ? > meta, config)
//    }
//
//    @Throws(java.io.IOException::class, FormatException::class) fun parse(handle: DataHandle<Location?>?, meta: Metadata,
//                                                                          config: SCIFIOConfig?): M {
//        return parse(handle, SCIFIOMetadataTools.< M > castMeta < M ? > meta, config)
//    }

    /**
     * Allows implementations of this class that override
     * [.parse] to set the metadata.
     *
     * @param meta the metadata object for this parser
     */
//    protected fun setMetaData(meta: Metadata) {
//        metadata = SCIFIOMetadataTools.< M > castMeta < M ? > meta
//    }
}