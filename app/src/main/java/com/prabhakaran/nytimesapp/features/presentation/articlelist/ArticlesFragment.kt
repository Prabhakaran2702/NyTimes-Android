package com.prabhakaran.nytimesapp.features.presentation.articlelist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prabhakaran.nytimesapp.common.utils.Constant.Category
import com.prabhakaran.nytimesapp.common.utils.ObjectSerializer
import com.prabhakaran.nytimesapp.databinding.FragmentArticlesBinding
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import com.prabhakaran.nytimesapp.features.data.model.Result
import com.prabhakaran.nytimesapp.features.presentation.articleview.ArticleViewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment(position: Int) : Fragment() {

    val _position = position

    private lateinit var binding: FragmentArticlesBinding

    private val viewModel : ArticlesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticlesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData(Category[_position])
        observeLiveData()

    }


    private fun observeLiveData(){

        viewModel.postLiveData.observe(viewLifecycleOwner){

            when(it){
                is ItemViewState.Loading -> { binding.progressBar.visibility = View.VISIBLE
                    binding.list.visibility = View.GONE
                }
                is ItemViewState.Content -> {

                    binding.progressBar.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE

                    if (binding.list is RecyclerView) {
                        with(binding.list) {

                            layoutManager =  LinearLayoutManager(context)
                            adapter = MyItemRecyclerViewAdapter({ article: NytNewsItem.Article, imageView: ImageView ->
                                navigateToArticle(article, imageView)
                            } ,context,it.news.articles)
                        }
                    } }

                is ItemViewState.Error -> {}
            }

        }

    }


    private fun navigateToArticle(article: NytNewsItem.Article, imageView: ImageView) {
        val intent = Intent(requireContext(), ArticleViewActivity::class.java)
        intent.putExtra("article", ObjectSerializer.serialize(article))
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            imageView,
            "article_image"
        )
        intent.putExtra("fab_visiblity", View.VISIBLE)
        startActivity(intent, activityOptions.toBundle())
    }


}