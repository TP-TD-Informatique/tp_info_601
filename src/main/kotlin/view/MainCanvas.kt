package view

import model.Node
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import kotlin.math.sqrt
import kotlin.math.truncate

class MainCanvas(private val nodes: ArrayList<Node>) : Canvas() {

    override fun paint(g: Graphics?) {
        super.paint(g)

        // Get the painter
        val g2 = g as Graphics2D

        // Fill the background
        background = Color.LIGHT_GRAY

        // Paint result
        if (nodes.size > 0) {
            // Paint nodes
            val nb = truncate(sqrt(nodes.size.toDouble())).toInt()
            var i = 0
            var j = 0
            val bord = 10
            val marge = (width - 2 * bord) / nb
            g2.color = Color.RED
            for (node in nodes) {
                g2.fillOval(bord + (i * marge), bord + (j * marge), 10, 10)

                i++
                if (i == nb) {
                    i = 0
                    j++
                }
            }

            // Paint relations
        }
    }
}