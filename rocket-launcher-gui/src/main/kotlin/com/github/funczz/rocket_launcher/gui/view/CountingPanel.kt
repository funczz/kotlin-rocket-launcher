package com.github.funczz.rocket_launcher.gui.view

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.domain.service.CountdownTimer
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.DecrementLauncherSamAction
import com.github.funczz.rocket_launcher.gui.sam.launcher.GuiLauncherSamExecutor
import java.util.*
import javax.swing.*

class CountingPanel(private var counter: UInt) : JPanel() {

    private val stateLabel: JLabel = createStateLabel()

    private val abortButton: JButton = createAbortButton()

    private val countdownTimer: CountdownTimer = createCountdownTimer()

    init {
        layout = createLayout()
        startCountdownTimer()
    }

    private fun createLayout(): BoxLayout {
        return BoxLayout(this, BoxLayout.Y_AXIS).apply {
            add(stateLabel)
            add(abortButton)
            add(Box.createVerticalBox())
        }
    }

    private fun createCountdownTimer(): CountdownTimer {
        return CountdownTimer(counter)
    }

    private fun startCountdownTimer() {
        countdownTimer.start {
            executeDecrementAction()
        }
    }

    private fun createStateLabel(): JLabel {
        return JLabel().apply { name = "stateLabel" }
            .also {
                updateStateLabel(it)
            }
    }

    private fun updateStateLabel() {
        updateStateLabel(stateLabel)
    }

    private fun updateStateLabel(label: JLabel) {
        SwingUtilities.invokeLater {
            label.text = "Counting. counter=%s".format(counter)
        }
    }

    private fun createAbortButton(): JButton {
        return JButton().apply { name = "abortButton" }
            .also {
                it.text = "ABORT"
                it.isEnabled = true
                it.addActionListener { executeAbortAction() }
            }
    }

    private fun executeDecrementAction() {
        countdownTimer.decrement {
            // countdownTimer.decrement ???????????????????????? it ??? ???????????????????????????????????????
            // DecrementLauncherSamAction ????????? LauncherSamActionInputData.counter ??????
            // it ??? 1 ?????????????????????????????????
            val samActionData = LauncherSamActionInputData.create(
                model = Launcher(state = Counting, counter = Optional.of(it + 1u))
            )
            GuiLauncherSamExecutor.execute(
                samAction = DecrementLauncherSamAction,
                samActionData = samActionData
            )
            counter = it
            updateStateLabel()
        }
    }

    private fun executeAbortAction() {
        countdownTimer.abort {
            val samActionData = LauncherSamActionInputData.create(
                model = Launcher(state = Counting, counter = Optional.of(it))
            )
            GuiLauncherSamExecutor.execute(
                samAction = AbortLauncherSamAction,
                samActionData = samActionData,
            )
        }
    }

}