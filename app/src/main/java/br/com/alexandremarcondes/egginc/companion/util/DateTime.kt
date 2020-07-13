package br.com.alexandremarcondes.egginc.companion.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Double.toDateTime(): LocalDateTime = Instant.ofEpochSecond(this.toLong())
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime()

fun Double.isInThePast(): Boolean = Instant.ofEpochSecond(this.toLong())
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime().isBefore(LocalDateTime.now())