package com.github.funczz.rocket_launcher.jsf.sam.launcher

import com.github.funczz.kotlin.rop.result.RopResult
import com.github.funczz.kotlin.rop_sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.core.sam.launcher.ILauncherSamState
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.jsf.bean.AbortedBean
import com.github.funczz.rocket_launcher.jsf.bean.CountingBean
import java.io.Serializable
import java.util.*
import javax.enterprise.context.SessionScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
open class JsfLauncherSamStateRepresentationBean : Serializable, ILauncherSamState,
    ISamStateRepresentation<
            LauncherSamModel,
            Unit,
            String
            > {

    @field:Default
    @field:Inject
    protected open lateinit var countingBean: CountingBean

    @field:Default
    @field:Inject
    protected open lateinit var abortedBean: AbortedBean

    override fun representation(
        model: LauncherSamModel,
        representationData: Unit,
    ): RopResult<String> = RopResult.tee {
        when {
            isReady(model) -> "index" //"index?faces-redirect=true"
            isTransitionToCounting(model) -> {
                countingBean.counter = model.counter.get().toInt()
                abortedBean.currentCounter = Optional.empty()
                "counting" //"counting?faces-redirect=true"
            }
            isAborted(model) -> {
                countingBean.counter = null
                abortedBean.currentCounter = model.counter
                "aborted" //"aborted?faces-redirect=true"
            }
            isLaunched(model) -> {
                countingBean.counter = null
                abortedBean.currentCounter = Optional.empty()
                "launched" //"launched?faces-redirect=true"
            }
            else -> ""
        }
    }

    companion object {
        private const val serialVersionUID: Long = 1159387041570488023L
    }

}