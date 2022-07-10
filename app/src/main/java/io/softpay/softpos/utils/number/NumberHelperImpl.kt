package io.softpay.softpos.utils.number

import io.softpay.softpos.utils.Constants
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class NumberHelperImpl @Inject constructor() : NumberHelper {

    override fun setThousandSeparator(input: Number?): String {
        return DecimalFormat(Constants.PATTERN_NUMBER).format(input)
    }

    override fun removeThousandSeparator(input: String): String {
        return input.replace("٬", "").replace(",", "")
    }

    override fun trimPointOfString(string: String): String {
        return if (string.contains(".")) {
            string.replace(".", "")
        } else {
            string
        }
    }
}
