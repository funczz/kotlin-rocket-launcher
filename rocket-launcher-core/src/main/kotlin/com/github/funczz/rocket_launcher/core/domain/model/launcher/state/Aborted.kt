package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.fsm.FsmTransition
import com.github.funczz.kotlin.fsm.IFsmState
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import java.util.*

object Aborted : IFsmState<LauncherEvent, Launcher> {
    override fun toTransition(event: LauncherEvent): Optional<FsmTransition<LauncherEvent, Launcher>> {
        return when (event) {
            is LauncherEvent.START -> Optional.empty()
            is LauncherEvent.DECREMENT -> Optional.empty()
            is LauncherEvent.LAUNCH -> Optional.empty()
            is LauncherEvent.ABORT -> Optional.empty()
            is LauncherEvent.FINISH -> Optional.of(FsmTransition.External(End))
        }
    }

    override fun onEntry(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx.copy(state = Aborted))
    }

    override fun onDo(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx)
    }

    override fun onExit(event: LauncherEvent, ctx: Launcher): Result<Launcher> {
        return Result.success(ctx)
    }
}