package io.scif.api

import kotlinxInternal.utf8Size

data class Message(val timestamp: Long, val text: String) {
    companion object
}

//fun Message.toBson(sink: Sink) {
//    val buffer = Buffer()
//    with (buffer) {
//        writeByte(0x9)                          // UTC-timestamp field
//        writeString("timestamp")                // field name
//        writeByte(0)
//        writeLongLe(timestamp)                  // field value
//        writeByte(0x2)                          // string field
//        writeString("text")                     // field name
//        writeByte(0)
//        writeIntLe(text.utf8Size().toInt() + 1) // field value: length followed by the string
//        writeString(text)
//        writeByte(0)
//        writeByte(0)                            // end of BSON document
//    }
//
//    // Write document length and then its body
//    sink.writeIntLe(buffer.size.toInt() + 4)
//    buffer.transferTo(sink)
//    sink.flush()
//}
//
//fun Message.Companion.fromBson(source: Source): Message {
//    source.require(4)                                    // check if the source contains length
//    val length = source.readIntLe() - 4L
//    source.require(length)                               // check if the source contains the whole message
//
//    fun readFieldName(source: Source): String {
//        val delimiterOffset = source.indexOf(0)          // find offset of the 0-byte terminating the name
//        check(delimiterOffset >= 0)                      // indexOf return -1 if value not found
//        val fieldName = source.readString(delimiterOffset) // read the string until terminator
//        source.skip(1)                                   // skip the terminator
//        return fieldName
//    }
//
//    // for simplicity, let's assume that the order of fields matches serialization order
//    var tag = source.readByte().toInt()                // read the field type
//    check(tag == 0x9 && readFieldName(source) == "timestamp")
//    val timestamp = source.readLongLe()                // read long value
//    tag = source.readByte().toInt()
//    check(tag == 0x2 && readFieldName(source) == "text")
//    val textLen = source.readIntLe() - 1L              // read string length (it includes the terminator)
//    val text = source.readString(textLen)                // read value
//    source.skip(1)                                     // skip terminator
//    source.skip(1)                                     // skip end of the document
//    return Message(timestamp, text)
//}
