package pl.daveproject.workout.activity

import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.daveproject.workout.R
import pl.daveproject.workout.model.Constants
import pl.daveproject.workout.model.Exercise

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<Exercise>? = null
    private var currentExercisePosition = -1


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

        exerciseList = Constants.createDefaultExerciseList()
        setupRestView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
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
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        val exerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        exerciseView.visibility = View.GONE

        val restView = findViewById<LinearLayout>(R.id.llRestView)
        restView.visibility = View.VISIBLE

        setUpcomingExercise()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setUpcomingExercise() {
        val upcomingExerciseName = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        val upcomingExerciseLabel = findViewById<TextView>(R.id.tvUpcomingExerciseLabel)
        val upcomingExercisePosition = currentExercisePosition + 1
        if(upcomingExercisePosition < exerciseList?.size!!) {
            upcomingExerciseName.text = exerciseList?.get(upcomingExercisePosition)?.getName()
        } else {
            upcomingExerciseName.visibility = View.GONE
            upcomingExerciseLabel.visibility = View.GONE
        }
    }

    private fun setExerciseProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.exerciseProgressBar)
        val tvTimer = findViewById<TextView>(R.id.tvExerciseTimer)
        progressBar.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBar.progress = 30 - exerciseProgress
                tvTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations you finished 7 minutes workout challenge.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }.start()
    }

    private fun setupExerciseView() {
        val exerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        exerciseView.visibility = View.VISIBLE

        val restView = findViewById<LinearLayout>(R.id.llRestView)
        restView.visibility = View.GONE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
        val exerciseName = findViewById<TextView>(R.id.tvExerciseName)
        val exerciseImage = findViewById<ImageView>(R.id.ivExerciseImage)

        exerciseName.text = exerciseList?.get(currentExercisePosition)?.getName()
        exerciseImage.setImageResource(exerciseList?.get(currentExercisePosition)?.getImage() ?: 0)
    }
}