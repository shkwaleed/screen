package com.example.xibo

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "display")
data class DisplayInfo(
    @field:Element(name = "date")
    var date: String = "",

    @field:Element(name = "timezone")
    var timezone: String = "",

    @field:Element(name = "status")
    var status: Int = 0,

    @field:Element(name = "code")
    var code: String = "",

    @field:Element(name = "message")
    var message: String = "",

    @field:Element(name = "checkSchedule")
    var checkSchedule: String = "",

    @field:Element(name = "checkRf")
    var checkRf: String = ""
)