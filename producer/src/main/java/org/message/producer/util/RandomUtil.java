package org.message.producer.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.message.producer.model.Address;
import org.message.producer.model.Comment;
import org.message.producer.model.Report;
import org.message.producer.model.User;
import org.message.producer.model.util.ReportStatus;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@UtilityClass
public class RandomUtil {

    public static User generateUser() {
        faker = getRandomFaker();
        Address address = generateAddress();
        List<Report> reports = generateReports();
        return new User(faker.idNumber().valid(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone(),
                faker.internet().emailAddress(),
                address,
                reports);
    }

    private static final Map<Integer, Locale> availableLocale = Map.of(
            1, new Locale("en-GB"),
            2, new Locale("en-US"),
            3, new Locale("pl_PL"),
            4, new Locale("de_DE"),
            5, new Locale("fr_FR")
    );

    private static Faker faker;

    private static Faker getRandomFaker() {
        int randomLocaleId = RandomUtils.nextInt(0, 6);
        Locale locale = availableLocale.get(randomLocaleId);
        return new Faker(locale);
    }

    private static Address generateAddress() {
        String streetName = faker.address().streetName();
        String number = faker.address().buildingNumber();
        String city = faker.address().city();
        String country = faker.address().country();
        return new Address(streetName, number, city, country);
    }

    private static List<Report> generateReports() {
        int numberOfReports = RandomUtils.nextInt(1, 10);
        List<Report> reports = new ArrayList<>();

        do {
            reports.add(generateReport());
            numberOfReports--;
        } while (numberOfReports > 0);

        return reports;
    }

    private static Report generateReport() {
        int summaryWords = RandomUtils.nextInt(1, 3);
        int sentenceCount = RandomUtils.nextInt(3, 6);
        return new Report(faker.lorem().sentence(summaryWords),
                faker.lorem().paragraph(sentenceCount),
                ReportStatus.randomReportStatus(),
                getComments());
    }

    private static List<Comment> getComments() {
        int commentsCount = RandomUtils.nextInt(6, 18);
        List<Comment> comments = new ArrayList<>();
        do {
            comments.add(new Comment(faker.lorem().paragraph()));
            commentsCount--;
        } while (commentsCount > 0);
        return comments;
    }
}
