package io.softpay.softpos.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.softpos.databinding.ActivityTransactionBinding

@AndroidEntryPoint
class TransactionActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTransactionBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mainViewModel.launchTransactionFlow()

    }
}