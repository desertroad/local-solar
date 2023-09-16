package desertroad.solar.units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

/**
 * https://en.wikipedia.org/wiki/Tropical_year
 * The mean tropical year on January 1, 2000, was 365.2421897 or ...
 */
internal val JAN_1_2000_UTC = 10957.days // since epoch

internal operator fun Duration.rem(other: Duration) =
        (inWholeMilliseconds % other.inWholeMilliseconds).milliseconds
