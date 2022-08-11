package com.github.funczz.rocket_launcher.gui.view

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.action.StartLauncherSamAction
import com.github.funczz.rocket_launcher.gui.Global
import com.github.funczz.rocket_launcher.gui.sam.launcher.GuiLauncherSamExecutor
import java.util.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class ReadyPanel : JPanel() {

    private val jFrame = Global.jFrame

    private var counter: Optional<UInt> = createCounter()

    private val stateLabel: JLabel = createStateLabel()

    private val inputField: JTextField = createInputField()

    private val startButton: JButton = createStartButton()

    init {
        layout = createLayout()
    }

    private fun createLayout(): BoxLayout {
        return BoxLayout(this, BoxLayout.Y_AXIS).apply {
            add(stateLabel)
            add(inputField)
            add(startButton)
            add(Box.createVerticalBox())
        }
    }

    private fun createCounter(text: String = ""): Optional<UInt> {
        return when {
            text.isNotBlank() -> try {
                Optional.of(text.toUInt())
            } catch (e: NumberFormatException) {
                Optional.empty()
            }
            else -> Optional.empty()
        }
    }

    private fun createStateLabel(): JLabel {
        return JLabel().apply { name = "stateLabel" }
            .also {
                it.text = "Ready."
            }
    }

    private fun createInputField(): JTextField {
        return JTextField().apply { name = "inputField" }
            .also { jTextField ->
                val listener = object : DocumentListener {

                    override fun insertUpdate(p0: DocumentEvent?) {
                        update()
                    }

                    override fun removeUpdate(p0: DocumentEvent?) {
                        update()
                    }

                    override fun changedUpdate(p0: DocumentEvent?) {
                        update()
                    }

                    private fun update() {
                        if (jTextField.text.isNotBlank()) {
                            counter = createCounter(text = jTextField.text)
                        }
                        startButton.isEnabled = when (counter.isPresent) {
                            true -> true
                            else -> false
                        }
                    }

                }
                jTextField.document.addDocumentListener(listener)
            }
    }

    private fun createStartButton(): JButton {
        return JButton().apply { name = "startButton" }
            .also {
                it.text = "START"
                it.isEnabled = false
                it.addActionListener { executeStartAction() }
            }
    }

    private fun executeStartAction() {
        val samActionData = LauncherSamActionInputData.create(
            model = Launcher(state = Ready, counter = counter)
        )
        GuiLauncherSamExecutor.execute(
            samAction = StartLauncherSamAction,
            samActionData = samActionData,
            representationData = jFrame,
        )
    }

    companion object {
        private const val serialVersionUID: Long = 4355009491286596498L
    }

}