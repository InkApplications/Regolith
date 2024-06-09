package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.DefaultingTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A double precision number with a required value.
 */
class RequiredDoubleSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Double,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<Double> = PassingValidator,
    val entryFactory: (RequiredDoubleSetting, Double) -> SettingEntry<Double, RequiredDoubleSetting> = ::Entry,
): DataSetting<Double?, Double> {
    override val dataTransformer: DataTransformer<Double?, Double> = DefaultingTransformer(defaultValue)
    override fun toPrimitive(): PrimitiveSetting<Double?> {
        return DoubleSetting(
            key = key,
            name = name,
            defaultValue = defaultValue,
            inputTransformer = NoTransformation(),
            inputValidator = inputValidator.transformedWith(dataTransformer),
            category = category,
            description = description,
            level = level,
        )
    }
    override fun toEntry(value: Double) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: RequiredDoubleSetting,
        override val value: Double
    ): SettingEntry<Double, RequiredDoubleSetting>
}
