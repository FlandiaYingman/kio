package top.anagke.kio.util

import ca.seinesoftware.hamcrest.path.PathMatcher.*
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import top.anagke.kio.util.TempFiles.LOCAL_TEMP_DIR
import top.anagke.kio.util.TempFiles.SYSTEM_TEMP_DIR
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.MILLISECONDS

internal class TempFilesTest {

    @Test
    fun testAllocFree_localTempDir() {
        val tempFile = TempFiles.alloc(LOCAL_TEMP_DIR)
        assertThat(LOCAL_TEMP_DIR, exists())
        assertThat(LOCAL_TEMP_DIR, aDirectory())
        assertThat(tempFile, exists())
        assertThat(tempFile, aRegularFile())

        TempFiles.free(tempFile)
        assertThat(tempFile, not(exists()))
    }

    @Test
    fun testAllocFree_systemTempDir() {
        val tempFile = TempFiles.alloc(SYSTEM_TEMP_DIR)
        assertThat(SYSTEM_TEMP_DIR, exists())
        assertThat(SYSTEM_TEMP_DIR, aDirectory())
        assertThat(tempFile, exists())
        assertThat(tempFile, aRegularFile())

        TempFiles.free(tempFile)
        assertThat(tempFile, not(exists()))
    }

    @Test
    fun testAllocFree_parallel() {
        val allocated = ConcurrentHashMap.newKeySet<Path>()
        val producer = Executors.newCachedThreadPool()
        val consumer = Executors.newCachedThreadPool()
        repeat(1000) {
            producer.submit {
                val alloc = TempFiles.alloc(LOCAL_TEMP_DIR)
                allocated.add(alloc)

                assertThat(alloc, exists())
                assertThat(alloc, aRegularFile())

                consumer.submit {
                    assertThat(alloc, exists())
                    assertThat(alloc, aRegularFile())

                    TempFiles.free(alloc)
                    assertThat(alloc, not(exists()))
                }
            }
        }
        producer.shutdown()
        producer.awaitTermination(Long.MAX_VALUE, MILLISECONDS)
        consumer.shutdown()
        consumer.awaitTermination(Long.MAX_VALUE, MILLISECONDS)
        allocated.forEach { alloc ->
            assertThat(alloc, not(exists()))
        }
    }

}