package me.pwcong.histogram.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import me.pwcong.histogram.R;
import me.pwcong.histogram.model.HistogramEntry;

/**
 * Created by pwcong on 2016/9/8.
 */
public class HistogramView extends View {

    public static int[] colors={0xff00b387,0xffff6666,0xff009fab,0xffffe500,0xff8192d6,0xff888888,0xfff29f35,0xffa79496,0xffb8f788,0xffd9b3e6,0xffc9cabb};

    ArrayList<HistogramEntry> entries;

    boolean updated         =   false;

    Paint paint;

    int height;
    int width;

    //柱子间距
    float spacing;
    //字体大小
    float textSize;
    //文字偏移量
    float textOffset;
    //柱状图边距
    float margin;

    /*  以下为可更改变量   */

    //柱状图坐标轴箭头偏移量
    float arrowSize             =   10.0f;
    //柱状图坐标轴粗度
    float strokeWidth           =   3.0f;
    //坐标系量词
    String quantifierName       =   "";
    //坐标轴颜色
    int axesColor               =   Color.BLACK;
    //坐标文字大小
    float axesTextSize          =   25;
    //坐标轴文字颜色
    int textColor               =   Color.BLACK;

    //显示折线
    boolean showLineChart       =   false;
    //显示横线
    boolean showHorizontalLine  =   false;
    //线条间距
    float lineSpacing           =   12.0f;


    //当前时间
    long currentTimes=0;
    //延迟时间
    long delayTimes=10;
    //动画总时间
    long totalTimes=400;


