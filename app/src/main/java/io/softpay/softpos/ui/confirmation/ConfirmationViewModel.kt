package io.softpay.softpos.ui.confirmation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.softpay.sdk.Input
import io.softpay.sdk.State
import io.softpay.sdk.Transaction
import io.softpay.sdk.TransactionManager
import io.softpay.softpos.R
import io.softpay.softpos.utils.Constants
import io.softpay.softpos.utils.SingleLiveEvent
import io.softpay.softpos.utils.number.NumberHelper
import io.softpay.softpos.utils.resource.ResourceUtilHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val mResourceUtilHelper: ResourceUtilHelper,
    private val mNumberHelper: NumberHelper,
    private var mTransactionManager: TransactionManager,


    ) : ViewModel() {

    var mState = MutableLiveData<String>()
    var mReferenceId = MutableLiveData<String>()
    var mDate = MutableLiveData<String>()
    var mTime = MutableLiveData<String>()
    var mAmount = MutableLiveData<String>()
    var mStoreName = MutableLiveData<String>()
    var mPostalCode = MutableLiveData<String>()
    var mAddress = MutableLiveData<String>()

    private var _mIsConfirmButtonClicked = SingleLiveEvent<String>()
    val mIsConfirmButtonClicked: LiveData<String> = _mIsConfirmButtonClicked

    fun buttonConfirmClicked() {
        _mIsConfirmButtonClicked.value = Constants.BUTTON_CONFIRM_CLICKED
    }

    fun buttonCancelClicked() {
        _mIsConfirmButtonClicked.value = Constants.BUTTON_CANCEL_CLICKED
    }

    fun confirmAmount(value: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            mTransactionManager.dispatch(
                Input.Confirm(value)
            )
        }
    }

    fun showInfo(time: String, date: String, model: Transaction) {
        model.referenceId.let { id ->
            mReferenceId.value = id
        }
        model.state.let { state ->
            if (state == State.AWAITING_CONFIRMATION)
                mState.value = "Payment Approved"
        }
        model.store.let { store ->
            mAddress.value = store?.address
            mPostalCode.value = store?.postalCode
            mStoreName.value = store?.name
        }
        model.referenceId.let { id ->
            mReferenceId.value = id
        }
        model.amount.let { amount ->
            mAmount.value = "${mNumberHelper.setThousandSeparator(amount?.div(100))} ${
                mResourceUtilHelper.getResourceString(
                    R.string.kr
                )
            }"
        }

        time.let {
            mTime.value = it
        }
        date.let {
            mDate.value = it
        }
    }
}