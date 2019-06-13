/*
Добавление логирования в класс
В Intellij IDEA Alt+Ctrl+Shift+S -> Global Libraries -> New Global Library -> From Maven...
В строке поиска в открывшемся окне укажи: org.slf4j:slf4j-api:1.7.23 -> Поиск (Shift+Enter)
Укажи куда скачать библиотеку логирования.
Выбрери к какому модулю проекта подключить библиотеку slf4j-api: нужно выбрать
4.JavaCollections -> OK
Apply -> OK.

Посмотри где бы ты в классе Solution применил какой уровень логирования?
В класс Solution нужно добавить вызовы методов уровня:
error - 1 раз;
debug - 6 раз - используй при изменениях значений полей класса;
trace - 4 раза - используй для отслеживания пути выполнения програмы;
Сообщения в логах старайся писать информативные.
Остальной код не изменяй.

Требования:
1. В классе Solution должно существовать приватное статическое финальное поле logger.
3. Добавь логирование уровня debug шесть раз.
4. Добавь логирование уровня trace четыре раза.
*/

package prototype.other.logging._slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        logger.debug("change value");
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static void main(String[] args) {
        new Solution(1,"two",new Date());
    }

    public void calculateAndSetValue3(long value) {
        value -= 133;
        if (value > Integer.MAX_VALUE) {
            logger.trace("");
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("change value");
        } else {
            value1 = (int) value;
            logger.debug("change value");
        }
    }

    public void printString() {
        if (value2 != null) {
            logger.trace("");
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        if (value3 != null) {
            logger.trace("");
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        try {
            logger.trace("");
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            logger.error("Err");
        }
    }

    public void setValue1(int value1) {
        this.value1 = value1;
        logger.debug("change value");
    }

    public void setValue2(String value2) {
        this.value2 = value2;
        logger.debug("change value");
    }

    public void setValue3(Date value3) {
        this.value3 = value3;
        logger.debug("change value");
    }
}