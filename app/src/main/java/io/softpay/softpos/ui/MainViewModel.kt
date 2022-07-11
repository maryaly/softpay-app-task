package io.softpay.softpos.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.softpay.sdk.Input
import io.softpay.sdk.Transaction
import io.softpay.sdk.TransactionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val transactionManager: TransactionManager) :
    ViewModel() {

    private val _transaction = MutableSharedFlow<Transaction>()
    val transaction = _transaction.asSharedFlow()

    var mTransaction = MutableLiveData<Transaction>()

    fun launchTransactionFlow() {
        viewModelScope.launch {
            transactionManager.newTransactionFlow()
                .catch { e ->
                    Timber.e("${e.message}")
                }.collect { value ->
                    mTransaction.value = value
                    _transaction.emit(value)
                }
        }
    }

    fun cancelTransaction() {
        viewModelScope.launch {
            transactionManager.dispatch(
                Input.Cancel
            )
        }
    }
}