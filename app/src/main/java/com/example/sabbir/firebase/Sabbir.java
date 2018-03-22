package com.example.sabbir.firebase;

/**
 * Created by sabbir on 3/21/2018.
 */

public class Sabbir {

    String sabbirid;
    String sabbirName;
    String sabbirGame;
    String id;

    public Sabbir(String id, String user, String game)
    {

        this.sabbirGame = game ;

        this.id = id;

        this.sabbirid = user;

    }

    public Sabbir()
    {


    }
/*
    public Sabbir(String sabbirid, String sabbirName, String sabbirGame, String id) {

        this.sabbirid = sabbirid;
        this.sabbirName = sabbirName;
        this.sabbirGame = sabbirGame;
        this.id = id;

    }*/

    public String getid() {
        return id;
    }

    public String getSabbirid() {
        return sabbirid;
    }

    public String getSabbirName() {
        return sabbirName;
    }

    public String getSabbirGame() {
        return sabbirGame;
    }
}
