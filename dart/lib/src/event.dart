import 'package:local_solar/src/phase.dart';

enum Event implements Comparable<Event> {
  astronomicalDawn(
      rising: true,
      prevPhase: Phase.night,
      nextPhase: Phase.astronomicalTwilight),
  nauticalDawn(
      rising: true,
      prevPhase: Phase.astronomicalTwilight,
      nextPhase: Phase.nauticalTwilight),
  civilDawn(
      rising: true,
      prevPhase: Phase.nauticalTwilight,
      nextPhase: Phase.civilTwilight),
  sunrise(rising: true, prevPhase: Phase.civilTwilight, nextPhase: Phase.day),
  sunset(rising: false, prevPhase: Phase.day, nextPhase: Phase.civilTwilight),
  civilDusk(
      rising: false,
      prevPhase: Phase.civilTwilight,
      nextPhase: Phase.nauticalTwilight),
  nauticalDusk(
      rising: false,
      prevPhase: Phase.nauticalTwilight,
      nextPhase: Phase.astronomicalTwilight),
  astronomicalDusk(
      rising: false,
      prevPhase: Phase.astronomicalTwilight,
      nextPhase: Phase.night);

  final bool rising;
  final Phase prevPhase;
  final Phase nextPhase;

  bool get setting => !rising;

  const Event(
      {required this.rising, required this.prevPhase, required this.nextPhase});

  @override
  int compareTo(Event other) => index.compareTo(other.index);
}
