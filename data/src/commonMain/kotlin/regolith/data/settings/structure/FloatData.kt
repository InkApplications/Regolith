package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting with a custom type that uses a Float as the backing data type.
 */
class FloatData<DATA>(
    override val key: String,
    override val name: String,
    floatDataTransformer: DataTransformer<Float?, DATA>,
    override val defaultValue: DATA,
    override val inputValidator: DataValidator<DATA> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    val entryFactory: (FloatData<DATA>, DATA) -> SettingEntry<DATA, FloatData<DATA>> = ::Entry,
): DataSetting<Double?, DATA> {
    override val dataTransformer: DataTransformer<Double?, DATA> = DoubleTransformations.DoubleToFloat.nullable()
        .then(floatDataTransformer)

    override fun toPrimitive(): PrimitiveSetting<Double?> {
        return DoubleSetting(
            key = key,
            name = name,
            defaultValue = dataTransformer.reverseTransform(defaultValue),
            inputTransformer = NoTransformation(),
            inputValidator = inputValidator.transformedWith(dataTransformer),
            category = category,
            description = description,
            level = level,
        )
    }

    override fun toEntry(value: DATA) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry<DATA>(
        override val setting: FloatData<DATA>,
        override val value: DATA,
    ): SettingEntry<DATA, FloatData<DATA>>
}
