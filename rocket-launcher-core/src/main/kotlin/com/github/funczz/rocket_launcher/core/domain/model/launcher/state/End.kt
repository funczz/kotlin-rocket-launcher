package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.fsm.FsmTransition
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import java.util.*

object End : ILauncherState {
    override fun toTransition(event: LauncherEvent): FsmTransition<LauncherEvent, Launcher> {
        return  FsmTransition.Deny()
    }

    override fun onEntry(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx.copy(state = End))
    }

    override fun onDo(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx)
    }

    override fun onExit(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx)
    }
}