package com.github.funczz.rocket_launcher.core.domain.model.launcher

import com.github.funczz.kotlin.fsm.IFsmState
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import java.util.*

data class Launcher(

    val state: IFsmState<LauncherEvent, Launcher> = Ready,

    val counter: Optional<UInt> = Optional.empty()

)