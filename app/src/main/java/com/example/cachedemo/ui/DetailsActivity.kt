package com.example.cachedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cachedemo.R
import com.example.cachedemo.common.AppConstants
import com.example.cachedemo.common.cache.SimpleMortyCache
import com.example.cachedemo.model.CharacterRM
import com.example.cachedemo.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    var characterRM = CharacterRM()
    private val mViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initObserver()
        initView()
    }

    private fun initView() {

        val id = intent.getIntExtra(AppConstants.CHARACTER_ID, 0)
        val url = intent.getStringExtra(AppConstants.CHARACTER_URL)
        cacheDataOrCallApi(id, url)
    }

    private fun cacheDataOrCallApi(id: Int, url: String?) {

        val cacheCharacter = SimpleMortyCache.characterDetailMap[id]
        if (cacheCharacter != null) {
            Glide.with(this@DetailsActivity)
                .load(cacheCharacter.image)
                .into(imgCharacterDetail)

            tvCharacterNameDetail.text = cacheCharacter.name
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                getCharacterDetail(url)
            }


//            var retrofit = RetrofitClient.provideRetrofit()
//            var apiInterface = retrofit.create(ApiEndPoint::class.java)
//            apiInterface.getCharacterDetail(url.toString()).enqueue(object : Callback<CharacterRM> {
//                override fun onResponse(
//                    call: Call<CharacterRM>,
//                    response: Response<CharacterRM>
//                ) {
//                    characterRM = response.body()!!
//                    SimpleMortyCache.characterMap[id] = characterRM
//                    Glide.with(this@DetailsActivity)
//                        .load(characterRM.image)
//                        .into(imgCharacterDetail)
//
//                    tvCharacterNameDetail.text = characterRM.name
//                }
//
//                override fun onFailure(call: Call<CharacterRM>, t: Throwable) {
//                    Toast.makeText(this@DetailsActivity, "No Data found", Toast.LENGTH_SHORT).show()
//                }
//
//
//            })
        }

    }

    private suspend fun getCharacterDetail(url: String?) {
        mViewModel.getCharacterDetail(url = url.toString())
    }

    private fun initObserver() {
        mViewModel.getCharacterDetailRequest().observe(this, Observer { response ->
            response?.let { requestState ->
                requestState.apiCustomResponse?.let {
                    characterRM = it
                    SimpleMortyCache.characterDetailMap[it.id] = characterRM
                    Glide.with(this@DetailsActivity)
                        .load(characterRM.image)
                        .into(imgCharacterDetail)

                    tvCharacterNameDetail.text = characterRM.name
                }

                requestState.error?.let { errorObj ->
                    Toast.makeText(
                        this@DetailsActivity,
                        errorObj.errorState,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        })
    }
}