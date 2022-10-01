package test.data;

import enums.PetCategoryName;
import enums.PetStatus;
import enums.PetTagName;
import pojo.pet.Category;
import pojo.pet.Pet;
import pojo.pet.Tag;

import java.util.Collections;
import java.util.Random;

public class PetTestDataGenerator extends TestDataGenerator{

    public Pet generatePate() {

        Category category = new Category();
        PetCategoryName randomPetCategory = getRandomPetCategory();
        category.setId(randomPetCategory.getId());
        category.setName(randomPetCategory.getCategoryName());

        Tag tag = new Tag();
        PetTagName randomPetPetTagName = getRandomPetTagName();
        tag.setId(randomPetPetTagName.getId());
        tag.setName(randomPetPetTagName.getTagName());

        Pet pet = new Pet();
        PetStatus randomPetStatus = getRandomPetStatus();
        pet.setId(faker().number().numberBetween(1, 9999));
        pet.setCategory(category);
        pet.setName(faker().funnyName().name());
        pet.setPhotoUrls(Collections.singletonList(faker().internet().url()));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus(randomPetStatus.getStatus());

        return pet;
    }

    private PetCategoryName getRandomPetCategory() {
        int pick = new Random().nextInt(PetCategoryName.values().length);
        return PetCategoryName.values()[pick];
    }

    private PetTagName getRandomPetTagName() {
        int pick = new Random().nextInt(PetTagName.values().length);
        return PetTagName.values()[pick];
    }

    private PetStatus getRandomPetStatus() {
        int pick = new Random().nextInt(PetStatus.values().length);
        return PetStatus.values()[pick];
    }
}
