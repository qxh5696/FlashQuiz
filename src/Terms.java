/**
 * Created by Qadir on 8/30/2015.
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class Terms {

    private String term;
    private String meaning;

    public Terms(String term, String meaning){
        this.term = term;
        this.meaning = meaning;
    }

    public String getTerm(){
        return term;
    }

    public String getMeaning(){
        return meaning;
    }
}
