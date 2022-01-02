package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.sam.*
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.sam.launcher.action.LaunchLauncherSamAction

class LauncherSamStateNextAction : ILauncherSamState, ISamStateNextAction<LauncherSamModel> {

    private val samPresent: ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> = LauncherSamModelPresent()

    override fun nextActionPredicate(data: SamNextActionData<LauncherSamModel>): Result<SamNextActionData<LauncherSamModel>> {
        if (data is Terminate<LauncherSamModel>) return Result.success(data)
        val model = data.model
        val result = when(isCountingZero(model = model)) {
            true -> {
                val prevModel = Launcher(state = model.state, counter = model.counter)
                val inputData = LauncherSamActionInputData(model = prevModel)
                val nextModel = LaunchLauncherSamAction.execute(samPresent::present, inputData)
                    .fold(
                        onSuccess = { it },
                        onFailure = { return Result.failure(it) }
                    )
                Continue(model = nextModel)
            }
            else -> Terminate(model = model)
        }
        return Result.success(result)
    }

}