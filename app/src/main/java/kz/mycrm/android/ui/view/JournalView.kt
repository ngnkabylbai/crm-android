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
    private var mNormalTimeColor = Color.rgb(115, 115, 115)
    private var mAccentTimeColor = Color.rgb(106, 105, 110)
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
    private var mOrderTextMarginTop: Float = mTextMarginTop/2
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
            mAccentTimeColor = a.getColor(R.styleable.JournalView_accentTimeColor, mAccentTimeColor)
            mNormalTimeColor = a.getColor(R.styleable.JournalView_normalTimeColor, mNormalTimeColor)
            mSeparatorColor = a.getColor(R.styleable.JournalView_separatorColor, mSeparatorColor)
            mNormalTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_normalTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mNormalTimeSize.toFloat(), context.resources.displayMetrics).toInt())
            mAccentTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_accentTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mAccentTimeSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderTimeTextSize = a.getDimensionPixelSize(R.styleable.JournalView_mOrderTimeTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderTimeTextSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderPatientNameSize = a.getDimensionPixelSize(R.styleable.JournalView_mOrderPatientNameSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderPatientNameSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderPatientNumberSize = a.getDimensionPixelSize(R.styleable.JournalView_mOrderPatientNumberSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderPatientNumberSize.toFloat(), context.resources.displayMetrics).toInt())
            mOrderServiceListSize = a.getDimensionPixelSize(R.styleable.JournalView_mOrderServiceListSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mOrderServiceListSize.toFloat(), context.resources.displayMetrics).toInt())
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
        mAccentTimeTextPaint.color = mAccentTimeColor
        mAccentTimeTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mAccentTimeTextPaint.textSize = mAccentTimeSize.toFloat()
        mAccentTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, accentRect)

        mTextMarginTop = accentRect.height()*2.toFloat()
        mViewPaddingLeft = mTextMarginTop/2

        // Time normal
        mNormalTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNormalTimeTextPaint.color = mNormalTimeColor
        mNormalTimeTextPaint.textSize = mNormalTimeSize.toFloat()
        mNormalTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, normalRect)

        // Difference between normal and accented text
        mHeightDiff = (accentRect.height() - normalRect.height())/2
        mWidthDiff = accentRect.width() - normalRect.width()

        // Order
        mOrderBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderBackgroundPaint.color = mOrderBackgroundColor
        mOrderBackgroundPaint.alpha = 180

        mOrderLeftAccentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderLeftAccentPaint.color = mOrderLeftAccentColor

        mOrderTimeTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderTimeTextPaint.color = mOrderTextColor
        mOrderTimeTextPaint.textSize = mOrderTimeTextSize.toFloat()

        mOrderPatientNamePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderPatientNamePaint.color = mOrderTextColor
        mOrderPatientNamePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mOrderPatientNamePaint.textSize = mOrderPatientNameSize.toFloat()

        mOrderPatientNumberPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderPatientNumberPaint.color = mOrderTextColor
        mOrderPatientNumberPaint.textSize = mOrderPatientNumberSize.toFloat()

        mOrderServiceListPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderServiceListPaint.color = mOrderTextColor
        mOrderServiceListPaint.textSize = mOrderServiceListSize.toFloat()

        mRectStartX = accentRect.width()+2 * mViewPaddingLeft
        mOrderTextMarginTop = mTextMarginTop/2
        mOrder5minHeight = mTextMarginTop+accentRect.height()
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
        if(mOrderList.isEmpty())
            return
        for(orderEvent in mOrderList) {
            drawOrderEvent(canvas, orderEvent)
        }
    }

    /**
     * Draws a particular order. During the process it creates OrderEvent object with obtained
     * order Rect and adds it into mOrderEventList
     * @param canvas The canvas where to draw
     * @param order The order should be drawn
     */
    private fun drawOrderEvent(canvas: Canvas, order: Order?) {
        if(order == null)
            return
        // Order rect and Left Accent
        val orderLeft = getOrderLeft(order)
        val orderTop = getOrderTop(order)
        val orderRight = getOrderRight(order)
        val orderBottom = getOrderBottom(order, orderTop)
        val orderRect = Rect(orderLeft.toInt(), orderTop.toInt(), orderRight.toInt(), orderBottom.toInt())

        mOrderEventList.add(OrderEvent(orderRect, order))

        val accentRight = orderLeft + 10f

        canvas.drawRect(orderRect, mOrderBackgroundPaint)
        canvas.drawRect(orderLeft, orderTop, accentRight, orderBottom, mOrderLeftAccentPaint)

        var textX = 0f
        var textY = 0f
        val marginTop = 20f
        var rect = Rect()
        var text = order.start?.substring(11, 16) +" - "+ order.end?.substring(11, 16)

        rect = getBoundedRect(text, mOrderTimeTextPaint, rect)
        textX += orderRect.left + 15f
        textY = orderRect.top + rect.height() + marginTop
        drawOrderText(orderRect, text, textX, textY, mOrderTimeTextPaint, canvas)

        text = order.customer?.lastname + " " + order.customer?.name
        rect = getBoundedRect(text, mOrderPatientNamePaint, rect)
        textX = textX
        textY += rect.height() + marginTop
        drawOrderText(orderRect, text, textX, textY, mOrderPatientNamePaint, canvas)

        text = order.customer?.phone ?: ""
        rect = getBoundedRect(text, mOrderPatientNumberPaint, rect)
        textX = textX
        textY += rect.height() + marginTop
        drawOrderText(orderRect, text, textX, textY, mOrderPatientNumberPaint, canvas)

        if(order.services != null) {
            text = ""
            for(s in order.services!!) {
                text += s.serviceName + " "
            }

            rect = getBoundedRect(text, mOrderPatientNamePaint, rect)
            textX = textX
            textY = orderRect.bottom.toFloat() -  rect.height()
            drawOrderText(orderRect, text, textX, textY, mOrderServiceListPaint, canvas)
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

        val format = SimpleDateFormat("hh:mm:ss", Locale.getDefault())

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
        val format = SimpleDateFormat("hh:mm:ss", Locale.getDefault())

        try {
            dateStart = format.parse(timeStart)
            dateEnd = format.parse(timeEnd)

            val difference = dateEnd.time - dateStart.time
            diffIn5minutes = difference/(1000*60) / 5
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        var yy = 0f
        for(i in 9..22) {
            var minute = 0
            yy += mTextMarginTop + accentRect.height().toFloat()
            canvas.drawText(timeFormat(i, minute), mViewPaddingLeft, yy, mAccentTimeTextPaint)
            for(j in 1..11) {
                minute += 5
                if(minute == 30) {
                    yy += mTextMarginTop + accentRect.height().toFloat()
                    canvas.drawText(timeFormat(i, minute), mViewPaddingLeft, yy, mAccentTimeTextPaint)
                } else {
                    yy += mTextMarginTop + mHeightDiff + normalRect.height()
                    canvas.drawText(timeFormat(i, minute), (mWidthDiff+ mViewPaddingLeft), yy, mNormalTimeTextPaint)
                    yy += mHeightDiff
                }
            }
        }
        yy += mTextMarginTop
        canvas.drawText(timeFormat(21, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)
    }

    /**
    * The function returns a string with right time format
     * @param hour The hour of the time
     * @param minute The minute of the time
     * @return The valid time format
    * */
    private fun timeFormat(hour:Int, minute:Int): String {
        return String.format("%02d:%02d", hour, minute)
    }

    /**
     * The function sets new values to mOrderList with the status of the new list and redraws the
     * whole view. If the status isSUCCESS, then a toast with number of orders is shown.
     * @param newOrderList A new OrderList
     * @param status The status of the new list
     */
    fun updateEventsAndInvalidate(newOrderList: ArrayList<Order>?, status: Status) {
        if(!isSameListWithOrigin(newOrderList)) {
            this.mOrderList = newOrderList!!
            invalidate()
            if(status == Status.SUCCESS)
                Toast.makeText(context, "Количество записей: " + mOrderList.size, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * The function compares a list with mOrderList
     * @param newOrderList A new list to compare
     * @return The new list is same with mOrderList or not
     */
    private fun isSameListWithOrigin(newOrderList: ArrayList<Order>?): Boolean {
        if(newOrderList == null || newOrderList.isEmpty())
            return true
        if(mOrderList.isEmpty())
            return false

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

















