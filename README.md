# Local Solar

This library was developed to calculate the position of the sun at a specific moment and
the times of events such as sunrise and sunset on mobile devices.

Astronomical knowledge was referenced from Wikipedia and various websites.


## Concept

In the documents were referred to, explanations were based on time zones and calendar systems.

However, location is represented solely by latitude and longitude, absolute points in time can be expressed using Unix timestamps.
Time zones need only be considered when displaying time, and day of year can be substituted by the remainder when dividing the timestamp by an average Julian year.

With this approach, I hoped for a simpler and more understandable implementation.

Below, I briefly introduce cases where such an approach has been applied.

### Equation of time

$\pi \approx 3.14159$


### Declination





## Implementations
+ [JVM (kotlin)](jvm)
+ dart : planned
+ swift : planned


## License

This code is under the [MIT License](LICENSE).


## Additional Resources
+ [Position of the Sun (Wikipedia)](https://en.wikipedia.org/wiki/Position_of_the_Sun)
+ 