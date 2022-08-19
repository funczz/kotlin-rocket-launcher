package com.github.funczz.rocket_launcher.core.sam.launcher.action

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_sam.ISamAction
import com.github.funczz.rocket_launcher.core.domain.model.launcher.LauncherEvent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import java.util.*

interface ILauncherSamAction : ISamAction<LauncherSamActionInputData, LauncherSamModel> {

    fun updateInputData(
        data: LauncherSamActionInputData,
        event: LauncherEvent
    ): RopResult<LauncherSamActionInputData> {
        val prevModel = data.model
        val (_, ctx) = prevModel.state.fire(event, prevModel)
        return ctx.andThen {
            RopResult.tee {
                LauncherSamActionInputData.create(
                    model = it,
                    prevModel = Optional.of(prevModel)
                )
            }
        }
    }

}