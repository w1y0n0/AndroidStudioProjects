package id.ac.pnc.mydicodingevent.ui.finished

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.ac.pnc.mydicodingevent.ListEventAdapter
import id.ac.pnc.mydicodingevent.R
import id.ac.pnc.mydicodingevent.databinding.FragmentFinishedBinding
import id.ac.pnc.mydicodingevent.utils.GridSpacingItemDecoration

class FinishedFragment : Fragment() {
    private var _binding: FragmentFinishedBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FinishedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listEvent.observe(viewLifecycleOwner) {

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            binding.rvFinished.layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)

            val adapter = ListEventAdapter(it, horizontal = true, disableEllipsize = true)
            binding.rvFinished.adapter = adapter

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            binding.rvFinished.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

            binding.errorPage.visibility = View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.errorPage.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            binding.errorMessage.text = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (savedInstanceState == null) {
            viewModel.getEvents()
        }

        binding.btnTryAgain.setOnClickListener {
            viewModel.getEvents()
            binding.errorPage.visibility = View.GONE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}