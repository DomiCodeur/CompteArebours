package CompteAr.backend;

import java.util.Date;


public class DateCalculator {
        //  calculateDaysRemaining "retourne le nombre de jours restants jusqu'à la date spécifiée.
    public static int calculateDaysRemaining(Date date) {
        long difference = date.getTime() - new Date().getTime();
        return (int) (difference / (1000 * 60 * 60 * 24));
    }

    // calculateMonthsRemaining(date)`** : retourne le nombre de mois restants jusqu'à la date spécifiée.
    public static int calculateMonthsRemaining(Date date) {
        int days = calculateDaysRemaining(date);
        return (int) (days / 30.4167);
    }

    // calculateYearsRemaining(date)`** : retourne le nombre d'années restantes jusqu'à la date spécifiée.
    public static int calculateYearsRemaining(Date date) {
        int days = calculateDaysRemaining(date);
        return (int) (days / 365);
    }

    // convertToDodos(date)`** : retourne le nombre de dodos restants jusqu'à la date spécifiée.
    public static int convertToDodos(Date date) {
        int days = calculateDaysRemaining(date);
        return (int) (days / 3.5);
    }
}
