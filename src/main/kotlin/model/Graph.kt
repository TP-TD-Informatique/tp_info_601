package model

import logger.info
import logger.success
import logger.warning
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

        nodes.forEach {
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
                            success("Node found")
                            return it
                        }
                    }
                }

            }
        }

        warning("Node not found")
        return null
    }

    fun getNodes(
        name: String? = null,
        uri: String? = null,
        type: NodeType? = null,
        attributes: HashMap<String, Any?>? = null,
        id: Int? = null,
        offset: Int = 0,
        limit: Int = 25
    ): ArrayList<Node> {
        val res = ArrayList<Node>()

        for (i in offset until (offset+limit)) {
            getNode(name, uri, type, attributes, id, offset)?.let { res.add(it) }
        }
        success(res.size, " node found")

        return res
    }

    fun updateNode(
        name: String? = null,
        uri: String? = null,
        attributes: HashMap<String, Any?>? = null,
        id: Int
    ): Boolean {
        val n = getNode(id = id)

        if (n != null) {
            if (name != null) n.name = name
            if (uri != null) n.uri = uri
            attributes?.forEach { (key, value) ->
                n.attributes[key] = value
            }
            success("Node updated")

            return true
        }

        return false
    }

    fun deleteNode(id: Int): Boolean {
        val n = getNode(id = id)

        if (n != null) {
            relations.forEach {
                if (it.first == n) {
                    n.relations.forEach { it2 ->
                        if (it2.second == it.second) {
                            n.relations.remove(it2)
                        }
                    }
                    relations.remove(it)
                    success("Relation removed")
                } else if (it.second == n) {
                    it.first.relations.forEach { it2 ->
                        if (it2.second == n) {
                            it.first.relations.remove(it2)
                        }
                    }
                    relations.remove(it)
                    success("Relation removed")
                }
            }
            nodes.remove(n)
            success("Node removed")

            return true
        }

        warning("Node not removed")
        return false
    }
}