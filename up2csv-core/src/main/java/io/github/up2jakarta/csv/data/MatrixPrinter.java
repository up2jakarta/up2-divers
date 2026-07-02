package io.github.up2jakarta.csv.data;

import java.io.PrintStream;

/**
 * Pretty Printer for matrix (2D array) with alignment of cells.
 *
 * @see #print(PrintStream, String[][])
 */
public abstract class MatrixPrinter {

    /**
     * Console Printer
     */
    public static final MatrixPrinter CONSOLE = new MatrixPrinter() {
        @Override
        public void print(PrintStream printer, String[][] data) {
            final int[] size = length(data);
            printf(printer, size, 'Ξ');
            printf(printer, size, data[0]);
            for (var i = 1; i < data.length; i++) {
                printf(printer, size, '-');
                printf(printer, size, data[i]);
            }
            printf(printer, size, 'Ξ');
        }
    };

    /**
     * Markdown Printer
     */
    public static final MatrixPrinter MARKDOWN = new MatrixPrinter() {
        @Override
        public void print(PrintStream printer, String[][] data) {
            final int[] size = length(data);
            printf(printer, size, data[0]);
            printf(printer, size, '-');
            for (var i = 1; i < data.length; i++) {
                printf(printer, size, data[i]);
            }
        }
    };

    private static void printf(PrintStream printer, final int[] size, String... data) {
        printer.print("|");
        for (var i = 0; i < size.length; i++) {
            printer.printf(" %-" + size[i] + "s |", data[i]);
        }
        printer.println();
    }

    private static void printf(PrintStream printer, final int[] size, char border) {
        final String br = String.valueOf(border);
        printer.print('|');
        for (var max : size) {
            printer.printf(br.repeat(max + 2));
            printer.print('|');
        }
        printer.println();
    }

    private static int[] length(String[][] data) {
        final int[] size = new int[data[0].length];
        for (final String[] input : data) {
            for (var j = 0; j < input.length; j++) {
                if (input[j] == null) {
                    input[j] = " ";
                }
                size[j] = Math.max(size[j], input[j].length());
            }
        }
        return size;
    }

    /**
     * Prints the specified <code>data</code> to the output <code>printer</code>
     *
     * @param printer the print stream
     * @param data    the matrix to print
     */
    public abstract void print(PrintStream printer, String[][] data);
}
