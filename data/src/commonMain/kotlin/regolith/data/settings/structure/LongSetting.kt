package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting that is stored as a Long value.
 */
class LongSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Long? = null,
    override val inputTransformer: DataTransformer<Long?, Long?> = NoTransformation(),
    override val inputValidator: DataValidator<Long?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
): PrimitiveSetting<Long?> {
    override fun toString(): String = "Setting($key)"
}
