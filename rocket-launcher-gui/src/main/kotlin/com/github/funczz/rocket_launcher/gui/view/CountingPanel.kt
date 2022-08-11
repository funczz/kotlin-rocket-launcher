package com.github.funczz.rocket_launcher.gui.view

import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.gui.view_model.CountingViewModel
import javax.swing.*

class CountingPanel(private val vm: CountingViewModel) : JPanel() {

    private val stateLabel: JLabel = JLabel().apply { name = "stateLabel" }
        .also {
            updateStateLabel(vm.initialCounter)
        }

    private val abortButton: JButton = JButton().apply { name = "abortButton" }
        .also {
            it.text = "ABORT"
            it.isEnabled = true
            it.addActionListener {
                vm.execute(action = AbortLauncherSamAction)
            }
        }

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS).apply {
            add(stateLabel)
            add(abortButton)
            add(Box.createVerticalBox())
        }

        vm.counterReceiver.attach {
            updateStateLabel(it)
        }
    }

    private fun updateStateLabel(v: UInt) {
        SwingUtilities.invokeLater {
            stateLabel.text = "Counting. counter=%s".format(v)
        }
    }

    companion object {
        private const val serialVersionUID: Long = -1307269781783811524L
    }

}