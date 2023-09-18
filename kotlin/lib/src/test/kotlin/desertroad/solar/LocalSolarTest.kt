package desertroad.solar

import desertroad.solar.internal.Angle.Companion.degrees
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.minutes

private val TIME_TOLERANCE = 2.minutes.inWholeMilliseconds
private val ANGLE_TOLERANCE = 1.degrees.inDegrees

class LocalSolarTest {

    @Test
    fun testOnBusan() {
        // Data from : https://www.timeanddate.com/sun/south-korea/busan?month=7&year=1980

        val localSolar = LocalSolar(
            latitude = (35 + 11 / 60.0),   // 35°11'N
            longitude = (129 + 4 / 60.0),  // 129°04'E
            time = toEpochMillis("1980-07-09T12:00+09:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T12:28+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 77.0,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        assertNotNull(events[Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T05:16+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 62.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T19:40+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 298.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnChristChurch() {
        // Data from : https://www.timeanddate.com/sun/new-zealand/christchurch?month=1&year=2007

        val localSolar = LocalSolar(
            latitude = -(43 + 32 / 60.0),   // 43°32'S
            longitude = -(172 + 38 / 60.0), // 172°38'E
            time = toEpochMillis("2007-01-07T12:00+14:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T13:35+14:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 69.0,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        assertNotNull(events[Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T05:57+14:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 123.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T21:13+14:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 237.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

    }
    @Test
    fun testOnSanFrancisco() {
        // Data from : https://www.timeanddate.com/sun/usa/san-francisco?month=8&year=2014

        val localSolar = LocalSolar(
            latitude = (37 + 47 / 60.0),   // 37°47'N
            longitude = -(122 + 25 / 60.0), // 122°25'W
            time = toEpochMillis("2014-08-29T12:00-07:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T13:10-07:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 61.0,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        assertNotNull(events[Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T06:37-07:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 78.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T19:43-07:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 282.0,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnTromso() {
        // Data from : https://www.timeanddate.com/sun/norway/tromso?month=6&year=2023

        val localSolar = LocalSolar(
            latitude = (69 + 39 / 60.0),   // 	69°39'N
            longitude = (18 + 57 / 60.0), // 18°57'E
            time = toEpochMillis("2023-06-14T12:00+02:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2023-06-14T12:44+02:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 44.0,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        // White night
        assertNull(events[Event.SUNRISE])
        assertNull(events[Event.SUNSET])
    }
}