// Copyright 2023 Kim Heeyong
// MIT License

enum CompassPoint {
  north("N", "north"),
  northByEast("NbE", "north by east"),
  northNortheast("NNE", "north-northeast"),
  northeastByNorth("NEbN", "northeast by north"),
  northeast("NE", "northeast"),
  northeastByEast("NEbE", "northeast by east"),
  eastNortheast("ENE", "east-northeast"),
  eastByNorth("EbN", "east by north"),
  east("E", "east"),
  eastBySouth("EbS", "east by south"),
  eastSoutheast("ESE", "east-southeast"),
  southeastByEast("SEbE", "southeast by east"),
  southeast("SE", "southeast"),
  southeastBySouth("SEbS", "southeast by south"),
  southSoutheast("SSE", "south-southeast"),
  southByEast("SbE", "south by east"),
  south("S", "south"),
  southByWest("SbW", "south by west"),
  southSouthwest("SSW", "south-southwest"),
  southwestBySouth("SWbS", "southwest by south"),
  southwest("SW", "southwest"),
  southwestByWest("SWbW", "southwest by west"),
  westSouthwest("WSW", "west-southwest"),
  westBySouth("WbS", "west by south"),
  west("W", "west"),
  westByNorth("WbN", "west by north"),
  westNorthwest("WNW", "west-northwest"),
  northwestByWest("NWbW", "northwest by west"),
  northwest("NW", "northwest"),
  northwestByNorth("NWbN", "northwest by north"),
  northNorthwest("NNW", "north-northwest"),
  northByWest("NbW", "north by west"),
  ;

  final String shortName;
  final String label;

  double get directionInDegrees => index * 360.0 / 32;

  const CompassPoint(this.shortName, this.label);

  static const _availableWinds = {4, 8, 16, 32};

  static CompassPoint closestValueOf(int wind, double directionInDegrees) {
    assert(_availableWinds.contains(wind));

    var n = (directionInDegrees / 360.0 + 0.5 / wind) % 1.0;
    if (n < 0) {
      n += 1.0;
    }

    final index = (n * wind).toInt() * 32 ~/ wind;

    return values[index];
  }

  static CompassPoint closestOn4WindOf(double directionInDegrees) =>
      closestValueOf(4, directionInDegrees);

  static CompassPoint closestOn8WindOf(double directionInDegrees) =>
      closestValueOf(8, directionInDegrees);

  static CompassPoint closestOn16WindOf(double directionInDegrees) =>
      closestValueOf(16, directionInDegrees);

  static CompassPoint closestOn32WindOf(double directionInDegrees) =>
      closestValueOf(32, directionInDegrees);
}
