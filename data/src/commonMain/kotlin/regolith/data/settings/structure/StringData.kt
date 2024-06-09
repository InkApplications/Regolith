package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting with a custom type that uses a string as the backing data type
 */
class StringData<DATA>(
    override val key: String,
    override val name: String,
    override val dataTransformer: DataTransformer<String?, DATA>,
    override val defaultValue: DATA,
    val dataValidator: DataValidator<DATA> = PassingValidator,
    override val inputValidator: DataValidator<DATA> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    val entryFactory: (StringData<DATA>, DATA) -> SettingEntry<DATA, StringData<DATA>> = ::Entry,
): DataSetting<String?, DATA> {
    override fun toPrimitive(): PrimitiveSetting<String?> {
        return StringSetting(
            key = key,
            name = name,
            defaultValue = dataTransformer.reverseTransform(defaultValue),
            inputTransformer = NoTransformation(),
            inputValidator = dataValidator.transformedWith(dataTransformer),
            category = category,
            description = description,
            level = level,
        )
    }

    override fun toEntry(value: DATA) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry<DATA>(
        override val setting: StringData<DATA>,
        override val value: DATA,
    ): SettingEntry<DATA, StringData<DATA>>
}

