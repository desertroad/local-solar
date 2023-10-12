package desertroad.solar.internal


@JvmInline
internal value class Angle(private val radianValue: Double) {

    inline val inDegrees: Double get() = Math.toDegrees(radianValue)

    inline val inRadians: Double get() = radianValue

    fun isNaN() = radianValue.isNaN()

    operator fun unaryMinus() = Angle(-radianValue)

    operator fun plus(other: Angle) = Angle(radianValue + other.radianValue)

    operator fun minus(other: Angle) = Angle(radianValue - other.radianValue)

    operator fun times(other: Number) = Angle(radianValue * other.toDouble())

    operator fun div(other: Angle): Double = radianValue / other.radianValue

    companion object {

        val ONE_ROTATION = 360.degrees

        inline val Number.degrees: Angle get() = Angle(Math.toRadians(toDouble()))

        inline val Number.radians: Angle get() = Angle(toDouble())

        inline val Number.rotations: Angle get() = toDouble() * ONE_ROTATION

        operator fun Number.times(angle: Angle) = Angle(toDouble() * angle.radianValue)

        fun sin(angle: Angle) = kotlin.math.sin(angle.radianValue)

        fun cos(angle: Angle) = kotlin.math.cos(angle.radianValue)
    }
}



