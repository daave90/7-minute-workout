package pl.daveproject.workout.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.daveproject.workout.R
import pl.daveproject.workout.db.SqliteOpenHelper
import pl.daveproject.workout.recyclerView.HistoryAdapter

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_history_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompleatedDates()
    }

    private fun getAllCompleatedDates() {
        val dbHandler = SqliteOpenHelper(this, null)
        val dates = dbHandler.getAllDates()

        if (dates.size > 0) {
            val tvHistory = findViewById<TextView>(R.id.tvHistory)
            tvHistory.visibility = View.VISIBLE

            val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
            rvHistory.visibility = View.VISIBLE

            val tvNoAvailableData = findViewById<TextView>(R.id.tvNoDataAvailable)
            tvNoAvailableData.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            rvHistory.adapter = HistoryAdapter(this, dates)
        } else {
            val tvHistory = findViewById<TextView>(R.id.tvHistory)
            tvHistory.visibility = View.GONE

            val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
            rvHistory.visibility = View.GONE

            val tvNoAvailableData = findViewById<TextView>(R.id.tvNoDataAvailable)
            tvNoAvailableData.visibility = View.VISIBLE
        }
    }
}