import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String name;
    private String surname;
    private String passportNO;


    public User(String name, String surname, String passportNO) {
        this.name = name;
        this.surname = surname;
        this.passportNO = passportNO;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassportNO() {
        return passportNO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(passportNO, user.passportNO);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (passportNO != null ? passportNO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User: " + "name = " + name + ", surname = " + surname + ", passportNO = '" + passportNO + '.';
    }
}
