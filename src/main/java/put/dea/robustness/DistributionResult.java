package put.dea.robustness;

import joinery.DataFrame;

import java.util.List;

/**
 * record representing the result of distribution and expected values
 *
 * @param distribution   values distribution
 * @param expectedValues expected values
 */
public record DistributionResult(DataFrame<Double> distribution,
                                 List<Double> expectedValues) {
}
