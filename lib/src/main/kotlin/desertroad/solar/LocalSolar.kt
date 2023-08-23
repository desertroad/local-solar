// Copyright 2023 Kim Heeyong
// MIT License

package desertroad.solar

import kotlin.math.*

private const val MILLIS_IN_MINUTE = 60_000L
private const val MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60L
private const val MILLIS_IN_DAY = MILLIS_IN_HOUR * 24L
private const val DAYS_IN_YEAR = 365.25
private const val TO_DEGREES = 180.0 / PI
private const val TO_RADIANS = PI / 180.0

/**
 * This is for calculating the position of the sun at a specific location and on a given solar day.
 *
 * The range of the solar day is defined solely by [latitudeInDegrees], [longitudeInDegrees], and [millisSinceEpoch], without considering the local time zone.
 *
 * - [Wikipedia - Position of the Sun](https://en.wikipedia.org/wiki/Position_of_the_Sun)
 * - [Wikipedia - Sunrise equation](https://en.wikipedia.org/wiki/Sunrise_equation)
 * - [Wikipedia - Solar zenith angle](https://en.wikipedia.org/wiki/Solar_zenith_angle)
 */
class LocalSolar(
    val latitudeInDegrees: Double,
    val longitudeInDegrees: Double,
    val millisSinceEpoch: Long,
) {
    private val latitudeInRadians: Double = latitudeInDegrees * TO_RADIANS
    private val localMeanOffset: Long = (longitudeInDegrees / 360.0 * MILLIS_IN_DAY).toLong()

    /**
     * The time range of mean solar day.
     */
    val meanSolarDay: LongRange = run {
        val start = (millisSinceEpoch + localMeanOffset) / MILLIS_IN_DAY * MILLIS_IN_DAY - localMeanOffset
        start..<start + MILLIS_IN_DAY
    }

    // Interpolated to simplify the impact of leap years and longitude.
    private val daysSinceEpoch = meanSolarDay.first.toDouble() / MILLIS_IN_DAY + 1

    /**
     * The difference between apparent solar time and mean solar time.
     *
     * [Equation of time](https://en.wikipedia.org/wiki/Equation_of_time)
     */
    val equationOfTime: Long = run {
        val B = (daysSinceEpoch - 81) / DAYS_IN_YEAR % 1.0 * 2 * PI
        val equationOfTimeInMinutes = 9.87 * sin(2 * B) - 7.67 * sin(B + 78.7 * TO_RADIANS)
        (equationOfTimeInMinutes * MILLIS_IN_MINUTE).toLong()
    }

    /**
     * The time range of apparent solar day.
     */
    val apparentSolarDay: LongRange = run {
        val start = meanSolarDay.first - equationOfTime
        start..<start + MILLIS_IN_DAY
    }

    /**
     * The moment when the Sun contacts the observer's meridian.
     */
    val apparentSolarNoon: Long =
        apparentSolarDay.first + MILLIS_IN_DAY / 2

    /**
     * The angular distance between the rays of the sun and the plane of the Earth's equator.
     *
     * [Wikipedia - Declination of the Sun as seen from Earth](https://en.wikipedia.org/wiki/Position_of_the_Sun#Declination_of_the_Sun_as_seen_from_Earth)
     */
    val declinationInRadians: Double =
        -23.44 * TO_RADIANS * cos((daysSinceEpoch + 10) / DAYS_IN_YEAR * 2 * PI)

    /**
     * The position of the sun at [millisSinceEpoch].
     */
    val current: Moment by lazy { computeMomentAt(millisSinceEpoch) }

    /**
     * The position of the sun at [apparentSolarNoon].
     */
    val meridian: Moment by lazy { computeMomentAt(apparentSolarNoon) }

    private fun toHourAngleInRadians(timeInMillis: Long): Double {
        require(timeInMillis in meanSolarDay || timeInMillis in apparentSolarDay) {
            "[timeInMillis] is not within this mean/apparent solar day. Create a new [LocalSolar]"
        }

        return (timeInMillis - apparentSolarNoon).toDouble() / MILLIS_IN_DAY * 2 * PI
    }

    private fun computeMillisFromMeridian(hourAngleInRadians: Double): Long =
        ((0.5 * hourAngleInRadians / PI) * MILLIS_IN_DAY).toLong()

    private fun computeAltitudeInRadians(hourAngleInRadians: Double): Double =
        asin(
            sin(declinationInRadians) * sin(latitudeInRadians) +
                    cos(declinationInRadians) * cos(latitudeInRadians) * cos(hourAngleInRadians)
        )

    private fun computeAzimuthInRadians(altitudeInRadians: Double, hourAngleInRadians: Double): Double {
        val cosAzimuth = (
                sin(declinationInRadians) * cos(latitudeInRadians) -
                        cos(declinationInRadians) * sin(latitudeInRadians) * cos(hourAngleInRadians)
                ) / cos(altitudeInRadians)

        val azi = acos(cosAzimuth.coerceIn(-1.0, 1.0))

        return if (hourAngleInRadians < 0) azi else 2 * PI - azi
    }

    /**
     * Calculating the position of the sun at [timeInMillis]
     */
    fun computeMomentAt(timeInMillis: Long): Moment {
        val hourAngleInRadians = toHourAngleInRadians(timeInMillis)
        val altitudeInRadians = computeAltitudeInRadians(hourAngleInRadians)

        return Moment(
            timeInMillis = timeInMillis,
            altitudeInRadians = altitudeInRadians,
            azimuthInRadians = computeAzimuthInRadians(altitudeInRadians, hourAngleInRadians)
        )
    }

    /**
     * Calculating the position of the sun at the moment when the given [altitudeInDegrees] is crossed.
     *
     * @return If the sun passes the [altitudeInDegrees] during this solar day, the sun's position at rising and setting moments.
     */
    fun computePassingMoments(altitudeInDegrees: Double): Pair<Moment, Moment>? {
        val altitudeInRadians = altitudeInDegrees * TO_RADIANS
        val hourAngleInRadians = acos(
            (sin(altitudeInRadians) - sin(declinationInRadians) * sin(latitudeInRadians)) /
                    (cos(declinationInRadians) * cos(latitudeInRadians))
        )

        if (hourAngleInRadians.isNaN())
            return null

        val millisFromMeridian = computeMillisFromMeridian(hourAngleInRadians)

        return Moment(
            timeInMillis = apparentSolarNoon - millisFromMeridian,
            altitudeInRadians = altitudeInRadians,
            azimuthInRadians = computeAzimuthInRadians(altitudeInRadians, -hourAngleInRadians)
        ) to Moment(
            timeInMillis = apparentSolarNoon + millisFromMeridian,
            altitudeInRadians = altitudeInRadians,
            azimuthInRadians = computeAzimuthInRadians(altitudeInRadians, hourAngleInRadians)
        )
    }

    /**
     * Calculating the transitions of horizon phases for the local solar day.
     */
    fun computeEvents(): Map<Event, Moment> {
        val events = sortedMapOf<Event, Moment>()

        computePassingMoments(Altitude.OFFICIAL)?.let { (sunrise, sunset) ->
            events[Event.SUNRISE] = sunrise
            events[Event.SUNSET] = sunset
        }

        computePassingMoments(Altitude.CIVIL)?.let { (civilDawn, civilDusk) ->
            events[Event.CIVIL_DAWN] = civilDawn
            events[Event.CIVIL_DUSK] = civilDusk
        }

        computePassingMoments(Altitude.NAUTICAL)?.let { (nauticalDawn, nauticalDusk) ->
            events[Event.NAUTICAL_DAWN] = nauticalDawn
            events[Event.NAUTICAL_DUSK] = nauticalDusk
        }

        computePassingMoments(Altitude.ASTRONOMICAL)?.let { (astronomicalDawn, astronomicalDusk) ->
            events[Event.ASTRONOMICAL_DAWN] = astronomicalDawn
            events[Event.ASTRONOMICAL_DUSK] = astronomicalDusk
        }

        return events.toMap()
    }

    /**
     * Collection for horizon boundaries based on solar altitude.
     */
    object Altitude {
        const val OFFICIAL = -50 / 60.0
        const val CIVIL = -6.0
        const val NAUTICAL = -12.0
        const val ASTRONOMICAL = -18.0
    }

    /**
     * Differentiation of horizon phases by altitude.
     */
    enum class Phase {
        DAY,
        CIVIL_TWILIGHT,
        NAUTICAL_TWILIGHT,
        ASTRONOMICAL_TWILIGHT,
        NIGHT, ;

        companion object {
            fun fromAltitude(altitudeInDegrees: Double) = when {
                altitudeInDegrees >= Altitude.OFFICIAL -> DAY
                altitudeInDegrees >= Altitude.CIVIL -> CIVIL_TWILIGHT
                altitudeInDegrees >= Altitude.NAUTICAL -> NAUTICAL_TWILIGHT
                altitudeInDegrees >= Altitude.ASTRONOMICAL -> ASTRONOMICAL_TWILIGHT
                else -> NIGHT
            }
        }
    }

    /**
     * Events triggering transitions in horizon phases.
     */
    enum class Event(val rising: Boolean, val prevPhase: Phase, val nextPhase: Phase) {
        ASTRONOMICAL_DAWN(rising = true, prevPhase = Phase.NIGHT, nextPhase = Phase.ASTRONOMICAL_TWILIGHT),
        NAUTICAL_DAWN(rising = true, prevPhase = Phase.ASTRONOMICAL_TWILIGHT, nextPhase = Phase.NAUTICAL_TWILIGHT),
        CIVIL_DAWN(rising = true, prevPhase = Phase.NAUTICAL_TWILIGHT, nextPhase = Phase.CIVIL_TWILIGHT),
        SUNRISE(rising = true, prevPhase = Phase.CIVIL_TWILIGHT, nextPhase = Phase.DAY),
        SUNSET(rising = false, prevPhase = Phase.DAY, nextPhase = Phase.CIVIL_TWILIGHT),
        CIVIL_DUSK(rising = false, prevPhase = Phase.CIVIL_TWILIGHT, nextPhase = Phase.NAUTICAL_TWILIGHT),
        NAUTICAL_DUSK(rising = false, prevPhase = Phase.NAUTICAL_TWILIGHT, nextPhase = Phase.ASTRONOMICAL_TWILIGHT),
        ASTRONOMICAL_DUSK(rising = false, prevPhase = Phase.ASTRONOMICAL_TWILIGHT, nextPhase = Phase.NIGHT);
    }

    /**
     * The position of the sun at a specific moment.
     */
    data class Moment(val timeInMillis: Long, val altitudeInRadians: Double, val azimuthInRadians: Double) {
        val altitudeInDegrees = altitudeInRadians * TO_DEGREES
        val azimuthInDegrees = azimuthInRadians * TO_DEGREES
        val phase = Phase.fromAltitude(altitudeInDegrees)
    }
}
