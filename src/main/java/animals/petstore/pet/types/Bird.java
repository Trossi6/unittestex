package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

/**
 * Bird attributes
 */
public class Bird extends Pet implements PetImpl {

    /* Properties */
    private int numberOfLegs;
    private Breed breed;
    private boolean canFly;

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it domesticated or wild bird
     * @param skinType The {@link Skin} of the bird
     * @param gender The {@link Gender} of the bird
     * @param breed The type of bird {@link Breed}
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed)
    {
        this(animalType, skinType, gender, breed, new BigDecimal(0));
    }

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it domesticated or wild bird
     * @param skinType The {@link Skin} of the bird
     * @param gender The {@link Gender} of the bird
     * @param breed The type of bird {@link Breed}
     * @param cost The cost of the bird
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost)
    {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it domesticated or wild bird
     * @param skinType The {@link Skin} of the bird
     * @param gender The {@link Gender} of the bird
     * @param breed The type of bird {@link Breed}
     * @param cost The cost of the bird
     * @param petStoreId The pet store id
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost, int petStoreId)
    {
        super(PetType.BIRD, cost, gender, petStoreId);
        super.skinType = skinType;
        super.animalType = animalType;
        this.numberOfLegs = 2;
        this.breed = breed;
        this.canFly = true; // default assumption
    }

    public String birdHypoallergenic() {
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "bird");
    }


    public String speak() {
        String language;
        switch (animalType) {
            case DOMESTIC:
                language = "The bird goes tweet! tweet!";
                break;
            case WILD:
                language = "The bird goes squawk! squawk!";
                break;
            default:
                language = "The bird goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
                break;
        }
        return language;
    }

    private String numberOfLegs() {
        return "Birds have " + numberOfLegs + " legs!";
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public Skin getSkinType() {
        return this.skinType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Breed getBreed() {
        return this.breed;
    }

    public AnimalType getAnimalType() {
        return super.animalType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "The bird is " + this.animalType + "!\n" +
                "The bird breed is " + this.getBreed() + "!\n" +
                this.birdHypoallergenic() + "!\n" +
                this.speak() + "\n" +
                this.numberOfLegs() + "\n" +
                (canFly ? "This bird can fly!" : "This bird cannot fly!");
    }
}
