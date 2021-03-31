package model

import model.enums.NodeType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Node(
    val id: UUID,
    var name: String?,
    var uri: String?,
    val type: NodeType,
    val attributes: HashMap<String, Any?>,
    val relations: ArrayList<Relation>
) {


    override fun toString() {
        var res: String = "($name:$type"
        if (attributes.isNotEmpty()) {
            res += " {"

        }

        return res
    }
}