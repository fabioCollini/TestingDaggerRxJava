package it.droidcon.testingdaggerrxjava

import org.junit.Test
import org.mockito.Mockito

class DaggerMockExample {
    class MyModule {
        fun provideString() = "myString"

        fun provideInt() = 123
    }

    @Test
    fun myTest() {
        val myModule = MyModule()
        val testFields = collectTestFields()
        val subclass = createSubclass(myModule, testFields)
        println(subclass.provideString())
        println(subclass.provideInt())
    }

    private fun <T : Any> createSubclass(module: T, testFields: Map<Class<*>, Any>): T =
            Mockito.mock(module.javaClass) { invocation ->
                testFields[invocation.method.returnType]
                        ?: invocation.method(module, *invocation.arguments)
            }

    private fun collectTestFields() =
            mapOf(
                    String::class.java to "test",
                    Integer.TYPE to 456
            )
}
