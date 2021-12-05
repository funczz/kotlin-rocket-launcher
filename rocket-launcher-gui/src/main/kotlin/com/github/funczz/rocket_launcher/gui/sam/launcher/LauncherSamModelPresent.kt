package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamModelPresent

class LauncherSamModelPresent : ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> {

    override fun present(data: LauncherSamActionInputData): Result<LauncherSamModel> {
        return Result.success(LauncherSamModel.create(
            model = data.model,
            prevModel = data.prevModel
        ))
    }

}