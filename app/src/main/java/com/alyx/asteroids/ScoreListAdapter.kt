package com.alyx.asteroids

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.lang.Math.round
import kotlin.math.roundToInt

class ScoreListAdapter(private val list: List<String>) :
    BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        requireNotNull(parent)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element, null, true)
        view.findViewById<TextView>(R.id.title).text = list[position]

        val imageView = view.findViewById<ImageView>(R.id.icon)

        when ((Math.random() * 3).roundToInt()) {
            0 -> {
                imageView.setImageResource(android.R.drawable.star_big_off)
            }
            1 -> {
                imageView.setImageResource(android.R.drawable.star_big_on)
            }
            else -> {
                imageView.setImageResource(android.R.drawable.star_big_off)
            }
        }
        return view
    }
}