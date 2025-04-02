package id.ac.pnc.mydicodingevent.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import id.ac.pnc.mydicodingevent.R
import id.ac.pnc.mydicodingevent.databinding.ActivityDetailBinding
import id.ac.pnc.mydicodingevent.utils.loadImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Event"
        }

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)

        if (savedInstanceState == null) {
            viewModel.getDetailEvent(eventId)
        }

        viewModel.event.observe(this) {
            supportActionBar?.title = it.name

            binding.apply {
                detailBg.loadImage(it.mediaCover)
                binding.detailName.text = it.name
                binding.detailOwnerName.text = getString(R.string.penyelenggara, it.ownerName)
                binding.detailTime.text = it.beginTime
                binding.detailQuota.text =
                    getString(
                        R.string.sisa_kuota,
                        String.format(Locale.getDefault(), "%d", it.quota - it.registrants)
                    )

                binding.detailDesc.text = HtmlCompat.fromHtml(
                    it.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.detailRegister.visibility = View.VISIBLE
                // Format sesuai dengan string "2024-05-17 17:00:00"
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                // Parsing endTime dengan format yang benar
                val endTime = LocalDateTime.parse(it.endTime, formatter)
                // Bandingkan dengan waktu sekarang (apakah waktu event sudah berakhir?)
                // isBefore: Cek apakah "endTime < waktu sekarang"
                if (endTime.isBefore(LocalDateTime.now())) {
                    binding.detailRegister.text = getString(R.string.pendaftaran_tutup)
                }

                binding.errorPage.visibility = View.GONE
            }
        }

        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        binding.detailRegister.setOnClickListener {
            val url = "https://www.dicoding.com/events/${eventId}"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }

        viewModel.errorMessage.observe(this) {
            binding.errorPage.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            binding.errorMessage.text = it
        }

        binding.btnTryAgain.setOnClickListener {
            viewModel.getDetailEvent(eventId)
            binding.errorPage.visibility = View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()

        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}