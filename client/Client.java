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

    // this is the survival time of status information
    private static final long RESET_TIME = 2000L;

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
                    if (windows.isSearchButtonPressed()) {
                        windows.resetSearchButton();
                        out.write("search\n");
                        out.write(windows.getSearchQuery() + "\n");
                        out.flush();
                        windows.setSearchResult(in.readLine());
                        break;
                    }
                    else if (windows.isAddButtonPressed()) {
                        windows.resetAddButton();
                        out.write("add\n");
                        out.write(windows.getAddQuery() + "\n");
                        out.write(windows.getMeaning() + "\n");
                        out.flush();
                        int response = Integer.parseInt(in.readLine());
                        // 0: success
                        // 1: duplicate
                        switch (response) {
                            case 0:
                                windows.setAddStatus("Success");
                                break;
                            case 1:
                                windows.setAddStatus("Word already exists");
                                break;
                        }
                        // create a new thread and let it sleep for 1s before resetting status info
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(RESET_TIME);
                                    windows.resetAddStatus();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();
                        break;
                    }
                    else if (windows.isRemoveButtonPressed()) {
                        windows.resetRemoveButton();
                        out.write("remove\n");
                        out.write(windows.getRemoveQuery() + "\n");
                        out.flush();
                        windows.resetRemoveStatus();
                        int response = Integer.parseInt(in.readLine());
                        // 0: success
                        // 1: not found
                        switch (response) {
                            case 0:
                                windows.setRemoveStatus("Success");
                                break;
                            case 1:
                                windows.setRemoveStatus("Word not found in the dictionary");
                                break;
                        }
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(RESET_TIME);
                                    windows.resetRemoveStatus();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();
                        break;
                    }
                    // this line is to suppress the compiler optimisation of skipping empty loop
                    System.out.println("");
                }
            }
        }
        catch (IOException ioe) {
            System.err.println("System failed to read /send data from/to socket stream.");
            return;
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
