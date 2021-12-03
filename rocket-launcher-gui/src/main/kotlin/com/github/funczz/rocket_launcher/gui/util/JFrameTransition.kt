package com.github.funczz.rocket_launcher.gui.util

import java.awt.Component
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.SwingUtilities

object JFrameTransition {

    fun transition(frame: JFrame, component: Component, function: (JFrame) -> Unit = {}) {
        invokeLaterForJFrame(frame) { jFrame ->
            jFrame.contentPane.apply {
                removeAll()
                add(component)
                function(jFrame)
            }
        }

    }

    fun transition(frame: JFrame, component: Component, constraints: Any, function: (JFrame) -> Unit = {}) {
        invokeLaterForJFrame(frame) { jFrame ->
            jFrame.contentPane.apply {
                removeAll()
                add(component, constraints)
                function(jFrame)
            }
        }
    }

    fun invokeLaterForJFrame(frame: JFrame, function: (JFrame) -> Unit) {
        SwingUtilities.invokeLater {
            function(frame)
            frame.revalidate()
        }
    }

    fun invokeAndWait(frame: JFrame, function: (JFrame) -> Unit) {
        when (EventQueue.isDispatchThread()) {
            true -> function(frame)
            else -> EventQueue.invokeAndWait { function(frame) }
        }
    }

}