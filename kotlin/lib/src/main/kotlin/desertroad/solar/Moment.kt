package desertroad.solar

import desertroad.solar.internal.Angle.Companion.degrees

abstract class Moment(val localSolar: LocalSolar) {
    abstract val time: Long
    abstract val altitude: Double
    abstract val azimuth: Double

    val zenith get() = 90.0 - altitude

    val phase get() = Phase.fromAltitude(altitude.degrees)

    override fun toString() =
        "Moment(time=[%tc], phase=%s, altitude=%.2f, azimuth=%.2f)".format(time, phase, altitude, azimuth)
}