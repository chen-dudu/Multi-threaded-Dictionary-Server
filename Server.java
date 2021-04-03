/**
 * Author: Liguo Chen
 * Student ID: 851090
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // number of threads in the pool
    public static final int NUM_THREADS = 4;

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

//        try {
        // prepare the dictionary to be used for reply requests
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile)));
        }
        catch (FileNotFoundException e) {
            System.err.println("The provided dictionary file " + dictFile + " cannot be found.\n");
            return;
        }
        String word, meaning;
        try {
            while ((word = reader.readLine()) != null) {
                meaning = reader.readLine();
                dict.put(word.toLowerCase(), meaning);
            }
        }
        catch (IOException ioe) {
            System.out.println("System failed to process the provided dictionary file.\n");
            return;
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

        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

        // an inf loop to keep the server online
        while (true) {
            System.out.println("Server is listening at port " + port + "\n");
            Socket client;
            try {
                client = server.accept();
            }
            catch (IOException ioe) {
                System.err.println("System failed to connect to client.\n");
                continue;
//                return;
            }
            System.out.println("Connected to a client.\n");

            // get a thread from the pool and assign the newly connected client to it
            pool.execute(new ServerThread(client, dict));
        }
    }
}