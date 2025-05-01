package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest
{
    private static PetStore petStore;

    @BeforeEach
    public void loadThePetStoreInventory()
    {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory()
    {
        assertEquals(5, petStore.getPetsForSale().size(),"Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest()
    {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle Remove Item Test")
    public void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        petStore.soldPetItem(poodle);
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
    }

    @Test
    @DisplayName("Poodle Duplicate Record Exception Test")
    public void poodleDupRecordExceptionTest() {
        petStore.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        String expectedMessage = "Duplicate Dog record store id [1]";
        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.soldPetItem(poodle);});
        assertEquals(expectedMessage, exception.getMessage(), "DuplicateRecordExceptionTest was NOT encountered!");

    }

    @Test
    @DisplayName("Sale of Sphynx Remove Item Test")
    public void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
        assertEquals(sphynx.getPetStoreId(), removedItem.getPetStoreId(), "The cat items are identical");
    }

    /**
     * Limitations to test factory as it does not instantiate before all
     * @return list of {@link DynamicNode} that contains the test results
     * @throws DuplicatePetStoreRecordException if duplicate pet record is found
     * @throws PetNotFoundSaleException if pet is not found
     */
    @TestFactory
    @DisplayName("Sale of Sphynx Remove Item Test2")
    public Stream<DynamicNode> sphynxSoldTest2() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        List<DynamicNode> nodes = new ArrayList<>();
        List<DynamicTest> dynamicTests = Arrays.asList(
                dynamicTest("Inventory Check Size Test ", () -> assertEquals(inventorySize,
                        petStore.getPetsForSale().size())),
                dynamicTest("The cat objects match ", () -> assertEquals(sphynx.toString(),
                        removedItem.toString()))
                );
        nodes.add(dynamicContainer("Cat Item 2 Test", dynamicTests));//dynamicNode("", dynamicContainers);

        return nodes.stream();
    }

    /**
     * Example of parameterized test
     * @param number to be tested
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE}) // six numbers
    void isNumberEven(int number)
    {
        assertTrue(Numbers.isEven(number));
    }

    @Test
    void testPetConstructorWithAllArguments() {
        PetType petType = PetType.CAT;
        BigDecimal cost = new BigDecimal("100.00");
        Gender gender = Gender.FEMALE;
        int petStoreId = 123;

        Pet pet = new Pet(petType, cost, gender, petStoreId);

        assertEquals(petType, pet.getPetType());
        assertEquals(cost, pet.getCost());
        assertEquals(gender, pet.getGender());
        assertEquals(petStoreId, pet.getPetStoreId());
    }

    @Test
    void testPetToStringWithPetStoreId() {
        Pet pet = new Pet(PetType.DOG, new BigDecimal("200.00"), Gender.MALE, 1);
        String result = pet.toString();
        assertTrue(result.contains("The DOG pet store id is 1"));
    }

    @Test
    void testPetToStringWithoutPetStoreId() {
        Pet pet = new Pet(PetType.CAT, new BigDecimal("100.00"), Gender.FEMALE);
        String result = pet.toString();
        assertTrue(result.contains("The type of pet is CAT"));
    }

    @Test
    void testGetters() {
        Pet pet = new Pet(PetType.DOG, new BigDecimal("150.00"), Gender.MALE);

        assertEquals(new BigDecimal("150.00"), pet.getCost());
        assertEquals(PetType.DOG, pet.getPetType());
        assertEquals(Gender.MALE, pet.getGender());
    }

    @Test
    void testPetHypoallergenic() {
        Pet pet = new Pet(PetType.CAT, new BigDecimal("50.00"), Gender.FEMALE);

        assertEquals("The pet is not hyperallergetic!", pet.petHypoallergenic(Skin.FUR));
        assertEquals("The pet is hyperallergetic!", pet.petHypoallergenic(Skin.HAIR));
        assertEquals("The pet skin is UNKNOWN at this time, so cannot determine if pet is hypoallergetic!", pet.petHypoallergenic(Skin.UNKNOWN));
    }

    @Test
    public void testPetsSold() {
        PetStore store = new PetStore();
        store.init();

        int initialSoldCount = store.getPetsSold().size();

        Pet pet = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE, new BigDecimal("750.00"), 3);

        store.addPetInventoryItem(pet);

        try {
            store.soldPetItem(pet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(initialSoldCount + 1, store.getPetsSold().size());
    }

    @Test
    public void testPetNotFoundSaleExceptionCanBeThrown() {
        String errorMessage = "Pet not found in the store";

        // Simulate throwing the exception
        PetNotFoundSaleException exception = assertThrows(PetNotFoundSaleException.class, () -> {
            throw new PetNotFoundSaleException(errorMessage);
        });

        // Check if the exception message is correct
        assertEquals(errorMessage, exception.getMessage(), "Error message should match");
    }


    @Test
    void testCatSpeak() {
        Cat cat = new Cat(AnimalType.DOMESTIC, Skin.FUR, Gender.FEMALE, Breed.SIAMESE);

        assertEquals("The cat goes prr! prr!", cat.speak());
    }

    @Test
    void testCatHypoallergenic() {
        Cat cat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.SIAMESE);

        assertEquals("The cat is hyperallergetic!", cat.catHypoallergenic());
    }

    @Test
    void testDogSpeak() {
        Dog dog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE);

        assertEquals("The dog goes woof! woof!", dog.speak());
    }

    @Test
    void testDogHypoallergenic() {
        Dog dog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE);

        assertEquals("The dog is not hyperallergetic!", dog.dogHypoallergenic());
    }

    @Test
    public void testBirdCreationAndBehavior() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.FEMALE,
                Breed.PARROT,
                new BigDecimal("99.99"),
                1
        );

        // Verify basic attributes
        assertEquals(Breed.PARROT, bird.getBreed());
        assertEquals(AnimalType.DOMESTIC, bird.getAnimalType());
        assertEquals(Skin.FEATHERS, bird.getSkinType());
        assertEquals(2, bird.getNumberOfLegs());
        assertEquals("The bird goes tweet! tweet!", bird.speak());

        // Test hypoallergenic output
        assertTrue(bird.birdHypoallergenic().contains("bird"));

        // Test string output includes expected parts
        String description = bird.toString();
        assertTrue(description.contains("bird is DOMESTIC"));
        assertTrue(description.contains("breed is PARROT"));
        assertTrue(description.contains("tweet! tweet!"));
        assertTrue(description.contains("Birds have 2 legs"));
    }


}

