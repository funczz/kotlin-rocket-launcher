package com.github.funczz.rocket_launcher.jsf.bean

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.action.StartLauncherSamAction
import com.github.funczz.rocket_launcher.jsf.sam.launcher.JsfLauncherSamExecutorBean
import java.io.Serializable
import java.util.*
import javax.enterprise.context.SessionScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
open class IndexBean : Serializable {

    open var inputCounter: Int? = null

    @field:Default
    @field:Inject
    protected open lateinit var jsfLauncherSamExecutorBean: JsfLauncherSamExecutorBean

    @field:Default
    @field:Inject
    protected open lateinit var errorBean: ErrorBean

    open fun start(): String? {
        val initialCounter = try {
            Optional.of(inputCounter!!.toUInt())
        } catch (e: NullPointerException) {
            Optional.empty<UInt>()
        } catch (e: NumberFormatException) {
            Optional.empty<UInt>()
        } catch (e: Exception) {
            return errorBean.error(e.message ?: "")
        }
        if (!initialCounter.isPresent) return null
        val model = Launcher(state = Ready, counter = initialCounter)
        val data = LauncherSamActionInputData.create(model)
        return try {
            jsfLauncherSamExecutorBean.execute(
                samAction = StartLauncherSamAction,
                samActionData = data,
                representationData = Unit,
            ).getOrThrow()
        } catch (e: Throwable) {
            return errorBean.error(e.message ?: "")
        }
    }

    companion object {
        private const val serialVersionUID: Long = 5856927244660302278L
    }
}