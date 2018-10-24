package com.tictactoe.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

@DatabaseTable(tableName = "dat_player")
public class Player implements Serializable, Parcelable {

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DRAWS = "draws";
    public static final String WINS = "wins";


    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(canBeNull = false, unique = true, columnName = NAME)
    private String name;

    @DatabaseField(canBeNull = false, columnName = WINS)
    private int wins;

    @DatabaseField(canBeNull = false, columnName = DRAWS)
    private int draws;

    public Player(){
    }

    public Player(String player){
        this.id = -1;
        this.name = player;
        this.wins = 0;
        this.draws = 0;
    }

    protected Player(Parcel in) {
        id = in.readInt();
        name = in.readString();
        wins = in.readInt();
        draws = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(wins);
        dest.writeInt(draws);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void addWins() {
        this.wins++;
    }

    public void addDraws() {
        this.draws++;
    }
}
