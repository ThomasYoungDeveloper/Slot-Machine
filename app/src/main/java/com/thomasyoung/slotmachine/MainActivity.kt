package com.thomasyoung.slotmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thomasyoung.slotmachine.databinding.ActivityMainBinding
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.view.animation.Animation

import android.view.animation.AlphaAnimation


import android.view.View

import android.widget.TextView





class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var slotValues = listOf<String>("🍒", "🍊", "🍓")
    private var slotRoll = mutableListOf<String>()
    private var currency: Int = 1000
    private var randomElement: String = ""
    var mMediaPlayer: MediaPlayer? = null
    var betAmount: Int = 1
    var winAmount: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        binding.tvCurrency.text = "Balance: ${currency.toString()}"

        binding.btnSpin.setOnClickListener{
            runSlots()
        }

        binding.btnDecreaseBet.setOnClickListener{
            decreaseBet()
        }

        binding.btnIncreaseBet.setOnClickListener{
            increaseBet()
        }




    }

    private fun increaseBet() {
        if (betAmount < 3 ){
            betAmount++
            binding.tvBetAmount.text = "${betAmount}x"
        }

    }

    private fun decreaseBet() {
        if (betAmount > 1 ){
            betAmount--
            binding.tvBetAmount.text = "${betAmount}x"
        }
    }

    private fun increaseCurrency(amount: Int, betAmount: Int) {
        flashMessage()
        binding.tvPlayAmount.text = "You Win!"
        val animator = ValueAnimator.ofInt(currency + (amount * betAmount))
        animator.duration = 3000
        animator.addUpdateListener { animation -> binding.tvCurrency.text = "Balance: ${animation.animatedValue.toString()}" }
        animator.start()
        winAmount = betAmount * amount
        currency += (amount * betAmount)
    }

    private fun randomValueFromList(numberOfTimes: Int): String {
        for (i in 1..numberOfTimes) {
            randomElement = slotValues.asSequence().shuffled().find { true }.toString()
            slotRoll.add(randomElement)
        }
        return randomElement
    }

    private fun runSlots(){

        if (currency * betAmount > 0) {
            binding.tvPlayAmount.text = "100 per Play"
            currency -= 100 * betAmount
            binding.tvCurrency.text = "Balance: ${currency.toString()}"
            randomValueFromList(3)
            binding.tvSlot1.text = slotRoll[0]
            binding.tvSlot2.text = slotRoll[1]
            binding.tvSlot3.text = slotRoll[2]
//            binding.tvSlot4.text = slotRoll[3]
//            binding.tvSlot5.text = slotRoll[4]
            checkIfWon()
            slotRoll.clear()
        } else {
            binding.tvPlayAmount.text = "Bankrupt!"
            Toast.makeText(this,"You have no money", Toast.LENGTH_SHORT).show()
        }


    }



    private fun checkIfWon(){
        // rolled 3 cherries
        if (binding.tvSlot1.text == "🍒" && binding.tvSlot2.text  == "🍒" && binding.tvSlot3.text  == "🍒"  ) {
            increaseCurrency(1000, betAmount)
            playJackpotSound()
            Toast.makeText(this,"Jackpot!!! You've won $winAmount", Toast.LENGTH_SHORT).show()
        } // rolled 3 oranges
        else if (binding.tvSlot1.text == "🍊" && binding.tvSlot2.text  == "🍊" && binding.tvSlot3.text  == "🍊" ) {
            increaseCurrency(500, betAmount)
            playJackpotSound()
            Toast.makeText(this,"You've won $winAmount!!!", Toast.LENGTH_SHORT).show()
        } // rolled 3 strawberries
        else if (binding.tvSlot1.text == "🍓" && binding.tvSlot2.text  == "🍓" && binding.tvSlot3.text  == "🍓" ) {
            increaseCurrency(250, betAmount)
            playJackpotSound()
            Toast.makeText(this,"You've won!! $winAmount", Toast.LENGTH_SHORT).show()
        }
    }


    private fun playJackpotSound(){
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.slot_machine_jackpot_sound)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun flashMessage() {
        val message = binding.tvPlayAmount

        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 150

        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 10
        message.startAnimation(anim)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMediaPlayer != null){
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

}