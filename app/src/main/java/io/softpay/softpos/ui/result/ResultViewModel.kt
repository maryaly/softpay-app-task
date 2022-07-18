package io.softpay.softpos.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.softpay.sdk.State
import io.softpay.sdk.Transaction
import timber.log.Timber

class ResultViewModel: ViewModel() {

    var mState = MutableLiveData<String>()
    var mDetails = MutableLiveData<String>()
    var mReferenceId = MutableLiveData<String>()


    fun showInfo(transaction: Transaction) {
        transaction.referenceId?.let { id ->
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

}