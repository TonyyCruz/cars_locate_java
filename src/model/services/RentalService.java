package model.services;

import java.time.Duration;
import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
  private Double pricePerHour;
  private Double pricePerDay;
  private BrazilTaxService taxService;

  public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
    this.pricePerHour = pricePerHour;
    this.pricePerDay = pricePerDay;
    this.taxService = taxService;
  }

  public void processInvoice(CarRental carRental) {
    double duration = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
    double hours = Math.ceil(duration / 60);
    double basePayment = baseRentalCalculate(hours);
    double tax = taxService.tax(basePayment);
    carRental.setInvoice(new Invoice(basePayment, tax));
  }

  private double baseRentalCalculate(double hours) {
    if (hours < 12) {
      return hours * pricePerHour;
    }
    if (hours < 24) {
      return pricePerDay;
    }
    double hoursAboveDay = hours % 24;
    if (hoursAboveDay > 12) {
      return Math.ceil(hours / 24) * pricePerDay;
    }
    return (Math.floor(hours / 24) * pricePerDay) + (hoursAboveDay * pricePerHour);
  }
}
