import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnotherMain {
    public static void main(String[] args) {
        int amountUsers = 10;
        int amountItems = 10;
        User[] users = Reader.readUsers("data.txt", amountUsers, amountItems);
        for (User user : users) {
            System.out.println(user);
        }

        int myIndex = 5;
        int n = users.length;

        double[] similarity = findSimilarity(n, users, myIndex);
        Map<Integer, Double> missedItems = findMissedItems(n, users, myIndex, similarity);

        System.out.println("Missed items");

        for(Map.Entry<Integer, Double> item : missedItems.entrySet()){
            if(item.getValue() == 0){
                System.out.println("Users with similar taste don't watch movie with " + item.getKey() + " index too");
            }else {
                System.out.print(item+", ");
            }
        }
    }

    private static Map<Integer, Double> findMissedItems(int n, User[] users, int myIndex, double[] similarity) {
        List<Integer> missedItemsIndexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double currentItem = users[myIndex].getItem(i);
            if (currentItem == 0) {
                missedItemsIndexes.add(i);
            }
        }

        Map<Integer, Double> missedItems = new HashMap<>();
        for (int index : missedItemsIndexes) {
            double currentItem = getMissedItem(n, users, index, similarity);
            missedItems.put(index, currentItem);
        }

        return missedItems;
    }

    private static double[] findSimilarity(int n, User[] users, int myIndex) {
        double[] similarity = new double[n];

        System.out.println("Selected user with index " + myIndex + ":\n" + users[myIndex]);

        System.out.println("similarity");
        for (int i = 0; i < n; i++) {
            similarity[i] = pearson(users, myIndex, i);
            System.out.println("User " + i + ":\n" + users[i]);
            System.out.println("Similarity with user " + i + ": " + similarity[i]);
        }

        return similarity;
    }

    private static double pearson(User[] users, int myIndex, int i) {
        int generalN = users[myIndex].getItems().length;
        int localN = findN(users[myIndex], users[i]);
        List<Integer> skippedIndexes = findSkippedIndexes(users, myIndex, i);
        double vector1 = sum(users[myIndex].getItems(), skippedIndexes)/localN;
        double vector2 = sum(users[i].getItems(), skippedIndexes)/localN;

        double diff = getDiff(users[myIndex].getItems(), users[i].getItems(), vector1, vector2, generalN, skippedIndexes);
        double sqrDiff1 = getSqrPow(users[myIndex].getItems(), vector1, generalN, skippedIndexes);
        double sqrDiff2 = getSqrPow(users[i].getItems(), vector2, generalN, skippedIndexes);
        return diff/(sqrDiff1*sqrDiff2);
    }

    private static List<Integer> findSkippedIndexes(User[] users, int myIndex, int i) {
        List<Integer> skippedIndexes = new ArrayList<>();
        for (int j = 0; j < users[myIndex].getItems().length; j++) {
            if(users[i].getItem(j) == 0 || users[myIndex].getItem(j) == 0)
                skippedIndexes.add(j);
        }
        return skippedIndexes;
    }

    private static double getSqrPow(double[] items, double vector1, int n, List<Integer> skippedIndexes) {
        double result = 0;
        for (int i = 0; i < n; i++) {
            if (skippedIndexes.contains(i))
                continue;
            result += Math.pow(items[i]-vector1, 2);
        }
        return Math.sqrt(result);
    }

    private static double getDiff(double[] items1, double[] items2, double vector1,double vector2, int n, List<Integer> skippedIndexes){
        double result = 0;
        for (int i = 0; i < n; i++) {
            if (skippedIndexes.contains(i))
                continue;
            result += (items1[i]-vector1)*(items2[i]-vector2);
        }
        return result;
    }


    private static double getMissedItem(int n, User[] users, int index, double[] similarity) {
        double currentItem = 0;
        double similaritySum = 0;
        for (int i = 0; i < n; i++) {
            double item = users[i].getItem(index);
            if (item == 0 || similarity[i] < 0.5) {
                continue;
            }
            currentItem += similarity[i] * item;
            similaritySum += similarity[i];
        }
        currentItem /= Math.abs(similaritySum);
        return currentItem;
    }

    private static double sum(double[] items, List<Integer> skippedIndexes) {
        double result = 0;
        for (int i = 0; i < items.length; i++) {
            double e = items[i];
            if (skippedIndexes.contains(i))
                continue;
            result += e;
        }
        return result;
    }


    private static int findN(User user1, User user2) {
        int n = 0;
        double[] items1 = user1.getItems();
        double[] items2 = user2.getItems();
        for (int i = 0; i < items1.length; i++) {
            if (items1[i] != 0 && items2[i] != 0)
                n++;
        }
        return n;
    }
}