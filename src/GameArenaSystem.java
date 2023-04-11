import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameArenaSystem implements java.io.Serializable {

    private ArrayList<User> users;
    private String[] games = {"Chess", "Checkers", "Death", "BlackJack", "TournamentLobby", "TicTacToe"};


    public GameArenaSystem() {
        users = new ArrayList<User>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public String[] getGames() { return games; }

    public User getUser(String username) {
        for (User u: users) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    public boolean usernameExists(String username) {
        return getUser(username) != null;
    }

    public boolean emailExists(String email) {
        for (User u: users) {
            if (u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public boolean accountExists(String username, String email) {
        for (User u: users) {
            if (u.getEmail().equals(email) && u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public User login(String username, String password) {
        for (User u: users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    public boolean registerUser(User newUser) {
        // Add a new user to the GameArenaSystem
        for (User u: users) {
            // Ensure no account with the same email or username has already been added
            if (u.getEmail().equals(newUser.getEmail()) || u.getUsername().equals(newUser.getUsername()))
                return false;
        }
        users.add(newUser);
        initGameStats(newUser);
        save();

        System.out.println("Registered " + newUser.getUsername());
        return true;
    }

    public void initGameStats(User u) {
        // Initialize the user's game stats hashmap with the system's current games
        HashMap<String, HashMap<String, Integer>> stats = new HashMap<>();

        for (String game: games) {
            stats.put(game, new HashMap<String, Integer>());
            stats.get(game).put("Wins", 0);
            stats.get(game).put("Losses", 0);
            stats.get(game).put("Ties", 0);
        }
        u.setStats(stats);
    }

    public void save() {
        save("system.dat");
    }

    public void save(String filename) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameArenaSystem load() {
        GameArenaSystem system;
        ObjectInputStream in;

        try {
            in = new ObjectInputStream(new FileInputStream("system.dat"));
            system = (GameArenaSystem) in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException ex) {
            // If it fails to load, likely due to changed class definition
            // Recreate with demo users and make new save file
            ex.printStackTrace();
            system = new GameArenaSystem();
            system.registerDemoUsers();
            system.save();
        }

        return system;
    }

    public void registerDemoUsers() {
        registerUser(new User("BobLovesGames","bob.dole@games.com", "Bob Dole", "blah"));
        registerUser(new User("BellDoesntLikeGamesAtAll","bell.pepper@nogames.com", "Bell Pepper", "BELL"));
        registerUser(new User("jesseCool","jesse.chambers@gmail.com", "Jesse Chambers", "joejoe"));
        registerUser(new User("Jabba123","jabba.hut@gmail.com", "Jabba The Hut", "Jabba123"));
        registerUser(new User("Luke123","luke.sky@starwars.com", "Luke Skywalker", "lukey123"));
        registerUser(new User("fungi123","fungus.mushroom@gamer.com", "Fungi Mushroom", "fungi123"));
        registerUser(new User("dark123","dark.vader@starwars.com", "Dark Vader", "blah123"));
    }
}
