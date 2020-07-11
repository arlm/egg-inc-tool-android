package br.com.alexandremarcondes.egginc.companion.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.floor
import kotlin.math.roundToInt

fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun LocalDateTime.toShortDateTime() = this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

fun Double.toDateTime(): LocalDateTime = Instant.ofEpochSecond(this.toLong())
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime()

fun Double.formatSeconds(): String {
    val sb = StringBuilder()
    var seconds = this

    val days = floor(seconds / (3600*24)).roundToInt()
    seconds  -= days*3600*24;

    if (days > 0) {
        sb.append(days)
        sb.append(" days")

        if (seconds > 0) {
            sb.append(" ")
        }
    }

    var hours   = floor(seconds / 3600).roundToInt()
    seconds  -= hours*3600;

    if (hours > 0) {
        if ((days > 0))
        sb.append(hours)
        sb.append(" hours")

        if (seconds > 0) {
            sb.append(" ")
        }
    }

    var minutes = floor(seconds / 60).roundToInt()
    seconds  -= minutes*60;

    if (minutes > 0) {
        sb.append(minutes)
        sb.append(" minutes")

        if (seconds > 0) {
            sb.append(" ")
        }
    }

    if (seconds > 0) {
        sb.append(seconds.roundToInt())
        sb.append(" seconds")
    }

    return sb.toString()
}