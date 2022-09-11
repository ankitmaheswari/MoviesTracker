package com.ankitmaheswari.moviestracker.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class Resource<out T>(
    val resourceStatus: ResourceStatus,
    val data: T?,
    val message: String?
) {

    var errorData: ErrorData? = null

    init {
        message?.let {
            try {
                val gson = Gson()
                errorData = gson.fromJson(message, ErrorData::class.java) as ErrorData
            } catch (e: JsonSyntaxException) {
                errorData = ErrorData(400, message, false)
            }
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*>

        if (resourceStatus !== resource.resourceStatus) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun toString(): String {
        return "Resource[" +
                "status=" + resourceStatus + '\'' +
                ",message='" + message + '\'' +
                ",data=" + data +
                ']'
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(resourceStatus = ResourceStatus.SUCCESS, data = data, message = null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(resourceStatus = ResourceStatus.ERROR, data = data, message = msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(resourceStatus = ResourceStatus.LOADING, data = data, message = null)
        }
    }
}