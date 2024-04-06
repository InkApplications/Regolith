package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A floating point number with a required value.
 */
class RequiredFloatSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Float,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<Float> = PassingValidator,
): DataSetting<Double?, Float> {
    override val dataTransformer: DataTransformer<Double?, Float> = DoubleTransformations.DoubleToFloat.nullable()
        .then(DefaultingTransformer(defaultValue))

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
    override fun toString(): String = "Setting($key)"
}
