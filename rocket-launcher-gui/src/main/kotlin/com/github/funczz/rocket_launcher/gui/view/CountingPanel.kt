package com.github.funczz.rocket_launcher.gui.view

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamExecutor
import com.github.funczz.rocket_launcher.gui.sam.launcher.action.AbortLauncherSamAction
import java.util.*
import javax.swing.*

class CountingPanel(private var counter: UInt) : JPanel() {

    private val stateLabel: JLabel = createStateLabel()

    private val abortButton: JButton = createAbortButton()

    init {
        layout = createLayout()
    }

    private fun createLayout(): BoxLayout {
        return BoxLayout(this, BoxLayout.Y_AXIS).apply {
            add(stateLabel)
            add(abortButton)
            add(Box.createVerticalBox())
        }
    }

    private fun createStateLabel(): JLabel {
        return JLabel().apply { name = "stateLabel" }
            .also {
                it.text = "Counting. counter=%s".format(counter)
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

    private fun executeAbortAction() {
        val samActionData = LauncherSamActionInputData.create(
            model = Launcher(state = Counting, counter = Optional.of(counter))
        )
        LauncherSamExecutor.execute(
            samAction = AbortLauncherSamAction,
            samActionData = samActionData,
        )
    }

}