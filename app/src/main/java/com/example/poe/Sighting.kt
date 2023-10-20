package com.example.poe

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Sighting(
    val species: String,
    val location: String,
    val date: String,
    val description: String,
    val imageUrl: Uri
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.EMPTY
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(species)
        parcel.writeString(location)
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeParcelable(imageUrl, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sighting> {
        override fun createFromParcel(parcel: Parcel): Sighting {
            return Sighting(parcel)
        }

        override fun newArray(size: Int): Array<Sighting?> {
            return arrayOfNulls(size)
        }
    }
}


