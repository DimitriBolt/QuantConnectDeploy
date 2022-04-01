package Investments.Bolt.QuantConnectDeploy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class ShortNextDayPortfolio {


    // static Class variables
    // private Instance variable
    private HashMap<String, Float> cutPortfolio;

    // Initializer block
    {
        this.cutPortfolio = new HashMap<>();
    }

    // Constructors
    ShortNextDayPortfolio(HashMap<String, Integer> nextDayPrediction) {
        Collection<Integer> predictionValueS = nextDayPrediction.values();
        Integer sumMinus = predictionValueS.stream().filter(x -> x < 0).reduce((acc, x) -> acc + x).get();
        Integer sumPlus = nextDayPrediction.entrySet().stream().map(Map.Entry::getValue).filter(x -> x > 0).reduce(Integer::sum).get();
        // https://www.baeldung.com/java-maps-streams
        // https://javarush.ru/groups/posts/2203-stream-api
        nextDayPrediction.forEach((ticker, prediction) -> {
            float cutPortfolioValue = (prediction > 0) ? (float) prediction / sumPlus : (float) prediction / (sumMinus * -1);
            this.cutPortfolio.put(ticker, cutPortfolioValue);
        });
    }

    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    HashMap<String, Float> getCutPortfolio() {
        return cutPortfolio;
    }
}
