package com.github.funczz.rocket_launcher.gui.view

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.domain.service.CountdownTimer
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamExecutor
import com.github.funczz.rocket_launcher.gui.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.gui.sam.launcher.action.DecrementLauncherSamAction
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
            // countdownTimer.decrement から渡される数値 it は デクリメント後の値なので、
            // DecrementLauncherSamAction に渡す LauncherSamActionInputData.counter には
            // it に 1 を足した値を代入する。
            val samActionData = LauncherSamActionInputData.create(
                model = Launcher(state = Counting, counter = Optional.of(it + 1u))
            )
            LauncherSamExecutor.execute(
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
            LauncherSamExecutor.execute(
                samAction = AbortLauncherSamAction,
                samActionData = samActionData,
            )
        }
    }

}