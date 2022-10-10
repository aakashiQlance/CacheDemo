package com.example.cachedemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cachedemo.model.RequestState
import com.example.cachedemo.shareddata.repo.RickAndMortyRepo
import com.example.cachedemo.model.CharResult
import com.example.cachedemo.model.CharacterRM

class MainViewModel(private var mRickAndMortyRepo: RickAndMortyRepo): ViewModel() {

    private val mCharacterRequest= MutableLiveData<RequestState<CharResult>>()
    fun getCharacterRequest() : LiveData<RequestState<CharResult>> = mCharacterRequest

    private val mCharacterDetailRequest = MutableLiveData<RequestState<CharacterRM>>()
    fun getCharacterDetailRequest(): LiveData<RequestState<CharacterRM>> = mCharacterDetailRequest

    fun getCharacterList(pages: Int) {
        mRickAndMortyRepo.getCharacter(pages,mCharacterRequest)
    }

    suspend fun getCharacterDetail(url:String){
        mRickAndMortyRepo.getCharacterDetail(url,mCharacterDetailRequest)
    }
}