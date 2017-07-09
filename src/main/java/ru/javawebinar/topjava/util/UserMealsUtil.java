package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededWithCycles(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = mealList.stream().
                collect(groupingBy(meal -> meal.getDateTime().toLocalDate(), summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededWithCycles(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();

        for (UserMeal meal : mealList) {
            caloriesPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (oldValue, currValue) -> oldValue + currValue);
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return result;
    }
}
