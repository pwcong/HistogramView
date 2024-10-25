package com.github.pwcong.histogramview.entry

class HistogramEntry(var name: String, var value: Float) {
    var color: Int = 0
    var left: Float = 0f
    var top: Float = 0f
    var right: Float = 0f
    var bottom: Float = 0f

    override fun toString(): String {
        return "HistogramEntry{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", color=" + color +
                ", left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}'
    }
}
