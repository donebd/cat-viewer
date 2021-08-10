package com.bakin.cat_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catView : ImageView = findViewById(R.id.image)
        val getCatButton : Button = findViewById(R.id.getCatButton)

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