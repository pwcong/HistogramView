# HistogramView [![](https://jitpack.io/v/pwcong/HistogramView.svg)](https://jitpack.io/#pwcong/HistogramView)

A simple view to show data by histogram.

![SnapShot1](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot1.gif)

![SnapShot2](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot2.gif)

![SnapShot3](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot3.gif)
*******

# How To Install

see https://jitpack.io/#pwcong/HistogramView

*******

# Usage

Add it in your xml of Layout.

```
    <com.github.pwcong.histogramview.view.HistogramView
        android:id="@+id/view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:arrow_size="15"
        app:axes_color="#3e3740"
        app:axes_text_size="20sp"
        app:line_spacing="20"
        app:quantifier_name="年龄/岁"
        app:show_horizontal_line="true"
        app:stroke_width="3"
        app:text_color="#44615e" />
```

Here is the list of attributes:

```
    <declare-styleable name="HistogramView">
        <attr name="text_color" format="color" />
        <attr name="quantifier_name" format="string" />
        <attr name="arrow_size" format="float" />
        <attr name="axes_color" format="color" />
        <attr name="axes_text_size" format="dimension" />
        <attr name="show_line_chart" format="boolean" />
        <attr name="show_horizontal_line" format="boolean" />
        <attr name="line_spacing" format="float" />
        <attr name="stroke_width" format="float" />
    </declare-styleable>

```


And then add some datas. Especially that class of data must be `HistogramEntry` or other class extends `HistogramView`.

```
    ...
        
    view = (HistogramView) findViewById(R.id.view);
    view.setData(getEntries());

    private ArrayList<HistogramEntry> getEntries(){

        ArrayList<HistogramEntry> entries=new ArrayList<>();
    
        entries.add(new HistogramEntry("小明",18));
        entries.add(new HistogramEntry("小红",9));
        entries.add(new HistogramEntry("小黑",40));
        entries.add(new HistogramEntry("小花",25));
        entries.add(new HistogramEntry("小黄",33));
    
        return entries;
    
    }

```

*******
