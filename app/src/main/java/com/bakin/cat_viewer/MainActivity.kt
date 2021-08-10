package com.bakin.cat_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    lateinit var catView : ImageView
    lateinit var getCatButton : Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catView  = findViewById(R.id.image)
        getCatButton = findViewById(R.id.getCatButton)

        getCatButton.setOnClickListener {
            Thread {
                val url = Cat.getUrl()
                    runOnUiThread {
                        Picasso.get().load(url).resize(1080,720).into(catView);
                    }
            }.start()
        }
    }

}