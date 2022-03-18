package com.example.myapplication

import android.content.res.AssetManager
import android.util.Log
import java.lang.Exception
import java.util.*
import kotlin.system.exitProcess

object PropertiesConfig {
    private val properties =  Properties()

    fun init(context: AssetManager) {
        try {
            properties.load(context.open("app.properties"))
        } catch (e: Exception) {
            Log.e("App Config", "Unable to open config file, because ${e.message}")
            exitProcess(-1)
        }
    }

    fun getURL(): String {
        return properties.getProperty("url")
    }

    fun getUserKeys(): Pair<String, String> {
        return Pair(properties.getProperty("intent_key_username"),
            properties.getProperty("intent_key_password"))
    }

    fun getInsiderKey(): String {
        return properties.getProperty("intent_key_insider")
    }
}