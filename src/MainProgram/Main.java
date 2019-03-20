package MainProgram;

import somantic.controller.Controller;
import MainProgram.Interface;
import WNprocess.SomanticFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    
    public static final int MAX_WORDS_IN_REPOSITORY = 20_000;
    
    public static void main(String[] args) {
        try {
            SomanticFacade somanticFactory = SomanticFacade.getInstance();
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
