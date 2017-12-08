package kz.mycrm.android.ui.view

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

/**
 * Created by Nurbek Kabylbay on 03.12.2017.
 */
class LoginEditTextView(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {

    private val login = this

    private val phoneListener = object: PhoneNumberFormattingTextWatcher() {
        private var backspacingFlag = false
        private var editedFlag = false
        private var cursorComplement: Int = 0

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            cursorComplement = s.length - login.selectionStart
            backspacingFlag = count > after
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if(s.length < 3 && backspacingFlag) {
                login.setText("+7 ")
                login.setSelection(3)
                return
            } else if(s.length == 14 && backspacingFlag) {
                login.setText(s.substring(0,13))
                login.setSelection(13)
                return
            } else if(s.length == 11 && backspacingFlag) {
                login.setText(s.substring(0,10))
                login.setSelection(10)
                return
            } else if(s.length == 7 && backspacingFlag) {
                login.setText(s.substring(0,6))
                login.setSelection(6)
                return
            }

            val string = s.toString()
            val phone = string.replace("[^\\d]".toRegex(), "")
            if (!editedFlag) {
                if(phone.length >= 9 && !backspacingFlag) {
                    editedFlag = true
                    val ans = "+7 "+phone.substring(1, 4)+" "+phone.substring(4,7)+"-"+phone.substring(7, 9)+"-"+phone.substring(9)
                    login.setText(ans)
                    login.setSelection(login.text.length - cursorComplement)
                } else if (phone.length >= 7 && !backspacingFlag) {
                    editedFlag = true
                    val ans = "+7 "+phone.substring(1, 4)+" "+phone.substring(4,7)+"-"+phone.substring(7)
                    login.setText(ans)
                    login.setSelection(login.text.length - cursorComplement)
                } else if(phone.length >= 4 && !backspacingFlag) {
                    editedFlag = true
                    val ans = "+7 " + phone.substring(1, 4) + " " + phone.substring(4)
                    login.setText(ans)
                    login.setSelection(login.text.length - cursorComplement)
                }
            } else {
                editedFlag = false
            }
        }
    }
    private val callback =  object : ActionMode.Callback {
        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return false
        }

        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {

        }
    }

    init {
        setText("+7 ")
        setSelection(3)
        this.customSelectionActionModeCallback = callback
        this.addTextChangedListener(phoneListener)
    }

    public override fun onSelectionChanged(start: Int, end: Int) {

        val text = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }
        super.onSelectionChanged(start, end)
    }
}