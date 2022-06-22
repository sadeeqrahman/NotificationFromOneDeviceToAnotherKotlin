package com.dxbapps.notificationfromonedevicetoanother

import android.app.Activity
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.dxbapps.notificationfromonedevicetoanother.R
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.VolleyError
import com.android.volley.AuthFailureError
import com.android.volley.Response
import org.json.JSONException
import java.util.HashMap

class FcmNotificationsSender(
    var userFcmToken: String,
    var title: String,
    var body: String,
    var mContext: Context,
    var mActivity: Activity
) {
    private var requestQueue: RequestQueue? = null
    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey =
        "AAAA6M9bBBk:APA91bErwFI-hkkW0kDIwt5UKxQDN5FmfxqoLN9dJeC0eLxRNBAzL2GF-OOruzeX8GuNhhcOrxnItqdr5VrTHS_dOLgK2_kzDo1wzi_JruWNkrG6v-e2Qg2zYyymL8CbuIksuEW5LKWh"

    fun SendNotifications() {
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try {
            mainObj.put("to", userFcmToken)
            val notiObject = JSONObject()
            notiObject.put("title", title)
            notiObject.put("body", body)
            notiObject.put(
                "icon",
                R.drawable.ic_launcher_background
            ) // enter icon that exists in drawable only
            mainObj.put("notification", notiObject)
            val request: JsonObjectRequest =
                object : JsonObjectRequest(Method.POST, postUrl, mainObj, Response.Listener {
                    // code run is got response
                }, Response.ErrorListener {
                    // code run is got error
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val header: MutableMap<String, String> = HashMap()
                        header["content-type"] = "application/json"
                        header["authorization"] = "key=$fcmServerKey"
                        return header
                    }
                }
            requestQueue!!.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}