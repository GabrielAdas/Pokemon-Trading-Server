import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class HollomonClient {
    private String server;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public HollomonClient(String server, int port) {
        this.server = server;
        this.port = port;
    }


    //  log in to the server with a username and password. If the login is successful, it returns a list of Card objects. Otherwise, it returns null.
    public List<Card> login(String username, String password) {
        try {
            socket = new Socket(server, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    
            writer.println(username.toLowerCase());
            writer.println(password);
    
            String response = reader.readLine();
            if (response != null && response.equals("User " + username.toLowerCase() + " logged in successfully.")) {
                List<Card> cards = new ArrayList<>();
                CardInputStream cardInputStream = new CardInputStream(socket.getInputStream());
                Card card;
                while ((card = cardInputStream.readCard()) != null) {
                    cards.add(card);
                }
                Collections.sort(cards);
                return cards;
            } else {
                return null;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // used to get the number of credits available to the user.
    public long getCredits() {
        try {
            writer.println("CREDITS");
            String response = reader.readLine();
            if (response != null) {
                long credits = Long.parseLong(response);
                String okResponse = reader.readLine();
                if (okResponse != null && okResponse.equals("OK")) {
                    return credits;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 to indicate an error occurred
    }


    // used to get a list of all the cards available.
    public List<Card> getCards() {
        try {
            writer.println("CARDS");
            List<Card> cards = new ArrayList<>();
            CardInputStream cardInputStream = new CardInputStream(socket.getInputStream());
            Card card;
            while ((card = cardInputStream.readCard()) != null) {
                cards.add(card);
            }
            Collections.sort(cards);
            return cards;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // used to get a list of all the cards available for purchase
    public List<Card> getOffers() {
        try {
            writer.println("OFFERS");
            List<Card> offers = new ArrayList<>();
            CardInputStream cardInputStream = new CardInputStream(socket.getInputStream());
            Card card;
            while ((card = cardInputStream.readCard()) != null) {
                offers.add(card);
            }
            Collections.sort(offers);
            return offers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // used to buy a specific card, provided the user has enough credits.
    public boolean buyCard(Card card) {
        try {
            long credits = getCredits();
            if (credits >= card.getPrice()) {
                writer.println("BUY " + card.getId());
                String response = reader.readLine();
                if (response != null && response.equals("OK")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // used to sell a specific card at a specified price.
    public boolean sellCard(Card card, long price) {
        try {
            writer.println("SELL " + card.getId() + " " + price);
            String response = reader.readLine();
            if (response != null && response.equals("OK")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
