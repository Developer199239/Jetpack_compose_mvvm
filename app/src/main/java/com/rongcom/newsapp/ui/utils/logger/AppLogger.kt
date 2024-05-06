package com.rongcom.newsapp.ui.utils.logger

import android.util.Log
import com.rongcom.newsapp.ui.utils.logger.Logger

class AppLogger : Logger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}