package com.github.funczz.rocket_launcher.gui.view

import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel

class LaunchedPanel : JPanel() {

    private val stateLabel: JLabel = createStateLabel()

    init {
        layout = createLayout()
    }

    private fun createLayout(): BoxLayout {
        return BoxLayout(this, BoxLayout.Y_AXIS).apply {
            add(stateLabel)
            add(Box.createVerticalBox())
        }
    }

    private fun createStateLabel(): JLabel {
        return JLabel().apply { name = "stateLabel" }
            .also {
                it.text = "Launched."
            }
    }

    companion object {
        private const val serialVersionUID: Long = 4637307618131502759L
    }

}