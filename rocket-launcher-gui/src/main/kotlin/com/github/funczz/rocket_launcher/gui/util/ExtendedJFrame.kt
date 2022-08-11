package com.github.funczz.rocket_launcher.gui.util

import java.awt.Component
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

class ExtendedJFrame : JFrame() {

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val listener = object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                //System.exit(0)
            }
        }
        addWindowListener(listener)
    }

    companion object {

        private const val serialVersionUID: Long = 223837816956662510L

        fun JFrame.invokeLater(function: (JFrame) -> Unit) {
            JFrameTransition.invokeLaterForJFrame(this) {
                function(it)
            }
        }

        fun JFrame.invokeAndWait(function: (JFrame) -> Unit) {
            JFrameTransition.invokeAndWait(this, function)
        }

        fun JFrame.transition(component: Component, function: (JFrame) -> Unit = {}) {
            JFrameTransition.transition(this, component, function)
        }

        fun JFrame.transition(component: Component, constraints: Any, function: (JFrame) -> Unit = {}) {
            JFrameTransition.transition(this, component, constraints, function)
        }

    }

}