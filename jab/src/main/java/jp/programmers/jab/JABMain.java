package jp.programmers.jab;

import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.CmdLineParser;

public class JABMain {

    public static void main(String... args) throws Exception {
        JABOptions options = new JABOptions();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        if (options.getArguments().isEmpty()) {
            System.err.println("Example: JAB" + parser.printExample(ALL));
            System.exit(-1);
        }
        JAB jab = JABFactory.create(options.getType());
        jab.init(options);
        jab.execute();
    }
}
