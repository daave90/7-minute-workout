package pl.daveproject.workout.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.daveproject.workout.R
import pl.daveproject.workout.model.Constants
import pl.daveproject.workout.model.Exercise
import pl.daveproject.workout.recyclerView.ExerciseStatusAdapter
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<Exercise>? = null
    private var currentExercisePosition = -1
    private var textToSpeech: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

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
        textToSpeech = TextToSpeech(this, this)
        exerciseList = Constants.createDefaultExerciseList()
        setupRestView()
        setupExerciseStatusRecycleView()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val langResult = textToSpeech?.setLanguage(Locale.US)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language" + Locale.US + "is not supported")
            }
        } else {
            Log.e("TTS", "TTS initialization failed!")
        }
    }

    override fun onDestroy() {
        destroyTts()
        destroyRestTimer()
        destroyMediaPlayer()
        super.onDestroy()
    }

    private fun destroyRestTimer() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
    }

    private fun destroyTts() {
        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
    }

    private fun destroyMediaPlayer() {
        if (player != null) {
            player?.stop()
        }
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
        playSound()

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

    private fun playSound() {
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpcomingExercise() {
        val upcomingExerciseName = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        val upcomingExerciseLabel = findViewById<TextView>(R.id.tvUpcomingExerciseLabel)
        val upcomingExercisePosition = currentExercisePosition + 1
        if (upcomingExercisePosition < exerciseList?.size!!) {
            upcomingExerciseName.text = exerciseList?.get(upcomingExercisePosition)?.getName()
        } else {
            upcomingExerciseName.visibility = View.GONE
            upcomingExerciseLabel.visibility = View.GONE
        }
    }

    private fun speakOut(upcomingExercise: String) {
        textToSpeech?.speak(upcomingExercise, TextToSpeech.QUEUE_FLUSH, null, "")
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
        speakOut(exerciseList!!.get(currentExercisePosition).getName())
        setExerciseProgressBar()
        val exerciseName = findViewById<TextView>(R.id.tvExerciseName)
        val exerciseImage = findViewById<ImageView>(R.id.ivExerciseImage)

        exerciseName.text = exerciseList?.get(currentExercisePosition)?.getName()
        exerciseImage.setImageResource(exerciseList?.get(currentExercisePosition)?.getImage() ?: 0)
    }

    private fun setupExerciseStatusRecycleView() {
        val exerciseStatus = findViewById<RecyclerView>(R.id.rvExerciseStatus)
        exerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        exerciseStatus.adapter = exerciseAdapter
    }
}