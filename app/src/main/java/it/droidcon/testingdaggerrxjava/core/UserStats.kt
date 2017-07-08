package it.droidcon.testingdaggerrxjava.core

import it.droidcon.testingdaggerrxjava.core.gson.User

data class UserStats(
        val id: Int,
        val reputation: Int,
        val name: String,
        val badges: List<String>
) {

    constructor(user: User, badges: List<String>) : this(user.id, user.reputation, user.name, badges)

    override fun toString() = "$reputation $name - ${badges.joinToString(", ")}"
}
