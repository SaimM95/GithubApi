package com.example.scotiatakehomeassignment.network

class Resource<T>(val status: Status, val data: T?) {

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun empty(): Resource<*> {
            return Resource(Status.EMPTY, null)
        }

        fun error(): Resource<*> {
            return Resource(Status.ERROR, null)
        }

        fun loading(): Resource<*> {
            return Resource(Status.LOADING, null)
        }
    }

}