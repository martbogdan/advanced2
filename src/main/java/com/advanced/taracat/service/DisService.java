package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Hero;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.DisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisService {

    @Autowired
    private DisRepository disRepository;
    @Autowired
    private UserService userService;


    // Витяг всіх героїв юзера
    public List<Hero> getAllByUsername(String userName){
        return disRepository.findAllByUser_Username(userName);
    }

    // Знайти героя за іменем
    public Hero getHeroByName (String heroName){
        return disRepository.findHeroByName(heroName);
    }

    public void addHero (String heroName, String userName, int herorace, int heroclass){

        /* Справочник

        Раси: Люди(1), Нечисть(2), Гноми(3), Дияволи(4), Ельфи(5);
        Типи Пошкоджень: Фіз Урон(1), Магія Повітря(2), Магія Смерті(3), Магія Землі(4), Магія Вогню(5);

        */

        User currentUser = userService.getUserByUsername(userName);

        Hero hero = new Hero();
        int heroSteps = 0;
        int heroHealth = 0;
        int heroInitiative = 0;
        int heroDamage = 0;
        int heroDamageCount = 0;
        int heroWeaponType = 0;

        hero.setUser(currentUser);
        hero.setName(heroName);
        hero.setHeroClass(heroclass);
        hero.setRace(herorace);
        hero.setLevel(1);
        hero.setLidership(3);
        hero.setExpirience(0);
        hero.setLeftExpirience(0);
        hero.setItems(0);
        hero.setArmor(0);
        hero.setAccuracy(80);
        hero.setAbilities(0);
        hero.setImmunitySlot(0);
        hero.setAttackType(0);
        hero.setDurability(0);

        if (heroclass == 1) { // Воїн
            heroSteps = 20;
            heroHealth = 150;
            heroInitiative = 50;
            heroDamage = 50;
            heroDamageCount = 1;
            heroWeaponType = 1;
        } else if (heroclass == 2) { // Маг
            heroSteps = 15;
            heroHealth = 75;
            heroInitiative = 40;
            heroDamage = 35;
            heroDamageCount = 6;

            if (herorace == 1) {
                heroWeaponType = 2; //Магія Повітря
            } else if (herorace == 2) {
                heroWeaponType = 3; //Магія Смерті
            } else if (herorace == 3) {
                heroWeaponType = 4; //Магія Землі
            } else if (herorace == 4) {
                heroWeaponType = 5; //Магія Вогню
            } else if (herorace == 5) {
                heroWeaponType = 2; //Магія Повітря
            }


        } else if (heroclass == 3) { // Рейнджер
            heroSteps = 25;
            heroHealth = 90;
            heroInitiative = 70;
            heroDamage = 40;
            heroDamageCount = 1;
            heroWeaponType = 1;
        }

        hero.setSteps(heroSteps);
        hero.setHealth(heroHealth);
        hero.setInitiative(heroInitiative);
        hero.setDamage(heroDamage);
        hero.setNumberOfGoals(heroDamageCount);
        hero.setWeaponType(heroWeaponType);

        disRepository.save(hero);

    }

/*    // Знайти всіх котів за Id
    public Cat getCatById (Long id){
        return catRepository.findCatById(id);
    }

    public Cat updateExpirience (Cat cat){

        Cat catDB = catRepository.findById(cat.getId()).get();

        int newExp = cat.getCat_expirience();
        int lastExp = cat.getCat_maxexpirience();
        int catLevel = cat.getCat_level();

        if (newExp >= lastExp) {
            cat.setCat_expirience(0);
            cat.setCat_level(catLevel+1);
        } else {
            cat.setCat_expirience(newExp);
            cat.setCat_level(catLevel);
        }

        catRepository.save(cat);

        return cat;

    }

    public void delete (Long id){
        Optional<Cat> toDelete = catRepository.findById(id);
        if (toDelete.isPresent()){
            catRepository.delete(toDelete.get());
        }
    }
*/

}
