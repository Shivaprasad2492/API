package blog.common;

public class CurrentUser {

    private static CurrentUser currentUser = new CurrentUser();
    private String userName;

    private CurrentUser() { }

    public static CurrentUser getInstance(){
        return currentUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
