package id.ac.pnc.mydicodingevent.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.pnc.mydicodingevent.data.remote.response.ListEventsItem
import id.ac.pnc.mydicodingevent.databinding.FragmentFavoriteBinding
import id.ac.pnc.mydicodingevent.ui.ViewModelFactory
import id.ac.pnc.mydicodingevent.ui.adapter.ListEventAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getFavoriteEvents()
        }

        viewModel.favoriteEvent.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                binding.progressBar.visibility = View.GONE
                binding.rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
                val items = arrayListOf<ListEventsItem>()
                result.forEach {
                    items.add(
                        ListEventsItem(
                            id = it.id,
                            name = it.name,
                            summary = it.summary,
                            imageLogo = it.imageLogo!!,
                            mediaCover = it.mediaCover!!,
                            registrants = 0,
                            link = "",
                            description = "",
                            ownerName = "",
                            cityName = "",
                            quota = 0,
                            beginTime = "",
                            endTime = "",
                            category = ""
                        )
                    )
                }
                val adapter = ListEventAdapter(items)
                binding.rvFavorite.adapter = adapter
                binding.errorPage.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTryAgain.setOnClickListener {
            viewModel.getFavoriteEvents()
            binding.errorPage.visibility = View.GONE
        }

        return root
    }
}