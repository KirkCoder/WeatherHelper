package ru.kcoder.weatherhelper.features.weather.detail.item.recycler

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DecoratorDetail(
    private val divider: Drawable,
    private val lustMargin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = lustMargin
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)

            child.findViewById<AppCompatTextView>(R.id.textViewName)?.let {
                val top = child.bottom
                val bottom = top + divider.intrinsicHeight
                val left = it.left
                val right = parent.width - parent.paddingRight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }
}