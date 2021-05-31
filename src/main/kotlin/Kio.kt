import java.io.File

//
// File Text & Bytes
//

var File.text: String
    get() = this.readText()
    set(value) = this.writeText(value)

var File.bytes: ByteArray
    get() = this.readBytes()
    set(value) = this.writeBytes(value)


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

fun File.requireExists() {
    if (this.notExists()) throw NoSuchFileException(this)
}

fun File.requireNotExists() {
    if (this.exists()) throw FileAlreadyExistsException(this)
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