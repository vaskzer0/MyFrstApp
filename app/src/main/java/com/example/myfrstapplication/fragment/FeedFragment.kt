package com.example.myfrstapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myfrstapplication.R
import com.example.myfrstapplication.adapter.OnPostInteractionListener
import com.example.myfrstapplication.adapter.PostsAdapter
import com.example.myfrstapplication.databinding.FragmentFeedBinding
import com.example.myfrstapplication.dto.Post
import com.example.myfrstapplication.viewmodel.PostViewModel
import java.util.List
import androidx.lifecycle.observe

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()
    private val interactionListener = object : OnPostInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            val shareIntent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_SEND
                putExtra(android.content.Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val chooserIntent = android.content.Intent.createChooser(shareIntent, getString(R.string.share_post_via))
            startActivity(chooserIntent)
            viewModel.shareById(post.id)
        }

        override fun onEdit(post: Post) {
            // Пока оставим заглушку, реализуем позже
            Toast.makeText(requireContext(), "Edit post ${post.id}", Toast.LENGTH_SHORT).show()
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            Toast.makeText(requireContext(), R.string.post_removed, Toast.LENGTH_SHORT).show()
        }

        override fun onAvatarClick(post: Post) {
            Toast.makeText(requireContext(), "Profile: ${post.author}", Toast.LENGTH_SHORT).show()
            viewModel.increaseViews(post.id)
        }
        override fun onPostClick(post: Post) {
            // Переход к детальному фрагменту с передачей ID поста
            val bundle = Bundle().apply {
                putLong("postId", post.id)
            }
            findNavController().navigate(
                R.id.action_feedFragment2_to_postDetailFragment,
                bundle
            )
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts: kotlin.collections.List<Post> ->
            // Тип теперь совпадает с тем, что отдает ViewModel
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment2_to_newPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
