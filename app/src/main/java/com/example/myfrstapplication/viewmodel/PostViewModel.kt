package com.example.myfrstapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfrstapplication.db.AppDb
import com.example.myfrstapplication.dto.Post
import com.example.myfrstapplication.repository.PostRepository
import com.example.myfrstapplication.repository.PostRepositoryFileImpl
import com.example.myfrstapplication.repository.PostRepositoryInMemoryImpl
import com.example.myfrstapplication.repository.PostRepositorySQLiteImpl
import kotlin.collections.List
import androidx.lifecycle.map
class PostViewModel(application: Application) : AndroidViewModel(application) {


    // Используем SQLite репозиторий
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )

    val data: LiveData<kotlin.collections.List<Post>> = repository.getAll().map { it.toList() }

    private val empty = Post(
        id = 0,
        author = "",
        content = "",
        published = ""
    )

    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Post> = _edited

    private val _editingMode = MutableLiveData(false)
    val editingMode: LiveData<Boolean> = _editingMode

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun increaseViews(id: Long) = repository.increaseViews(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun save() {
        _edited.value?.let { post ->
            if (post.content.isNotBlank()) {
                repository.save(post)
            }
        }
        _edited.value = empty
        _editingMode.value = false
    }

    fun edit(post: Post) {
        _edited.value = post
        _editingMode.value = true
    }

    fun changeContent(content: String) {
        val text = content.trim()
        _edited.value?.let { post ->
            if (post.content != text) {
                _edited.value = post.copy(content = text)
            }
        }
    }

    fun cancelEdit() {
        _edited.value = empty
        _editingMode.value = false
    }

    fun saveEditedPost(editingPostId: Long, text: String) {}
}



