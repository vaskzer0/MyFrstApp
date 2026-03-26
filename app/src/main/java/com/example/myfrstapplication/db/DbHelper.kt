package com.example.myfrstapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myfrstapplication.db.PostContract.Columns
import android.provider.BaseColumns


class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }


    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "почему бы и нет")
            put(Columns.AUTHOR_ID, 2)
            put(Columns.CONTENT, "Жизнь в гараже нестандартное решение, которое открывает ряд преимуществ перед традиционными жилищами: Низкая стоимость: Гаражи часто обходятся дешевле квартир или домов, позволяя сэкономить средства на жилье. Простота обустройства: Пространство легко переделывается под жилые нужды благодаря компактности и простоте конструкций. Идеальное место для творческих людей: Отличная возможность организовать мастерскую, студию или хобби-зону рядом с домом. Жилище в гараже подходит людям, ценящим свободу, креативность и жизнь вне рамок традиционных стандартов комфорта.\"",)
            put(Columns.PUBLISHED, "21 мая в 18:36")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 999)
            put(Columns.SHARES, 25)
            put(Columns.VIEWS, 5700)
            put(Columns.VIDEO, "https://youtu.be/L9yYlEzN4Iw")
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)

        // Второй пост с видео
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "гоночное сообщество быстрые ножки")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Инвалидные кресла играют важную роль в жизни миллионов людей во всём мире, обеспечивая мобильность, комфорт и возможность вести активный образ жизни людям с ограниченными возможностями передвижения. Современные модели кресел отличаются высоким уровнем комфорта, функциональности и надежности, помогая человеку чувствовать себя уверенно вне зависимости от обстоятельств.")
            put(Columns.PUBLISHED, "28 мая в 10:25")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 3142)
            put(Columns.SHARES, 189)
            put(Columns.VIEWS, 5300)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ремонт и строительство!")
            put(Columns.AUTHOR_ID, 4)
            put(Columns.CONTENT, "ИЩЕТЕ НЕДОРОГИЕ, КАЧЕСТВЕННЫЕ И КРАСИВЫЕ КЕРАМИЧЕСКИЕ ПЛИТКИ? Представляем вашему вниманию нашу коллекцию кафельной плитки премиум-класса по доступным ценам! Идеальное сочетание эстетики и долговечности Огромный выбор цветов и фактур Удобство укладки и ухода Экологичность и безопасность Доступные цены и выгодные условия покупки Наши плитки украшают кухни, санузлы, коридоры и балконы тысяч российских домов, радуя глаз и подчеркивая индивидуальность интерьера. Позвольте своему дому сиять красотой и элегантностью современной керамической плитки!")
            put(Columns.PUBLISHED, "2 мая в 01:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1250)
            put(Columns.SHARES, 42)
            put(Columns.VIEWS, 8900)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "анегдоты")
            put(Columns.AUTHOR_ID, 5)
            put(Columns.CONTENT, "Встречаются двое друзей:— Ты слышал новость? Армянский миллионер решил вложить деньги в новый бизнес-проект.— А что за проект?— Будет выпускать растворимый кофе.— Это понятно, а почему именно кофе?— Ну, представляешь, насколько удобней пить натуральный кофе, когда достаточно добавить горячей воды? А тут вообще ничего варить не надо — залил кипятком и готово! Настоящий прорыв в индустрии напитков! Только название неудачное получилось…— Почему?— Да потому что назвал его… «Растворимый Мугник».")
            put(Columns.PUBLISHED, "15 мая в 08:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 5678)
            put(Columns.SHARES, 1234)
            put(Columns.VIEWS, 45000)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "шашлычная на газу")
            put(Columns.AUTHOR_ID, 6)
            put(Columns.CONTENT, "Шашлык на газу — отличный способ приготовить сочное мясо быстро и удобно, особенно если хочется насладиться вкусом качественного блюда без копоти и сильного запаха дыма. Традиционный вариант приготовления шашлыка предполагает открытый огонь и угли, однако современные технологии позволяют добиться похожего результата даже с помощью газовых горелок.")
            put(Columns.PUBLISHED, "23 мая в 09:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 9250)
            put(Columns.SHARES, 5420)
            put(Columns.VIEWS, 189000)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Android Dev")
            put(Columns.AUTHOR_ID, 7)
            put(Columns.CONTENT, "Вышел новый релиз Android Studio! Теперь с поддержкой Gemini AI и улучшенным композером.")
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

    }
}
