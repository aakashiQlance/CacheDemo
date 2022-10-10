package com.example.cachedemo.shareddata.repo

import androidx.lifecycle.MutableLiveData
import com.example.cachedemo.model.RequestState
import com.example.cachedemo.shareddata.api.ApiEndPoint
import com.example.cachedemo.shareddata.networkmanager.NetworkManager
import com.example.cachedemo.model.CharResult
import com.example.cachedemo.model.CharacterRM

class RickAndMortyRepoImpl(private var apiEndpoint: ApiEndPoint) : RickAndMortyRepo {
    override fun getCharacter(
        pages: Int,
        callback: MutableLiveData<RequestState<CharResult>>,
    ) {
        callback.postValue(RequestState(progress = true))
        NetworkManager.requestData(apiEndpoint.getCharacter(pages.toString()), callback)
    }

    override suspend fun getCharacterDetail(
        url: String,
        callback: MutableLiveData<RequestState<CharacterRM>>,
    ) {
//        callback.postValue(RequestState(progress = true))
        NetworkManager.requestData1(apiEndpoint.getCharacterDetail(url), callback)
    }


}