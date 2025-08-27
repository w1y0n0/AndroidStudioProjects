package com.wiyono.mystoryapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wiyono.mystoryapp.R
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.databinding.ActivityDetailBinding
import com.wiyono.mystoryapp.util.withDateFormat
import com.wiyono.mystoryapp.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this, ViewModelFactory(this))[DetailViewModel::class.java]

        val storyId = intent.getStringExtra(EXTRA_ID)
        storyId?.run(detailViewModel::setStoryId)

        // setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "" // hilangin app_name
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.detailStory.observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val item = result.data
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(item.photoUrl)
                            .into(ivDetailPhoto)

                        Glide.with(this@DetailActivity)
                            .load(R.drawable.outline_account_circle_24)
                            .placeholder(R.drawable.outline_account_circle_24)
                            .into(ivAvatar)

                        tvTitle.text = item.name
                        tvSubtitle.text = item.createdAt?.withDateFormat("shortTime")

                        tvDescription.text = item.description
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    companion object {
        const val EXTRA_ID = "id"
    }
}