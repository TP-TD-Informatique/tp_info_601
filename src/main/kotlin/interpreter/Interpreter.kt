package interpreter



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
    var listeNomVariable = ArrayList<String>()
    var listeTypeVariable = ArrayList<String>()

    var reste = splitLast(line,"(")
    reste = splitFirst(reste ,")")


    var typeType = splitFirst(reste,"{")
    reste = splitLast(reste,"{")

    var arg = splitFirst(reste,"}")
    reste = splitLast(reste,"}")

    arg = supprimeEspace(arg);

    while (arg.indexOf(",") != -1) {
        listeNomVariable.add(splitFirst(arg, ":="))
        arg = splitLast(arg, ":=")
        listeTypeVariable.add(splitFirst(arg, ","))
        arg = splitLast(arg, ",")
    }
    listeNomVariable.add(splitFirst(arg, ":="))
    arg = splitLast(arg, ":=")
    listeTypeVariable.add(arg)


    println("type type : " + typeType)
    println("listeNomVariable : " + listeNomVariable)
    println("listeTypeVariable : " + listeTypeVariable)


}

fun delete(line: String) {
/*

*/
}

fun select(line: String) {
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
            while (phrase.indexOf(",") != -1) {

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