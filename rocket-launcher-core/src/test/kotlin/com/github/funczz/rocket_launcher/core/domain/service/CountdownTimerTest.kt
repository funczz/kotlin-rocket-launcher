package com.github.funczz.rocket_launcher.core.domain.service

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import io.kotlintest.matchers.beGreaterThan
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.*

@Suppress("BlockingMethodInNonBlockingContext")
internal class CountdownTimerTest : StringSpec() {

    init {

        "10 -> 0" {
            val expected = Optional.of(0u)
            val timer = CountdownTimer(10u)

            println("count %s".format(10u))

            var actual = Optional.empty<UInt>()
            timer.afterEach {
                actual = Optional.of(it)
                println("count %s".format(it))
            }
            val thread = timer.start()
            thread.join()
            actual shouldBe expected
        }

        "abort" {
            val timer = CountdownTimer(10u)

            println("count %s".format(10u))

            var actual = Optional.empty<UInt>()
            timer.afterEach {
                actual = Optional.of(it)
                println("count %s".format(it))
            }
            val thread = timer.start()
            Thread.sleep(3000L)
            timer.abort {
                println("aborted(%s)".format(it))
            }
            thread.join()
            actual.get() should beGreaterThan(0u)
        }

        "Launcher" {
            val expected = 0u
            var launcher = Launcher()
            launcher = launcher.state.fire(LauncherEvent.START(10u), launcher)
                .second
                .getOrThrow()
            val timer = CountdownTimer(launcher.counter.get())
            timer.afterEach {
                println("count %s".format(it))
                launcher = launcher.state.fire(LauncherEvent.DECREMENT, launcher)
                    .second
                    .getOrThrow()
            }.start().join()

            launcher.counter.get() shouldBe expected
        }

    }
}