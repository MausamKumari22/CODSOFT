import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class QuizApplication {
    // Class to represent a question with its options and correct answer
    static class Question {
        String questionText;
        String[] options;
        int correctAnswerIndex;

        Question(String questionText, String[] options, int correctAnswerIndex) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        boolean isAnswerCorrect(int answerIndex) {
            return answerIndex == correctAnswerIndex;
        }
    }

    // Timer class to implement time-limited answers
    static class TimerTask implements Callable<Boolean> {
        private final Scanner scanner;

        TimerTask(Scanner scanner) {
            this.scanner = scanner;
        }

        @Override
        public Boolean call() {
            System.out.println("Time is up!");
            return false;
        }

        public Boolean runTimer(int timeLimit) {
            try {
                Thread.sleep(timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    // Main method to run the quiz
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a list of questions
        List<Question> questions = Arrays.asList(
                new Question("What is the capital of France?",
                        new String[] { "1. Berlin", "2. Madrid", "3. Paris", "4. Rome" }, 2),
                new Question("What is 5 + 3?", new String[] { "1. 6", "2. 7", "3. 8", "4. 9" }, 3),
                new Question("Which is the largest planet?",
                        new String[] { "1. Earth", "2. Mars", "3. Jupiter", "4. Saturn" }, 3),
                new Question("Who is the founder of Microsoft?",
                        new String[] { "1. Steve Jobs", "2. Bill Gates", "3. Mark Zuckerberg", "4. Elon Musk" }, 2),
                new Question("What is the boiling point of water?",
                        new String[] { "1. 90°C", "2. 95°C", "3. 100°C", "4. 105°C" }, 3),
                new Question("Which language is used for Android development?",
                        new String[] { "1. Java", "2. C++", "3. Python", "4. JavaScript" }, 1),
                new Question("What is the currency of Japan?",
                        new String[] { "1. Yen", "2. Dollar", "3. Pound", "4. Euro" }, 1),
                new Question("What is the square root of 64?", new String[] { "1. 6", "2. 7", "3. 8", "4. 9" }, 3),
                new Question("Who wrote 'To Kill a Mockingbird'?",
                        new String[] { "1. Harper Lee", "2. J.K. Rowling", "3. George Orwell", "4. Ernest Hemingway" },
                        1),
                new Question("What is the chemical symbol for water?",
                        new String[] { "1. H2O", "2. O2", "3. CO2", "4. NaCl" }, 1));

        int score = 0;
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Ask each question with timer
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + q.questionText);
            for (String option : q.options) {
                System.out.println(option);
            }

            // Start timer for the question
            TimerTask task = new TimerTask(scanner);
            Future<Boolean> result = executor.submit(() -> task.runTimer(500)); // 5 seconds

            // Wait for the answer or timeout
            try {
                if (result.get() != null) {
                    System.out.println("Select your answer (1-4): ");
                    int userAnswer = scanner.nextInt();

                    if (q.isAnswerCorrect(userAnswer)) {
                        System.out.println("Correct!");
                        score++;
                    } else {
                        System.out.println("Incorrect.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }

        // Display the result
        System.out.println("Quiz Finished!");
        System.out.println("Your Score: " + score + " out of " + questions.size());
    }
}
