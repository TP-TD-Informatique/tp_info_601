package model

import logger.debug
import logger.info
import logger.success
import logger.warning
import model.enums.NodeType
import model.enums.RelationType
import java.io.*
import java.util.*

class Graph(
    private var nodes: ArrayList<Node> = ArrayList<Node>(),
    private var relations: ArrayList<Relation> = ArrayList<Relation>(),
    private var index: Int = 0
) {

    /**
     * Create a new node
     *
     * Required params :
     * - type
     * - attributes
     *
     * Optional params :
     * - name
     * - uri
     *
     * Example :
     * ```
     * val attr = HashMap<String, Any?>()
     * attr["firstname"] = "John"
     * attr["lastname"] = "Doe"
     *
     * var myNode = createNode("Actor", "https://www.a_website.com", NodeType.valueOf("Person"), attr)
     * ```
     *
     * @param name The name of the node
     * @param uri A link to a website for more information
     * @param type The type of the node, you can use `NodeType.valueOf(String)`
     * @param attributes The attributes of the node, it's just a simple String => Any? association array
     *
     * @return The created Node
     */
    fun createNode(name: String?, uri: String?, type: NodeType, attributes: HashMap<String, Any?>): Node {
        val n = Node(index++, name, uri, type, attributes, ArrayList())
        info("Create node ", n)
        nodes.add(n)
        success("Node added to graph")

        return n
    }

    /**
     * Same as previous function but with one difference : attributes are passed via a variadic
     * @see createNode
     *
     * @param name String?
     * @param uri String?
     * @param type NodeType
     * @param attributes vararg Pair<String, Any?>
     *
     * @return The created Node
     */
    fun createNode(name: String?, uri: String?, type: NodeType, vararg attributes: Pair<String, Any?>): Node {
        val attr = HashMap<String, Any?>()
        for (attribute in attributes) {
            attr[attribute.first] = attribute.second
        }

        return createNode(name, uri, type, attr)
    }

    /**
     * Return a node depending on it's name, uri, type, id and attributes
     * If one of this parameters is null, it is not used for the search
     * You can get the nth node with the offset parameter
     *
     * @param name String?
     * @param uri String?
     * @param type NodeType?
     * @param attributes HashMap<String, Any?>?
     * @param id Int?
     * @param offset Int = 0
     *
     * @return A Node or null
     */
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

    /**
     * Same as getNode, but returns an array of node.
     * You can get the nth node with offset and limit the number of result with limit
     * @see getNode
     *
     * @param name String?
     * @param uri String?
     * @param type NodeType?
     * @param attributes HashMap<String, Any?>?
     * @param id Int?
     * @param offset Int = 0
     * @param limit Int = 25
     *
     * @return An ArrayList of Node
     */
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

    /**
     * Return the last created node
     * Or null if there is 0 node
     *
     * @return Node?
     */
    fun getLastNode() = (
            if (index > 0)
                nodes[index - 1]
            else
                null
            )

    /**
     * Get the node with id, and update the values.
     * You can update name, uri and attributes.
     * If one of this parameter is null, the value is not updated
     *
     * @param name String?
     * @param uri String?
     * @param attributes HashMap<String, Any?>?
     * @param id Int
     *
     * @return If the node is updated
     */
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

    /**
     * Get the node with id, and delete it and his relations
     *
     * @param id Int
     *
     * @return If the node is deleted
     */
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

    /**
     * Create a relation between node from and node to
     *
     * @param from Node
     * @param to Node
     * @param type RelationType - The type of the relation
     *
     * @return The created Relation
     */
    fun createRelation(from: Node, to: Node, type: RelationType): Relation {
        val r = Relation(type, from, to)
        info("Create relation ", r)
        from.relations.add(r)
        relations.add(r)
        success("Relation created")

        return r
    }

    /**
     * Return a Relation depending on his type, and the two nodes id
     * If one of the parameters is null, is not used for search
     * You can get the nth relation with offset
     *
     * @param type RelationType?
     * @param from Int?
     * @param to Int?
     * @param offset Int = 0
     *
     * @return A Relation or null
     */
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

    /**
     * Get a Relation depending on two nodes and the type id and delete it
     *
     * @param from Int
     * @param to Int
     * @param type RelationType?
     *
     * @return If the relation is deleted
     */
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

    /**
     * Search a path composed of Node and Relation between nd1 and nd2
     * It use a depth process
     *
     * @param nd1 Int - Node1 id
     * @param nd2 Int - Node2 id
     *
     * @return The list of Node between nd1 and nd2
     */
    fun pathBetweenTwoNodes(nd1: Int, nd2: Int): Array<Node> {
        val node1 = getNode(id = nd1)
        val node2 = getNode(id = nd2)
        val result = Stack<Node>()

        nodes.forEach { it.found = false }

        if (node1 != null && node2 != null) {
            result.push(node1)
            var currentNode = node1
            node1.found = true

            while (currentNode != node2) {
                debug("pathBetweenTwoNodes -> currentNode : $currentNode")
                val nextNode = (currentNode?.relations?.firstOrNull { !it.second.found })?.second

                if (nextNode != null) {
                    nextNode.found = true
                    result.push(nextNode)
                    currentNode = nextNode
                } else {
                    debug("pathBetweenTwoNodes -> cul-de-sac")
                    result.pop()
                    currentNode = result.peek()
                }
            }
        }

        return result.toArray() as Array<Node>
    }

    /**
     * Return a tree of Node with nd as root
     * The result is a HashMap, each nodes is associated to its parent
     *
     * @param nd Int - Node id
     * @param result HashMap<Node, Node>
     *
     * @return HashMap<Node, Node>
     */
    fun treeView(nd: Int, result: HashMap<Node, Node>): HashMap<Node, Node> {
        val node = getNode(id = nd)

        nodes.forEach { it.found = false }

        if (node != null) {
            val queue = LinkedList<Node>() // It's a Queue (LinkedList implements Queue)

            queue.push(node)
            node.found = true
            while (queue.isNotEmpty()) {
                val currentNode = queue.pop()
                debug("treeView -> currentNode : ", currentNode)

                currentNode.relations.forEach {
                    val n = it.second
                    if (!n.found) {
                        queue.push(n)
                        n.found = true
                        result[n] = currentNode
                    }
                }
            }
        }

        return result
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.

    /**
     * Save the database
     *
     * @param databaseName String
     */
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
            dos.writeInt(index)
            dos.close()
            success("Index saved ! $index")
        } catch (e: IOException) {
            error("Error when save index : ${e.message}")
        }
    }

    /**
     * Load the database
     *
     * @param databaseName String
     */
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
            success("Nodes loaded ! ${nodes.size}")
        } catch (e: Exception) {
            error("Error when load nodes : ${e.message}")
        }
    }

    private fun loadRelations(databaseName: String) {
        info("Load relations")
        try {
            val ois = ObjectInputStream(FileInputStream("./databases/$databaseName.relations"))
            relations = ois.readObject() as ArrayList<Relation>
            success("Relations loaded ! ${relations.size}")
        } catch (e: Exception) {
            error("Error when load relations : ${e.message}")
        }
    }

    private fun loadIndex(databaseName: String) {
        info("Load index")
        try {
            val dis = DataInputStream(FileInputStream("./databases/$databaseName.index"))
            index = dis.readInt()
            success("Index loaded $index")
        } catch (e: Exception) {
            error("Error when load index : ${e.message}")
        }
    }
}