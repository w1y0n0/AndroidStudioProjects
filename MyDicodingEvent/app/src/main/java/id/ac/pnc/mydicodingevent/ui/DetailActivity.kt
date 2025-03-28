package id.ac.pnc.mydicodingevent.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import id.ac.pnc.mydicodingevent.databinding.ActivityDetailBinding
import id.ac.pnc.mydicodingevent.utils.convertHtmlToFormattedString
import id.ac.pnc.mydicodingevent.utils.convertStringToFormattedString
import id.ac.pnc.mydicodingevent.utils.gotoUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val eventId = intent.getStringExtra(EXTRA_ID)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        eventId.let { id ->
            viewModel.getDetailData(id!!)
        }

        viewModel.listEventDetail.observe(this) { result ->
            binding.apply {
                eventImage.load(result.mediaCover)
                eventCategory.text = result.category
                eventTitle.text = result.name
                eventOrganizer.text = result.ownerName
//                eventWebView.loadDataWithBaseURL(null, result.description!!, "text/html", "UTF-8", null)
//                eventWebView.settings.apply {
//                    this.builtInZoomControls = false
//                    this.setSupportZoom(false)
//                }
//                eventWebView.setInitialScale(100)
                eventDescriptions.text =
                    result.description?.let { convertHtmlToFormattedString(it) }

                eventExpired.text = result.endTime?.let { convertStringToFormattedString(it) }
                eventQuota.text = result.quota.toString()

                eventRegisterButton.setOnClickListener {
                    gotoUrl(
                        this@DetailActivity,
                        result.link
                    )
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}