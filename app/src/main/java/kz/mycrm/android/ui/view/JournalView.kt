package kz.mycrm.android.ui.view

import android.content.Context
import android.graphics.*
import android.support.v4.view.GestureDetectorCompat
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Nurbek Kabylbay on 24.11.2017.
 */
class JournalView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Paints of main elements and background
    private lateinit var mBackgroundPaint: Paint
    private lateinit var mAccentTimeTextPaint: Paint
    private lateinit var mNormalTimeTextPaint: Paint
    private lateinit var mNormalSeparatorPaint: Paint
    private lateinit var mDashedSeparatorPaint: Paint

    // Paints of Orders
    private lateinit var mOrderBackgroundPaint: Paint
    private lateinit var mOrderLeftAccentPaint: Paint
    private lateinit var mOrderTimeTextPaint: TextPaint
    private lateinit var mOrderPatientNamePaint: TextPaint
    private lateinit var mOrderPatientNumberPaint: TextPaint
    private lateinit var mOrderServiceListPaint: TextPaint

    // Attributes and default values
    private var mTextMarginTop = 100f
    private var mViewPaddingLeft = 0f
    private var mBackgroundColor = Color.rgb(225, 230, 239)
    private var mSeparatorColor = Color.rgb(115, 115, 115)
    private var mTimeTextColor = Color.rgb(106, 105, 110)
    private var mNormalTimeSize = 14
    private var mAccentTimeSize = 18
    private var mOrderBackgroundColor = Color.rgb(11, 121, 224)
    private var mOrderLeftAccentColor = Color.rgb(16, 88, 204)
    private var mOrderTextColor = Color.rgb(255, 255, 255)
    private var mOrderTimeTextSize = 14
    private var mOrderPatientNameSize = 18
    private var mOrderPatientNumberSize = 18
    private var mOrderServiceListSize = 14

    private val accentRect = Rect()
    private val normalRect = Rect()

    // Differences between normal and accented time text
    private var mHeightDiff = 0
    private var mWidthDiff = 0

    // Global variables used as View's height and width
    private var mScreenWidth = 0f
    private var mScreenHeight = 0f

    // Order events
    private var mOrderList: ArrayList<Order> = ArrayList()
    private var mOrderEventList: ArrayList<OrderEvent> = ArrayList()
    private var mRectStartX: Float = accentRect.width()+2 * mViewPaddingLeft
    private var mOrder5minHeight: Float = mTextMarginTop+accentRect.height()

    // Order Click handling
    private var mOrderEventClickListener: OrderEventClickListener? = null
    private var mGestureDetector: GestureDetectorCompat

    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if(mOrderEventClickListener != null && !mOrderEventList.isEmpty()) {
                for(event in mOrderEventList) {
                    if (e.x > event.rect.left && e.x < event.rect.right && e.y > event.rect.top && e.y < event.rect.bottom) {
                        mOrderEventClickListener!!.onOrderEventClicked(event.order)
                        playSoundEffect(SoundEffectConstants.CLICK)
                        return super.onSingleTapConfirmed(e)
                    }
                }
            }
            return super.onSingleTapConfirmed(e)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    init {

        mGestureDetector = GestureDetectorCompat(context, mGestureListener)

        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        mScreenWidth = displayMetrics.widthPixels.toFloat()

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.JournalView, 0 , 0)
        try {
            mBackgroundColor = a.getColor(R.styleable.JournalView_backgroundColor, mBackgroundColor)
            mTimeTextColor = a.getColor(R.styleable.JournalView_timeTextColor, mTimeTextColor)
            mSeparatorColor = a.getColor(R.styleable.JournalView_separatorColor, mSeparatorColor)
            mNormalTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_normalTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mNormalTimeSize.toFloat(), context.resources.displayMetrics).toInt())
            mAccentTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_accentTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mAccentTimeSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderTimeTextSize = a.getDimensionPixelSize(R.styleable.JournalView_orderTimeTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderTimeTextSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderPatientNameSize = a.getDimensionPixelSize(R.styleable.JournalView_orderPatientNameSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderPatientNameSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderPatientNumberSize = a.getDimensionPixelSize(R.styleable.JournalView_orderPatientNumberSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderPatientNumberSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderServiceListSize = a.getDimensionPixelSize(R.styleable.JournalView_orderServiceListSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderServiceListSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderBackgroundColor = a.getColor(R.styleable.JournalView_orderBackgroundColor, mOrderBackgroundColor)
            mOrderLeftAccentColor = a.getColor(R.styleable.JournalView_orderLeftAccentColor, mOrderLeftAccentColor)
            mOrderTextColor = a.getColor(R.styleable.JournalView_orderTextColor, mOrderTextColor)
        } finally {
            a.recycle()
        }
        viewInit()
    }

    /**
     * Initializes view models
     */
    private fun viewInit() {
        // Background
        mBackgroundPaint = Paint()
        mBackgroundPaint.color = mBackgroundColor

        // Separator lines
        mNormalSeparatorPaint = Paint()
        mNormalSeparatorPaint.color = mSeparatorColor
        mDashedSeparatorPaint = Paint()
        mDashedSeparatorPaint.color = mSeparatorColor
        mDashedSeparatorPaint.style = Paint.Style.STROKE
        mDashedSeparatorPaint.strokeWidth = 2f
        mDashedSeparatorPaint.pathEffect = DashPathEffect(floatArrayOf(5f, 10f), 0f)

        // Time accented
        mAccentTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAccentTimeTextPaint.color = this.mTimeTextColor
        mAccentTimeTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mAccentTimeTextPaint.textSize = mAccentTimeSize.toFloat()
        mAccentTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, accentRect)

        mTextMarginTop = accentRect.height()*2.toFloat()
        mViewPaddingLeft = mTextMarginTop/2

        // Time normal
        mNormalTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNormalTimeTextPaint.color = this.mTimeTextColor
        mNormalTimeTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mNormalTimeTextPaint.textSize = mNormalTimeSize.toFloat()
        mNormalTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, normalRect)
        mNormalTimeTextPaint.alpha = 120

        // Difference between normal and accented text
        mHeightDiff = (accentRect.height() - normalRect.height())/2
        mWidthDiff = accentRect.width() - normalRect.width()

        // ORDER
        // Background
        mOrderBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderBackgroundPaint.color = mOrderBackgroundColor
        mOrderBackgroundPaint.alpha = 180

        // Left accent line
        mOrderLeftAccentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderLeftAccentPaint.color = mOrderLeftAccentColor

        // Time
        mOrderTimeTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderTimeTextPaint.color = mOrderTextColor
        mOrderTimeTextPaint.textSize = mOrderTimeTextSize.toFloat()
        mOrderTimeTextPaint.alpha = 200

        // Name of patient
        mOrderPatientNamePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderPatientNamePaint.color = mOrderTextColor
        mOrderPatientNamePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mOrderPatientNamePaint.textSize = mOrderPatientNameSize.toFloat()

        // Number of patient
        mOrderPatientNumberPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderPatientNumberPaint.color = mOrderTextColor
        mOrderPatientNumberPaint.textSize = mOrderPatientNumberSize.toFloat()
        mOrderPatientNumberPaint.alpha = 200

        // List of services
        mOrderServiceListPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderServiceListPaint.color = mOrderTextColor
        mOrderServiceListPaint.textSize = mOrderServiceListSize.toFloat()
        mOrderServiceListPaint.alpha = 180

        // Left side of first at a row
        mRectStartX = accentRect.width() + 2*mViewPaddingLeft
        // Height of 5 minutes
        mOrder5minHeight = mTextMarginTop + accentRect.height()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mScreenHeight = 158*mTextMarginTop + 157*accentRect.height()
        this.setMeasuredDimension(mScreenWidth.toInt(), mScreenHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        // Background
        canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),mBackgroundPaint)

        // Time texts
        drawTimes(canvas)

        // Lines and Axes
        drawLinesAndAxes(canvas)

        // Order events
        drawEventOrders(canvas)
    }

    /**
     * Draws all orders in the mOrderList
     * @param canvas The canvas where to draw
     */
    private fun drawEventOrders(canvas: Canvas) {
        if(mOrderEventList.isEmpty())
            return
        for(orderEvent in mOrderEventList) {
            drawOrderEvent(canvas, orderEvent)
        }
    }

    /**
     * Draws a particular order. During the process it creates OrderEvent object with obtained
     * order Rect and adds it into mOrderEventList
     * @param canvas The canvas where to draw
     * @param order The order should be drawn
     */
    private fun drawOrderEvent(canvas: Canvas, orderEvent: OrderEvent) {
        // Order rect and Left Accent
        val orderLeft = orderEvent.rect.left.toFloat()
        val orderBottom = orderEvent.rect.bottom.toFloat()
        val orderTop = orderEvent.rect.top.toFloat()

        val accentRight = orderLeft + 10f

        canvas.drawRect(orderEvent.rect, mOrderBackgroundPaint)
        canvas.drawRect(orderLeft, orderTop, accentRight, orderBottom, mOrderLeftAccentPaint)

        var textX = orderEvent.rect.left + 30f
        var textY = 0f
        val marginTop = 30f
        var rect = Rect()
//        var text = orderEvent.order.start?.substring(11, 16) +" - "+ orderEvent.order.end?.substring(11, 16)

        val time = orderEvent.order.start?.substring(11, 16) + orderEvent.order.end?.substring(11, 16) //09:0009:30
        var text = ""

        try {
            if(time.isNotEmpty()) {
                text = time.substring(0, 2).toInt().toString()+":"+ time.substring(3, 5)+ " \u2014 " +
                            time.substring(5, 7).toInt() +":"+ time.substring(8)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        rect = getBoundedRect(text, mOrderTimeTextPaint, rect)
        textY = orderEvent.rect.top + rect.height() + marginTop
        drawOrderText(orderEvent.rect, text, textX, textY, mOrderTimeTextPaint, canvas)

        text = orderEvent.order.customerName ?: ""
        rect = getBoundedRect(text, mOrderPatientNamePaint, rect)
        textY += rect.height() + marginTop
        drawOrderText(orderEvent.rect, text, textX, textY, mOrderPatientNamePaint, canvas)

        if((orderEvent.rect.bottom-orderEvent.rect.top+1f)/mOrder5minHeight == 2f)
            return

        text = orderEvent.order.customerPhone ?: ""
        rect = getBoundedRect(text, mOrderPatientNumberPaint, rect)
        textY += rect.height() + marginTop
        drawOrderText(orderEvent.rect, text, textX, textY, mOrderPatientNumberPaint, canvas)

        if((orderEvent.rect.bottom-orderEvent.rect.top+1f)/mOrder5minHeight == 3f)
            return

        if(orderEvent.order.services != null) {
            text = ""
            for(s in orderEvent.order.services!!) {
                text += s.serviceName + ", "
            }

            text = text.substring(0, text.length-2)

            rect = getBoundedRect(text, mOrderPatientNamePaint, rect)
            textX = textX
            textY = orderEvent.rect.bottom.toFloat() -  rect.height()
            drawOrderText(orderEvent.rect, text, textX, textY, mOrderServiceListPaint, canvas)
        }
    }

    /**
     * The function that calculates where the top of an order should be. The journal starts from
     * 09:00, therefore, firstly, it calculates number of (5 minutes) between the start of the
     * journal and the start time of the order. The whole journal has padding as mTextMarginTop/2.
     * So sum the of mTextMarginTop/2 and number of (5 minutes) multiplied by the height of
     * (5 minute order) gives us the result
     * @param order The order the TOP should be calculated of
     * @return TOP value
     */
    private fun getOrderTop(order: Order?): Float {
        if(order == null)
            return mTextMarginTop/2
        // 2017-12-08 09:00:00
        val timeStart = "09:00:00" // Day starts from 09:00
        val timeEnd = order.start!!.substring(11)

        val dateStart: Date?
        val dateEnd: Date?
        var diffIn5minutes: Long = 0

        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        try {
            dateStart = format.parse(timeStart)
            dateEnd = format.parse(timeEnd)

            val difference = dateEnd.time - dateStart.time
            diffIn5minutes = difference/(1000*60) / 5
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if(diffIn5minutes == 0.toLong())
            return mTextMarginTop/2
        return mTextMarginTop/2 + diffIn5minutes*mOrder5minHeight
    }

    /**
     * The function that calculated where the bottom of an order should be. It calculates number of
     * (5 minutes) between start and end times of the order
     * @param order The order the BOTTOM should be calculated of
     * @param top The top value of the order
     * @return BOTTOM value
     */
    private fun getOrderBottom(order: Order?, top: Float) : Float {
        if(order == null)
            return mTextMarginTop/2
        // 2017-12-08 09:00
        val timeStart = order.start!!.substring(11)
        val timeEnd = order.end!!.substring(11)

        val dateStart: Date?
        val dateEnd: Date?
        var diffIn5minutes: Long = 0
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        try {
            dateStart = format.parse(timeStart)
            dateEnd = format.parse(timeEnd)

            val difference = dateEnd.time - dateStart.time
            diffIn5minutes = difference/(1000*60) / 5
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(diffIn5minutes.toInt() == 0)
            return top
        return top + diffIn5minutes* mOrder5minHeight - 1f // -1f gives a gap between two orders
    }

    /**
     * The function calculates where the left side of an order should start
     * @param order The order the LEFT should be calculated of
     * @return LEFT value
     */
    private fun getOrderLeft(order: Order?): Float {
        return mRectStartX
    }

    /**
     * The function calculates where the right side of an order should start
     * @param order The order the RIGHT should be calculated of
     * @return RIGHT value
     */
    private fun getOrderRight(order: Order?): Float {
        return mScreenWidth
    }

    /**
     * The function return a rect of with bounds of a text
     * @param text A text
     * @param paint A paint the text drawn with
     * @param rect A rect where the bound should be saved
     * @return The rect with bounds of the text
     */
    private fun getBoundedRect(text:String, paint: TextPaint, rect: Rect): Rect {
        paint.getTextBounds(text, 0, text.length, rect)
        return rect
    }

    /***
     * The function draws a text on an Order. The text is ellipsized at the end
     * @param orderRect The rect of the order
     * @param text The text to draw
     * @param textX X coordinate of the text
     * @param textY Y coordinate of the text
     * @param paint A paint the text will be drawn
     * @param canvas The canvas where the order is drawn
     */
    private fun drawOrderText(orderRect: Rect,text: String, textX: Float, textY: Float, paint: TextPaint, canvas: Canvas) {
        val widthDiff = 2*(textX - orderRect.left)
        val txt = TextUtils.ellipsize(text, mOrderTimeTextPaint, orderRect.width().toFloat()-widthDiff  , TextUtils.TruncateAt.END)
        canvas.drawText(txt, 0, txt.length, textX, textY, paint)
    }

    /**
     * The function draws lines and axes as separators of the view
     * @param canvas The main canvas of the view
     */
    private fun drawLinesAndAxes(canvas: Canvas) {
        val lineStartX = accentRect.width()+2 * mViewPaddingLeft
        val lineEndX = mScreenWidth

        canvas.drawLine(lineStartX, mTextMarginTop/2, lineStartX, mScreenHeight, mNormalSeparatorPaint)
        canvas.drawLine(lineStartX, mTextMarginTop/2, lineEndX, mTextMarginTop/2, mNormalSeparatorPaint)

        var yy = mTextMarginTop/2 + mTextMarginTop + accentRect.height()
        for(i in 1..157) {
            val path = Path()
                path.moveTo(lineStartX, yy)
                path.lineTo(lineEndX, yy)
            canvas.drawPath(path, mDashedSeparatorPaint)

            yy += mTextMarginTop + accentRect.height()
        }
    }

    /**
     * The function draws texts of times on the left side
     * @param canvas The main canvas of the view
     */
    private fun drawTimes(canvas: Canvas) {

        val marginTop = (mOrder5minHeight-accentRect.height())/2 //
        var yy = mTextMarginTop/2
        val start = mTextMarginTop/2
        val accentedTime = marginTop + accentRect.height()
        val normalTime = marginTop+ mHeightDiff + normalRect.height()
        var hourCounter = 0

        for(i in 9..21) {
            // 09:00 ACCENTED
            yy = start + hourCounter*mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 09:15
            yy = start + (hourCounter+3)*mOrder5minHeight + normalTime
            canvas.drawText(timeFormat(i, 15), (mWidthDiff + mViewPaddingLeft), yy, mNormalTimeTextPaint)

            // 09:30 ACCENTED
            yy = start + (hourCounter+6)*mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 30), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 09:45
            yy = start + (hourCounter+9)*mOrder5minHeight + normalTime
            canvas.drawText(timeFormat(i, 45), (mWidthDiff + mViewPaddingLeft), yy, mNormalTimeTextPaint)

            hourCounter += 12
        }
        // Last Accented hour 22:00
        yy += 2*marginTop + 2*mOrder5minHeight + accentRect.height()
        canvas.drawText(timeFormat(22, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)
    }

    /**
    * The function returns a string with right time format
     * @param hour The hour of the time
     * @param minute The minute of the time
     * @return The valid time format
    * */
    private fun timeFormat(hour:Int, minute:Int): String {
        return String.format("%2d:%02d", hour, minute)
    }

    /**
     * The function sets new values to mOrderList with the status of the new list and redraws the
     * whole view. If the status isSUCCESS, then a toast with number of orders is shown.
     * @param newOrderList A new OrderList
     * @param status The status of the new list
     */
    private var isUpdated = true
    fun updateEventsAndInvalidate(newOrderList: ArrayList<Order>, status: Status) {
        if(!isSameListWithOrigin(newOrderList, status)) {
            isUpdated = true
            this.mOrderList = newOrderList
            loadOrderEventRects(newOrderList)
            invalidate()
        }
        if(isUpdated && status == Status.SUCCESS) {
            Toast.makeText(context, "Количество записей: " + mOrderEventList.size, Toast.LENGTH_SHORT).show()
            isUpdated = false
        }
    }

    private fun loadOrderEventRects(newOrderList: ArrayList<Order>) {
        mOrderEventList.clear()
        for(order in newOrderList) {
            val orderLeft = getOrderLeft(order)
            val orderTop = getOrderTop(order)
            val orderRight = getOrderRight(order)
            val orderBottom = getOrderBottom(order, orderTop)
            val orderRect = Rect(orderLeft.toInt(), orderTop.toInt(), orderRight.toInt(), orderBottom.toInt())
            if(orderTop >= orderBottom) {
                continue
            }
            mOrderEventList.add(OrderEvent(orderRect, order))
        }
    }

    /**
     * The function compares a list with mOrderList
     * @param newOrderList A new list to compare
     * @return The new list is same with mOrderList or not
     */
    private fun isSameListWithOrigin(newOrderList: ArrayList<Order>, status: Status): Boolean {
        if(newOrderList.isEmpty() && mOrderList.isEmpty() && status == Status.LOADING)
            return true

        return newOrderList.any { mOrderList.contains(it) }
    }

    /** Setter for EventClickListener */
    fun setOnEventClickListener(listener: OrderEventClickListener) {
        mOrderEventClickListener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    /////////////////////////////////////////////////////
    ///                   ORDER EVENT                  //
    /////////////////////////////////////////////////////
    inner class OrderEvent(var rect: Rect, var order: Order)

    /////////////////////////////////////////////////////
    ///                   LISTENER                     //
    /////////////////////////////////////////////////////
    interface OrderEventClickListener {
        fun onOrderEventClicked(order: Order) {}
    }
}

















