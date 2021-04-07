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
    var reste = splitLast(line,"(")
    reste = splitFirst(reste ,")")


    var nomType = splitFirst(reste,":")
    reste = splitLast(reste,":")

    var typeType = splitFirst(reste,"{")
    reste = splitLast(reste,"{")

    var arg = splitFirst(reste,"}")
    reste = splitLast(reste,"}")

    println("nom type : " + nomType)
    println("type type : " + typeType)
    println("arg : " + arg)
    println("reste : " + reste)

}

fun delete(line: String) {
/*

*/
}

fun select(line: String) {
    var reste = splitLast(line,"(")
    reste = splitFirst(reste ,")")

    var nomType = splitFirst(reste,":")
    reste = splitLast(reste,":")

    var nomVariable = splitFirst(reste," ")
    reste = splitLast(reste," ")

    var testWhere = splitFirst(reste," ")
    reste = splitLast(reste," ")

    // estWhere variable de test à supprimer
    var estWhere = false
    if (testWhere.toUpperCase() == "WHERE") {
        estWhere = true

        var typeType = splitFirst(reste,"{")
        reste = splitLast(reste,"{")

    }

    println("select test nomType : " + nomType)
    println("select test nomVariable : " + nomVariable)
    println("select test testWhere : " + testWhere)
    println("select test estWhere : " + estWhere)

}
