package pl.daveproject.workout

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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