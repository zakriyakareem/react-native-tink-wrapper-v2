package com.tinkwrapperv2

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OnCustomContainerCreatedListener
import com.tink.moneymanagerui.OnFragmentViewCreatedListener
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration

class MainActivity : FragmentActivity(), OnFragmentViewCreatedListener {

  private lateinit var currentFinanceOverviewFragment: FinanceOverviewFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val rootView = findViewById<ViewGroup>(android.R.id.content)

//    // Get the screen width and height
//    val displayMetrics = resources.displayMetrics
//    val screenWidth = displayMetrics.widthPixels
//    val screenHeight = displayMetrics.heightPixels
//
//    // Calculate 80% of the screen width and height
//    val targetWidth = (screenWidth * 0.8).toInt()
//    val targetHeight = (screenHeight * 0.8).toInt()
//
//    // Find the FrameLayout by its ID
//    val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
//
//    // Set the width and height of the FrameLayout to 80% of the screen size
//    val layoutParams = FrameLayout.LayoutParams(targetWidth, targetHeight)
//    fragmentContainer.layoutParams = layoutParams

    val environment: Environment = Environment.Production
    val clientId: String = "2b40d76678a2415eb4be14a415685db2"
    val accessToken: String = "eyJhbGciOiJFUzI1NiIsImtpZCI6ImFlMmI0MzNkLWFhYmYtNDMzZC1iZTM5LTZhYjNmOTBjNDZjMCIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQ3NDU5NDQsImlhdCI6MTY4NDczODc0NCwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiI0ZWEwZWJmYS01NWQwLTQ0NWYtOGM2Yy0yYzMwZTQyOTVhNWEiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbInByb3ZpZGVyczpyZWFkIiwidXNlcjpjcmVhdGUiLCJhdXRob3JpemF0aW9uOnJlYWQiLCJjcmVkZW50aWFsczp3cml0ZSIsImF1dGhvcml6YXRpb246Z3JhbnQiLCJjcmVkZW50aWFsczpyZWZyZXNoIiwidXNlcjpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCJdLCJzdWIiOiJ0aW5rOi8vYXV0aC91c2VyLzEzZTQxMGZhNzc0ZTQ2MjdiMWNkMjI2MWNmMTBhMjFmIiwidGluazovL2FwcC9pZCI6IjVlNzNmZGNkMWEwYzRiOTNiYWEzMWM3OTZkMTVhZWEwIiwidGluazovL2FwcC92ZXJpZmllZCI6ImZhbHNlIiwidGluazovL2NsaWVudC9pZCI6IjJiNDBkNzY2NzhhMjQxNWViNGJlMTRhNDE1Njg1ZGIyIn0.O3kwEuo7hli28BU3dT2RZDrr-tu21SVmJd-mK_H8L4N2cO3hbGKHZSNi33iQKnBnu-jddbwHhjWzhIJbfEl9YQ"

    val config = TinkConfiguration(
      environment,
      clientId,
      Uri.parse("https://console.tink.com/callback")
    )

    Tink.init(config, applicationContext)

    supportFragmentManager.beginTransaction().add(
      R.id.fragmentContainer,
      FinanceOverviewFragment.newInstance(
        accessToken = accessToken,
        styleResId = R.style.TinkStyle_ChewingGum,
       // tracker = LogTracker(),
        overviewFeatures = getOverviewFeatures(),
        isEditableOnPendingTransaction = true,
        isTransactionDetailsEnabled = true,
        fragmentViewCreatedListener = this@MainActivity
      ).also {
        currentFinanceOverviewFragment = it
      }
    ).commit()
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
          containerViewId = R.id.myapp_custom_view,
          width = FrameLayout.LayoutParams.MATCH_PARENT,
          height = FrameLayout.LayoutParams.MATCH_PARENT,

        ),
        OverviewFeature.Accounts(),
//        OverviewFeature.LatestTransactions,
//        OverviewFeature.Budgets
      )
    )

  /**
   * Add your own custom component to the finance overview
   *
   */
  private fun setupCustomView(viewId: Int) {
    currentFinanceOverviewFragment.getContainerById(
      viewId,
      object : OnCustomContainerCreatedListener {
        override fun onCustomContainerCreated(container: FrameLayout) {
          val customComponent = View.inflate(
            this@MainActivity,
            R.layout.layout_custom_button,
            container
          )

          customComponent.findViewById<Button>(R.id.myapp_custom_button)
            .setOnClickListener {
              Toast.makeText(it.context, "Custom button clicked", Toast.LENGTH_SHORT)
                .show()
            }
        }
      }
    )
  }

  /**
   * Setup custom views inside this method
   * */
  override fun onFragmentViewCreated() {
    setupCustomView(R.id.myapp_custom_view)
    // Add more custom layouts here
  }

  override fun onBackPressed() {
    if (!currentFinanceOverviewFragment.handleBackPress()) {
      super.onBackPressed()
    }
  }
}
