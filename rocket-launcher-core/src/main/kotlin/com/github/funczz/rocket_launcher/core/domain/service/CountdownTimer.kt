package com.github.funczz.rocket_launcher.core.domain.service

import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class CountdownTimer(
    private var counter: UInt
) {

    private var aborted: Boolean = false

    private val lock = ReentrantLock(true)

    fun abort(f: (UInt) -> Unit) {
        withLock {
            aborted = true
            f(counter)
        }
    }

    fun decrement(f: (UInt) -> Unit) {
        withLock {
            if (!aborted) {
                f(counter)
            }
        }
    }

    fun start(f: (UInt) -> Unit): Thread {
        return thread {
            while (counter > 0u) {
                if (aborted) {
                    break
                }
                var result = Optional.empty<UInt>()
                Thread.sleep(1000L)
                withLock {
                    result = if (!aborted) {
                        counter -= 1u
                        Optional.of(counter)
                    } else {
                        Optional.empty()
                    }
                }
                if (result.isPresent) {
                    f(result.get())
                } else {
                    break
                }
            }
        }
    }

    /**
     * ファンクションを <code>ReentrantLock</code> による排他制御下で実行する
     * 非公開メソッド
     * @param function 実行するファンクション
     * @return ファンクションの実行結果を返す
     */
    private inline fun <R> withLock(function: () -> R): R {
        return lock.withLock(function)
    }
}