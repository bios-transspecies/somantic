package MainProgram;

public class Algorytm {

    private float wejscieHistFloat = 0;

    float licz(float in) {
        wejscieHistFloat = (in + wejscieHistFloat * 3) / 4;
        return wejscieHistFloat;
    }
}
