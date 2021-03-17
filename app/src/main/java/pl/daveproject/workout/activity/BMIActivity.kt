package pl.daveproject.workout.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import pl.daveproject.workout.R
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"
    var currentVisibleView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_bmi_activity)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"
        }
        toolbar.setNavigationOnClickListener { view ->
            onBackPressed()
        }

        val calculateBtn = findViewById<Button>(R.id.btnCalculateUnits)
        calculateBtn.setOnClickListener {
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)) {
                calculateBmiForMetricUnit()
            } else if (currentVisibleView.equals(US_UNITS_VIEW)){
                calculateBmiForUsUnit()
            }
        }

        makeVisibleMetricUnitsView()
        val rgUnits = findViewById<RadioGroup>(R.id.rgUnits)
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun calculateBmiForMetricUnit() {
        val weightEt = findViewById<AppCompatEditText>(R.id.etMetricUnitWeight)
        val heightEt = findViewById<AppCompatEditText>(R.id.etMetricUnitHeight)
        if (validateMetricUnits()) {
            val height = heightEt.text.toString().toFloat() / 100
            val weight = weightEt.text.toString().toFloat()

            val bmi = weight / (height * height)
            displayBmiResult(bmi)
        }
    }

    private fun calculateBmiForUsUnit() {
        val weightValue = findViewById<AppCompatEditText>(R.id.etMetricUnitWeight)
        val heightFeetValue = findViewById<AppCompatEditText>(R.id.etUsUnitHeightFeet)
        val heightInchValue = findViewById<AppCompatEditText>(R.id.etUsUnitHeightInch)
        if(validateUSUnits()) {
            val weight = weightValue.text.toString().toFloat()
            val heightFeet = heightFeetValue.text.toString().toFloat()
            val heightInch = heightInchValue.text.toString().toFloat()

            val height = heightInch + heightFeet * 12
            val bmi = weight / (height * height)
            displayBmiResult(bmi)
        }
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true
        val weightValue = findViewById<AppCompatEditText>(R.id.etMetricUnitWeight)
        val heightFeet = findViewById<AppCompatEditText>(R.id.etUsUnitHeightFeet)
        val heightInch = findViewById<AppCompatEditText>(R.id.etUsUnitHeightInch)

        if (heightFeet.text.toString().isEmpty() ||
            heightInch.text.toString().isEmpty() ||
            weightValue.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        val weightValue = findViewById<AppCompatEditText>(R.id.etMetricUnitWeight)
        val heightValue = findViewById<AppCompatEditText>(R.id.etMetricUnitHeight)

        if (weightValue.text.toString().isEmpty() || heightValue.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val tvBMIValue = findViewById<TextView>(R.id.tvBMIValue)
        val tvBMIType = findViewById<TextView>(R.id.tvBMIType)
        val tvBMIDescription = findViewById<TextView>(R.id.tvBMIDescription)
        val llDiplayBMIResult = findViewById<LinearLayout>(R.id.llDiplayBMIResult)

        llDiplayBMIResult.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        val tilMetricUnitWeight = findViewById<TextInputLayout>(R.id.tilMetricUnitWeight)
        tilMetricUnitWeight.visibility = View.VISIBLE
        val tilMetricUnitHeight = findViewById<TextInputLayout>(R.id.tilMetricUnitHeight)
        tilMetricUnitHeight.visibility = View.VISIBLE

        val etMetricUnitWeight = findViewById<AppCompatEditText>(R.id.etMetricUnitWeight)
        val etMetricUnitHeight = findViewById<AppCompatEditText>(R.id.etMetricUnitHeight)
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        val tilUsUnitWeight = findViewById<TextInputLayout>(R.id.tilUsUnitWeight)
        tilUsUnitWeight.visibility = View.GONE
        val llUsUnitsHeight = findViewById<LinearLayout>(R.id.llUsUnitsHeight)
        llUsUnitsHeight.visibility = View.GONE

        val llDiplayBMIResult = findViewById<LinearLayout>(R.id.llDiplayBMIResult)
        llDiplayBMIResult.visibility = View.GONE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW

        val tilMetricUnitWeight = findViewById<TextInputLayout>(R.id.tilMetricUnitWeight)
        tilMetricUnitWeight.visibility = View.GONE
        val tilMetricUnitHeight = findViewById<TextInputLayout>(R.id.tilMetricUnitHeight)
        tilMetricUnitHeight.visibility = View.GONE

        val etUsUnitWeight = findViewById<AppCompatEditText>(R.id.etUsUnitWeight)
        val etUsUnitHeightFeet = findViewById<AppCompatEditText>(R.id.etUsUnitHeightFeet)
        val etUsUnitHeightInch = findViewById<AppCompatEditText>(R.id.etUsUnitHeightInch)
        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        val tilUsUnitWeight = findViewById<TextInputLayout>(R.id.tilUsUnitWeight)
        tilUsUnitWeight.visibility = View.VISIBLE
        val llUsUnitsHeight = findViewById<LinearLayout>(R.id.llUsUnitsHeight)
        llUsUnitsHeight.visibility = View.VISIBLE

        val llDiplayBMIResult = findViewById<LinearLayout>(R.id.llDiplayBMIResult)
        llDiplayBMIResult.visibility = View.GONE
    }
}