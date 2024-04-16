package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

class RequiredIntSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Int,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<Int> = PassingValidator,
): DataSetting<Long?, Int> {
    override val dataTransformer: DataTransformer<Long?, Int> = LongTransformations.LongToInt.nullable().then(DefaultingTransformer(defaultValue))
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
}
