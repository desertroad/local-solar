package desertroad.solar

import desertroad.solar.internal.Angle.Companion.degrees

/**
 * Contains information about the position of the Sun at a specific [time] from the observer's location.
 *
 * All angular measurements are in degrees.
 */
abstract class Moment internal constructor(@JvmField val localSolar: LocalSolar, @JvmField val time: Long) {
    abstract val altitude: Double

    abstract val azimuth: Double

    val zenith get() = 90.0 - altitude

    val phase by lazy { Phase.fromAltitude(altitude.degrees) }

    override fun toString() =
        "Moment(time=[%tc], phase=%s, altitude=%.2f, azimuth=%.2f)".format(time, phase, altitude, azimuth)
}