import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Qadir on 8/30/2015.
 */
public class FlashQuiz {

    public FlashQuiz(){

    }

    private static PrintWriter openWriter(String name){
        try {
            File file = new File(name);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            return out;
        }
        catch (IOException e){
            System.out.println("I/O Error");
            System.exit(0);
        }
        return null;
    }

    public static void writeGroup(Groups g, PrintWriter out){
        String group = "#" + g.getName();
        if(g.getSubGroupsBool()){
            for (Groups grp : g.getSubGroupList()){
                group += "\n\t @"+grp.getName();
                for(Terms t : grp.getTermsList()){
                    group += "\n\t\t $" +  t.getTerm() + " - " + t.getMeaning();
                }
            }
        }
        else{
            for(Terms t : g.getTermsList()){
                group += "\n\t\t$" + t.getTerm() + " - " + t.getMeaning();
            }
        }
        out.println(group);
    }

    public static Groups prompt(){
        Groups group;
        System.out.println("Enter a subject to study for.");
        String groupName = new Scanner(System.in).nextLine();
        System.out.println("Would you like to make a sub-section for the subject?");
        String subGrouping = new Scanner(System.in).nextLine();
        if(subGrouping.equalsIgnoreCase("yes")){
            group = new Groups(groupName, true);
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
            return group;
        }
        else{
            group = new Groups(groupName, false);
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
            return group;
        }
    }

    private static BufferedReader getReader(String name){
        BufferedReader in = null;
        try{
            File file = new File(name);
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e){
            System.out.println("The file does not exist.");
            System.exit(0);
        }
        return in;
    }

    private static ArrayList<Groups> readGroups(BufferedReader in){
        String line = " ";
        int numSubjects = -1;
        int numSubSubjects = -1;
        ArrayList<Groups> list = new ArrayList<Groups>();
        while(!line.isEmpty()){
            try{
                line = in.readLine();
                if(line != null){
                    line = line.trim();
                }
                else{
                    break;
                }

            }
            catch (IOException e){
                System.out.println("The file doesn't exist.");
                System.exit(0);
            }
            if (line == null)
                return null;
            else{
                if(line.charAt(0) == '#'){
                    numSubjects++;
                    list.add(new Groups(line.substring(1), true));
                }
                else if(line.charAt(0) == '@'){
                    numSubSubjects++;
                    list.get(numSubjects).setSubGroupsBool(true);
                    list.get(numSubjects).addSubGroup(new Groups(line.substring(1), false));
                }
                else if(line.charAt(0) == '$'){
                    String[] lineSplit = line.split("-");
                    System.out.println("Line Split: " + lineSplit[0].substring(1));
                    if(lineSplit.length == 1){
                        list.get(numSubjects).getSubGroupList().get(numSubSubjects).addTerms(
                                new Terms(lineSplit[0].substring(1), ""));
                    }else{
                        list.get(numSubjects).getSubGroupList().get(numSubSubjects).addTerms(
                                new Terms(lineSplit[0].substring(1), lineSplit[1]));
                    }
                }
            }
            numSubSubjects = 0;
        }
        return list;
    }

    public static void main(String[] args) throws IOException{
        ArrayList<Groups> collection = new ArrayList<>();
        System.out.println("If you have data on a file you would like to read in, enter the file name.");
        String fileName = new Scanner(System.in).nextLine();
        if(!fileName.isEmpty()){
            File file = new File(fileName);
            BufferedReader in = getReader(fileName);
            collection = readGroups(in);
            System.out.println("Collection: " + collection.get(0).getSubGroupList().get(0).getTermsList());
        }
        System.out.println("Would you like to enter some other cards? (Type \"yes\" to continue)");
        String input = new Scanner(System.in).nextLine();
        if(input.equalsIgnoreCase("yes")){
            PrintWriter out = openWriter("saveFile.txt");

            collection.add(FlashQuiz.prompt());

            for(Groups g : collection){
                writeGroup(g, out);
            }
            out.close();
        }
        collection.get(0).shuffleQuiz();
    }


}
