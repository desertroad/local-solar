package desertroad.solar

abstract class Moment {
    abstract val time: Long
    abstract val altitude: Double
    abstract val azimuth: Double

    val zenith get() = 90.0 - altitude

    override fun toString() =
        "Moment(time=[%tc], altitude=%.2f, azimuth=%.2f)".format(time, altitude, azimuth)
}