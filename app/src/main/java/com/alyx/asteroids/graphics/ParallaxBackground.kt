package com.alyx.asteroids.graphics

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ProgressBar

class ParallaxBackground@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ProgressBar(context, attrs, defStyleAttr) {
    override fun invalidateDrawable(dr: Drawable) {
        super.invalidateDrawable(dr)
    }


}