package com.butterfly.testreddit.models.converters

import androidx.annotation.Nullable

/**
 * Encapsulate logic for converting from one type to another and vice versa
 *
 * @param <IN>  Input type
 * @param <OUT> Output type
 *
 */

interface Converter<IN, OUT> {

    val single: SingleConverter<IN, OUT>

    /**
     * Convert IN to OUT
     *
     * @param inObject object ot inToOut
     * @return Nullable [OUT] converted object
     */
    fun inToOut(@Nullable inObject: IN?): OUT?

    /**
     * Convert List of IN to List of OUT
     *
     * @param inObjects [List] of [IN] objects to listInToOut
     * @return [List] of converted objects
     */
    @Nullable
    fun listInToOut(@Nullable inObjects: List<IN>?): List<OUT>
}
