// Copyright 2023 Kim Heeyong
// MIT License

import 'package:local_solar/src/altitude.dart';
import 'package:local_solar/src/angle.dart';

/// Differentiation of horizon phases by altitude.
enum Phase {
  day,
  civilTwilight,
  nauticalTwilight,
  astronomicalTwilight,
  night, ;

  static Phase fromAltitude(Angle altitude) {
    if (altitude.inRadians >= Altitude.atSunrise.inRadians) {
      return Phase.day;
    } else if (altitude.inRadians >= Altitude.civil.inRadians) {
      return Phase.civilTwilight;
    } else if (altitude.inRadians >= Altitude.nautical.inRadians) {
      return Phase.nauticalTwilight;
    } else if (altitude.inRadians >= Altitude.astronomical.inRadians) {
      return Phase.astronomicalTwilight;
    } else {
      return Phase.night;
    }
  }
}