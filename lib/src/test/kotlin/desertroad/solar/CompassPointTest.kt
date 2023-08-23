package desertroad.solar

import kotlin.test.Test
import kotlin.test.assertEquals

private const val EPSILON = 0.1

class CompassPointTest {
    @Test
    fun testOn4Wind() {
        (-3..3).forEach {
            val rotation = it * 360.0

            val north = rotation + 0.0
            val east = rotation + 90.0
            val south = rotation + 180.0
            val west = rotation + 270.0

            assertEquals(CompassPoint.N, CompassPoint.closestOn4WindOf(north))
            assertEquals(CompassPoint.E, CompassPoint.closestOn4WindOf(east))
            assertEquals(CompassPoint.S, CompassPoint.closestOn4WindOf(south))
            assertEquals(CompassPoint.W, CompassPoint.closestOn4WindOf(west))

            // boundaries
            val northeast = rotation + 45.0
            val southeast = rotation + 135.0
            val southwest = rotation + 225.0
            val northwest = rotation + 315.0

            assertEquals(CompassPoint.N, CompassPoint.closestOn4WindOf(northeast - EPSILON))
            assertEquals(CompassPoint.E, CompassPoint.closestOn4WindOf(northeast + EPSILON))

            assertEquals(CompassPoint.E, CompassPoint.closestOn4WindOf(southeast - EPSILON))
            assertEquals(CompassPoint.S, CompassPoint.closestOn4WindOf(southeast + EPSILON))

            assertEquals(CompassPoint.S, CompassPoint.closestOn4WindOf(southwest - EPSILON))
            assertEquals(CompassPoint.W, CompassPoint.closestOn4WindOf(southwest + EPSILON))

            assertEquals(CompassPoint.W, CompassPoint.closestOn4WindOf(northwest - EPSILON))
            assertEquals(CompassPoint.N, CompassPoint.closestOn4WindOf(northwest + EPSILON))
        }

    }

    @Test
    fun testOn8Wind() {
        (-3..3).forEach {
            val rotation = it * 360.0

            val north = rotation + 0.0
            val northeast = rotation + 45.0
            val east = rotation + 90.0
            val southeast = rotation + 135.0
            val south = rotation + 180.0
            val southwest = rotation + 225.0
            val west = rotation + 270.0
            val northwest = rotation + 315.0

            assertEquals(CompassPoint.N, CompassPoint.closestOn8WindOf(north))
            assertEquals(CompassPoint.NE, CompassPoint.closestOn8WindOf(northeast))
            assertEquals(CompassPoint.E, CompassPoint.closestOn8WindOf(east))
            assertEquals(CompassPoint.SE, CompassPoint.closestOn8WindOf(southeast))
            assertEquals(CompassPoint.S, CompassPoint.closestOn8WindOf(south))
            assertEquals(CompassPoint.SW, CompassPoint.closestOn8WindOf(southwest))
            assertEquals(CompassPoint.W, CompassPoint.closestOn8WindOf(west))
            assertEquals(CompassPoint.NW, CompassPoint.closestOn8WindOf(northwest))

            // boundaries
            val northNortheast = rotation + 22.5
            val eastNortheast = rotation + 67.5
            val eastSoutheast = rotation + 112.5
            val southSoutheast = rotation + 157.5
            val southSouthwest = rotation + 202.5
            val westSouthwest = rotation + 247.5
            val westNorthwest = rotation + 292.5
            val northNorthwest = rotation + 337.5

            assertEquals(CompassPoint.N, CompassPoint.closestOn8WindOf(northNortheast - EPSILON))
            assertEquals(CompassPoint.NE, CompassPoint.closestOn8WindOf(northNortheast + EPSILON))

            assertEquals(CompassPoint.NE, CompassPoint.closestOn8WindOf(eastNortheast - EPSILON))
            assertEquals(CompassPoint.E, CompassPoint.closestOn8WindOf(eastNortheast + EPSILON))

            assertEquals(CompassPoint.E, CompassPoint.closestOn8WindOf(eastSoutheast - EPSILON))
            assertEquals(CompassPoint.SE, CompassPoint.closestOn8WindOf(eastSoutheast + EPSILON))

            assertEquals(CompassPoint.SE, CompassPoint.closestOn8WindOf(southSoutheast - EPSILON))
            assertEquals(CompassPoint.S, CompassPoint.closestOn8WindOf(southSoutheast + EPSILON))

            assertEquals(CompassPoint.S, CompassPoint.closestOn8WindOf(southSouthwest - EPSILON))
            assertEquals(CompassPoint.SW, CompassPoint.closestOn8WindOf(southSouthwest + EPSILON))

            assertEquals(CompassPoint.SW, CompassPoint.closestOn8WindOf(westSouthwest - EPSILON))
            assertEquals(CompassPoint.W, CompassPoint.closestOn8WindOf(westSouthwest + EPSILON))

            assertEquals(CompassPoint.W, CompassPoint.closestOn8WindOf(westNorthwest - EPSILON))
            assertEquals(CompassPoint.NW, CompassPoint.closestOn8WindOf(westNorthwest + EPSILON))

            assertEquals(CompassPoint.NW, CompassPoint.closestOn8WindOf(northNorthwest - EPSILON))
            assertEquals(CompassPoint.N, CompassPoint.closestOn8WindOf(northNorthwest + EPSILON))
        }
    }

