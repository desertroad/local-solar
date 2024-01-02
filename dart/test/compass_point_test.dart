import 'package:flutter_test/flutter_test.dart';
import 'package:local_solar/src/compass_point.dart';

const _EPSILON = 0.001;

void main() {
  test('test on 4-wind', () {
    for (var r = -3; r <= 3; r++) {
      final rotation = r * 360.0;

      final north = rotation + 0.0;
      final east = rotation + 90.0;
      final south = rotation + 180.0;
      final west = rotation + 270.0;

      expect(CompassPoint.closestOn4WindOf(north), CompassPoint.N);
      expect(CompassPoint.closestOn4WindOf(east), CompassPoint.E);
      expect(CompassPoint.closestOn4WindOf(south), CompassPoint.S);
      expect(CompassPoint.closestOn4WindOf(west), CompassPoint.W);

      // boundaries
      final northeast = rotation + 45.0;
      final southeast = rotation + 135.0;
      final southwest = rotation + 225.0;
      final northwest = rotation + 315.0;

      expect(
          CompassPoint.closestOn4WindOf(northeast - _EPSILON), CompassPoint.N);
      expect(
          CompassPoint.closestOn4WindOf(northeast + _EPSILON), CompassPoint.E);

      expect(
          CompassPoint.closestOn4WindOf(southeast - _EPSILON), CompassPoint.E);
      expect(
          CompassPoint.closestOn4WindOf(southeast + _EPSILON), CompassPoint.S);

      expect(
          CompassPoint.closestOn4WindOf(southwest - _EPSILON), CompassPoint.S);
      expect(
          CompassPoint.closestOn4WindOf(southwest + _EPSILON), CompassPoint.W);

      expect(
          CompassPoint.closestOn4WindOf(northwest - _EPSILON), CompassPoint.W);
      expect(
          CompassPoint.closestOn4WindOf(northwest + _EPSILON), CompassPoint.N);
    }
  });

  test('test on 8-wind', () {
    for (var r = -3; r <= 3; r++) {
      final rotation = r * 360.0;

      final north = rotation + 0.0;
      final northeast = rotation + 45.0;
      final east = rotation + 90.0;
      final southeast = rotation + 135.0;
      final south = rotation + 180.0;
      final southwest = rotation + 225.0;
      final west = rotation + 270.0;
      final northwest = rotation + 315.0;

      expect(CompassPoint.closestOn8WindOf(north), CompassPoint.N);
      expect(CompassPoint.closestOn8WindOf(northeast), CompassPoint.NE);
      expect(CompassPoint.closestOn8WindOf(east), CompassPoint.E);
      expect(CompassPoint.closestOn8WindOf(southeast), CompassPoint.SE);
      expect(CompassPoint.closestOn8WindOf(south), CompassPoint.S);
      expect(CompassPoint.closestOn8WindOf(southwest), CompassPoint.SW);
      expect(CompassPoint.closestOn8WindOf(west), CompassPoint.W);
      expect(CompassPoint.closestOn8WindOf(northwest), CompassPoint.NW);

      // boundaries
      final northNortheast = rotation + 22.5;
      final eastNortheast = rotation + 67.5;
      final eastSoutheast = rotation + 112.5;
      final southSoutheast = rotation + 157.5;
      final southSouthwest = rotation + 202.5;
      final westSouthwest = rotation + 247.5;
      final westNorthwest = rotation + 292.5;
      final northNorthwest = rotation + 337.5;

      expect(CompassPoint.closestOn8WindOf(northNortheast - _EPSILON),
          CompassPoint.N);
      expect(CompassPoint.closestOn8WindOf(northNortheast + _EPSILON),
          CompassPoint.NE);

      expect(CompassPoint.closestOn8WindOf(eastNortheast - _EPSILON),
          CompassPoint.NE);
      expect(CompassPoint.closestOn8WindOf(eastNortheast + _EPSILON),
          CompassPoint.E);

      expect(CompassPoint.closestOn8WindOf(eastSoutheast - _EPSILON),
          CompassPoint.E);
      expect(CompassPoint.closestOn8WindOf(eastSoutheast + _EPSILON),
          CompassPoint.SE);

      expect(CompassPoint.closestOn8WindOf(southSoutheast - _EPSILON),
          CompassPoint.SE);
      expect(CompassPoint.closestOn8WindOf(southSoutheast + _EPSILON),
          CompassPoint.S);

      expect(CompassPoint.closestOn8WindOf(southSouthwest - _EPSILON),
          CompassPoint.S);
      expect(CompassPoint.closestOn8WindOf(southSouthwest + _EPSILON),
          CompassPoint.SW);

      expect(CompassPoint.closestOn8WindOf(westSouthwest - _EPSILON),
          CompassPoint.SW);
      expect(CompassPoint.closestOn8WindOf(westSouthwest + _EPSILON),
          CompassPoint.W);

      expect(CompassPoint.closestOn8WindOf(westNorthwest - _EPSILON),
          CompassPoint.W);
      expect(CompassPoint.closestOn8WindOf(westNorthwest + _EPSILON),
          CompassPoint.NW);

      expect(CompassPoint.closestOn8WindOf(northNorthwest - _EPSILON),
          CompassPoint.NW);
      expect(CompassPoint.closestOn8WindOf(northNorthwest + _EPSILON),
          CompassPoint.N);
    }
  });

  test('test on 16-wind', () {
    for (var r = -3; r <= 3; r++) {
      final rotation = r * 360.0;

      final north = rotation + 0.0;
      final northNortheast = rotation + 22.5;
      final northeast = rotation + 45.0;
      final eastNortheast = rotation + 67.5;
      final east = rotation + 90.0;
      final eastSoutheast = rotation + 112.5;
      final southeast = rotation + 135.0;
      final southSoutheast = rotation + 157.5;
      final south = rotation + 180.0;
      final southSouthwest = rotation + 202.5;
      final southwest = rotation + 225.0;
      final westSouthwest = rotation + 247.5;
      final west = rotation + 270.0;
      final westNorthwest = rotation + 292.5;
      final northwest = rotation + 315.0;
      final northNorthwest = rotation + 337.5;

      expect(CompassPoint.closestOn16WindOf(north), CompassPoint.N);
      expect(CompassPoint.closestOn16WindOf(northNortheast), CompassPoint.NNE);
      expect(CompassPoint.closestOn16WindOf(northeast), CompassPoint.NE);
      expect(CompassPoint.closestOn16WindOf(eastNortheast), CompassPoint.ENE);
      expect(CompassPoint.closestOn16WindOf(east), CompassPoint.E);
      expect(CompassPoint.closestOn16WindOf(eastSoutheast), CompassPoint.ESE);
      expect(CompassPoint.closestOn16WindOf(southeast), CompassPoint.SE);
      expect(CompassPoint.closestOn16WindOf(southSoutheast), CompassPoint.SSE);
      expect(CompassPoint.closestOn16WindOf(south), CompassPoint.S);
      expect(CompassPoint.closestOn16WindOf(southSouthwest), CompassPoint.SSW);
      expect(CompassPoint.closestOn16WindOf(southwest), CompassPoint.SW);
      expect(CompassPoint.closestOn16WindOf(westSouthwest), CompassPoint.WSW);
      expect(CompassPoint.closestOn16WindOf(west), CompassPoint.W);
      expect(CompassPoint.closestOn16WindOf(westNorthwest), CompassPoint.WNW);
      expect(CompassPoint.closestOn16WindOf(northwest), CompassPoint.NW);
      expect(CompassPoint.closestOn16WindOf(northNorthwest), CompassPoint.NNW);

      // boundaries
      final northByEast = rotation + 11.25;
      final northeastByNorth = rotation + 33.75;
      final northeastByEast = rotation + 56.25;
      final eastByNorth = rotation + 78.75;
      final eastBySouth = rotation + 101.25;
      final southeastByEast = rotation + 123.75;
      final southeastBySouth = rotation + 146.25;
      final southByEast = rotation + 168.75;
      final southByWest = rotation + 191.25;
      final southwestBySouth = rotation + 213.75;
      final southwestByWest = rotation + 236.25;
      final westBySouth = rotation + 258.75;
      final westByNorth = rotation + 281.25;
      final northwestByWest = rotation + 303.75;
      final northwestByNorth = rotation + 326.25;
      final northByWest = rotation + 348.75;

      expect(CompassPoint.closestOn16WindOf(northByEast - _EPSILON),
          CompassPoint.N);
      expect(CompassPoint.closestOn16WindOf(northByEast + _EPSILON),
          CompassPoint.NNE);

      expect(CompassPoint.closestOn16WindOf(northeastByNorth - _EPSILON),
          CompassPoint.NNE);
      expect(CompassPoint.closestOn16WindOf(northeastByNorth + _EPSILON),
          CompassPoint.NE);

      expect(CompassPoint.closestOn16WindOf(northeastByEast - _EPSILON),
          CompassPoint.NE);
      expect(CompassPoint.closestOn16WindOf(northeastByEast + _EPSILON),
          CompassPoint.ENE);

      expect(CompassPoint.closestOn16WindOf(eastByNorth - _EPSILON),
          CompassPoint.ENE);
      expect(CompassPoint.closestOn16WindOf(eastByNorth + _EPSILON),
          CompassPoint.E);

      expect(CompassPoint.closestOn16WindOf(eastBySouth - _EPSILON),
          CompassPoint.E);
      expect(CompassPoint.closestOn16WindOf(eastBySouth + _EPSILON),
          CompassPoint.ESE);

      expect(CompassPoint.closestOn16WindOf(southeastByEast - _EPSILON),
          CompassPoint.ESE);
      expect(CompassPoint.closestOn16WindOf(southeastByEast + _EPSILON),
          CompassPoint.SE);

      expect(CompassPoint.closestOn16WindOf(southeastBySouth - _EPSILON),
          CompassPoint.SE);
      expect(CompassPoint.closestOn16WindOf(southeastBySouth + _EPSILON),
          CompassPoint.SSE);

      expect(CompassPoint.closestOn16WindOf(southByEast - _EPSILON),
          CompassPoint.SSE);
      expect(CompassPoint.closestOn16WindOf(southByEast + _EPSILON),
          CompassPoint.S);

      expect(CompassPoint.closestOn16WindOf(southByWest - _EPSILON),
          CompassPoint.S);
      expect(CompassPoint.closestOn16WindOf(southByWest + _EPSILON),
          CompassPoint.SSW);

      expect(CompassPoint.closestOn16WindOf(southwestBySouth - _EPSILON),
          CompassPoint.SSW);
      expect(CompassPoint.closestOn16WindOf(southwestBySouth + _EPSILON),
          CompassPoint.SW);

      expect(CompassPoint.closestOn16WindOf(southwestByWest - _EPSILON),
          CompassPoint.SW);
      expect(CompassPoint.closestOn16WindOf(southwestByWest + _EPSILON),
          CompassPoint.WSW);

      expect(CompassPoint.closestOn16WindOf(westBySouth - _EPSILON),
          CompassPoint.WSW);
      expect(CompassPoint.closestOn16WindOf(westBySouth + _EPSILON),
          CompassPoint.W);

      expect(CompassPoint.closestOn16WindOf(westByNorth - _EPSILON),
          CompassPoint.W);
      expect(CompassPoint.closestOn16WindOf(westByNorth + _EPSILON),
          CompassPoint.WNW);

      expect(CompassPoint.closestOn16WindOf(northwestByWest - _EPSILON),
          CompassPoint.WNW);
      expect(CompassPoint.closestOn16WindOf(northwestByWest + _EPSILON),
          CompassPoint.NW);

      expect(CompassPoint.closestOn16WindOf(northwestByNorth - _EPSILON),
          CompassPoint.NW);
      expect(CompassPoint.closestOn16WindOf(northwestByNorth + _EPSILON),
          CompassPoint.NNW);

      expect(CompassPoint.closestOn16WindOf(northByWest - _EPSILON),
          CompassPoint.NNW);
      expect(CompassPoint.closestOn16WindOf(northByWest + _EPSILON),
          CompassPoint.N);
    }
  });

  test('test on 32-wind', () {
    for (var r = -3; r <= 3; r++) {
      final rotation = r * 360.0;

      final north = rotation + 0.0;
      final northByEast = rotation + 11.25;
      final northNortheast = rotation + 22.5;
      final northeastByNorth = rotation + 33.75;
      final northeast = rotation + 45.0;
      final northeastByEast = rotation + 56.25;
      final eastNortheast = rotation + 67.5;
      final eastByNorth = rotation + 78.75;
      final east = rotation + 90.0;
      final eastBySouth = rotation + 101.25;
      final eastSoutheast = rotation + 112.5;
      final southeastByEast = rotation + 123.75;
      final southeast = rotation + 135.0;
      final southeastBySouth = rotation + 146.25;
      final southSoutheast = rotation + 157.5;
      final southByEast = rotation + 168.75;
      final south = rotation + 180.0;
      final southByWest = rotation + 191.25;
      final southSouthwest = rotation + 202.5;
      final southwestBySouth = rotation + 213.75;
      final southwest = rotation + 225.0;
      final southwestByWest = rotation + 236.25;
      final westSouthwest = rotation + 247.5;
      final westBySouth = rotation + 258.75;
      final west = rotation + 270.0;
      final westByNorth = rotation + 281.25;
      final westNorthwest = rotation + 292.5;
      final northwestByWest = rotation + 303.75;
      final northwest = rotation + 315.0;
      final northwestByNorth = rotation + 326.25;
      final northNorthwest = rotation + 337.5;
      final northByWest = rotation + 348.75;

      expect(CompassPoint.closestOn32WindOf(north), CompassPoint.N);
      expect(CompassPoint.closestOn32WindOf(northByEast), CompassPoint.NbE);
      expect(CompassPoint.closestOn32WindOf(northNortheast), CompassPoint.NNE);
      expect(
          CompassPoint.closestOn32WindOf(northeastByNorth), CompassPoint.NEbN);
      expect(CompassPoint.closestOn32WindOf(northeast), CompassPoint.NE);
      expect(
          CompassPoint.closestOn32WindOf(northeastByEast), CompassPoint.NEbE);
      expect(CompassPoint.closestOn32WindOf(eastNortheast), CompassPoint.ENE);
      expect(CompassPoint.closestOn32WindOf(eastByNorth), CompassPoint.EbN);
      expect(CompassPoint.closestOn32WindOf(east), CompassPoint.E);
      expect(CompassPoint.closestOn32WindOf(eastBySouth), CompassPoint.EbS);
      expect(CompassPoint.closestOn32WindOf(eastSoutheast), CompassPoint.ESE);
      expect(
          CompassPoint.closestOn32WindOf(southeastByEast), CompassPoint.SEbE);
      expect(CompassPoint.closestOn32WindOf(southeast), CompassPoint.SE);
      expect(
          CompassPoint.closestOn32WindOf(southeastBySouth), CompassPoint.SEbS);
      expect(CompassPoint.closestOn32WindOf(southSoutheast), CompassPoint.SSE);
      expect(CompassPoint.closestOn32WindOf(southByEast), CompassPoint.SbE);
      expect(CompassPoint.closestOn32WindOf(south), CompassPoint.S);
      expect(CompassPoint.closestOn32WindOf(southByWest), CompassPoint.SbW);
      expect(CompassPoint.closestOn32WindOf(southSouthwest), CompassPoint.SSW);
      expect(
          CompassPoint.closestOn32WindOf(southwestBySouth), CompassPoint.SWbS);
      expect(CompassPoint.closestOn32WindOf(southwest), CompassPoint.SW);
      expect(
          CompassPoint.closestOn32WindOf(southwestByWest), CompassPoint.SWbW);
      expect(CompassPoint.closestOn32WindOf(westSouthwest), CompassPoint.WSW);
      expect(CompassPoint.closestOn32WindOf(westBySouth), CompassPoint.WbS);
      expect(CompassPoint.closestOn32WindOf(west), CompassPoint.W);
      expect(CompassPoint.closestOn32WindOf(westByNorth), CompassPoint.WbN);
      expect(CompassPoint.closestOn32WindOf(westNorthwest), CompassPoint.WNW);
      expect(
          CompassPoint.closestOn32WindOf(northwestByWest), CompassPoint.NWbW);
      expect(CompassPoint.closestOn32WindOf(northwest), CompassPoint.NW);
      expect(
          CompassPoint.closestOn32WindOf(northwestByNorth), CompassPoint.NWbN);
      expect(CompassPoint.closestOn32WindOf(northNorthwest), CompassPoint.NNW);
      expect(CompassPoint.closestOn32WindOf(northByWest), CompassPoint.NbW);
    }
  });
}
