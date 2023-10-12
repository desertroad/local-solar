package desertroad.solar

import desertroad.solar.internal.Angle.Companion.degrees
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private val TIME_TOLERANCE = 60.seconds.inWholeMilliseconds
private val ANGLE_TOLERANCE = 0.5.degrees.inDegrees

/**
 * Data from
 *
 * https://gml.noaa.gov/grad/solcalc/
 * https://www.timeanddate.com/
 */
class LocalSolarTest {

    @Test
    fun testOnBusan() {
        val localSolar = LocalSolar(
            latitude = /* 35°11'N */ (35 + 11 / 60.0),
            longitude = /* 129°04'E */ (129 + 4 / 60.0),
            time = toEpochMillis("1980-07-09T12:00+09:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T12:28:47+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 77.17,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        assertNotNull(events[Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T05:17+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 61.61,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("1980-07-09T19:41+09:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 298.38,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnChristChurch() {
        val localSolar = LocalSolar(
            latitude = /* 43°32'S */ -(43 + 32 / 60.0),
            longitude = /* 172°38'E */ (172 + 38 / 60.0),
            time = toEpochMillis("2007-01-07T12:00+13:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T13:35:11+13:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 68.92,
                actual = altitude,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        val events = localSolar.events

        assertNotNull(events[Event.SUNRISE]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T05:57+13:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 122.81,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }

        assertNotNull(events[Event.SUNSET]).run {
            assertEquals(
                expected = toEpochMillis("2007-01-07T21:13+13:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 237.38,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnSanFrancisco() {
        val localSolar = LocalSolar(
            latitude = /* 37°47'N */ (37 + 47 / 60.0),
            longitude = /* 122°25'W */ -(122 + 25 / 60.0),
            time = toEpochMillis("2014-08-29T12:00-07:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2014-08-29T13:10:40-07:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 61.38,
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
                expected = 77.56,
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
                expected = 282.13,
                actual = azimuth,
                absoluteTolerance = ANGLE_TOLERANCE
            )
        }
    }

    @Test
    fun testOnTromso() {
        val localSolar = LocalSolar(
            latitude = /* 69°39'N */ (69 + 39 / 60.0),
            longitude = /* 18°57'E */ (18 + 57 / 60.0),
            time = toEpochMillis("2023-06-14T12:00+02:00")
        )

        localSolar.meridian.run {
            assertEquals(
                expected = toEpochMillis("2023-06-14T12:44:20+02:00"),
                actual = time,
                absoluteTolerance = TIME_TOLERANCE
            )

            assertEquals(
                expected = 43.63,
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