package nalouti.raoudha.monprojetevent;

public class UserData {
    private int idUser;
    private String password, urlimg, email, fullName;

    public UserData(String password, String urlimg, String email, String fullName) {

        this.password = password;
        this.urlimg = urlimg;
        this.email = email;
        this.fullName = fullName;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

