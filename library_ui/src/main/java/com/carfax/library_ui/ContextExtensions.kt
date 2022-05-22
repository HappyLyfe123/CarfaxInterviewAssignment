package com.carfax.library_ui

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.createErrorNoActionAlertDialog(title:String, message: String, positiveButtonValue: String): AlertDialog{
    return AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButtonValue){
            dialog, _ ->
            dialog.dismiss()
        }
    }.create()
}