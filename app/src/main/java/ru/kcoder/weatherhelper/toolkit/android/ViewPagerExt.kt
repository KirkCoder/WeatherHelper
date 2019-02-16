package ru.kcoder.weatherhelper.toolkit.android

import androidx.viewpager.widget.ViewPager

fun ViewPager.selectedPageListener(callback: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            callback(position)
        }
    })
}