package controller;

public record Location(int x, int y) {
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
