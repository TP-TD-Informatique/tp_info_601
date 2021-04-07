package model

import logger.info
import logger.success
import logger.warning
import model.enums.NodeType
import model.enums.RelationType

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
                            success("Node found ", it)
                            return it
                        }
                    }
                }
            } else if (name == null && uri == null && type == null && id == null && attributes == null) {
                if (off-- == 0) {
                    success("Node found ", it)
                    return it
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

        for (i in offset until (offset + limit)) {
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
            success("Node updated ", n)

            return true
        }

        return false
    }

    fun deleteNode(id: Int): Boolean {
        val n = getNode(id = id)

        if (n != null) {
            relations.forEach {
                if (it.first == n || it.second == n) {
                    deleteRelation(from = it.first.id, to = it.second.id)
                }
            }
            nodes.remove(n)
            success("Node removed")

            return true
        }

        warning("Node not removed")
        return false
    }

    fun createRelation(from: Node, to: Node, type: RelationType): Relation {
        val r = Relation(type, from, to)
        info("Create relation ", r)
        from.relations.add(r)
        relations.add(r)
        success("Relation created")

        return r
    }

    fun getRelation(
        type: RelationType? = null,
        from: Int? = null,
        to: Int? = null,
        offset: Int = 0
    ): Relation? {
        var off = offset

        relations.forEach {
            if ((if (type == null) true else it.type == type) &&
                (if (from == null) true else it.first.id == from) &&
                (if (to == null) true else it.second.id == to)
            ) {
                if (off-- == 0) {
                    success("Relation found ", it)
                    return it
                }
            } else if (type == null && from == null && to == null) {
                if (off-- == 0) {
                    success("Relation found ", it)
                    return it
                }
            }
        }

        warning("Relation not found")
        return null
    }

    fun deleteRelation(
        from: Int,
        to: Int,
        type: RelationType? = null
    ): Boolean {
        val r = getRelation(type, from, to)

        if (r != null) {
            val n = getNode(id = from)

            if (n != null) {
                n.relations.remove(r)
                relations.remove(r)

                success("Relation removed")
                return true
            }
        }

        warning("Relation not removed")
        return false
    }
}