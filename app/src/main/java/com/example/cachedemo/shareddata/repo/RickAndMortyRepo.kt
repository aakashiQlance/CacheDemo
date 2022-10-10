package com.example.cachedemo.shareddata.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.example.cachedemo.model.RequestState
import com.example.cachedemo.model.CharResult
import com.example.cachedemo.model.CharacterRM

interface RickAndMortyRepo  {

    fun getCharacter(pages:Int, callback: MutableLiveData<RequestState<CharResult>>)

    suspend fun getCharacterDetail(url: String, callback: MutableLiveData<RequestState<CharacterRM>>)

}