package kz.mycrm.android.ui.view

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet
import kz.mycrm.android.util.Logger

/**
 * Created by Nurbek Kabylbay on 03.12.2017.
 */
class LoginEditTextView(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {

    init {
        setText("+7 7")
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        Logger.debug(text.length.toString() + ",bef"+lengthBefore+",aft" + lengthAfter)
        if(lengthAfter == 4 && lengthBefore == 1 && lengthAfter == 0)
            return
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        Logger.debug(text.length.toString() + ",bef"+lengthBefore+",aft" + lengthAfter)
    }
}