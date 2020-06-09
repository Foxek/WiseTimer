package com.foxek.simpletimer.presentation.interval.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.foxek.simpletimer.R
import kotlinx.android.synthetic.main.view_edit_interval.view.*

class IntervalEditView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        View.inflate(context, R.layout.view_edit_interval, this)
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

    companion object {
        private const val UNIT_DIVIDER = 60
        private const val SINGLE_NUMERAL_LIMIT = 10
    }
}