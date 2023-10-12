package desertroad.solar

/**
 * Events triggering transitions in horizon phases.
 */
enum class Event(val rising: Boolean, val prevPhase: Phase, val nextPhase: Phase) {
    ASTRONOMICAL_DAWN(
        rising = true,
        prevPhase = Phase.NIGHT,
        nextPhase = Phase.ASTRONOMICAL_TWILIGHT
    ),
    NAUTICAL_DAWN(
        rising = true,
        prevPhase = Phase.ASTRONOMICAL_TWILIGHT,
        nextPhase = Phase.NAUTICAL_TWILIGHT
    ),
    CIVIL_DAWN(
        rising = true,
        prevPhase = Phase.NAUTICAL_TWILIGHT,
        nextPhase = Phase.CIVIL_TWILIGHT
    ),
    SUNRISE(
        rising = true,
        prevPhase = Phase.CIVIL_TWILIGHT,
        nextPhase = Phase.DAY
    ),
    SUNSET(
        rising = false,
        prevPhase = Phase.DAY,
        nextPhase = Phase.CIVIL_TWILIGHT
    ),
    CIVIL_DUSK(
        rising = false,
        prevPhase = Phase.CIVIL_TWILIGHT,
        nextPhase = Phase.NAUTICAL_TWILIGHT
    ),
    NAUTICAL_DUSK(
        rising = false,
        prevPhase = Phase.NAUTICAL_TWILIGHT,
        nextPhase = Phase.ASTRONOMICAL_TWILIGHT
    ),
    ASTRONOMICAL_DUSK(
        rising = false,
        prevPhase = Phase.ASTRONOMICAL_TWILIGHT,
        nextPhase = Phase.NIGHT
    );

    val setting get() = !rising
}