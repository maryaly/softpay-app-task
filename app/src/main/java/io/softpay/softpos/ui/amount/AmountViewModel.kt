package io.softpay.softpos.ui.amount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.softpay.sdk.Input
import io.softpay.sdk.TransactionManager
import io.softpay.softpos.R
import io.softpay.softpos.utils.Constants
import io.softpay.softpos.utils.SingleLiveEvent
import io.softpay.softpos.utils.number.NumberHelper
import io.softpay.softpos.utils.resource.ResourceUtilHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AmountViewModel @Inject constructor(
    val mResourceUtilHelper: ResourceUtilHelper,
    private var transactionManager: TransactionManager,
    private val mNumberHelper: NumberHelper
) : ViewModel() {

    var mAmount = MutableLiveData(mResourceUtilHelper.getResourceString(R.string._0_00))
    var mDate = MutableLiveData<String>()
    var mTime = MutableLiveData<String>()
    private val mFinalAmount = StringBuilder()

    private var _mWhichButtonClicked = SingleLiveEvent<String>()
    val mWhichButtonClicked: LiveData<String> = _mWhichButtonClicked


    fun numberOneClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._1))
    }

    fun numberTwoClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._2))
    }

    fun numberThreeClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._3))
    }

    fun numberFourClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._4))
    }

    fun numberFiveClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._5))
    }

    fun numberSixClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._6))
    }

    fun numberSevenClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._7))
    }

    fun numberEightClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._8))
    }

    fun numberNineClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._9))
    }

    fun numberZeroClicked() {
        typeTheNumber(mResourceUtilHelper.getResourceString(R.string._0))
    }

    fun buttonCancelClicked() {
        _mWhichButtonClicked.value = Constants.BUTTON_CANCEL_CLICKED
    }

    fun buttonEnterClicked() {
        _mWhichButtonClicked.value = Constants.BUTTON_ENTER_CLICKED
    }

    fun buttonBackClicked() {
        val typedText = mAmount.value.toString()
        if (typedText.isNotBlank()) {
            if (typedText != mResourceUtilHelper.getResourceString(R.string._0_00))
                mAmount.value = typedText.substring(0, typedText.length - 1)
        } else {
            mAmount.value = mResourceUtilHelper.getResourceString(R.string._0_00)
            mFinalAmount.setLength(0)
        }
    }

    fun getTimeAndDateOfTransaction() {
        val date = SimpleDateFormat("dd/M/yyyy")
        val time = SimpleDateFormat("hh:mm:ss")
        mDate.value = date.format(Date())
        mTime.value = time.format(Date())
    }

    fun dispatchAmount(amount: String) {
        viewModelScope.launch {
            transactionManager.dispatch(
                Input.Amount(
                    mNumberHelper.removeThousandSeparator(mNumberHelper.trimPointOfString(amount))
                        .toLong()
                )
            )
        }
    }

    private fun typeTheNumber(data: String) {
        mFinalAmount.append(data)
        mAmount.value = mFinalAmount.toString()

        mAmount.let { amount ->
            mAmount.value = mNumberHelper.setThousandSeparator(amount.value?.toLong())
        }
    }

}