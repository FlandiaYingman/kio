@file:Suppress("unused")

import java.io.File

//
// File Assertion
//

fun File.requireExists() {
    if (this.notExists()) throw NoSuchFileException(this)
}

fun File.requireNotExists() {
    if (this.exists()) throw FileAlreadyExistsException(this)
}


//
// File Text & Bytes
//

var File.text: String
    get() {
        if (this.notExists()) this.createFile()
        return this.readText()
    }
    set(value) {
        this.requireExists()
        this.writeText(value)
    }

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

fun File.rename(name: String): File {
    this.requireExists()
    return this.resolveSibling(name).also { this.renameTo(it) }
}

fun File.renamePrefix(prefix: String): File {
    this.requireExists()
    return this.resolveSibling("$prefix.${this.extension}").also { this.renameTo(it) }
}

fun File.renameSuffix(suffix: String): File {
    this.requireExists()
    return this.resolveSibling("${this.nameWithoutExtension}.$suffix").also { this.renameTo(it) }
}


//
// File Creation & Deletion
//

fun File.notExists(): Boolean {
    return this.exists().not()
}

fun File.createFile() {
    this.requireNotExists()
    val parent = this.canonicalFile.parentFile
    if (parent?.notExists() == true) {
        parent.createDir()
    }
    this.createNewFile()
}

fun File.createDir() {
    this.requireNotExists()
    this.mkdirs()
}

fun File.deleteFile() {
    this.requireExists()
    this.delete()
}

fun File.deleteDir() {
    this.requireExists()
    this.deleteRecursively()
}