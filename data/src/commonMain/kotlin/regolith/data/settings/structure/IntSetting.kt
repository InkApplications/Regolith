package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.IntTransformations
import com.inkapplications.data.transformer.LongTransformations.LongToInt
import com.inkapplications.data.transformer.nullable
import com.inkapplications.data.transformer.then
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * An integer number setting.
 *
 * Internally this is converted and stored as a long type.
 */
class IntSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Int? = null,
    override val inputValidator: DataValidator<Int?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    val entryFactory: (IntSetting, Int?) -> SettingEntry<Int?, IntSetting> = ::Entry,
): DataSetting<Long?, Int?> {
    override val dataTransformer: DataTransformer<Long?, Int?> = LongToInt.nullable()
    override fun toPrimitive(): PrimitiveSetting<Long?> {
        return LongSetting(
            key = key,
            name = name,
            defaultValue = dataTransformer.reverseTransform(defaultValue),
            inputTransformer = dataTransformer.then(IntTransformations.IntToLong.nullable()),
            inputValidator = inputValidator.transformedWith(LongToInt.nullable()),
            category = category,
            description = description,
            level = level,
        )
    }
    override fun toEntry(value: Int?) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: IntSetting,
        override val value: Int?
    ): SettingEntry<Int?, IntSetting>
}
