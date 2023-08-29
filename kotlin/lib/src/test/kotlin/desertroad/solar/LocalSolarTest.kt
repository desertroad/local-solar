package desertroad.solar

import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private const val TIME_TOLERANCE = 120_000  // milliseconds
private const val ANGLE_TOLERANCE = 2.0     // degrees

class LocalSolarTest {

    val dateFormat = DateTimeFormatter.ISO_DATE_TIME

    fun toEpochMillis(date: String) =
        dateFormat.parse(date, Instant::from).toEpochMilli()

    @Test
    fun testOnBusan() {
        // Data from : https://www.timeanddate.com/sun/south-korea/busan?month=7&year=1980

        val localSolar = LocalSolar(
            latitudeInDegrees = (35 + 11 / 60.0),   // 35°11'N
            longitudeInDegrees = (129 + 4 / 60.0),  // 129°04'E
            millisSinceEpoch = toEpochMillis("1980-07-09T12:00+09:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T12:28+09:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 77.0,
                actual = altitudeInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.computeEvents()

        assertNotNull(events[LocalSolar.Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T05:16+09:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 62.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[LocalSolar.Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T19:40+09:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 298.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnChristChurch() {
        // Data from : https://www.timeanddate.com/sun/new-zealand/christchurch?month=1&year=2007

        val localSolar = LocalSolar(
            latitudeInDegrees = -(43 + 32 / 60.0),   // 43°32'S
            longitudeInDegrees = -(172 + 38 / 60.0), // 172°38'E
            millisSinceEpoch = toEpochMillis("2007-01-07T12:00+14:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T13:35+14:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 69.0,
                actual = altitudeInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.computeEvents()

        assertNotNull(events[LocalSolar.Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T05:57+14:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 123.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[LocalSolar.Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T21:13+14:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 237.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

    }
    @Test
    fun testOnSanFrancisco() {
        // Data from : https://www.timeanddate.com/sun/usa/san-francisco?month=8&year=2014

        val localSolar = LocalSolar(
            latitudeInDegrees = (37 + 47 / 60.0),   // 37°47'N
            longitudeInDegrees = -(122 + 25 / 60.0), // 122°25'W
            millisSinceEpoch = toEpochMillis("2014-08-29T12:00-07:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T13:10-07:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 61.0,
                actual = altitudeInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.computeEvents()

        assertNotNull(events[LocalSolar.Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T06:37-07:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 78.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[LocalSolar.Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T19:43-07:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 282.0,
                actual = azimuthInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnTromso() {
        // Data from : https://www.timeanddate.com/sun/norway/tromso?month=6&year=2023

        val localSolar = LocalSolar(
            latitudeInDegrees = (69 + 39 / 60.0),   // 	69°39'N
            longitudeInDegrees = (18 + 57 / 60.0), // 18°57'E
            millisSinceEpoch = toEpochMillis("2023-06-14T12:00+02:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2023-06-14T12:44+02:00").toDouble(),
                actual = timeInMillis.toDouble(),
                absoluteTolerance = TIME_TOLERANCE.toDouble()
            )

            assertEquals(
                expected = 44.0,
                actual = altitudeInDegrees,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.computeEvents()

        // White night
        assertNull(events[LocalSolar.Event.SUNRISE])
        assertNull(events[LocalSolar.Event.SUNSET])
    }
}