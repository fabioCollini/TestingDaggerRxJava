package it.droidcon.testingdaggerrxjava

import it.cosenonjaviste.daggermock.DaggerMockRule

class DaggerMock {
    companion object {
        inline fun <reified C> rule(vararg modules: Any) = DaggerMockRule(C::class.java, *modules)
    }
}