package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.rop_sam.ISamModel
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.ILauncherState
import java.util.*

class LauncherSamModel(

    val model: Launcher,

    val prevModel: Optional<Launcher> = Optional.empty(),

    ) : ISamModel {

    val state: ILauncherState
        get() = model.state

    val counter: Optional<UInt>
        get() = model.counter

    companion object {

        @JvmStatic
        fun create(
            model: Launcher,
            prevModel: Optional<Launcher> = Optional.empty()
        ): LauncherSamModel {
            return LauncherSamModel(model = model, prevModel = prevModel)
        }
    }
}