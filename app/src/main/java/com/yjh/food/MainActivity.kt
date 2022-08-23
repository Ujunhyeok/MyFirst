package com.yjh.food


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.math.roundToInt
import androidx.core.widget.addTextChangedListener
import androidx.multidex.MultiDex
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MultiDex.install(this)
        MobileAds.initialize(this){}

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        val drink : EditText = findViewById(R.id.Drink)
        drink.addTextChangedListener(NumberTextWatcher(drink))
        val food : EditText = findViewById(R.id.Food)
        food.addTextChangedListener(NumberTextWatcher(food))
        val sugar : EditText = findViewById(R.id.Sugar)
        sugar.addTextChangedListener(NumberTextWatcher(sugar))
        val alleat : EditText = findViewById(R.id.AllEat)
        val onlyfood : EditText = findViewById(R.id.OnlyFood)
        val resultButton : Button = findViewById(R.id.resultButton)



        ClearButton.setOnClickListener{
            Food.setText("")
            Drink.setText("")
            Sugar.setText("")
            AllEat.setText("")
            OnlyFood.setText("")
        }



        resultButton.setOnClickListener{
            if(drink.text.isEmpty() || food.text.isEmpty() || sugar.text.isEmpty() ||alleat.text.isEmpty() || onlyfood.text.isEmpty() ) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var temp_drink = 0
            temp_drink = drink.text.toString().replace(",","").toInt()
            var temp_food = 0
            temp_food = food.text.toString().replace(",","").toInt()
            var temp_sugar = 0
            temp_sugar = sugar.text.toString().replace(",","").toInt()
            var temp_plus = 0
            temp_plus = temp_drink + temp_sugar


            if(temp_food < temp_plus){
                Toast.makeText(this,"술과 음료수값이 총 식사비용을 초과합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(alleat.text.toString() == "0" && onlyfood.text.toString() == "0"){
                Toast.makeText(this,"먹은사람이 아무도없다고?",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val drinkbill: Double = temp_drink.toDouble()
            val foodbill: Double = temp_food.toDouble()
            val sugarbill: Double = temp_sugar.toDouble()
            val alleater: Double = alleat.text.toString().toDouble()
            val onlyfoodeater: Double = onlyfood.text.toString().toDouble()

            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("drinkbill", drinkbill)
            intent.putExtra("foodbill", foodbill)
            intent.putExtra("sugarbill",sugarbill)
            intent.putExtra("alleater", alleater)
            intent.putExtra("onlyfoodeater", onlyfoodeater)

            startActivity(intent)
        }
    }

}


