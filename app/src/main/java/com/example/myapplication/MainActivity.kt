package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // 渲染界面
        val view = binding.root
        setContentView(view)

        // 获取从上一个Activity传来的用户名和密码，并渲染到页面上
        val username = intent.getStringExtra("username")
        binding.usernameDisplay.text = String.format(getString(R.string.to_fill_username), username)
        val password = intent.getStringExtra("password")
        binding.passwordDisplay.text = String.format(getString(R.string.to_fill_password), password)

        // 创建线程，用于刷新时间
        thread(start = true) {
            // 获取时间
            val time = getCurrentTime()
            // 找到控件并刷新内容
            binding.timeDisplay.text = time
            // sleep1秒钟
            Thread.sleep(1000)
        }
    }

    private fun getCurrentTime(): String {
        val format = SimpleDateFormat.getDateTimeInstance()
        return format.format(Calendar.getInstance().time)
    }
}