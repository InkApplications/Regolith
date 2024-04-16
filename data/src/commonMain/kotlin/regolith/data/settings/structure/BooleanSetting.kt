package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.transformer.LongTransformations.LongToBoolean
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A Boolean setting.
 *
 * Internally this is converted and stored as a Long value.
 */
class BooleanSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Boolean = false,
    override val inputValidator: DataValidator<Boolean> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
): DataSetting<Long?, Boolean> {
    override val dataTransformer: DataTransformer<Long?, Boolean> = LongToBoolean.nullable().then(DefaultingTransformer(defaultValue))

    override fun toPrimitive(): PrimitiveSetting<Long?> {
        return LongSetting(
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

    override fun toString(): String = "Setting($key)"
}
