package com.example.myfrstapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfrstapplication.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    // Исходные данные
    private var post = Post(
        id = 1,
        author = "почему бы и нет",
        content = "Жизнь в гараже нестандартное решение, которое открывает ряд преимуществ перед традиционными жилищами: Низкая стоимость: Гаражи часто обходятся дешевле квартир или домов, позволяя сэкономить средства на жилье.\n" +
                "Простота обустройства: Пространство легко переделывается под жилые нужды благодаря компактности и простоте конструкций.\n" +
                "Идеальное место для творческих людей: Отличная возможность организовать мастерскую, студию или хобби-зону рядом с домом.\n" +
                "Жилище в гараже подходит людям, ценящим свободу, креативность и жизнь вне рамок традиционных стандартов комфорта.\"",
        published = "21 мая в 18:36",
        likedByMe = false,
        likes = 999,
        shares = 25,
        views = 5700
    )

    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(post)

    // Внешний доступ только для чтения (LiveData, а не MutableLiveData)
    override fun get(): LiveData<Post> = _data

    override fun like() {
        // Меняем состояние лайка на противоположное
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        // Оповещаем подписчиков об изменении
        _data.value = post
    }

    override fun share() {
        post = post.copy(
            shares = post.shares + 1
        )
        _data.value = post
    }

    override fun increaseViews() {
        // Можно будет реализовать позже
        post = post.copy(
            views = post.views + 1
        )
        _data.value = post
    }
}