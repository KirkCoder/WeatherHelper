package ru.kcoder.weatherhelper.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import android.graphics.drawable.Drawable
import android.widget.SeekBar


class SeekBarWithTickMarks(context: Context, attrs: AttributeSet) : AppCompatSeekBar(context, attrs) {

    private var tickMarkOne: Drawable? = null
    private var tickMarkSecond: Drawable? = null
    private var isMarkAvailable = false

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        drawTickMarks(canvas)
        super.onDraw(canvas)
    }

    private fun drawTickMarks(canvas: Canvas) {
        val tmpMarkOne = tickMarkOne ?: tickMarkSecond
        val tmpMarkSecond = tickMarkSecond ?: tickMarkOne
        val count = max

        if (tmpMarkOne != null && tmpMarkSecond != null && isMarkAvailable && count > 1) {
            val w = tmpMarkOne.intrinsicWidth
            val h = tmpMarkOne.intrinsicHeight
            val halfThumbW = thumb.intrinsicWidth / 2
            val halfW = if (w >= 0) w / 2 else 1
            val halfH = if (h >= 0) h / 2 else 1
            tmpMarkOne.setBounds(-halfW, -halfH, halfW, halfH)
            val spacing =
                (width - paddingLeft - paddingRight + thumbOffset * 2 - halfThumbW * 2) / count.toFloat()

            val w2 = tmpMarkSecond.intrinsicWidth
            val h2 = tmpMarkSecond.intrinsicHeight
            val halfThumbW2 = thumb.intrinsicWidth / 2
            val halfW2 = if (w2 >= 0) w2 / 2 else 1
            val halfH2 = if (h2 >= 0) h2 / 2 else 1
            tmpMarkSecond.setBounds(-halfW2, -halfH2, halfW2, halfH2)
            val spacing2 =
                (width - paddingLeft - paddingRight + thumbOffset * 2 - halfThumbW2 * 2) / count.toFloat()

            val saveCount = canvas.save()
            canvas.translate((paddingLeft - thumbOffset + halfThumbW).toFloat(), (height / 2).toFloat())
            for (i in 0..count) {
                if (i % 2 != 0) {
                    tmpMarkOne.draw(canvas)
                    canvas.translate(spacing, 0f)
                }

                if (i % 2 == 0) {
                    tmpMarkSecond.draw(canvas)
                    canvas.translate(spacing2, 0f)
                }
            }
            canvas.restoreToCount(saveCount)
        }
    }

    fun setTickMarks(tickMarkOne: Drawable?, tickMarkSecond: Drawable?) {
        this.tickMarkOne = tickMarkOne
        this.tickMarkSecond = tickMarkSecond
    }

    fun setMarksCount(size: Int) {
        if (size != 0) {
            max = size * 2
            isMarkAvailable = true

            progress = 1
            initListener()
        }
    }

    fun setListener(callback: ((position: Int) -> Unit)?){
        initListener(callback)
    }

    private fun initListener(callback: ((position: Int) -> Unit)? = null){
        setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress % 2 == 0 && progress != max){
                    seekBar.progress = progress + 1
                }
                if (progress == max) {
                    seekBar.progress = progress - 1
                }
                callback?.invoke(seekBar.progress / 2)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}