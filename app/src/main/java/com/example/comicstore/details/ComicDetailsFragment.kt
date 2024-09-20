package com.example.comicstore.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.ComicStore.databinding.FragmentComicDetailsBinding
import com.example.comicstore.ComicViewModel
import com.example.ComicStore.R

class ComicDetailsFragment : Fragment() {
    private val viewModel by hiltNavGraphViewModels<ComicViewModel>(R.id.comic_nav)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentComicDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_comic_details, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}