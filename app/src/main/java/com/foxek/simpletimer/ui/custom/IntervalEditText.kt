package com.foxek.simpletimer.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.foxek.simpletimer.R
import kotlinx.android.synthetic.main.custom_interval_view.view.*

class IntervalEditText : LinearLayout {

    companion object {
        private const val UNIT_DIVIDER = 60
        private const val SINGLE_NUMERAL_LIMIT = 10
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.custom_interval_view, this)

        if(!isInEditMode) { }
    }

    fun setValue(time: Int) {
        view_interval_field_minutes.setText(format(time / UNIT_DIVIDER))
        view_interval_field_seconds.setText(format(time % UNIT_DIVIDER))
    }

    fun getValue(): Int {
        val result: Int

        return if ((view_interval_field_minutes.text.isNotEmpty()) and (view_interval_field_seconds.text.isNotEmpty())) {
            result = view_interval_field_minutes.text.toString().toInt() * UNIT_DIVIDER + view_interval_field_seconds.text.toString().toInt()
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
        view_interval_field_minutes.isCursorVisible = false
        view_interval_field_seconds.isCursorVisible = false
    }
}