@file:Suppress("unused")

package top.anagke.kio

import java.io.File

//
// File Assertion
//

/**
 * Throws an [NoSuchFileException] if the file or directory doesn't exist.
 */
fun File.requireExists() {
    if (this.notExists()) throw NoSuchFileException(this)
}

/**
 * Throws an [FileAlreadyExistsException] if the file or directory exists.
 */
fun File.requireNotExists() {
    if (this.exists()) throw FileAlreadyExistsException(this)
}

/**
 * Throws an [NoSuchFileException] if the pathname doesn't denote a file.
 *
 * This method assumes that the pathname exists by calling [File.requireExists].
 */
fun File.requireIsFile() {
    if (this.isFile.not()) throw NoSuchFileException(this, reason = "Isn't file")
}

/**
 * Throws an [NoSuchFileException] if the pathname doesn't denote a directory.
 *
 * This method assumes that the pathname exists by calling [File.requireExists].
 */
fun File.requireIsDir() {
    if (this.isDirectory.not()) throw NoSuchFileException(this, reason = "Isn't directory")
}


//
// File Creation & Deletion
//

/**
 * The inverse of [File.exists].
 */
fun File.notExists(): Boolean {
    return this.exists().not()
}

/**
 * Creates the file.
 *
 * - Does nothing if the file already exists.
 * - Creates parents if the parents don't exist.
 * - The pathname should denote a file if the it already exists.
 */
fun File.createFile() {
    val parent = this.canonicalFile.parentFile
    if (parent?.notExists() == true) {
        parent.createDir()
    }
    if (this.notExists()) {
        this.createNewFile()
    } else {
        requireIsFile()
    }
}

/**
 * Creates the dir.
 *
 * - Does nothing if the directory already exists.
 * - Creates parents if the parents don't exist.
 * - The pathname should denote a directory if the it already exists.
 */
fun File.createDir() {
    val parent = this.canonicalFile.parentFile
    if (parent?.notExists() == true) {
        parent.createDir()
    }
    if (this.notExists()) {
        this.mkdir()
    } else {
        requireIsDir()
    }
}

/**
 * Deletes the file.
 *
 * - Does nothing if the file doesn't exist.
 */
fun File.deleteFile() {
    if (this.exists()) {
        this.delete()
    }
}

/**
 * Deletes the directory (recursively).
 *
 * - Does nothing if the directory doesn't exist.
 */
fun File.deleteDir() {
    if (this.exists()) {
        this.deleteRecursively()
    }
}


//
// File Text & Bytes
//

/**
 * The text of the file.
 *
 * - Creates the file if the file doesn't exist.
 * - The pathname should denote a file.
 */
var File.text: String
    get() {
        this.createFile()
        return this.readText()
    }
    set(value) {
        this.createFile()
        this.writeText(value)
    }

/**
 * The bytes of the file.
 *
 * - Creates the file if the file doesn't exist.
 * - The pathname should denote a file.
 */
var File.bytes: ByteArray
    get() {
        this.createFile()
        return this.readBytes()
    }
    set(value) {
        this.createFile()
        this.writeBytes(value)
    }


//
// File Name
//

/**
 * Renames the file, returns the new file.
 *
 * - The file should exist.
 */
fun File.rename(name: String): File {
    this.requireExists()
    return this.resolveSibling(name).also { this.renameTo(it) }
}

/**
 * Renames the file, but only prefix (i.e. name without extension), returns the new file.
 *
 * - The file should exist.
 */
fun File.renamePrefix(prefix: String): File {
    this.requireExists()
    return this.resolveSibling("$prefix.${this.extension}").also { this.renameTo(it) }
}

/**
 * Renames the file, but only suffix (i.e. extension), returns the new file.
 *
 * - The file should exist.
 */
fun File.renameSuffix(suffix: String): File {
    this.requireExists()
    return this.resolveSibling("${this.nameWithoutExtension}.$suffix").also { this.renameTo(it) }
}
