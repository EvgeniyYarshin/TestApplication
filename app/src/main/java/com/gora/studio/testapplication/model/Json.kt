package com.gora.studio.testapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import okhttp3.ResponseBody
import org.json.JSONObject

@Entity
data class Json(
    @field:SerializedName("id")
    @field:PrimaryKey
    var id: Int,
    @field:SerializedName("json")
    var json: String
)