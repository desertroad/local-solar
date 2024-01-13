extension DurationFactory on num {
  Duration get milliseconds => const Duration(milliseconds: 1) * this;

  Duration get minutes => const Duration(minutes: 1) * this;

  Duration get days => const Duration(days: 1) * this;
}

extension DurationExt on Duration {
  num operator /(Duration other) => inMilliseconds / other.inMilliseconds;

  DateTime toDateTime() => DateTime.fromMillisecondsSinceEpoch(inMilliseconds);
}

extension DateTimeExt on DateTime {
  Duration toDuration() => Duration(microseconds: microsecondsSinceEpoch);
}