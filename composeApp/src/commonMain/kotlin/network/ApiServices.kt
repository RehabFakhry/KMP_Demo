package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class ApiServices(private val httpClient: HttpClient) {
    suspend fun getPosts(): Result<List<PostModel>> {
        return try {
            val response: HttpResponse = httpClient.get("https://jsonplaceholder.typicode.com/posts")
            val posts = Json.decodeFromString<List<PostModel>>(response.body())
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadNewPost(post:PostModel): Result<Boolean> {
        return try {
            val result = httpClient.post("https://jsonplaceholder.typicode.com/posts"){
                contentType(ContentType.Application.Json)
                setBody(post)
            }
            Result.success(result.status.isSuccess())
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun updatePost(postId:Int,post: PostModel): Result<Boolean> {
        return try {
            val result = httpClient.put("https://jsonplaceholder.typicode.com/posts/$postId"){
                contentType(ContentType.Application.Json)
                setBody(post)
            }
            Result.success(result.status.isSuccess())
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun updatePostDescription(postId:Int,post: PostModel): Result<Boolean> {
        return try {
            val result = httpClient.patch("https://jsonplaceholder.typicode.com/posts/$postId"){
                contentType(ContentType.Application.Json)
                setBody(post)
            }
            Result.success(result.status.isSuccess())
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun deletePost(postId:Int): Result<Boolean> {
        return try {
            val result = httpClient.delete("https://jsonplaceholder.typicode.com/posts/$postId"){
                contentType(ContentType.Application.Json)
            }
            Result.success(result.status.isSuccess())
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}
