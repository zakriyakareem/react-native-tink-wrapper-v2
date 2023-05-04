import android.view.View
import androidx.annotation.NonNull
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.tink.link.core.data.request.common.Market
import com.tink.link.core.data.request.configuration.Configuration
import com.tink.link.core.data.request.transactions.ConnectAccountsForOneTimeAccess
import com.tink.link.core.features.accountCheck.TinkAccountCheck
import com.tink.link.core.navigator.FullScreen
import android.content.Intent
import com.tink.link.sample.fullscreen.FullScreenActivity
import com.tink.link.sample.fullscreen.FullScreenComposeActivity

class TinkViewManager : SimpleViewManager<View>() {

  companion object {
    const val REACT_CLASS = "TinkView"
  }

  override fun getName(): String {
    return REACT_CLASS
  }


@NonNull
override fun createViewInstance(reactContext: ThemedReactContext): View {
  val intent = Intent(reactContext, FullScreenActivity::class.java)
  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
  reactContext.startActivity(intent)

  // You can return any view here, since the activity will be displayed on top of it
  return View(reactContext)
}

}
