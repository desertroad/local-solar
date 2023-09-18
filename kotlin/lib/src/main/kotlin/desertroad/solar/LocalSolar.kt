package desertroad.solar

import desertroad.solar.internal.Angle.Companion.cos
import desertroad.solar.internal.Angle.Companion.degrees
import desertroad.solar.internal.Angle.Companion.radians
import desertroad.solar.internal.Angle.Companion.rotations
import desertroad.solar.internal.Angle.Companion.sin
import desertroad.solar.internal.Angle.Companion.times
import desertroad.solar.internal.Altitude
import desertroad.solar.internal.Angle
import kotlin.math.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit.DAYS
import kotlin.time.times


/**
 * This is for calculating the position of the sun at a specific location and on a given solar day.
 *
 * The range of the solar day is defined solely by [latitude], [longitude], and [time], without considering the local time zone.
 *
 * - [Wikipedia - Position of the Sun](https://en.wikipedia.org/wiki/Position_of_the_Sun)
 * - [Wikipedia - Sunrise equation](https://en.wikipedia.org/wiki/Sunrise_equation)
 * - [Wikipedia - Solar zenith angle](https://en.wikipedia.org/wiki/Solar_zenith_angle)
 */
class LocalSolar(val latitude: Double, val longitude: Double, time: Long) {
    private val _latitude: Angle = latitude.degrees
    private val _longitude: Angle = longitude.degrees

    private val _localMeanTimeOffset: Duration = (_longitude / 1.rotations) * 1.days

    /**
     * The time range of mean solar day.
     */
    val localMeanDay: LongRange
        get() = _localMeanDay.start.inWholeMilliseconds..<_localMeanDay.endExclusive.inWholeMilliseconds
    private val _localMeanDay: OpenEndRange<Duration> = run {
        val start = floor((time.milliseconds + _localMeanTimeOffset) / 1.days) * 1.days - _localMeanTimeOffset
        start..<start + 1.days
    }

    /**
     * The difference between apparent solar time and mean solar time.
     *
     * [Equation of time](https://en.wikipedia.org/wiki/Equation_of_time)
     */
    val equationOfTime: Long
        get() = _equationOfTime.inWholeMilliseconds
    private val _equationOfTime: Duration = run {
        /*
        Due to its yearly cycle, there is no need to calculate the day of the year.
        Unix time can be used directly, and the formula has been adjusted to account for the difference of one day from epoch in UTC.
         */
        val d = (6.240_040_77 + 0.017_201_97 * (_localMeanDay.start - JAN_1_2000_UTC + 1.days).toDouble(DAYS)).radians
        (-7.659 * sin(d) + 9.863 * sin(2 * d + 3.5932.radians)).minutes
    }

    /**
     * The angular distance between the rays of the sun and the plane of the Earth's equator.
     *
     * [Wikipedia - Declination of the Sun as seen from Earth](https://en.wikipedia.org/wiki/Position_of_the_Sun#Declination_of_the_Sun_as_seen_from_Earth)
     */
    val declination: Double
        get() = _declination.inDegrees
    private val _declination: Angle = run {
        /*
        Due to its yearly cycle, there is no need to calculate the day of the year.
        Unix time can be used directly.
         */
        val n = _localMeanDay.start.toDouble(DAYS)
        -asin(0.39779 * cos((0.98565 * (n + 10) + 1.914 * sin(0.98565.degrees * (n - 2))).degrees)).radians
    }

    /**
     * The time when the Sun contacts the observer's meridian.
     * https://www.timeanddate.com/astronomy/solar-noon.html
     */
    val solarNoon: Long
        get() = _solarNoon.inWholeMilliseconds
    private val _solarNoon: Duration = _localMeanDay.start + 0.5.days - _equationOfTime

    /**
     * TODO
     */
    val meridian: Moment by lazy { getMomentAt(_solarNoon) }

    /**
     * TODO
     */
    val events: Map<Event, Moment> by lazy { computeEvents() }

    /**
     * TODO
     */
    fun getMomentAt(time: Long): Moment = getMomentAt(time.milliseconds)

    private fun toHourAngle(sinceEpoch: Duration): Angle {
        require(sinceEpoch in _localMeanDay) {
            "[sinceEpoch] is not within this Local Mean Day"
        }

        return ((sinceEpoch - _solarNoon) / 1.days).rotations
    }

