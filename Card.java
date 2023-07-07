public class Card implements Comparable<Card> {
    private final long id;
    private final String name;
    private final Rank rank;
    private long price;

    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = 0;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && equals(name, card.name) && rank == card.rank;
    }

    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    @Override
    public int hashCode() {
        return hashCode(id) ^ hashCode(name) ^ hashCode(rank);
    }

    private static int hashCode(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    @Override
    public int compareTo(Card other) {
        int rankComparison = this.rank.compareTo(other.rank);
        if (rankComparison != 0) {
            return rankComparison;
        }

        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }

        return compare(this.id, other.id);
    }

    private static int compare(long a, long b) {
        return (a < b) ? -1 : ((a == b) ? 0 : 1);
    }
}
