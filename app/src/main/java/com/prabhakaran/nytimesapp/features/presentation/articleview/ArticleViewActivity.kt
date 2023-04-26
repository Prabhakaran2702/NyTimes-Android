package com.prabhakaran.nytimesapp.features.presentation.articleview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.prabhakaran.nytimesapp.R
import com.prabhakaran.nytimesapp.common.utils.ObjectSerializer
import com.prabhakaran.nytimesapp.databinding.ActivityArticleViewBinding
import com.prabhakaran.nytimesapp.databinding.FragmentArticlesBinding
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import com.prabhakaran.nytimesapp.features.data.model.Result

class ArticleViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleViewBinding
    private lateinit var menu: Menu
    private lateinit var article: NytNewsItem.Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleViewBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f
        article = ObjectSerializer.deserialize(intent.getStringExtra("article")) as NytNewsItem.Article
        setUpLayout()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            this.menu = menu
        }
        menuInflater.inflate(R.menu.article_menu, menu)
        hideOptions(R.id.action_bookmark)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_bookmark ->{
                Toast.makeText(this,"Added to Bookmark", Toast.LENGTH_SHORT ).show()
            }
        }
        return true
    }

    fun showOptions(id: Int){
        val item = menu.findItem(id)
        item.setVisible(intent.getIntExtra("fab_visiblity", View.GONE) == View.VISIBLE)
    }

    fun hideOptions(id: Int){
        val item = menu.findItem(id)
        item.setVisible(false)
    }

    private fun setUpLayout(){

        // filling every views

        if(article.media!!.isNotEmpty()){
            if(article.media?.get(0)?.mediaMetadata?.get(0)?.url.isNullOrEmpty()){

            }else {
                binding.expandedImage.visibility = View.VISIBLE
                Glide.with(this)
                    .load(article.media!![0].mediaMetadata?.get(0)?.url) // image url
                    .placeholder(android.R.drawable.ic_input_get) // any placeholder to load at start
                    .error(android.R.drawable.stat_notify_error)  // any image in case of error
                    .override(200, 200) // resizing
                    .centerCrop()
                    .into(binding.expandedImage)
            }
        }
        else{
            binding.expandedImage.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.ny) // image url
                .placeholder(android.R.drawable.ic_input_get) // any placeholder to load at start
                .error(android.R.drawable.stat_notify_error)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(binding.expandedImage)
        }


        binding.includedLayout.apply {
            articleTitle.text = article.title
            articleContent.text = article.abstract +"\n\n\n"+
                    "Full article at : "+article.url
        }

        //handling events
        binding.fabBookmark.visibility = intent.getIntExtra("fab_visiblity", View.GONE)

        binding.fabBookmark.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/web"
            intent.putExtra(Intent.EXTRA_TEXT, article.title + "\n"+article.url)
            val shareIntent = Intent.createChooser(intent, "Share via")
            startActivity(shareIntent)
        }

        binding.shareButton.setOnClickListener{
          finish()
        }

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    showOptions(R.id.action_bookmark)
                } else if (isShow) {
                    isShow = false
                    hideOptions(R.id.action_bookmark)
                }
            }
        })

    }



}