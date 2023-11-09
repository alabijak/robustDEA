package put.dea.common.smaa;

import joinery.DataFrame;

import java.util.List;

public record DistributionResult(DataFrame<Double> distribution,
                                 List<Double> expectedValues) {
}
