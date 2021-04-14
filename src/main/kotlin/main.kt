import interpreter.initGraph
import logger.init
import model.Graph
import view.MainWindow


fun main(args: Array<String>) {
    init()
    val graph = Graph()
    graph.load("movies")
    initGraph(graph)

    // Vue GUI principale au lancement
    MainWindow(graph)
}

