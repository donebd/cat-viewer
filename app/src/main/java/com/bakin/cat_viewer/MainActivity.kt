package com.bakin.cat_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    lateinit var catView: ImageView
    lateinit var getCatButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var catScalable : SubsamplingScaleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {

        catView = findViewById(R.id.image)
        getCatButton = findViewById(R.id.getCatButton)
        progressBar = findViewById(R.id.progressBar)
        catScalable = findViewById(R.id.imageView)

        getCatButton.setOnClickListener {
            if (!hasInternet(this)) {
                Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            it.isClickable = false
            Thread {
                val url = Cat.getUrl()
                runOnUiThread {
                    Picasso.get().load(url).resize(1080, 720).into(catView)
                }
            }.start()
        }

        catView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            var count = 0
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
                if (count == 0) {
                    count++
                    return
                }
                if (count % 2 == 1) {
                    progressBar.visibility = View.INVISIBLE
                    catScalable.visibility = View.VISIBLE
                    getCatButton.isClickable = true

                    val image = catView.drawable.toBitmap()
                    catScalable.setImage(ImageSource.bitmap(image))
                } else {
                    progressBar.visibility = View.VISIBLE
                    catScalable.visibility = View.INVISIBLE
                }
                count++
            }
        })

    }

}