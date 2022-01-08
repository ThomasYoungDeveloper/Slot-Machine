package com.thomasyoung.slotmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thomasyoung.slotmachine.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var slotValues = listOf<String>("🍒", "🍊", "🍓")
    private var currency: Int = 100
    private var randomElement: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        binding.tvCurrency.text = currency.toString()

        binding.btnBet.setOnClickListener{
            runSlots()
        }




    }

    fun randomValueFromList() {
        randomElement = slotValues.asSequence().shuffled().find { true }.toString()

    }

    fun runSlots(){

        if (currency > 0) {
            currency -= 10
            binding.tvCurrency.text = currency.toString()
            randomValueFromList()
            binding.tvSlot1.text = randomElement
            randomValueFromList()
            binding.tvSlot2.text = randomElement
            randomValueFromList()
            binding.tvSlot3.text = randomElement
            checkIfWon()
        } else {
            Toast.makeText(this,"You have no money", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkIfWon(){
        if (binding.tvSlot1.text == "🍒" && binding.tvSlot2.text  == "🍒" && binding.tvSlot3.text  == "🍒"  ) {
            currency += 1000
            Toast.makeText(this,"Jackpot!!!", Toast.LENGTH_SHORT).show()
        } else if (binding.tvSlot1.text == "🍊" && binding.tvSlot2.text  == "🍊" && binding.tvSlot3.text  == "🍊" ) {
            currency += 500
            Toast.makeText(this,"You've won!!!", Toast.LENGTH_SHORT).show()

        } else if (binding.tvSlot1.text == "🍓" && binding.tvSlot2.text  == "🍓" && binding.tvSlot3.text  == "🍓" ) {
            currency += 250
            Toast.makeText(this,"You've won!!", Toast.LENGTH_SHORT).show()
        }
    }
}