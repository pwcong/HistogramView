package com.github.pwcong.histogramviewdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.pwcong.histogramview.entry.HistogramEntry
import com.github.pwcong.histogramview.view.HistogramView


class MainActivity : AppCompatActivity() {
    private var view: HistogramView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById<View>(R.id.view) as HistogramView
        view!!.setData(entries)
    }

    private val entries: List<HistogramEntry>
        get() {
            val entries = mutableListOf<HistogramEntry>()

            entries.add(HistogramEntry("小明", 18f))
            entries.add(HistogramEntry("小红", 9f))
            entries.add(HistogramEntry("小黑", 40f))
            entries.add(HistogramEntry("小花", 25f))
            entries.add(HistogramEntry("小黄", 33f))

            return entries
        }
}
