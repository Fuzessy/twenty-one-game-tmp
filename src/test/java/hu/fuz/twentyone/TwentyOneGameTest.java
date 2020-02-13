package hu.fuz.twentyone;

import hu.fuz.twentyone.model.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwentyOneGameTest {

    @Test(expected = CardIsNullException.class)
    public void nullCardTest(){
        CardEvaluator cardEvaluator = new CardEvaluator();
        cardEvaluator.evaluate(null);
    }

    @Test(expected = CardRankIsNullException.class)
    public void nullRankOfCardTest(){
        CardEvaluator cardEvaluator = new CardEvaluator();
        cardEvaluator.evaluate(new Card(null));
    }

    @Test
    public void evaluateCardTest(){
        CardEvaluator cardEvaluator = new CardEvaluator();
        assertEquals(2,cardEvaluator.evaluate(new Card(CardRank.UNTER)));
        assertEquals(3,cardEvaluator.evaluate(new Card(CardRank.OBER)));
        assertEquals(4,cardEvaluator.evaluate(new Card(CardRank.KING)));
        assertEquals(7,cardEvaluator.evaluate(new Card(CardRank.SEVEN)));
        assertEquals(8,cardEvaluator.evaluate(new Card(CardRank.EIGHT)));
        assertEquals(9,cardEvaluator.evaluate(new Card(CardRank.NINE)));
        assertEquals(10,cardEvaluator.evaluate(new Card(CardRank.TEN)));
        assertEquals(11,cardEvaluator.evaluate(new Card(CardRank.ACE)));
    }

}
