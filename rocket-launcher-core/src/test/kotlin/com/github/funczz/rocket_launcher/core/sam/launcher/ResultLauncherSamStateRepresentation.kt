package io.kotlintest.provided.com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.core.sam.launcher.ILauncherSamState
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import java.util.*

class ResultLauncherSamStateRepresentation(

    var result: Optional<String> = Optional.empty()

) : ILauncherSamState, ISamStateRepresentation<LauncherSamModel> {

    override fun representation(model: LauncherSamModel) {
        result = when {
            isReady(model) -> Optional.of("Ready.")
            isTransitionToCounting(model) -> Optional.of("TransitionToCounting. counter=%s".format(model.counter.get()))
            isAborted(model) -> Optional.of("Aborted. counter=%s".format(model.counter.get()))
            isLaunched(model) -> Optional.of("Launched.")
            else -> {
                Optional.empty()
            }
        }
    }
}