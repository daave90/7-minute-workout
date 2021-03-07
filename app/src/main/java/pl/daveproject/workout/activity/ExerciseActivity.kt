package pl.daveproject.workout.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.daveproject.workout.R

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { view ->
            onBackPressed()
        }

        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                val exerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
                exerciseView.visibility = View.VISIBLE

                val restView = findViewById<LinearLayout>(R.id.llRestView)
                restView.visibility = View.GONE
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.exerciseProgressBar)
        val tvTimer = findViewById<TextView>(R.id.tvExerciseTimer)
        progressBar.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBar.progress = 30 - exerciseProgress
                tvTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Change exercise", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setupExerciseView() {
        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }
}