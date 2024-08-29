package main.java.object;

public class Door extends SuperObject {
    public Door(int width, int height) {
        this.name = "Door";
        setupImage(this.name, width, height);
        this.solid = true;
    }
}