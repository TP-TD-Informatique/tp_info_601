import controller.Utilisateur
import logger.exit
import logger.init


fun main(args: Array<String>) {
    init()
    val view = MyView()
    val person1 = Utilisateur("John","Doe",24)
    println("Hello world!")

    exit()
}