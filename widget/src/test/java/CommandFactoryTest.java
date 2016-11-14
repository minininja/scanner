import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.CommandFactory;
import org.dorkmaster.scanner.command.ScanCmd;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class CommandFactoryTest {

    @Test
    public void test() {
        ScanCmd start = new ScanCmd("c:\\tmp", Arrays.asList(new Integer[]{1024}));
        byte[] buf = CommandFactory.serialize(start);
        Command end = CommandFactory.deserialize(buf);

        Assert.assertEquals(start, end);
    }
}
