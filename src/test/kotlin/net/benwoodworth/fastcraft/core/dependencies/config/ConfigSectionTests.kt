package net.benwoodworth.fastcraft.core.dependencies.config

import net.benwoodworth.fastcraft.ImplementationTests
import org.junit.Assert.*
import org.junit.jupiter.api.Test

/**
 * Tests for [ConfigSection].
 */
abstract class ConfigSectionTests : ImplementationTests<ConfigSection>() {

    @Test
    fun `when getting a non-existent key, null should be returned`() {
        val key = "key"

        assertNull(testInstance.getString(key))
        assertNull(testInstance.getStringList(key))
        assertNull(testInstance.getInt(key))
        assertNull(testInstance.getIntList(key))
        assertNull(testInstance.getBoolean(key))
    }

    @Test
    fun `getString() and setString() should set and return the correct Strings`() {
        for (expected in listOf(
                "",
                "This test string has one line",
                "This is a test string!\nIt has two lines :D",
                "This one has special characters \r\n\t"
        )) {
            val testInstance = createInstance()
            testInstance.setString("key", expected)

            val actual = testInstance.getString("key")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `getStringList() and setStringList() should get and return the correct values`() {
        for (expected in listOf(
                emptyList(),
                listOf("first", "second", "third"),
                listOf("this is", "a list", "of", "string values")
        )) {
            val testInstance = createInstance()
            testInstance.setStringList("key", expected)

            val actual = testInstance.getStringList("key")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `getInt() and setInt() should set and return the correct Ints`() {
        for (expected in -1000..1000) {
            val testInstance = createInstance()
            testInstance.setInt("key", expected)

            val actual = testInstance.getInt("key")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `getIntList() and setIntList() should get and return the correct values`() {
        for (expected in listOf(
                emptyList(),
                (-10..10).toList(),
                (-1000..1000).step(17).toList()
        )) {
            val testInstance = createInstance()
            testInstance.setIntList("key", expected)

            val actual = testInstance.getIntList("key")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `getBoolean() and setBoolean() should set and return the correct Booleans`() {
        for (expected in listOf(true, false)) {
            val testInstance = createInstance()
            testInstance.setBoolean("key", expected)

            val actual = testInstance.getBoolean("key")
            assertEquals(expected, actual)
        }
    }
}
