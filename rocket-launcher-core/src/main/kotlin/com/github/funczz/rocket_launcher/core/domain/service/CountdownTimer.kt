package com.github.funczz.rocket_launcher.core.domain.service

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class CountdownTimer(
    private val initialCounter: UInt, private val interval: Long = 1000L
) {

    private var counter: UInt = initialCounter

    private var beforeEachFunction: (UInt) -> Unit = {}

    private var afterEachFunction: (UInt) -> Unit = {}

    private var aborted: Boolean = false

    private val lock = ReentrantLock(true)

    fun beforeEach(f: (UInt) -> Unit): CountdownTimer {
        beforeEachFunction = f
        return this
    }

    fun afterEach(f: (UInt) -> Unit): CountdownTimer {
        afterEachFunction = f
        return this
    }

    fun abort(f: (UInt) -> Unit) {
        withLock {
            if (aborted) return@withLock
            aborted = true
            f(counter)
        }
    }

    fun start(): Thread {
        initialize()
        return thread {
            while (counter > 0u) {
                if (aborted) {
                    break
                }
                Thread.sleep(interval)
                if (aborted) {
                    break
                }
                withLock {
                    beforeEachFunction(counter)
                    counter -= 1u
                    afterEachFunction(counter)
                }
            }
        }
    }

    private fun initialize() {
        withLock {
            counter = initialCounter
            aborted = false
        }
    }

    private inline fun <R> withLock(f: () -> R): R {
        return lock.withLock(f)
    }
}