package test;

import java.nio.file.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.tike.utils.Tools;

import abner.*;

public class abnerTest {
	public static void main(String[] args) throws Exception {
		

		Tagger tagger = new Tagger();
		String html = tagger.tagSGML("ACIN1 gene");
		System.out.println(html);

		Path path = Paths.get("H:\\keywords.txt");
		String text = new String(Files.readAllBytes(path));
		html = tagger.tagSGML(text);
		Tools.writeFile("H:\\keywords.txt.out", html);

		// String html = "<html><head><title>First parsetitle>head>"
		// + "<body><p>Parsed HTML into a doc.p>body>html>";
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("PROTEIN");
		Element head = doc.select("#head").first();

		for (int i = 0; i < links.size(); i++) {
			System.out.println(links.get(i).html());

		}
	}

}