# HistogramView [![](https://jitpack.io/v/pwcong/HistogramView.svg)](https://jitpack.io/#pwcong/HistogramView)

A simple view to show data by histogram.

![SnapShot](https://github.com/pwcong/SnapShot/blob/master/HistogramView/snapshot.gif)

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
	        compile 'com.github.pwcong:HistogramView:v1.0.0'
	}
	
```

*******

# Usage

Add it in your xml of Layout.

```
<me.pwcong.histogram.view.HistogramView
        app:textColor="#44615e"
        app:axesColor="#3e3740"
        android:id="@+id/view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

And then add some datas. Especially that class of data must be `HistogramEntry` or other class extends `HistogramView`.

```
        ...
        
        view = (HistogramView) findViewById(R.id.view);
        view.setData(getEntries());
        view.setAxesQuantifierName("年龄/岁");
  
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


