import android.net.Uri
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.annotations.ReactPropGroup
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration

class TinkViewManager(
  private val reactContext: ReactApplicationContext
) : ViewGroupManager<FrameLayout>() {

  private val environment: Environment = Environment.Production

  private var propToken: String? = null
  private var propClientId: String? = null
  private var propCallbackUrl: String = ""
  private var propRedirectUrl: String = ""

  private var propWidth: Int? = null
  private var propHeight: Int? = null

  companion object {
    const val REACT_CLASS = "TinkView"
    private const val COMMAND_CREATE = 1
  }

  override fun getName(): String {
    return REACT_CLASS
  }

  /**
   * Return a FrameLayout which will later hold the Fragment
   */
  override fun createViewInstance(reactContext: ThemedReactContext) =
    FrameLayout(reactContext)


  /**
   * Map the "create" command to an integer
   */
  override fun getCommandsMap() = mapOf("create" to COMMAND_CREATE)

  @ReactPropGroup(names = ["width", "height"], customType = "Style")
  fun setStyle(view: FrameLayout, index: Int, value: Int) {
    if (index == 0) propWidth = value
    if (index == 1) propHeight = value
  }

  @ReactProp(name = "token")
  fun setToken(view: FrameLayout, token: String) {
    propToken = token
  }

  @ReactProp(name = "clientId")
  fun setClientId(view: FrameLayout, clientId: String) {
    propClientId = clientId
  }

  @ReactProp(name = "callbackUrl")
  fun setCallbackUrl(view: FrameLayout, callbackUrl: String) {
    propCallbackUrl = callbackUrl
  }

  @ReactProp(name = "redirectUrl")
  fun setRedirectUrl(view: FrameLayout, redirectUrl: String) {
    propRedirectUrl = redirectUrl
  }

  /**
   * Handle "create" command (called from JS) and call createFragment method
   */
  override fun receiveCommand(
    root: FrameLayout,
    commandId: String,
    args: ReadableArray?
  ) {
    super.receiveCommand(root, commandId, args)
    val reactNativeViewId = requireNotNull(args).getInt(0)

    when (commandId.toInt()) {
      COMMAND_CREATE -> createFragment(root, reactNativeViewId)
    }
  }

  /**
   * Replace your React Native view with a custom fragment
   */
  private fun createFragment(root: FrameLayout, reactNativeViewId: Int) {
    val parentView = root.findViewById<ViewGroup>(reactNativeViewId)
    setupLayout(parentView)

    val config = TinkConfiguration(
      environment,
      requireNotNull(propClientId),
      Uri.parse(propRedirectUrl),
      propCallbackUrl
    )

    Tink.init(config, reactContext)
    val fragment = FinanceOverviewFragment.newInstance(
      accessToken = requireNotNull(propToken),
      styleResId = com.tinkwrapperv2.R.style.TinkStyle_ChewingGum,
      overviewFeatures = getOverviewFeatures(),
      isEditableOnPendingTransaction = true,
      isTransactionDetailsEnabled = true,
    )

    val activity = reactContext.currentActivity as FragmentActivity
    activity.supportFragmentManager
      .beginTransaction()
      .replace(reactNativeViewId, fragment, reactNativeViewId.toString())
      .commit()
  }

  private fun setupLayout(view: View) {
    Choreographer.getInstance().postFrameCallback(object: Choreographer.FrameCallback {
      override fun doFrame(frameTimeNanos: Long) {
        manuallyLayoutChildren(view)
        view.viewTreeObserver.dispatchOnGlobalLayout()
        Choreographer.getInstance().postFrameCallback(this)
      }
    })
  }


  /**
   * Layout all children properly
   */
  private fun manuallyLayoutChildren(view: View) {
    // propWidth and propHeight coming from react-native props
    val width = requireNotNull(propWidth)
    val height = requireNotNull(propHeight)

    view.measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY))

    view.layout(0, 0, width, height)
  }

  fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }


  /**
   * Choose which features to display as well as in what order
   *
   */
  private fun getOverviewFeatures() =
    OverviewFeatures(
      features =
      listOf(
        OverviewFeature.ActionableInsights,
        OverviewFeature.Statistics(
          listOf(
            StatisticType.EXPENSES,
            StatisticType.INCOME
          )
        ),
        OverviewFeature.CustomContainerView(
          containerViewId = com.tinkwrapperv2.R.id.myapp_custom_view,
          width = FrameLayout.LayoutParams.MATCH_PARENT,
          height = FrameLayout.LayoutParams.MATCH_PARENT,

          ),
        OverviewFeature.Accounts(),
      )
    )
}
