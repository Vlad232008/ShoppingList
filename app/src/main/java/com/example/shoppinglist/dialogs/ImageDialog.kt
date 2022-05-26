package com.example.shoppinglist.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.example.shoppinglist.databinding.DeleteDialogBinding
import com.example.shoppinglist.databinding.ImageDialogBinding

object ImageDialog {
    fun showDialog(context: Context, url: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = ImageDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.ivBigImage.setImageURI(url.toUri())
        binding.apply {
            ivBigImage.setOnClickListener {
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
}