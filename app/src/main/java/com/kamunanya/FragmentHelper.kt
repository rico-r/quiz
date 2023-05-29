package com.kamunanya

import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.TypefaceSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kamunanya.R

fun setAppTitle(fragment: Fragment, title: String) {
    val spannableString = SpannableString(title)
    spannableString.setSpan(
        TypefaceSpan(fragment.resources.getFont(R.font.bodo)),
        0,
        title.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    (fragment.activity as AppCompatActivity).supportActionBar?.title = spannableString
}