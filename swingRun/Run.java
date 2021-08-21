package swingRun;

public class Run {
    int year;
    int month;
    int day;
    int time;
    String pace;
    float mileage;

    public Run(int year, int month, int day, int time, String pace) {
        this.year= year;
        this.month= month;
        this.day= day;
        this.time= time;
        this.pace= pace;
        int minutes= Integer.parseInt(pace.substring(0, 2));
        float remain= Float.parseFloat(pace.substring(3)) / 60;
        float total= minutes + remain;

        float mile= time / total;
        float next= mile / 60;
        mileage= next;

    }

    public Run(int year, int month, int day, int time, float mileage) {
        this.year= year;
        this.month= month;
        this.day= day;
        this.time= time;
        this.mileage= mileage;

        float ratio1= time / mileage;
        float ratio= ratio1 / 60;
        int big= (int) (ratio - ratio % 1);
        float dec= ratio % 1;
        dec= dec * 100;
        dec= (float) (dec * 0.6);
        int dec1= (int) dec;

        String p1;
        String p2;
        String mid= ":";
        if (big < 10) {
            p1= "0" + String.valueOf(big);
        } else {
            p1= String.valueOf(big);
        }
        if (dec1 < 10) {
            p2= "0" + String.valueOf(dec1);
        } else {
            p2= String.valueOf(dec1);
        }
        pace= p1 + mid + p2;
    }
}
