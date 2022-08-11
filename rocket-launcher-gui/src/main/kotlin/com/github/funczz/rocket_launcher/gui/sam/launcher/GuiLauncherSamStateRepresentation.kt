package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.core.sam.launcher.ILauncherSamState
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.gui.util.ExtendedJFrame
import com.github.funczz.rocket_launcher.gui.util.ExtendedJFrame.Companion.transition
import com.github.funczz.rocket_launcher.gui.view.AbortedPanel
import com.github.funczz.rocket_launcher.gui.view.CountingPanel
import com.github.funczz.rocket_launcher.gui.view.LaunchedPanel
import com.github.funczz.rocket_launcher.gui.view.ReadyPanel
import com.github.funczz.rocket_launcher.gui.view_model.CountingViewModel
import java.util.*
import javax.swing.JPanel

object GuiLauncherSamStateRepresentation : ILauncherSamState,
    ISamStateRepresentation<
            LauncherSamModel,
            ExtendedJFrame,
            Unit,
            > {

    override fun representation(model: LauncherSamModel, representationData: ExtendedJFrame): Result<Unit> {
        val panel: Optional<JPanel> = when {
            isReady(model) -> Optional.of(ReadyPanel())
            isTransitionToCounting(model) -> Optional.of(CountingPanel(vm = CountingViewModel(model = model)))
            isAborted(model) -> Optional.of(AbortedPanel(model.counter))
            isLaunched(model) -> Optional.of(LaunchedPanel())
            else -> {
                Optional.empty<JPanel>()
            }
        }
        if (panel.isPresent) {
            representationData.transition(panel.get()) {
                it.pack()
                it.isVisible = true
            }
        }
        return Result.success(Unit)
    }
}