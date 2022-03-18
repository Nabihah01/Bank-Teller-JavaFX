package com.example.bank_teller;

/**
 Represents a Profile with three fields: the account holder's first name, last name, and
 date of birth
 @author Nabihah, Maryam
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     Creates an instance of Profile with given String
     @param profile A string with patient in "firstName lastName mm/dd/yyyy" format
     */
    public Profile(String profile) {
        String [] split = profile.split(" ");
        this.fname = split[0];
        this.lname = split[1];
        this.dob = new Date(split[2]);
    }

    /**
     Overrides toString method.
     @return Patient as a string in "firstName lastName hh/mm/yyy" format
     */
    @Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

    /**
     Overrides equals method.
     @param obj an object
     @return true if both objects of type profile are equal. false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return this.fname.equalsIgnoreCase(profile.fname) && this.lname
                    .equalsIgnoreCase(profile.lname)
            && this.dob.equals(profile.dob);
        }
        return false;
    }
}
