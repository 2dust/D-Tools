package com.github.dust2.tools.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.net.Uri
import android.os.Build

object Utils {
    fun openUri(context: Context?, uriString: String) {
        val uri = Uri.parse(uriString)
        context?.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

}

