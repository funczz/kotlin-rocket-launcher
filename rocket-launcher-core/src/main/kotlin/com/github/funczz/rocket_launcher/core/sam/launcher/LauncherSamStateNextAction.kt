package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.sam.ISamModelPresent
import com.github.funczz.kotlin.sam.ISamStateNextAction
import com.github.funczz.kotlin.sam.NextActionData
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.sam.launcher.action.LaunchLauncherSamAction

object LauncherSamStateNextAction : ILauncherSamState, ISamStateNextAction<LauncherSamModel> {

    private val samPresent: ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> = LauncherSamModelPresent

    override fun nextActionPredicate(data: NextActionData<LauncherSamModel>): Result<NextActionData<LauncherSamModel>> {
        if (data is NextActionData.Terminate<LauncherSamModel>) return Result.success(data)
        val model = data.model
        val result = when (isCountingZero(model = model)) {
            true -> {
                val prevModel = Launcher(state = model.state, counter = model.counter)
                val inputData = LauncherSamActionInputData(model = prevModel)
                val nextModel = LaunchLauncherSamAction.execute(samPresent::present, inputData)
                    .fold(
                        onSuccess = { it },
                        onFailure = { return Result.failure(it) }
                    )
                NextActionData.Continue(model = nextModel)
            }
            else -> NextActionData.Terminate(model = model)
        }
        return Result.success(result)
    }

}