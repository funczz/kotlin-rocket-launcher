package com.github.funczz.rocket_launcher.core.domain.model.launcher.state

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_fsm.FsmTransition
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import java.util.*

object Counting : ILauncherState {
    override fun toTransition(event: LauncherEvent): FsmTransition<LauncherEvent, Launcher> {
        return when (event) {
            is LauncherEvent.START -> FsmTransition.Deny()
            is LauncherEvent.DECREMENT -> FsmTransition.Internal()
            is LauncherEvent.LAUNCH -> FsmTransition.External(Launched)
            is LauncherEvent.ABORT -> FsmTransition.External(Aborted)
            is LauncherEvent.FINISH -> FsmTransition.External(End)
        }
    }

    override fun onEntry(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx.copy(state = Counting)
    }

    override fun onDo(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        if (event is LauncherEvent.DECREMENT) {
            if (!ctx.counter.isPresent) {
                throw NoSuchElementException("Launcher.counter: No value present")
            }
            if (ctx.counter.get() <= 0u) {
                throw IllegalAccessException("Launcher.counter: value less than or equal to zero")
            }
            ctx.copy(counter = Optional.of(ctx.counter.get() - 1u))
        } else ctx
    }

    override fun onExit(event: LauncherEvent, ctx: Launcher): RopResult<Launcher> = RopResult.tee {
        ctx
    }
}