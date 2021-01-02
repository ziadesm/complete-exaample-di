package com.example.completeexample.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.completeexample.R
import com.example.completeexample.model.Blog
import com.example.completeexample.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, { dataState ->
            when(dataState){
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitle(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message!!)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String){
        if (message != null) {
            text.text = message
        } else{
            text.text = "Unknown Error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) VISIBLE else GONE
    }

    private fun appendBlogTitle(blogs: List<Blog>){
        val sb = StringBuilder()
        for(blog in blogs) {
            sb.append(blog.title + "\n")
        }
        text.text = sb.toString()
    }
}