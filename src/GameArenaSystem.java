import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

public class GameArenaSystem implements java.io.Serializable {

    private ArrayList<User> users;
    private String[] games = {"Chess", "Checkers", "Death", "BlackJack", "TournamentLobby", "TicTacToe"};

    public GameArenaSystem() {
        users = new ArrayList<User>();
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String my_name = resultSet.getString("name");
                String user_username = resultSet.getString("username");
                String user_password = resultSet.getString("password");
                String user_email = resultSet.getString("email");
              User user = new User(user_username,user_email,my_name,user_password);
                user.setId(resultSet.getInt("id"));

                userList.add(user);
            }
        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
        return userList;
    }

    public String[] getGames() { return games; }
    public void removeFriendById(int friendId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            System.out.println("HERE");
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "DELETE FROM friends WHERE friend_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, friendId);


            statement.executeUpdate();

            System.out.println("SUCCESS");
        } catch (SQLException e) {

            System.out.println("Error here now");
            // handle the exception here
        } finally {
            // close the resources
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
    }

    public void updatePassword(String username, String email, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "UPDATE users SET password = ? WHERE username = ? AND email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.setString(3, email);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Unable to update password. User not found.");
            }
        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
    }


    public User getUser(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String my_name = resultSet.getString("name");
                String user_username = resultSet.getString("username");
                String user_password = resultSet.getString("password");
                String user_email = resultSet.getString("email");
                user = new User(user_username,user_email,my_name,user_password);
                user.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
        return user;
    }

    public ArrayList<User> getFriendsForUser(int userId) {
        ArrayList<User> friends = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT u.*\n" +
                    "FROM users u\n" +
                    "JOIN friends f ON u.id = f.friend_id\n" +
                    "WHERE f.user_id = ?\n" +
                    "UNION\n" +
                    "SELECT u.*\n" +
                    "FROM users u\n" +
                    "JOIN friends f ON u.id = f.user_id\n" +
                    "WHERE f.friend_id = ?\n";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String my_name = resultSet.getString("name");
                String user_username = resultSet.getString("username");
                String user_password = resultSet.getString("password");
                String user_email = resultSet.getString("email");
                User friend = new User(user_username,user_email,my_name,user_password);
                friend.setId(id);
                // add the friend to the list
                friends.add(friend);
            }
        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
        return friends;
    }

    public void addFriend(int userId, int friendId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            System.out.println(userId + " " + friendId);
            System.out.println("HERE WE GOO");
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, friendId);
            statement.executeUpdate();
        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
    }


    public boolean usernameExists(String username) {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            // handle the exception here
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }

        return exists;
    }


    public boolean emailExists(String email) {
        boolean emailExists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                emailExists = true;
            }

        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
        return false;
    }


    public boolean accountExists(String username,String email) {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users WHERE username = ? OR email = ?";


            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            // handle the exception here
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }

        return exists;
    }

    public void addUserToDB(User user) {

        Connection connection = null;
        try
        {
            System.out.println(user.getName());
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "INSERT INTO users (username, password,email,name) VALUES (?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.executeUpdate();







        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }


    }
    public User login(String username, String password) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String my_name = resultSet.getString("name");
                String user_username = resultSet.getString("username");
                String user_password = resultSet.getString("password");
                String user_email = resultSet.getString("email");
                user = new User(user_username,user_email,my_name,user_password);
                user.setId(id);

            }

        } catch (SQLException e) {
            // handle the exception here
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle the exception here
                }
            }
        }
        return user;
    }



    public boolean registerUser(User newUser) {

    //Added database
        addUserToDB(newUser);
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
        /*
        registerUser(new User("BobLovesGames","bob.dole@games.com", "Bob Dole", "blah"));
        registerUser(new User("BellDoesntLikeGamesAtAll","bell.pepper@nogames.com", "Bell Pepper", "BELL"));
        registerUser(new User("jesseCool","jesse.chambers@gmail.com", "Jesse Chambers", "joejoe"));
        registerUser(new User("Jabba123","jabba.hut@gmail.com", "Jabba The Hut", "Jabba123"));
        registerUser(new User("Luke123","luke.sky@starwars.com", "Luke Skywalker", "lukey123"));
        registerUser(new User("fungi123","fungus.mushroom@gamer.com", "Fungi Mushroom", "fungi123"));
        registerUser(new User("dark123","dark.vader@starwars.com", "Dark Vader", "blah123"));


         */
    }
}
