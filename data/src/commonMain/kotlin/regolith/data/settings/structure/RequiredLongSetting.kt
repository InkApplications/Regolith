package regolith.data.settings.structure

import com.inkapplications.data.transformer.*
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

class RequiredLongSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: Long,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<Long> = PassingValidator,
    val entryFactory: (RequiredLongSetting, Long) -> SettingEntry<Long, RequiredLongSetting> = ::Entry,
): DataSetting<Long?, Long> {
    override val dataTransformer: DataTransformer<Long?, Long> = DefaultingTransformer(defaultValue)
    override fun toPrimitive(): PrimitiveSetting<Long?> {
        return LongSetting(
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

    override fun toEntry(value: Long) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: RequiredLongSetting,
        override val value: Long,
    ): SettingEntry<Long, RequiredLongSetting>
}
