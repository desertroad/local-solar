import 'dart:collection';
import 'dart:math' as dart_math;

import 'package:local_solar/src/altitude.dart';
import 'package:local_solar/src/angle.dart';
import 'package:local_solar/src/duration.dart';
import 'package:local_solar/src/event.dart';
import 'package:local_solar/src/moment.dart';

/// This is for calculating the position of the sun at a specific location and on a given solar day.///
/// The range of the solar day is defined solely by [latitude], [longitude], and [time], without considering the local time zone.
///
/// - [Wikipedia: Position of the Sun](https://en.wikipedia.org/wiki/Position_of_the_Sun)
/// - [Wikipedia: Sunrise equation](https://en.wikipedia.org/wiki/Sunrise_equation)
/// - [Wikipedia: Solar zenith angle](https://en.wikipedia.org/wiki/Solar_zenith_angle)
class LocalSolar {
  static final _tropicalYear = 365.2422.days;
  static final _jan1st2000NoonUtc = 10957.5.days; // since epoch
  static const _earthRadiusInMeters = 6371e3;

  final double latitude;
  final double longitude;
  final Duration _time;

  LocalSolar({required this.latitude, required this.longitude, required DateTime time})
      : _time = time.toDuration();

  late final Angle _latitude = latitude.degrees;
  late final Angle _longitude = longitude.degrees;

  late final Duration _localMeanTimeOffset =
      1.days * (_longitude / 1.rotations);
  late final Duration _startOfLocalMeanDay =
      ((_time + _localMeanTimeOffset) / 1.days).floor().days -
          _localMeanTimeOffset;

  /// The equation of time at local mean solar noon.
  ///
  /// [computeEquationOfTime]
  ///
  /// - [Wikipedia: Equation of time](https://en.wikipedia.org/wiki/Equation_of_time)
  late final Duration equationOfTime =
      _computeEquationOfTime(_startOfLocalMeanDay + 0.5.days);

  /// The declination of the Sun at local mean solar noon.
  ///
  /// - [Wikipedia: Declination of the Sun as seen from Earth](https://en.wikipedia.org/wiki/Position_of_the_Sun#Declination_of_the_Sun_as_seen_from_Earth)
  double get declination => _declination.inDegrees;
  late final Angle _declination =
      _computeDeclination(_startOfLocalMeanDay + 0.5.days);

  /// The time of local apparent solar noon
  ///
  /// - [timeanddate: What Is Solar Noon?](https://www.timeanddate.com/astronomy/solar-noon.html)
  DateTime get solarNoon => _solarNoon.toDateTime();
  late final Duration _solarNoon =
      _startOfLocalMeanDay + 0.5.days - equationOfTime;

  /// The position of the Sun at local apparent solar noon
  late final Moment meridian = _getMomentAt(_solarNoon);

  /// The positions and times of the Sun in altitude-dependent events such as sunrise and sunset.
  late final Map<Event, Moment> events = _computeEvents();

  Angle _toHourAngle(Duration time) {
    // require(sinceEpoch in _localMeanDay) {
    // "[sinceEpoch] is not within this Local Mean Day"
    // }

    return ((time - _solarNoon) / 1.days).rotations;
  }

  /// https://en.wikipedia.org/wiki/Solar_zenith_angle
  Angle _computeAltitude(Angle hourAngle) => dart_math
      .asin(sin(_declination) * sin(_latitude) +
          cos(_declination) * cos(_latitude) * cos(hourAngle))
      .radians;

  /// https://en.wikipedia.org/wiki/Solar_azimuth_angle
  Angle _computeAzimuth(Angle altitude, Angle hourAngle) {
    final cosAzimuth = (sin(_declination) * cos(_latitude) -
            cos(_declination) * sin(_latitude) * cos(hourAngle)) /
        cos(altitude);

    final azimuth = dart_math.acos(cosAzimuth.clamp(-1.0, 1.0)).radians;

    return hourAngle.inRadians < 0 ? azimuth : (1.rotations - azimuth);
  }

  static Duration computeEquationOfTime(DateTime time) =>
      _computeEquationOfTime(time.toDuration());

  static Duration _computeEquationOfTime(Duration time) {
    final D = (time - _jan1st2000NoonUtc) / 1.days;
    final M = (6.24004077 + 0.01720197 * D).radians;
    final a = -7.659 * sin(M);
    final b = 9.863 * sin(M * 2 + 3.5932.radians);

    return (a + b).minutes;
  }

  static Angle _computeDeclination(Duration time) {
    /// The variation in the ecliptic longitude occurs on a tropical year cycle,
    /// which is why we utilized Gregorian dates.
    /// By dividing the unix timestamp by the length of a tropical year,
    /// we can eliminate the need for a calendar system.
    final sinEL = cos(((time + 10.days) / _tropicalYear).rotations +
        1.914.degrees * sin(((time - 2.days) / _tropicalYear).rotations));

    return dart_math.asin(sin((-23.44).degrees) * sinEL).radians;
  }

  List<Moment>? _computePassingMoments(Angle altitude) {
    final absHourAngle = dart_math
        .acos((sin(altitude) - sin(_declination) * sin(_latitude)) /
            (cos(_declination) * cos(_latitude)))
        .radians;

    if (absHourAngle.inRadians.isNaN) return null;

    Moment fromHourAngle(Angle hourAngle) {
      final sinceEpoch = _solarNoon + (hourAngle / 1.rotations).days;
      late final azimuth = _computeAzimuth(altitude, hourAngle);

      return Moment(this, sinceEpoch.toDateTime(), () => altitude.inDegrees,
          () => azimuth.inDegrees);
    }

    return [fromHourAngle(absHourAngle * -1), fromHourAngle(absHourAngle)];
  }

  Map<Event, Moment> _computeEvents() {
    final events = SplayTreeMap<Event, Moment>();

    void addEvents(Angle altitude, Event risingEvent, Event settingEvent) {
      final moments = _computePassingMoments(altitude);
      if (moments != null) {
        final [risingMoment, settingMoment] = moments;
        events[risingEvent] = risingMoment;
        events[settingEvent] = settingMoment;
      }
    }

    addEvents(Altitude.atSunrise, Event.sunrise, Event.sunset);
    addEvents(Altitude.civil, Event.civilDawn, Event.civilDusk);
    addEvents(Altitude.nautical, Event.nauticalDawn, Event.nauticalDusk);
    addEvents(
        Altitude.astronomical, Event.astronomicalDawn, Event.astronomicalDusk);

    return events;
  }

  Moment _getMomentAt(Duration sinceEpoch) {
    final hourAngle = _toHourAngle(sinceEpoch);
    late final altitude = _computeAltitude(hourAngle);
    late final azimuth = _computeAzimuth(altitude, hourAngle);

    return Moment(this, sinceEpoch.toDateTime(), () => altitude.inDegrees,
        () => azimuth.inDegrees);
  }

  double _distanceTo(Angle latitude, Angle longitude) {
    final deltaLat = _latitude - latitude;
    final deltaLong = _longitude - longitude;

    final a = sin(deltaLat * 0.5) * sin(deltaLat * 0.5) +
        cos(_latitude) *
            cos(latitude) *
            sin(deltaLong * 0.5) *
            sin(deltaLong * 0.5);

    final c = 2 * dart_math.atan2(dart_math.sqrt(a), dart_math.sqrt(1 - a));

    return _earthRadiusInMeters * c;
  }
}
