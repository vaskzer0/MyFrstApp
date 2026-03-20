package com.example.myfrstapplication.repository

import androidx.lifecycle.LiveData
import com.example.myfrstapplication.dto.Post

interface PostRepository {
    // Возвращает LiveData, на которую можно подписаться
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun increaseViews(id: Long)

}
