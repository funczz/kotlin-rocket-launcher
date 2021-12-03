package com.github.funczz.rocket_launcher.core.domain.model.launcher

sealed class LauncherEvent {

    class START(val counter: UInt): LauncherEvent()

    object DECREMENT: LauncherEvent()

    object LAUNCH: LauncherEvent()

    object ABORT: LauncherEvent()

    object FINISH: LauncherEvent()

}