import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CardInputStream extends InputStream {
    private BufferedReader reader;

    public CardInputStream(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public int read() throws IOException {
        return reader.read();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }


    // used to read a Card object from the input stream. 
    //It first reads a line of text from the input stream and checks if it is equal to "CARD". 
    // If not, it returns null. If it is "CARD", it reads the next four lines of text, which represent the ID, name, rank, and price of the card, respectively.
    // It then creates a new Card object with the specified ID, name, and rank, sets the price of the card, and returns the Card object.
    public Card readCard() throws IOException {
        String line = reader.readLine();
        if (line == null || !line.equals("CARD")) {
            return null;
        }

        long id = Long.parseLong(reader.readLine());
        String name = reader.readLine();
        Rank rank = Rank.valueOf(reader.readLine());
        long price = Long.parseLong(reader.readLine());

        Card card = new Card(id, name, rank);
        card.setPrice(price);

        return card;
    }

    public String readResponse() throws IOException {
        return reader.readLine();
    }
}
