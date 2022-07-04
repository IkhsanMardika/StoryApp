package com.karyadika.storyapp.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadika.storyapp.databinding.FragmentHomeBinding
import com.karyadika.storyapp.ui.main.MainActivity
import com.karyadika.storyapp.ui.main.home.adapter.LoadingStateAdapter
import com.karyadika.storyapp.ui.main.home.adapter.StoryAdapter
import com.karyadika.storyapp.ui.main.upload.UploadStoryActivity
import com.karyadika.storyapp.utils.UserViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var storyAdapter: StoryAdapter

    private val homeViewModel: HomeViewModel by viewModels {
        UserViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStories.layoutManager = LinearLayoutManager(requireContext())

        fetchData()
        setListener()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun fetchData() {
        val token = requireActivity().intent.getStringExtra(MainActivity.EXTRA_TOKEN).toString()
        storyAdapter = StoryAdapter()

        homeViewModel.fetchAllStory(token).observe(viewLifecycleOwner) {
            storyAdapter.submitData(lifecycle, it)
        }

        binding.rvStories.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
    }

    private fun setListener() {
        binding.fabCreateStory.setOnClickListener {
            startActivity(Intent(requireContext(), UploadStoryActivity::class.java))
        }
        binding.refreshStory.setOnRefreshListener {
            fetchData()
            storyAdapter.refresh()
            binding.refreshStory.isRefreshing = false
        }
    }

}