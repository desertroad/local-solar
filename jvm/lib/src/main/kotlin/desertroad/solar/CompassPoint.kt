// Copyright 2023 Kim Heeyong
// MIT License

package desertroad.solar

/**
 *
 */
enum class CompassPoint(val label: String) {
    N("north"),
    NbE("north by east"),
    NNE("north-northeast"),
    NEbN("northeast by north"),
    NE("northeast"),
    NEbE("northeast by east"),
    ENE("east-northeast"),
    EbN("east by north"),
    E("east"),
    EbS("east by south"),
    ESE("east-southeast"),
    SEbE("southeast by east"),
    SE("southeast"),
    SEbS("southeast by south"),
    SSE("south-southeast"),
    SbE("south by east"),
    S("south"),
    SbW("south by west"),
    SSW("south-southwest"),
    SWbS("southwest by south"),
    SW("southwest"),
    SWbW("southwest by west"),
    WSW("west-southwest"),
    WbS("west by south"),
    W("west"),
    WbN("west by north"),
    WNW("west-northwest"),
    NWbW("northwest by west"),
    NW("northwest"),
    NWbN("northwest by north"),
    NNW("north-northwest"),
    NbW("north by west"), ;

    val directionInDegrees = ordinal * 360.0 / 32

    companion object {
        private val AVAILABLE_WINDS = listOf(4, 8, 16, 32)

        fun closestValueOf(wind: Int, directionInDegrees: Double): CompassPoint {
            require(wind in AVAILABLE_WINDS) { "[wind] should be one of $AVAILABLE_WINDS" }

            var n = (directionInDegrees / 360.0 + 0.5 / wind) % 1.0
            if (n < 0)
                n += 1.0

            val index = (n * wind).toInt() * 32 / wind

            return entries[index]
        }

        fun closestOn4WindOf(directionInDegrees: Double): CompassPoint = closestValueOf(4, directionInDegrees)

        fun closestOn8WindOf(directionInDegrees: Double): CompassPoint = closestValueOf(8, directionInDegrees)

        fun closestOn16WindOf(directionInDegrees: Double): CompassPoint = closestValueOf(16, directionInDegrees)

        fun closestOn32WindOf(directionInDegrees: Double): CompassPoint = closestValueOf(32, directionInDegrees)
    }
}
