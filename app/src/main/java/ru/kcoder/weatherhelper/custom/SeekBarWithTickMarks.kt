package ru.kcoder.weatherhelper.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import android.graphics.drawable.Drawable


class SeekBarWithTickMarks(context: Context, attrs: AttributeSet) : AppCompatSeekBar(context, attrs) {

    private var tickMarkOne: Drawable? = null
    private var tickMarkSecond: Drawable? = null

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        drawTickMarks(canvas)
        super.onDraw(canvas)
    }

    private fun drawTickMarks(canvas: Canvas) {
        val tmpMarkOne = tickMarkOne ?: tickMarkSecond
        val tmpMarkSecond = tickMarkSecond ?: tickMarkOne
        if (tmpMarkOne != null && tmpMarkSecond != null) {
            val count = max
            if (count > 1) {
                val w = tmpMarkOne.intrinsicWidth
                val h = tmpMarkOne.intrinsicHeight
                val halfThumbW = thumb.intrinsicWidth / 2
                val halfW = if (w >= 0) w / 2 else 1
                val halfH = if (h >= 0) h / 2 else 1
                tmpMarkOne.setBounds(-halfW, -halfH, halfW, halfH)
                val spacing =
                    (width - paddingLeft - paddingRight + thumbOffset * 2 - halfThumbW * 2) / count.toFloat()

                val saveCount = canvas.save()
                canvas.translate((paddingLeft - thumbOffset + halfThumbW).toFloat(), (height / 2).toFloat())
                for (i in 0..count) {
                    if (i != progress && i % 2 != 0) {
                        tmpMarkOne.draw(canvas)
                    }

                    if (i != progress && i % 2 == 0) {
                        tmpMarkSecond.draw(canvas)
                    }
                    canvas.translate(spacing, 0f)
                }
                canvas.restoreToCount(saveCount)
            }
        }
    }

    fun setTickMarks(tickMarkOne: Drawable?, tickMarkSecond: Drawable?) {
        this.tickMarkOne = tickMarkOne
        this.tickMarkSecond = tickMarkSecond
    }
}