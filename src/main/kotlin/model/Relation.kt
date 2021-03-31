package model

import model.enums.RelationType

class Relation(val type: RelationType, val first: Node, val second: Node) {

    override fun toString() = "[:$type]"
}