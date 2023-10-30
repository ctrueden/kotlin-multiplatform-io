/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2023 SCIFIO developers.
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
package io.scif.formats.dicom

import io.scif.formats.dicom.DicomDictionary.table

/**
 * Data dictionary of DICOM types.
 *
 * There are literally thousands of fields defined by the DICOM specifications,
 * so this list may be incomplete.
 *
 * @author Andrea Ballaminut
 * @author Curtis Rueden
 * @author Melissa Linkert
 * @author Mark Hiner
 */
object DicomDictionary {
    private val table = HashMap<UInt, Pair<String, String>>(4000)

    /** Checks whether the given code is in the dictionary.  */
    operator fun contains(code: UInt): Boolean = code in table

    /** Gets the name for the given code.  */
    fun name(code: UInt): String? = table[code]?.first

    /** Gets the VR for the given code.  */
    fun vr(code: UInt): String? = table[code]?.second

    // -- Helper methods --
    init {
        addAttributeGroup0002()
        addAttributeGroup0008()
        addAttributeGroup0010()
        addAttributeGroup0012()
        addAttributeGroup0014()
        addAttributeGroup0018()
        addAttributeGroup0020()
        addAttributeGroup0022()
        addAttributeGroup0024()
        addAttributeGroup0028()
        addAttributeGroup0032()
        addAttributeGroup0038()
        addAttributeGroup003A()
        addAttributeGroup0042()
        addAttributeGroup0044()
        addAttributeGroup0046()
        addAttributeGroup0048()
        addAttributeGroup0050()
        addAttributeGroup0052()
        addAttributeGroup0054()
        addAttributeGroup0060()
        addAttributeGroup0062()
        addAttributeGroup0064()
        addAttributeGroup0066()
        addAttributeGroup0068()
        addAttributeGroup0070()
        addAttributeGroup0072()
        addAttributeGroup0074()
        addAttributeGroup0076()
        addAttributeGroup0078()
        addAttributeGroup0080()
        addAttributeGroup0088()
        addAttributeGroup0100()
        addAttributeGroup0400()
        addAttributeGroup2000()
        addAttributeGroup2010()
        addAttributeGroup2020()
        addAttributeGroup2030()
        addAttributeGroup2040()
        addAttributeGroup2050()
        addAttributeGroup2100()
        addAttributeGroup2110()
        addAttributeGroup2120()
        addAttributeGroup2130()
        addAttributeGroup2200()
        addAttributeGroup3002()
        addAttributeGroup3004()
        addAttributeGroup3006()
        addAttributeGroup3008()
        addAttributeGroup300A()
        addAttributeGroup300C()
        addAttributeGroup300E()
        addAttributeGroup4000()
        addAttributeGroup4008()
        addAttributeGroup4010()
        addAttributeGroup4FFE()
        addAttributeGroup5200()
        addAttributeGroup5400()
        addAttributeGroup5600()
        addAttributeGroup7FE0()
        addAttributeGroupFFFA()
    }

    /**
     * Adds attributes of group 0x0002.
     */
    private fun addAttributeGroup0002() {
        table[0x00020000u] = "File Meta Information Group Length" to "UL"
        table[0x00020001u] = "File Meta Information Version" to "OB"
        table[0x00020002u] = "Media Storage SOP Class UID" to "UI"
        table[0x00020003u] = "Media Storage SOP Instance UID" to "UI"
        table[0x00020010u] = "Transfer Syntax UID" to "UI"
        table[0x00020012u] = "Implementation Class UID" to "UI"
        table[0x00020013u] = "Implementation Version Name" to "SH"
        table[0x00020016u] = "Source Application Entity Title" to "AE"
        table[0x00020017u] = "Sending Application Entity Title" to "AE"
        table[0x00020018u] = "Receiving Application Entity Title" to "AE"
        table[0x00020100u] = "Private Information Creator UID" to "UI"
        table[0x00020102u] = "Private Information" to "OB"
    }

    /**
     * Adds attributes of group 0x0008.
     */
    private fun addAttributeGroup0008() {
        table[0x00080001u] = "Length to End" to "UL" // Retired
        table[0x00080005u] = "Specific Character Set" to "CS"
        table[0x00080006u] = "Language Code Sequence" to "SQ"
        table[0x00080008u] = "Image Type" to "CS"
        table[0x00080010u] = "Recognition Code" to "SH" // Retired
        table[0x00080012u] = "Instance Creation Date" to "DA"
        table[0x00080013u] = "Instance Creation Time" to "TM"
        table[0x00080014u] = "Instance Creator UID" to "UI"
        table[0x00080015u] = "Instance Coercion DateTime" to "DT"
        table[0x00080016u] = "SOP Class UID" to "UI"
        table[0x00080018u] = "SOP Instance UID" to "UI"
        table[0x0008001Au] = "Related General SOP Class UID" to "UI"
        table[0x0008001Bu] = "Original Specialized SOP ClassUID" to "UI"
        table[0x00080020u] = "Study Date" to "DA"
        table[0x00080021u] = "Series Date" to "DA"
        table[0x00080022u] = "Acquisition Date" to "DA"
        table[0x00080023u] = "Content Date" to "DA"
        table[0x00080024u] = "Overlay Date" to "DA" // Retired
        table[0x00080025u] = "Curve Date" to "DA" // Retired
        table[0x0008002Au] = "Acquisition DateTime" to "DT"
        table[0x00080030u] = "Study Time" to "TM"
        table[0x00080031u] = "Series Time" to "TM"
        table[0x00080032u] = "Acquisition Time" to "TM"
        table[0x00080033u] = "Content Time" to "TM"
        table[0x00080034u] = "Overlay Time" to "TM" // Retired
        table[0x00080035u] = "Curve Time" to "TM" // Retired
        table[0x00080040u] = "Data Set Type" to "US" // Retired
        table[0x00080041u] = "Data Set Subtype" to "LO" // Retired
        table[0x00080042u] = "Nuclear Medicine Series Type" to "CS" // Retired
        table[0x00080050u] = "Accession Number" to "SH"
        table[0x00080051u] = "Issuer of Accession NumberSequence" to "SQ"
        table[0x00080052u] = "Query/Retrieve Level" to "CS"
        table[0x00080053u] = "Query/Retrieve View" to "CS"
        table[0x00080054u] = "Retrieve AE Title" to "AE"
        table[0x00080056u] = "Instance Availability" to "CS"
        table[0x00080058u] = "Failed SOP Instance UID List" to "UI"
        table[0x00080060u] = "Modality" to "CS"
        table[0x00080061u] = "Modalities in Study" to "CS"
        table[0x00080062u] = "SOP Classes in Study" to "UI"
        table[0x00080064u] = "Conversion Type" to "CS"
        table[0x00080068u] = "Presentation Intent Type" to "CS"
        table[0x00080070u] = "Manufacturer" to "LO"
        table[0x00080080u] = "Institution Name" to "LO"
        table[0x00080081u] = "Institution Address" to "ST"
        table[0x00080082u] = "Institution Code Sequence" to "SQ"
        table[0x00080090u] = "Referring Physician's Name" to "PN"
        table[0x00080092u] = "Referring Physician's Address" to "ST"
        table[0x00080094u] = "Referring Physician's TelephoneNumbers" to "SH"
        table[0x00080096u] = "Referring Physician IdentificationSequence" to "SQ"
        table[0x0008009Cu] = "Consulting Physician's Name" to "PN"
        table[0x0008009Du] = "Consulting Physician IdentificationSequence" to "SQ"
        table[0x00080100u] = "Code Value" to "SH"
        table[0x00080101u] = "Extended Code Value" to "LO" // DICOS
        table[0x00080102u] = "Coding Scheme Designator" to "SH"
        table[0x00080103u] = "Coding Scheme Version" to "SH"
        table[0x00080104u] = "Code Meaning" to "LO"
        table[0x00080105u] = "Mapping Resource" to "CS"
        table[0x00080106u] = "Context Group Version" to "DT"
        table[0x00080107u] = "Context Group Local Version" to "DT"
        table[0x00080108u] = "Extended Code Meaning" to "LT" // DICOS
        table[0x0008010Bu] = "Context Group Extension Flag" to "CS"
        table[0x0008010Cu] = "Coding Scheme UID" to "UI"
        table[0x0008010Du] = "Context Group Extension CreatorUID" to "UI"
        table[0x0008010Fu] = "Context Identifier" to "CS"
        table[0x00080110u] = "Coding Scheme IdentificationSequence" to "SQ"
        table[0x00080112u] = "Coding Scheme Registry" to "LO"
        table[0x00080114u] = "Coding Scheme External ID" to "ST"
        table[0x00080115u] = "Coding Scheme Name" to "ST"
        table[0x00080116u] = "Coding Scheme ResponsibleOrganization" to "ST"
        table[0x00080117u] = "Context UID" to "UI"
        table[0x00080118u] = "Mapping Resource UID" to "UI"
        table[0x00080119u] = "Long Code Value" to "UC"
        table[0x00080120u] = "URN Code Value" to "UR"
        table[0x00080121u] = "Equivalent Code Sequence" to "SQ"
        table[0x00080201u] = "Timezone Offset From UTC" to "SH"
        table[0x00080300u] = "Private Data ElementCharacteristics Sequence" to "SQ"
        table[0x00080301u] = "Private Group Reference" to "US"
        table[0x00080302u] = "Private Creator Reference" to "LO"
        table[0x00080303u] = "Block Identifying Information Status" to "CS"
        table[0x00080304u] = "Nonidentifying Private Elements" to "US"
        table[0x00080306u] = "Identifying Private Elements" to "US"
        table[0x00080305u] = "Deidentification Action Sequence" to "SQ"
        table[0x00080307u] = "Deidentification Action" to "CS"
        table[0x00081000u] = "Network ID" to "AE" // Retired
        table[0x00081010u] = "Station Name" to "SH"
        table[0x00081030u] = "Study Description" to "LO"
        table[0x00081032u] = "Procedure Code Sequence" to "SQ"
        table[0x0008103Eu] = "Series Description" to "LO"
        table[0x0008103Fu] = "Series Description Code Sequence" to "SQ"
        table[0x00081040u] = "Institutional Department Name" to "LO"
        table[0x00081048u] = "Physician(s) of Record" to "PN"
        table[0x00081049u] = "Physician(s) of RecordIdentification Sequence" to "SQ"
        table[0x00081050u] = "Performing Physician's Name" to "PN"
        table[0x00081052u] = "Performing Physician IdentificationSequence" to "SQ"
        table[0x00081060u] = "Name of Physician(s) ReadingStudy" to "PN"
        table[0x00081062u] = "Physician(s) Reading StudyIdentification Sequence" to "SQ"
        table[0x00081070u] = "Operators' Name" to "PN"
        table[0x00081072u] = "Operator Identification Sequence" to "SQ"
        table[0x00081080u] = "Admitting Diagnoses Description" to "LO"
        table[0x00081084u] = "Admitting Diagnoses CodeSequence" to "SQ"
        table[0x00081090u] = "Manufacturer's Model Name" to "LO"
        table[0x00081100u] = "Referenced Results Sequence" to "SQ" // Retired
        table[0x00081110u] = "Referenced Study Sequence" to "SQ"
        table[0x00081111u] = "Referenced Performed ProcedureStep Sequence" to "SQ"
        table[0x00081115u] = "Referenced Series Sequence" to "SQ"
        table[0x00081120u] = "Referenced Patient Sequence" to "SQ"
        table[0x00081125u] = "Referenced Visit Sequence" to "SQ"
        table[0x00081130u] = "Referenced Overlay Sequence" to "SQ" // Retired
        table[0x00081134u] = "Referenced Stereometric InstanceSequence" to "SQ"
        table[0x0008113Au] = "Referenced Waveform Sequence" to "SQ"
        table[0x00081140u] = "Referenced Image Sequence" to "SQ"
        table[0x00081145u] = "Referenced Curve Sequence" to "SQ" // Retired
        table[0x0008114Au] = "Referenced Instance Sequence" to "SQ"
        table[0x0008114Bu] = "Referenced Real World ValueMapping Instance Sequence" to "SQ"
        table[0x00081150u] = "Referenced SOP Class UID" to "UI"
        table[0x00081155u] = "Referenced SOP Instance UID" to "UI"
        table[0x0008115Au] = "SOP Classes Supported" to "UI"
        table[0x00081160u] = "Referenced Frame Number" to "IS"
        table[0x00081161u] = "Simple Frame List" to "UL"
        table[0x00081162u] = "Calculated Frame List" to "UL"
        table[0x00081163u] = "Time Range" to "FD"
        table[0x00081164u] = "Frame Extraction Sequence" to "SQ"
        table[0x00081167u] = "Multi-frame Source SOP InstanceUID" to "UI"
        table[0x00081190u] = "Retrieve URL" to "UR"
        table[0x00081195u] = "Transaction UID" to "UI"
        table[0x00081196u] = "Warning Reason" to "US"
        table[0x00081197u] = "Failure Reason" to "US"
        table[0x00081198u] = "Failed SOP Sequence" to "SQ"
        table[0x00081199u] = "Referenced SOP Sequence" to "SQ"
        table[0x00081200u] = "Studies Containing OtherReferenced Instances Sequence" to "SQ"
        table[0x00081250u] = "Related Series Sequence" to "SQ"
        table[0x00082110u] = "Lossy Image Compression(Retired)" to "CS" // Retired
        table[0x00082111u] = "Derivation Description" to "ST"
        table[0x00082112u] = "Source Image Sequence" to "SQ"
        table[0x00082120u] = "Stage Name" to "SH"
        table[0x00082122u] = "Stage Number" to "IS"
        table[0x00082124u] = "Number of Stages" to "IS"
        table[0x00082127u] = "View Name" to "SH"
        table[0x00082128u] = "View Number" to "IS"
        table[0x00082129u] = "Number of Event Timers" to "IS"
        table[0x0008212Au] = "Number of Views in Stage" to "IS"
        table[0x00082130u] = "Event Elapsed Time(s)" to "DS"
        table[0x00082132u] = "Event Timer Name(s)" to "LO"
        table[0x00082133u] = "Event Timer Sequence" to "SQ"
        table[0x00082134u] = "Event Time Offset" to "FD"
        table[0x00082135u] = "Event Code Sequence" to "SQ"
        table[0x00082142u] = "Start Trim" to "IS"
        table[0x00082143u] = "Stop Trim" to "IS"
        table[0x00082144u] = "Recommended Display FrameRate" to "IS"
        table[0x00082200u] = "Transducer Position" to "CS" // Retired
        table[0x00082204u] = "Transducer Orientation" to "CS" // Retired
        table[0x00082208u] = "Anatomic Structure" to "CS" // Retired
        table[0x00082218u] = "Anatomic Region Sequence" to "SQ"
        table[0x00082220u] = "Anatomic Region ModifierSequence" to "SQ"
        table[0x00082228u] = "Primary Anatomic StructureSequence" to "SQ"
        table[0x00082229u] = "Anatomic Structure, Space orRegion Sequence" to "SQ"
        table[0x00082230u] = "Primary Anatomic StructureModifier Sequence" to "SQ"
        table[0x00082240u] = "Transducer Position Sequence" to "SQ" // Retired
        table[0x00082242u] = "Transducer Position ModifierSequence" to "SQ" // Retired
        table[0x00082244u] = "Transducer Orientation Sequence" to "SQ" // Retired
        table[0x00082246u] = "Transducer Orientation ModifierSequence" to "SQ" // Retired
        table[0x00082251u] = "Anatomic Structure Space OrRegion Code Sequence (Trial)" to "SQ" // Retired
        table[0x00082253u] = "Anatomic Portal Of Entrance CodeSequence (Trial)" to "SQ" // Retired
        table[0x00082255u] = "Anatomic Approach Direction CodeSequence (Trial)" to "SQ" // Retired
        table[0x00082256u] = "Anatomic Perspective Description(Trial)" to "ST" // Retired
        table[0x00082257u] = "Anatomic Perspective CodeSequence (Trial)" to "SQ" // Retired
        table[0x00082258u] = "Anatomic Location Of ExaminingInstrument Description (Trial)" to "ST" // Retired
        table[0x00082259u] = "Anatomic Location Of ExaminingInstrument Code Sequence (Trial)" to "SQ" // Retired
        table[0x0008225Au] = "Anatomic Structure Space OrRegion Modifier Code Sequence(Trial)" to "SQ" // Retired
        table[0x0008225Cu] = "On Axis Background AnatomicStructure Code Sequence (Trial)" to "SQ" // Retired
        table[0x00083001u] = "Alternate RepresentationSequence" to "SQ"
        table[0x00083010u] = "Irradiation Event UID" to "UI"
        table[0x00083011u] = "Source Irradiation Event Sequence" to "SQ"
        table[0x00083012u] = "RadiopharmaceuticalAdministration Event UID" to "UI"
        table[0x00084000u] = "Identifying Comments" to "LT" // Retired
        table[0x00089007u] = "Frame Type" to "CS"
        table[0x00089092u] = "Referenced Image EvidenceSequence" to "SQ"
        table[0x00089121u] = "Referenced Raw Data Sequence" to "SQ"
        table[0x00089123u] = "Creator-Version UID" to "UI"
        table[0x00089124u] = "Derivation Image Sequence" to "SQ"
        table[0x00089154u] = "Source Image Evidence Sequence" to "SQ"
        table[0x00089205u] = "Pixel Presentation" to "CS"
        table[0x00089206u] = "Volumetric Properties" to "CS"
        table[0x00089207u] = "Volume Based CalculationTechnique" to "CS"
        table[0x00089208u] = "Complex Image Component" to "CS"
        table[0x00089209u] = "Acquisition Contrast" to "CS"
        table[0x00089215u] = "Derivation Code Sequence" to "SQ"
        table[0x00089237u] = "Referenced Presentation StateSequence" to "SQ"
        table[0x00089410u] = "Referenced Other Plane Sequence" to "SQ"
        table[0x00089458u] = "Frame Display Sequence" to "SQ"
        table[0x00089459u] = "Recommended Display FrameRate in Float" to "FL"
        table[0x00089460u] = "Skip Frame Range Flag" to "CS"
    }

    /**
     * Adds attributes of group 0x0010.
     */
    private fun addAttributeGroup0010() {
        table[0x00100010u] = "Patient's Name" to "PN"
        table[0x00100020u] = "Patient ID" to "LO"
        table[0x00100021u] = "Issuer of Patient ID" to "LO"
        table[0x00100022u] = "Type of Patient ID" to "CS"
        table[0x00100024u] = "Issuer of Patient ID QualifiersSequence" to "SQ"
        table[0x00100030u] = "Patient's Birth Date" to "DA"
        table[0x00100032u] = "Patient's Birth Time" to "TM"
        table[0x00100040u] = "Patient's Sex" to "CS"
        table[0x00100050u] = "Patient's Insurance Plan CodeSequence" to "SQ"
        table[0x00100101u] = "Patient's Primary Language CodeSequence" to "SQ"
        table[0x00100102u] = "Patient's Primary LanguageModifier Code Sequence" to "SQ"
        table[0x00100200u] = "Quality Control Subject" to "CS"
        table[0x00100201u] = "Quality Control Subject Type CodeSequence" to "SQ"
        table[0x00101000u] = "Other Patient IDs" to "LO"
        table[0x00101001u] = "Other Patient Names" to "PN"
        table[0x00101002u] = "Other Patient IDs Sequence" to "SQ"
        table[0x00101005u] = "Patient's Birth Name" to "PN"
        table[0x00101010u] = "Patient's Age" to "AS"
        table[0x00101020u] = "Patient's Size" to "DS"
        table[0x00101021u] = "Patient's Size Code Sequence" to "SQ"
        table[0x00101030u] = "Patient's Weight" to "DS"
        table[0x00101040u] = "Patient's Address" to "LO"
        table[0x00101050u] = "Insurance Plan Identification" to "LO" // Retired
        table[0x00101060u] = "Patient's Mother's Birth Name" to "PN"
        table[0x00101080u] = "Military Rank" to "LO"
        table[0x00101081u] = "Branch of Service" to "LO"
        table[0x00101090u] = "Medical Record Locator" to "LO"
        table[0x00101100u] = "Referenced Patient PhotoSequence" to "SQ"
        table[0x00102000u] = "Medical Alerts" to "LO"
        table[0x00102110u] = "Allergies" to "LO"
        table[0x00102150u] = "Country of Residence" to "LO"
        table[0x00102152u] = "Region of Residence" to "LO"
        table[0x00102154u] = "Patient's Telephone Numbers" to "SH"
        table[0x00102155u] = "Patient's Telecom Information" to "LT"
        table[0x00102160u] = "Ethnic Group" to "SH"
        table[0x00102180u] = "Occupation" to "SH"
        table[0x001021A0u] = "Smoking Status" to "CS"
        table[0x001021B0u] = "Additional Patient History" to "LT"
        table[0x001021C0u] = "Pregnancy Status" to "US"
        table[0x001021D0u] = "Last Menstrual Date" to "DA"
        table[0x001021F0u] = "Patient's Religious Preference" to "LO"
        table[0x00102201u] = "Patient Species Description" to "LO"
        table[0x00102202u] = "Patient Species Code Sequence" to "SQ"
        table[0x00102203u] = "Patient's Sex Neutered" to "CS"
        table[0x00102210u] = "Anatomical Orientation Type" to "CS"
        table[0x00102292u] = "Patient Breed Description" to "LO"
        table[0x00102293u] = "Patient Breed Code Sequence" to "SQ"
        table[0x00102294u] = "Breed Registration Sequence" to "SQ"
        table[0x00102295u] = "Breed Registration Number" to "LO"
        table[0x00102296u] = "Breed Registry Code Sequence" to "SQ"
        table[0x00102297u] = "Responsible Person" to "PN"
        table[0x00102298u] = "Responsible Person Role" to "CS"
        table[0x00102299u] = "Responsible Organization" to "LO"
        table[0x00104000u] = "Patient Comments" to "LT"
        table[0x00109431u] = "Examined Body Thickness" to "FL"
    }

    /**
     * Adds attributes of group 0x0012.
     */
    private fun addAttributeGroup0012() {
        table[0x00120010u] = "Clinical Trial Sponsor Name" to "LO"
        table[0x00120020u] = "Clinical Trial Protocol ID" to "LO"
        table[0x00120021u] = "Clinical Trial Protocol Name" to "LO"
        table[0x00120030u] = "Clinical Trial Site ID" to "LO"
        table[0x00120031u] = "Clinical Trial Site Name" to "LO"
        table[0x00120040u] = "Clinical Trial Subject ID" to "LO"
        table[0x00120042u] = "Clinical Trial Subject Reading ID" to "LO"
        table[0x00120050u] = "Clinical Trial Time Point ID" to "LO"
        table[0x00120051u] = "Clinical Trial Time Point Description" to "ST"
        table[0x00120060u] = "Clinical Trial Coordinating CenterName" to "LO"
        table[0x00120062u] = "Patient Identity Removed" to "CS"
        table[0x00120063u] = "De-identification Method" to "LO"
        table[0x00120064u] = "De-identification Method CodeSequence" to "SQ"
        table[0x00120071u] = "Clinical Trial Series ID" to "LO"
        table[0x00120072u] = "Clinical Trial Series Description" to "LO"
        table[0x00120081u] = "Clinical Trial Protocol EthicsCommittee Name" to "LO"
        table[0x00120082u] = "Clinical Trial Protocol EthicsCommittee Approval Number" to "LO"
        table[0x00120083u] = "Consent for Clinical Trial UseSequence" to "SQ"
        table[0x00120084u] = "Distribution Type" to "CS"
        table[0x00120085u] = "Consent for Distribution Flag" to "CS"
    }

    /**
     * Adds attributes of group 0x0014.
     */
    private fun addAttributeGroup0014() {
        table[0x00140023u] = "CAD File Format" to "ST" // Retired
        table[0x00140024u] = "Component Reference System" to "ST" // Retired
        table[0x00140025u] = "Component ManufacturingProcedure" to "ST" // DICONDE
        table[0x00140028u] = "Component Manufacturer" to "ST" // DICONDE
        table[0x00140030u] = "Material Thickness" to "DS" // DICONDE
        table[0x00140032u] = "Material Pipe Diameter" to "DS" // DICONDE
        table[0x00140034u] = "Material Isolation Diameter" to "DS" // DICONDE
        table[0x00140042u] = "Material Grade" to "ST" // DICONDE
        table[0x00140044u] = "Material Properties Description" to "ST" // DICONDE
        table[0x00140045u] = "Material Properties File Format(Retired)" to "ST" // Retired
        table[0x00140046u] = "Material Notes" to "LT" // DICONDE
        table[0x00140050u] = "Component Shape" to "CS" // DICONDE
        table[0x00140052u] = "Curvature Type" to "CS" // DICONDE
        table[0x00140054u] = "Outer Diameter" to "DS" // DICONDE
        table[0x00140056u] = "Inner Diameter" to "DS" // DICONDE
        table[0x00141010u] = "Actual Environmental Conditions" to "ST" // DICONDE
        table[0x00141020u] = "Expiry Date" to "DA" // DICONDE
        table[0x00141040u] = "Environmental Conditions" to "ST" // DICONDE
        table[0x00142002u] = "Evaluator Sequence" to "SQ" // DICONDE
        table[0x00142004u] = "Evaluator Number" to "IS" // DICONDE
        table[0x00142006u] = "Evaluator Name" to "PN" // DICONDE
        table[0x00142008u] = "Evaluation Attempt" to "IS" // DICONDE
        table[0x00142012u] = "Indication Sequence" to "SQ" // DICONDE
        table[0x00142014u] = "Indication Number" to "IS" // DICONDE
        table[0x00142016u] = "Indication Label" to "SH" // DICONDE
        table[0x00142018u] = "Indication Description" to "ST" // DICONDE
        table[0x0014201Au] = "Indication Type" to "CS" // DICONDE
        table[0x0014201Cu] = "Indication Disposition" to "CS" // DICONDE
        table[0x0014201Eu] = "Indication ROI Sequence" to "SQ" // DICONDE
        table[0x00142030u] = "Indication Physical PropertySequence" to "SQ" // DICONDE
        table[0x00142032u] = "Property Label" to "SH" // DICONDE
        table[0x00142202u] = "Coordinate System Number ofAxes" to "IS" // DICONDE
        table[0x00142204u] = "Coordinate System Axes Sequence" to "SQ" // DICONDE
        table[0x00142206u] = "Coordinate System AxisDescription" to "ST" // DICONDE
        table[0x00142208u] = "Coordinate System Data SetMapping" to "CS" // DICONDE
        table[0x0014220Au] = "Coordinate System Axis Number" to "IS" // DICONDE
        table[0x0014220Cu] = "Coordinate System Axis Type" to "CS" // DICONDE
        table[0x0014220Eu] = "Coordinate System Axis Units" to "CS" // DICONDE
        table[0x00142210u] = "Coordinate System Axis Values" to "OB" // DICONDE
        table[0x00142220u] = "Coordinate System TransformSequence" to "SQ" // DICONDE
        table[0x00142222u] = "Transform Description" to "ST" // DICONDE
        table[0x00142224u] = "Transform Number of Axes" to "IS" // DICONDE
        table[0x00142226u] = "Transform Order of Axes" to "IS" // DICONDE
        table[0x00142228u] = "Transformed Axis Units" to "CS" // DICONDE
        table[0x0014222Au] = "Coordinate System TransformRotation and Scale Matrix" to "DS" // DICONDE
        table[0x0014222Cu] = "Coordinate System TransformTranslation Matrix" to "DS" // DICONDE
        table[0x00143011u] = "Internal Detector Frame Time" to "DS" // DICONDE
        table[0x00143012u] = "Number of Frames Integrated" to "DS" // DICONDE
        table[0x00143020u] = "Detector Temperature Sequence" to "SQ" // DICONDE
        table[0x00143022u] = "Sensor Name" to "ST" // DICONDE
        table[0x00143024u] = "Horizontal Offset of Sensor" to "DS" // DICONDE
        table[0x00143026u] = "Vertical Offset of Sensor" to "DS" // DICONDE
        table[0x00143028u] = "Sensor Temperature" to "DS" // DICONDE
        table[0x00143040u] = "Dark Current Sequence" to "SQ" // DICONDE
        // add(0x00143050, "Dark Current Counts", "OB or OW"); //DICONDE
        table[0x00143060u] = "Gain Correction ReferenceSequence" to "SQ" // DICONDE
        // add(0x00143070, "Air Counts", "OB or OW"); //DICONDE
        table[0x00143071u] = "KV Used in Gain Calibration" to "DS" // DICONDE
        table[0x00143072u] = "MA Used in Gain Calibration" to "DS" // DICONDE
        table[0x00143073u] = "Number of Frames Used forIntegration" to "DS" // DICONDE
        table[0x00143074u] = "Filter Material Used in GainCalibration" to "LO" // DICONDE
        table[0x00143075u] = "Filter Thickness Used in GainCalibration" to "DS" // DICONDE
        table[0x00143076u] = "Date of Gain Calibration" to "DA" // DICONDE
        table[0x00143077u] = "Time of Gain Calibration" to "TM" // DICONDE
        table[0x00143080u] = "Bad Pixel Image" to "OB" // DICONDE
        table[0x00143099u] = "Calibration Notes" to "LT" // DICONDE
        table[0x00144002u] = "Pulser Equipment Sequence" to "SQ" // DICONDE
        table[0x00144004u] = "Pulser Type" to "CS" // DICONDE
        table[0x00144006u] = "Pulser Notes" to "LT" // DICONDE
        table[0x00144008u] = "Receiver Equipment Sequence" to "SQ" // DICONDE
        table[0x0014400Au] = "Amplifier Type" to "CS" // DICONDE
        table[0x0014400Cu] = "Receiver Notes" to "LT" // DICONDE
        table[0x0014400Eu] = "Pre-Amplifier Equipment Sequence" to "SQ" // DICONDE
        table[0x0014400Fu] = "Pre-Amplifier Notes" to "LT" // DICONDE
        table[0x00144010u] = "Transmit Transducer Sequence" to "SQ" // DICONDE
        table[0x00144011u] = "Receive Transducer Sequence" to "SQ" // DICONDE
        table[0x00144012u] = "Number of Elements" to "US" // DICONDE
        table[0x00144013u] = "Element Shape" to "CS" // DICONDE
        table[0x00144014u] = "Element Dimension A" to "DS" // DICONDE
        table[0x00144015u] = "Element Dimension B" to "DS" // DICONDE
        table[0x00144016u] = "Element Pitch A" to "DS" // DICONDE
        table[0x00144017u] = "Measured Beam Dimension A" to "DS" // DICONDE
        table[0x00144018u] = "Measured Beam Dimension B" to "DS" // DICONDE
        table[0x00144019u] = "Location of Measured BeamDiameter" to "DS" // DICONDE
        table[0x0014401Au] = "Nominal Frequency" to "DS" // DICONDE
        table[0x0014401Bu] = "Measured Center Frequency" to "DS" // DICONDE
        table[0x0014401Cu] = "Measured Bandwidth" to "DS" // DICONDE
        table[0x0014401Du] = "Element Pitch B" to "DS" // DICONDE
        table[0x00144020u] = "Pulser Settings Sequence" to "SQ" // DICONDE
        table[0x00144022u] = "Pulse Width" to "DS" // DICONDE
        table[0x00144024u] = "Excitation Frequency" to "DS" // DICONDE
        table[0x00144026u] = "Modulation Type" to "CS" // DICONDE
        table[0x00144028u] = "Damping" to "DS" // DICONDE
        table[0x00144030u] = "Receiver Settings Sequence" to "SQ" // DICONDE
        table[0x00144031u] = "Acquired Soundpath Length" to "DS" // DICONDE
        table[0x00144032u] = "Acquisition Compression Type" to "CS" // DICONDE
        table[0x00144033u] = "Acquisition Sample Size" to "IS" // DICONDE
        table[0x00144034u] = "Rectifier Smoothing" to "DS" // DICONDE
        table[0x00144035u] = "DAC Sequence" to "SQ" // DICONDE
        table[0x00144036u] = "DAC Type" to "CS" // DICONDE
        table[0x00144038u] = "DAC Gain Points" to "DS" // DICONDE
        table[0x0014403Au] = "DAC Time Points" to "DS" // DICONDE
        table[0x0014403Cu] = "DAC Amplitude" to "DS" // DICONDE
        table[0x00144040u] = "Pre-Amplifier Settings Sequence" to "SQ" // DICONDE
        table[0x00144050u] = "Transmit Transducer SettingsSequence" to "SQ" // DICONDE
        table[0x00144051u] = "Receive Transducer SettingsSequence" to "SQ" // DICONDE
        table[0x00144052u] = "Incident Angle" to "DS" // DICONDE
        table[0x00144054u] = "Coupling Technique" to "ST" // DICONDE
        table[0x00144056u] = "Coupling Medium" to "ST" // DICONDE
        table[0x00144057u] = "Coupling Velocity" to "DS" // DICONDE
        table[0x00144058u] = "Probe Center Location X" to "DS" // DICONDE
        table[0x00144059u] = "Probe Center Location Z" to "DS" // DICONDE
        table[0x0014405Au] = "Sound Path Length" to "DS" // DICONDE
        table[0x0014405Cu] = "Delay Law Identifier" to "ST" // DICONDE
        table[0x00144060u] = "Gate Settings Sequence" to "SQ" // DICONDE
        table[0x00144062u] = "Gate Threshold" to "DS" // DICONDE
        table[0x00144064u] = "Velocity of Sound" to "DS" // DICONDE
        table[0x00144070u] = "Calibration Settings Sequence" to "SQ" // DICONDE
        table[0x00144072u] = "Calibration Procedure" to "ST" // DICONDE
        table[0x00144074u] = "Procedure Version" to "SH" // DICONDE
        table[0x00144076u] = "Procedure Creation Date" to "DA" // DICONDE
        table[0x00144078u] = "Procedure Expiration Date" to "DA" // DICONDE
        table[0x0014407Au] = "Procedure Last Modified Date" to "DA" // DICONDE
        table[0x0014407Cu] = "Calibration Time" to "TM" // DICONDE
        table[0x0014407Eu] = "Calibration Date" to "DA" // DICONDE
        table[0x00144080u] = "Probe Drive Equipment Sequence" to "SQ" // DICONDE
        table[0x00144081u] = "Drive Type" to "CS" // DICONDE
        table[0x00144082u] = "Probe Drive Notes" to "LT" // DICONDE
        table[0x00144083u] = "Drive Probe Sequence" to "SQ" // DICONDE
        table[0x00144084u] = "Probe Inductance" to "DS" // DICONDE
        table[0x00144085u] = "Probe Resistance" to "DS" // DICONDE
        table[0x00144086u] = "Receive Probe Sequence" to "SQ" // DICONDE
        table[0x00144087u] = "Probe Drive Settings Sequence" to "SQ" // DICONDE
        table[0x00144088u] = "Bridge Resistors" to "DS" // DICONDE
        table[0x00144089u] = "Probe Orientation Angle" to "DS" // DICONDE
        table[0x0014408Bu] = "User Selected Gain Y" to "DS" // DICONDE
        table[0x0014408Cu] = "User Selected Phase" to "DS" // DICONDE
        table[0x0014408Du] = "User Selected Offset X" to "DS" // DICONDE
        table[0x0014408Eu] = "User Selected Offset Y" to "DS" // DICONDE
        table[0x00144091u] = "Channel Settings Sequence" to "SQ" // DICONDE
        table[0x00144092u] = "Channel Threshold" to "DS" // DICONDE
        table[0x0014409Au] = "Scanner Settings Sequence" to "SQ" // DICONDE
        table[0x0014409Bu] = "Scan Procedure" to "ST" // DICONDE
        table[0x0014409Cu] = "Translation Rate X" to "DS" // DICONDE
        table[0x0014409Du] = "Translation Rate Y" to "DS" // DICONDE
        table[0x0014409Fu] = "Channel Overlap" to "DS" // DICONDE
        table[0x001440A0u] = "Image Quality Indicator Type" to "LO" // DICONDE
        table[0x001440A1u] = "Image Quality Indicator Material" to "LO" // DICONDE
        table[0x001440A2u] = "Image Quality Indicator Size" to "LO" // DICONDE
        table[0x00145002u] = "LINAC Energy" to "IS" // DICONDE
        table[0x00145004u] = "LINAC Output" to "IS" // DICONDE
        table[0x00145100u] = "Active Aperture" to "US" // DICONDE
        table[0x00145101u] = "Total Aperture" to "DS" // DICONDE
        table[0x00145102u] = "Aperture Elevation" to "DS" // DICONDE
        table[0x00145103u] = "Main Lobe Angle" to "DS" // DICONDE
        table[0x00145104u] = "Main Roof Angle" to "DS" // DICONDE
        table[0x00145105u] = "Connector Type" to "CS" // DICONDE
        table[0x00145106u] = "Wedge Model Number" to "SH" // DICONDE
        table[0x00145107u] = "Wedge Angle Float" to "DS" // DICONDE
        table[0x00145108u] = "Wedge Roof Angle" to "DS" // DICONDE
        table[0x00145109u] = "Wedge Element 1 Position" to "CS" // DICONDE
        table[0x0014510Au] = "Wedge Material Velocity" to "DS" // DICONDE
        table[0x0014510Bu] = "Wedge Material" to "SH" // DICONDE
        table[0x0014510Cu] = "Wedge Offset Z" to "DS" // DICONDE
        table[0x0014510Du] = "Wedge Origin Offset X" to "DS" // DICONDE
        table[0x0014510Eu] = "Wedge Time Delay" to "DS" // DICONDE
        table[0x0014510Fu] = "Wedge Name" to "SH" // DICONDE
        table[0x00145110u] = "Wedge Manufacturer Name" to "SH" // DICONDE
        table[0x00145111u] = "Wedge Description" to "LO" // DICONDE
        table[0x00145112u] = "Nominal Beam Angle" to "DS" // DICONDE
        table[0x00145113u] = "Wedge Offset X" to "DS" // DICONDE
        table[0x00145114u] = "Wedge Offset Y" to "DS" // DICONDE
        table[0x00145115u] = "Wedge Total Length" to "DS" // DICONDE
        table[0x00145116u] = "Wedge In Contact Length" to "DS" // DICONDE
        table[0x00145117u] = "Wedge Front Gap" to "DS" // DICONDE
        table[0x00145118u] = "Wedge Total Height" to "DS" // DICONDE
        table[0x00145119u] = "Wedge Front Height" to "DS" // DICONDE
        table[0x0014511Au] = "Wedge Rear Height" to "DS" // DICONDE
        table[0x0014511Bu] = "Wedge Total Width" to "DS" // DICONDE
        table[0x0014511Cu] = "Wedge In Contact Width" to "DS" // DICONDE
        table[0x0014511Du] = "Wedge Chamfer Height" to "DS" // DICONDE
        table[0x0014511Eu] = "Wedge Curve" to "CS" // DICONDE
        table[0x0014511Fu] = "Radius Along the Wedge" to "DS" // DICONDE
    }

