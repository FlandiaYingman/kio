@file:Suppress("unused")

package top.anagke.kio

import java.io.InputStream

/**
 * Returns the default block size for forEachBlock().
 *
 * ```
 * Derived from the Kotlin Programming Language Standard Library,
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * License: Apache License (http://www.apache.org/licenses/LICENSE-2.0)
 * ```
 */
internal const val DEFAULT_BLOCK_SIZE: Int = 4096
/**
 * Returns the minimum block size for forEachBlock().
 *
 * ```
 * Derived from the Kotlin Programming Language Standard Library,
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * License: Apache License (http://www.apache.org/licenses/LICENSE-2.0)
 * ```
 */
internal const val MINIMUM_BLOCK_SIZE: Int = 512

/**
 * Reads input stream by byte blocks and calls [action] for each block read.
 * Block has default size which is 4096 bytes.
 * This functions passes the byte array and amount of bytes in the array to the [action] function.
 *
 * You can use this function for huge files.
 *
 * ```
 * Derived from the Kotlin Programming Language Standard Library,
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * License: Apache License (http://www.apache.org/licenses/LICENSE-2.0)
 * ```
 *
 * @param action function to process file blocks.
 */
fun InputStream.forEachBlock(action: (buffer: ByteArray, bytesRead: Int) -> Unit): Unit =
    this.forEachBlock(DEFAULT_BLOCK_SIZE, action)

/**
 * Reads input stream by byte blocks and calls [action] for each block read.
 * This functions passes the byte array and amount of bytes in the array to the [action] function.
 *
 * You can use this function for huge files.
 *
 * ```
 * Derived from the Kotlin Programming Language Standard Library,
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * License: Apache License (http://www.apache.org/licenses/LICENSE-2.0)
 * ```
 *
 * @param action function to process file blocks.
 * @param blockSize size of a block, replaced by 512 if it's less, 4096 by default.
 */
fun InputStream.forEachBlock(blockSize: Int, action: (buffer: ByteArray, bytesRead: Int) -> Unit) {
    val arr = ByteArray(blockSize.coerceAtLeast(MINIMUM_BLOCK_SIZE))
    use { input ->
        do {
            val size = input.read(arr)
            if (size <= 0) {
                break
            } else {
                action(arr, size)
            }
        } while (true)
    }
}