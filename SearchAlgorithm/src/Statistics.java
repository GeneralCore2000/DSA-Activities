public class Statistics {
    public long[] timeArray;

    public Statistics(long[] timeArray) {
        this.timeArray = timeArray;
    }

    public void summarizeStats() {
        System.out.println("\n" + "=".repeat(10) + " STATISTICS " + "=".repeat(10));
        System.out.println("Search total time: " + getTotalTime() + "ns (" + getTotalTime() / 1_000_000.0 + "ms)");
        System.out.println("Slowest time: " + getSlowest() + "ns (" + getSlowest() / 1_000.0 + "μs)" + " [" + getSlowestIndex() + "]");
        System.out.println("Fastest time: " + getFastest() + "ns (" + getFastest() / 1_000.0 + "μs)" + " [" + getFastestIndex() + "]");
        System.out.println("Average time: " + getTimeAverage() + "ns (" + (getTimeAverage() / 1_000_000.0) + "ms)");
        System.out.println("=".repeat(32) + "\n");
    }

    public void summarizeStats(long sortTotalTime) {
        long searchTotalTime = getTotalTime();
        long combinedTime = searchTotalTime + sortTotalTime;

        System.out.println("\n" + "=".repeat(10) + " STATISTICS " + "=".repeat(10));
        System.out.println("Sort total time: " + sortTotalTime + "ns (" + sortTotalTime / 1_000_000.0 + "ms)");
        System.out.println("Search total time: " + searchTotalTime + "ns (" + searchTotalTime / 1_000_000.0 + "ms)");
        System.out.println("Combined total time (sort + search): " + combinedTime + " ns (" + (combinedTime / 1_000_000.0) + "ms)");
        System.out.println("Slowest time: " + getSlowest() + "ns (" + getSlowest() / 1_000.0 + "μs)" + " [" + getSlowestIndex() + "]");
        System.out.println("Fastest time: " + getFastest() + "ns (" + getFastest() / 1_000.0 + "μs)" + " [" + getFastestIndex() + "]");
        System.out.println("Average time: " + getTimeAverage() + "ns (" + (getTimeAverage() / 1_000_000.0) + "ms)");
        System.out.println("=".repeat(32) + "\n");
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
        return timeSum / timeArray.length;
    }

    public long getFastest() {
        long fastest = timeArray[0];
        for (int i = 1; i < timeArray.length; i++) {
            if (timeArray[i] < fastest) {
                fastest = timeArray[i];
            }
        }
        return fastest;
    }

    public long getFastestIndex() {
        long fastest = timeArray[0];
        long fastestIndex = 0;
        for (int i = 1; i < timeArray.length; i++) {
            if (timeArray[i] < fastest) {
                fastest = timeArray[i];
                fastestIndex = i;
            }
        }
        return fastestIndex + 1;
    }

    public long getSlowestIndex() {
        long slowest = timeArray[0];
        long slowestIndex = 0;
        for (int i = 1; i < timeArray.length; i++) {
            if (timeArray[i] > slowest) {
                slowest = timeArray[i];
                slowestIndex = i;
            }
        }
        return slowestIndex + 1;
    }

    public long getSlowest() {
        long slowest = timeArray[0];
        for (int i = 1; i < timeArray.length; i++) {
            if (timeArray[i] > slowest) {
                slowest = timeArray[i];
            }
        }
        return slowest;
    }
}
