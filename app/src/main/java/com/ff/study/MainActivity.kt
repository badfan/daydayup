package com.ff.study

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ff.annotation.FFWork
import com.ff.common.screenadapter.ScreenAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenAdapter.match(this, 720f, ScreenAdapter.MATCH_BASE_WIDTH)

        setContentView(R.layout.activity_main)
        FFWork.inject(this)
        tv_content!!.text = "注解成功啦"

        button.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ImgActivity::class.java))
        })



    }

    override fun onPause() {
        super.onPause()
        Log.e("adapter", "onPause")
        ScreenAdapter.cancelMatch(this)
    }

    override fun onStop() {
        super.onStop()
        Log.e("adapter", "onStop")
        ScreenAdapter.cancelMatch(this)
    }

    public fun dbtest() {
//        DBOpe.getInstance(getContext());

        Handler().post(Runnable { })

        var instance: Teacher

        Teacher.newInstance().getName()
    }

    class Teacher {
        companion object {

            private lateinit var instance: Teacher

            fun newInstance(): Teacher {
                if (instance == null) {
                    instance = Teacher()
                }
                return instance!!
            }

        }

        fun getName(): String {
            return "aa"
        }
    }
}

