import 'dart:math' as dart_math;

class Angle {
  final double _radianValue;

  double get inRadians => _radianValue;

  double get inDegrees => 180.0 / dart_math.pi * _radianValue;

  Angle(this._radianValue);

  Angle operator +(Angle other) => Angle(_radianValue + other._radianValue);

  Angle operator -(Angle other) => Angle(_radianValue - other._radianValue);

  Angle operator *(num other) => Angle(_radianValue * other.toDouble());

  double operator /(Angle other) => _radianValue / other._radianValue;
}

final _oneRotation = 360.degrees;

extension AngleExt on num {
  Angle get degrees => Angle(dart_math.pi / 180.0 * toDouble());

  Angle get radians => Angle(toDouble());

  Angle get rotations => _oneRotation * toDouble();

  Angle operator *(Angle other) => other * toDouble();
}

double sin(Angle angle) => dart_math.sin(angle._radianValue);

double cos(Angle angle) => dart_math.cos(angle._radianValue);
