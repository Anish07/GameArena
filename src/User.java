import java.util.ArrayList;
import java.util.HashMap;

public class User implements java.io.Serializable {
    private String name;
    private String email;
    private int secondsPlayed;
    private String username;
    private String status;
    private String password;
    private boolean online;
    private ArrayList<User> friendsList;
    private HashMap<String, HashMap<String, Integer>> stats;

    public User(String username, String email, String name, String password) //To be executed for user registration, involves essential data about a user
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.secondsPlayed = 0;
        this.status = "idle";
        this.stats  = new HashMap<>();
        this.friendsList = new ArrayList<User>();
    }

    public User(String username, String email, String name) {
        this(username, email, name, null);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public HashMap<String, HashMap<String, Integer>> getStats() { return stats; }

    public void setStats(HashMap<String, HashMap<String, Integer>> stats) { this.stats = stats; }

    public void setOnline()
    {
        this.online = true;
    }

    public void setOffline()
    {
        this.online = false;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    
    public void updateStat(String game, String stat) {
        // Increments `stat` {"Wins", "Losses", "Ties"} for `game`
        HashMap<String, Integer> gameStats = stats.get(game);
        if (gameStats != null && gameStats.get(stat) != null)
            gameStats.put(stat, gameStats.get(stat) + 1);
    }

    public void playGame() //skeleton placeholder to use for other things
    {
        setStatus("playing a game");
    }
    public void ExitGame()
    {
        setStatus("idle");
    }
    public void recordSecond()
    {
        try
        {
            while(this.status == "playing a game"){
                Thread.sleep(1000);
                this.secondsPlayed++; //Increment by one every second
            }
        } catch (InterruptedException e) {

        }
    }
    public int getMinutesPlayed()
    {
        return this.secondsPlayed / 60;
    }
    public int getHoursPlayed()
    {
        return getMinutesPlayed() / 60;
    }
    public void addFriend(User user)
    {
        this.friendsList.add(user);
    }
    public void getFriendsList()
    {
        for (User u : friendsList)
        {
            u.print();
        }
    }
    public String print()
    {
        return ("\n Name: " + name + " Username: " + username + " Status: " + status);
    }


}
