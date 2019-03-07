package MainProgram;


import somantic.controller.Controller;
import MainProgram.Interface;
import WNprocess.SomanticFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        try {
            SomanticFactory somanticFactory = new SomanticFactory();
            somanticFactory.loadRepo();
            Interface.setSomanticFactory(somanticFactory);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Controller controller = new Controller();
        controller.setVisible(true);
    }
}
