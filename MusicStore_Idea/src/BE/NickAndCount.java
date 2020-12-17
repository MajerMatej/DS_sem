package BE;

public class NickAndCount {
    private String nick;
    private int count;

    public NickAndCount(String nick, int count) {
        this.nick = nick;
        this.count = count;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
