package regolith.data.settings

import com.inkapplications.data.validator.DataValidator

/**
 * Constructs used for data validation
 */
interface Validated<DATA> {
    /**
     * Validator to be used on user-supplied input fields before storage.
     */
    val inputValidator: DataValidator<DATA>
}
