/*
 * Author: Jaren Montano
 * Date: 3/28/22
 * Notes: basic player class
 * get n setters and a constructor
 *
 *
 * Recent change:
 */
public class Player {
    private String fName;
    private String lName;
    private double shootPct;
    private int rebounds;
    private int assists;
    private int turnovers;
    private String conference;
    private int position;
    private boolean isStarter;

    public Player(){

    }
    public Player(String f, String l, double s){
        fName = f;
        lName = l;
        shootPct = s;
    }
    public Player(String f, String l, double s, int r, int a, int t){
        fName = f;
        lName = l;
        shootPct = s;
        rebounds = r;
        assists = a;
        turnovers = t;

    }
    public Player(String f, String l, double s, int r, int a, int t, String c, int p, boolean starter){
        fName = f;
        lName = l;
        shootPct = s;
        rebounds = r;
        assists = a;
        turnovers = t;
        conference = c;
        position = p;
        isStarter = starter;

    }


    public int getAssists() {
        return assists;
    }

    public int getRebounds() {
        return rebounds;
    }

    public double getShootPct() {
        return shootPct;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getConference() {
        return conference;
    }

    public int getPosition() {
        return position;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }

    public void setShootPct(double shootPct) {
        this.shootPct = shootPct;
    }

    public void setTurnovers(int turnovers) {
        this.turnovers = turnovers;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStarter(boolean starter) {
        isStarter = starter;
    }

    @Override
    public String toString() {
        return "Player [" +
                "fName='" + fName  +
                ", lName='" + lName  +
                ", shootPct=" + shootPct +
                ", rebounds=" + rebounds +
                ", assists=" + assists +
                ", turnovers=" + turnovers +
                ", conference='" + conference  +
                ", position=" + position +
                ", isStarter=" + isStarter +
                ']';
    }
}
