package model.services;

public class BrazilTaxService implements TaxService {
  
  @Override
  public double tax(double amount) {
    double tax = amount <= 100 ? 0.2 : 0.15;
    return amount * tax;
  }
}
