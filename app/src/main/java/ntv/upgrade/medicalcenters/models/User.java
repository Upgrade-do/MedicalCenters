package ntv.upgrade.medicalcenters.models;

/**
 * Created by xeros on 3/27/2016.
 */
public class User {

    private String
            UID,
            SSN,
            userName,
            userBirthDate,
            userBloodType,
            ASRName,
            ARSAssociateID,
            ARSClient,
            ARSPlan;

    private double
            ARSID;

    public User() {
    }

    public User(String UID, String SSN, String userName, String userBirthDate,
                String userBloodType, String ASRName, String ARSAssociateID,
                String ARSClient, String ARSPlan, double ARSID) {
        this.UID = UID;
        this.userName = userName;
        this.userBirthDate = userBirthDate;
        this.userBloodType = userBloodType;
        this.ASRName = ASRName;
        this.ARSAssociateID = ARSAssociateID;
        this.ARSClient = ARSClient;
        this.ARSPlan = ARSPlan;
        this.SSN = SSN;
        this.ARSID = ARSID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserBloodType() {
        return userBloodType;
    }

    public void setUserBloodType(String userBloodType) {
        this.userBloodType = userBloodType;
    }

    public String getASRName() {
        return ASRName;
    }

    public void setASRName(String ASRName) {
        this.ASRName = ASRName;
    }

    public String getARSAssociateID() {
        return ARSAssociateID;
    }

    public void setARSAssociateID(String ARSAssociateID) {
        this.ARSAssociateID = ARSAssociateID;
    }

    public String getARSClient() {
        return ARSClient;
    }

    public void setARSClient(String ARSClient) {
        this.ARSClient = ARSClient;
    }

    public String getARSPlan() {
        return ARSPlan;
    }

    public void setARSPlan(String ARSPlan) {
        this.ARSPlan = ARSPlan;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public double getARSID() {
        return ARSID;
    }

    public void setARSID(double ARSID) {
        this.ARSID = ARSID;
    }
}