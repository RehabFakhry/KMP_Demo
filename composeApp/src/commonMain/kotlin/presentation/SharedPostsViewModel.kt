package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import network.ApiServices
import network.PostModel
import org.koin.core.time.Timer

class SharedPostsViewModel(
    private val apiServices: ApiServices
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostModel>>(emptyList())
    val posts: StateFlow<List<PostModel>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val result = apiServices.getPosts()
            result.fold(
                onSuccess = {
                    _posts.value = it
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            val result = apiServices.deletePost(postId)
            result.fold(
                onSuccess = {
                    _posts.value = _posts.value.filter { it.id != postId }
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }
}
