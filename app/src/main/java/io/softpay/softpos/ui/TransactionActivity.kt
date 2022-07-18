package io.softpay.softpos.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.softpos.R
import io.softpay.softpos.databinding.ActivityTransactionBinding

@AndroidEntryPoint
class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
    }

    override fun onBackPressed() {
        return
    }
}