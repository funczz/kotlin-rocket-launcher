package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel

object StartLauncherSamAction : ILauncherSamAction {

    override fun execute(
        present: (LauncherSamActionInputData) -> RopResult<LauncherSamModel>,
        data: LauncherSamActionInputData
    ): RopResult<LauncherSamModel> = RopResult.tee {
        val count = data.model.counter.get()
        updateInputData(data = data, event = LauncherEvent.START(count))
    }.andThen {
        present(it.getOrThrow())
    }

}