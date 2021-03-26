import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Server {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            // not enough input params provided
            System.out.println("Usage: java -jar DictionaryServer.jar <port> <dictionary-file>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dictFile = args[1];

        HashMap<String, String> dict = new HashMap<>();

//        try {
        // prepare the dictionary to be used for reply requests
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile)));
        }
        catch (FileNotFoundException e) {
            System.err.println("The provided dictionary file " + dictFile + " cannot be found");
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
            System.out.println("System failed to process the provided dictionary file.");
            return;
        }

        // create a server socket
        ServerSocket server;
        try {
            server = new ServerSocket(port);
        }
        catch (IOException ioe) {
            System.err.println("System failed to open a socket for the server.");
            return;
        }

        // an inf loop to keep the server online
        Socket client = server.accept();
        while (true) {
            System.out.println("Server is listening at port " + port);
//            Socket client;
//            try {
//                client = server.accept();
//            }
//            catch (IOException ioe) {
//                System.err.println("System failed to connect to client.");
//                continue;
////                return;
//            }
            System.out.println("Connected to a client");

            BufferedReader in;
            BufferedWriter out;
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
                out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            }
            catch (IOException ioe) {
                System.err.println("System failed to get stream from client socket.");
                continue;
            }

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
                }
                out.write(response + "\n");
                out.flush();
            }
            catch (IOException ioe1) {
                System.err.println("System failed to read data from socket stream.");
                try {
                    client.close();
                }
                catch (IOException ioe2) {
                    System.err.println("System failed to close faulty client socket.");
                }
            }
        }
    }
}