    /**
     * Adds attributes of group 0x0018.
     */
    private fun addAttributeGroup0018() {
        table[0x00180010u] = "Contrast/Bolus Agent" to "LO"
        table[0x00180012u] = "Contrast/Bolus Agent Sequence" to "SQ"
        table[0x00180013u] = "Contrast/Bolus T1 Relaxivity" to "FL"
        table[0x00180014u] = "Contrast/Bolus AdministrationRoute Sequence" to "SQ"
        table[0x00180015u] = "Body Part Examined" to "CS"
        table[0x00180020u] = "Scanning Sequence" to "CS"
        table[0x00180021u] = "Sequence Variant" to "CS"
        table[0x00180022u] = "Scan Options" to "CS"
        table[0x00180023u] = "MR Acquisition Type" to "CS"
        table[0x00180024u] = "Sequence Name" to "SH"
        table[0x00180025u] = "Angio Flag" to "CS"
        table[0x00180026u] = "Intervention Drug InformationSequence" to "SQ"
        table[0x00180027u] = "Intervention Drug Stop Time" to "TM"
        table[0x00180028u] = "Intervention Drug Dose" to "DS"
        table[0x00180029u] = "Intervention Drug Code Sequence" to "SQ"
        table[0x0018002Au] = "Additional Drug Sequence" to "SQ"
        table[0x00180030u] = "Radionuclide" to "LO" // Retired
        table[0x00180031u] = "Radiopharmaceutical" to "LO"
        table[0x00180032u] = "Energy Window Centerline" to "DS" // Retired
        table[0x00180033u] = "Energy Window Total Width" to "DS" // Retired
        table[0x00180034u] = "Intervention Drug Name" to "LO"
        table[0x00180035u] = "Intervention Drug Start Time" to "TM"
        table[0x00180036u] = "Intervention Sequence" to "SQ"
        table[0x00180037u] = "Therapy Type" to "CS" // Retired
        table[0x00180038u] = "Intervention Status" to "CS"
        table[0x00180039u] = "Therapy Description" to "CS" // Retired
        table[0x0018003Au] = "Intervention Description" to "ST"
        table[0x00180040u] = "Cine Rate" to "IS"
        table[0x00180042u] = "Initial Cine Run State" to "CS"
        table[0x00180050u] = "Slice Thickness" to "DS"
        table[0x00180060u] = "KVP" to "DS"
        table[0x00180070u] = "Counts Accumulated" to "IS"
        table[0x00180071u] = "Acquisition Termination Condition" to "CS"
        table[0x00180072u] = "Effective Duration" to "DS"
        table[0x00180073u] = "Acquisition Start Condition" to "CS"
        table[0x00180074u] = "Acquisition Start Condition Data" to "IS"
        table[0x00180075u] = "Acquisition Termination ConditionData" to "IS"
        table[0x00180080u] = "Repetition Time" to "DS"
        table[0x00180081u] = "Echo Time" to "DS"
        table[0x00180082u] = "Inversion Time" to "DS"
        table[0x00180083u] = "Number of Averages" to "DS"
        table[0x00180084u] = "Imaging Frequency" to "DS"
        table[0x00180085u] = "Imaged Nucleus" to "SH"
        table[0x00180086u] = "Echo Number(s)" to "IS"
        table[0x00180087u] = "Magnetic Field Strength" to "DS"
        table[0x00180088u] = "Spacing Between Slices" to "DS"
        table[0x00180089u] = "Number of Phase Encoding Steps" to "IS"
        table[0x00180090u] = "Data Collection Diameter" to "DS"
        table[0x00180091u] = "Echo Train Length" to "IS"
        table[0x00180093u] = "Percent Sampling" to "DS"
        table[0x00180094u] = "Percent Phase Field of View" to "DS"
        table[0x00180095u] = "Pixel Bandwidth" to "DS"
        table[0x00181000u] = "Device Serial Number" to "LO"
        table[0x00181002u] = "Device UID" to "UI"
        table[0x00181003u] = "Device ID" to "LO"
        table[0x00181004u] = "Plate ID" to "LO"
        table[0x00181005u] = "Generator ID" to "LO"
        table[0x00181006u] = "Grid ID" to "LO"
        table[0x00181007u] = "Cassette ID" to "LO"
        table[0x00181008u] = "Gantry ID" to "LO"
        table[0x00181010u] = "Secondary Capture Device ID" to "LO"
        table[0x00181011u] = "Hardcopy Creation Device ID" to "LO" // Retired
        table[0x00181012u] = "Date of Secondary Capture" to "DA"
        table[0x00181014u] = "Time of Secondary Capture" to "TM"
        table[0x00181016u] = "Secondary Capture DeviceManufacturer" to "LO"
        table[0x00181017u] = "Hardcopy Device Manufacturer" to "LO" // Retired
        table[0x00181018u] = "Secondary Capture DeviceManufacturer's Model Name" to "LO"
        table[0x00181019u] = "Secondary Capture DeviceSoftware Versions" to "LO"
        table[0x0018101Au] = "Hardcopy Device Software Version" to "LO" // Retired
        table[0x0018101Bu] = "Hardcopy Device Manufacturer'sModel Name" to "LO" // Retired
        table[0x00181020u] = "Software Version(s)" to "LO"
        table[0x00181022u] = "Video Image Format Acquired" to "SH"
        table[0x00181023u] = "Digital Image Format Acquired" to "LO"
        table[0x00181030u] = "Protocol Name" to "LO"
        table[0x00181040u] = "Contrast/Bolus Route" to "LO"
        table[0x00181041u] = "Contrast/Bolus Volume" to "DS"
        table[0x00181042u] = "Contrast/Bolus Start Time" to "TM"
        table[0x00181043u] = "Contrast/Bolus Stop Time" to "TM"
        table[0x00181044u] = "Contrast/Bolus Total Dose" to "DS"
        table[0x00181045u] = "Syringe Counts" to "IS"
        table[0x00181046u] = "Contrast Flow Rate" to "DS"
        table[0x00181047u] = "Contrast Flow Duration" to "DS"
        table[0x00181048u] = "Contrast/Bolus Ingredient" to "CS"
        table[0x00181049u] = "Contrast/Bolus IngredientConcentration" to "DS"
        table[0x00181050u] = "Spatial Resolution" to "DS"
        table[0x00181060u] = "Trigger Time" to "DS"
        // add(0x00181061, "Trigger Source or Type", "LO");
        table[0x00181062u] = "Nominal Interval" to "IS"
        table[0x00181063u] = "Frame Time" to "DS"
        table[0x00181064u] = "Cardiac Framing Type" to "LO"
        table[0x00181065u] = "Frame Time Vector" to "DS"
        table[0x00181066u] = "Frame Delay" to "DS"
        table[0x00181067u] = "Image Trigger Delay" to "DS"
        table[0x00181068u] = "Multiplex Group Time Offset" to "DS"
        table[0x00181069u] = "Trigger Time Offset" to "DS"
        table[0x0018106Au] = "Synchronization Trigger" to "CS"
        table[0x0018106Cu] = "Synchronization Channel" to "US"
        table[0x0018106Eu] = "Trigger Sample Position" to "UL"
        table[0x00181070u] = "Radiopharmaceutical Route" to "LO"
        table[0x00181071u] = "Radiopharmaceutical Volume" to "DS"
        table[0x00181072u] = "Radiopharmaceutical Start Time" to "TM"
        table[0x00181073u] = "Radiopharmaceutical Stop Time" to "TM"
        table[0x00181074u] = "Radionuclide Total Dose" to "DS"
        table[0x00181075u] = "Radionuclide Half Life" to "DS"
        table[0x00181076u] = "Radionuclide Positron Fraction" to "DS"
        table[0x00181077u] = "Radiopharmaceutical SpecificActivity" to "DS"
        table[0x00181078u] = "Radiopharmaceutical StartDateTime" to "DT"
        table[0x00181079u] = "Radiopharmaceutical StopDateTime" to "DT"
        table[0x00181080u] = "Beat Rejection Flag" to "CS"
        table[0x00181081u] = "Low R-R Value" to "IS"
        table[0x00181082u] = "High R-R Value" to "IS"
        table[0x00181083u] = "Intervals Acquired" to "IS"
        table[0x00181084u] = "Intervals Rejected" to "IS"
        table[0x00181085u] = "PVC Rejection" to "LO"
        table[0x00181086u] = "Skip Beats" to "IS"
        table[0x00181088u] = "Heart Rate" to "IS"
        table[0x00181090u] = "Cardiac Number of Images" to "IS"
        table[0x00181094u] = "Trigger Window" to "IS"
        table[0x00181100u] = "Reconstruction Diameter" to "DS"
        table[0x00181110u] = "Distance Source to Detector" to "DS"
        table[0x00181111u] = "Distance Source to Patient" to "DS"
        table[0x00181114u] = "Estimated RadiographicMagnification Factor" to "DS"
        table[0x00181120u] = "Gantry/Detector Tilt" to "DS"
        table[0x00181121u] = "Gantry/Detector Slew" to "DS"
        table[0x00181130u] = "Table Height" to "DS"
        table[0x00181131u] = "Table Traverse" to "DS"
        table[0x00181134u] = "Table Motion" to "CS"
        table[0x00181135u] = "Table Vertical Increment" to "DS"
        table[0x00181136u] = "Table Lateral Increment" to "DS"
        table[0x00181137u] = "Table Longitudinal Increment" to "DS"
        table[0x00181138u] = "Table Angle" to "DS"
        table[0x0018113Au] = "Table Type" to "CS"
        table[0x00181140u] = "Rotation Direction" to "CS"
        table[0x00181141u] = "Angular Position" to "DS" // Retired
        table[0x00181142u] = "Radial Position" to "DS"
        table[0x00181143u] = "Scan Arc" to "DS"
        table[0x00181144u] = "Angular Step" to "DS"
        table[0x00181145u] = "Center of Rotation Offset" to "DS"
        table[0x00181146u] = "Rotation Offset" to "DS" // Retired
        table[0x00181147u] = "Field of View Shape" to "CS"
        table[0x00181149u] = "Field of View Dimension(s)" to "IS"
        table[0x00181150u] = "Exposure Time" to "IS"
        table[0x00181151u] = "X-Ray Tube Current" to "IS"
        table[0x00181152u] = "Exposure" to "IS"
        table[0x00181153u] = "Exposure in uAs" to "IS"
        table[0x00181154u] = "Average Pulse Width" to "DS"
        table[0x00181155u] = "Radiation Setting" to "CS"
        table[0x00181156u] = "Rectification Type" to "CS"
        table[0x0018115Au] = "Radiation Mode" to "CS"
        table[0x0018115Eu] = "Image and Fluoroscopy Area DoseProduct" to "DS"
        table[0x00181160u] = "Filter Type" to "SH"
        table[0x00181161u] = "Type of Filters" to "LO"
        table[0x00181162u] = "Intensifier Size" to "DS"
        table[0x00181164u] = "Imager Pixel Spacing" to "DS"
        table[0x00181166u] = "Grid" to "CS"
        table[0x00181170u] = "Generator Power" to "IS"
        table[0x00181180u] = "Collimator/grid Name" to "SH"
        table[0x00181181u] = "Collimator Type" to "CS"
        table[0x00181182u] = "Focal Distance" to "IS"
        table[0x00181183u] = "X Focus Center" to "DS"
        table[0x00181184u] = "Y Focus Center" to "DS"
        table[0x00181190u] = "Focal Spot(s)" to "DS"
        table[0x00181191u] = "Anode Target Material" to "CS"
        table[0x001811A0u] = "Body Part Thickness" to "DS"
        table[0x001811A2u] = "Compression Force" to "DS"
        table[0x001811A4u] = "Paddle Description" to "LO"
        table[0x00181200u] = "Date of Last Calibration" to "DA"
        table[0x00181201u] = "Time of Last Calibration" to "TM"
        table[0x00181202u] = "DateTime of Last Calibration" to "DT"
        table[0x00181210u] = "Convolution Kernel" to "SH"
        table[0x00181240u] = "Upper/Lower Pixel Values" to "IS" // Retired
        table[0x00181242u] = "Actual Frame Duration" to "IS"
        table[0x00181243u] = "Count Rate" to "IS"
        table[0x00181244u] = "Preferred Playback Sequencing" to "US"
        table[0x00181250u] = "Receive Coil Name" to "SH"
        table[0x00181251u] = "Transmit Coil Name" to "SH"
        table[0x00181260u] = "Plate Type" to "SH"
        table[0x00181261u] = "Phosphor Type" to "LO"
        table[0x00181300u] = "Scan Velocity" to "DS"
        table[0x00181301u] = "Whole Body Technique" to "CS"
        table[0x00181302u] = "Scan Length" to "IS"
        table[0x00181310u] = "Acquisition Matrix" to "US"
        table[0x00181312u] = "In-plane Phase Encoding Direction" to "CS"
        table[0x00181314u] = "Flip Angle" to "DS"
        table[0x00181315u] = "Variable Flip Angle Flag" to "CS"
        table[0x00181316u] = "SAR" to "DS"
        table[0x00181318u] = "dB/dt" to "DS"
        table[0x00181400u] = "Acquisition Device ProcessingDescription" to "LO"
        table[0x00181401u] = "Acquisition Device ProcessingCode" to "LO"
        table[0x00181402u] = "Cassette Orientation" to "CS"
        table[0x00181403u] = "Cassette Size" to "CS"
        table[0x00181404u] = "Exposures on Plate" to "US"
        table[0x00181405u] = "Relative X-Ray Exposure" to "IS"
        table[0x00181411u] = "Exposure Index" to "DS"
        table[0x00181412u] = "Target Exposure Index" to "DS"
        table[0x00181413u] = "Deviation Index" to "DS"
        table[0x00181450u] = "Column Angulation" to "DS"
        table[0x00181460u] = "Tomo Layer Height" to "DS"
        table[0x00181470u] = "Tomo Angle" to "DS"
        table[0x00181480u] = "Tomo Time" to "DS"
        table[0x00181490u] = "Tomo Type" to "CS"
        table[0x00181491u] = "Tomo Class" to "CS"
        table[0x00181495u] = "Number of Tomosynthesis SourceImages" to "IS"
        table[0x00181500u] = "Positioner Motion" to "CS"
        table[0x00181508u] = "Positioner Type" to "CS"
        table[0x00181510u] = "Positioner Primary Angle" to "DS"
        table[0x00181511u] = "Positioner Secondary Angle" to "DS"
        table[0x00181520u] = "Positioner Primary Angle Increment" to "DS"
        table[0x00181521u] = "Positioner Secondary AngleIncrement" to "DS"
        table[0x00181530u] = "Detector Primary Angle" to "DS"
        table[0x00181531u] = "Detector Secondary Angle" to "DS"
        table[0x00181600u] = "Shutter Shape" to "CS"
        table[0x00181602u] = "Shutter Left Vertical Edge" to "IS"
        table[0x00181604u] = "Shutter Right Vertical Edge" to "IS"
        table[0x00181606u] = "Shutter Upper Horizontal Edge" to "IS"
        table[0x00181608u] = "Shutter Lower Horizontal Edge" to "IS"
        table[0x00181610u] = "Center of Circular Shutter" to "IS"
        table[0x00181612u] = "Radius of Circular Shutter" to "IS"
        table[0x00181620u] = "Vertices of the Polygonal Shutter" to "IS"
        table[0x00181622u] = "Shutter Presentation Value" to "US"
        table[0x00181623u] = "Shutter Overlay Group" to "US"
        table[0x00181624u] = "Shutter Presentation Color CIELabValue" to "US"
        table[0x00181700u] = "Collimator Shape" to "CS"
        table[0x00181702u] = "Collimator Left Vertical Edge" to "IS"
        table[0x00181704u] = "Collimator Right Vertical Edge" to "IS"
        table[0x00181706u] = "Collimator Upper Horizontal Edge" to "IS"
        table[0x00181708u] = "Collimator Lower Horizontal Edge" to "IS"
        table[0x00181710u] = "Center of Circular Collimator" to "IS"
        table[0x00181712u] = "Radius of Circular Collimator" to "IS"
        table[0x00181720u] = "Vertices of the PolygonalCollimator" to "IS"
        table[0x00181800u] = "Acquisition Time Synchronized" to "CS"
        table[0x00181801u] = "Time Source" to "SH"
        table[0x00181802u] = "Time Distribution Protocol" to "CS"
        table[0x00181803u] = "NTP Source Address" to "LO"
        table[0x00182001u] = "Page Number Vector" to "IS"
        table[0x00182002u] = "Frame Label Vector" to "SH"
        table[0x00182003u] = "Frame Primary Angle Vector" to "DS"
        table[0x00182004u] = "Frame Secondary Angle Vector" to "DS"
        table[0x00182005u] = "Slice Location Vector" to "DS"
        table[0x00182006u] = "Display Window Label Vector" to "SH"
        table[0x00182010u] = "Nominal Scanned Pixel Spacing" to "DS"
        table[0x00182020u] = "Digitizing Device TransportDirection" to "CS"
        table[0x00182030u] = "Rotation of Scanned Film" to "DS"
        table[0x00182041u] = "Biopsy Target Sequence" to "SQ"
        table[0x00182042u] = "Target UID" to "UI"
        table[0x00182043u] = "Localizing Cursor Position" to "FL"
        table[0x00182044u] = "Calculated Target Position" to "FL"
        table[0x00182045u] = "Target Label" to "SH"
        table[0x00182046u] = "Displayed Z Value" to "FL"
        table[0x00183100u] = "IVUS Acquisition" to "CS"
        table[0x00183101u] = "IVUS Pullback Rate" to "DS"
        table[0x00183102u] = "IVUS Gated Rate" to "DS"
        table[0x00183103u] = "IVUS Pullback Start Frame Number" to "IS"
        table[0x00183104u] = "IVUS Pullback Stop Frame Number" to "IS"
        table[0x00183105u] = "Lesion Number" to "IS"
        table[0x00184000u] = "Acquisition Comments" to "LT" // Retired
        table[0x00185000u] = "Output Power" to "SH"
        table[0x00185010u] = "Transducer Data" to "LO"
        table[0x00185012u] = "Focus Depth" to "DS"
        table[0x00185020u] = "Processing Function" to "LO"
        table[0x00185021u] = "Postprocessing Function" to "LO" // Retired
        table[0x00185022u] = "Mechanical Index" to "DS"
        table[0x00185024u] = "Bone Thermal Index" to "DS"
        table[0x00185026u] = "Cranial Thermal Index" to "DS"
        table[0x00185027u] = "Soft Tissue Thermal Index" to "DS"
        table[0x00185028u] = "Soft Tissue-focus Thermal Index" to "DS"
        table[0x00185029u] = "Soft Tissue-surface Thermal Index" to "DS"
        table[0x00185030u] = "Dynamic Range" to "DS" // Retired
        table[0x00185040u] = "Total Gain" to "DS" // Retired
        table[0x00185050u] = "Depth of Scan Field" to "IS"
        table[0x00185100u] = "Patient Position" to "CS"
        table[0x00185101u] = "View Position" to "CS"
        table[0x00185104u] = "Projection Eponymous Name CodeSequence" to "SQ"
        table[0x00185210u] = "Image Transformation Matrix" to "DS" // Retired
        table[0x00185212u] = "Image Translation Vector" to "DS" // Retired
        table[0x00186000u] = "Sensitivity" to "DS"
        table[0x00186011u] = "Sequence of Ultrasound Regions" to "SQ"
        table[0x00186012u] = "Region Spatial Format" to "US"
        table[0x00186014u] = "Region Data Type" to "US"
        table[0x00186016u] = "Region Flags" to "UL"
        table[0x00186018u] = "Region Location Min X0" to "UL"
        table[0x0018601Au] = "Region Location Min Y0" to "UL"
        table[0x0018601Cu] = "Region Location Max X1" to "UL"
        table[0x0018601Eu] = "Region Location Max Y1" to "UL"
        table[0x00186020u] = "Reference Pixel X0" to "SL"
        table[0x00186022u] = "Reference Pixel Y0" to "SL"
        table[0x00186024u] = "Physical Units X Direction" to "US"
        table[0x00186026u] = "Physical Units Y Direction" to "US"
        table[0x00186028u] = "Reference Pixel Physical Value X" to "FD"
        table[0x0018602Au] = "Reference Pixel Physical Value Y" to "FD"
        table[0x0018602Cu] = "Physical Delta X" to "FD"
        table[0x0018602Eu] = "Physical Delta Y" to "FD"
        table[0x00186030u] = "Transducer Frequency" to "UL"
        table[0x00186031u] = "Transducer Type" to "CS"
        table[0x00186032u] = "Pulse Repetition Frequency" to "UL"
        table[0x00186034u] = "Doppler Correction Angle" to "FD"
        table[0x00186036u] = "Steering Angle" to "FD"
        table[0x00186038u] = "Doppler Sample Volume X Position(Retired)" to "UL" // Retired
        table[0x00186039u] = "Doppler Sample Volume X Position" to "SL"
        table[0x0018603Au] = "Doppler Sample Volume Y Position(Retired)" to "UL" // Retired
        table[0x0018603Bu] = "Doppler Sample Volume Y Position" to "SL"
        table[0x0018603Cu] = "TM-Line Position X0 (Retired)" to "UL" // Retired
        table[0x0018603Du] = "TM-Line Position X0" to "SL"
        table[0x0018603Eu] = "TM-Line Position Y0 (Retired)" to "UL" // Retired
        table[0x0018603Fu] = "TM-Line Position Y0" to "SL"
        table[0x00186040u] = "TM-Line Position X1 (Retired)" to "UL" // Retired
        table[0x00186041u] = "TM-Line Position X1" to "SL"
        table[0x00186042u] = "TM-Line Position Y1 (Retired)" to "UL" // Retired
        table[0x00186043u] = "TM-Line Position Y1" to "SL"
        table[0x00186044u] = "Pixel Component Organization" to "US"
        table[0x00186046u] = "Pixel Component Mask" to "UL"
        table[0x00186048u] = "Pixel Component Range Start" to "UL"
        table[0x0018604Au] = "Pixel Component Range Stop" to "UL"
        table[0x0018604Cu] = "Pixel Component Physical Units" to "US"
        table[0x0018604Eu] = "Pixel Component Data Type" to "US"
        table[0x00186050u] = "Number of Table Break Points" to "UL"
        table[0x00186052u] = "Table of X Break Points" to "UL"
        table[0x00186054u] = "Table of Y Break Points" to "FD"
        table[0x00186056u] = "Number of Table Entries" to "UL"
        table[0x00186058u] = "Table of Pixel Values" to "UL"
        table[0x0018605Au] = "Table of Parameter Values" to "FL"
        table[0x00186060u] = "R Wave Time Vector" to "FL"
        table[0x00187000u] = "Detector Conditions Nominal Flag" to "CS"
        table[0x00187001u] = "Detector Temperature" to "DS"
        table[0x00187004u] = "Detector Type" to "CS"
        table[0x00187005u] = "Detector Configuration" to "CS"
        table[0x00187006u] = "Detector Description" to "LT"
        table[0x00187008u] = "Detector Mode" to "LT"
        table[0x0018700Au] = "Detector ID" to "SH"
        table[0x0018700Cu] = "Date of Last Detector Calibration" to "DA"
        table[0x0018700Eu] = "Time of Last Detector Calibration" to "TM"
        table[0x00187010u] = "Exposures on Detector Since LastCalibration" to "IS"
        table[0x00187011u] = "Exposures on Detector SinceManufactured" to "IS"
        table[0x00187012u] = "Detector Time Since Last Exposure" to "DS"
        table[0x00187014u] = "Detector Active Time" to "DS"
        table[0x00187016u] = "Detector Activation Offset FromExposure" to "DS"
        table[0x0018701Au] = "Detector Binning" to "DS"
        table[0x00187020u] = "Detector Element Physical Size" to "DS"
        table[0x00187022u] = "Detector Element Spacing" to "DS"
        table[0x00187024u] = "Detector Active Shape" to "CS"
        table[0x00187026u] = "Detector Active Dimension(s)" to "DS"
        table[0x00187028u] = "Detector Active Origin" to "DS"
        table[0x0018702Au] = "Detector Manufacturer Name  DetectorManufacturerName  LO  1 (0018,702B) Detector Manufacturer's ModelName" to "LO"
        table[0x00187030u] = "Field of View Origin" to "DS"
        table[0x00187032u] = "Field of View Rotation" to "DS"
        table[0x00187034u] = "Field of View Horizontal Flip" to "CS"
        table[0x00187036u] = "Pixel Data Area Origin Relative ToFOV" to "FL"
        table[0x00187038u] = "Pixel Data Area Rotation AngleRelative To FOV" to "FL"
        table[0x00187040u] = "Grid Absorbing Material" to "LT"
        table[0x00187041u] = "Grid Spacing Material" to "LT"
        table[0x00187042u] = "Grid Thickness" to "DS"
        table[0x00187044u] = "Grid Pitch" to "DS"
        table[0x00187046u] = "Grid Aspect Ratio" to "IS"
        table[0x00187048u] = "Grid Period" to "DS"
        table[0x0018704Cu] = "Grid Focal Distance" to "DS"
        table[0x00187050u] = "Filter Material" to "CS"
        table[0x00187052u] = "Filter Thickness Minimum" to "DS"
        table[0x00187054u] = "Filter Thickness Maximum" to "DS"
        table[0x00187056u] = "Filter Beam Path Length Minimum" to "FL"
        table[0x00187058u] = "Filter Beam Path Length Maximum" to "FL"
        table[0x00187060u] = "Exposure Control Mode" to "CS"
        table[0x00187062u] = "Exposure Control Mode Description" to "LT"
        table[0x00187064u] = "Exposure Status" to "CS"
        table[0x00187065u] = "Phototimer Setting" to "DS"
        table[0x00188150u] = "Exposure Time in uS" to "DS"
        table[0x00188151u] = "X-Ray Tube Current in uA" to "DS"
        table[0x00189004u] = "Content Qualification" to "CS"
        table[0x00189005u] = "Pulse Sequence Name" to "SH"
        table[0x00189006u] = "MR Imaging Modifier Sequence" to "SQ"
        table[0x00189008u] = "Echo Pulse Sequence" to "CS"
        table[0x00189009u] = "Inversion Recovery" to "CS"
        table[0x00189010u] = "Flow Compensation" to "CS"
        table[0x00189011u] = "Multiple Spin Echo" to "CS"
        table[0x00189012u] = "Multi-planar Excitation" to "CS"
        table[0x00189014u] = "Phase Contrast" to "CS"
        table[0x00189015u] = "Time of Flight Contrast" to "CS"
        table[0x00189016u] = "Spoiling" to "CS"
        table[0x00189017u] = "Steady State Pulse Sequence" to "CS"
        table[0x00189018u] = "Echo Planar Pulse Sequence" to "CS"
        table[0x00189019u] = "Tag Angle First Axis" to "FD"
        table[0x00189020u] = "Magnetization Transfer" to "CS"
        table[0x00189021u] = "T2 Preparation" to "CS"
        table[0x00189022u] = "Blood Signal Nulling" to "CS"
        table[0x00189024u] = "Saturation Recovery" to "CS"
        table[0x00189025u] = "Spectrally Selected Suppression" to "CS"
        table[0x00189026u] = "Spectrally Selected Excitation" to "CS"
        table[0x00189027u] = "Spatial Pre-saturation" to "CS"
        table[0x00189028u] = "Tagging" to "CS"
        table[0x00189029u] = "Oversampling Phase" to "CS"
        table[0x00189030u] = "Tag Spacing First Dimension" to "FD"
        table[0x00189032u] = "Geometry of k-Space Traversal" to "CS"
        table[0x00189033u] = "Segmented k-Space Traversal" to "CS"
        table[0x00189034u] = "Rectilinear Phase EncodeReordering" to "CS"
        table[0x00189035u] = "Tag Thickness" to "FD"
        table[0x00189036u] = "Partial Fourier Direction" to "CS"
        table[0x00189037u] = "Cardiac Synchronization Technique" to "CS"
        table[0x00189041u] = "Receive Coil Manufacturer Name" to "LO"
        table[0x00189042u] = "MR Receive Coil Sequence" to "SQ"
        table[0x00189043u] = "Receive Coil Type" to "CS"
        table[0x00189044u] = "Quadrature Receive Coil" to "CS"
        table[0x00189045u] = "Multi-Coil Definition Sequence" to "SQ"
        table[0x00189046u] = "Multi-Coil Configuration" to "LO"
        table[0x00189047u] = "Multi-Coil Element Name" to "SH"
        table[0x00189048u] = "Multi-Coil Element Used" to "CS"
        table[0x00189049u] = "MR Transmit Coil Sequence" to "SQ"
        table[0x00189050u] = "Transmit Coil Manufacturer Name" to "LO"
        table[0x00189051u] = "Transmit Coil Type" to "CS"
        table[0x00189052u] = "Spectral Width" to "FD"
        table[0x00189053u] = "Chemical Shift Reference" to "FD"
        table[0x00189054u] = "Volume Localization Technique" to "CS"
        table[0x00189058u] = "MR Acquisition FrequencyEncoding Steps" to "US"
        table[0x00189059u] = "De-coupling" to "CS"
        table[0x00189060u] = "De-coupled Nucleus" to "CS"
        table[0x00189061u] = "De-coupling Frequency" to "FD"
        table[0x00189062u] = "De-coupling Method" to "CS"
        table[0x00189063u] = "De-coupling Chemical ShiftReference" to "FD"
        table[0x00189064u] = "k-space Filtering" to "CS"
        table[0x00189065u] = "Time Domain Filtering" to "CS"
        table[0x00189066u] = "Number of Zero Fills" to "US"
        table[0x00189067u] = "Baseline Correction" to "CS"
        table[0x00189069u] = "Parallel Reduction Factor In-plane" to "FD"
        table[0x00189070u] = "Cardiac R-R Interval Specified" to "FD"
        table[0x00189073u] = "Acquisition Duration" to "FD"
        table[0x00189074u] = "Frame Acquisition DateTime" to "DT"
        table[0x00189075u] = "Diffusion Directionality" to "CS"
        table[0x00189076u] = "Diffusion Gradient DirectionSequence" to "SQ"
        table[0x00189077u] = "Parallel Acquisition" to "CS"
        table[0x00189078u] = "Parallel Acquisition Technique" to "CS"
        table[0x00189079u] = "Inversion Times" to "FD"
        table[0x00189080u] = "Metabolite Map Description" to "ST"
        table[0x00189081u] = "Partial Fourier" to "CS"
        table[0x00189082u] = "Effective Echo Time" to "FD"
        table[0x00189083u] = "Metabolite Map Code Sequence" to "SQ"
        table[0x00189084u] = "Chemical Shift Sequence" to "SQ"
        table[0x00189085u] = "Cardiac Signal Source" to "CS"
        table[0x00189087u] = "Diffusion b-value" to "FD"
        table[0x00189089u] = "Diffusion Gradient Orientation" to "FD"
        table[0x00189090u] = "Velocity Encoding Direction" to "FD"
        table[0x00189091u] = "Velocity Encoding Minimum Value" to "FD"
        table[0x00189092u] = "Velocity Encoding AcquisitionSequence" to "SQ"
        table[0x00189093u] = "Number of k-Space Trajectories" to "US"
        table[0x00189094u] = "Coverage of k-Space" to "CS"
        table[0x00189095u] = "Spectroscopy Acquisition PhaseRows" to "UL"
        table[0x00189096u] = "Parallel Reduction Factor In-plane(Retired)" to "FD" // Retired
        table[0x00189098u] = "Transmitter Frequency" to "FD"
        table[0x00189100u] = "Resonant Nucleus" to "CS"
        table[0x00189101u] = "Frequency Correction" to "CS"
        table[0x00189103u] = "MR Spectroscopy FOV/GeometrySequence" to "SQ"
        table[0x00189104u] = "Slab Thickness" to "FD"
        table[0x00189105u] = "Slab Orientation" to "FD"
        table[0x00189106u] = "Mid Slab Position" to "FD"
        table[0x00189107u] = "MR Spatial Saturation Sequence" to "SQ"
        table[0x00189112u] = "MR Timing and RelatedParameters Sequence" to "SQ"
        table[0x00189114u] = "MR Echo Sequence" to "SQ"
        table[0x00189115u] = "MR Modifier Sequence" to "SQ"
        table[0x00189117u] = "MR Diffusion Sequence" to "SQ"
        table[0x00189118u] = "Cardiac Synchronization Sequence" to "SQ"
        table[0x00189119u] = "MR Averages Sequence" to "SQ"
        table[0x00189125u] = "MR FOV/Geometry Sequence" to "SQ"
        table[0x00189126u] = "Volume Localization Sequence" to "SQ"
        table[0x00189127u] = "Spectroscopy Acquisition DataColumns" to "UL"
        table[0x00189147u] = "Diffusion Anisotropy Type" to "CS"
        table[0x00189151u] = "Frame Reference DateTime" to "DT"
        table[0x00189152u] = "MR Metabolite Map Sequence" to "SQ"
        table[0x00189155u] = "Parallel Reduction Factorout-of-plane" to "FD"
        table[0x00189159u] = "Spectroscopy AcquisitionOut-of-plane Phase Steps" to "UL"
        table[0x00189166u] = "Bulk Motion Status" to "CS" // Retired
        table[0x00189168u] = "Parallel Reduction Factor SecondIn-plane" to "FD"
        table[0x00189169u] = "Cardiac Beat Rejection Technique" to "CS"
        table[0x00189170u] = "Respiratory Motion CompensationTechnique" to "CS"
        table[0x00189171u] = "Respiratory Signal Source" to "CS"
        table[0x00189172u] = "Bulk Motion CompensationTechnique" to "CS"
        table[0x00189173u] = "Bulk Motion Signal Source" to "CS"
        table[0x00189174u] = "Applicable Safety Standard Agency" to "CS"
        table[0x00189175u] = "Applicable Safety StandardDescription" to "LO"
        table[0x00189176u] = "Operating Mode Sequence" to "SQ"
        table[0x00189177u] = "Operating Mode Type" to "CS"
        table[0x00189178u] = "Operating Mode" to "CS"
        table[0x00189179u] = "Specific Absorption Rate Definition" to "CS"
        table[0x00189180u] = "Gradient Output Type" to "CS"
        table[0x00189181u] = "Specific Absorption Rate Value" to "FD"
        table[0x00189182u] = "Gradient Output" to "FD"
        table[0x00189183u] = "Flow Compensation Direction" to "CS"
        table[0x00189184u] = "Tagging Delay" to "FD"
        table[0x00189185u] = "Respiratory Motion CompensationTechnique Description" to "ST"
        table[0x00189186u] = "Respiratory Signal Source ID" to "SH"
        table[0x00189195u] = "Chemical Shift Minimum IntegrationLimit in Hz" to "FD" // Retired
        table[0x00189196u] = "Chemical Shift MaximumIntegration Limit in Hz" to "FD" // Retired
        table[0x00189197u] = "MR Velocity Encoding Sequence" to "SQ"
        table[0x00189198u] = "First Order Phase Correction" to "CS"
        table[0x00189199u] = "Water Referenced PhaseCorrection" to "CS"
        table[0x00189200u] = "MR Spectroscopy Acquisition Type" to "CS"
        table[0x00189214u] = "Respiratory Cycle Position" to "CS"
        table[0x00189217u] = "Velocity Encoding Maximum Value" to "FD"
        table[0x00189218u] = "Tag Spacing Second Dimension" to "FD"
        table[0x00189219u] = "Tag Angle Second Axis" to "SS"
        table[0x00189220u] = "Frame Acquisition Duration" to "FD"
        table[0x00189226u] = "MR Image Frame Type Sequence" to "SQ"
        table[0x00189227u] = "MR Spectroscopy Frame TypeSequence" to "SQ"
        table[0x00189231u] = "MR Acquisition Phase EncodingSteps in-plane" to "US"
        table[0x00189232u] = "MR Acquisition Phase EncodingSteps out-of-plane" to "US"
        table[0x00189234u] = "Spectroscopy Acquisition PhaseColumns" to "UL"
        table[0x00189236u] = "Cardiac Cycle Position" to "CS"
        table[0x00189239u] = "Specific Absorption Rate Sequence" to "SQ"
        table[0x00189240u] = "RF Echo Train Length" to "US"
        table[0x00189241u] = "Gradient Echo Train Length" to "US"
        table[0x00189250u] = "Arterial Spin Labeling Contrast" to "CS"
        table[0x00189251u] = "MR Arterial Spin LabelingSequence" to "SQ"
        table[0x00189252u] = "ASL Technique Description" to "LO"
        table[0x00189253u] = "ASL Slab Number" to "US"
        table[0x00189254u] = "ASL Slab Thickness" to "FD"
        table[0x00189255u] = "ASL Slab Orientation" to "FD"
        table[0x00189256u] = "ASL Mid Slab Position" to "FD"
        table[0x00189257u] = "ASL Context" to "CS"
        table[0x00189258u] = "ASL Pulse Train Duration" to "UL"
        table[0x00189259u] = "ASL Crusher Flag" to "CS"
        table[0x0018925Au] = "ASL Crusher Flow Limit" to "FD"
        table[0x0018925Bu] = "ASL Crusher Description" to "LO"
        table[0x0018925Cu] = "ASL Bolus Cut-off Flag" to "CS"
        table[0x0018925Du] = "ASL Bolus Cut-off TimingSequence" to "SQ"
        table[0x0018925Eu] = "ASL Bolus Cut-off Technique" to "LO"
        table[0x0018925Fu] = "ASL Bolus Cut-off Delay Time" to "UL"
        table[0x00189260u] = "ASL Slab Sequence" to "SQ"
        table[0x00189295u] = "Chemical Shift Minimum IntegrationLimit in ppm" to "FD"
        table[0x00189296u] = "Chemical Shift MaximumIntegration Limit in ppm" to "FD"
        table[0x00189297u] = "Water Reference Acquisition" to "CS"
        table[0x00189298u] = "Echo Peak Position" to "IS"
        table[0x00189301u] = "CT Acquisition Type Sequence" to "SQ"
        table[0x00189302u] = "Acquisition Type" to "CS"
        table[0x00189303u] = "Tube Angle" to "FD"
        table[0x00189304u] = "CT Acquisition Details Sequence" to "SQ"
        table[0x00189305u] = "Revolution Time" to "FD"
        table[0x00189306u] = "Single Collimation Width" to "FD"
        table[0x00189307u] = "Total Collimation Width" to "FD"
        table[0x00189308u] = "CT Table Dynamics Sequence" to "SQ"
        table[0x00189309u] = "Table Speed" to "FD"
        table[0x00189310u] = "Table Feed per Rotation" to "FD"
        table[0x00189311u] = "Spiral Pitch Factor" to "FD"
        table[0x00189312u] = "CT Geometry Sequence" to "SQ"
        table[0x00189313u] = "Data Collection Center (Patient)" to "FD"
        table[0x00189314u] = "CT Reconstruction Sequence" to "SQ"
        table[0x00189315u] = "Reconstruction Algorithm" to "CS"
        table[0x00189316u] = "Convolution Kernel Group" to "CS"
        table[0x00189317u] = "Reconstruction Field of View" to "FD"
        table[0x00189318u] = "Reconstruction Target Center(Patient)" to "FD"
        table[0x00189319u] = "Reconstruction Angle" to "FD"
        table[0x00189320u] = "Image Filter" to "SH"
        table[0x00189321u] = "CT Exposure Sequence" to "SQ"
        table[0x00189322u] = "Reconstruction Pixel Spacing" to "FD"
        table[0x00189323u] = "Exposure Modulation Type" to "CS"
        table[0x00189324u] = "Estimated Dose Saving" to "FD"
        table[0x00189325u] = "CT X-Ray Details Sequence" to "SQ"
        table[0x00189326u] = "CT Position Sequence" to "SQ"
        table[0x00189327u] = "Table Position" to "FD"
        table[0x00189328u] = "Exposure Time in ms" to "FD"
        table[0x00189329u] = "CT Image Frame Type Sequence" to "SQ"
        table[0x00189330u] = "X-Ray Tube Current in mA" to "FD"
        table[0x00189332u] = "Exposure in mAs" to "FD"
        table[0x00189333u] = "Constant Volume Flag" to "CS"
        table[0x00189334u] = "Fluoroscopy Flag" to "CS"
        table[0x00189335u] = "Distance Source to Data CollectionCenter" to "FD"
        table[0x00189337u] = "Contrast/Bolus Agent Number" to "US"
        table[0x00189338u] = "Contrast/Bolus Ingredient CodeSequence" to "SQ"
        table[0x00189340u] = "Contrast Administration ProfileSequence" to "SQ"
        table[0x00189341u] = "Contrast/Bolus Usage Sequence" to "SQ"
        table[0x00189342u] = "Contrast/Bolus Agent Administered" to "CS"
        table[0x00189343u] = "Contrast/Bolus Agent Detected" to "CS"
        table[0x00189344u] = "Contrast/Bolus Agent Phase" to "CS"
        table[0x00189345u] = "CTDIvol" to "FD"
        table[0x00189346u] = "CTDI Phantom Type CodeSequence" to "SQ"
        table[0x00189351u] = "Calcium Scoring Mass FactorPatient" to "FL"
        table[0x00189352u] = "Calcium Scoring Mass FactorDevice" to "FL"
        table[0x00189353u] = "Energy Weighting Factor" to "FL"
        table[0x00189360u] = "CT Additional X-Ray SourceSequence" to "SQ"
        table[0x00189401u] = "Projection Pixel CalibrationSequence" to "SQ"
        table[0x00189402u] = "Distance Source to Isocenter" to "FL"
        table[0x00189403u] = "Distance Object to Table Top" to "FL"
        table[0x00189404u] = "Object Pixel Spacing in Center ofBeam" to "FL"
        table[0x00189405u] = "Positioner Position Sequence" to "SQ"
        table[0x00189406u] = "Table Position Sequence" to "SQ"
        table[0x00189407u] = "Collimator Shape Sequence" to "SQ"
        table[0x00189410u] = "Planes in Acquisition" to "CS"
        table[0x00189412u] = "XA/XRF Frame CharacteristicsSequence" to "SQ"
        table[0x00189417u] = "Frame Acquisition Sequence" to "SQ"
        table[0x00189420u] = "X-Ray Receptor Type" to "CS"
        table[0x00189423u] = "Acquisition Protocol Name" to "LO"
        table[0x00189424u] = "Acquisition Protocol Description" to "LT"
        table[0x00189425u] = "Contrast/Bolus Ingredient Opaque" to "CS"
        table[0x00189426u] = "Distance Receptor Plane toDetector Housing" to "FL"
        table[0x00189427u] = "Intensifier Active Shape" to "CS"
        table[0x00189428u] = "Intensifier Active Dimension(s)" to "FL"
        table[0x00189429u] = "Physical Detector Size" to "FL"
        table[0x00189430u] = "Position of Isocenter Projection" to "FL"
        table[0x00189432u] = "Field of View Sequence" to "SQ"
        table[0x00189433u] = "Field of View Description" to "LO"
        table[0x00189434u] = "Exposure Control Sensing RegionsSequence" to "SQ"
        table[0x00189435u] = "Exposure Control Sensing RegionShape" to "CS"
        table[0x00189436u] = "Exposure Control Sensing RegionLeft Vertical Edge" to "SS"
        table[0x00189437u] = "Exposure Control Sensing RegionRight Vertical Edge" to "SS"
        table[0x00189438u] = "Exposure Control Sensing RegionUpper Horizontal Edge" to "SS"
        table[0x00189439u] = "Exposure Control Sensing RegionLower Horizontal Edge" to "SS"
        table[0x00189440u] = "Center of Circular ExposureControl Sensing Region" to "SS"
        table[0x00189441u] = "Radius of Circular ExposureControl Sensing Region" to "US"
        table[0x00189442u] = "Vertices of the Polygonal ExposureControl Sensing Region" to "SS"
        table[0x00189445u] = "Column Angulation (Patient)" to "FL"
        table[0x00189449u] = "Beam Angle" to "FL"
        table[0x00189451u] = "Frame Detector ParametersSequence" to "SQ"
        table[0x00189452u] = "Calculated Anatomy Thickness" to "FL"
        table[0x00189455u] = "Calibration Sequence" to "SQ"
        table[0x00189456u] = "Object Thickness Sequence" to "SQ"
        table[0x00189457u] = "Plane Identification" to "CS"
        table[0x00189461u] = "Field of View Dimension(s) in Float" to "FL"
        table[0x00189462u] = "Isocenter Reference SystemSequence" to "SQ"
        table[0x00189463u] = "Positioner Isocenter Primary Angle" to "FL"
        table[0x00189464u] = "Positioner Isocenter SecondaryAngle" to "FL"
        table[0x00189465u] = "Positioner Isocenter DetectorRotation Angle" to "FL"
        table[0x00189466u] = "Table X Position to Isocenter" to "FL"
        table[0x00189467u] = "Table Y Position to Isocenter" to "FL"
        table[0x00189468u] = "Table Z Position to Isocenter" to "FL"
        table[0x00189469u] = "Table Horizontal Rotation Angle" to "FL"
        table[0x00189470u] = "Table Head Tilt Angle" to "FL"
        table[0x00189471u] = "Table Cradle Tilt Angle" to "FL"
        table[0x00189472u] = "Frame Display Shutter Sequence" to "SQ"
        table[0x00189473u] = "Acquired Image Area Dose Product" to "FL"
        table[0x00189474u] = "C-arm Positioner TabletopRelationship" to "CS"
        table[0x00189476u] = "X-Ray Geometry Sequence" to "SQ"
        table[0x00189477u] = "Irradiation Event IdentificationSequence" to "SQ"
        table[0x00189504u] = "X-Ray 3D Frame Type Sequence" to "SQ"
        table[0x00189506u] = "Contributing Sources Sequence" to "SQ"
        table[0x00189507u] = "X-Ray 3D Acquisition Sequence" to "SQ"
        table[0x00189508u] = "Primary Positioner Scan Arc" to "FL"
        table[0x00189509u] = "Secondary Positioner Scan Arc" to "FL"
        table[0x00189510u] = "Primary Positioner Scan StartAngle" to "FL"
        table[0x00189511u] = "Secondary Positioner Scan StartAngle" to "FL"
        table[0x00189514u] = "Primary Positioner Increment" to "FL"
        table[0x00189515u] = "Secondary Positioner Increment" to "FL"
        table[0x00189516u] = "Start Acquisition DateTime" to "DT"
        table[0x00189517u] = "End Acquisition DateTime" to "DT"
        table[0x00189518u] = "Primary Positioner Increment Sign" to "SS"
        table[0x00189519u] = "Secondary Positioner IncrementSign" to "SS"
        table[0x00189524u] = "Application Name" to "LO"
        table[0x00189525u] = "Application Version" to "LO"
        table[0x00189526u] = "Application Manufacturer" to "LO"
        table[0x00189527u] = "Algorithm Type" to "CS"
        table[0x00189528u] = "Algorithm Description" to "LO"
        table[0x00189530u] = "X-Ray 3D ReconstructionSequence" to "SQ"
        table[0x00189531u] = "Reconstruction Description" to "LO"
        table[0x00189538u] = "Per Projection AcquisitionSequence" to "SQ"
        table[0x00189541u] = "Detector Position Sequence" to "SQ"
        table[0x00189542u] = "X-Ray Acquisition Dose Sequence" to "SQ"
        table[0x00189543u] = "X-Ray Source Isocenter PrimaryAngle" to "FD"
        table[0x00189544u] = "X-Ray Source Isocenter SecondaryAngle" to "FD"
        table[0x00189545u] = "Breast Support Isocenter PrimaryAngle" to "FD"
        table[0x00189546u] = "Breast Support IsocenterSecondary Angle" to "FD"
        table[0x00189547u] = "Breast Support X Position toIsocenter" to "FD"
        table[0x00189548u] = "Breast Support Y Position toIsocenter" to "FD"
        table[0x00189549u] = "Breast Support Z Position toIsocenter" to "FD"
        table[0x00189550u] = "Detector Isocenter Primary Angle" to "FD"
        table[0x00189551u] = "Detector Isocenter SecondaryAngle" to "FD"
        table[0x00189552u] = "Detector X Position to Isocenter" to "FD"
        table[0x00189553u] = "Detector Y Position to Isocenter" to "FD"
        table[0x00189554u] = "Detector Z Position to Isocenter" to "FD"
        table[0x00189555u] = "X-Ray Grid Sequence" to "SQ"
        table[0x00189556u] = "X-Ray Filter Sequence" to "SQ"
        table[0x00189557u] = "Detector Active Area TLHCPosition" to "FD"
        table[0x00189558u] = "Detector Active Area Orientation" to "FD"
        table[0x00189559u] = "Positioner Primary Angle Direction" to "CS"
        table[0x00189601u] = "Diffusion b-matrix Sequence" to "SQ"
        table[0x00189602u] = "Diffusion b-value XX" to "FD"
        table[0x00189603u] = "Diffusion b-value XY" to "FD"
        table[0x00189604u] = "Diffusion b-value XZ" to "FD"
        table[0x00189605u] = "Diffusion b-value YY" to "FD"
        table[0x00189606u] = "Diffusion b-value YZ" to "FD"
        table[0x00189607u] = "Diffusion b-value ZZ" to "FD"
        table[0x00189701u] = "Decay Correction DateTime" to "DT"
        table[0x00189715u] = "Start Density Threshold" to "FD"
        table[0x00189716u] = "Start Relative Density DifferenceThreshold" to "FD"
        table[0x00189717u] = "Start Cardiac Trigger CountThreshold" to "FD"
        table[0x00189718u] = "Start Respiratory Trigger CountThreshold" to "FD"
        table[0x00189719u] = "Termination Counts Threshold" to "FD"
        table[0x00189720u] = "Termination Density Threshold" to "FD"
        table[0x00189721u] = "Termination Relative DensityThreshold" to "FD"
        table[0x00189722u] = "Termination Time Threshold" to "FD"
        table[0x00189723u] = "Termination Cardiac Trigger CountThreshold" to "FD"
        table[0x00189724u] = "Termination Respiratory TriggerCount Threshold" to "FD"
        table[0x00189725u] = "Detector Geometry" to "CS"
        table[0x00189726u] = "Transverse Detector Separation" to "FD"
        table[0x00189727u] = "Axial Detector Dimension" to "FD"
        table[0x00189729u] = "Radiopharmaceutical AgentNumber" to "US"
        table[0x00189732u] = "PET Frame Acquisition Sequence" to "SQ"
        table[0x00189733u] = "PET Detector Motion DetailsSequence" to "SQ"
        table[0x00189734u] = "PET Table Dynamics Sequence" to "SQ"
        table[0x00189735u] = "PET Position Sequence" to "SQ"
        table[0x00189736u] = "PET Frame Correction FactorsSequence" to "SQ"
        table[0x00189737u] = "Radiopharmaceutical UsageSequence" to "SQ"
        table[0x00189738u] = "Attenuation Correction Source" to "CS"
        table[0x00189739u] = "Number of Iterations" to "US"
        table[0x00189740u] = "Number of Subsets" to "US"
        table[0x00189749u] = "PET Reconstruction Sequence" to "SQ"
        table[0x00189751u] = "PET Frame Type Sequence" to "SQ"
        table[0x00189755u] = "Time of Flight Information Used" to "CS"
        table[0x00189756u] = "Reconstruction Type" to "CS"
        table[0x00189758u] = "Decay Corrected" to "CS"
        table[0x00189759u] = "Attenuation Corrected" to "CS"
        table[0x00189760u] = "Scatter Corrected" to "CS"
        table[0x00189761u] = "Dead Time Corrected" to "CS"
        table[0x00189762u] = "Gantry Motion Corrected" to "CS"
        table[0x00189763u] = "Patient Motion Corrected" to "CS"
        table[0x00189764u] = "Count Loss NormalizationCorrected" to "CS"
        table[0x00189765u] = "Randoms Corrected" to "CS"
        table[0x00189766u] = "Non-uniform Radial SamplingCorrected" to "CS"
        table[0x00189767u] = "Sensitivity Calibrated" to "CS"
        table[0x00189768u] = "Detector Normalization Correction" to "CS"
        table[0x00189769u] = "Iterative Reconstruction Method" to "CS"
        table[0x00189770u] = "Attenuation Correction TemporalRelationship" to "CS"
        table[0x00189771u] = "Patient Physiological StateSequence" to "SQ"
        table[0x00189772u] = "Patient Physiological State CodeSequence" to "SQ"
        table[0x00189801u] = "Depth(s) of Focus" to "FD"
        table[0x00189803u] = "Excluded Intervals Sequence" to "SQ"
        table[0x00189804u] = "Exclusion Start DateTime" to "DT"
        table[0x00189805u] = "Exclusion Duration" to "FD"
        table[0x00189806u] = "US Image Description Sequence" to "SQ"
        table[0x00189807u] = "Image Data Type Sequence" to "SQ"
        table[0x00189808u] = "Data Type" to "CS"
        table[0x00189809u] = "Transducer Scan Pattern CodeSequence" to "SQ"
        table[0x0018980Bu] = "Aliased Data Type" to "CS"
        table[0x0018980Cu] = "Position Measuring Device Used" to "CS"
        table[0x0018980Du] = "Transducer Geometry CodeSequence" to "SQ"
        table[0x0018980Eu] = "Transducer Beam Steering CodeSequence" to "SQ"
        table[0x0018980Fu] = "Transducer Application CodeSequence" to "SQ"
        // add(0x00189810, "Zero Velocity Pixel Value", "US or SS");
        table[0x0018A001u] = "Contributing Equipment Sequence" to "SQ"
        table[0x0018A002u] = "Contribution DateTime" to "DT"
        table[0x0018A003u] = "Contribution Description" to "ST"
    }

