package com.github.funczz.rocket_launcher.core.domain.model.launcher

import com.github.funczz.kotlin.fsm.FsmResult
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.*
import io.kotlintest.matchers.result.shouldBeSuccess
import io.kotlintest.specs.StringSpec
import java.util.*

internal class LauncherTest : StringSpec() {

    init {

        "Ready --ABORT--> Aborted" {
            val expected = FsmResult(
                state = Aborted,
                ctx = Launcher(state = Aborted, counter = Optional.empty())
            )

            val state = Ready
            val ctx = Launcher()

            val actual = state.fire(LauncherEvent.ABORT, ctx)

            actual shouldBeSuccess expected
        }

        "Ready --START(10)--> Counting" {
            val expected = FsmResult(
                state = Counting,
                ctx = Launcher(state = Counting, counter = Optional.of(10u))
            )

            val state = Ready
            val ctx = Launcher()

            val actual = state.fire(LauncherEvent.START(10u), ctx)

            actual shouldBeSuccess expected
        }

        "Counting --ABORT--> Aborted" {
            val expected = FsmResult(
                state = Aborted,
                ctx = Launcher(state = Aborted, counter = Optional.of(10u))
            )

            val state = Counting
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.ABORT, ctx)

            actual shouldBeSuccess expected
        }

        "Counting --LAUNCH--> Launched" {
            val expected = FsmResult(
                state = Launched,
                ctx = Launcher(state = Launched, counter = Optional.of(10u))
            )

            val state = Counting
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.LAUNCH, ctx)

            actual shouldBeSuccess expected
        }

        "Counting <--DECREMENT" {
            val expected = FsmResult(
                state = Counting,
                ctx = Launcher(state = Counting, counter = Optional.of(9u))
            )

            val state = Counting
            val ctx = Launcher(state = Counting, counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.DECREMENT, ctx)

            actual shouldBeSuccess expected
        }

        "Aborted --FINISH--> END" {
            val expected = FsmResult(
                state = End,
                ctx = Launcher(state = End, counter = Optional.of(10u))
            )

            val state = Aborted
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.FINISH, ctx)

            actual shouldBeSuccess expected
        }

        "Launched --FINISH--> END" {
            val expected = FsmResult(
                state = End,
                ctx = Launcher(state = End, counter = Optional.of(10u))
            )

            val state = Launched
            val ctx = Launcher(counter = Optional.of(10u))

            val actual = state.fire(LauncherEvent.FINISH, ctx)

            actual shouldBeSuccess expected
        }

    }

}