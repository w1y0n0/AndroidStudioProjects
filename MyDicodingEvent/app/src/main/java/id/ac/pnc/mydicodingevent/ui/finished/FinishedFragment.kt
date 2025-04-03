package id.ac.pnc.mydicodingevent.ui.finished

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.ac.pnc.mydicodingevent.R
import id.ac.pnc.mydicodingevent.ui.adapter.ListEventAdapter
import id.ac.pnc.mydicodingevent.databinding.FragmentFinishedBinding
import id.ac.pnc.mydicodingevent.ui.ViewModelFactory
import id.ac.pnc.mydicodingevent.utils.GridSpacingItemDecoration
import id.ac.pnc.mydicodingevent.utils.Result

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FinishedViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getFinishedEvent(40)
        }

        viewModel.listEvents.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val listEventData = result.data

                        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
                        binding.rvFinished.layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)

                        val adapter = ListEventAdapter(listEventData, horizontal = true, disableEllipsize = true)
                        binding.rvFinished.adapter = adapter

                        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                        binding.rvFinished.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

                        binding.rvFinished.adapter = adapter
                        binding.errorPage.visibility = View.GONE
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorPage.visibility =
                            if (result.error.isNotEmpty()) View.VISIBLE else View.GONE
                        binding.errorMessage.text = result.error
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTryAgain.setOnClickListener {
            viewModel.getFinishedEvent(40)
            binding.errorPage.visibility = View.GONE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}