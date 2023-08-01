package com.tinkwrapperv2

import androidx.compose.material.lightColors
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.tink.link.core.base.Tink
import com.tink.link.core.data.request.common.Market
import com.tink.link.core.data.request.configuration.Configuration
import com.tink.link.core.data.request.transactions.ConnectAccountsForOneTimeAccess
import com.tink.link.core.data.response.error.TinkError
import com.tink.link.core.data.response.success.accountCheck.TinkAccountCheckSuccess
import com.tink.link.core.features.accountCheck.TinkAccountCheck
import com.tink.link.core.navigator.FullScreen
import com.tink.link.core.navigator.LaunchMode
import com.tink.link.core.themes.TinkAppearance
import com.tink.link.core.themes.TinkAppearanceXml


class TinkWrapperV2Module(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {


  override fun getName(): String {
    return NAME
  }
  val configuration = Configuration(
    clientId = "{YOUR_CLIENT_ID}",
    redirectUri = "{YOUR_REDIRECT_URI}"
  )
  val request = ConnectAccountsForOneTimeAccess(
    market = Market.SE
  )

  companion object {
    const val NAME = "TinkWrapperV2"
  }
}

private fun TinkAccountCheck.createReport(activity: ReactApplicationContext?, configuration: Configuration, request: ConnectAccountsForOneTimeAccess, launchMode: FullScreen, onSuccess: (TinkAccountCheckSuccess) -> Unit, onError: (TinkError) -> Unit) {

}






