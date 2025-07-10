package io.github.up2jakarta.job.core;

@SuppressWarnings("unused")
public abstract class MatrixPrinter {

    private static void printf(final int[] maxArray, String... dataArray) {
        System.out.print("|");
        for (var i = 0; i < maxArray.length; i++) {
            System.out.printf(" %-" + maxArray[i] + "s |", dataArray[i]);
        }
        System.out.println();
    }

    private static void printf(final int[] maxArray, boolean border) {
        final String sep = border ? "Ξ" : "-";
        System.out.print("|");
        for (var max : maxArray) {
            System.out.printf("%s|", sep.repeat(max + 2));
        }
        System.out.println();
    }

    public static void printf(final String[][] stats) {
        final int[] maxArray = new int[stats[0].length];
        for (final String[] input : stats) {
            for (var j = 0; j < input.length; j++) {
                if (input[j] == null) {
                    input[j] = "";
                }
                maxArray[j] = Math.max(maxArray[j], input[j].length());
            }
        }
        for (var i = 0; i < stats.length; i++) {
            printf(maxArray, i == 0);
            printf(maxArray, stats[i]);
        }
        printf(maxArray, true);
    }
}
