import java.util.ArrayList;

public class Statistics {
    private final ArrayList<Long> timeArray;
    private final String searchType; //"Linear", "Binary"
    private final String key;

    public Statistics(ArrayList<Long> timeArray, String searchType, String key) {
        this.timeArray = timeArray;
        this.searchType = searchType;
        this.key = key;
    }

    public String getSearchType() {
        return searchType;
    }

    public void summarizeStats() {
        System.out.println("Key entered: " + key);
        System.out.println("Search total time: " + getTotalTime() + "ns (" + getTotalTime() / 1_000_000.0 + "ms)");
        System.out.println("Slowest time: " + getSlowest() + "ns (" + getSlowest() / 1_000.0 + "μs)" + " [" + getSlowestIndex() + "]");
        System.out.println("Fastest time: " + getFastest() + "ns (" + getFastest() / 1_000.0 + "μs)" + " [" + getFastestIndex() + "]");
        System.out.println("Average time: " + getTimeAverage() + "ns (" + (getTimeAverage() / 1_000_000.0) + "ms)");
        System.out.println("=".repeat(32));
    }

    public void summarizeStats(long sortTotalTime) {
        long searchTotalTime = getTotalTime();
        long combinedTime = searchTotalTime + sortTotalTime;
        System.out.println("Sort total time: " + sortTotalTime + "ns (" + sortTotalTime / 1_000_000.0 + "ms)");
        System.out.println("Combined total time (sort + search): " + combinedTime + " ns (" + (combinedTime / 1_000_000.0) + "ms)");
        summarizeStats();
    }

    public long getTotalTime() {
        long timeSum = 0;
        for (long l : timeArray) {
            timeSum += l;
        }
        return timeSum;
    }

    public long getTimeAverage() {
        long timeSum = 0;
        for (long l : timeArray) {
            timeSum += l;
        }
        return timeSum / timeArray.size();
    }

    public long getFastest() {
        long fastest = timeArray.getFirst();
        for (int i = 1; i < timeArray.size(); i++) {
            if (timeArray.get(i) < fastest) {
                fastest = timeArray.get(i);
            }
        }
        return fastest;
    }

    public long getFastestIndex() {
        long fastest = timeArray.getFirst();
        long fastestIndex = 0;
        for (int i = 1; i < timeArray.size(); i++) {
            if (timeArray.get(i) < fastest) {
                fastest = timeArray.get(i);
                fastestIndex = i;
            }
        }
        return fastestIndex + 1;
    }

    public long getSlowestIndex() {
        long slowest = timeArray.getFirst();
        long slowestIndex = 0;
        for (int i = 1; i < timeArray.size(); i++) {
            if (timeArray.get(i) > slowest) {
                slowest = timeArray.get(i);
                slowestIndex = i;
            }
        }
        return slowestIndex + 1;
    }

    public long getSlowest() {
        long slowest = timeArray.getFirst();
        for (int i = 1; i < timeArray.size(); i++) {
            if (timeArray.get(i) > slowest) {
                slowest = timeArray.get(i);
            }
        }
        return slowest;
    }
}
