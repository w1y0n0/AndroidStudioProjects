package com.wiyono.mystoryapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiyono.mystoryapp.R
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.data.remote.response.StoryItem
import com.wiyono.mystoryapp.databinding.ActivityMainBinding
import com.wiyono.mystoryapp.ui.adapter.StoryAdapter
import com.wiyono.mystoryapp.ui.auth.AuthActivity
import com.wiyono.mystoryapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyAdapter = StoryAdapter()

        mainViewModel = ViewModelProvider(this, ViewModelFactory(this))[MainViewModel::class.java]

        setSupportActionBar(binding.toolbar)

        getRecycle()
        onClickCallback()
        setUpViewModel()
        addStory()
        setRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btn_logout -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Logout")
            setMessage("Apakah Anda yakin ingin keluar ?")
            setPositiveButton("Ya") { _, _ ->
                mainViewModel.logout()

                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
            create().show()
        }
    }

    private fun setRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            mainViewModel.refreshData()
        }
    }

    private fun getRecycle() {
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = storyAdapter
        }
    }

    private fun onClickCallback() {
        storyAdapter.setOnItemCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryItem) {
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
//                startActivity(intent)
            }

        })
    }

    private fun setUpViewModel() {
        mainViewModel.getAllStory().observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    binding.swipeRefresh.isRefreshing = false
                }
                is Result.Loading -> { showLoading(true) }
                is Result.Success -> {
                    showLoading(false)
                    storyAdapter.submitList(result.data)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun addStory() {
        binding.fab.setOnClickListener {
//            val intent = Intent(this@MainActivity, UploadStoryActivity::class.java)
//            launcherCamera.launch(intent)
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            mainViewModel.refreshData()
        }
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}