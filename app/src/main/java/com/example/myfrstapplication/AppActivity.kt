package com.example.myfrstapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.example.activity.EditPostContract
import com.example.myfrstapplication.adapter.OnPostInteractionListener
import com.example.myfrstapplication.adapter.PostsAdapter
import com.example.myfrstapplication.dto.Post
import com.example.myfrstapplication.viewmodel.PostViewModel


class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        // Обработка входящего интента
        handleIntent(intent)
    }



    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (!text.isNullOrBlank()) {
                // Переходим к фрагменту создания поста с текстом
                val bundle = Bundle().apply {
                    putString("postContent", text)
                }
                findNavController(R.id.nav_host_fragment).navigate(
                    R.id.newPostFragment,
                    bundle
                )
            }

        }
    }
}

