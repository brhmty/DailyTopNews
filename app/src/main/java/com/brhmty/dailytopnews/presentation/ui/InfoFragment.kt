package com.brhmty.dailytopnews.presentation.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.brhmty.dailytopnews.R
import com.brhmty.dailytopnews.data.model.Article
import com.brhmty.dailytopnews.databinding.FragmentInfoBinding
import com.brhmty.dailytopnews.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class InfoFragment : Fragment() {
private lateinit var binding: FragmentInfoBinding
private lateinit var newsViewModel: NewsViewModel
private lateinit var article: Article
private lateinit var articleUrl: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).viewModel
        binding = FragmentInfoBinding.bind(view)
        newsViewModel.mutableSelectedArticle.observe(viewLifecycleOwner){
            article = it
            articleUrl = article.url.toString()
            binding.wvInfo.apply {
                webViewClient =object: WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        binding.progressBarInfo.visibility = View.VISIBLE
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.progressBarInfo.visibility = View.INVISIBLE
                    }
                }
                if(article.url != null)
                    loadUrl(articleUrl)
            }
        }
            binding.fabInfo.setOnClickListener {
                newsViewModel.saveNews(article)
                Snackbar.make(view, "Saved Successfully.", Snackbar.LENGTH_SHORT).show()
            }
    }


}


