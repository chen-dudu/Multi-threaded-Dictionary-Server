/**
 * Author: Liguo Chen
 * Student ID: 851090
 * Description: This file contains the logic of the thread serving client
 */

package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ServerThread implements Runnable {

    private Socket client;
    private HashMap<String, String> dict;

    public ServerThread(Socket client, HashMap<String, String> dict) {
        this.client = client;
        this.dict = dict;
    }

    @Override
    public void run() {
        BufferedReader in;
        BufferedWriter out;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
        }
        catch (IOException ioe) {
            if (client.isConnected()) {
                System.err.println("System failed to get stream from client socket.\nClient socket is about to be closed.\n");
            }
            else {
                System.err.println("Client is lost, close client socket.\n");
                try {
                    client.close();
                }
                catch (IOException e) {
                    System.err.println("System failed to close client socket.\n");
                }
            }
            return;
        }

        while (true) {
            try {
                String operation = in.readLine();
                String query = in.readLine();
                String response = null;
                switch (operation) {
                    case "search":
                        String result = dict.get(query);
                        response = result == null ? "No match result" : result;
                        break;
                    case "add":
                        if (dict.containsKey(query)) {
                            response = "1";
                        }
                        else {
                            String newMeaning = in.readLine();
                            dict.put(query, newMeaning);
                            response = "0";
                        }
                        break;
                    case "remove":
                        if (dict.containsKey(query)) {
                            dict.remove(query);
                            response = "0";
                        }
                        else {
                            response = "1";
                        }
                        break;
                    case "update":
                        if (dict.containsKey(query)) {
                            String newMeaning = in.readLine();
                            dict.put(query, newMeaning);
                            response = "0";
                        }
                        else {
                            response = "1";
                        }
                        break;
                }
                out.write(response + "\n");
                out.flush();
            }
            catch (IOException ioe) {
                if (client.isConnected()) {
                    System.err.println("System failed to read data from socket stream.\nClient socket is about to be closed.\n");
                }
                else {
                    System.err.println("Client is lost, close client socket.\n");
                    try {
                        client.close();
                    }
                    catch (IOException e) {
                        System.err.println("System failed to close client socket.\n");
                    }
                }
                return;
            }
        }
    }
}
