package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_fsm.FsmTransition
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import java.util.*

object Ready : ILauncherState {
    override fun toTransition(event: LauncherEvent): FsmTransition<LauncherEvent, Launcher> {
        return when (event) {
            is LauncherEvent.START -> FsmTransition.External(Counting)
            is LauncherEvent.DECREMENT -> FsmTransition.Deny()
            is LauncherEvent.LAUNCH -> FsmTransition.Deny()
            is LauncherEvent.ABORT -> FsmTransition.External(Aborted)
            is LauncherEvent.FINISH -> FsmTransition.Deny()
        }
    }

    override fun onEntry(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx.copy(state = Ready)
    }

    override fun onDo(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx
    }

    override fun onExit(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        when (event) {
            is LauncherEvent.START -> ctx.copy(
                counter = Optional.of(event.counter)
            )
            else -> ctx
        }
    }
}