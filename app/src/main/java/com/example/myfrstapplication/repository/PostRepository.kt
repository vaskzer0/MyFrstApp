@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.example.myfrstapplication.repository

import androidx.lifecycle.LiveData
import com.example.myfrstapplication.dto.Post
import java.util.List

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun increaseViews(id: Long)
    fun save(post: Post): Post        // для создания и обновления
    fun removeById(id: Long)     // для удаления
}

