package com.butterfly.testreddit.models.converters

import androidx.annotation.Nullable

/**
 * Base implementation of [Converter]
 *
 * @param <IN>  Input type
 * @param <OUT> Output type
 *
 */

abstract class BaseConverter<IN : Any, OUT : Any> : Converter<IN, OUT> {

    override val single: SingleConverter<IN, OUT> by lazy { SingleConverter(this) }

    /**
     * Convert IN to OUT
     *
     * @param inObject object ot inToOut
     * @return Nullable [OUT] converted object
     */
    override fun inToOut(@Nullable inObject: IN?): OUT? = processConvertInToOut(inObject)

    /**
     * Convert List of IN to List of OUT
     *
     * @param inObjects [List] of [IN] objects to listInToOut
     * @return [List] of converted objects
     */
    @Nullable
    override fun listInToOut(@Nullable inObjects: List<IN>?): List<OUT> =
        inObjects?.mapNotNull { inToOut(it) }
            ?: listOf()

    protected abstract fun processConvertInToOut(@Nullable inObject: IN?): OUT?

}