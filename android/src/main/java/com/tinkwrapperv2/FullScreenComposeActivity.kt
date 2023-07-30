import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OnCustomContainerCreatedListener
import com.tink.moneymanagerui.OnFragmentViewCreatedListener
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration

class FullScreenComposeActivity : AppCompatActivity(), OnFragmentViewCreatedListener {

  private lateinit var currentFinanceOverviewFragment: FinanceOverviewFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(com.tink.link.ui.R.layout.tink_activity_main)

    val environment: Environment = Environment.Production
    val clientId: String = ""
    val accessToken: String = ""

    val config = TinkConfiguration(
      environment,
      clientId,
      Uri.parse("https://localhost:3000/callback")
    )

    Tink.init(config, applicationContext)

    supportFragmentManager.beginTransaction().add(
      com.tink.link.ui.R.id.fragment_container_view_tag,
      FinanceOverviewFragment.newInstance(
        accessToken = accessToken,
        styleResId = com.tink.link.ui.R.style.TinkLinkUiStyle,
        overviewFeatures = getOverviewFeatures(),
        isEditableOnPendingTransaction = true,
        isTransactionDetailsEnabled = true,
        fragmentViewCreatedListener = this@FullScreenComposeActivity
      ).also {
        currentFinanceOverviewFragment = it
      }
    ).commit()
  }

  private fun getOverviewFeatures() =
    OverviewFeatures(
      features = listOf(
        OverviewFeature.ActionableInsights,
        OverviewFeature.Statistics(
          listOf(
            StatisticType.EXPENSES,
            StatisticType.INCOME
          )
        ),
        OverviewFeature.CustomContainerView(
          containerViewId = com.tink.link.ui.R.id.container,
          width = FrameLayout.LayoutParams.MATCH_PARENT,
          height = FrameLayout.LayoutParams.WRAP_CONTENT
        ),
        OverviewFeature.Accounts(),
        OverviewFeature.LatestTransactions,
        OverviewFeature.Budgets
      )
    )

  private fun setupCustomView(viewId: Int) {
    currentFinanceOverviewFragment.getContainerById(
      viewId,
      object : OnCustomContainerCreatedListener {
        override fun onCustomContainerCreated(container: FrameLayout) {
          val customComponent = View.inflate(
            this@FullScreenComposeActivity,
            com.tink.link.ui.R.layout.custom_dialog,
            container
          )

          customComponent.findViewById<Button>(com.tink.link.ui.R.id.custom)
            .setOnClickListener {
              Toast.makeText(it.context, "Custom button clicked", Toast.LENGTH_SHORT)
                .show()
            }
        }
      }
    )
  }

  override fun onFragmentViewCreated() {
    setupCustomView(com.tink.link.ui.R.id.custom)
  }

  override fun onBackPressed() {
    if (!currentFinanceOverviewFragment.handleBackPress()) {
      super.onBackPressed()
    }
  }

  // Add an exported static function to start the activity from other classes
  companion object {
    fun start(context: Context) {
      val intent = Intent(context, FullScreenComposeActivity::class.java)
      context.startActivity(intent)
    }
  }
}
