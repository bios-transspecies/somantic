package MainProgram;

import RiTa.RiTaRepo;
import guru.ttslib.TTS;

class TTSimpl {

    private static final TTS tts = new TTS();
    private static RiTaRepo repo;
    
    static void start(String word) {
        System.err.println(word);
        if(word != null && word.length()>0){
            new Thread(() -> {
               // repo.get(word).print();
                Interface.setIsSpeaking(true);
                tts.setPitch(Float.MIN_VALUE);
                tts.speak(word);
                Interface.setIsSpeaking(false);
            }).start();
        }
    }

    static void setRitaRepo(RiTaRepo repo) {
       TTSimpl.repo = repo;
    }
}
