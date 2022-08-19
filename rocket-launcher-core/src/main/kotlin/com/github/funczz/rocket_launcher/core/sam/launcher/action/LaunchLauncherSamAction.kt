package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel

object LaunchLauncherSamAction : ILauncherSamAction {

    override fun execute(
        present: (LauncherSamActionInputData) -> RopResult<LauncherSamModel>,
        data: LauncherSamActionInputData
    ): RopResult<LauncherSamModel> = RopResult.tee {
        updateInputData(data = data, event = LauncherEvent.LAUNCH)
    }.andThen {
        present(it.getOrThrow())
    }

}