package desertroad.solar.units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.times

/**
 * https://en.wikipedia.org/wiki/Tropical_year
 * The mean tropical year on January 1, 2000, was 365.2421897 or ...
 */
internal val TROPICAL_YEAR = 365.242_189_7.days

internal inline val Number.years: Duration
    get() = toDouble() * TROPICAL_YEAR

internal operator fun Duration.rem(other: Duration) =
    (inWholeMilliseconds % other.inWholeMilliseconds).milliseconds
