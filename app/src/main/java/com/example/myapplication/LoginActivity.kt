/**
 * Author: 孙强 20194780 <1604070358@qq.com>
 * Github Repo: github.com/jyjdal/AndroidActivity
 * Description: 登录页面，用于接收参数传递给主页面，以及接收并展示从主页面返回的信息
 * Date: 2022-03-17
 *  Info: 添加页面布局，实现除跳转以外的输入处理
 */

package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityLoginBinding
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取binding，设置页面
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 初始化配置文件读写类，因为这是进入的第一个页面，因此在这里进行初始化
        PropertiesConfig.init(this.assets)
        val userKeys = PropertiesConfig.getUserKeys()

        // 初始化launcher
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if ((it.resultCode != Activity.RESULT_OK) || it.data == null) {
                    Log.e("App Callback", "Error when going back!")
                    exitProcess(-1)
                }
                val data = it.data!!

                // TODO 这里需要改成Dialog
                Toast.makeText(
                    this,
                    data.getStringExtra(PropertiesConfig.getInsiderKey()),
                    Toast.LENGTH_SHORT
                ).show()
            }

        // 当登录按钮被按下
        binding.login.setOnClickListener {
            // 获取用户输入内容，进行判空校验
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            if (username.isEmpty() or username.isEmpty()) {
                Toast.makeText(this, "Cannot input empty values!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 成功登录（用户名和密码都不为空的前提）
            Log.i(
                "Login Button",
                "User trying to login with username: ${username}, password: ${password}."
            )

            val intent = Intent("com.example.myapplication.MAIN_PAGE").apply {
                putExtra(userKeys.first, username)
                putExtra(userKeys.second, password)
            }
            activityResultLauncher.launch(intent)
        }

        // 当退出按钮被按下
        binding.exit.setOnClickListener {
            Log.i("Exit Button", "User clicked exit button, app will shutdown.")
            // 使用finish可以不像System.exit(0)一样粗暴地停机，但是可能会有资源未被及时回收
            finish()
        }
    }

    // 当前Activity被切换
    override fun onPause() {
        Log.d("App Pause", "Login activity paused.")
        super.onPause()
    }

    // 当前Activity被销毁，一般是程序退出时使用
    override fun onDestroy() {
        Log.i("App Exit", "Shutting down.")
        super.onDestroy()
    }
}