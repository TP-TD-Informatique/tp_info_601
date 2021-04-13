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
    //query("create (r:relation,a:actor,b:actor )")
    //select (a:actor WHERE { a.name == wilson} a.node)
    //select (a:actor WHERE { a.name == wilson} a.name)
    //select (a:actor,b:actor WHERE { a.name == wilson} a.name)
    //select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)
    query("select (a:actor,b:movie WHERE { a.name == wilson,b.name == l'aventure c'est l'aventure,a.id == 788, b.id ==965} delete )")
    /*query("select (a:actor WHERE { a.name == wilson} a.node)")
    query("select (a:actor WHERE { a.name == wilson} a.name)")
    query("select (a:actor WHERE { a.name == wilson, a.firstname != fred, a.age > 25} a.name)")
    */

    /* PROTOTYPE NE PAS SUPPR

       create (<nomType> {<nomAttribut1> := <valeurAttribut1>, <nomAttribut2> := <valeurAttribut2>})
       -
       create(acteur {nom := smith, prenom := jim, genre := hélicoptère de combat})

       -
       delete (<nomVariable>:<nomType> where {<nomVariable>.<nomAttribut> == <valeurAttribut>})
       delete(:<nomType>)
       -
       delete(a:acteur where {a.nom == geyer})
       delete(:acteur)
       -

       update(<nomVariable>:<nomType> where { <nomVariable>.<nomAttribut> == <valeurAttribut>} to {<nomAttribut> := <valeurAttribut> })
       update(:acteur {nomAttribut})
       -
       update(a.acteur where { a.nom == smith} to {a.prénom := kevin})
       -
       select (<nomVariable>:<nomType> where { <nomVariable>.<nomAttribut> == <valeurAttribut>} return <noeud>)

       */
    graph.save(databaseName)
    exit()
}

