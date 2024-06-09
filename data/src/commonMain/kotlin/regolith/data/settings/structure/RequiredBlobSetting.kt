package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.DefaultingTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import com.inkapplications.data.validator.transformedWith
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A [BlobSetting] with a required value
 */
class RequiredBlobSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: ByteArray,
    override val level: SettingLevel = SettingLevel.DEFAULT,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val inputValidator: DataValidator<ByteArray> = PassingValidator,
    val entryFactory: (RequiredBlobSetting, ByteArray) -> SettingEntry<ByteArray, RequiredBlobSetting> = ::Entry,
): DataSetting<ByteArray?, ByteArray> {
    override val dataTransformer: DataTransformer<ByteArray?, ByteArray> = DefaultingTransformer(defaultValue)

    override fun toPrimitive(): PrimitiveSetting<ByteArray?> {
        return BlobSetting(
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

    override fun toEntry(value: ByteArray) = entryFactory(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: RequiredBlobSetting,
        override val value: ByteArray
    ): SettingEntry<ByteArray, RequiredBlobSetting>
}
