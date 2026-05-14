package ru.mirea.korovkin.employeedb_4_5;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Superhero {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String heroName;

    public String realName;

    public int powerLevel;
}