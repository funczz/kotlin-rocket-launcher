package com.github.funczz.rocket_launcher.jsf.bean

import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Counting
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.DecrementLauncherSamAction
import com.github.funczz.rocket_launcher.jsf.sam.launcher.JsfLauncherSamExecutorBean
import java.io.Serializable
import java.util.*
import javax.enterprise.context.SessionScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
open class CountingBean : Serializable {

    open var counter: Int? = null

    @field:Default
    @field:Inject
    protected open lateinit var jsfLauncherSamExecutorBean: JsfLauncherSamExecutorBean

    @field:Default
    @field:Inject
    protected open lateinit var errorBean: ErrorBean

    open fun decrement(): String? {
        val model = Launcher(state = Counting, counter = Optional.of(counter!!.toUInt()))
        val data = LauncherSamActionInputData.create(model)
        val samModel = try {
            jsfLauncherSamExecutorBean
                .doAction(DecrementLauncherSamAction, data)
                .getOrThrow()
        } catch (e: Exception) {
            counter = null
            return errorBean.error(e.message ?: "")
        }
        counter = samModel.counter.get().toInt()
        return try {
            jsfLauncherSamExecutorBean.doRepresentation(samModel, Unit).getOrThrow()
        } catch (e: Throwable) {
            counter = null
            return errorBean.error(e.message ?: "")
        }
    }

    open fun abort(): String? {
        val currentCounter = try {
            Optional.of(counter!!.toUInt())
        } catch (e: NullPointerException) {
            counter = null
            return errorBean.error("%s: %s".format(e.javaClass.canonicalName, "counter"))
        } catch (e: Exception) {
            counter = null
            return errorBean.error(e.message ?: "")
        }
        val model = Launcher(state = Counting, counter = currentCounter)
        val data = LauncherSamActionInputData.create(model)
        return try {
            jsfLauncherSamExecutorBean.execute(
                samAction = AbortLauncherSamAction,
                samActionData = data,
                representationData = Unit,
            ).getOrThrow()
        } catch (e: Throwable) {
            counter = null
            return errorBean.error(e.message ?: "")
        }

    }

    companion object {
        private const val serialVersionUID: Long = 1112982258100208407L
    }

}