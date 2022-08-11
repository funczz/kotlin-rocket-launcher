package com.github.funczz.rocket_launcher.gui.view_model

import com.github.funczz.kotlin.channel.Channel
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.domain.service.CountdownTimer
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.DecrementLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.ILauncherSamAction
import com.github.funczz.rocket_launcher.gui.Global
import com.github.funczz.rocket_launcher.gui.sam.launcher.GuiLauncherSamExecutor
import java.util.*

class CountingViewModel(model: LauncherSamModel, defaultCounter: UInt = 10u) {

    private val jFrame = Global.jFrame

    val initialCounter: UInt

    val counterReceiver: Channel.Receiver<UInt>

    private val counterSender: Channel<UInt>.Sender

    private val countdownTimer: CountdownTimer

    init {
        initialCounter = try {
            model.counter.get()
        } catch (e: Throwable) {
            defaultCounter
        }

        Channel.new<UInt>().also {
            counterSender = it.first
            counterReceiver = it.second
        }

        countdownTimer = CountdownTimer(initialCounter)
            .beforeEach {
                val samActionData = LauncherSamActionInputData.create(
                    model = Launcher(state = Counting, counter = Optional.of(it))
                )
                GuiLauncherSamExecutor.execute(
                    samAction = DecrementLauncherSamAction,
                    samActionData = samActionData,
                    representationData = jFrame,
                )
            }
            .afterEach {
                counterSender.send(it)
            }
        countdownTimer.start()
    }

    fun execute(action: ILauncherSamAction) {
        when (action) {
            is AbortLauncherSamAction -> {
                countdownTimer.abort {
                    val samActionData = LauncherSamActionInputData.create(
                        model = Launcher(state = Counting, counter = Optional.of(it))
                    )
                    GuiLauncherSamExecutor.execute(
                        samAction = AbortLauncherSamAction,
                        samActionData = samActionData,
                        representationData = jFrame,
                    )
                }
            }
            else -> {}
        }
    }

}