    @Test
    fun testOn16Wind() {
        (-3..3).forEach {
            val rotation = it * 360.0

            val north = rotation + 0.0
            val northNortheast = rotation + 22.5
            val northeast = rotation + 45.0
            val eastNortheast = rotation + 67.5
            val east = rotation + 90.0
            val eastSoutheast = rotation + 112.5
            val southeast = rotation + 135.0
            val southSoutheast = rotation + 157.5
            val south = rotation + 180.0
            val southSouthwest = rotation + 202.5
            val southwest = rotation + 225.0
            val westSouthwest = rotation + 247.5
            val west = rotation + 270.0
            val westNorthwest = rotation + 292.5
            val northwest = rotation + 315.0
            val northNorthwest = rotation + 337.5

            assertEquals(CompassPoint.N, CompassPoint.closestOn16WindOf(north))
            assertEquals(CompassPoint.NNE, CompassPoint.closestOn16WindOf(northNortheast))
            assertEquals(CompassPoint.NE, CompassPoint.closestOn16WindOf(northeast))
            assertEquals(CompassPoint.ENE, CompassPoint.closestOn16WindOf(eastNortheast))
            assertEquals(CompassPoint.E, CompassPoint.closestOn16WindOf(east))
            assertEquals(CompassPoint.ESE, CompassPoint.closestOn16WindOf(eastSoutheast))
            assertEquals(CompassPoint.SE, CompassPoint.closestOn16WindOf(southeast))
            assertEquals(CompassPoint.SSE, CompassPoint.closestOn16WindOf(southSoutheast))
            assertEquals(CompassPoint.S, CompassPoint.closestOn16WindOf(south))
            assertEquals(CompassPoint.SSW, CompassPoint.closestOn16WindOf(southSouthwest))
            assertEquals(CompassPoint.SW, CompassPoint.closestOn16WindOf(southwest))
            assertEquals(CompassPoint.WSW, CompassPoint.closestOn16WindOf(westSouthwest))
            assertEquals(CompassPoint.W, CompassPoint.closestOn16WindOf(west))
            assertEquals(CompassPoint.WNW, CompassPoint.closestOn16WindOf(westNorthwest))
            assertEquals(CompassPoint.NW, CompassPoint.closestOn16WindOf(northwest))
            assertEquals(CompassPoint.NNW, CompassPoint.closestOn16WindOf(northNorthwest))

            // boundaries
            val northByEast = rotation + 11.3
            val northeastByNorth = rotation + 33.8
            val northeastByEast = rotation + 56.3
            val eastByNorth = rotation + 78.8
            val eastBySouth = rotation + 101.3
            val southeastByEast = rotation + 123.8
            val southeastBySouth = rotation + 146.3
            val andSouthByEast = rotation + 168.8
            val southByWest = rotation + 191.3
            val southwestBySouth = rotation + 213.8
            val southwestByWest = rotation + 236.3
            val andWestBySouth = rotation + 258.8
            val westByNorth = rotation + 281.3
            val northwestByWest = rotation + 303.8
            val northwestByNorth = rotation + 326.3
            val northByWest = rotation + 348.8

            assertEquals(CompassPoint.N, CompassPoint.closestOn16WindOf(northByEast - EPSILON))
            assertEquals(CompassPoint.NNE, CompassPoint.closestOn16WindOf(northByEast + EPSILON))

            assertEquals(CompassPoint.NNE, CompassPoint.closestOn16WindOf(northeastByNorth - EPSILON))
            assertEquals(CompassPoint.NE, CompassPoint.closestOn16WindOf(northeastByNorth + EPSILON))

            assertEquals(CompassPoint.NE, CompassPoint.closestOn16WindOf(northeastByEast - EPSILON))
            assertEquals(CompassPoint.ENE, CompassPoint.closestOn16WindOf(northeastByEast + EPSILON))

            assertEquals(CompassPoint.ENE, CompassPoint.closestOn16WindOf(eastByNorth - EPSILON))
            assertEquals(CompassPoint.E, CompassPoint.closestOn16WindOf(eastByNorth + EPSILON))

            assertEquals(CompassPoint.E, CompassPoint.closestOn16WindOf(eastBySouth - EPSILON))
            assertEquals(CompassPoint.ESE, CompassPoint.closestOn16WindOf(eastBySouth + EPSILON))

            assertEquals(CompassPoint.ESE, CompassPoint.closestOn16WindOf(southeastByEast - EPSILON))
            assertEquals(CompassPoint.SE, CompassPoint.closestOn16WindOf(southeastByEast + EPSILON))

            assertEquals(CompassPoint.SE, CompassPoint.closestOn16WindOf(southeastBySouth - EPSILON))
            assertEquals(CompassPoint.SSE, CompassPoint.closestOn16WindOf(southeastBySouth + EPSILON))

            assertEquals(CompassPoint.SSE, CompassPoint.closestOn16WindOf(andSouthByEast - EPSILON))
            assertEquals(CompassPoint.S, CompassPoint.closestOn16WindOf(andSouthByEast + EPSILON))

            assertEquals(CompassPoint.S, CompassPoint.closestOn16WindOf(southByWest - EPSILON))
            assertEquals(CompassPoint.SSW, CompassPoint.closestOn16WindOf(southByWest + EPSILON))

            assertEquals(CompassPoint.SSW, CompassPoint.closestOn16WindOf(southwestBySouth - EPSILON))
            assertEquals(CompassPoint.SW, CompassPoint.closestOn16WindOf(southwestBySouth + EPSILON))

            assertEquals(CompassPoint.SW, CompassPoint.closestOn16WindOf(southwestByWest - EPSILON))
            assertEquals(CompassPoint.WSW, CompassPoint.closestOn16WindOf(southwestByWest + EPSILON))

            assertEquals(CompassPoint.WSW, CompassPoint.closestOn16WindOf(andWestBySouth - EPSILON))
            assertEquals(CompassPoint.W, CompassPoint.closestOn16WindOf(andWestBySouth + EPSILON))

            assertEquals(CompassPoint.W, CompassPoint.closestOn16WindOf(westByNorth - EPSILON))
            assertEquals(CompassPoint.WNW, CompassPoint.closestOn16WindOf(westByNorth + EPSILON))

            assertEquals(CompassPoint.WNW, CompassPoint.closestOn16WindOf(northwestByWest - EPSILON))
            assertEquals(CompassPoint.NW, CompassPoint.closestOn16WindOf(northwestByWest + EPSILON))

            assertEquals(CompassPoint.NW, CompassPoint.closestOn16WindOf(northwestByNorth - EPSILON))
            assertEquals(CompassPoint.NNW, CompassPoint.closestOn16WindOf(northwestByNorth + EPSILON))

            assertEquals(CompassPoint.NNW, CompassPoint.closestOn16WindOf(northByWest - EPSILON))
            assertEquals(CompassPoint.N, CompassPoint.closestOn16WindOf(northByWest + EPSILON))
        }
    }

