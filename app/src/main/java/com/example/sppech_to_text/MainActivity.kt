package com.example.sppech_to_text

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtView: TextView
    private lateinit var buttonSpeak: Button
    private lateinit var buttonClear: Button
    private lateinit var islContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtView = findViewById(R.id.txt)
        buttonSpeak = findViewById(R.id.button_speak)
        buttonClear = findViewById(R.id.button_clear)
        islContainer = findViewById(R.id.isl_container)

        buttonSpeak.setOnClickListener {
            speak()
        }

        buttonClear.setOnClickListener {
            clearContent()
        }
    }

    private fun speak() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "SPEAK NOW")
        startActivityForResult(intent, 99)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99 && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""

            if (txtView.text.toString() == "Your Text") {
                txtView.text = spokenText
            } else {
                txtView.append(" $spokenText")
            }

            displayISLImages(spokenText)
        }
    }

    private fun displayISLImages(text: String) {
        val letterToImage = mapOf(
            'a' to R.drawable.a,
            'b' to R.drawable.b,
            'c' to R.drawable.c,
            'd' to R.drawable.d,
            'e' to R.drawable.e,
            'f' to R.drawable.f,
            'g' to R.drawable.g,
            'h' to R.drawable.h,
            'i' to R.drawable.i,
            'j' to R.drawable.j,
            'k' to R.drawable.k,
            'l' to R.drawable.l,
            'm' to R.drawable.m,
            'n' to R.drawable.n,
            'o' to R.drawable.o,
            'p' to R.drawable.p,
            'q' to R.drawable.q,
            'r' to R.drawable.r,
            's' to R.drawable.s,
            't' to R.drawable.t,
            'u' to R.drawable.u,
            'v' to R.drawable.v,
            'w' to R.drawable.w,
            'x' to R.drawable.x,
            'y' to R.drawable.y,
            'z' to R.drawable.z,
            ' ' to R.drawable.space
        )

        val imageWidth = 250
        val imageHeight = 250
        val imagesPerRow = 4

        var rowLayout: LinearLayout? = null
        var count = 0

        for (char in text) {
            if (char.isLetter() || char.isWhitespace()) {
                if (count % imagesPerRow == 0) {
                    rowLayout = LinearLayout(this).apply {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    islContainer.addView(rowLayout)
                }

                letterToImage[char.lowercaseChar()]?.let {
                    val imageView = ImageView(this).apply {
                        setImageResource(it)
                        layoutParams = LinearLayout.LayoutParams(
                            imageWidth, imageHeight
                        ).apply {
                            setMargins(8, 8, 8, 8)
                        }
                    }
                    rowLayout?.addView(imageView)
                }

                count++
            }
        }
    }

    private fun clearContent() {
        islContainer.removeAllViews()
        txtView.text = "Your Text"
    }
}
