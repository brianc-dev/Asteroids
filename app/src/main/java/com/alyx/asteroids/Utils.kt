package com.alyx.asteroids

import android.content.res.Resources
import android.util.TypedValue
import kotlinx.coroutines.*


/** Convert DP to PX

 */
val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)

class Timer {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private val timer: Job = startCoroutineTimer(delayMillis = 0, repeatMillis = 20000) {
        scope.launch(Dispatchers.Main) {
        }
    }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}