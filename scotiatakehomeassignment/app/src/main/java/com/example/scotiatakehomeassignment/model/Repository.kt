package com.example.scotiatakehomeassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * {
    "id":"123"
    "name":"HelloWorld",
    "description":"Create hello world",
    "updated_at":"2020-05-13T11:15:02Z",
    "stargazers_count":4,
    "forks_count":19,
    "owner":
        {
            "id":"123",
            "avatar_url":"https://avatars3.githubusercontent.com/u/383316?v=4"
        }
    }
 */
@Parcelize
data class Repository(
    val id: String,
    val name: String,
    val description: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("stargazers_count") val starsCount: Int,
    @field:SerializedName("forks_count") val forksCount: Int,
    val owner: Owner
) : Parcelable

@Parcelize
data class Owner(
    val id: String,
    @field:SerializedName("avatar_url") val avatarUrl: String
) : Parcelable