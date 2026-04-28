public enum Grade {
    O("O", "Outstanding", 10, 90, 100),
    A_PLUS("A+", "Excellent", 9, 80, 89),
    A("A", "Very Good", 8, 70, 79),
    B_PLUS("B+", "Good", 7, 60, 69),
    B("B", "Above Average", 6, 50, 59),
    C("C", "Average", 5, 40, 49),
    F("F", "Fail", 0, 0, 39),
    AB("AB", "Absent", 0, -1, -1);

    private final String code;
    private final String description;
    private final int points;
    private final int minScore;
    private final int maxScore;

    Grade(String code, String description, int points, int minScore, int maxScore) {
        this.code = code;
        this.description = description;
        this.points = points;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public static Grade fromScore(int score) {
        if (score == -1) {
            return AB;
        }
        for (Grade grade : values()) {
            if (score >= grade.minScore && score <= grade.maxScore) {
                return grade;
            }
        }
        return F;
    }

    public boolean isFail() {
        return this == F || this == AB;
    }
}
