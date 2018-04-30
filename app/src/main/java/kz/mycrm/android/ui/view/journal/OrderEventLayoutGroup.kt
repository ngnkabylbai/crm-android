package kz.mycrm.android.ui.view.journal

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextUtils
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Nurbek Kabylbay on 21.01.2018.
 */
class OrderEventLayoutGroup(private var orderEventLayoutList: ArrayList<OrderEventLayout>,
                            private val journalView: JournalView) {

    private var isInitializing = true // if the group is drawn for the first time
    private var expandedEventLayout: OrderEventLayout? = null // reference to the expanded event

    private var lessenOrderWidth = (journalView.mScreenWidth - journalView.mRectStartX) / 8
    private var fullOrderWidth = journalView.mScreenWidth - journalView.mRectStartX
    private var orderLeft = journalView.mRectStartX

    private var orderRight = journalView.mScreenWidth - lessenOrderWidth * (orderEventLayoutList.size - 1)
    private var expandedOrderWidth = (fullOrderWidth - lessenOrderWidth * (orderEventLayoutList.size - 1)).toInt()

    val groupRect = Rect()

    init {

        groupRect.right = journalView.mScreenWidth.toInt()
        groupRect.left = journalView.mRectStartX.toInt()
        groupRect.top = orderEventLayoutList[0].rect.top
        groupRect.bottom = groupRect.top
        for(event in orderEventLayoutList) {
            if(event.rect.bottom > groupRect.bottom)
                groupRect.bottom = event.rect.bottom
        }
    }

    fun drawOrderEvents(canvas: Canvas) {
        if (expandedOrderWidth < 0)
            return

        expandedEventLayout = expandedEventLayout ?: orderEventLayoutList[0]
        for (i in 0..(orderEventLayoutList.size - 1)) {
            // Order rect and Left Accent
            val orderEventLayout = orderEventLayoutList[i]

            val curOrderRight: Int
            val curOrderLeft: Int

            if (journalView.isAnimating) {
                curOrderRight = orderEventLayout.rect.right
                curOrderLeft = orderEventLayout.rect.left
            }  else if(orderEventLayoutList.size == 2) {
                    curOrderLeft = (orderLeft +(fullOrderWidth/2)*i).toInt() + i*1
                    curOrderRight = (orderLeft +(fullOrderWidth/2)*(i+1)).toInt()
            } else {
                curOrderRight = getCurOrderRight(i)
                curOrderLeft = getCurOrderLeft(i, curOrderRight)
            }

            orderEventLayout.rect.left = curOrderLeft
            orderEventLayout.rect.right = curOrderRight

            val orderBottom = orderEventLayout.rect.bottom.toFloat()
            val orderTop = orderEventLayout.rect.top.toFloat()
            val orderLeft = orderEventLayout.rect.left.toFloat()

            val accentWidth = DimensionConverter.dpToPx(3, journalView.context)
            val accentRight = curOrderLeft + accentWidth

            canvas.drawRect(orderEventLayout.rect, journalView.mOrderBackgroundPaint)
            canvas.drawRect(curOrderLeft.toFloat(), orderTop, accentRight, orderBottom, journalView.mOrderLeftAccentPaint)

            if(this == journalView.mAnimatingEventLayoutGroup)
                continue

            if(orderEventLayout != expandedEventLayout && orderEventLayoutList.size != 2 )
                continue

            if (!orderEventLayout.isInitialized) {
                // TODO: Create proper layout params !?
                val p = if(orderEventLayoutList.size == 2)
                        LinearLayout.LayoutParams(expandedOrderWidth/2-40, WRAP_CONTENT)
                    else
                        LinearLayout.LayoutParams(expandedOrderWidth-40, ViewGroup.LayoutParams.WRAP_CONTENT)

                orderEventLayout.timeTextView.layoutParams = p
                orderEventLayout.nameTextView.layoutParams = p
                orderEventLayout.phoneTextView.layoutParams = p
                orderEventLayout.serviceTextView.layoutParams = p

                val time = orderEventLayout.order.start?.substring(11, 16) + orderEventLayout.order.end?.substring(11, 16) //09:0009:30
                var text = ""

                try {
                    if (time.isNotEmpty()) {
                        text = time.substring(0, 2).toInt().toString() + ":" + time.substring(3, 5) + " \u2014 " +
                                time.substring(5, 7).toInt() + ":" + time.substring(8)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                orderEventLayout.timeTextView.visibility = setTextAndGetViewVisibility(orderEventLayout.timeTextView, text)
                orderEventLayout.nameTextView.visibility = setTextAndGetViewVisibility(orderEventLayout.nameTextView,
                        orderEventLayout.order.customerName ?: "")
                orderEventLayout.phoneTextView.visibility = setTextAndGetViewVisibility(orderEventLayout.phoneTextView,
                        orderEventLayout.order.customerPhone ?: "")

                text = ""
                if (orderEventLayout.order.services.isNotEmpty()) {
                    for (s in orderEventLayout.order.services) {
                        text += s.serviceName + ", "
                    }
                    text = text.substring(0, text.length - 2)

                    orderEventLayout.serviceTextView.visibility = setTextAndGetViewVisibility(orderEventLayout.serviceTextView, text)
                }

                measureAndLayout(orderEventLayout)

//                var b = orderEventLayout.nameTextView.bottom
//                var c = orderEventLayout.phoneTextView.bottom
//                var d = orderEventLayout.serviceTextView.bottom
//                var z = orderEventLayout.bottom

                if(orderEventLayout.nameTextView.bottom + orderEventLayout.mPaddingBottom > orderEventLayout.bottom) {
                    val maxLines = getMaxLineNumber(orderEventLayout.nameTextView)
                    when(maxLines) {
                        -1,0 -> orderEventLayout.nameTextView.visibility = View.GONE
                        else -> {
                            orderEventLayout.nameTextView.maxLines = maxLines
                            orderEventLayout.nameTextView.ellipsize = TextUtils.TruncateAt.END
                        }
                    }
                }

                if(orderEventLayout.phoneTextView.bottom + orderEventLayout.mPaddingBottom > orderEventLayout.bottom) {
                    orderEventLayout.phoneTextView.visibility = View.GONE
                }

                if(orderEventLayout.serviceTextView.bottom + orderEventLayout.mPaddingBottom > orderEventLayout.bottom) {
                    val maxLines = getMaxLineNumber(orderEventLayout.serviceTextView)
                    when(maxLines) {
                        -1,0 -> orderEventLayout.serviceTextView.visibility = View.GONE
                        else -> {
                            orderEventLayout.serviceTextView.maxLines = maxLines
                            orderEventLayout.serviceTextView.ellipsize = TextUtils.TruncateAt.END
                        }
                    }
                } else {
                    val marginTop = orderEventLayout.bottom - orderEventLayout.phoneTextView.bottom
                        - orderEventLayout.serviceTextView.height
                    val params = orderEventLayout.serviceTextView.layoutParams as LinearLayout.LayoutParams
                    params.topMargin = marginTop
                    orderEventLayout.serviceTextView.layoutParams = params
                }

                orderEventLayout.isInitialized = true
            }

            drawLayout(orderEventLayout, canvas,orderLeft+accentWidth, orderTop)
        }
        isInitializing = false
    }

    private fun setTextAndGetViewVisibility(view: TextView, text: String): Int {
        view.text = text
        return if(text.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun drawLayout(layout: OrderEventLayout, canvas: Canvas, dx: Float, dy: Float) {
        canvas.save()
        canvas.translate(dx, dy)
        layout.draw(canvas)
        canvas.restore()
    }

    private fun measureAndLayout(orderEventLayout: OrderEventLayout) {
            orderEventLayout.measure(orderEventLayout.rect.width(), orderEventLayout.rect.height())
            orderEventLayout.layout(0,0, orderEventLayout.rect.width(), orderEventLayout.rect.height())
    }

    private fun getMaxLineNumber(view: TextView): Int {
        val parentLayout = view.parent as LinearLayout
        var counter = view.lineCount
        while (true) {
            counter--
            if (counter <= 0){
                return -1
            } else if (view.top + counter*view.lineHeight <= parentLayout.bottom - parentLayout.paddingBottom) {
                return counter
            }
        }
    }

    private fun getCurOrderLeft(position: Int, curOrderRight: Int): Int {
        val result: Float

        if (isInitializing && position == 0)
            result = orderLeft
        else if (isInitializing && position > 0)
            result = curOrderRight - lessenOrderWidth
        else
            result = orderEventLayoutList[position].rect.left.toFloat()

        return result.toInt()
    }

    private fun getCurOrderRight(position: Int): Int {
        val result: Float

        if (isInitializing && position == 0)
            result = orderRight
        else if (isInitializing && position > 0)
            result = orderRight + position * lessenOrderWidth
        else
            result = orderEventLayoutList[position].rect.right.toFloat()

        return result.toInt()
    }

    fun onOrderEventGroupClicked(e: MotionEvent) {
        if (!orderEventLayoutList.isEmpty()) {
            if(journalView.isAnimating)
                return
            for (i in 0..(orderEventLayoutList.size - 1)) {
                val event = orderEventLayoutList[i]
                if (e.x > event.rect.left && e.x < event.rect.right && e.y > event.rect.top && e.y < event.rect.bottom) {
                        if(orderEventLayoutList.size == 2){
                            journalView.mOrderEventClickListener?.onOrderEventClicked(event.order)
                            return
                        }
                    if (event == expandedEventLayout) {
                        journalView.mOrderEventClickListener?.onOrderEventClicked(event.order)
                        journalView.mAnimatingEventLayoutGroup = null
                    } else if (orderEventLayoutList.indexOf(expandedEventLayout) > i) {
                        getAnimator(i, event, event.rect.right.toFloat(),
                                (event.rect.left + expandedOrderWidth).toFloat()).start()
                        journalView.mAnimatingEventLayoutGroup = this
                    } else {
                        getAnimator(i, event, event.rect.left.toFloat(),
                                journalView.mRectStartX + i * lessenOrderWidth).start()
                        journalView.mAnimatingEventLayoutGroup = this
                    }

                    journalView.playSoundEffect(SoundEffectConstants.CLICK)
                }
            }
        }
    }

    /** First of all, If you are compelled to read and understand this... then, I'm so sorry...
     * The function changes rects in the group to make an animation of expanding and closing
     * @param event an event should be expanded
     * @param position position of the event
     * @param start A start value
     * @param end An end value
     * @return A ValueAnimator
     * */
    lateinit var curEventLayout: OrderEventLayout
    lateinit var nextEventLayout: OrderEventLayout
    private fun getAnimator(position: Int, eventLayout: OrderEventLayout, start: Float, end: Float): Animator {
        val expanding = ValueAnimator.ofFloat(start, end)

        expanding.duration = 680
        expanding.addUpdateListener { valueAnimator ->
            if(orderEventLayoutList.indexOf(expandedEventLayout) < position) {
                eventLayout.rect.left = (valueAnimator.animatedValue as Float).toInt()
                for(i in 0..(orderEventLayoutList.size-2)){ // iterating from left to right
                    curEventLayout = orderEventLayoutList[i]
                    nextEventLayout = orderEventLayoutList[i+1]
                    if(curEventLayout == eventLayout)
                        continue
                    if(curEventLayout == expandedEventLayout) {
                        curEventLayout.rect.right = (curEventLayout.rect.left + expandedOrderWidth) - (start - eventLayout.rect.left).toInt()
                        nextEventLayout.rect.left = curEventLayout.rect.right
                        continue
                    }
                    curEventLayout.rect.right = curEventLayout.rect.left + lessenOrderWidth.toInt()
                    nextEventLayout.rect.left = curEventLayout.rect.right
                }
            }
            else {
                eventLayout.rect.right = (valueAnimator.animatedValue as Float).toInt()
                for(i in (orderEventLayoutList.size-1) downTo 1){ // iterating from right to left
                    curEventLayout = orderEventLayoutList[i]
                    nextEventLayout = orderEventLayoutList[i-1]
                    if(curEventLayout == eventLayout)
                        continue
                    if(curEventLayout == expandedEventLayout) {
                        curEventLayout.rect.left = (curEventLayout.rect.right - expandedOrderWidth) + (eventLayout.rect.right - start).toInt()
                        nextEventLayout.rect.right = curEventLayout.rect.left
                        continue
                    }
                    curEventLayout.rect.left = curEventLayout.rect.right - lessenOrderWidth.toInt()
                    nextEventLayout.rect.right = curEventLayout.rect.left
                }
            }
            journalView.invalidate()
        }
        expanding.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                expandedEventLayout = eventLayout
                journalView.isAnimatingFlag = true
                journalView.mAnimatingEventLayoutGroup = null
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
                journalView.isAnimating = true
            }
        })

        return expanding
    }

    fun getGroupSize(): Int {
        return orderEventLayoutList.size
    }
}