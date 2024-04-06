package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting that is stored as a double precision number.
 */
class DoubleSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Double? = null,
    override val inputTransformer: DataTransformer<Double?, Double?> = NoTransformation(),
    override val inputValidator: DataValidator<Double?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
): PrimitiveSetting<Double?> {
    override fun toString(): String = "Setting($key)"
}
