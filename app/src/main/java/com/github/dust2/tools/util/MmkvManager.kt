package com.github.dust2.tools.util

import com.github.dust2.tools.AppConfig.ID_SETTING
import com.tencent.mmkv.MMKV

object MmkvManager {

    private val settingsStorage by lazy { MMKV.mmkvWithID(ID_SETTING, MMKV.MULTI_PROCESS_MODE) }

    fun getSetting(): MMKV {
        return settingsStorage
    }
}
