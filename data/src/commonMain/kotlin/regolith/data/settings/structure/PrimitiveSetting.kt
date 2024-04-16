package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer
import regolith.data.settings.Validated

/**
 * Key/value data for an application configuration.
 */
sealed interface PrimitiveSetting<STORED>: Setting<STORED>, Validated<STORED> {
    /**
     * The initial value when the application is first run.
     */
    val defaultValue: STORED

    /**
     * Transformer for changing user-supplied input into the setting data type.
     *
     * Since this is a primitive type, no actual data transformation is needed.
     * However, this transformer provides an opportunity to clean up the
     * user input before it is stored. eg. trimming whitespace or formatting.
     */
    val inputTransformer: DataTransformer<STORED, STORED>
}
