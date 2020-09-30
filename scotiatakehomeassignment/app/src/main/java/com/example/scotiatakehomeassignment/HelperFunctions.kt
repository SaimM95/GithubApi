package com.example.scotiatakehomeassignment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(
    patternIn: String,
    patternOut: String,
    dateIn: String
): String? {
    val sdf = SimpleDateFormat(patternIn, Locale.getDefault())
    return try {
        val date = sdf.parse(dateIn)
        val sdfOut = SimpleDateFormat(patternOut, Locale.getDefault())
        date?.let {
            return@let sdfOut.format(it)
        } ?: run {
            return@run null
        }
    } catch (e: ParseException) {
        Timber.e(e)
        null
    }
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}