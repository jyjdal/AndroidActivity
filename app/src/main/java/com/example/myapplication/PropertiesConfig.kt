/**
 * Author: 孙强 20194780 <1604070358@qq.com>
 * Github Repo: github.com/jyjdal/AndroidActivity
 * Description: 配置信息类，用于封装读取配置文件的操作
 * Date: 2022-03-18
 *  Info: 添加配置信息读取类，实现对配置信息操作的封装
 */

package com.example.myapplication

import android.content.res.AssetManager
import android.util.Log
import java.lang.Exception
import java.util.*
import kotlin.system.exitProcess

object PropertiesConfig {
    private val properties =  Properties()

    // 初始化对配置文件的加载，必须先执行这个方法才能调用后续的读取方法
    fun init(context: AssetManager) {
        try {
            properties.load(context.open("app.properties"))
        } catch (e: Exception) {
            Log.e("App Config", "Unable to open config file, because ${e.message}")
            exitProcess(-1)
        }
    }

    // 读取浏览器访问的URL
    fun getURL(): String {
        return properties.getProperty("url")
    }

    // 读取登录界面向主界面传送的用户名和密码的键
    fun getUserKeys(): Pair<String, String> {
        return Pair(properties.getProperty("intent_key_username"),
            properties.getProperty("intent_key_password"))
    }

    // 读取主界面向传送的知情人的键
    fun getInsiderKey(): String {
        return properties.getProperty("intent_key_insider")
    }
}