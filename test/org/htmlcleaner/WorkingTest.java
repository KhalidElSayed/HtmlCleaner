package org.htmlcleaner;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.htmlcleaner.*;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;
import java.net.URL;

/**
 * Vladimir Nikic
 * Date: Apr 13, 2007
 */
public class WorkingTest {

    public static void main(String[] args) throws IOException, XPatherException, ParserConfigurationException {
        String html = "<script src=\"a\" type=\"text/javascript\" /><script src=\"b\" type=\"text/javascript\"/>";
        final HtmlCleaner cleaner = new HtmlCleaner();
        final CleanerProperties props = cleaner.getProperties();

//        props.setOmitUnknownTags(false);
//        props.setUseCdataForScriptAndStyle(true);
//        props.setRecognizeUnicodeChars(false);
//        props.setUseEmptyElementTags(true);
//        props.setAdvancedXmlEscape(true);
//        props.setTranslateSpecialEntities(true);
//        props.setBooleanAttributeValues("empty");
//        props.setNamespacesAware(false);

//        final String resources[] = {
//                "http://www.b92.net",
//                "http://www.nba.com",
//                "http://www.naslovi.net/",
//                "http://www.theserverside.com/",
//                "http://www.yahoo.com",
//        };
//        final String resources[] = {
//                "c:/temp/htmlcleanertest/1.htm",
//                "c:/temp/htmlcleanertest/2.htm",
//                "c:/temp/htmlcleanertest/3.htm",
//                "c:/temp/htmlcleanertest/4.htm",
//                "c:/temp/htmlcleanertest/5.htm",
//        };

        props.setTransResCharsToNCR(false);
        props.setIgnoreQuestAndExclam(true);
        props.setUseCdataForScriptAndStyle(true);
        props.setRecognizeUnicodeChars(true);
        props.setTranslateSpecialEntities(true);
        props.setOmitXmlDeclaration(true);
        final PrettyXmlSerializer prettySerializer = new PrettyXmlSerializer(props);
        final SimpleHtmlSerializer simpleHtmlSerializer = new SimpleHtmlSerializer(props);

        long start = System.currentTimeMillis();

        TagNode node = cleaner.clean(new File("c:/temp/htmlcleanertest/mama.html"), "UTF-8");
        node.traverse(new TagNodeVisitor() {
            int changesCount = 0;
            public boolean visit(TagNode tagNode) {
                if ("a".equals(tagNode.getName())) {
//                    tagNode.removeFromTree();
                    tagNode.setAttribute("href", "#" + tagNode.getAttributeByName("href") + "#");
                    changesCount++;
                }
                return changesCount <= 2;
            }
        });
        simpleHtmlSerializer.writeToFile(node, "c:/temp/htmlcleanertest/mamaout.html", "UTF-8");

//        for (int i = 0; i < resources.length; i++) {
//            TagNode node = cleaner.clean(new URL(resources[i]));
//            prettySerializer.writeXmlToFile(node, "c:/temp/htmlcleanertest/out/" + i + ".xml", "UTF-8");
//        }
//        System.out.println("Vreme u jednom tredu: " + (System.currentTimeMillis() - start));
//
//        final long start1 = System.currentTimeMillis();
//
//        for (int i = 0; i < resources.length; i++) {
//            final int index = i;
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        TagNode node = cleaner.clean(new URL(resources[index]));
//                        prettySerializer.writeXmlToFile(node, "c:/temp/htmlcleanertest/out/" + index + "a.xml", "UTF-8");
//                        System.out.println("Vreme u tredu " + index + ": " + (System.currentTimeMillis() - start1));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }

//        TagNode node = cleaner.clean(new File("c:/temp/test.html"));
//        prettySerializer.writeXmlToFile(node, "c:/temp/htmlcleanertest/1.xml", "utf-8");
    }

}