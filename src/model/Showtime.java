package model;

public class Showtime {
    private int startTime;
    private boolean cc;
    private int aId;

    public Showtime(int startTime, boolean cc, int aId) {
        this.startTime = startTime;
        this.cc = cc;
        this.aId = aId;
    }

    public int getStartTime() {
        return startTime;
    }

    public boolean isCc() {
        return cc;
    }

    public int getaId() {
        return aId;
    }
}
