package com.foxek.simpletimer.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.foxek.simpletimer.R
import kotlinx.android.synthetic.main.view_edit_interval.view.*

class IntervalEditView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : LinearLayout(context, attrs, defStyleAttr) {

    fun setValue(time: Int) {
        etMinutes.setText(format(time / UNIT_DIVIDER))
        etSeconds.setText(format(time % UNIT_DIVIDER))
    }

    fun getValue(): Int {
        val result: Int

        return if ((etMinutes.text.isNotEmpty()) and (etSeconds.text.isNotEmpty())) {
            result = etMinutes.text.toString().toInt() * UNIT_DIVIDER + etSeconds.text.toString().toInt()
            if (result == 0) 1 else result
        } else
            1
    }

    private fun format(time: Int): String {
        return if (time < SINGLE_NUMERAL_LIMIT)
            "0$time"
        else
            time.toString()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        etMinutes.isCursorVisible = false
        etSeconds.isCursorVisible = false
    }

    companion object {
        private const val UNIT_DIVIDER = 60
        private const val SINGLE_NUMERAL_LIMIT = 10
    }
}