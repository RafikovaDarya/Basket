import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    public static int[] prices = {100, 200, 300};

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        LoadFromXML fromXML = new LoadFromXML(new File("shop.xml"));
        fromXML.readXML();
        File loadFile = new File(fromXML.loadFile);
        File saveFile = new File(fromXML.saveFile);
        File logFile = new File(fromXML.logFile);

        ClientLog clientlog = new ClientLog();


        Basket basket = fillBasket(loadFile, fromXML.loadEnable, fromXML.loadFormat);

        basket.printCart();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                System.out.println("Ваша корзина: ");
                basket.amountOfProducts();
                if (fromXML.logEnable) {
                    clientlog.exportAsCSV(logFile);
                }
                break;
            }

            String[] parts = input.split(" ");
            int productNum = Integer.parseInt(parts[0]) - 1;
            int amount = Integer.parseInt(parts[1]);

            basket.addToCart(productNum, amount);

            if (fromXML.logEnable) {
                clientlog.log(productNum, amount);
            }

            if (fromXML.saveEnable) {
                if (fromXML.saveFormat.equals("json")) {
                    basket.saveJSON(saveFile);
                }
                if (fromXML.saveFormat.equals("txt")) {
                    basket.saveTxt(saveFile);
                }
            }
        }
    }

    private static Basket fillBasket(File loadFile, boolean loadEnable, String loadFormat) {
        Basket basket;
        if (loadFile.exists() && loadEnable) {
            basket = loadFormat.equals("json") ? Basket.loadFromJSONFile(loadFile)
                    : Basket.loadFromTxtFile(loadFile);
        } else {
            basket = new Basket(products, prices);
        }
        return basket;
    }


}