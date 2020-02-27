import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static User[] readUsers(String name, int amountUsers, int amountItems) {
        File file = new File("src/main/resources/" + name);
        User[] users = new User[amountUsers];
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int i = 0; i < users.length; i++) {
                String[] line = br.readLine().split(",");
                double[] items = new double[amountItems];
                for (int j = 0; j < items.length; j++) {
                    if (line[j].isEmpty()) {
                        items[j] = 0;
                    } else {
                        items[j] = Double.parseDouble(line[j]);
                    }
                }
                users[i] = new User(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
