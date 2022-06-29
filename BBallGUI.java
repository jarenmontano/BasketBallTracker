/*
 * Author: Jaren Montano
 * Date: 4/4/2022
 * Notes: the basic gui for the bball panel
 *
 *
 * Recent change:
 */

import javax.swing.*;
import java.awt.*;

public class BBallGUI {

    public static void main(String[] args){
        JFrame GUI = new JFrame("My BBall Stats Program");
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setSize(1200,450);
        Container pane = GUI.getContentPane();

        JMenuBar menubar = new JMenuBar();
        GUI.setJMenuBar(menubar);

        pane.add(new BBallPanel(menubar));

        // that menu bar:)



        GUI.setResizable(false);
        GUI.setVisible(true);
    }


}
