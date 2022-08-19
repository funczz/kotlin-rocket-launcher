package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.rop_fsm.IFsmState
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent

interface ILauncherState : IFsmState<LauncherEvent, Launcher>