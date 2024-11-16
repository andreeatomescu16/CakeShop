package ui;

public class CakeUtils {
    public static int calculatePrice(String type, int weight) {
        return switch (type.toLowerCase()) {
            case "chocolate" -> 20 * weight;
            case "vanilla" -> 15 * weight;
            case "caramel" -> 35 * weight;
            default -> 0;
        };
    }
}
