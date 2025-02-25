package org.example.parser;

import org.example.exceptions.DeserializationException;
import org.example.models.Coordinates;
import org.example.models.Label;
import org.example.models.MusicBand;
import org.example.models.MusicGenre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * CsvParser - класс парсера для формата CSV.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class CsvParser {

    /**
     * Метод для получения сериализованной коллекции в виде строки
     * @param collection коллекция элементов MusicBand
     */
    public static String serialize(Collection<MusicBand> collection) {
        String output = "";

        for(var mb : collection) {
            output += String.format("%d;", mb.getId());
            output += mb.getName() + ";";
            output += String.format("%d;%d;", mb.getCoordinates().getX(), mb.getCoordinates().getY());
            output += parseDateToString(mb.getCreationDate()) + ";";
            output += String.format("%d;%d;", mb.getNumberOfParticipants(), mb.getAlbumsCount());
            output += mb.getGenre().toString() + ";";
            output += String.format("%.8f;\n", mb.getLabel().getSales());
        }

        return output;
    }

    /**
     * Метод для получения коллекции из файла формата csv
     * @param input данные из файла
     */
    public static Collection<MusicBand> deserialize(String input) throws DeserializationException {
        LinkedList<MusicBand> deserializedCollection = new LinkedList<>();

        for(var line : input.trim().split("\n")) {
            if(line.isEmpty()) {
                continue;
            }
            try {
                MusicBand musicBand = parseLine(line);

                deserializedCollection.add(musicBand);
            } catch (Exception e) {
                throw new DeserializationException("Не удалось десериализовать коллекцию");
            }
        }

        return deserializedCollection;
    }

    /**
     * Метод для парсинга строки из файла
     */
    private static MusicBand parseLine(String line) throws ParseException {
        String[] parts = line.trim().split(";");

        if(parts.length != 9) {
            throw new DeserializationException("Не удалось сериализовать коллекцию");
        }


        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1];
        Integer x = Integer.valueOf(parts[2]);
        long y = Long.parseLong(parts[3]);
        Date date = parseStringDate(parts[4]);
        Long numberOfParticipants = Long.valueOf(parts[5]);
        long albumCounts = Long.parseLong(parts[6]);
        MusicGenre genre = MusicGenre.valueOf(parts[7]);
        double sales = Double.parseDouble(parts[8].replace(',', '.'));

        Label label = new Label(sales);
        Coordinates coordinates = new Coordinates(x, y);

        MusicBand musicBand = new MusicBand(id, name, coordinates, date, numberOfParticipants, albumCounts, genre, label);

        return musicBand;
    }

    /**
     * Метод для получения даты из строки
     */
    private static Date parseStringDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.parse(date);
    }

    /**
     * Метод для получения строки формата dd-MM-yyyy из даты
     */
    private static String parseDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
