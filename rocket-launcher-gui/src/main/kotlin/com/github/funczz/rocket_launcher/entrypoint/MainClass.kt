package com.github.funczz.rocket_launcher.entrypoint

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.gui.Global
import com.github.funczz.rocket_launcher.gui.sam.launcher.GuiLauncherSamExecutor
import java.util.*

class MainClass {

    companion object {

        /**
         * メインクラス
         */
        @JvmStatic
        fun main(args: Array<String>) {
            GuiLauncherSamExecutor.doRepresentation(
                model = LauncherSamModel.create(
                    model = Launcher(
                        state = Ready,
                        counter = Optional.empty(),
                    )
                ),
                representationData = Global.jFrame
            )
        }

    }

}