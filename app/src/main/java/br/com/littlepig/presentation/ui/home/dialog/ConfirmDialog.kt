package br.com.littlepig.presentation.ui.home.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.com.littlepig.R

class ConfirmDialog(
    private var onClick: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity).apply {
                setTitle(R.string.attention)
                setMessage(R.string.ensure_delete)
                    .setPositiveButton(R.string.button_confirm) { _, _ ->
                        onClick.invoke()
                    }
                    .setNegativeButton(R.string.button_cancel) { dialog , _ ->
                        dialog.dismiss()
                    }
            }

            builder.create()
        } ?: throw IllegalArgumentException("Activity cannot be null")
    }
}
