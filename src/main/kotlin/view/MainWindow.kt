package view

import interpreter.query
import model.Node
import java.awt.*
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder

class MainWindow : JFrame("Knowledge graph") {
    private lateinit var input: JTextField
    private lateinit var queryButton: JButton
    private lateinit var canvas: MainCanvas
    var nodes = ArrayList<Node>()

    init {
        val dim = Toolkit.getDefaultToolkit().screenSize
        setLocation(dim.width / 2 - width / 2, dim.height / 2 - height / 2)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        minimumSize = Dimension(600, 600)

        drawContent()

        isVisible = true
    }

    private fun drawContent() {
        background = Color.LIGHT_GRAY

        layout = BorderLayout()


        val topPanel = JPanel()
        topPanel.layout = BorderLayout()
        topPanel.border = MatteBorder(0, 0, 1, 0, Color.BLACK)

        input = JTextField()
        input.border = CompoundBorder(
            MatteBorder(10, 10, 10, 10, Color.LIGHT_GRAY),
            CompoundBorder(MatteBorder(2, 2, 2, 2, Color.BLACK), EmptyBorder(4, 4, 4, 4))
        )
        topPanel.add(input, BorderLayout.CENTER)

        queryButton = JButton("Query !")
        queryButton.border = CompoundBorder(
            MatteBorder(10, 10, 10, 10, Color.LIGHT_GRAY),
            CompoundBorder(MatteBorder(2, 2, 2, 2, Color.BLACK), EmptyBorder(4, 4, 4, 4))
        )
        queryButton.background = Color.WHITE
        queryButton.cursor = Cursor(Cursor.HAND_CURSOR)
        queryButton.addActionListener {
            nodes = query(input.text)
            canvas.repaint()
        }
        topPanel.add(queryButton, BorderLayout.EAST)

        add(topPanel, BorderLayout.NORTH)


        val canvasPanel = JPanel()
        canvasPanel.layout = BorderLayout()

        canvas = MainCanvas(nodes)
        canvasPanel.add(canvas, BorderLayout.CENTER)

        add(canvasPanel, BorderLayout.CENTER)
    }
}