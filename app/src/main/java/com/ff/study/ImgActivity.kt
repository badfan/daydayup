package com.ff.study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ff.annotation.FFWork

class ImgActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("adapter","ImgActivity onCreate")
        setContentView(R.layout.activity_img)
        FFWork.inject(this)
    }


}
