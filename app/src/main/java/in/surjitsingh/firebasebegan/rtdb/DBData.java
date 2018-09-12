package in.surjitsingh.firebasebegan.rtdb;

public class DBData {
    String id, name, email, mess;

    public DBData(){}

    public DBData(String id, String name, String email, String mess) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mess = mess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
