import 'package:flutter_test/flutter_test.dart';
import 'package:local_solar/src/compass_point.dart';

const _epsilon = 0.001;

void main() {
  test('test on 4-wind', () {
    for (var r = -3; r <= 3; r++) {
      final rotation = r * 360.0;

      final north = rotation + 0.0;
      final east = rotation + 90.0;
      final south = rotation + 180.0;
      final west = rotation + 270.0;

      expect(CompassPoint.closestOn4WindOf(north), CompassPoint.north);
      expect(CompassPoint.closestOn4WindOf(east), CompassPoint.east);
      expect(CompassPoint.closestOn4WindOf(south), CompassPoint.south);
      expect(CompassPoint.closestOn4WindOf(west), CompassPoint.west);

      // boundaries
      final northeast = rotation + 45.0;
      final southeast = rotation + 135.0;
      final southwest = rotation + 225.0;
      final northwest = rotation + 315.0;

      expect(CompassPoint.closestOn4WindOf(northeast - _epsilon),
          CompassPoint.north);
      expect(CompassPoint.closestOn4WindOf(northeast + _epsilon),
          CompassPoint.east);

      expect(CompassPoint.closestOn4WindOf(southeast - _epsilon),
          CompassPoint.east);
      expect(CompassPoint.closestOn4WindOf(southeast + _epsilon),
          CompassPoint.south);

      expect(CompassPoint.closestOn4WindOf(southwest - _epsilon),
          CompassPoint.south);
      expect(CompassPoint.closestOn4WindOf(southwest + _epsilon),
          CompassPoint.west);

      expect(CompassPoint.closestOn4WindOf(northwest - _epsilon),
          CompassPoint.west);
      expect(CompassPoint.closestOn4WindOf(northwest + _epsilon),
          CompassPoint.north);
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

      expect(CompassPoint.closestOn8WindOf(north), CompassPoint.north);
      expect(CompassPoint.closestOn8WindOf(northeast), CompassPoint.northeast);
      expect(CompassPoint.closestOn8WindOf(east), CompassPoint.east);
      expect(CompassPoint.closestOn8WindOf(southeast), CompassPoint.southeast);
      expect(CompassPoint.closestOn8WindOf(south), CompassPoint.south);
      expect(CompassPoint.closestOn8WindOf(southwest), CompassPoint.southwest);
      expect(CompassPoint.closestOn8WindOf(west), CompassPoint.west);
      expect(CompassPoint.closestOn8WindOf(northwest), CompassPoint.northwest);

      // boundaries
      final northNortheast = rotation + 22.5;
      final eastNortheast = rotation + 67.5;
      final eastSoutheast = rotation + 112.5;
      final southSoutheast = rotation + 157.5;
      final southSouthwest = rotation + 202.5;
      final westSouthwest = rotation + 247.5;
      final westNorthwest = rotation + 292.5;
      final northNorthwest = rotation + 337.5;

      expect(CompassPoint.closestOn8WindOf(northNortheast - _epsilon),
          CompassPoint.north);
      expect(CompassPoint.closestOn8WindOf(northNortheast + _epsilon),
          CompassPoint.northeast);

      expect(CompassPoint.closestOn8WindOf(eastNortheast - _epsilon),
          CompassPoint.northeast);
      expect(CompassPoint.closestOn8WindOf(eastNortheast + _epsilon),
          CompassPoint.east);

      expect(CompassPoint.closestOn8WindOf(eastSoutheast - _epsilon),
          CompassPoint.east);
      expect(CompassPoint.closestOn8WindOf(eastSoutheast + _epsilon),
          CompassPoint.southeast);

      expect(CompassPoint.closestOn8WindOf(southSoutheast - _epsilon),
          CompassPoint.southeast);
      expect(CompassPoint.closestOn8WindOf(southSoutheast + _epsilon),
          CompassPoint.south);

      expect(CompassPoint.closestOn8WindOf(southSouthwest - _epsilon),
          CompassPoint.south);
      expect(CompassPoint.closestOn8WindOf(southSouthwest + _epsilon),
          CompassPoint.southwest);

      expect(CompassPoint.closestOn8WindOf(westSouthwest - _epsilon),
          CompassPoint.southwest);
      expect(CompassPoint.closestOn8WindOf(westSouthwest + _epsilon),
          CompassPoint.west);

      expect(CompassPoint.closestOn8WindOf(westNorthwest - _epsilon),
          CompassPoint.west);
      expect(CompassPoint.closestOn8WindOf(westNorthwest + _epsilon),
          CompassPoint.northwest);

      expect(CompassPoint.closestOn8WindOf(northNorthwest - _epsilon),
          CompassPoint.northwest);
      expect(CompassPoint.closestOn8WindOf(northNorthwest + _epsilon),
          CompassPoint.north);
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

      expect(CompassPoint.closestOn16WindOf(north), CompassPoint.north);
      expect(CompassPoint.closestOn16WindOf(northNortheast),
          CompassPoint.northNortheast);
      expect(CompassPoint.closestOn16WindOf(northeast), CompassPoint.northeast);
      expect(CompassPoint.closestOn16WindOf(eastNortheast),
          CompassPoint.eastNortheast);
      expect(CompassPoint.closestOn16WindOf(east), CompassPoint.east);
      expect(CompassPoint.closestOn16WindOf(eastSoutheast),
          CompassPoint.eastSoutheast);
      expect(CompassPoint.closestOn16WindOf(southeast), CompassPoint.southeast);
      expect(CompassPoint.closestOn16WindOf(southSoutheast),
          CompassPoint.southSoutheast);
      expect(CompassPoint.closestOn16WindOf(south), CompassPoint.south);
      expect(CompassPoint.closestOn16WindOf(southSouthwest),
          CompassPoint.southSouthwest);
      expect(CompassPoint.closestOn16WindOf(southwest), CompassPoint.southwest);
      expect(CompassPoint.closestOn16WindOf(westSouthwest),
          CompassPoint.westSouthwest);
      expect(CompassPoint.closestOn16WindOf(west), CompassPoint.west);
      expect(CompassPoint.closestOn16WindOf(westNorthwest),
          CompassPoint.westNorthwest);
      expect(CompassPoint.closestOn16WindOf(northwest), CompassPoint.northwest);
      expect(CompassPoint.closestOn16WindOf(northNorthwest),
          CompassPoint.northNorthwest);

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

      expect(CompassPoint.closestOn16WindOf(northByEast - _epsilon),
          CompassPoint.north);
      expect(CompassPoint.closestOn16WindOf(northByEast + _epsilon),
          CompassPoint.northNortheast);

      expect(CompassPoint.closestOn16WindOf(northeastByNorth - _epsilon),
          CompassPoint.northNortheast);
      expect(CompassPoint.closestOn16WindOf(northeastByNorth + _epsilon),
          CompassPoint.northeast);

      expect(CompassPoint.closestOn16WindOf(northeastByEast - _epsilon),
          CompassPoint.northeast);
      expect(CompassPoint.closestOn16WindOf(northeastByEast + _epsilon),
          CompassPoint.eastNortheast);

      expect(CompassPoint.closestOn16WindOf(eastByNorth - _epsilon),
          CompassPoint.eastNortheast);
      expect(CompassPoint.closestOn16WindOf(eastByNorth + _epsilon),
          CompassPoint.east);

      expect(CompassPoint.closestOn16WindOf(eastBySouth - _epsilon),
          CompassPoint.east);
      expect(CompassPoint.closestOn16WindOf(eastBySouth + _epsilon),
          CompassPoint.eastSoutheast);

      expect(CompassPoint.closestOn16WindOf(southeastByEast - _epsilon),
          CompassPoint.eastSoutheast);
      expect(CompassPoint.closestOn16WindOf(southeastByEast + _epsilon),
          CompassPoint.southeast);

      expect(CompassPoint.closestOn16WindOf(southeastBySouth - _epsilon),
          CompassPoint.southeast);
      expect(CompassPoint.closestOn16WindOf(southeastBySouth + _epsilon),
          CompassPoint.southSoutheast);

      expect(CompassPoint.closestOn16WindOf(southByEast - _epsilon),
          CompassPoint.southSoutheast);
      expect(CompassPoint.closestOn16WindOf(southByEast + _epsilon),
          CompassPoint.south);

      expect(CompassPoint.closestOn16WindOf(southByWest - _epsilon),
          CompassPoint.south);
      expect(CompassPoint.closestOn16WindOf(southByWest + _epsilon),
          CompassPoint.southSouthwest);

      expect(CompassPoint.closestOn16WindOf(southwestBySouth - _epsilon),
          CompassPoint.southSouthwest);
      expect(CompassPoint.closestOn16WindOf(southwestBySouth + _epsilon),
          CompassPoint.southwest);

      expect(CompassPoint.closestOn16WindOf(southwestByWest - _epsilon),
          CompassPoint.southwest);
      expect(CompassPoint.closestOn16WindOf(southwestByWest + _epsilon),
          CompassPoint.westSouthwest);

      expect(CompassPoint.closestOn16WindOf(westBySouth - _epsilon),
          CompassPoint.westSouthwest);
      expect(CompassPoint.closestOn16WindOf(westBySouth + _epsilon),
          CompassPoint.west);

      expect(CompassPoint.closestOn16WindOf(westByNorth - _epsilon),
          CompassPoint.west);
      expect(CompassPoint.closestOn16WindOf(westByNorth + _epsilon),
          CompassPoint.westNorthwest);

      expect(CompassPoint.closestOn16WindOf(northwestByWest - _epsilon),
          CompassPoint.westNorthwest);
      expect(CompassPoint.closestOn16WindOf(northwestByWest + _epsilon),
          CompassPoint.northwest);

      expect(CompassPoint.closestOn16WindOf(northwestByNorth - _epsilon),
          CompassPoint.northwest);
      expect(CompassPoint.closestOn16WindOf(northwestByNorth + _epsilon),
          CompassPoint.northNorthwest);

      expect(CompassPoint.closestOn16WindOf(northByWest - _epsilon),
          CompassPoint.northNorthwest);
      expect(CompassPoint.closestOn16WindOf(northByWest + _epsilon),
          CompassPoint.north);
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

      expect(CompassPoint.closestOn32WindOf(north), CompassPoint.north);
      expect(CompassPoint.closestOn32WindOf(northByEast),
          CompassPoint.northByEast);
      expect(CompassPoint.closestOn32WindOf(northNortheast),
          CompassPoint.northNortheast);
      expect(CompassPoint.closestOn32WindOf(northeastByNorth),
          CompassPoint.northeastByNorth);
      expect(CompassPoint.closestOn32WindOf(northeast), CompassPoint.northeast);
      expect(CompassPoint.closestOn32WindOf(northeastByEast),
          CompassPoint.northeastByEast);
      expect(CompassPoint.closestOn32WindOf(eastNortheast),
          CompassPoint.eastNortheast);
      expect(CompassPoint.closestOn32WindOf(eastByNorth),
          CompassPoint.eastByNorth);
      expect(CompassPoint.closestOn32WindOf(east), CompassPoint.east);
      expect(CompassPoint.closestOn32WindOf(eastBySouth),
          CompassPoint.eastBySouth);
      expect(CompassPoint.closestOn32WindOf(eastSoutheast),
          CompassPoint.eastSoutheast);
      expect(CompassPoint.closestOn32WindOf(southeastByEast),
          CompassPoint.southeastByEast);
      expect(CompassPoint.closestOn32WindOf(southeast), CompassPoint.southeast);
      expect(CompassPoint.closestOn32WindOf(southeastBySouth),
          CompassPoint.southeastBySouth);
      expect(CompassPoint.closestOn32WindOf(southSoutheast),
          CompassPoint.southSoutheast);
      expect(CompassPoint.closestOn32WindOf(southByEast),
          CompassPoint.southByEast);
      expect(CompassPoint.closestOn32WindOf(south), CompassPoint.south);
      expect(CompassPoint.closestOn32WindOf(southByWest),
          CompassPoint.southByWest);
      expect(CompassPoint.closestOn32WindOf(southSouthwest),
          CompassPoint.southSouthwest);
      expect(CompassPoint.closestOn32WindOf(southwestBySouth),
          CompassPoint.southwestBySouth);
      expect(CompassPoint.closestOn32WindOf(southwest), CompassPoint.southwest);
      expect(CompassPoint.closestOn32WindOf(southwestByWest),
          CompassPoint.southwestByWest);
      expect(CompassPoint.closestOn32WindOf(westSouthwest),
          CompassPoint.westSouthwest);
      expect(CompassPoint.closestOn32WindOf(westBySouth),
          CompassPoint.westBySouth);
      expect(CompassPoint.closestOn32WindOf(west), CompassPoint.west);
      expect(CompassPoint.closestOn32WindOf(westByNorth),
          CompassPoint.westByNorth);
      expect(CompassPoint.closestOn32WindOf(westNorthwest),
          CompassPoint.westNorthwest);
      expect(CompassPoint.closestOn32WindOf(northwestByWest),
          CompassPoint.northwestByWest);
      expect(CompassPoint.closestOn32WindOf(northwest), CompassPoint.northwest);
      expect(CompassPoint.closestOn32WindOf(northwestByNorth),
          CompassPoint.northwestByNorth);
      expect(CompassPoint.closestOn32WindOf(northNorthwest),
          CompassPoint.northNorthwest);
      expect(CompassPoint.closestOn32WindOf(northByWest),
          CompassPoint.northByWest);
    }
  });
}
