package tests;

import animals.AnimalType;
import animals.petstore.pet.types.Bird;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BirdTests {

    @Test
    public void testBasicConstructor() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.PARROT);

        assertEquals(AnimalType.DOMESTIC, bird.getAnimalType());
        assertEquals(Skin.FEATHERS, bird.getSkinType());
        assertEquals(Gender.FEMALE, bird.getGender());
        assertEquals(Breed.PARROT, bird.getBreed());
        assertEquals(0, bird.getCost().compareTo(BigDecimal.ZERO));
        assertEquals(2, bird.getNumberOfLegs());
        assertTrue(bird.canFly());
    }

    @Test
    public void testFullConstructorAndSetters() {
        Bird bird = new Bird(AnimalType.WILD, Skin.FEATHERS, Gender.MALE, Breed.EAGLE, new BigDecimal("199.99"), 101);

        assertEquals(new BigDecimal("199.99"), bird.getCost());
        assertEquals(101, bird.getPetStoreId());

        bird.setNumberOfLegs(1);
        bird.setCanFly(false);

        assertEquals(1, bird.getNumberOfLegs());
        assertFalse(bird.canFly());
    }

    @Test
    public void testSpeakDomestic() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.MALE, Breed.PARROT);
        assertEquals("The bird goes tweet! tweet!", bird.speak());
    }

    @Test
    public void testSpeakWild() {
        Bird bird = new Bird(AnimalType.WILD, Skin.FEATHERS, Gender.FEMALE, Breed.EAGLE);
        assertEquals("The bird goes squawk! squawk!", bird.speak());
    }

    @Test
    public void testHypoallergenic() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.PARROT);
        assertTrue(bird.birdHypoallergenic().toLowerCase().contains("bird"));
    }

    @Test
    public void testToStringIncludesFields() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.PARROT);
        String output = bird.toString();

        assertTrue(output.contains("bird is DOMESTIC"));
        assertTrue(output.contains("bird breed is"));
        assertTrue(output.contains("bird goes tweet"));
        assertTrue(output.contains("Birds have 2 legs"));
    }
}