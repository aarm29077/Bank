import java.io.Serializable;

public class Account implements Serializable {
    private long id;
    private int pin;
    private long total;

    public Account(long id, int pin) {
        this.id = id;
        this.pin = pin;
    }

    public void deposit(long amount) {
        total += amount;
    }

    public void withdraw(long amount) {
        if (total - amount < 0) {
            System.out.println("Insufficient funds");
        } else {
            total -= amount;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Account: " + "id = " + id + ", total = " + total + '.';
    }
}
