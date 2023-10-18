# local-solar (on JVM)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.desertroad/local-solar.svg)](https://central.sonatype.com/artifact/io.github.desertroad/local-solar)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/github/license/desertroad/local-solar)](https://github.com/desertroad/local-solar/blob/main/LICENSE)

## Get the current position of the sun
First, you need to obtain the latitude and longitude information for your current location.

You can do this by selecting 'Show my location' on [OpenStreetMap](https://www.openstreetmap.org/), 
and the latitude and longitude for your current location will be displayed in the URL.

```
https://www.openstreetmap.org/#map=[zoom]/[latitude]/[longitude]

ex) https://www.openstreetmap.org/#map=18/-43.5320/172.6306
    Christchurch, New Zealand (43.5320° S, 172.6306° E)
```

> NOTE
> 
> Positive latitudes are north of the equator, negative latitudes are south of the equator. Positive longitudes are east of the Prime Meridian; negative longitudes are west of the Prime Meridian.
> 
> -- https://en.wikipedia.org/wiki/Decimal_degrees


Assuming the current location is Christchurch, New Zealand, and the local time is considered to be 14:30 on October 18, 2023.
```kotlin
val tracker = LocalSolar.tracker()

val current = tracker.track(
    latitude = -43.5320,
    longitude = 172.6306,
    time = System.currentTimeMillis()
)

println("Current time = %tc".format(current.time))
println("- Phase = %s".format(current.phase))
println("- Altitude = %.2f (degrees)".format(current.altitude))
println("- Azimuth = %.2f (degrees)".format(current.azimuth))
```

Output
```
Current time = Wed Oct 18 14:30:00 NZDT 2023
- Phase = DAY
- Altitude = 52.02 (degrees)
- Azimuth = 328.64 (degrees)
```


## Calculating today's sunrise and sunset times
You can obtain events for sunrise and sunset (as well as dawn and dusk) for a day using the `LocalSolar.event` property lazily.

```kotlin
val localSolar = LocalSolar(
    latitude = -43.5320,
    longitude = 172.6306,
    time = System.currentTimeMillis()
)

localSolar.events[Event.SUNRISE]?.let { sunrise ->
    println("Sunrise time = %tc".format(sunrise.time))
    println("- Azimuth = %.4f (degrees)".format(sunrise.azimuth))
}

println()

localSolar.events[Event.SUNSET]?.let { sunset ->
    println("Sunset time = %tc".format(sunset.time))
    println("- Azimuth = %.2f (degrees)".format(sunset.azimuth))
}
```

Output
```
Sunrise time = Wed Oct 18 06:33:40 NZDT 2023
- Azimuth = 103.71 (degrees)

Sunset time = Wed Oct 18 19:54:52 NZDT 2023
- Azimuth = 256.28 (degrees)
```



## Releases
The latest release is available on [Maven Central](https://central.sonatype.com/artifact/io.github.desertroad/local-solar).
```groovy
implementation("io.github.desertroad:local-solar:0.1")
```

## License
```
MIT License

Copyright (c) 2023 Kim Heeyong

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```