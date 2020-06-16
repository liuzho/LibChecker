package com.absinthe.libchecker.view.dialogfragment

import android.app.Dialog
import android.os.Bundle
import com.absinthe.libchecker.R
import com.absinthe.libchecker.view.ClassifyDialogView
import com.absinthe.libchecker.view.LCDialogFragment
import com.absinthe.libchecker.bean.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

const val EXTRA_ITEM_LIST = "EXTRA_ITEM_LIST"

class ClassifyDialogFragment : LCDialogFragment() {

    var item: ArrayList<AppItem> = ArrayList()
    private val dialogView by lazy { ClassifyDialogView(requireContext()) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList<AppItem>(
                EXTRA_ITEM_LIST
            )?.toList()?.let {
                dialogView.adapter.setNewInstance(it.toMutableList())
                item = it as ArrayList<AppItem>
            }
        } else {
            dialogView.adapter.setNewInstance(item.toMutableList())
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(String.format(getString(R.string.title_statistics_dialog), getTitle()))
            .setView(dialogView)
            .create()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(EXTRA_ITEM_LIST, item)
        super.onSaveInstanceState(outState)
    }

    private fun getTitle(): String {
        if (item.isEmpty()) {
            return ""
        }

        return when (item[0].abi) {
            ARMV8 -> getString(R.string.string_64_bit)
            ARMV7 -> getString(R.string.string_32_bit)
            ARMV5 -> getString(R.string.string_32_bit)
            NO_LIBS -> getString(R.string.no_libs)
            else -> ""
        }
    }
}