package com.github.pwcong.histogramview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.github.pwcong.histogramview.R
import com.github.pwcong.histogramview.entry.HistogramEntry
import kotlin.math.sin

class HistogramView : View {
    private var entries: List<HistogramEntry>? = null

    private var updated: Boolean = false

    private var paint: Paint? = null

    private var height: Int = 0
    private var width: Int = 0

    //柱子间距
    private var spacing: Float = 0f

    //字体大小
    private var textSize: Float = 0f

    //文字偏移量
    private var textOffset: Float = 0f

    //柱状图边距
    private var margin: Float = 0f

    /*  以下为可更改变量   */ //柱状图坐标轴箭头偏移量
    private var arrowSize: Float = 10.0f

    //柱状图坐标轴粗度
    private var strokeWidth: Float = 3.0f

    //坐标系量词
    private var quantifierName: String? = ""

    //坐标轴颜色
    private var axesColor: Int = Color.BLACK

    //坐标文字大小
    private var axesTextSize: Float = 25f

    //坐标轴文字颜色
    private var textColor: Int = Color.BLACK

    //显示折线
    private var showLineChart: Boolean = false

    //显示横线
    private var showHorizontalLine: Boolean = false

    //线条间距
    private var lineSpacing: Float = 12.0f


    //当前动作数值
    private var current: Long = 0

    //限制动作最大值
    private var increment: Long = 3

    //延迟时间
    private var delayTimes: Long = 10


    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        height = h
        width = w
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        height = MeasureSpec.getSize(heightMeasureSpec)
        width = MeasureSpec.getSize(widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        drawAxes(canvas)

        if (entries == null || entries!!.isEmpty()) return

        if (!updated) {
            initVariable()
            initData()
            updated = true
        }

        if (current < 90) {
            current += increment
            postInvalidateDelayed(delayTimes)
        } else {
            current = 90
            postInvalidateDelayed(delayTimes)
        }

        drawEntry(canvas)

        if (showLineChart) {
            drawLineChart(canvas)
        }

        if (showHorizontalLine) {
            drawHorizontalLine(canvas)
        }

        drawText(canvas)
        drawQuantifierName(canvas)
    }

    private fun drawAxes(canvas: Canvas) {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = axesColor
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = strokeWidth

        val line: Path = Path()

        line.moveTo(margin, margin)
        line.lineTo(margin, height - margin)
        line.lineTo(width - margin, height - margin)

        canvas.drawPath(line, paint!!)

        paint!!.style = Paint.Style.FILL

        val arrow: Path = Path()

        arrow.moveTo(margin, margin)
        arrow.lineTo(margin - arrowSize, margin + arrowSize)
        arrow.lineTo(margin, margin + arrowSize)

        arrow.moveTo(width - margin, height - margin)
        arrow.lineTo(width - margin - arrowSize, height - margin + arrowSize)
        arrow.lineTo(width - margin - arrowSize, height - margin)

        canvas.drawPath(arrow, paint!!)
    }

    private fun drawQuantifierName(canvas: Canvas) {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = textColor
        paint!!.textSize = axesTextSize

        canvas.drawText(quantifierName!!, 1.3f * margin, margin, paint!!)
    }

    private fun drawLineChart(canvas: Canvas) {
        val ratio = sin(Math.toRadians(current.toDouble())).toFloat()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = axesColor
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = strokeWidth * 0.5f

        paint!!.setPathEffect(DashPathEffect(floatArrayOf(lineSpacing, lineSpacing), 1f))


        val path: Path = Path()

        path.moveTo(
            (entries!![0].left + entries!![0].right) / 2,
            entries!![0].top + (height - margin - entries!![0].top) * (1 - 1.0f * ratio)
        )

        for (j in 1 until entries!!.size) {
            val entry = entries!![j]

            val pathX = (entry.left + entry.right) / 2
            val pathY = entry.top + (height - margin - entry.top) * (1 - 1.0f * ratio)

            path.lineTo(pathX, pathY)
        }

        canvas.drawPath(path, paint!!)
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        val ratio = sin(Math.toRadians(current.toDouble())).toFloat()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = axesColor
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = strokeWidth * 0.5f

        paint!!.setPathEffect(DashPathEffect(floatArrayOf(lineSpacing, lineSpacing), 1f))


        val path: Path = Path()

        for (i in entries!!.indices) {
            val entry = entries!![i]

            val pathX = entry.left
            val pathY = entry.top + (height - margin - entry.top) * (1 - 1.0f * ratio)

            path.moveTo(margin, pathY)
            path.lineTo(pathX, pathY)
        }

        canvas.drawPath(path, paint!!)
    }

    private fun drawText(canvas: Canvas) {
        val ratio = sin(Math.toRadians(current.toDouble())).toFloat()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = textColor
        paint!!.textSize = textSize

        for (entry in entries!!) {
            canvas.drawText(
                entry.name, entry.left, entry.bottom + 2 * (textOffset + strokeWidth), paint!!
            )

            val valueTop =
                entry.top + (height - margin - entry.top) * (1 - 1.0f * ratio) - textOffset

            canvas.drawText(entry.value.toString(), entry.left, valueTop, paint!!)
        }
    }

