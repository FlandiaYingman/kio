package top.anagke.kio.file

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class KioFileKtTest {

    @BeforeEach
    fun setup() {
        promiseNewTestDir()
    }

    @AfterEach
    fun teardown() {
        promiseDeleteTestDir()
    }


    @Test
    fun test_requireExists() {
        TEST_FILE.createNewFile()
        TEST_FILE.requireExists()
        TEST_FILE.delete()
        assertThrows<NoSuchFileException> { TEST_FILE.requireExists() }
    }

    @Test
    fun test_requireNotExists() {
        TEST_FILE.createNewFile()
        assertThrows<FileAlreadyExistsException> { TEST_FILE.requireNotExists() }
        TEST_FILE.delete()
        TEST_FILE.requireNotExists()
    }

    @Test
    fun test_requireIsFile() {
        TEST_FILE.createNewFile()
        TEST_FILE.requireIsFile()
        TEST_FILE.delete()

        TEST_FILE.mkdir()
        assertThrows<NoSuchFileException> { TEST_FILE.requireIsFile() }
        TEST_FILE.delete()

        assertThrows<NoSuchFileException> { TEST_FILE.requireIsFile() }
    }

    @Test
    fun test_requireIsDir() {
        TEST_FILE.createNewFile()
        assertThrows<NoSuchFileException> { TEST_FILE.requireIsDir() }
        TEST_FILE.delete()

        TEST_FILE.mkdir()
        TEST_FILE.requireIsDir()
        TEST_FILE.delete()

        assertThrows<NoSuchFileException> { TEST_FILE.requireIsDir() }
    }

    @Test
    fun test_notExists() {
        assertTrue(TEST_FILE.notExists())

        TEST_FILE.createNewFile()
        assertFalse(TEST_FILE.notExists())
        TEST_FILE.delete()

        TEST_FILE.mkdir()
        assertFalse(TEST_FILE.notExists())
        TEST_FILE.delete()
    }

    @Test
    fun test_createFile() {
        TEST_FILE.createFile()
        assertTrue(TEST_FILE.isFile)
    }

    @Test
    fun test_createDir() {
        TEST_FILE.createDir()
        assertTrue(TEST_FILE.isDirectory)
    }

    @Test
    fun test_deleteFile() {
        TODO()
    }

    @Test
    fun test_deleteDir() {
        TODO()
    }

    @Test
    fun test_getText() {
        TODO()
    }

    @Test
    fun test_setText() {
        TODO()
    }

    @Test
    fun test_getBytes() {
        TODO()
    }

    @Test
    fun test_setBytes() {
        TODO()
    }

    @Test
    fun test_rename() {
        TODO()
    }

    @Test
    fun test_renamePrefix() {
        TODO()
    }

    @Test
    fun test_renameSuffix() {
        TODO()
    }

    @Test
    fun test_sameTo() {
        TODO()
    }

    @Test
    fun test_differTo() {
        TODO()
    }

    @Test
    fun test_parentSameTo() {
        TODO()
    }

    @Test
    fun test_parentDifferTo() {
        TODO()
    }

}