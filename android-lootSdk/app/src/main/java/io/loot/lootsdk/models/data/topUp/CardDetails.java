package io.loot.lootsdk.models.data.topUp;

public class CardDetails {

    private String cardNumber;
    private String cvv;
    private int expiryMonth;
    private int expiryYear;

    public CardDetails(String cardNumber, int expiryMonth, int expiryYear, String cvv) {
        this.setCardNumber(cardNumber);
        this.setCvv(cvv);
        this.setExpiryMonth(expiryMonth);
        this.setExpiryYear(expiryYear);
    }

    public CardDetails() {
        this.cardNumber = "";
        this.cvv = "";
        this.expiryMonth = 0;
        this.expiryYear = 0;
    }

    public CardDetails(CardDetails cardDetails) {
        this.cardNumber = cardDetails.getCardNumber();
        this.cvv = cardDetails.getCvv();
        this.expiryMonth = cardDetails.getExpiryMonth();
        this.expiryYear = cardDetails.getExpiryYear();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {

        if (cardNumber == null) {
            cardNumber = "";
        }
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        if (cvv == null) {
            cvv = "";
        }
        this.cvv = cvv;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }
}
