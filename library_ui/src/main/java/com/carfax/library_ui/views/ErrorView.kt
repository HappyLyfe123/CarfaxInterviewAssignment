package com.carfax.library_ui.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.carfax.library_ui.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorView)
    private val view: View = View.inflate(context, R.layout.view_holder_error_info, this)

    init {
        view.findViewById<TextView>(R.id.error_message_text_view).text = attributes.getString(R.styleable.ErrorView_text)
    }

    fun setText(text: String){
        view.findViewById<TextView>(R.id.error_message_text_view).text = text
    }
}