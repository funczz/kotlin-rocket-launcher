package com.github.funczz.rocket_launcher.gui.sam.launcher.action

import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.gui.sam.launcher.LauncherSamModel

object LaunchLauncherSamAction : ILauncherSamAction {

    override fun execute(
        present: (LauncherSamActionInputData) -> Result<LauncherSamModel>,
        data: LauncherSamActionInputData
    ): Result<LauncherSamModel> {
        val newData = updateInputData(data = data, event = LauncherEvent.LAUNCH)
            .fold(
                onSuccess = { it },
                onFailure = { return Result.failure(it) }
            )
        return present(newData)
    }

}