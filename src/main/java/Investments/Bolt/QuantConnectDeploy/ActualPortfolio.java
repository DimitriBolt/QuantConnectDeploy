package Investments.Bolt.QuantConnectDeploy;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

class ActualPortfolio {
    // static Class variables
    // private Instance variable
    private HashMap<String, Float> actualPortfolio;
    // Initializer block
    // Constructors
    public ActualPortfolio(File iFile, ActualPrediction actualPrediction, NexTradingDay nexTradingDay) {

        var allPredictionS = actualPrediction.getPredictedTickers();
        var nextDayPrediction = allPredictionS.get(nexTradingDay.getNextTradingDay());

        ShortNextDayPortfolio shortNextDayPortfolio = new ShortNextDayPortfolio(nextDayPrediction);

        TickerChooser tickerChooser = new TickerChooser(iFile);

        WideNextDayPortfolio wideNextDayPortfolio = new WideNextDayPortfolio(shortNextDayPortfolio, tickerChooser);
        actualPortfolio = wideNextDayPortfolio.getWidePortfolio();
    }
    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    public HashMap<String, Float> getActualPortfolio() {
        return actualPortfolio;
    }
}
