package com.example.myfrstapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfrstapplication.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostRepositoryInMemoryImpl : PostRepository {
    // Счетчик для генерации ID
    private var nextId = 5L

    // Текущий пользователь (для демонстрации)
    private val currentUserId = 1L
    private val currentUserName = "Я"

    // Теперь это список, а не один пост
    private var posts = listOf(
        Post(
            id = 1,
            author = "почему бы и нет",
            authorId = 2,
            content = "Жизнь в гараже нестандартное решение, которое открывает ряд преимуществ перед традиционными жилищами: Низкая стоимость: Гаражи часто обходятся дешевле квартир или домов, позволяя сэкономить средства на жилье.\n" +
                    "Простота обустройства: Пространство легко переделывается под жилые нужды благодаря компактности и простоте конструкций.\n" +
                    "Идеальное место для творческих людей: Отличная возможность организовать мастерскую, студию или хобби-зону рядом с домом.\n" +
                    "Жилище в гараже подходит людям, ценящим свободу, креативность и жизнь вне рамок традиционных стандартов комфорта.\"",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999,
            shares = 25,
            views = 5700
        ),
        Post(
            id = 2,
            author = "гоночное сообщество быстрые ножки",
            authorId = 3,
            content = "Инвалидные кресла играют важную роль в жизни миллионов людей во всём мире, обеспечивая мобильность, комфорт и возможность вести активный образ жизни людям с ограниченными возможностями передвижения. Современные модели кресел отличаются высоким уровнем комфорта, функциональности и надежности, помогая человеку чувствовать себя уверенно вне зависимости от обстоятельств.",
            published = "28 мая в 10:25",
            likedByMe = false,
            likes = 3142,
            shares = 189,
            views = 5300
        ),
        Post(
            id = 3,
            author = "ремонт и строительство!",
            authorId = 4,
            content = "ИЩЕТЕ НЕДОРОГИЕ, КАЧЕСТВЕННЫЕ И КРАСИВЫЕ КЕРАМИЧЕСКИЕ ПЛИТКИ?\n" +
                    "\n" +
                    "Представляем вашему вниманию нашу коллекцию кафельной плитки премиум-класса по доступным ценам!\n" +
                    "\n" +
                    "Идеальное сочетание эстетики и долговечности Огромный выбор цветов и фактур Удобство укладки и ухода Экологичность и безопасность Доступные цены и выгодные условия покупки  \n" +
                    "\n" +
                    "Наши плитки украшают кухни, санузлы, коридоры и балконы тысяч российских домов, радуя глаз и подчеркивая индивидуальность интерьера.\n" +
                    "\n" +
                    "Позвольте своему дому сиять красотой и элегантностью современной керамической плитки!",
            published = "2 мая в 01:42",
            likedByMe = true,
            likes = 1250,
            shares = 420,
            views = 8900
        ),
        Post(
            id = 4,
            author = "анегдоты",
            authorId = 5,
            content = "Встречаются двое друзей:— Ты слышал новость? Армянский миллионер решил вложить деньги в новый бизнес-проект.— А что за проект?— Будет выпускать растворимый кофе.— Это понятно, а почему именно кофе?— Ну, представляешь, насколько удобней пить натуральный кофе, когда достаточно добавить горячей воды? А тут вообще ничего варить не надо — залил кипятком и готово! Настоящий прорыв в индустрии напитков! Только название неудачное получилось…— Почему?— Да потому что назвал его… «Растворимый Мугник».",
            published = "15 мая в 08:00",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000
        ),
        Post(
            id = 5,
            author = "шашлычная на газу",
            authorId = 6,
            content = "Шашлык на газу — отличный способ приготовить сочное мясо быстро и удобно, особенно если хочется насладиться вкусом качественного блюда без копоти и сильного запаха дыма. Традиционный вариант приготовления шашлыка предполагает открытый огонь и угли, однако современные технологии позволяют добиться похожего результата даже с помощью газовых горелок.",
            published = "23 мая в 09:42",
            likedByMe = true,
            likes = 9250,
            shares = 5420,
            views = 18900
        )

    )

    private val _data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }
    override fun save(post: Post) {
        if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            posts = listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts = posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    // Сохраняем автора, дату и счетчики, обновляем только контент
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}