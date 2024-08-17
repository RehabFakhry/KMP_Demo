package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.SharedPostsViewModel

val appModule = module {
    single { createApiClient() }
    single { ApiServices(get()) }
    viewModel { SharedPostsViewModel(get()) }
}

fun createApiClient(): HttpClient {
   return HttpClient {
        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.DEFAULT
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}