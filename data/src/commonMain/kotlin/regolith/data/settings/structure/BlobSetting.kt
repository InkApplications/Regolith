package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import com.inkapplications.data.transformer.NoTransformation
import com.inkapplications.data.validator.DataValidator
import com.inkapplications.data.validator.PassingValidator
import regolith.data.settings.SettingCategory
import regolith.data.settings.SettingLevel

/**
 * A setting that is stored as an arbitrary blob of data.
 */
class BlobSetting(
    override val key: String,
    override val name: String,
    override val defaultValue: ByteArray? = null,
    override val inputTransformer: DataTransformer<ByteArray?, ByteArray?> = NoTransformation(),
    override val inputValidator: DataValidator<ByteArray?> = PassingValidator,
    override val category: SettingCategory? = null,
    override val description: String? = null,
    override val level: SettingLevel = SettingLevel.DEFAULT,
): PrimitiveSetting<ByteArray?> {
    override fun toEntry(value: ByteArray?) = Entry(this, value)
    override fun toString(): String = "Setting($key)"

    data class Entry(
        override val setting: BlobSetting,
        override val value: ByteArray?
    ): SettingEntry<ByteArray?, BlobSetting>
}
