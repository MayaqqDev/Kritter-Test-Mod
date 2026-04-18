package dev.mayaqq.testmod

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Testmod : Logger by LoggerFactory.getLogger("testmod") {
    fun init() {
        info("Testmod INIT")
    }
}