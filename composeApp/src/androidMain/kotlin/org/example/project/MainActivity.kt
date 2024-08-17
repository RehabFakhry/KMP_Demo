package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import presentation.PostsScreen
import presentation.SharedPostsViewModel

class MainActivity : ComponentActivity() {
    private val postViewModel: SharedPostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostsScreen(postViewModel)
        }
    }
}