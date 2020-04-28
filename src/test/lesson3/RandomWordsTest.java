package lesson3;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class RandomWordsTest {

    @Test
    public void testCreateList() throws IOException {
        int listSize = 25;
        List<String> list = new RandomWords().createList(listSize);
        Assert.assertEquals(list.size(), listSize, "Size for new list is equals to expected");
    }
}