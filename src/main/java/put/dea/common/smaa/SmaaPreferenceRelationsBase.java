package put.dea.common.smaa;

import joinery.DataFrame;

import java.util.stream.IntStream;

public class SmaaPreferenceRelationsBase {
    private final int numberOfSamples;

    public SmaaPreferenceRelationsBase(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    public DataFrame<Double> calculatePeois(DataFrame<Double> efficiencies) {
        var peoi = new DataFrame<Double>(efficiencies.index().stream().map(Object::toString).toList());

        for (int dmuA = 0; dmuA < efficiencies.length(); dmuA++) {
            var efficienciesA = efficiencies.row(dmuA);
            var row = IntStream.range(0, efficiencies.length())
                    .mapToDouble(dmuB ->
                            IntStream.range(0, efficiencies.size())
                                    .filter(sampleIdx -> efficienciesA.get(sampleIdx) >= efficiencies.get(dmuB, sampleIdx))
                                    .count())
                    .map(count -> count / numberOfSamples)
                    .boxed()
                    .toList();
            peoi.append(row);
        }
        return peoi;

    }
}
