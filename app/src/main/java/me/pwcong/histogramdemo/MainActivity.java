package me.pwcong.histogramdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import me.pwcong.histogram.model.HistogramEntry;
import me.pwcong.histogram.view.HistogramView;

public class MainActivity extends AppCompatActivity {

    HistogramView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view= (HistogramView) findViewById(R.id.view);
        view.setData(getEntries());


    }

    private ArrayList<HistogramEntry> getEntries(){

        ArrayList<HistogramEntry> entries=new ArrayList<>();

        entries.add(new HistogramEntry("小明",18));
        entries.add(new HistogramEntry("小红",9));
        entries.add(new HistogramEntry("小黑",40));
        entries.add(new HistogramEntry("小花",25));
        entries.add(new HistogramEntry("小黄",33));

        return entries;

    }

}
