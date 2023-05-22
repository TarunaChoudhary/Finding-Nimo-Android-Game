package com.example.a31marchfindergame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit
var maxScore:Int = 0
class hello : AppCompatActivity() {
    lateinit var scoreText: TextView
    lateinit var highscore:TextView
    lateinit var restart: Button
    lateinit var viewKonfetti:KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        viewKonfetti=findViewById(R.id.viewKonfetti)
        scoreText=findViewById(R.id.coins)
        highscore=findViewById(R.id.highscore)
        restart=findViewById(R.id.restart)
        scoreText.text= "YOUR SCORE: $userScore"

        if (userScore>maxScore){
            //confetti for crossing high score
            val party = Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                position = Position.Relative(0.5, 0.3)
            )
            viewKonfetti.start(party)
            highscore.text= "HIGH SCORE: $userScore"
            maxScore= userScore
        }
        else{
            highscore.text= "HIGH SCORE: $maxScore"
        }

        restart.setOnClickListener {
            mediaplayer.release()
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}