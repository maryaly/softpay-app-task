package io.softpay.softpos.utils.number

interface NumberHelper {
    fun setThousandSeparator(input: Number?): String
    fun removeThousandSeparator(input: String): String
    fun trimPointOfString(string: String): String
}
