import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static User[] readUsers() {
        File file = new File("src/main/resources/data.txt");
        User[] users = new User[10];
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int i = 0; i < users.length; i++) {
                String[] line = br.readLine().split(",");
                double[] items = new double[users.length];
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
