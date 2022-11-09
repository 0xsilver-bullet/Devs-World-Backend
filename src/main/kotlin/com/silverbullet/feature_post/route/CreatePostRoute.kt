package com.silverbullet.feature_post.route

import com.silverbullet.feature_post.data.request.CreatePostRequest
import com.silverbullet.feature_post.service.PostService
import com.silverbullet.utils.Constants
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

fun Route.createPostRoute() {

    val postService: PostService by inject()

    authenticate {
        post("/create") {
            var request: CreatePostRequest? = null
            var filePath: String? = null
            var imageUrl: String? = null
            var fileBuffer: ByteArray? = null
            val parts = call.receiveMultipart()
            parts.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        request = Json.decodeFromString(part.value)
                            ?: kotlin.run {
                                return@forEachPart
                            }
                    }

                    is PartData.FileItem -> {
                        val fileExt = part.originalFileName?.takeLastWhile { it != '.' } ?: return@forEachPart
                        val fileName = UUID.randomUUID().toString() + ".$fileExt"
                        fileBuffer = part.streamProvider().readBytes()
                        filePath = Path("").absolutePathString() + Constants.POST_IMAGE_PATH + fileName
                        imageUrl = Constants.BASE_URL + "/posts/$fileName"
                    }

                    is PartData.BinaryItem -> Unit
                    is PartData.BinaryChannelItem -> Unit
                }
            }
            if (request == null) {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.BadRequest)
                return@post
            }
            if (filePath == null || imageUrl == null || fileBuffer == null) {
                call.failureBasicResponse<Unit>()
                return@post
            }
            val created = postService.createPost(
                userId = call.userId,
                request = request!!,
                imageUrl = imageUrl!!,
                imageInternalPath = filePath!!
            )
            if (created) {
                call.successfulBasicResponse<Unit>()
                File(filePath!!).writeBytes(fileBuffer!!)
            } else {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.InternalServerError)
            }
        }
    }
}