package com.example.a31marchfindergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.provider.Settings.Global.getString
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import java.util.*

//public var hs:Int=0
public var userScore:Int=0
lateinit var mediaplayer:MediaPlayer
class MainActivity : AppCompatActivity() {

    lateinit var timerText: TextView
    lateinit var coinsIV:ImageView
    lateinit  var scoreText:TextView
    var bool:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //for bg audio
        mediaplayer = MediaPlayer.create(this, R.raw.playful);
        mediaplayer.start()
        mediaplayer.isLooping=true
        userScore=0
        timerText = findViewById(R.id.timer)
        scoreText=findViewById(R.id.coins)
        val dialog = createInstructionsDialog()
        dialog.show()

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                try {

                    scoreText.text= userScore.toString()
                    if (bool){
                        coinsIV=findViewById(R.id.coinsIv)
                        val animationBounce = AnimationUtils.loadAnimation(this@MainActivity, R.anim.bounce)
                        coinsIV.startAnimation(animationBounce)
                        bool=false
                    }

                } catch (e: Exception) {
                }
            }
        }, 0, 1000)
    }

    private fun createInstructionsDialog(): Dialog {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.fish11)
            .setTitle(R.string.instructions_title)
            .setMessage(R.string.instructions)
            .setPositiveButtonIcon(ContextCompat.getDrawable(this, android.R.drawable.ic_media_play))
            .setPositiveButton(""){ dialog, which -> runCountdown() }
        return builder.create()
    }

    private fun runCountdown() {
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                val intent= Intent(this@MainActivity,hello::class.java)
                startActivity(intent)
            }
        }.start()
    }
    fun scoreUpdate(){

        bool=true
    }
}