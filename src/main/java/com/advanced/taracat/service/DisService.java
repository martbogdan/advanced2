package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Hero;
import com.advanced.taracat.dao.entity.Location;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.DisRepository;
import com.advanced.taracat.dao.repository.LocationRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisService {

    @Autowired
    private DisRepository disRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserService userService;


    // Витяг всіх героїв юзера
    public List<Hero> getAllByUsername(String userName) {
        return disRepository.findAllByUser_Username(userName);
    }

    // Витяг всіх активних героїв юзера
    public List<Hero> getAllActiveByUsername(String userName) {
        List<Hero> heroes = disRepository.findAllByUser_Username(userName);
        List<Hero> heroesActive = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isActive()) {
                heroesActive.add(hero);
            }
        }
        return heroesActive;
    }

    // Знайти героя за id
    public Hero getHeroById(Long id) {
        return disRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    // Знайти героя за іменем
    public Hero getHeroByName(String heroName) {
        return disRepository.findHeroByName(heroName);
    }

    public void addHero(String heroName, String userName, int herorace, int heroclass) {

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
        hero.setActive(true);
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
        hero.setZoneX(1);
        hero.setZoneY(1);
        hero.setZoneInsideX(1);
        hero.setZoneInsideX(1);

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

    public void delete(Long id) {
        Optional<Hero> heroToDelete = disRepository.findById(id);
        if (heroToDelete.isPresent()) {
            disRepository.delete(heroToDelete.get());
        }
    }

    public Hero deleteMark(Long id) {
        Hero heroToMark = disRepository.findById(id).orElseThrow(NotFoundException::new);
        if (heroToMark.isActive() == true) {
            heroToMark.setActive(false);
        }
        return disRepository.save(heroToMark);
    }

    public int findZone(int zoneId) {

        if (zoneId == 1) {
            zoneId = 0;
        } else {
            zoneId = (zoneId-1)*50;
        }

        return zoneId;

    }

    public void updateZone( Long heroId, int zoneX, int zoneY) {

        Hero hero = getHeroById(heroId);

        if (hero != null) {
            hero.setZoneX(zoneX);
            hero.setZoneY(zoneY);
        }

        disRepository.save(hero);

    }

    public List<Location> getAllLocations () {
        return locationRepository.findAllByLocationOnOff(1);
    }

    public String checkLocationInZone(int x, int y) {

        List<Location> locations = getAllLocations();

        Location checkLocation;
        String locationInfo = "В данній зоні немає жодної локації";

        if (locations != null && locations.size() > 0) {

            System.out.println(x + " AND " + y);

            for (int i=0;i<locations.size();i++) {

                checkLocation = locations.get(i);

                if (checkLocation.getZoneIdX() == x && checkLocation.getZoneIdY() == y) {

                    locationInfo = checkLocation.getName();

                }

            }

        }

        return locationInfo;

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
