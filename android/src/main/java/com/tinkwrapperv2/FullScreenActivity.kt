package com.tink.link.sample.fullscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.ThemedReactContext
import com.tink.link.core.base.Tink
import com.tink.link.core.data.request.accountcheck.AccountCheckCreateReport
import com.tink.link.core.data.request.common.Market
import com.tink.link.core.data.request.configuration.Configuration
import com.tink.link.core.data.request.transactions.ConnectAccountsForOneTimeAccess
import com.tink.link.core.data.response.error.TinkError
import com.tink.link.core.data.response.success.accountCheck.TinkAccountCheckSuccess
import com.tink.link.core.data.response.success.transactions.TinkTransactionsSuccess
import com.tink.link.core.navigator.FullScreen
import com.tink.link.core.themes.TinkAppearance
import com.tink.link.core.themes.TinkAppearanceXml
import com.tink.link.sdk.R
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType

//import com.tinkwrapperv2.TinkViewManager.React

/**
 * This class is to show how to implement Transactions as Fullscreen in XML.
 *
 * */
class FullScreenActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val reactContext = TinkViewManager.ReactContextSingleton.reactContext
    // Add XML view.
    setContentView(com.tinkwrapperv2.R.layout.fps_view)
    // Get the nativeId of the ReactContext from the intent.

    // Restore SDK state if possible
    savedInstanceState?.let {
      Tink.restoreState(it)
    }

    // Present the SDK.
    showTransactionsWithOneTimeAccess()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    // Save SDK state to bundle
    Tink.saveState(outState)
  }
  companion object {
    const val REQUEST_CODE_FULLSCREEN = 1001
    const val RESULT_KEY = "result"
  }



  // Example of one time access to Transactions presented in a fullscreen.
  // TODO: For launching other flows, please find implementation guidance in this link.
  private fun showTransactionsWithOneTimeAccess() {
    val configuration = Configuration(
      clientId = "2b40d76678a2415eb4be14a415685db2",
      redirectUri = "https://console.tink.com/callback")
    val fullScreen = FullScreen(getTinkAppearance())
    // More parameters can be added to ConnectAccountsForOneTimeAccess().
    val oneTimeAccess = AccountCheckCreateReport(Market.SE)



    // Call this method to trigger the flow.
    Tink.AccountCheck.createReport(
      this,
      configuration,
      oneTimeAccess,
      fullScreen,
      { success: TinkAccountCheckSuccess ->
        Log.d(TAG, "code = ${success.accountVerificationReportId}")
        val params = Arguments.createMap()
        params.putString("id", success.accountVerificationReportId)
        sendEvent("onSuccess", params)

      },
      { error: TinkError ->
        Log.d(TAG, "error message = ${error.errorDescription}")
        val params = Arguments.createMap()
        params.putString("error", error.errorDescription)
        sendEvent("onError", params)
      }
    )
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    Log.d(TAG, "onActivityResult  = $data")
    super.onActivityResult(requestCode, resultCode, data)
    Log.d(TAG, "onActivityResult 2 = $data")
    if (requestCode == REQUEST_CODE_FULLSCREEN) {
      if (resultCode == Activity.RESULT_OK) {
        data?.let { intent ->
          val result = intent.getStringExtra("result")
          val params = Arguments.createMap()
          params.putString("result", result)
          sendEvent("onSuccess", params)
        }
      } else if (resultCode == Activity.RESULT_CANCELED) {
        val params = Arguments.createMap()
        params.putString("error", "Fullscreen Activity was cancelled")
        sendEvent("onError", params)
      }
    }
  }

  private fun sendEvent(eventName: String, params: WritableMap) {
    val reactContext = TinkViewManager.ReactContextSingleton.reactContext
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
  private fun getTinkAppearance(): TinkAppearance {
    return TinkAppearanceXml(
      light = TinkAppearanceXml.ThemeAttributes(
        toolbarColorId = R.color.white,
        windowBackgroundColorId = R.color.white,
        iconBackId = R.drawable.ic_cross,
        iconBackTint = R.color.black,
        iconBackDescriptionId = R.string.default_title,
        iconCloseId = R.drawable.ic_cross,
        iconCloseTint = R.color.black,
        iconCloseDescriptionId = R.string.default_title,
        toolbarTitleObj = TinkAppearanceXml.ToolbarTitle(),

      ),
      dark = TinkAppearanceXml.ThemeAttributes(
        toolbarColorId = R.color.black,
        windowBackgroundColorId = R.color.white,
        iconBackId = R.drawable.ic_cross,
        iconBackTint = R.color.white,
        iconBackDescriptionId = R.string.default_title,
        iconCloseId = R.drawable.ic_cross,
        iconCloseTint = R.color.white,
        iconCloseDescriptionId = R.string.default_title,
        toolbarTitleObj = TinkAppearanceXml.ToolbarTitle(),


      )
    )
  }
}

private const val TAG = "tink-sdk-sample"

