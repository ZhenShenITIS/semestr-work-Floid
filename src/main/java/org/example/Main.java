package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.min;

public class Main {
    public static List<String> list = new ArrayList<>();
    public static List<Integer[][]> listOfArrays = new ArrayList<>();
    public static List<Integer> iterationCount = new ArrayList<>();
    public static List<Long> leadTime = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {
        writeToFile();
        readFromFile();
        writeToListOfArrays(list);
        for (Integer[][] listOfArray : listOfArrays) {
            floid(listOfArray);
        }
        writeResults();
        System.out.println(iterationCount);
        System.out.println(leadTime);
    }
    public static void writeToFile(){
        try {
            FileWriter writer = new FileWriter("example.txt",false);
            for (int i = 10; i < 101; i++){
                writer.write(createArray(i));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Ошибка при записи в файл");
        }
    }
    public static void readFromFile() throws FileNotFoundException {
        Scanner s = new Scanner(new File("example.txt"));
        while (s.hasNext()){
            list.add(s.nextLine());
        }
        s.close();
    }
    public static void writeResults(){
        try {
            FileWriter writer = new FileWriter("graphicInfo.txt",false);
            for (int i = 0; i < 91; i++){
                writer.write(iterationCount.get(i) + " " + leadTime.get(i) + " " + i);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Ошибка при записи в файл");
        }
        System.out.println(iterationCount);
        System.out.println(leadTime);
    }
    public static void writeToListOfArrays(List<String> list){
        for (String string : list) {
            listOfArrays.add(convert(string));
        }
    }
    public static Integer[][] convert(String str){
        String[] stringArray = str.split(";");
        Integer[][] integers = new Integer[stringArray.length][];
        for (int i = 0; i < stringArray.length; i++){
            String[] line = stringArray[i].split(" ");
            integers[i] = new Integer[line.length];
            for (int k = 0; k < line.length; k++){
                integers[i][k] = Integer.valueOf(line[k]);
            }
        }
        return integers;
    }

    public static String createArray(int n){
        StringBuilder array = new StringBuilder();
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                if (i == j) {
                    if (j == n - 1){
                        array.append(0);
                    } else array.append(0).append(" ");
                } else if (i == n - 1){
                    array.append(get_weight());
                }
                else array.append(get_weight()).append(" ");
            }
            array.append(";");
        }
        return array.toString();
    }

    public static int get_weight(){
        int[] array = new int[51];
        double[] probabilities = new double[51]; // Вероятности выбора соответствующих элементов

        for (int i = 0; i < 50; i++){
            array[i] = i+1;
        }
        array[50] = Integer.MAX_VALUE;

        for (int i = 0; i < 50; i++){
            probabilities[i] = 0.018;
        }
        probabilities[50] = 0.1;
        Random random = new Random();
        double randomValue = random.nextDouble(); // Генерация случайной вероятности от 0 до 1
        double cumulativeProbability = 0;
        int selectedElement = -1;

        for (int i = 0; i < array.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                selectedElement = array[i]; // Выбор элемента из массива с учетом вероятности
                break;
            }
        }
        return selectedElement;
    }
    public static String toString(List<Integer[][]> listOfArrays) {
        StringBuilder sb = new StringBuilder();
        for (Integer[][] array : listOfArrays) {
            for (Integer[] row : array) {
                for (Integer num : row) {
                    sb.append(num).append(" ");
                }
                sb.append("; ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public static void floid(Integer[][] d){
        int iteration_count = 0;
        long start = System.nanoTime();
        int n = d.length;



        for (int k = 0; k < n; k++){
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    d[i][j] = min(d[i][j], d[i][k] + d[k][j]);
                    iteration_count++;
                }
            }
        }



        long end = System.nanoTime();
        iterationCount.add(iteration_count);
        leadTime.add(end - start);
    }
}