import controllers.UserStore
import models.User
import mu.KotlinLogging
import utils.isValidInRange
import utils.validGender

//val user = User()
val userStore = UserStore()
private val logger = KotlinLogging.logger {}

fun main(){
    println("Welcome to Health Tracker")

    //Some Temporary Test Data
    userStore.create((User(1, "Homer Simpson", "homer@simpson.com", 178.0, 2.0f, 'M')))
    userStore.create((User(2, "Marge Simpson", "marge@simpson.com", 140.0, 1.6f, 'F')))
    userStore.create((User(3, "Lisa Simpson", "lisa@simpson.com", 100.0, 1.2f, 'F')))
    userStore.create((User(4, "Bart Simpson", "bart@simpson.com", 80.0, 1.0f, 'M')))
    userStore.create((User(5, "Maggie Simpson", "maggie@simpson.com", 50.0, 0.7f, 'F')))


    runApp()
}

fun addUser(){
    userStore.create(getUserDetails())

}

fun listUsers(){
    println("The user details are:")
    userStore.findAll()
        .sortedBy { it.name }
        .forEach{println(it)}
}

fun menu(): Int{
    print("""
        |Main Menu:
        |  1. Add User
        |  2. List Users
        |  3. Search by Id
        |  4. Delete by Id
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
            4 -> deleteUser()
            5 -> updateUser()
            6 -> println("Feature coming soon")
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
        logger.info{"Search - no user found"}
    else
        println(user)
}

fun deleteUser(){
    if (userStore.delete(getUserById()))
        println ("User deleted")
    else
        println ("No user")
}

fun updateUser() {
    listUsers()
    val foundUser = getUserById()

    if(foundUser != null) {
        val user = getUserDetails()
        user.id = foundUser.id
        if (userStore.update(user))
            println("User updated")
        else
            println("User not updated")
    }
    else
        println("User not found")
}

private fun getUserDetails() : User{

    val user = User()
    userStore.create(user)

    println("Please enter the following for the user:")

    print("    Name: ")
    user.name = readLine()!!

    print("    Email: ")
    user.email = readLine()!!

//    print("    Id: ")
//    user.id = readLine()?.toIntOrNull() ?: -1

    print ("    Height (meters): ")
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
    return user
}