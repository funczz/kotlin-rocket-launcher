package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_fsm.FsmTransition
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent

object Aborted : ILauncherState {
    override fun toTransition(event: LauncherEvent): FsmTransition<LauncherEvent, Launcher> {
        return when (event) {
            is LauncherEvent.START -> FsmTransition.Deny()
            is LauncherEvent.DECREMENT -> FsmTransition.Deny()
            is LauncherEvent.LAUNCH -> FsmTransition.Deny()
            is LauncherEvent.ABORT -> FsmTransition.Deny()
            is LauncherEvent.FINISH -> FsmTransition.External(End)
        }
    }

    override fun onEntry(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx.copy(state = Aborted)
    }


    override fun onDo(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx
    }

    override fun onExit(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx
    }
}