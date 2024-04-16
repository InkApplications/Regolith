package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

class RequiredStringSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: String,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<String> = PassingValidator,
): DataSetting<String?, String> {
    override val dataTransformer: DataTransformer<String?, String> = DefaultingTransformer(defaultValue)
    override fun toPrimitive(): PrimitiveSetting<String?> {
        return StringSetting(
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
    override fun toString(): String = "Setting($key)"
}
