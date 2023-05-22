package com.example.a31marchfindergame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import org.w3c.dom.Text
import java.util.*
import kotlin.math.floor
import kotlin.random.Random.Default.nextInt

class SpotLightView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :  AppCompatImageView( context, attrs, defStyleAttr) {

    //var score:Int=0
    var imgNum:Int=0
    private var shaderPaint = Paint()
    private var shouldDrawSpotLight = false
    private var gameOver = false
    private var shader: Shader
    private lateinit var winnerRect: RectF
    private var androidBitmapX = 0f
    private var androidBitmapY = 0f
    private var img1 = BitmapFactory.decodeResource(resources, R.drawable.fish11)
    //private val img2 = BitmapFactory.decodeResource(resources, R.drawable.fish12)
    //private val img3 = BitmapFactory.decodeResource(resources, R.drawable.coins)
    private val spotlight = BitmapFactory.decodeResource(resources, R.drawable.mask)
    private val shaderMatrix = Matrix()

    init {

        val bitmap = Bitmap.createBitmap(spotlight.width, spotlight.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw a black rectangle.
        paint.color = Color.BLACK
        canvas.drawRect(0.0f, 0.0f, spotlight.width.toFloat(), spotlight.height.toFloat(), paint)

        // Use DST_OUT compositing mode to mask out the spotlight from the black rectangle.
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawBitmap(spotlight, 0.0f, 0.0f, paint)

        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        shaderPaint.shader = shader
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val motionEventX = motionEvent.x
        val motionEventY = motionEvent.y

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                shouldDrawSpotLight = true
                if (gameOver) {
                    gameOver = false
                    val audio = MediaPlayer.create(context , R.raw.clickaudio)
                    audio.start()
                    userScore+=50
                    val activity1=MainActivity()
                    activity1.scoreUpdate()
                    setupImg()
                    setupWinnerRect()
                }
            }
            MotionEvent.ACTION_UP -> {
                shouldDrawSpotLight = false
                gameOver = winnerRect.contains(motionEventX, motionEventY)
            }
        }
        shaderMatrix.setTranslate(
            motionEventX - spotlight.width / 2.0f,
            motionEventY - spotlight.height / 2.0f
        )
        shader.setLocalMatrix(shaderMatrix)
        invalidate()
        return true
    }

    /**
     * This method is called every time the size of a view changes, including the first time after
     * it has been inflated.
     *
     * @param newWidth Current width of view
     * @param newHeight Current height of view
     * @param oldWidth Previous width of view
     * @param oldHeight Previous height of view
     */
    override fun onSizeChanged(
        newWidth: Int,
        newHeight: Int,
        oldWidth: Int,
        oldHeight: Int
    ) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        setupWinnerRect()
    }

    /**
     * Render view content:
     * Fill the canvas with white and draw the bitmap of the Android image.
     * If game is not over and shouldDrawSpotLight is true, draw a full screen rectangle using the
     * paint with BitmapShader, else fill the canvas with black color.
     *
     * @param canvas The canvas on which the background will be drawn
     */
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        //canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(img1, androidBitmapX, androidBitmapY, shaderPaint)

        if (!gameOver) {
            if (shouldDrawSpotLight) {
                canvas.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), shaderPaint)
            }
            else {
                canvas.drawColor(Color.BLACK)
            }
        }


    }

    fun setupImg(){
        val num=(1..3).random()
        imgNum=num
        when(num){
            1-> img1 = BitmapFactory.decodeResource(resources, R.drawable.fish11)
            2-> img1 = BitmapFactory.decodeResource(resources, R.drawable.fish12)
            3-> img1 = BitmapFactory.decodeResource(resources, R.drawable.coins)
        }
    }

    /**
     * Calculates a randomized location for the Android bitmap and the winning bounding rectangle.
     */

    private fun setupWinnerRect() {
                androidBitmapX = floor(Random().nextFloat() * (width - img1.width))
                androidBitmapY = floor(Random().nextFloat() * (height - img1.height))
                winnerRect = RectF(
                    (androidBitmapX),
                    (androidBitmapY),
                    (androidBitmapX + img1.width),
                    (androidBitmapY + img1.height)
                )

    }
}