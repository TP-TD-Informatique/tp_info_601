package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyView extends JFrame {
    public static void main(String[] args) {
        //Fenêtre dont le titre est entre parenthèses
        JFrame frame = new JFrame("Hello World");
        //La fenetre se ferme en cliquant sur la croix
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //On ajoute du texte dans la fenêtre
        frame.getContentPane().add(new JLabel("Mon texte dans fenêtre"));
        //Taille minimale fenêtre
        frame.pack();
        //On centre la fenêtre
        frame.setLocationRelativeTo(null);
        //On rend la fenêtre visible
        frame.setVisible(true);
    }
}package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyView extends JFrame {
    public static void main(String[] args) {
        //Fenêtre dont le titre est entre parenthèses
        JFrame frame = new JFrame("Hello World");
        //La fenetre se ferme en cliquant sur la croix
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //On ajoute du texte dans la fenêtre
        frame.getContentPane().add(new JLabel("Mon texte dans fenêtre"));
        //Taille minimale fenêtre
        frame.pack();
        //On centre la fenêtre
        frame.setLocationRelativeTo(null);
        //On rend la fenêtre visible
        frame.setVisible(true);
    }
}