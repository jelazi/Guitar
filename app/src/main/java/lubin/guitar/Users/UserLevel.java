package lubin.guitar.Users;

public enum UserLevel {
    BEGINNER (1),
    EXPERT (2),
    PROFESSIONAL (3),
    GENIUS (4),
    CHAMPION (5);

    private int value;

    private UserLevel(int value) {
        this.value = value;
    }

    public int getValue () {
        return value;
    }
}
