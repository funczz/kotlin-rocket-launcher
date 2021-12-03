package com.github.funczz.rocket_launcher.core.domain.model.launcher

import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.ILauncherState
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import java.util.*

data class Launcher(

    val state: ILauncherState = Ready,

    val counter: Optional<UInt> = Optional.empty()

)