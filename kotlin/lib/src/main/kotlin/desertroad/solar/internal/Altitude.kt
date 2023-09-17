package desertroad.solar.internal

import desertroad.solar.internal.Angle.Companion.degrees

/**
 * Collection for horizon boundaries based on solar altitude.
 *
 * https://en.wikipedia.org/wiki/Sunrise#Angle_with_respect_to_horizon
 */
internal object Altitude {
    val AT_SUNRISE = (-50 / 60.0).degrees
    val CIVIL = (-6.0).degrees
    val NAUTICAL = (-12.0).degrees
    val ASTRONOMICAL = (-18.0).degrees
}