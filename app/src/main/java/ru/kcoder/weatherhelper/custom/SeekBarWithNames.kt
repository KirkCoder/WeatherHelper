package ru.kcoder.weatherhelper.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.seekbar_with_names.view.*
import ru.kcoder.weatherhelper.ru.weatherhelper.R


class SeekBarWithNames(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private var tickMarkOne: Drawable? = null
    private var tickMarkSecond: Drawable? = null
    private var widthMeasureSpec = 0
    private var heightMeasureSpec = 0

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.SeekBarWithNames,
            0, 0
        )
        try {
            tickMarkOne = attrs.getDrawable(R.styleable.SeekBarWithNames_tickMarkOne)
            tickMarkSecond = attrs.getDrawable(R.styleable.SeekBarWithNames_tickMarkSecond)
        } finally {
            attrs.recycle()
        }
    }

    fun setNames(list: List<String>) {
        visibility = if (list.size < 2) View.GONE
        else View.VISIBLE
        displayNames(list)
        seekBarWithTickMarks.setMarksCount(list.size)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        LayoutInflater.from(context)
            .inflate(R.layout.seekbar_with_names, this)

        seekBarWithTickMarks.setTickMarks(tickMarkOne, tickMarkSecond)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (changed) {
            alignIntervals()

            // We've changed the intervals layout, we need to refresh.

            linerLayoutLabels.measure(widthMeasureSpec, heightMeasureSpec)
            linerLayoutLabels.layout(
                linerLayoutLabels.left,
                linerLayoutLabels.top,
                linerLayoutLabels.right,
                linerLayoutLabels.bottom
            )
        }
    }

    private fun alignIntervals() {
        val interval = seekBarWithTickMarks.width / seekBarWithTickMarks.max
        for (i in 0 until linerLayoutLabels.childCount) {
            val view = linerLayoutLabels.getChildAt(i)
            if (i == 0)
                view.setPadding(interval - view.width / 2, 0, 0, 0)
            else
                view.setPadding(interval * 2 - view.width, 0, 0, 0)
        }
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.widthMeasureSpec = widthMeasureSpec
        this.heightMeasureSpec = heightMeasureSpec

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun displayNames(list: List<String>) {
        for (names in list) {
            linerLayoutLabels.addView(createTextView(names))
        }
    }

    private fun createTextView(interval: String): TextView {
        return TextView(context).apply {
            id = View.generateViewId()
            text = interval
        }
    }

    fun setListener(callback: ((position: Int) -> Unit)?) {
        seekBarWithTickMarks.setListener(callback)
    }
}