package model

import model.enums.RelationType
import java.io.Serializable

class Relation(val type: RelationType, val first: Node, val second: Node) : Serializable {

    override fun toString() = "[:$type]"
}