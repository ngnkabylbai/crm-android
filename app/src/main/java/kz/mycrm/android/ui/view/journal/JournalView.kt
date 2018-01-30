package kz.mycrm.android.ui.view.journal

import android.content.Context
import android.graphics.*
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
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
    lateinit var mOrderBackgroundPaint: Paint
    lateinit var mOrderLeftAccentPaint: Paint

    // Attributes and default values
    private var mTextMarginTop = 100f
    private var mViewPaddingLeft = 0f
    private var mBackgroundColor = Color.rgb(232, 237, 240)
    private var mSeparatorColor = Color.rgb(196, 206, 210)
    private var mTimeTextColor = Color.rgb(30, 40, 46)
    private var mNormalTimeSize = DimensionConverter.pxToSp(10, context)
    private var mAccentTimeSize = DimensionConverter.pxToSp(12, context)
    private var mOrderBackgroundColor = Color.rgb(0, 74, 211)
//    private var mOrderLeftAccentColor = Color.rgb(16, 88, 204)
    var mOrderTextColor = Color.rgb(255, 255, 255)
    var mOrderTimeTextSize = 10
    var mOrderPatientNameSize = 13
    var mOrderPatientNumberSize = 12
    var mOrderServiceListSize = 11

    private val accentRect = Rect()
    private val normalRect = Rect()

    var marginTop: Float = 0f
    private var yy: Float = 0f
    private var start: Float = 0f
    private var accentedTime: Float = 0f
    private var normalTime: Float = 0f
    private var hourCounter = 0

    // Differences between normal and accented time text
    private var mHeightDiff = 0
    private var mWidthDiff = 0

    // Global variables used as View's height and width
    var mScreenWidth = 0f
    var mScreenHeight = 0f

    // Order events
    private var mOrderList: ArrayList<Order> = ArrayList()
    private var mOrderEventLayoutGroupList: ArrayList<OrderEventLayoutGroup> = ArrayList()
    private var mOrder5minHeight: Float = mTextMarginTop + accentRect.height()
    var mRectStartX: Float = accentRect.width() + 2 * mViewPaddingLeft

    // Order Click handling
    var mOrderEventClickListener: OrderEventClickListener? = null
    private var mGestureDetector: GestureDetectorCompat

    var isAnimating = false
    var isAnimatingFlag = false
    var mAnimatingEventLayoutGroup: OrderEventLayoutGroup? = null


    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (mOrderEventClickListener != null && !mOrderEventLayoutGroupList.isEmpty()) {
                for (eventGroup in mOrderEventLayoutGroupList) {
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
            mNormalTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_normalTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mNormalTimeSize, context.resources.displayMetrics).toInt()).toFloat()
            mAccentTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_accentTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mAccentTimeSize, context.resources.displayMetrics).toInt()).toFloat()
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
        mDashedSeparatorPaint.strokeWidth = DimensionConverter.dpToPx(1, context)
        mDashedSeparatorPaint.pathEffect = DashPathEffect(floatArrayOf(5f, 10f), 0f)

        // Time accented
        mAccentTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAccentTimeTextPaint.color = this.mTimeTextColor
        mAccentTimeTextPaint.textSize = mAccentTimeSize.toFloat()
        mAccentTimeTextPaint.getTextBounds("09:00", 0, "09:00".length, accentRect)

        mTextMarginTop = (accentRect.height() * 2.toFloat())/1.5f
        mViewPaddingLeft = mTextMarginTop / 2

        // Time normal
        mNormalTimeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNormalTimeTextPaint.color = this.mTimeTextColor
        mNormalTimeTextPaint.textSize = mNormalTimeSize
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

        // Left side of first at a row
        mRectStartX = accentRect.width() + 2 * mViewPaddingLeft
        // Height of 5 minutes
        mOrder5minHeight = mTextMarginTop + accentRect.height()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mScreenHeight = 171 * mTextMarginTop + 169 * accentRect.height() + mTextMarginTop
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
        if (mOrderEventLayoutGroupList.isEmpty())
            return
        for (orderEventGroup in mOrderEventLayoutGroupList) {
            orderEventGroup.drawOrderEvents(canvas)
        }
    }

    /**
     * Draws a particular order. During the process it creates OrderEventLayout object with obtained
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
        val timeStart = "08:00:00" // Day starts from 08:00
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
        // 2017-12-08 08:00
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
        for (i in 1..171) {
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

        for (i in 8..21) {
            // 08:00 ACCENTED
            yy = start + hourCounter * mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 0), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 08:15
            yy = start + (hourCounter + 3) * mOrder5minHeight + normalTime
            canvas.drawText(timeFormat(i, 15), (mWidthDiff + mViewPaddingLeft), yy, mNormalTimeTextPaint)

            // 08:30 ACCENTED
            yy = start + (hourCounter + 6) * mOrder5minHeight + accentedTime
            canvas.drawText(timeFormat(i, 30), mViewPaddingLeft, yy, mAccentTimeTextPaint)

            // 08:45
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

            mOrderEventLayoutGroupList = if(BuildConfig.MOCK) {
                getTestOrderEventGroups()
            } else {
                val mOrderEventList = getOrderEventRects(newOrderList)
                getOrderEventGroups(mOrderEventList)
            }

            invalidate()
        }
        if (isUpdated && status == Status.SUCCESS) {
            Toast.makeText(context, resources.getString(R.string.event_count)+": "+ mOrderEventLayoutGroupList.map{it.getGroupSize()}.sum(), Toast.LENGTH_SHORT).show()
            isUpdated = false
        }
    }

    // Test group of events
    private fun getTestOrderEventGroups(): ArrayList<OrderEventLayoutGroup> {
        val testOrderList = ArrayList<Order>()
        val list = ArrayList<Service>()
            for(i in 1..5)
                list.add(Service("1", "Повторное лечение"))

        for(i in 0..6) {
            val order = Order()
            order.id = i.toString()
            order.services = list
            order.customerName = "Иванов Иван Иванович Иванулы"
            order.customerPhone = "+7 456 312 21 45"
            testOrderList.add(order)
        }

        testOrderList[0].start = "2017-12-21 09:00:00"
        testOrderList[0].end = "2017-12-21 09:50:00"

        testOrderList[1].start = "2017-12-21 09:05:00"
        testOrderList[1].end = "2017-12-21 09:20:00"

        testOrderList[2].start = "2017-12-21 09:10:00"
        testOrderList[2].end = "2017-12-21 09:35:00"

        testOrderList[3].start = "2017-12-21 09:25:00"
        testOrderList[3].end = "2017-12-21 10:40:00"

        testOrderList[4].start = "2017-12-21 11:00:00"
        testOrderList[4].end = "2017-12-21 11:30:00"

        testOrderList[5].start = "2017-12-21 11:00:00"
        testOrderList[5].end = "2017-12-21 11:15:00"

        testOrderList[6].start = "2017-12-21 11:30:00"
        testOrderList[6].end = "2017-12-21 12:30:00"

        val eventList = getOrderEventRects(testOrderList)
        return getOrderEventGroups(eventList)
    }

    /** The function wraps list of Orders into OrderEvents
     * @param newOrderList A list of Orders
     * @return The list of OrderEvents
     * */
    private fun getOrderEventRects(newOrderList: ArrayList<Order>): ArrayList<OrderEventLayout> {
        val newOrderEventList = ArrayList<OrderEventLayout>()
        for (order in newOrderList) {
            val orderTop = getOrderTop(order)
            val orderBottom = getOrderBottom(order, orderTop)
            if (orderTop >= orderBottom) {
                continue
            }
            val orderRect = Rect(0, orderTop.toInt(), 0, orderBottom.toInt())
            newOrderEventList.add(OrderEventLayout(orderRect, order, this))
        }
        return newOrderEventList
    }

    /** The function groups a orderEventLayoutList of OrderEvents into OrderEventGroups
     * @param orderEventLayoutList A orderEventLayoutList of OrderEvents
     * @return The orderEventLayoutList of groups
     * */
    private fun getOrderEventGroups(orderEventLayoutList: ArrayList<OrderEventLayout>): ArrayList<OrderEventLayoutGroup> {

        val newOrderEventGroup = ArrayList<OrderEventLayoutGroup>()

        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Collections.sort(orderEventLayoutList, object : Comparator<OrderEventLayout> {
            override fun compare(first: OrderEventLayout, second: OrderEventLayout): Int {
                return datetimeFormat.parse(first.order.start).compareTo(datetimeFormat.parse(second.order.start))
            }
        })

        mOrderEventLayoutGroupList.clear()
        var currentGroup = ArrayList<OrderEventLayout>()

        if (orderEventLayoutList.isEmpty())
            return newOrderEventGroup

        currentGroup.add(orderEventLayoutList[0])
        orderEventLayoutList[0].isSorted = true
        var endDate = datetimeFormat.parse(orderEventLayoutList[0].order.end)

        for (j in 1..(orderEventLayoutList.size - 1)) {
            val startDate = datetimeFormat.parse(orderEventLayoutList[j].order.start)
            if (startDate.after(endDate) || startDate.equals(endDate)) {
                endDate = datetimeFormat.parse(orderEventLayoutList[j].order.end)
                newOrderEventGroup.add(OrderEventLayoutGroup(currentGroup,this@JournalView))
                currentGroup = ArrayList()
            }
            currentGroup.add(orderEventLayoutList[j])
            orderEventLayoutList[j].isSorted = true
        }
        if (currentGroup.isNotEmpty())
            newOrderEventGroup.add(OrderEventLayoutGroup(currentGroup, this@JournalView))

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
}
