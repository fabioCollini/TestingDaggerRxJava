package it.droidcon.testingdaggerrxjava.core

import it.droidcon.testingdaggerrxjava.core.gson.User

data class UserStats(
        val user: User,
        val badges: List<String>,
        val id: Int = user.id,
        val reputation: Int = user.reputation,
        val name: String = user.name
) {

    override fun toString() = "$reputation $name - ${badges.joinToString(", ")}"

    companion object {

        fun create(id: Int, reputation: Int, name: String, vararg badges: String): UserStats {
            return UserStats(User.create(id, reputation, name), listOf(*badges))
        }
    }
}
