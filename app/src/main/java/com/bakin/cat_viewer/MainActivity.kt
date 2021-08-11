package com.bakin.cat_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    lateinit var catView: ImageView
    lateinit var getCatButton: Button
    lateinit var progressBar: ProgressBar
    private var isImageScaled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {

        catView = findViewById(R.id.image)
        getCatButton = findViewById(R.id.getCatButton)
        progressBar = findViewById(R.id.progressBar)

        getCatButton.setOnClickListener {
            it.isClickable = false
            Thread {
                val url = Cat.getUrl()
                runOnUiThread {
                    Picasso.get().load(url).resize(1080, 720).into(catView)
                }
            }.start()
        }

        catView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            var count = -1
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                count++
                println(v)
                if (count == 0) return
                progressBar.visibility = if (count % 2 == 1) {
                    getCatButton.isClickable = true
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
            }
        })

        catView.setOnClickListener {
            val scale = if (!isImageScaled) 1.4f else 1f
            it.animate().scaleX(scale).scaleY(scale).setDuration(500)
            isImageScaled = !isImageScaled
        }

    }

}