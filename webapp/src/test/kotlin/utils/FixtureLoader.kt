package utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.charset.StandardCharsets

object FixtureLoader {
    val objectMapper: ObjectMapper =
        jacksonObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @JvmStatic
    fun readFile(path: String): String = this.javaClass.getResource(path)!!.readText(StandardCharsets.UTF_8)
}