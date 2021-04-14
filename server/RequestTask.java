/**
 * Author: Liguo Chen
 * Student ID: 851090
 * Description: This file contains the logic of a request task
 */

package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RequestTask implements Runnable {

    private Socket client;
    private HashMap<String, String> dict;

    public RequestTask(Socket client, HashMap<String, String> dict) {
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
                String newMeaning;
                String response = null;
                // client closing the connection will cause a NULL to be read from the input stream
                if (operation == null) {
                    throw new IOException();
                }
                switch (operation) {
                    case "search":
                        String result = dict.get(query);
                        response = result == null ? "" : result;
                        break;
                    case "add":
                        newMeaning = in.readLine();
                        if (dict.containsKey(query)) {
                            response = "1";
                        }
                        else {
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
                        newMeaning = in.readLine();
                        if (dict.containsKey(query)) {
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