    public HistogramView(Context context) {
        super(context);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height=h;
        width=w;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height=MeasureSpec.getSize(heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawAxes(canvas);

        if(entries==null||entries.isEmpty())
            return;

        if(!updated){
            initVariable();
            initData();
            updated=true;
        }

        if(currentTimes<totalTimes){

            currentTimes+=delayTimes;
            postInvalidateDelayed(delayTimes);

        }

        drawEntry(canvas);

        if(showLineChart){
            drawLineChart(canvas);
        }

        if(showHorizontalLine){
            drawHorizontalLine(canvas);
        }

        drawText(canvas);
        drawQuantifierName(canvas);

    }

    private void drawAxes(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(axesColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        Path line=new Path();

        line.moveTo(margin,margin);
        line.lineTo(margin,height-margin);
        line.lineTo(width-margin,height-margin);

        canvas.drawPath(line,paint);

        paint.setStyle(Paint.Style.FILL);

        Path arrow=new Path();

        arrow.moveTo(margin,margin);
        arrow.lineTo(margin- arrowSize,margin+ arrowSize);
        arrow.lineTo(margin,margin+ arrowSize);

        arrow.moveTo(width-margin,height-margin);
        arrow.lineTo(width-margin- arrowSize,height-margin+ arrowSize);
        arrow.lineTo(width-margin- arrowSize,height-margin);

        canvas.drawPath(arrow,paint);

    }

    private void drawQuantifierName(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(textColor);
        paint.setTextSize(axesTextSize);

        canvas.drawText(quantifierName,1.3f*margin,margin,paint);

    }

    private void drawLineChart(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(axesColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth*0.5f);

        paint.setPathEffect(new DashPathEffect(new float[]{lineSpacing,lineSpacing},1));


        Path path=new Path();

        path.moveTo((entries.get(0).getLeft()+entries.get(0).getRight())/2,
                entries.get(0).getTop()+(height-margin-entries.get(0).getTop())*(1-1.0f*currentTimes/totalTimes));

        for(int j=1;j<entries.size();j++) {

            HistogramEntry entry = entries.get(j);

            float pathX = (entry.getLeft() + entry.getRight()) / 2;
            float pathY = entry.getTop() + (height - margin - entry.getTop()) * (1 - 1.0f * currentTimes / totalTimes);

            path.lineTo(pathX, pathY);
        }

        canvas.drawPath(path,paint);


    }

    private void drawHorizontalLine(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(axesColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth*0.5f);

        paint.setPathEffect(new DashPathEffect(new float[]{lineSpacing,lineSpacing},1));


        Path path=new Path();

        for(int i=0;i<entries.size();i++) {

            HistogramEntry entry = entries.get(i);

            float pathX = entry.getLeft();
            float pathY = entry.getTop() + (height - margin - entry.getTop()) * (1 - 1.0f * currentTimes / totalTimes);

            path.moveTo(margin,pathY);
            path.lineTo(pathX, pathY);
        }

        canvas.drawPath(path,paint);

    }

    private void drawText(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        for (HistogramEntry entry:entries){

            canvas.drawText(entry.getName(),entry.getLeft(),entry.getBottom()+2*(textOffset+strokeWidth),paint);

            float valueTop=entry.getTop()+(height-margin-entry.getTop())*(1-1.0f*currentTimes/totalTimes)-textOffset;

            canvas.drawText(String.valueOf(entry.getValue()),entry.getLeft(),valueTop,paint);

        }

    }

    private void drawEntry(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        for(HistogramEntry entry:entries){

            paint.setColor(entry.getColor());

            float rectTop=entry.getTop()+(height-margin-entry.getTop())*(1-1.0f*currentTimes/totalTimes);

            canvas.drawRect(entry.getLeft(),rectTop,entry.getRight(),entry.getBottom(),paint);

        }

    }


    private void initAttrs(AttributeSet attributeSet){

        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.HistogramView);

        textColor = typedArray.getColor(R.styleable.HistogramView_textColor, Color.BLACK);
        axesColor = typedArray.getColor(R.styleable.HistogramView_axesColor,Color.BLACK);
        axesTextSize = typedArray.getDimension(R.styleable.HistogramView_axesTextSize,25);
        quantifierName = typedArray.getString(R.styleable.HistogramView_quantifierName);
        arrowSize = typedArray.getFloat(R.styleable.HistogramView_arrowSize,10.0f);
        strokeWidth = typedArray.getFloat(R.styleable.HistogramView_strokeWidth,3.0f);
        showLineChart = typedArray.getBoolean(R.styleable.HistogramView_showLineChart,false);
        showHorizontalLine = typedArray.getBoolean(R.styleable.HistogramView_showHorizontalLine,false);
        lineSpacing = typedArray.getFloat(R.styleable.HistogramView_lineSpacing,12.0f);




    }

    private void initVariable(){

        spacing=40-4*entries.size();

        if(spacing<4){
            spacing=4;
        }

        textSize=width/entries.size()*0.18f;
        textOffset=textSize*0.5f;
        margin=textSize*1.6f;

    }

    private void initData(){

        float eachWidth=(width-2*margin-(entries.size()+1)*spacing)/entries.size();

        float max=0;
        for(HistogramEntry entry:entries){
            if(max<entry.getValue())
                max=entry.getValue();
        }

        int salt = (int) Math.round(Math.random() * colors.length);

        for(int i=0;i<entries.size();i++){

            HistogramEntry entry = entries.get(i);
            entry.setColor(colors[(i+salt)%colors.length]);

            entry.setLeft(margin+(i+1)*spacing+i*eachWidth);
            entry.setTop(height-margin-((height-3*margin)*entry.getValue()/max));
            entry.setRight(margin+(i+1)*spacing+(i+1)*eachWidth);
            entry.setBottom(height-margin-strokeWidth);

        }

    }

    /**
     * 设置坐标系量词名称，即纵坐标关系名称
     * @param quantifierName String
     */
    public void setQuantifierName(String quantifierName){
        this.quantifierName=quantifierName;
    }

    /**
     * 设置展示数据，推荐数据个数大于2且小于8，此时能获得最佳效果
     * @param entries ArrayList<HistogramEntry>
     */
    public void setData(ArrayList<HistogramEntry> entries){
        this.entries=entries;
        updated=false;
        postInvalidate();
    }

    /**
     * 设置坐标轴线条粗细
     * @param strokeWidth float
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    /**
     * 设置动画帧延迟时间
     * @param delayTimes long
     */
    public void setDelayTimes(long delayTimes) {
        this.delayTimes = delayTimes;
    }

    /**
     * 设置动画总时间
     * @param totalTimes long
     */
    public void setTotalTimes(long totalTimes) {
        this.totalTimes = totalTimes;
    }

    /**
     * 设置坐标轴箭头偏移量
     * @param arrowSize float
     */
    public void setArrowSize(float arrowSize) {
        this.arrowSize = arrowSize;
    }

    /**
     * 设置字体颜色
     * @param textColor int
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置坐标轴线条颜色
     * @param axesColor int
     */
    public void setAxesColor(int axesColor) {
        this.axesColor = axesColor;
    }

    /**
     * 设置坐标轴文字大小
     * @param axesTextSize float
     */
    public void setAxesTextSize(float axesTextSize) {
        this.axesTextSize = axesTextSize;
    }

    /**
     * 是否显示折线
     * @param showLineChart boolean
     */
    public void setShowLineChart(boolean showLineChart) {
        this.showLineChart = showLineChart;
    }

    /**
     * 是否显示横线
     * @param showHorizontalLine boolean
     */
    public void setShowHorizontalLine(boolean showHorizontalLine) {
        this.showHorizontalLine = showHorizontalLine;
    }

    /**
     * 虚线间距
     * @param lineSpacing float
     */
    public void setLineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
    }




}
