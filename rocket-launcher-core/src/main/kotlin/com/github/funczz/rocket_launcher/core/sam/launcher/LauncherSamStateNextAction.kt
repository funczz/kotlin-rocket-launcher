package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_sam.ISamModelPresent
import com.github.funczz.kotlin.rop_sam.ISamStateNextAction
import com.github.funczz.kotlin.rop_sam.NextActionData
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.sam.launcher.action.LaunchLauncherSamAction

object LauncherSamStateNextAction : ILauncherSamState, ISamStateNextAction<LauncherSamModel> {

    private val samPresent: ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> = LauncherSamModelPresent

    override fun nextActionPredicate(data: NextActionData<LauncherSamModel>): RopResult<NextActionData<LauncherSamModel>> {
        if (data is NextActionData.Terminate<LauncherSamModel>) return RopResult.success { data }
        val model = data.model
        return when (isCountingZero(model = model)) {
            true -> {
                val prevModel = Launcher(state = model.state, counter = model.counter)
                val inputData = LauncherSamActionInputData(model = prevModel)
                LaunchLauncherSamAction.execute(samPresent::present, inputData)
                    .andThen {
                        RopResult.tee {
                            NextActionData.Continue(model = it)
                        }
                    }
            }
            else -> RopResult.tee { NextActionData.Terminate(model = model) }
        }
    }

}