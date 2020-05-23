package com.example.qrscanner;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser {

    private String parsing_string;

    public XmlParser() {
    }

    public XmlParser(String parsing_string) {
        this.parsing_string = parsing_string;
    }

    public List<String> parse_string() {

        final List<String> parsed_strings = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Здесь мы определили анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {
                // Поле для указания, что тэг NAME начался
                boolean title = false;
                boolean vendor_code = false;

                // Метод вызывается когда SAXParser "натыкается" на начало тэга
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    // Если тэг имеет имя NAME, то мы этот момент отмечаем - начался тэг NAME
                    if (qName.equalsIgnoreCase("TITLE")) {
                        title = true;
                    }
                    if (qName.equalsIgnoreCase("VENDOR_CODE")) {
                        vendor_code = true;
                    }
                }

                // Метод вызывается когда SAXParser считывает текст между тэгами
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    // Если перед этим мы отметили, что имя тэга NAME - значит нам надо текст использовать.
                    System.out.println(new String(ch, start, length));
                    if (title) {
                        parsed_strings.add(new String(ch, start, length));
                        title = false;
                    }
                    if (vendor_code) {
                        parsed_strings.add(new String(ch, start, length));
                        vendor_code = false;
                    }
                }
            };

            // Стартуем разбор методом parse, которому передаем наследника от DefaultHandler, который будет вызываться в нужные моменты
            InputSource is = new InputSource(new StringReader(this.parsing_string));
            saxParser.parse(is, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (parsed_strings.size() == 2) {
            return parsed_strings;
        }
        else {
            return null;
        }
    }

}