    @Test
    fun testOn32Wind() {
        (-3..3).forEach {
            val rotation = it * 360.0

            val north = rotation + 0.0
            val northByEast = rotation + 11.3
            val northNortheast = rotation + 22.5
            val northeastByNorth = rotation + 33.8
            val northeast = rotation + 45.0
            val northeastByEast = rotation + 56.3
            val eastNortheast = rotation + 67.5
            val eastByNorth = rotation + 78.8
            val east = rotation + 90.0
            val eastBySouth = rotation + 101.3
            val eastSoutheast = rotation + 112.5
            val southeastByEast = rotation + 123.8
            val southeast = rotation + 135.0
            val southeastBySouth = rotation + 146.3
            val southSoutheast = rotation + 157.5
            val andSouthByEast = rotation + 168.8
            val south = rotation + 180.0
            val southByWest = rotation + 191.3
            val southSouthwest = rotation + 202.5
            val southwestBySouth = rotation + 213.8
            val southwest = rotation + 225.0
            val southwestByWest = rotation + 236.3
            val westSouthwest = rotation + 247.5
            val andWestBySouth = rotation + 258.8
            val west = rotation + 270.0
            val westByNorth = rotation + 281.3
            val westNorthwest = rotation + 292.5
            val northwestByWest = rotation + 303.8
            val northwest = rotation + 315.0
            val northwestByNorth = rotation + 326.3
            val northNorthwest = rotation + 337.5
            val northByWest = rotation + 348.8

            assertEquals(CompassPoint.N, CompassPoint.closestOn32WindOf(north))
            assertEquals(CompassPoint.NbE, CompassPoint.closestOn32WindOf(northByEast))
            assertEquals(CompassPoint.NNE, CompassPoint.closestOn32WindOf(northNortheast))
            assertEquals(CompassPoint.NEbN, CompassPoint.closestOn32WindOf(northeastByNorth))
            assertEquals(CompassPoint.NE, CompassPoint.closestOn32WindOf(northeast))
            assertEquals(CompassPoint.NEbE, CompassPoint.closestOn32WindOf(northeastByEast))
            assertEquals(CompassPoint.ENE, CompassPoint.closestOn32WindOf(eastNortheast))
            assertEquals(CompassPoint.EbN, CompassPoint.closestOn32WindOf(eastByNorth))
            assertEquals(CompassPoint.E, CompassPoint.closestOn32WindOf(east))
            assertEquals(CompassPoint.EbS, CompassPoint.closestOn32WindOf(eastBySouth))
            assertEquals(CompassPoint.ESE, CompassPoint.closestOn32WindOf(eastSoutheast))
            assertEquals(CompassPoint.SEbE, CompassPoint.closestOn32WindOf(southeastByEast))
            assertEquals(CompassPoint.SE, CompassPoint.closestOn32WindOf(southeast))
            assertEquals(CompassPoint.SEbS, CompassPoint.closestOn32WindOf(southeastBySouth))
            assertEquals(CompassPoint.SSE, CompassPoint.closestOn32WindOf(southSoutheast))
            assertEquals(CompassPoint.SbE, CompassPoint.closestOn32WindOf(andSouthByEast))
            assertEquals(CompassPoint.S, CompassPoint.closestOn32WindOf(south))
            assertEquals(CompassPoint.SbW, CompassPoint.closestOn32WindOf(southByWest))
            assertEquals(CompassPoint.SSW, CompassPoint.closestOn32WindOf(southSouthwest))
            assertEquals(CompassPoint.SWbS, CompassPoint.closestOn32WindOf(southwestBySouth))
            assertEquals(CompassPoint.SW, CompassPoint.closestOn32WindOf(southwest))
            assertEquals(CompassPoint.SWbW, CompassPoint.closestOn32WindOf(southwestByWest))
            assertEquals(CompassPoint.WSW, CompassPoint.closestOn32WindOf(westSouthwest))
            assertEquals(CompassPoint.WbS, CompassPoint.closestOn32WindOf(andWestBySouth))
            assertEquals(CompassPoint.W, CompassPoint.closestOn32WindOf(west))
            assertEquals(CompassPoint.WbN, CompassPoint.closestOn32WindOf(westByNorth))
            assertEquals(CompassPoint.WNW, CompassPoint.closestOn32WindOf(westNorthwest))
            assertEquals(CompassPoint.NWbW, CompassPoint.closestOn32WindOf(northwestByWest))
            assertEquals(CompassPoint.NW, CompassPoint.closestOn32WindOf(northwest))
            assertEquals(CompassPoint.NWbN, CompassPoint.closestOn32WindOf(northwestByNorth))
            assertEquals(CompassPoint.NNW, CompassPoint.closestOn32WindOf(northNorthwest))
            assertEquals(CompassPoint.NbW, CompassPoint.closestOn32WindOf(northByWest))
        }
    }
}