package controller

class Utilisateur (fName: String,lName: String,age:Int){
    var firstName = fName
    var lastName = lName
    var ageUser = age

    init {
        println("First Name = $firstName")
    }
}