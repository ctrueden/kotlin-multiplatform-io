/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2015 Board of Regents of the University of
 * Wisconsin-Madison
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
package io.scif.config

import io.scif.*
import io.scif.codec.CodecOptions
import io.scif.img.ImageRegion
import io.scif.img.ImgFactoryHeuristic
import io.scif.img.Range
import io.scif.img.converters.PlaneConverter
import java.awt.image.ColorModel

/**
 * Configuration class for all SCIFIO components. Similar to a [Context],
 * this class is effectively a container for state. However, its intended scope
 * is per method call stack, and not through a complete application. If any
 * object in a call stack has behavior that can be modified through this class,
 * a complete method chain accepting `SCIFIOConfig` instances should be
 * available - even if the intermediate classes do not require configuration
 * (the need for configuration is, effectively contagious).
 *
 *
 * Note that each getter and setter method signature in this class is prefixed
 * by the component it affects.
 *
 *
 * @author Mark Hiner
 * @see Checker
 *
 * @see Parser
 *
 * @see Writer
 *
 * @see Groupable
 *
 * @see ImgOpener
 *
 * @see ImgSaver
 */
class SCIFIOConfig
/** Zero-param constructor. Creates an empty configuration. */
    () : MutableMap<String, Any> by HashMap() {

    // -- Fields --
    // Checker
    private var openDataset = true

    // Parser
    private var level: MetadataLevel? = null

    private var filterMetadata = false

    private var saveOriginalMetadata = false

    // Writer
    private var writeSequential = false

    private var model: ColorModel? = null

    private var fps = 10

    private var compression: String? = null

    private var options: CodecOptions? = null

    // Groupable
    /** Whether or not to group multi-file formats.  */
    private var group = false

    // ImgOpener
    /**
     * Access type options for opening datasets.
     *
     *  *
     * [ImgMode.ARRAY] will attempt to use [ArrayImgFactory]
     *  *
     * [ImgMode.AUTO] allows the program to decide, e.g. based on
     * available memory.
     *  *
     * [ImgMode.CELL] will attempt to use [CellImgFactory]
     *  *
     * [ImgMode.PLANAR] will attempt to use [PlanarImgFactory]
     *
     *
     * @author Mark Hiner
     */
    enum class ImgMode { ARRAY, AUTO, CELL, PLANAR }

    // If true, planarEnabled returns true. If false, cellEnabled returns true.
    // If null, both planar/cell enabled will return false.
    private var imgModes = arrayOf(ImgMode.AUTO)

    // Whether ImgOpeners should open all images
    private var openAll = false

    // Image indices
    private var range: Range = Range("0")

    // sub-region specification for opening portions of an image
    private var region: ImageRegion? = null

    // Whether or not to use a MinMaxFilter
    private var computeMinMax = false

    // Custom plane converter
    private var planeConverter: PlaneConverter? = null

    // Custom heuristic for choosing an ImgFactory
    private var imgFactoryHeuristic: ImgFactoryHeuristic? = null

    // ImgSaver
    private var writeRGB = true

    // -- Constructors --

    /**
     * Copying constructor. Returns a copy of the given SCIFIOConfig.
     *
     * @param config Configuration to copy.
     */
    constructor(config: SCIFIOConfig) : this() {
        this += config
        openDataset = config.openDataset
        level = config.level
        filterMetadata = config.filterMetadata
        saveOriginalMetadata = config.saveOriginalMetadata
        writeSequential = config.writeSequential
        model = config.model
        fps = config.fps
        compression = config.compression
        options = config.options
        group = config.group
        imgModes = config.imgModes
        range = config.range
        region = config.region
        computeMinMax = config.computeMinMax
        planeConverter = config.planeConverter
        imgFactoryHeuristic = config.imgFactoryHeuristic
        writeRGB = config.writeRGB
    }

    // -- Checker Methods --
    fun checkerSetOpen(open: Boolean): SCIFIOConfig {
        openDataset = open
        return this
    }

    fun checkerIsOpen(): Boolean = openDataset

    // -- Clonable methods --
    fun clone() = SCIFIOConfig(this)
}