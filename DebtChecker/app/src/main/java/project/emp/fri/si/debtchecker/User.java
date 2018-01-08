package project.emp.fri.si.debtchecker;

/**
 * Created by Jan on 29. 12. 2017.
 */

public class User {

    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String nickname;
    private String password;

    User(int id) {

        this.id = id;

        if (id != -1) {
            String[] attributes = DBInterface.queryUser(new String[]{"*"}, id).split(" ");

            this.name = attributes[0];
            this.surname = attributes[1];
            this.email = attributes[2];
            this.nickname = attributes[3];
            this.username = attributes[4];
            this.password = attributes[5];
        }
    }

    User(int id, String name, String surname, String username, String email, String nickname, String password) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
