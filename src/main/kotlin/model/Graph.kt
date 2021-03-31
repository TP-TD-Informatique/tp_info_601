package model

import logger.info
import logger.success
import model.enums.NodeType

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

    fun getNode(
        name: String? = null,
        uri: String? = null,
        type: NodeType? = null,
        attributes: HashMap<String, Any?>? = null,
        id: Int? = null,
        offset: Int = 0
    ): Node? {
        var off = offset

        nodes.forEach { it ->
            // Test simple parameters
            if ((if (name == null) true else it.name == name) &&
                (if (uri == null) true else it.uri == uri) &&
                (if (type == null) true else it.type == type) &&
                (if (id == null) true else it.id == id)
            ) {
                // Test attributes
                if (attributes != null) {
                    if (it.attributes.size == attributes.size) {
                        var ok = true
                        it.attributes.forEach { it2 ->
                            if (attributes[it2.key] != it2.value)
                                ok = false
                        }

                        if (ok && off-- == 0) {
                            return it
                        }
                    }
                }

            }
        }

        return null
    }
}