package project.emp.fri.si.debtchecker;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jan on 26. 12. 2017.
 */

public class DBInterface extends AsyncTask<String, Integer, String> {

    private static final String DBNAME = "empdb_testna";
    private static final String DBUSER = "root";
    private static final String DBPASS = "clumbsyhack3r";

    private String operation_type;
    private View progressBar;


    DBInterface(String operation_type, View progress) {

        this.operation_type = operation_type;
        this.progressBar = progress;
    }

    static boolean insertUser(String name, String surname, String email, String nickname, String username, String password) {

        String insertStr = "INSERT INTO Oseba VALUES (NULL, '" + name + "', '" + surname + "', '" + email + "', '" + nickname + "', '" + username + "', '" + DBInterface.encryptSHA256(password) + "')";
        try {

            String out = new DBInterface("insert", null).execute(insertStr).get();

            if (!out.equals(""))
                return false;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean insert(String[] attributeNames, String[] attributeValues, String table, String[] conditions) {

        StringBuilder attNames = new StringBuilder("");
        for (String attName : attributeNames)
            attNames.append("'" + attName + "', ");
        attNames.delete(attNames.length() - 3, attNames.length() - 1);

        StringBuilder attValues = new StringBuilder("");
        for (String attValue : attributeValues)
            attNames.append("'" + attValues + "', ");
        attNames.delete(attNames.length() - 3, attNames.length() - 1);

        String insertStr = "INSERT INTO " + table + " (" + attNames.toString() + ") VALUES (" + attNames.toString() + ")";

        try {
            String out = new DBInterface("insert", null).execute(insertStr).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    static String[] queryPayments(int id) {

        String selectStr = "SELECT * FROM Placilo WHERE idPlacnik=" + id + " OR idPrejemnik=" + id;
        String data = null;
        String[] out = null;
        try {
            data = new DBInterface("select", null).execute(selectStr).get();
            out = data.split("\n");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return out;
    }

    static String queryUserAll(int id) {
        String selectString = "SELECT * FROM Oseba WHERE id=" + id;
        String out = null;
        try {
            out = new DBInterface("select", null).execute(selectString).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return out;
    }

    static String queryUser(String[] attributes, int id) {

        StringBuilder att = new StringBuilder("");
        for (String attribute : attributes)
            att.append("'" + attribute + "', ");
        att.delete(att.length() - 3, att.length() - 1);

        String selectStr = "SELECT " + att.toString() + " FROM Oseba WHERE id=" + id;
        String out = null;
        try {
            out = new DBInterface("insert", null).execute(selectStr).get();
            //System.out.println("***" + out);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return out;
    }

    static String query(String[] attributes, String table, String[] conditions) {

        return querySpecial(attributes, table, null, conditions, null);
    }

    static String querySpecial(String[] attributes, String table, String[] joins, String[] conditions, String[] group) {

        // Create a query string from the data given... will be done soon.. give a man some time!
        return null;
    }

    static String encryptSHA256(String text) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        md.update(text.getBytes());
        byte[] digest = md.digest();

        // Return the encryption, without \n at the end
        return Base64.encodeToString(digest, Base64.DEFAULT).replace("\n", "");
    }


    @Override
    protected String doInBackground(String... params) {

        // Return string
        String out = null;

        // Encode standard data in the URI
        String url = "http://83.212.126.62/";
        String encodedData = "";
        try {
            encodedData = URLEncoder.encode("dbname", "UTF-8") + "=" + URLEncoder.encode(DBNAME, "UTF-8");
            encodedData += "&" + URLEncoder.encode("dbuser", "UTF-8") + "=" + URLEncoder.encode(DBUSER, "UTF-8");
            encodedData += "&" + URLEncoder.encode("dbpass", "UTF-8") + "=" + URLEncoder.encode(DBPASS, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        switch (this.operation_type) {
            case "login":

                url += "RESTlogin.php";
                try {
                    encodedData += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                out = sentHttpPOST(url, encodedData, true);

                if (out.length() < 40)
                    out = "noResult";

                break;

            case "select":
                url += "RESTselect.php";
                try {
                    encodedData += "&" + URLEncoder.encode("selectString", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                    // Need to add constructive data
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                out = sentHttpPOST(url, encodedData, false);
                break;
            case "insert":

                url += "RESTinsert.php";
                try {
                    encodedData += "&" + URLEncoder.encode("insertString", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                out = sentHttpPOST(url, encodedData, true);
                break;
        }
        return out;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Nullable
    private static String sentHttpPOST(String urlConnection, String encodedData, boolean postProgress) {

        // Basic components needed
        HttpURLConnection c = null;
        int timeout = 5000;

        // If progress checker will be added in the future
        //long startingTime = System.currentTimeMillis();

        try {
            URL url = new URL(urlConnection);
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);        // This is for POST method
            c.setChunkedStreamingMode(0);       // Beacuse we dont know the length of the message
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);

            // This 3 lines are only for POST method
            OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());
            out.write(encodedData);
            out.flush();

            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    // Return a substring of the result without the last \n
                    // max function takes care, so that if the string is too short, it still returns only an empty string
                    return sb.substring(0, Math.max(sb.length() - 1, 0));
                case 500:
                    return "500EROOOR PISMO!";
            }

        } catch (IOException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return "Error 1!";
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
                    return "Error 2!";
                }
            }
        }
        return null;
    }
}
