package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.DecrementLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.StartLauncherSamAction
import io.kotlintest.provided.com.github.funczz.rocket_launcher.core.sam.launcher.ResultLauncherSamExecutor
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.*

internal class ResultLauncherSamExecutorTest : StringSpec() {

    init {

        "isReady" {
            val expected = Optional.of("Ready.")

            val result = ResultLauncherSamExecutor.doRepresentation(
                model = LauncherSamModel.create(
                    model = Launcher(state = Ready, counter = Optional.empty())
                ),
                representationData = Unit
            ).getOrThrow()

            result shouldBe expected
        }

        "isTransitionToCounting" {
            val expected = Optional.of("TransitionToCounting. counter=10")

            val data = LauncherSamActionInputData.create(
                model = Launcher(state = Ready, counter = Optional.of(10u))
            )
            val result = ResultLauncherSamExecutor.execute(
                samAction = StartLauncherSamAction,
                samActionData = data,
                representationData = Unit
            ).getOrThrow()

            result shouldBe expected
        }

        "isCounting" {
            val expected = Optional.empty<String>()

            val data = LauncherSamActionInputData.create(
                model = Launcher(state = Counting, counter = Optional.of(10u))
            )
            val result = ResultLauncherSamExecutor.execute(
                samAction = DecrementLauncherSamAction,
                samActionData = data,
                representationData = Unit
            ).getOrThrow()

            result shouldBe expected
        }

        "isAborted" {
            val expected = Optional.of("Aborted. counter=10")

            val data = LauncherSamActionInputData.create(
                model = Launcher(state = Ready, counter = Optional.of(10u))
            )
            val result = ResultLauncherSamExecutor.execute(
                samAction = AbortLauncherSamAction,
                samActionData = data,
                representationData = Unit
            ).getOrThrow()

            result shouldBe expected
        }

        "isCountingZero -> isLaunched" {
            val expected = Optional.of("Launched.")

            val data = LauncherSamActionInputData.create(
                model = Launcher(state = Counting, counter = Optional.of(1u))
            )
            val result = ResultLauncherSamExecutor.execute(
                samAction = DecrementLauncherSamAction,
                samActionData = data,
                representationData = Unit
            ).getOrThrow()

            result shouldBe expected
        }
    }
}