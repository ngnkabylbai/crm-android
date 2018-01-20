package kz.mycrm.android.ui.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.v4.view.GestureDetectorCompat
import android.text.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Nurbek Kabylbay on 24.11.2017.
 */
class JournalView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val displayMetrics = DisplayMetrics()

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
    private var mBackgroundColor = Color.rgb(232, 237, 240)
    private var mSeparatorColor = Color.rgb(196, 206, 210)
    private var mTimeTextColor = Color.rgb(30, 40, 46)
    private var mNormalTimeSize = 10
    private var mAccentTimeSize = 12
    private var mOrderBackgroundColor = Color.rgb(0, 74, 211)
//    private var mOrderLeftAccentColor = Color.rgb(16, 88, 204)
    private var mOrderTextColor = Color.rgb(255, 255, 255)
    private var mOrderTimeTextSize = 12
    private var mOrderPatientNameSize = 16
    private var mOrderPatientNumberSize = 16
    private var mOrderServiceListSize = 12

    private val accentRect = Rect()
    private val normalRect = Rect()

    private var marginTop: Float = 0f
    private var yy: Float = 0f
    private var start: Float = 0f
    private var accentedTime: Float = 0f
    private var normalTime: Float = 0f
    private var hourCounter = 0

    // Differences between normal and accented time text
    private var mHeightDiff = 0
    private var mWidthDiff = 0

    // Global variables used as View's height and width
    private var mScreenWidth = 0f
    private var mScreenHeight = 0f

    // Order events
    private var mOrderList: ArrayList<Order> = ArrayList()
    private var mOrderEventGroupList: ArrayList<OrderEventGroup> = ArrayList()
    private var mRectStartX: Float = accentRect.width() + 2 * mViewPaddingLeft
    private var mOrder5minHeight: Float = mTextMarginTop + accentRect.height()

    // Order Click handling
    private var mOrderEventClickListener: OrderEventClickListener? = null
    private var mGestureDetector: GestureDetectorCompat

    private val animator = ValueAnimator()

    private var isAnimating = false
    private var isAnimatingFlag = false
    private var mAnimatingEventGroup: OrderEventGroup? = null


    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (mOrderEventClickListener != null && !mOrderEventGroupList.isEmpty()) {
                for (eventGroup in mOrderEventGroupList) {
                    if (e.x > eventGroup.groupRect.left && e.x < eventGroup.groupRect.right && e.y > eventGroup.groupRect.top && e.y < eventGroup.groupRect.bottom) {
                        eventGroup.onOrderEventGroupClicked(e)
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
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        mScreenWidth = displayMetrics.widthPixels.toFloat()

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.JournalView, 0, 0)
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
//            mOrderLeftAccentColor = a.getColor(R.styleable.JournalView_orderLeftAccentColor, mOrderLeftAccentColor)
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
//        mAccentTimeTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mAccentTimeTextPaint.textSize = mAccentTimeSize.toFloat()
        mAccentTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, accentRect)

        mTextMarginTop = (accentRect.height() * 2.toFloat())/2
        mViewPaddingLeft = mTextMarginTop / 2

        // Time normal
        mNormalTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNormalTimeTextPaint.color = this.mTimeTextColor
//        mNormalTimeTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mNormalTimeTextPaint.textSize = mNormalTimeSize.toFloat()
        mNormalTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, normalRect)
        mNormalTimeTextPaint.alpha = 100

        // Difference between normal and accented text
        mHeightDiff = (accentRect.height() - normalRect.height()) / 2
        mWidthDiff = accentRect.width() - normalRect.width()

        // ORDER
        // Background
        mOrderBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderBackgroundPaint.color = mOrderBackgroundColor
        mOrderBackgroundPaint.alpha = 180

        // Left accent line
        mOrderLeftAccentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOrderLeftAccentPaint.color = mOrderBackgroundColor

        // Time
        mOrderTimeTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderTimeTextPaint.color = mOrderTextColor
        mOrderTimeTextPaint.textSize = mOrderTimeTextSize.toFloat()
        mOrderTimeTextPaint.alpha = 200

        // Name of patient
        mOrderPatientNamePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOrderPatientNamePaint.color = mOrderTextColor
