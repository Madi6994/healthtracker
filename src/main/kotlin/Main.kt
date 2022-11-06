import controllers.UserStore
import models.User
import utils.isValidInRange
import utils.validGender

//val user = User()
val userStore = UserStore()

fun main(){
    println("Welcome to Health Tracker")
    runApp()
}

fun addUser(){

    val user = User()
    userStore.create(user)

    println("Please enter the following for the user:")

    print("    Name: ")
    user.name = readLine()!!

    print("    Email: ")
    user.email = readLine()!!

    print("    Id: ")
    user.id = readLine()?.toIntOrNull() ?: -1

    print ("   Height (meters): ")
    val height = readLine()?.toFloatOrNull() ?: 0f
    if (isValidInRange(0.5,3.0, height.toDouble()))
        user.height = height
    else
        user.height = 0f


    print ("   Weight (kg): ")
    val weight = readLine()?.toDoubleOrNull() ?: .0
    if (isValidInRange(25.0,500.0, weight))
        user.weight = weight
    else
        user.weight = 0.0

    print ("   Gender (M/F/O): ")
    user.gender = validGender(readLine()!!.getOrNull(0) ?: ' ')
}

fun listUsers(){
    println("The user details are: ${userStore.findAll()}")
}

fun menu(): Int{
    print("""
        |Main Menu:
        |  1. Add User
        |  2. List Users
        |  3. Search by Id
        |  0. Exit
        |Please enter your option: """.trimMargin())
    return readLine()?.toIntOrNull() ?: -1
}

fun runApp(){
    var input: Int
    do {
        input = menu()
        when(input) {
            1 -> addUser()
            2 -> listUsers()
            3 -> searchById()
            in(4..6) -> println("Feature coming soon")
            0 -> println("Bye...")
            else -> print("Invalid Option")
        }
    } while (input != 0)
}

fun getUserById() : User?{
    print("Enter the id of the user: ")
    return  userStore.findOne(readLine()?.toIntOrNull() ?: -1)
}

fun searchById() {
    val user = getUserById()
    if (user == null)
        println ("No user found")
    else
        println(user)
}

fun deleteUser(){
    if (userStore.delete(getUserById()))
        println ("User deleted")
    else
        println ("No user")
}