import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import network.appModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import presentation.PostsScreen
import presentation.SharedPostsViewModel

fun main() = application {
    startKoin {
        modules(appModule)
    }

    val sharedPostsViewModel: SharedPostsViewModel by inject(SharedPostsViewModel::class.java)

    application {
        Window(onCloseRequest = ::exitApplication) {
            PostsScreen(sharedPostsViewModel)
        }
    }
}
