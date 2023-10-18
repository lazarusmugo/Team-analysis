import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataEntry {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Printing Message
    System.out.println("Welcome to the FIFA WWC Data Entry Program!");

    // Adding two blank lines
    System.out.println();
    System.out.println();

    // Prompt for number of teams to enter data for
    System.out.print("How many teams data are you planning to enter? ");
    int numberOfTeams = readPositiveInt(scanner);

    // Creating an array to hold team information
    Team[] teams = new Team[numberOfTeams];
    int teamCount = 0;

    // Data entry using loop
    do {
      System.out.println("Enter the data for Team " + (teamCount + 1) + ":");
      System.out.print("Team Name: ");
      String teamName = readNonEmptyString(scanner);
      System.out.print("Team Code: ");
      String teamCode = readNonEmptyString(scanner);
      System.out.print("Goals Scored by the Team: ");
      int goalsScored = readNonNegativeInt(scanner);
      System.out.print("Goals Scored Against the Team: ");
      int goalsScoredAgainst = readNonNegativeInt(scanner);
      System.out.print("Group: ");
      String group = readValidGroup(scanner);

      //create a team object and store it in the array
      teams[teamCount] =
        new Team(teamName, teamCode, goalsScored, goalsScoredAgainst, group);
      teamCount++;
    } while (teamCount < numberOfTeams); //continue until (numberOfTeams)

    //display the entered team data to user
    System.out.println("\nThe current data looks like this:");
    for (int i = 0; i < teamCount; i++) {
      System.out.println(teams[i]);
    }

    //ask for csvfile name and write data to the given filename
    try {
      System.out.print("\nWhat would you like to name your CSV file? ");
      String fileName = scanner.nextLine();
      writeDataToCSV(teams, teamCount, fileName);
      System.out.println("Data saved to " + fileName + ".csv");
    } catch (IOException e) {
      System.err.println("Error writing to the CSV file: " + e.getMessage());
    }

    //display closing message to user
    System.out.println("Thank you and have a great day!");
    scanner.close();
  }

  //helper method to read a positive integer from the input
  private static int readPositiveInt(Scanner scanner) {
    int number;
    do {
      while (!scanner.hasNextInt()) {
        System.out.println("Please enter a valid number.");
        scanner.next();
      }
      number = scanner.nextInt();
      if (number <= 0) {
        System.out.println("Please enter a positive number.");
      }
    } while (number <= 0);
    scanner.nextLine();
    return number;
  }

  //helper method to read a non-empty string from the user
  private static String readNonEmptyString(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        System.out.println("Please enter a valid string.");
      }
    } while (input.isEmpty());
    return input;
  }

  //helper method to read a non-negative or zero integer from the user input
  private static int readNonNegativeInt(Scanner scanner) {
    int number;
    do {
      while (!scanner.hasNextInt()) {
        System.out.println("Please enter a valid number.");
        scanner.next();
      }
      number = scanner.nextInt();
      if (number < 0) {
        System.out.println("Please enter a non-negative number.");
      }
    } while (number < 0);
    scanner.nextLine();
    return number;
  }

  //Helper method to get a valid group (A, B, C, or D)only from the user input
  private static String readValidGroup(Scanner scanner) {
    String group;
    do {
      group = scanner.nextLine().toUpperCase();
      if (!group.matches("[ABCD]")) {
        System.out.println("Please enter a valid group (A, B, C, D).");
      }
    } while (!group.matches("[ABCD]"));
    return group;
  }

  //helper method to write data to a csvfile
  private static void writeDataToCSV(
    Team[] teams,
    int teamCount,
    String fileName
  ) throws IOException {
    FileWriter writer = new FileWriter(fileName + ".csv");
    writer.write(
      "Team Name,Team Code,Goals Scored,Goals Scored Against,Group\n"
    );

    for (int i = 0; i < teamCount; i++) {
      writer.write(
        String.format(
          "%s,%s,%d,%d,%s\n",
          teams[i].getTeamName(),
          teams[i].getTeamCode(),
          teams[i].getGoalsScored(),
          teams[i].getGoalsScoredAgainst(),
          teams[i].getGroup()
        )
      );
    }

    writer.close();
  }
}

//team class to represent team data
class Team {

  private String teamName;
  private String teamCode;
  private int goalsScored;
  private int goalsScoredAgainst;
  private String group;

  public Team(
    String teamName,
    String teamCode,
    int goalsScored,
    int goalsScoredAgainst,
    String group
  ) {
    this.teamName = teamName;
    this.teamCode = teamCode;
    this.goalsScored = goalsScored;
    this.goalsScoredAgainst = goalsScoredAgainst;
    this.group = group;
  }

  //getter methods for accessing team data
  public String getTeamName() {
    return teamName;
  }

  public String getTeamCode() {
    return teamCode;
  }

  public int getGoalsScored() {
    return goalsScored;
  }

  public int getGoalsScoredAgainst() {
    return goalsScoredAgainst;
  }

  public String getGroup() {
    return group;
  }

  //override toString method for displaying team data
  @Override
  public String toString() {
    return (
      "Team Name: " +
      teamName +
      ", Team Code: " +
      teamCode +
      ", Goals Scored: " +
      goalsScored +
      ", Goals Scored Against: " +
      goalsScoredAgainst +
      ", Group: " +
      group
    );
  }
}
