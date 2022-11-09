package com.silverbullet.feature_user.route

import com.silverbullet.feature_user.data.request.UpdateUserRequest
import com.silverbullet.feature_user.service.UserService
import com.silverbullet.utils.*
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
import java.util.UUID
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

/**
 * update data must be in body and named with ${BodyKeys.UPDATED_USER}
 */
fun Route.updateUser() {

    val userService: UserService by inject()

    var updateUserRequest: UpdateUserRequest? = null
    var filePath: String? = null
    var profileImageUrl: String? = null

    authenticate {
        put("/update") {
            val parts = call.receiveMultipart()
            parts.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == BodyKeys.UPDATE_USER) {
                            // if parsing failed the request is not valid
                            updateUserRequest = Json.decodeFromString(part.value)
                            if (updateUserRequest == null) {
                                return@forEachPart
                            }
                        }
                    }

                    is PartData.FileItem -> {
                        val fileExt = part.originalFileName?.takeLastWhile { it != '.' } ?: return@forEachPart
                        val fileName = UUID.randomUUID().toString() + ".$fileExt"
                        profileImageUrl = Constants.BASE_URL + "/profile_pictures/$fileName"
                        filePath = Path("").absolutePathString() + Constants.PROFILE_PICTURE_PATH + fileName
                        val fileBuffer = part.streamProvider().readBytes()
                        filePath?.let { path ->
                            File(path).writeBytes(fileBuffer)
                        }
                    }

                    is PartData.BinaryItem -> Unit
                    is PartData.BinaryChannelItem -> Unit
                }
            }
            if (updateUserRequest == null) {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.BadRequest)
                filePath?.let {
                    File(it).delete()
                }
            }
            updateUserRequest?.let { request ->
                val updated = userService.updateUser(
                    userId = call.userId,
                    request = request,
                    profileImageUrl = profileImageUrl,
                    profileImageInternalPath = filePath
                ) { oldUser ->
                    if (filePath != null) {
                        // Then file path is updated and you need to delete old file in case it exists
                        oldUser.profileImageInternalPath?.let { oldFilePath ->
                            if (filePath != oldFilePath) {
                                File(oldFilePath).delete()
                            }
                        }
                    }
                }
                if (updated) {
                    call.successfulBasicResponse<Unit>()
                } else {
                    call.failureBasicResponse<Unit>()
                }
            } ?: call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.InternalServerError)
        }
    }
}