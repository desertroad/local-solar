import 'package:local_solar/src/angle.dart';

/// Collection for horizon boundaries based on solar altitude.
///
/// https://en.wikipedia.org/wiki/Sunrise#Angle_with_respect_to_horizon
class Altitude {
  static final Angle atSunrise = (-50 / 60.0).degrees;
  static final Angle civil = (-6.0).degrees;
  static final Angle nautical = (-12.0).degrees;
  static final Angle astronomical = (-18.0).degrees;
}
