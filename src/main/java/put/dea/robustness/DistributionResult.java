package put.dea.robustness;

import tech.tablesaw.api.Table;

import java.util.List;

/**
 * record representing the result of distribution and expected values
 *
 * @param distribution   values distribution
 * @param expectedValues expected values
 */
public record DistributionResult(Table distribution,
                                 List<Double> expectedValues) {
}
