package com.messenger.presentation.viewModel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.messenger.domain.media.CreateImageFile
import com.messenger.domain.media.EncodeImageBitmap
import com.messenger.domain.media.GetPickedImage
import com.messenger.domain.type.None
import javax.inject.Inject

class MediaViewModel @Inject constructor(
    private val createImageFileUseCase: CreateImageFile,
    private val encodeImageBitmapUseCase: EncodeImageBitmap,
    private val getPickedImageUseCase: GetPickedImage
) : BaseViewModel() {

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 10001
        const val CAPTURE_IMAGE_REQUEST_CODE = 10002
    }

    class PickedImage(val bitmap: Bitmap, val string: String)

    private val _cameraFileCreatedData: MutableLiveData<Uri> = MutableLiveData()
    val cameraFileCreatedData: LiveData<Uri>
        get() = _cameraFileCreatedData

    private val _imageBitmapData: MutableLiveData<Bitmap> = MutableLiveData()
    val imageBitmapData: LiveData<Bitmap>
        get() = _imageBitmapData

    private val _pickedImageData: MutableLiveData<PickedImage> = MutableLiveData()
    val pickedImageData: LiveData<PickedImage>
        get() = _pickedImageData

    fun createCameraFile() {
        createImageFileUseCase(None()) { it.either(::handleFailure, ::handleCameraFileCreated) }
    }

    private fun getPickedImage(uri: Uri?) {
        _isLoading.value = true
        getPickedImageUseCase(uri) { it.either(::handleFailure, ::handleImageBitmap) }
    }

    private fun encodeImage(bitmap: Bitmap) {
        encodeImageBitmapUseCase(bitmap) { it.either(::handleFailure, ::handleImageString) }
    }


    fun onPickImageResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = when (requestCode) {
                PICK_IMAGE_REQUEST_CODE -> data?.data
                CAPTURE_IMAGE_REQUEST_CODE -> cameraFileCreatedData.value
                else -> null
            }

            getPickedImage(uri)
        }
    }

    private fun handleCameraFileCreated(uri: Uri) {
        _cameraFileCreatedData.value = uri
    }

    private fun handleImageBitmap(bitmap: Bitmap) {
        _imageBitmapData.value = bitmap

        encodeImage(bitmap)
    }

    private fun handleImageString(string: String) {
        _pickedImageData.value = PickedImage(imageBitmapData.value!!, string)
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        createImageFileUseCase.unsubscribe()
        encodeImageBitmapUseCase.unsubscribe()
        getPickedImageUseCase.unsubscribe()
    }
}