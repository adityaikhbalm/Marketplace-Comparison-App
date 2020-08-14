package com.adityaikhbalm.remote

import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.HashMap

interface ApiService {
    @FormUrlEncoded
    @POST("{path}")
    suspend fun fetchData(
        @Path("path") path: String,
        @FieldMap hashFields: HashMap<String, String>
    ): Any
}
