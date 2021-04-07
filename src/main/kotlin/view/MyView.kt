import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

class MyView : JFrame("Knowledge Graph") {
    init {
        createUI("Knowledge Graph")
    }

    // créé la GUI
    private fun createUI(title: String) {

        setTitle(title)

        createMenuBar()

        // la fenêtre doit se fermer quand on clique sur la croix
        // defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        // taille de la fenêtre
        setSize(1000, 800)
        // on ajoute le texte "Hello, World!" dans la fenêtre
        contentPane.add(JLabel("Hello, World!"))
        // on centre la fenêtre
        setLocationRelativeTo(null)
        // on rend la fenêtre visible
        isVisible = true
    }

    // créé la barre de menu en haut à gauche de la fenêtre
    private fun createMenuBar() {
        // menu bar
        val menubar = JMenuBar()
        val file = JMenu("File")

        file.mnemonic = KeyEvent.VK_F

        // file -> exit
        val icon = ImageIcon("src/main/resources/exit.png")
        val eMenuItem = JMenuItem("Exit", icon)
        eMenuItem.mnemonic = KeyEvent.VK_E
        eMenuItem.toolTipText = "Exit application"
        eMenuItem.addActionListener { _: ActionEvent -> System.exit(0) }
        file.add(eMenuItem)

        // ajout items au menu bar
        menubar.add(file)

        jMenuBar = menubar
    }
}