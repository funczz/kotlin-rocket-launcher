package com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Aborted
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Launched
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready

interface ILauncherSamState {

    fun isReady(model: LauncherSamModel): Boolean {
        return !model.counter.isPresent
    }

    fun isTransitionToCounting(model: LauncherSamModel): Boolean {
        return model.state is Counting && model.prevModel.isPresent && model.prevModel.get().state is Ready
    }

    fun isCounting(model: LauncherSamModel): Boolean {
        return model.state is Counting && model.counter.isPresent && model.prevModel.get().state is Counting
    }

    fun isCountingZero(model: LauncherSamModel): Boolean {
        return model.state is Counting && model.counter.isPresent && model.counter.get() == 0u
    }

    fun isLaunched(model: LauncherSamModel): Boolean {
        return model.state is Launched
    }

    fun isAborted(model: LauncherSamModel): Boolean {
        return model.state is Aborted
    }

}