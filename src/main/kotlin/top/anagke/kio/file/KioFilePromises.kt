@file:Suppress("unused", "MoveVariableDeclarationIntoWhen")

package top.anagke.kio.file

import top.anagke.kio.file.FilePromise.FileExistence.*
import top.anagke.kio.file.FilePromise.FileKind.DIRECTORY
import top.anagke.kio.file.FilePromise.FileKind.FILE
import java.io.File

class FilePromise
internal constructor(private val file: File) {

    enum class FileExistence {
        NEW, EXIST, NOT_EXIST,
    }

    enum class FileKind {
        FILE, DIRECTORY
    }


    private var existence: FileExistence? = null

    private var kind: FileKind? = null


    private fun isComplete(): Boolean {
        return when (existence) {
            null -> false
            NEW, EXIST -> kind != null && existence != null
            NOT_EXIST -> true
        }
    }

    private fun complete() {
        val existence = checkNotNull(existence)
        when (existence) {
            NEW -> promiseNew()
            EXIST -> promiseExist()
            NOT_EXIST -> promiseNotExist()
        }
    }


    private fun promiseNew() {
        val kind = checkNotNull(kind)
        promiseNotExist()
        when (kind) {
            FILE -> file.createFile()
            DIRECTORY -> file.createDir()
        }
    }

    private fun promiseExist() {
        val kind = checkNotNull(kind)
        when (kind) {
            FILE -> if (!file.isFile) promiseNew()
            DIRECTORY -> if (!file.isDirectory) promiseNew()
        }
    }

    private fun promiseNotExist() {
        when {
            file.isFile -> file.deleteFile()
            file.isDirectory -> file.deleteDir()
        }
    }


    fun file(): FilePromise {
        this.kind = FILE
        return this
    }

    fun directory(): FilePromise {
        this.kind = DIRECTORY
        return this
    }

    fun exist() {
        existence = EXIST
        complete()
    }

    fun notExist() {
        existence = NOT_EXIST
        complete()
    }

    fun new() {
        existence = NEW
        complete()
    }

}

fun File.promise(): FilePromise {
    return FilePromise(this)
}



fun main() {
    File("./test_file.txt")
        .promise()
        .exist()

}