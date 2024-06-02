package com.github.dust2.tools

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

class MyApplication : Application() {
    companion object {
        lateinit var application: MyApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }

}
