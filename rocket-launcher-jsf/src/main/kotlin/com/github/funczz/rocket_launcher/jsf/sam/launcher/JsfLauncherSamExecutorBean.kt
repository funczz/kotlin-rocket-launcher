package com.github.funczz.rocket_launcher.jsf.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.ILauncherState
import com.github.funczz.rocket_launcher.core.domain.model.launcher.state.Ready
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModelPresent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamStateNextAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.AbortLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.DecrementLauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.ILauncherSamAction
import com.github.funczz.rocket_launcher.core.sam.launcher.action.StartLauncherSamAction
import java.io.Serializable
import java.util.*
import javax.enterprise.context.SessionScoped
import javax.inject.Named

@Named
@SessionScoped
open class JsfLauncherSamExecutorBean : Serializable, ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        LauncherSamModelPresent,
        LauncherSamStateNextAction,
        JsfLauncherSamStateRepresentation
        > {

    private val samPresent = LauncherSamModelPresent()

    private val samNextAction = LauncherSamStateNextAction()

    private val samStateRepresentation = JsfLauncherSamStateRepresentation()

    private var state: ILauncherState = Ready

    open var number: Int? = null

    open var throwable: Throwable? = null

    override fun samPresent(): LauncherSamModelPresent {
        return samPresent
    }

    override fun samNextAction(): LauncherSamStateNextAction {
        return samNextAction
    }

    override fun samRepresentation(): JsfLauncherSamStateRepresentation {
        return samStateRepresentation!!
    }

    open fun startAction(): String? {
        this.state = Ready
        this.throwable = null
        return this.action(samAction = StartLauncherSamAction)
    }

    open fun decrementAction(): String? {
        return this.action(samAction = DecrementLauncherSamAction)
    }

    open fun abortAction(): String? {
        return this.action(samAction = AbortLauncherSamAction)
    }

    open fun errorMessage(): String {
        return this.throwable?.stackTraceToString() ?: ""
    }

    private fun action(samAction: ILauncherSamAction): String? {
        val counter = try {
            Optional.of(number!!.toUInt())
        } catch (e: NullPointerException) {
            Optional.empty<UInt>()
        } catch (e: NumberFormatException) {
            Optional.empty<UInt>()
        } catch (e: Exception) {
            this.throwable = e
            return "error"
        }
        val model = Launcher(state = state, counter = counter)
        val data = LauncherSamActionInputData.create(model)
        val resultAction = this.doAction(samAction, data)
        val samModel = try {
            resultAction.getOrThrow()
        } catch (e: Exception) {
            this.throwable = e
            return "error"
        }
        state = samModel.state
        number = try {
            samModel.counter.get().toInt()
        } catch (e: Exception) {
            this.throwable = e
            return "error"
        }
        this.doRepresentation(samModel)
        return samRepresentation().returnValue
    }

    companion object {
        private const val SerialVersionUID: Long = 1L
    }

}