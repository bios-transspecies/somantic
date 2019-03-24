package somantic.processors;

public class Algorytm {

    private float wejscieHistFloat = 0;

    public float licz(float in) {
        return wejscieHistFloat = (in + wejscieHistFloat * 3) / 4;
    }
}
