/**
 * A class that lets users log in
 *
 * Purdue University -- CS18000 -- Summer 2024 -- Group Project
 *
 * @author Utkarsh Bali
 * @version July 8, 2024
 */

public class Login {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile loggedIn() {
        // If authentication successful, create Profile for the user
        // return new Profile(this.username);
    }
}