    /**
     * Adds attributes of group 0x0020.
     */
    private fun addAttributeGroup0020() {
        table[0x0020000Du] = "Study Instance UID" to "UI"
        table[0x0020000Eu] = "Series Instance UID" to "UI"
        table[0x00200010u] = "Study ID" to "SH"
        table[0x00200011u] = "Series Number" to "IS"
        table[0x00200012u] = "Acquisition Number" to "IS"
        table[0x00200013u] = "Instance Number" to "IS"
        table[0x00200014u] = "Isotope Number" to "IS" // Retired
        table[0x00200015u] = "Phase Number" to "IS" // Retired
        table[0x00200016u] = "Interval Number" to "IS" // Retired
        table[0x00200017u] = "Time Slot Number" to "IS" // Retired
        table[0x00200018u] = "Angle Number" to "IS" // Retired
        table[0x00200019u] = "Item Number" to "IS"
        table[0x00200020u] = "Patient Orientation" to "CS"
        table[0x00200022u] = "Overlay Number" to "IS" // Retired
        table[0x00200024u] = "Curve Number" to "IS" // Retired
        table[0x00200026u] = "LUT Number" to "IS" // Retired
        table[0x00200030u] = "Image Position" to "DS" // Retired
        table[0x00200032u] = "Image Position (Patient)" to "DS"
        table[0x00200035u] = "Image Orientation" to "DS" // Retired
        table[0x00200037u] = "Image Orientation (Patient)" to "DS"
        table[0x00200050u] = "Location" to "DS" // Retired
        table[0x00200052u] = "Frame of Reference UID" to "UI"
        table[0x00200060u] = "Laterality" to "CS"
        table[0x00200062u] = "Image Laterality" to "CS"
        table[0x00200070u] = "Image Geometry Type" to "LO" // Retired
        table[0x00200080u] = "Masking Image" to "CS" // Retired
        table[0x002000AAu] = "Report Number" to "IS" // Retired
        table[0x00200100u] = "Temporal Position Identifier" to "IS"
        table[0x00200105u] = "Number of Temporal Positions" to "IS"
        table[0x00200110u] = "Temporal Resolution" to "DS"
        table[0x00200200u] = "Synchronization Frame ofReference UID" to "UI"
        table[0x00200242u] = "SOP Instance UID ofConcatenation Source" to "UI"
        table[0x00201000u] = "Series in Study" to "IS" // Retired
        table[0x00201001u] = "Acquisitions in Series" to "IS" // Retired
        table[0x00201002u] = "Images in Acquisition" to "IS"
        table[0x00201003u] = "Images in Series" to "IS" // Retired
        table[0x00201004u] = "Acquisitions in Study" to "IS" // Retired
        table[0x00201005u] = "Images in Study" to "IS" // Retired
        table[0x00201020u] = "Reference" to "LO" // Retired
        table[0x00201040u] = "Position Reference Indicator" to "LO"
        table[0x00201041u] = "Slice Location" to "DS"
        table[0x00201070u] = "Other Study Numbers" to "IS" // Retired
        table[0x00201200u] = "Number of Patient Related Studies" to "IS"
        table[0x00201202u] = "Number of Patient Related Series" to "IS"
        table[0x00201204u] = "Number of Patient RelatedInstances" to "IS"
        table[0x00201206u] = "Number of Study Related Series" to "IS"
        table[0x00201208u] = "Number of Study Related Instances" to "IS"
        table[0x00201209u] = "Number of Series RelatedInstances" to "IS"
        table[0x00203401u] = "Modifying Device ID" to "CS" // Retired
        table[0x00203402u] = "Modified Image ID" to "CS" // Retired
        table[0x00203403u] = "Modified Image Date" to "DA" // Retired
        table[0x00203404u] = "Modifying Device Manufacturer" to "LO" // Retired
        table[0x00203405u] = "Modified Image Time" to "TM" // Retired
        table[0x00203406u] = "Modified Image Description" to "LO" // Retired
        table[0x00204000u] = "Image Comments" to "LT"
        table[0x00205000u] = "Original Image Identification" to "AT" // Retired
        table[0x00205002u] = "Original Image IdentificationNomenclature" to "LO" // Retired
        table[0x00209056u] = "Stack ID" to "SH"
        table[0x00209057u] = "In-Stack Position Number" to "UL"
        table[0x00209071u] = "Frame Anatomy Sequence" to "SQ"
        table[0x00209072u] = "Frame Laterality" to "CS"
        table[0x00209111u] = "Frame Content Sequence" to "SQ"
        table[0x00209113u] = "Plane Position Sequence" to "SQ"
        table[0x00209116u] = "Plane Orientation Sequence" to "SQ"
        table[0x00209128u] = "Temporal Position Index" to "UL"
        table[0x00209153u] = "Nominal Cardiac Trigger DelayTime" to "FD"
        table[0x00209154u] = "Nominal Cardiac Trigger Time PriorTo R-Peak" to "FL"
        table[0x00209155u] = "Actual Cardiac Trigger Time PriorTo R-Peak" to "FL"
        table[0x00209156u] = "Frame Acquisition Number" to "US"
        table[0x00209157u] = "Dimension Index Values" to "UL"
        table[0x00209158u] = "Frame Comments" to "LT"
        table[0x00209161u] = "Concatenation UID" to "UI"
        table[0x00209162u] = "In-concatenation Number" to "US"
        table[0x00209163u] = "In-concatenation Total Number" to "US"
        table[0x00209164u] = "Dimension Organization UID" to "UI"
        table[0x00209165u] = "Dimension Index Pointer" to "AT"
        table[0x00209167u] = "Functional Group Pointer" to "AT"
        table[0x00209170u] = "Unassigned Shared ConvertedAttributes Sequence" to "SQ"
        table[0x00209171u] = "Unassigned Per-Frame ConvertedAttributes Sequence" to "SQ"
        table[0x00209172u] = "Conversion Source AttributesSequence" to "SQ"
        table[0x00209213u] = "Dimension Index Private Creator" to "LO"
        table[0x00209221u] = "Dimension Organization Sequence" to "SQ"
        table[0x00209222u] = "Dimension Index Sequence" to "SQ"
        table[0x00209228u] = "Concatenation Frame OffsetNumber" to "UL"
        table[0x00209238u] = "Functional Group Private Creator" to "LO"
        table[0x00209241u] = "Nominal Percentage of CardiacPhase" to "FL"
        table[0x00209245u] = "Nominal Percentage of RespiratoryPhase" to "FL"
        table[0x00209246u] = "Starting Respiratory Amplitude" to "FL"
        table[0x00209247u] = "Starting Respiratory Phase" to "CS"
        table[0x00209248u] = "Ending Respiratory Amplitude" to "FL"
        table[0x00209249u] = "Ending Respiratory Phase" to "CS"
        table[0x00209250u] = "Respiratory Trigger Type" to "CS"
        table[0x00209251u] = "R-R Interval Time Nominal" to "FD"
        table[0x00209252u] = "Actual Cardiac Trigger Delay Time" to "FD"
        table[0x00209253u] = "Respiratory SynchronizationSequence" to "SQ"
        table[0x00209254u] = "Respiratory Interval Time" to "FD"
        table[0x00209255u] = "Nominal Respiratory Trigger DelayTime" to "FD"
        table[0x00209256u] = "Respiratory Trigger DelayThreshold" to "FD"
        table[0x00209257u] = "Actual Respiratory Trigger DelayTime" to "FD"
        table[0x00209301u] = "Image Position (Volume)" to "FD"
        table[0x00209302u] = "Image Orientation (Volume)" to "FD"
        table[0x00209307u] = "Ultrasound Acquisition Geometry" to "CS"
        table[0x00209308u] = "Apex Position" to "FD"
        table[0x00209309u] = "Volume to Transducer MappingMatrix" to "FD"
        table[0x0020930Au] = "Volume to Table Mapping Matrix" to "FD"
        table[0x0020930Bu] = "Volume to Transducer Relationship" to "CS"
        table[0x0020930Cu] = "Patient Frame of Reference Source" to "CS"
        table[0x0020930Du] = "Temporal Position Time Offset" to "FD"
        table[0x0020930Eu] = "Plane Position (Volume) Sequence" to "SQ"
        table[0x0020930Fu] = "Plane Orientation (Volume)  Sequence" to "SQ"
        table[0x00209310u] = "Temporal Position Sequence" to "SQ"
        table[0x00209311u] = "Dimension Organization Type" to "CS"
        table[0x00209312u] = "Volume Frame of Reference UID" to "UI"
        table[0x00209313u] = "Table Frame of Reference UID" to "UI"
        table[0x00209421u] = "Dimension Description Label" to "LO"
        table[0x00209450u] = "Patient Orientation in FrameSequence" to "SQ"
        table[0x00209453u] = "Frame Label" to "LO"
        table[0x00209518u] = "Acquisition Index" to "US"
        table[0x00209529u] = "Contributing SOP InstancesReference Sequence" to "SQ"
        table[0x00209536u] = "Reconstruction Index" to "US"
    }

    /**
     * Adds attributes of group 0x0022.
     */
    private fun addAttributeGroup0022() {
        table[0x00220001u] = "Light Path Filter Pass-ThroughWavelength" to "US"
        table[0x00220002u] = "Light Path Filter Pass Band" to "US"
        table[0x00220003u] = "Image Path Filter Pass-ThroughWavelength" to "US"
        table[0x00220004u] = "Image Path Filter Pass Band" to "US"
        table[0x00220005u] = "Patient Eye MovementCommanded" to "CS"
        table[0x00220006u] = "Patient Eye Movement CommandCode Sequence" to "SQ"
        table[0x00220007u] = "Spherical Lens Power" to "FL"
        table[0x00220008u] = "Cylinder Lens Power" to "FL"
        table[0x00220009u] = "Cylinder Axis" to "FL"
        table[0x0022000Au] = "Emmetropic Magnification" to "FL"
        table[0x0022000Bu] = "Intra Ocular Pressure" to "FL"
        table[0x0022000Cu] = "Horizontal Field of View" to "FL"
        table[0x0022000Du] = "Pupil Dilated" to "CS"
        table[0x0022000Eu] = "Degree of Dilation" to "FL"
        table[0x00220010u] = "Stereo Baseline Angle" to "FL"
        table[0x00220011u] = "Stereo Baseline Displacement" to "FL"
        table[0x00220012u] = "Stereo Horizontal Pixel Offset" to "FL"
        table[0x00220013u] = "Stereo Vertical Pixel Offset" to "FL"
        table[0x00220014u] = "Stereo Rotation" to "FL"
        table[0x00220015u] = "Acquisition Device Type CodeSequence" to "SQ"
        table[0x00220016u] = "Illumination Type Code Sequence" to "SQ"
        table[0x00220017u] = "Light Path Filter Type Stack CodeSequence" to "SQ"
        table[0x00220018u] = "Image Path Filter Type Stack CodeSequence" to "SQ"
        table[0x00220019u] = "Lenses Code Sequence" to "SQ"
        table[0x0022001Au] = "Channel Description CodeSequence" to "SQ"
        table[0x0022001Bu] = "Refractive State Sequence" to "SQ"
        table[0x0022001Cu] = "Mydriatic Agent Code Sequence" to "SQ"
        table[0x0022001Du] = "Relative Image Position CodeSequence" to "SQ"
        table[0x0022001Eu] = "Camera Angle of View" to "FL"
        table[0x00220020u] = "Stereo Pairs Sequence" to "SQ"
        table[0x00220021u] = "Left Image Sequence" to "SQ"
        table[0x00220022u] = "Right Image Sequence" to "SQ"
        table[0x00220028u] = "Stereo Pairs Present" to "CS"
        table[0x00220030u] = "Axial Length of the Eye" to "FL"
        table[0x00220031u] = "Ophthalmic Frame LocationSequence" to "SQ"
        table[0x00220032u] = "Reference Coordinates" to "FL"
        table[0x00220035u] = "Depth Spatial Resolution" to "FL"
        table[0x00220036u] = "Maximum Depth Distortion" to "FL"
        table[0x00220037u] = "Along-scan Spatial Resolution" to "FL"
        table[0x00220038u] = "Maximum Along-scan Distortion" to "FL"
        table[0x00220039u] = "Ophthalmic Image Orientation" to "CS"
        table[0x00220041u] = "Depth of Transverse Image" to "FL"
        table[0x00220042u] = "Mydriatic Agent ConcentrationUnits Sequence" to "SQ"
        table[0x00220048u] = "Across-scan Spatial Resolution" to "FL"
        table[0x00220049u] = "Maximum Across-scan Distortion" to "FL"
        table[0x0022004Eu] = "Mydriatic Agent Concentration" to "DS"
        table[0x00220055u] = "Illumination Wave Length" to "FL"
        table[0x00220056u] = "Illumination Power" to "FL"
        table[0x00220057u] = "Illumination Bandwidth" to "FL"
        table[0x00220058u] = "Mydriatic Agent Sequence" to "SQ"
        table[0x00221007u] = "Ophthalmic Axial MeasurementsRight Eye Sequence" to "SQ"
        table[0x00221008u] = "Ophthalmic Axial MeasurementsLeft Eye Sequence" to "SQ"
        table[0x00221009u] = "Ophthalmic Axial MeasurementsDevice Type" to "CS"
        table[0x00221010u] = "Ophthalmic Axial LengthMeasurements Type" to "CS"
        table[0x00221012u] = "Ophthalmic Axial Length Sequence" to "SQ"
        table[0x00221019u] = "Ophthalmic Axial Length" to "FL"
        table[0x00221024u] = "Lens Status Code Sequence" to "SQ"
        table[0x00221025u] = "Vitreous Status Code Sequence" to "SQ"
        table[0x00221028u] = "IOL Formula Code Sequence" to "SQ"
        table[0x00221029u] = "IOL Formula Detail" to "LO"
        table[0x00221033u] = "Keratometer Index" to "FL"
        table[0x00221035u] = "Source of Ophthalmic Axial LengthCode Sequence" to "SQ"
        table[0x00221037u] = "Target Refraction" to "FL"
        table[0x00221039u] = "Refractive Procedure Occurred" to "CS"
        table[0x00221040u] = "Refractive Surgery Type CodeSequence" to "SQ"
        table[0x00221044u] = "Ophthalmic Ultrasound MethodCode Sequence" to "SQ"
        table[0x00221050u] = "Ophthalmic Axial LengthMeasurements Sequence" to "SQ"
        table[0x00221053u] = "IOL Power" to "FL"
        table[0x00221054u] = "Predicted Refractive Error" to "FL"
        table[0x00221059u] = "Ophthalmic Axial Length Velocity" to "FL"
        table[0x00221065u] = "Lens Status Description" to "LO"
        table[0x00221066u] = "Vitreous Status Description" to "LO"
        table[0x00221090u] = "IOL Power Sequence" to "SQ"
        table[0x00221092u] = "Lens Constant Sequence" to "SQ"
        table[0x00221093u] = "IOL Manufacturer" to "LO"
        table[0x00221094u] = "Lens Constant Description" to "LO" // Retired
        table[0x00221095u] = "Implant Name" to "LO"
        table[0x00221096u] = "Keratometry Measurement TypeCode Sequence" to "SQ"
        table[0x00221097u] = "Implant Part Number" to "LO"
        table[0x00221100u] = "Referenced Ophthalmic AxialMeasurements Sequence" to "SQ"
        table[0x00221101u] = "Ophthalmic Axial LengthMeasurements Segment NameCode Sequence" to "SQ"
        table[0x00221103u] = "Refractive Error Before RefractiveSurgery Code Sequence" to "SQ"
        table[0x00221121u] = "IOL Power For Exact Emmetropia" to "FL"
        table[0x00221122u] = "IOL Power For Exact TargetRefraction" to "FL"
        table[0x00221125u] = "Anterior Chamber Depth DefinitionCode Sequence" to "SQ"
        table[0x00221127u] = "Lens Thickness Sequence" to "SQ"
        table[0x00221128u] = "Anterior Chamber Depth Sequence" to "SQ"
        table[0x00221130u] = "Lens Thickness" to "FL"
        table[0x00221131u] = "Anterior Chamber Depth" to "FL"
        table[0x00221132u] = "Source of Lens Thickness DataCode Sequence" to "SQ"
        table[0x00221133u] = "Source of Anterior Chamber DepthData Code Sequence" to "SQ"
        table[0x00221134u] = "Source of RefractiveMeasurements Sequence" to "SQ"
        table[0x00221135u] = "Source of RefractiveMeasurements Code Sequence" to "SQ"
        table[0x00221140u] = "Ophthalmic Axial LengthMeasurement Modified" to "CS"
        table[0x00221150u] = "Ophthalmic Axial Length DataSource Code Sequence" to "SQ"
        table[0x00221153u] = "Ophthalmic Axial LengthAcquisition Method CodeSequence" to "SQ" // Retired
        table[0x00221155u] = "Signal to Noise Ratio" to "FL"
        table[0x00221159u] = "Ophthalmic Axial Length DataSource Description" to "LO"
        table[0x00221210u] = "Ophthalmic Axial LengthMeasurements Total LengthSequence" to "SQ"
        table[0x00221211u] = "Ophthalmic Axial LengthMeasurements Segmental LengthSequence" to "SQ"
        table[0x00221212u] = "Ophthalmic Axial LengthMeasurements Length SummationSequence" to "SQ"
        table[0x00221220u] = "Ultrasound Ophthalmic AxialLength Measurements Sequence" to "SQ"
        table[0x00221225u] = "Optical Ophthalmic Axial LengthMeasurements Sequence" to "SQ"
        table[0x00221230u] = "Ultrasound Selected OphthalmicAxial Length Sequence" to "SQ"
        table[0x00221250u] = "Ophthalmic Axial Length SelectionMethod Code Sequence" to "SQ"
        table[0x00221255u] = "Optical Selected Ophthalmic AxialLength Sequence" to "SQ"
        table[0x00221257u] = "Selected Segmental OphthalmicAxial Length Sequence" to "SQ"
        table[0x00221260u] = "Selected Total Ophthalmic AxialLength Sequence" to "SQ"
        table[0x00221262u] = "Ophthalmic Axial Length QualityMetric Sequence" to "SQ"
        table[0x00221265u] = "Ophthalmic Axial Length QualityMetric Type Code Sequence" to "SQ" // Retired
        table[0x00221273u] = "Ophthalmic Axial Length QualityMetric Type Description" to "LO" // Retired
        table[0x00221300u] = "Intraocular Lens Calculations RightEye Sequence" to "SQ"
        table[0x00221310u] = "Intraocular Lens Calculations LeftEye Sequence" to "SQ"
        table[0x00221330u] = "Referenced Ophthalmic AxialLength Measurement QC ImageSequence" to "SQ"
        table[0x00221415u] = "Ophthalmic Mapping Device Type" to "CS"
        table[0x00221420u] = "Acquisition Method CodeSequence" to "SQ"
        table[0x00221423u] = "Acquisition Method AlgorithmSequence" to "SQ"
        table[0x00221436u] = "Ophthalmic Thickness Map TypeCode Sequence" to "SQ"
        table[0x00221443u] = "Ophthalmic Thickness MappingNormals Sequence" to "SQ"
        table[0x00221445u] = "Retinal Thickness Definition CodeSequence" to "SQ"
        table[0x00221450u] = "Pixel Value Mapping to CodedConcept Sequence" to "SQ"
        // add(0x00221452, "Mapped Pixel Value", "US or SS");
        table[0x00221454u] = "Pixel Value Mapping Explanation" to "LO"
        table[0x00221458u] = "Ophthalmic Thickness Map QualityThreshold Sequence" to "SQ"
        table[0x00221460u] = "Ophthalmic Thickness MapThreshold Quality Rating" to "FL"
        table[0x00221463u] = "Anatomic Structure ReferencePoint" to "FL"
        table[0x00221465u] = "Registration to Localizer Sequence" to "SQ"
        table[0x00221466u] = "Registered Localizer Units" to "CS"
        table[0x00221467u] = "Registered Localizer Top Left HandCorner" to "FL"
        table[0x00221468u] = "Registered Localizer Bottom RightHand Corner" to "FL"
        table[0x00221470u] = "Ophthalmic Thickness Map QualityRating Sequence" to "SQ"
        table[0x00221472u] = "Relevant OPT Attributes Sequence" to "SQ"
        table[0x00221512u] = "Transformation Method CodeSequence" to "SQ"
        table[0x00221513u] = "Transformation AlgorithmSequence" to "SQ"
        table[0x00221515u] = "Ophthalmic Axial Length Method" to "CS"
        table[0x00221517u] = "Ophthalmic FOV" to "FL"
        table[0x00221518u] = "Two Dimensional to ThreeDimensional Map Sequence" to "SQ"
        table[0x00221525u] = "Wide Field OphthalmicPhotography Quality RatingSequence" to "SQ"
        table[0x00221526u] = "Wide Field OphthalmicPhotography Quality ThresholdSequence" to "SQ"
        table[0x00221527u] = "Wide Field OphthalmicPhotography Threshold QualityRating" to "FL"
        table[0x00221528u] = "X Coordinates Center Pixel ViewAngle" to "FL"
        table[0x00221529u] = "Y Coordinates Center Pixel ViewAngle" to "FL"
        table[0x00221530u] = "Number of Map Points" to "UL"
        table[0x00221531u] = "Two Dimensional to ThreeDimensional Map Data" to "OF"
    }

