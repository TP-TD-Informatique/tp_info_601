import logger.exit
import logger.init
import view.MainView
import javax.swing.JFrame


fun main(args: Array<String>) {
    /*init()

    // Vue GUI principale au lancement
    val homeView: JFrame = MainView("Knowledge Graph")
    homeView.isVisible = true

    exit()
*/
    //select (a:actor WHERE { a.name == wilson} a.node)
    //select (a:actor WHERE { a.name == wilson} a.name)
    //select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)
    query("select (a:actor WHERE { a.name == wilson} a.node)")
    query("select (a:actor WHERE { a.name == wilson} a.name)")
    query("select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)")
}
