package com.github.funczz.rocket_launcher.gui.view

import java.util.*
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel

class AbortedPanel(private val counter: Optional<UInt>) : JPanel() {

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
        val v = when (counter.isPresent) {
            true -> counter.get()
            else -> "NONE"
        }
        return JLabel().apply { name = "stateLabel" }
            .also {
                it.text = "Aborted. counter=%s".format(v)
            }
    }

    companion object {
        private const val serialVersionUID: Long = -5387774498851773350L
    }

}