package io.softpay.softpos.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.softpay.softpos.utils.SingleLiveEvent
import io.softpay.softpos.utils.resource.ResourceUtilHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.softpay.sdk.Input
import io.softpay.sdk.State
import io.softpay.sdk.Transaction
import io.softpay.sdk.TransactionManager
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private var mTransactionManager: TransactionManager,

    ) : ViewModel() {

    var mState = MutableLiveData<String>()
    var mDetails = MutableLiveData<String>()
    var mReferenceId = MutableLiveData<String>()

    private var _mIsConfirmButtonClicked = SingleLiveEvent<Boolean>()
    val mIsConfirmButtonClicked: LiveData<Boolean> = _mIsConfirmButtonClicked

    fun buttonConfirmClicked() {
        _mIsConfirmButtonClicked.value = true
    }

    fun showInfo(transaction: Transaction) {
        transaction.referenceId.let { id ->
            mReferenceId.value = id
        }

        transaction.state.let {
            if (it == State.SUCCESS) {
                mState.value = "Success"
                mDetails.value = "Sit Back and relax. Your Burger will be with you soon."
                Timber.e("" + it)
            } else {
                mState.value = "Failed"
                mDetails.value = "Unfortunately Your Transaction Request is Failed."
                Timber.e("" + it)
            }
        }

    }

    suspend fun cancelTransaction() {
        mTransactionManager.dispatch(
            Input.Cancel
        )
    }
}