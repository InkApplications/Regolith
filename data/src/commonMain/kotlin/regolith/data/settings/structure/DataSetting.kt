package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import regolith.data.settings.Validated

/**
 * A setting that can be represented by a structured data type.
 *
 * @param DATA The structured data type for the setting.
 */
interface DataSetting<STORED, DATA>: Setting<STORED>, Validated<DATA> {
    /**
     * The initial value when the application is first run.
     */
    val defaultValue: DATA

    /**
     * Transformer to convert data to/from its primitive type.
     */
    val dataTransformer: DataTransformer<STORED, DATA>

    /**
     * Get the primitive setting definition used for the stored data.
     */
    fun toPrimitive(): PrimitiveSetting<STORED>
}
