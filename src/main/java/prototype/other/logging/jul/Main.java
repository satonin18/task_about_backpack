package prototype.other.logging.jul;
//TODO: J.U.L. //VM options:-Djava.util.logging.config.file=C:\JavaRushTasks\4.JavaCollections\src\com\javarush\task\task34\logging\jul\logging.properties
import java.util.logging.*;

public class Main {
//    private static final Logger logger = Logger.getGlobal();
    private static final Logger logger = Logger.getLogger(Main.class.getName() );

    public static void main(String[] args) throws Exception {
        //AFTER inklude VM main comand string
//        -Djava.util.logging.config.file=C:\JavaRushTasks\4.JavaCollections\src\com\javarush\task\task34\logging\jul\logging.properties
        LogManager.getLogManager().readConfiguration();
        System.out.println(System.getProperty("java.util.logging.config.file"));
        logger.log(Level.FINER, "finer");
        logger.log(Level.INFO , "info");
        logger.log(Level.WARNING , "WARNING", new Throwable());

//        Handler fileHandler = new FileHandler("%h/myJavaLog.log");
        // PATH ('/'or'\') SEE TO UNIX SYSTEM
//        Handler fileHandler = new FileHandler("C:/JavaRushTasks/logs/myJavaLog");
        //OR I WRITE IN CONFIG PROPRTY setting
        Handler fileHandler = new FileHandler();
        logger.addHandler(fileHandler);

//        fileHandler.setFilter(new MyFilter()); //only 'max'
//        fileHandler.setFormatter(new MyFormatter()); //NO XM: FORMAT
        logger.setUseParentHandlers(false);

        logger.info("info000");
        logger.info("infoMMM max");
    }
    static class MyFilter implements Filter {
        @Override
        public boolean isLoggable (LogRecord record) {
            return record.getMessage().endsWith("max");
        }
    }
    static class MyFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getLevel() + ": " + record.getMessage();
        }
    }
}