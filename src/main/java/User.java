import java.util.Arrays;

public class User {
    private double[] items;

    public User(double[] items){
        this.items = items;
    }

    public void addItem(int index, double item){
        items[index] = item;
    }

    public double getItem(int index){
        if(index >= items.length || index < 0){
            throw new IllegalArgumentException("Are you good, man? Index " + index + " doesn't exist");
        }
        return items[index];
    }

    public double[] getItems() {
        return items;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("items=").append(Arrays.toString(items));
        sb.append('}');
        return sb.toString();
    }
}
