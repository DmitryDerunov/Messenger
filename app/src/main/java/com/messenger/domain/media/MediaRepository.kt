package com.messenger.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.messenger.domain.type.Either
import com.messenger.domain.type.Failure

interface MediaRepository {
    fun createImageFile(): Either<Failure, Uri>
    fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String>
    fun getPickedImage(uri: Uri?): Either<Failure, Bitmap>
}