# HistogramView [![](https://jitpack.io/v/pwcong/HistogramView.svg)](https://jitpack.io/#pwcong/HistogramView)

A simple view to show data by histogram.

![SnapShot1](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot1.gif)

![SnapShot2](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot2.gif)

![SnapShot3](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot3.gif)
*******

# How To 

To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```	
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

```

## Step 2. Add the dependency
```
	dependencies {
	        compile 'com.github.pwcong:HistogramView:v1.0.1'
	}
	
```

*******

# Usage

Add it in your xml of Layout.

```
<me.pwcong.histogram.view.HistogramView
        app:textColor="#44615e"
        app:axesColor="#3e3740"
        app:axesTextSize="20sp"
        app:showHorizontalLine="true"
        <!- app:showLineChart="true" -->
        app:quantifierName="年龄/岁"
        app:arrowSize="15"
        app:strokeWidth="3"
        app:lineSpacing="20"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

Here is the list of attributes:

```
	<attr name="textColor" format="color"/>
        <attr name="quantifierName" format="string"/>
        <attr name="arrowSize" format="float"/>
        <attr name="axesColor" format="color"/>
        <attr name="axesTextSize" format="dimension"/>
        <attr name="showLineChart" format="boolean"/>
        <attr name="showHorizontalLine" format="boolean"/>
        <attr name="lineSpacing" format="float"/>
        <attr name="strokeWidth" format="float"/>

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

# Recently

## v1.0.1
增加折线和横线辅助显示功能，提供xml属性值修改。

## v1.0.0
简单的直方图数据显示View。



