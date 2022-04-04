/**
 * Author: 孙强 20194780 <1604070358@qq.com>
 * Github Repo: github.com/jyjdal/AndroidActivity
 * Description: 主页面，用于接收并展示登录页面传递的内容、访问一个URL、
 *      以及接收接收信息返回给主页面
 */

package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // 渲染界面
        val view = binding.root
        setContentView(view)

        // 获取从上一个Activity传来的用户名和密码，并渲染到页面上
        val userKeys = PropertiesConfig.getUserKeys()
        val username = intent.getStringExtra(userKeys.first)
        binding.usernameDisplay.text = String.format(getString(R.string.to_fill_username), username)
        val password = intent.getStringExtra(userKeys.second)
        binding.passwordDisplay.text = String.format(getString(R.string.to_fill_password), password)

        // 设置定时任务，每隔1秒刷新一次当前时间
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                binding.timeDisplay.text = getCurrentTime()
                handler.postDelayed(this, 1000)
            }
        })

        // 访问网页的按钮被按下
        binding.searchButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(PropertiesConfig.getURL())
            startActivity(intent)
        }

        // 返回按钮被按下
        binding.goBack.setOnClickListener {
            // 获取用户输入，校验数据非空（一定要在后面加上toString()）
            val insiderName = binding.inputName.text.toString()
            if (insiderName.isEmpty()) {
                Toast.makeText(
                    this,
                    "Insider's name can't be empty", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // 准备数据，返回登录页面
            setResult(
                Activity.RESULT_OK,
                Intent().apply { putExtra(PropertiesConfig.getInsiderKey(), insiderName) })
            finish()
        }
    }

    // 获取当前时间
    private fun getCurrentTime(): String {
        val format = SimpleDateFormat.getDateTimeInstance()
        return format.format(Calendar.getInstance().time)
    }
}