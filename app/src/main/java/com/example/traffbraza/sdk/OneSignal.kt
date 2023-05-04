package com.example.traffbraza.sdk

import android.content.Context
import com.onesignal.OneSignal


private const val ONESIGNAL_APP_ID = "76bc7031-1339-4195-8557-e923757663f4"

class OneSignal {

    fun init(context: Context) {
        // Logging set to help debug issues, remove before releasing your app.
        // TODO: remove
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications()
    }
}
