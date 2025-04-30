// Production Code
package animals.petstore.store;

public class PetNotFoundSaleException extends Exception {
    public PetNotFoundSaleException(String errorMessage) {
        super(errorMessage);
    }
}
