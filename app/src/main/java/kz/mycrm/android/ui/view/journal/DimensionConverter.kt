package kz.mycrm.android.ui.view.journal

import android.content.Context

/**
 * Created by Nurbek Kabylbay on 21.01.2018.
 */
object DimensionConverter {

    fun pxToSp(px: Int, context: Context): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }

    fun dpToPx(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.scaledDensity
    }
}