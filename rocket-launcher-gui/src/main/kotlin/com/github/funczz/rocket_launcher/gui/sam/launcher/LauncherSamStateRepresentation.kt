package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.gui.util.ExtendedJFrame
import com.github.funczz.rocket_launcher.gui.util.ExtendedJFrame.Companion.transition
import com.github.funczz.rocket_launcher.gui.view.ReadyPanel
import java.util.*
import javax.swing.JPanel

class LauncherSamStateRepresentation(

    private val jFrame: ExtendedJFrame = ExtendedJFrame()

) : ILauncherSamState, ISamStateRepresentation<LauncherSamModel> {

    override fun representation(model: LauncherSamModel) {
        val panel: Optional<JPanel> = when {
            isReady(model) -> {
                Optional.of(ReadyPanel())
            }
            else -> {
                Optional.empty<JPanel>()
            }
        }
        if (panel.isPresent) {
            jFrame.transition(panel.get()) {
                it.pack()
                it.isVisible = true
            }
        }
    }
}