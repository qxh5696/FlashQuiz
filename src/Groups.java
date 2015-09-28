import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Qadir on 8/30/2015.
 */
public class Groups {

    private String name;
    private boolean subGroups = false;
    private ArrayList<Groups> subGroupList; //= new ArrayList<Groups>();
    private ArrayList<Terms> termsList = new ArrayList<Terms>();

    public Groups(String name, boolean subGroups){
        this.name = name;
        this.subGroups = subGroups;
        if(this.subGroups == true){
            subGroupList = new ArrayList<Groups>();
        }
    }

    public void addSubGroup(Groups subGroup){
        subGroupList.add(subGroup);
    }

    public void addTerms(Terms t){
        termsList.add(t);
    }

    public boolean getSubGroupsBool(){
        return this.subGroups;
    }

    public void setSubGroupsBool(Boolean bool){
        this.subGroups = bool;
    }

    public ArrayList<Groups> getSubGroupList(){
        return this.subGroupList;
    }

    public ArrayList<Terms> getTermsList(){
        return this.termsList;
    }


    /*
    public void prompt(){
        System.out.println("Enter a subject to study for.");
        //String grouping = new Scanner(System.in).nextLine();
        //if(grouping.equalsIgnoreCase("yes")){
            //System.out.println("Enter the group name for the cards");
            String groupName = new Scanner(System.in).nextLine();
            System.out.println("Would you like to make a sub-section for the subject?");
            String subGrouping = new Scanner(System.in).nextLine();
            if(subGrouping.equalsIgnoreCase("yes")){
                Groups group = new Groups(groupName, true);
                while(subGrouping.equalsIgnoreCase("yes")){
                    System.out.println("Enter the sub-subject name for the cards.");
                    String subGroupName = new Scanner(System.in).nextLine();
                    Groups subGroup = new Groups(subGroupName, false);
                    System.out.println("Start entering cards, type \"finished\" when you are done.");
                    String input = " ";
                    while (!input.equalsIgnoreCase("finished")){
                        System.out.println("Enter a term.");
                        input = new Scanner(System.in).nextLine();
                        if(input.equalsIgnoreCase("finished")){
                            break;
                        }
                        String term = new String(input);
                        System.out.println("Enter a definition.");
                        String definition = new Scanner(System.in).nextLine();
                        subGroup.addTerms(new Terms(term, definition));
                    }
                    group.addSubGroup(subGroup);
                    System.out.println("Would you like to enter another subgroup?");
                    subGrouping = new Scanner(System.in).nextLine();
                }
            }
            else{
                Groups group = new Groups(groupName, false);
                System.out.println("Start entering cards, type \"finished\" when you are done.");
                String input = " ";
                while (!input.equalsIgnoreCase("finished")){
                    System.out.println("Enter a term.");
                    input = new Scanner(System.in).nextLine();
                    if(input.equalsIgnoreCase("finished")){
                        break;
                    }
                    String term = new String(input);
                    System.out.println("Enter a definition.");
                    String definition = new Scanner(System.in).nextLine();
                    group.addTerms(new Terms(term, definition));
                }

            }
        //}
        else{
            System.out.println("You must at least enter a subject name for the cards.");

            Groups tempGroup = new Groups();
            String input = " ";
            while (!input.equalsIgnoreCase("finished")){
                System.out.println("Enter a term.");
                input = new Scanner(System.in).nextLine();
                String term = input;
                if(input.equalsIgnoreCase("finished")){
                    break;
                }
                System.out.println("Enter a definition.");
                String definition = new Scanner(System.in).nextLine();
                tempGroup.addTerms(new Terms(term, definition));
            }
        }


    }*/
    
    public String getName(){
        return this.name;
    }
    
    public void displayGroup(){
        if(this.subGroups == true){
            for (int i = 0; i < this.subGroupList.size(); i++){
                System.out.println("\tSubgroup: " + subGroupList.get(i).getName());
                subGroupList.get(i).displayGroup();
            }
        }
        else{
            for(int i = 0; i < this.termsList.size(); i++){
                System.out.println("Term: " + termsList.get(i).getTerm());
                System.out.println("\tMeaning: " + termsList.get(i).getMeaning());
                System.out.println();
            }
        }
        
    }

    public void inOrderQuiz(){
        if(this.subGroups == true){
            for(int i = 0; i < this.subGroupList.size(); i++){
                subGroupList.get(i).inOrderQuiz();
            }
        }
        else{
            for(int i = 0; i < this.termsList.size(); i++){
                termsList.get(i).getTerm();
                System.out.println("Hit enter to see the definition.");
                String input = new Scanner(System.in).next();
                System.out.println("Definition: " + termsList.get(i).getMeaning());
            }
        }
    }

    public void shuffleQuiz(){
        if(this.subGroups == true){
            Collections.shuffle(this.subGroupList);
            for(int i = 0; i < this.subGroupList.size(); i++){
                subGroupList.get(i).shuffleQuiz();
            }
        }
        else{
            Collections.shuffle(this.termsList);
            for(int i = 0; i < this.termsList.size(); i++){
                termsList.get(i).getTerm();
                System.out.println("Hit enter to see the definition.");
                String input = new Scanner(System.in).next();
                System.out.println("Definition: " + termsList.get(i).getMeaning());
            }
        }
    }

}
