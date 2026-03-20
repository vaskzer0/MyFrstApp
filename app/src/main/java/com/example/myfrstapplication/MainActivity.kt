package com.example.myfrstapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myfrstapplication.adapter.OnPostInteractionListener
import com.example.myfrstapplication.adapter.PostsAdapter
import com.example.myfrstapplication.databinding.ActivityMainBinding
import com.example.myfrstapplication.dto.Post
import com.example.myfrstapplication.viewmodel.PostViewModel
import java.text.DecimalFormat



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    private val interactionListener = object : OnPostInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            // Переводим фокус на поле ввода
            binding.content.requestFocus()
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            Toast.makeText(this@MainActivity, "Пост удален", Toast.LENGTH_SHORT).show()
        }

        override fun onAvatarClick(post: Post) {
            Toast.makeText(this@MainActivity, "Профиль: ${post.author}", Toast.LENGTH_SHORT).show()
            viewModel.increaseViews(post.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка адаптера
        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        // Наблюдение за списком постов
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        // Наблюдение за редактируемым постом
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                // Редактирование существующего поста
                binding.content.setText(post.content)
                binding.content.requestFocus()
            }
                /*else {
                // Сброс (новый пост или отмена)
                binding.content.setText("")
            }*/
        }

        // Наблюдение за режимом редактирования
        viewModel.editingMode.observe(this) { isEditing ->
            if (isEditing) {
                binding.cancelGroup.visibility = View.VISIBLE
            } else {
                binding.cancelGroup.visibility = View.GONE
            }
        }

        // Отслеживание изменений текста
        binding.content.addTextChangedListener { text ->
            viewModel.changeContent(text.toString())
        }

        // Кнопка сохранения
        binding.save.setOnClickListener {
            if (binding.content.text.isNullOrBlank()) {
                Toast.makeText(this, "Введите текст поста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.save()
            // Скрываем клавиатуру
            hideKeyboard(binding.content)
        }

        // Кнопка отмены редактирования
        binding.cancel.setOnClickListener {
            viewModel.cancelEdit()
            hideKeyboard(binding.content)
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

