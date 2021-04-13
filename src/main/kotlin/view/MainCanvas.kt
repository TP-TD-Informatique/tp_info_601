package view

import model.Node
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
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
            val bord = 10
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
            // TODO : g2.drawLine(x1, y1, x2, y2)

            // Paint nodes
            for (node in myNodes) {
                g2.color = Color.RED
                g2.fillOval(node.coord.x, node.coord.y, 10, 10)

                g2.color = Color.BLACK
                if (node.node.name != null)
                    g2.drawString(node.node.name, node.coord.x, node.coord.y)
            }
        }
    }
}