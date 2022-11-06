package controllers

import models.User

class UserStore {

    val users = ArrayList<User>()

    fun findAll(): List<User> {
        return users
    }

    fun create(user: User) {
        users.add(user)
    }

    fun findOne(id: Int): User? {
        return users.find { p -> p.id == id }
    }

    fun delete(user: User?): Boolean {
        return users.remove(user)
    }
}