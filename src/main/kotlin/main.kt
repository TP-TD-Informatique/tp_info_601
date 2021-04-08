import logger.exit
import logger.init
import view.MainView
import javax.swing.JFrame


fun main(args: Array<String>) {
    init()

    // Vue GUI principale au lancement
    val homeView: JFrame = MainView("Knowledge Graph")
    homeView.isVisible = true

    exit()
}