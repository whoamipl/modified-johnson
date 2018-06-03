import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<int[]> machines = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<Task> n1 = new ArrayList<>();
        List<Task> n2 = new ArrayList<>();

        try {
            Files.lines(Paths.get("data.txt"))
                    .map((line) -> line.split(" "))
                    .forEach(arr -> machines.add(Arrays.stream(arr)
                            .mapToInt(Integer::parseInt)
                            .toArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int t1 = 0;
        int t2 = 0;

        for (int i = 0; i < machines.get(0).length; ++i) {
            t1 = machines.get(0)[i] + machines.get(1)[i];
            t2 = machines.get(1)[i] + machines.get(2)[i];
            tasks.add(new Task(i + 1,machines.get(0)[i], machines.get(1)[i], machines.get(2)[i], t1, t2));

        }

        for (Task t : tasks) {
            if (t.getT1() < t.getT2()) {
                n1.add(t);
            }

            if (t.getT1() >= t.getT2()) {
                n2.add(t);
            }
        }

        n1.sort(Comparator.comparing(Task::getT1));
        n2.sort(Comparator.comparing(Task::getT2).reversed());

        List<Task> toPrint = new ArrayList<>(n1);
        toPrint.addAll(n2);

        createSchedule(toPrint);
    }

    private static void createSchedule(List<Task> toPrint) {
        int spaces = 0;
        int placeToStart = -1;
        String[] machines = new String[3];
        machines[0] = ("|M1|");
        machines[1] = ("|M2|");
        machines[2] = ("|M3|");

        for (Task t : toPrint) {
            System.out.println(t.toString());

            for (int i = 0; i < t.getM1(); i++) {
                machines[0] += t.id;
            }

            placeToStart = machines[0].lastIndexOf(String.valueOf(t.id));

            while (machines[1].length() < placeToStart)
                machines[1] += "-";

            for (int i = 0; i < t.getM2(); ++i) {
                machines[1] += t.id;
            }

            placeToStart = machines[1].lastIndexOf(String.valueOf(t.id));

            while (machines[2].length() < placeToStart)
                machines[2] += "-";

            for (int i = 0; i < t.getM3(); ++i) {
                machines[2] += t.id;
            }
        }

        for (String s : machines) {
            System.out.println(s);
        }
    }

    private static class Task {
        private int id;
        private int m1,m2,m3;
        private int t1, t2;

        public Task(int id, int m1, int m2, int m3, int t1, int t2) {
            this.id = id;
            this.m1 = m1;
            this.m2 = m2;
            this.m3 = m3;
            this.t1 = t1;
            this.t2 = t2;
        }

        @Override
        public String toString() {
            return "Task " + id +" {" +
                    "m1=" + m1 +
                    ", m2=" + m2 +
                    ", m3=" + m3 +
                    ", t1=" + t1 +
                    ", t2=" + t2 +
                    '}';
        }

        public int getM1() {
            return m1;
        }

        public void setM1(int m1) {
            this.m1 = m1;
        }

        public int getM2() {
            return m2;
        }

        public void setM2(int m2) {
            this.m2 = m2;
        }

        public int getM3() {
            return m3;
        }

        public void setM3(int m3) {
            this.m3 = m3;
        }

        public int getT1() {
            return t1;
        }

        public void setT1(int t1) {
            this.t1 = t1;
        }

        public int getT2() {
            return t2;
        }

        public void setT2(int t2) {
            this.t2 = t2;
        }
    }

    private boolean idSecondMachineDominated(List<int[]> machines) {
        for (int i = 0; i < machines.get(0).length; ++i) {
            if (machines.get(0)[i] < machines.get(1)[i] || machines.get(0)[i] < machines.get(2)[i]) {
                throw new UnsupportedOperationException("Second machine is not dominated");
            }
        }
        return false;
    }
}
