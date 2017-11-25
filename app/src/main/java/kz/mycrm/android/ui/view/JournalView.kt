package kz.mycrm.android.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import kz.mycrm.android.R

/**
 * Created by Nurbek Kabylbay on 24.11.2017.
 */
class JournalView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var mBackgroundPaint: Paint
    private lateinit var mAccentTimePaint: Paint
    private lateinit var mNormalTimePaint: Paint
    private lateinit var mNormalSeparatorPaint: Paint
    private lateinit var mDashedSeparatorPaint: Paint

    // Attributes and default values
    private var mTextMarginTop = 100
    private var mViewPaddingTop = 0
    private var mViewPaddingRight = 0
    private var mBackgroundColor = Color.rgb(225, 218, 255)
    private var mSeparatorColor = Color.rgb(204, 198, 226)
    private var mNormalTimeColor = Color.rgb(204, 198, 226)
    private var mAccentTimeColor = Color.rgb(106, 105, 110)
    private var mNormalTimeSize = 14
    private var mAccentTimeSize = 18

    private val accentRect = Rect()
    private val normalRect = Rect()

    // Differences between normal and accented time text
    private var mHeightDiff = 0
    private var mWidthDiff = 0

    // Global variables used as View's height and width
    private var mScreenWidth = 0
    private var mScreenHeight = 0

    init {

        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        mScreenWidth = displayMetrics.widthPixels


        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.JournalView, 0 , 0)
        try {
            mBackgroundColor = a.getColor(R.styleable.JournalView_backgroundColor, mBackgroundColor)
            mAccentTimeColor = a.getColor(R.styleable.JournalView_accentTimeColor, mAccentTimeColor)
            mNormalTimeColor = a.getColor(R.styleable.JournalView_normalTimeColor, mNormalTimeColor)
            mSeparatorColor = a.getColor(R.styleable.JournalView_separatorColor, mSeparatorColor)
            mViewPaddingTop = a.getDimensionPixelSize(R.styleable.JournalView_viewPaddingTop, mViewPaddingTop)
            mViewPaddingRight = a.getDimensionPixelSize(R.styleable.JournalView_viewPaddingRight, mViewPaddingRight)
            mNormalTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_normalTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mNormalTimeSize.toFloat(), context.resources.displayMetrics).toInt())
            mAccentTimeSize = a.getDimensionPixelSize(R.styleable.JournalView_accentTimeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mAccentTimeSize.toFloat(), context.resources.displayMetrics).toInt())
        } finally {
            a.recycle()
        }

        viewInit()
    }

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
        mDashedSeparatorPaint.strokeWidth = 1f // TODO: add default value


        // Time accented
        mAccentTimePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mAccentTimePaint.color = mAccentTimeColor
        mAccentTimePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mAccentTimePaint.textSize = mAccentTimeSize.toFloat()
        mAccentTimePaint.getTextBounds("09:00", 0, "09:00".length, accentRect)

        mTextMarginTop = accentRect.height()*2
        mViewPaddingRight = mTextMarginTop/2
        mViewPaddingTop = mTextMarginTop/2


        // Time normal
        mNormalTimePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mNormalTimePaint.color = mNormalTimeColor
        mNormalTimePaint.textSize = mNormalTimeSize.toFloat()
        mNormalTimePaint.getTextBounds("09:00", 0, "09:00".length, normalRect)

        mHeightDiff = (accentRect.height() - normalRect.height())/2
        mWidthDiff = accentRect.width() - normalRect.width()
    }

//  mTimeTextHeight => textMarginTop
//  for records height = minutes%5 *(textMarginTop/2)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mScreenHeight = 145*mTextMarginTop + mViewPaddingTop

        this.setMeasuredDimension(mScreenWidth, mScreenHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Background
        canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),mBackgroundPaint)

        // Time texts
        drawTimes(canvas)

        // Lines and Axes
        drawLinesAndAxes(canvas)

    }

    private fun drawLinesAndAxes(canvas: Canvas) {
        val paddingLeft = (accentRect.width()+2*mViewPaddingRight).toFloat()
        canvas.drawLine(paddingLeft, mViewPaddingTop.toFloat(),
                paddingLeft,mScreenHeight.toFloat(), mNormalSeparatorPaint)

    }

    private fun drawTimes(canvas: Canvas) {
        var yy = accentRect.height().toFloat()
        for(i in 9..20) {
            var minute = 0
            yy += mTextMarginTop
            canvas.drawText(timeFormat(i, minute), mViewPaddingRight.toFloat(), yy, mAccentTimePaint)
            for(j in 1..11) {
                yy += mTextMarginTop + mHeightDiff
                minute += 5
                canvas.drawText(timeFormat(i, minute), (mWidthDiff+mViewPaddingRight.toFloat()), yy, mNormalTimePaint)
                yy += +mHeightDiff
            }
        }
        yy += mTextMarginTop
        canvas.drawText(timeFormat(21, 0), mViewPaddingRight.toFloat(), yy, mAccentTimePaint)
    }

    private fun timeFormat(hour:Int, minute:Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}

















