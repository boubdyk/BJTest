package bs.bj;

import bs.bj.service.CardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by boubdyk on 30.10.2015.
 */
public class Test {
    private static ApplicationContext context;
    private static CardService cardService;

    static {
        context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        cardService = context.getBean(CardService.class);
    }

    public static void main(String[] args) {
        System.out.println(cardService.addCard());
    }
}
