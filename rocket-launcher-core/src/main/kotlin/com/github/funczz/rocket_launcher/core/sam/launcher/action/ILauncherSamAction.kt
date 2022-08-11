package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.kotlin.sam.ISamAction
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import java.util.*

interface ILauncherSamAction : ISamAction<LauncherSamActionInputData, LauncherSamModel> {

    fun updateInputData(data: LauncherSamActionInputData, event: LauncherEvent): Result<LauncherSamActionInputData> {
        val prevModel = data.model

        val result = prevModel.state.fire(event, prevModel)
        val model = result.second.fold(
            onSuccess = { it },
            onFailure = { return Result.failure(it) }
        )

        return Result.success(
            LauncherSamActionInputData.create(
                model = model,
                prevModel = Optional.of(prevModel)
            )
        )
    }

}