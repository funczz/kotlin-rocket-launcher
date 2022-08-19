package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_sam.ISamModelPresent

object LauncherSamModelPresent : ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> {

    override fun present(data: LauncherSamActionInputData): RopResult<LauncherSamModel> = RopResult.tee {
        LauncherSamModel.create(
            model = data.model,
            prevModel = data.prevModel
        )
    }

}