package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import network.ApiServices
import network.PostModel

class SharedPostsViewModel(
    private val apiServices: ApiServices
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostModel>>(emptyList())
    val posts: StateFlow<List<PostModel>> = _posts

    private val _uploadPostState = MutableStateFlow(false)
    val uploadPostState: StateFlow<Boolean> = _uploadPostState


    private val _updatePostState = MutableStateFlow(false)
    val updatePostState: StateFlow<Boolean> = _updatePostState

    private val _updatePostDescription = MutableStateFlow(false)
    val updatePostDescription: StateFlow<Boolean> = _updatePostDescription

    private val _deletePost = MutableStateFlow(false)
    val deletePost: StateFlow<Boolean> = _deletePost

    init {
        fetchPosts()
        uploadNewPost()
        updatePost()
        updatePostDescription()
        deletePost()
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

    private fun uploadNewPost() {
        viewModelScope.launch {
            val post = PostModel(3654,1,"Grokking Algorithms","Grokking algorithms book is the best choice for beginners ")
            val result = apiServices.uploadNewPost(post)
            result.fold(
                onSuccess = {
                    _uploadPostState.value = it
                    println("uploading result: $it")
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }

    private fun updatePost() {
        viewModelScope.launch {
            val post = PostModel(3654,1,"Grokking Algorithms","Grokking algorithms book is the best choice for beginners ")
            val result = apiServices.updatePost(1,post)
            result.fold(
                onSuccess = {
                    _updatePostState.value = it
                    println("updating result: $it")
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }

    private fun updatePostDescription() {
        viewModelScope.launch {
            val post = PostModel(3654,1,"Grokking Algorithms","Grokking algorithms book is one of the best choice for beginners ")
            val result = apiServices.updatePostDescription(1,post)
            result.fold(
                onSuccess = {
                    _updatePostDescription.value = it
                    println("updating result: $it")
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }

    private fun deletePost() {
        viewModelScope.launch {
            val result = apiServices.deletePost(1)
            result.fold(
                onSuccess = {
                    _deletePost.value = it
                    println("deleting result: $it")
                },
                onFailure = {
                    it.printStackTrace()
                }
            )
        }
    }
}