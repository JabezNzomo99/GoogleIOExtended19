package com.jabezmagomere.movies.util

import android.content.Context
import android.net.ConnectivityManager

class Util{
  companion object {
    private fun isOnline(context: Context):Boolean{
      val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo = connectionManager.activeNetworkInfo
      return networkInfo!=null && networkInfo.isConnected
    }
  }
}