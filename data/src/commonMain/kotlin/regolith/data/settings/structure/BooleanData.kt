package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting with a custom type that uses a Boolean as the backing data type.
 */
class BooleanData<DATA>(
    override val key: String,
    override val name: String,
    booleanDataTransformer: DataTransformer<Boolean, DATA>,
    override val defaultValue: DATA,
    override val inputValidator: DataValidator<DATA> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    val entryFactory: (BooleanData<DATA>, DATA) -> SettingEntry<DATA, BooleanData<DATA>> = ::Entry,
): DataSetting<Long?, DATA> {
    override val dataTransformer: DataTransformer<Long?, DATA> = LongTransformations.LongToBoolean.nullable()
        .then(DefaultingTransformer(booleanDataTransformer.reverseTransform(defaultValue)))
        .then(booleanDataTransformer)

    override fun toPrimitive(): PrimitiveSetting<Long?> {
        return LongSetting(
            key = key,
            name = name,
            defaultValue = dataTransformer.reverseTransform(defaultValue),
            inputValidator = inputValidator.transformedWith(dataTransformer),
            category = category,
            description = description,
            level = level,
        )
    }

    override fun toEntry(value: DATA) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry<DATA>(
        override val setting: BooleanData<DATA>,
        override val value: DATA,
    ): SettingEntry<DATA, BooleanData<DATA>>
}
