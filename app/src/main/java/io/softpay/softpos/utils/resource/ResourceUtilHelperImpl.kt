package io.softpay.softpos.utils.resource

import android.content.Context
import io.softpay.softpos.utils.Constants
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ResourceUtilHelperImpl @Inject constructor(
    private val mContext: Context
) : ResourceUtilHelper {

    override fun getResourceString(resourceId: Int): String =
        try {
            mContext.getString(resourceId)
        } catch (exp: Exception) {
            Timber.e(exp)
            Constants.EMPTY_STRING
        }
}