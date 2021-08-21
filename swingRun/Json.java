package swingRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class Json {

    // creates a JSON from the given Run object. Then makes a file with
    // the name "data" and as many ones as needed on the end until the file
    // is unique
    public static void addToJson(Run run) {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        try {
            boolean bool= true;
            String name= "data";
            while (bool) {
                String temp= name;
                File tempFile= new File(temp.concat(".json"));
                boolean exists= tempFile.exists();
                if (!exists) {
                    bool= false;
                } else {
                    name= name.concat("1");
                }

            }

            Writer writer= new FileWriter(name.concat(".json"));
            gson.toJson(run, writer);

            writer.close();

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // gets all saved JSONs and returns an array list containing each one
    public static ArrayList<String> getAllRuns() {
        ArrayList<String> list= new ArrayList<>();
        try {
            String name= "data";
            while (true) {
                // String content= new Scanner(new File(name.concat(".json"))).useDelimiter("\\Z")
                // .next();
                Scanner scan= new Scanner(new File(name.concat(".json")));
                String content= scan.useDelimiter("\\Z").next();
                list.add(content);
                name= name.concat("1");
                scan.close();

            }
        } catch (FileNotFoundException e) {
            // done
            return list;
        }

    }

    // removes most recent Run json
    public static void removeLastRun() {
        boolean bool= true;
        String name= "data";
        File tempFile= new File(name.concat(".json"));
        boolean exists= tempFile.exists();
        if (!exists) {
            bool= false;
        }
        while (bool) {
            String temp= name;
            File tempFil= new File(temp.concat("1.json"));
            boolean exist= tempFil.exists();
            if (!exist) {
                bool= false;
            } else {
                name= name.concat("1");
            }

        }
        name= name.concat(".json");

        File file= new File(name);
        file.delete();

    }

    // converts the array list of jsons into their equivalent Run objects,
    // then returns an arraylist of the Run objects
    public static ArrayList<Run> objectRuns(ArrayList<String> list) {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Run> fin= new ArrayList();
        for (String json : list) {
            fin.add(gson.fromJson(json, Run.class));
        }
        if (fin.size() == 0) { return null; }
        return fin;
    }
}
