package interpreter

import model.Graph
import model.Graph.*
import model.Node
import model.enums.NodeType

private lateinit var GRAPH: Graph

fun initGraph(graph: Graph) {
    GRAPH = graph
}

fun interpreterError(msg: String) {
    println("erreur type : ")
    println(msg)
}

fun query(line: String) {
    var firstWord = splitFirst(line, " ")
    var restLine = splitLast(line, " ")
    firstWord = firstWord.toUpperCase()
    println(" action : " + firstWord)

    if (firstWord == "CREATE") {
        create(restLine)
    } else if (firstWord == "SELECT") {
        select(restLine)
    } else  {
        interpreterError("error")
    }
}

fun splitFirst(line: String, sep: String): String {
    val array = line.split(sep)
    if (array.isNotEmpty()) {
        return array[0]
    }
    return "error"
}

fun splitLast(line: String, sep: String): String {
    val array = line.split(sep, limit = 2)
    if (array.size > 1) {
        return array[1]
    } else {
        return "ERROR"
    }
}

fun create(line: String) {
    var nomVariable = ""
    var hashMap = HashMap<String, Any?>()
    var uri = "UNSET"
    var name = "UNSET"

    var reste = splitLast(line, "(")
    reste = splitFirst(reste, ")")

    var estRelation = true
    for (item in reste) {
        if (item == '{') {
            estRelation = false
        }
    }
    if (!estRelation) {

        var typeType = splitFirst(reste, "{")
        reste = splitLast(reste, "{")

        var arg = splitFirst(reste, "}")
        reste = splitLast(reste, "}")

        arg = supprimeEspace(arg);

        while (arg.indexOf(",") != -1) {
            nomVariable = splitFirst(arg, ":=")
            arg = splitLast(arg, ":=")
            if (nomVariable.toUpperCase() == "NAME") {
                name = splitFirst(arg, ",")
            } else if (nomVariable.toUpperCase() == "URI") {
                uri = splitFirst(arg, ",")
            } else {
                hashMap.put(nomVariable, splitFirst(arg, ","))
            }
            arg = splitLast(arg, ",")
        }
        nomVariable = splitFirst(arg, ":=")
        arg = splitLast(arg, ":=")
        if (nomVariable.toUpperCase() == "NAME") {
            name = splitFirst(arg, ",")
        } else if (nomVariable.toUpperCase() == "URI") {
            uri = splitFirst(arg, ",")
        } else {
            hashMap.put(nomVariable, splitFirst(arg, ","))
        }
        GRAPH.createNode(type = NodeType.valueOf(typeType.toUpperCase()), uri = (if (uri == "UNSET") null else uri), name = (if (name == "UNSET") null else name), attributes = hashMap)
    } else {
        var nomRelation = splitFirst(reste, ":")
        reste = splitLast(reste, ":")
        var typeRelation = splitFirst(reste, ",")
        reste = splitLast(reste, ",")
        var nomLien1 = splitFirst(reste, ":")
        reste = splitLast(reste, ":")
        var noeud1 = splitFirst(reste, ",")
        reste = splitLast(reste, ",")
        var nomLien2 = splitFirst(reste, ":")
        reste = splitLast(reste, ":")
        var noeud2 = reste
    }
}

fun delete(listNode:ArrayList<Node>) {
    for (i in listNode){
        GRAPH.deleteNode(i.id)
    }
}

fun select(line: String): ArrayList<Node> {
    var listeRes = ArrayList<Node>()
    var listeId = ArrayList<String>()
    var place: Int
    var nomVariable = ""
    var hashMapList = ArrayList<HashMap<String, Any?>>()
    var listeName = ArrayList<String>()
    var listeUri = ArrayList<String>()
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
        println("phrase1 : " + phrase)
        println("reste1 : " + reste)

        for (int in listeVariable) {
            hashMapList.add(HashMap<String, Any?>())
            listeName.add("UNSET")
            listeUri.add("UNSET")
            listeId.add("UNSET")

        }
        phrase = supprimeEspace(phrase)
        if (phrase.toUpperCase() == "WHERE") {
            while ((reste.indexOf(",") < reste.indexOf("}")) && reste.indexOf(",") != -1) {
                phrase = supprimeEspace(splitFirst(reste, "."))
                place = listeVariable.indexOf(phrase)
                if (place == -1) {
                    error("erreur la variable " + splitFirst(reste, ".") + " n'est pas spécifiée")
                } else {
                    reste = splitLast(reste, ".")
                    phrase = splitFirst(reste, "==")
                    phrase = supprimeEspace(phrase)
                    nomVariable = phrase
                    reste = splitLast(reste, "==")
                    phrase = splitFirst(reste, ",")
                    phrase = supprimeEspace(phrase)
                    hashMapList.get(place).put(nomVariable, phrase)
                    reste = splitLast(reste, ",")
                }

            }
            phrase = supprimeEspace(splitFirst(reste, "."))
            place = listeVariable.indexOf(phrase)
            if (place == -1) {
                error("erreur la variable " + splitFirst(reste, ".") + " n'est pas spécifiée")
            } else {
                reste = splitLast(reste, ".")
                phrase = splitFirst(reste, "==")
                phrase = supprimeEspace(phrase)
                nomVariable = phrase
                reste = splitLast(reste, "==")
                phrase = splitFirst(reste, "}")
                phrase = supprimeEspace(phrase)
                hashMapList.get(place).put(nomVariable, phrase)
                reste = splitLast(reste, "}")
                println(hashMapList)
            }
            for (i in 0 until listeName.size) {
                if (hashMapList[i].containsKey("name")) {
                    listeName[i] = hashMapList[i]["name"] as String
                    hashMapList[i].remove("name")
                }
                if (hashMapList[i].containsKey("uri")) {
                    listeUri[i] = hashMapList[i]["uri"] as String
                    hashMapList[i].remove("uri")
                }
                if (hashMapList[i].containsKey("id")) {
                    listeId[i] = hashMapList[i]["id"] as String
                    hashMapList[i].remove("id")
                }
            }
            for (i in 0 until listeVariable.size) {
                val name = listeName[i]
                val uri = listeUri[i]
                val id = listeId[i]
                val type = listeTypeVariable[i]
                listeRes.addAll(GRAPH.getNodes(uri = (if (uri == "UNSET") null else uri), name = (if (name == "UNSET") null else name), id = (if (id == "UNSET") null else id.toInt()), type = NodeType.valueOf(type.toUpperCase()), attributes = hashMapList[i]))
            }
            if (supprimeEspace(reste).toUpperCase() == "DELETE") {
              delete(listeRes)
            }
        }

    }

    return listeRes


}

fun supprimeEspace(line: String): String {
    var res = ""
    var listeChar = line.toCharArray();
    for (item in listeChar) {
        if (item != ' ') {
            res += item
        }
    }
    return res
}