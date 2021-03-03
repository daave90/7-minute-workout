package pl.daveproject.workout

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
            Toast.makeText(this, "Start exercises", Toast.LENGTH_SHORT).show()
        }
    }
}