package regolith.data.settings.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class EnumTransformersTest {
    private enum class TestEnum(val key: String) {
        Value1("key1"),
        Value2("key2"),
    }

    @Test
    fun keyedEnum() {
        val transformer = createKeyedEnumTransformer(TestEnum::key)
        assertEquals(TestEnum.Value1, transformer.transform("key1"))
        assertEquals(TestEnum.Value2, transformer.transform("key2"))
        assertEquals(TestEnum.Value1, transformer.transform(null))
    }

    @Test
    fun namedEnum() {
        val transformer = createKeyedEnumTransformer<TestEnum>()
        assertEquals(TestEnum.Value1, transformer.transform("Value1"))
        assertEquals(TestEnum.Value2, transformer.transform("Value2"))
        assertEquals(TestEnum.Value1, transformer.transform(null))
    }

    @Test
    fun namedEnumCustomDefault() {
        val transformer = createKeyedEnumTransformer<TestEnum>(
            defaultValue = TestEnum.Value2
        )
        assertEquals(TestEnum.Value2, transformer.transform(null))
    }

    @Test
    fun ordinalEnum() {
        val transformer = createOrdinalEnumTransformer<TestEnum>()
        assertEquals(TestEnum.Value1, transformer.transform(0))
        assertEquals(TestEnum.Value2, transformer.transform(1))
        assertEquals(TestEnum.Value1, transformer.transform(null))
    }

    @Test
    fun ordinalEnumCustomDefault() {
        val transformer = createOrdinalEnumTransformer<TestEnum>(
            defaultValue = TestEnum.Value2
        )
        assertEquals(TestEnum.Value2, transformer.transform(null))
    }
}