    private fun drawEntry(canvas: Canvas) {
        val ratio = sin(Math.toRadians(current.toDouble())).toFloat()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.FILL

        for (entry in entries!!) {
            paint!!.color = entry.color

            val rectTop = entry.top + (height - margin - entry.top) * (1 - 1.0f * ratio)

            canvas.drawRect(entry.left, rectTop, entry.right, entry.bottom, paint!!)
        }
    }


    private fun initAttrs(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HistogramView)

        textColor = typedArray.getColor(R.styleable.HistogramView_text_color, Color.BLACK)
        axesColor = typedArray.getColor(R.styleable.HistogramView_axes_color, Color.BLACK)
        axesTextSize = typedArray.getDimension(R.styleable.HistogramView_axes_text_size, 25f)
        quantifierName = typedArray.getString(R.styleable.HistogramView_quantifier_name)
        arrowSize = typedArray.getFloat(R.styleable.HistogramView_arrow_size, 10.0f)
        strokeWidth = typedArray.getFloat(R.styleable.HistogramView_stroke_width, 3.0f)
        showLineChart = typedArray.getBoolean(R.styleable.HistogramView_show_line_chart, false)
        showHorizontalLine =
            typedArray.getBoolean(R.styleable.HistogramView_show_horizontal_line, false)
        lineSpacing = typedArray.getFloat(R.styleable.HistogramView_line_spacing, 12.0f)

        typedArray.recycle()
    }

    private fun initVariable() {
        spacing = (40 - 4 * entries!!.size).toFloat()

        if (spacing < 4) {
            spacing = 4f
        }

        textSize = width / entries!!.size * 0.18f
        textOffset = textSize * 0.5f
        margin = textSize * 1.6f
    }

    private fun initData() {
        val eachWidth = (width - 2 * margin - (entries!!.size + 1) * spacing) / entries!!.size

        var max = 0f
        for (entry in entries!!) {
            if (max < entry.value) max = entry.value
        }

        val salt = Math.round(Math.random() * colors.size).toInt()

        for (i in entries!!.indices) {
            val entry = entries!![i]
            entry.color = colors[(i + salt) % colors.size]

            entry.left = margin + (i + 1) * spacing + i * eachWidth
            entry.top = height - margin - ((height - 3 * margin) * entry.value / max)
            entry.right = margin + (i + 1) * spacing + (i + 1) * eachWidth
            entry.bottom = height - margin - strokeWidth
        }
    }

    /**
     * 设置坐标系量词名称，即纵坐标关系名称
     * @param quantifierName String
     */
    fun setQuantifierName(quantifierName: String?) {
        this.quantifierName = quantifierName
    }

    /**
     * 设置展示数据，推荐数据个数大于2且小于8，此时能获得最佳效果
     * @param entries List<HistogramEntry>
    </HistogramEntry> */
    fun setData(entries: List<HistogramEntry>?) {
        this.entries = entries
        updated = false
        postInvalidate()
    }

    /**
     * 设置坐标轴线条粗细
     * @param strokeWidth float
     */
    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
    }

    /**
     * 设置动画帧延迟时间
     * @param delayTimes long
     */
    fun setDelayTimes(delayTimes: Long) {
        this.delayTimes = delayTimes
    }

    /**
     * 设置坐标轴箭头偏移量
     * @param arrowSize float
     */
    fun setArrowSize(arrowSize: Float) {
        this.arrowSize = arrowSize
    }

    /**
     * 设置字体颜色
     * @param textColor int
     */
    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    /**
     * 设置坐标轴线条颜色
     * @param axesColor int
     */
    fun setAxesColor(axesColor: Int) {
        this.axesColor = axesColor
    }

    /**
     * 设置坐标轴文字大小
     * @param axesTextSize float
     */
    fun setAxesTextSize(axesTextSize: Float) {
        this.axesTextSize = axesTextSize
    }

    /**
     * 是否显示折线
     * @param showLineChart boolean
     */
    fun setShowLineChart(showLineChart: Boolean) {
        this.showLineChart = showLineChart
    }

    /**
     * 是否显示横线
     * @param showHorizontalLine boolean
     */
    fun setShowHorizontalLine(showHorizontalLine: Boolean) {
        this.showHorizontalLine = showHorizontalLine
    }

    /**
     * 虚线间距
     * @param lineSpacing float
     */
    fun setLineSpacing(lineSpacing: Float) {
        this.lineSpacing = lineSpacing
    }


    companion object {
        var colors: IntArray = intArrayOf(
            -0xff4c79,
            -0x999a,
            -0xff6055,
            -0x1b00,
            -0x7e6d2a,
            -0x777778,
            -0xd60cb,
            -0x586b6a,
            -0x470878,
            -0x264c1a,
            -0x363545
        )
    }
}
