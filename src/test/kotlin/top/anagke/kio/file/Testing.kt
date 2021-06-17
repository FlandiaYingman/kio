package top.anagke.kio.file

import java.io.File

val TEST_DIR: File = File("TEST_DIR")
val TEST_FILE: File = resolve("test_file.test_ext")

fun promiseNewTestDir() {
    TEST_DIR.deleteDir()
    TEST_DIR.createDir()
}

fun promiseDeleteTestDir() {
    TEST_DIR.deleteDir()
}

fun resolve(path: String): File {
    return TEST_DIR.resolve(path)
}