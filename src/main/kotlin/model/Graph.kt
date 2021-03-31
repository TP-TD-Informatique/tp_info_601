package model

import logger.info
import logger.success
import model.enums.NodeType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Graph(private val nodes: ArrayList<Node>, private val relations: ArrayList<Relation>, private var index: Int) {

    fun createNode(name: String?, uri: String?, type: NodeType, attributes: HashMap<String, Any?>): Node {
        val n = Node(index++, name, uri, type, attributes, ArrayList())
        info("Create node ", n)
        nodes.add(n)
        success("Node added to graph")

        return n
    }

    fun createNode(name: String?, uri: String?, type: NodeType, vararg attributes: Pair<String, Any?>): Node {
        val attr = HashMap<String, Any?>()
        for (attribute in attributes) {
            attr[attribute.first] = attribute.second
        }

        return createNode(name, uri, type, attr)
    }

    fun getNode(name: String? = null, uri: String? = null, type: NodeType? = null, attributes: HashMap<String, Any?>? = null, id: UUID? = null): Node? {
        var res: Node? = null

        nodes.forEach {
            // Test simple parameters
            if ((if (name == null) true else it.name == name) &&
                (if (uri == null) true else it.uri == uri) &&
                (if (type == null) true else it.type == type)) {
                // Test attributes
            }
        }

        return res
    }
}