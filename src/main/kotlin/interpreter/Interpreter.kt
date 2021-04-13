package interpreter
import model.Graph
import model.Graph.*
import model.Node
import model.enums.NodeType

private lateinit var GRAPH: Graph

fun initGraph(graph: Graph) {
    GRAPH = graph
}

fun interpreterError(msg : String){
    println("erreur type : ")
    println(msg)
}

fun query(line: String) {
    var firstWord = splitFirst(line," ")
    var restLine = splitLast(line," ")
    firstWord = firstWord.toUpperCase()
    println(" action : " + firstWord)

    if(firstWord == "CREATE"){
        create(restLine)
    }else if(firstWord == "SELECT"){
        select(restLine)
    }else if(firstWord == "DELETE") {
        delete(restLine)
    }else {
        interpreterError("error")
    }
}

fun splitFirst(line: String,sep: String): String {
    val array = line.split(sep)
    if (array.isNotEmpty()){
        return array[0]
    }
    return "error"
}

fun splitLast(line: String,sep: String):String{
    val array = line.split(sep,limit = 2)
    if (array.size > 1){return array[1] }
    else {return "ERROR"}
}

fun create(line: String) {
    var nomVariable = ""
    var hashMap  = HashMap<String,Any?>()
    var uri = "UNSET"
    var name = "UNSET"

    var reste = splitLast(line,"(")
    reste = splitFirst(reste ,")")


    var typeType = splitFirst(reste,"{")
    reste = splitLast(reste,"{")

    var arg = splitFirst(reste,"}")
    reste = splitLast(reste,"}")

    arg = supprimeEspace(arg);

    while (arg.indexOf(",") != -1) {
        nomVariable = splitFirst(arg, ":=")
        arg = splitLast(arg, ":=")
        if (nomVariable.toUpperCase() == "NAME") {
            name = splitFirst(arg, ",")
        }else if(nomVariable.toUpperCase() == "URI"){
            uri = splitFirst(arg, ",")
        }else {
            hashMap.put(nomVariable, splitFirst(arg, ","))
        }
        arg = splitLast(arg, ",")
    }
    nomVariable = splitFirst(arg, ":=")
    arg = splitLast(arg, ":=")
    if (nomVariable.toUpperCase() == "NAME") {
        name = splitFirst(arg, ",")
    }else if(nomVariable.toUpperCase() == "URI"){
        uri = splitFirst(arg, ",")
    }else {
        hashMap.put(nomVariable, splitFirst(arg, ","))
    }
    GRAPH.createNode(type = NodeType.valueOf(typeType),uri = uri,name = name,attributes = hashMap)

}

fun delete(line: String) {
}

fun select(line: String)/*:ArrayList<Node>*/ {
    var nomVariable = ""
    var hashMapList = ArrayList<HashMap<String,Any?>>()
    var uri = "UNSET"
    var name = "UNSET"
    var listeVariable = ArrayList<String>()
    var listeTypeVariable = ArrayList<String>()
    var reste = splitLast(line, "(")
    reste = splitFirst(reste, ")")

    if (reste.indexOf("{") != -1) {
        var phrase = splitFirst(reste, "{")
        reste = splitLast(reste, "{")


        while (phrase.indexOf(",") != -1) {
            listeVariable.add(splitFirst(phrase, ":"))
            phrase = splitLast(phrase, ":")
            listeTypeVariable.add(splitFirst(phrase, ","))
            phrase = splitLast(phrase, ",")
        }
        listeVariable.add(splitFirst(phrase, ":"))
        phrase = splitLast(phrase, ":")
        listeTypeVariable.add(splitFirst(phrase, " "))
        phrase = splitLast(phrase, " ")

        println(listeTypeVariable)
        println(listeVariable)
        println("phrase : " + phrase)
        println("reste : " + reste)

        if (phrase.toUpperCase() == "WHERE") {
            while (reste.indexOf(",") != -1) {
                var place = listeVariable.indexOf(splitFirst(reste,"."))
                if (place == -1){
                    error("erreur la variable " + splitFirst(reste,".") + " n'est pas spécifiée")
                }

            }
        }


    }



//    var nomType = splitFirst(reste,":")
//    reste = splitLast(reste,":")
//
//    var nomVariable = splitFirst(reste," ")
//    reste = splitLast(reste," ")
//
//    var testWhere = splitFirst(reste," ")
//    reste = splitLast(reste," ")
//
//    // estWhere variable de test à supprimer
//    var estWhere = false
//    if (testWhere.toUpperCase() == "WHERE") {
//        estWhere = true
//
//        var typeType = splitFirst(reste,"{")
//        reste = splitLast(reste,"{")
//
//
//    }

//    println("select test nomType : " + nomType)
//    println("select test nomVariable : " + nomVariable)
//    println("select test testWhere : " + testWhere)
//    println("select test estWhere : " + estWhere)
    //println("select test reste : " + reste)


}
fun supprimeEspace(line : String): String {
    var res = ""
    var listeChar = line.toCharArray();
    for (item in listeChar){
        if (item != ' '){
            res += item
        }
    }
    return res
}