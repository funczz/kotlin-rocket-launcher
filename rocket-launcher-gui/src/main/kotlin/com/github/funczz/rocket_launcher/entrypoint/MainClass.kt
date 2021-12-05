package com.github.funczz.rocket_launcher.entrypoint

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamExecutor
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamModel
import java.util.*

class MainClass {

    companion object {

        /**
         * メインクラス
         */
        @JvmStatic
        fun main(args: Array<String>) {
            LauncherSamExecutor.doRepresentation(
                model = LauncherSamModel.create(
                    model = Launcher(
                        state = Ready,
                        counter = Optional.empty(),
                    )
                )
            )
        }

    }

}