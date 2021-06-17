@file:Suppress("unused")

package top.anagke.kio

import java.lang.Math.multiplyExact


internal const val BINARY = 1024

internal fun Int.toBinaryBytes(unit: Int): Int {
    var times = 1
    repeat(unit) { times = multiplyExact(times, BINARY) }
    return multiplyExact(this, times)
}

internal fun Long.toBinaryBytes(unit: Int): Long {
    var times = 1L
    repeat(unit) { times = multiplyExact(times, BINARY.toLong()) }
    return multiplyExact(this, times)
}

val Int.KiB get() = this.toBinaryBytes(1)
val Int.MiB get() = this.toBinaryBytes(2)

val Long.KiB get() = this.toBinaryBytes(1)
val Long.MiB get() = this.toBinaryBytes(2)
val Long.GiB get() = this.toBinaryBytes(3)
val Long.TiB get() = this.toBinaryBytes(4)


internal const val DECIMAL = 1000

internal fun Int.toDecimalBytes(unit: Int): Int {
    var times = 1
    repeat(unit) { times = multiplyExact(times, DECIMAL) }
    return multiplyExact(this, times)
}

internal fun Long.toDecimalBytes(unit: Int): Long {

    var times = 1L
    repeat(unit) { times = multiplyExact(times, DECIMAL.toLong()) }
    return multiplyExact(this, times)
}

val Int.KB get() = this.toDecimalBytes(1)
val Int.MB get() = this.toDecimalBytes(2)

val Long.KB get() = this.toDecimalBytes(1)
val Long.MB get() = this.toDecimalBytes(2)
val Long.GB get() = this.toDecimalBytes(3)
val Long.TB get() = this.toDecimalBytes(4)
