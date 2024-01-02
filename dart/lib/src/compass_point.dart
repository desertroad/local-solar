// Copyright 2023 Kim Heeyong
// MIT License

enum CompassPoint {
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
  NbW("north by west"),
  ;

  final String label;

  double get directionInDegrees => index * 360.0 / 32;

  const CompassPoint(this.label);

  static const _AVAILABLE_WINDS = {4, 8, 16, 32};

  static CompassPoint closestValueOf(int wind, double directionInDegrees) {
    assert(_AVAILABLE_WINDS.contains(wind));

    var n = (directionInDegrees / 360.0 + 0.5 / wind) % 1.0;
    if (n < 0) {
      n += 1.0;
    }

    final index = (n * wind).toInt() * 32 ~/ wind;

    return values[index];
  }

  static CompassPoint closestOn4WindOf(double directionInDegrees) => closestValueOf(4, directionInDegrees);

  static CompassPoint closestOn8WindOf(double directionInDegrees) => closestValueOf(8, directionInDegrees);

  static CompassPoint closestOn16WindOf(double directionInDegrees) => closestValueOf(16, directionInDegrees);

  static CompassPoint closestOn32WindOf(double directionInDegrees) => closestValueOf(32, directionInDegrees);
}
