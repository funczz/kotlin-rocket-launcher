package com.github.funczz.rocket_launcher.core.domain.model.launcher

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.*

internal class LauncherTest : StringSpec() {

    init {

        "Ready --ABORT--> Aborted" {
            val expected = Pair(
                Aborted,
                RopResult.success { Launcher(state = Aborted, counter = Optional.empty()) }
            )

            val state = Ready
            val ctx = Launcher()

            val actual = state.fire(LauncherEvent.ABORT, ctx)

            actual shouldBe expected
        }

        "Ready --START(10)--> Counting" {
            val expected = Pair(
                Counting,
                RopResult.success { Launcher(state = Counting, counter = Optional.of(10u)) }
            )

            val state = Ready
            val ctx = Launcher()

            val actual = state.fire(LauncherEvent.START(10u), ctx)

            actual shouldBe expected
        }

        "Counting --ABORT--> Aborted" {
            val expected = Pair(
                Aborted,
                RopResult.success { Launcher(state = Aborted, counter = Optional.of(10u)) }
            )

            val state = Counting
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.ABORT, ctx)

            actual shouldBe expected
        }

        "Counting --LAUNCH--> Launched" {
            val expected = Pair(
                Launched,
                RopResult.success { Launcher(state = Launched, counter = Optional.of(10u)) }
            )

            val state = Counting
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.LAUNCH, ctx)

            actual shouldBe expected
        }

        "Counting <--DECREMENT" {
            val expected = Pair(
                Counting,
                RopResult.success { Launcher(state = Counting, counter = Optional.of(9u)) }
            )

            val state = Counting
            val ctx = Launcher(state = Counting, counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.DECREMENT, ctx)

            actual shouldBe expected
        }

        "Aborted --FINISH--> END" {
            val expected = Pair(
                End,
                RopResult.success { Launcher(state = End, counter = Optional.of(10u)) }
            )

            val state = Aborted
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.FINISH, ctx)

            actual shouldBe expected
        }

        "Launched --FINISH--> END" {
            val expected = Pair(
                End,
                RopResult.success { Launcher(state = End, counter = Optional.of(10u)) }
            )

            val state = Launched
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.FINISH, ctx)

            actual shouldBe expected
        }

    }

}