package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

public class UpdateUserAndImage {
    private String firstName , middleName , lastName , profileId , profilePic;

    public UpdateUserAndImage() {
    }

    public UpdateUserAndImage(String firstName, String middleName, String lastName, String profileId, String profilePic) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.profileId = profileId;
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
