package somantic;

import somantic.controller.Controller;
import somantic.processors.Interface;
import somantic.library.SomanticFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    
    public static final int MAX_WORDS_IN_REPOSITORY = 20_000;
    private static final String SOMANTIC_TITLE = "SOMANTIC :: first AFFECTIVE TRANSSPECIES TRANSLATOR by Michal Brzezinski";
    
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
        controller.setName(SOMANTIC_TITLE);
        controller.setTitle(SOMANTIC_TITLE);
        controller.setVisible(true);
    }
}
