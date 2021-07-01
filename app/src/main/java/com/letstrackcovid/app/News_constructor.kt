package com.letstrackcovid.app

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class News_constructor constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: News_constructor? = null
        fun getInstance(context:Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: News_constructor(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}