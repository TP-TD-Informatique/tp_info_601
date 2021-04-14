package view

import model.Node
import java.awt.*
import javax.swing.JTextField
import kotlin.math.sqrt
import kotlin.math.truncate

class MainCanvas(private val nodes: ArrayList<Node>) : Canvas() {
    private class Coord(val x: Int, val y: Int)
    private class MyNode(val node: Node, val coord: Coord)

    private var myNodes = ArrayList<MyNode>()

    override fun paint(g: Graphics?) {
        super.paint(g)

        // Get the painter
        val g2 = g as Graphics2D

        // Fill the background
        background = Color.LIGHT_GRAY

        // Paint result
        if (nodes.size > 0) {
            val nb = truncate(sqrt(nodes.size.toDouble())).toInt()
            var i = 0
            var j = 0
            val bord = 50
            val marge = (width - 2 * bord) / nb
            for (node in nodes) {
                myNodes.add(
                    MyNode(node, Coord(bord + (i * marge), bord + (j * marge)))
                )

                i++
                if (i == nb) {
                    i = 0
                    j++
                }
            }

            // Paint relations
            g2.color = Color.BLACK
            for (node in myNodes) {
                node.node.relations.forEach { relation ->
                    val node2 = nodes.firstOrNull { it == relation.second }
                    if (node2 != null) {
                        val myNode2 = myNodes.first { it.node == node2 }
                        // Draw line
                        g2.drawLine(node.coord.x, node.coord.y, myNode2.coord.x, myNode2.coord.y)
                        // Draw Text
                        val x = (myNode2.coord.x + node.coord.x) / 2
                        val y = (myNode2.coord.y + node.coord.y) / 2
                        g2.drawString(relation.type.toString(), x, y)
                    }
                }
            }

            // Paint nodes
            for (node in myNodes) {
                g2.color = Color.RED
                g2.fillOval(node.coord.x, node.coord.y, 20, 20)

                g2.color = Color.BLACK
                if (node.node.name != null)
                    g2.drawString(node.node.name, node.coord.x, node.coord.y)
            }
        }
    }

    // La fonction drawLine modifiée faite en Java avec Damien
    /*private fun drawLien(node1: MyNode, node2: Node, nomRelation: String, g2: Graphics2D) {
        // Le label qui indique le nom de la liaison
        val label = JTextField(nomRelation)
        val labelXpos = (node2. + node1.coords.x) / 2
        val labelYpos = (node2.coords.y + node1.coords.y) / 2
        label.background = null
        label.border = null
        label.setBounds(labelXpos, labelYpos, label.preferredSize.width, label.preferredSize.height)

        // La ligne
        g2.stroke = BasicStroke(2F)
        g2.drawLine(
            node1.coords.x + node1.dimensions.width / 2,
            node1.coords.y + node1.dimensions.height / 2,
            node2.coords.x,
            node2.coords.y
        )
        // je sé pas commen ajouter
        // add(label)
    }*/
}