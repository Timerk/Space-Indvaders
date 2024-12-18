public class Score {
    
    int score;
    String name;
    String date;
    String difficulty;

    public Score(int score, String name, String date, int difficultyInt) {
        this.score = score;
        this.name = name;
        this.date = date;
        
        switch (difficultyInt) {
            case 3:
                this.difficulty = "Leicht";
                break;
            case 4:
                this.difficulty = "Mittel";
                break;
            case 6:
                this.difficulty = "Schwer";
                break;
            default:
                break;
        }
    }
}
