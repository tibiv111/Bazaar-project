package project.bazaar.api


import android.util.Log
import com.squareup.moshi.Moshi
import project.bazaar.utils.Constants
import project.bazaar.utils.Constants.BASE_URL
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

//    private val retrofit by lazy{
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    val api: MarketApi by lazy{
        retrofit.create(MarketApi :: class.java)
    }


}