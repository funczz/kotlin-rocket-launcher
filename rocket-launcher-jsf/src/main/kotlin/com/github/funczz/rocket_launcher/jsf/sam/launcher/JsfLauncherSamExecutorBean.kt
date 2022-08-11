package com.github.funczz.rocket_launcher.jsf.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModelPresent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamStateNextAction
import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
open class JsfLauncherSamExecutorBean : Serializable, ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        Unit,
        String?
        > {

    private val samPresent = LauncherSamModelPresent::present

    private val samNextAction = LauncherSamStateNextAction::nextAction

    @field:Default
    @field:Inject
    protected open lateinit var samStateRepresentation: JsfLauncherSamStateRepresentationBean

    override fun samPresent(): (LauncherSamActionInputData) -> Result<LauncherSamModel> {
        return samPresent
    }

    override fun samNextAction(): (LauncherSamModel) -> Result<LauncherSamModel> {
        return samNextAction
    }

    override fun samRepresentation(): (LauncherSamModel, Unit) -> Result<String?> {
        return samStateRepresentation::representation
    }

    companion object {
        private const val serialVersionUID: Long = 4492329372063289190L
    }

}