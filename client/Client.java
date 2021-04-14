/**
 * Author: Liguo Chen
 * Student ID: 851090
 */

package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {

        if (args.length < 2) {
            // not enough input params provided
            System.out.println("Usage: java -jar DictionaryClient.jar <server-address> <server-port>");
            return;
        }

        String serverAddr = args[0];
        int serverPort = Integer.parseInt(args[1]);

        Socket s = null;

        try {
            s = new Socket(serverAddr, serverPort);
        }
        catch (UnknownHostException uh) {
            System.err.println("System failed to recognise the provided host.");
            return;
        }
        catch (IOException ioe) {
            System.err.println("System failed to establish a connection with the server.");
            return;
        }

        BufferedReader in;
        BufferedWriter out;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
        }
        catch (IOException ioe) {
            System.err.println("System failed to get stream from server socket.");
            return;
        }

        Windows windows = new Windows();

        try {
            while (true) {
                while (true) {
                    // this inner inf loop is for waiting user input
                    if (windows.isSearchButtonPressed()) {
                        out.write("search\n");
                        out.write(windows.getSearchQuery().toLowerCase() + "\n");
                        out.flush();
                        windows.resetSearchButton();
                        windows.setSearchResult(in.readLine());
                        break;
                    }
                    else if (windows.isAddButtonPressed()) {
                        out.write("add\n");
                        out.write(windows.getAddQuery().toLowerCase() + "\n");
                        out.write(windows.getMeaning() + "\n");
                        out.flush();
                        windows.resetAddButton();
                        int response = Integer.parseInt(in.readLine());
                        // 0: success
                        // 1: duplicate
                        switch (response) {
                            case 0:
                                windows.setAddStatus("Success", true);
                                break;
                            case 1:
                                windows.setAddStatus("Word already exists", false);
                                break;
                        }
                        windows.resetAddStatus();
                        break;
                    }
                    else if (windows.isRemoveButtonPressed()) {
                        out.write("remove\n");
                        out.write(windows.getRemoveQuery().toLowerCase() + "\n");
                        out.flush();
                        windows.resetRemoveButton();
                        windows.resetRemoveStatus();
                        int response = Integer.parseInt(in.readLine());
                        // 0: success
                        // 1: not found
                        switch (response) {
                            case 0:
                                windows.setRemoveStatus("Success", true);
                                break;
                            case 1:
                                windows.setRemoveStatus("Word not found in the dictionary", false);
                                break;
                        }
                        windows.resetRemoveStatus();
                        break;
                    }
                    else if (windows.isUpdateButtonPressed()) {
                        out.write("update\n");
                        out.write(windows.getUpdateQuery().toLowerCase() + "\n");
                        out.write(windows.getMeaningUpdate() + "\n");
                        out.flush();
                        windows.resetUpdateButton();
                        int response = Integer.parseInt(in.readLine());
                        // 0: success
                        // 1: not found
                        switch (response) {
                            case 0:
                                windows.setUpdateStatus("Success", true);
                                break;
                            case 1:
                                windows.setUpdateStatus("Word not found in the dictionary", false);
                                break;
                        }
                        windows.resetUpdateStatus();
                        break;
                    }
                    else if (windows.isShutdownButtonPressed()) {
                        if (s != null) {
                            try {
                                s.close();
                            }
                            catch (IOException ioe) {
                                System.err.println("System failed when trying to close the socket.");
                            }
                        }
                        System.exit(0);
                    }
                    // this line is to suppress the compiler optimisation of skipping empty loop
                    System.out.println("");
                }
            }
        }
        catch (IOException ioe) {
            System.err.println("System failed to read /send data from/to socket stream.");
            String msg = "Server is lost";
            if (windows.isSearchButtonPressed()) {
                windows.setSearchResult(msg);
            }
            else if (windows.isAddButtonPressed()) {
                windows.setAddStatus(msg, false);
            }
            else if (windows.isRemoveButtonPressed()) {
                windows.setRemoveStatus(msg, false);
            }
            else if (windows.isUpdateButtonPressed()) {
                windows.setUpdateStatus(msg, false);
            }
        }
        finally {
            if (s != null) {
                try {
                    s.close();
                }
                catch (IOException ioe) {
                    System.err.println("System failed when trying to close the socket.");
                }
            }
        }
    }
}
