@file:JvmName("Utils")

package desertroad.solar

import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

private val dateFormat = DateTimeFormatter.ISO_DATE_TIME

fun toEpochMillis(date: String) =
        dateFormat.parse(date, Instant::from).toEpochMilli()

fun assertEquals(expected: Long, actual: Long, absoluteTolerance: Long) {
    assertEquals(expected.toDouble(), actual.toDouble(), absoluteTolerance.toDouble(),
        "Expected <$expected>~actual<$actual> = ${(expected - actual).absoluteValue} > absolute tolerance <$absoluteTolerance>")
}
