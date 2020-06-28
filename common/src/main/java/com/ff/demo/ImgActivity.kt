package com.ff.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ff.common.R
import com.ff.common.annotations.FFWork

class ImgActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("adapter","ImgActivity onCreate")
        setContentView(R.layout.activity_img)
        FFWork.inject(this)
    }


}
