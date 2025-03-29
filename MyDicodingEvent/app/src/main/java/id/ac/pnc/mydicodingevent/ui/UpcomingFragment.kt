package id.ac.pnc.mydicodingevent.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.pnc.mydicodingevent.data.response.ListEventsItem
import id.ac.pnc.mydicodingevent.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[UpcomingViewModel::class.java]
        viewModel.getUpcomingEventList("")
        viewModel.listEventItem.observe(viewLifecycleOwner){ item ->
            setEventData(item)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setEventData(listEvent: List<ListEventsItem>) {
        val adapter = EventAdapter()
        adapter.submitList(listEvent)
        binding.apply {
            rvEvent.setHasFixedSize(true)
            rvEvent.layoutManager = LinearLayoutManager(requireActivity())
            rvEvent.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}