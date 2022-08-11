package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import java.util.*

data class LauncherSamActionInputData(

    val model: Launcher,

    val prevModel: Optional<Launcher> = Optional.empty(),

) {

    companion object {

        @JvmStatic
        fun create(
            model: Launcher,
            prevModel: Optional<Launcher> = Optional.empty()
        ): LauncherSamActionInputData {
            return LauncherSamActionInputData(model = model, prevModel = prevModel)
        }

    }

}