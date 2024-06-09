package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToFloat
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.transformer.nullable
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A Floating point number setting.
 *
 * Internally, this is converted and stored as a double type.
 */
class FloatSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Float? = null,
    override val inputValidator: DataValidator<Float?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    val entryFactory: (FloatSetting, Float?) -> SettingEntry<Float?, FloatSetting> = ::Entry,
): DataSetting<Double?, Float?> {
    override val dataTransformer: DataTransformer<Double?, Float?> = DoubleToFloat.nullable()

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
    override fun toEntry(value: Float?) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: FloatSetting,
        override val value: Float?
    ): SettingEntry<Float?, FloatSetting>
}
