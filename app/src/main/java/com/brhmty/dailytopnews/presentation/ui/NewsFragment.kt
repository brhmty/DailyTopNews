package com.brhmty.dailytopnews.presentation.ui


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brhmty.dailytopnews.R
import com.brhmty.dailytopnews.databinding.FragmentNewsBinding
import com.brhmty.dailytopnews.databinding.StandardBottomSheetBinding
import com.brhmty.dailytopnews.domain.util.Resource
import com.brhmty.dailytopnews.presentation.adapter.NewsAdapter
import com.brhmty.dailytopnews.presentation.adapter.SearchedNewsAdapter
import com.brhmty.dailytopnews.presentation.viewmodel.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsBinding: FragmentNewsBinding
    private lateinit var sheetBinding: StandardBottomSheetBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchedNewsAdapter: SearchedNewsAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheet: LinearLayout
    private lateinit var country: String
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private var idNews: Int = 0
    private var pageSize: Int = 50


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsBinding = FragmentNewsBinding.bind(view)
        bottomSheet = view.findViewById(R.id.bottomSheet)
        sheetBinding = StandardBottomSheetBinding.bind(bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        searchedNewsAdapter= (activity as MainActivity).searchedNewsAdapter
        alertDialogBuilder = AlertDialog.Builder(context)

        viewModel.fragmentSelector(idNews)

        //to prevent turns lifecycleowner null exception
        if(view.findViewTreeLifecycleOwner() != null)
            lifecycleOwner = view.findViewTreeLifecycleOwner()!!

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)?: return
        country = sharedPref.getString(R.string.shared_preference_country.toString(), "tr")!!

        newsAdapter.setOnItemClickListener {
            viewModel.selectedArticle(it)
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment
            )
        }
        searchedNewsAdapter.setOnItemClickListener {
            viewModel.selectedArticle(it)
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment
            )
        }

        sheetBinding.btnSheetClose.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

            viewNewsList()
            setSearchView()
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, pageSize)
        viewModel.getHeadLines.observe(lifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    response.error?.let {
                        if(it == "InternetError"){
                            alertDialogBuilder.setMessage("Check Your Internet Connection")
                            alertDialogBuilder.setNeutralButton("Retry") { _, _ ->
                                viewNewsList()
                            }.show()
                        }else
                            Toast.makeText(activity, "Something is wrong.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        initNewsFragmentRecyclerView()
    }

    private fun viewSearchedNewsList() {
            viewModel.getSearchedHeadlines.observe(lifecycleOwner){ response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            searchedNewsAdapter.differ.submitList(it.articles)
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Failure -> {
                        hideProgressBar()
                        response.error?.let {
                            if(it == "InternetError"){
                                alertDialogBuilder.setMessage("Check Your Internet Connection")
                                alertDialogBuilder.setNeutralButton("Retry") { _, _ ->
                                    viewNewsList()
                                }.show()
                            }else
                                Toast.makeText(activity, "Something is wrong.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setSearchView(){
            newsBinding.svNews.setOnQueryTextListener(
                object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.getSearchedNewsHeadLines(country, query.toString(), pageSize)
                        initBottomSheetRecyclerView()
                        viewSearchedNewsList()
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
            newsBinding.svNews.setOnCloseListener {
                    viewNewsList()

                false
            }
    }

    private fun initNewsFragmentRecyclerView() {
            newsBinding.rvNews.adapter = newsAdapter
            newsBinding.rvNews.layoutManager = LinearLayoutManager(activity)
    }

    private fun initBottomSheetRecyclerView(){
            sheetBinding.rvSheet.adapter =  searchedNewsAdapter
            sheetBinding.rvSheet.layoutManager = LinearLayoutManager(activity)
            bottomSheetBehavior.isDraggable = false
    }

    private fun showProgressBar(){
        newsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        newsBinding.progressBar.visibility = View.INVISIBLE
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        newsBinding.svNews.setQuery("", true)
    }
}