package regolith.resources.android

import regolith.resources.ResourceIdentifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Enforces Int ID's for Android resources
 */
@OptIn(ExperimentalContracts::class)
internal fun requireInt(id: ResourceIdentifier): ResourceIdentifier.IdInt {
    contract {
        returns() implies (id is ResourceIdentifier.IdInt)
    }
    if (id !is ResourceIdentifier.IdInt) {
        throw IllegalArgumentException("Android resources require Integer IDs")
    }
    return id
}
