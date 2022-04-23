package com.example.imguram.ui.feed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import coil.request.ImageRequest
import com.example.imguram.R
import com.example.imguram.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private val viewModel: FeedViewModel by viewModels()
    private val feedAdapter = FeedRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feed = arguments?.getString("feed")
        feed?.let {
            viewModel.updateFeed(feed)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //underscore for private values _binding
        val binding = FeedFragmentBinding.inflate(inflater,container,false)
        binding.rvGalleryFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGalleryFeed.adapter = feedAdapter

        viewModel.feed.observe(viewLifecycleOwner){
            it?.forEach { image->
                val request = ImageRequest.Builder(requireContext())
                    .data("https://i.imgur.com/${image?.cover}.jpg")
                    .size(binding.rvGalleryFeed.width)
                    .build()
                Coil.imageLoader(requireContext()).enqueue(request)
            }

            feedAdapter.submitList(it)
//            Toast.makeText(requireContext(),"Downloaded ${it?.size} images",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }


}