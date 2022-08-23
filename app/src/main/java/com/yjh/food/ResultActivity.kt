package com.yjh.food

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.adView
import java.text.DecimalFormat
import kotlin.math.roundToInt


class ResultActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val decimal = DecimalFormat("#,###")
        val drinkbill = intent.getDoubleExtra("drinkbill", 0.0)
        val foodbill = intent.getDoubleExtra("foodbill", 0.0)
        val sugarbill = intent.getDoubleExtra("sugarbill",0.0)
        val alleater  = intent.getDoubleExtra("alleater", 0.0)
        val onlyfoodeater = intent.getDoubleExtra("onlyfoodeater", 0.0)
        var pig : Double = (drinkbill / alleater) + ((foodbill-drinkbill-sugarbill) / (alleater + onlyfoodeater))
        var trash : Double = ((foodbill-drinkbill-sugarbill) / (alleater + onlyfoodeater)) + (sugarbill / onlyfoodeater)

        if(pig.isNaN()){
            pig = 0.0
        }

        if(trash.isNaN()){
            trash = 0.0
        }

        val pigfirst = decimal.format(pig)
        val trashfirst = decimal.format(trash)

        Log.d("pigfirst",pigfirst)
        Log.d("trashfirst",trashfirst)

        val thistispig: TextView = findViewById(R.id.AllEaterResult)
        val thisistrash: TextView = findViewById(R.id.OnlyFoodEaterResult)

        thistispig.text = pigfirst.toString()
        thisistrash.text = trashfirst.toString()



        MainButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)

            startActivity(intent)
        }

    }
}