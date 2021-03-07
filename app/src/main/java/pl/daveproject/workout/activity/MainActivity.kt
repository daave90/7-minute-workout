package pl.daveproject.workout.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import pl.daveproject.workout.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<LinearLayout>(R.id.llStart)
        startBtn.setOnClickListener { view ->
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }
}