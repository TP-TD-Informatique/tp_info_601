import interpreter.initGraph
import interpreter.query
import logger.exit
import logger.init
import model.Graph
import view.MainView


fun main(args: Array<String>) {
    init()
    val graph = Graph()
    val databaseName = "movies"
    graph.load(databaseName)
    initGraph(graph)

    // Vue GUI principale au lancement
    val homeView = MainView("Knowledge Graph")
    homeView.isVisible = true

    //query("create (actor {name  :=   wilson ,  firstname:=peter  ,  age:=18   })")
    //select (a:actor WHERE { a.name == wilson} a.node)
    //select (a:actor WHERE { a.name == wilson} a.name)
    //select (a:actor,b:actor WHERE { a.name == wilson} a.name)
    //select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)
    query("select (a:actor,b:film WHERE { a.name == wilson,b.name == l'aventure c'est l'aventure} a.name,b.name)")
    /*query("select (a:actor WHERE { a.name == wilson} a.node)")
    query("select (a:actor WHERE { a.name == wilson} a.name)")
    query("select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)")
    */

    graph.save(databaseName)
    exit()
}

