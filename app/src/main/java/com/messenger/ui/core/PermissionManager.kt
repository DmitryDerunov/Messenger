package com.messenger.ui.core

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
//import com.github.kayvannj.permission_utils.Func
//import com.github.kayvannj.permission_utils.PermissionUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor() {

    companion object {
        const val REQUEST_CAMERA_PERMISSION = 10003
        const val REQUEST_WRITE_PERMISSION = 10004
    }

    //var requestObject: PermissionUtil.PermissionRequestObject? = null

//    fun checkWritePermission(activity: AppCompatActivity, body: () -> Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestObject = PermissionUtil.with(activity)
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .ask(REQUEST_WRITE_PERMISSION) {
//                    body()
//                }
//        } else {
//            body()
//        }
//    }

//    fun checkCameraPermission(activity: AppCompatActivity, body: () -> Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestObject = PermissionUtil.with(activity)
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .ask(REQUEST_CAMERA_PERMISSION) {
//                    body()
//                }
//        } else {
//            body()
//        }
//    }
}

//fun PermissionUtil.PermissionRequestObject.ask(code: Int, granted: () -> Unit): PermissionUtil.PermissionRequestObject? {
//    return this.onAllGranted(object : Func() {
//        override fun call() {
//            granted()
//        }
//    }).ask(code)
//}
