import java.awt.Color
import java.awt.Graphics
import javax.swing.JComponent

internal class MyNode(i: Int, i1: Int, i2: Int) : JComponent() {
    private val serialVersionUID = 1L

    fun init(x: Int, y: Int, diameter: Int) {
        this.setLocation(x, y)
        this.setSize(diameter, diameter)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.color = Color.red
        g.fillOval(0, 0, 100, 100)
    }
}