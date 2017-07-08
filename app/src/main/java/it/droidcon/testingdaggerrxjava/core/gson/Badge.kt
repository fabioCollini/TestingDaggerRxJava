package it.droidcon.testingdaggerrxjava.core.gson

data class Badge(val name: String) {

    override fun toString() = name

    companion object {

        fun createList(vararg names: String): List<Badge> = names.map { Badge(it) }
    }
}