    /**
     * https://en.wikipedia.org/wiki/Solar_zenith_angle
     */
    private fun computeAltitude(hourAngle: Angle): Angle =
            asin(
                    sin(_declination) * sin(_latitude) +
                            cos(_declination) * cos(_latitude) * cos(hourAngle)
            ).radians

    /**
     * https://en.wikipedia.org/wiki/Solar_azimuth_angle
     */
    private fun computeAzimuth(altitude: Angle, hourAngle: Angle): Angle {
        val cosAzimuth = (
                sin(_declination) * cos(_latitude) -
                        cos(_declination) * sin(_latitude) * cos(hourAngle)
                ) / cos(altitude)

        val azimuth = acos(cosAzimuth.coerceIn(-1.0, 1.0)).radians

        return if (hourAngle.inRadians < 0) azimuth else (1.rotations - azimuth)
    }

    private fun computePassingMoments(altitude: Angle): Array<Moment>? {
        val absHourAngle = acos(
                (sin(altitude) - sin(_declination) * sin(_latitude)) /
                        (cos(_declination) * cos(_latitude))
        ).radians

        if (absHourAngle.isNaN())
            return null

        fun fromHourAngle(hourAngle: Angle): Moment {
            val sinceEpoch = _solarNoon + (hourAngle / 1.rotations).days
            val azimuth by lazy { computeAzimuth(altitude, hourAngle) }

            return object : Moment(this, sinceEpoch.inWholeMilliseconds) {
                override val altitude: Double get() = altitude.inDegrees
                override val azimuth: Double get() = azimuth.inDegrees
            }
        }

        return arrayOf(fromHourAngle(-absHourAngle), fromHourAngle(absHourAngle))
    }

    private fun getMomentAt(sinceEpoch: Duration): Moment {
        val hourAngle = toHourAngle(sinceEpoch)
        val altitude by lazy { computeAltitude(hourAngle) }
        val azimuth by lazy { computeAzimuth(altitude, hourAngle) }

        return object : Moment(this, sinceEpoch.inWholeMilliseconds) {
            override val altitude: Double get() = altitude.inDegrees
            override val azimuth: Double get() = azimuth.inDegrees
        }
    }

    private fun computeEvents(): Map<Event, Moment> {
        val events = sortedMapOf<Event, Moment>()

        fun addEvents(altitude: Angle, risingEvent: Event, settingEvent: Event) {
            computePassingMoments(altitude)?.let { (risingMoment, settingMoment) ->
                events[risingEvent] = risingMoment
                events[settingEvent] = settingMoment
            }
        }

        addEvents(Altitude.AT_SUNRISE, Event.SUNRISE, Event.SUNSET)
        addEvents(Altitude.CIVIL, Event.CIVIL_DAWN, Event.CIVIL_DUSK)
        addEvents(Altitude.NAUTICAL, Event.NAUTICAL_DAWN, Event.NAUTICAL_DUSK)
        addEvents(Altitude.ASTRONOMICAL, Event.ASTRONOMICAL_DAWN, Event.ASTRONOMICAL_DUSK)

        return events
    }

    private fun distanceTo(latitude: Angle, longitude: Angle): Double {
        val deltaLat = _latitude - latitude
        val deltaLong = _longitude - longitude

        val a = sin(deltaLat * 0.5) * sin(deltaLat * 0.5) +
                cos(_latitude) * cos(latitude) *
                sin(deltaLong * 0.5) * sin(deltaLong * 0.5)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return 6371e3 * c
    }

    companion object {
        private val JAN_1_2000_UTC = 10957.days // since epoch

        /**
         * To update the location and time with relatively minor differences, it allows you to reuse unnecessary repetitive calculations.
         *
         * @param tolerance The distance threshold (in meters) considered as close. (default = 1km)
         */
        @JvmStatic
        @JvmOverloads
        fun tracker(tolerance: Double = 1000.0): Tracker {
            var last: LocalSolar? = null

            return Tracker { latitude, longitude, time ->
                val prev = last
                val distance = prev?.distanceTo(latitude.degrees, longitude.degrees)

                val curr = if (distance == null || distance > tolerance || time !in prev.localMeanDay) {
                    LocalSolar(latitude, longitude, time)
                } else prev

                last = curr

                curr.getMomentAt(time)
            }
        }
    }

    fun interface Tracker {
        fun track(latitude: Double, longitude: Double, time: Long): Moment
    }
}