//        mOrderPatientNamePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
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
        mRectStartX = accentRect.width() + 2 * mViewPaddingLeft
        // Height of 5 minutes
        mOrder5minHeight = mTextMarginTop + accentRect.height()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mScreenHeight = 158 * mTextMarginTop + 157 * accentRect.height() + mTextMarginTop
        this.setMeasuredDimension(mScreenWidth.toInt(), mScreenHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        // Background
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), mBackgroundPaint)

        // Time texts
        drawTimes(canvas)

        // Lines and Axes
        drawLinesAndAxes(canvas)

        // Order events
        drawEventOrders(canvas)

        if (isAnimatingFlag) {
            isAnimating = false
            isAnimatingFlag = false
        }
    }

    /**
     * Draws all orders in the mOrderList
     * @param canvas The canvas where to draw
     */
    private fun drawEventOrders(canvas: Canvas) {
//        if(mOrderEventList.isEmpty())
//            return
//        for(orderEvent in mOrderEventList) {
//            drawOrderEvent(canvas, orderEvent)
//        }

        if (mOrderEventGroupList.isEmpty())
            return
        for (orderEventGroup in mOrderEventGroupList) {
            orderEventGroup.drawOrderEvents(canvas)
        }
    }

    /**
     * Draws a particular order. During the process it creates OrderEvent object with obtained
     * order Rect and adds it into mOrderEventList
     * @param canvas The canvas where to draw
     * @param order The order should be drawn
     */
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
        if (order == null)
            return mTextMarginTop*2
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
            diffIn5minutes = difference / (1000 * 60) / 5
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (diffIn5minutes == 0.toLong())
            return mTextMarginTop*2
        return mTextMarginTop*2 + diffIn5minutes * mOrder5minHeight
    }

    /**
     * The function that calculated where the bottom of an order should be. It calculates number of
     * (5 minutes) between start and end times of the order
     * @param order The order the BOTTOM should be calculated of
     * @param top The top value of the order
     * @return BOTTOM value
     */
    private fun getOrderBottom(order: Order?, top: Float): Float {
        if (order == null)
            return mTextMarginTop / 2
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
            diffIn5minutes = difference / (1000 * 60) / 5
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (diffIn5minutes.toInt() == 0)
            return top
        return top + diffIn5minutes * mOrder5minHeight - 1f // -1f gives a gap between two orders
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
    private fun drawOrderText(layout: DynamicLayout, textX: Float, textY: Float, canvas: Canvas) {
        canvas.save()
        canvas.translate(textX, textY)
        layout.draw(canvas)
        canvas.restore()

//        val txt = TextUtils.ellipsize(text, mOrderTimeTextPaint, orderRect.width().toFloat() - widthDiff, TextUtils.TruncateAt.END)
//        canvas.drawText(txt, 0, txt.length, textX, textY, paint)
    }

    /**
     * The function draws lines and axes as separators of the view
     * @param canvas The main canvas of the view
     */
    private fun drawLinesAndAxes(canvas: Canvas) {
        val lineStartX = accentRect.width() + 2 * mViewPaddingLeft
        val lineEndX = mScreenWidth

        canvas.drawLine(lineStartX, mTextMarginTop*2, lineStartX, mScreenHeight, mNormalSeparatorPaint)
        canvas.drawLine(lineStartX, mTextMarginTop*2, lineEndX, mTextMarginTop*2, mNormalSeparatorPaint)

        var yy = mTextMarginTop*2 + mTextMarginTop + accentRect.height()
        for (i in 1..157) {
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

        marginTop = (mOrder5minHeight - accentRect.height()) / 2 //
        start = mTextMarginTop*2
        accentedTime = marginTop + accentRect.height()
        normalTime = marginTop + mHeightDiff + normalRect.height()
        hourCounter = 0

        for (i in 9..21) {
            // 09:00 ACCENTED
            yy = start + hourCounter * mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 09:15
            yy = start + (hourCounter + 3) * mOrder5minHeight + normalTime
            canvas.drawText(timeFormat(i, 15), (mWidthDiff + mViewPaddingLeft), yy, mNormalTimeTextPaint)

            // 09:30 ACCENTED
            yy = start + (hourCounter + 6) * mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 30), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 09:45
            yy = start + (hourCounter + 9) * mOrder5minHeight + normalTime
            canvas.drawText(timeFormat(i, 45), (mWidthDiff + mViewPaddingLeft), yy, mNormalTimeTextPaint)

            hourCounter += 12
        }
        // Last Accented hour 22:00
        yy += 2 * marginTop + 2 * mOrder5minHeight + accentRect.height()
        canvas.drawText(timeFormat(22, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)
    }

    /**
     * The function returns a string with right time format
     * @param hour The hour of the time
     * @param minute The minute of the time
     * @return The valid time format
     * */
    private fun timeFormat(hour: Int, minute: Int): String {
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

        if (!isSameListWithOrigin(newOrderList, status)) {
            isUpdated = true
            mOrderList = newOrderList

            mOrderEventGroupList = if(BuildConfig.MOCK) {
                getTestOrderEventGroups()
            } else {
                val mOrderEventList = getOrderEventRects(newOrderList)
                getOrderEventGroups(mOrderEventList)
            }

            invalidate()
        }
        if (isUpdated && status == Status.SUCCESS) {
            Toast.makeText(context, resources.getString(R.string.event_count)+": "+mOrderEventGroupList.map{it.getGroupSize()}.sum(), Toast.LENGTH_SHORT).show()
            isUpdated = false
        }
    }

    // Test group of events
    private fun getTestOrderEventGroups(): ArrayList<OrderEventGroup> {
        val testOrderList = ArrayList<Order>()
        val list = ArrayList<Service>()
        var order = Order()
        order.id = "1"
        order.start = "2017-12-21 09:00:00"
        order.end = "2017-12-21 09:50:00"
        order.services = list
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        testOrderList.add(order)

        order = Order()
        order.id = "2"
        order.start = "2017-12-21 09:05:00"
        order.end = "2017-12-21 09:20:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        order = Order()
        order.id = "3"
        order.start = "2017-12-21 09:10:00"
        order.end = "2017-12-21 09:35:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        order = Order()
        order.id = "4"
        order.start = "2017-12-21 09:25:00"
        order.end = "2017-12-21 10:40:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        order = Order()
        order.id = "5"
        order.start = "2017-12-21 11:00:00"
        order.end = "2017-12-21 12:00:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        order = Order()
        order.id = "6"
        order.start = "2017-12-21 11:00:00"
        order.end = "2017-12-21 11:30:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        order = Order()
        order.id = "7"
        order.start = "2017-12-21 12:00:00"
        order.end = "2017-12-21 12:30:00"
        order.customerName = "Artur Abdalimov"
        order.customerPhone = "+7 456 312 21 45"
        order.services = list
        testOrderList.add(order)

        val eventList = getOrderEventRects(testOrderList)
        return getOrderEventGroups(eventList)
    }

    /** The function wraps list of Orders into OrderEvents
     * @param newOrderList A list of Orders
     * @return The list of OrderEvents
     * */
    private fun getOrderEventRects(newOrderList: ArrayList<Order>): ArrayList<OrderEvent> {
        val newOrderEventList = ArrayList<OrderEvent>()
        for (order in newOrderList) {
            val orderTop = getOrderTop(order)
            val orderBottom = getOrderBottom(order, orderTop)
            if (orderTop >= orderBottom) {
                continue
            }
            val orderRect = Rect(0, orderTop.toInt(), 0, orderBottom.toInt())
            newOrderEventList.add(OrderEvent(orderRect, order))
        }
        return newOrderEventList
    }

    /** The function groups a orderEventList of OrderEvents into OrderEventGroups
     * @param orderEventList A orderEventList of OrderEvents
     * @return The orderEventList of groups
     * */
    private fun getOrderEventGroups(orderEventList: ArrayList<OrderEvent>): ArrayList<OrderEventGroup> {

        val newOrderEventGroup = ArrayList<OrderEventGroup>()

        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Collections.sort(orderEventList, object : Comparator<OrderEvent> {
            override fun compare(first: OrderEvent, second: OrderEvent): Int {
                return datetimeFormat.parse(first.order.start).compareTo(datetimeFormat.parse(second.order.start))
            }
        })

        mOrderEventGroupList.clear()
        var currentGroup = ArrayList<OrderEvent>()

        if (orderEventList.isEmpty())
            return newOrderEventGroup

        currentGroup.add(orderEventList[0])
        orderEventList[0].isSorted = true
        var endDate = datetimeFormat.parse(orderEventList[0].order.end)

        for (j in 1..(orderEventList.size - 1)) {
            val startDate = datetimeFormat.parse(orderEventList[j].order.start)
            if (startDate.after(endDate) || startDate.equals(endDate)) {
                endDate = datetimeFormat.parse(orderEventList[j].order.end)
                newOrderEventGroup.add(OrderEventGroup(currentGroup))
                currentGroup = ArrayList()
            }
            currentGroup.add(orderEventList[j])
            orderEventList[j].isSorted = true
        }
        if (currentGroup.isNotEmpty())
            newOrderEventGroup.add(OrderEventGroup(currentGroup))

        return newOrderEventGroup
    }

    /**
     * The function compares a list with mOrderList
     * @param newOrderList A new list to compare
     * @return The new list is same with mOrderList or not
     */
    private fun isSameListWithOrigin(newOrderList: ArrayList<Order>, status: Status): Boolean {
        if (newOrderList.isEmpty() && mOrderList.isEmpty() && status == Status.LOADING)
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
    inner class OrderEvent(var rect: Rect, var order: Order) {
        var isSorted = false

        override fun toString(): String {
            return "OrderEvent(order=$order, isSorted=$isSorted)"
        }
    }

    /////////////////////////////////////////////////////
    ///                   ORDER EVENT GROUP            //
    /////////////////////////////////////////////////////
    inner class OrderEventGroup(private var orderEventList: ArrayList<OrderEvent>) {

        private var isInitializing = true // if the group is drawn for the first time
        private var expandedEvent: OrderEvent? = null // reference to the expanded event

        val groupRect = Rect()
        private var lessenRect = Rect()

        init {
            mOrderTimeTextPaint.getTextBounds("15:20 \u2014 16:20", 0,"15:20 \u2014 16:20".length, lessenRect)

            groupRect.right = mScreenWidth.toInt()
            groupRect.left = mRectStartX.toInt()
            groupRect.top = orderEventList[0].rect.top
            groupRect.bottom = groupRect.top
            for(event in orderEventList) {
                if(event.rect.bottom > groupRect.bottom)
                    groupRect.bottom = event.rect.bottom
            }
        }

        private var lessenOrderWidth = (mScreenWidth - mRectStartX) / 8
        private var fullOrderWidth = mScreenWidth - mRectStartX
        private var orderLeft = mRectStartX

        private var orderRight = mScreenWidth - lessenOrderWidth * (orderEventList.size - 1)
        private var expandedOrderWidth = (fullOrderWidth - lessenOrderWidth * (orderEventList.size - 1)).toInt()

        fun drawOrderEvents(canvas: Canvas) {
            if (expandedOrderWidth < 0)
                return

            expandedEvent = expandedEvent ?: orderEventList[0]
            for (i in 0..(orderEventList.size - 1)) {
                // Order rect and Left Accent
                val orderEvent = orderEventList[i]

                val curOrderRight: Int
                val curOrderLeft: Int

                if (isAnimating) {
                    curOrderRight = orderEvent.rect.right
                    curOrderLeft = orderEvent.rect.left
                }
// else if(orderEventList.size == 2) {
//                    curOrderLeft = (mRectStartX +(fullOrderWidth/2)*i).toInt() + i*1
//                    curOrderRight = (mRectStartX +(fullOrderWidth/2)*(i+1)).toInt()
//                }
                    else {
                    curOrderRight = getCurOrderRight(i)
                    curOrderLeft = getCurOrderLeft(i, curOrderRight)
                }

                orderEvent.rect.left = curOrderLeft
                orderEvent.rect.right = curOrderRight

                val orderBottom = orderEvent.rect.bottom.toFloat()
                val orderTop = orderEvent.rect.top.toFloat()
                val orderLeft = orderEvent.rect.left.toFloat()

                val accentRight = curOrderLeft + 10f

                canvas.drawRect(orderEvent.rect, mOrderBackgroundPaint)
                canvas.drawRect(curOrderLeft.toFloat(), orderTop, accentRight, orderBottom, mOrderLeftAccentPaint)

                if(this == mAnimatingEventGroup)
                    continue

                if(orderEvent != expandedEvent)
                    continue

                var textY = orderTop + marginTop
                var textX = orderLeft + 3*marginTop
                val marginTop = 30f

                val time = orderEvent.order.start?.substring(11, 16) + orderEvent.order.end?.substring(11, 16) //09:0009:30
                var text = ""

                try {
                    if (time.isNotEmpty()) {
                        text = time.substring(0, 2).toInt().toString() + ":" + time.substring(3, 5) + " \u2014 " +
                                time.substring(5, 7).toInt() + ":" + time.substring(8)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val timeDynamicLayout = DynamicLayout(text.trim(), mOrderTimeTextPaint,
                        orderEvent.rect.width() - marginTop.toInt(),
                        Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true)

                drawOrderText(timeDynamicLayout, textX, textY, canvas)

                textY = orderTop+timeDynamicLayout.height+marginTop/2
                textX = orderLeft + marginTop
                text = orderEvent.order.customerName ?: ""

                val nameDynamicLayout = DynamicLayout(text.trim(), mOrderPatientNamePaint,
                        orderEvent.rect.width() - marginTop.toInt(),
                        Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true)


                if(textY + nameDynamicLayout.height > (orderBottom-marginTop/2))
                    continue
                drawOrderText(nameDynamicLayout, textX, textY, canvas)

                textY = orderTop+timeDynamicLayout.height+nameDynamicLayout.height+marginTop
                textX = orderLeft + marginTop
                text = orderEvent.order.customerPhone ?: ""

                val phoneDynamicLayout = DynamicLayout(text.trim(), mOrderPatientNamePaint,
                        orderEvent.rect.width() - marginTop.toInt(),
                        Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true)

                if(textY + phoneDynamicLayout.height > (orderBottom-marginTop/2))
                    continue
                drawOrderText(phoneDynamicLayout, textX, textY, canvas)

                if (orderEvent.order.services.isNotEmpty()) {
                    text = ""
                    for (s in orderEvent.order.services) {
                        text += s.serviceName + ", "
                    }
                    text = text.substring(0, text.length - 2)

                    val serviceDynamicLayout = DynamicLayout(text.trim(), mOrderServiceListPaint,
                            orderEvent.rect.width() - marginTop.toInt(),
                            Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true)

                    textY = orderBottom - serviceDynamicLayout.height.toFloat() - marginTop/2
                    textX = orderLeft + marginTop

                    if(textY + phoneDynamicLayout.height > (orderBottom-marginTop/2))
                        continue
                    drawOrderText(serviceDynamicLayout, textX, textY, canvas)
                }
            }
            isInitializing = false
        }

        private fun getCurOrderLeft(position: Int, curOrderRight: Int): Int {
            val result: Float

            if (isInitializing && position == 0)
                result = orderLeft
            else if (isInitializing && position > 0)
                result = curOrderRight - lessenOrderWidth
            else
                result = orderEventList[position].rect.left.toFloat()

            return result.toInt()
        }

        private fun getCurOrderRight(position: Int): Int {
            val result: Float

            if (isInitializing && position == 0)
                result = orderRight
            else if (isInitializing && position > 0)
                result = orderRight + position * lessenOrderWidth
            else
                result = orderEventList[position].rect.right.toFloat()

            return result.toInt()
        }

        fun onOrderEventGroupClicked(e: MotionEvent) {
            if (!orderEventList.isEmpty()) {
                if(isAnimating)
                    return
                for (i in 0..(orderEventList.size - 1)) {
                    val event = orderEventList[i]
                    if (e.x > event.rect.left && e.x < event.rect.right && e.y > event.rect.top && e.y < event.rect.bottom) {
//                        if(orderEventList.size == 2){
//                            mOrderEventClickListener?.onOrderEventClicked(event.order)
//                            return
//                        }
                        if (event == expandedEvent) {
                            mOrderEventClickListener?.onOrderEventClicked(event.order)
                            mAnimatingEventGroup = null
                        } else if (orderEventList.indexOf(expandedEvent) > i) {
                            getAnimator(i, event, event.rect.right.toFloat(),
                                    (event.rect.left + expandedOrderWidth).toFloat()).start()
                            mAnimatingEventGroup = this
                        } else {
                            getAnimator(i, event, event.rect.left.toFloat(),
                                    mRectStartX + i * lessenOrderWidth).start()
                            mAnimatingEventGroup = this
                        }

                        playSoundEffect(SoundEffectConstants.CLICK)
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
        lateinit var curEvent: OrderEvent
        lateinit var nextEvent: OrderEvent
        private fun getAnimator(position: Int, event: OrderEvent, start: Float, end: Float): Animator {
            val expanding = ValueAnimator.ofFloat(start, end)

            expanding.duration = 500
            expanding.addUpdateListener { valueAnimator ->
                if(orderEventList.indexOf(expandedEvent) < position) {
                    event.rect.left = (valueAnimator.animatedValue as Float).toInt()
                    for(i in 0..(orderEventList.size-2)){ // iterating from left to right
                         curEvent = orderEventList[i]
                         nextEvent = orderEventList[i+1]
                        if(curEvent == event)
                            continue
                        if(curEvent == expandedEvent) {
                            curEvent.rect.right = (curEvent.rect.left + expandedOrderWidth) - (start - event.rect.left).toInt()
                            nextEvent.rect.left = curEvent.rect.right
                            continue
                        }
                        curEvent.rect.right = curEvent.rect.left + lessenOrderWidth.toInt()
                        nextEvent.rect.left = curEvent.rect.right
                    }
                }
                else {
                    event.rect.right = (valueAnimator.animatedValue as Float).toInt()
                    for(i in (orderEventList.size-1) downTo 1){ // iterating from right to left
                        curEvent = orderEventList[i]
                        nextEvent = orderEventList[i-1]
                        if(curEvent == event)
                            continue
                        if(curEvent == expandedEvent) {
                            curEvent.rect.left = (curEvent.rect.right - expandedOrderWidth) + (event.rect.right - start).toInt()
                            nextEvent.rect.right = curEvent.rect.left
                            continue
                        }
                        curEvent.rect.left = curEvent.rect.right - lessenOrderWidth.toInt()
                        nextEvent.rect.right = curEvent.rect.left
                    }
                }
                this@JournalView.invalidate()
            }
            expanding.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    expandedEvent = event
                    isAnimatingFlag = true
                    mAnimatingEventGroup = null
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                    isAnimating = true
                }
            })

            return expanding
        }

        fun getGroupSize(): Int {
            return orderEventList.size
        }
    }


    /////////////////////////////////////////////////////
    ///                   LISTENER                     //
    /////////////////////////////////////////////////////
    interface OrderEventClickListener {
        fun onOrderEventClicked(order: Order) {}
    }
}

















