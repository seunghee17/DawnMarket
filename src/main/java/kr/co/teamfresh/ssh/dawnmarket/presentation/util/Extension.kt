package kr.co.teamfresh.ssh.dawnmarket.presentation.util

import android.content.res.Resources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Extension {
    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit){
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
    private const val IMG_BASE_URL = "https://d1afu5va4iy6dc.cloudfront.net/"
    fun String.toFullImageUrl():String{
        return IMG_BASE_URL + this
    }

    //필요할까?
    fun Float.fromDpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

}