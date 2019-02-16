package structure.com.foodportal.helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import structure.com.foodportal.fragment.foodportal.FoodHomeFragment;

public class LocalDataHelper {


    public static void writeToFile(String data, Context context, String cname) {
        try {

            OutputStreamWriter outputStreamWriter = null;
            switch (cname) {

                case "Home":
                     outputStreamWriter = new OutputStreamWriter(context.openFileOutput("homedata.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                    break;

                case "Detail":
                     outputStreamWriter = new OutputStreamWriter(context.openFileOutput("detail.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                    break;

                case "StepByStep":
                     outputStreamWriter = new OutputStreamWriter(context.openFileOutput("stepbystep.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                    break;


            }



        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context, String cname) {

        String ret = "";

        try {
            InputStream inputStream = null;


            switch (cname) {

                case "Home":
                    inputStream = context.openFileInput("homedata.txt");
                    break;

                case "Detail":
                    inputStream = context.openFileInput("detail.txt");
                    break;

                case "StepByStep":
                    inputStream = context.openFileInput("stepbystep.txt");
                    break;


            }

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
