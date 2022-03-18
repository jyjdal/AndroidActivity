/**
 * Author: 孙强 20194780 <1604070358@qq.com>
 * Github Repo: github.com/jyjdal/AndroidActivity
 * Description: 主页面，用于接收并展示登录页面传递的内容、访问一个URL、
 *      以及接收接收信息返回给主页面
 * Date: 2022-03-17
 *  Info: 添加页面布局，实现除跳转以外的输入处理
 */

package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val userKeys = PropertiesConfig.getUserKeys()
        val username = intent.getStringExtra(userKeys.first)
        binding.usernameDisplay.text = String.format(getString(R.string.to_fill_username), username)
        val password = intent.getStringExtra(userKeys.second)
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

        // 访问网页的按钮被按下
        binding.searchButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(PropertiesConfig.getURL())
            startActivity(intent)
        }

        // 返回按钮被按下
        binding.goBack.setOnClickListener {
            val insiderName = binding.insiderName.text

            //  校验参数非空
            if (insiderName.isEmpty()) {
                Toast.makeText(
                    this,
                    "Insider's name can't be empty", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // 准备数据，返回登录页面
            val intent = Intent("android.intent.action.MAIN")
            intent.putExtra(PropertiesConfig.getInsiderKey(), insiderName)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun getCurrentTime(): String {
        val format = SimpleDateFormat.getDateTimeInstance()
        return format.format(Calendar.getInstance().time)
    }
}