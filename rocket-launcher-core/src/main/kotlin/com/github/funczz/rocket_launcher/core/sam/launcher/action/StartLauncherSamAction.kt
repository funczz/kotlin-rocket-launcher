package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel

object StartLauncherSamAction : ILauncherSamAction {

    override fun execute(
        present: (LauncherSamActionInputData) -> Result<LauncherSamModel>,
        data: LauncherSamActionInputData
    ): Result<LauncherSamModel> {
        val newData = updateInputData(data = data, event = LauncherEvent.START(data.model.counter.get()))
            .fold(
                onSuccess = { it },
                onFailure = { return Result.failure(it) }
            )
        return present(newData)
    }

}