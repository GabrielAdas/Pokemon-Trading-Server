public class CardTest {
    public static void main(String[] args) {
        Card card1 = new Card(1, "Card A", Rank.UNIQUE);
        Card card2 = new Card(2, "Card B", Rank.RARE);
        Card card3 = new Card(3, "Card C", Rank.UNCOMMON);
        Card card4 = new Card(4, "Card D", Rank.COMMON);
        card4.setPrice(100);

        Card[] cardArray = new Card[4];
        cardArray[0] = card1;
        cardArray[1] = card2;
        cardArray[2] = card3;
        cardArray[3] = card4;

        int setSize = 0;
        Card[] cardSet = new Card[cardArray.length];
        for (int i = 0; i < cardArray.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < setSize; j++) {
                if (cardSet[j].equals(cardArray[i])) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                cardSet[setSize++] = cardArray[i];
            }
        }

        System.out.println("Set size: " + setSize); // Should be 3

        Card[] sortedSet = new Card[setSize];
        System.arraycopy(cardSet, 0, sortedSet, 0, setSize);

        for (int i = 0; i < setSize - 1; i++) {
            for (int j = 0; j < setSize - i - 1; j++) {
                if (sortedSet[j].compareTo(sortedSet[j + 1]) > 0) {
                    Card temp = sortedSet[j];
                    sortedSet[j] = sortedSet[j + 1];
                    sortedSet[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted set contents:");
        for (int i = 0; i < setSize; i++) {
            System.out.println(sortedSet[i]);
        }
    }
}
