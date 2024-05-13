package demo.fx_expenses;

import java.util.Date;

public class Task {

    String description;
    Date date_start;
    String impotance;



    public Task( String description, java.sql.Date date_start, String impotance) {

        this.description = description;
        this.date_start = date_start;
        this.impotance = impotance;
    }


    public String getDescription() {
        return description;
    }

    public Date getDate_start() {
        return date_start;
    }

    public String getImpotance() {
        return impotance;
    }
}
