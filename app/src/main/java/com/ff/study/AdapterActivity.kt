package com.ff.study

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ff.common.annotations.FFWork
import com.ff.common.screenadapter.ScreenAdapter
import kotlinx.android.synthetic.main.act_adapter.*

class AdapterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenAdapter.match(this, 720f, ScreenAdapter.MATCH_BASE_WIDTH)

        setContentView(R.layout.act_adapter)
        FFWork.inject(this)
        tv_content!!.text = "注解成功啦"

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

            private var instance: Teacher? = null

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

