import 'package:flutter_test/flutter_test.dart';
import 'package:local_solar/src/angle.dart';
import 'package:local_solar/src/event.dart';

import 'package:local_solar/src/local_solar.dart';

const _timeTolerance = Duration(seconds: 60);
final _angleTolerance = 0.5.degrees;

void _expectTime(
        {required DateTime actual,
        required DateTime expected,
        required Duration tolerance}) =>
    expect(actual.millisecondsSinceEpoch,
        closeTo(expected.millisecondsSinceEpoch, tolerance.inMilliseconds));

void main() {
  test('on Busan', () {
    final localSolar = LocalSolar(
        latitude: /* 35°11'N */ (35 + 11 / 60.0),
        longitude: /* 129°04'E */ (129 + 4 / 60.0),
        time: DateTime.parse("1980-07-09T12:00+09:00"));

    final noon = localSolar.meridian;
    _expectTime(
      actual: noon.time,
      expected: DateTime.parse("1980-07-09T12:28:47+09:00"),
      tolerance: _timeTolerance,
    );

    expect(noon.altitude, closeTo(77.17, _angleTolerance.inDegrees));

    final events = localSolar.events;

    final sunrise = events[Event.sunrise];
    expect(sunrise, isNotNull);
    _expectTime(
        actual: sunrise!.time,
        expected: DateTime.parse("1980-07-09T05:17+09:00"),
        tolerance: _timeTolerance);
    expect(sunrise.azimuth, closeTo(61.61, _angleTolerance.inDegrees));

    final sunset = events[Event.sunset];
    expect(sunset, isNotNull);
    _expectTime(
        actual: sunset!.time,
        expected: DateTime.parse("1980-07-09T19:41+09:00"),
        tolerance: _timeTolerance);
    expect(sunset.azimuth, closeTo(298.38, _angleTolerance.inDegrees));
  });

  test('on ChristChurch', () {
    final localSolar = LocalSolar(
        latitude: /* 43°32'S */ -(43 + 32 / 60.0),
        longitude: /* 172°38'E */ (172 + 38 / 60.0),
        time: DateTime.parse("2007-01-07T12:00+13:00"));

    final noon = localSolar.meridian;
    _expectTime(
        actual: noon.time,
        expected: DateTime.parse("2007-01-07T13:35:11+13:00"),
        tolerance: _timeTolerance);
    expect(noon.altitude, closeTo(68.92, _angleTolerance.inDegrees));

    final events = localSolar.events;
    final sunrise = events[Event.sunrise];
    expect(sunrise, isNotNull);
    _expectTime(
        actual: sunrise!.time,
        expected: DateTime.parse("2007-01-07T05:57+13:00"),
        tolerance: _timeTolerance);
    expect(sunrise.azimuth, closeTo(122.81, _angleTolerance.inDegrees));

    final sunset = events[Event.sunset];
    expect(sunset, isNotNull);
    _expectTime(
        actual: sunset!.time,
        expected: DateTime.parse("2007-01-07T21:13+13:00"),
        tolerance: _timeTolerance);
    expect(sunset.azimuth, closeTo(237.38, _angleTolerance.inDegrees));
  });

  test('on San Francisco', () {
    final localSolar = LocalSolar(
        latitude: /* 37°47'N */ (37 + 47 / 60.0),
        longitude: /* 122°25'W */ -(122 + 25 / 60.0),
        time: DateTime.parse("2014-08-29T12:00-07:00"));

    final noon = localSolar.meridian;
    _expectTime(
        actual: noon.time,
        expected: DateTime.parse("2014-08-29T13:10:40-07:00"),
        tolerance: _timeTolerance);
    expect(noon.altitude, closeTo(61.38, _angleTolerance.inDegrees));

    final events = localSolar.events;
    final sunrise = events[Event.sunrise];
    expect(sunrise, isNotNull);
    _expectTime(
        actual: sunrise!.time,
        expected: DateTime.parse("2014-08-29T06:37-07:00"),
        tolerance: _timeTolerance);
    expect(sunrise.azimuth, closeTo(77.56, _angleTolerance.inDegrees));

    final sunset = events[Event.sunset];
    expect(sunset, isNotNull);
    _expectTime(
        actual: sunset!.time,
        expected: DateTime.parse("2014-08-29T19:43-07:00"),
        tolerance: _timeTolerance);
    expect(sunset.azimuth, closeTo(282.13, _angleTolerance.inDegrees));
  });

  test('on Tromso', () {
    final localSolar = LocalSolar(
        latitude: /* 69°39'N */ (69 + 39 / 60.0),
        longitude: /* 18°57'E */ (18 + 57 / 60.0),
        time: DateTime.parse("2023-06-14T12:00+02:00"));

    final noon = localSolar.meridian;
    _expectTime(
        actual: noon.time,
        expected: DateTime.parse("2023-06-14T12:44:20+02:00"),
        tolerance: _timeTolerance);
    expect(noon.altitude, closeTo(43.63, _angleTolerance.inDegrees));

    final events = localSolar.events;

    // White night
    expect(events[Event.sunrise], isNull);
    expect(events[Event.sunset], isNull);
  });
}
