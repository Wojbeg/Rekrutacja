package com.wojbeg.spacelaunchnews.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.wojbeg.spacelaunchnews.R
import com.wojbeg.spacelaunchnews.databinding.FragmentArticleBinding
import com.wojbeg.spacelaunchnews.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: NewsViewModel by activityViewModels()
    val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        val article = args.article

        binding.webView.apply {
            webViewClient = object : WebViewClient(){
                override fun onReceivedError(webView: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                    try {
                        webView?.stopLoading()
                    } catch (e: Exception) {
                    }

                    if (webView?.canGoBack() == true) {
                        webView.goBack();
                    }

                    webView?.loadUrl("about:blank")

                    val alertDialog = AlertDialog.Builder(view.context)
                    .setTitle("Can't load website")
                    .setMessage(getString(R.string.no_internet))
                    .setPositiveButton("OK") {_, _ ->
                        activity?.onBackPressed();
                    }.setCancelable(false)
                        .create()

                    alertDialog.show()
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    handler!!.proceed()
                }
            }
            settings.apply {
                cacheMode = WebSettings.LOAD_DEFAULT
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view, getString(R.string.article_save), Snackbar.LENGTH_LONG)
                .apply {
                    setAction(getString(R.string.undo)){
                        viewModel.deleteArticle(article)
                        binding.fab.visibility = View.VISIBLE
                    }
                    show()
                }
            binding.fab.visibility = View.GONE
        }

        viewModel.setPrevFragmentNum(4)

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}