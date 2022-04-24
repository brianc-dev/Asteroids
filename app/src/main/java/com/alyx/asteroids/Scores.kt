package com.alyx.asteroids

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

class Scores : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scores)
        listAdapter = ScoreListAdapter(this, MainActivity.scoresStorage.scoresList(10))
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val obj = listAdapter.getItem(position)
        Snackbar.make(this, findViewById<ViewGroup>(android.R.id.content), "$position - $obj", Snackbar.LENGTH_LONG).show()
    }
}
