package kz.mycrm.android.ui.view.journal

import android.graphics.Rect
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kz.mycrm.android.db.entity.Order

/**
 * Created by Nurbek Kabylbay on 21.01.2018.
 */
class OrderEventLayout(var rect: Rect, var order: Order, val parent: JournalView): LinearLayout(parent.context) {
    var isSorted = false
    var isInitialized = false

    val timeTextView = TextView(parent.context)
    val nameTextView = TextView(parent.context)
    val phoneTextView = TextView(parent.context)
    val serviceTextView = TextView(parent.context)

    val mPaddingLeft = DimensionConverter.dpToPx(6, parent.context).toInt()
    val mPaddingTop = DimensionConverter.dpToPx(6, parent.context).toInt()
    val mPaddingRight = DimensionConverter.dpToPx(6, parent.context).toInt()
    val mPaddingBottom = DimensionConverter.dpToPx(7, parent.context).toInt()

    init {
        this.orientation = LinearLayout.VERTICAL
        this.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom)

        timeTextView.visibility = View.VISIBLE
        timeTextView.setTextColor(parent.mOrderTextColor)
        timeTextView.textSize = DimensionConverter.pxToSp(parent.mOrderTimeTextSize, parent.context)
        timeTextView.alpha = 0.8f
        timeTextView.setPadding(DimensionConverter.dpToPx(1, parent.context).toInt(),0,0,0)

        nameTextView.visibility = View.VISIBLE
        nameTextView.typeface = Typeface.DEFAULT_BOLD
        nameTextView.setTextColor(parent.mOrderTextColor)
        nameTextView.textSize = DimensionConverter.pxToSp(parent.mOrderPatientNameSize, parent.context)

        phoneTextView.visibility = View.VISIBLE
        phoneTextView.setTextColor(parent.mOrderTextColor)
        phoneTextView.textSize = DimensionConverter.pxToSp(parent.mOrderPatientNumberSize, parent.context)

        serviceTextView.visibility = View.VISIBLE
        serviceTextView.setTextColor(parent.mOrderTextColor)
        serviceTextView.textSize = DimensionConverter.pxToSp(parent.mOrderServiceListSize, parent.context)
        serviceTextView.alpha = 0.66f

        removeAllViews()
        this.addView(timeTextView)
        this.addView(nameTextView)
        this.addView(phoneTextView)
        this.addView(serviceTextView)
    }

    override fun toString(): String {
        return "OrderEventLayout(order=$order, isSorted=$isSorted)"
    }
}