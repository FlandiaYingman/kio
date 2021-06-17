package top.anagke.kio

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Suppress("PrivatePropertyName")
internal class KioUnitKtTest {

    private fun testUnitInt(coe: Int, fn: (Int) -> Int) {
        assertEquals(0 * coe, fn(0))
        assertEquals(1 * coe, fn(1))
        assertEquals(4 * coe, fn(4))

        val intOverflow = (Int.MAX_VALUE / coe) + 1
        assertThrows<ArithmeticException> { fn(intOverflow) }
        assertEquals((intOverflow - 1) * coe, fn(intOverflow - 1))
        val intUnderflow = (Int.MIN_VALUE / coe) - 1
        assertThrows<ArithmeticException> { fn(intUnderflow) }
        assertEquals((intUnderflow + 1) * coe, fn(intUnderflow + 1))
    }

    private fun testUnitLong(coe: Long, fn: (Long) -> Long) {
        assertEquals(0 * coe, fn(0))
        assertEquals(1 * coe, fn(1))
        assertEquals(4 * coe, fn(4))

        val intOverflow = (Long.MAX_VALUE / coe) + 1
        assertThrows<ArithmeticException> { fn(intOverflow) }
        assertEquals((intOverflow - 1) * coe, fn(intOverflow - 1))
        val intUnderflow = (Long.MIN_VALUE / coe) - 1
        assertThrows<ArithmeticException> { fn(intUnderflow) }
        assertEquals((intUnderflow + 1) * coe, fn(intUnderflow + 1))
    }

    @Test
    fun getKiB() {
        testUnitInt(1024) { it.KiB }
        testUnitLong(1024L) { it.KiB }
    }


    @Test
    fun getMiB() {
        testUnitInt(1024 * 1024) { it.MiB }
        testUnitLong(1024L * 1024) { it.MiB }
    }

    @Test
    fun getGiB() {
        testUnitLong(1024L * 1024 * 1024) { it.GiB }
    }

    @Test
    fun getTiB() {
        testUnitLong(1024L * 1024 * 1024 * 1024) { it.TiB }
    }


    @Test
    fun getKB() {
        testUnitInt(1000) { it.KB }
        testUnitLong(1000L) { it.KB }
    }


    @Test
    fun getMB() {
        testUnitInt(1000 * 1000) { it.MB }
        testUnitLong(1000L * 1000) { it.MB }
    }

    @Test
    fun getGB() {
        testUnitLong(1000L * 1000 * 1000) { it.GB }
    }

    @Test
    fun getTB() {
        testUnitLong(1000L * 1000 * 1000 * 1000) { it.TB }
    }

}