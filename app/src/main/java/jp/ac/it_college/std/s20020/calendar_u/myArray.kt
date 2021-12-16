package jp.ac.it_college.std.s20020.calendar_u

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class myArray(val list: List<myArray>) : ArrayList<myArray>(list), Parcelable {
}