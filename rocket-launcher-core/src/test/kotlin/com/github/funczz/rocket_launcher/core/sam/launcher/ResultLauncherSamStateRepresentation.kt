package io.kotlintest.provided.com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.core.sam.launcher.ILauncherSamState
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import java.util.*

object ResultLauncherSamStateRepresentation : ILauncherSamState,
    ISamStateRepresentation<
            LauncherSamModel,
            Unit,
            Optional<String>,
            > {

    override fun representation(
        model: LauncherSamModel,
        representationData: Unit,
    ): RopResult<Optional<String>> = RopResult.tee {
        when {
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