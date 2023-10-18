import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataRetrieval {

    public static void main(String[] args) {
        System.out.println("\n  Welcome to the FIFA WWC Analysis Program\n");

        //get user input
        Scanner scanner = new Scanner(System.in);
        String filePath;

        //ask for file name if wrong ask again
        do {
            System.out.print("Please enter the file name (without extension .csv): ");
            String fileName = scanner.nextLine();
            filePath = fileName + ".csv";

            //check if the file exists or not
            if (!fileExists(filePath)) {
                System.out.println("'" + filePath + "' not found. Please enter a valid file name.\n");
            }
        } while (!fileExists(filePath));

        System.out.println("\n File found. \n");

        //read data and store data in an array
        TeamData[] dataArray = readCSVFile(filePath);

        if (dataArray.length == 0) {
            System.out.println("No data found in the CSV file. Exiting the program.");
            scanner.close();
            return;
        }

        String analysisType;
        do {
            System.out.print("\n Data Analysis System:\n" +
                    "1. Overall Analysis\n" +
                    "2. Group Analysis\n" +
                    "3. Exit\n" +
                    "Enter your choice (1, 2, or 3): ");

            analysisType = scanner.nextLine().trim();

            if ("1".equals(analysisType)) {
                performOverallAnalysis(dataArray);
            } else if ("2".equals(analysisType)) {
                performGroupAnalysis(dataArray, scanner);
            } else if ("3".equals(analysisType)) {
                System.out.println("\nExiting the program. Goodbye!\n");
                break;
            } else {
                System.out.println("\nInvalid input. Please choose the correct option.");
            }
        } while (true);
        scanner.close();
    }

    //check if a file exist or not
    private static boolean fileExists(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            fileReader.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //read data and store data in an array
    private static TeamData[] readCSVFile(String filePath) {
        List<TeamData> dataList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            //read the header first line of the csvfile
            String headerLine = br.readLine();

            if (headerLine == null || headerLine.trim().isEmpty()) {
                System.out.println("The CSV file is empty. Please check the file.");
                br.close();
                return new TeamData[0]; 
                //return an empty array
            }

            //read each line of data from the csvfile
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 5) {
                    System.out.println("Invalid formatting in line: " + line);
                    continue;
                }

                String teamName = data[0].trim();
                String teamCode = data[1].trim();
                int goalsFor = Integer.parseInt(data[2].trim());
                int goalsAgainst = Integer.parseInt(data[3].trim());
                String group = data[4].trim();
                int netGoals = goalsFor - goalsAgainst;

                //create a TeamData object and add it to the list
                TeamData team = new TeamData(teamName, teamCode, goalsFor, goalsAgainst, group, netGoals);
                dataList.add(team);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //convert the list to an array
        return dataList.toArray(new TeamData[0]);
    }

    //does analysis and display it
    private static void performOverallAnalysis(TeamData[] dataArray) {
        //sort the data by netgoals in descending order
        Arrays.sort(dataArray, Comparator.comparingInt(TeamData::getNetGoals).reversed());

        System.out.println("\nTeams sorted by Net Goals (Descending Order):\n");
        for (TeamData team : dataArray) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", NetGoals: " + team.getNetGoals());
        }

        //sort the data by number of goal against in descending order
        Arrays.sort(dataArray, Comparator.comparingInt(TeamData::getGoalsAgainst).reversed());

        System.out.println("\nTeams sorted by Goals Against (Descending Order):\n");
        for (TeamData team : dataArray) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", Goals Against: " + team.getGoalsAgainst());
        }

        //sort the data by  number of goal for in descending order
        Arrays.sort(dataArray, Comparator.comparingInt(TeamData::getGoalsFor).reversed());

        System.out.println("\nTeams sorted by Goals For (Descending Order):\n");
        for (TeamData team : dataArray) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", Goals For: " + team.getGoalsFor());
        }

        //find and display the best team (with highest netgoals)
        TeamData bestPerformingTeam = dataArray[0];
        System.out.println("\nBest Performing Team (Highest Net Goals):\n");
        System.out.println("Team Name: " + bestPerformingTeam.getTeamName() +
                ", Team Code: " + bestPerformingTeam.getTeamCode() +
                ", Goals For: " + bestPerformingTeam.getGoalsFor() +
                ", Goals Against: " + bestPerformingTeam.getGoalsAgainst() +
                ", Group: " + bestPerformingTeam.getGroup());
    }

    //perform group analysis on the data
    private static void performGroupAnalysis(TeamData[] dataArray, Scanner scanner) {
        System.out.print("\n Enter the group (A, B, C, D): ");
        String groupChoice = scanner.nextLine().trim().toUpperCase();

        List<TeamData> groupTeams = new ArrayList<>();
        for (TeamData team : dataArray) {
            if (team.getGroup().equals(groupChoice)) {
                groupTeams.add(team);
            }
        }

        if (groupTeams.isEmpty()) {
            System.out.println("\n  No teams found in group " + groupChoice);
            return;
        }

        //sort the group teams by net  number of goal in descending order
        groupTeams.sort(Comparator.comparingInt(TeamData::getNetGoals).reversed());

        System.out.println("\nTeams in Group " + groupChoice + " sorted by Net Goals (Descending Order):\n");
        for (TeamData team : groupTeams) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", Net Goals: " + team.getNetGoals());
        }

        //sort the group teams by  number of goal against in descending order
        groupTeams.sort(Comparator.comparingInt(TeamData::getGoalsAgainst).reversed());

        System.out.println("\nTeams in Group " + groupChoice + " sorted by Goals Against (Descending Order):\n");
        for (TeamData team : groupTeams) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", Goals Against: " + team.getGoalsAgainst());
        }

        //sort the group teams by number of goal for in descending order
        groupTeams.sort(Comparator.comparingInt(TeamData::getGoalsFor).reversed());

        System.out.println("\nTeams in Group " + groupChoice + " sorted by Goals For (Descending Order):\n");
        for (TeamData team : groupTeams) {
            System.out.println("Team Name: " + team.getTeamName() +
                    ", Team Code: " + team.getTeamCode() +
                    ", Group: " + team.getGroup() +
                    ", Goals For: " + team.getGoalsFor());
        }

        //find and display the best team in the group (with the biggest number of netgoal)
        TeamData bestPerformingTeamInGroup = groupTeams.get(0);
        System.out.println("\nBest Performing Team in Group " + groupChoice + " (Highest Net Goals):\n");
        System.out.println("Team Name: " + bestPerformingTeamInGroup.getTeamName() +
                ", Team Code: " + bestPerformingTeamInGroup.getTeamCode() +
                ", Goals For: " + bestPerformingTeamInGroup.getGoalsFor() +
                ", Goals Against: " + bestPerformingTeamInGroup.getGoalsAgainst() +
                ", Group: " + bestPerformingTeamInGroup.getGroup());
    }
}

//class to represent team data
class TeamData {
    private String teamName;
    private String teamCode;
    private int goalsFor;
    private int goalsAgainst;
    private String group;
    private int netGoals;

    //constructor that initializes TeamData objects
    public TeamData(String teamName, String teamCode, int goalsFor, int goalsAgainst, String group, int netGoals) {
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.group = group;
        this.netGoals = netGoals;
    }

    //getter methods to retrieve object attribute
    public String getTeamName() {
        return teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public String getGroup() {
        return group;
    }

    public int getNetGoals() {
        return netGoals;
    }
}
