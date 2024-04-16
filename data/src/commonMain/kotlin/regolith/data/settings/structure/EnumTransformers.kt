package regolith.data.settings.structure

import com.inkapplications.data.transformer.DataTransformer

/**
 * Creates a transformer for an Enum class that converts an enum into a string
 * for storage.
 *
 * By default, this will use the enum's name as the key. However, this can be
 * prone to error if the type is renamed. You can provide a custom key provider
 * to use a static value to allow the code structure to be renamed while
 * retaining a custom key for any property:
 *
 *     createKeyedEnumTransformer(ExampleEnum::customKey)
 *
 * @param keyProvider A function to provide the stored string for a given enum value.
 * @param defaultValue The value to use when the stored value is null.
 */
inline fun <reified T: Enum<T>> createKeyedEnumTransformer(
    crossinline keyProvider: (T) -> String = { it.name },
    defaultValue: T = enumValues<T>()[0],
): DataTransformer<String?, T> {
    return object: DataTransformer<String?, T> {
        override fun transform(data: String?): T {
            if (data == null) {
                return defaultValue
            }
            return enumValues<T>().firstOrNull { keyProvider(it) == data }
                ?: throw IllegalArgumentException("Unknown key: $data for ${T::class.simpleName}")
        }

        override fun reverseTransform(data: T): String {
            return keyProvider(data)
        }
    }
}

/**
 * Creates a transformer for an Enum class that converts the enum's ordinal
 * into a Long value for storage.
 *
 * @param defaultValue The value to use when the stored value is null.
 */
inline fun <reified T: Enum<T>> createOrdinalEnumTransformer(
    defaultValue: T = enumValues<T>()[0],
): DataTransformer<Long?, T> {
    return object: DataTransformer<Long?, T> {
        override fun transform(data: Long?): T {
            if (data == null) {
                return defaultValue
            }
            return enumValues<T>()[data.toInt()]
        }

        override fun reverseTransform(data: T): Long {
            return data.ordinal.toLong()
        }
    }
}
