package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting that is stored as a String.
 */
class StringSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: String? = null,
    override val inputTransformer: DataTransformer<String?, String?> = NoTransformation(),
    override val inputValidator: DataValidator<String?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
): PrimitiveSetting<String?> {
    override fun toString(): String = "Setting($key)"
}
