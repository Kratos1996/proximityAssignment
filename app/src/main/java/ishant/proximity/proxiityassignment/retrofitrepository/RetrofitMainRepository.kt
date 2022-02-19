package ishant.proximity.proxiityassignment.retrofitrepository
/*
* © Copyright Ishant Sharma
* Android Developer
* JAVA/KOTLIN
* */

import android.content.Context
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketService

class RetrofitMainRepository constructor(var context: Context, private var socket: WebSocketService) :
    RetrofitRepository {


//Example Demo
   /* override suspend fun SearchNow(keyword:String): RetrofitResource<SearchResponse> {
        return try {
            val searchAutoComplete = apiClient.SearchNow(keyword)
            val result = searchAutoComplete.body()
            if (searchAutoComplete.isSuccessful && result != null) {
                return RetrofitResource.Success(result)
            } else {
                return RetrofitResource.Error(context.getString(R.string.error_on_fetching_search))
            }
        } catch (e: Exception) {
            return RetrofitResource.Error(*//*e.message ?:*//* context.getString(R.string.server_not_responding))
        }
    }*/


}