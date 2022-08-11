package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel

object AbortLauncherSamAction : ILauncherSamAction {

    override fun execute(
        present: (LauncherSamActionInputData) -> Result<LauncherSamModel>,
        data: LauncherSamActionInputData
    ): Result<LauncherSamModel> {
        val newData = updateInputData(data = data, event = LauncherEvent.ABORT)
            .fold(
                onSuccess = { it },
                onFailure = { return Result.failure(it) }
            )
        return present(newData)
    }

}