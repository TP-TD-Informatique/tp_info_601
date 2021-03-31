package model

import logger.info
import logger.success
import model.enums.NodeType
import java.util.*
import kotlin.collections.ArrayList

class Graph(private val nodes: ArrayList<Node>, private val relations: ArrayList<Relation>) {

    fun createNode(name: String?, uri: String?, type: NodeType, attributes: HashMap<String, Any?>) {
        val n = Node(UUID.randomUUID(), name, uri, type, attributes, ArrayList())
        info("Create node ", n)
        nodes.add(n)
        success("Node added to graph")
    }

}