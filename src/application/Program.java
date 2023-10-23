package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import model.entities.CarRental;
import model.entities.Vehicle;

public class Program {

  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);
    System.out.println("Input the invoice data.");
    System.out.print("Car model: ");
    String carModel = sc.nextLine();
    System.out.print("Date of rented (DD/MM/YYYY HH:MM) : ");
    LocalDateTime startDate = formattedDate(sc);
    System.out.print("Date of return (DD/MM/YYYY HH:MM) : ");
    LocalDateTime endDate = formattedDate(sc);
    while (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
      System.out.println("The return date cannot be beforet he invoice date.");
      System.out.print("Please insert a valid return date (DD/MM/YYYY HH:MM): ");
      endDate = formattedDate(sc);
    }
    CarRental carRental = new CarRental(startDate, endDate, new Vehicle(carModel));

    System.out.println(carRental);
    sc.close();
  }

  private static LocalDateTime formattedDate(Scanner sc) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    String stringDate = sc.nextLine();
    while (!isValidDate(dateTimeFormatter, stringDate)) {
      System.out.println("Invalid date, please insert a future date in format (DD/MM/YYYY HH:MM)");
      System.out.print("Insert the date: ");
      stringDate = sc.nextLine();
    }
    return LocalDateTime.parse(stringDate, dateTimeFormatter);
  }

  private static boolean isValidDate(DateTimeFormatter dateTimeFormatter, String stringDate) {
    try {
      LocalDateTime date = LocalDateTime.parse(stringDate, dateTimeFormatter);
      LocalDateTime currentDate = LocalDateTime.now();
      return currentDate.isBefore(date);
    } catch (DateTimeParseException e) {
      return false;
    }
  }

}
