package com.embea.insurancedemo.controller

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.math.BigDecimal
import java.math.RoundingMode

class MoneySerializer : JsonSerializer<BigDecimal?>() {

    override fun serialize(value: BigDecimal?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen?.writeNumber(value?.setScale(2, RoundingMode.HALF_UP).toString())
    }
}

class MoneyDeserializer : JsonDeserializer<BigDecimal?>() {

    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext?): BigDecimal? {
        val oc = jsonParser.codec
        val node = oc.readTree<JsonNode>(jsonParser)
        return node.decimalValue()
    }
}
