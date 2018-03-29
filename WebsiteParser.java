import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class WebsiteParser {
	public static void main(String[] args) throws Exception {
		Document document = Jsoup.connect("http://pk.ulstu.ru/index.php?nav=news").get();
		
		Elements elements = document.select("li");
		
		for(Element element : elements ) {
			System.out.println(element);
		}
	
	}
}
