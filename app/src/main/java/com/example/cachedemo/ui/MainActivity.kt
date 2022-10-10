package com.example.cachedemo.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.cachedemo.R
import com.example.cachedemo.common.AppConstants
import com.example.cachedemo.databinding.ActivityMainBinding
import com.example.cachedemo.ui.viewmodel.MainViewModel
import com.example.cachedemo.model.CharResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    var adapter: CharacterAdapter? = null
    var arrayList: ArrayList<CharResult> = ArrayList()
    var url = ""
    var page = 1
    var limitForPage = 0
    private val mViewModel: MainViewModel by viewModel()
    private val PROGRESS_START = 0
    private val PROGRESS_MAX = 100
    private val JOB_TIME = 4000
    private var currentProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        initProgressBar()
//        dialog?.show()


        initObserver()
        initView()
        rvScrollListener()
        setAdapter()
    }

    private fun ProgressBar.startProgress(progressVal: Int) {
        currentProgress = progressVal
        CoroutineScope(IO).launch {
            for (i in currentProgress..PROGRESS_MAX step 5) {
                delay((JOB_TIME / PROGRESS_MAX).toLong())
                this@startProgress.progress = i
            }
        }
    }

    private fun initView() {
        setAdapter()

        getCharacters(page)

    }

    private fun rvScrollListener() {
        mBinding.rvRickMorty.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (page >= limitForPage) return
                    else {
                        page++
                        mBinding.progressView.visibility = View.VISIBLE
                        getCharacters(page)
                    }
                }
            }
        })
    }

    private fun getCharacters(page: Int) {
        resetProgress()
        mBinding.progressView.startProgress(currentProgress)
        mViewModel.getCharacterList(page)
    }


    private fun resetProgress() {
        mBinding.progressView.progress = PROGRESS_START
        mBinding.progressView.max = PROGRESS_MAX
    }

    private fun initObserver() {
        mViewModel.getCharacterRequest().observe(this, Observer { response ->

            response?.let { requestState ->
                requestState.apiResponse?.let {
                    limitForPage = it.info.pages
                    CoroutineScope(Main).launch {
                        mBinding.progressView.visibility = View.GONE
                    }
                    if (!it.results.isNullOrEmpty()) {

                        arrayList.addAll(it.results)
                        adapter?.notifyDataSetChanged()
                    }
                }
                requestState.error?.let { errorObj ->
                    resetProgress()
                    Toast.makeText(
                        this@MainActivity,
                        errorObj.errorState,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        })
    }

    private fun setAdapter() {

        adapter = object : CharacterAdapter(this, arrayList) {
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)

                url = arrayList[position].url

                intent.putExtra(AppConstants.CHARACTER_ID, arrayList[position].id)
                intent.putExtra(AppConstants.CHARACTER_URL, url)
                startActivity(intent)
            }
        }

        mBinding.rvRickMorty.adapter = adapter
    }
}