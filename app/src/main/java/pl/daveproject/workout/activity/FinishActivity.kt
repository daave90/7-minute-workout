package pl.daveproject.workout.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.daveproject.workout.R
import pl.daveproject.workout.db.SqliteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_finish_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val finishButton = findViewById<Button>(R.id.btnFinish)
        finishButton.setOnClickListener {
            finish()
        }
        addDateToDatabase()
    }

    private fun addDateToDatabase() {
        val calendar = Calendar.getInstance()
        val datetime = calendar.time
        Log.i("DATE:", "" + datetime)

        val sdf = SimpleDateFormat("dd MMM yyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(datetime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("DATE:", "Date added $date")
    }
}