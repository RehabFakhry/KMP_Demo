package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

class ApiServices(private val httpClient: HttpClient) {
    suspend fun getPosts(): Result<List<PostModel>> {
        return try {
            val response: HttpResponse =
                httpClient.get("https://jsonplaceholder.typicode.com/posts")
            val posts = Json.decodeFromString<List<PostModel>>(response.body())
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePost(id: Int): Result<Boolean> {
        return try {
            val response: HttpResponse =
                httpClient.delete("https://jsonplaceholder.typicode.com/posts/$id")
            if (response.status == HttpStatusCode.OK)
                Result.success(true)
            else {
                Result.failure(Exception("Can't delete post"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}