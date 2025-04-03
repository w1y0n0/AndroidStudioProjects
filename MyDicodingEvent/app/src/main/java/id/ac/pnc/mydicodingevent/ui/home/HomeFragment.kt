package id.ac.pnc.mydicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.pnc.mydicodingevent.R
import id.ac.pnc.mydicodingevent.ui.adapter.ListEventAdapter
import id.ac.pnc.mydicodingevent.databinding.FragmentHomeBinding
import id.ac.pnc.mydicodingevent.ui.ViewModelFactory
import id.ac.pnc.mydicodingevent.ui.finished.FinishedViewModel
import id.ac.pnc.mydicodingevent.ui.settings.SettingsViewModel
import id.ac.pnc.mydicodingevent.ui.upcoming.UpcomingViewModel
import id.ac.pnc.mydicodingevent.utils.HorizontalSpacingItemDecoration
import id.ac.pnc.mydicodingevent.utils.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val upcomingViewModel: UpcomingViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }
    private val finishedViewModel: FinishedViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }
    private val settingsViewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val limit = 5

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            finishedViewModel.getFinishedEvent(limit)
            upcomingViewModel.getUpcomingEvent(limit)
        }

        settingsViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        getFinishedEvent()
        getUpcomingEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTryAgain.setOnClickListener {
            upcomingViewModel.getUpcomingEvent(limit)
            binding.upcomingErrorPage.visibility = View.GONE
        }
        binding.finishedBtnTryAgain.setOnClickListener {
            finishedViewModel.getFinishedEvent(limit)
            binding.finishedErrorPage.visibility = View.GONE
        }

        return root
    }

    private fun getFinishedEvent() {

        finishedViewModel.listEvents.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.finishedProgressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.finishedProgressBar.visibility = View.GONE
                        val listEventData = result.data
                        binding.rvFinished.layoutManager = LinearLayoutManager(
                            requireActivity(),
                        )
                        val adapter = ListEventAdapter(listEventData)
                        binding.rvFinished.adapter = adapter
                        binding.finishedErrorPage.visibility = View.GONE
                    }

                    is Result.Error -> {
                        binding.finishedProgressBar.visibility = View.GONE
                        binding.finishedErrorPage.visibility =
                            if (result.error.isNotEmpty()) View.VISIBLE else View.GONE
                        binding.errorMessage.text = result.error
                    }
                }
            }
        }

    }

    private fun getUpcomingEvent() {

        upcomingViewModel.listEvents.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.upcomingProgressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.upcomingProgressBar.visibility = View.GONE
                        val listEventData = result.data
                        binding.rvUpcoming.layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        val adapter = ListEventAdapter(listEventData, horizontal = true)
                        binding.rvUpcoming.adapter = adapter

                        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing_8)
                        binding.rvUpcoming.addItemDecoration(HorizontalSpacingItemDecoration(spacingInPixels))

                        binding.upcomingErrorPage.visibility = View.GONE
                    }

                    is Result.Error -> {
                        binding.upcomingProgressBar.visibility = View.GONE
                        binding.upcomingErrorPage.visibility =
                            if (result.error.isNotEmpty()) View.VISIBLE else View.GONE
                        binding.errorMessage.text = result.error
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}