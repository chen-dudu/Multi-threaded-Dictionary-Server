/**
 * Author: Liguo Chen
 * Student ID: 851090
 */

package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Server {

    public static void main(String[] args) {

        if (args.length < 2) {
            // not enough input params provided
            System.out.println("Usage: java -jar DictionaryServer.jar <port> <dictionary-file>\n");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dictFile = args[1];

        if (port < 1024 || port > 65535) {
            System.err.println("Please choose a reasonable port number and start the server again.\n");
            return;
        }

        HashMap<String, String> dict = new HashMap<>();

        // parse the json file containing initial words
        JSONParser parser = new JSONParser();
        JSONArray words;
        try {
            words = (JSONArray) parser.parse(new FileReader(dictFile));
        }
        catch (FileNotFoundException fe) {
            System.err.println("The provided dictionary file " + dictFile + " cannot be found.\n");
            return;
        }
        catch (Exception e) {
            System.err.println("System failed to process the provided dictionary file.\n");
            return;
        }

        for (Object object: words) {
            JSONObject word = (JSONObject) object;
            dict.put(word.get("word").toString(), word.get("meaning").toString());
        }

        // create a server socket
        ServerSocket server;
        try {
            server = new ServerSocket(port);
        }
        catch (IOException ioe) {
            System.err.println("System failed to open a socket for the server.\nYou may want to try a different port.\n");
            return;
        }

        Windows window = new Windows(server);

        // keep waiting until the launch button is pressed
        while(!window.isLaunchButtonPressed()) {
            // this line is to suppress the compiler optimisation of skipping empty loop
            System.out.println();
        }

        int numThread = window.getConcurrentUser();

        ExecutorService pool = Executors.newFixedThreadPool(numThread);

        // an inf loop to keep the server online
        while (true) {
            System.out.println("Server is listening at port " + port + "\n");
            Socket client;
            try {
                client = server.accept();
            }
            catch (SocketException se) {
                // capture the exception when server socket is being closed
                System.out.println("Server has been shut down.\n");
                break;
            }
            catch (IOException ioe) {
                System.err.println("System failed to connect to client.\n");
                continue;
//                return;
            }
            System.out.println("Connected to a client.\n");

            // get a thread from the pool and assign the newly connected client to it
            pool.execute(new RequestTask(client, dict));
        }
        // destroy the thread pool
        pool.shutdown();
        try {
            if (!pool.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            System.err.println("System failed to clsoe the thread pool.\n");
        }
        // close the program
        System.exit(0);
    }
}
