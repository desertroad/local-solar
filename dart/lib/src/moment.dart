import 'package:local_solar/src/angle.dart';
import 'package:local_solar/src/local_solar.dart';
import 'package:local_solar/src/phase.dart';

class Moment {
  final LocalSolar localSolar;
  final DateTime time;
  final double Function() _altitude;
  final double Function() _azimuth;

  Moment(this.localSolar, this.time, this._altitude, this._azimuth);

  double get azimuth => _azimuth();
  double get altitude => _altitude();

  double get zenith => 90.0 - altitude;

  late final phase = Phase.fromAltitude(altitude.degrees);

  @override
  String toString() {
    return 'Moment{time: $time, altitude: $altitude, azimuth: $azimuth, phase: $phase}';
  }
}
