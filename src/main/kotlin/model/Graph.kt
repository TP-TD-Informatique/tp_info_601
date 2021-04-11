package model

import logger.info
import logger.success
import logger.warning
import model.enums.NodeType
import model.enums.RelationType
import java.io.*
import java.util.*

class Graph(private var nodes: ArrayList<Node>, private var relations: ArrayList<Relation>, private var index: Int) {

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

    fun pathBetweenTwoNodes_profondeur(nd1: Int, nd2: Int): Array<Node> {
        val node1 = getNode(id = nd1)
        val node2 = getNode(id = nd2)
        val result = Stack<Node>()

        nodes.forEach { it.found = false }

        if (node1 != null && node2 != null) {
            result.push(node1)
            var currentNode = node1
            node1.found = true

            while (currentNode != node2) {
                val nextNode = (currentNode?.relations?.firstOrNull { !it.second.found })?.second

                if (nextNode != null) {
                    nextNode.found = true
                    result.push(nextNode)
                    currentNode = nextNode
                } else {
                    result.pop()
                    currentNode = result.peek()
                }
            }
        } else {
            error("Node ${if (node1 == null) 1 else 2} not found for profondeur")
        }

        return result.toArray() as Array<Node>
    }
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.

    fun save(databaseName: String) {
        if (File("./databases").mkdir()) {
            warning("Dir 'databases' created")
        }

        saveNodes(databaseName)
        saveRelations(databaseName)
        saveIndex(databaseName)
    }

    private fun saveNodes(databaseName: String) {
        info("Save ${nodes.size} nodes")
        try {
            val oos = ObjectOutputStream(FileOutputStream("./databases/$databaseName.nodes"))
            oos.writeObject(nodes)
            oos.close()
            success("${nodes.size} nodes saved !")
        } catch (e: IOException) {
            error("Error when save nodes : ${e.message}")
        }
    }

    private fun saveRelations(databaseName: String) {
        info("Save ${relations.size} relations")
        try {
            val oos = ObjectOutputStream(FileOutputStream("./databases/$databaseName.relations"))
            oos.writeObject(nodes)
            oos.close()
            success("${relations.size} relations saved !")
        } catch (e: IOException) {
            error("Error when save relations : ${e.message}")
        }
    }

    private fun saveIndex(databaseName: String) {
        info("Save index")
        try {
            val dos = DataOutputStream(FileOutputStream("./databases/$databaseName.index"))
            dos.write(index)
            dos.close()
            success("Index saved !")
        } catch (e: IOException) {
            error("Error when save index : ${e.message}")
        }
    }

    fun load(databaseName: String) {
        loadNodes(databaseName)
        loadRelations(databaseName)
        loadIndex(databaseName)
    }

    private fun loadNodes(databaseName: String) {
        info("Load nodes")
        try {
            val ois = ObjectInputStream(FileInputStream("./databases/$databaseName.nodes"))
            nodes = ois.readObject() as ArrayList<Node>
            success("Nodes loaded !")
        } catch (e: Exception) {
            error("Error when load nodes : ${e.message}")
        }
    }

    private fun loadRelations(databaseName: String) {
        info("Load relations")
        try {
            val ois = ObjectInputStream(FileInputStream("./databases/$databaseName.relations"))
            relations = ois.readObject() as ArrayList<Relation>
            success("Relations loaded !")
        } catch (e: Exception) {
            error("Error when load relations : ${e.message}")
        }
    }

    private fun loadIndex(databaseName: String) {
        info("Load index")
        try {
            val dis = DataInputStream(FileInputStream("./databases/$databaseName.index"))
            index = dis.readInt()
            success("Index loaded")
        } catch (e: Exception) {
            error("Error when load index : ${e.message}")
        }
    }
}