    /**
     * Adds attributes of group 0x0024.
     */
    private fun addAttributeGroup0024() {
        table[0x00240010u] = "Visual Field Horizontal Extent" to "FL"
        table[0x00240011u] = "Visual Field Vertical Extent" to "FL"
        table[0x00240012u] = "Visual Field Shape" to "CS"
        table[0x00240016u] = "Screening Test Mode CodeSequence" to "SQ"
        table[0x00240018u] = "Maximum Stimulus Luminance" to "FL"
        table[0x00240020u] = "Background Luminance" to "FL"
        table[0x00240021u] = "Stimulus Color Code Sequence" to "SQ"
        table[0x00240024u] = "Background Illumination ColorCode Sequence" to "SQ"
        table[0x00240025u] = "Stimulus Area" to "FL"
        table[0x00240028u] = "Stimulus Presentation Time" to "FL"
        table[0x00240032u] = "Fixation Sequence" to "SQ"
        table[0x00240033u] = "Fixation Monitoring CodeSequence" to "SQ"
        table[0x00240034u] = "Visual Field Catch Trial Sequence" to "SQ"
        table[0x00240035u] = "Fixation Checked Quantity" to "US"
        table[0x00240036u] = "Patient Not Properly FixatedQuantity" to "US"
        table[0x00240037u] = "Presented Visual Stimuli Data Flag" to "CS"
        table[0x00240038u] = "Number of Visual Stimuli" to "US"
        table[0x00240039u] = "Excessive Fixation Losses DataFlag" to "CS"
        table[0x00240040u] = "Excessive Fixation Losses" to "CS"
        table[0x00240042u] = "Stimuli Retesting Quantity" to "US"
        table[0x00240044u] = "Comments on Patient'sPerformance of Visual Field" to "LT"
        table[0x00240045u] = "False Negatives Estimate Flag" to "CS"
        table[0x00240046u] = "False Negatives Estimate" to "FL"
        table[0x00240048u] = "Negative Catch Trials Quantity" to "US"
        table[0x00240050u] = "False Negatives Quantity" to "US"
        table[0x00240051u] = "Excessive False Negatives DataFlag" to "CS"
        table[0x00240052u] = "Excessive False Negatives" to "CS"
        table[0x00240053u] = "False Positives Estimate Flag" to "CS"
        table[0x00240054u] = "False Positives Estimate" to "FL"
        table[0x00240055u] = "Catch Trials Data Flag" to "CS"
        table[0x00240056u] = "Positive Catch Trials Quantity" to "US"
        table[0x00240057u] = "Test Point Normals Data Flag" to "CS"
        table[0x00240058u] = "Test Point Normals Sequence" to "SQ"
        table[0x00240059u] = "Global Deviation ProbabilityNormals Flag" to "CS"
        table[0x00240060u] = "False Positives Quantity" to "US"
        table[0x00240061u] = "Excessive False Positives DataFlag" to "CS"
        table[0x00240062u] = "Excessive False Positives" to "CS"
        table[0x00240063u] = "Visual Field Test Normals Flag" to "CS"
        table[0x00240064u] = "Results Normals Sequence" to "SQ"
        table[0x00240065u] = "Age Corrected Sensitivity DeviationAlgorithm Sequence" to "SQ"
        table[0x00240066u] = "Global Deviation From Normal" to "FL"
        table[0x00240067u] = "Generalized Defect SensitivityDeviation Algorithm Sequence" to "SQ"
        table[0x00240068u] = "Localized Deviation From Normal" to "FL"
        table[0x00240069u] = "Patient Reliability Indicator" to "LO"
        table[0x00240070u] = "Visual Field Mean Sensitivity" to "FL"
        table[0x00240071u] = "Global Deviation Probability" to "FL"
        table[0x00240072u] = "Local Deviation Probability NormalsFlag" to "CS"
        table[0x00240073u] = "Localized Deviation Probability" to "FL"
        table[0x00240074u] = "Short Term Fluctuation Calculated" to "CS"
        table[0x00240075u] = "Short Term Fluctuation" to "FL"
        table[0x00240076u] = "Short Term Fluctuation ProbabilityCalculated" to "CS"
        table[0x00240077u] = "Short Term Fluctuation Probability" to "FL"
        table[0x00240078u] = "Corrected Localized DeviationFrom Normal Calculated" to "CS"
        table[0x00240079u] = "Corrected Localized DeviationFrom Normal" to "FL"
        table[0x00240080u] = "Corrected Localized DeviationFrom Normal Probability Calculated" to "CS"
        table[0x00240081u] = "Corrected Localized DeviationFrom Normal Probability" to "FL"
        table[0x00240083u] = "Global Deviation ProbabilitySequence" to "SQ"
        table[0x00240085u] = "Localized Deviation ProbabilitySequence" to "SQ"
        table[0x00240086u] = "Foveal Sensitivity Measured" to "CS"
        table[0x00240087u] = "Foveal Sensitivity" to "FL"
        table[0x00240088u] = "Visual Field Test Duration" to "FL"
        table[0x00240089u] = "Visual Field Test Point Sequence" to "SQ"
        table[0x00240090u] = "Visual Field Test PointX-Coordinate" to "FL"
        table[0x00240091u] = "Visual Field Test PointY-Coordinate" to "FL"
        table[0x00240092u] = "Age Corrected Sensitivity DeviationValue" to "FL"
        table[0x00240093u] = "Stimulus Results" to "CS"
        table[0x00240094u] = "Sensitivity Value" to "FL"
        table[0x00240095u] = "Retest Stimulus Seen" to "CS"
        table[0x00240096u] = "Retest Sensitivity Value" to "FL"
        table[0x00240097u] = "Visual Field Test Point NormalsSequence" to "SQ"
        table[0x00240098u] = "Quantified Defect" to "FL"
        table[0x00240100u] = "Age Corrected Sensitivity DeviationProbability Value" to "FL"
        table[0x00240102u] = "Generalized Defect CorrectedSensitivity Deviation Flag" to "CS"
        table[0x00240103u] = "Generalized Defect CorrectedSensitivity Deviation Value" to "FL"
        table[0x00240104u] = "Generalized Defect CorrectedSensitivity Deviation ProbabilityValue" to "FL"
        table[0x00240105u] = "Minimum Sensitivity Value" to "FL"
        table[0x00240106u] = "Blind Spot Localized" to "CS"
        table[0x00240107u] = "Blind Spot X-Coordinate" to "FL"
        table[0x00240108u] = "Blind Spot Y-Coordinate" to "FL"
        table[0x00240110u] = "Visual Acuity MeasurementSequence" to "SQ"
        table[0x00240112u] = "Refractive Parameters Used onPatient Sequence" to "SQ"
        table[0x00240113u] = "Measurement Laterality" to "CS"
        table[0x00240114u] = "Ophthalmic Patient ClinicalInformation Left Eye Sequence" to "SQ"
        table[0x00240115u] = "Ophthalmic Patient ClinicalInformation Right Eye Sequence" to "SQ"
        table[0x00240117u] = "Foveal Point Normative Data Flag" to "CS"
        table[0x00240118u] = "Foveal Point Probability Value" to "FL"
        table[0x00240120u] = "Screening Baseline Measured" to "CS"
        table[0x00240122u] = "Screening Baseline MeasuredSequence" to "SQ"
        table[0x00240124u] = "Screening Baseline Type" to "CS"
        table[0x00240126u] = "Screening Baseline Value" to "FL"
        table[0x00240202u] = "Algorithm Source" to "LO"
        table[0x00240306u] = "Data Set Name" to "LO"
        table[0x00240307u] = "Data Set Version" to "LO"
        table[0x00240308u] = "Data Set Source" to "LO"
        table[0x00240309u] = "Data Set Description" to "LO"
        table[0x00240317u] = "Visual Field Test Reliability GlobalIndex Sequence" to "SQ"
        table[0x00240320u] = "Visual Field Global Results IndexSequence" to "SQ"
        table[0x00240325u] = "Data Observation Sequence" to "SQ"
        table[0x00240338u] = "Index Normals Flag" to "CS"
        table[0x00240341u] = "Index Probability" to "FL"
        table[0x00240344u] = "Index Probability Sequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x0028.
     */
    private fun addAttributeGroup0028() {
        table[0x00280002u] = "Samples per Pixel" to "US"
        table[0x00280003u] = "Samples per Pixel Used" to "US"
        table[0x00280004u] = "Photometric Interpretation" to "CS"
        table[0x00280005u] = "Image Dimensions" to "US" // Retired
        table[0x00280006u] = "Planar Configuration" to "US"
        table[0x00280008u] = "Number of Frames" to "IS"
        table[0x00280009u] = "Frame Increment Pointer" to "AT"
        table[0x0028000Au] = "Frame Dimension Pointer" to "AT"
        table[0x00280010u] = "Rows" to "US"
        table[0x00280011u] = "Columns" to "US"
        table[0x00280012u] = "Planes" to "US" // Retired
        table[0x00280014u] = "Ultrasound Color Data Present" to "US"
        table[0x00280030u] = "Pixel Spacing" to "DS"
        table[0x00280031u] = "Zoom Factor" to "DS"
        table[0x00280032u] = "Zoom Center" to "DS"
        table[0x00280034u] = "Pixel Aspect Ratio" to "IS"
        table[0x00280040u] = "Image Format" to "CS" // Retired
        table[0x00280050u] = "Manipulated Image" to "LO" // Retired
        table[0x00280051u] = "Corrected Image" to "CS"
        table[0x0028005Fu] = "Compression Recognition Code" to "LO" // Retired
        table[0x00280060u] = "Compression Code" to "CS" // Retired
        table[0x00280061u] = "Compression Originator" to "SH" // Retired
        table[0x00280062u] = "Compression Label" to "LO" // Retired
        table[0x00280063u] = "Compression Description" to "SH" // Retired
        table[0x00280065u] = "Compression Sequence" to "CS" // Retired
        table[0x00280066u] = "Compression Step Pointers" to "AT" // Retired
        table[0x00280068u] = "Repeat Interval" to "US" // Retired
        table[0x00280069u] = "Bits Grouped" to "US" // Retired
        table[0x00280070u] = "Perimeter Table" to "US" // Retired
        // add(0x00280071, "Perimeter Value", "US or SS"); //Retired
        table[0x00280080u] = "Predictor Rows" to "US" // Retired
        table[0x00280081u] = "Predictor Columns" to "US" // Retired
        table[0x00280082u] = "Predictor Constants" to "US" // Retired
        table[0x00280090u] = "Blocked Pixels" to "CS" // Retired
        table[0x00280091u] = "Block Rows" to "US" // Retired
        table[0x00280092u] = "Block Columns" to "US" // Retired
        table[0x00280093u] = "Row Overlap" to "US" // Retired
        table[0x00280094u] = "Column Overlap" to "US" // Retired
        table[0x00280100u] = "Bits Allocated" to "US"
        table[0x00280101u] = "Bits Stored" to "US"
        table[0x00280102u] = "High Bit" to "US"
        table[0x00280103u] = "Pixel Representation" to "US"
        // add(0x00280104, "Smallest Valid Pixel Value", "US or SS"); //Retired
        // add(0x00280105, "Largest Valid Pixel Value", "US or SS"); //Retired
        // add(0x00280106, "Smallest Image Pixel Value", "US or SS");
        // add(0x00280107, "Largest Image Pixel Value", "US or SS");
        // add(0x00280108, "Smallest Pixel Value in Series", "US or SS");
        // add(0x00280109, "Largest Pixel Value in Series", "US or SS");
        // add(0x00280110, "Smallest Image Pixel Value inPlane", "US or SS1");
        // //Retired
        // add(0x00280111, "Largest Image Pixel Value in Plane", "US or SS");
        // //Retired
        // add(0x00280120, "Pixel Padding Value", "US or SS");
        // add(0x00280121, "Pixel Padding Range Limit", "US or SS");
        table[0x00280122u] = "Float Pixel Padding Value" to "FL"
        table[0x00280123u] = "Double Float Pixel Padding Value" to "FD"
        table[0x00280124u] = "Float Pixel Padding Range Limit" to "FL"
        table[0x00280125u] = "Double Float Pixel Padding RangeLimit" to "FD"
        table[0x00280200u] = "Image Location" to "US" // Retired
        table[0x00280300u] = "Quality Control Image" to "CS"
        table[0x00280301u] = "Burned In Annotation" to "CS"
        table[0x00280302u] = "Recognizable Visual Features" to "CS"
        table[0x00280303u] = "Longitudinal Temporal InformationModified" to "CS"
        table[0x00280304u] = "Referenced Color Palette InstanceUID" to "UI"
        table[0x00280400u] = "Transform Label" to "LO" // Retired
        table[0x00280401u] = "Transform Version Number" to "LO" // Retired
        table[0x00280402u] = "Number of Transform Steps" to "US" // Retired
        table[0x00280403u] = "Sequence of Compressed Data" to "LO" // Retired
        table[0x00280404u] = "Details of Coefficients" to "AT" // Retired
        table[0x00280700u] = "DCT Label" to "LO" // Retired
        table[0x00280701u] = "Data Block Description" to "CS" // Retired
        table[0x00280702u] = "Data Block" to "AT" // Retired
        table[0x00280710u] = "Normalization Factor Format" to "US" // Retired
        table[0x00280720u] = "Zonal Map Number Format" to "US" // Retired
        table[0x00280721u] = "Zonal Map Location" to "AT" // Retired
        table[0x00280722u] = "Zonal Map Format" to "US" // Retired
        table[0x00280730u] = "Adaptive Map Format" to "US" // Retired
        table[0x00280740u] = "Code Number Format" to "US" // Retired
        table[0x00280A02u] = "Pixel Spacing Calibration Type" to "CS"
        table[0x00280A04u] = "Pixel Spacing CalibrationDescription" to "LO"
        table[0x00281040u] = "Pixel Intensity Relationship" to "CS"
        table[0x00281041u] = "Pixel Intensity Relationship Sign" to "SS"
        table[0x00281050u] = "Window Center" to "DS"
        table[0x00281051u] = "Window Width" to "DS"
        table[0x00281052u] = "Rescale Intercept" to "DS"
        table[0x00281053u] = "Rescale Slope" to "DS"
        table[0x00281054u] = "Rescale Type" to "LO"
        table[0x00281055u] = "Window Center & WidthExplanation" to "LO"
        table[0x00281056u] = "VOI LUT Function" to "CS"
        table[0x00281080u] = "Gray Scale" to "CS" // Retired
        table[0x00281090u] = "Recommended Viewing Mode" to "CS"
        // add(0x00281100, "Gray Lookup Table Descriptor", "US or SS"); //Retired
        // add(0x00281101, "Red Palette Color Lookup TableDescriptor", "US or SS");
        // add(0x00281102, "Green Palette Color Lookup TableDescriptor", "US or
        // SS");
        // add(0x00281103, "Blue Palette Color Lookup TableDescriptor", "US or SS");
        table[0x00281104u] = "Alpha Palette Color Lookup TableDescriptor" to "US"
        // add(0x00281111, "Large Red Palette Color LookupTable Descriptor", "US or
        // SS"); //Retired
        // add(0x00281112, "Large Green Palette Color LookupTable Descriptor", "US
        // or SS"); //Retired
        // add(0x00281113, "Large Blue Palette Color LookupTable Descriptor", "US or
        // SS"); //Retired
        table[0x00281199u] = "Palette Color Lookup Table UID" to "UI"
        // add(0x00281200, "Gray Lookup Table Data", "US or SS or OW"); //Retired
        table[0x00281201u] = "Red Palette Color Lookup TableData" to "OW"
        table[0x00281202u] = "Green Palette Color Lookup TableData" to "OW"
        table[0x00281203u] = "Blue Palette Color Lookup TableData" to "OW"
        table[0x00281204u] = "Alpha Palette Color Lookup TableData" to "OW"
        table[0x00281211u] = "Large Red Palette Color LookupTable Data" to "OW" // Retired
        table[0x00281212u] = "Large Green Palette Color LookupTable Data" to "OW" // Retired
        table[0x00281213u] = "Large Blue Palette Color LookupTable Data" to "OW" // Retired
        table[0x00281214u] = "Large Palette Color Lookup TableUID" to "UI" // Retired
        table[0x00281221u] = "Segmented Red Palette ColorLookup Table Data" to "OW"
        table[0x00281222u] = "Segmented Green Palette ColorLookup Table Data" to "OW"
        table[0x00281223u] = "Segmented Blue Palette ColorLookup Table Data" to "OW"
        table[0x00281300u] = "Breast Implant Present" to "CS"
        table[0x00281350u] = "Partial View" to "CS"
        table[0x00281351u] = "Partial View Description" to "ST"
        table[0x00281352u] = "Partial View Code Sequence" to "SQ"
        table[0x0028135Au] = "Spatial Locations Preserved" to "CS"
        table[0x00281401u] = "Data Frame Assignment Sequence" to "SQ"
        table[0x00281402u] = "Data Path Assignment" to "CS"
        table[0x00281403u] = "Bits Mapped to Color Lookup Table" to "US"
        table[0x00281404u] = "Blending LUT 1 Sequence" to "SQ"
        table[0x00281405u] = "Blending LUT 1 Transfer Function" to "CS"
        table[0x00281406u] = "Blending Weight Constant" to "FD"
        table[0x00281407u] = "Blending Lookup Table Descriptor" to "US"
        table[0x00281408u] = "Blending Lookup Table Data" to "OW"
        table[0x0028140Bu] = "Enhanced Palette Color LookupTable Sequence" to "SQ"
        table[0x0028140Cu] = "Blending LUT 2 Sequence" to "SQ"
        table[0x0028140Du] = "Blending LUT 2 Transfer Function  BlendingLUT2TransferFunction  CS  1 (0028,140E) Data Path ID" to "CS"
        table[0x0028140Fu] = "RGB LUT Transfer Function" to "CS"
        table[0x00281410u] = "Alpha LUT Transfer Function" to "CS"
        table[0x00282000u] = "ICC Profile" to "OB"
        table[0x00282110u] = "Lossy Image Compression" to "CS"
        table[0x00282112u] = "Lossy Image Compression Ratio" to "DS"
        table[0x00282114u] = "Lossy Image Compression Method" to "CS"
        table[0x00283000u] = "Modality LUT Sequence" to "SQ"
        // add(0x00283002, "LUT Descriptor", "US or SS");
        table[0x00283003u] = "LUT Explanation" to "LO"
        table[0x00283004u] = "Modality LUT Type" to "LO"
        // add(0x00283006, "LUT Data", "US or OW");
        table[0x00283010u] = "VOI LUT Sequence" to "SQ"
        table[0x00283110u] = "Softcopy VOI LUT Sequence" to "SQ"
        table[0x00284000u] = "Image Presentation Comments" to "LT" // Retired
        table[0x00285000u] = "Bi-Plane Acquisition Sequence" to "SQ" // Retired
        table[0x00286010u] = "Representative Frame Number" to "US"
        table[0x00286020u] = "Frame Numbers of Interest (FOI)" to "US"
        table[0x00286022u] = "Frame of Interest Description" to "LO"
        table[0x00286023u] = "Frame of Interest Type" to "CS"
        table[0x00286030u] = "Mask Pointer(s)" to "US" // Retired
        table[0x00286040u] = "R Wave Pointer" to "US"
        table[0x00286100u] = "Mask Subtraction Sequence" to "SQ"
        table[0x00286101u] = "Mask Operation" to "CS"
        table[0x00286102u] = "Applicable Frame Range" to "US"
        table[0x00286110u] = "Mask Frame Numbers" to "US"
        table[0x00286112u] = "Contrast Frame Averaging" to "US"
        table[0x00286114u] = "Mask Sub-pixel Shift" to "FL"
        table[0x00286120u] = "TID Offset" to "SS"
        table[0x00286190u] = "Mask Operation Explanation" to "ST"
        table[0x00287000u] = "Equipment Administrator Sequence" to "SQ"
        table[0x00287001u] = "Number of Display Subsystems" to "US"
        table[0x00287002u] = "Current Configuration ID" to "US"
        table[0x00287003u] = "Display Subsystem ID" to "US"
        table[0x00287004u] = "Display Subsystem Name" to "SH"
        table[0x00287005u] = "Display Subsystem Description" to "LO"
        table[0x00287006u] = "System Status" to "CS"
        table[0x00287007u] = "System Status Comment" to "LO"
        table[0x00287008u] = "Target Luminance CharacteristicsSequence" to "SQ"
        table[0x00287009u] = "Luminance Characteristics ID" to "US"
        table[0x0028700Au] = "Display Subsystem ConfigurationSequence" to "SQ"
        table[0x0028700Bu] = "Configuration ID" to "US"
        table[0x0028700Cu] = "Configuration Name" to "SH"
        table[0x0028700Du] = "Configuration Description" to "LO"
        table[0x0028700Eu] = "Referenced Target LuminanceCharacteristics ID" to "US"
        table[0x0028700Fu] = "QA Results Sequence" to "SQ"
        table[0x00287010u] = "Display Subsystem QA ResultsSequence" to "SQ"
        table[0x00287011u] = "Configuration QA ResultsSequence" to "SQ"
        table[0x00287012u] = "Measurement EquipmentSequence" to "SQ"
        table[0x00287013u] = "Measurement Functions" to "CS"
        table[0x00287014u] = "Measurement Equipment Type" to "CS"
        table[0x00287015u] = "Visual Evaluation Result Sequence" to "SQ"
        table[0x00287016u] = "Display Calibration ResultSequence" to "SQ"
        table[0x00287017u] = "DDL Value" to "US"
        table[0x00287018u] = "CIExy White Point" to "FL"
        table[0x00287019u] = "Display Function Type" to "CS"
        table[0x0028701Au] = "Gamma Value" to "FL"
        table[0x0028701Bu] = "Number of Luminance Points" to "US"
        table[0x0028701Cu] = "Luminance Response Sequence" to "SQ"
        table[0x0028701Du] = "Target Minimum Luminance" to "FL"
        table[0x0028701Eu] = "Target Maximum Luminance" to "FL"
        table[0x0028701Fu] = "Luminance Value" to "FL"
        table[0x00287020u] = "Luminance Response Description" to "LO"
        table[0x00287021u] = "White Point Flag" to "CS"
        table[0x00287022u] = "Display Device Type CodeSequence" to "SQ"
        table[0x00287023u] = "Display Subsystem Sequence" to "SQ"
        table[0x00287024u] = "Luminance Result Sequence" to "SQ"
        table[0x00287025u] = "Ambient Light Value Source" to "CS"
        table[0x00287026u] = "Measured Characteristics" to "CS"
        table[0x00287027u] = "Luminance Uniformity ResultSequence" to "SQ"
        table[0x00287028u] = "Visual Evaluation Test Sequence" to "SQ"
        table[0x00287029u] = "Test Result" to "CS"
        table[0x0028702Au] = "Test Result Comment" to "LO"
        table[0x0028702Bu] = "Test Image Validation" to "CS"
        table[0x0028702Cu] = "Test Pattern Code Sequence" to "SQ"
        table[0x0028702Du] = "Measurement Pattern CodeSequence" to "SQ"
        table[0x0028702Eu] = "Visual Evaluation Method CodeSequence" to "SQ"
        table[0x00287FE0u] = "Pixel Data Provider URL" to "UR"
        table[0x00289001u] = "Data Point Rows" to "UL"
        table[0x00289002u] = "Data Point Columns" to "UL"
        table[0x00289003u] = "Signal Domain Columns" to "CS"
        table[0x00289099u] = "Largest Monochrome Pixel Value" to "US" // Retired
        table[0x00289108u] = "Data Representation" to "CS"
        table[0x00289110u] = "Pixel Measures Sequence" to "SQ"
        table[0x00289132u] = "Frame VOI LUT Sequence" to "SQ"
        table[0x00289145u] = "Pixel Value TransformationSequence" to "SQ"
        table[0x00289235u] = "Signal Domain Rows" to "CS"
        table[0x00289411u] = "Display Filter Percentage" to "FL"
        table[0x00289415u] = "Frame Pixel Shift Sequence" to "SQ"
        table[0x00289416u] = "Subtraction Item ID" to "US"
        table[0x00289422u] = "Pixel Intensity Relationship LUTSequence" to "SQ"
        table[0x00289443u] = "Frame Pixel Data PropertiesSequence" to "SQ"
        table[0x00289444u] = "Geometrical Properties" to "CS"
        table[0x00289445u] = "Geometric Maximum Distortion" to "FL"
        table[0x00289446u] = "Image Processing Applied" to "CS"
        table[0x00289454u] = "Mask Selection Mode" to "CS"
        table[0x00289474u] = "LUT Function" to "CS"
        table[0x00289478u] = "Mask Visibility Percentage" to "FL"
        table[0x00289501u] = "Pixel Shift Sequence" to "SQ"
        table[0x00289502u] = "Region Pixel Shift Sequence" to "SQ"
        table[0x00289503u] = "Vertices of the Region" to "SS"
        table[0x00289505u] = "Multi-frame Presentation Sequence" to "SQ"
        table[0x00289506u] = "Pixel Shift Frame Range" to "US"
        table[0x00289507u] = "LUT Frame Range" to "US"
        table[0x00289520u] = "Image to Equipment MappingMatrix" to "DS"
        table[0x00289537u] = "Equipment Coordinate SystemIdentification" to "CS"
    }

    /**
     * Adds attributes of group 0x0032.
     */
    private fun addAttributeGroup0032() {
        table[0x0032000Au] = "Study Status ID" to "CS" // Retired
        table[0x0032000Cu] = "Study Priority ID" to "CS" // Retired
        table[0x00320012u] = "Study ID Issuer" to "LO" // Retired
        table[0x00320032u] = "Study Verified Date" to "DA" // Retired
        table[0x00320033u] = "Study Verified Time" to "TM" // Retired
        table[0x00320034u] = "Study Read Date" to "DA" // Retired
        table[0x00320035u] = "Study Read Time" to "TM" // Retired
        table[0x00321000u] = "Scheduled Study Start Date" to "DA" // Retired
        table[0x00321001u] = "Scheduled Study Start Time" to "TM" // Retired
        table[0x00321010u] = "Scheduled Study Stop Date" to "DA" // Retired
        table[0x00321011u] = "Scheduled Study Stop Time" to "TM" // Retired
        table[0x00321020u] = "Scheduled Study Location" to "LO" // Retired
        table[0x00321021u] = "Scheduled Study Location AE Title" to "AE" // Retired
        table[0x00321030u] = "Reason for Study" to "LO" // Retired
        table[0x00321031u] = "Requesting Physician IdentificationSequence" to "SQ"
        table[0x00321032u] = "Requesting Physician" to "PN"
        table[0x00321033u] = "Requesting Service" to "LO"
        table[0x00321034u] = "Requesting Service CodeSequence" to "SQ"
        table[0x00321040u] = "Study Arrival Date" to "DA" // Retired
        table[0x00321041u] = "Study Arrival Time" to "TM" // Retired
        table[0x00321050u] = "Study Completion Date" to "DA" // Retired
        table[0x00321051u] = "Study Completion Time" to "TM" // Retired
        table[0x00321055u] = "Study Component Status ID" to "CS" // Retired
        table[0x00321060u] = "Requested Procedure Description" to "LO"
        table[0x00321064u] = "Requested Procedure CodeSequence" to "SQ"
        table[0x00321070u] = "Requested Contrast Agent" to "LO"
        table[0x00324000u] = "Study Comments" to "LT" // Retired
    }

    /**
     * Adds attributes of group 0x0038.
     */
    private fun addAttributeGroup0038() {
        table[0x00380004u] = "Referenced Patient Alias Sequence" to "SQ"
        table[0x00380008u] = "Visit Status ID" to "CS"
        table[0x00380010u] = "Admission ID" to "LO"
        table[0x00380011u] = "Issuer of Admission ID" to "LO" // Retired
        table[0x00380014u] = "Issuer of Admission ID Sequence" to "SQ"
        table[0x00380016u] = "Route of Admissions" to "LO"
        table[0x0038001Au] = "Scheduled Admission Date" to "DA" // Retired
        table[0x0038001Bu] = "Scheduled Admission Time" to "TM" // Retired
        table[0x0038001Cu] = "Scheduled Discharge Date" to "DA" // Retired
        table[0x0038001Du] = "Scheduled Discharge Time" to "TM" // Retired
        table[0x0038001Eu] = "Scheduled Patient InstitutionResidence" to "LO" // Retired
        table[0x00380020u] = "Admitting Date" to "DA"
        table[0x00380021u] = "Admitting Time" to "TM"
        table[0x00380030u] = "Discharge Date" to "DA" // Retired
        table[0x00380032u] = "Discharge Time" to "TM" // Retired
        table[0x00380040u] = "Discharge Diagnosis Description" to "LO" // Retired
        table[0x00380044u] = "Discharge Diagnosis CodeSequence" to "SQ" // Retired
        table[0x00380050u] = "Special Needs" to "LO"
        table[0x00380060u] = "Service Episode ID" to "LO"
        table[0x00380061u] = "Issuer of Service Episode ID" to "LO" // Retired
        table[0x00380062u] = "Service Episode Description" to "LO"
        table[0x00380064u] = "Issuer of Service Episode IDSequence" to "SQ"
        table[0x00380100u] = "Pertinent Documents Sequence" to "SQ"
        table[0x00380101u] = "Pertinent Resources Sequence" to "SQ"
        table[0x00380102u] = "Resource Description" to "LO"
        table[0x00380300u] = "Current Patient Location" to "LO"
        table[0x00380400u] = "Patient's Institution Residence" to "LO"
        table[0x00380500u] = "Patient State" to "LO"
        table[0x00380502u] = "Patient Clinical Trial ParticipationSequence" to "SQ"
        table[0x00384000u] = "Visit Comments" to "LT"
    }

    /**
     * Adds attributes of group 0x003A.
     */
    private fun addAttributeGroup003A() {
        table[0x00400001u] = "Scheduled Station AE Title" to "AE"
        table[0x00400002u] = "Scheduled Procedure Step StartDate" to "DA"
        table[0x00400003u] = "Scheduled Procedure Step StartTime" to "TM"
        table[0x00400004u] = "Scheduled Procedure Step EndDate" to "DA"
        table[0x00400005u] = "Scheduled Procedure Step EndTime" to "TM"
        table[0x00400006u] = "Scheduled Performing Physician'sName" to "PN"
        table[0x00400007u] = "Scheduled Procedure StepDescription" to "LO"
        table[0x00400008u] = "Scheduled Protocol CodeSequence" to "SQ"
        table[0x00400009u] = "Scheduled Procedure Step ID" to "SH"
        table[0x0040000Au] = "Stage Code Sequence" to "SQ"
        table[0x0040000Bu] = "Scheduled Performing PhysicianIdentification Sequence" to "SQ"
        table[0x00400010u] = "Scheduled Station Name" to "SH"
        table[0x00400011u] = "Scheduled Procedure StepLocation" to "SH"
        table[0x00400012u] = "Pre-Medication" to "LO"
        table[0x00400020u] = "Scheduled Procedure Step Status" to "CS"
        table[0x00400026u] = "Order Placer Identifier Sequence" to "SQ"
        table[0x00400027u] = "Order Filler Identifier Sequence" to "SQ"
        table[0x00400031u] = "Local Namespace Entity ID" to "UT"
        table[0x00400032u] = "Universal Entity ID" to "UT"
        table[0x00400033u] = "Universal Entity ID Type" to "CS"
        table[0x00400035u] = "Identifier Type Code" to "CS"
        table[0x00400036u] = "Assigning Facility Sequence" to "SQ"
        table[0x00400039u] = "Assigning Jurisdiction CodeSequence" to "SQ"
        // add(0x0040003A, "Assigning Agency or DepartmentCode Sequence", "SQ");
        table[0x00400100u] = "Scheduled Procedure StepSequence" to "SQ"
        table[0x00400220u] = "Referenced Non-Image CompositeSOP Instance Sequence" to "SQ"
        table[0x00400241u] = "Performed Station AE Title" to "AE"
        table[0x00400242u] = "Performed Station Name" to "SH"
        table[0x00400243u] = "Performed Location" to "SH"
        table[0x00400244u] = "Performed Procedure Step StartDate" to "DA"
        table[0x00400245u] = "Performed Procedure Step StartTime" to "TM"
        table[0x00400250u] = "Performed Procedure Step EndDate" to "DA"
        table[0x00400251u] = "Performed Procedure Step EndTime" to "TM"
        table[0x00400252u] = "Performed Procedure Step Status" to "CS"
        table[0x00400253u] = "Performed Procedure Step ID" to "SH"
        table[0x00400254u] = "Performed Procedure StepDescription" to "LO"
        table[0x00400255u] = "Performed Procedure TypeDescription" to "LO"
        table[0x00400260u] = "Performed Protocol CodeSequence" to "SQ"
        table[0x00400261u] = "Performed Protocol Type" to "CS"
        table[0x00400270u] = "Scheduled Step AttributesSequence" to "SQ"
        table[0x00400275u] = "Request Attributes Sequence" to "SQ"
        table[0x00400280u] = "Comments on the PerformedProcedure Step" to "ST"
        table[0x00400281u] = "Performed Procedure StepDiscontinuation Reason CodeSequence" to "SQ"
        table[0x00400293u] = "Quantity Sequence" to "SQ"
        table[0x00400294u] = "Quantity" to "DS"
        table[0x00400295u] = "Measuring Units Sequence" to "SQ"
        table[0x00400296u] = "Billing Item Sequence" to "SQ"
        table[0x00400300u] = "Total Time of Fluoroscopy" to "US"
        table[0x00400301u] = "Total Number of Exposures" to "US"
        table[0x00400302u] = "Entrance Dose" to "US"
        table[0x00400303u] = "Exposed Area" to "US"
        table[0x00400306u] = "Distance Source to Entrance" to "DS"
        table[0x00400307u] = "Distance Source to Support" to "DS" // Retired
        table[0x0040030Eu] = "Exposure Dose Sequence" to "SQ"
        table[0x00400310u] = "Comments on Radiation Dose" to "ST"
        table[0x00400312u] = "X-Ray Output" to "DS"
        table[0x00400314u] = "Half Value Layer" to "DS"
        table[0x00400316u] = "Organ Dose" to "DS"
        table[0x00400318u] = "Organ Exposed" to "CS"
        table[0x00400320u] = "Billing Procedure Step Sequence" to "SQ"
        table[0x00400321u] = "Film Consumption Sequence" to "SQ"
        table[0x00400324u] = "Billing Supplies and DevicesSequence" to "SQ"
        table[0x00400330u] = "Referenced Procedure StepSequence" to "SQ" // Retired
        table[0x00400340u] = "Performed Series Sequence" to "SQ"
        table[0x00400400u] = "Comments on the ScheduledProcedure Step" to "LT"
        table[0x00400440u] = "Protocol Context Sequence" to "SQ"
        table[0x00400441u] = "Content Item Modifier Sequence" to "SQ"
        table[0x00400500u] = "Scheduled Specimen Sequence" to "SQ"
        table[0x0040050Au] = "Specimen Accession Number" to "LO" // Retired
        table[0x00400512u] = "Container Identifier" to "LO"
        table[0x00400513u] = "Issuer of the Container IdentifierSequence" to "SQ"
        table[0x00400515u] = "Alternate Container IdentifierSequence" to "SQ"
        table[0x00400518u] = "Container Type Code Sequence" to "SQ"
        table[0x0040051Au] = "Container Description" to "LO"
        table[0x00400520u] = "Container Component Sequence" to "SQ"
        table[0x00400550u] = "Specimen Sequence" to "SQ" // Retired
        table[0x00400551u] = "Specimen Identifier" to "LO"
        table[0x00400552u] = "Specimen Description Sequence(Trial)" to "SQ" // Retired
        table[0x00400553u] = "Specimen Description (Trial)" to "ST" // Retired
        table[0x00400554u] = "Specimen UID" to "UI"
        table[0x00400555u] = "Acquisition Context Sequence" to "SQ"
        table[0x00400556u] = "Acquisition Context Description" to "ST"
        table[0x0040059Au] = "Specimen Type Code Sequence" to "SQ"
        table[0x00400560u] = "Specimen Description Sequence" to "SQ"
        table[0x00400562u] = "Issuer of the Specimen IdentifierSequence" to "SQ"
        table[0x00400600u] = "Specimen Short Description" to "LO"
        table[0x00400602u] = "Specimen Detailed Description" to "UT"
        table[0x00400610u] = "Specimen Preparation Sequence" to "SQ"
        table[0x00400612u] = "Specimen Preparation StepContent Item Sequence" to "SQ"
        table[0x00400620u] = "Specimen Localization ContentItem Sequence" to "SQ"
        table[0x004006FAu] = "Slide Identifier" to "LO" // Retired
        table[0x0040071Au] = "Image Center Point CoordinatesSequence" to "SQ"
        table[0x0040072Au] = "X Offset in Slide CoordinateSystem" to "DS"
        table[0x0040073Au] = "Y Offset in Slide CoordinateSystem" to "DS"
        table[0x0040074Au] = "Z Offset in Slide CoordinateSystem" to "DS"
        table[0x004008D8u] = "Pixel Spacing Sequence" to "SQ" // Retired
        table[0x004008DAu] = "Coordinate System Axis CodeSequence" to "SQ" // Retired
        table[0x004008EAu] = "Measurement Units CodeSequence" to "SQ"
        table[0x004009F8u] = "Vital Stain Code Sequence (Trial)" to "SQ" // Retired
        table[0x00401001u] = "Requested Procedure ID" to "SH"
        table[0x00401002u] = "Reason for the RequestedProcedure" to "LO"
        table[0x00401003u] = "Requested Procedure Priority" to "SH"
        table[0x00401004u] = "Patient Transport Arrangements" to "LO"
        table[0x00401005u] = "Requested Procedure Location" to "LO"
        table[0x00401006u] = "Placer Order Number / Procedure" to "SH" // Retired
        table[0x00401007u] = "Filler Order Number / Procedure" to "SH" // Retired
        table[0x00401008u] = "Confidentiality Code" to "LO"
        table[0x00401009u] = "Reporting Priority" to "SH"
        table[0x0040100Au] = "Reason for Requested ProcedureCode Sequence" to "SQ"
        table[0x00401010u] = "Names of Intended Recipients ofResults" to "PN"
        table[0x00401011u] = "Intended Recipients of ResultsIdentification Sequence" to "SQ"
        table[0x00401012u] = "Reason For Performed ProcedureCode Sequence" to "SQ"
        table[0x00401060u] = "Requested Procedure Description(Trial)" to "LO" // Retired
        table[0x00401101u] = "Person Identification CodeSequence" to "SQ"
        table[0x00401102u] = "Person's Address" to "ST"
        table[0x00401103u] = "Person's Telephone Numbers" to "LO"
        table[0x00401104u] = "Person's Telecom Information" to "LT"
        table[0x00401400u] = "Requested Procedure Comments" to "LT"
        table[0x00402001u] = "Reason for the Imaging ServiceRequest" to "LO" // Retired
        table[0x00402004u] = "Issue Date of Imaging ServiceRequest" to "DA"
        table[0x00402005u] = "Issue Time of Imaging ServiceRequest" to "TM"
        table[0x00402006u] = "Placer Order Number / ImagingService Request (Retired)" to "SH" // Retired
        table[0x00402007u] = "Filler Order Number / ImagingService Request (Retired)" to "SH" // Retired
        table[0x00402008u] = "Order Entered By" to "PN"
        table[0x00402009u] = "Order Enterer's Location" to "SH"
        table[0x00402010u] = "Order Callback Phone Number" to "SH"
        table[0x00402011u] = "Order Callback TelecomInformation" to "LT"
        table[0x00402016u] = "Placer Order Number / ImagingService Request" to "LO"
        table[0x00402017u] = "Filler Order Number / ImagingService Request" to "LO"
        table[0x00402400u] = "Imaging Service RequestComments" to "LT"
        table[0x00403001u] = "Confidentiality Constraint onPatient Data Description" to "LO"
        table[0x00404001u] = "General Purpose ScheduledProcedure Step Status" to "CS" // Retired
        table[0x00404002u] = "General Purpose PerformedProcedure Step Status" to "CS" // Retired
        table[0x00404003u] = "General Purpose ScheduledProcedure Step Priority" to "CS" // Retired
        table[0x00404004u] = "Scheduled Processing ApplicationsCode Sequence" to "SQ" // Retired
        table[0x00404005u] = "Scheduled Procedure Step StartDateTime" to "DT"
        table[0x00404006u] = "Multiple Copies Flag" to "CS" // Retired
        table[0x00404007u] = "Performed Processing ApplicationsCode Sequence" to "SQ"
        table[0x00404009u] = "Human Performer Code Sequence" to "SQ"
        table[0x00404010u] = "Scheduled Procedure StepModification DateTime" to "DT"
        table[0x00404011u] = "Expected Completion DateTime" to "DT"
        table[0x00404015u] = "Resulting General PurposePerformed Procedure StepsSequence" to "SQ" // Retired
        table[0x00404016u] = "Referenced General PurposeScheduled Procedure StepSequence" to "SQ" // Retired
        table[0x00404018u] = "Scheduled Workitem CodeSequence" to "SQ"
        table[0x00404019u] = "Performed Workitem CodeSequence" to "SQ"
        table[0x00404020u] = "Input Availability Flag" to "CS"
        table[0x00404021u] = "Input Information Sequence" to "SQ"
        table[0x00404022u] = "Relevant Information Sequence" to "SQ" // Retired
        table[0x00404023u] = "Referenced General PurposeScheduled Procedure StepTransaction UID" to "UI" // Retired
        table[0x00404025u] = "Scheduled Station Name CodeSequence" to "SQ"
        table[0x00404026u] = "Scheduled Station Class CodeSequence" to "SQ"
        table[0x00404027u] = "Scheduled Station GeographicLocation Code Sequence" to "SQ"
        table[0x00404028u] = "Performed Station Name CodeSequence" to "SQ"
        table[0x00404029u] = "Performed Station Class CodeSequence" to "SQ"
        table[0x00404030u] = "Performed Station GeographicLocation Code Sequence" to "SQ"
        table[0x00404031u] = "Requested Subsequent WorkitemCode Sequence" to "SQ" // Retired
        table[0x00404032u] = "Non-DICOM Output CodeSequence" to "SQ" // Retired
        table[0x00404033u] = "Output Information Sequence" to "SQ"
        table[0x00404034u] = "Scheduled Human PerformersSequence" to "SQ"
        table[0x00404035u] = "Actual Human PerformersSequence" to "SQ"
        table[0x00404036u] = "Human Performer's Organization" to "LO"
        table[0x00404037u] = "Human Performer's Name" to "PN"
        table[0x00404040u] = "Raw Data Handling" to "CS"
        table[0x00404041u] = "Input Readiness State" to "CS"
        table[0x00404050u] = "Performed Procedure Step StartDateTime" to "DT"
        table[0x00404051u] = "Performed Procedure Step EndDateTime" to "DT"
        table[0x00404052u] = "Procedure Step CancellationDateTime" to "DT"
        table[0x00408302u] = "Entrance Dose in mGy" to "DS"
        table[0x00409092u] = "Parametric Map Frame TypeSequence" to "SQ"
        table[0x00409094u] = "Referenced Image Real WorldValue Mapping Sequence" to "SQ"
        table[0x00409096u] = "Real World Value MappingSequence" to "SQ"
        table[0x00409098u] = "Pixel Value Mapping CodeSequence" to "SQ"
        table[0x00409210u] = "LUT Label" to "SH"
        // add(0x00409211, "Real World Value Last ValueMapped", "US or SS");
        table[0x00409212u] = "Real World Value LUT Data" to "FD"
        // add(0x00409216, "Real World Value First ValueMapped", "US or SS");
        table[0x00409220u] = "Quantity Definition Sequence" to "SQ"
        table[0x00409224u] = "Real World Value Intercept" to "FD"
        table[0x00409225u] = "Real World Value Slope" to "FD"
        table[0x0040A007u] = "Findings Flag (Trial)" to "CS" // Retired
        table[0x0040A010u] = "Relationship Type" to "CS"
        table[0x0040A020u] = "Findings Sequence (Trial)" to "SQ" // Retired
        table[0x0040A021u] = "Findings Group UID (Trial)" to "UI" // Retired
        table[0x0040A022u] = "Referenced Findings Group UID(Trial)" to "UI" // Retired
        table[0x0040A023u] = "Findings Group Recording Date(Trial)" to "DA" // Retired
        table[0x0040A024u] = "Findings Group Recording Time(Trial)" to "TM" // Retired
        table[0x0040A026u] = "Findings Source Category CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A027u] = "Verifying Organization" to "LO"
        table[0x0040A028u] = "Documenting OrganizationIdentifier Code Sequence (Trial)" to "SQ" // Retired
        table[0x0040A030u] = "Verification DateTime" to "DT"
        table[0x0040A032u] = "Observation DateTime" to "DT"
        table[0x0040A040u] = "Value Type" to "CS"
        table[0x0040A043u] = "Concept Name Code Sequence" to "SQ"
        table[0x0040A047u] = "Measurement Precision Description(Trial)" to "LO" // Retired
        table[0x0040A050u] = "Continuity Of Content" to "CS"
        // add(0x0040A057, "Urgency or Priority Alerts (Trial)", "CS"); //Retired
        table[0x0040A060u] = "Sequencing Indicator (Trial)" to "LO" // Retired
        table[0x0040A066u] = "Document Identifier CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A067u] = "Document Author (Trial)" to "PN" // Retired
        table[0x0040A068u] = "Document Author Identifier CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A070u] = "Identifier Code Sequence (Trial)" to "SQ" // Retired
        table[0x0040A073u] = "Verifying Observer Sequence" to "SQ"
        table[0x0040A074u] = "Object Binary Identifier (Trial)" to "OB" // Retired
        table[0x0040A075u] = "Verifying Observer Name" to "PN"
        table[0x0040A076u] = "Documenting Observer IdentifierCode Sequence (Trial)" to "SQ" // Retired
        table[0x0040A078u] = "Author Observer Sequence" to "SQ"
        table[0x0040A07Au] = "Participant Sequence" to "SQ"
        table[0x0040A07Cu] = "Custodial Organization Sequence" to "SQ"
        table[0x0040A080u] = "Participation Type" to "CS"
        table[0x0040A082u] = "Participation DateTime" to "DT"
        table[0x0040A084u] = "Observer Type" to "CS"
        table[0x0040A085u] = "Procedure Identifier CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A088u] = "Verifying Observer IdentificationCode Sequence" to "SQ"
        table[0x0040A089u] = "Object Directory Binary Identifier(Trial)" to "OB" // Retired
        table[0x0040A090u] = "Equivalent CDA DocumentSequence" to "SQ" // Retired
        table[0x0040A0B0u] = "Referenced Waveform Channels" to "US"
        // add(0x0040A110, "Date of Document or VerbalTransaction (Trial)", "DA");
        // //Retired
        table[0x0040A112u] = "Time of Document Creation orVerbal Transaction (Trial)" to "TM" // Retired
        table[0x0040A120u] = "DateTime" to "DT"
        table[0x0040A121u] = "Date" to "DA"
        table[0x0040A122u] = "Time" to "TM"
        table[0x0040A123u] = "Person Name" to "PN"
        table[0x0040A124u] = "UID" to "UI"
        table[0x0040A125u] = "Report Status ID (Trial)" to "CS" // Retired
        table[0x0040A130u] = "Temporal Range Type" to "CS"
        table[0x0040A132u] = "Referenced Sample Positions" to "UL"
        table[0x0040A136u] = "Referenced Frame Numbers" to "US"
        table[0x0040A138u] = "Referenced Time Offsets" to "DS"
        table[0x0040A13Au] = "Referenced DateTime" to "DT"
        table[0x0040A160u] = "Text Value" to "UT"
        table[0x0040A161u] = "Floating Point Value" to "FD"
        table[0x0040A162u] = "Rational Numerator Value" to "SL"
        table[0x0040A163u] = "Rational Denominator Value" to "UL"
        table[0x0040A167u] = "Observation Category CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A168u] = "Concept Code Sequence" to "SQ"
        table[0x0040A16Au] = "Bibliographic Citation (Trial)" to "ST" // Retired
        table[0x0040A170u] = "Purpose of Reference CodeSequence" to "SQ"
        table[0x0040A171u] = "Observation UID" to "UI"
        table[0x0040A172u] = "Referenced Observation UID (Trial)" to "UI" // Retired
        table[0x0040A173u] = "Referenced Observation Class(Trial)" to "CS" // Retired
        table[0x0040A174u] = "Referenced Object ObservationClass (Trial)" to "CS" // Retired
        table[0x0040A180u] = "Annotation Group Number" to "US"
        table[0x0040A192u] = "Observation Date (Trial)" to "DA" // Retired
        table[0x0040A193u] = "Observation Time (Trial)" to "TM" // Retired
        table[0x0040A194u] = "Measurement Automation (Trial)" to "CS" // Retired
        table[0x0040A195u] = "Modifier Code Sequence" to "SQ"
        table[0x0040A224u] = "Identification Description (Trial)" to "ST" // Retired
        table[0x0040A290u] = "Coordinates Set Geometric Type(Trial)" to "CS" // Retired
        table[0x0040A296u] = "Algorithm Code Sequence (Trial)" to "SQ" // Retired
        table[0x0040A297u] = "Algorithm Description (Trial)" to "ST" // Retired
        table[0x0040A29Au] = "Pixel Coordinates Set (Trial)" to "SL" // Retired
        table[0x0040A300u] = "Measured Value Sequence" to "SQ"
        table[0x0040A301u] = "Numeric Value Qualifier CodeSequence" to "SQ"
        table[0x0040A307u] = "Current Observer (Trial)" to "PN" // Retired
        table[0x0040A30Au] = "Numeric Value" to "DS"
        table[0x0040A313u] = "Referenced Accession Sequence(Trial)" to "SQ" // Retired
        table[0x0040A33Au] = "Report Status Comment (Trial)" to "ST" // Retired
        table[0x0040A340u] = "Procedure Context Sequence(Trial)" to "SQ" // Retired
        table[0x0040A352u] = "Verbal Source (Trial)" to "PN" // Retired
        table[0x0040A353u] = "Address (Trial)" to "ST" // Retired
        table[0x0040A354u] = "Telephone Number (Trial)" to "LO" // Retired
        table[0x0040A358u] = "Verbal Source Identifier CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A360u] = "Predecessor Documents Sequence" to "SQ"
        table[0x0040A370u] = "Referenced Request Sequence" to "SQ"
        table[0x0040A372u] = "Performed Procedure CodeSequence" to "SQ"
        table[0x0040A375u] = "Current Requested ProcedureEvidence Sequence" to "SQ"
        table[0x0040A380u] = "Report Detail Sequence (Trial)" to "SQ" // Retired
        table[0x0040A385u] = "Pertinent Other EvidenceSequence" to "SQ"
        table[0x0040A390u] = "HL7 Structured DocumentReference Sequence" to "SQ"
        table[0x0040A402u] = "Observation Subject UID (Trial)" to "UI" // Retired
        table[0x0040A403u] = "Observation Subject Class (Trial)" to "CS" // Retired
        table[0x0040A404u] = "Observation Subject Type CodeSequence (Trial)" to "SQ" // Retired
        table[0x0040A491u] = "Completion Flag" to "CS"
        table[0x0040A492u] = "Completion Flag Description" to "LO"
        table[0x0040A493u] = "Verification Flag" to "CS"
        table[0x0040A494u] = "Archive Requested" to "CS"
        table[0x0040A496u] = "Preliminary Flag" to "CS"
        table[0x0040A504u] = "Content Template Sequence" to "SQ"
        table[0x0040A525u] = "Identical Documents Sequence" to "SQ"
        table[0x0040A600u] = "Observation Subject Context Flag(Trial)" to "CS" // Retired
        table[0x0040A601u] = "Observer Context Flag (Trial)" to "CS" // Retired
        table[0x0040A603u] = "Procedure Context Flag (Trial)" to "CS" // Retired
        table[0x0040A730u] = "Content Sequence" to "SQ"
        table[0x0040A731u] = "Relationship Sequence (Trial)" to "SQ" // Retired
        table[0x0040A732u] = "Relationship Type Code Sequence(Trial)" to "SQ" // Retired
        table[0x0040A744u] = "Language Code Sequence (Trial)" to "SQ" // Retired
        table[0x0040A992u] = "Uniform Resource Locator (Trial)" to "ST" // Retired
        table[0x0040B020u] = "Waveform Annotation Sequence" to "SQ"
        table[0x0040DB00u] = "Template Identifier" to "CS"
        table[0x0040DB06u] = "Template Version" to "DT" // Retired
        table[0x0040DB07u] = "Template Local Version" to "DT" // Retired
        table[0x0040DB0Bu] = "Template Extension Flag" to "CS" // Retired
        table[0x0040DB0Cu] = "Template Extension OrganizationUID" to "UI" // Retired
        table[0x0040DB0Du] = "Template Extension Creator UID" to "UI" // Retired
        table[0x0040DB73u] = "Referenced Content Item Identifier" to "UL"
        table[0x0040E001u] = "HL7 Instance Identifier" to "ST"
        table[0x0040E004u] = "HL7 Document Effective Time" to "DT"
        table[0x0040E006u] = "HL7 Document Type CodeSequence" to "SQ"
        table[0x0040E008u] = "Document Class Code Sequence" to "SQ"
        table[0x0040E010u] = "Retrieve URI" to "UR"
        table[0x0040E011u] = "Retrieve Location UID" to "UI"
        table[0x0040E020u] = "Type of Instances" to "CS"
        table[0x0040E021u] = "DICOM Retrieval Sequence" to "SQ"
        table[0x0040E022u] = "DICOM Media Retrieval Sequence" to "SQ"
        table[0x0040E023u] = "WADO Retrieval Sequence" to "SQ"
        table[0x0040E024u] = "XDS Retrieval Sequence" to "SQ"
        table[0x0040E025u] = "WADO-RS Retrieval Sequence" to "SQ"
        table[0x0040E030u] = "Repository Unique ID" to "UI"
        table[0x0040E031u] = "Home Community ID" to "UI"
    }

    /**
     * Adds attributes of group 0x0042.
     */
    private fun addAttributeGroup0042() {
        table[0x00420010u] = "Document Title" to "ST"
        table[0x00420011u] = "Encapsulated Document" to "OB"
        table[0x00420012u] = "MIME Type of EncapsulatedDocument" to "LO"
        table[0x00420013u] = "Source Instance Sequence" to "SQ"
        table[0x00420014u] = "List of MIME Types" to "LO"
    }

    /**
     * Adds attributes of group 0x0044.
     */
    private fun addAttributeGroup0044() {
        table[0x00440001u] = "Product Package Identifier" to "ST"
        table[0x00440002u] = "Substance Administration Approval" to "CS"
        table[0x00440003u] = "Approval Status FurtherDescription" to "LT"
        table[0x00440004u] = "Approval Status DateTime" to "DT"
        table[0x00440007u] = "Product Type Code Sequence" to "SQ"
        table[0x00440008u] = "Product Name" to "LO"
        table[0x00440009u] = "Product Description" to "LT"
        table[0x0044000Au] = "Product Lot Identifier" to "LO"
        table[0x0044000Bu] = "Product Expiration DateTime" to "DT"
        table[0x00440010u] = "Substance AdministrationDateTime" to "DT"
        table[0x00440011u] = "Substance Administration Notes" to "LO"
        table[0x00440012u] = "Substance Administration DeviceID" to "LO"
        table[0x00440013u] = "Product Parameter Sequence" to "SQ"
        table[0x00440019u] = "Substance AdministrationParameter Sequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x0046.
     */
    private fun addAttributeGroup0046() {
        table[0x00460012u] = "Lens Description" to "LO"
        table[0x00460014u] = "Right Lens Sequence" to "SQ"
        table[0x00460015u] = "Left Lens Sequence" to "SQ"
        table[0x00460016u] = "Unspecified Laterality LensSequence" to "SQ"
        table[0x00460018u] = "Cylinder Sequence" to "SQ"
        table[0x00460028u] = "Prism Sequence" to "SQ"
        table[0x00460030u] = "Horizontal Prism Power" to "FD"
        table[0x00460032u] = "Horizontal Prism Base" to "CS"
        table[0x00460034u] = "Vertical Prism Power" to "FD"
        table[0x00460036u] = "Vertical Prism Base" to "CS"
        table[0x00460038u] = "Lens Segment Type" to "CS"
        table[0x00460040u] = "Optical Transmittance" to "FD"
        table[0x00460042u] = "Channel Width" to "FD"
        table[0x00460044u] = "Pupil Size" to "FD"
        table[0x00460046u] = "Corneal Size" to "FD"
        table[0x00460050u] = "Autorefraction Right Eye Sequence" to "SQ"
        table[0x00460052u] = "Autorefraction Left Eye Sequence" to "SQ"
        table[0x00460060u] = "Distance Pupillary Distance" to "FD"
        table[0x00460062u] = "Near Pupillary Distance" to "FD"
        table[0x00460063u] = "Intermediate Pupillary Distance" to "FD"
        table[0x00460064u] = "Other Pupillary Distance" to "FD"
        table[0x00460070u] = "Keratometry Right Eye Sequence" to "SQ"
        table[0x00460071u] = "Keratometry Left Eye Sequence" to "SQ"
        table[0x00460074u] = "Steep Keratometric Axis Sequence" to "SQ"
        table[0x00460075u] = "Radius of Curvature" to "FD"
        table[0x00460076u] = "Keratometric Power" to "FD"
        table[0x00460077u] = "Keratometric Axis" to "FD"
        table[0x00460080u] = "Flat Keratometric Axis Sequence" to "SQ"
        table[0x00460092u] = "Background Color" to "CS"
        table[0x00460094u] = "Optotype" to "CS"
        table[0x00460095u] = "Optotype Presentation" to "CS"
        table[0x00460097u] = "Subjective Refraction Right EyeSequence" to "SQ"
        table[0x00460098u] = "Subjective Refraction Left EyeSequence" to "SQ"
        table[0x00460100u] = "Add Near Sequence" to "SQ"
        table[0x00460101u] = "Add Intermediate Sequence" to "SQ"
        table[0x00460102u] = "Add Other Sequence" to "SQ"
        table[0x00460104u] = "Add Power" to "FD"
        table[0x00460106u] = "Viewing Distance" to "FD"
        table[0x00460121u] = "Visual Acuity Type Code Sequence" to "SQ"
        table[0x00460122u] = "Visual Acuity Right Eye Sequence" to "SQ"
        table[0x00460123u] = "Visual Acuity Left Eye Sequence" to "SQ"
        table[0x00460124u] = "Visual Acuity Both Eyes OpenSequence" to "SQ"
        table[0x00460125u] = "Viewing Distance Type" to "CS"
        table[0x00460135u] = "Visual Acuity Modifiers" to "SS"
        table[0x00460137u] = "Decimal Visual Acuity" to "FD"
        table[0x00460139u] = "Optotype Detailed Definition" to "LO"
        table[0x00460145u] = "Referenced RefractiveMeasurements Sequence" to "SQ"
        table[0x00460146u] = "Sphere Power" to "FD"
        table[0x00460147u] = "Cylinder Power" to "FD"
        table[0x00460201u] = "Corneal Topography Surface" to "CS"
        table[0x00460202u] = "Corneal Vertex Location" to "FL"
        table[0x00460203u] = "Pupil Centroid X-Coordinate" to "FL"
        table[0x00460204u] = "Pupil Centroid Y-Coordinate" to "FL"
        table[0x00460205u] = "Equivalent Pupil Radius" to "FL"
        table[0x00460207u] = "Corneal Topography Map TypeCode Sequence" to "SQ"
        table[0x00460208u] = "Vertices of the Outline of Pupil" to "IS"
        table[0x00460210u] = "Corneal Topography MappingNormals Sequence" to "SQ"
        table[0x00460211u] = "Maximum Corneal CurvatureSequence" to "SQ"
        table[0x00460212u] = "Maximum Corneal Curvature" to "FL"
        table[0x00460213u] = "Maximum Corneal CurvatureLocation" to "FL"
        table[0x00460215u] = "Minimum Keratometric Sequence" to "SQ"
        table[0x00460218u] = "Simulated Keratometric CylinderSequence" to "SQ"
        table[0x00460220u] = "Average Corneal Power" to "FL"
        table[0x00460224u] = "Corneal I-S Value" to "FL"
        table[0x00460227u] = "Analyzed Area" to "FL"
        table[0x00460230u] = "Surface Regularity Index" to "FL"
        table[0x00460232u] = "Surface Asymmetry Index" to "FL"
        table[0x00460234u] = "Corneal Eccentricity Index" to "FL"
        table[0x00460236u] = "Keratoconus Prediction Index" to "FL"
        table[0x00460238u] = "Decimal Potential Visual Acuity" to "FL"
        table[0x00460242u] = "Corneal Topography Map QualityEvaluation" to "CS"
        table[0x00460244u] = "Source Image Corneal ProcessedData Sequence" to "SQ"
        table[0x00460247u] = "Corneal Point Location" to "FL"
        table[0x00460248u] = "Corneal Point Estimated" to "CS"
        table[0x00460249u] = "Axial Power" to "FL"
        table[0x00460250u] = "Tangential Power" to "FL"
        table[0x00460251u] = "Refractive Power" to "FL"
        table[0x00460252u] = "Relative Elevation" to "FL"
        table[0x00460253u] = "Corneal Wavefront" to "FL"
    }

    /**
     * Adds attributes of group 0x0048.
     */
    private fun addAttributeGroup0048() {
        table[0x00480001u] = "Imaged Volume Width" to "FL"
        table[0x00480002u] = "Imaged Volume Height" to "FL"
        table[0x00480003u] = "Imaged Volume Depth" to "FL"
        table[0x00480006u] = "Total Pixel Matrix Columns" to "UL"
        table[0x00480007u] = "Total Pixel Matrix Rows" to "UL"
        table[0x00480008u] = "Total Pixel Matrix Origin Sequence" to "SQ"
        table[0x00480010u] = "Specimen Label in Image" to "CS"
        table[0x00480011u] = "Focus Method" to "CS"
        table[0x00480012u] = "Extended Depth of Field" to "CS"
        table[0x00480013u] = "Number of Focal Planes" to "US"
        table[0x00480014u] = "Distance Between Focal Planes" to "FL"
        table[0x00480015u] = "Recommended Absent PixelCIELab Value" to "US"
        table[0x00480100u] = "Illuminator Type Code Sequence" to "SQ"
        table[0x00480102u] = "Image Orientation (Slide)" to "DS"
        table[0x00480105u] = "Optical Path Sequence" to "SQ"
        table[0x00480106u] = "Optical Path Identifier" to "SH"
        table[0x00480107u] = "Optical Path Description" to "ST"
        table[0x00480108u] = "Illumination Color Code Sequence" to "SQ"
        table[0x00480110u] = "Specimen Reference Sequence" to "SQ"
        table[0x00480111u] = "Condenser Lens Power" to "DS"
        table[0x00480112u] = "Objective Lens Power" to "DS"
        table[0x00480113u] = "Objective Lens Numerical Aperture" to "DS"
        table[0x00480120u] = "Palette Color Lookup TableSequence" to "SQ"
        table[0x00480200u] = "Referenced Image NavigationSequence" to "SQ"
        table[0x00480201u] = "Top Left Hand Corner of LocalizerArea" to "US"
        table[0x00480202u] = "Bottom Right Hand Corner ofLocalizer Area" to "US"
        table[0x00480207u] = "Optical Path IdentificationSequence" to "SQ"
        table[0x0048021Au] = "Plane Position (Slide) Sequence" to "SQ"
        table[0x0048021Eu] = "Column Position In Total ImagePixel Matrix" to "SL"
        table[0x0048021Fu] = "Row Position In Total Image PixelMatrix" to "SL"
        table[0x00480301u] = "Pixel Origin Interpretation" to "CS"
        table[0x00480001u] = "Imaged Volume Width" to "FL"
        table[0x00480002u] = "Imaged Volume Height" to "FL"
        table[0x00480003u] = "Imaged Volume Depth" to "FL"
        table[0x00480006u] = "Total Pixel Matrix Columns" to "UL"
        table[0x00480007u] = "Total Pixel Matrix Rows" to "UL"
        table[0x00480008u] = "Total Pixel Matrix Origin Sequence" to "SQ"
        table[0x00480010u] = "Specimen Label in Image" to "CS"
        table[0x00480011u] = "Focus Method" to "CS"
        table[0x00480012u] = "Extended Depth of Field" to "CS"
        table[0x00480013u] = "Number of Focal Planes" to "US"
        table[0x00480014u] = "Distance Between Focal Planes" to "FL"
        table[0x00480015u] = "Recommended Absent PixelCIELab Value" to "US"
        table[0x00480100u] = "Illuminator Type Code Sequence" to "SQ"
        table[0x00480102u] = "Image Orientation (Slide)" to "DS"
        table[0x00480105u] = "Optical Path Sequence" to "SQ"
        table[0x00480106u] = "Optical Path Identifier" to "SH"
        table[0x00480107u] = "Optical Path Description" to "ST"
        table[0x00480108u] = "Illumination Color Code Sequence" to "SQ"
        table[0x00480110u] = "Specimen Reference Sequence" to "SQ"
        table[0x00480111u] = "Condenser Lens Power" to "DS"
        table[0x00480112u] = "Objective Lens Power" to "DS"
        table[0x00480113u] = "Objective Lens Numerical Aperture" to "DS"
        table[0x00480120u] = "Palette Color Lookup TableSequence" to "SQ"
        table[0x00480200u] = "Referenced Image NavigationSequence" to "SQ"
        table[0x00480201u] = "Top Left Hand Corner of LocalizerArea" to "US"
        table[0x00480202u] = "Bottom Right Hand Corner ofLocalizer Area" to "US"
        table[0x00480207u] = "Optical Path IdentificationSequence" to "SQ"
        table[0x0048021Au] = "Plane Position (Slide) Sequence" to "SQ"
        table[0x0048021Eu] = "Column Position In Total ImagePixel Matrix" to "SL"
        table[0x0048021Fu] = "Row Position In Total Image PixelMatrix" to "SL"
        table[0x00480301u] = "Pixel Origin Interpretation" to "CS"
    }

    /**
     * Adds attributes of group 0x0050.
     */
    private fun addAttributeGroup0050() {
        table[0x00500004u] = "Calibration Image" to "CS"
        table[0x00500010u] = "Device Sequence" to "SQ"
        table[0x00500012u] = "Container Component Type CodeSequence" to "SQ"
        table[0x00500013u] = "Container Component Thickness" to "FD"
        table[0x00500014u] = "Device Length" to "DS"
        table[0x00500015u] = "Container Component Width" to "FD"
        table[0x00500016u] = "Device Diameter" to "DS"
        table[0x00500017u] = "Device Diameter Units" to "CS"
        table[0x00500018u] = "Device Volume" to "DS"
        table[0x00500019u] = "Inter-Marker Distance" to "DS"
        table[0x0050001Au] = "Container Component Material" to "CS"
        table[0x0050001Bu] = "Container Component ID" to "LO"
        table[0x0050001Cu] = "Container Component Length" to "FD"
        table[0x0050001Du] = "Container Component Diameter" to "FD"
        table[0x0050001Eu] = "Container Component Description" to "LO"
        table[0x00500020u] = "Device Description" to "LO"
    }

    /**
     * Adds attributes of group 0x0052.
     */
    private fun addAttributeGroup0052() {
        table[0x00520001u] = "Contrast/Bolus Ingredient Percentby Volume" to "FL"
        table[0x00520002u] = "OCT Focal Distance" to "FD"
        table[0x00520003u] = "Beam Spot Size" to "FD"
        table[0x00520004u] = "Effective Refractive Index" to "FD"
        table[0x00520006u] = "OCT Acquisition Domain" to "CS"
        table[0x00520007u] = "OCT Optical Center Wavelength" to "FD"
        table[0x00520008u] = "Axial Resolution" to "FD"
        table[0x00520009u] = "Ranging Depth" to "FD"
        table[0x00520011u] = "A-line Rate" to "FD"
        table[0x00520012u] = "A-lines Per Frame" to "US"
        table[0x00520013u] = "Catheter Rotational Rate" to "FD"
        table[0x00520014u] = "A-line Pixel Spacing" to "FD"
        table[0x00520016u] = "Mode of Percutaneous AccessSequence" to "SQ"
        table[0x00520025u] = "Intravascular OCT Frame TypeSequence" to "SQ"
        table[0x00520026u] = "OCT Z Offset Applied" to "CS"
        table[0x00520027u] = "Intravascular Frame ContentSequence" to "SQ"
        table[0x00520028u] = "Intravascular Longitudinal Distance" to "FD"
        table[0x00520029u] = "Intravascular OCT Frame ContentSequence" to "SQ"
        table[0x00520030u] = "OCT Z Offset Correction" to "SS"
        table[0x00520031u] = "Catheter Direction of Rotation" to "CS"
        table[0x00520033u] = "Seam Line Location" to "FD"
        table[0x00520034u] = "First A-line Location" to "FD"
        table[0x00520036u] = "Seam Line Index" to "US"
        table[0x00520038u] = "Number of Padded A-lines" to "US"
        table[0x00520039u] = "Interpolation Type" to "CS"
        table[0x0052003Au] = "Refractive Index Applied" to "CS"
    }

    /**
     * Adds attributes of group 0x0054.
     */
    private fun addAttributeGroup0054() {
        table[0x00540010u] = "Energy Window Vector" to "US"
        table[0x00540011u] = "Number of Energy Windows" to "US"
        table[0x00540012u] = "Energy Window InformationSequence" to "SQ"
        table[0x00540013u] = "Energy Window Range Sequence" to "SQ"
        table[0x00540014u] = "Energy Window Lower Limit" to "DS"
        table[0x00540015u] = "Energy Window Upper Limit" to "DS"
        table[0x00540016u] = "Radiopharmaceutical InformationSequence" to "SQ"
        table[0x00540017u] = "Residual Syringe Counts" to "IS"
        table[0x00540018u] = "Energy Window Name" to "SH"
        table[0x00540020u] = "Detector Vector" to "US"
        table[0x00540021u] = "Number of Detectors" to "US"
        table[0x00540022u] = "Detector Information Sequence" to "SQ"
        table[0x00540030u] = "Phase Vector" to "US"
        table[0x00540031u] = "Number of Phases" to "US"
        table[0x00540032u] = "Phase Information Sequence" to "SQ"
        table[0x00540033u] = "Number of Frames in Phase" to "US"
        table[0x00540036u] = "Phase Delay" to "IS"
        table[0x00540038u] = "Pause Between Frames" to "IS"
        table[0x00540039u] = "Phase Description" to "CS"
        table[0x00540050u] = "Rotation Vector" to "US"
        table[0x00540051u] = "Number of Rotations" to "US"
        table[0x00540052u] = "Rotation Information Sequence" to "SQ"
        table[0x00540053u] = "Number of Frames in Rotation" to "US"
        table[0x00540060u] = "R-R Interval Vector" to "US"
        table[0x00540061u] = "Number of R-R Intervals" to "US"
        table[0x00540062u] = "Gated Information Sequence" to "SQ"
        table[0x00540063u] = "Data Information Sequence" to "SQ"
        table[0x00540070u] = "Time Slot Vector" to "US"
        table[0x00540071u] = "Number of Time Slots" to "US"
        table[0x00540072u] = "Time Slot Information Sequence" to "SQ"
        table[0x00540073u] = "Time Slot Time" to "DS"
        table[0x00540080u] = "Slice Vector" to "US"
        table[0x00540081u] = "Number of Slices" to "US"
        table[0x00540090u] = "Angular View Vector" to "US"
        table[0x00540100u] = "Time Slice Vector" to "US"
        table[0x00540101u] = "Number of Time Slices" to "US"
        table[0x00540200u] = "Start Angle" to "DS"
        table[0x00540202u] = "Type of Detector Motion" to "CS"
        table[0x00540210u] = "Trigger Vector" to "IS"
        table[0x00540211u] = "Number of Triggers in Phase" to "US"
        table[0x00540220u] = "View Code Sequence" to "SQ"
        table[0x00540222u] = "View Modifier Code Sequence" to "SQ"
        table[0x00540300u] = "Radionuclide Code Sequence" to "SQ"
        table[0x00540302u] = "Administration Route CodeSequence" to "SQ"
        table[0x00540304u] = "Radiopharmaceutical CodeSequence" to "SQ"
        table[0x00540306u] = "Calibration Data Sequence" to "SQ"
        table[0x00540308u] = "Energy Window Number" to "US"
        table[0x00540400u] = "Image ID" to "SH"
        table[0x00540410u] = "Patient Orientation Code Sequence" to "SQ"
        table[0x00540412u] = "Patient Orientation Modifier CodeSequence" to "SQ"
        table[0x00540414u] = "Patient Gantry Relationship CodeSequence" to "SQ"
        table[0x00540500u] = "Slice Progression Direction" to "CS"
        table[0x00540501u] = "Scan Progression Direction" to "CS"
        table[0x00541000u] = "Series Type" to "CS"
        table[0x00541001u] = "Units" to "CS"
        table[0x00541002u] = "Counts Source" to "CS"
        table[0x00541004u] = "Reprojection Method" to "CS"
        table[0x00541006u] = "SUV Type" to "CS"
        table[0x00541100u] = "Randoms Correction Method" to "CS"
        table[0x00541101u] = "Attenuation Correction Method" to "LO"
        table[0x00541102u] = "Decay Correction" to "CS"
        table[0x00541103u] = "Reconstruction Method" to "LO"
        table[0x00541104u] = "Detector Lines of Response Used" to "LO"
        table[0x00541105u] = "Scatter Correction Method" to "LO"
        table[0x00541200u] = "Axial Acceptance" to "DS"
        table[0x00541201u] = "Axial Mash" to "IS"
        table[0x00541202u] = "Transverse Mash" to "IS"
        table[0x00541203u] = "Detector Element Size" to "DS"
        table[0x00541210u] = "Coincidence Window Width" to "DS"
        table[0x00541220u] = "Secondary Counts Type" to "CS"
        table[0x00541300u] = "Frame Reference Time" to "DS"
        table[0x00541310u] = "Primary (Prompts) CountsAccumulated" to "IS"
        table[0x00541311u] = "Secondary Counts Accumulated" to "IS"
        table[0x00541320u] = "Slice Sensitivity Factor" to "DS"
        table[0x00541321u] = "Decay Factor" to "DS"
        table[0x00541322u] = "Dose Calibration Factor" to "DS"
        table[0x00541323u] = "Scatter Fraction Factor" to "DS"
        table[0x00541324u] = "Dead Time Factor" to "DS"
        table[0x00541330u] = "Image Index" to "US"
        table[0x00541400u] = "Counts Included" to "CS" // Retired
        table[0x00541401u] = "Dead Time Correction Flag" to "CS" // Retired
    }

    /**
     * Adds attributes of group 0x0060.
     */
    private fun addAttributeGroup0060() {
        table[0x00603000u] = "Histogram Sequence" to "SQ"
        table[0x00603002u] = "Histogram Number of Bins" to "US"
        // add(0x00603004, "Histogram First Bin Value", "US or SS");
        // add(0x00603006, "Histogram Last Bin Value", "US or SS");
        table[0x00603008u] = "Histogram Bin Width" to "US"
        table[0x00603010u] = "Histogram Explanation" to "LO"
        table[0x00603020u] = "Histogram Data" to "UL"
    }

    /**
     * Adds attributes of group 0x0062.
     */
    private fun addAttributeGroup0062() {
        table[0x00620001u] = "Segmentation Type" to "CS"
        table[0x00620002u] = "Segment Sequence" to "SQ"
        table[0x00620003u] = "Segmented Property CategoryCode Sequence" to "SQ"
        table[0x00620004u] = "Segment Number" to "US"
        table[0x00620005u] = "Segment Label" to "LO"
        table[0x00620006u] = "Segment Description" to "ST"
        table[0x00620008u] = "Segment Algorithm Type" to "CS"
        table[0x00620009u] = "Segment Algorithm Name" to "LO"
        table[0x0062000Au] = "Segment Identification Sequence" to "SQ"
        table[0x0062000Bu] = "Referenced Segment Number" to "US"
        table[0x0062000Cu] = "Recommended Display GrayscaleValue" to "US"
        table[0x0062000Du] = "Recommended Display CIELabValue" to "US"
        table[0x0062000Eu] = "Maximum Fractional Value" to "US"
        table[0x0062000Fu] = "Segmented Property Type CodeSequence" to "SQ"
        table[0x00620010u] = "Segmentation Fractional Type" to "CS"
        table[0x00620011u] = "Segmented Property Type ModifierCode Sequence" to "SQ"
        table[0x00620012u] = "Used Segments Sequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x0064.
     */
    private fun addAttributeGroup0064() {
        table[0x00640002u] = "Deformable Registration Sequence" to "SQ"
        table[0x00640003u] = "Source Frame of Reference UID" to "UI"
        table[0x00640005u] = "Deformable Registration GridSequence" to "SQ"
        table[0x00640007u] = "Grid Dimensions" to "UL"
        table[0x00640008u] = "Grid Resolution" to "FD"
        table[0x00640009u] = "Vector Grid Data" to "OF"
        table[0x0064000Fu] = "Pre Deformation MatrixRegistration Sequence" to "SQ"
        table[0x00640010u] = "Post Deformation MatrixRegistration Sequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x0066.
     */
    private fun addAttributeGroup0066() {
        table[0x00660001u] = "Number of Surfaces" to "UL"
        table[0x00660002u] = "Surface Sequence" to "SQ"
        table[0x00660003u] = "Surface Number" to "UL"
        table[0x00660004u] = "Surface Comments" to "LT"
        table[0x00660009u] = "Surface Processing" to "CS"
        table[0x0066000Au] = "Surface Processing Ratio" to "FL"
        table[0x0066000Bu] = "Surface Processing Description" to "LO"
        table[0x0066000Cu] = "Recommended PresentationOpacity" to "FL"
        table[0x0066000Du] = "Recommended Presentation Type" to "CS"
        table[0x0066000Eu] = "Finite Volume" to "CS"
        table[0x00660010u] = "Manifold" to "CS"
        table[0x00660011u] = "Surface Points Sequence" to "SQ"
        table[0x00660012u] = "Surface Points Normals Sequence" to "SQ"
        table[0x00660013u] = "Surface Mesh Primitives Sequence" to "SQ"
        table[0x00660015u] = "Number of Surface Points" to "UL"
        table[0x00660016u] = "Point Coordinates Data" to "OF"
        table[0x00660017u] = "Point Position Accuracy" to "FL"
        table[0x00660018u] = "Mean Point Distance" to "FL"
        table[0x00660019u] = "Maximum Point Distance" to "FL"
        table[0x0066001Au] = "Points Bounding Box Coordinates" to "FL"
        table[0x0066001Bu] = "Axis of Rotation" to "FL"
        table[0x0066001Cu] = "Center of Rotation" to "FL"
        table[0x0066001Eu] = "Number of Vectors" to "UL"
        table[0x0066001Fu] = "Vector Dimensionality" to "US"
        table[0x00660020u] = "Vector Accuracy" to "FL"
        table[0x00660021u] = "Vector Coordinate Data" to "OF"
        table[0x00660023u] = "Triangle Point Index List" to "OW"
        table[0x00660024u] = "Edge Point Index List" to "OW"
        table[0x00660025u] = "Vertex Point Index List" to "OW"
        table[0x00660026u] = "Triangle Strip Sequence" to "SQ"
        table[0x00660027u] = "Triangle Fan Sequence" to "SQ"
        table[0x00660028u] = "Line Sequence" to "SQ"
        table[0x00660029u] = "Primitive Point Index List" to "OW"
        table[0x0066002Au] = "Surface Count" to "UL"
        table[0x0066002Bu] = "Referenced Surface Sequence" to "SQ"
        table[0x0066002Cu] = "Referenced Surface Number" to "UL"
        table[0x0066002Du] = "Segment Surface GenerationAlgorithm Identification Sequence" to "SQ"
        table[0x0066002Eu] = "Segment Surface Source InstanceSequence" to "SQ"
        table[0x0066002Fu] = "Algorithm Family Code Sequence" to "SQ"
        table[0x00660030u] = "Algorithm Name Code Sequence" to "SQ"
        table[0x00660031u] = "Algorithm Version" to "LO"
        table[0x00660032u] = "Algorithm Parameters" to "LT"
        table[0x00660034u] = "Facet Sequence" to "SQ"
        table[0x00660035u] = "Surface Processing AlgorithmIdentification Sequence" to "SQ"
        table[0x00660036u] = "Algorithm Name" to "LO"
        table[0x00660037u] = "Recommended Point Radius" to "FL"
        table[0x00660038u] = "Recommended Line Thickness" to "FL"
        table[0x00660040u] = "Long Primitive Point Index List" to "UL"
        table[0x00660041u] = "Long Triangle Point Index List" to "UL"
        table[0x00660042u] = "Long Edge Point Index List" to "UL"
        table[0x00660043u] = "Long Vertex Point Index List" to "UL"
    }

    /**
     * Adds attributes of group 0x0068.
     */
    private fun addAttributeGroup0068() {
        table[0x00686210u] = "Implant Size" to "LO"
        table[0x00686221u] = "Implant Template Version" to "LO"
        table[0x00686222u] = "Replaced Implant TemplateSequence" to "SQ"
        table[0x00686223u] = "Implant Type" to "CS"
        table[0x00686224u] = "Derivation Implant TemplateSequence" to "SQ"
        table[0x00686225u] = "Original Implant TemplateSequence" to "SQ"
        table[0x00686226u] = "Effective DateTime" to "DT"
        table[0x00686230u] = "Implant Target Anatomy Sequence" to "SQ"
        table[0x00686260u] = "Information From ManufacturerSequence" to "SQ"
        table[0x00686265u] = "Notification From ManufacturerSequence" to "SQ"
        table[0x00686270u] = "Information Issue DateTime" to "DT"
        table[0x00686280u] = "Information Summary" to "ST"
        table[0x006862A0u] = "Implant Regulatory DisapprovalCode Sequence" to "SQ"
        table[0x006862A5u] = "Overall Template Spatial Tolerance" to "FD"
        table[0x006862C0u] = "HPGL Document Sequence" to "SQ"
        table[0x006862D0u] = "HPGL Document ID" to "US"
        table[0x006862D5u] = "HPGL Document Label" to "LO"
        table[0x006862E0u] = "View Orientation Code Sequence" to "SQ"
        table[0x006862F0u] = "View Orientation Modifier" to "FD"
        table[0x006862F2u] = "HPGL Document Scaling" to "FD"
        table[0x00686300u] = "HPGL Document" to "OB"
        table[0x00686310u] = "HPGL Contour Pen Number" to "US"
        table[0x00686320u] = "HPGL Pen Sequence" to "SQ"
        table[0x00686330u] = "HPGL Pen Number" to "US"
        table[0x00686340u] = "HPGL Pen Label" to "LO"
        table[0x00686345u] = "HPGL Pen Description" to "ST"
        table[0x00686346u] = "Recommended Rotation Point" to "FD"
        table[0x00686347u] = "Bounding Rectangle" to "FD"
        table[0x00686350u] = "Implant Template 3D ModelSurface Number" to "US"
        table[0x00686360u] = "Surface Model DescriptionSequence" to "SQ"
        table[0x00686380u] = "Surface Model Label" to "LO"
        table[0x00686390u] = "Surface Model Scaling Factor" to "FD"
        table[0x006863A0u] = "Materials Code Sequence" to "SQ"
        table[0x006863A4u] = "Coating Materials Code Sequence" to "SQ"
        table[0x006863A8u] = "Implant Type Code Sequence" to "SQ"
        table[0x006863ACu] = "Fixation Method Code Sequence" to "SQ"
        table[0x006863B0u] = "Mating Feature Sets Sequence" to "SQ"
        table[0x006863C0u] = "Mating Feature Set ID" to "US"
        table[0x006863D0u] = "Mating Feature Set Label" to "LO"
        table[0x006863E0u] = "Mating Feature Sequence" to "SQ"
        table[0x006863F0u] = "Mating Feature ID" to "US"
        table[0x00686400u] = "Mating Feature Degree of FreedomSequence" to "SQ"
        table[0x00686410u] = "Degree of Freedom ID" to "US"
        table[0x00686420u] = "Degree of Freedom Type" to "CS"
        table[0x00686430u] = "2D Mating Feature CoordinatesSequence" to "SQ"
        table[0x00686440u] = "Referenced HPGL Document ID" to "US"
        table[0x00686450u] = "2D Mating Point" to "FD"
        table[0x00686460u] = "2D Mating Axes" to "FD"
        table[0x00686470u] = "2D Degree of Freedom Sequence" to "SQ"
        table[0x00686490u] = "3D Degree of Freedom Axis" to "FD"
        table[0x006864A0u] = "Range of Freedom" to "FD"
        table[0x006864C0u] = "3D Mating Point" to "FD"
        table[0x006864D0u] = "3D Mating Axes" to "FD"
        table[0x006864F0u] = "2D Degree of Freedom Axis" to "FD"
        table[0x00686500u] = "Planning Landmark PointSequence" to "SQ"
        table[0x00686510u] = "Planning Landmark Line Sequence" to "SQ"
        table[0x00686520u] = "Planning Landmark PlaneSequence" to "SQ"
        table[0x00686530u] = "Planning Landmark ID" to "US"
        table[0x00686540u] = "Planning Landmark Description" to "LO"
        table[0x00686545u] = "Planning Landmark IdentificationCode Sequence" to "SQ"
        table[0x00686550u] = "2D Point Coordinates Sequence" to "SQ"
        table[0x00686560u] = "2D Point Coordinates" to "FD"
        table[0x00686590u] = "3D Point Coordinates" to "FD"
        table[0x006865A0u] = "2D Line Coordinates Sequence" to "SQ"
        table[0x006865B0u] = "2D Line Coordinates" to "FD"
        table[0x006865D0u] = "3D Line Coordinates" to "FD"
        table[0x006865E0u] = "2D Plane Coordinates Sequence" to "SQ"
        table[0x006865F0u] = "2D Plane Intersection" to "FD"
        table[0x00686610u] = "3D Plane Origin" to "FD"
        table[0x00686620u] = "3D Plane Normal" to "FD"
    }

    /**
     * Adds attributes of group 0x0070.
     */
    private fun addAttributeGroup0070() {
        table[0x00700001u] = "Graphic Annotation Sequence" to "SQ"
        table[0x00700002u] = "Graphic Layer" to "CS"
        table[0x00700003u] = "Bounding Box Annotation Units" to "CS"
        table[0x00700004u] = "Anchor Point Annotation Units" to "CS"
        table[0x00700005u] = "Graphic Annotation Units" to "CS"
        table[0x00700006u] = "Unformatted Text Value" to "ST"
        table[0x00700008u] = "Text Object Sequence" to "SQ"
        table[0x00700009u] = "Graphic Object Sequence" to "SQ"
        table[0x00700010u] = "Bounding Box Top Left HandCorner" to "FL"
        table[0x00700011u] = "Bounding Box Bottom Right HandCorner" to "FL"
        table[0x00700012u] = "Bounding Box Text HorizontalJustification" to "CS"
        table[0x00700014u] = "Anchor Point" to "FL"
        table[0x00700015u] = "Anchor Point Visibility" to "CS"
        table[0x00700020u] = "Graphic Dimensions" to "US"
        table[0x00700021u] = "Number of Graphic Points" to "US"
        table[0x00700022u] = "Graphic Data" to "FL"
        table[0x00700023u] = "Graphic Type" to "CS"
        table[0x00700024u] = "Graphic Filled" to "CS"
        table[0x00700040u] = "Image Rotation (Retired)" to "IS" // Retired
        table[0x00700041u] = "Image Horizontal Flip" to "CS"
        table[0x00700042u] = "Image Rotation" to "US"
        table[0x00700050u] = "Displayed Area Top Left HandCorner (Trial)" to "US" // Retired
        table[0x00700051u] = "Displayed Area Bottom Right HandCorner (Trial)" to "US" // Retired
        table[0x00700052u] = "Displayed Area Top Left HandCorner" to "SL"
        table[0x00700053u] = "Displayed Area Bottom Right HandCorner" to "SL"
        table[0x0070005Au] = "Displayed Area SelectionSequence" to "SQ"
        table[0x00700060u] = "Graphic Layer Sequence" to "SQ"
        table[0x00700062u] = "Graphic Layer Order" to "IS"
        table[0x00700066u] = "Graphic Layer RecommendedDisplay Grayscale Value" to "US"
        table[0x00700067u] = "Graphic Layer RecommendedDisplay RGB Value" to "US" // Retired
        table[0x00700068u] = "Graphic Layer Description" to "LO"
        table[0x00700080u] = "Content Label" to "CS"
        table[0x00700081u] = "Content Description" to "LO"
        table[0x00700082u] = "Presentation Creation Date" to "DA"
        table[0x00700083u] = "Presentation Creation Time" to "TM"
        table[0x00700084u] = "Content Creator's Name" to "PN"
        table[0x00700086u] = "Content Creator's IdentificationCode Sequence" to "SQ"
        table[0x00700087u] = "Alternate Content DescriptionSequence" to "SQ"
        table[0x00700100u] = "Presentation Size Mode" to "CS"
        table[0x00700101u] = "Presentation Pixel Spacing" to "DS"
        table[0x00700102u] = "Presentation Pixel Aspect Ratio" to "IS"
        table[0x00700103u] = "Presentation Pixel MagnificationRatio" to "FL"
        table[0x00700207u] = "Graphic Group Label" to "LO"
        table[0x00700208u] = "Graphic Group Description" to "ST"
        table[0x00700209u] = "Compound Graphic Sequence" to "SQ"
        table[0x00700226u] = "Compound Graphic Instance ID" to "UL"
        table[0x00700227u] = "Font Name" to "LO"
        table[0x00700228u] = "Font Name Type" to "CS"
        table[0x00700229u] = "CSS Font Name" to "LO"
        table[0x00700230u] = "Rotation Angle" to "FD"
        table[0x00700231u] = "Text Style Sequence" to "SQ"
        table[0x00700232u] = "Line Style Sequence" to "SQ"
        table[0x00700233u] = "Fill Style Sequence" to "SQ"
        table[0x00700234u] = "Graphic Group Sequence" to "SQ"
        table[0x00700241u] = "Text Color CIELab Value" to "US"
        table[0x00700242u] = "Horizontal Alignment" to "CS"
        table[0x00700243u] = "Vertical Alignment" to "CS"
        table[0x00700244u] = "Shadow Style" to "CS"
        table[0x00700245u] = "Shadow Offset X" to "FL"
        table[0x00700246u] = "Shadow Offset Y" to "FL"
        table[0x00700247u] = "Shadow Color CIELab Value" to "US"
        table[0x00700248u] = "Underlined" to "CS"
        table[0x00700249u] = "Bold" to "CS"
        table[0x00700250u] = "Italic" to "CS"
        table[0x00700251u] = "Pattern On Color CIELab Value" to "US"
        table[0x00700252u] = "Pattern Off Color CIELab Value" to "US"
        table[0x00700253u] = "Line Thickness" to "FL"
        table[0x00700254u] = "Line Dashing Style" to "CS"
        table[0x00700255u] = "Line Pattern" to "UL"
        table[0x00700256u] = "Fill Pattern" to "OB"
        table[0x00700257u] = "Fill Mode" to "CS"
        table[0x00700258u] = "Shadow Opacity" to "FL"
        table[0x00700261u] = "Gap Length" to "FL"
        table[0x00700262u] = "Diameter of Visibility" to "FL"
        table[0x00700273u] = "Rotation Point" to "FL"
        table[0x00700274u] = "Tick Alignment" to "CS"
        table[0x00700278u] = "Show Tick Label" to "CS"
        table[0x00700279u] = "Tick Label Alignment" to "CS"
        table[0x00700282u] = "Compound Graphic Units" to "CS"
        table[0x00700284u] = "Pattern On Opacity" to "FL"
        table[0x00700285u] = "Pattern Off Opacity" to "FL"
        table[0x00700287u] = "Major Ticks Sequence" to "SQ"
        table[0x00700288u] = "Tick Position" to "FL"
        table[0x00700289u] = "Tick Label" to "SH"
        table[0x00700294u] = "Compound Graphic Type" to "CS"
        table[0x00700295u] = "Graphic Group ID" to "UL"
        table[0x00700306u] = "Shape Type" to "CS"
        table[0x00700308u] = "Registration Sequence" to "SQ"
        table[0x00700309u] = "Matrix Registration Sequence" to "SQ"
        table[0x0070030Au] = "Matrix Sequence" to "SQ"
        table[0x0070030Cu] = "Frame of ReferenceTransformation Matrix Type" to "CS"
        table[0x0070030Du] = "Registration Type Code Sequence" to "SQ"
        table[0x0070030Fu] = "Fiducial Description" to "ST"
        table[0x00700310u] = "Fiducial Identifier" to "SH"
        table[0x00700311u] = "Fiducial Identifier Code Sequence" to "SQ"
        table[0x00700312u] = "Contour Uncertainty Radius" to "FD"
        table[0x00700314u] = "Used Fiducials Sequence" to "SQ"
        table[0x00700318u] = "Graphic Coordinates DataSequence" to "SQ"
        table[0x0070031Au] = "Fiducial UID" to "UI"
        table[0x0070031Cu] = "Fiducial Set Sequence" to "SQ"
        table[0x0070031Eu] = "Fiducial Sequence" to "SQ"
        table[0x00700401u] = "Graphic Layer RecommendedDisplay CIELab Value" to "US"
        table[0x00700402u] = "Blending Sequence" to "SQ"
        table[0x00700403u] = "Relative Opacity" to "FL"
        table[0x00700404u] = "Referenced Spatial RegistrationSequence" to "SQ"
        table[0x00700405u] = "Blending Position" to "CS"
    }

    /**
     * Adds attributes of group 0x0072.
     */
    private fun addAttributeGroup0072() {
        table[0x00720002u] = "Hanging Protocol Name" to "SH"
        table[0x00720004u] = "Hanging Protocol Description" to "LO"
        table[0x00720006u] = "Hanging Protocol Level" to "CS"
        table[0x00720008u] = "Hanging Protocol Creator" to "LO"
        table[0x0072000Au] = "Hanging Protocol CreationDateTime" to "DT"
        table[0x0072000Cu] = "Hanging Protocol DefinitionSequence" to "SQ"
        table[0x0072000Eu] = "Hanging Protocol UserIdentification Code Sequence" to "SQ"
        table[0x00720010u] = "Hanging Protocol User GroupName" to "LO"
        table[0x00720012u] = "Source Hanging ProtocolSequence" to "SQ"
        table[0x00720014u] = "Number of Priors Referenced" to "US"
        table[0x00720020u] = "Image Sets Sequence" to "SQ"
        table[0x00720022u] = "Image Set Selector Sequence" to "SQ"
        table[0x00720024u] = "Image Set Selector Usage Flag" to "CS"
        table[0x00720026u] = "Selector Attribute" to "AT"
        table[0x00720028u] = "Selector Value Number" to "US"
        table[0x00720030u] = "Time Based Image Sets Sequence" to "SQ"
        table[0x00720032u] = "Image Set Number" to "US"
        table[0x00720034u] = "Image Set Selector Category" to "CS"
        table[0x00720038u] = "Relative Time" to "US"
        table[0x0072003Au] = "Relative Time Units" to "CS"
        table[0x0072003Cu] = "Abstract Prior Value" to "SS"
        table[0x0072003Eu] = "Abstract Prior Code Sequence" to "SQ"
        table[0x00720040u] = "Image Set Label" to "LO"
        table[0x00720050u] = "Selector Attribute VR" to "CS"
        table[0x00720052u] = "Selector Sequence Pointer" to "AT"
        table[0x00720054u] = "Selector Sequence Pointer PrivateCreator" to "LO"
        table[0x00720056u] = "Selector Attribute Private Creator" to "LO"
        table[0x00720060u] = "Selector AT Value" to "AT"
        table[0x00720062u] = "Selector CS Value" to "CS"
        table[0x00720064u] = "Selector IS Value" to "IS"
        table[0x00720066u] = "Selector LO Value" to "LO"
        table[0x00720068u] = "Selector LT Value" to "LT"
        table[0x0072006Au] = "Selector PN Value" to "PN"
        table[0x0072006Cu] = "Selector SH Value" to "SH"
        table[0x0072006Eu] = "Selector ST Value" to "ST"
        table[0x00720070u] = "Selector UT Value" to "UT"
        table[0x00720072u] = "Selector DS Value" to "DS"
        table[0x00720074u] = "Selector FD Value" to "FD"
        table[0x00720076u] = "Selector FL Value" to "FL"
        table[0x00720078u] = "Selector UL Value" to "UL"
        table[0x0072007Au] = "Selector US Value" to "US"
        table[0x0072007Cu] = "Selector SL Value" to "SL"
        table[0x0072007Eu] = "Selector SS Value" to "SS"
        table[0x0072007Fu] = "Selector UI Value" to "UI"
        table[0x00720080u] = "Selector Code Sequence Value" to "SQ"
        table[0x00720100u] = "Number of Screens" to "US"
        table[0x00720102u] = "Nominal Screen DefinitionSequence" to "SQ"
        table[0x00720104u] = "Number of Vertical Pixels" to "US"
        table[0x00720106u] = "Number of Horizontal Pixels" to "US"
        table[0x00720108u] = "Display Environment SpatialPosition" to "FD"
        table[0x0072010Au] = "Screen Minimum Grayscale BitDepth" to "US"
        table[0x0072010Cu] = "Screen Minimum Color Bit Depth" to "US"
        table[0x0072010Eu] = "Application Maximum Repaint Time" to "US"
        table[0x00720200u] = "Display Sets Sequence" to "SQ"
        table[0x00720202u] = "Display Set Number" to "US"
        table[0x00720203u] = "Display Set Label" to "LO"
        table[0x00720204u] = "Display Set Presentation Group" to "US"
        table[0x00720206u] = "Display Set Presentation GroupDescription" to "LO"
        table[0x00720208u] = "Partial Data Display Handling" to "CS"
        table[0x00720210u] = "Synchronized Scrolling Sequence" to "SQ"
        table[0x00720212u] = "Display Set Scrolling Group" to "US"
        table[0x00720214u] = "Navigation Indicator Sequence" to "SQ"
        table[0x00720216u] = "Navigation Display Set" to "US"
        table[0x00720218u] = "Reference Display Sets" to "US"
        table[0x00720300u] = "Image Boxes Sequence" to "SQ"
        table[0x00720302u] = "Image Box Number" to "US"
        table[0x00720304u] = "Image Box Layout Type" to "CS"
        table[0x00720306u] = "Image Box Tile HorizontalDimension" to "US"
        table[0x00720308u] = "Image Box Tile Vertical Dimension" to "US"
        table[0x00720310u] = "Image Box Scroll Direction" to "CS"
        table[0x00720312u] = "Image Box Small Scroll Type" to "CS"
        table[0x00720314u] = "Image Box Small Scroll Amount" to "US"
        table[0x00720316u] = "Image Box Large Scroll Type" to "CS"
        table[0x00720318u] = "Image Box Large Scroll Amount" to "US"
        table[0x00720320u] = "Image Box Overlap Priority" to "US"
        table[0x00720330u] = "Cine Relative to Real-Time" to "FD"
        table[0x00720400u] = "Filter Operations Sequence" to "SQ"
        table[0x00720402u] = "Filter-by Category" to "CS"
        table[0x00720404u] = "Filter-by Attribute Presence" to "CS"
        table[0x00720406u] = "Filter-by Operator" to "CS"
        table[0x00720420u] = "Structured Display BackgroundCIELab Value" to "US"
        table[0x00720421u] = "Empty Image Box CIELab Value" to "US"
        table[0x00720422u] = "Structured Display Image BoxSequence" to "SQ"
        table[0x00720424u] = "Structured Display Text BoxSequence" to "SQ"
        table[0x00720427u] = "Referenced First Frame Sequence" to "SQ"
        table[0x00720430u] = "Image Box SynchronizationSequence" to "SQ"
        table[0x00720432u] = "Synchronized Image Box List" to "US"
        table[0x00720434u] = "Type of Synchronization" to "CS"
        table[0x00720500u] = "Blending Operation Type" to "CS"
        table[0x00720510u] = "Reformatting Operation Type" to "CS"
        table[0x00720512u] = "Reformatting Thickness" to "FD"
        table[0x00720514u] = "Reformatting Interval" to "FD"
        table[0x00720516u] = "Reformatting Operation Initial ViewDirection" to "CS"
        table[0x00720520u] = "3D Rendering Type" to "CS"
        table[0x00720600u] = "Sorting Operations Sequence" to "SQ"
        table[0x00720602u] = "Sort-by Category" to "CS"
        table[0x00720604u] = "Sorting Direction" to "CS"
        table[0x00720700u] = "Display Set Patient Orientation" to "CS"
        table[0x00720702u] = "VOI Type" to "CS"
        table[0x00720704u] = "Pseudo-Color Type" to "CS"
        table[0x00720705u] = "Pseudo-Color Palette InstanceReference Sequence" to "SQ"
        table[0x00720706u] = "Show Grayscale Inverted" to "CS"
        table[0x00720710u] = "Show Image True Size Flag" to "CS"
        table[0x00720712u] = "Show Graphic Annotation Flag" to "CS"
        table[0x00720714u] = "Show Patient Demographics Flag" to "CS"
        table[0x00720716u] = "Show Acquisition Techniques Flag" to "CS"
        table[0x00720717u] = "Display Set Horizontal Justification" to "CS"
        table[0x00720718u] = "Display Set Vertical Justification" to "CS"
    }

    /**
     * Adds attributes of group 0x0074.
     */
    private fun addAttributeGroup0074() {
        table[0x00740120u] = "Continuation Start Meterset" to "FD"
        table[0x00740121u] = "Continuation End Meterset" to "FD"
        table[0x00741000u] = "Procedure Step State" to "CS"
        table[0x00741002u] = "Procedure Step ProgressInformation Sequence" to "SQ"
        table[0x00741004u] = "Procedure Step Progress" to "DS"
        table[0x00741006u] = "Procedure Step ProgressDescription" to "ST"
        table[0x00741008u] = "Procedure Step CommunicationsURI Sequence" to "SQ"
        table[0x0074100Au] = "Contact URI" to "UR"
        table[0x0074100Cu] = "Contact Display Name" to "LO"
        table[0x0074100Eu] = "Procedure Step DiscontinuationReason Code Sequence" to "SQ"
        table[0x00741020u] = "Beam Task Sequence" to "SQ"
        table[0x00741022u] = "Beam Task Type" to "CS"
        table[0x00741024u] = "Beam Order Index (Trial)" to "IS" // Retired
        table[0x00741025u] = "Autosequence Flag" to "CS"
        table[0x00741026u] = "Table Top Vertical AdjustedPosition" to "FD"
        table[0x00741027u] = "Table Top Longitudinal AdjustedPosition" to "FD"
        table[0x00741028u] = "Table Top Lateral AdjustedPosition" to "FD"
        table[0x0074102Au] = "Patient Support Adjusted Angle" to "FD"
        table[0x0074102Bu] = "Table Top Eccentric AdjustedAngle" to "FD"
        table[0x0074102Cu] = "Table Top Pitch Adjusted Angle" to "FD"
        table[0x0074102Du] = "Table Top Roll Adjusted Angle" to "FD"
        table[0x00741030u] = "Delivery Verification ImageSequence" to "SQ"
        table[0x00741032u] = "Verification Image Timing" to "CS"
        table[0x00741034u] = "Double Exposure Flag" to "CS"
        table[0x00741036u] = "Double Exposure Ordering" to "CS"
        table[0x00741038u] = "Double Exposure Meterset (Trial)" to "DS" // Retired
        table[0x0074103Au] = "Double Exposure Field Delta (Trial)" to "DS" // Retired
        table[0x00741040u] = "Related Reference RT ImageSequence" to "SQ"
        table[0x00741042u] = "General Machine VerificationSequence" to "SQ"
        table[0x00741044u] = "Conventional Machine VerificationSequence" to "SQ"
        table[0x00741046u] = "Ion Machine Verification Sequence" to "SQ"
        table[0x00741048u] = "Failed Attributes Sequence" to "SQ"
        table[0x0074104Au] = "Overridden Attributes Sequence" to "SQ"
        table[0x0074104Cu] = "Conventional Control PointVerification Sequence" to "SQ"
        table[0x0074104Eu] = "Ion Control Point VerificationSequence" to "SQ"
        table[0x00741050u] = "Attribute Occurrence Sequence" to "SQ"
        table[0x00741052u] = "Attribute Occurrence Pointer" to "AT"
        table[0x00741054u] = "Attribute Item Selector" to "UL"
        table[0x00741056u] = "Attribute Occurrence PrivateCreator" to "LO"
        table[0x00741057u] = "Selector Sequence Pointer Items" to "IS"
        table[0x00741200u] = "Scheduled Procedure Step Priority" to "CS"
        table[0x00741202u] = "Worklist Label" to "LO"
        table[0x00741204u] = "Procedure Step Label" to "LO"
        table[0x00741210u] = "Scheduled Processing ParametersSequence" to "SQ"
        table[0x00741212u] = "Performed Processing ParametersSequence" to "SQ"
        table[0x00741216u] = "Unified Procedure Step PerformedProcedure Sequence" to "SQ"
        table[0x00741220u] = "Related Procedure Step Sequence" to "SQ" // Retired
        table[0x00741222u] = "Procedure Step Relationship Type" to "LO" // Retired
        table[0x00741224u] = "Replaced Procedure StepSequence" to "SQ"
        table[0x00741230u] = "Deletion Lock" to "LO"
        table[0x00741234u] = "Receiving AE" to "AE"
        table[0x00741236u] = "Requesting AE" to "AE"
        table[0x00741238u] = "Reason for Cancellation" to "LT"
        table[0x00741242u] = "SCP Status" to "CS"
        table[0x00741244u] = "Subscription List Status" to "CS"
        table[0x00741246u] = "Unified Procedure Step List Status" to "CS"
        table[0x00741324u] = "Beam Order Index" to "UL"
        table[0x00741338u] = "Double Exposure Meterset" to "FD"
        table[0x0074133Au] = "Double Exposure Field Delta" to "FD"
    }

    /**
     * Adds attributes of group 0x0076.
     */
    private fun addAttributeGroup0076() {
        table[0x00760001u] = "Implant Assembly Template Name" to "LO"
        table[0x00760003u] = "Implant Assembly Template Issuer" to "LO"
        table[0x00760006u] = "Implant Assembly TemplateVersion" to "LO"
        table[0x00760008u] = "Replaced Implant AssemblyTemplate Sequence" to "SQ"
        table[0x0076000Au] = "Implant Assembly Template Type" to "CS"
        table[0x0076000Cu] = "Original Implant AssemblyTemplate Sequence" to "SQ"
        table[0x0076000Eu] = "Derivation Implant AssemblyTemplate Sequence" to "SQ"
        table[0x00760010u] = "Implant Assembly Template TargetAnatomy Sequence" to "SQ"
        table[0x00760020u] = "Procedure Type Code Sequence" to "SQ"
        table[0x00760030u] = "Surgical Technique" to "LO"
        table[0x00760032u] = "Component Types Sequence" to "SQ"
        table[0x00760034u] = "Component Type Code Sequence" to "CS"
        table[0x00760036u] = "Exclusive Component Type" to "CS"
        table[0x00760038u] = "Mandatory Component Type" to "CS"
        table[0x00760040u] = "Component Sequence" to "SQ"
        table[0x00760055u] = "Component ID" to "US"
        table[0x00760060u] = "Component Assembly Sequence" to "SQ"
        table[0x00760070u] = "Component 1 Referenced ID" to "US"
        table[0x00760080u] = "Component 1 Referenced MatingFeature Set ID" to "US"
        table[0x00760090u] = "Component 1 Referenced MatingFeature ID" to "US"
        table[0x007600A0u] = "Component 2 Referenced ID" to "US"
        table[0x007600B0u] = "Component 2 Referenced MatingFeature Set ID" to "US"
        table[0x007600C0u] = "Component 2 Referenced MatingFeature ID" to "US"
    }

    /**
     * Adds attributes of group 0x0078.
     */
    private fun addAttributeGroup0078() {
        table[0x00780001u] = "Implant Template Group Name" to "LO"
        table[0x00780010u] = "Implant Template GroupDescription" to "ST"
        table[0x00780020u] = "Implant Template Group Issuer" to "LO"
        table[0x00780024u] = "Implant Template Group Version" to "LO"
        table[0x00780026u] = "Replaced Implant Template GroupSequence" to "SQ"
        table[0x00780028u] = "Implant Template Group TargetAnatomy Sequence" to "SQ"
        table[0x0078002Au] = "Implant Template Group MembersSequence" to "SQ"
        table[0x0078002Eu] = "Implant Template Group MemberID" to "US"
        table[0x00780050u] = "3D Implant Template GroupMember Matching Point" to "FD"
        table[0x00780060u] = "3D Implant Template GroupMember Matching Axes" to "FD"
        table[0x00780070u] = "Implant Template Group MemberMatching 2D CoordinatesSequence" to "SQ"
        table[0x00780090u] = "2D Implant Template GroupMember Matching Point" to "FD"
        table[0x007800A0u] = "2D Implant Template GroupMember Matching Axes" to "FD"
        table[0x007800B0u] = "Implant Template Group VariationDimension Sequence" to "SQ"
        table[0x007800B2u] = "Implant Template Group VariationDimension Name" to "LO"
        table[0x007800B4u] = "Implant Template Group VariationDimension Rank Sequence" to "SQ"
        table[0x007800B6u] = "Referenced Implant TemplateGroup Member ID" to "US"
        table[0x007800B8u] = "Implant Template Group VariationDimension Rank" to "US"
    }

    /**
     * Adds attributes of group 0x0080.
     */
    private fun addAttributeGroup0080() {
        table[0x00800001u] = "Surface Scan Acquisition TypeCode Sequence" to "SQ"
        table[0x00800002u] = "Surface Scan Mode CodeSequence" to "SQ"
        table[0x00800003u] = "Registration Method CodeSequence" to "SQ"
        table[0x00800004u] = "Shot Duration Time" to "FD"
        table[0x00800005u] = "Shot Offset Time" to "FD"
        table[0x00800006u] = "Surface Point Presentation ValueData" to "US"
        table[0x00800007u] = "Surface Point Color CIELab ValueData" to "US"
        table[0x00800008u] = "UV Mapping Sequence" to "SQ"
        table[0x00800009u] = "Texture Label" to "SH"
        table[0x00800010u] = "U Value Data" to "OF"
        table[0x00800011u] = "V Value Data" to "OF"
        table[0x00800012u] = "Referenced Texture Sequence" to "SQ"
        table[0x00800013u] = "Referenced Surface DataSequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x0088.
     */
    private fun addAttributeGroup0088() {
        table[0x00880130u] = "Storage Media File-set ID" to "SH"
        table[0x00880140u] = "Storage Media File-set UID" to "UI"
        table[0x00880200u] = "Icon Image Sequence" to "SQ"
        table[0x00880904u] = "Topic Title" to "LO" // Retired
        table[0x00880906u] = "Topic Subject" to "ST" // Retired
        table[0x00880910u] = "Topic Author" to "LO" // Retired
        table[0x00880912u] = "Topic Keywords" to "LO" // Retired
    }

    /**
     * Adds attributes of group 0x0100.
     */
    private fun addAttributeGroup0100() {
        table[0x01000410u] = "SOP Instance Status" to "CS"
        table[0x01000420u] = "SOP Authorization DateTime" to "DT"
        table[0x01000424u] = "SOP Authorization Comment" to "LT"
        table[0x01000426u] = "Authorization EquipmentCertification Number" to "LO"
    }

    /**
     * Adds attributes of group 0x0400.
     */
    private fun addAttributeGroup0400() {
        table[0x04000005u] = "MAC ID Number" to "US"
        table[0x04000010u] = "MAC Calculation Transfer SyntaxUID" to "UI"
        table[0x04000015u] = "MAC Algorithm" to "CS"
        table[0x04000020u] = "Data Elements Signed" to "AT"
        table[0x04000100u] = "Digital Signature UID" to "UI"
        table[0x04000105u] = "Digital Signature DateTime" to "DT"
        table[0x04000110u] = "Certificate Type" to "CS"
        table[0x04000115u] = "Certificate of Signer" to "OB"
        table[0x04000120u] = "Signature" to "OB"
        table[0x04000305u] = "Certified Timestamp Type" to "CS"
        table[0x04000310u] = "Certified Timestamp" to "OB"
        table[0x04000401u] = "Digital Signature Purpose CodeSequence" to "SQ"
        table[0x04000402u] = "Referenced Digital SignatureSequence" to "SQ"
        table[0x04000403u] = "Referenced SOP Instance MACSequence" to "SQ"
        table[0x04000404u] = "MAC" to "OB"
        table[0x04000500u] = "Encrypted Attributes Sequence" to "SQ"
        table[0x04000510u] = "Encrypted Content Transfer SyntaxUID" to "UI"
        table[0x04000520u] = "Encrypted Content" to "OB"
        table[0x04000550u] = "Modified Attributes Sequence" to "SQ"
        table[0x04000561u] = "Original Attributes Sequence" to "SQ"
        table[0x04000562u] = "Attribute Modification DateTime" to "DT"
        table[0x04000563u] = "Modifying System" to "LO"
        table[0x04000564u] = "Source of Previous Values" to "LO"
        table[0x04000565u] = "Reason for the AttributeModification" to "CS"
    }

    /**
     * Adds attributes of group 0x2000.
     */
    private fun addAttributeGroup2000() {
        table[0x20000010u] = "Number of Copies" to "IS"
        table[0x2000001Eu] = "Printer Configuration Sequence" to "SQ"
        table[0x20000020u] = "Print Priority" to "CS"
        table[0x20000030u] = "Medium Type" to "CS"
        table[0x20000040u] = "Film Destination" to "CS"
        table[0x20000050u] = "Film Session Label" to "LO"
        table[0x20000060u] = "Memory Allocation" to "IS"
        table[0x20000061u] = "Maximum Memory Allocation" to "IS"
        table[0x20000062u] = "Color Image Printing Flag" to "CS" // Retired
        table[0x20000063u] = "Collation Flag" to "CS" // Retired
        table[0x20000065u] = "Annotation Flag" to "CS" // Retired
        table[0x20000067u] = "Image Overlay Flag" to "CS" // Retired
        table[0x20000069u] = "Presentation LUT Flag" to "CS" // Retired
        table[0x2000006Au] = "Image Box Presentation LUT Flag" to "CS" // Retired
        table[0x200000A0u] = "Memory Bit Depth" to "US"
        table[0x200000A1u] = "Printing Bit Depth" to "US"
        table[0x200000A2u] = "Media Installed Sequence" to "SQ"
        table[0x200000A4u] = "Other Media Available Sequence" to "SQ"
        table[0x200000A8u] = "Supported Image Display FormatsSequence" to "SQ"
        table[0x20000500u] = "Referenced Film Box Sequence" to "SQ"
        table[0x20000510u] = "Referenced Stored Print Sequence" to "SQ" // Retired
    }

    /**
     * Adds attributes of group 0x2010.
     */
    private fun addAttributeGroup2010() {
        table[0x20100010u] = "Image Display Format" to "ST"
        table[0x20100030u] = "Annotation Display Format ID" to "CS"
        table[0x20100040u] = "Film Orientation" to "CS"
        table[0x20100050u] = "Film Size ID" to "CS"
        table[0x20100052u] = "Printer Resolution ID" to "CS"
        table[0x20100054u] = "Default Printer Resolution ID" to "CS"
        table[0x20100060u] = "Magnification Type" to "CS"
        table[0x20100080u] = "Smoothing Type" to "CS"
        table[0x201000A6u] = "Default Magnification Type" to "CS"
        table[0x201000A7u] = "Other Magnification TypesAvailable" to "CS"
        table[0x201000A8u] = "Default Smoothing Type" to "CS"
        table[0x201000A9u] = "Other Smoothing Types Available" to "CS"
        table[0x20100100u] = "Border Density" to "CS"
        table[0x20100110u] = "Empty Image Density" to "CS"
        table[0x20100120u] = "Min Density" to "US"
        table[0x20100130u] = "Max Density" to "US"
        table[0x20100140u] = "Trim" to "CS"
        table[0x20100150u] = "Configuration Information" to "ST"
        table[0x20100152u] = "Configuration InformationDescription" to "LT"
        table[0x20100154u] = "Maximum Collated Films" to "IS"
        table[0x2010015Eu] = "Illumination" to "US"
        table[0x20100160u] = "Reflected Ambient Light" to "US"
        table[0x20100376u] = "Printer Pixel Spacing" to "DS"
        table[0x20100500u] = "Referenced Film SessionSequence" to "SQ"
        table[0x20100510u] = "Referenced Image Box Sequence" to "SQ"
        table[0x20100520u] = "Referenced Basic Annotation BoxSequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x2020.
     */
    private fun addAttributeGroup2020() {
        table[0x20200010u] = "Image Box Position" to "US"
        table[0x20200020u] = "Polarity" to "CS"
        table[0x20200030u] = "Requested Image Size" to "DS"
        table[0x20200040u] = "Requested Decimate/CropBehavior" to "CS"
        table[0x20200050u] = "Requested Resolution ID" to "CS"
        table[0x202000A0u] = "Requested Image Size Flag" to "CS"
        table[0x202000A2u] = "Decimate/Crop Result" to "CS"
        table[0x20200110u] = "Basic Grayscale Image Sequence" to "SQ"
        table[0x20200111u] = "Basic Color Image Sequence" to "SQ"
        table[0x20200130u] = "Referenced Image Overlay BoxSequence" to "SQ" // Retired
        table[0x20200140u] = "Referenced VOI LUT BoxSequence" to "SQ" // Retired1
    }

    /**
     * Adds attributes of group 0x2030.
     */
    private fun addAttributeGroup2030() {
        table[0x20300010u] = "Annotation Position" to "US"
        table[0x20300020u] = "Text String" to "LO"
    }

    /**
     * Adds attributes of group 0x2040.
     */
    private fun addAttributeGroup2040() {
        table[0x20400010u] = "Referenced Overlay PlaneSequence" to "SQ" // Retired
        table[0x20400011u] = "Referenced Overlay Plane Groups" to "US" // Retired
        table[0x20400020u] = "Overlay Pixel Data Sequence" to "SQ" // Retired
        table[0x20400060u] = "Overlay Magnification Type" to "CS" // Retired
        table[0x20400070u] = "Overlay Smoothing Type" to "CS" // Retired
        // add(0x20400072, "Overlay or Image Magnification", "CS"); //Retired
        table[0x20400074u] = "Magnify to Number of Columns" to "US" // Retired
        table[0x20400080u] = "Overlay Foreground Density" to "CS" // Retired
        table[0x20400082u] = "Overlay Background Density" to "CS" // Retired
        table[0x20400090u] = "Overlay Mode" to "CS" // Retired
        table[0x20400100u] = "Threshold Density" to "CS" // Retired
        table[0x20400500u] = "Referenced Image Box Sequence(Retired)" to "SQ" // Retired
    }

    /**
     * Adds attributes of group 0x2050.
     */
    private fun addAttributeGroup2050() {
        table[0x20500010u] = "Presentation LUT Sequence" to "SQ"
        table[0x20500020u] = "Presentation LUT Shape" to "CS"
        table[0x20500500u] = "Referenced Presentation LUTSequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x2100.
     */
    private fun addAttributeGroup2100() {
        table[0x21000010u] = "Print Job ID" to "SH" // Retired
        table[0x21000020u] = "Execution Status" to "CS"
        table[0x21000030u] = "Execution Status Info" to "CS"
        table[0x21000040u] = "Creation Date" to "DA"
        table[0x21000050u] = "Creation Time" to "TM"
        table[0x21000070u] = "Originator" to "AE"
        table[0x21000140u] = "Destination AE" to "AE" // Retired
        table[0x21000160u] = "Owner ID" to "SH"
        table[0x21000170u] = "Number of Films" to "IS"
        table[0x21000500u] = "Referenced Print Job Sequence(Pull Stored Print)" to "SQ" // Retired
    }

    /**
     * Adds attributes of group 0x2110.
     */
    private fun addAttributeGroup2110() {
        table[0x21100010u] = "Printer Status" to "CS"
        table[0x21100020u] = "Printer Status Info" to "CS"
        table[0x21100030u] = "Printer Name" to "LO"
        table[0x21100099u] = "Print Queue ID" to "SH" // Retired
    }

    /**
     * Adds attributes of group 0x2120.
     */
    private fun addAttributeGroup2120() {
        table[0x21200010u] = "Queue Status" to "CS" // Retired
        table[0x21200050u] = "Print Job Description Sequence" to "SQ" // Retired
        table[0x21200070u] = "Referenced Print Job Sequence" to "SQ" // Retired
    }

    /**
     * Adds attributes of group 0x2130.
     */
    private fun addAttributeGroup2130() {
        table[0x21300010u] = "Print Management CapabilitiesSequence" to "SQ" // Retired
        table[0x21300015u] = "Printer Characteristics Sequence" to "SQ" // Retired
        table[0x21300030u] = "Film Box Content Sequence" to "SQ" // Retired
        table[0x21300040u] = "Image Box Content Sequence" to "SQ" // Retired
        table[0x21300050u] = "Annotation Content Sequence" to "SQ" // Retired
        table[0x21300060u] = "Image Overlay Box ContentSequence" to "SQ" // Retired
        table[0x21300080u] = "Presentation LUT ContentSequence" to "SQ" // Retired
        table[0x213000A0u] = "Proposed Study Sequence" to "SQ" // Retired
        table[0x213000C0u] = "Original Image Sequence" to "SQ" // Retired
    }

    /**
     * Adds attributes of group 0x2200.
     */
    private fun addAttributeGroup2200() {
        table[0x22000001u] = "Label Using Information ExtractedFrom Instances" to "CS"
        table[0x22000002u] = "Label Text" to "UT"
        table[0x22000003u] = "Label Style Selection" to "CS"
        table[0x22000004u] = "Media Disposition" to "LT"
        table[0x22000005u] = "Barcode Value" to "LT"
        table[0x22000006u] = "Barcode Symbology" to "CS"
        table[0x22000007u] = "Allow Media Splitting" to "CS"
        table[0x22000008u] = "Include Non-DICOM Objects" to "CS"
        table[0x22000009u] = "Include Display Application" to "CS"
        table[0x2200000Au] = "Preserve Composite InstancesAfter Media Creation" to "CS"
        table[0x2200000Bu] = "Total Number of Pieces of MediaCreated" to "US"
        table[0x2200000Cu] = "Requested Media ApplicationProfile" to "LO"
        table[0x2200000Du] = "Referenced Storage MediaSequence" to "SQ"
        table[0x2200000Eu] = "Failure Attributes  FailureAttributes AT  1-n(2200,000F)  Allow Lossy Compression" to "CS"
        table[0x22000020u] = "Request Priority" to "CS"
    }

    /**
     * Adds attributes of group 0x3002.
     */
    private fun addAttributeGroup3002() {
        table[0x30020002u] = "RT Image Label" to "SH"
        table[0x30020003u] = "RT Image Name" to "LO"
        table[0x30020004u] = "RT Image Description" to "ST"
        table[0x3002000Au] = "Reported Values Origin" to "CS"
        table[0x3002000Cu] = "RT Image Plane" to "CS"
        table[0x3002000Du] = "X-Ray Image Receptor Translation" to "DS"
        table[0x3002000Eu] = "X-Ray Image Receptor Angle" to "DS"
        table[0x30020010u] = "RT Image Orientation" to "DS"
        table[0x30020011u] = "Image Plane Pixel Spacing" to "DS"
        table[0x30020012u] = "RT Image Position" to "DS"
        table[0x30020020u] = "Radiation Machine Name" to "SH"
        table[0x30020022u] = "Radiation Machine SAD" to "DS"
        table[0x30020024u] = "Radiation Machine SSD" to "DS"
        table[0x30020026u] = "RT Image SID" to "DS"
        table[0x30020028u] = "Source to Reference ObjectDistance" to "DS"
        table[0x30020029u] = "Fraction Number" to "IS"
        table[0x30020030u] = "Exposure Sequence" to "SQ"
        table[0x30020032u] = "Meterset Exposure" to "DS"
        table[0x30020034u] = "Diaphragm Position" to "DS"
        table[0x30020040u] = "Fluence Map Sequence" to "SQ"
        table[0x30020041u] = "Fluence Data Source" to "CS"
        table[0x30020042u] = "Fluence Data Scale" to "DS"
        table[0x30020050u] = "Primary Fluence Mode Sequence" to "SQ"
        table[0x30020051u] = "Fluence Mode" to "CS"
        table[0x30020052u] = "Fluence Mode ID" to "SH"
    }

    /**
     * Adds attributes of group 0x3004.
     */
    private fun addAttributeGroup3004() {
        table[0x30040001u] = "DVH Type" to "CS"
        table[0x30040002u] = "Dose Units" to "CS"
        table[0x30040004u] = "Dose Type" to "CS"
        table[0x30040005u] = "Spatial Transform of Dose" to "CS"
        table[0x30040006u] = "Dose Comment" to "LO"
        table[0x30040008u] = "Normalization Point" to "DS"
        table[0x3004000Au] = "Dose Summation Type" to "CS"
        table[0x3004000Cu] = "Grid Frame Offset Vector" to "DS"
        table[0x3004000Eu] = "Dose Grid Scaling" to "DS"
        table[0x30040010u] = "RT Dose ROI Sequence" to "SQ"
        table[0x30040012u] = "Dose Value" to "DS"
        table[0x30040014u] = "Tissue Heterogeneity Correction" to "CS"
        table[0x30040040u] = "DVH Normalization Point" to "DS"
        table[0x30040042u] = "DVH Normalization Dose Value" to "DS"
        table[0x30040050u] = "DVH Sequence" to "SQ"
        table[0x30040052u] = "DVH Dose Scaling" to "DS"
        table[0x30040054u] = "DVH Volume Units" to "CS"
        table[0x30040056u] = "DVH Number of Bins" to "IS"
        table[0x30040058u] = "DVH Data" to "DS"
        table[0x30040060u] = "DVH Referenced ROI Sequence" to "SQ"
        table[0x30040062u] = "DVH ROI Contribution Type" to "CS"
        table[0x30040070u] = "DVH Minimum Dose" to "DS"
        table[0x30040072u] = "DVH Maximum Dose" to "DS"
        table[0x30040074u] = "DVH Mean Dose" to "DS"
    }

    /**
     * Adds attributes of group 0x3006.
     */
    private fun addAttributeGroup3006() {
        table[0x30060002u] = "Structure Set Label" to "SH"
        table[0x30060004u] = "Structure Set Name" to "LO"
        table[0x30060006u] = "Structure Set Description" to "ST"
        table[0x30060008u] = "Structure Set Date" to "DA"
        table[0x30060009u] = "Structure Set Time" to "TM"
        table[0x30060010u] = "Referenced Frame of ReferenceSequence" to "SQ"
        table[0x30060012u] = "RT Referenced Study Sequence" to "SQ"
        table[0x30060014u] = "RT Referenced Series Sequence" to "SQ"
        table[0x30060016u] = "Contour Image Sequence" to "SQ"
        table[0x30060018u] = "Predecessor Structure SetSequence" to "SQ"
        table[0x30060020u] = "Structure Set ROI Sequence" to "SQ"
        table[0x30060022u] = "ROI Number" to "IS"
        table[0x30060024u] = "Referenced Frame of ReferenceUID" to "UI"
        table[0x30060026u] = "ROI Name" to "LO"
        table[0x30060028u] = "ROI Description" to "ST"
        table[0x3006002Au] = "ROI Display Color" to "IS"
        table[0x3006002Cu] = "ROI Volume" to "DS"
        table[0x30060030u] = "RT Related ROI Sequence" to "SQ"
        table[0x30060033u] = "RT ROI Relationship" to "CS"
        table[0x30060036u] = "ROI Generation Algorithm" to "CS"
        table[0x30060038u] = "ROI Generation Description" to "LO"
        table[0x30060039u] = "ROI Contour Sequence" to "SQ"
        table[0x30060040u] = "Contour Sequence" to "SQ"
        table[0x30060042u] = "Contour Geometric Type" to "CS"
        table[0x30060044u] = "Contour Slab Thickness" to "DS"
        table[0x30060045u] = "Contour Offset Vector" to "DS"
        table[0x30060046u] = "Number of Contour Points" to "IS"
        table[0x30060048u] = "Contour Number" to "IS"
        table[0x30060049u] = "Attached Contours" to "IS"
        table[0x30060050u] = "Contour Data" to "DS"
        table[0x30060080u] = "RT ROI Observations Sequence" to "SQ"
        table[0x30060082u] = "Observation Number" to "IS"
        table[0x30060084u] = "Referenced ROI Number" to "IS"
        table[0x30060085u] = "ROI Observation Label" to "SH"
        table[0x30060086u] = "RT ROI Identification CodeSequence" to "SQ"
        table[0x30060088u] = "ROI Observation Description" to "ST"
        table[0x300600A0u] = "Related RT ROI ObservationsSequence" to "SQ"
        table[0x300600A4u] = "RT ROI Interpreted Type" to "CS"
        table[0x300600A6u] = "ROI Interpreter" to "PN"
        table[0x300600B0u] = "ROI Physical Properties Sequence" to "SQ"
        table[0x300600B2u] = "ROI Physical Property" to "CS"
        table[0x300600B4u] = "ROI Physical Property Value" to "DS"
        table[0x300600B6u] = "ROI Elemental CompositionSequence" to "SQ"
        table[0x300600B7u] = "ROI Elemental Composition AtomicNumber" to "US"
        table[0x300600B8u] = "ROI Elemental Composition AtomicMass Fraction" to "FL"
        table[0x300600B9u] = "Additional RT ROI IdentificationCode Sequence" to "SQ"
        table[0x300600C0u] = "Frame of Reference RelationshipSequence" to "SQ" // Retired
        table[0x300600C2u] = "Related Frame of Reference UID" to "UI" // Retired
        table[0x300600C4u] = "Frame of ReferenceTransformation Type" to "CS" // Retired
        table[0x300600C6u] = "Frame of ReferenceTransformation Matrix" to "DS"
        table[0x300600C8u] = "Frame of ReferenceTransformation Comment" to "LO"
    }

    /**
     * Adds attributes of group 0x3008.
     */
    private fun addAttributeGroup3008() {
        table[0x30080010u] = "Measured Dose ReferenceSequence" to "SQ"
        table[0x30080012u] = "Measured Dose Description" to "ST"
        table[0x30080014u] = "Measured Dose Type" to "CS"
        table[0x30080016u] = "Measured Dose Value" to "DS"
        table[0x30080020u] = "Treatment Session BeamSequence" to "SQ"
        table[0x30080021u] = "Treatment Session Ion BeamSequence" to "SQ"
        table[0x30080022u] = "Current Fraction Number" to "IS"
        table[0x30080024u] = "Treatment Control Point Date" to "DA"
        table[0x30080025u] = "Treatment Control Point Time" to "TM"
        table[0x3008002Au] = "Treatment Termination Status" to "CS"
        table[0x3008002Bu] = "Treatment Termination Code" to "SH"
        table[0x3008002Cu] = "Treatment Verification Status" to "CS"
        table[0x30080030u] = "Referenced Treatment RecordSequence" to "SQ"
        table[0x30080032u] = "Specified Primary Meterset" to "DS"
        table[0x30080033u] = "Specified Secondary Meterset" to "DS"
        table[0x30080036u] = "Delivered Primary Meterset" to "DS"
        table[0x30080037u] = "Delivered Secondary Meterset" to "DS"
        table[0x3008003Au] = "Specified Treatment Time" to "DS"
        table[0x3008003Bu] = "Delivered Treatment Time" to "DS"
        table[0x30080040u] = "Control Point Delivery Sequence" to "SQ"
        table[0x30080041u] = "Ion Control Point DeliverySequence" to "SQ"
        table[0x30080042u] = "Specified Meterset" to "DS"
        table[0x30080044u] = "Delivered Meterset" to "DS"
        table[0x30080045u] = "Meterset Rate Set" to "FL"
        table[0x30080046u] = "Meterset Rate Delivered" to "FL"
        table[0x30080047u] = "Scan Spot Metersets Delivered" to "FL"
        table[0x30080048u] = "Dose Rate Delivered" to "DS"
        table[0x30080050u] = "Treatment Summary CalculatedDose Reference Sequence" to "SQ"
        table[0x30080052u] = "Cumulative Dose to DoseReference" to "DS"
        table[0x30080054u] = "First Treatment Date" to "DA"
        table[0x30080056u] = "Most Recent Treatment Date" to "DA"
        table[0x3008005Au] = "Number of Fractions Delivered" to "IS"
        table[0x30080060u] = "Override Sequence" to "SQ"
        table[0x30080061u] = "Parameter Sequence Pointer" to "AT"
        table[0x30080062u] = "Override Parameter Pointer" to "AT"
        table[0x30080063u] = "Parameter Item Index" to "IS"
        table[0x30080064u] = "Measured Dose Reference Number" to "IS"
        table[0x30080065u] = "Parameter Pointer" to "AT"
        table[0x30080066u] = "Override Reason" to "ST"
        table[0x30080068u] = "Corrected Parameter Sequence" to "SQ"
        table[0x3008006Au] = "Correction Value" to "FL"
        table[0x30080070u] = "Calculated Dose ReferenceSequence" to "SQ"
        table[0x30080072u] = "Calculated Dose ReferenceNumber" to "IS"
        table[0x30080074u] = "Calculated Dose ReferenceDescription" to "ST"
        table[0x30080076u] = "Calculated Dose Reference DoseValue" to "DS"
        table[0x30080078u] = "Start Meterset" to "DS"
        table[0x3008007Au] = "End Meterset" to "DS"
        table[0x30080080u] = "Referenced Measured DoseReference Sequence" to "SQ"
        table[0x30080082u] = "Referenced Measured DoseReference Number" to "IS"
        table[0x30080090u] = "Referenced Calculated DoseReference Sequence" to "SQ"
        table[0x30080092u] = "Referenced Calculated DoseReference Number" to "IS"
        table[0x300800A0u] = "Beam Limiting Device Leaf PairsSequence" to "SQ"
        table[0x300800B0u] = "Recorded Wedge Sequence" to "SQ"
        table[0x300800C0u] = "Recorded Compensator Sequence" to "SQ"
        table[0x300800D0u] = "Recorded Block Sequence" to "SQ"
        table[0x300800E0u] = "Treatment Summary MeasuredDose Reference Sequence" to "SQ"
        table[0x300800F0u] = "Recorded Snout Sequence" to "SQ"
        table[0x300800F2u] = "Recorded Range Shifter Sequence" to "SQ"
        table[0x300800F4u] = "Recorded Lateral SpreadingDevice Sequence" to "SQ"
        table[0x300800F6u] = "Recorded Range ModulatorSequence" to "SQ"
        table[0x30080100u] = "Recorded Source Sequence" to "SQ"
        table[0x30080105u] = "Source Serial Number" to "LO"
        table[0x30080110u] = "Treatment Session ApplicationSetup Sequence" to "SQ"
        table[0x30080116u] = "Application Setup Check" to "CS"
        table[0x30080120u] = "Recorded Brachy AccessoryDevice Sequence" to "SQ"
        table[0x30080122u] = "Referenced Brachy AccessoryDevice Number" to "IS"
        table[0x30080130u] = "Recorded Channel Sequence" to "SQ"
        table[0x30080132u] = "Specified Channel Total Time" to "DS"
        table[0x30080134u] = "Delivered Channel Total Time" to "DS"
        table[0x30080136u] = "Specified Number of Pulses" to "IS"
        table[0x30080138u] = "Delivered Number of Pulses" to "IS"
        table[0x3008013Au] = "Specified Pulse Repetition Interval" to "DS"
        table[0x3008013Cu] = "Delivered Pulse Repetition Interval" to "DS"
        table[0x30080140u] = "Recorded Source ApplicatorSequence" to "SQ"
        table[0x30080142u] = "Referenced Source ApplicatorNumber" to "IS"
        table[0x30080150u] = "Recorded Channel ShieldSequence" to "SQ"
        table[0x30080152u] = "Referenced Channel ShieldNumber" to "IS"
        table[0x30080160u] = "Brachy Control Point DeliveredSequence" to "SQ"
        table[0x30080162u] = "Safe Position Exit Date" to "DA"
        table[0x30080164u] = "Safe Position Exit Time" to "TM"
        table[0x30080166u] = "Safe Position Return Date" to "DA"
        table[0x30080168u] = "Safe Position Return Time" to "TM"
        table[0x30080171u] = "Pulse Specific Brachy Control PointDelivered Sequence" to "SQ"
        table[0x30080172u] = "Pulse Number" to "US"
        table[0x30080173u] = "Brachy Pulse Control PointDelivered Sequence" to "SQ"
        table[0x30080200u] = "Current Treatment Status" to "CS"
        table[0x30080202u] = "Treatment Status Comment" to "ST"
        table[0x30080220u] = "Fraction Group SummarySequence" to "SQ"
        table[0x30080223u] = "Referenced Fraction Number" to "IS"
        table[0x30080224u] = "Fraction Group Type" to "CS"
        table[0x30080230u] = "Beam Stopper Position" to "CS"
        table[0x30080240u] = "Fraction Status SummarySequence" to "SQ"
        table[0x30080250u] = "Treatment Date" to "DA"
        table[0x30080251u] = "Treatment Time" to "TM"
    }

    /**
     * Adds attributes of group 0x300A.
     */
    private fun addAttributeGroup300A() {
        table[0x300A0002u] = "RT Plan Label" to "SH"
        table[0x300A0003u] = "RT Plan Name" to "LO"
        table[0x300A0004u] = "RT Plan Description" to "ST"
        table[0x300A0006u] = "RT Plan Date" to "DA"
        table[0x300A0007u] = "RT Plan Time" to "TM"
        table[0x300A0009u] = "Treatment Protocols" to "LO"
        table[0x300A000Au] = "Plan Intent" to "CS"
        table[0x300A000Bu] = "Treatment Sites" to "LO"
        table[0x300A000Cu] = "RT Plan Geometry" to "CS"
        table[0x300A000Eu] = "Prescription Description" to "ST"
        table[0x300A0010u] = "Dose Reference Sequence" to "SQ"
        table[0x300A0012u] = "Dose Reference Number" to "IS"
        table[0x300A0013u] = "Dose Reference UID" to "UI"
        table[0x300A0014u] = "Dose Reference Structure Type" to "CS"
        table[0x300A0015u] = "Nominal Beam Energy Unit" to "CS"
        table[0x300A0016u] = "Dose Reference Description" to "LO"
        table[0x300A0018u] = "Dose Reference Point Coordinates" to "DS"
        table[0x300A001Au] = "Nominal Prior Dose" to "DS"
        table[0x300A0020u] = "Dose Reference Type" to "CS"
        table[0x300A0021u] = "Constraint Weight" to "DS"
        table[0x300A0022u] = "Delivery Warning Dose" to "DS"
        table[0x300A0023u] = "Delivery Maximum Dose" to "DS"
        table[0x300A0025u] = "Target Minimum Dose" to "DS"
        table[0x300A0026u] = "Target Prescription Dose" to "DS"
        table[0x300A0027u] = "Target Maximum Dose" to "DS"
        table[0x300A0028u] = "Target Underdose Volume Fraction" to "DS"
        table[0x300A002Au] = "Organ at Risk Full-volume Dose" to "DS"
        table[0x300A002Bu] = "Organ at Risk Limit Dose" to "DS"
        table[0x300A002Cu] = "Organ at Risk Maximum Dose" to "DS"
        table[0x300A002Du] = "Organ at Risk Overdose VolumeFraction" to "DS"
        table[0x300A0040u] = "Tolerance Table Sequence" to "SQ"
        table[0x300A0042u] = "Tolerance Table Number" to "IS"
        table[0x300A0043u] = "Tolerance Table Label" to "SH"
        table[0x300A0044u] = "Gantry Angle Tolerance" to "DS"
        table[0x300A0046u] = "Beam Limiting Device AngleTolerance" to "DS"
        table[0x300A0048u] = "Beam Limiting Device ToleranceSequence" to "SQ"
        table[0x300A004Au] = "Beam Limiting Device PositionTolerance" to "DS"
        table[0x300A004Bu] = "Snout Position Tolerance" to "FL"
        table[0x300A004Cu] = "Patient Support Angle Tolerance" to "DS"
        table[0x300A004Eu] = "Table Top Eccentric AngleTolerance" to "DS"
        table[0x300A004Fu] = "Table Top Pitch Angle Tolerance" to "FL"
        table[0x300A0050u] = "Table Top Roll Angle Tolerance" to "FL"
        table[0x300A0051u] = "Table Top Vertical PositionTolerance" to "DS"
        table[0x300A0052u] = "Table Top Longitudinal PositionTolerance" to "DS"
        table[0x300A0053u] = "Table Top Lateral PositionTolerance" to "DS"
        table[0x300A0055u] = "RT Plan Relationship" to "CS"
        table[0x300A0070u] = "Fraction Group Sequence" to "SQ"
        table[0x300A0071u] = "Fraction Group Number" to "IS"
        table[0x300A0072u] = "Fraction Group Description" to "LO"
        table[0x300A0078u] = "Number of Fractions Planned" to "IS"
        table[0x300A0079u] = "Number of Fraction Pattern DigitsPer Day" to "IS"
        table[0x300A007Au] = "Repeat Fraction Cycle Length" to "IS"
        table[0x300A007Bu] = "Fraction Pattern" to "LT"
        table[0x300A0080u] = "Number of Beams" to "IS"
        table[0x300A0082u] = "Beam Dose Specification Point" to "DS"
        table[0x300A0084u] = "Beam Dose" to "DS"
        table[0x300A0086u] = "Beam Meterset" to "DS"
        table[0x300A0088u] = "Beam Dose Point Depth" to "FL" // Retired
        table[0x300A0089u] = "Beam Dose Point Equivalent Depth" to "FL" // Retired
        table[0x300A008Au] = "Beam Dose Point SSD" to "FL" // Retired
        table[0x300A008Bu] = "Beam Dose Meaning" to "CS"
        table[0x300A008Cu] = "Beam Dose Verification ControlPoint Sequence" to "SQ"
        table[0x300A008Du] = "Average Beam Dose Point Depth" to "FL"
        table[0x300A008Eu] = "Average Beam Dose PointEquivalent Depth" to "FL"
        table[0x300A008Fu] = "Average Beam Dose Point SSD" to "FL"
        table[0x300A00A0u] = "Number of Brachy ApplicationSetups" to "IS"
        table[0x300A00A2u] = "Brachy Application Setup DoseSpecification Point" to "DS"
        table[0x300A00A4u] = "Brachy Application Setup Dose" to "DS"
        table[0x300A00B0u] = "Beam Sequence" to "SQ"
        table[0x300A00B2u] = "Treatment Machine Name" to "SH"
        table[0x300A00B3u] = "Primary Dosimeter Unit" to "CS"
        table[0x300A00B4u] = "Source-Axis Distance" to "DS"
        table[0x300A00B6u] = "Beam Limiting Device Sequence" to "SQ"
        table[0x300A00B8u] = "RT Beam Limiting Device Type" to "CS"
        table[0x300A00BAu] = "Source to Beam Limiting DeviceDistance" to "DS"
        table[0x300A00BBu] = "Isocenter to Beam Limiting DeviceDistance" to "FL"
        table[0x300A00BCu] = "Number of Leaf/Jaw Pairs" to "IS"
        table[0x300A00BEu] = "Leaf Position Boundaries" to "DS"
        table[0x300A00C0u] = "Beam Number" to "IS"
        table[0x300A00C2u] = "Beam Name" to "LO"
        table[0x300A00C3u] = "Beam Description" to "ST"
        table[0x300A00C4u] = "Beam Type" to "CS"
        table[0x300A00C5u] = "Beam Delivery Duration Limit" to "FD"
        table[0x300A00C6u] = "Radiation Type" to "CS"
        table[0x300A00C7u] = "High-Dose Technique Type" to "CS"
        table[0x300A00C8u] = "Reference Image Number" to "IS"
        table[0x300A00CAu] = "Planned Verification ImageSequence" to "SQ"
        table[0x300A00CCu] = "Imaging Device-SpecificAcquisition Parameters" to "LO"
        table[0x300A00CEu] = "Treatment Delivery Type" to "CS"
        table[0x300A00D0u] = "Number of Wedges" to "IS"
        table[0x300A00D1u] = "Wedge Sequence" to "SQ"
        table[0x300A00D2u] = "Wedge Number" to "IS"
        table[0x300A00D3u] = "Wedge Type" to "CS"
        table[0x300A00D4u] = "Wedge ID" to "SH"
        table[0x300A00D5u] = "Wedge Angle" to "IS"
        table[0x300A00D6u] = "Wedge Factor" to "DS"
        table[0x300A00D7u] = "Total Wedge TrayWater-Equivalent Thickness" to "FL"
        table[0x300A00D8u] = "Wedge Orientation" to "DS"
        table[0x300A00D9u] = "Isocenter to Wedge Tray Distance" to "FL"
        table[0x300A00DAu] = "Source to Wedge Tray Distance" to "DS"
        table[0x300A00DBu] = "Wedge Thin Edge Position" to "FL"
        table[0x300A00DCu] = "Bolus ID" to "SH"
        table[0x300A00DDu] = "Bolus Description" to "ST"
        table[0x300A00DEu] = "Effective Wedge Angle" to "DS"
        table[0x300A00E0u] = "Number of Compensators" to "IS"
        table[0x300A00E1u] = "Material ID" to "SH"
        table[0x300A00E2u] = "Total Compensator Tray Factor" to "DS"
        table[0x300A00E3u] = "Compensator Sequence" to "SQ"
        table[0x300A00E4u] = "Compensator Number" to "IS"
        table[0x300A00E5u] = "Compensator ID" to "SH"
        table[0x300A00E6u] = "Source to Compensator TrayDistance" to "DS"
        table[0x300A00E7u] = "Compensator Rows" to "IS"
        table[0x300A00E8u] = "Compensator Columns" to "IS"
        table[0x300A00E9u] = "Compensator Pixel Spacing" to "DS"
        table[0x300A00EAu] = "Compensator Position" to "DS"
        table[0x300A00EBu] = "Compensator Transmission Data" to "DS"
        table[0x300A00ECu] = "Compensator Thickness Data" to "DS"
        table[0x300A00EDu] = "Number of Boli" to "IS"
        table[0x300A00EEu] = "Compensator Type" to "CS"
        table[0x300A00EFu] = "Compensator Tray ID" to "SH"
        table[0x300A00F0u] = "Number of Blocks" to "IS"
        table[0x300A00F2u] = "Total Block Tray Factor" to "DS"
        table[0x300A00F3u] = "Total Block Tray Water-EquivalentThickness" to "FL"
        table[0x300A00F4u] = "Block Sequence" to "SQ"
        table[0x300A00F5u] = "Block Tray ID" to "SH"
        table[0x300A00F6u] = "Source to Block Tray Distance" to "DS"
        table[0x300A00F7u] = "Isocenter to Block Tray Distance" to "FL"
        table[0x300A00F8u] = "Block Type" to "CS"
        table[0x300A00F9u] = "Accessory Code" to "LO"
        table[0x300A00FAu] = "Block Divergence" to "CS"
        table[0x300A00FBu] = "Block Mounting Position" to "CS"
        table[0x300A00FCu] = "Block Number" to "IS"
        table[0x300A00FEu] = "Block Name" to "LO"
        table[0x300A0100u] = "Block Thickness" to "DS"
        table[0x300A0102u] = "Block Transmission" to "DS"
        table[0x300A0104u] = "Block Number of Points" to "IS"
        table[0x300A0106u] = "Block Data" to "DS"
        table[0x300A0107u] = "Applicator Sequence" to "SQ"
        table[0x300A0108u] = "Applicator ID" to "SH"
        table[0x300A0109u] = "Applicator Type" to "CS"
        table[0x300A010Au] = "Applicator Description" to "LO"
        table[0x300A010Cu] = "Cumulative Dose ReferenceCoefficient" to "DS"
        table[0x300A010Eu] = "Final Cumulative Meterset Weight" to "DS"
        table[0x300A0110u] = "Number of Control Points" to "IS"
        table[0x300A0111u] = "Control Point Sequence" to "SQ"
        table[0x300A0112u] = "Control Point Index" to "IS"
        table[0x300A0114u] = "Nominal Beam Energy" to "DS"
        table[0x300A0115u] = "Dose Rate Set" to "DS"
        table[0x300A0116u] = "Wedge Position Sequence" to "SQ"
        table[0x300A0118u] = "Wedge Position" to "CS"
        table[0x300A011Au] = "Beam Limiting Device PositionSequence" to "SQ"
        table[0x300A011Cu] = "Leaf/Jaw Positions" to "DS"
        table[0x300A011Eu] = "Gantry Angle" to "DS"
        table[0x300A011Fu] = "Gantry Rotation Direction" to "CS"
        table[0x300A0120u] = "Beam Limiting Device Angle" to "DS"
        table[0x300A0121u] = "Beam Limiting Device RotationDirection" to "CS"
        table[0x300A0122u] = "Patient Support Angle" to "DS"
        table[0x300A0123u] = "Patient Support Rotation Direction" to "CS"
        table[0x300A0124u] = "Table Top Eccentric Axis Distance" to "DS"
        table[0x300A0125u] = "Table Top Eccentric Angle" to "DS"
        table[0x300A0126u] = "Table Top Eccentric RotationDirection" to "CS"
        table[0x300A0128u] = "Table Top Vertical Position" to "DS"
        table[0x300A0129u] = "Table Top Longitudinal Position" to "DS"
        table[0x300A012Au] = "Table Top Lateral Position" to "DS"
        table[0x300A012Cu] = "Isocenter Position" to "DS"
        table[0x300A012Eu] = "Surface Entry Point" to "DS"
        table[0x300A0130u] = "Source to Surface Distance" to "DS"
        table[0x300A0131u] = "Average Beam Dose Point Sourceto External Contour SurfaceDistance" to "FL"
        table[0x300A0132u] = "Source to External ContourDistance" to "FL"
        table[0x300A0133u] = "External Contour Entry Point" to "FL"
        table[0x300A0134u] = "Cumulative Meterset Weight" to "DS"
        table[0x300A0140u] = "Table Top Pitch Angle" to "FL"
        table[0x300A0142u] = "Table Top Pitch Rotation Direction" to "CS"
        table[0x300A0144u] = "Table Top Roll Angle" to "FL"
        table[0x300A0146u] = "Table Top Roll Rotation Direction" to "CS"
        table[0x300A0148u] = "Head Fixation Angle" to "FL"
        table[0x300A014Au] = "Gantry Pitch Angle" to "FL"
        table[0x300A014Cu] = "Gantry Pitch Rotation Direction" to "CS"
        table[0x300A014Eu] = "Gantry Pitch Angle Tolerance" to "FL"
        table[0x300A0180u] = "Patient Setup Sequence" to "SQ"
        table[0x300A0182u] = "Patient Setup Number" to "IS"
        table[0x300A0183u] = "Patient Setup Label" to "LO"
        table[0x300A0184u] = "Patient Additional Position" to "LO"
        table[0x300A0190u] = "Fixation Device Sequence" to "SQ"
        table[0x300A0192u] = "Fixation Device Type" to "CS"
        table[0x300A0194u] = "Fixation Device Label" to "SH"
        table[0x300A0196u] = "Fixation Device Description" to "ST"
        table[0x300A0198u] = "Fixation Device Position" to "SH"
        table[0x300A0199u] = "Fixation Device Pitch Angle" to "FL"
        table[0x300A019Au] = "Fixation Device Roll Angle" to "FL"
        table[0x300A01A0u] = "Shielding Device Sequence" to "SQ"
        table[0x300A01A2u] = "Shielding Device Type" to "CS"
        table[0x300A01A4u] = "Shielding Device Label" to "SH"
        table[0x300A01A6u] = "Shielding Device Description" to "ST"
        table[0x300A01A8u] = "Shielding Device Position" to "SH"
        table[0x300A01B0u] = "Setup Technique" to "CS"
        table[0x300A01B2u] = "Setup Technique Description" to "ST"
        table[0x300A01B4u] = "Setup Device Sequence" to "SQ"
        table[0x300A01B6u] = "Setup Device Type" to "CS"
        table[0x300A01B8u] = "Setup Device Label" to "SH"
        table[0x300A01BAu] = "Setup Device Description" to "ST"
        table[0x300A01BCu] = "Setup Device Parameter" to "DS"
        table[0x300A01D0u] = "Setup Reference Description" to "ST"
        table[0x300A01D2u] = "Table Top Vertical SetupDisplacement" to "DS"
        table[0x300A01D4u] = "Table Top Longitudinal SetupDisplacement" to "DS"
        table[0x300A01D6u] = "Table Top Lateral SetupDisplacement" to "DS"
        table[0x300A0200u] = "Brachy Treatment Technique" to "CS"
        table[0x300A0202u] = "Brachy Treatment Type" to "CS"
        table[0x300A0206u] = "Treatment Machine Sequence" to "SQ"
        table[0x300A0210u] = "Source Sequence" to "SQ"
        table[0x300A0212u] = "Source Number" to "IS"
        table[0x300A0214u] = "Source Type" to "CS"
        table[0x300A0216u] = "Source Manufacturer" to "LO"
        table[0x300A0218u] = "Active Source Diameter" to "DS"
        table[0x300A021Au] = "Active Source Length" to "DS"
        table[0x300A021Bu] = "Source Model ID" to "SH"
        table[0x300A021Cu] = "Source Description" to "LO"
        table[0x300A0222u] = "Source Encapsulation NominalThickness" to "DS"
        table[0x300A0224u] = "Source Encapsulation NominalTransmission" to "DS"
        table[0x300A0226u] = "Source Isotope Name" to "LO"
        table[0x300A0228u] = "Source Isotope Half Life" to "DS"
        table[0x300A0229u] = "Source Strength Units" to "CS"
        table[0x300A022Au] = "Reference Air Kerma Rate" to "DS"
        table[0x300A022Bu] = "Source Strength" to "DS"
        table[0x300A022Cu] = "Source Strength Reference Date" to "DA"
        table[0x300A022Eu] = "Source Strength Reference Time" to "TM"
        table[0x300A0230u] = "Application Setup Sequence" to "SQ"
        table[0x300A0232u] = "Application Setup Type" to "CS"
        table[0x300A0234u] = "Application Setup Number" to "IS"
        table[0x300A0236u] = "Application Setup Name" to "LO"
        table[0x300A0238u] = "Application Setup Manufacturer" to "LO"
        table[0x300A0240u] = "Template Number" to "IS"
        table[0x300A0242u] = "Template Type" to "SH"
        table[0x300A0244u] = "Template Name" to "LO"
        table[0x300A0250u] = "Total Reference Air Kerma" to "DS"
        table[0x300A0260u] = "Brachy Accessory DeviceSequence" to "SQ"
        table[0x300A0262u] = "Brachy Accessory Device Number" to "IS"
        table[0x300A0263u] = "Brachy Accessory Device ID" to "SH"
        table[0x300A0264u] = "Brachy Accessory Device Type" to "CS"
        table[0x300A0266u] = "Brachy Accessory Device Name" to "LO"
        table[0x300A026Au] = "Brachy Accessory Device NominalThickness" to "DS"
        table[0x300A026Cu] = "Brachy Accessory Device NominalTransmission" to "DS"
        table[0x300A0280u] = "Channel Sequence" to "SQ"
        table[0x300A0282u] = "Channel Number" to "IS"
        table[0x300A0284u] = "Channel Length" to "DS"
        table[0x300A0286u] = "Channel Total Time" to "DS"
        table[0x300A0288u] = "Source Movement Type" to "CS"
        table[0x300A028Au] = "Number of Pulses" to "IS"
        table[0x300A028Cu] = "Pulse Repetition Interval" to "DS"
        table[0x300A0290u] = "Source Applicator Number" to "IS"
        table[0x300A0291u] = "Source Applicator ID" to "SH"
        table[0x300A0292u] = "Source Applicator Type" to "CS"
        table[0x300A0294u] = "Source Applicator Name" to "LO"
        table[0x300A0296u] = "Source Applicator Length" to "DS"
        table[0x300A0298u] = "Source Applicator Manufacturer" to "LO"
        table[0x300A029Cu] = "Source Applicator Wall NominalThickness" to "DS"
        table[0x300A029Eu] = "Source Applicator Wall NominalTransmission" to "DS"
        table[0x300A02A0u] = "Source Applicator Step Size" to "DS"
        table[0x300A02A2u] = "Transfer Tube Number" to "IS"
        table[0x300A02A4u] = "Transfer Tube Length" to "DS"
        table[0x300A02B0u] = "Channel Shield Sequence" to "SQ"
        table[0x300A02B2u] = "Channel Shield Number" to "IS"
        table[0x300A02B3u] = "Channel Shield ID" to "SH"
        table[0x300A02B4u] = "Channel Shield Name" to "LO"
        table[0x300A02B8u] = "Channel Shield Nominal Thickness" to "DS"
        table[0x300A02BAu] = "Channel Shield NominalTransmission" to "DS"
        table[0x300A02C8u] = "Final Cumulative Time Weight" to "DS"
        table[0x300A02D0u] = "Brachy Control Point Sequence" to "SQ"
        table[0x300A02D2u] = "Control Point Relative Position" to "DS"
        table[0x300A02D4u] = "Control Point 3D Position" to "DS"
        table[0x300A02D6u] = "Cumulative Time Weight" to "DS"
        table[0x300A02E0u] = "Compensator Divergence" to "CS"
        table[0x300A02E1u] = "Compensator Mounting Position" to "CS"
        table[0x300A02E2u] = "Source to Compensator Distance" to "DS"
        table[0x300A02E3u] = "Total Compensator TrayWater-Equivalent Thickness" to "FL"
        table[0x300A02E4u] = "Isocenter to Compensator TrayDistance" to "FL"
        table[0x300A02E5u] = "Compensator Column Offset" to "FL"
        table[0x300A02E6u] = "Isocenter to CompensatorDistances" to "FL"
        table[0x300A02E7u] = "Compensator Relative StoppingPower Ratio" to "FL"
        table[0x300A02E8u] = "Compensator Milling Tool Diameter" to "FL"
        table[0x300A02EAu] = "Ion Range Compensator Sequence" to "SQ"
        table[0x300A02EBu] = "Compensator Description" to "LT"
        table[0x300A0302u] = "Radiation Mass Number" to "IS"
        table[0x300A0304u] = "Radiation Atomic Number" to "IS"
        table[0x300A0306u] = "Radiation Charge State" to "SS"
        table[0x300A0308u] = "Scan Mode" to "CS"
        table[0x300A030Au] = "Virtual Source-Axis Distances" to "FL"
        table[0x300A030Cu] = "Snout Sequence" to "SQ"
        table[0x300A030Du] = "Snout Position" to "FL"
        table[0x300A030Fu] = "Snout ID" to "SH"
        table[0x300A0312u] = "Number of Range Shifters" to "IS"
        table[0x300A0314u] = "Range Shifter Sequence" to "SQ"
        table[0x300A0316u] = "Range Shifter Number" to "IS"
        table[0x300A0318u] = "Range Shifter ID" to "SH"
        table[0x300A0320u] = "Range Shifter Type" to "CS"
        table[0x300A0322u] = "Range Shifter Description" to "LO"
        table[0x300A0330u] = "Number of Lateral SpreadingDevices" to "IS"
        table[0x300A0332u] = "Lateral Spreading DeviceSequence" to "SQ"
        table[0x300A0334u] = "Lateral Spreading Device Number" to "IS"
        table[0x300A0336u] = "Lateral Spreading Device ID" to "SH"
        table[0x300A0338u] = "Lateral Spreading Device Type" to "CS"
        table[0x300A033Au] = "Lateral Spreading DeviceDescription" to "LO"
        table[0x300A033Cu] = "Lateral Spreading Device WaterEquivalent Thickness" to "FL"
        table[0x300A0340u] = "Number of Range Modulators" to "IS"
        table[0x300A0342u] = "Range Modulator Sequence" to "SQ"
        table[0x300A0344u] = "Range Modulator Number" to "IS"
        table[0x300A0346u] = "Range Modulator ID" to "SH"
        table[0x300A0348u] = "Range Modulator Type" to "CS"
        table[0x300A034Au] = "Range Modulator Description" to "LO"
        table[0x300A034Cu] = "Beam Current Modulation ID" to "SH"
        table[0x300A0350u] = "Patient Support Type" to "CS"
        table[0x300A0352u] = "Patient Support ID" to "SH"
        table[0x300A0354u] = "Patient Support Accessory Code" to "LO"
        table[0x300A0356u] = "Fixation Light Azimuthal Angle" to "FL"
        table[0x300A0358u] = "Fixation Light Polar Angle" to "FL"
        table[0x300A035Au] = "Meterset Rate" to "FL"
        table[0x300A0360u] = "Range Shifter Settings Sequence" to "SQ"
        table[0x300A0362u] = "Range Shifter Setting" to "LO"
        table[0x300A0364u] = "Isocenter to Range Shifter Distance" to "FL"
        table[0x300A0366u] = "Range Shifter Water EquivalentThickness" to "FL"
        table[0x300A0370u] = "Lateral Spreading Device SettingsSequence" to "SQ"
        table[0x300A0372u] = "Lateral Spreading Device Setting" to "LO"
        table[0x300A0374u] = "Isocenter to Lateral SpreadingDevice Distance" to "FL"
        table[0x300A0380u] = "Range Modulator SettingsSequence" to "SQ"
        table[0x300A0382u] = "Range Modulator Gating StartValue" to "FL"
        table[0x300A0384u] = "Range Modulator Gating StopValue" to "FL"
        table[0x300A0386u] = "Range Modulator Gating StartWater Equivalent Thickness" to "FL"
        table[0x300A0388u] = "Range Modulator Gating StopWater Equivalent Thickness" to "FL"
        table[0x300A038Au] = "Isocenter to Range ModulatorDistance" to "FL"
        table[0x300A0390u] = "Scan Spot Tune ID" to "SH"
        table[0x300A0392u] = "Number of Scan Spot Positions" to "IS"
        table[0x300A0394u] = "Scan Spot Position Map" to "FL"
        table[0x300A0396u] = "Scan Spot Meterset Weights" to "FL"
        table[0x300A0398u] = "Scanning Spot Size" to "FL"
        table[0x300A039Au] = "Number of Paintings" to "IS"
        table[0x300A03A0u] = "Ion Tolerance Table Sequence" to "SQ"
        table[0x300A03A2u] = "Ion Beam Sequence" to "SQ"
        table[0x300A03A4u] = "Ion Beam Limiting DeviceSequence" to "SQ"
        table[0x300A03A6u] = "Ion Block Sequence" to "SQ"
        table[0x300A03A8u] = "Ion Control Point Sequence" to "SQ"
        table[0x300A03AAu] = "Ion Wedge Sequence" to "SQ"
        table[0x300A03ACu] = "Ion Wedge Position Sequence" to "SQ"
        table[0x300A0401u] = "Referenced Setup ImageSequence" to "SQ"
        table[0x300A0402u] = "Setup Image Comment" to "ST"
        table[0x300A0410u] = "Motion Synchronization Sequence" to "SQ"
        table[0x300A0412u] = "Control Point Orientation" to "FL"
        table[0x300A0420u] = "General Accessory Sequence" to "SQ"
        table[0x300A0421u] = "General Accessory ID" to "SH"
        table[0x300A0422u] = "General Accessory Description" to "ST"
        table[0x300A0423u] = "General Accessory Type" to "CS"
        table[0x300A0424u] = "General Accessory Number" to "IS"
        table[0x300A0425u] = "Source to General AccessoryDistance" to "FL"
        table[0x300A0431u] = "Applicator Geometry Sequence" to "SQ"
        table[0x300A0432u] = "Applicator Aperture Shape" to "CS"
        table[0x300A0433u] = "Applicator Opening" to "FL"
        table[0x300A0434u] = "Applicator Opening X" to "FL"
        table[0x300A0435u] = "Applicator Opening Y" to "FL"
        table[0x300A0436u] = "Source to Applicator MountingPosition Distance" to "FL"
        table[0x300A0440u] = "Number of Block Slab Items" to "IS"
        table[0x300A0441u] = "Block Slab Sequence" to "SQ"
        table[0x300A0442u] = "Block Slab Thickness" to "DS"
        table[0x300A0443u] = "Block Slab Number" to "US"
        table[0x300A0450u] = "Device Motion Control Sequence" to "SQ"
        table[0x300A0451u] = "Device Motion Execution Mode" to "CS"
        table[0x300A0452u] = "Device Motion Observation Mode" to "CS"
        table[0x300A0453u] = "Device Motion Parameter CodeSequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x300C.
     */
    private fun addAttributeGroup300C() {
        table[0x300C0002u] = "Referenced RT Plan Sequence" to "SQ"
        table[0x300C0004u] = "Referenced Beam Sequence" to "SQ"
        table[0x300C0006u] = "Referenced Beam Number" to "IS"
        table[0x300C0007u] = "Referenced Reference ImageNumber" to "IS"
        table[0x300C0008u] = "Start Cumulative Meterset Weight" to "DS"
        table[0x300C0009u] = "End Cumulative Meterset Weight" to "DS"
        table[0x300C000Au] = "Referenced Brachy ApplicationSetup Sequence" to "SQ"
        table[0x300C000Cu] = "Referenced Brachy ApplicationSetup Number" to "IS"
        table[0x300C000Eu] = "Referenced Source Number" to "IS"
        table[0x300C0020u] = "Referenced Fraction GroupSequence" to "SQ"
        table[0x300C0022u] = "Referenced Fraction GroupNumber" to "IS"
        table[0x300C0040u] = "Referenced Verification ImageSequence" to "SQ"
        table[0x300C0042u] = "Referenced Reference ImageSequence" to "SQ"
        table[0x300C0050u] = "Referenced Dose ReferenceSequence" to "SQ"
        table[0x300C0051u] = "Referenced Dose ReferenceNumber" to "IS"
        table[0x300C0055u] = "Brachy Referenced DoseReference Sequence" to "SQ"
        table[0x300C0060u] = "Referenced Structure SetSequence" to "SQ"
        table[0x300C006Au] = "Referenced Patient Setup Number" to "IS"
        table[0x300C0080u] = "Referenced Dose Sequence" to "SQ"
        table[0x300C00A0u] = "Referenced Tolerance TableNumber" to "IS"
        table[0x300C00B0u] = "Referenced Bolus Sequence" to "SQ"
        table[0x300C00C0u] = "Referenced Wedge Number" to "IS"
        table[0x300C00D0u] = "Referenced Compensator Number" to "IS"
        table[0x300C00E0u] = "Referenced Block Number" to "IS"
        table[0x300C00F0u] = "Referenced Control Point Index" to "IS"
        table[0x300C00F2u] = "Referenced Control PointSequence" to "SQ"
        table[0x300C00F4u] = "Referenced Start Control PointIndex" to "IS"
        table[0x300C00F6u] = "Referenced Stop Control PointIndex" to "IS"
        table[0x300C0100u] = "Referenced Range Shifter Number" to "IS"
        table[0x300C0102u] = "Referenced Lateral SpreadingDevice Number" to "IS"
        table[0x300C0104u] = "Referenced Range ModulatorNumber" to "IS"
        table[0x300C0111u] = "Omitted Beam Task Sequence" to "SQ"
        table[0x300C0112u] = "Reason for Omission" to "CS"
        table[0x300C0113u] = "Reason for Omission Description" to "LO"
    }

    /**
     * Adds attributes of group 0x300E.
     */
    private fun addAttributeGroup300E() {
        table[0x300E0002u] = "Approval Status" to "CS"
        table[0x300E0004u] = "Review Date" to "DA"
        table[0x300E0005u] = "Review Time" to "TM"
        table[0x300E0008u] = "Reviewer Name" to "PN"
    }

    /**
     * Adds attributes of group 0x4000.
     */
    private fun addAttributeGroup4000() {
        table[0x40000010u] = "Arbitrary" to "LT" // Retired
        table[0x40004000u] = "Text Comments" to "LT" // Retired
    }

    /**
     * Adds attributes of group 0x4008.
     */
    private fun addAttributeGroup4008() {
        table[0x40080040u] = "Results ID" to "SH" // Retired
        table[0x40080042u] = "Results ID Issuer" to "LO" // Retired
        table[0x40080050u] = "Referenced InterpretationSequence" to "SQ" // Retired
        table[0x400800FFu] = "Report Production Status (Trial)" to "CS" // Retired
        table[0x40080100u] = "Interpretation Recorded Date" to "DA" // Retired
        table[0x40080101u] = "Interpretation Recorded Time" to "TM" // Retired
        table[0x40080102u] = "Interpretation Recorder" to "PN" // Retired
        table[0x40080103u] = "Reference to Recorded Sound" to "LO" // Retired
        table[0x40080108u] = "Interpretation Transcription Date" to "DA" // Retired
        table[0x40080109u] = "Interpretation Transcription Time" to "TM" // Retired
        table[0x4008010Au] = "Interpretation Transcriber" to "PN" // Retired
        table[0x4008010Bu] = "Interpretation Text" to "ST" // Retired
        table[0x4008010Cu] = "Interpretation Author" to "PN" // Retired
        table[0x40080111u] = "Interpretation Approver Sequence" to "SQ" // Retired
        table[0x40080112u] = "Interpretation Approval Date" to "DA" // Retired
        table[0x40080113u] = "Interpretation Approval Time" to "TM" // Retired
        table[0x40080114u] = "Physician Approving Interpretation" to "PN" // Retired
        table[0x40080115u] = "Interpretation DiagnosisDescription" to "LT" // Retired
        table[0x40080117u] = "Interpretation Diagnosis CodeSequence" to "SQ" // Retired
        table[0x40080118u] = "Results Distribution List Sequence" to "SQ" // Retired
        table[0x40080119u] = "Distribution Name" to "PN" // Retired
        table[0x4008011Au] = "Distribution Address" to "LO" // Retired
        table[0x40080200u] = "Interpretation ID" to "SH" // Retired
        table[0x40080202u] = "Interpretation ID Issuer" to "LO" // Retired
        table[0x40080210u] = "Interpretation Type ID" to "CS" // Retired
        table[0x40080212u] = "Interpretation Status ID" to "CS" // Retired
        table[0x40080300u] = "Impressions" to "ST" // Retired
        table[0x40084000u] = "Results Comments" to "ST" // Retired
    }

    /**
     * Adds attributes of group 0x4010.
     */
    private fun addAttributeGroup4010() {
        table[0x40100001u] = "Low Energy Detectors" to "CS" // DICOS
        table[0x40100002u] = "High Energy Detectors" to "CS" // DICOS
        table[0x40100004u] = "Detector Geometry Sequence" to "SQ" // DICOS
        table[0x40101001u] = "Threat ROI Voxel Sequence" to "SQ" // DICOS
        table[0x40101004u] = "Threat ROI Base" to "FL" // DICOS
        table[0x40101005u] = "Threat ROI Extents" to "FL" // DICOS
        table[0x40101006u] = "Threat ROI Bitmap" to "OB" // DICOS
        table[0x40101007u] = "Route Segment ID" to "SH" // DICOS
        table[0x40101008u] = "Gantry Type" to "CS" // DICOS
        table[0x40101009u] = "OOI Owner Type" to "CS" // DICOS
        table[0x4010100Au] = "Route Segment Sequence" to "SQ" // DICOS
        table[0x40101010u] = "Potential Threat Object ID" to "US" // DICOS
        table[0x40101011u] = "Threat Sequence" to "SQ" // DICOS
        table[0x40101012u] = "Threat Category" to "CS" // DICOS
        table[0x40101013u] = "Threat Category Description" to "LT" // DICOS
        table[0x40101014u] = "ATD Ability Assessment" to "CS" // DICOS
        table[0x40101015u] = "ATD Assessment Flag" to "CS" // DICOS
        table[0x40101016u] = "ATD Assessment Probability" to "FL" // DICOS
        table[0x40101017u] = "Mass" to "FL" // DICOS
        table[0x40101018u] = "Density" to "FL" // DICOS
        table[0x40101019u] = "Z Effective" to "FL" // DICOS
        table[0x4010101Au] = "Boarding Pass ID" to "SH" // DICOS
        table[0x4010101Bu] = "Center of Mass" to "FL" // DICOS
        table[0x4010101Cu] = "Center of PTO" to "FL" // DICOS
        table[0x4010101Du] = "Bounding Polygon" to "FL" // DICOS
        table[0x4010101Eu] = "Route Segment Start Location ID" to "SH" // DICOS
        table[0x4010101Fu] = "Route Segment End Location ID" to "SH" // DICOS
        table[0x40101020u] = "Route Segment Location ID Type" to "CS" // DICOS
        table[0x40101021u] = "Abort Reason" to "CS" // DICOS
        table[0x40101023u] = "Volume of PTO" to "FL" // DICOS
        table[0x40101024u] = "Abort Flag" to "CS" // DICOS
        table[0x40101025u] = "Route Segment Start Time" to "DT" // DICOS
        table[0x40101026u] = "Route Segment End Time" to "DT" // DICOS
        table[0x40101027u] = "TDR Type" to "CS" // DICOS
        table[0x40101028u] = "International Route Segment" to "CS" // DICOS
        table[0x40101029u] = "Threat Detection Algorithm andVersion" to "LO" // DICOS
        table[0x4010102Au] = "Assigned Location" to "SH" // DICOS
        table[0x4010102Bu] = "Alarm Decision Time" to "DT" // DICOS
        table[0x40101031u] = "Alarm Decision" to "CS" // DICOS
        table[0x40101033u] = "Number of Total Objects" to "US" // DICOS
        table[0x40101034u] = "Number of Alarm Objects" to "US" // DICOS
        table[0x40101037u] = "PTO Representation Sequence" to "SQ" // DICOS
        table[0x40101038u] = "ATD Assessment Sequence" to "SQ" // DICOS1
        table[0x40101039u] = "TIP Type" to "CS" // DICOS
        table[0x4010103Au] = "DICOS Version" to "CS" // DICOS
        table[0x40101041u] = "OOI Owner Creation Time" to "DT" // DICOS
        table[0x40101042u] = "OOI Type" to "CS" // DICOS
        table[0x40101043u] = "OOI Size" to "FL" // DICOS
        table[0x40101044u] = "Acquisition Status" to "CS" // DICOS
        table[0x40101045u] = "Basis Materials Code Sequence" to "SQ" // DICOS
        table[0x40101046u] = "Phantom Type" to "CS" // DICOS
        table[0x40101047u] = "OOI Owner Sequence" to "SQ" // DICOS
        table[0x40101048u] = "Scan Type" to "CS" // DICOS
        table[0x40101051u] = "Itinerary ID" to "LO" // DICOS
        table[0x40101052u] = "Itinerary ID Type" to "SH" // DICOS
        table[0x40101053u] = "Itinerary ID Assigning Authority" to "LO" // DICOS
        table[0x40101054u] = "Route ID" to "SH" // DICOS
        table[0x40101055u] = "Route ID Assigning Authority" to "SH" // DICOS
        table[0x40101056u] = "Inbound Arrival Type" to "CS" // DICOS
        table[0x40101058u] = "Carrier ID" to "SH" // DICOS
        table[0x40101059u] = "Carrier ID Assigning Authority" to "CS" // DICOS
        table[0x40101060u] = "Source Orientation" to "FL" // DICOS
        table[0x40101061u] = "Source Position" to "FL" // DICOS
        table[0x40101062u] = "Belt Height" to "FL" // DICOS
        table[0x40101064u] = "Algorithm Routing Code Sequence" to "SQ" // DICOS
        table[0x40101067u] = "Transport Classification" to "CS" // DICOS
        table[0x40101068u] = "OOI Type Descriptor" to "LT" // DICOS
        table[0x40101069u] = "Total Processing Time" to "FL" // DICOS
        table[0x4010106Cu] = "Detector Calibration Data" to "OB" // DICOS
        table[0x4010106Du] = "Additional Screening Performed" to "CS" // DICOS
        table[0x4010106Eu] = "Additional Inspection SelectionCriteria" to "CS" // DICOS
        table[0x4010106Fu] = "Additional Inspection MethodSequence" to "SQ" // DICOS
        table[0x40101070u] = "AIT Device Type" to "CS" // DICOS
        table[0x40101071u] = "QR Measurements Sequence" to "SQ" // DICOS
        table[0x40101072u] = "Target Material Sequence" to "SQ" // DICOS
        table[0x40101073u] = "SNR Threshold" to "FD" // DICOS
        table[0x40101075u] = "Image Scale Representation" to "DS" // DICOS
        table[0x40101076u] = "Referenced PTO Sequence" to "SQ" // DICOS
        table[0x40101077u] = "Referenced TDR InstanceSequence" to "SQ" // DICOS
        table[0x40101078u] = "PTO Location Description" to "ST" // DICOS
        table[0x40101079u] = "Anomaly Locator IndicatorSequence" to "SQ" // DICOS
        table[0x4010107Au] = "Anomaly Locator Indicator" to "FL" // DICOS
        table[0x4010107Bu] = "PTO Region Sequence" to "SQ" // DICOS
        table[0x4010107Cu] = "Inspection Selection Criteria" to "CS" // DICOS
        table[0x4010107Du] = "Secondary Inspection MethodSequence" to "SQ" // DICOS
        table[0x4010107Eu] = "PRCS to RCS Orientation" to "DS" // DICOS
    }

    /**
     * Adds attributes of group 0x4FFE.
     */
    private fun addAttributeGroup4FFE() {
        table[0x4FFE0001u] = "MAC Parameters Sequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x5200.
     */
    private fun addAttributeGroup5200() {
        table[0x52009229u] = "Shared Functional GroupsSequence" to "SQ"
        table[0x52009230u] = "Per-frame Functional GroupsSequence" to "SQ"
    }

    /**
     * Adds attributes of group 0x5400.
     */
    private fun addAttributeGroup5400() {
        table[0x54000100u] = "Waveform Sequence" to "SQ"
        // add(0x54000110, "Channel Minimum Value", "OB or OW");
        // add(0x54000112, "Channel Maximum Value", "OB or OW");
        table[0x54001004u] = "Waveform Bits Allocated" to "US"
        table[0x54001006u] = "Waveform Sample Interpretation" to "CS"
        // add(0x5400100A, "Waveform Padding Value", "OB or OW");
        // add(0x54001010, "Waveform Data", "OB or OW");
    }

    /**
     * Adds attributes of group 0x5600.
     */
    private fun addAttributeGroup5600() {
        table[0x56000010u] = "First Order Phase Correction Angle" to "OF"
        table[0x56000020u] = "Spectroscopy Data" to "OF"
    }

    /**
     * Adds attributes of group 0x7FE0.
     */
    private fun addAttributeGroup7FE0() {
        table[0x7FE00008u] = "Float Pixel Data" to "OF"
        table[0x7FE00009u] = "Double Float Pixel Data" to "OD"
        // add(0x7FE00010, "Pixel Data", "OB or OW");
        table[0x7FE00020u] = "Coefficients SDVN" to "OW" // Retired
        table[0x7FE00030u] = "Coefficients SDHN" to "OW" // Retired
        table[0x7FE00040u] = "Coefficients SDDN" to "OW" // Retired
    }

    /**
     * Adds attributes of group 0xFFFA.
     */
    private fun addAttributeGroupFFFA() {
        table[0xFFFAFFFAu] = "Digital Signatures Sequence" to "SQ"
    }
}