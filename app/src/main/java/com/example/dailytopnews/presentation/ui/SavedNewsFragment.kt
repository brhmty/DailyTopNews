package com.example.dailytopnews.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytopnews.R
import com.example.dailytopnews.databinding.FragmentSavedNewsBinding
import com.example.dailytopnews.presentation.adapter.SavedNewsAdapter
import com.example.dailytopnews.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var lifecycleOwner: LifecycleOwner
    private var idSavedNews: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)

        if(view.findViewTreeLifecycleOwner() != null)
            lifecycleOwner = view.findViewTreeLifecycleOwner()!!

        viewModel = (activity as MainActivity).viewModel
        viewModel.fragmentSelector(idSavedNews)

        savedNewsAdapter = (activity as MainActivity).savedNewsAdapter

        savedNewsAdapter.setOnItemClickListener {
            viewModel.selectedArticle(it)
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_infoFragment,
            )
        }
            viewSavedArticles()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
              return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = savedNewsAdapter.differ.currentList[position]
                viewModel.deleteSavedNews(article)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveNews(article)
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    private fun viewSavedArticles() {
           viewModel.getSavedNews().observe(lifecycleOwner) {
               savedNewsAdapter.differ.submitList(it)
           }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.progressBarSavedNews.visibility = View.VISIBLE
        binding.rvSavedNews.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(activity)
            binding.progressBarSavedNews.visibility = View.INVISIBLE
        }
    }

}