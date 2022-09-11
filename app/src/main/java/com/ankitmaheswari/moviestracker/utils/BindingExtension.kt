package com.ankitmaheswari.moviestracker.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.ankitmaheswari.moviestracker.model.Resource
import com.ankitmaheswari.moviestracker.model.ResourceStatus
import com.bumptech.glide.Glide

/**
 * Created by Ankit Maheswari on 11/09/22.
 */

inline fun <reified T> View.bindResource(resource: Resource<T>?, onSuccess: (Resource<T>) -> Unit) {
    if (resource != null) {
        when (resource.resourceStatus) {
            ResourceStatus.LOADING -> Unit
            ResourceStatus.SUCCESS -> onSuccess(resource)
            ResourceStatus.ERROR ->
                Toast.makeText(context,
                    resource.errorData?.status_message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

fun ImageView.load(imageUrl: String) {
    Glide.with(this).load(imageUrl).into(this)
}