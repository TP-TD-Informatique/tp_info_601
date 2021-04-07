import java.awt.BorderLayout
import java.awt.Color
import javax.swing.*
import java.awt.FlowLayout
import javax.swing.JSeparator


class MyView : JFrame("Knowledge Graph") {
    init {
        createUI("Knowledge Graph")
    }

    // créé la GUI
    private fun createUI(title: String) {

        setTitle(title)

        // creation du panel du dessus (un container)
        var topPanel = JPanel();
        topPanel.layout = FlowLayout()
        topPanel.setBounds(40,80,200,200)
        topPanel.setBackground(Color.gray)

        // input avec les commandes
        var input = JTextField("Insérez vos commandes ici.");
        input.setBounds(50,100, 400,30);
        topPanel.add(input)

        // bouton pour valider
        var button = JButton()
        button.setText("Envoyer")
        topPanel.add(button)

        // separator
        var sep = JSeparator()
        topPanel.add(sep)

        // creation du panel du centre (un container)
        var centerPanel = JPanel();
        centerPanel.layout = BorderLayout()

        // ajout des noeuds
        var node1 = MyNode(20, 20, 100)
        centerPanel.add(node1)

        // la fenêtre doit se fermer quand on clique sur la croix
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        // ?
        setDefaultLookAndFeelDecorated(true)
        // taille de la fenêtre
        setSize(1000, 800)
        // on ajoute le panel
        add(topPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)
        // on centre la fenêtre
        setLocationRelativeTo(null)
        // on rend la fenêtre visible
        isVisible = true
    }
}