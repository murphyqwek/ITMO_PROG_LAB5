package org.example;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Использование: java -jar [имя_файла].jar <файл_существующей_коллекции> или <имя_нового_файла_коллекции>");
        }
        else {
            Run run = new Run(args[0]);
            run.run();
        }
    }
}