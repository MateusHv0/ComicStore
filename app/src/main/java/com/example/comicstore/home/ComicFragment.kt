package com.example.comicstore.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ComicStore.R
import com.example.ComicStore.databinding.FragmentComicListBinding
import com.example.comicstore.ComicViewModel


class ComicFragment : Fragment(), ComicItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by hiltNavGraphViewModels<ComicViewModel>(R.id.comic_nav)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentComicListBinding.inflate(inflater)
        val view = binding.root
        val recyclerView = binding.list

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = MyItemRecyclerViewAdapter(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ComicFragment.adapter
        }

        initObservers()

        return view
    }
    override fun onItemSelected(position: Int) {
        viewModel.onComicSelected(position)
    }

    private fun initObservers() {
        viewModel.comicListLivedata.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })
        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentNotHandled()?.let {
                val action = ComicFragmentDirections.actionComicFragmentToComicDetailsFragment()
                findNavController().navigate(action)
            }
        })
    }
}