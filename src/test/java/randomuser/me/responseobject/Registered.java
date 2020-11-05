package randomuser.me.responseobject;

public class Registered {
    String date;
    int age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Registered{" +
                "date='" + date + '\'' +
                ", age=" + age +
                '}';
    }
}
