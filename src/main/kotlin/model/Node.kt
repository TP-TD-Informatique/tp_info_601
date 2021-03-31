package model

import model.enums.NodeType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Node(
    val id: Int,
    var name: String?,
    var uri: String?,
    val type: NodeType,
    val attributes: HashMap<String, Any?>,
    val relations: ArrayList<Relation>
) {


    override fun toString(): String {
        var res = "($name:$type"
        if (attributes.isNotEmpty()) {
            res += " {"

            for (attribute in attributes) {
                res += "${attribute.key}:${attribute.value} "
            }

            res +=  "}"
        }
        res += ")"

        return res
    }
}