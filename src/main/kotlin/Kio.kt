@file:Suppress("unused")

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
 * - No matter whether the file exists or not.
 * - The parents of the file is not required to exist.
 */
fun File.createFile() {
    this.requireNotExists()
    val parent = this.canonicalFile.parentFile
    if (parent?.notExists() == true) {
        parent.createDir()
    }
    this.createNewFile()
}

/**
 * Creates the file.
 *
 * - No matter whether the directory exists or not.
 * - The parents of the directory is not required to exist.
 */
fun File.createDir() {
    this.requireNotExists()
    this.mkdirs()
}

/**
 * Deletes the file.
 *
 * - No matter whether the file exists or not.
 */
fun File.deleteFile() {
    this.requireExists()
    this.delete()
}

/**
 * Deletes the directory.
 *
 * - No matter whether the directory exists or not.
 * - The directory is not required to be empty.
 */
fun File.deleteDir() {
    this.requireExists()
    this.deleteRecursively()
}


//
// File Text & Bytes
//

/**
 * The text of the file.
 *
 * - If the file doesn't exist, getting or setting this value will create a new file.
 * - The pathname should denote a file.
 */
var File.text: String
    get() {
        if (this.notExists()) this.createFile()
        return this.readText()
    }
    set(value) {
        this.requireExists()
        this.writeText(value)
    }

/**
 * The bytes of the file.
 *
 * - If the file doesn't exist, getting or setting this value will create a new file.
 * - The pathname should denote a file.
 */
var File.bytes: ByteArray
    get() {
        if (this.notExists()) this.createFile()
        return this.readBytes()
    }
    set(value) {
        this.requireExists()
        this.writeBytes(value)
    }


//
// File Name
//

/**
 * Renames the file, returns the new file.
 */
fun File.rename(name: String): File {
    this.requireExists()
    return this.resolveSibling(name).also { this.renameTo(it) }
}

/**
 * Renames the file, but only prefix (i.e. name without extension), returns the new file.
 */
fun File.renamePrefix(prefix: String): File {
    this.requireExists()
    return this.resolveSibling("$prefix.${this.extension}").also { this.renameTo(it) }
}

/**
 * Renames the file, but only suffix (i.e. extension), returns the new file.
 */
fun File.renameSuffix(suffix: String): File {
    this.requireExists()
    return this.resolveSibling("${this.nameWithoutExtension}.$suffix").also { this.renameTo(it) }
}
