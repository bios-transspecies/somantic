package MainProgram;

public class Algorytm {

    private float wejscieHistFloat = 0;

    float licz(float in) {
        wejscieHistFloat = (in + wejscieHistFloat) / 2;
        return wejscieHistFloat;
    }
}
