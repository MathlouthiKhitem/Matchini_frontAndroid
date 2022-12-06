package com.example.matchinii.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.matchinii.R
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.SeekBar

private const val TAG = "SettingsActivity"
private var distance1 :SeekBar?=null
lateinit  var man :SwitchCompat
lateinit  var woman :SwitchCompat
lateinit  var distancetext :TextView
lateinit  var age_rnge:TextView
lateinit var Logout1: Button
//var rangeSeekBar: RangeSeekBar? = null

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        distance1 = findViewById(R.id.distance)
        man = findViewById<SwitchCompat>(R.id.switch_man)
        woman = findViewById<SwitchCompat>(R.id.switch_woman)
        distancetext = findViewById(R.id.distance_text)
        age_rnge = findViewById<TextView>(R.id.age_range)
        Logout1=findViewById(R.id.Logout)
        //rangeSeekBar = findViewById(R.id.rangeSeekbar)


        distance.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                distance_text.text = "$progress Km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        man.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                man.setChecked(true)
                woman.setChecked(false)
            }
        })
        woman.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                woman.setChecked(true)
                man.setChecked(false)
            }
        })
     /*   rangeSeekBar.setOnRangeSeekBarChangeListener(object : OnRangeSeekBarChangeListener() {
            fun onRangeSeekBarValuesChanged(bar: RangeSeekBar?, minValue: Any, maxValue: Any) {
                age_rnge.setText("$minValue-$maxValue")
            }
        })
        back.setOnClickListener { onBackPressed() }


    }*/

    Logout1.setOnClickListener() {
        startActivity(Intent(applicationContext, SecondActivity::class.java))
        finish()
    }


}
}