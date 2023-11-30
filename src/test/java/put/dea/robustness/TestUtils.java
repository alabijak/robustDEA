package put.dea.robustness;

import tech.tablesaw.api.Table;

public class TestUtils {
    public static double[][] transposeArray(double[][] array) {
        var result = new double[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[j][i] = array[i][j];
            }
        }
        return result;
    }

    public static double[][] tranformTableToArray(Table table) {
        var result = new double[table.rowCount()][table.columnCount()];
        for (int i = 0; i < table.rowCount(); i++) {
            var row = table.row(i);
            for (int j = 0; j < row.columnCount(); j++) {
                result[i][j] = row.getDouble(j);
            }
        }
        return result;
    }
}
