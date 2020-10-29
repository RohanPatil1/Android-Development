package com.rohan.shop.ui.productlist.dialogutils

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.rohan.shop.R
import com.rohan.shop.data.db.entity.Product
import kotlinx.android.synthetic.main.add_product_dialog.*

class AddProductDialog(context: Context, var dialogListener: AddDialogListener) :
    AppCompatDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_dialog )

        tvAdd.setOnClickListener {
            val name = etName.text.toString()
            val amount = etAmount.text.toString()

            if (name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(context, "Please Enter All The Information", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val item = Product(name, amount.toInt())
            dialogListener.onAddButtonClicked(item)
            dismiss()

        }

        tvCancel.setOnClickListener {
            cancel()
        }

    }
}