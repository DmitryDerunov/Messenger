package com.messenger.data.media

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.messenger.domain.media.MediaRepository
import com.messenger.domain.type.Either
import com.messenger.domain.type.Failure
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl @Inject constructor(private val context: Context) : MediaRepository {

    override fun createImageFile(): Either<Failure, Uri> {
        val uri = MediaHelper.createImageFile(context)
        return if (uri == null) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(uri)
        }
    }

    override fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String> {
        if (bitmap == null) return Either.Left(Failure.FilePickError)

        val imageString = MediaHelper.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100)

        return if (imageString.isEmpty()) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(imageString)
        }
    }

    override fun getPickedImage(uri: Uri?): Either<Failure, Bitmap> {
        if (uri == null) return Either.Left(Failure.FilePickError)

        val filePath = MediaHelper.getPath(context, uri)
        val image = MediaHelper.saveBitmapToFile(File(filePath))

        return if (image == null) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(image)
        }
    }
}