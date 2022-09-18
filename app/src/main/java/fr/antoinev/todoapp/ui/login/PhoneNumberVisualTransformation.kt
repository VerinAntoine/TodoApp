package fr.antoinev.todoapp.ui.login

import android.telephony.PhoneNumberUtils
import android.text.Selection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*

/**
 * Code by Julien Salvi
 * https://medium.com/google-developer-experts/hands-on-jetpack-compose-visualtransformation-to-create-a-phone-number-formatter-99b0347fc4f
 */
class PhoneNumberVisualTransformation(
    countryCode: String = Locale.getDefault().country
): VisualTransformation {

    private val phoneNumberFormatter = PhoneNumberUtil.getInstance().getAsYouTypeFormatter(countryCode)

    override fun filter(text: AnnotatedString): TransformedText {
        val transformation = format(text, Selection.getSelectionEnd(text))

        return TransformedText(AnnotatedString(transformation.formatted ?: ""), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return transformation.originalToFormatted[offset]
            }

            override fun transformedToOriginal(offset: Int): Int {
                return transformation.formattedToOriginal[offset]
            }
        })
    }

    private fun format(string: CharSequence, cursor: Int): Transformation {
        phoneNumberFormatter.clear()

        val currentIndex = cursor - 1
        var formatted: String? = null
        var lastNonSeparator = 0.toChar()
        var hasCursor = false

        string.forEachIndexed { index, char ->
            if(PhoneNumberUtils.isNonSeparator(char)) {
                if(lastNonSeparator.code != 0) {
                    formatted = getFormattedNumber(lastNonSeparator, hasCursor)
                    hasCursor = false
                }
                lastNonSeparator = char
            }

            if(index == currentIndex) hasCursor = true
        }

        if(lastNonSeparator.code != 0) formatted = getFormattedNumber(lastNonSeparator, hasCursor)

        val originalToFormatted = mutableListOf<Int>()
        val formattedToOriginal = mutableListOf<Int>()
        var specialCharCount = 0

        formatted?.forEachIndexed { index, char ->
            if(!PhoneNumberUtils.isNonSeparator(char)) specialCharCount++
            else originalToFormatted.add(index)

            formattedToOriginal.add(index - specialCharCount)
        }

        originalToFormatted.add(originalToFormatted.maxOrNull()?.plus(1) ?: 0)
        formattedToOriginal.add(formattedToOriginal.maxOrNull()?.plus(1) ?: 0)

        return Transformation(formatted, originalToFormatted, formattedToOriginal)
    }

    private fun getFormattedNumber(lastNonSeparator: Char, hasCursor: Boolean): String? {
        return if(hasCursor) phoneNumberFormatter.inputDigitAndRememberPosition(lastNonSeparator)
        else phoneNumberFormatter.inputDigit(lastNonSeparator)
    }

    private data class Transformation(
        val formatted: String?,
        val originalToFormatted: List<Int>,
        val formattedToOriginal: List<Int>
    )

}