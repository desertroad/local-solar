package desertroad.solar

import desertroad.solar.units.Angle

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
        internal fun fromAltitude(altitude: Angle) = when {
            altitude.inRadians >= Altitude.AT_SUNRISE.inRadians -> DAY
            altitude.inRadians >= Altitude.CIVIL.inRadians -> CIVIL_TWILIGHT
            altitude.inRadians >= Altitude.NAUTICAL.inRadians -> NAUTICAL_TWILIGHT
            altitude.inRadians >= Altitude.ASTRONOMICAL.inRadians -> ASTRONOMICAL_TWILIGHT
            else -> NIGHT
        }
    }
}
