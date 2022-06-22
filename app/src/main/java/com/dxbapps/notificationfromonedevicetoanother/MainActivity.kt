package com.dxbapps.notificationfromonedevicetoanother

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private var title: EditText? = null
    private var message: EditText? = null
    private var token: EditText? = null
    private var sendall: Button? = null
    private var single: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("all")

        title = findViewById(R.id.title)
        message = findViewById(R.id.message)
        token = findViewById(R.id.token)
        sendall = findViewById(R.id.send_all_devices)
        single = findViewById(R.id.send_single_devices)


        sendall!!.setOnClickListener(View.OnClickListener {
            val notificationsSender = FcmNotificationsSender(
                "/topics/all",
                title!!.text.toString(), message!!.text.toString(),
                applicationContext, this@MainActivity
            )
            notificationsSender.SendNotifications()
        })
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val user_token = task.result
                token!!.setText(user_token)
                // Log and toast
            })
        single!!.setOnClickListener(View.OnClickListener {
            val notificationsSender = FcmNotificationsSender(
                token!!.text.toString(),
                title!!.text.toString(), message!!.text.toString(),
                applicationContext, this@MainActivity
            )
            notificationsSender.SendNotifications()
        })
    }
}