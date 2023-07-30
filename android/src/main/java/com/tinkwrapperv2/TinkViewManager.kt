import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.tink.link.core.themes.TinkAppearance
import com.tink.link.core.themes.TinkAppearanceXml
import com.tink.link.sample.fullscreen.FullScreenActivity
import com.tink.link.sdk.R
import com.tinkwrapperv2.MainActivity

class TinkViewManager : SimpleViewManager<View>() {
  private var context: Context? = null
  object ReactContextSingleton {
    lateinit var reactContext: ThemedReactContext
  }
  companion object {
    const val REACT_CLASS = "TinkView"
    lateinit var reactContext: ReactContext
    const val REQUEST_CODE_FULLSCREEN = 1001
  }

  override fun getName(): String {
    return REACT_CLASS
  }


  @NonNull
  override fun createViewInstance(reactContext: ThemedReactContext): View {
    ReactContextSingleton.reactContext = reactContext
    val intent = Intent(reactContext, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    reactContext.startActivityForResult(intent, REQUEST_CODE_FULLSCREEN, null)
    return View(reactContext)


  }




  fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }


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


